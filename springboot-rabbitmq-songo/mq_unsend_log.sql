/*
 Navicat Premium Data Transfer

 Source Server         : 192.168.1.105
 Source Server Type    : MySQL
 Source Server Version : 80021
 Source Host           : 192.168.1.105:13306
 Source Schema         : test

 Target Server Type    : MySQL
 Target Server Version : 80021
 File Encoding         : 65001

 Date: 29/11/2020 19:31:29
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for mq_unsend_log
-- ----------------------------
DROP TABLE IF EXISTS `mq_unsend_log`;
CREATE TABLE `mq_unsend_log`  (
  `msg_id` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '消息ID',
  `exchange` varchar(128) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '交换机',
  `routing_key` varchar(128) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '路由键',
  `message` longtext CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '消息体',
  `count` int NOT NULL DEFAULT 0 COMMENT '重发次数',
  `status` int NOT NULL DEFAULT 0 COMMENT '0重发中，1重发成功，2重发失败',
  `create_time` datetime(0) NULL DEFAULT NULL,
  `modify_time` datetime(0) NULL DEFAULT NULL,
  PRIMARY KEY (`msg_id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci COMMENT = '消息重发记录表' ROW_FORMAT = Dynamic;

SET FOREIGN_KEY_CHECKS = 1;
