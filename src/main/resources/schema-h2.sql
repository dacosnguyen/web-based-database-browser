DROP TABLE IF EXISTS connection_detail;

CREATE TABLE connection_detail (
  id            INTEGER SERIAL PRIMARY KEY NOT NULL ,
  name          VARCHAR(64) NOT NULL,
  hostname      VARCHAR(64) NOT NULL,
  port          INTEGER NOT NULL,
  databaseName  VARCHAR(64) NOT NULL,
  username      VARCHAR(64) NOT NULL,
  password      VARCHAR(128) NOT NULL,
  description   VARCHAR(256)
  );
