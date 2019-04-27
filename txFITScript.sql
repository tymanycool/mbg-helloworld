/*==============================================================*/
/* DBMS name:      MySQL 5.0                                    */
/* Created on:     2019/4/25 15:48:00                           */
/*==============================================================*/


drop table if exists SYS_DICT;

drop table if exists SYS_SEQUENCE;

drop table if exists bt_repay_deduct_file;

drop table if exists ft_repay_dept_rlt;

drop table if exists ot_repay_deduct_confirm;

drop table if exists ot_repay_deduct_trans;

drop index Index_1 on sys_dept;

drop table if exists sys_dept;

drop table if exists sys_dept_resource;

drop table if exists tx_attach_jnl;

drop table if exists tx_authentication_info;

drop table if exists tx_bank_card_auth;

drop table if exists tx_batch;

drop table if exists tx_callback_info;

drop table if exists tx_cust_info;

drop table if exists tx_file_record;

drop table if exists tx_loan_account;

drop table if exists tx_loan_apply;

drop table if exists tx_loan_credit;

drop table if exists tx_loan_payment;

drop table if exists tx_loan_product;

drop table if exists tx_loan_repay_info;

drop table if exists tx_loan_repayment;

drop table if exists tx_loan_withdraw;

drop table if exists tx_trans_jnl;

/*==============================================================*/
/* Table: SYS_DICT                                              */
/*==============================================================*/
create temporary table SYS_DICT
(
   DICT_TYPE            varchar(32) not null comment 'deptType：机构类型
            resourceType：资源类型
            loanType：贷款类型
            idcardType：证件类型
            deptIdentityType：机构标识',
   DICT_VALUE           varchar(32) not null comment '数值',
   DICT_NAME            varchar(32) comment 'Name',
   DICT_DESCRIPTION     varchar(32) comment '描述',
   primary key (DICT_TYPE, DICT_VALUE)
);

alter table SYS_DICT comment '数据字典表';

/*==============================================================*/
/* Table: SYS_SEQUENCE                                          */
/*==============================================================*/
create table SYS_SEQUENCE
(
   SEQ_NAME             varchar(50) not null comment '自增序列名',
   MINVAL               char(11) not null comment '最小值',
   MAXVAL               char(11) not null comment '最大值',
   CURRENT_VAL          char(11) not null comment '当前值',
   INCREMENT_VAL        char(11) not null comment '自增步长'
);

alter table SYS_SEQUENCE comment '自增序列表2';

/*==============================================================*/
/* Table: bt_repay_deduct_file                                  */
/*==============================================================*/
create table bt_repay_deduct_file
(
   ID                   varchar(32) not null comment '序号',
   ASSET_DEPT_ID        varchar(8) comment '资产方机构标识',
   CAPITAL_DEPT_ID      varchar(8) comment '资金方机构标识',
   REPAY_DATE           date default NULL comment '还款日期',
   FILE_PATH            varchar(255) comment '文件路径',
   FILE_NAME            varchar(255) comment '文件名称',
   FILE_TYPE            varchar(2) comment '文件类型
             00    批扣合并
             02    批扣资金 
             03    批扣结果',
   FILE_STATUS          varchar(2) comment '文件状态 
            00：创建成功 
            01：上传成功 
            02：上传失败 
            03：下载成功 
            04：处理成功 
            05：处理失败',
   GMT_CREATE           datetime default NULL comment '创建时间',
   GMT_MODIFY           datetime default NULL comment '修改时间',
   primary key (ID)
)
ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin ROW_FORMAT=DYNAMIC COMMENT='批量扣款文件记录表';

alter table bt_repay_deduct_file comment '批量扣款文件记录表';

/*==============================================================*/
/* Table: ft_repay_dept_rlt                                     */
/*==============================================================*/
create table ft_repay_dept_rlt
(
   ID                   varchar(32) not null comment '还款设置id',
   DEPT_ID              varchar(8) comment '机构标识',
   DEPT_TP              char(1) comment '机构类型
            0：资产方 
            1：资金方',
   RLT_DEPT_ID          varchar(20) comment '关联机构标识',
   RLT_DEPT_TP          char(1) comment '关联机构类型 
            0：资产方 
            1：资金方',
   REPAY_TP             char(1) comment '还款类型：
            0：手动 
            1：自动',
   GMT_CREATE           datetime default NULL comment '创建时间',
   GMT_MODIFY           datetime default NULL comment '修改时间',
   primary key (ID)
)
ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin ROW_FORMAT=DYNAMIC COMMENT='还款机构关联设置表';

alter table ft_repay_dept_rlt comment '还款机构关联设置表';

