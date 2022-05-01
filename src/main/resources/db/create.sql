SET MODE PostgreSQL;
DROP TABLE heroes;
CREATE TABLE IF NOT EXISTS heroes (
  id int PRIMARY KEY auto_increment,
  name VARCHAR,
  age INT,
  powers VARCHAR,
  weakness VARCHAR,
  squadsId INTEGER
  completes BOOLEAN,
);
DROP TABLE squads;
CREATE TABLE IF NOT EXISTS squads (
  id int PRIMARY KEY auto_increment,
  name VARCHAR,
  cause VARCHAR,
  maxSize INT
);