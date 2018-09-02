CREATE TABLE appUser (
  id int(11) NOT NULL AUTO_INCREMENT,
  firstName varchar(45) DEFAULT NULL,
  lastName varchar(45) DEFAULT NULL,
  dob date DEFAULT NULL,
  email varchar(45) NOT NULL,
  pw varchar(45) DEFAULT NULL,
  PRIMARY KEY (id),
  UNIQUE KEY id_UNIQUE (id),
  UNIQUE KEY email_UNIQUE (email)
);