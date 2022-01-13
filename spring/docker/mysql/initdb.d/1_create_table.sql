CREATE DATABASE anypoint_20220113;
CREATE DATABASE demo;
USE demo;

GRANT ALL ON `demo`.* TO 'demo'@'%';
FLUSH PRIVILEGES;

CREATE TABLE user (
    id bigint(20) NOT NULL AUTO_INCREMENT,
    login_id VARCHAR(255) NOT NULL,
    email VARCHAR(255) NOT NULL,
    password VARCHAR(2000) NOT NULL,

    PRIMARY KEY (id)
);

CREATE TABLE `order` (
    id bigint(20) NOT NULL AUTO_INCREMENT,
    order_user bigint(20) NOT NULL,

    PRIMARY KEY (id),
    CONSTRAINT order_user FOREIGN KEY (order_user) REFERENCES user(id)
);

CREATE TABLE product (
    id bigint(20) NOT NULL AUTO_INCREMENT,
    name VARCHAR(255) NOT NULL,
    price INT NOT NULL,

    PRIMARY KEY (id)
);

CREATE TABLE order_product (
    id bigint(20) NOT NULL AUTO_INCREMENT,
    `order` bigint(20) NOT NULL,
    product bigint(20) NOT NULL,

    PRIMARY KEY (id),
    CONSTRAINT order_product_order FOREIGN KEY (`order`) REFERENCES `order`(id),
    CONSTRAINT order_product_product FOREIGN KEY (product) REFERENCES product(id)
);
