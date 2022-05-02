import pandas as pd
from multiprocessing import Pool
import os 
import numpy as np
from datetime import datetime

class SQLServerSNBLoader:

    def __init__(self, path_to_data, db_datasource):
        """
        Args:
            - path_to_data (str): Path where the data is stored.
            - db_datasource (DBLoader): A DBLoader object containing a
                                        get_connection() function with an
                                        implemented connection pool.
        """
        self.data_path = path_to_data
        self.db_datasource = db_datasource

    def __get_df_from_csv(self, csv_file) -> pd.DataFrame:
        """
        Load dataframe from csv
        Args:
            - csv_file (str): csv-filename
        """
        filepath = os.path.join(self.data_path, csv_file).replace("\\","/")
        chunksize = 10 ** 6
        df_list = []
        with pd.read_csv(filepath, chunksize=chunksize, sep='|') as reader:
            for chunk in reader:
                df_list.append(chunk)
        return pd.concat(df_list)

    def insert_persons_sql_graph(self) -> None:
        """
        Inserts all the persons in the SQL Server database in a 
        'node' table.
        """
        df = self.__get_df_from_csv("person_0_0.csv")
        self.__insert_asynchronous(df, self.handle_person_df)

    def insert_knows_sql_graph(self) -> None:
        """
        Inserts all the knows relations in the SQL Server database in an
        'edge' table.
        """
        df = self.__get_df_from_csv("person_knows_person_0_0.csv")
        self.__insert_asynchronous(df, self.handle_knows_df)

    def handle_knows_df(self, df) -> None:
        """
        Worker function to insert rows from friendship dataframe.
        Args:
            - df (pd.DataFrame): The dataframe to insert
        """
        con = self.db_datasource.get_connection()
        df.apply(lambda row : self.insert_knows(
            row[0],
            row[1],
            row[2],
            con
        ), axis = 1)
        con.close()

    def handle_person_df(self, df) -> None:
        """
        Worker function to insert rows from person dataframe.
        Args:
            - df (pd.DataFrame): The dataframe to insert
        """
        con = self.db_datasource.get_connection()
        df.apply(lambda row : self.insert_person(
            row['id'],
            row['firstName'],
            row['lastName'],
            row['gender'],
            row['birthday'],
            row['creationDate'],
            row['locationIP'],
            row['browserUsed'],
            row['place'],
            con
        ), axis = 1)
        con.close()

    def insert_person(self, p_personid, p_firstname, p_lastname, p_gender,
        p_birthday, p_creationdate, p_locationip, p_browserused, p_placeid, 
        con
        ) -> None:
        """
        Insert person node in person table.
        Args:
            - p_personid (str): ID of the person
            - p_firstname (str): Firstname of the person
            - p_lastname (str): Lastname of the person
            - p_gender (str): Gender
            - p_birthday (str): Birthday
            - p_creationdate (str): Creationdate
            - p_locationip (str): Location IP Address
            - p_browserused (str): Browser
            - p_placeid (str): Place ID
        """
        stmt = "USE ldbc; INSERT INTO person VALUES (?, ?,?,?,?,?,?,?,?);"
        con.execute(stmt,
            (
                p_personid,
                p_firstname,
                p_lastname,
                p_gender,
                datetime.strptime(p_birthday, '%Y-%m-%d'),
                datetime.strptime(p_creationdate, '%Y-%m-%dT%H:%M:%S.%f%z'),
                p_locationip,
                p_browserused,
                p_placeid
            )
        )

    def insert_knows(self, person1id, person2id, creationdate, con):
        """
        Insert friendship relation between two persons.
        Args:
            - person1id (str): ID of person 1
            - person2id (str): ID of person 2
            - creationdate (str): Date of creation of the relation
            - con (object): Connection object
        """
        stmt = "USE ldbc; INSERT INTO knows VALUES ((SELECT $node_id FROM person WHERE p_personid = ?),(SELECT $node_id FROM person WHERE p_personid = ?),?, ?, ?);"
        con.execute(stmt,(person1id, person2id,person1id, person2id, datetime.strptime(creationdate, '%Y-%m-%dT%H:%M:%S.%f%z')))
        con.execute(stmt, (person2id, person1id,person2id, person1id, datetime.strptime(creationdate, '%Y-%m-%dT%H:%M:%S.%f%z')))

    def __insert_asynchronous(self, df, insert_function) -> None:
        """
        Inserts dataframe data using multiprocessing
        Args:
            - df (pd.DataFrame): The dataframe to insert in the database
            - insert_function (func): Pointer to the insert function
        """
        df_list = np.array_split(df, os.cpu_count())

        with Pool(os.cpu_count()) as p:
            p.map(insert_function, df_list)

