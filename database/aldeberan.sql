-- --------------------------------------------------------
-- Host:                         us-cdbr-east-04.cleardb.com
-- Server version:               5.6.50-log - MySQL Community Server (GPL)
-- Server OS:                    Linux
-- HeidiSQL Version:             11.2.0.6213
-- --------------------------------------------------------

/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET NAMES utf8 */;
/*!50503 SET NAMES utf8mb4 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;


-- Dumping database structure for heroku_340a0634a49f183
CREATE DATABASE IF NOT EXISTS `heroku_340a0634a49f183` /*!40100 DEFAULT CHARACTER SET utf8 */;
USE `heroku_340a0634a49f183`;

-- Dumping structure for table heroku_340a0634a49f183.address_user
CREATE TABLE IF NOT EXISTS `address_user` (
  `address_id` int(11) NOT NULL AUTO_INCREMENT,
  `user_id` varchar(35) NOT NULL,
  `address_recipient` varchar(100) NOT NULL,
  `address_contact` varchar(20) NOT NULL,
  `address_line1` varchar(500) NOT NULL,
  `address_line2` varchar(500) NOT NULL,
  `address_code` varchar(5) NOT NULL,
  `address_city` varchar(50) NOT NULL,
  `address_state` varchar(50) NOT NULL,
  `address_country` varchar(50) NOT NULL,
  `is_default` int(1) NOT NULL,
  PRIMARY KEY (`address_id`)
) ENGINE=InnoDB AUTO_INCREMENT=145 DEFAULT CHARSET=utf8;

-- Dumping data for table heroku_340a0634a49f183.address_user: ~9 rows (approximately)
/*!40000 ALTER TABLE `address_user` DISABLE KEYS */;
INSERT INTO `address_user` (`address_id`, `user_id`, `address_recipient`, `address_contact`, `address_line1`, `address_line2`, `address_code`, `address_city`, `address_state`, `address_country`, `is_default`) VALUES
	(15, 'S5DepbW8fmNPM8RpJyM68XTTHJ72', 'SC ong', '182992362', 'Dpulze ', 'PERSIARAN MULTIMEDIA', '63100', 'Cyberjaya', 'Selangor', 'Malaysia', 0),
	(25, '5dJ3jFfUgIYFkX7jUcRZ8s54DoB3', 'Wilson Ong Wen Hao', '163963254', 'UiTM Kampus Dengkil', 'Universiti Teknologi MARA Kampus Dengkil', '43800', 'Dengkil', 'Selangor', 'Malaysia', 1),
	(35, '5dJ3jFfUgIYFkX7jUcRZ8s54DoB3', 'Wilson Ong Wen Hao', '163963254', 'NO 8, Jln Perak, Pulau Tikus,', '', '10350', 'George Town', 'Pulau Pinang', 'Malaysia', 0),
	(45, 'S5DepbW8fmNPM8RpJyM68XTTHJ72', 'yeet', '2136646944', 'dpulze', 'PERSIARAN MULTIMEDIA', '63100', 'cyberjaya', 'Selangor', 'Malaysia', 0),
	(65, 'fShPQcG09Sf5Vay2u4ir3qzk1B22', 'Wilson ming', '12345678', 'Taman Universiti Indah ', '', '43300', 'Seri Kembangan ', 'Selangor', 'Malaysia ', 1),
	(105, '5dJ3jFfUgIYFkX7jUcRZ8s54DoB3', 'yabai', '163963254', '13A-G, Jalan Kajang Perdana 8', 'Taman Kajang Perdana', '43000', 'Kajang', 'Selangor', 'Malaysia', 0),
	(115, '5dJ3jFfUgIYFkX7jUcRZ8s54DoB3', 'asdas', '163963254', '13A-G, Jalan Kajang Perdana 8', 'Taman Kajang Perdana', '43000', 'Kajang', 'Selangor', 'Malaysia', 0),
	(125, 'XHXDzxi0ZMM4I8dEwLYoTNIGkb93', 'halo', '12849667', 'dpulze', 'PERSIARAN multimedia', '63100', 'cyberjaya', 'Selangor', 'Malaysia', 0),
	(135, 'tWc6280GWjhXAPF7EWPWMARwJ1r2', 'RTX 3090', '163963254', '13A-G, Jalan Kajang Perdana 8', 'Taman Kajang Perdana', '43000', 'Kajang', 'Selangor', 'Malaysia', 1);
/*!40000 ALTER TABLE `address_user` ENABLE KEYS */;

-- Dumping structure for table heroku_340a0634a49f183.orders
CREATE TABLE IF NOT EXISTS `orders` (
  `order_id` int(11) NOT NULL AUTO_INCREMENT,
  `user_id` varchar(35) NOT NULL,
  `order_reference` varchar(20) NOT NULL,
  `order_date` datetime NOT NULL,
  `order_total` decimal(7,2) NOT NULL,
  `order_status` varchar(20) NOT NULL,
  PRIMARY KEY (`order_id`)
) ENGINE=InnoDB AUTO_INCREMENT=525 DEFAULT CHARSET=utf8;

-- Dumping data for table heroku_340a0634a49f183.orders: ~49 rows (approximately)
/*!40000 ALTER TABLE `orders` DISABLE KEYS */;
INSERT INTO `orders` (`order_id`, `user_id`, `order_reference`, `order_date`, `order_total`, `order_status`) VALUES
	(5, '0TbifvLstjWRpIpzDpBaZQRvXrA2', 'AE_QYITR2B73UYY1LK', '2021-10-26 17:22:44', 7.20, 'delivered'),
	(15, '0TbifvLstjWRpIpzDpBaZQRvXrA2', 'AE_RVCXVP81SV7WD7X', '2021-10-26 17:26:36', 4.00, 'delivered'),
	(25, '0TbifvLstjWRpIpzDpBaZQRvXrA2', 'AE_QW2T09K80IWELOJ', '2021-10-26 17:33:35', 4.00, 'delivered'),
	(35, '0TbifvLstjWRpIpzDpBaZQRvXrA2', 'AE_49RJNTEX08XG6JZ', '2021-10-26 17:37:59', 4.00, 'delivered'),
	(45, '0TbifvLstjWRpIpzDpBaZQRvXrA2', 'AE_5M2AD4Z2J1H4IOA', '2021-10-27 10:20:25', 2.00, 'delivered'),
	(55, 'XHXDzxi0ZMM4I8dEwLYoTNIGkb93', 'AE_NNBOBG7BTZWH6YF', '2021-10-28 12:14:35', 2.00, 'shipping'),
	(65, 'XHXDzxi0ZMM4I8dEwLYoTNIGkb93', 'AE_5A1ZO22BEZDSTTP', '2021-10-28 12:17:16', 2.00, 'shipping'),
	(75, '0TbifvLstjWRpIpzDpBaZQRvXrA2', 'AE_I5HS5X8KVMC54JI', '2021-10-28 13:45:55', 7.00, 'delivered'),
	(85, '0TbifvLstjWRpIpzDpBaZQRvXrA2', 'AE_AUZSPJTRCTQK86Y', '2021-10-28 15:06:53', 3.60, 'delivered'),
	(95, '0TbifvLstjWRpIpzDpBaZQRvXrA2', 'AE_26KRA95R9Y1FBGA', '2021-10-28 15:49:16', 7.20, 'delivered'),
	(105, '0TbifvLstjWRpIpzDpBaZQRvXrA2', 'AE_2PI378REWR8CYX4', '2021-10-28 15:56:17', 2.00, 'delivered'),
	(115, '0TbifvLstjWRpIpzDpBaZQRvXrA2', 'AE_9G5IR245HILYRSD', '2021-10-28 15:57:56', 2.00, 'delivered'),
	(125, '0TbifvLstjWRpIpzDpBaZQRvXrA2', 'AE_GH90QVY4W9PW0HU', '2021-10-28 15:59:58', 5.20, 'delivered'),
	(165, '5dJ3jFfUgIYFkX7jUcRZ8s54DoB3', 'AE_WLCQBV96H4OSAY0', '2021-10-28 17:13:36', 8.60, 'delivered'),
	(175, '5dJ3jFfUgIYFkX7jUcRZ8s54DoB3', 'AE_324LLIHYWC80DEN', '2021-10-28 17:17:36', 5.80, 'delivered'),
	(185, '5dJ3jFfUgIYFkX7jUcRZ8s54DoB3', 'AE_9AIMLSLJ5D7VW31', '2021-10-28 18:08:59', 9.00, 'delivered'),
	(195, '5dJ3jFfUgIYFkX7jUcRZ8s54DoB3', 'AE_VBF1MC07KE9C2B8', '2021-10-28 18:29:06', 3.60, 'delivered'),
	(205, '5dJ3jFfUgIYFkX7jUcRZ8s54DoB3', 'AE_RMEUUET7ZP2HMVV', '2021-10-28 19:01:07', 233.10, 'delivered'),
	(215, '5dJ3jFfUgIYFkX7jUcRZ8s54DoB3', 'AE_S16HV1ITQADWTYY', '2021-10-28 19:16:41', 7.20, 'shipping'),
	(225, 'fShPQcG09Sf5Vay2u4ir3qzk1B22', 'AE_Q7QLBZD41M4QTLK', '2021-10-30 16:33:16', 41.30, 'delivered'),
	(235, '5dJ3jFfUgIYFkX7jUcRZ8s54DoB3', 'AE_R04SL8X4A1E3S0P', '2021-10-30 18:25:43', 74.30, 'shipping'),
	(245, '5dJ3jFfUgIYFkX7jUcRZ8s54DoB3', 'AE_ZBU5EJCLPEZDYKW', '2021-10-30 18:27:32', 5.20, 'shipping'),
	(255, 'XHXDzxi0ZMM4I8dEwLYoTNIGkb93', 'AE_V18BHDC4I11HS9G', '2021-10-30 18:42:21', 43.60, 'delivered'),
	(265, '5dJ3jFfUgIYFkX7jUcRZ8s54DoB3', 'AE_S93Z354QA7WSYIJ', '2021-11-01 03:02:04', 12.20, 'delivered'),
	(275, '5dJ3jFfUgIYFkX7jUcRZ8s54DoB3', 'AE_G0Y0IQ0P5HQ1PZB', '2021-11-01 03:10:04', 7.00, 'shipping'),
	(285, '5dJ3jFfUgIYFkX7jUcRZ8s54DoB3', 'AE_GQT5R40P0AMGE18', '2021-11-01 03:38:47', 9.00, 'shipping'),
	(295, '5dJ3jFfUgIYFkX7jUcRZ8s54DoB3', 'AE_DDM1L97XNQH9GQK', '2021-11-01 03:48:31', 2.00, 'shipping'),
	(305, '5dJ3jFfUgIYFkX7jUcRZ8s54DoB3', 'AE_CT6OWOGEF9ABPAK', '2021-11-03 10:38:57', 7.00, 'delivered'),
	(315, '5dJ3jFfUgIYFkX7jUcRZ8s54DoB3', 'AE_IEJGNLT6CSPJ98A', '2021-11-03 11:00:19', 7.00, 'delivered'),
	(325, '5dJ3jFfUgIYFkX7jUcRZ8s54DoB3', 'AE_YD6WSEDQ95JLPMB', '2021-11-03 11:14:10', 3.60, 'delivered'),
	(335, '5dJ3jFfUgIYFkX7jUcRZ8s54DoB3', 'AE_LA805ZSAH5EWG9I', '2021-11-03 11:19:33', 5.20, 'delivered'),
	(345, '5dJ3jFfUgIYFkX7jUcRZ8s54DoB3', 'AE_T43JCKAE4WEQ9QT', '2021-11-03 11:33:55', 5.20, 'delivered'),
	(355, 'XHXDzxi0ZMM4I8dEwLYoTNIGkb93', 'AE_6SLMC8MLYSMK7TZ', '2021-11-03 03:55:13', 7.20, 'delivered'),
	(365, 'XHXDzxi0ZMM4I8dEwLYoTNIGkb93', 'AE_5VFAV90BPBVTRL3', '2021-11-03 03:58:18', 2.00, 'shipping'),
	(375, 'XHXDzxi0ZMM4I8dEwLYoTNIGkb93', 'AE_AUP39XF57EHW2QE', '2021-11-03 04:00:03', 2.00, 'delivered'),
	(385, 'XHXDzxi0ZMM4I8dEwLYoTNIGkb93', 'AE_O72D4WF4CTY2WDH', '2021-11-03 04:02:08', 2.00, 'delivered'),
	(395, 'XHXDzxi0ZMM4I8dEwLYoTNIGkb93', 'AE_MJ7W8YQ69DUAFYU', '2021-11-03 04:02:55', 2.00, 'shipping'),
	(405, 'XHXDzxi0ZMM4I8dEwLYoTNIGkb93', 'AE_T0ZLUE8A6LEU8W9', '2021-11-03 04:05:53', 2.00, 'delivered'),
	(415, 'XHXDzxi0ZMM4I8dEwLYoTNIGkb93', 'AE_ATMEPBFWRHBEFCY', '2021-11-03 04:10:17', 2.00, 'delivered'),
	(425, 'XHXDzxi0ZMM4I8dEwLYoTNIGkb93', 'AE_HKL6UYTNRH7IM1L', '2021-11-03 04:12:15', 2.00, 'delivered'),
	(435, 'XHXDzxi0ZMM4I8dEwLYoTNIGkb93', 'AE_L6P95LWXZ3YW5Y3', '2021-11-03 04:13:00', 2.00, 'delivered'),
	(445, 'XHXDzxi0ZMM4I8dEwLYoTNIGkb93', 'AE_VUR61R5Z6F288WV', '2021-11-03 04:17:18', 2.00, 'shipping'),
	(455, 'XHXDzxi0ZMM4I8dEwLYoTNIGkb93', 'AE_1Y1LZI3S4EHMD3Z', '2021-11-03 04:19:44', 2.00, 'delivered'),
	(465, '5dJ3jFfUgIYFkX7jUcRZ8s54DoB3', 'AE_7X7HTXEKDTSPNWK', '2021-11-03 12:24:35', 2.00, 'delivered'),
	(475, 'XHXDzxi0ZMM4I8dEwLYoTNIGkb93', 'AE_XUNFYP94HAC72IN', '2021-11-03 04:26:31', 2.00, 'delivered'),
	(485, 'XHXDzxi0ZMM4I8dEwLYoTNIGkb93', 'AE_LOZ03SJ3GME4AJN', '2021-11-03 04:32:58', 2.00, 'shipping'),
	(495, 'tWc6280GWjhXAPF7EWPWMARwJ1r2', 'AE_IHYXBKHGICYY4OI', '2021-11-03 16:46:33', 2.00, 'delivered'),
	(505, 'tWc6280GWjhXAPF7EWPWMARwJ1r2', 'AE_FWPNW9BPLWD1230', '2021-11-03 16:57:22', 9.20, 'shipping'),
	(515, 'tWc6280GWjhXAPF7EWPWMARwJ1r2', 'AE_TIAOM5UAJPRIYM7', '2021-11-03 17:00:16', 7.20, 'shipping');
/*!40000 ALTER TABLE `orders` ENABLE KEYS */;

-- Dumping structure for table heroku_340a0634a49f183.order_address
CREATE TABLE IF NOT EXISTS `order_address` (
  `order_address_id` int(11) NOT NULL AUTO_INCREMENT,
  `order_id` int(11) NOT NULL,
  `address_recipient` varchar(100) NOT NULL,
  `address_contact` varchar(20) NOT NULL,
  `address_line1` varchar(500) NOT NULL,
  `address_line2` varchar(500) NOT NULL,
  `address_code` varchar(5) NOT NULL,
  `address_city` varchar(50) NOT NULL,
  `address_state` varchar(50) NOT NULL,
  `address_country` varchar(50) NOT NULL,
  PRIMARY KEY (`order_address_id`)
) ENGINE=InnoDB AUTO_INCREMENT=495 DEFAULT CHARSET=utf8;