/*==============================================================*/
/* Table: ot_repay_deduct_confirm                               */
/*==============================================================*/
create table ot_repay_deduct_confirm
(
   ID                   varchar(32) not null comment '序号',
   REPAY_DATE           date default NULL comment '还款日期',
   ASSET_DEPT_ID        varchar(20) comment '资产方机构标识',
   IS_ASSET_CONFIRM     char(1) comment '资产方是否确认 
            0：否 
            1：是',
   CAPITAL_DEPT_ID      varchar(20) comment '资金方机构标识',
   IS_CAPITAL_CONFIRM   char(1) comment '资金方是否确认 
            0：否 
            1：是',
   GMT_CREATE           datetime default NULL comment '创建时间',
   GMT_MODIFY           datetime default NULL comment '修改时间',
   primary key (ID)
)
ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin ROW_FORMAT=DYNAMIC COMMENT='还款批量扣款确认表';

alter table ot_repay_deduct_confirm comment '还款批量扣款确认表';

/*==============================================================*/
/* Table: ot_repay_deduct_trans                                 */
/*==============================================================*/
create table ot_repay_deduct_trans
(
   SEQ_NO               char(32) character set utf8 not null comment '平台流水(订单号)',
   ASSET_DEPT_ID        char(8) character set utf8 comment '资产方机构标识',
   CAPITAL_DEPT_ID      char(8) character set utf8 comment '资金方机构标识',
   REPAY_DATE           date default NULL comment '扣款日期',
   ASSET_SEQ_NO         varchar(50) character set utf8 comment '资产方订单号',
   CURRENT_REPAY_PERIOD varchar(2) character set utf8 comment '本期期数',
   USER_ID              varchar(32) character set utf8 comment '用户唯一编号',
   NAME                 varchar(50) character set utf8 comment '姓名',
   ID_NO                varchar(50) character set utf8 comment '身份证号',
   SIGN_NO              varchar(50) character set utf8 comment '协议号',
   REPAY_AMT            decimal(21, 2) default NULL comment '还款金额',
   SVC_REPAY_AMT        decimal(21, 2) default NULL comment '服务费金额',
   PRI_INT_REPAY_AMT    decimal(21, 2) default NULL comment '本息金额',
   GOODS_NAME           varchar(255) character set utf8 comment '商品名称',
   ORDER_INFO           varchar(255) character set utf8 comment '订单详情',
   BATCH_NO             varchar(32) character set utf8 comment '批次号',
   REPAY_STATUS         char(2) character set utf8 comment '还款状态：00：成功 01：失败 02：资金方还款成功 03：已受理 04：未处理',
   REPAY_DESC           varchar(255) character set utf8 comment '还款结果描述',
   GMT_CREATE           datetime comment '创建时间',
   GMT_MODIFY           datetime default NULL comment '修改时间',
   primary key (SEQ_NO)
)
ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_bin COMMENT = '还款批量扣款明细表' ROW_FORMAT = Dynamic;

alter table ot_repay_deduct_trans comment '还款批量扣款明细表';

/*==============================================================*/
/* Table: sys_dept                                              */
/*==============================================================*/
create table sys_dept
(
   dept_id              char(20) not null comment '机构id',
   dept_identity        char(8) not null comment '机构标识
            场景方：CJ
            银行：YH
            保险：BX
            腾讯：TX',
   parent_dept_identity char(8) comment '父级机构标识',
   dept_name            varchar(64) comment '机构名称',
   dept_type            char(2) not null comment '机构类型(deptType)
            01：腾讯
            02：科蓝
            03：银行
            04：保险方
            05：场景方',
   dept_level           char(2) not null comment '机构级别
            01：一级商户
            02：二级商户',
   reg_amount           varchar(25) comment '注册资金',
   reg_date             timestamp comment '注册时间',
   email                varchar(64) comment '邮箱',
   state                char(2) not null comment '机构状态
            01-有效
            02-无效',
   address              varchar(255) comment '机构地址',
   weburl               varchar(255) comment '公司网站地址',
   legal_represent_name varchar(255) comment '法人代表姓名',
   legal_represent_card varchar(255) comment '法人代表身份证',
   legal_represent_phone varchar(255) comment '法人代表手机号',
   parent_deptid        varchar(20) comment '上级机构',
   instancy_person      varchar(50) comment '联系人姓名',
   instancy_phone       varchar(11) comment '紧急联系电话',
   create_time          timestamp comment '创建时间',
   update_time          timestamp comment '更新时间',
   operator             char(20) comment '操作人',
   del_flag             char(2) comment '删除标志
            01-有效
            02-无效',
   primary key (dept_id),
   key AK_sys_dept_identify (dept_identity)
);

alter table sys_dept comment '机构表';

/*==============================================================*/
/* Index: Index_1                                               */
/*==============================================================*/
create unique index Index_1 on sys_dept
(
   dept_identity
);

