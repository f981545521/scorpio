-- ORDER模块
CREATE DATABASE IF NOT EXISTS `aquarius_order` DEFAULT CHARSET utf8mb4 COLLATE utf8mb4_bin;
use aquarius_order;

CREATE TABLE `t_order` (
   `order_id` int(10) unsigned NOT NULL AUTO_INCREMENT,
   `order_name` varchar(200) CHARACTER SET utf8mb4 NOT NULL DEFAULT '',
   `status` int(3) NOT NULL DEFAULT '0',
   PRIMARY KEY (`order_id`)
) ENGINE=InnoDB AUTO_INCREMENT=100 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_bin COMMENT='Order';

CREATE TABLE `undo_log` (
    `id` bigint(20) NOT NULL AUTO_INCREMENT,
    `branch_id` bigint(20) NOT NULL,
    `xid` varchar(100) NOT NULL,
    `context` varchar(128) NOT NULL,
    `rollback_info` longblob NOT NULL,
    `log_status` int(11) NOT NULL,
    `log_created` datetime NOT NULL,
    `log_modified` datetime NOT NULL,
    `ext` varchar(100) DEFAULT NULL,
    PRIMARY KEY (`id`),
    UNIQUE KEY `ux_undo_log` (`xid`,`branch_id`)
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8 Comment = 'seata日志表';



-- PRODUCT模块
CREATE DATABASE IF NOT EXISTS `aquarius_product` DEFAULT CHARSET utf8mb4 COLLATE utf8mb4_bin;
use aquarius_product;

CREATE TABLE `t_product` (
    `product_id` int(10) unsigned NOT NULL AUTO_INCREMENT,
    `product_name` varchar(200) CHARACTER SET utf8mb4 NOT NULL DEFAULT '',
    `status` int(3) NOT NULL DEFAULT '0',
    `stock_number` int(3) NOT NULL DEFAULT '0',
    PRIMARY KEY (`product_id`)
) ENGINE=InnoDB AUTO_INCREMENT=100 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_bin COMMENT='Product';
INSERT INTO `aquarius_product`.`t_product`(`product_id`, `product_name`, `status`, `stock_number`) VALUES (1, '笔记本', 0, 1000);


CREATE TABLE `undo_log` (
    `id` bigint(20) NOT NULL AUTO_INCREMENT,
    `branch_id` bigint(20) NOT NULL,
    `xid` varchar(100) NOT NULL,
    `context` varchar(128) NOT NULL,
    `rollback_info` longblob NOT NULL,
    `log_status` int(11) NOT NULL,
    `log_created` datetime NOT NULL,
    `log_modified` datetime NOT NULL,
    `ext` varchar(100) DEFAULT NULL,
    PRIMARY KEY (`id`),
    UNIQUE KEY `ux_undo_log` (`xid`,`branch_id`)
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8 Comment = 'seata日志表';
