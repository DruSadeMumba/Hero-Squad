SET MODE PostgreSQL;
DROP TABLE heroes;
CREATE TABLE IF NOT EXISTS heroes (
  id int PRIMARY KEY auto_increment,
  name VARCHAR,
  age INT,
  powers VARCHAR,
  weakness VARCHAR,
  squadId INTEGER,
  completes BOOLEAN
);

CREATE TABLE IF NOT EXISTS squads (
  id int PRIMARY KEY auto_increment,
  name VARCHAR,
  cause VARCHAR,
  maxSize INT
);