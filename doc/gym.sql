-- MySQL dump 10.13  Distrib 5.6.22, for Win64 (x86_64)
--
-- Host: 127.0.0.1    Database: gym
-- ------------------------------------------------------
-- Server version	5.6.22-log

/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8 */;
/*!40103 SET @OLD_TIME_ZONE=@@TIME_ZONE */;
/*!40103 SET TIME_ZONE='+00:00' */;
/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;

--
-- Table structure for table `file_apk_version`
--

DROP TABLE IF EXISTS `file_apk_version`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `file_apk_version` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `admin` tinyint(1) NOT NULL,
  `detail` varchar(255) DEFAULT NULL,
  `name` varchar(255) DEFAULT NULL,
  `packageName` varchar(255) DEFAULT NULL,
  `verCode` varchar(255) DEFAULT NULL,
  `verName` varchar(255) DEFAULT NULL,
  `verPath` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `id` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `file_apk_version`
--

LOCK TABLES `file_apk_version` WRITE;
/*!40000 ALTER TABLE `file_apk_version` DISABLE KEYS */;
INSERT INTO `file_apk_version` VALUES (1,1,'FitnessCenter_1.0.18_manager.apk管理员版本。','FitnessCenter_1.0.17_manager.apk','net.jylcdw','1018','1.0.18','http://localhost:8080/gym/FitnessCenter_1.0.18_manager.apk');
/*!40000 ALTER TABLE `file_apk_version` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `file_attachment`
--

DROP TABLE IF EXISTS `file_attachment`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `file_attachment` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `ext` varchar(255) DEFAULT NULL,
  `name` varchar(255) DEFAULT NULL,
  `path` varchar(255) DEFAULT NULL,
  `uuid` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `id` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `file_attachment`
--

LOCK TABLES `file_attachment` WRITE;
/*!40000 ALTER TABLE `file_attachment` DISABLE KEYS */;
/*!40000 ALTER TABLE `file_attachment` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `gym_back_db`
--

DROP TABLE IF EXISTS `gym_back_db`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `gym_back_db` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `createTime` varchar(255) DEFAULT NULL,
  `name` varchar(255) DEFAULT NULL,
  `username` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `id` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=10 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `gym_back_db`
--

LOCK TABLES `gym_back_db` WRITE;
/*!40000 ALTER TABLE `gym_back_db` DISABLE KEYS */;
INSERT INTO `gym_back_db` VALUES (1,'2014-12-10 20:11:33','C:/gym_logs/gym_2014-12-10201133.sql','大兀'),(2,'2014-12-10 20:16:46','C:/gym_logs/gym_2014-12-10201646.sql','大兀'),(3,'2014-12-10 20:16:52','C:/gym_logs/gym_2014-12-10201652.sql','大兀'),(4,'2014-12-10 20:18:31','C:/gym_logs/gym_2014-12-10201831.sql','大兀'),(5,'2014-12-10 20:18:56','C:/gym_logs/gym_2014-12-10201856.sql','大兀'),(6,'2014-12-10 20:23:27','C:/gym_logs/gym_2014-12-10202327.sql','大兀'),(7,'2014-12-11 09:02:24','C:/gym_logs/gym_2014-12-11090224.sql','大兀'),(8,'2014-12-11 09:02:31','C:/gym_logs/gym_2014-12-11090231.sql','大兀'),(9,'2014-12-13 13:42:49','C:/gym_logs/gym_2014-12-13134249.sql','大兀');
/*!40000 ALTER TABLE `gym_back_db` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `gym_deductions`
--

DROP TABLE IF EXISTS `gym_deductions`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `gym_deductions` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `completeTime` varchar(255) DEFAULT NULL,
  `createTime` varchar(255) DEFAULT NULL,
  `exeCname` varchar(255) DEFAULT NULL,
  `exeUser` varchar(255) DEFAULT NULL,
  `money` double DEFAULT NULL,
  `remind` tinyint(1) NOT NULL,
  `status` int(11) DEFAULT NULL,
  `type` varchar(255) DEFAULT NULL,
  `username` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `id` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=12 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `gym_deductions`
--

LOCK TABLES `gym_deductions` WRITE;
/*!40000 ALTER TABLE `gym_deductions` DISABLE KEYS */;
INSERT INTO `gym_deductions` VALUES (1,'2014-12-10 09:59:26','2014-12-09 17:43:34','大兀','大兀',80.58,0,2,'1',NULL),(2,'2014-12-10 09:59:05','2014-12-09 17:43:41','大兀','大兀',888.88,0,2,'0',NULL),(3,'2014-12-10 10:01:36','2014-12-10 09:59:08','大兀','大兀',600,1,1,'0','A8'),(4,'2014-12-10 10:00:00','2014-12-10 09:59:35','大兀','大兀',500,1,1,'1','A8'),(5,'2014-12-10 12:21:54','2014-12-10 11:36:54','大兀','大兀',458.58,0,2,'0',NULL),(6,'2014-12-10 19:20:07','2014-12-10 11:36:56','大兀','大兀',20,1,1,'1','A16'),(7,'2014-12-10 19:20:04','2014-12-10 12:21:56','大兀','大兀',580,1,1,'0','A16'),(8,'2014-12-10 19:27:31','2014-12-10 19:20:30','大兀','大兀',99900.99,1,1,'0','A16'),(9,'2014-12-10 19:21:06','2014-12-10 19:20:31','大兀','大兀',99999.99,1,1,'1','A16'),(10,'2014-12-10 19:26:34','2014-12-10 19:26:19','test','test',89.74,1,1,'0','A16'),(11,'2014-12-10 19:26:36','2014-12-10 19:26:20','test','test',56.12,1,1,'1','A16');
/*!40000 ALTER TABLE `gym_deductions` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `gym_log`
--

DROP TABLE IF EXISTS `gym_log`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `gym_log` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `createTime` varchar(255) DEFAULT NULL,
  `name` varchar(255) DEFAULT NULL,
  `username` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `id` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=89 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `gym_log`
--

LOCK TABLES `gym_log` WRITE;
/*!40000 ALTER TABLE `gym_log` DISABLE KEYS */;
INSERT INTO `gym_log` VALUES (1,'2014-12-09 17:32:18','注册','A1'),(2,'2014-12-09 17:34:10','注册','A2'),(3,'2014-12-09 17:35:34','注册','A3'),(4,'2014-12-09 17:41:01','注册','A4'),(5,'2014-12-09 17:42:41','注册','大兀'),(6,'2014-12-09 17:42:46','登录','大兀'),(7,'2014-12-09 17:44:26','退出','大兀'),(8,'2014-12-09 17:45:37','注册','操作员888'),(9,'2014-12-09 17:45:47','登录','大兀'),(10,'2014-12-09 17:45:58','设置修改','大兀'),(11,'2014-12-09 17:46:10','登录','大兀'),(12,'2014-12-10 09:22:01','注册','test'),(13,'2014-12-10 09:56:29','登录','大兀'),(14,'2014-12-10 09:58:26','注册','A8'),(15,'2014-12-10 09:58:57','登录','A8'),(16,'2014-12-10 10:01:36','记录修改','大兀'),(17,'2014-12-10 10:40:42','注册','A9'),(18,'2014-12-10 10:52:06','注册','A10'),(19,'2014-12-10 11:28:34','注册','A11'),(20,'2014-12-10 11:32:54','退出','大兀'),(21,'2014-12-10 11:35:11','注册','A12'),(22,'2014-12-10 11:36:40','登录','大兀'),(23,'2014-12-10 12:00:35','登录','A8'),(24,'2014-12-10 12:21:26','注册','test1'),(25,'2014-12-10 13:21:20','登录','大兀'),(26,'2014-12-10 13:24:50','登录','大兀'),(27,'2014-12-10 17:47:41','登录','大兀'),(28,'2014-12-10 18:58:52','注册','A14'),(29,'2014-12-10 19:00:17','登录','大兀'),(30,'2014-12-10 19:01:12','退出','大兀'),(31,'2014-12-10 19:01:59','登录','大兀'),(32,'2014-12-10 19:02:17','设置修改','大兀'),(33,'2014-12-10 19:02:23','退出','大兀'),(34,'2014-12-10 19:03:23','注册','test2'),(35,'2014-12-10 19:03:42','登录','大兀'),(36,'2014-12-10 19:04:04','设置修改','大兀'),(37,'2014-12-10 19:04:13','设置修改','大兀'),(38,'2014-12-10 19:04:49','登录','test'),(39,'2014-12-10 19:16:33','注册','A16'),(40,'2014-12-10 19:17:15','查询找回密码问题','A1'),(41,'2014-12-10 19:18:25','查询找回密码问题','A1'),(42,'2014-12-10 19:20:00','登录','A16'),(43,'2014-12-10 19:21:38','退出','大兀'),(44,'2014-12-10 19:22:35','注册','sjy'),(45,'2014-12-10 19:23:19','登录','大兀'),(46,'2014-12-10 19:23:28','设置修改','大兀'),(47,'2014-12-10 19:23:31','设置修改','大兀'),(48,'2014-12-10 19:23:48','登录','sjy'),(49,'2014-12-10 19:23:49','审核','sjy'),(50,'2014-12-10 19:24:33','审核','sjy'),(51,'2014-12-10 19:24:37','审核','sjy'),(52,'2014-12-10 19:24:42','审核','sjy'),(53,'2014-12-10 19:24:46','审核','sjy'),(54,'2014-12-10 19:24:48','审核','sjy'),(55,'2014-12-10 19:24:54','审核','sjy'),(56,'2014-12-10 19:24:57','审核','sjy'),(57,'2014-12-10 19:25:54','登录','test'),(58,'2014-12-10 19:27:31','记录修改','test'),(59,'2014-12-10 19:27:54','退出','A16'),(60,'2014-12-10 19:28:31','登录','A16'),(61,'2014-12-10 19:41:55','登录','A16'),(62,'2014-12-10 19:42:54','退出','A16'),(63,'2014-12-10 19:43:22','查询找回密码问题','A14'),(64,'2014-12-10 19:47:02','登录','大兀'),(65,'2014-12-10 20:11:33','备份','大兀'),(66,'2014-12-10 20:16:46','备份','大兀'),(67,'2014-12-10 20:16:52','备份','大兀'),(68,'2014-12-10 20:17:48','退出','test'),(69,'2014-12-10 20:18:23','登录','大兀'),(70,'2014-12-10 20:18:31','备份','大兀'),(71,'2014-12-10 20:18:56','备份','大兀'),(72,'2014-12-10 20:23:27','备份','大兀'),(73,'2014-12-11 09:02:24','备份','大兀'),(75,'2014-12-13 11:26:44','退出','大兀'),(76,'2014-12-13 11:27:22','登录','大兀'),(77,'2014-12-13 11:32:45','登录','大兀'),(78,'2014-12-13 12:24:02','登录','大兀'),(79,'2014-12-13 12:25:02','登录','A16'),(80,'2014-12-13 12:43:15','登录','大兀'),(81,'2014-12-13 13:21:14','登录','大兀'),(82,'2014-12-13 13:24:03','登录','大兀'),(83,'2014-12-13 13:26:02','退出','大兀'),(84,'2014-12-13 13:26:17','登录','大兀'),(85,'2014-12-13 13:29:31','退出','大兀'),(86,'2014-12-13 13:29:44','登录','大兀'),(87,'2014-12-13 13:42:29','登录','大兀'),(88,'2014-12-13 13:42:49','备份','大兀');
/*!40000 ALTER TABLE `gym_log` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `gym_member_limit`
--

DROP TABLE IF EXISTS `gym_member_limit`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `gym_member_limit` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `createTime` varchar(255) DEFAULT NULL,
  `limitnum` varchar(255) DEFAULT NULL,
  `loginName` varchar(255) DEFAULT NULL,
  `updateTime` varchar(255) DEFAULT NULL,
  `uuid` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `id` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `gym_member_limit`
--

LOCK TABLES `gym_member_limit` WRITE;
/*!40000 ALTER TABLE `gym_member_limit` DISABLE KEYS */;
/*!40000 ALTER TABLE `gym_member_limit` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `gym_member_money`
--

DROP TABLE IF EXISTS `gym_member_money`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `gym_member_money` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `loginName` varchar(255) DEFAULT NULL,
  `minusLimit` double DEFAULT NULL,
  `money` double DEFAULT NULL,
  `updateTime` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `id` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=12 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `gym_member_money`
--

LOCK TABLES `gym_member_money` WRITE;
/*!40000 ALTER TABLE `gym_member_money` DISABLE KEYS */;
INSERT INTO `gym_member_money` VALUES (1,'A1',30,0,'2014-12-09 17:32:18'),(2,'A2',30,0,'2014-12-09 17:34:10'),(3,'A3',30,0,'2014-12-09 17:35:34'),(4,'A4',30,0,'2014-12-09 17:41:01'),(5,'A8',99999.99,100,'2014-12-10 10:01:36'),(6,'A9',30,0,'2014-12-10 10:40:42'),(7,'A10',30,0,'2014-12-10 10:52:05'),(8,'A11',30,0,'2014-12-10 11:28:33'),(9,'A12',30,0,'2014-12-10 11:35:11'),(10,'A14',30,0,'2014-12-10 18:58:52'),(11,'A16',99999.99,494.62,'2014-12-10 19:27:31');
/*!40000 ALTER TABLE `gym_member_money` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `gym_members`
--

DROP TABLE IF EXISTS `gym_members`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `gym_members` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `createTime` varchar(255) DEFAULT NULL,
  `errorTimes` int(11) DEFAULT NULL,
  `firstLogin` tinyint(1) DEFAULT NULL,
  `locked` tinyint(1) DEFAULT NULL,
  `lockedTime` varchar(255) DEFAULT NULL,
  `loginName` varchar(255) DEFAULT NULL,
  `loginTime` varchar(255) DEFAULT NULL,
  `mac` varchar(255) DEFAULT NULL,
  `passed` tinyint(1) NOT NULL,
  `password` varchar(255) DEFAULT NULL,
  `qx1` int(11) DEFAULT NULL,
  `qx2` int(11) DEFAULT NULL,
  `qx3` int(11) DEFAULT NULL,
  `qx4` int(11) DEFAULT NULL,
  `qx5` int(11) DEFAULT NULL,
  `roleId` varchar(255) DEFAULT NULL,
  `uuid` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `id` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=18 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `gym_members`
--

LOCK TABLES `gym_members` WRITE;
/*!40000 ALTER TABLE `gym_members` DISABLE KEYS */;
INSERT INTO `gym_members` VALUES (1,'2014-12-09 17:32:17',3,1,0,'2014-12-09 17:32:17','A1',NULL,'abc',1,'202CB962AC59075B964B07152D234B70',NULL,NULL,NULL,NULL,NULL,'0','A1'),(2,'2014-12-09 17:34:10',0,1,0,'2014-12-09 17:34:10','A2',NULL,'000000000000000',1,'C4CA4238A0B923820DCC509A6F75849B',NULL,NULL,NULL,NULL,NULL,'0','A2'),(3,'2014-12-09 17:35:34',0,1,0,'2014-12-09 17:35:34','A3',NULL,'000000000000000',1,'C4CA4238A0B923820DCC509A6F75849B',NULL,NULL,NULL,NULL,NULL,'0','A3'),(4,'2014-12-09 17:41:01',0,1,0,'2014-12-09 17:41:01','A4',NULL,'99000536188467',1,'C4CA4238A0B923820DCC509A6F75849B',NULL,NULL,NULL,NULL,NULL,'0','A4'),(5,'2014-12-09 17:42:41',0,0,0,NULL,'大兀',NULL,'99000536188467',1,'C71F370FEB6C500F3D752357BAECDF1E',1,1,1,1,1,'1','A5'),(6,'2014-12-09 17:45:37',0,1,0,'2014-12-09 17:45:37','操作员888',NULL,'99000536188467',1,'C4CA4238A0B923820DCC509A6F75849B',1,1,1,1,1,'2','A6'),(7,'2014-12-10 09:22:01',0,0,0,'2014-12-10 09:22:01','test',NULL,'000000000000000',1,'C4CA4238A0B923820DCC509A6F75849B',1,NULL,1,1,1,'2','A7'),(8,'2014-12-10 09:58:26',0,0,0,'2014-12-10 09:58:26','A8',NULL,'99000536188467',1,'C4CA4238A0B923820DCC509A6F75849B',NULL,NULL,NULL,NULL,NULL,'0','A8'),(9,'2014-12-10 10:40:41',0,1,0,'2014-12-10 10:40:41','A9',NULL,'abc',1,'202CB962AC59075B964B07152D234B70',NULL,NULL,NULL,NULL,NULL,'0','A9'),(10,'2014-12-10 10:52:05',0,1,0,'2014-12-10 10:52:05','A10',NULL,'abc',1,'202CB962AC59075B964B07152D234B70',NULL,NULL,NULL,NULL,NULL,'0','A10'),(11,'2014-12-10 11:28:33',0,1,0,'2014-12-10 11:28:33','A11',NULL,'abc',1,'202CB962AC59075B964B07152D234B70',NULL,NULL,NULL,NULL,NULL,'0','A11'),(12,'2014-12-10 11:35:11',0,1,0,'2014-12-10 11:35:11','A12',NULL,'abc',1,'202CB962AC59075B964B07152D234B70',NULL,NULL,NULL,NULL,NULL,'0','A12'),(13,'2014-12-10 12:21:26',0,1,0,'2014-12-10 12:21:26','test1',NULL,'000000000000000',1,'C4CA4238A0B923820DCC509A6F75849B',1,NULL,1,1,1,'2','A13'),(14,'2014-12-10 18:58:52',0,1,0,'2014-12-10 18:58:52','A14',NULL,'abc',1,'202CB962AC59075B964B07152D234B70',NULL,NULL,NULL,NULL,NULL,'0','A14'),(15,'2014-12-10 19:03:23',0,1,0,'2014-12-10 19:03:23','test2',NULL,'000000000000000',1,'C4CA4238A0B923820DCC509A6F75849B',1,NULL,1,1,1,'2','A15'),(16,'2014-12-10 19:16:33',0,0,0,'2014-12-10 19:16:33','A16',NULL,'000000000000000',1,'C4CA4238A0B923820DCC509A6F75849B',NULL,NULL,NULL,NULL,NULL,'0','A16'),(17,'2014-12-10 19:22:35',0,0,0,'2014-12-10 19:22:35','sjy',NULL,'867994002648836',1,'C4CA4238A0B923820DCC509A6F75849B',0,1,0,0,0,'3','A17');
/*!40000 ALTER TABLE `gym_members` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `gym_question_aswer`
--

DROP TABLE IF EXISTS `gym_question_aswer`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `gym_question_aswer` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `answer` varchar(255) DEFAULT NULL,
  `loginName` varchar(255) DEFAULT NULL,
  `question` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `id` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=52 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `gym_question_aswer`
--

LOCK TABLES `gym_question_aswer` WRITE;
/*!40000 ALTER TABLE `gym_question_aswer` DISABLE KEYS */;
INSERT INTO `gym_question_aswer` VALUES (1,'a1','A1','q1'),(2,'a2','A1','q2'),(3,'a3','A1','q3'),(4,'2','A2','出生在哪里'),(5,'3','A2','小学班主任名字'),(6,'4','A2','你父亲的名字'),(7,'2','A3','出生在哪里'),(8,'3','A3','小学班主任名字'),(9,'4','A3','你父亲的名字'),(10,'2','A4','出生在哪里'),(11,'2','A4','小学班主任名字'),(12,'3','A4','你父亲的名字'),(13,'1','大兀','出生在哪里'),(14,'2','大兀','小学班主任名字'),(15,'3','大兀','你父亲的名字'),(16,'1','操作员888','出生在哪里'),(17,'2','操作员888','小学班主任名字'),(18,'3','操作员888','你父亲的名字'),(19,'2','test','出生在哪里'),(20,'3','test','小学班主任名字'),(21,'4','test','你父亲的名字'),(22,'2','A8','出生在哪里'),(23,'3','A8','小学班主任名字'),(24,'4','A8','你父亲的名字'),(25,'a1','A9','q1'),(26,'a2','A9','q2'),(27,'a3','A9','q3'),(28,'a1','A10','q1'),(29,'a2','A10','q2'),(30,'a3','A10','q3'),(31,'a1','A11','q1'),(32,'a2','A11','q2'),(33,'a3','A11','q3'),(34,'a1','A12','q1'),(35,'a2','A12','q2'),(36,'a3','A12','q3'),(37,'2','test1','出生在哪里'),(38,'3','test1','小学班主任名字'),(39,'4','test1','你父亲的名字'),(40,'a1','A14','q1'),(41,'a2','A14','q2'),(42,'a3','A14','q3'),(43,'2','test2','出生在哪里'),(44,'3','test2','小学班主任名字'),(45,'4','test2','你父亲的名字'),(46,'2','A16','出生在哪里'),(47,'3','A16','小学班主任名字'),(48,'4','A16','你父亲的名字'),(49,'2','sjy','出生在哪里'),(50,'3','sjy','小学班主任名字'),(51,'4','sjy','你父亲的名字');
/*!40000 ALTER TABLE `gym_question_aswer` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `gym_questions`
--

DROP TABLE IF EXISTS `gym_questions`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `gym_questions` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `question` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `id` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `gym_questions`
--

LOCK TABLES `gym_questions` WRITE;
/*!40000 ALTER TABLE `gym_questions` DISABLE KEYS */;
/*!40000 ALTER TABLE `gym_questions` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `sys_dict`
--

DROP TABLE IF EXISTS `sys_dict`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `sys_dict` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `type_code` varchar(255) DEFAULT NULL,
  `dict_paixu` int(11) DEFAULT NULL,
  `dict_key` varchar(255) DEFAULT NULL,
  `dict_type` varchar(255) DEFAULT NULL,
  `dict_value` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `id` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `sys_dict`
--

LOCK TABLES `sys_dict` WRITE;
/*!40000 ALTER TABLE `sys_dict` DISABLE KEYS */;
INSERT INTO `sys_dict` VALUES (1,'userTypes',1,'0','用户类型','管理员'),(2,'userTypes',2,'1','用户类型','服务站');
/*!40000 ALTER TABLE `sys_dict` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `sys_log`
--

DROP TABLE IF EXISTS `sys_log`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `sys_log` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `descript` varchar(255) DEFAULT NULL,
  `ip` varchar(255) DEFAULT NULL,
  `status` bigint(20) NOT NULL,
  `updateDate` varchar(255) DEFAULT NULL,
  `uri` varchar(255) DEFAULT NULL,
  `userid` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `id` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `sys_log`
--

LOCK TABLES `sys_log` WRITE;
/*!40000 ALTER TABLE `sys_log` DISABLE KEYS */;
/*!40000 ALTER TABLE `sys_log` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `sys_menu`
--

DROP TABLE IF EXISTS `sys_menu`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `sys_menu` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `event` varchar(100) DEFAULT NULL,
  `title` varchar(50) DEFAULT NULL,
  `weight` int(11) DEFAULT NULL,
  `parentid` bigint(20) DEFAULT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `id` (`id`),
  KEY `FK74A447918560B047` (`parentid`),
  CONSTRAINT `FK74A447918560B047` FOREIGN KEY (`parentid`) REFERENCES `sys_menu` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=10 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `sys_menu`
--

LOCK TABLES `sys_menu` WRITE;
/*!40000 ALTER TABLE `sys_menu` DISABLE KEYS */;
INSERT INTO `sys_menu` VALUES (1,'','信息管理',4,NULL),(2,'','系统管理',3,NULL),(3,'showDictList()','字典管理',0,2),(4,'showUserList()','用户管理',0,2),(5,'showRoleList()','角色管理',0,2),(6,'showMenuList()','菜单管理',0,2),(7,'showLogList()','日志管理',0,2),(8,'queryVersion()','普通版本信息',1,1),(9,'queryAdminVersion()','管理员版本信息',1,1);
/*!40000 ALTER TABLE `sys_menu` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `sys_role`
--

DROP TABLE IF EXISTS `sys_role`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `sys_role` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `description` varchar(100) DEFAULT NULL,
  `name` varchar(100) DEFAULT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `id` (`id`),
  UNIQUE KEY `name` (`name`)
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `sys_role`
--

LOCK TABLES `sys_role` WRITE;
/*!40000 ALTER TABLE `sys_role` DISABLE KEYS */;
INSERT INTO `sys_role` VALUES (1,'管理员','admin'),(3,'apkUpload','apkUpload');
/*!40000 ALTER TABLE `sys_role` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `sys_role_menu`
--

DROP TABLE IF EXISTS `sys_role_menu`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `sys_role_menu` (
  `roleId` bigint(20) NOT NULL,
  `menuid` bigint(20) NOT NULL,
  KEY `FK65D4849682ED02A` (`roleId`),
  KEY `FK65D48496FF1863BC` (`menuid`),
  CONSTRAINT `FK65D4849682ED02A` FOREIGN KEY (`roleId`) REFERENCES `sys_role` (`id`),
  CONSTRAINT `FK65D48496FF1863BC` FOREIGN KEY (`menuid`) REFERENCES `sys_menu` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `sys_role_menu`
--

LOCK TABLES `sys_role_menu` WRITE;
/*!40000 ALTER TABLE `sys_role_menu` DISABLE KEYS */;
INSERT INTO `sys_role_menu` VALUES (1,2),(1,3),(1,4),(1,5),(1,6),(1,7),(1,1),(1,8),(1,9),(3,1),(3,8),(3,9);
/*!40000 ALTER TABLE `sys_role_menu` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `sys_user`
--

DROP TABLE IF EXISTS `sys_user`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `sys_user` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `email` varchar(255) DEFAULT NULL,
  `loginDate` varchar(255) DEFAULT NULL,
  `userid` varchar(100) DEFAULT NULL,
  `pwd` varchar(100) DEFAULT NULL,
  `realName` varchar(255) DEFAULT NULL,
  `regDate` varchar(255) DEFAULT NULL,
  `status` int(11) NOT NULL,
  `userType` smallint(6) DEFAULT NULL,
  `weight` int(11) NOT NULL,
  `roleid` bigint(20) DEFAULT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `id` (`id`),
  UNIQUE KEY `userid` (`userid`),
  KEY `FK74A81DFD82ED02A` (`roleid`),
  CONSTRAINT `FK74A81DFD82ED02A` FOREIGN KEY (`roleid`) REFERENCES `sys_role` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `sys_user`
--

LOCK TABLES `sys_user` WRITE;
/*!40000 ALTER TABLE `sys_user` DISABLE KEYS */;
INSERT INTO `sys_user` VALUES (1,'703350556@qq.com',NULL,'admin','47414176ba15b246','系统管理员','',0,1,0,1),(2,'test@test.cm',NULL,'banben','21407d8626fc7025','版本维护','2014-12-08 20:33:34',0,0,0,3);
/*!40000 ALTER TABLE `sys_user` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2014-12-13 13:42:50
