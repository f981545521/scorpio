-- ORDER模块
CREATE DATABASE IF NOT EXISTS `aquarius_order` DEFAULT CHARSET utf8mb4 COLLATE utf8mb4_bin;
use aquarius_order;

CREATE TABLE `t_order` (
   `order_id` int(10) unsigned NOT NULL AUTO_INCREMENT,
   `order_name` varchar(200) CHARACTER SET utf8mb4 NOT NULL DEFAULT '',
   `status` int(3) NOT NULL DEFAULT '0',
   PRIMARY KEY (`order_id`)
) ENGINE=InnoDB AUTO_INCREMENT=100 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_bin COMMENT='Order';

-- PRODUCT模块
CREATE DATABASE IF NOT EXISTS `aquarius_product` DEFAULT CHARSET utf8mb4 COLLATE utf8mb4_bin;
use aquarius_product;

CREATE TABLE `t_product` (
    `product_id` int(10) unsigned NOT NULL AUTO_INCREMENT,
    `product_name` varchar(200) CHARACTER SET utf8mb4 NOT NULL DEFAULT '',
    `status` int(3) NOT NULL DEFAULT '0',
    PRIMARY KEY (`product_id`)
) ENGINE=InnoDB AUTO_INCREMENT=100 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_bin COMMENT='Product';