/*==============================================================*/
/* Table: sys_dept_resource                                     */
/*==============================================================*/
create table sys_dept_resource
(
   dept_resource_id     char(20) not null comment '机构资源ID',
   dept_identity        char(8) comment '机构标识
            场景方：CJ
            银行：YH
            保险：BX
            腾讯：TX',
   comm_dept_identity   char(8) comment '通信方机构标识
            1.若通信方为本系统(资金服务平台)，则为TX000014
            2.若通信方为连连支付，则为ZF000006',
   resource_type        char(2) comment '资源类型（resouceType）
            01：授权书 
            02：证书 
            03：公钥
            04：私钥',
   resource_name        varchar(50) comment '资源名称',
   resource_url         varchar(256) comment '资源存储路径，机构标识为路径',
   create_time          timestamp comment '创建时间',
   update_time          timestamp comment '更新时间',
   operator             char(20) comment '操作人',
   primary key (dept_resource_id),
   key AK_Key_1 (dept_identity)
);

alter table sys_dept_resource comment '机构的资源存放表';

/*==============================================================*/
/* Table: tx_attach_jnl                                         */
/*==============================================================*/
create table tx_attach_jnl
(
   attach_jnl_id        char(20) not null comment '流水附表ID',
   jnl_id               char(20) not null comment '流水主表ID',
   dept_identity        char(8) comment '机构标识',
   req_direction        char(2) comment '请求方向
            01 接收
            10 发送',
   req_ip               varchar(64) comment '上游ip',
   req_url              varchar(255) comment '下游请求url',
   req_params           mediumtext comment '请求报文',
   resp_params          mediumtext comment '响应报文',
   return_code          varchar(32) comment '返回码',
   return_msg           varchar(256) comment '返回信息',
   status               char(2) comment '交易状态
            01成功
            02失败',
   remark               varchar(256) comment '备注',
   create_date          timestamp comment '创建时间',
   primary key (attach_jnl_id),
   key AK_Key_2 (jnl_id)
);

alter table tx_attach_jnl comment '流水附表';

/*==============================================================*/
/* Table: tx_authentication_info                                */
/*==============================================================*/
create table tx_authentication_info
(
   auth_jnlno           char(20) not null comment '信息认证ID',
   uuid                 char(64) not null comment '全局标识uuid',
   apply_dept_ident     char(8) comment '申请机构标识',
   ref_dept_ident       char(8) comment '和该次授权关联的机构标识',
   loan_type            char(2) comment '贷款类型(loanType)
            00现金贷
            01车贷
            02消费贷',
   name                 varchar(32) comment '姓名',
   phone                varchar(32) comment '手机号',
   card_type            char(2) comment '证件类型(idcardType)
            00-身份证',
   idcard_no            varchar(64) comment '证件号',
   idcard_frimg_url     varchar(256) comment '证件正面存储路径',
   idcard_bkimg_url     varchar(256) comment '证件反面存储路径',
   live_img_url         varchar(256) comment '人脸照片存储路径',
   live_video_url       varchar(256) comment '活体视频存储路径',
   auth_sign_url        varchar(256) comment '手签授权书存储路径',
   upload_finish        char(2) comment '所有文件是否完成
            01：完成
            02：未完成',
   auth_id              varchar(64) comment 'E证通授权id',
   auth_result          varchar(64) comment 'E证通身份认证结果',
   insurer_auth_id      varchar(64) comment '保险公司授信ID',
   insurer_auth_result  varchar(64) comment '保险公司授信结果',
   risk_state           char(2) comment '风控评估状态',
   risk_score           varchar(20) comment '风控分',
   anti_fraud_score1    varchar(20) comment '反欺诈分1',
   anti_fraud_score2    varchar(20) comment '反欺诈分2',
   anti_fraud_score3    varchar(20) comment '反欺诈分3',
   anti_fraud_score4    varchar(20) comment '反欺诈分4',
   create_time          timestamp comment '创建时间',
   update_time          timestamp comment '更新时间',
   primary key (auth_jnlno),
   key AK_Key_2 (uuid),
   key AK_Key_3 (apply_dept_ident),
   key AK_Key_4 (idcard_no)
);

alter table tx_authentication_info comment '保存认证数据(E证通)';

/*==============================================================*/
/* Table: tx_bank_card_auth                                     */
/*==============================================================*/
create table tx_bank_card_auth
(
   id                   char(20) not null comment '鉴权ID',
   seq_no               char(20) not null comment '腾讯平台流水号',
   user_id              varchar(32) comment '用户鉴权唯一编号',
   cust_name            char(30) not null comment '客户姓名',
   id_type              varchar(2) not null comment '证件类型
            01身份证',
   id_no                varchar(30) not null comment '证件号码',
   bank_name            varchar(40) not null comment '银行名称',
   bank_card_no         varchar(19) not null comment '银行账号',
   reserve_phone        varchar(20) not null comment '银行预留手机号',
   agree_no             varchar(64) not null comment '签约协议号',
   auth_status          char(2) not null comment '鉴权状态
            01:签约申请
            02:签约验证
            03:签约成功
            04:签约失败',
   create_time          datetime not null comment '记录时间',
   update_time          datetime comment '更新时间',
   primary key (id)
)
ENGINE=InnoDB DEFAULT CHARSET=utf8 ROW_FORMAT=COMPACT COMMENT='账户表';

alter table tx_bank_card_auth comment '银行卡鉴权表';

