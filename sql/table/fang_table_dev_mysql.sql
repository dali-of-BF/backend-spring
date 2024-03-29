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
                             PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci ROW_FORMAT = Dynamic;

SET FOREIGN_KEY_CHECKS = 1;


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

SET FOREIGN_KEY_CHECKS = 1;

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