-- Dumping data for table heroku_340a0634a49f183.order_address: ~49 rows (approximately)
/*!40000 ALTER TABLE `order_address` DISABLE KEYS */;
INSERT INTO `order_address` (`order_address_id`, `order_id`, `address_recipient`, `address_contact`, `address_line1`, `address_line2`, `address_code`, `address_city`, `address_state`, `address_country`) VALUES
	(5, 5, 'Wilson Leong Kah Ming', '129032232', 'UITM Dengkil', '', '43800', 'Dengkil', 'Selangor', 'Malaysia'),
	(15, 15, 'Kelvilson', '182040302', 'Blok C1, Cyberia Smarthomes', 'Jalan Cyberia 1, Cyber 11', '63000', 'Cyberjaya', 'Selangor', 'Malaysia'),
	(25, 25, 'Kelvilson', '182040302', 'Blok C1, Cyberia Smarthomes', 'Jalan Cyberia 1, Cyber 11', '63000', 'Cyberjaya', 'Selangor', 'Malaysia'),
	(35, 35, 'Kelvilson', '182040302', 'Blok C1, Cyberia Smarthomes', 'Jalan Cyberia 1, Cyber 11', '63000', 'Cyberjaya', 'Selangor', 'Malaysia'),
	(45, 45, 'Kelvilson', '182040302', 'Blok C1, Cyberia Smarthomes', 'Jalan Cyberia 1, Cyber 11', '63000', 'Cyberjaya', 'Selangor', 'Malaysia'),
	(55, 55, 'Wilson Leong Kah Ming', '123456789', 'UITM Kampus Dengkil', '', '43800', 'Dengkil', 'Selangor', 'Malaysia'),
	(65, 65, 'Wilson Leong Kah Ming', '123456789', 'UITM Kampus Dengkil', '', '43800', 'Dengkil', 'Selangor', 'Malaysia'),
	(75, 75, 'Kelvilson Penang', '182040302', 'NO 8, Jln Perak, Pulau Tikus,', '', '10350', 'George Town', 'Pulau Pinang', 'Malaysia'),
	(85, 85, 'Kelvilson', '182040302', 'Blok C1, Cyberia Smarthomes', 'Jalan Cyberia 1, Cyber 11', '63000', 'Cyberjaya', 'Selangor', 'Malaysia'),
	(95, 95, 'Kelvilson', '182040302', 'Blok C1, Cyberia Smarthomes', 'Jalan Cyberia 1, Cyber 11', '63000', 'Cyberjaya', 'Selangor', 'Malaysia'),
	(105, 105, 'Kelvilson', '182040302', 'Blok C1, Cyberia Smarthomes', 'Jalan Cyberia 1, Cyber 11', '63000', 'Cyberjaya', 'Selangor', 'Malaysia'),
	(115, 115, 'Kelvilson', '182040302', 'Blok C1, Cyberia Smarthomes', 'Jalan Cyberia 1, Cyber 11', '63000', 'Cyberjaya', 'Selangor', 'Malaysia'),
	(125, 125, 'Kelvilson', '182040302', 'Blok C1, Cyberia Smarthomes', 'Jalan Cyberia 1, Cyber 11', '63000', 'Cyberjaya', 'Selangor', 'Malaysia'),
	(135, 155, 'Wilson Ong Wen Hao', '163963254', 'UiTM Kampus Dengkil', 'Universiti Teknologi MARA Kampus Dengkil', '43800', 'Dengkil', 'Selangor', 'Malaysia'),
	(145, 165, 'Wilson Ong Wen Hao', '163963254', 'NO 8, Jln Perak, Pulau Tikus,', '', '10350', 'George Town', 'Pulau Pinang', 'Malaysia'),
	(155, 175, 'Wilson Ong Wen Hao', '163963254', 'UiTM Kampus Dengkil', 'Universiti Teknologi MARA Kampus Dengkil', '43800', 'Dengkil', 'Selangor', 'Malaysia'),
	(165, 185, 'Wilson Ong Wen Hao', '163963254', 'NO 8, Jln Perak, Pulau Tikus,', '', '10350', 'George Town', 'Pulau Pinang', 'Malaysia'),
	(175, 195, 'LMAO', '123', 'Dpulze', 'Persiaran Multimedia', '63100', 'Cyberjaya', 'Selangor', 'Malaysia'),
	(185, 205, 'LMAO', '123', 'Dpulze', 'Persiaran Multimedia', '63100', 'Cyberjaya', 'Selangor', 'Malaysia'),
	(195, 215, 'Wilson Ong Wen Hao', '163963254', 'UiTM Kampus Dengkil', 'Universiti Teknologi MARA Kampus Dengkil', '43800', 'Dengkil', 'Selangor', 'Malaysia'),
	(205, 225, 'Wilson Leong Kah Ming', '129032232', 'UiTM Kampus Dengkil', 'Universiti Teknologi MARA Kampus Dengkil', '43800', 'Dengkil', 'Selangor', 'Malaysia'),
	(215, 235, 'Wilson Ong Wen Hao', '163963254', 'UiTM Kampus Dengkil', 'Universiti Teknologi MARA Kampus Dengkil', '43800', 'Dengkil', 'Selangor', 'Malaysia'),
	(225, 255, 'Wilson Ong Wen Hao', '163963254', 'UiTM Kampus Dengkil', 'Universiti Teknologi MARA Kampus Dengkil', '43800', 'Dengkil', 'Selangor', 'Malaysia'),
	(235, 265, 'Wilson Ong Wen Hao', '163963254', 'NO 8, Jln Perak, Pulau Tikus,', '', '10350', 'George Town', 'Pulau Pinang', 'Malaysia'),
	(245, 275, 'Wilson Ong Wen Hao', '163963254', 'NO 8, Jln Perak, Pulau Tikus,', '', '10350', 'George Town', 'Pulau Pinang', 'Malaysia'),
	(255, 285, 'Wilson Ong Wen Hao', '163963254', 'NO 8, Jln Perak, Pulau Tikus,', '', '10350', 'George Town', 'Pulau Pinang', 'Malaysia'),
	(265, 295, 'Wilson Ong Wen Hao', '163963254', 'UiTM Kampus Dengkil', 'Universiti Teknologi MARA Kampus Dengkil', '43800', 'Dengkil', 'Selangor', 'Malaysia'),
	(275, 305, 'Wilson Ong Wen Hao', '163963254', 'NO 8, Jln Perak, Pulau Tikus,', '', '10350', 'George Town', 'Pulau Pinang', 'Malaysia'),
	(285, 315, 'Wilson Ong Wen Hao', '163963254', 'NO 8, Jln Perak, Pulau Tikus,', '', '10350', 'George Town', 'Pulau Pinang', 'Malaysia'),
	(295, 325, 'Wilson Ong Wen Hao', '163963254', 'UiTM Kampus Dengkil', 'Universiti Teknologi MARA Kampus Dengkil', '43800', 'Dengkil', 'Selangor', 'Malaysia'),
	(305, 335, 'Wilson Ong Wen Hao', '163963254', 'UiTM Kampus Dengkil', 'Universiti Teknologi MARA Kampus Dengkil', '43800', 'Dengkil', 'Selangor', 'Malaysia'),
	(315, 345, 'Wilson Ong Wen Hao', '163963254', 'UiTM Kampus Dengkil', 'Universiti Teknologi MARA Kampus Dengkil', '43800', 'Dengkil', 'Selangor', 'Malaysia'),
	(325, 355, 'SC ONG', '182992362', 'MMU, PERSIARAN MULTIMEDIA', '', '63100', 'Cyberjaya', 'Selangor', 'Malaysia'),
	(335, 365, 'SC ONG', '182992362', 'MMU, PERSIARAN MULTIMEDIA', '', '63100', 'Cyberjaya', 'Selangor', 'Malaysia'),
	(345, 375, 'SC ONG', '182992362', 'MMU, PERSIARAN MULTIMEDIA', '', '63100', 'Cyberjaya', 'Selangor', 'Malaysia'),
	(355, 385, 'SC ONG', '182992362', 'MMU, PERSIARAN MULTIMEDIA', '', '63100', 'Cyberjaya', 'Selangor', 'Malaysia'),
	(365, 395, 'SC ONG', '182992362', 'MMU, PERSIARAN MULTIMEDIA', '', '63100', 'Cyberjaya', 'Selangor', 'Malaysia'),
	(375, 405, 'SC ONG', '182992362', 'MMU, PERSIARAN MULTIMEDIA', '', '63100', 'Cyberjaya', 'Selangor', 'Malaysia'),
	(385, 415, 'SC ONG', '182992362', 'MMU, PERSIARAN MULTIMEDIA', '', '63100', 'Cyberjaya', 'Selangor', 'Malaysia'),
	(395, 425, 'SC ONG', '182992362', 'MMU, PERSIARAN MULTIMEDIA', '', '63100', 'Cyberjaya', 'Selangor', 'Malaysia'),
	(405, 435, 'SC ONG', '182992362', 'MMU, PERSIARAN MULTIMEDIA', '', '63100', 'Cyberjaya', 'Selangor', 'Malaysia'),
	(415, 445, 'SC ONG', '182992362', 'MMU, PERSIARAN MULTIMEDIA', '', '63100', 'Cyberjaya', 'Selangor', 'Malaysia'),
	(425, 455, 'SC ONG', '182992362', 'MMU, PERSIARAN MULTIMEDIA', '', '63100', 'Cyberjaya', 'Selangor', 'Malaysia'),
	(435, 465, 'Wilson Ong Wen Hao', '163963254', 'UiTM Kampus Dengkil', 'Universiti Teknologi MARA Kampus Dengkil', '43800', 'Dengkil', 'Selangor', 'Malaysia'),
	(445, 475, 'SC ONG', '182992362', 'MMU, PERSIARAN MULTIMEDIA', '', '63100', 'Cyberjaya', 'Selangor', 'Malaysia'),
	(455, 485, 'SC ONG', '182992362', 'MMU, PERSIARAN MULTIMEDIA', '', '63100', 'Cyberjaya', 'Selangor', 'Malaysia'),
	(465, 495, 'RTX 3090', '163963254', '13A-G, Jalan Kajang Perdana 8', 'Taman Kajang Perdana', '43000', 'Kajang', 'Selangor', 'Malaysia'),
	(475, 505, 'RTX 3090', '163963254', '13A-G, Jalan Kajang Perdana 8', 'Taman Kajang Perdana', '43000', 'Kajang', 'Selangor', 'Malaysia'),
	(485, 515, 'RTX 3090', '163963254', '13A-G, Jalan Kajang Perdana 8', 'Taman Kajang Perdana', '43000', 'Kajang', 'Selangor', 'Malaysia');
/*!40000 ALTER TABLE `order_address` ENABLE KEYS */;

-- Dumping structure for table heroku_340a0634a49f183.order_item
CREATE TABLE IF NOT EXISTS `order_item` (
  `order_item_id` int(11) NOT NULL AUTO_INCREMENT,
  `order_id` int(11) NOT NULL,
  `product_name` mediumtext NOT NULL,
  `product_SKU` mediumtext NOT NULL,
  `product_quantity` int(11) NOT NULL,
  `product_price` decimal(7,2) NOT NULL,
  `product_img` mediumtext NOT NULL,
  PRIMARY KEY (`order_item_id`)
) ENGINE=InnoDB AUTO_INCREMENT=1005 DEFAULT CHARSET=utf8;

