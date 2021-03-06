/*
Navicat MySQL Data Transfer

Source Server         : localhost
Source Server Version : 50027
Source Host           : localhost:3306
Source Database       : test

Target Server Type    : MYSQL
Target Server Version : 50027
File Encoding         : 65001

Date: 2018-06-15 12:29:37
*/

SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
-- Table structure for xq_account
-- ----------------------------
DROP TABLE IF EXISTS `xq_account`;
CREATE TABLE `xq_account` (
  `acct_pkey` varchar(64) NOT NULL COMMENT '存管账户主键',
  `user_id` varchar(64) default NULL COMMENT '用户表主键',
  `account` varchar(35) default NULL COMMENT '存管帐号',
  `serial_no` varchar(64) default NULL COMMENT '流水号',
  `name` varchar(50) default NULL COMMENT '姓名/经办人姓名',
  `enter_name` varchar(100) default NULL COMMENT '企业名称',
  `id_type` char(1) default NULL COMMENT '证件类型 0-身份证,1-护照,2-组织机构代码证,B-港澳通行证,C-入台证',
  `id_no` varchar(30) default NULL COMMENT '证件号码',
  `phone` varchar(20) default NULL COMMENT '手机号码',
  `email` varchar(50) default NULL COMMENT '邮箱',
  `channel` varchar(5) default NULL COMMENT '渠道',
  `create_time` datetime default NULL COMMENT '开户时间',
  `acc_state` char(1) default '0' COMMENT '是否有效 0-有效,1-无效,3-初始状态',
  `acc_type` char(1) default NULL COMMENT '账户类型 0-个人,1-企业',
  `lend_borr` char(1) default NULL COMMENT '出借/借款人 0-出借人，1-借款人，2-出借/借款人',
  `is_trustee` char(1) default NULL COMMENT '受托人 0-是，1-否',
  `error_msg` varchar(200) default NULL COMMENT '错误信息',
  `standby1` varchar(500) default NULL COMMENT '备用字段1',
  `standby2` varchar(500) default NULL COMMENT '备用字段2',
  `standby3` varchar(500) default NULL COMMENT '备用字段3',
  `fadada_customer_id` varchar(50) default NULL COMMENT '法大大客户编号',
  `legalPerson_name` varchar(20) default NULL COMMENT '企业法人姓名',
  `transactors_phone_no` varchar(20) default NULL COMMENT '经办人手机号',
  `transactorId_card_num` varchar(20) default NULL COMMENT '经办人身份证号码',
  `review_status` char(1) default NULL COMMENT '企业审核状态  1 待审核 2 审核通过   3拒绝',
  `comment` blob COMMENT 'blob-test',
  PRIMARY KEY  (`acct_pkey`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='存管账户表';
