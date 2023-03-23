/*
 Navicat Premium Data Transfer

 Source Server         : 47.107.47.184_5432
 Source Server Type    : PostgreSQL
 Source Server Version : 140001 (140001)
 Source Host           : 47.107.47.184:5432
 Source Catalog        : base_spring
 Source Schema         : public

 Target Server Type    : PostgreSQL
 Target Server Version : 140001 (140001)
 File Encoding         : 65001

 Date: 28/10/2022 14:29:04
*/


-- ----------------------------
-- Table structure for sys_account
-- ----------------------------
DROP TABLE IF EXISTS "public"."sys_account";
CREATE TABLE "public"."sys_account" (
                                        "id" varchar(36) COLLATE "pg_catalog"."default" NOT NULL,
                                        "username" varchar(50) COLLATE "pg_catalog"."default",
                                        "phone" varchar(11) COLLATE "pg_catalog"."default" NOT NULL,
                                        "password" varchar(100) COLLATE "pg_catalog"."default" NOT NULL,
                                        "deleted" bool NOT NULL DEFAULT false,
                                        "created_by" varchar(50) COLLATE "pg_catalog"."default",
                                        "created_date" timestamp(6),
                                        "last_modified_by" varchar(50) COLLATE "pg_catalog"."default",
                                        "last_modified_date" timestamp(6),
                                        "gender" varchar(2) COLLATE "pg_catalog"."default",
                                        "status" bool NOT NULL DEFAULT true,
                                        "id_card" varchar(18) COLLATE "pg_catalog"."default"
)
;
COMMENT ON COLUMN "public"."sys_account"."id" IS 'id';
COMMENT ON COLUMN "public"."sys_account"."username" IS '用户名';
COMMENT ON COLUMN "public"."sys_account"."phone" IS '手机号';
COMMENT ON COLUMN "public"."sys_account"."password" IS '密码';
COMMENT ON COLUMN "public"."sys_account"."deleted" IS '删除位';
COMMENT ON COLUMN "public"."sys_account"."created_by" IS '创建人';
COMMENT ON COLUMN "public"."sys_account"."created_date" IS '创建时间';
COMMENT ON COLUMN "public"."sys_account"."last_modified_by" IS '最后修改人';
COMMENT ON COLUMN "public"."sys_account"."last_modified_date" IS '最后修改时间';
COMMENT ON COLUMN "public"."sys_account"."gender" IS '0:男 1：女';
COMMENT ON COLUMN "public"."sys_account"."status" IS '账号状态：true 启用  false 停用';
COMMENT ON COLUMN "public"."sys_account"."id_card" IS '身份证';
COMMENT ON TABLE "public"."sys_account" IS '账号信息';

-- ----------------------------
-- Primary Key structure for table sys_account
-- ----------------------------
ALTER TABLE "public"."sys_account" ADD CONSTRAINT "sys_account_copy1_pkey" PRIMARY KEY ("id");
