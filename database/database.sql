/*
SQLyog Community v13.1.1 (64 bit)
MySQL - 10.1.38-MariaDB : Database - dentistmedical
*********************************************************************
*/

/*!40101 SET NAMES utf8 */;

/*!40101 SET SQL_MODE=''*/;

/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;
CREATE DATABASE /*!32312 IF NOT EXISTS*/`dentistmedical` /*!40100 DEFAULT CHARACTER SET utf8 COLLATE utf8_unicode_ci */;

USE `dentistmedical`;

/*Table structure for table `access` */

DROP TABLE IF EXISTS `access`;

CREATE TABLE `access` (
  `id` int(10) NOT NULL AUTO_INCREMENT,
  `name` varchar(250) COLLATE utf8_unicode_ci DEFAULT NULL,
  `isPatient` int(1) DEFAULT '0',
  `isSchedule` int(1) DEFAULT '0',
  `isInspection` int(1) DEFAULT '0',
  `isTreatment` int(1) DEFAULT '0',
  `isAccess` int(1) DEFAULT '0',
  `isPayment` int(1) DEFAULT '0',
  `isReport` int(1) DEFAULT '0',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=5 DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;

/*Data for the table `access` */

insert  into `access`(`id`,`name`,`isPatient`,`isSchedule`,`isInspection`,`isTreatment`,`isAccess`,`isPayment`,`isReport`) values 
(1,'Эмч',0,0,0,0,0,0,0),
(2,'Админ',1,1,1,1,1,1,0),
(3,'Хүлээн авах',1,1,0,0,0,0,0),
(4,'Сувилагч',1,1,1,1,0,0,0);

/*Table structure for table `bill` */

DROP TABLE IF EXISTS `bill`;

CREATE TABLE `bill` (
  `all_price` int(11) NOT NULL,
  `bill_id` int(11) NOT NULL,
  `ddtd_id` int(11) NOT NULL,
  `lottery_id` int(11) NOT NULL,
  `noat` int(11) NOT NULL,
  `picture` varchar(200) NOT NULL,
  `ttd_id` int(11) NOT NULL,
  `inspection_id` int(11) NOT NULL,
  `bttd_id` int(11) DEFAULT NULL,
  `patient_type` varchar(50) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

/*Data for the table `bill` */

insert  into `bill`(`all_price`,`bill_id`,`ddtd_id`,`lottery_id`,`noat`,`picture`,`ttd_id`,`inspection_id`,`bttd_id`,`patient_type`) values 
(122000,1,427264,7863785,3000,'эөабө',797964,2,29482,'Байгууллага');

/*Table structure for table `inspection` */

DROP TABLE IF EXISTS `inspection`;

CREATE TABLE `inspection` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `p_id` int(11) NOT NULL,
  `t_id` int(11) NOT NULL,
  `date` datetime NOT NULL,
  `doctor_id` int(11) NOT NULL,
  `pain_now` varchar(500) DEFAULT NULL,
  `zoolon_ed` varchar(200) DEFAULT NULL,
  `lymph_gland_type` varchar(500) DEFAULT NULL,
  `lips` varchar(500) DEFAULT NULL,
  `tongue` varchar(500) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=17 DEFAULT CHARSET=utf8;

/*Data for the table `inspection` */

insert  into `inspection`(`id`,`p_id`,`t_id`,`date`,`doctor_id`,`pain_now`,`zoolon_ed`,`lymph_gland_type`,`lips`,`tongue`) values 
(14,123,1,'2019-06-01 00:00:00',1,'asdf',NULL,'asdf','asdf','asdf'),
(15,123,1,'2019-06-01 00:00:00',1,'hgjf',NULL,'fghj','fghj','fghj'),
(16,123,1,'2019-06-02 00:00:00',1,'nb',NULL,'bn','cvnb','cvnb');

/*Table structure for table `pain` */

DROP TABLE IF EXISTS `pain`;

CREATE TABLE `pain` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `p_name` varchar(100) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=17 DEFAULT CHARSET=utf8;

/*Data for the table `pain` */

insert  into `pain`(`id`,`p_name`) values 
(1,'Янгинаж'),
(2,'Халуун хүйтэн'),
(3,'Шөнө'),
(4,'Лугшиж'),
(5,'Аяндаа'),
(6,'Үе үе'),
(7,'Хазахад'),
(8,'Шүд цоорсон'),
(9,'Гоо сайхны зовиуртай'),
(10,'Урьдчилан сэргийлэх үзлэгт хамрагдах'),
(11,'Амнаас эвгүй үнэр гардаг'),
(12,'Алдагдсан'),
(13,'Хэвийн'),
(14,'Тийм'),
(15,'Тийм'),
(16,'Тийм');

/*Table structure for table `pain_inspection` */

DROP TABLE IF EXISTS `pain_inspection`;

CREATE TABLE `pain_inspection` (
  `inspection_id` int(11) NOT NULL,
  `pain_id` int(11) NOT NULL,
  `type` varchar(1) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

/*Data for the table `pain_inspection` */

insert  into `pain_inspection`(`inspection_id`,`pain_id`,`type`) values 
(14,1,'1'),
(14,6,'1'),
(14,10,'1'),
(14,14,'1'),
(14,11,'1'),
(14,4,'1'),
(14,11,'1'),
(14,8,'1'),
(14,10,'1'),
(14,14,'1'),
(16,1,'1'),
(16,5,'1'),
(16,10,'1'),
(16,14,'1');

/*Table structure for table `patient` */

DROP TABLE IF EXISTS `patient`;

CREATE TABLE `patient` (
  `id` int(20) NOT NULL AUTO_INCREMENT,
  `RegisterNo` varchar(20) CHARACTER SET utf8 COLLATE utf8_unicode_ci DEFAULT NULL,
  `Lname` varchar(100) CHARACTER SET utf8 COLLATE utf8_unicode_ci DEFAULT NULL,
  `Fname` varchar(100) CHARACTER SET utf8 COLLATE utf8_unicode_ci DEFAULT NULL,
  `Gender` varchar(1) CHARACTER SET utf8 COLLATE utf8_unicode_ci DEFAULT NULL,
  `BirthDate` date DEFAULT NULL,
  `Address` varchar(250) CHARACTER SET utf8 COLLATE utf8_unicode_ci DEFAULT NULL,
  `Pnumber` int(10) DEFAULT NULL,
  `Email` varchar(30) CHARACTER SET utf8 COLLATE utf8_unicode_ci DEFAULT NULL,
  `comment` varchar(250) CHARACTER SET utf8 COLLATE utf8_unicode_ci DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8;

/*Data for the table `patient` */

insert  into `patient`(`id`,`RegisterNo`,`Lname`,`Fname`,`Gender`,`BirthDate`,`Address`,`Pnumber`,`Email`,`comment`) values 
(2,'8','йыбө','йыбө','m','2019-06-01','????',454,'????','????????'),
(123,'asdf','Батгэрэлт','йыб','m','2019-06-01','??? ???? ???? ???? ',5456465,'sdfsdfsdf',NULL);

/*Table structure for table `patient_treatment` */

DROP TABLE IF EXISTS `patient_treatment`;

CREATE TABLE `patient_treatment` (
  `patient_id` int(11) NOT NULL,
  `treatment_id` int(11) NOT NULL,
  `type` varchar(1) NOT NULL,
  `expirydate` date NOT NULL,
  `teeth` varchar(100) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

/*Data for the table `patient_treatment` */

insert  into `patient_treatment`(`patient_id`,`treatment_id`,`type`,`expirydate`,`teeth`) values 
(1,3,'a','2019-05-02','hoid');

/*Table structure for table `schedule` */

DROP TABLE IF EXISTS `schedule`;

CREATE TABLE `schedule` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `date` date NOT NULL,
  `p_id` int(11) NOT NULL,
  `doctor_id` int(11) NOT NULL,
  `comment` text NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8;

/*Data for the table `schedule` */

insert  into `schedule`(`id`,`date`,`p_id`,`doctor_id`,`comment`) values 
(1,'2019-06-02',123,1,'sdfsdfsdf'),
(2,'2019-06-01',123,1,'');

/*Table structure for table `test` */

DROP TABLE IF EXISTS `test`;

CREATE TABLE `test` (
  `id` int(10) NOT NULL AUTO_INCREMENT,
  `name` varchar(1000) COLLATE utf8_unicode_ci DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;

/*Data for the table `test` */

insert  into `test`(`id`,`name`) values 
(1,'йыбө');

/*Table structure for table `treatment` */

DROP TABLE IF EXISTS `treatment`;

CREATE TABLE `treatment` (
  `id` int(11) NOT NULL,
  `t_name` varchar(500) NOT NULL,
  `unit` varchar(100) DEFAULT NULL,
  `price` int(11) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

/*Data for the table `treatment` */

insert  into `treatment`(`id`,`t_name`,`unit`,`price`) values 
(3,'Saforide','SH',2500),
(27,'Агт араа шүд авах',NULL,40000),
(39,'Ribbond авагддаггүй шүдэлбэр(1шүд)','',350),
(49,'U Veneer (1шүд)','SH',150000),
(80,'Botox','SH',100000);

/*Table structure for table `user` */

DROP TABLE IF EXISTS `user`;

CREATE TABLE `user` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `lname` varchar(50) NOT NULL,
  `fname` varchar(50) NOT NULL,
  `dob` date NOT NULL,
  `position` int(20) DEFAULT NULL,
  `email` varchar(50) NOT NULL,
  `password` varchar(50) DEFAULT NULL,
  `address` varchar(100) NOT NULL,
  `phonenumber` int(11) NOT NULL,
  `gender` varchar(1) NOT NULL,
  `register_no` varchar(10) NOT NULL,
  PRIMARY KEY (`id`),
  KEY `position` (`position`)
) ENGINE=MyISAM AUTO_INCREMENT=6 DEFAULT CHARSET=latin1;

/*Data for the table `user` */

insert  into `user`(`id`,`lname`,`fname`,`dob`,`position`,`email`,`password`,`address`,`phonenumber`,`gender`,`register_no`) values 
(1,'Battur','boldoo','1997-05-06',2,'123','asdf','asdassfafhfjdfetgdfxvcx',9934744,'m','ta98011719'),
(3,'Nomin','Nomin','2019-05-27',1,'Nomin','asdf','',0,'f',''),
(4,'ASD','ASF','2019-06-01',2,'asdf','asdf','ASDF',234234,'m','ASDF'),
(5,'????','????','2019-06-01',2,'1234',NULL,'????',99394774,'m','24234234');

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;
