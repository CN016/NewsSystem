/*
 Navicat Premium Data Transfer

 Source Server         : 016
 Source Server Type    : MySQL
 Source Server Version : 50724
 Source Host           : localhost:3306
 Source Schema         : news_demo

 Target Server Type    : MySQL
 Target Server Version : 50724
 File Encoding         : 65001

 Date: 14/08/2022 18:26:32
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for t_user
-- ----------------------------
DROP TABLE IF EXISTS `t_user`;
CREATE TABLE `t_user`  (
  `uid` int(11) NOT NULL AUTO_INCREMENT,
  `uname` varchar(20) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `password` varchar(200) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `telephone` varchar(15) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `email` varchar(20) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `sex` varchar(5) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `lid` int(11) NULL DEFAULT NULL,
  `active` int(11) NULL DEFAULT NULL,
  `code` varchar(30) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `news_name` varchar(10) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `head_image` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `ad_role` int(11) NULL DEFAULT NULL,
  `create_time` datetime NULL DEFAULT NULL,
  PRIMARY KEY (`uid`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 24 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of t_user
-- ----------------------------
INSERT INTO `t_user` VALUES (1, '114514', 'C4D038B4BED09FDB1471EF51EC3A32CD', '32wqq34', '32wqq34', '1', 2, 1, '123', '324', '1231', 1241, '2022-08-16 18:13:22');
INSERT INTO `t_user` VALUES (2, '4324', '4324', '324234', '324234', '1', 1, 1, '213', '324', '2354', 32456, '2022-08-18 18:17:24');
INSERT INTO `t_user` VALUES (3, '2', '2', 'asd', 'asd', '1', 1, 1, '32', '546', 'dsf', 342, '2022-08-03 18:21:00');
INSERT INTO `t_user` VALUES (4, '323', '323', 'vdsgfhyuj', 'vdsgfhyuj', '1', 1, 1, '4564', '6565', 'dsfdsf', 234, '2022-08-10 18:21:05');
INSERT INTO `t_user` VALUES (5, '455464', '455464', '4357hgf', '4357hgf', '1', 1, 1, '765', '357867', 'afs', 654, '2022-07-28 18:21:02');
INSERT INTO `t_user` VALUES (6, '24378', '24378', '5467htrg', '5467htrg', '1', 1, 1, '5', '54675', 'fsd', 876, '2022-08-10 18:21:08');
INSERT INTO `t_user` VALUES (7, '45678', '45678', 'vb vgfhs', 'vb vgfhs', '1', 1, 1, '58', '354665', 'sad', 132, '2022-08-19 18:21:11');
INSERT INTO `t_user` VALUES (8, '546', '546', 'sdfdb', 'sdfdb', '1', 1, 1, '234', '54665', 'fad', 786, '2022-08-10 18:21:14');
INSERT INTO `t_user` VALUES (9, '43654', '43654', 'scdscvf', 'scdscvf', '11', 1, 1, '244', '23435', 'saf', 634, '2022-08-08 18:21:18');
INSERT INTO `t_user` VALUES (21, '016016', '1D448E7A721A34814F95FF742C3E58C4', '13811012138', 'sdwadw@csa.com', '男', 1, 1, '0448484675', '34头发上的', '/images/none.jpg', NULL, '2022-08-13 10:12:38');
INSERT INTO `t_user` VALUES (22, '123456', '1D448E7A721A34814F95FF742C3E58C4', '15677323590', 'dsahi@qq.com', '女', 1, 1, '5244512725', '1234512', '/images/none.jpg', NULL, '2022-08-13 10:15:26');
INSERT INTO `t_user` VALUES (23, '123456666', '1D448E7A721A34814F95FF742C3E58C4', '13811012138', 'dsandj@ci.com', '男', 1, 1, '7552777171', '214345546', '/images/none.jpg', NULL, '2022-08-14 09:14:38');

SET FOREIGN_KEY_CHECKS = 1;
