/*
Navicat MySQL Data Transfer

Source Server         : localhost
Source Server Version : 50027
Source Host           : localhost:3306
Source Database       : csii_tencent_all

Target Server Type    : MYSQL
Target Server Version : 50027
File Encoding         : 65001

Date: 2018-10-03 20:08:55
*/

SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
-- Table structure for tx_up_log
-- ----------------------------
DROP TABLE IF EXISTS `tx_up_log`;
CREATE TABLE `tx_up_log` (
  `id` varchar(32) NOT NULL COMMENT 'id',
  `seq_no` varchar(32) default NULL COMMENT '流水号',
  `ip` varchar(20) default NULL COMMENT '请求ip',
  `req_params` mediumtext COMMENT '请求报文',
  `resp_params` mediumtext COMMENT '响应报文',
  `remark` varchar(255) default NULL COMMENT '备注',
  `create_date` datetime default NULL COMMENT '创建时间',
  PRIMARY KEY  (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 ROW_FORMAT=COMPACT COMMENT='上游报文表';