/*==============================================================*/
/* Table: tx_batch                                              */
/*==============================================================*/
create table tx_batch
(
   batch_id             char(20) not null comment '主键ID',
   jnl_id               char(20) default NULL comment '腾讯平台流水号',
   batch_name           varchar(256) default NULL comment '批次名称',
   trans_code           varchar(64) default NULL comment '交易码',
   batch_status         char(2) default NULL comment '01-执行中，02-执行成功，03-执行失败',
   batch_msg            varchar(2048) default NULL comment '返回消息(正常返回,异常信息)',
   opr_time             datetime default NULL comment '操作时间',
   primary key (batch_id),
   key jnl_id (jnl_id)
)
ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='批次表';

alter table tx_batch comment '批次表';

/*==============================================================*/
/* Table: tx_callback_info                                      */
/*==============================================================*/
create table tx_callback_info
(
   callback_id          char(20) not null comment '回调ID',
   dept_identity        char(8) comment '回调接收方机构标识',
   order_id             char(32) comment '贷款订单号',
   callback_seq         char(20) not null comment '回调流水(腾讯内部)',
   callback_type        char(6) comment '回调类型
            TX0001：贷款申请结果通知
            TX0002：放款申请结果通知',
   url                  varchar(255) not null comment '异步通知地址',
   content              text comment '回调内容',
   trans_code           varchar(64) comment '交易码',
   callback_times       varchar(6) not null comment '回调次数',
   status               char(2) comment '回调状态
            01待回调
            02回调成功
            03回调中
            04回调失败',
   create_time          datetime not null comment '创建时间',
   update_time          datetime comment '修改时间',
   primary key (callback_id)
)
ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='通知表';

alter table tx_callback_info comment '回调信息表';

/*==============================================================*/
/* Table: tx_cust_info                                          */
/*==============================================================*/
create table tx_cust_info
(
   id                   char(20) not null comment '客户ID',
   order_id             char(32) not null comment '腾讯平台订单号',
   cust_name            varchar(30) not null comment '客户姓名',
   cust_type            char(1) not null default '0' comment '客户类型
            个人：0
            企业：1
            默认个人',
   sex                  enum('M','F') not null comment '性别:
            男：M
            女：F',
   reg_mobile           varchar(20) not null comment '手机号',
   card_type            char(2) not null comment '证件类型(idcardType)
            身份证：01',
   idcard_no            varchar(30) not null comment '证件号',
   issue_authority      varchar(255) comment '发证机关',
   iss_date             datetime comment '证件签发时间',
   expiry_date          datetime comment '证件到期时间',
   nationality          char(30) comment '民族',
   birth                datetime comment '出生日期',
   qualification        char(2) comment '学历
            01-小学及以下 02-初中  03-高中 04-中专  05-高职 06-专科 07-本科 08-硕士研究生 09-博士研究生及以上',
   marry_stauts         char(2) comment '婚姻状况
            01-已婚
            02-未婚
            03-离异',
   company_name         varchar(255) comment '单位名称',
   company_phone        varchar(20) comment '单位电话',
   company_fix          varchar(20) comment '单位传真',
   office_address       varchar(255) comment '办公地址',
   resident_city        varchar(50) comment '居住城市',
   resident_addr        varchar(80) comment '居住地址',
   zip_code             varchar(20) comment '邮编',
   occupation           char(1) comment '本人职业
            A-律师、会计师、顾问或中介 B-国家企事业单位 C-贸易公司、离岸公司 D-工商服务业 E-金融业 F-农林牧渔 G-医疗、教育、非盈利事业 H-学生 I-珠宝、艺术品、稀有矿石 K-彩票、娱乐服务 L-无业或其他 M-军人 N-自由业主 O-采掘业、制造业、科技产业 P-行政、安全保卫人员',
   title                varchar(20) comment '职务',
   month_incom          varchar(15) comment '月薪',
   year_income          varchar(15) comment '个人年收入',
   email                varchar(30) comment '电子邮箱',
   domicile_province    varchar(50) comment '户籍所在省',
   domicile_city        varchar(50) comment '户籍所在城市',
   domicile_county      varchar(50) comment '户籍所在区县',
   domicile_addr        varchar(255) comment '户籍详细地址',
   degree               char(1) comment '最高学位
            0-其他
            1-名誉博士
            2-博士
            3-硕士
            4-学士
            9-未知',
   children             char(1) comment '是否有子女
            0-否
            1-是
            2-未知',
   home_status          char(1) comment '居住状况
            1-自置
            2-按揭
            3-亲属楼宇
            4-集体宿舍
            5-租房
            6-共有住宅
            7-其他
            9-未知',
   work_way             char(1) comment '工作单位所属行业
            A-农、林、牧、渔业
            B-采掘业
            C-制造业
            D-电力、燃气及水的生产和供应业
            E-建筑业
            F-交通运输、仓储和邮政业
            G-信息传输、计算机服务和软件业
            H-批发和零售业
            I-住宿和餐饮业
            J-金融业
            K-房地产业
            L-租赁和商务服务业
            M-科学研究、技术服务业和地质勘察业N-水利、环境和公共设施管理业
            O-居民服务和其他服务业
            P-教育
            Q-卫生、社会保障和社会福利业
            R-文化、体育和娱乐业
            S-公共管理和社会组织
            T-国际组织
            Z-未知。',
   create_time          datetime not null comment '创建时间',
   update_time          datetime comment '修改时间',
   primary key (id)
)
ENGINE=InnoDB DEFAULT CHARSET=utf8 ROW_FORMAT=COMPACT COMMENT='客户表';

