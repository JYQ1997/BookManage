
-- 公告表
DROP TABLE IF EXISTS `oa_notify`;
CREATE TABLE `oa_notify` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '编号',
  `type` char(1) COLLATE utf8_bin DEFAULT NULL COMMENT '类型',
  `title` varchar(200) COLLATE utf8_bin DEFAULT NULL COMMENT '标题',
  `content` varchar(2000) COLLATE utf8_bin DEFAULT NULL COMMENT '内容',
  `files` varchar(2000) COLLATE utf8_bin DEFAULT NULL COMMENT '附件',
  `status` char(1) COLLATE utf8_bin DEFAULT NULL COMMENT '状态',
  `create_by` bigint(20) DEFAULT NULL COMMENT '创建者',
  `create_date` datetime DEFAULT NULL COMMENT '创建时间',
  `update_by` varchar(64) COLLATE utf8_bin DEFAULT NULL COMMENT '更新者',
  `update_date` datetime DEFAULT NULL COMMENT '更新时间',
  `remarks` varchar(255) COLLATE utf8_bin DEFAULT NULL COMMENT '备注信息',
  `del_flag` char(1) COLLATE utf8_bin DEFAULT '0' COMMENT '删除标记',
  PRIMARY KEY (`id`),
  KEY `oa_notify_del_flag` (`del_flag`)
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8 COLLATE=utf8_bin COMMENT='通知通告';

-- 系统文件表
-- ----------------------------
DROP TABLE IF EXISTS `sys_file`;
CREATE TABLE `sys_file` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `type` int(11) DEFAULT NULL COMMENT '文件类型',
  `url` varchar(200) DEFAULT NULL COMMENT 'URL地址',
  `create_date` datetime DEFAULT NULL COMMENT '创建时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8 COMMENT='文件上传';


-- 系统菜单表
DROP TABLE IF EXISTS `sys_menu`;
CREATE TABLE `sys_menu` (
  `menu_id` bigint(20) NOT NULL AUTO_INCREMENT,
  `parent_id` bigint(20) DEFAULT NULL COMMENT '父菜单ID，一级菜单为0',
  `name` varchar(50) DEFAULT NULL COMMENT '菜单名称',
  `url` varchar(200) DEFAULT NULL COMMENT '菜单URL',
  `perms` varchar(500) DEFAULT NULL COMMENT '授权(多个用逗号分隔，如：user:list,user:create)',
  `type` int(11) DEFAULT NULL COMMENT '类型   0：目录   1：菜单   2：按钮',
  `icon` varchar(50) DEFAULT NULL COMMENT '菜单图标',
  `order_num` int(11) DEFAULT NULL COMMENT '排序',
  `gmt_create` datetime DEFAULT NULL COMMENT '创建时间',
  `gmt_modified` datetime DEFAULT NULL COMMENT '修改时间',
  PRIMARY KEY (`menu_id`)
) ENGINE=InnoDB AUTO_INCREMENT=26 DEFAULT CHARSET=utf8 COMMENT='菜单管理';

