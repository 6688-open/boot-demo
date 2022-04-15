/*
Navicat MySQL Data Transfer

Source Server         : localhost
Source Server Version : 50622
Source Host           : localhost:3306
Source Database       : ds_0001

Target Server Type    : MYSQL
Target Server Version : 50622
File Encoding         : 65001

Date: 2021-07-28 16:10:33
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
) ENGINE=InnoDB AUTO_INCREMENT=22 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of tab_user_001
-- ----------------------------
INSERT INTO `tab_user_001` VALUES ('3', '爸爸723', '男', '30', '2021-06-09 17:16:21', '2021-06-09 17:16:21', '0', '23015267584342255');
INSERT INTO `tab_user_001` VALUES ('19', '奶奶8819', '女', '62', '2021-06-09 17:16:21', '2021-06-09 17:16:21', '0', '40018684300110566');
INSERT INTO `tab_user_001` VALUES ('21', '奶奶9021', '女', '62', '2021-06-09 17:16:21', '2021-06-09 17:16:21', '0', '60011762635558537');

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
) ENGINE=InnoDB AUTO_INCREMENT=24 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of tab_user_002
-- ----------------------------
INSERT INTO `tab_user_002` VALUES ('16', '奶奶8516', '女', '62', '2021-06-09 17:16:21', '2021-06-09 17:16:21', '0', '10010118445330376');
INSERT INTO `tab_user_002` VALUES ('22', '奶奶9122', '女', '62', '2021-06-09 17:16:21', '2021-06-09 17:16:21', '0', '70014185668778150');
INSERT INTO `tab_user_002` VALUES ('23', '奶奶9223', '女', '62', '2021-06-09 17:16:21', '2021-06-09 17:16:21', '0', '80012884365218827');

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
) ENGINE=InnoDB AUTO_INCREMENT=19 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of tab_user_003
-- ----------------------------
INSERT INTO `tab_user_003` VALUES ('2', '小小712', '女', '2', '2021-06-09 17:16:21', '2021-06-09 17:16:21', '0', '62010205611103320');
INSERT INTO `tab_user_003` VALUES ('4', '妈妈734', '女', '28', '2021-06-09 17:16:21', '2021-06-09 17:16:21', '0', '21013725320547342');
INSERT INTO `tab_user_003` VALUES ('18', '奶奶8718', '女', '62', '2021-06-09 17:16:21', '2021-06-09 17:16:21', '0', '30016202061162745');

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
) ENGINE=InnoDB AUTO_INCREMENT=25 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of tab_user_004
-- ----------------------------
INSERT INTO `tab_user_004` VALUES ('17', '奶奶8617', '女', '62', '2021-06-09 17:16:21', '2021-06-09 17:16:21', '0', '20011567382121068');
INSERT INTO `tab_user_004` VALUES ('20', '奶奶8920', '女', '62', '2021-06-09 17:16:21', '2021-06-09 17:16:21', '0', '50015045343788162');
INSERT INTO `tab_user_004` VALUES ('24', '奶奶9324', '女', '62', '2021-06-09 17:16:21', '2021-06-09 17:16:21', '0', '90018711274020745');
