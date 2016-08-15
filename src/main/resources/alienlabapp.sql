/*
SQLyog Ultimate v11.24 (32 bit)
MySQL - 5.6.21-log : Database - alienlabapp
*********************************************************************
*/

/*!40101 SET NAMES utf8 */;

/*!40101 SET SQL_MODE=''*/;

/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;
CREATE DATABASE /*!32312 IF NOT EXISTS*/`alienlabapp` /*!40100 DEFAULT CHARACTER SET utf8 */;

USE `alienlabapp`;

/*Table structure for table `tb_menu` */

DROP TABLE IF EXISTS `tb_menu`;

CREATE TABLE `tb_menu` (
  `menu_id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '菜单编码',
  `menu_name` varchar(60) DEFAULT NULL COMMENT '菜单名称',
  `menu_type` varchar(10) DEFAULT NULL COMMENT '菜单类型（标题、内部链接、外部链接、命令）',
  `menu_pid` bigint(10) DEFAULT NULL COMMENT '父类编码',
  `menu_content` varchar(100) DEFAULT NULL COMMENT '菜单内容（链接）',
  `menu_attr` varchar(200) DEFAULT NULL COMMENT '爱单属性（预留）',
  `menu_status` varchar(32) DEFAULT NULL COMMENT '状态',
  `menu_sort` int(5) DEFAULT NULL COMMENT '排序',
  PRIMARY KEY (`menu_id`),
  UNIQUE KEY `UNIQUE` (`menu_id`)
) ENGINE=InnoDB AUTO_INCREMENT=69 DEFAULT CHARSET=utf8;

/*Data for the table `tb_menu` */

insert  into `tb_menu`(`menu_id`,`menu_name`,`menu_type`,`menu_pid`,`menu_content`,`menu_attr`,`menu_status`,`menu_sort`) values (65,'系统设置','模块',0,'#','icon-home',NULL,1),(66,'用户管理','功能',65,NULL,NULL,'sys.user',2),(67,'角色管理','功能',65,NULL,NULL,'sys.role',4),(68,'菜单管理','功能',65,NULL,NULL,'sys.menu',5);

/*Table structure for table `tb_role` */

DROP TABLE IF EXISTS `tb_role`;

CREATE TABLE `tb_role` (
  `role_id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '角色编码',
  `role_name` varchar(30) DEFAULT NULL COMMENT '角色名称',
  `role_index` varchar(300) DEFAULT NULL COMMENT '角色首页',
  PRIMARY KEY (`role_id`),
  KEY `role_id` (`role_id`)
) ENGINE=InnoDB AUTO_INCREMENT=7 DEFAULT CHARSET=utf8;

/*Data for the table `tb_role` */

insert  into `tb_role`(`role_id`,`role_name`,`role_index`) values (4,'系统管理员','sys.index'),(5,'二级管理员','sys.index'),(6,'普通用户','sys.index');

/*Table structure for table `tb_role_menu` */

DROP TABLE IF EXISTS `tb_role_menu`;

CREATE TABLE `tb_role_menu` (
  `role_menu_id` bigint(20) NOT NULL AUTO_INCREMENT,
  `role_id` bigint(20) DEFAULT NULL COMMENT '角色编码',
  `menu_id` bigint(20) DEFAULT NULL COMMENT '菜单编码',
  PRIMARY KEY (`role_menu_id`),
  KEY `fkroleid` (`role_id`),
  KEY `fkmenuid` (`menu_id`),
  CONSTRAINT `fkmenuid` FOREIGN KEY (`menu_id`) REFERENCES `tb_menu` (`menu_id`),
  CONSTRAINT `fkroleid` FOREIGN KEY (`role_id`) REFERENCES `tb_role` (`role_id`)
) ENGINE=InnoDB AUTO_INCREMENT=5 DEFAULT CHARSET=utf8;

/*Data for the table `tb_role_menu` */

insert  into `tb_role_menu`(`role_menu_id`,`role_id`,`menu_id`) values (1,4,65),(2,4,66),(3,4,67),(4,4,68);

/*Table structure for table `tb_role_user` */

DROP TABLE IF EXISTS `tb_role_user`;

CREATE TABLE `tb_role_user` (
  `role_user_id` bigint(20) NOT NULL AUTO_INCREMENT,
  `role_id` bigint(20) NOT NULL COMMENT '角色编码',
  `user_id` bigint(20) NOT NULL COMMENT '用户编码',
  PRIMARY KEY (`role_user_id`),
  KEY `fk_roleid` (`role_id`),
  KEY `fkuserid` (`user_id`),
  CONSTRAINT `fk_roleid` FOREIGN KEY (`role_id`) REFERENCES `tb_role` (`role_id`),
  CONSTRAINT `fkuserid` FOREIGN KEY (`user_id`) REFERENCES `tb_user` (`user_id`)
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8;

/*Data for the table `tb_role_user` */

insert  into `tb_role_user`(`role_user_id`,`role_id`,`user_id`) values (1,4,1231),(2,5,1231),(3,6,1232);

/*Table structure for table `tb_user` */

DROP TABLE IF EXISTS `tb_user`;

CREATE TABLE `tb_user` (
  `user_id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '用户编码',
  `user_loginname` varchar(20) NOT NULL COMMENT '用户登录名',
  `user_pwd` varchar(64) NOT NULL COMMENT '用户密码',
  `user_name` varchar(90) DEFAULT NULL COMMENT '用户名称',
  `user_createtime` datetime DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `user_lastlogin` datetime DEFAULT NULL COMMENT '最后一次登录时间',
  `user_purview` varchar(10) DEFAULT NULL COMMENT '权限标识（ALL表示所有权限）',
  `user_status` varchar(5) DEFAULT '1' COMMENT '用户状态',
  PRIMARY KEY (`user_id`),
  UNIQUE KEY `UNIQUE_LOGINNAME` (`user_loginname`),
  FULLTEXT KEY `login_index` (`user_loginname`,`user_pwd`)
) ENGINE=InnoDB AUTO_INCREMENT=1233 DEFAULT CHARSET=utf8;

/*Data for the table `tb_user` */

insert  into `tb_user`(`user_id`,`user_loginname`,`user_pwd`,`user_name`,`user_createtime`,`user_lastlogin`,`user_purview`,`user_status`) values (1231,'alienzoo','EH1YaFFnD2hcOwpl','unail','2016-08-03 16:52:08','2016-08-15 13:11:52','ALL','1'),(1232,'user1','EH1YaFFnD2hcOwpl','staff1','2016-08-04 18:26:10','2016-08-05 09:52:47','3','1');

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;
