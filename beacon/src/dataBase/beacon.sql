/*
MySQL Data Transfer
Source Host: localhost
Source Database: beacon
Target Host: localhost
Target Database: beacon
Date: 2014/8/16 8:49:09
*/

SET FOREIGN_KEY_CHECKS=0;
-- ----------------------------
-- Table structure for t_sys_file
-- ----------------------------
DROP TABLE IF EXISTS `t_sys_file`;
CREATE TABLE `t_sys_file` (
  `ID` varchar(32) NOT NULL,
  `FILENAME` varchar(300) DEFAULT NULL,
  `LOGICFILENAME` varchar(300) DEFAULT NULL,
  `BUSIID` varchar(32) DEFAULT NULL,
  `BUSITYPE` varchar(80) DEFAULT NULL,
  `LOCKED` varchar(2) DEFAULT NULL,
  `OPERATETIME` timestamp NULL DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP,
  `OPERATETYPE` varchar(80) DEFAULT NULL,
  `OPERATORCODE` varchar(32) DEFAULT NULL,
  `OPERATORNAME` varchar(80) DEFAULT NULL,
  `remark` varchar(512) DEFAULT NULL,
  PRIMARY KEY (`ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Table structure for t_sys_user
-- ----------------------------
DROP TABLE IF EXISTS `t_sys_user`;
CREATE TABLE `t_sys_user` (
  `ID` varchar(32) NOT NULL DEFAULT '',
  `LOGINID` varchar(32) DEFAULT NULL,
  `SEX` varchar(80) DEFAULT NULL,
  `USERNAME` varchar(120) DEFAULT NULL,
  `STATUS` varchar(80) DEFAULT NULL,
  `OPERATETIME` timestamp NULL DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP,
  `OPERATERCODE` varchar(32) DEFAULT NULL,
  `OPERATERNAME` varchar(120) DEFAULT NULL,
  `DELFLAG` varchar(1) DEFAULT NULL,
  `PASSWD` varchar(80) DEFAULT NULL,
  PRIMARY KEY (`ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records 
-- ----------------------------
INSERT INTO `t_sys_file` VALUES ('1', '申龙军', '申龙军', '1', '1', '1', '2014-08-10 20:43:46', 'A', 'shenlj', '申龙军', '测试工程');
INSERT INTO `t_sys_user` VALUES ('1', 'shenlj', '1', '申龙军', '1', '2014-08-11 18:16:44', 'shenlj', 'shenlj', 'A', '0df51c4410405aa865fe5b4355a3f4e8');
INSERT INTO `t_sys_user` VALUES ('2', 'shenhr', '2', '申浩冉', '1', '2014-08-12 13:38:14', 'shenlj', 'shenlj', 'A', '0df51c4410405aa865fe5b4355a3f4e8');
