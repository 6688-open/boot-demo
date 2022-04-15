/*
Navicat MySQL Data Transfer

Source Server         : localhost
Source Server Version : 50622
Source Host           : localhost:3306
Source Database       : ds_0002

Target Server Type    : MYSQL
Target Server Version : 50622
File Encoding         : 65001

Date: 2021-07-28 16:10:44
*/

SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
-- Table structure for tab_user_001
-- ----------------------------
DROP TABLE IF EXISTS `tab_user_001`;
CREATE TABLE `tab_user_001` (
  `id` bigint(32) unsigned NOT NULL AUTO_INCREMENT COMMENT '主键',
  `name` varchar(64) DEFAULT NULL COMMENT '姓名',
  `sex` varchar(32) DEFAULT NULL COMMENT '性别',
  `age` int(11) DEFAULT NULL COMMENT '年龄',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `update_time` datetime DEFAULT NULL COMMENT '更新时间',
  `status` tinyint(1) DEFAULT NULL COMMENT '是否删除 1删除 0未删除',
  `wb_no` varchar(64) DEFAULT NULL COMMENT '运单',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=15 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of tab_user_001
-- ----------------------------
INSERT INTO `tab_user_001` VALUES ('6', '奶奶756', '女', '62', '2021-06-09 17:16:21', '2021-06-09 17:16:21', '0', '88027157426713728');
INSERT INTO `tab_user_001` VALUES ('10', '奶奶7910', '女', '62', '2021-06-09 17:16:21', '2021-06-09 17:16:21', '0', '40026317265711187');
INSERT INTO `tab_user_001` VALUES ('14', '奶奶8314', '女', '62', '2021-06-09 17:16:21', '2021-06-09 17:16:21', '0', '80023881262768416');

-- ----------------------------
-- Table structure for tab_user_002
-- ----------------------------
DROP TABLE IF EXISTS `tab_user_002`;
CREATE TABLE `tab_user_002` (
  `id` bigint(32) unsigned NOT NULL AUTO_INCREMENT COMMENT '主键',
  `name` varchar(64) DEFAULT NULL COMMENT '姓名',
  `sex` varchar(32) DEFAULT NULL COMMENT '性别',
  `age` int(11) DEFAULT NULL COMMENT '年龄',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `update_time` datetime DEFAULT NULL COMMENT '更新时间',
  `status` tinyint(1) DEFAULT NULL COMMENT '是否删除 1删除 0未删除',
  `wb_no` varchar(64) DEFAULT NULL COMMENT '运单',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=16 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of tab_user_002
-- ----------------------------
INSERT INTO `tab_user_002` VALUES ('5', '爷爷745', '男', '64', '2021-06-09 17:16:21', '2021-06-09 17:16:21', '0', '72024363864350357');
INSERT INTO `tab_user_002` VALUES ('9', '奶奶789', '女', '62', '2021-06-09 17:16:21', '2021-06-09 17:16:21', '0', '30023638465761285');
INSERT INTO `tab_user_002` VALUES ('11', '奶奶8011', '女', '62', '2021-06-09 17:16:21', '2021-06-09 17:16:21', '0', '50024585604216780');
INSERT INTO `tab_user_002` VALUES ('12', '奶奶8112', '女', '62', '2021-06-09 17:16:21', '2021-06-09 17:16:21', '0', '60025151686185058');
INSERT INTO `tab_user_002` VALUES ('15', '奶奶8415', '女', '62', '2021-06-09 17:16:21', '2021-06-09 17:16:21', '0', '90024405461140687');

-- ----------------------------
-- Table structure for tab_user_003
-- ----------------------------
DROP TABLE IF EXISTS `tab_user_003`;
CREATE TABLE `tab_user_003` (
  `id` bigint(32) unsigned NOT NULL AUTO_INCREMENT COMMENT '主键',
  `name` varchar(64) DEFAULT NULL COMMENT '姓名',
  `sex` varchar(32) DEFAULT NULL COMMENT '性别',
  `age` int(11) DEFAULT NULL COMMENT '年龄',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `update_time` datetime DEFAULT NULL COMMENT '更新时间',
  `status` tinyint(1) DEFAULT NULL COMMENT '是否删除 1删除 0未删除',
  `wb_no` varchar(64) DEFAULT NULL COMMENT '运单',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=14 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of tab_user_003
-- ----------------------------
INSERT INTO `tab_user_003` VALUES ('13', '奶奶8213', '女', '62', '2021-06-09 17:16:21', '2021-06-09 17:16:21', '0', '70027208753227328');

-- ----------------------------
-- Table structure for tab_user_004
-- ----------------------------
DROP TABLE IF EXISTS `tab_user_004`;
CREATE TABLE `tab_user_004` (
  `id` bigint(32) unsigned NOT NULL AUTO_INCREMENT COMMENT '主键',
  `name` varchar(64) DEFAULT NULL COMMENT '姓名',
  `sex` varchar(32) DEFAULT NULL COMMENT '性别',
  `age` int(11) DEFAULT NULL COMMENT '年龄',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `update_time` datetime DEFAULT NULL COMMENT '更新时间',
  `status` tinyint(1) DEFAULT NULL COMMENT '是否删除 1删除 0未删除',
  `wb_no` varchar(64) DEFAULT NULL COMMENT '运单',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=9 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of tab_user_004
-- ----------------------------
INSERT INTO `tab_user_004` VALUES ('7', '奶奶767', '女', '62', '2021-06-09 17:16:21', '2021-06-09 17:16:21', '0', '10023374570183618');
INSERT INTO `tab_user_004` VALUES ('8', '奶奶778', '女', '62', '2021-06-09 17:16:21', '2021-06-09 17:16:21', '0', '20021784772665733');
