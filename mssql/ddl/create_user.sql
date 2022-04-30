USE ldbc;
CREATE LOGIN ldbc_admin WITH PASSWORD = 'MySecr3tP4ssword';
CREATE USER ldbc_admin FOR LOGIN ldbc_admin;
ALTER SERVER ROLE [sysadmin] ADD MEMBER ldbc_admin;
