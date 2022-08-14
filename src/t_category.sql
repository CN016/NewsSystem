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

 Date: 14/08/2022 18:27:16
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for t_category
-- ----------------------------
DROP TABLE IF EXISTS `t_category`;
CREATE TABLE `t_category`  (
  `cid` int(11) NULL DEFAULT NULL,
  `cname` varchar(150) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of t_category
-- ----------------------------
INSERT INTO `t_category` VALUES (1, '新时代“课程思政”教学设计创新的理路');
INSERT INTO `t_category` VALUES (2, '新华社评论员：美国粗暴践踏国际法和国际关系基本准则');
INSERT INTO `t_category` VALUES (3, '“美国最危险的女人”，丑闻缠身');
INSERT INTO `t_category` VALUES (4, '国产大豆定价权谁说了算');
INSERT INTO `t_category` VALUES (5, '以青春力量释放创业就业活力');
INSERT INTO `t_category` VALUES (6, '人民财评：为清朗网络空间打牢法治基石');
INSERT INTO `t_category` VALUES (7, '看不见的人，怎么用抖音？');
INSERT INTO `t_category` VALUES (8, '细心贴心！这个地铁站四小时内被乘客点赞三次');
INSERT INTO `t_category` VALUES (9, '向存量要效益！广州以“工改工”打造村镇工业集聚区改造样板');
INSERT INTO `t_category` VALUES (43130474, '元宇宙招聘潮开启：500万高薪不稀奇，技术人才最稀缺');
INSERT INTO `t_category` VALUES (83170391, '文明花开竞芬芳——多地文明城市创建成效观察');
INSERT INTO `t_category` VALUES (67947955, '新华社评论员：祖国完全统一的历史任务一定要实现，也一定能够实现！——写在《台湾问题与新时代中国统一事业》白皮书发表之际');

SET FOREIGN_KEY_CHECKS = 1;
