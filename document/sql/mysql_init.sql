CREATE TABLE `student`(
    `id`    int(10) NOT NULL AUTO_INCREMENT,
    `name`  varchar(200) CHARACTER SET utf8mb4 NOT NULL DEFAULT '',
    `age`   int(3) NOT NULL DEFAULT '0',
    `birth` datetime not null default now(),
    PRIMARY KEY (`id`)
) ENGINE = InnoDB DEFAULT CHARSET = utf8mb4 COLLATE = utf8mb4_unicode_ci COMMENT ='学生';

-- ----------------------------
-- Records of student
-- ----------------------------
INSERT INTO `student` VALUES ('1', '刘备', '34', now());
INSERT INTO `student` VALUES ('2', '曹操', '45', now());
INSERT INTO `student` VALUES ('3', '孙权', '43', now());
INSERT INTO `student` VALUES ('4', '袁绍', '38', now());
INSERT INTO `student` VALUES ('5', '马腾', '58', now());
INSERT INTO `student` VALUES ('6', '关羽', '23', now());
INSERT INTO `student` VALUES ('7', '史蒂夫', '27', now());

-- 定时任务
CREATE TABLE `t_task_schedule_job`(
    `job_id`          bigint(20) NOT NULL AUTO_INCREMENT COMMENT '任务id',
    `bean_name`       varchar(200)  DEFAULT NULL COMMENT 'spring bean名称',
    `params`          varchar(2000) DEFAULT NULL COMMENT '参数',
    `cron_expression` varchar(100)  DEFAULT NULL COMMENT 'cron表达式',
    `status`          int(1)        DEFAULT NULL COMMENT '任务状态  0：正常  1：暂停',
    `remark`          varchar(255)  DEFAULT NULL COMMENT '备注',
    `create_time`     datetime      DEFAULT NULL COMMENT '创建时间',
    PRIMARY KEY (`job_id`)
) ENGINE = `InnoDB` DEFAULT CHARACTER SET utf8mb4 COMMENT ='定时任务';
INSERT INTO `t_task_schedule_job`(`job_id`, `bean_name`, `params`, `cron_expression`, `status`, `remark`, `create_time`) VALUES (1, 'myDynamicTask', NULL, '0/5 * * * * *', 0, NULL, '2020-04-04 21:56:48');

-- 定时任务日志
drop table if exists t_task_schedule_job_log;
CREATE TABLE `t_task_schedule_job_log`(
    `log_id`      bigint(20) NOT NULL AUTO_INCREMENT COMMENT '任务日志id',
    `job_id`      bigint(20) NOT NULL COMMENT '任务id',
    `bean_name`   varchar(200)  DEFAULT NULL COMMENT 'spring bean名称',
    `params`      varchar(2000) DEFAULT NULL COMMENT '参数',
    `status`      int(1)     NOT NULL COMMENT '任务状态    0：成功    1：失败',
    `error`       varchar(2000) DEFAULT NULL COMMENT '失败信息',
    `times`       int(11)    NOT NULL COMMENT '耗时(单位：毫秒)',
    `create_time` datetime      DEFAULT NULL COMMENT '创建时间',
    `remark`      varchar(2000) DEFAULT NULL COMMENT '备注',
    PRIMARY KEY (`log_id`),
    KEY `job_id` (`job_id`)
) ENGINE = `InnoDB` DEFAULT CHARACTER SET utf8mb4 COMMENT ='定时任务日志';








