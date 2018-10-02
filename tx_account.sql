/*
Navicat MySQL Data Transfer

Source Server         : localhost
Source Server Version : 50027
Source Host           : localhost:3306
Source Database       : csii_tencent_all

Target Server Type    : MYSQL
Target Server Version : 50027
File Encoding         : 65001

Date: 2018-10-02 21:14:28
*/

SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
-- Table structure for tx_account
-- ----------------------------
DROP TABLE IF EXISTS `tx_account`;
CREATE TABLE `tx_account` (
  `ac_no` varchar(255) NOT NULL COMMENT '银行账号',
  `order_id` varchar(255) default NULL COMMENT '订单号',
  `org_id` varchar(32) default NULL COMMENT '机构id',
  `cust_id` varchar(32) default NULL COMMENT '客户id',
  `bank_id` varchar(255) default NULL COMMENT '银行编码',
  `bank_name` varchar(255) default NULL COMMENT '银行名称',
  `account_type` varchar(255) default NULL COMMENT '银行账号类型',
  `bind_phone` varchar(11) default NULL COMMENT '预留手机号',
  `pass_word` varchar(255) default NULL COMMENT '支付密码',
  `serial_no` varchar(255) default NULL COMMENT '开户流水号',
  `create_date` datetime default NULL COMMENT '记录时间',
  `update_date` datetime default NULL COMMENT '更新时间',
  PRIMARY KEY  (`ac_no`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 ROW_FORMAT=COMPACT COMMENT='账户表';
