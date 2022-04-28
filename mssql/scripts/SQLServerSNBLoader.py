# Work in Progress
import dask.dataframe as dd
from datetime import datetime
import os 

class SQLServerSNBLoader:

    def __init__(self, path_to_data):
        """
        Args:
            - path_to_data (str): Path where the data is stored.
        """
        self.data_path = path_to_data

    def load_person_data(self):
        filepath = os.path.join(self.path_to_data, "person_0_0.csv").replace("\\","/")
        dask_df = dd.read_csv(filepath, sep='|')
        return dask_df

    def load_knows_data(self):
        filepath = os.path.join(self.path_to_data, "person_knows_person_0_0.csv").replace("\\","/")
        dask_df = dd.read_csv(filepath, sep='|')
        return dask_df

    def insert_person(self, p_personid, p_firstname, p_lastname, p_gender, p_birthday, p_creationdate, p_locationip, p_browserused, p_placeid):
        """
        """
        stmt = "INSERT INTO dbo.person VALUES (?, ?,?,?,?,?,?,?,?);"

        con.execute(stmt, (p_personid, p_firstname, p_lastname, p_gender, datetime.strptime(p_birthday, '%Y-%m-%d'), datetime.strptime(p_creationdate, '%Y-%m-%dT%H:%M:%S.%f%z'), p_locationip, p_browserused, p_placeid))

    def insert_persons_sql_graph(self):
        """
        Inserts all the persons in the SQL Server database in a 
        'node' table.
        """
        dask_df = self.load_person_data
        dask_df.apply(lambda row : self.insert_person(
            row['id'],
            row['firstName'],
            row['lastName'],
            row['gender'],
            row['birthday'],
            row['creationDate'],
            row['locationIP'],
            row['browserUsed'],
            row['place'],
        ), axis = 1)

    def insert_knows(self, person1id, person2id, creationdate):
        # print((person1id, person2id, creationdate))
        stmt = "INSERT INTO dbo.knows VALUES ((SELECT $node_id FROM dbo.person WHERE p_personid = ?),(SELECT $node_id FROM dbo.person WHERE p_personid = ?),?, ?, ?);"
        con.execute(stmt, (person1id, person2id,person1id, person2id, datetime.strptime(creationdate, '%Y-%m-%dT%H:%M:%S.%f%z')))
        con.execute(stmt, (person2id, person1id,person2id, person1id, datetime.strptime(creationdate, '%Y-%m-%dT%H:%M:%S.%f%z')))

    def insert_knows_sql_graph(self, internal_csv_path):
        """
        Inserts all the knows in the SQL Server database in a 
        'edge' table.
        Args:
            internal_csv_path (str): 
        """
        chunksize = 10 ** 6
        with pd.read_csv(internal_csv_path, chunksize=chunksize, sep='|') as reader:
            for chunk in reader:
                chunk.apply(lambda row : self.insert_knows(
                    row[0],
                    row[1],
                    row[2],
                ), axis = 1)