alter table tx_cust_info comment '客户信息表';

/*==============================================================*/
/* Table: tx_file_record                                        */
/*==============================================================*/
create table tx_file_record
(
   file_id              char(20) not null comment '文件ID',
   order_id             varchar(64) comment '贷款订单号',
   dept_identity        char(8) not null comment '机构标识',
   file_direction       char(2) comment '文件上传方向
            01接收
            10发送',
   file_type            char(2) not null comment '文件类型
            01：信审影像文件
            02：贷后影像文件
            03：每日放款明细文件
            04：每日还款明细文件
            05：还款计划文件
            06：保单明细文件',
   file_path            varchar(256) comment '文件地址',
   file_name            varchar(100) comment '文件名称',
   file_date            timestamp comment '文件上传时间',
   file_encrypt         varchar(200) comment '解压zip文件密码
            通知类型为01时不能为空',
   file_md5             varchar(100) comment 'MD5',
   status               char(2) comment '状态
            01传输中
            02传输成功
            03传输失败',
   create_time          timestamp comment '创建时间',
   update_time          timestamp comment '更新时间',
   primary key (file_id)
);

alter table tx_file_record comment '文件记录表';

/*==============================================================*/
/* Table: tx_loan_account                                       */
/*==============================================================*/
create table tx_loan_account
(
   id                   char(20) not null comment '账户ID',
   cust_id              char(20) not null comment '客户ID',
   account_type         char(2) not null comment '银行账号类型
            01一类
            02二类户',
   card_no              varchar(64) not null comment '银行账号',
   bank_code            varchar(20) comment '银行编码',
   bank_name            varchar(255) comment '银行名称',
   reserve_phone        varchar(11) comment '银行预留手机号',
   serial_no            varchar(64) comment '开户流水号',
   purpose              varchar(2) not null comment '账户用途
            01：放款
            02：还款',
   create_time          datetime not null comment '记录时间',
   update_time          datetime comment '更新时间',
   primary key (id)
)
ENGINE=InnoDB DEFAULT CHARSET=utf8 ROW_FORMAT=COMPACT COMMENT='账户表';

alter table tx_loan_account comment '账户信息表';

/*==============================================================*/
/* Table: tx_loan_apply                                         */
/*==============================================================*/
create table tx_loan_apply
(
   id                   char(20) not null comment '贷款申请ID',
   dept_identity        char(8) not null comment '资产方机构标识',
   product_code         char(8) not null comment '产品编码',
   order_id             char(32) not null comment '腾讯平台订单号：
            发送方机构ID + yyyyMMddHHmmss + 00（两位预留位）+ 6位随机数',
   user_id              varchar(32) comment '用户鉴权唯一编号',
   asset_order_id       varchar(255) comment '资金方订单号',
   pri_contract_num     varchar(1000) comment '资金方合同号',
   order_status         char(2) not null default '00' comment '贷前订单状态：
            00入库状态,
            11开户成功,12开户失败
            21授信成功,22授信失败23授信中
            31审批拒绝,32审批中,33审批通过
            41人工审批拒绝,42人工审批中,43人工审核通过
            51放款中,52放款成功,53放款失败',
   seq_no               varchar(20) not null comment '腾讯平台流水号',
   approval_seq         varchar(64) comment '审批流水号',
   br_no                varchar(10) comment '合作机构号 
            厦门信托机构号',
   project_no           varchar(20) comment '信托项目编号',
   product_no           varchar(20) comment '信托产品号',
   currency             char(3) not null default 'CNY' comment '申请币种
            CNY,人民币
            EUR,欧元
            GBP,英镑
            HCD,港币
            JPY,日元
            USD,美元',
   contact_name         varchar(20) comment '联系人姓名',
   contact_phone        varchar(20) comment '联系人手机号',
   contact_rel          varchar(2) comment '联系人关系
            01-亲属
            02-朋友
            03-同事
            04-其它
            05-担保人
            06-直系亲属',
   vouch_type           char(1) comment '担保方式
            1-质押
            2-抵押
            4-信用',
   loan_purpose         varchar(255) comment '贷款用途
            01-经营类
            02-消费类',
   loan_amt             varchar(64) not null comment '贷款金额',
   loan_term            varchar(3) comment '贷款期限(单位月)',
   loan_rate            varchar(20) comment '客户贷款利率',
   loan_capital_rate    varchar(20) comment '贷款产品利率',
   loan_strat_date      datetime comment '贷款起始日期',
   loan_end_date        datetime comment '贷款到期日期',
   pay_type             varchar(2) default '99' comment '放款类型:
            00自主支付
            01受托支付
            02现金
            03转账
            99其他
            ',
   deduct_tpye          char(1) comment '扣款类型:
            1-委托扣本息(足额)
            2-委托扣息(足额)
            3-委托扣本息(不足额)
            4-委托扣息(不足额)
            9-柜台还款',
   repay_type           varchar(2) comment '还款方式 01等额本息 02等额本金 03等本等息 04随借随还 05按频率付息、任意本金计划 06按月付息，按季还本 07按期付息到期还本 08到期还本付息 09一次性还款付息 10按期付息一次性还本 11按日均摊 12等额减息 13其他 ',
   first_pay_date       datetime comment '首次还款日',
   risk_level           varchar(64) comment '客户风险等级',
   risk_result          varchar(255) comment '风险识别结果',
   return_code          varchar(20) comment '返回结果代码',
   return_message       varchar(255) comment '返回信息',
   insurance_no         varchar(64) comment '保单号',
   insurance_url        varchar(255) comment '保单Url地址',
   redirect_url         varchar(512) comment '回调地址',
   reverse1             varchar(255) comment '备用字段1',
   reverse2             varchar(255) comment '备用字段2',
   reverse3             varchar(255) comment '备用字段3',
   reverse4             varchar(255) comment '备用字段4',
   reverse5             varchar(255) comment '备用字段5',
   create_time          datetime not null comment '记录时间',
   update_time          datetime comment '更新时间',
   primary key (id),
   key order_id (order_id)
)
ENGINE=InnoDB DEFAULT CHARSET=utf8 ROW_FORMAT=COMPACT COMMENT='贷款申请表';

