CREATE KEYSPACE tutorial WITH REPLICATION = { 'class' : 'SimpleStrategy', 'replication_factor' : 1 };

CREATE TYPE tutorial.location (city TEXT, zip_code TEXT, address TEXT);
CREATE TABLE tutorial.users (id UUID PRIMARY KEY, name TEXT, surname TEXT, creation_timestamp TIMESTAMP, tags list<TEXT>, location TEXT);

INSERT INTO tutorial.users (id, name, surname, tags) VALUES (650f8d60-22bf-4acf-9f9b-22ecf6368f06, 'test', 'test', ['1', '2']);