-- Dumping data for table heroku_340a0634a49f183.order_item: ~99 rows (approximately)
/*!40000 ALTER TABLE `order_item` DISABLE KEYS */;
INSERT INTO `order_item` (`order_item_id`, `order_id`, `product_name`, `product_SKU`, `product_quantity`, `product_price`, `product_img`) VALUES
	(5, 5, 'FOCACCIA LOAF', 'focaccia_loaf', 1, 5.20, 'https://firebasestorage.googleapis.com/v0/b/aldeberan-emporium-firebase.appspot.com/o/products%2Ffoccacia_loaf.jpg?alt=media&token=a483badb-906c-4eaf-b66d-e7b39c4e39ce'),
	(15, 15, 'BUTTER CROISSANT', 'butter_croissant', 1, 2.00, 'https://firebasestorage.googleapis.com/v0/b/aldeberan-emporium-firebase.appspot.com/o/products%2Fraspberry_butter.jpg?alt=media&token=6e369671-6728-49bd-8b34-fcfbcfd122ad'),
	(25, 25, 'BUTTER CROISSANT', 'butter_croissant', 1, 2.00, 'https://firebasestorage.googleapis.com/v0/b/aldeberan-emporium-firebase.appspot.com/o/products%2Fraspberry_butter.jpg?alt=media&token=6e369671-6728-49bd-8b34-fcfbcfd122ad'),
	(35, 35, 'BUTTER CROISSANT', 'butter_croissant', 1, 2.00, 'https://firebasestorage.googleapis.com/v0/b/aldeberan-emporium-firebase.appspot.com/o/products%2Fraspberry_butter.jpg?alt=media&token=6e369671-6728-49bd-8b34-fcfbcfd122ad'),
	(45, 35, 'CLASSIC CRESCENT', 'classic_crescent', 1, 2.00, 'https://firebasestorage.googleapis.com/v0/b/aldeberan-emporium-firebase.appspot.com/o/products%2Fclassic_crecent.jpg?alt=media&token=52644a96-f311-4c5d-a659-d84786eb68f0'),
	(55, 45, 'BUTTER CROISSANT', 'butter_croissant', 1, 2.00, 'https://firebasestorage.googleapis.com/v0/b/aldeberan-emporium-firebase.appspot.com/o/products%2Fraspberry_butter.jpg?alt=media&token=6e369671-6728-49bd-8b34-fcfbcfd122ad'),
	(65, 55, 'CLASSIC CRESCENT', 'classic_crescent', 1, 2.00, 'https://firebasestorage.googleapis.com/v0/b/aldeberan-emporium-firebase.appspot.com/o/products%2Fclassic_crecent.jpg?alt=media&token=52644a96-f311-4c5d-a659-d84786eb68f0'),
	(75, 65, 'BUTTER CROISSANT', 'butter_croissant', 1, 2.00, 'https://firebasestorage.googleapis.com/v0/b/aldeberan-emporium-firebase.appspot.com/o/products%2Fraspberry_butter.jpg?alt=media&token=6e369671-6728-49bd-8b34-fcfbcfd122ad'),
	(85, 75, 'CLASSIC CRESCENT', 'classic_crescent', 1, 2.00, 'https://firebasestorage.googleapis.com/v0/b/aldeberan-emporium-firebase.appspot.com/o/products%2Fclassic_crecent.jpg?alt=media&token=52644a96-f311-4c5d-a659-d84786eb68f0'),
	(95, 85, 'GLAZE BUTTER CROISSANT', 'glaze_butter_croissant', 1, 3.60, 'https://firebasestorage.googleapis.com/v0/b/aldeberan-emporium-firebase.appspot.com/o/products%2Fglaze_butter_croissant.jpg?alt=media&token=c1157ef9-74ea-4919-b883-c5d592702779'),
	(105, 95, 'BUTTER CROISSANT', 'butter_croissant', 1, 2.00, 'https://firebasestorage.googleapis.com/v0/b/aldeberan-emporium-firebase.appspot.com/o/products%2Fraspberry_butter.jpg?alt=media&token=6e369671-6728-49bd-8b34-fcfbcfd122ad'),
	(115, 95, 'FOCACCIA LOAF', 'focaccia_loaf', 1, 5.20, 'https://firebasestorage.googleapis.com/v0/b/aldeberan-emporium-firebase.appspot.com/o/products%2Ffoccacia_loaf.jpg?alt=media&token=a483badb-906c-4eaf-b66d-e7b39c4e39ce'),
	(125, 105, 'ARTISAN BAGUETTE', 'artisan_baguette', 1, 2.00, 'https://firebasestorage.googleapis.com/v0/b/aldeberan-emporium-firebase.appspot.com/o/products%2Fartisan_baguette.jpg?alt=media&token=1bd53188-1fb7-4621-80b2-b779d9058c60'),
	(135, 115, 'ARTISAN BAGUETTE', 'artisan_baguette', 1, 2.00, 'https://firebasestorage.googleapis.com/v0/b/aldeberan-emporium-firebase.appspot.com/o/products%2Fartisan_baguette.jpg?alt=media&token=1bd53188-1fb7-4621-80b2-b779d9058c60'),
	(155, 165, 'LAVA BUTTER CROISSANT', 'lava_butter_croissant', 1, 3.60, 'https://firebasestorage.googleapis.com/v0/b/aldeberan-emporium-firebase.appspot.com/o/products%2Flava_butter_croissant.jpg?alt=media&token=e1d74293-9ab3-4586-9497-320cc8c9d1d7'),
	(165, 175, 'GOLDEN ROTI', 'golden_roti', 1, 5.80, 'https://firebasestorage.googleapis.com/v0/b/aldeberan-emporium-firebase.appspot.com/o/products%2Fgolden_roti_loaf.jpg?alt=media&token=d329b97b-7545-4df0-9d3c-cb95a8c7ae00'),
	(175, 185, 'BUTTER CROISSANT', 'butter_croissant', 1, 2.00, 'https://firebasestorage.googleapis.com/v0/b/aldeberan-emporium-firebase.appspot.com/o/products%2Fraspberry_butter.jpg?alt=media&token=6e369671-6728-49bd-8b34-fcfbcfd122ad'),
	(185, 185, 'CLASSIC CRESCENT', 'classic_crescent', 1, 2.00, 'https://firebasestorage.googleapis.com/v0/b/aldeberan-emporium-firebase.appspot.com/o/products%2Fclassic_crecent.jpg?alt=media&token=52644a96-f311-4c5d-a659-d84786eb68f0'),
	(195, 195, 'CHEESE BUTTER CROISSANT', 'cheese_butter_croissant', 1, 3.60, 'https://firebasestorage.googleapis.com/v0/b/aldeberan-emporium-firebase.appspot.com/o/products%2Fcheese_butter_croissant.jpg?alt=media&token=8f24346a-b009-490a-a694-db72362bf23c'),
	(205, 205, 'CLASSIC CRESCENT', 'classic_crescent', 11, 2.00, 'https://firebasestorage.googleapis.com/v0/b/aldeberan-emporium-firebase.appspot.com/o/products%2Fclassic_crecent.jpg?alt=media&token=52644a96-f311-4c5d-a659-d84786eb68f0'),
	(215, 205, 'BLACK SESAME CROISSANT', 'black_sesame_croissant', 3, 3.60, 'https://firebasestorage.googleapis.com/v0/b/aldeberan-emporium-firebase.appspot.com/o/products%2Fblack_sesame_croissant.jpg?alt=media&token=a15621df-b0d1-4428-a68f-27265ca1fa90'),
	(225, 205, 'BUTTER CROISSANT', 'butter_croissant', 8, 2.00, 'https://firebasestorage.googleapis.com/v0/b/aldeberan-emporium-firebase.appspot.com/o/products%2Fraspberry_butter.jpg?alt=media&token=6e369671-6728-49bd-8b34-fcfbcfd122ad'),
	(235, 205, 'FOCACCIA LOAF', 'focaccia_loaf', 25, 5.20, 'https://firebasestorage.googleapis.com/v0/b/aldeberan-emporium-firebase.appspot.com/o/products%2Ffoccacia_loaf.jpg?alt=media&token=a483badb-906c-4eaf-b66d-e7b39c4e39ce'),
	(245, 205, 'ARTISAN BAGUETTE', 'artisan_baguette', 14, 2.00, 'https://firebasestorage.googleapis.com/v0/b/aldeberan-emporium-firebase.appspot.com/o/products%2Fartisan_baguette.jpg?alt=media&token=1bd53188-1fb7-4621-80b2-b779d9058c60'),
	(255, 205, 'BLACK GOLD CROISSANT CHOC LAVA', 'black_gold_croissant_choc_lava', 1, 3.60, 'https://firebasestorage.googleapis.com/v0/b/aldeberan-emporium-firebase.appspot.com/o/products%2Fblack_gold.jpg?alt=media&token=b0f0a77f-4a11-442e-9a57-e1d8159a9657'),
	(265, 205, 'LAVA BUTTER CROISSANT', 'lava_butter_croissant', 1, 3.60, 'https://firebasestorage.googleapis.com/v0/b/aldeberan-emporium-firebase.appspot.com/o/products%2Flava_butter_croissant.jpg?alt=media&token=e1d74293-9ab3-4586-9497-320cc8c9d1d7'),
	(275, 205, 'GOLDEN ROTI', 'golden_roti', 1, 5.80, 'https://firebasestorage.googleapis.com/v0/b/aldeberan-emporium-firebase.appspot.com/o/products%2Fgolden_roti_loaf.jpg?alt=media&token=d329b97b-7545-4df0-9d3c-cb95a8c7ae00'),
	(285, 205, 'LUCKY ALMOND CRISP', 'lucky_almond_crisp', 1, 8.50, 'https://firebasestorage.googleapis.com/v0/b/aldeberan-emporium-firebase.appspot.com/o/products%2Flucky_almond_crisp.jpg?alt=media&token=0766f755-4c24-4443-95d7-cf119d2e2b9d'),
	(295, 205, 'COFFEE CROISSANT', 'coffee_croissant', 1, 2.40, 'https://firebasestorage.googleapis.com/v0/b/aldeberan-emporium-firebase.appspot.com/o/products%2Fcoffee_croissant.jpg?alt=media&token=9958a76e-2616-4203-8429-0e3a9cd72d62'),
	(305, 205, 'RASPBERRY CROISSANT', 'raspberry_croissant', 1, 2.40, 'https://firebasestorage.googleapis.com/v0/b/aldeberan-emporium-firebase.appspot.com/o/products%2Fraspberry_croissant.jpg?alt=media&token=ec38760e-862e-4ce3-8028-6bad75ec0292'),
	(315, 215, 'CHEESE BUTTER CROISSANT', 'cheese_butter_croissant', 1, 3.60, 'https://firebasestorage.googleapis.com/v0/b/aldeberan-emporium-firebase.appspot.com/o/products%2Fcheese_butter_croissant.jpg?alt=media&token=8f24346a-b009-490a-a694-db72362bf23c'),
	(325, 215, 'BLACK GOLD CROISSANT CHOC LAVA', 'black_gold_croissant_choc_lava', 1, 3.60, 'https://firebasestorage.googleapis.com/v0/b/aldeberan-emporium-firebase.appspot.com/o/products%2Fblack_gold.jpg?alt=media&token=b0f0a77f-4a11-442e-9a57-e1d8159a9657'),
	(335, 225, 'CLASSIC CRESCENT', 'classic_crescent', 4, 2.00, 'https://firebasestorage.googleapis.com/v0/b/aldeberan-emporium-firebase.appspot.com/o/products%2Fclassic_crecent.jpg?alt=media&token=52644a96-f311-4c5d-a659-d84786eb68f0'),
	(345, 225, 'MATCHA BUTTER CROISSANT', 'matcha_butter_croissant', 3, 2.40, 'https://firebasestorage.googleapis.com/v0/b/aldeberan-emporium-firebase.appspot.com/o/products%2Fmatcha_butter_croissant.jpg?alt=media&token=eec4dc80-d1a8-4a77-aae0-5ba1bc850548'),
	(355, 225, 'BUTTER CROISSANT', 'butter_croissant', 1, 2.00, 'https://firebasestorage.googleapis.com/v0/b/aldeberan-emporium-firebase.appspot.com/o/products%2Fraspberry_butter.jpg?alt=media&token=6e369671-6728-49bd-8b34-fcfbcfd122ad'),
	(365, 225, 'FOCACCIA LOAF', 'focaccia_loaf', 1, 5.20, 'https://firebasestorage.googleapis.com/v0/b/aldeberan-emporium-firebase.appspot.com/o/products%2Ffoccacia_loaf.jpg?alt=media&token=a483badb-906c-4eaf-b66d-e7b39c4e39ce'),
	(375, 225, 'ARTISAN BAGUETTE', 'artisan_baguette', 1, 2.00, 'https://firebasestorage.googleapis.com/v0/b/aldeberan-emporium-firebase.appspot.com/o/products%2Fartisan_baguette.jpg?alt=media&token=1bd53188-1fb7-4621-80b2-b779d9058c60'),
	(385, 225, 'BLACK GOLD CROISSANT CHOC LAVA', 'black_gold_croissant_choc_lava', 1, 3.60, 'https://firebasestorage.googleapis.com/v0/b/aldeberan-emporium-firebase.appspot.com/o/products%2Fblack_gold.jpg?alt=media&token=b0f0a77f-4a11-442e-9a57-e1d8159a9657'),
	(395, 225, 'LUCKY ALMOND CRISP', 'lucky_almond_crisp', 1, 8.50, 'https://firebasestorage.googleapis.com/v0/b/aldeberan-emporium-firebase.appspot.com/o/products%2Flucky_almond_crisp.jpg?alt=media&token=0766f755-4c24-4443-95d7-cf119d2e2b9d'),
	(405, 225, 'COFFEE CROISSANT', 'coffee_croissant', 1, 2.40, 'https://firebasestorage.googleapis.com/v0/b/aldeberan-emporium-firebase.appspot.com/o/products%2Fcoffee_croissant.jpg?alt=media&token=9958a76e-2616-4203-8429-0e3a9cd72d62'),
	(415, 225, 'RASPBERRY CROISSANT', 'raspberry_croissant', 1, 2.40, 'https://firebasestorage.googleapis.com/v0/b/aldeberan-emporium-firebase.appspot.com/o/products%2Fraspberry_croissant.jpg?alt=media&token=ec38760e-862e-4ce3-8028-6bad75ec0292'),
	(425, 235, 'BUTTER CROISSANT', 'butter_croissant', 4, 2.00, 'https://firebasestorage.googleapis.com/v0/b/aldeberan-emporium-firebase.appspot.com/o/products%2Fraspberry_butter.jpg?alt=media&token=6e369671-6728-49bd-8b34-fcfbcfd122ad'),
	(435, 235, 'FOCACCIA LOAF', 'focaccia_loaf', 4, 5.20, 'https://firebasestorage.googleapis.com/v0/b/aldeberan-emporium-firebase.appspot.com/o/products%2Ffoccacia_loaf.jpg?alt=media&token=a483badb-906c-4eaf-b66d-e7b39c4e39ce'),
	(445, 235, 'ARTISAN BAGUETTE', 'artisan_baguette', 2, 2.00, 'https://firebasestorage.googleapis.com/v0/b/aldeberan-emporium-firebase.appspot.com/o/products%2Fartisan_baguette.jpg?alt=media&token=1bd53188-1fb7-4621-80b2-b779d9058c60'),
	(455, 235, 'CLASSIC CRESCENT', 'classic_crescent', 1, 2.00, 'https://firebasestorage.googleapis.com/v0/b/aldeberan-emporium-firebase.appspot.com/o/products%2Fclassic_crecent.jpg?alt=media&token=52644a96-f311-4c5d-a659-d84786eb68f0'),
	(465, 235, 'BLACK SESAME CROISSANT', 'black_sesame_croissant', 1, 3.60, 'https://firebasestorage.googleapis.com/v0/b/aldeberan-emporium-firebase.appspot.com/o/products%2Fblack_sesame_croissant.jpg?alt=media&token=a15621df-b0d1-4428-a68f-27265ca1fa90'),
	(475, 235, 'LAVA BUTTER CROISSANT', 'lava_butter_croissant', 1, 3.60, 'https://firebasestorage.googleapis.com/v0/b/aldeberan-emporium-firebase.appspot.com/o/products%2Flava_butter_croissant.jpg?alt=media&token=e1d74293-9ab3-4586-9497-320cc8c9d1d7'),
	(485, 235, 'GOLDEN ROTI', 'golden_roti', 1, 5.80, 'https://firebasestorage.googleapis.com/v0/b/aldeberan-emporium-firebase.appspot.com/o/products%2Fgolden_roti_loaf.jpg?alt=media&token=d329b97b-7545-4df0-9d3c-cb95a8c7ae00'),
	(495, 235, 'CHEESE BUTTER CROISSANT', 'cheese_butter_croissant', 1, 3.60, 'https://firebasestorage.googleapis.com/v0/b/aldeberan-emporium-firebase.appspot.com/o/products%2Fcheese_butter_croissant.jpg?alt=media&token=8f24346a-b009-490a-a694-db72362bf23c'),
	(505, 235, 'BLACK GOLD CROISSANT CHOC LAVA', 'black_gold_croissant_choc_lava', 1, 3.60, 'https://firebasestorage.googleapis.com/v0/b/aldeberan-emporium-firebase.appspot.com/o/products%2Fblack_gold.jpg?alt=media&token=b0f0a77f-4a11-442e-9a57-e1d8159a9657'),
	(515, 235, 'GLAZE BUTTER CROISSANT', 'glaze_butter_croissant', 1, 3.60, 'https://firebasestorage.googleapis.com/v0/b/aldeberan-emporium-firebase.appspot.com/o/products%2Fglaze_butter_croissant.jpg?alt=media&token=c1157ef9-74ea-4919-b883-c5d592702779'),
	(525, 235, 'COFFEE CROISSANT', 'coffee_croissant', 1, 2.40, 'https://firebasestorage.googleapis.com/v0/b/aldeberan-emporium-firebase.appspot.com/o/products%2Fcoffee_croissant.jpg?alt=media&token=9958a76e-2616-4203-8429-0e3a9cd72d62'),
	(535, 235, 'LUCKY ALMOND CRISP', 'lucky_almond_crisp', 1, 8.50, 'https://firebasestorage.googleapis.com/v0/b/aldeberan-emporium-firebase.appspot.com/o/products%2Flucky_almond_crisp.jpg?alt=media&token=0766f755-4c24-4443-95d7-cf119d2e2b9d'),
	(545, 235, 'RASPBERRY CROISSANT', 'raspberry_croissant', 1, 2.40, 'https://firebasestorage.googleapis.com/v0/b/aldeberan-emporium-firebase.appspot.com/o/products%2Fraspberry_croissant.jpg?alt=media&token=ec38760e-862e-4ce3-8028-6bad75ec0292'),
	(555, 235, 'MATCHA BUTTER CROISSANT', 'matcha_butter_croissant', 1, 2.40, 'https://firebasestorage.googleapis.com/v0/b/aldeberan-emporium-firebase.appspot.com/o/products%2Fmatcha_butter_croissant.jpg?alt=media&token=eec4dc80-d1a8-4a77-aae0-5ba1bc850548'),
	(565, 255, 'BUTTER CROISSANT', 'butter_croissant', 2, 2.00, 'https://firebasestorage.googleapis.com/v0/b/aldeberan-emporium-firebase.appspot.com/o/products%2Fraspberry_butter.jpg?alt=media&token=6e369671-6728-49bd-8b34-fcfbcfd122ad'),
	(575, 255, 'CLASSIC CRESCENT', 'classic_crescent', 1, 2.00, 'https://firebasestorage.googleapis.com/v0/b/aldeberan-emporium-firebase.appspot.com/o/products%2Fclassic_crecent.jpg?alt=media&token=52644a96-f311-4c5d-a659-d84786eb68f0'),
	(585, 255, 'FOCACCIA LOAF', 'focaccia_loaf', 2, 5.20, 'https://firebasestorage.googleapis.com/v0/b/aldeberan-emporium-firebase.appspot.com/o/products%2Ffoccacia_loaf.jpg?alt=media&token=a483badb-906c-4eaf-b66d-e7b39c4e39ce'),
	(595, 255, 'ARTISAN BAGUETTE', 'artisan_baguette', 1, 2.00, 'https://firebasestorage.googleapis.com/v0/b/aldeberan-emporium-firebase.appspot.com/o/products%2Fartisan_baguette.jpg?alt=media&token=1bd53188-1fb7-4621-80b2-b779d9058c60'),
	(605, 255, 'MATCHA BUTTER CROISSANT', 'matcha_butter_croissant', 1, 2.40, 'https://firebasestorage.googleapis.com/v0/b/aldeberan-emporium-firebase.appspot.com/o/products%2Fmatcha_butter_croissant.jpg?alt=media&token=eec4dc80-d1a8-4a77-aae0-5ba1bc850548'),
	(615, 255, 'RASPBERRY CROISSANT', 'raspberry_croissant', 1, 2.40, 'https://firebasestorage.googleapis.com/v0/b/aldeberan-emporium-firebase.appspot.com/o/products%2Fraspberry_croissant.jpg?alt=media&token=ec38760e-862e-4ce3-8028-6bad75ec0292'),
	(625, 255, 'COFFEE CROISSANT', 'coffee_croissant', 1, 2.40, 'https://firebasestorage.googleapis.com/v0/b/aldeberan-emporium-firebase.appspot.com/o/products%2Fcoffee_croissant.jpg?alt=media&token=9958a76e-2616-4203-8429-0e3a9cd72d62'),
	(635, 255, 'BLACK GOLD CROISSANT CHOC LAVA', 'black_gold_croissant_choc_lava', 1, 3.60, 'https://firebasestorage.googleapis.com/v0/b/aldeberan-emporium-firebase.appspot.com/o/products%2Fblack_gold.jpg?alt=media&token=b0f0a77f-4a11-442e-9a57-e1d8159a9657'),
	(645, 255, 'BLACK SESAME CROISSANT', 'black_sesame_croissant', 1, 3.60, 'https://firebasestorage.googleapis.com/v0/b/aldeberan-emporium-firebase.appspot.com/o/products%2Fblack_sesame_croissant.jpg?alt=media&token=a15621df-b0d1-4428-a68f-27265ca1fa90'),
	(655, 255, 'CHEESE BUTTER CROISSANT', 'cheese_butter_croissant', 1, 3.60, 'https://firebasestorage.googleapis.com/v0/b/aldeberan-emporium-firebase.appspot.com/o/products%2Fcheese_butter_croissant.jpg?alt=media&token=8f24346a-b009-490a-a694-db72362bf23c'),
	(665, 255, 'GLAZE BUTTER CROISSANT', 'glaze_butter_croissant', 1, 3.60, 'https://firebasestorage.googleapis.com/v0/b/aldeberan-emporium-firebase.appspot.com/o/products%2Fglaze_butter_croissant.jpg?alt=media&token=c1157ef9-74ea-4919-b883-c5d592702779'),
	(675, 255, 'LAVA BUTTER CROISSANT', 'lava_butter_croissant', 1, 3.60, 'https://firebasestorage.googleapis.com/v0/b/aldeberan-emporium-firebase.appspot.com/o/products%2Flava_butter_croissant.jpg?alt=media&token=e1d74293-9ab3-4586-9497-320cc8c9d1d7'),
	(685, 265, 'FOCACCIA LOAF', 'focaccia_loaf', 1, 5.20, 'https://firebasestorage.googleapis.com/v0/b/aldeberan-emporium-firebase.appspot.com/o/products%2Ffoccacia_loaf.jpg?alt=media&token=a483badb-906c-4eaf-b66d-e7b39c4e39ce'),
	(695, 265, 'ARTISAN BAGUETTE', 'artisan_baguette', 1, 2.00, 'https://firebasestorage.googleapis.com/v0/b/aldeberan-emporium-firebase.appspot.com/o/products%2Fartisan_baguette.jpg?alt=media&token=1bd53188-1fb7-4621-80b2-b779d9058c60'),
	(705, 275, 'BUTTER CROISSANT', 'butter_croissant', 1, 2.00, 'https://firebasestorage.googleapis.com/v0/b/aldeberan-emporium-firebase.appspot.com/o/products%2Fraspberry_butter.jpg?alt=media&token=6e369671-6728-49bd-8b34-fcfbcfd122ad'),
	(715, 285, 'ARTISAN BAGUETTE', 'artisan_baguette', 1, 2.00, 'https://firebasestorage.googleapis.com/v0/b/aldeberan-emporium-firebase.appspot.com/o/products%2Fartisan_baguette.jpg?alt=media&token=1bd53188-1fb7-4621-80b2-b779d9058c60'),
	(725, 285, 'CLASSIC CRESCENT', 'classic_crescent', 1, 2.00, 'https://firebasestorage.googleapis.com/v0/b/aldeberan-emporium-firebase.appspot.com/o/products%2Fclassic_crecent.jpg?alt=media&token=52644a96-f311-4c5d-a659-d84786eb68f0'),
	(735, 295, 'ARTISAN BAGUETTE', 'artisan_baguette', 1, 2.00, 'https://firebasestorage.googleapis.com/v0/b/aldeberan-emporium-firebase.appspot.com/o/products%2Fartisan_baguette.jpg?alt=media&token=1bd53188-1fb7-4621-80b2-b779d9058c60'),
	(745, 305, 'BUTTER CROISSANT', 'butter_croissant', 1, 2.00, 'https://firebasestorage.googleapis.com/v0/b/aldeberan-emporium-firebase.appspot.com/o/products%2Fraspberry_butter.jpg?alt=media&token=6e369671-6728-49bd-8b34-fcfbcfd122ad'),
	(755, 315, 'BUTTER CROISSANT', 'butter_croissant', 1, 2.00, 'https://firebasestorage.googleapis.com/v0/b/aldeberan-emporium-firebase.appspot.com/o/products%2Fraspberry_butter.jpg?alt=media&token=6e369671-6728-49bd-8b34-fcfbcfd122ad'),
	(765, 325, 'BLACK GOLD CROISSANT CHOC LAVA', 'black_gold_croissant_choc_lava', 1, 3.60, 'https://firebasestorage.googleapis.com/v0/b/aldeberan-emporium-firebase.appspot.com/o/products%2Fblack_gold.jpg?alt=media&token=b0f0a77f-4a11-442e-9a57-e1d8159a9657'),
	(775, 335, 'FOCACCIA LOAF', 'focaccia_loaf', 1, 5.20, 'https://firebasestorage.googleapis.com/v0/b/aldeberan-emporium-firebase.appspot.com/o/products%2Ffoccacia_loaf.jpg?alt=media&token=a483badb-906c-4eaf-b66d-e7b39c4e39ce'),
	(785, 345, 'FOCACCIA LOAF', 'focaccia_loaf', 1, 5.20, 'https://firebasestorage.googleapis.com/v0/b/aldeberan-emporium-firebase.appspot.com/o/products%2Ffoccacia_loaf.jpg?alt=media&token=a483badb-906c-4eaf-b66d-e7b39c4e39ce'),
	(795, 355, 'FOCACCIA LOAF', 'focaccia_loaf', 1, 5.20, 'https://firebasestorage.googleapis.com/v0/b/aldeberan-emporium-firebase.appspot.com/o/products%2Ffoccacia_loaf.jpg?alt=media&token=a483badb-906c-4eaf-b66d-e7b39c4e39ce'),
	(805, 355, 'BUTTER CROISSANT', 'butter_croissant', 1, 2.00, 'https://firebasestorage.googleapis.com/v0/b/aldeberan-emporium-firebase.appspot.com/o/products%2Fraspberry_butter.jpg?alt=media&token=6e369671-6728-49bd-8b34-fcfbcfd122ad'),
	(815, 365, 'BUTTER CROISSANT', 'butter_croissant', 1, 2.00, 'https://firebasestorage.googleapis.com/v0/b/aldeberan-emporium-firebase.appspot.com/o/products%2Fraspberry_butter.jpg?alt=media&token=6e369671-6728-49bd-8b34-fcfbcfd122ad'),
	(825, 375, 'BUTTER CROISSANT', 'butter_croissant', 1, 2.00, 'https://firebasestorage.googleapis.com/v0/b/aldeberan-emporium-firebase.appspot.com/o/products%2Fraspberry_butter.jpg?alt=media&token=6e369671-6728-49bd-8b34-fcfbcfd122ad'),
	(835, 385, 'BUTTER CROISSANT', 'butter_croissant', 1, 2.00, 'https://firebasestorage.googleapis.com/v0/b/aldeberan-emporium-firebase.appspot.com/o/products%2Fraspberry_butter.jpg?alt=media&token=6e369671-6728-49bd-8b34-fcfbcfd122ad'),
	(845, 395, 'BUTTER CROISSANT', 'butter_croissant', 1, 2.00, 'https://firebasestorage.googleapis.com/v0/b/aldeberan-emporium-firebase.appspot.com/o/products%2Fraspberry_butter.jpg?alt=media&token=6e369671-6728-49bd-8b34-fcfbcfd122ad'),
	(855, 405, 'BUTTER CROISSANT', 'butter_croissant', 1, 2.00, 'https://firebasestorage.googleapis.com/v0/b/aldeberan-emporium-firebase.appspot.com/o/products%2Fraspberry_butter.jpg?alt=media&token=6e369671-6728-49bd-8b34-fcfbcfd122ad'),
	(865, 415, 'BUTTER CROISSANT', 'butter_croissant', 1, 2.00, 'https://firebasestorage.googleapis.com/v0/b/aldeberan-emporium-firebase.appspot.com/o/products%2Fraspberry_butter.jpg?alt=media&token=6e369671-6728-49bd-8b34-fcfbcfd122ad'),
	(875, 425, 'BUTTER CROISSANT', 'butter_croissant', 1, 2.00, 'https://firebasestorage.googleapis.com/v0/b/aldeberan-emporium-firebase.appspot.com/o/products%2Fraspberry_butter.jpg?alt=media&token=6e369671-6728-49bd-8b34-fcfbcfd122ad'),
	(885, 435, 'BUTTER CROISSANT', 'butter_croissant', 1, 2.00, 'https://firebasestorage.googleapis.com/v0/b/aldeberan-emporium-firebase.appspot.com/o/products%2Fraspberry_butter.jpg?alt=media&token=6e369671-6728-49bd-8b34-fcfbcfd122ad'),
	(895, 445, 'BUTTER CROISSANT', 'butter_croissant', 1, 2.00, 'https://firebasestorage.googleapis.com/v0/b/aldeberan-emporium-firebase.appspot.com/o/products%2Fraspberry_butter.jpg?alt=media&token=6e369671-6728-49bd-8b34-fcfbcfd122ad'),
	(905, 455, 'BUTTER CROISSANT', 'butter_croissant', 1, 2.00, 'https://firebasestorage.googleapis.com/v0/b/aldeberan-emporium-firebase.appspot.com/o/products%2Fraspberry_butter.jpg?alt=media&token=6e369671-6728-49bd-8b34-fcfbcfd122ad'),
	(915, 465, 'BUTTER CROISSANT', 'butter_croissant', 1, 2.00, 'https://firebasestorage.googleapis.com/v0/b/aldeberan-emporium-firebase.appspot.com/o/products%2Fraspberry_butter.jpg?alt=media&token=6e369671-6728-49bd-8b34-fcfbcfd122ad'),
	(925, 475, 'BUTTER CROISSANT', 'butter_croissant', 1, 2.00, 'https://firebasestorage.googleapis.com/v0/b/aldeberan-emporium-firebase.appspot.com/o/products%2Fraspberry_butter.jpg?alt=media&token=6e369671-6728-49bd-8b34-fcfbcfd122ad'),
	(935, 485, 'BUTTER CROISSANT', 'butter_croissant', 1, 2.00, 'https://firebasestorage.googleapis.com/v0/b/aldeberan-emporium-firebase.appspot.com/o/products%2Fraspberry_butter.jpg?alt=media&token=6e369671-6728-49bd-8b34-fcfbcfd122ad'),
	(945, 495, 'ARTISAN BAGUETTE', 'artisan_baguette', 1, 2.00, 'https://firebasestorage.googleapis.com/v0/b/aldeberan-emporium-firebase.appspot.com/o/products%2Fartisan_baguette.jpg?alt=media&token=1bd53188-1fb7-4621-80b2-b779d9058c60'),
	(955, 505, 'BUTTER CROISSANT', 'butter_croissant', 1, 2.00, 'https://firebasestorage.googleapis.com/v0/b/aldeberan-emporium-firebase.appspot.com/o/products%2Fraspberry_butter.jpg?alt=media&token=6e369671-6728-49bd-8b34-fcfbcfd122ad'),
	(965, 505, 'BLACK GOLD CROISSANT CHOC LAVA', 'black_gold_croissant_choc_lava', 1, 3.60, 'https://firebasestorage.googleapis.com/v0/b/aldeberan-emporium-firebase.appspot.com/o/products%2Fblack_gold.jpg?alt=media&token=b0f0a77f-4a11-442e-9a57-e1d8159a9657'),
	(975, 505, 'BLACK SESAME CROISSANT', 'black_sesame_croissant', 1, 3.60, 'https://firebasestorage.googleapis.com/v0/b/aldeberan-emporium-firebase.appspot.com/o/products%2Fblack_sesame_croissant.jpg?alt=media&token=a15621df-b0d1-4428-a68f-27265ca1fa90'),
	(985, 515, 'BLACK GOLD CROISSANT CHOC LAVA', 'black_gold_croissant_choc_lava', 1, 3.60, 'https://firebasestorage.googleapis.com/v0/b/aldeberan-emporium-firebase.appspot.com/o/products%2Fblack_gold.jpg?alt=media&token=b0f0a77f-4a11-442e-9a57-e1d8159a9657'),
	(995, 515, 'BLACK SESAME CROISSANT', 'black_sesame_croissant', 1, 3.60, 'https://firebasestorage.googleapis.com/v0/b/aldeberan-emporium-firebase.appspot.com/o/products%2Fblack_sesame_croissant.jpg?alt=media&token=a15621df-b0d1-4428-a68f-27265ca1fa90');