alter table tx_loan_apply comment '贷款申请表';

/*==============================================================*/
/* Table: tx_loan_credit                                        */
/*==============================================================*/
create table tx_loan_credit
(
   id                   char(20) not null comment '授信ID',
   order_id             char(32) not null comment '腾讯平台订单号',
   bank_credit_no       varchar(64) comment '银行额度编号',
   insurer_credit_no    varchar(64) comment '保险额度编号',
   credit_term          varchar(3) comment '授信期限',
   credit_rate          varchar(20) comment '授信利率',
   credit_amt           varchar(64) comment '授信金额',
   begin_date           datetime comment '授信起始日',
   end_date             datetime comment '授信到期日',
   stsubs_ac_name       varchar(30) comment '受托支付户名',
   stsubs_acno          varchar(64) comment '受托支付账号',
   depesubs_ac_name     varchar(30) comment '受托扣款户名',
   depesubs_ac_no       varchar(64) comment '受托扣款账号',
   return_code          varchar(10) comment '返回结果代码',
   return_message       varchar(256) comment '返回信息',
   reverse1             varchar(256) comment '备用字段1',
   reverse2             varchar(256) comment '备用字段2',
   reverse3             varchar(256) comment '备用字段3',
   reverse4             varchar(256) comment '备用字段4',
   create_time          datetime not null comment '记录时间',
   update_time          datetime comment '更新时间',
   primary key (id),
   key order_id (order_id)
)
ENGINE=InnoDB DEFAULT CHARSET=utf8 ROW_FORMAT=COMPACT COMMENT='授信表';

alter table tx_loan_credit comment '贷款授信表';

/*==============================================================*/
/* Table: tx_loan_payment                                       */
/*==============================================================*/
create table tx_loan_payment
(
   id                   char(20) not null comment '放款ID',
   seq_no               char(20) comment '腾讯平台流水号',
   order_id             char(32) not null comment '腾讯平台订单号',
   contract_no          varchar(64) comment '合同编号(用户签署)',
   evidence_no          varchar(64) comment '借据号(放款通知)',
   pay_seq              varchar(64) comment '放款流水号',
   borrow_bank_code     varchar(10) comment '借方银行代码',
   borrow_ac_no         varchar(64) not null comment '借方账号',
   borrow_ac_name       varchar(30) comment '借方账户名称',
   loan_bank_code       varchar(10) comment '贷方银行代码',
   loan_ac_no           varchar(64) comment '贷方帐号',
   loan_ac_name         varchar(30) comment '贷方帐号户名',
   amount               varchar(64) not null comment '发生额',
   status               char(2) not null comment '放款(提现)状态（状态需要和申请表中保持同步）
            51放款(提现)中
            52放款(提现)成功
            53放款(提现)失败',
   message              varchar(256) comment '放款信息',
   start_time           datetime comment '放款发起时间',
   end_time             datetime comment '放款完成时间',
   recorded_mode        char(2) not null comment '入账方式
            00：实时
            01：T+1',
   redirect_url         varchar(256) not null comment '回调地址',
   create_time          datetime not null comment '创建时间',
   update_time          datetime comment '修改时间',
   primary key (id),
   key order_id (order_id, evidence_no)
)
ENGINE=InnoDB DEFAULT CHARSET=utf8 ROW_FORMAT=COMPACT COMMENT='支付记录表';

