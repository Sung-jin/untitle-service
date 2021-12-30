USE demo;

INSERT INTO `user` VALUES (1,'admin','admin@demo.com','$2a$10$PjL4PrcP41Q3B61fZBXBRemrQm/OxLxyv2FFLl6y1WxZkP1/UPbQa');

INSERT INTO `product` VALUES (1,'상품이름 1',10000),(2,'상품이름 2',20000),(3,'상품이름 3',30000);

INSERT INTO `order` VALUES (1,1);

INSERT INTO `order_product` VALUES (1,1,1),(2,1,2),(3,1,3);