/*!40000 ALTER TABLE `order_item` ENABLE KEYS */;

-- Dumping structure for table heroku_340a0634a49f183.order_payment
CREATE TABLE IF NOT EXISTS `order_payment` (
  `order_payment_id` int(11) NOT NULL AUTO_INCREMENT,
  `order_id` int(11) NOT NULL,
  `payment_type` varchar(20) NOT NULL,
  `payment_id` varchar(100) NOT NULL,
  PRIMARY KEY (`order_payment_id`)
) ENGINE=InnoDB AUTO_INCREMENT=485 DEFAULT CHARSET=utf8;

-- Dumping data for table heroku_340a0634a49f183.order_payment: ~48 rows (approximately)
/*!40000 ALTER TABLE `order_payment` DISABLE KEYS */;
INSERT INTO `order_payment` (`order_payment_id`, `order_id`, `payment_type`, `payment_id`) VALUES
	(5, 5, 'Card', 'pi_3JolrbKPfEZYnk5W0cQG9FlM'),
	(15, 15, 'Card', 'pi_3JolvMKPfEZYnk5W0tfD6v2X'),
	(25, 25, 'Card', 'pi_3Jom26KPfEZYnk5W0hqj9Y62'),
	(35, 35, 'Card', 'pi_3Jom6OKPfEZYnk5W1gCXauPV'),
	(45, 45, 'Card', 'pi_3Jp1kAKPfEZYnk5W1jzMQHGx'),
	(55, 55, 'Card', 'pi_3JpQ0BKPfEZYnk5W0lXdtVKA'),
	(65, 65, 'Card', 'pi_3JpQ32KPfEZYnk5W0WFRwHQ0'),
	(75, 75, 'Card', 'pi_3JpRQtKPfEZYnk5W0L31IcHW'),
	(85, 85, 'Card', 'pi_3JpSh8KPfEZYnk5W1EDREIWh'),
	(95, 95, 'Card', 'pi_3JpTMAKPfEZYnk5W0Fne6JHs'),
	(105, 105, 'Card', 'pi_3JpTT0KPfEZYnk5W13nSsyCk'),
	(115, 115, 'Card', 'pi_3JpTUcKPfEZYnk5W0sjEv7mp'),
	(125, 155, 'Card', 'pi_3JpUZGKPfEZYnk5W0U6sP9Mh'),
	(135, 165, 'Card', 'pi_3JpUfoKPfEZYnk5W0YeeK5Bd'),
	(145, 175, 'Card', 'pi_3JpUjdKPfEZYnk5W1hgS9KGh'),
	(155, 185, 'Card', 'pi_3JpVWJKPfEZYnk5W1Sliz91j'),
	(165, 195, 'Card', 'pi_3JpVqsKPfEZYnk5W0YJKiCgY'),
	(175, 205, 'Card', 'pi_3JpWLnKPfEZYnk5W1bhy8WHc'),
	(185, 215, 'Card', 'pi_3JpWaRKPfEZYnk5W075NwUcZ'),
	(195, 225, 'Card', 'pi_3JqCzlKPfEZYnk5W1N9B47Zz'),
	(205, 235, 'Card', 'pi_3JqEknKPfEZYnk5W0yABApbi'),
	(215, 255, 'Card', 'pi_3JqF0pKPfEZYnk5W1NSs2cQc'),
	(225, 265, 'Card', 'pi_3JqjHzKPfEZYnk5W0cJv24mv'),
	(235, 275, 'Card', 'pi_3JqjMCKPfEZYnk5W02JuvDYU'),
	(245, 285, 'Card', 'pi_3Jqjr4KPfEZYnk5W0CFakqrR'),
	(255, 295, 'Card', 'pi_3JqjzrKPfEZYnk5W1UXCNita'),
	(265, 305, 'Card', 'pi_3JrZN1KPfEZYnk5W0DSKd3aJ'),
	(275, 315, 'Card', 'pi_3JrZhbKPfEZYnk5W0OMljOWy'),
	(285, 325, 'Card', 'pi_3JrZv3KPfEZYnk5W0n0AwQoQ'),
	(295, 335, 'Card', 'pi_3Jra0VKPfEZYnk5W17WRCHLR'),
	(305, 345, 'Card', 'pi_3JraELKPfEZYnk5W1wB6WYbK'),
	(315, 355, 'Card', 'pi_3JraYzKPfEZYnk5W1XMlPSKS'),
	(325, 365, 'Card', 'pi_3Jrac1KPfEZYnk5W1JWZq1NC'),
	(335, 375, 'Card', 'pi_3JradhKPfEZYnk5W0PxvQ6VK'),
	(345, 385, 'Card', 'pi_3JraffKPfEZYnk5W1CIg2uLn'),
	(355, 395, 'Card', 'pi_3JragYKPfEZYnk5W0Y5Uokwi'),
	(365, 405, 'Card', 'pi_3JrajPKPfEZYnk5W1r36txPt'),
	(375, 415, 'Card', 'pi_3JrandKPfEZYnk5W1NvFNYtJ'),
	(385, 425, 'Card', 'pi_3JrapVKPfEZYnk5W0AfgTuHE'),
	(395, 435, 'Card', 'pi_3JraqIKPfEZYnk5W1eh35FvP'),
	(405, 445, 'Card', 'pi_3JrauSKPfEZYnk5W0zMQRqYp'),
	(415, 455, 'Card', 'pi_3JrawmKPfEZYnk5W1UFqqPo3'),
	(425, 465, 'Card', 'pi_3Jrb1PKPfEZYnk5W1p7ORqA3'),
	(435, 475, 'Card', 'pi_3Jrb3IKPfEZYnk5W0hmZ5g7J'),
	(445, 485, 'Card', 'pi_3Jrb9TKPfEZYnk5W06mMCZAa'),
	(455, 495, 'Card', 'pi_3Jrf6hKPfEZYnk5W1TnV7MGH'),
	(465, 505, 'Card', 'pi_3JrfHJKPfEZYnk5W1AbxCkTS'),
	(475, 515, 'Card', 'pi_3JrfJqKPfEZYnk5W0azQuwpU');