alter table tx_loan_payment comment '放款表';

/*==============================================================*/
/* Table: tx_loan_product                                       */
/*==============================================================*/
create table tx_loan_product
(
   id                   char(20) not null comment '产品表ID',
   product_code         char(8) not null comment '产品编码
            P0000001
            P0000002
            依次增加',
   cj_dept_identity     char(8) not null comment '资产方机构标识',
   yh_dept_identity     char(8) not null comment '资金方机构标识',
   bx_dept_identity     char(8) comment '保险机构标识',
   zf_dept_identity     char(8) comment '支付公司机构标识',
   forward_url          varchar(255) not null comment '请求转发地址',
   loan_term            char(3) comment '贷款期限',
   loan_rate            char(10) comment '贷款利率',
   pay_type             char(2) comment '放款方式
            见字典',
   repay_type           char(2) comment '还款方式
            见字典',
   create_time          datetime comment '创建时间',
   update_time          datetime comment '更新时间',
   operator             char(20) not null comment '操作人',
   del_flag             char(2) not null comment '删除标志
            01正常
            02删除',
   primary key (id),
   key AK_tx_loan_product_code (product_code)
);

alter table tx_loan_product comment '贷款产品表';

/*==============================================================*/
/* Table: tx_loan_repay_info                                    */
/*==============================================================*/
create table tx_loan_repay_info
(
   id                   char(20) not null comment '还款总状态ID',
   order_id             varchar(32) not null comment '腾讯平台订单号',
   evidence_no          varchar(64) comment '借据号',
   order_status         char(2) not null default '80' comment '贷后订单状态：
            80未还款
            81正常还款中
            82已逾期
            83提前结清
            84正常结清
            85逾期结清',
   repay_principal      varchar(32) comment '应还本金',
   actual_principal     varchar(32) comment '实还本金',
   residual_principal   varchar(32) comment '剩余本金',
   repay_interest       varchar(32) comment '应还利息',
   actual_interest      varchar(32) comment '实还利息',
   residual_interest    varchar(32) comment '剩余利息',
   repay_principal_interest varchar(32) comment '应还本金利息',
   actual_principal_interest varchar(32) comment '实还本金利息',
   residual_principal_interest varchar(32) comment '剩余本金利息',
   create_time          datetime not null comment '创建时间',
   update_time          datetime comment '修改时间',
   primary key (id)
)
ENGINE=InnoDB DEFAULT CHARSET=utf8 ROW_FORMAT=COMPACT COMMENT='还款表';

alter table tx_loan_repay_info comment '还款总状态表';

/*==============================================================*/
/* Table: tx_loan_repayment                                     */
/*==============================================================*/
create table tx_loan_repayment
(
   id                   char(20) not null comment '还款ID',
   order_id             varchar(32) not null comment '腾讯平台订单号',
   user_id              varchar(32) comment '用户鉴权唯一编号',
   yh_dept_identity     char(8) not null comment '资金方机构标识',
   cj_dept_identity     char(8) not null comment '资产方机构标识',
   repay_no             varchar(64) default NULL comment '资金方还款流水号',
   evidence_no          varchar(64) default NULL comment '借据号',
   trans_type           char(2) not null comment '交易类型：
            01-提前还款,
            02-退货,
            03-主动还当期
            04-自动还当期',
   loan_term            char(3) not null comment '本期期数',
   auto_pay_flag        char(1) not null default '1' comment '是否在线支付
            0否
            1是',
   otherpay_flag        varchar(1) not null default '0' comment '代偿标志
            0否
            1是',
   payback_flag         varchar(1) not null default '0' comment '回购标志
            0否
            1是',
   status               char(2) not null comment '本次还款状态（注意更新还款总状态）
            71 还款成功
            72 还款中
            73 还款失败',
   repay_time           datetime comment '还款时间',
   end_time             datetime comment '到期日',
   repay_account_type   char(2) comment '银行账号类型
            01一类
            02二类户',
   repay_account_no     varchar(64) comment '存款账户账号',
   repay_account_name   varchar(30) comment '存款账户名称',
   repay_amount         varchar(32) not null comment '本期实际还款总金额',
   repay_balance        varchar(32) comment '本期未还余额',
   plan_balance_fine    varchar(32) comment '本期计划本金罚息',
   plan_balance_cfine   varchar(32) comment '本期计划本金罚息复利',
   plan_interest_fine   varchar(32) comment '本期计划利息罚息',
   plan_interest_cfine  varchar(32) comment '本期计划利息罚息复利',
   plan_date            datetime comment '本期计划还款日期',
   plan_amount          varchar(32) comment '本期计划还款金额',
   plan_balance         varchar(32) comment '本期计划还款本金',
   plan_interest        varchar(32) comment '本期计划还款利息',
   plan_free            varchar(32) comment '本期计划应收费用',
   actual_principal     varchar(32) comment '本期已还本金',
   actual_principal_fine varchar(32) comment '本期已还本金罚息',
   actual_interest      varchar(32) comment '本期已还利息',
   actual_principal_cfine varchar(32) comment '本期已收本金罚息复利',
   actual_interest_fine varchar(32) comment '本期已收利息罚息',
   actual_interest_cfine varchar(32) comment '本期已收利息罚息复利',
   actual_free          varchar(32) comment '本期已收费用',
   unpaid_principal     varchar(32) comment '本期未还本金',
   unpaid_principal_fine varchar(32) comment '本期未还本金罚息',
   unpaid_interest      varchar(32) comment '本期未还利息',
   unpaid_principal_cfine varchar(32) comment '本期未还本金罚息复利',
   unpaid_interest_fine varchar(32) comment '本期未还利息罚息',
   unpaid_interest_cfine varchar(32) comment '本期未还利息罚息复利',
   unpaid_free          varchar(32) comment '本期未还费用',
   create_time          datetime not null comment '创建时间',
   update_time          datetime comment '修改时间',
   primary key (id)
)
ENGINE=InnoDB DEFAULT CHARSET=utf8 ROW_FORMAT=COMPACT COMMENT='还款表';

