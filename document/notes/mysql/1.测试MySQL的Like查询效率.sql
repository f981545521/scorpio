-- 准备数据
-- 1.1 创建表
CREATE TABLE `product` (
    `id` int(10) NOT NULL AUTO_INCREMENT COMMENT '主键,不为空',
    `merchant_id` int(11) DEFAULT NULL COMMENT '商家id',
    `product_name` varchar(128) CHARACTER SET utf8 COLLATE utf8_bin DEFAULT NULL COMMENT '商品名称',
    `product_title` varchar(128) CHARACTER SET utf8 COLLATE utf8_bin DEFAULT NULL COMMENT '商品标题',
    `classify_1` int(10) DEFAULT NULL COMMENT '1级分类',
    `classify_2` int(10) DEFAULT NULL COMMENT '2级分类',
    `classify_3` int(10) DEFAULT NULL COMMENT '3级分类',
    `brand_id` int(10) DEFAULT NULL COMMENT '品牌',
    `product_amount` decimal(10,0) DEFAULT NULL COMMENT '商品金额',
    `sales_amount` decimal(10,0) DEFAULT NULL COMMENT '销售金额',
    `product_status` int(10) DEFAULT NULL COMMENT '商品状态,1删除，0正常',
    `flag` int(10) DEFAULT NULL COMMENT '标记',
    `freight` decimal(10,2) DEFAULT NULL COMMENT '运费',
    `sku_info` varchar(255) CHARACTER SET utf8 COLLATE utf8_bin DEFAULT NULL COMMENT '规格说明',
    `remarks` varchar(255) CHARACTER SET utf8 COLLATE utf8_bin DEFAULT NULL COMMENT '售后说明',
    `pic_url` varchar(500) CHARACTER SET utf8 COLLATE utf8_bin DEFAULT NULL COMMENT '预览图片地址',
    `create_time` datetime DEFAULT NULL COMMENT '创建时间',
    `update_time` datetime DEFAULT NULL COMMENT '修改时间',
    `user_id` int(10) DEFAULT NULL COMMENT '操作人',
    `user_ip` varchar(128) CHARACTER SET utf8 COLLATE utf8_bin DEFAULT NULL COMMENT '操作人ip',
    `product_name_query` varchar(255) GENERATED ALWAYS AS (locate('8',`product_name`)) STORED,
    PRIMARY KEY (`id`),
    FULLTEXT KEY `index_name` (`product_name`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='商品信息表';

-- 1.2 创建存储过程
CREATE DEFINER=`root`@`localhost` PROCEDURE `insert_product3`(IN total BIGINT)
BEGIN
DECLARE start_key BIGINT DEFAULT 0;
DECLARE max_id BIGINT DEFAULT 0;
SELECT COUNT(1) into max_id from product;
WHILE start_key<total
DO
SET max_id=max_id+1;
INSERT INTO product(product_title,product_name,remarks,merchant_id,create_time,update_time,classify_1,classify_2,classify_3,brand_id,user_id,user_ip,freight,pic_url,sales_amount,product_amount,product_status)
VALUES(CONCAT('商品_iphone8_title_test_',max_id),CONCAT('商品_iphone8_name_test_',max_id),CONCAT('商品_iphone8_remrk_test_',max_id),max_id,NOW(),NOW(),1,1,1,1,1000,'192.168.18.100',5000,'http://www.baidu.com',5000,5000,0);
SET start_key=start_key+1;
COMMIT;
END WHILE;
END

-- 1.3 调用存储过程
-- 数字是要增加记录的数量，如果数字越大、消耗的世界越长，参考时间：
-- 100w 1600s 26min
-- 200w 3122s 26min
--
-- call insert_product3(2000000)
-- > OK
-- > 时间: 3122.488s
call insert_product3(1000);







