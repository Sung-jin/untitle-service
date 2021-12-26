CREATE DATABASE demo;
USE demo;

GRANT ALL ON `demo`.* TO 'demo'@'%';
FLUSH PRIVILEGES;

CREATE TABLE user (
    id bigint(20) NOT NULL AUTO_INCREMENT,
    email VARCHAR(255),
    password VARCHAR(2000),

    PRIMARY KEY (id)
);