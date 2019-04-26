/*
Navicat MySQL Data Transfer

Source Server         : localhost
Source Server Version : 50027
Source Host           : localhost:3306
Source Database       : csii_tencent_all

Target Server Type    : MYSQL
Target Server Version : 50027
File Encoding         : 65001

Date: 2018-10-03 20:14:00
*/

SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
-- Table structure for tx_trans_flow
-- ----------------------------
DROP TABLE IF EXISTS `tx_trans_flow`;
CREATE TABLE `tx_trans_flow` (
  `id` varchar(32) NOT NULL COMMENT '腾讯平台流水号',
  `org_id` varchar(32) default NULL COMMENT '机构id',
  `third_seq` varchar(255) default NULL COMMENT '第三方流水',
  `trans_code` varchar(255) default NULL COMMENT '交易编码',
  `trans_state` varchar(255) default NULL COMMENT '交易状态',
  `exception_code` varchar(255) default NULL COMMENT '异常码',
  `exception_message` varchar(1000) default NULL COMMENT '异常信息',
  `remark` varchar(255) default NULL COMMENT '备注',
  `opr_date` datetime default NULL COMMENT '操作时间',
  PRIMARY KEY  (`id`),
  UNIQUE KEY `tx_trans_flow_third_seq` USING BTREE (`third_seq`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 ROW_FORMAT=COMPACT COMMENT='流水表';
