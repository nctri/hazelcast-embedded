CREATE USER hazelcast IDENTIFIED BY oracle;
GRANT CONNECT TO hazelcast;
GRANT CONNECT, RESOURCE, DBA TO hazelcast;
GRANT UNLIMITED TABLESPACE TO hazelcast;

alter profile DEFAULT limit password_life_time UNLIMITED;
alter user hazelcast identified by oracle account unlock;
GRANT ALL PRIVILEGES TO hazelcast;


commit;

SET SCHEMA hazelcast;

CREATE TABLE hazelcast.TBL_EMPLOYEES (
                               id NUMBER GENERATED ALWAYS as IDENTITY(START with 1 INCREMENT by 1)  PRIMARY KEY,
                               first_name VARCHAR2(250) NOT NULL,
                               last_name VARCHAR(2250) NOT NULL,
                               email VARCHAR2(250) DEFAULT NULL
);

INSERT INTO hazelcast.TBL_EMPLOYEES (first_name, last_name, email) VALUES ('Lokesh', 'Gupta', 'abc@gmail.com');
INSERT INTO hazelcast.TBL_EMPLOYEES (first_name, last_name, email) VALUES ('Deja', 'Vu', 'xyz@email.com');
INSERT INTO hazelcast.TBL_EMPLOYEES (first_name, last_name, email) VALUES ('Caption', 'America', 'cap@marvel.com');