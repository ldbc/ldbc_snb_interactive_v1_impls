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

    def __get_df_from_csv(self, csv_file, types=None) -> pd.DataFrame:
        """
        Load dataframe from csv
        Args:
            - csv_file (str): csv-filename
        """
        filepath = os.path.join(self.data_path, csv_file).replace("\\","/")
        chunksize = 10 ** 6
        df_list = []
        with pd.read_csv(filepath, chunksize=chunksize, sep='|', converters=types) as reader:
            for chunk in reader:
                df_list.append(chunk)
        return pd.concat(df_list)

    def insert_persons_sql_graph(self) -> None:
        """
        Inserts all the persons in the SQL Server database in a 
        'node' table.
        """
        df = self.__get_df_from_csv("dynamic/person_0_0.csv")
        self.__insert_asynchronous(df, self.insert_person)

    def insert_knows_sql_graph(self) -> None:
        """
        Inserts all the knows relations in the SQL Server database in an
        'edge' table.
        """
        df = self.__get_df_from_csv("dynamic/person_knows_person_0_0.csv")
        self.__insert_asynchronous(df, self.insert_knows)

    def insert_places_to_sql(self) -> None:
        """
        Inserts all the places in the SQL Server database UTF-8.
        Types is set since 'isPartOf' is a nullable integer, creating errors
        when Pandas tries to infer the datatype.
        """
        df = self.__get_df_from_csv(
            "static/place_0_0.csv",
            types={
                "id":int,
                "name":str,
                "url":str,
                "type":str,
                "isPartOf":str
        })
        self.__insert_asynchronous(df, self.insert_place)

    def insert_tag_to_sql(self) -> None:
        """
        Insert tag to sql (unicode workaround)
        """
        df = self.__get_df_from_csv(
            "static/tag_0_0.csv",
            types={
                "id":int,
                "name":str,
                "url":str,
                "hasType":str,
        })
        self.__insert_asynchronous(df, self.insert_tag)

    def insert_tagclass_to_sql(self) -> None:
        """
        Insert tagclass to sql (unicode workaround)
        """
        df = self.__get_df_from_csv(
            "static/tagclass_0_0.csv",
            types= {
                "id":int,
                "name":str,
                "url":str,
                "isSubclassOf":str,
        })
        self.__insert_asynchronous(df, self.insert_tagclass)

    def insert_post_to_sql(self) -> None:
        """
        Insert post to sql (unicode workaround)
        """
        df = self.__get_df_from_csv(
            "dynamic/post_0_0.csv",
            types= {
                "id":int,
                "imageFile":str,
                "creationDate":str,
                "locationIP":str,
                "browserUsed":str,
                "language":str,
                "content":str, #unicode sensitive
                "length":int,
                "creator":int,
                "Forum.id":str, # can be null
                "place":int 
        })
        self.__insert_asynchronous(df, self.insert_post)

    def insert_comment_to_sql(self) -> None:
        """
        Insert comment to sql (unicode workaround)
        """
        df = self.__get_df_from_csv(
            "dynamic/comment_0_0.csv",
            types= {
                "id":int,
                "creationDate":str,
                "locationIP":str,
                "browserUsed":str,
                "content":str, #unicode sensitive
                "length":int,
                "creator":int,
                "replyOfPost":str,
                "replyOfComment":str 
        })
        self.__insert_asynchronous(df, self.insert_comment)

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
        stmt = u"USE ldbc; INSERT INTO person VALUES (?, ?,?,?,?,?,?,?,?);"
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

    def insert_place(self, pl_placeid, pl_name, pl_url, pl_type, pl_containerplaceid, con):
        """
        Insert static place information. This function is to ensure the unicode characters
        are inserted correctly (which is as of 4-5-2022 not possible using BULK INSERT in
        Linux containers).
        Args:
            - pl_placeid (int):
            - pl_name (str):
            - pl_url (str):
            - pl_type (str):
            - pl_containerplaceid (str):
            - con (object): Connection object
        """
        # This part is to insert unicode characters correctly into SQL Server. 
        # TODO: An alternative is to use FreeTDS driver, future research
        pl_name = pl_name.replace("'", "''")
        stmt = "USE ldbc; INSERT INTO place VALUES (?, N'" + pl_name + "', ?, ?, ?)"
        if pl_containerplaceid == '':
            pl_containerplaceid = None
        con.execute(stmt, 
            (
                pl_placeid, pl_url, pl_type, pl_containerplaceid
            )
        )

    def insert_tag(self, t_tagid, t_name, t_url, t_tagclassid, con):
        """
        Insert static tag information. This function is to ensure the unicode characters
        are inserted correctly (which is as of 4-5-2022 not possible using BULK INSERT in
        Linux containers).
        Args:
            - t_tagid (int):
            - t_name (str):
            - t_url (str):
            - t_tagclassid (str):
            - con (object): Connection object
        """
        t_name = t_name.replace("'", "''")
        stmt = "USE ldbc; INSERT INTO tag VALUES (?, N'" + t_name + "', ?, ?)"
        con.execute(stmt, 
            (
                t_tagid, t_url, t_tagclassid
            )
        )

    def insert_tagclass(self, tc_tagclassid, tc_name, tc_url, tc_subclassoftagclassid, con):
        """
        Insert static tagclass information. This function is to ensure the unicode characters
        are inserted correctly (which is as of 4-5-2022 not possible using BULK INSERT in
        Linux containers).
        Args:
            - tc_tagclassid (int):
            - tc_name (str):
            - tc_url (str):
            - tc_subclassoftagclassid (str):
            - con (object): Connection object
        """
        # This part is to insert unicode characters correctly into SQL Server. 
        # TODO: An alternative is to use FreeTDS driver, future research
        tc_name = tc_name.replace("'", "''")
        stmt = "USE ldbc; INSERT INTO tagclass VALUES (?, N'" + tc_name + "', ?, ?)"
        if tc_subclassoftagclassid == '':
            tc_subclassoftagclassid = None
        con.execute(stmt, 
            (
                tc_tagclassid, tc_url, tc_subclassoftagclassid
            )
        )

    def insert_post(
        self,
        m_messageid,
        m_ps_imagefile,
        m_creationdate,
        m_locationip,
        m_browserused,
        m_ps_language,
        m_content, #unicode sensitive
        m_length,
        m_creatorid,
        m_ps_forumid,
        m_locationid,
        con
    ):
        """
        Insert static tagclass information. This function is to ensure the unicode characters
        are inserted correctly (which is as of 4-5-2022 not possible using BULK INSERT in
        Linux containers).
        Args:
            - con (object): Connection object
        """
        m_content = m_content.replace("'", "''")
        stmt = "USE ldbc; INSERT INTO post VALUES (?, ?, ?, ?, ?, ?, N'" + m_content + "', ?, ?, ?, ?)"
        # pstmt = f"USE ldbc; INSERT INTO post VALUES ({m_messageid}, {m_ps_imagefile}, {datetime.strptime(m_creationdate, '%Y-%m-%dT%H:%M:%S.%f%z')}, {m_locationip}, {m_browserused}, {m_ps_language} N'" + m_content + f"', {m_length}, {m_creatorid}, {m_ps_forumid}, {m_locationid})"
        if m_ps_forumid == '':
            m_ps_forumid = None
        # print(pstmt)
        con.execute(stmt, 
            (
                m_messageid,
                m_ps_imagefile,
                datetime.strptime(m_creationdate, '%Y-%m-%dT%H:%M:%S.%f%z'),
                m_locationip,
                m_browserused,
                m_ps_language,
                m_length,
                m_creatorid,
                m_ps_forumid,
                m_locationid,
            )
        )

    def insert_comment(
        self,
        m_messageid,# bigint primary key,
        m_creationdate,# datetime2,
        m_locationip,# varchar(MAX) not null,
        m_browserused,# varchar(MAX) not null,
        m_content,# ntext,
        m_length,# int not null,
        m_creatorid,# bigint not null,
        m_locationid,# bigint not null,
        m_replyof_post,# bigint,
        m_replyof_comment,# bigint
        con
    ):
        """
        Insert static tagclass information. This function is to ensure the unicode characters
        are inserted correctly (which is as of 4-5-2022 not possible using BULK INSERT in
        Linux containers).
        Args:
            - con (object): Connection object
        """
        m_content = m_content.replace("'", "''")
        stmt = "USE ldbc; INSERT INTO comment VALUES (?,?,?,?, N'" + m_content + "',?,?,?,?,?)"
        if m_replyof_post == '':
            m_replyof_post = None
        if m_replyof_comment == '':
            m_replyof_comment = None
        con.execute(stmt, 
            (
                m_messageid,
                datetime.strptime(m_creationdate, '%Y-%m-%dT%H:%M:%S.%f%z'),
                m_locationip,
                m_browserused,
                m_length,
                m_creatorid,
                m_locationid,
                m_replyof_post,
                m_replyof_comment
            )
        )

    def handle_df(self, df, insert_func):
        """
        Helper function to pass row values to insert function.
        It handles the connection per thread (which is from the
        pyodbc connectionpool)
        Args:
            - df (pd.DataFrame): The dataframe to insert in the database
            - insert_function (func): Pointer to the insert function
        """
        con = self.db_datasource.get_connection()
        df.apply(lambda row : insert_func(*row.values, con), axis = 1)
        con.close()

    def __insert_asynchronous(self, df, insert_function) -> None:
        """
        Inserts dataframe data using multiprocessing. Splits the
        pd.DataFrame to the number of cpu threads available.
        Args:
            - df (pd.DataFrame): The dataframe to insert in the database
            - insert_function (func): Pointer to the insert function
        """
        df_list = np.array_split(df, os.cpu_count())
        uow = [(df_part, insert_function) for df_part in df_list]

        with Pool(os.cpu_count()) as p:
            p.starmap(self.handle_df, uow)