/*!40000 ALTER TABLE `order_payment` ENABLE KEYS */;

-- Dumping structure for table heroku_340a0634a49f183.product
CREATE TABLE IF NOT EXISTS `product` (
  `product_id` int(11) NOT NULL AUTO_INCREMENT,
  `product_name` varchar(500) CHARACTER SET utf8mb4 NOT NULL,
  `product_SKU` varchar(500) CHARACTER SET utf8mb4 NOT NULL,
  `product_availability` int(1) NOT NULL,
  `product_stock` int(11) NOT NULL,
  `product_price` decimal(7,2) NOT NULL,
  `product_img` varchar(500) CHARACTER SET utf8mb4 NOT NULL,
  PRIMARY KEY (`product_id`)
) ENGINE=InnoDB AUTO_INCREMENT=18 DEFAULT CHARSET=utf8;

-- Dumping data for table heroku_340a0634a49f183.product: ~16 rows (approximately)
/*!40000 ALTER TABLE `product` DISABLE KEYS */;
INSERT INTO `product` (`product_id`, `product_name`, `product_SKU`, `product_availability`, `product_stock`, `product_price`, `product_img`) VALUES
	(1, 'BUTTER CROISSANT', 'butter_croissant', 1, 99959, 2.00, 'https://firebasestorage.googleapis.com/v0/b/aldeberan-emporium-firebase.appspot.com/o/products%2Fraspberry_butter.jpg?alt=media&token=6e369671-6728-49bd-8b34-fcfbcfd122ad'),
	(2, 'CLASSIC CRESCENT', 'classic_crescent', 1, 99976, 2.00, 'https://firebasestorage.googleapis.com/v0/b/aldeberan-emporium-firebase.appspot.com/o/products%2Fclassic_crecent.jpg?alt=media&token=52644a96-f311-4c5d-a659-d84786eb68f0'),
	(3, 'FOCACCIA LOAF', 'focaccia_loaf', 1, 99961, 5.20, 'https://firebasestorage.googleapis.com/v0/b/aldeberan-emporium-firebase.appspot.com/o/products%2Ffoccacia_loaf.jpg?alt=media&token=a483badb-906c-4eaf-b66d-e7b39c4e39ce'),
	(4, 'ARTISAN BAGUETTE', 'artisan_baguette', 1, 99975, 2.00, 'https://firebasestorage.googleapis.com/v0/b/aldeberan-emporium-firebase.appspot.com/o/products%2Fartisan_baguette.jpg?alt=media&token=1bd53188-1fb7-4621-80b2-b779d9058c60'),
	(6, 'MATCHA BUTTER CROISSANT', 'matcha_butter_croissant', 1, 99994, 2.40, 'https://firebasestorage.googleapis.com/v0/b/aldeberan-emporium-firebase.appspot.com/o/products%2Fmatcha_butter_croissant.jpg?alt=media&token=eec4dc80-d1a8-4a77-aae0-5ba1bc850548'),
	(7, 'RASPBERRY CROISSANT', 'raspberry_croissant', 1, 99995, 2.40, 'https://firebasestorage.googleapis.com/v0/b/aldeberan-emporium-firebase.appspot.com/o/products%2Fraspberry_croissant.jpg?alt=media&token=ec38760e-862e-4ce3-8028-6bad75ec0292'),
	(8, 'COFFEE CROISSANT', 'coffee_croissant', 1, 99995, 2.40, 'https://firebasestorage.googleapis.com/v0/b/aldeberan-emporium-firebase.appspot.com/o/products%2Fcoffee_croissant.jpg?alt=media&token=9958a76e-2616-4203-8429-0e3a9cd72d62'),
	(9, 'MINI RASPBERRY BLOOMS', 'mini_raspberry_blooms', 1, 99999, 2.40, 'https://firebasestorage.googleapis.com/v0/b/aldeberan-emporium-firebase.appspot.com/o/products%2Fmini_raspberry_bloom.jpg?alt=media&token=efe01357-63b0-4453-85cc-23f0d3d13d0c'),
	(10, 'LUCKY ALMOND CRISP', 'lucky_almond_crisp', 1, 99995, 8.50, 'https://firebasestorage.googleapis.com/v0/b/aldeberan-emporium-firebase.appspot.com/o/products%2Flucky_almond_crisp.jpg?alt=media&token=0766f755-4c24-4443-95d7-cf119d2e2b9d'),
	(11, 'GOLDEN ROTI', 'golden_roti', 1, 99996, 5.80, 'https://firebasestorage.googleapis.com/v0/b/aldeberan-emporium-firebase.appspot.com/o/products%2Fgolden_roti_loaf.jpg?alt=media&token=d329b97b-7545-4df0-9d3c-cb95a8c7ae00'),
	(12, 'BUTTER CROISSANT', 'butter_croissant', 1, 99959, 1.80, 'https://firebasestorage.googleapis.com/v0/b/aldeberan-emporium-firebase.appspot.com/o/products%2Fbutter_croissant_medium.jpg?alt=media&token=053b640c-bdec-40c3-b24f-1bf88c4b7d7a'),
	(13, 'LAVA BUTTER CROISSANT', 'lava_butter_croissant', 1, 99995, 3.60, 'https://firebasestorage.googleapis.com/v0/b/aldeberan-emporium-firebase.appspot.com/o/products%2Flava_butter_croissant.jpg?alt=media&token=e1d74293-9ab3-4586-9497-320cc8c9d1d7'),
	(14, 'GLAZE BUTTER CROISSANT', 'glaze_butter_croissant', 1, 99995, 3.60, 'https://firebasestorage.googleapis.com/v0/b/aldeberan-emporium-firebase.appspot.com/o/products%2Fglaze_butter_croissant.jpg?alt=media&token=c1157ef9-74ea-4919-b883-c5d592702779'),
	(15, 'CHEESE BUTTER CROISSANT', 'cheese_butter_croissant', 1, 99994, 3.60, 'https://firebasestorage.googleapis.com/v0/b/aldeberan-emporium-firebase.appspot.com/o/products%2Fcheese_butter_croissant.jpg?alt=media&token=8f24346a-b009-490a-a694-db72362bf23c'),
	(16, 'BLACK SESAME CROISSANT', 'black_sesame_croissant', 1, 99992, 3.60, 'https://firebasestorage.googleapis.com/v0/b/aldeberan-emporium-firebase.appspot.com/o/products%2Fblack_sesame_croissant.jpg?alt=media&token=a15621df-b0d1-4428-a68f-27265ca1fa90'),
	(17, 'BLACK GOLD CROISSANT CHOC LAVA', 'black_gold_croissant_choc_lava', 1, 99991, 3.60, 'https://firebasestorage.googleapis.com/v0/b/aldeberan-emporium-firebase.appspot.com/o/products%2Fblack_gold.jpg?alt=media&token=b0f0a77f-4a11-442e-9a57-e1d8159a9657');
/*!40000 ALTER TABLE `product` ENABLE KEYS */;

-- Dumping structure for table heroku_340a0634a49f183.quote
CREATE TABLE IF NOT EXISTS `quote` (
  `quote_id` int(11) NOT NULL AUTO_INCREMENT,
  `user_id` varchar(35) NOT NULL,
  `total` decimal(50,2) NOT NULL,
  `quote_status` int(1) NOT NULL,
  PRIMARY KEY (`quote_id`)
) ENGINE=InnoDB AUTO_INCREMENT=595 DEFAULT CHARSET=utf8;

-- Dumping data for table heroku_340a0634a49f183.quote: ~58 rows (approximately)
/*!40000 ALTER TABLE `quote` DISABLE KEYS */;
INSERT INTO `quote` (`quote_id`, `user_id`, `total`, `quote_status`) VALUES
	(5, '0TbifvLstjWRpIpzDpBaZQRvXrA2', 7.20, 1),
	(15, '0TbifvLstjWRpIpzDpBaZQRvXrA2', 4.00, 1),
	(25, '0TbifvLstjWRpIpzDpBaZQRvXrA2', 4.00, 1),
	(35, '0TbifvLstjWRpIpzDpBaZQRvXrA2', 4.00, 1),
	(45, '0TbifvLstjWRpIpzDpBaZQRvXrA2', 2.00, 1),
	(55, '0TbifvLstjWRpIpzDpBaZQRvXrA2', 2.00, 1),
	(65, 'S5DepbW8fmNPM8RpJyM68XTTHJ72', 58.30, 0),
	(75, 'CrUDIVYKf8U6jKQ5mPjsraFGGJG2', 0.00, 0),
	(85, 'XHXDzxi0ZMM4I8dEwLYoTNIGkb93', 6.00, 1),
	(95, 'XHXDzxi0ZMM4I8dEwLYoTNIGkb93', 4.00, 1),
	(105, 'XHXDzxi0ZMM4I8dEwLYoTNIGkb93', 43.60, 1),
	(115, '0TbifvLstjWRpIpzDpBaZQRvXrA2', 3.60, 1),
	(125, '0TbifvLstjWRpIpzDpBaZQRvXrA2', 7.20, 1),
	(135, '0TbifvLstjWRpIpzDpBaZQRvXrA2', 2.00, 1),
	(145, '0TbifvLstjWRpIpzDpBaZQRvXrA2', 2.00, 1),
	(155, '0TbifvLstjWRpIpzDpBaZQRvXrA2', 5.20, 1),
	(165, '5dJ3jFfUgIYFkX7jUcRZ8s54DoB3', 8.50, 1),
	(175, '5dJ3jFfUgIYFkX7jUcRZ8s54DoB3', 3.60, 1),
	(185, '5dJ3jFfUgIYFkX7jUcRZ8s54DoB3', 5.80, 1),
	(195, '5dJ3jFfUgIYFkX7jUcRZ8s54DoB3', 9.20, 1),
	(205, '5dJ3jFfUgIYFkX7jUcRZ8s54DoB3', 3.60, 1),
	(215, '5dJ3jFfUgIYFkX7jUcRZ8s54DoB3', 233.10, 1),
	(225, '5dJ3jFfUgIYFkX7jUcRZ8s54DoB3', 14.40, 1),
	(235, '5dJ3jFfUgIYFkX7jUcRZ8s54DoB3', 79.50, 1),
	(245, 'fShPQcG09Sf5Vay2u4ir3qzk1B22', 41.30, 1),
	(265, 'nan', 0.00, 0),
	(275, 'fShPQcG09Sf5Vay2u4ir3qzk1B22', 0.00, 0),
	(285, '5dJ3jFfUgIYFkX7jUcRZ8s54DoB3', 7.20, 1),
	(295, '', 0.00, 0),
	(305, 'XHXDzxi0ZMM4I8dEwLYoTNIGkb93', 7.20, 1),
	(315, '1ygjU26cbUhAQ4Ntlm1meQMuqVI2', 0.00, 0),
	(325, '5dJ3jFfUgIYFkX7jUcRZ8s54DoB3', 2.00, 1),
	(335, '5dJ3jFfUgIYFkX7jUcRZ8s54DoB3', 9.20, 1),
	(345, '5dJ3jFfUgIYFkX7jUcRZ8s54DoB3', 2.00, 1),
	(355, '5dJ3jFfUgIYFkX7jUcRZ8s54DoB3', 2.00, 1),
	(365, '5dJ3jFfUgIYFkX7jUcRZ8s54DoB3', 2.00, 1),
	(375, '5dJ3jFfUgIYFkX7jUcRZ8s54DoB3', 3.60, 1),
	(385, '5dJ3jFfUgIYFkX7jUcRZ8s54DoB3', 5.20, 1),
	(395, '5dJ3jFfUgIYFkX7jUcRZ8s54DoB3', 5.20, 1),
	(405, '5dJ3jFfUgIYFkX7jUcRZ8s54DoB3', 2.00, 1),
	(415, 'XHXDzxi0ZMM4I8dEwLYoTNIGkb93', 2.00, 1),
	(425, 'XHXDzxi0ZMM4I8dEwLYoTNIGkb93', 2.00, 1),
	(435, 'XHXDzxi0ZMM4I8dEwLYoTNIGkb93', 2.00, 1),
	(445, 'XHXDzxi0ZMM4I8dEwLYoTNIGkb93', 2.00, 1),
	(455, 'XHXDzxi0ZMM4I8dEwLYoTNIGkb93', 2.00, 1),
	(465, 'XHXDzxi0ZMM4I8dEwLYoTNIGkb93', 2.00, 1),
	(475, 'XHXDzxi0ZMM4I8dEwLYoTNIGkb93', 2.00, 1),
	(485, 'XHXDzxi0ZMM4I8dEwLYoTNIGkb93', 2.00, 1),
	(495, 'XHXDzxi0ZMM4I8dEwLYoTNIGkb93', 2.00, 1),
	(505, 'XHXDzxi0ZMM4I8dEwLYoTNIGkb93', 2.00, 1),
	(515, 'XHXDzxi0ZMM4I8dEwLYoTNIGkb93', 2.00, 1),
	(525, '5dJ3jFfUgIYFkX7jUcRZ8s54DoB3', 0.00, 0),
	(535, 'XHXDzxi0ZMM4I8dEwLYoTNIGkb93', 2.00, 1),
	(545, 'XHXDzxi0ZMM4I8dEwLYoTNIGkb93', 2.00, 0),
	(555, 'tWc6280GWjhXAPF7EWPWMARwJ1r2', 2.00, 1),
	(565, 'tWc6280GWjhXAPF7EWPWMARwJ1r2', 9.20, 1),
	(575, 'tWc6280GWjhXAPF7EWPWMARwJ1r2', 7.20, 1),
	(585, 'tWc6280GWjhXAPF7EWPWMARwJ1r2', 0.00, 0);