INSERT INTO `sys_menu` VALUES ('1', '0', '基础管理', '', '', '0', 'fa fa-bars', '0', '2020-10-13 12:00:00', '2020-10-13 12:00:00');
INSERT INTO `sys_menu` VALUES ('2', '1', '文件管理', '/common/sysFile', 'common:sysFile:sysFile', '1', 'fa fa-folder-open', '0', '2020-10-13 12:00:00', '2020-10-13 12:00:00');
INSERT INTO `sys_menu` VALUES ('3', '1', 'swagger', '/swagger-ui.html', '', '1', '', '1', '2020-10-13 12:00:00', '2020-10-13 12:00:00');
INSERT INTO `sys_menu` VALUES ('4', '0', '系统管理', null, null, '0', 'fa fa-desktop', '1', '2020-10-13 12:00:00',
'2020-10-13 12:00:00');
INSERT INTO `sys_menu` VALUES ('5', '4', '系统菜单', 'sys/menu/', 'sys:menu:menu', '1', 'fa fa-th-list', '0','2020-10-13 12:00:00', '2020-10-13 12:00:00');
INSERT INTO `sys_menu` VALUES ('6', '4', '新增', '', 'sys:menu:add', '2', '', '0', '2020-10-13 12:00:00', '2020-10-13 12:00:00');
INSERT INTO `sys_menu` VALUES ('7', '4', '编辑', '', 'sys:menu:edit', '2', '', '0', '2020-10-13 12:00:00', '2020-10-13 12:00:00');
INSERT INTO `sys_menu` VALUES ('8', '4', '删除', '', 'sys:menu:remove', '2', '', '0', '2020-10-13 12:00:00', '2020-10-13 12:00:00');
INSERT INTO `sys_menu` VALUES ('9', '4', '用户管理', 'sys/user/', 'sys:user:user', '1', 'fa fa-user', '1', '2020-10-13 12:00:00', '2020-10-13 12:00:00');
INSERT INTO `sys_menu` VALUES ('10', '9', '新增', '', 'sys:user:add', '2', '', '0', '2020-10-13 12:00:00', '2020-10-13 12:00:00');
INSERT INTO `sys_menu` VALUES ('11', '9', '编辑', '', 'sys:user:edit', '2', '', '0', '2020-10-13 12:00:00', '2020-10-13 12:00:00');
INSERT INTO `sys_menu` VALUES ('12', '9', '删除', null, 'sys:user:remove', '2', null, '0', '2020-10-13 12:00:00', '2020-10-13 12:00:00');
INSERT INTO `sys_menu` VALUES ('13', '9', '批量删除', '', 'sys:user:batchRemove', '2', '', '0', '2020-10-13 12:00:00', '2020-10-13 12:00:00');
INSERT INTO `sys_menu` VALUES ('14', '9', '停用', null, 'sys:user:disable', '2', null, '0', '2020-10-13 12:00:00', '2020-10-13 12:00:00');
INSERT INTO `sys_menu` VALUES ('15', '9', '重置密码', '', 'sys:user:resetPwd', '2', '', '0', '2020-10-13 12:00:00', '2020-10-13 12:00:00');
INSERT INTO `sys_menu` VALUES ('16', '9', '角色管理', 'sys/role', 'sys:role:role', '1', 'fa fa-paw', '2', '2020-10-13 12:00:00', '2020-10-13 12:00:00');
INSERT INTO `sys_menu` VALUES ('17', '16', '新增', '', 'sys:role:add', '2', '', '0', '2020-10-13 12:00:00', '2020-10-13 12:00:00');
INSERT INTO `sys_menu` VALUES ('18', '16', '编辑', '', 'sys:role:edit', '2', '', null, '2020-10-13 12:00:00', '2020-10-13 12:00:00');
INSERT INTO `sys_menu` VALUES ('19', '16', '删除', '', 'sys:role:remove', '2', null, null, '2020-10-13 12:00:00', '2020-10-13 12:00:00');
INSERT INTO `sys_menu` VALUES ('20', '0', '图书管理', '', '', '0', 'fa fa-rss', '3', '2020-10-13 12:00:00', '2020-10-13 12:00:00');
INSERT INTO `sys_menu` VALUES ('21', '20', '类型管理', 'book/type', 'book:type', '1', 'fa fa-file-image-o', '1', '2020-10-13 12:00:00', '2020-10-13 12:00:00');
INSERT INTO `sys_menu` VALUES ('22', '21', '新增', '', 'book:type:add', '2', '', null, '2020-10-13 12:00:00', '2020-10-13 12:00:00');
INSERT INTO `sys_menu` VALUES ('23', '21', '编辑', '', 'book:type:edit', '2', null, null, '2020-10-13 12:00:00', '2020-10-13 12:00:00');
INSERT INTO `sys_menu` VALUES ('24', '21', '删除', '', 'book:type:remove', '2', null, null, '2020-10-13 12:00:00', '2020-10-13 12:00:00');
INSERT INTO `sys_menu` VALUES ('25', '21', '批量删除', '', 'book:type:batchRemove', '2', null, null, '2020-10-13 12:00:00', '2020-10-13 12:00:00');
INSERT INTO `sys_menu` VALUES ('26', '20', '图书列表', '/book/list', 'book:list', '1', 'fa fa-edit', '2','2020-10-13 12:00:00', '2020-10-13 12:00:00');
INSERT INTO `sys_menu` VALUES ('27', '26', '上架', '', 'book:list:enable', '2', '', null, '2020-10-13 12:00:00',
'2020-10-13 12:00:00');
INSERT INTO `sys_menu` VALUES ('28', '26', '下架', '', 'book:list:disable', '2', null, null, '2020-10-13 12:00:00',
'2020-10-13 12:00:00');
INSERT INTO `sys_menu` VALUES ('29', '26', '阅读', '', 'book:list:read', '2', null, null, '2020-10-13 12:00:00',
'2020-10-13 12:00:00');
INSERT INTO `sys_menu` VALUES ('30', '26', '下载', '', 'book:list:download', '2', null, null, '2020-10-13 12:00:00',
'2020-10-13 12:00:00');
INSERT INTO `sys_menu` VALUES ('31', '20', '上传图书', '/book/list', 'book:list', '1', 'fa fa-edit', '3','2020-10-13 12:00:00', '2020-10-13 12:00:00');


