/*
 Navicat Premium Data Transfer

 Source Server         : localhost
 Source Server Type    : MySQL
 Source Server Version : 80022
 Source Host           : localhost:3306
 Source Schema         : multi-datasource-trx1

 Target Server Type    : MySQL
 Target Server Version : 80022
 File Encoding         : 65001

 Date: 15/06/2024 11:52:42
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for t_user
-- ----------------------------
DROP TABLE IF EXISTS `t_user`;
CREATE TABLE `t_user`  (
  `tb_user_id` int NOT NULL AUTO_INCREMENT,
  `tb_user_name` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL,
  `tb_password` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL,
  `tb_phone` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL,
  PRIMARY KEY (`tb_user_id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of t_user
-- ----------------------------
INSERT INTO `t_user` VALUES (1, '张三', '123', '123919239');
INSERT INTO `t_user` VALUES (2, '张三', '123', '123919239');
INSERT INTO `t_user` VALUES (3, '张三', '123', '123919239');
INSERT INTO `t_user` VALUES (4, '张三', '123', '123919239');
INSERT INTO `t_user` VALUES (5, '张三', '123', '123919239');
INSERT INTO `t_user` VALUES (6, '张三', '123', '123919239');
INSERT INTO `t_user` VALUES (7, '张三', '123', '123919239');

SET FOREIGN_KEY_CHECKS = 1;