alter table tx_loan_repayment comment '还款记录表';

/*==============================================================*/
/* Table: tx_loan_withdraw                                      */
/*==============================================================*/
create table tx_loan_withdraw
(
   id                   char(20) not null comment '提现ID',
   seq_no               char(20) comment '腾讯平台流水号',
   order_id             char(32) comment '腾讯平台订单号',
   channel_seq          varchar(64) comment '提现流水号',
   cust_name            varchar(30) not null comment '借款人姓名',
   id_no                varchar(20) not null comment '证件号码',
   bank_card_no         varchar(30) not null comment '银行卡号',
   bank_no              varchar(30) comment '银行卡联行号',
   amount               varchar(64) not null comment '发生额',
   status               char(2) not null comment '提现状态（状态需要和申请表中保持同步）
            61提现中
            62提现成功
            63提现失败',
   message              varchar(256) comment '放款信息',
   start_time           datetime comment '提现发起时间',
   end_time             datetime comment '提现完成时间',
   create_time          datetime not null comment '创建时间',
   update_time          datetime comment '修改时间',
   primary key (id)
)
ENGINE=InnoDB DEFAULT CHARSET=utf8 ROW_FORMAT=COMPACT COMMENT='支付记录表';

alter table tx_loan_withdraw comment '提现流水表';

/*==============================================================*/
/* Table: tx_trans_jnl                                          */
/*==============================================================*/
create table tx_trans_jnl
(
   jnl_id               char(20) not null comment '流水主表ID',
   dept_identity        char(8) comment '机构标识',
   product_code         char(8) comment '产品编码',
   order_id             varchar(64) comment '贷款订单号',
   third_seq            varchar(32) comment '第三方流水号',
   local_ip             varchar(64) comment '节点IP',
   trans_code           varchar(64) comment '交易编码',
   remark               varchar(255) comment '备注',
   opr_date             timestamp comment '操作时间',
   primary key (jnl_id),
   key AK_Key_2 (third_seq)
);

alter table tx_trans_jnl comment '流水主表';

alter table sys_dept_resource add constraint FK_Reference_20 foreign key (dept_identity)
      references sys_dept (dept_identity) on delete restrict on update restrict;

alter table tx_attach_jnl add constraint FK_Reference_3 foreign key (jnl_id)
      references tx_trans_jnl (jnl_id) on delete restrict on update restrict;

alter table tx_bank_card_auth add constraint FK_Reference_16 foreign key (seq_no)
      references tx_trans_jnl (jnl_id) on delete restrict on update restrict;

alter table tx_cust_info add constraint FK_Reference_13 foreign key (order_id)
      references tx_loan_apply (order_id) on delete restrict on update restrict;

alter table tx_loan_account add constraint FK_Reference_14 foreign key (cust_id)
      references tx_cust_info (id) on delete restrict on update restrict;

alter table tx_loan_apply add constraint FK_Reference_18 foreign key (product_code)
      references tx_loan_product (product_code) on delete restrict on update restrict;

alter table tx_loan_credit add constraint fk_order_id_1 foreign key (order_id)
      references tx_loan_apply (order_id) on delete restrict on update restrict;

alter table tx_loan_payment add constraint fk_order_id_2 foreign key (order_id)
      references tx_loan_apply (order_id) on delete restrict on update restrict;

alter table tx_loan_payment add constraint fk_seq_no_1 foreign key (seq_no)
      references tx_trans_jnl (jnl_id) on delete restrict on update restrict;

alter table tx_loan_repay_info add constraint FK_Reference_17 foreign key (order_id)
      references tx_loan_apply (order_id) on delete restrict on update restrict;

alter table tx_loan_repayment add constraint fk_order_id_3 foreign key (order_id)
      references tx_loan_apply (order_id) on delete restrict on update restrict;

