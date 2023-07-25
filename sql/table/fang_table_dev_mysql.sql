/*
 Navicat Premium Data Transfer

 Source Server         : 47.107.47.184_3306
 Source Server Type    : MySQL
 Source Server Version : 80032
 Source Host           : 47.107.47.184:3306
 Source Schema         : base_spring

 Target Server Type    : MySQL
 Target Server Version : 80032
 File Encoding         : 65001

 Date: 24/03/2023 05:33:09
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for sys_account
-- ----------------------------
DROP TABLE IF EXISTS `sys_account`;
CREATE TABLE `sys_account`  (
  `id` varchar(36) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL,
  `username` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '账号名',
  `phone` varchar(11) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '电话号码',
  `password` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '密码',
  `deleted` tinyint(1) NOT NULL DEFAULT 0,
  `created_by` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL,
  `created_date` timestamp(6) NULL DEFAULT NULL,
  `last_modified_by` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL,
  `last_modified_date` timestamp(6) NULL DEFAULT NULL,
  `gender` varchar(1) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '性别男1女0',
  `status` tinyint(1) NOT NULL DEFAULT 1 COMMENT '状态 1正常0异常',
  `id_card` varchar(18) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '身份证',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci ROW_FORMAT = Dynamic;

SET FOREIGN_KEY_CHECKS = 1;

ALTER TABLE `base_spring`.`sys_account`
    ADD COLUMN `app_id` varchar(20) NOT NULL COMMENT '所属系统' AFTER `id_card`;

ALTER TABLE `base_spring`.`sys_account`
    ADD COLUMN `is_super` tinyint(1) NULL COMMENT '1表示是超管 0表示不是' AFTER `app_id`;

ALTER TABLE `base_spring`.`sys_account`
    MODIFY COLUMN `is_super` tinyint(1) NOT NULL DEFAULT 0 COMMENT '1表示是超管 0表示不是' AFTER `app_id`,
    ADD COLUMN `avatar` varchar(500) NULL COMMENT '头像' AFTER `is_super`;

ALTER TABLE `base_spring`.`sys_account`
    ADD COLUMN `remember_me` tinyint(1) NOT NULL DEFAULT 0 COMMENT '记住我 0不记住' AFTER `avatar`;

-- ----------------------------
-- Records of sys_account
-- ----------------------------
INSERT INTO `sys_account` VALUES ('1', 'admin', '18206004582', '$2a$10$I2nceupoe8KEDKzRy50vuOp2xlop6vxAKyx1tvVSZsuyxz3I9AMTq', 0, NULL, NULL, NULL, NULL, '1', 1, NULL, 'pc', 1, NULL, 0);


-- ----------------------------
-- Table structure for sys_role
-- ----------------------------
DROP TABLE IF EXISTS `sys_role`;
CREATE TABLE `sys_role`  (
                             `id` varchar(36) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL,
                             `role_name` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '角色名称',
                             `status` tinyint(1) NULL DEFAULT 1 COMMENT '1启用 0禁用',
                             `created_by` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL,
                             `created_date` timestamp(6) NULL DEFAULT NULL,
                             `last_modified_by` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL,
                             `last_modified_date` timestamp(6) NULL DEFAULT NULL,
                             `deleted` tinyint(1) NOT NULL DEFAULT 0,
                             PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci ROW_FORMAT = Dynamic;

ALTER TABLE `base_spring`.`sys_role`
    ADD COLUMN `system_code` varchar(10) NULL COMMENT '系统标识' AFTER `last_modified_date`;


-- ----------------------------
-- Table structure for sys_menu
-- ----------------------------
DROP TABLE IF EXISTS `sys_menu`;
CREATE TABLE `sys_menu`  (
                             `id` varchar(36) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL,
                             `menu_name` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '菜单名称',
                             `status` tinyint(1) NULL DEFAULT 1 COMMENT '1启用 0禁用',
                             `created_by` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL,
                             `created_date` timestamp(6) NULL DEFAULT NULL,
                             `last_modified_by` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL,
                             `last_modified_date` timestamp(6) NULL DEFAULT NULL,
                             PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci ROW_FORMAT = Dynamic;

ALTER TABLE `base_spring`.`sys_menu`
    MODIFY COLUMN `menu_name` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '菜单名称' AFTER `id`,
    ADD COLUMN `deleted` tinyint(1) NOT NULL DEFAULT 0 COMMENT '删除标志位' AFTER `last_modified_date`,
    ADD COLUMN `parent_id` varchar(36) NOT NULL DEFAULT 0 COMMENT '父级id' AFTER `deleted`,
    ADD COLUMN `menu_level` tinyint(1) NOT NULL DEFAULT 1 COMMENT '菜单层级' AFTER `parent_id`,
    ADD COLUMN `menu_type` tinyint(1) NOT NULL COMMENT '菜单类型（1模块2菜单3按钮）' AFTER `menu_level`,
    ADD COLUMN `sort` int NULL COMMENT '排序' AFTER `menu_type`,
    ADD COLUMN `router` varchar(100) NULL COMMENT '路由' AFTER `sort`;

ALTER TABLE `base_spring`.`sys_menu`
    ADD COLUMN `permission` varchar(100) NULL COMMENT '权限值' AFTER `router`;

DROP TABLE IF EXISTS `sys_resource`;
CREATE TABLE `base_spring`.`sys_resource`  (
                                           `id` varchar(36) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL,
                                           `resource_url` varchar(100) NOT NULL COMMENT 'url',
                                           `tag` varchar(50) NULL COMMENT '标签',
                                           `remark` varchar(255) NULL COMMENT '备注',
                                           `resource_method` varchar(10) NOT NULL COMMENT '请求方法',
                                           `app_id` varchar(255) NULL COMMENT '应用id',
                                           `created_by` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL,
                                           `last_modified_by` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL,
                                           `created_date` timestamp(6) NULL DEFAULT NULL,
                                           `last_modified_date` timestamp(6) NULL DEFAULT NULL,
                                           `deleted` tinyint(1) NOT NULL DEFAULT 0,
                                           PRIMARY KEY (`id`) USING BTREE
)ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci ROW_FORMAT = Dynamic;




-- ----------------------------
-- Table structure for sys_account_role
-- ----------------------------
DROP TABLE IF EXISTS `sys_account_role`;
CREATE TABLE `sys_account_role`  (
                                     `id` varchar(36) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL,
                                     `account_id` varchar(36) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL,
                                     `role_id` varchar(36) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL,
                                     `deleted` tinyint(1) NOT NULL DEFAULT 0,
                                     `created_by` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL,
                                     `created_date` timestamp(6) NULL DEFAULT NULL,
                                     `last_modified_by` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL,
                                     `last_modified_date` timestamp(6) NULL DEFAULT NULL,
                                     PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci ROW_FORMAT = Dynamic;

SET FOREIGN_KEY_CHECKS = 1;

DROP TABLE IF EXISTS `sys_log`;
CREATE TABLE `sys_log`  (
                            `id` varchar(36) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL,
                            `ip` varchar(200) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL,
                            `url` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL,
                            `status` tinyint(1) NULL DEFAULT NULL COMMENT '1正常0异常',
                            `oper_time` timestamp NULL DEFAULT NULL COMMENT '操作时间',
                            `cost_time` timestamp(3) NULL DEFAULT NULL COMMENT '运行持续时间',
                            `oper_by` varchar(30) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '操作人',
                            `error` text CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL COMMENT '错误日志',
                            `method` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '请求方式',
                            `deleted` tinyint(1) NOT NULL DEFAULT 0,
                            `created_by` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL,
                            `created_date` timestamp(6) NULL DEFAULT NULL,
                            `last_modified_by` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL,
                            `last_modified_date` timestamp(6) NULL DEFAULT NULL,
                            PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci ROW_FORMAT = Dynamic;

ALTER TABLE `base_spring`.`sys_log`
    MODIFY COLUMN `cost_time` datetime(3) NULL DEFAULT NULL COMMENT '运行持续时间' AFTER `oper_time`;
-- ----------------------------
-- Table structure for sys_menu_resource
-- ----------------------------
DROP TABLE IF EXISTS `sys_menu_resource`;
CREATE TABLE `sys_menu_resource`  (
                                      `id` varchar(36) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL,
                                      `menu_id` varchar(36) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL,
                                      `resource_id` varchar(36) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL,
                                      `deleted` tinyint(1) NOT NULL DEFAULT 0,
                                      `created_by` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL,
                                      `created_date` timestamp(6) NULL DEFAULT NULL,
                                      `last_modified_by` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL,
                                      `last_modified_date` timestamp(6) NULL DEFAULT NULL,
                                      PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for sys_role_menu
-- ----------------------------
DROP TABLE IF EXISTS `sys_role_menu`;
CREATE TABLE `sys_role_menu`  (
                                  `id` varchar(36) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL,
                                  `role_id` varchar(36) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL,
                                  `menu_id` varchar(36) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL,
                                  `deleted` tinyint(1) NOT NULL DEFAULT 0,
                                  `created_by` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL,
                                  `created_date` timestamp(6) NULL DEFAULT NULL,
                                  `last_modified_by` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL,
                                  `last_modified_date` timestamp(6) NULL DEFAULT NULL,
                                  `system_code` varchar(10) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '系统标识',
                                  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci ROW_FORMAT = Dynamic;
