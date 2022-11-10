alter role postgres with superuser login password 'mysecretpassword';
drop database if exists ldbcsnb;
create database ldbcsnb owner postgres;