-- 系统角色表
DROP TABLE IF EXISTS `sys_role`;
CREATE TABLE `sys_role` (
  `role_id` bigint(20) NOT NULL AUTO_INCREMENT,
  `role_name` varchar(100) DEFAULT NULL COMMENT '角色名称',
  `role_sign` varchar(100) DEFAULT NULL COMMENT '角色标识',
  `remark` varchar(100) DEFAULT NULL COMMENT '备注',
  `user_id_create` bigint(255) DEFAULT NULL COMMENT '创建用户id',
  `gmt_create` datetime DEFAULT NULL COMMENT '创建时间',
  `gmt_modified` datetime DEFAULT NULL COMMENT '创建时间',
  PRIMARY KEY (`role_id`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8 COMMENT='角色';

-- ----------------------------
-- Records of sys_role
-- ----------------------------
INSERT INTO `sys_role` VALUES ('1', '超级用户角色', 'admin', '拥有最高权限', null, '2020-10-13 12:00:00', '2020-10-13 12:00:00');
INSERT INTO `sys_role` VALUES ('2', '普通用户', 'user', '基本用户权限', null, '2020-10-13 12:00:00', '2020-10-13 12:00:00');


DROP TABLE IF EXISTS `sys_role_menu`;
CREATE TABLE `sys_role_menu` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `role_id` bigint(20) DEFAULT NULL COMMENT '角色ID',
  `menu_id` bigint(20) DEFAULT NULL COMMENT '菜单ID',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8 COMMENT='角色与菜单对应关系';

INSERT INTO `sys_role_menu` (role_id,menu_id) VALUES ('1','1');
INSERT INTO `sys_role_menu` (role_id,menu_id) VALUES ('1','2');
INSERT INTO `sys_role_menu` (role_id,menu_id) VALUES ('1','3');
INSERT INTO `sys_role_menu` (role_id,menu_id) VALUES ('1','4');
INSERT INTO `sys_role_menu` (role_id,menu_id) VALUES ('1','5');
INSERT INTO `sys_role_menu` (role_id,menu_id) VALUES ('1','6');
INSERT INTO `sys_role_menu` (role_id,menu_id) VALUES ('1','7');
INSERT INTO `sys_role_menu` (role_id,menu_id) VALUES ('1','8');
INSERT INTO `sys_role_menu` (role_id,menu_id) VALUES ('1','9');
INSERT INTO `sys_role_menu` (role_id,menu_id) VALUES ('1','10');
INSERT INTO `sys_role_menu` (role_id,menu_id) VALUES ('1','11');
INSERT INTO `sys_role_menu` (role_id,menu_id) VALUES ('1','12');
INSERT INTO `sys_role_menu` (role_id,menu_id) VALUES ('1','13');
INSERT INTO `sys_role_menu` (role_id,menu_id) VALUES ('1','14');
INSERT INTO `sys_role_menu` (role_id,menu_id) VALUES ('1','15');
INSERT INTO `sys_role_menu` (role_id,menu_id) VALUES ('1','16');
INSERT INTO `sys_role_menu` (role_id,menu_id) VALUES ('1','17');
INSERT INTO `sys_role_menu` (role_id,menu_id) VALUES ('1','18');
INSERT INTO `sys_role_menu` (role_id,menu_id) VALUES ('1','19');
INSERT INTO `sys_role_menu` (role_id,menu_id) VALUES ('1','20');
INSERT INTO `sys_role_menu` (role_id,menu_id) VALUES ('1','21');
INSERT INTO `sys_role_menu` (role_id,menu_id) VALUES ('1','22');
INSERT INTO `sys_role_menu` (role_id,menu_id) VALUES ('1','23');
INSERT INTO `sys_role_menu` (role_id,menu_id) VALUES ('1','24');
INSERT INTO `sys_role_menu` (role_id,menu_id) VALUES ('1','25');
INSERT INTO `sys_role_menu` (role_id,menu_id) VALUES ('1','26');
INSERT INTO `sys_role_menu` (role_id,menu_id) VALUES ('1','27');
INSERT INTO `sys_role_menu` (role_id,menu_id) VALUES ('1','28');
INSERT INTO `sys_role_menu` (role_id,menu_id) VALUES ('1','29');
INSERT INTO `sys_role_menu` (role_id,menu_id) VALUES ('1','30');
INSERT INTO `sys_role_menu` (role_id,menu_id) VALUES ('1','31');
-- 系统用户表
DROP TABLE IF EXISTS `sys_user`;
CREATE TABLE `sys_user` (
  `user_id` bigint(20) NOT NULL AUTO_INCREMENT,
  `user_name` varchar(50) DEFAULT NULL COMMENT '用户名',
  `name` varchar(100) DEFAULT NULL,
  `password` varchar(50) DEFAULT NULL COMMENT '密码',
  `email` varchar(100) DEFAULT NULL COMMENT '邮箱',
  `mobile` varchar(100) DEFAULT NULL COMMENT '手机号',
  `status` tinyint(255) DEFAULT NULL COMMENT '状态 0:禁用，1:正常',
  `user_id_create` bigint(255) DEFAULT NULL COMMENT '创建用户id',
  `gmt_create` datetime DEFAULT NULL COMMENT '创建时间',
  `gmt_modified` datetime DEFAULT NULL COMMENT '修改时间',
  `sex` bigint(32) DEFAULT NULL COMMENT '性别',
  `birth` datetime DEFAULT NULL COMMENT '出生日期',
  `pic_id` bigint(32) DEFAULT NULL,
  `live_address` varchar(500) DEFAULT NULL COMMENT '现居住地',
  `hobby` varchar(255) DEFAULT NULL COMMENT '爱好',
  `province` varchar(255) DEFAULT NULL COMMENT '省份',
  `city` varchar(255) DEFAULT NULL COMMENT '所在城市',
  `district` varchar(255) DEFAULT NULL COMMENT '所在地区',
  PRIMARY KEY (`user_id`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8;

INSERT INTO `sys_user` VALUES ('1', 'admin', '超级管理员', '132456', '6', 'admin@example.com', '17699999999', '1', '1',
'2020-10-13 12:00:00', '2020-10-13 12:00:00', '96', '2017-12-14 00:00:00', '138', 'ccc', '122;121;', '北京市', '北京市市辖区', '东城区');
INSERT INTO `sys_user` VALUES ('2', 'test', '临时用户', '132456', '6', 'test@bootdo.com', null, '1', '1', '2017-08-14 13:43:05', '2017-08-14 21:15:36', null, null, null, null, null, null, null, null);


-- 用户角色关联表
DROP TABLE IF EXISTS `sys_user_role`;
CREATE TABLE `sys_user_role` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `user_id` bigint(20) DEFAULT NULL COMMENT '用户ID',
  `role_id` bigint(20) DEFAULT NULL COMMENT '角色ID',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8 COMMENT='用户与角色对应关系';

INSERT INTO `sys_user_role` VALUES ('1', '1', '1');
INSERT INTO `sys_user_role` VALUES ('2', '2', '2');

DROP TABLE IF EXISTS `book_content`;
CREATE TABLE `book_content` (
  `bid` bigint(20) NOT NULL AUTO_INCREMENT,
  `title` varchar(255) DEFAULT NULL COMMENT '图书标题',
  `slug` varchar(255) DEFAULT NULL,
  `created` bigint(20) DEFAULT NULL COMMENT '创建人id',
  `modified` bigint(20) DEFAULT NULL COMMENT '最近修改人id',
  `desc` text COMMENT '内容简介',
  `type` varchar(16) DEFAULT NULL COMMENT '类型',
  `tags` varchar(200) DEFAULT NULL COMMENT '标签',
  `images` varchar(200) DEFAULT NULL COMMENT '封面',
  `book_url` varchar(200) DEFAULT NULL COMMENT '图书路径',
  `is_download` int(1) DEFAULT NULL COMMENT '1:允许下载;2:禁止下载',
  `download_pay` datetime DEFAULT NULL COMMENT '下载金币',
  `comments_num` int(5) DEFAULT '0' COMMENT '评论数量',
  `allow_comment` int(1) DEFAULT '0' COMMENT '1:允许评论;2:禁止评论',
  `allow_feed` int(1) DEFAULT '0' COMMENT '允许反馈',
  `status` int(1) DEFAULT NULL COMMENT '状态 1:上架;2:下架',
  `author` varchar(100) DEFAULT NULL COMMENT '作者',
  `gtm_create` datetime DEFAULT NULL COMMENT '创建时间',
  `gtm_modified` datetime DEFAULT NULL COMMENT '修改时间',
  PRIMARY KEY (`bid`)
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8 COMMENT='文章内容';