/*!40000 ALTER TABLE `quote` ENABLE KEYS */;

-- Dumping structure for table heroku_340a0634a49f183.quote_item
CREATE TABLE IF NOT EXISTS `quote_item` (
  `quote_item_id` int(11) NOT NULL AUTO_INCREMENT,
  `quote_id` int(11) NOT NULL,
  `product_name` mediumtext NOT NULL,
  `product_SKU` mediumtext NOT NULL,
  `product_quantity` int(11) NOT NULL,
  `product_price` decimal(7,2) NOT NULL,
  `product_img` mediumtext NOT NULL,
  PRIMARY KEY (`quote_item_id`),
  KEY `quote_id` (`quote_id`),
  CONSTRAINT `quote_item_ibfk_1` FOREIGN KEY (`quote_id`) REFERENCES `quote` (`quote_id`)
) ENGINE=InnoDB AUTO_INCREMENT=2025 DEFAULT CHARSET=utf8;

-- Dumping data for table heroku_340a0634a49f183.quote_item: ~123 rows (approximately)
/*!40000 ALTER TABLE `quote_item` DISABLE KEYS */;
INSERT INTO `quote_item` (`quote_item_id`, `quote_id`, `product_name`, `product_SKU`, `product_quantity`, `product_price`, `product_img`) VALUES
	(5, 5, 'FOCACCIA LOAF', 'focaccia_loaf', 1, 5.20, 'https://firebasestorage.googleapis.com/v0/b/aldeberan-emporium-firebase.appspot.com/o/products%2Ffoccacia_loaf.jpg?alt=media&token=a483badb-906c-4eaf-b66d-e7b39c4e39ce'),
	(15, 5, 'ARTISAN BAGUETTE', 'artisan_baguette', 1, 2.00, 'https://firebasestorage.googleapis.com/v0/b/aldeberan-emporium-firebase.appspot.com/o/products%2Fartisan_baguette.jpg?alt=media&token=1bd53188-1fb7-4621-80b2-b779d9058c60'),
	(25, 15, 'BUTTER CROISSANT', 'butter_croissant', 1, 2.00, 'https://firebasestorage.googleapis.com/v0/b/aldeberan-emporium-firebase.appspot.com/o/products%2Fraspberry_butter.jpg?alt=media&token=6e369671-6728-49bd-8b34-fcfbcfd122ad'),
	(35, 15, 'CLASSIC CRESCENT', 'classic_crescent', 1, 2.00, 'https://firebasestorage.googleapis.com/v0/b/aldeberan-emporium-firebase.appspot.com/o/products%2Fclassic_crecent.jpg?alt=media&token=52644a96-f311-4c5d-a659-d84786eb68f0'),
	(45, 25, 'BUTTER CROISSANT', 'butter_croissant', 1, 2.00, 'https://firebasestorage.googleapis.com/v0/b/aldeberan-emporium-firebase.appspot.com/o/products%2Fraspberry_butter.jpg?alt=media&token=6e369671-6728-49bd-8b34-fcfbcfd122ad'),
	(55, 25, 'CLASSIC CRESCENT', 'classic_crescent', 1, 2.00, 'https://firebasestorage.googleapis.com/v0/b/aldeberan-emporium-firebase.appspot.com/o/products%2Fclassic_crecent.jpg?alt=media&token=52644a96-f311-4c5d-a659-d84786eb68f0'),
	(65, 35, 'BUTTER CROISSANT', 'butter_croissant', 1, 2.00, 'https://firebasestorage.googleapis.com/v0/b/aldeberan-emporium-firebase.appspot.com/o/products%2Fraspberry_butter.jpg?alt=media&token=6e369671-6728-49bd-8b34-fcfbcfd122ad'),
	(75, 35, 'CLASSIC CRESCENT', 'classic_crescent', 1, 2.00, 'https://firebasestorage.googleapis.com/v0/b/aldeberan-emporium-firebase.appspot.com/o/products%2Fclassic_crecent.jpg?alt=media&token=52644a96-f311-4c5d-a659-d84786eb68f0'),
	(85, 45, 'BUTTER CROISSANT', 'butter_croissant', 1, 2.00, 'https://firebasestorage.googleapis.com/v0/b/aldeberan-emporium-firebase.appspot.com/o/products%2Fraspberry_butter.jpg?alt=media&token=6e369671-6728-49bd-8b34-fcfbcfd122ad'),
	(95, 55, 'CLASSIC CRESCENT', 'classic_crescent', 1, 2.00, 'https://firebasestorage.googleapis.com/v0/b/aldeberan-emporium-firebase.appspot.com/o/products%2Fclassic_crecent.jpg?alt=media&token=52644a96-f311-4c5d-a659-d84786eb68f0'),
	(135, 85, 'CLASSIC CRESCENT', 'classic_crescent', 1, 2.00, 'https://firebasestorage.googleapis.com/v0/b/aldeberan-emporium-firebase.appspot.com/o/products%2Fclassic_crecent.jpg?alt=media&token=52644a96-f311-4c5d-a659-d84786eb68f0'),
	(145, 85, 'BUTTER CROISSANT', 'butter_croissant', 2, 2.00, 'https://firebasestorage.googleapis.com/v0/b/aldeberan-emporium-firebase.appspot.com/o/products%2Fraspberry_butter.jpg?alt=media&token=6e369671-6728-49bd-8b34-fcfbcfd122ad'),
	(155, 95, 'BUTTER CROISSANT', 'butter_croissant', 1, 2.00, 'https://firebasestorage.googleapis.com/v0/b/aldeberan-emporium-firebase.appspot.com/o/products%2Fraspberry_butter.jpg?alt=media&token=6e369671-6728-49bd-8b34-fcfbcfd122ad'),
	(165, 95, 'CLASSIC CRESCENT', 'classic_crescent', 1, 2.00, 'https://firebasestorage.googleapis.com/v0/b/aldeberan-emporium-firebase.appspot.com/o/products%2Fclassic_crecent.jpg?alt=media&token=52644a96-f311-4c5d-a659-d84786eb68f0'),
	(185, 115, 'GLAZE BUTTER CROISSANT', 'glaze_butter_croissant', 1, 3.60, 'https://firebasestorage.googleapis.com/v0/b/aldeberan-emporium-firebase.appspot.com/o/products%2Fglaze_butter_croissant.jpg?alt=media&token=c1157ef9-74ea-4919-b883-c5d592702779'),
	(195, 125, 'BUTTER CROISSANT', 'butter_croissant', 1, 2.00, 'https://firebasestorage.googleapis.com/v0/b/aldeberan-emporium-firebase.appspot.com/o/products%2Fraspberry_butter.jpg?alt=media&token=6e369671-6728-49bd-8b34-fcfbcfd122ad'),
	(205, 125, 'FOCACCIA LOAF', 'focaccia_loaf', 1, 5.20, 'https://firebasestorage.googleapis.com/v0/b/aldeberan-emporium-firebase.appspot.com/o/products%2Ffoccacia_loaf.jpg?alt=media&token=a483badb-906c-4eaf-b66d-e7b39c4e39ce'),
	(215, 135, 'ARTISAN BAGUETTE', 'artisan_baguette', 1, 2.00, 'https://firebasestorage.googleapis.com/v0/b/aldeberan-emporium-firebase.appspot.com/o/products%2Fartisan_baguette.jpg?alt=media&token=1bd53188-1fb7-4621-80b2-b779d9058c60'),
	(225, 145, 'ARTISAN BAGUETTE', 'artisan_baguette', 1, 2.00, 'https://firebasestorage.googleapis.com/v0/b/aldeberan-emporium-firebase.appspot.com/o/products%2Fartisan_baguette.jpg?alt=media&token=1bd53188-1fb7-4621-80b2-b779d9058c60'),
	(235, 155, 'FOCACCIA LOAF', 'focaccia_loaf', 1, 5.20, 'https://firebasestorage.googleapis.com/v0/b/aldeberan-emporium-firebase.appspot.com/o/products%2Ffoccacia_loaf.jpg?alt=media&token=a483badb-906c-4eaf-b66d-e7b39c4e39ce'),
	(295, 165, 'LUCKY ALMOND CRISP', 'lucky_almond_crisp', 1, 8.50, 'https://firebasestorage.googleapis.com/v0/b/aldeberan-emporium-firebase.appspot.com/o/products%2Flucky_almond_crisp.jpg?alt=media&token=0766f755-4c24-4443-95d7-cf119d2e2b9d'),
	(335, 175, 'LAVA BUTTER CROISSANT', 'lava_butter_croissant', 1, 3.60, 'https://firebasestorage.googleapis.com/v0/b/aldeberan-emporium-firebase.appspot.com/o/products%2Flava_butter_croissant.jpg?alt=media&token=e1d74293-9ab3-4586-9497-320cc8c9d1d7'),
	(345, 185, 'GOLDEN ROTI', 'golden_roti', 1, 5.80, 'https://firebasestorage.googleapis.com/v0/b/aldeberan-emporium-firebase.appspot.com/o/products%2Fgolden_roti_loaf.jpg?alt=media&token=d329b97b-7545-4df0-9d3c-cb95a8c7ae00'),
	(365, 195, 'BUTTER CROISSANT', 'butter_croissant', 1, 2.00, 'https://firebasestorage.googleapis.com/v0/b/aldeberan-emporium-firebase.appspot.com/o/products%2Fraspberry_butter.jpg?alt=media&token=6e369671-6728-49bd-8b34-fcfbcfd122ad'),
	(405, 65, 'MATCHA BUTTER CROISSANT', 'matcha_butter_croissant', 4, 2.40, 'https://firebasestorage.googleapis.com/v0/b/aldeberan-emporium-firebase.appspot.com/o/products%2Fmatcha_butter_croissant.jpg?alt=media&token=eec4dc80-d1a8-4a77-aae0-5ba1bc850548'),
	(415, 195, 'CLASSIC CRESCENT', 'classic_crescent', 1, 2.00, 'https://firebasestorage.googleapis.com/v0/b/aldeberan-emporium-firebase.appspot.com/o/products%2Fclassic_crecent.jpg?alt=media&token=52644a96-f311-4c5d-a659-d84786eb68f0'),
	(435, 205, 'CHEESE BUTTER CROISSANT', 'cheese_butter_croissant', 1, 3.60, 'https://firebasestorage.googleapis.com/v0/b/aldeberan-emporium-firebase.appspot.com/o/products%2Fcheese_butter_croissant.jpg?alt=media&token=8f24346a-b009-490a-a694-db72362bf23c'),
	(445, 195, 'FOCACCIA LOAF', 'focaccia_loaf', 1, 5.20, 'https://firebasestorage.googleapis.com/v0/b/aldeberan-emporium-firebase.appspot.com/o/products%2Ffoccacia_loaf.jpg?alt=media&token=a483badb-906c-4eaf-b66d-e7b39c4e39ce'),
	(465, 215, 'CLASSIC CRESCENT', 'classic_crescent', 11, 2.00, 'https://firebasestorage.googleapis.com/v0/b/aldeberan-emporium-firebase.appspot.com/o/products%2Fclassic_crecent.jpg?alt=media&token=52644a96-f311-4c5d-a659-d84786eb68f0'),
	(485, 215, 'BLACK SESAME CROISSANT', 'black_sesame_croissant', 3, 3.60, 'https://firebasestorage.googleapis.com/v0/b/aldeberan-emporium-firebase.appspot.com/o/products%2Fblack_sesame_croissant.jpg?alt=media&token=a15621df-b0d1-4428-a68f-27265ca1fa90'),
	(505, 215, 'BUTTER CROISSANT', 'butter_croissant', 8, 2.00, 'https://firebasestorage.googleapis.com/v0/b/aldeberan-emporium-firebase.appspot.com/o/products%2Fraspberry_butter.jpg?alt=media&token=6e369671-6728-49bd-8b34-fcfbcfd122ad'),
	(525, 215, 'FOCACCIA LOAF', 'focaccia_loaf', 25, 5.20, 'https://firebasestorage.googleapis.com/v0/b/aldeberan-emporium-firebase.appspot.com/o/products%2Ffoccacia_loaf.jpg?alt=media&token=a483badb-906c-4eaf-b66d-e7b39c4e39ce'),
	(535, 215, 'ARTISAN BAGUETTE', 'artisan_baguette', 14, 2.00, 'https://firebasestorage.googleapis.com/v0/b/aldeberan-emporium-firebase.appspot.com/o/products%2Fartisan_baguette.jpg?alt=media&token=1bd53188-1fb7-4621-80b2-b779d9058c60'),
	(545, 215, 'BLACK GOLD CROISSANT CHOC LAVA', 'black_gold_croissant_choc_lava', 1, 3.60, 'https://firebasestorage.googleapis.com/v0/b/aldeberan-emporium-firebase.appspot.com/o/products%2Fblack_gold.jpg?alt=media&token=b0f0a77f-4a11-442e-9a57-e1d8159a9657'),
	(555, 215, 'LAVA BUTTER CROISSANT', 'lava_butter_croissant', 1, 3.60, 'https://firebasestorage.googleapis.com/v0/b/aldeberan-emporium-firebase.appspot.com/o/products%2Flava_butter_croissant.jpg?alt=media&token=e1d74293-9ab3-4586-9497-320cc8c9d1d7'),
	(565, 215, 'GOLDEN ROTI', 'golden_roti', 1, 5.80, 'https://firebasestorage.googleapis.com/v0/b/aldeberan-emporium-firebase.appspot.com/o/products%2Fgolden_roti_loaf.jpg?alt=media&token=d329b97b-7545-4df0-9d3c-cb95a8c7ae00'),
	(575, 215, 'LUCKY ALMOND CRISP', 'lucky_almond_crisp', 1, 8.50, 'https://firebasestorage.googleapis.com/v0/b/aldeberan-emporium-firebase.appspot.com/o/products%2Flucky_almond_crisp.jpg?alt=media&token=0766f755-4c24-4443-95d7-cf119d2e2b9d'),
	(595, 215, 'COFFEE CROISSANT', 'coffee_croissant', 1, 2.40, 'https://firebasestorage.googleapis.com/v0/b/aldeberan-emporium-firebase.appspot.com/o/products%2Fcoffee_croissant.jpg?alt=media&token=9958a76e-2616-4203-8429-0e3a9cd72d62'),
	(605, 215, 'RASPBERRY CROISSANT', 'raspberry_croissant', 1, 2.40, 'https://firebasestorage.googleapis.com/v0/b/aldeberan-emporium-firebase.appspot.com/o/products%2Fraspberry_croissant.jpg?alt=media&token=ec38760e-862e-4ce3-8028-6bad75ec0292'),
	(615, 225, 'CHEESE BUTTER CROISSANT', 'cheese_butter_croissant', 1, 3.60, 'https://firebasestorage.googleapis.com/v0/b/aldeberan-emporium-firebase.appspot.com/o/products%2Fcheese_butter_croissant.jpg?alt=media&token=8f24346a-b009-490a-a694-db72362bf23c'),
	(625, 225, 'BLACK GOLD CROISSANT CHOC LAVA', 'black_gold_croissant_choc_lava', 1, 3.60, 'https://firebasestorage.googleapis.com/v0/b/aldeberan-emporium-firebase.appspot.com/o/products%2Fblack_gold.jpg?alt=media&token=b0f0a77f-4a11-442e-9a57-e1d8159a9657'),
	(635, 65, 'BUTTER CROISSANT', 'butter_croissant', 5, 2.00, 'https://firebasestorage.googleapis.com/v0/b/aldeberan-emporium-firebase.appspot.com/o/products%2Fraspberry_butter.jpg?alt=media&token=6e369671-6728-49bd-8b34-fcfbcfd122ad'),
	(665, 245, 'CLASSIC CRESCENT', 'classic_crescent', 4, 2.00, 'https://firebasestorage.googleapis.com/v0/b/aldeberan-emporium-firebase.appspot.com/o/products%2Fclassic_crecent.jpg?alt=media&token=52644a96-f311-4c5d-a659-d84786eb68f0'),
	(675, 245, 'MATCHA BUTTER CROISSANT', 'matcha_butter_croissant', 3, 2.40, 'https://firebasestorage.googleapis.com/v0/b/aldeberan-emporium-firebase.appspot.com/o/products%2Fmatcha_butter_croissant.jpg?alt=media&token=eec4dc80-d1a8-4a77-aae0-5ba1bc850548'),
	(685, 245, 'BUTTER CROISSANT', 'butter_croissant', 1, 2.00, 'https://firebasestorage.googleapis.com/v0/b/aldeberan-emporium-firebase.appspot.com/o/products%2Fraspberry_butter.jpg?alt=media&token=6e369671-6728-49bd-8b34-fcfbcfd122ad'),
	(695, 245, 'FOCACCIA LOAF', 'focaccia_loaf', 1, 5.20, 'https://firebasestorage.googleapis.com/v0/b/aldeberan-emporium-firebase.appspot.com/o/products%2Ffoccacia_loaf.jpg?alt=media&token=a483badb-906c-4eaf-b66d-e7b39c4e39ce'),
	(705, 245, 'ARTISAN BAGUETTE', 'artisan_baguette', 1, 2.00, 'https://firebasestorage.googleapis.com/v0/b/aldeberan-emporium-firebase.appspot.com/o/products%2Fartisan_baguette.jpg?alt=media&token=1bd53188-1fb7-4621-80b2-b779d9058c60'),
	(715, 105, 'BUTTER CROISSANT', 'butter_croissant', 2, 2.00, 'https://firebasestorage.googleapis.com/v0/b/aldeberan-emporium-firebase.appspot.com/o/products%2Fraspberry_butter.jpg?alt=media&token=6e369671-6728-49bd-8b34-fcfbcfd122ad'),
	(725, 105, 'CLASSIC CRESCENT', 'classic_crescent', 1, 2.00, 'https://firebasestorage.googleapis.com/v0/b/aldeberan-emporium-firebase.appspot.com/o/products%2Fclassic_crecent.jpg?alt=media&token=52644a96-f311-4c5d-a659-d84786eb68f0'),
	(735, 105, 'FOCACCIA LOAF', 'focaccia_loaf', 2, 5.20, 'https://firebasestorage.googleapis.com/v0/b/aldeberan-emporium-firebase.appspot.com/o/products%2Ffoccacia_loaf.jpg?alt=media&token=a483badb-906c-4eaf-b66d-e7b39c4e39ce'),
	(745, 105, 'ARTISAN BAGUETTE', 'artisan_baguette', 1, 2.00, 'https://firebasestorage.googleapis.com/v0/b/aldeberan-emporium-firebase.appspot.com/o/products%2Fartisan_baguette.jpg?alt=media&token=1bd53188-1fb7-4621-80b2-b779d9058c60'),
	(755, 105, 'MATCHA BUTTER CROISSANT', 'matcha_butter_croissant', 1, 2.40, 'https://firebasestorage.googleapis.com/v0/b/aldeberan-emporium-firebase.appspot.com/o/products%2Fmatcha_butter_croissant.jpg?alt=media&token=eec4dc80-d1a8-4a77-aae0-5ba1bc850548'),
	(765, 105, 'RASPBERRY CROISSANT', 'raspberry_croissant', 1, 2.40, 'https://firebasestorage.googleapis.com/v0/b/aldeberan-emporium-firebase.appspot.com/o/products%2Fraspberry_croissant.jpg?alt=media&token=ec38760e-862e-4ce3-8028-6bad75ec0292'),
	(775, 105, 'COFFEE CROISSANT', 'coffee_croissant', 1, 2.40, 'https://firebasestorage.googleapis.com/v0/b/aldeberan-emporium-firebase.appspot.com/o/products%2Fcoffee_croissant.jpg?alt=media&token=9958a76e-2616-4203-8429-0e3a9cd72d62'),
	(785, 105, 'BLACK GOLD CROISSANT CHOC LAVA', 'black_gold_croissant_choc_lava', 1, 3.60, 'https://firebasestorage.googleapis.com/v0/b/aldeberan-emporium-firebase.appspot.com/o/products%2Fblack_gold.jpg?alt=media&token=b0f0a77f-4a11-442e-9a57-e1d8159a9657'),
	(795, 105, 'BLACK SESAME CROISSANT', 'black_sesame_croissant', 1, 3.60, 'https://firebasestorage.googleapis.com/v0/b/aldeberan-emporium-firebase.appspot.com/o/products%2Fblack_sesame_croissant.jpg?alt=media&token=a15621df-b0d1-4428-a68f-27265ca1fa90'),
	(805, 105, 'CHEESE BUTTER CROISSANT', 'cheese_butter_croissant', 1, 3.60, 'https://firebasestorage.googleapis.com/v0/b/aldeberan-emporium-firebase.appspot.com/o/products%2Fcheese_butter_croissant.jpg?alt=media&token=8f24346a-b009-490a-a694-db72362bf23c'),
	(815, 105, 'GLAZE BUTTER CROISSANT', 'glaze_butter_croissant', 1, 3.60, 'https://firebasestorage.googleapis.com/v0/b/aldeberan-emporium-firebase.appspot.com/o/products%2Fglaze_butter_croissant.jpg?alt=media&token=c1157ef9-74ea-4919-b883-c5d592702779'),
	(825, 105, 'LAVA BUTTER CROISSANT', 'lava_butter_croissant', 1, 3.60, 'https://firebasestorage.googleapis.com/v0/b/aldeberan-emporium-firebase.appspot.com/o/products%2Flava_butter_croissant.jpg?alt=media&token=e1d74293-9ab3-4586-9497-320cc8c9d1d7'),
	(835, 65, 'ARTISAN BAGUETTE', 'artisan_baguette', 1, 2.00, 'https://firebasestorage.googleapis.com/v0/b/aldeberan-emporium-firebase.appspot.com/o/products%2Fartisan_baguette.jpg?alt=media&token=1bd53188-1fb7-4621-80b2-b779d9058c60'),
	(845, 65, 'FOCACCIA LOAF', 'focaccia_loaf', 1, 5.20, 'https://firebasestorage.googleapis.com/v0/b/aldeberan-emporium-firebase.appspot.com/o/products%2Ffoccacia_loaf.jpg?alt=media&token=a483badb-906c-4eaf-b66d-e7b39c4e39ce'),
	(855, 65, 'COFFEE CROISSANT', 'coffee_croissant', 1, 2.40, 'https://firebasestorage.googleapis.com/v0/b/aldeberan-emporium-firebase.appspot.com/o/products%2Fcoffee_croissant.jpg?alt=media&token=9958a76e-2616-4203-8429-0e3a9cd72d62'),
	(865, 65, 'MINI RASPBERRY BLOOMS', 'mini_raspberry_blooms', 1, 2.40, 'https://firebasestorage.googleapis.com/v0/b/aldeberan-emporium-firebase.appspot.com/o/products%2Fmini_raspberry_bloom.jpg?alt=media&token=efe01357-63b0-4453-85cc-23f0d3d13d0c'),
	(875, 65, 'LUCKY ALMOND CRISP', 'lucky_almond_crisp', 1, 8.50, 'https://firebasestorage.googleapis.com/v0/b/aldeberan-emporium-firebase.appspot.com/o/products%2Flucky_almond_crisp.jpg?alt=media&token=0766f755-4c24-4443-95d7-cf119d2e2b9d'),
	(885, 65, 'GOLDEN ROTI', 'golden_roti', 1, 5.80, 'https://firebasestorage.googleapis.com/v0/b/aldeberan-emporium-firebase.appspot.com/o/products%2Fgolden_roti_loaf.jpg?alt=media&token=d329b97b-7545-4df0-9d3c-cb95a8c7ae00'),
	(895, 65, 'GLAZE BUTTER CROISSANT', 'glaze_butter_croissant', 1, 3.60, 'https://firebasestorage.googleapis.com/v0/b/aldeberan-emporium-firebase.appspot.com/o/products%2Fglaze_butter_croissant.jpg?alt=media&token=c1157ef9-74ea-4919-b883-c5d592702779'),
	(905, 65, 'CHEESE BUTTER CROISSANT', 'cheese_butter_croissant', 1, 3.60, 'https://firebasestorage.googleapis.com/v0/b/aldeberan-emporium-firebase.appspot.com/o/products%2Fcheese_butter_croissant.jpg?alt=media&token=8f24346a-b009-490a-a694-db72362bf23c'),
	(915, 65, 'BLACK SESAME CROISSANT', 'black_sesame_croissant', 1, 3.60, 'https://firebasestorage.googleapis.com/v0/b/aldeberan-emporium-firebase.appspot.com/o/products%2Fblack_sesame_croissant.jpg?alt=media&token=a15621df-b0d1-4428-a68f-27265ca1fa90'),
	(925, 65, 'BLACK GOLD CROISSANT CHOC LAVA', 'black_gold_croissant_choc_lava', 1, 3.60, 'https://firebasestorage.googleapis.com/v0/b/aldeberan-emporium-firebase.appspot.com/o/products%2Fblack_gold.jpg?alt=media&token=b0f0a77f-4a11-442e-9a57-e1d8159a9657'),
	(935, 225, 'FOCACCIA LOAF', 'focaccia_loaf', 1, 5.20, 'https://firebasestorage.googleapis.com/v0/b/aldeberan-emporium-firebase.appspot.com/o/products%2Ffoccacia_loaf.jpg?alt=media&token=a483badb-906c-4eaf-b66d-e7b39c4e39ce'),
	(945, 225, 'CLASSIC CRESCENT', 'classic_crescent', 1, 2.00, 'https://firebasestorage.googleapis.com/v0/b/aldeberan-emporium-firebase.appspot.com/o/products%2Fclassic_crecent.jpg?alt=media&token=52644a96-f311-4c5d-a659-d84786eb68f0'),
	(955, 235, 'BUTTER CROISSANT', 'butter_croissant', 4, 2.00, 'https://firebasestorage.googleapis.com/v0/b/aldeberan-emporium-firebase.appspot.com/o/products%2Fraspberry_butter.jpg?alt=media&token=6e369671-6728-49bd-8b34-fcfbcfd122ad'),
	(965, 235, 'FOCACCIA LOAF', 'focaccia_loaf', 5, 5.20, 'https://firebasestorage.googleapis.com/v0/b/aldeberan-emporium-firebase.appspot.com/o/products%2Ffoccacia_loaf.jpg?alt=media&token=a483badb-906c-4eaf-b66d-e7b39c4e39ce'),
	(975, 235, 'ARTISAN BAGUETTE', 'artisan_baguette', 2, 2.00, 'https://firebasestorage.googleapis.com/v0/b/aldeberan-emporium-firebase.appspot.com/o/products%2Fartisan_baguette.jpg?alt=media&token=1bd53188-1fb7-4621-80b2-b779d9058c60'),
	(985, 235, 'CLASSIC CRESCENT', 'classic_crescent', 1, 2.00, 'https://firebasestorage.googleapis.com/v0/b/aldeberan-emporium-firebase.appspot.com/o/products%2Fclassic_crecent.jpg?alt=media&token=52644a96-f311-4c5d-a659-d84786eb68f0'),
	(995, 235, 'BLACK SESAME CROISSANT', 'black_sesame_croissant', 1, 3.60, 'https://firebasestorage.googleapis.com/v0/b/aldeberan-emporium-firebase.appspot.com/o/products%2Fblack_sesame_croissant.jpg?alt=media&token=a15621df-b0d1-4428-a68f-27265ca1fa90'),
	(1005, 235, 'LAVA BUTTER CROISSANT', 'lava_butter_croissant', 1, 3.60, 'https://firebasestorage.googleapis.com/v0/b/aldeberan-emporium-firebase.appspot.com/o/products%2Flava_butter_croissant.jpg?alt=media&token=e1d74293-9ab3-4586-9497-320cc8c9d1d7'),
	(1015, 235, 'GOLDEN ROTI', 'golden_roti', 1, 5.80, 'https://firebasestorage.googleapis.com/v0/b/aldeberan-emporium-firebase.appspot.com/o/products%2Fgolden_roti_loaf.jpg?alt=media&token=d329b97b-7545-4df0-9d3c-cb95a8c7ae00'),
	(1025, 235, 'CHEESE BUTTER CROISSANT', 'cheese_butter_croissant', 1, 3.60, 'https://firebasestorage.googleapis.com/v0/b/aldeberan-emporium-firebase.appspot.com/o/products%2Fcheese_butter_croissant.jpg?alt=media&token=8f24346a-b009-490a-a694-db72362bf23c'),
	(1035, 235, 'BLACK GOLD CROISSANT CHOC LAVA', 'black_gold_croissant_choc_lava', 1, 3.60, 'https://firebasestorage.googleapis.com/v0/b/aldeberan-emporium-firebase.appspot.com/o/products%2Fblack_gold.jpg?alt=media&token=b0f0a77f-4a11-442e-9a57-e1d8159a9657'),
	(1045, 235, 'GLAZE BUTTER CROISSANT', 'glaze_butter_croissant', 1, 3.60, 'https://firebasestorage.googleapis.com/v0/b/aldeberan-emporium-firebase.appspot.com/o/products%2Fglaze_butter_croissant.jpg?alt=media&token=c1157ef9-74ea-4919-b883-c5d592702779'),
	(1055, 235, 'COFFEE CROISSANT', 'coffee_croissant', 1, 2.40, 'https://firebasestorage.googleapis.com/v0/b/aldeberan-emporium-firebase.appspot.com/o/products%2Fcoffee_croissant.jpg?alt=media&token=9958a76e-2616-4203-8429-0e3a9cd72d62'),
	(1065, 235, 'LUCKY ALMOND CRISP', 'lucky_almond_crisp', 1, 8.50, 'https://firebasestorage.googleapis.com/v0/b/aldeberan-emporium-firebase.appspot.com/o/products%2Flucky_almond_crisp.jpg?alt=media&token=0766f755-4c24-4443-95d7-cf119d2e2b9d'),
	(1075, 235, 'RASPBERRY CROISSANT', 'raspberry_croissant', 1, 2.40, 'https://firebasestorage.googleapis.com/v0/b/aldeberan-emporium-firebase.appspot.com/o/products%2Fraspberry_croissant.jpg?alt=media&token=ec38760e-862e-4ce3-8028-6bad75ec0292'),
	(1085, 235, 'MATCHA BUTTER CROISSANT', 'matcha_butter_croissant', 1, 2.40, 'https://firebasestorage.googleapis.com/v0/b/aldeberan-emporium-firebase.appspot.com/o/products%2Fmatcha_butter_croissant.jpg?alt=media&token=eec4dc80-d1a8-4a77-aae0-5ba1bc850548'),
	(1105, 245, 'BLACK GOLD CROISSANT CHOC LAVA', 'black_gold_croissant_choc_lava', 1, 3.60, 'https://firebasestorage.googleapis.com/v0/b/aldeberan-emporium-firebase.appspot.com/o/products%2Fblack_gold.jpg?alt=media&token=b0f0a77f-4a11-442e-9a57-e1d8159a9657'),
	(1115, 245, 'LUCKY ALMOND CRISP', 'lucky_almond_crisp', 1, 8.50, 'https://firebasestorage.googleapis.com/v0/b/aldeberan-emporium-firebase.appspot.com/o/products%2Flucky_almond_crisp.jpg?alt=media&token=0766f755-4c24-4443-95d7-cf119d2e2b9d'),
	(1125, 245, 'COFFEE CROISSANT', 'coffee_croissant', 1, 2.40, 'https://firebasestorage.googleapis.com/v0/b/aldeberan-emporium-firebase.appspot.com/o/products%2Fcoffee_croissant.jpg?alt=media&token=9958a76e-2616-4203-8429-0e3a9cd72d62'),
	(1135, 245, 'RASPBERRY CROISSANT', 'raspberry_croissant', 1, 2.40, 'https://firebasestorage.googleapis.com/v0/b/aldeberan-emporium-firebase.appspot.com/o/products%2Fraspberry_croissant.jpg?alt=media&token=ec38760e-862e-4ce3-8028-6bad75ec0292'),
	(1255, 285, 'FOCACCIA LOAF', 'focaccia_loaf', 1, 5.20, 'https://firebasestorage.googleapis.com/v0/b/aldeberan-emporium-firebase.appspot.com/o/products%2Ffoccacia_loaf.jpg?alt=media&token=a483badb-906c-4eaf-b66d-e7b39c4e39ce'),
	(1545, 305, 'FOCACCIA LOAF', 'focaccia_loaf', 1, 5.20, 'https://firebasestorage.googleapis.com/v0/b/aldeberan-emporium-firebase.appspot.com/o/products%2Ffoccacia_loaf.jpg?alt=media&token=a483badb-906c-4eaf-b66d-e7b39c4e39ce'),
	(1555, 285, 'ARTISAN BAGUETTE', 'artisan_baguette', 1, 2.00, 'https://firebasestorage.googleapis.com/v0/b/aldeberan-emporium-firebase.appspot.com/o/products%2Fartisan_baguette.jpg?alt=media&token=1bd53188-1fb7-4621-80b2-b779d9058c60'),
	(1565, 325, 'BUTTER CROISSANT', 'butter_croissant', 1, 2.00, 'https://firebasestorage.googleapis.com/v0/b/aldeberan-emporium-firebase.appspot.com/o/products%2Fraspberry_butter.jpg?alt=media&token=6e369671-6728-49bd-8b34-fcfbcfd122ad'),
	(1575, 335, 'ARTISAN BAGUETTE', 'artisan_baguette', 1, 2.00, 'https://firebasestorage.googleapis.com/v0/b/aldeberan-emporium-firebase.appspot.com/o/products%2Fartisan_baguette.jpg?alt=media&token=1bd53188-1fb7-4621-80b2-b779d9058c60'),
	(1585, 335, 'CLASSIC CRESCENT', 'classic_crescent', 1, 2.00, 'https://firebasestorage.googleapis.com/v0/b/aldeberan-emporium-firebase.appspot.com/o/products%2Fclassic_crecent.jpg?alt=media&token=52644a96-f311-4c5d-a659-d84786eb68f0'),
	(1595, 335, 'FOCACCIA LOAF', 'focaccia_loaf', 1, 5.20, 'https://firebasestorage.googleapis.com/v0/b/aldeberan-emporium-firebase.appspot.com/o/products%2Ffoccacia_loaf.jpg?alt=media&token=a483badb-906c-4eaf-b66d-e7b39c4e39ce'),
	(1615, 345, 'ARTISAN BAGUETTE', 'artisan_baguette', 1, 2.00, 'https://firebasestorage.googleapis.com/v0/b/aldeberan-emporium-firebase.appspot.com/o/products%2Fartisan_baguette.jpg?alt=media&token=1bd53188-1fb7-4621-80b2-b779d9058c60'),
	(1625, 355, 'BUTTER CROISSANT', 'butter_croissant', 1, 2.00, 'https://firebasestorage.googleapis.com/v0/b/aldeberan-emporium-firebase.appspot.com/o/products%2Fraspberry_butter.jpg?alt=media&token=6e369671-6728-49bd-8b34-fcfbcfd122ad'),
	(1635, 365, 'BUTTER CROISSANT', 'butter_croissant', 1, 2.00, 'https://firebasestorage.googleapis.com/v0/b/aldeberan-emporium-firebase.appspot.com/o/products%2Fraspberry_butter.jpg?alt=media&token=6e369671-6728-49bd-8b34-fcfbcfd122ad'),
	(1645, 375, 'BLACK GOLD CROISSANT CHOC LAVA', 'black_gold_croissant_choc_lava', 1, 3.60, 'https://firebasestorage.googleapis.com/v0/b/aldeberan-emporium-firebase.appspot.com/o/products%2Fblack_gold.jpg?alt=media&token=b0f0a77f-4a11-442e-9a57-e1d8159a9657'),
	(1655, 385, 'FOCACCIA LOAF', 'focaccia_loaf', 1, 5.20, 'https://firebasestorage.googleapis.com/v0/b/aldeberan-emporium-firebase.appspot.com/o/products%2Ffoccacia_loaf.jpg?alt=media&token=a483badb-906c-4eaf-b66d-e7b39c4e39ce'),
	(1665, 395, 'FOCACCIA LOAF', 'focaccia_loaf', 1, 5.20, 'https://firebasestorage.googleapis.com/v0/b/aldeberan-emporium-firebase.appspot.com/o/products%2Ffoccacia_loaf.jpg?alt=media&token=a483badb-906c-4eaf-b66d-e7b39c4e39ce'),
	(1675, 405, 'BUTTER CROISSANT', 'butter_croissant', 1, 2.00, 'https://firebasestorage.googleapis.com/v0/b/aldeberan-emporium-firebase.appspot.com/o/products%2Fraspberry_butter.jpg?alt=media&token=6e369671-6728-49bd-8b34-fcfbcfd122ad'),
	(1725, 305, 'BUTTER CROISSANT', 'butter_croissant', 1, 2.00, 'https://firebasestorage.googleapis.com/v0/b/aldeberan-emporium-firebase.appspot.com/o/products%2Fraspberry_butter.jpg?alt=media&token=6e369671-6728-49bd-8b34-fcfbcfd122ad'),
	(1735, 415, 'BUTTER CROISSANT', 'butter_croissant', 1, 2.00, 'https://firebasestorage.googleapis.com/v0/b/aldeberan-emporium-firebase.appspot.com/o/products%2Fraspberry_butter.jpg?alt=media&token=6e369671-6728-49bd-8b34-fcfbcfd122ad'),
	(1745, 425, 'BUTTER CROISSANT', 'butter_croissant', 1, 2.00, 'https://firebasestorage.googleapis.com/v0/b/aldeberan-emporium-firebase.appspot.com/o/products%2Fraspberry_butter.jpg?alt=media&token=6e369671-6728-49bd-8b34-fcfbcfd122ad'),
	(1755, 435, 'BUTTER CROISSANT', 'butter_croissant', 1, 2.00, 'https://firebasestorage.googleapis.com/v0/b/aldeberan-emporium-firebase.appspot.com/o/products%2Fraspberry_butter.jpg?alt=media&token=6e369671-6728-49bd-8b34-fcfbcfd122ad'),
	(1765, 445, 'BUTTER CROISSANT', 'butter_croissant', 1, 2.00, 'https://firebasestorage.googleapis.com/v0/b/aldeberan-emporium-firebase.appspot.com/o/products%2Fraspberry_butter.jpg?alt=media&token=6e369671-6728-49bd-8b34-fcfbcfd122ad'),
	(1775, 455, 'BUTTER CROISSANT', 'butter_croissant', 1, 2.00, 'https://firebasestorage.googleapis.com/v0/b/aldeberan-emporium-firebase.appspot.com/o/products%2Fraspberry_butter.jpg?alt=media&token=6e369671-6728-49bd-8b34-fcfbcfd122ad'),
	(1785, 465, 'BUTTER CROISSANT', 'butter_croissant', 1, 2.00, 'https://firebasestorage.googleapis.com/v0/b/aldeberan-emporium-firebase.appspot.com/o/products%2Fraspberry_butter.jpg?alt=media&token=6e369671-6728-49bd-8b34-fcfbcfd122ad'),
	(1795, 475, 'BUTTER CROISSANT', 'butter_croissant', 1, 2.00, 'https://firebasestorage.googleapis.com/v0/b/aldeberan-emporium-firebase.appspot.com/o/products%2Fraspberry_butter.jpg?alt=media&token=6e369671-6728-49bd-8b34-fcfbcfd122ad'),
	(1805, 485, 'BUTTER CROISSANT', 'butter_croissant', 1, 2.00, 'https://firebasestorage.googleapis.com/v0/b/aldeberan-emporium-firebase.appspot.com/o/products%2Fraspberry_butter.jpg?alt=media&token=6e369671-6728-49bd-8b34-fcfbcfd122ad'),
	(1815, 495, 'BUTTER CROISSANT', 'butter_croissant', 1, 2.00, 'https://firebasestorage.googleapis.com/v0/b/aldeberan-emporium-firebase.appspot.com/o/products%2Fraspberry_butter.jpg?alt=media&token=6e369671-6728-49bd-8b34-fcfbcfd122ad'),
	(1825, 505, 'BUTTER CROISSANT', 'butter_croissant', 1, 2.00, 'https://firebasestorage.googleapis.com/v0/b/aldeberan-emporium-firebase.appspot.com/o/products%2Fraspberry_butter.jpg?alt=media&token=6e369671-6728-49bd-8b34-fcfbcfd122ad'),
	(1835, 515, 'BUTTER CROISSANT', 'butter_croissant', 1, 2.00, 'https://firebasestorage.googleapis.com/v0/b/aldeberan-emporium-firebase.appspot.com/o/products%2Fraspberry_butter.jpg?alt=media&token=6e369671-6728-49bd-8b34-fcfbcfd122ad'),
	(1845, 535, 'BUTTER CROISSANT', 'butter_croissant', 1, 2.00, 'https://firebasestorage.googleapis.com/v0/b/aldeberan-emporium-firebase.appspot.com/o/products%2Fraspberry_butter.jpg?alt=media&token=6e369671-6728-49bd-8b34-fcfbcfd122ad'),
	(1855, 555, 'ARTISAN BAGUETTE', 'artisan_baguette', 1, 2.00, 'https://firebasestorage.googleapis.com/v0/b/aldeberan-emporium-firebase.appspot.com/o/products%2Fartisan_baguette.jpg?alt=media&token=1bd53188-1fb7-4621-80b2-b779d9058c60'),
	(1885, 565, 'BUTTER CROISSANT', 'butter_croissant', 1, 2.00, 'https://firebasestorage.googleapis.com/v0/b/aldeberan-emporium-firebase.appspot.com/o/products%2Fraspberry_butter.jpg?alt=media&token=6e369671-6728-49bd-8b34-fcfbcfd122ad'),
	(1895, 565, 'BLACK GOLD CROISSANT CHOC LAVA', 'black_gold_croissant_choc_lava', 1, 3.60, 'https://firebasestorage.googleapis.com/v0/b/aldeberan-emporium-firebase.appspot.com/o/products%2Fblack_gold.jpg?alt=media&token=b0f0a77f-4a11-442e-9a57-e1d8159a9657'),
	(1905, 565, 'BLACK SESAME CROISSANT', 'black_sesame_croissant', 1, 3.60, 'https://firebasestorage.googleapis.com/v0/b/aldeberan-emporium-firebase.appspot.com/o/products%2Fblack_sesame_croissant.jpg?alt=media&token=a15621df-b0d1-4428-a68f-27265ca1fa90'),
	(1965, 575, 'BLACK GOLD CROISSANT CHOC LAVA', 'black_gold_croissant_choc_lava', 1, 3.60, 'https://firebasestorage.googleapis.com/v0/b/aldeberan-emporium-firebase.appspot.com/o/products%2Fblack_gold.jpg?alt=media&token=b0f0a77f-4a11-442e-9a57-e1d8159a9657'),
	(1975, 575, 'BLACK SESAME CROISSANT', 'black_sesame_croissant', 1, 3.60, 'https://firebasestorage.googleapis.com/v0/b/aldeberan-emporium-firebase.appspot.com/o/products%2Fblack_sesame_croissant.jpg?alt=media&token=a15621df-b0d1-4428-a68f-27265ca1fa90'),
	(2015, 545, 'CLASSIC CRESCENT', 'classic_crescent', 1, 2.00, 'https://firebasestorage.googleapis.com/v0/b/aldeberan-emporium-firebase.appspot.com/o/products%2Fclassic_crecent.jpg?alt=media&token=52644a96-f311-4c5d-a659-d84786eb68f0');
/*!40000 ALTER TABLE `quote_item` ENABLE KEYS */;

-- Dumping structure for view heroku_340a0634a49f183.v_countorderitem
-- Creating temporary table to overcome VIEW dependency errors
CREATE TABLE `v_countorderitem` (
	`order_id` INT(11) NULL,
	`total_items` BIGINT(21) NOT NULL
) ENGINE=MyISAM;

-- Dumping structure for table heroku_340a0634a49f183.wishlist
CREATE TABLE IF NOT EXISTS `wishlist` (
  `wishlist_id` int(11) NOT NULL AUTO_INCREMENT,
  `user_id` varchar(35) NOT NULL,
  `product_id` int(11) DEFAULT NULL,
  PRIMARY KEY (`wishlist_id`)
) ENGINE=InnoDB AUTO_INCREMENT=505 DEFAULT CHARSET=utf8;

-- Dumping data for table heroku_340a0634a49f183.wishlist: ~3 rows (approximately)
/*!40000 ALTER TABLE `wishlist` DISABLE KEYS */;
INSERT INTO `wishlist` (`wishlist_id`, `user_id`, `product_id`) VALUES
	(465, '5dJ3jFfUgIYFkX7jUcRZ8s54DoB3', 4),
	(475, '5dJ3jFfUgIYFkX7jUcRZ8s54DoB3', 3),
	(485, '5dJ3jFfUgIYFkX7jUcRZ8s54DoB3', 2);
/*!40000 ALTER TABLE `wishlist` ENABLE KEYS */;

-- Dumping structure for view heroku_340a0634a49f183.v_countorderitem
-- Removing temporary table and create final VIEW structure
DROP TABLE IF EXISTS `v_countorderitem`;
CREATE ALGORITHM=UNDEFINED SQL SECURITY DEFINER VIEW `v_countorderitem` AS select `o`.`order_id` AS `order_id`,count(0) AS `total_items` from (`order_item` `oi` left join `orders` `o` on((`o`.`order_id` = `oi`.`order_id`))) group by `o`.`order_id`;

/*!40101 SET SQL_MODE=IFNULL(@OLD_SQL_MODE, '') */;
/*!40014 SET FOREIGN_KEY_CHECKS=IFNULL(@OLD_FOREIGN_KEY_CHECKS, 1) */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40111 SET SQL_NOTES=IFNULL(@OLD_SQL_NOTES, 1) */;
