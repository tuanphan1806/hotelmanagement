-- MySQL dump 10.13  Distrib 8.0.35, for Win64 (x86_64)
--
-- Host: localhost    Database: hotelmanagement
-- ------------------------------------------------------
-- Server version	8.0.35

/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!50503 SET NAMES utf8mb4 */;
/*!40103 SET @OLD_TIME_ZONE=@@TIME_ZONE */;
/*!40103 SET TIME_ZONE='+00:00' */;
/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;

--
-- Table structure for table `chat_messages`
--

DROP TABLE IF EXISTS `chat_messages`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `chat_messages` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `sender` varchar(255) DEFAULT NULL,
  `session_id` varchar(255) DEFAULT NULL,
  `text` text,
  `timestamp` datetime(6) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=7 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `chat_messages`
--

LOCK TABLES `chat_messages` WRITE;
/*!40000 ALTER TABLE `chat_messages` DISABLE KEYS */;
INSERT INTO `chat_messages` VALUES (1,'customer','SESSION-001','Xin chào, tôi muốn hỏi về phòng còn trống?','2026-06-30 02:29:36.000000'),(2,'bot','SESSION-001','Xin chào! Hiện tại chúng tôi còn nhiều phòng trống. Bạn muốn đặt từ ngày nào?','2026-06-30 02:29:36.000000'),(3,'customer','SESSION-001','Tôi muốn đặt từ 10/07 đến 13/07 cho 2 người.','2026-06-30 02:29:36.000000'),(4,'bot','SESSION-001','Chúng tôi có phòng Standard (800k/đêm) và Deluxe (1.5tr/đêm) phù hợp cho 2 người.','2026-06-30 02:29:36.000000'),(5,'customer','SESSION-002','Khách sạn có bãi đỗ xe không?','2026-06-30 02:29:36.000000'),(6,'bot','SESSION-002','Dạ có! Chúng tôi có bãi đỗ xe miễn phí cho khách lưu trú.','2026-06-30 02:29:36.000000');
/*!40000 ALTER TABLE `chat_messages` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `facilities`
--

DROP TABLE IF EXISTS `facilities`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `facilities` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `created_at` datetime(6) DEFAULT NULL,
  `updated_at` datetime(6) DEFAULT NULL,
  `description` text,
  `facility_name` varchar(255) DEFAULT NULL,
  `image_url` varchar(500) DEFAULT NULL,
  `type` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=11 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `facilities`
--

LOCK TABLES `facilities` WRITE;
/*!40000 ALTER TABLE `facilities` DISABLE KEYS */;
INSERT INTO `facilities` VALUES (1,'2026-06-30 02:29:36.000000','2026-06-30 02:29:36.000000','Điều hòa nhiệt độ 2 chiều inverter','Điều hòa','https://cdn.luxstay.vn/facilities/aircon.jpg','ROOM'),(2,'2026-06-30 02:29:36.000000','2026-06-30 02:29:36.000000','TV màn hình phẳng 55 inch 4K','Smart TV 55\"','https://cdn.luxstay.vn/facilities/tv.jpg','ROOM'),(3,'2026-06-30 02:29:36.000000','2026-06-30 02:29:36.000000','Minibar với đồ uống và snack','Minibar','https://cdn.luxstay.vn/facilities/minibar.jpg','ROOM'),(4,'2026-06-30 02:29:36.000000','2026-06-30 02:29:36.000000','Bồn tắm riêng','Bồn tắm','https://cdn.luxstay.vn/facilities/bathtub.jpg','ROOM'),(5,'2026-06-30 02:29:36.000000','2026-06-30 02:29:36.000000','WiFi tốc độ cao 1Gbps','WiFi tốc độ cao','https://cdn.luxstay.vn/facilities/wifi.jpg','ROOM'),(6,'2026-06-30 02:29:36.000000','2026-06-30 02:29:36.000000','Két an toàn trong phòng','Két an toàn','https://cdn.luxstay.vn/facilities/safe.jpg','ROOM'),(7,'2026-06-30 02:29:36.000000','2026-06-30 02:29:36.000000','Hồ bơi ngoài trời tầng thượng','Hồ bơi','https://cdn.luxstay.vn/facilities/pool.jpg','HOTEL'),(8,'2026-06-30 02:29:36.000000','2026-06-30 02:29:36.000000','Phòng gym hiện đại 24/7','Phòng Gym','https://cdn.luxstay.vn/facilities/gym.jpg','HOTEL'),(9,'2026-06-30 02:29:36.000000','2026-06-30 02:29:36.000000','Spa và massage cao cấp','Spa & Massage','https://cdn.luxstay.vn/facilities/spa.jpg','HOTEL'),(10,'2026-06-30 02:29:36.000000','2026-06-30 02:29:36.000000','Nhà hàng phục vụ 3 bữa','Nhà hàng','https://cdn.luxstay.vn/facilities/restaurant.jpg','HOTEL');
/*!40000 ALTER TABLE `facilities` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `galleries`
--

DROP TABLE IF EXISTS `galleries`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `galleries` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `created_at` datetime(6) DEFAULT NULL,
  `updated_at` datetime(6) DEFAULT NULL,
  `image_url` varchar(500) DEFAULT NULL,
  `title` varchar(255) DEFAULT NULL,
  `type` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=9 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `galleries`
--

LOCK TABLES `galleries` WRITE;
/*!40000 ALTER TABLE `galleries` DISABLE KEYS */;
INSERT INTO `galleries` VALUES (1,'2026-06-30 02:29:36.000000','2026-06-30 02:29:36.000000','https://cdn.luxstay.vn/gallery/lobby.jpg','Sảnh Khách Sạn','HOTEL'),(2,'2026-06-30 02:29:36.000000','2026-06-30 02:29:36.000000','https://cdn.luxstay.vn/gallery/pool.jpg','Hồ Bơi Tầng Thượng','HOTEL'),(3,'2026-06-30 02:29:36.000000','2026-06-30 02:29:36.000000','https://cdn.luxstay.vn/gallery/standard.jpg','Phòng Standard','ROOM'),(4,'2026-06-30 02:29:36.000000','2026-06-30 02:29:36.000000','https://cdn.luxstay.vn/gallery/deluxe.jpg','Phòng Deluxe','ROOM'),(5,'2026-06-30 02:29:36.000000','2026-06-30 02:29:36.000000','https://cdn.luxstay.vn/gallery/suite.jpg','Phòng Suite','ROOM'),(6,'2026-06-30 02:29:36.000000','2026-06-30 02:29:36.000000','https://cdn.luxstay.vn/gallery/restaurant.jpg','Nhà Hàng','HOTEL'),(7,'2026-06-30 02:29:36.000000','2026-06-30 02:29:36.000000','https://cdn.luxstay.vn/gallery/spa.jpg','Khu Spa','HOTEL'),(8,'2026-06-30 02:29:36.000000','2026-06-30 02:29:36.000000','https://cdn.luxstay.vn/gallery/presidential.jpg','Phòng Presidential Suite','ROOM');
/*!40000 ALTER TABLE `galleries` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `group_has_role`
--

DROP TABLE IF EXISTS `group_has_role`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `group_has_role` (
  `group_id` bigint NOT NULL,
  `role_id` bigint NOT NULL,
  PRIMARY KEY (`group_id`,`role_id`),
  KEY `FK5ayf1jgw937kcflrhnugtg2xh` (`role_id`),
  CONSTRAINT `FK5ayf1jgw937kcflrhnugtg2xh` FOREIGN KEY (`role_id`) REFERENCES `roles` (`id`),
  CONSTRAINT `FKsvenjxg4p96l9p4qvlwmaak43` FOREIGN KEY (`group_id`) REFERENCES `tbl_groups` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `group_has_role`
--

LOCK TABLES `group_has_role` WRITE;
/*!40000 ALTER TABLE `group_has_role` DISABLE KEYS */;
INSERT INTO `group_has_role` VALUES (1,1),(2,2),(3,3),(4,4);
/*!40000 ALTER TABLE `group_has_role` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `group_has_user`
--

DROP TABLE IF EXISTS `group_has_user`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `group_has_user` (
  `user_id` bigint NOT NULL,
  `group_id` bigint NOT NULL,
  PRIMARY KEY (`user_id`,`group_id`),
  KEY `FKjv6m9vvc8etoiclfvgnalh3hr` (`group_id`),
  CONSTRAINT `FKhr5ypg2t7gd3huwgl4tijtx4l` FOREIGN KEY (`user_id`) REFERENCES `users` (`id`),
  CONSTRAINT `FKjv6m9vvc8etoiclfvgnalh3hr` FOREIGN KEY (`group_id`) REFERENCES `tbl_groups` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `group_has_user`
--

LOCK TABLES `group_has_user` WRITE;
/*!40000 ALTER TABLE `group_has_user` DISABLE KEYS */;
INSERT INTO `group_has_user` VALUES (1,1),(2,2),(3,2),(8,3),(4,4),(5,4),(6,4),(9,4),(10,4);
/*!40000 ALTER TABLE `group_has_user` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `guests`
--

DROP TABLE IF EXISTS `guests`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `guests` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `created_at` datetime(6) DEFAULT NULL,
  `updated_at` datetime(6) DEFAULT NULL,
  `date_of_birth` date DEFAULT NULL,
  `email` varchar(255) DEFAULT NULL,
  `full_name` varchar(255) NOT NULL,
  `id_card_number` varchar(50) DEFAULT NULL,
  `id_card_type` enum('CCCD','CMND','PASSPORT') DEFAULT NULL,
  `is_primary` bit(1) DEFAULT NULL,
  `nationality` varchar(255) DEFAULT NULL,
  `phone` varchar(20) DEFAULT NULL,
  `reservation_room_id` bigint DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `FKe29g4uv5q14pn3v8togaxgj03` (`reservation_room_id`),
  CONSTRAINT `FKe29g4uv5q14pn3v8togaxgj03` FOREIGN KEY (`reservation_room_id`) REFERENCES `reservation_rooms` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=12 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `guests`
--

LOCK TABLES `guests` WRITE;
/*!40000 ALTER TABLE `guests` DISABLE KEYS */;
INSERT INTO `guests` VALUES (1,'2026-06-30 02:29:36.000000','2026-06-30 02:29:36.000000','1990-05-15','customer1@gmail.com','Phạm Thị Hoa','079090001234','CCCD',_binary '','Việt Nam','0901000004',1),(2,'2026-06-30 02:29:36.000000','2026-06-30 02:29:36.000000','1992-08-20',NULL,'Phạm Văn Nam','079092005678','CCCD',_binary '\0','Việt Nam','0901000011',1),(3,'2026-06-30 02:29:36.000000','2026-06-30 02:29:36.000000','1988-03-10',NULL,'Trần Thị Bình','079088009012','CCCD',_binary '','Việt Nam','0901000012',2),(4,'2026-06-30 02:29:36.000000','2026-06-30 02:29:36.000000','1985-11-25','customer2@gmail.com','Nguyễn Quốc Hùng','079085003456','CCCD',_binary '','Việt Nam','0901000005',3),(5,'2026-06-30 02:29:36.000000','2026-06-30 02:29:36.000000','1987-07-14',NULL,'Nguyễn Thị Lan','079087007890','CCCD',_binary '\0','Việt Nam','0901000013',3),(6,'2026-06-30 02:29:36.000000','2026-06-30 02:29:36.000000','1995-01-30','customer3@gmail.com','Vũ Thị Mai','079095001122','CCCD',_binary '','Việt Nam','0901000006',5),(7,'2026-06-30 02:29:36.000000','2026-06-30 02:29:36.000000','1993-09-08',NULL,'Vũ Văn Đức','079093003344','CCCD',_binary '\0','Việt Nam','0901000014',5),(8,'2026-06-30 02:29:36.000000','2026-06-30 02:29:36.000000','2000-12-05',NULL,'Vũ Ngọc An',NULL,NULL,_binary '\0','Việt Nam',NULL,5),(9,'2026-06-30 02:29:36.000000','2026-06-30 02:29:36.000000','1980-06-22','customer2@gmail.com','Nguyễn Quốc Hùng','079085003456','CCCD',_binary '','Việt Nam','0901000005',8),(10,'2026-06-30 02:29:36.000000','2026-06-30 02:29:36.000000','1982-04-18',NULL,'Nguyễn Thị Cúc','079082005566','CCCD',_binary '\0','Việt Nam','0901000015',8),(11,'2026-06-30 02:29:36.000000','2026-06-30 02:29:36.000000','2010-09-01',NULL,'Nguyễn Minh Khôi',NULL,NULL,_binary '\0','Việt Nam',NULL,8);
/*!40000 ALTER TABLE `guests` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `invalidated_tokens`
--

DROP TABLE IF EXISTS `invalidated_tokens`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `invalidated_tokens` (
  `token` varchar(255) NOT NULL,
  `expiry_time` datetime(6) DEFAULT NULL,
  `reason` varchar(50) DEFAULT NULL,
  PRIMARY KEY (`token`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `invalidated_tokens`
--

LOCK TABLES `invalidated_tokens` WRITE;
/*!40000 ALTER TABLE `invalidated_tokens` DISABLE KEYS */;
INSERT INTO `invalidated_tokens` VALUES ('06fa4495-87b9-4c0c-adf0-41e737a81167','2026-07-14 03:10:10.000000','LOGOUT'),('1a791d09-2020-4920-beae-4e8face55074','2026-07-14 02:46:16.000000','LOGOUT'),('3f86d2e4-0a68-47be-a062-8519dc6560d3','2026-07-14 02:38:49.000000','LOGOUT'),('96d7b9f6-7da9-4855-ac62-20c37de83c0f','2026-07-14 02:43:30.000000','LOGOUT'),('d8836fdb-5aa7-4a45-aa77-082ced30c2ff','2026-07-14 02:44:26.000000','LOGOUT'),('eyJhbGciOiJIUzI1NiJ9.old_token_1','2026-07-01 02:29:36.000000','LOGOUT'),('eyJhbGciOiJIUzI1NiJ9.old_token_2','2026-07-02 02:29:36.000000','SESSION_REPLACED'),('f194596c-c9bb-4831-9d0a-ad1f5e13074b','2026-07-14 02:46:48.000000','LOGOUT');
/*!40000 ALTER TABLE `invalidated_tokens` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `payments`
--

DROP TABLE IF EXISTS `payments`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `payments` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `created_at` datetime(6) DEFAULT NULL,
  `updated_at` datetime(6) DEFAULT NULL,
  `amount` decimal(12,2) NOT NULL,
  `paid_at` datetime(6) DEFAULT NULL,
  `payment_method` enum('BANKING','CASH','MOMO','VNPAY') NOT NULL,
  `payment_status` varchar(20) NOT NULL DEFAULT 'PENDING',
  `transaction_code` varchar(255) DEFAULT NULL,
  `reservation_id` bigint NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `UKe7qdxh4fch1yfisduker8j6w2` (`reservation_id`),
  UNIQUE KEY `UK8inpv30544qjykcwa6ck7pusy` (`transaction_code`),
  KEY `idx_payment_status` (`payment_status`),
  KEY `idx_payment_transaction_code` (`transaction_code`),
  CONSTRAINT `FKp8yh4sjt3u0g6aru1oxfh3o14` FOREIGN KEY (`reservation_id`) REFERENCES `reservations` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=7 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `payments`
--

LOCK TABLES `payments` WRITE;
/*!40000 ALTER TABLE `payments` DISABLE KEYS */;
INSERT INTO `payments` VALUES (1,'2026-06-30 02:29:36.000000','2026-06-30 02:29:36.000000',4740000.00,'2026-06-30 02:29:36.000000','BANKING','PAID','TXN-VCB-20260710-001',1),(2,'2026-06-30 02:29:36.000000','2026-06-30 02:29:36.000000',6465000.00,'2026-06-30 02:29:36.000000','VNPAY','PAID','TXN-VNPAY-20260715-002',2),(3,'2026-06-30 02:29:36.000000','2026-06-30 02:29:36.000000',9480000.00,'2026-06-30 02:29:36.000000','CASH','PAID','TXN-CASH-20260628-003',3),(4,'2026-06-30 02:29:36.000000','2026-06-30 02:29:36.000000',0.00,NULL,'BANKING','REFUNDED','TXN-VCB-20260705-004',4),(5,'2026-06-30 02:29:36.000000','2026-06-30 02:29:36.000000',21620000.00,NULL,'BANKING','PENDING',NULL,5),(6,'2026-06-30 02:29:36.000000','2026-06-30 02:29:36.000000',6952000.00,'2026-06-30 02:29:36.000000','MOMO','PAID','TXN-MOMO-20260625-006',6);
/*!40000 ALTER TABLE `payments` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `permissions`
--

DROP TABLE IF EXISTS `permissions`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `permissions` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `created_at` datetime(6) DEFAULT NULL,
  `updated_at` datetime(6) DEFAULT NULL,
  `name` varchar(100) NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `UKpnvtwliis6p05pn6i3ndjrqt2` (`name`)
) ENGINE=InnoDB AUTO_INCREMENT=18 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `permissions`
--

LOCK TABLES `permissions` WRITE;
/*!40000 ALTER TABLE `permissions` DISABLE KEYS */;
INSERT INTO `permissions` VALUES (1,'2026-06-30 02:29:36.000000','2026-06-30 02:29:36.000000','user:create'),(2,'2026-06-30 02:29:36.000000','2026-06-30 02:29:36.000000','user:read'),(3,'2026-06-30 02:29:36.000000','2026-06-30 02:29:36.000000','user:update'),(4,'2026-06-30 02:29:36.000000','2026-06-30 02:29:36.000000','user:delete'),(5,'2026-06-30 02:29:36.000000','2026-06-30 02:29:36.000000','room:create'),(6,'2026-06-30 02:29:36.000000','2026-06-30 02:29:36.000000','room:read'),(7,'2026-06-30 02:29:36.000000','2026-06-30 02:29:36.000000','room:update'),(8,'2026-06-30 02:29:36.000000','2026-06-30 02:29:36.000000','room:delete'),(9,'2026-06-30 02:29:36.000000','2026-06-30 02:29:36.000000','reservation:create'),(10,'2026-06-30 02:29:36.000000','2026-06-30 02:29:36.000000','reservation:read'),(11,'2026-06-30 02:29:36.000000','2026-06-30 02:29:36.000000','reservation:update'),(12,'2026-06-30 02:29:36.000000','2026-06-30 02:29:36.000000','reservation:delete'),(13,'2026-06-30 02:29:36.000000','2026-06-30 02:29:36.000000','payment:read'),(14,'2026-06-30 02:29:36.000000','2026-06-30 02:29:36.000000','payment:update'),(15,'2026-06-30 02:29:36.000000','2026-06-30 02:29:36.000000','review:create'),(16,'2026-06-30 02:29:36.000000','2026-06-30 02:29:36.000000','review:read'),(17,'2026-06-30 02:29:36.000000','2026-06-30 02:29:36.000000','review:delete');
/*!40000 ALTER TABLE `permissions` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `reservation_room_types`
--

DROP TABLE IF EXISTS `reservation_room_types`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `reservation_room_types` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `created_at` datetime(6) DEFAULT NULL,
  `updated_at` datetime(6) DEFAULT NULL,
  `quantity` int NOT NULL,
  `room_price` decimal(12,2) NOT NULL,
  `subtotal` decimal(12,2) NOT NULL,
  `reservation_id` bigint NOT NULL,
  `room_type_id` bigint NOT NULL,
  PRIMARY KEY (`id`),
  KEY `FK8lfgyecs06xlni10u8xs7a7fy` (`reservation_id`),
  KEY `FK5hoacrg2vv4p92ipxkqx588uk` (`room_type_id`),
  CONSTRAINT `FK5hoacrg2vv4p92ipxkqx588uk` FOREIGN KEY (`room_type_id`) REFERENCES `room_types` (`id`),
  CONSTRAINT `FK8lfgyecs06xlni10u8xs7a7fy` FOREIGN KEY (`reservation_id`) REFERENCES `reservations` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=13 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `reservation_room_types`
--

LOCK TABLES `reservation_room_types` WRITE;
/*!40000 ALTER TABLE `reservation_room_types` DISABLE KEYS */;
INSERT INTO `reservation_room_types` VALUES (1,'2026-06-30 02:29:36.000000','2026-06-30 02:29:36.000000',2,800000.00,4800000.00,1,1),(2,'2026-06-30 02:29:36.000000','2026-06-30 02:29:36.000000',1,1500000.00,4500000.00,2,2),(3,'2026-06-30 02:29:36.000000','2026-06-30 02:29:36.000000',1,800000.00,2400000.00,2,1),(4,'2026-06-30 02:29:36.000000','2026-06-30 02:29:36.000000',1,3000000.00,12000000.00,3,3),(5,'2026-06-30 02:29:36.000000','2026-06-30 02:29:36.000000',1,800000.00,1600000.00,4,1),(6,'2026-06-30 02:29:36.000000','2026-06-30 02:29:36.000000',1,8000000.00,40000000.00,5,5),(7,'2026-06-30 02:29:36.000000','2026-06-30 02:29:36.000000',1,1500000.00,6000000.00,6,2),(8,'2026-06-30 03:29:43.809687','2026-06-30 03:29:43.809687',1,800000.00,1600000.00,7,1),(9,'2026-06-30 03:42:04.037716','2026-06-30 03:42:04.037716',2,1500000.00,6000000.00,8,2),(10,'2026-06-30 03:42:04.044715','2026-06-30 03:42:04.044715',1,3000000.00,6000000.00,8,3),(11,'2026-06-30 04:13:40.175716','2026-06-30 04:13:40.175716',2,1500000.00,6000000.00,9,2),(12,'2026-06-30 04:13:40.186721','2026-06-30 04:13:40.186721',1,3000000.00,6000000.00,9,3);
/*!40000 ALTER TABLE `reservation_room_types` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `reservation_rooms`
--

DROP TABLE IF EXISTS `reservation_rooms`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `reservation_rooms` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `created_at` datetime(6) DEFAULT NULL,
  `updated_at` datetime(6) DEFAULT NULL,
  `status` varchar(20) NOT NULL DEFAULT 'PENDING_ASSIGN',
  `assigned_by` bigint DEFAULT NULL,
  `reservation_room_type_id` bigint NOT NULL,
  `room_id` bigint DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `FKjxjtc4mikk2upyppl46dxxt60` (`assigned_by`),
  KEY `FKe1fabohk3l28aa110p6kayr7y` (`reservation_room_type_id`),
  KEY `FK81imhuuxynjj2ivy82koy38nm` (`room_id`),
  CONSTRAINT `FK81imhuuxynjj2ivy82koy38nm` FOREIGN KEY (`room_id`) REFERENCES `rooms` (`id`),
  CONSTRAINT `FKe1fabohk3l28aa110p6kayr7y` FOREIGN KEY (`reservation_room_type_id`) REFERENCES `reservation_room_types` (`id`),
  CONSTRAINT `FKjxjtc4mikk2upyppl46dxxt60` FOREIGN KEY (`assigned_by`) REFERENCES `users` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=16 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `reservation_rooms`
--

LOCK TABLES `reservation_rooms` WRITE;
/*!40000 ALTER TABLE `reservation_rooms` DISABLE KEYS */;
INSERT INTO `reservation_rooms` VALUES (1,'2026-06-30 02:29:36.000000','2026-06-30 02:29:36.000000','ASSIGNED',2,1,1),(2,'2026-06-30 02:29:36.000000','2026-06-30 02:29:36.000000','ASSIGNED',2,1,2),(3,'2026-06-30 02:29:36.000000','2026-06-30 02:29:36.000000','ASSIGNED',2,2,6),(4,'2026-06-30 02:29:36.000000','2026-06-30 02:29:36.000000','ASSIGNED',2,3,5),(5,'2026-06-30 02:29:36.000000','2026-06-30 02:29:36.000000','CHECKED_IN',3,4,13),(6,'2026-06-30 02:29:36.000000','2026-06-30 02:29:36.000000','CANCELLED',NULL,5,NULL),(7,'2026-06-30 02:29:36.000000','2026-06-30 02:29:36.000000','ASSIGNED',2,6,16),(8,'2026-06-30 02:29:36.000000','2026-06-30 02:29:36.000000','CHECKED_OUT',3,7,8),(9,'2026-06-30 03:29:43.821629','2026-06-30 03:53:24.276825','ASSIGNED',NULL,8,1),(10,'2026-06-30 03:42:04.042714','2026-06-30 03:42:04.042714','PENDING_ASSIGN',NULL,9,NULL),(11,'2026-06-30 03:42:04.043714','2026-06-30 03:42:04.043714','PENDING_ASSIGN',NULL,9,NULL),(12,'2026-06-30 03:42:04.050724','2026-06-30 03:42:04.050724','PENDING_ASSIGN',NULL,10,NULL),(13,'2026-06-30 04:13:40.181722','2026-06-30 04:13:40.181722','PENDING_ASSIGN',NULL,11,NULL),(14,'2026-06-30 04:13:40.184720','2026-06-30 04:13:40.184720','PENDING_ASSIGN',NULL,11,NULL),(15,'2026-06-30 04:13:40.190719','2026-06-30 04:13:40.190719','PENDING_ASSIGN',NULL,12,NULL);
/*!40000 ALTER TABLE `reservation_rooms` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `reservations`
--

DROP TABLE IF EXISTS `reservations`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `reservations` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `created_at` datetime(6) DEFAULT NULL,
  `updated_at` datetime(6) DEFAULT NULL,
  `cancellation_reason` text,
  `check_in` datetime(6) NOT NULL,
  `check_out` datetime(6) NOT NULL,
  `discount_amount` decimal(12,2) NOT NULL,
  `guest_count` int DEFAULT NULL,
  `note` text,
  `reservation_code` varchar(50) NOT NULL,
  `status` enum('CANCELLED','CHECKED_IN','CHECKED_OUT','CONFIRMED','DRAFT') NOT NULL,
  `tax_amount` decimal(12,2) NOT NULL,
  `total_amount` decimal(12,2) NOT NULL,
  `customer_id` bigint NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `UK6g1uj544xgjuyhj6kjh6pka6l` (`reservation_code`),
  KEY `FKcmkyuub3ieebwbnrvh5710ply` (`customer_id`),
  CONSTRAINT `FKcmkyuub3ieebwbnrvh5710ply` FOREIGN KEY (`customer_id`) REFERENCES `users` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=10 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `reservations`
--

LOCK TABLES `reservations` WRITE;
/*!40000 ALTER TABLE `reservations` DISABLE KEYS */;
INSERT INTO `reservations` VALUES (1,'2026-06-30 02:29:36.000000','2026-06-30 04:00:29.700229','string','2026-07-10 14:00:00.000000','2026-07-13 12:00:00.000000',0.00,2,'Khách yêu cầu tầng cao','RES-2026-0001','CANCELLED',240000.00,4740000.00,4),(2,'2026-06-30 02:29:36.000000','2026-06-30 02:29:36.000000',NULL,'2026-07-15 14:00:00.000000','2026-07-18 12:00:00.000000',150000.00,4,'Thêm giường phụ nếu có','RES-2026-0002','CONFIRMED',315000.00,6465000.00,5),(3,'2026-06-30 02:29:36.000000','2026-06-30 02:29:36.000000',NULL,'2026-06-28 14:00:00.000000','2026-07-02 12:00:00.000000',0.00,2,NULL,'RES-2026-0003','CHECKED_IN',480000.00,9480000.00,6),(4,'2026-06-30 02:29:36.000000','2026-06-30 02:29:36.000000','Thay đổi kế hoạch du lịch','2026-07-05 14:00:00.000000','2026-07-07 12:00:00.000000',0.00,1,NULL,'RES-2026-0004','CANCELLED',0.00,0.00,9),(5,'2026-06-30 02:29:36.000000','2026-06-30 02:29:36.000000',NULL,'2026-07-20 14:00:00.000000','2026-07-25 12:00:00.000000',500000.00,2,'Phòng trăng mật, trang trí hoa','RES-2026-0005','CONFIRMED',1120000.00,21620000.00,4),(6,'2026-06-30 02:29:36.000000','2026-06-30 02:29:36.000000',NULL,'2026-06-25 14:00:00.000000','2026-06-29 12:00:00.000000',0.00,3,NULL,'RES-2026-0006','CHECKED_OUT',352000.00,6952000.00,5),(7,'2026-06-30 03:29:43.785391','2026-06-30 04:01:40.636410','string','2026-07-01 08:00:00.000000','2026-07-01 10:02:00.000000',0.00,1,'string','RES-48E5823B','CANCELLED',0.00,1600000.00,1),(8,'2026-06-30 03:42:04.023714','2026-06-30 03:44:23.651867',NULL,'2026-07-01 08:00:00.000000','2026-07-01 10:02:00.000000',0.00,1,'string','RES-9D1A4096','CONFIRMED',0.00,12000000.00,1),(9,'2026-06-30 04:13:40.172719','2026-06-30 04:28:46.295743','Hết thời gian giữ chỗ','2026-07-01 08:00:00.000000','2026-07-01 10:02:00.000000',0.00,1,'string','RES-F63585DD','CANCELLED',0.00,12000000.00,1);
/*!40000 ALTER TABLE `reservations` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `reviews`
--

DROP TABLE IF EXISTS `reviews`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `reviews` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `created_at` datetime(6) DEFAULT NULL,
  `updated_at` datetime(6) DEFAULT NULL,
  `comment` text,
  `rating` int NOT NULL,
  `reservation_id` bigint NOT NULL,
  `room_type_id` bigint NOT NULL,
  `user_id` bigint NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `uk_review_user_reservation` (`user_id`,`reservation_id`),
  KEY `idx_review_room_type` (`room_type_id`),
  KEY `idx_review_reservation` (`reservation_id`),
  CONSTRAINT `FK4mjgyh1vc99vaf9l18j2ejc89` FOREIGN KEY (`reservation_id`) REFERENCES `reservations` (`id`),
  CONSTRAINT `FKcgy7qjc1r99dp117y9en6lxye` FOREIGN KEY (`user_id`) REFERENCES `users` (`id`),
  CONSTRAINT `FKrsky1fnfdmm2chhfxvivf67hx` FOREIGN KEY (`room_type_id`) REFERENCES `room_types` (`id`),
  CONSTRAINT `reviews_chk_1` CHECK (((`rating` <= 5) and (`rating` >= 1)))
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `reviews`
--

LOCK TABLES `reviews` WRITE;
/*!40000 ALTER TABLE `reviews` DISABLE KEYS */;
INSERT INTO `reviews` VALUES (1,'2026-06-30 02:29:36.000000','2026-06-30 02:29:36.000000','Phòng sạch sẽ, nhân viên thân thiện. Sẽ quay lại lần sau!',5,6,2,5),(2,'2026-06-30 02:29:36.000000','2026-06-30 02:29:36.000000','Suite rất rộng, bồn tắm jacuzzi tuyệt vời. Dịch vụ 5 sao!',5,3,3,6);
/*!40000 ALTER TABLE `reviews` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `role_has_permission`
--

DROP TABLE IF EXISTS `role_has_permission`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `role_has_permission` (
  `role_id` bigint NOT NULL,
  `permission_id` bigint NOT NULL,
  PRIMARY KEY (`role_id`,`permission_id`),
  KEY `FK4tkb5h6g9725voio02abkw8cq` (`permission_id`),
  CONSTRAINT `FK4tkb5h6g9725voio02abkw8cq` FOREIGN KEY (`permission_id`) REFERENCES `permissions` (`id`),
  CONSTRAINT `FKl5t2ucrudh92ach6bddb918kp` FOREIGN KEY (`role_id`) REFERENCES `roles` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `role_has_permission`
--

LOCK TABLES `role_has_permission` WRITE;
/*!40000 ALTER TABLE `role_has_permission` DISABLE KEYS */;
INSERT INTO `role_has_permission` VALUES (1,1),(1,2),(1,3),(1,4),(1,5),(1,6),(2,6),(3,6),(4,6),(1,7),(3,7),(1,8),(1,9),(2,9),(4,9),(1,10),(2,10),(4,10),(1,11),(2,11),(1,12),(2,12),(1,13),(2,13),(1,14),(2,14),(1,15),(4,15),(1,16),(2,16),(4,16),(1,17);
/*!40000 ALTER TABLE `role_has_permission` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `roles`
--

DROP TABLE IF EXISTS `roles`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `roles` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `created_at` datetime(6) DEFAULT NULL,
  `updated_at` datetime(6) DEFAULT NULL,
  `name` varchar(50) NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `UKofx66keruapi6vyqpv6f2or37` (`name`)
) ENGINE=InnoDB AUTO_INCREMENT=5 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `roles`
--

LOCK TABLES `roles` WRITE;
/*!40000 ALTER TABLE `roles` DISABLE KEYS */;
INSERT INTO `roles` VALUES (1,'2026-06-30 02:29:36.000000','2026-06-30 02:29:36.000000','SUPER_ADMIN'),(2,'2026-06-30 02:29:36.000000','2026-06-30 02:29:36.000000','RECEPTIONIST'),(3,'2026-06-30 02:29:36.000000','2026-06-30 02:29:36.000000','HOUSEKEEPING'),(4,'2026-06-30 02:29:36.000000','2026-06-30 02:29:36.000000','CUSTOMER');
/*!40000 ALTER TABLE `roles` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `room_holds`
--

DROP TABLE IF EXISTS `room_holds`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `room_holds` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `created_at` datetime(6) DEFAULT NULL,
  `updated_at` datetime(6) DEFAULT NULL,
  `expires_at` datetime(6) NOT NULL,
  `status` varchar(20) NOT NULL DEFAULT 'ACTIVE',
  `reservation_room_type_id` bigint NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `UKk37e3w8x2ijfkjxsihv3gfmtj` (`reservation_room_type_id`),
  KEY `idx_room_hold_reservation_room_type` (`reservation_room_type_id`),
  KEY `idx_room_hold_status` (`status`),
  KEY `idx_room_hold_expires_at` (`expires_at`),
  CONSTRAINT `FKd8u26kdadc9mvp2ks95263pll` FOREIGN KEY (`reservation_room_type_id`) REFERENCES `reservation_room_types` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=10 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `room_holds`
--

LOCK TABLES `room_holds` WRITE;
/*!40000 ALTER TABLE `room_holds` DISABLE KEYS */;
INSERT INTO `room_holds` VALUES (1,'2026-06-30 02:29:36.000000','2026-06-30 02:29:36.000000','2026-06-30 02:44:36.000000','RELEASED',1),(2,'2026-06-30 02:29:36.000000','2026-06-30 02:29:36.000000','2026-06-30 02:44:36.000000','RELEASED',2),(3,'2026-06-30 02:29:36.000000','2026-06-30 02:29:36.000000','2026-06-30 02:44:36.000000','RELEASED',3),(4,'2026-06-30 02:29:36.000000','2026-06-30 02:29:36.000000','2026-07-20 02:29:36.000000','ACTIVE',6),(5,'2026-06-30 03:29:43.815625','2026-06-30 03:39:48.574542','2026-06-30 03:44:43.808591','CONVERTED',8),(6,'2026-06-30 03:42:04.040715','2026-06-30 03:44:23.654210','2026-06-30 03:57:04.037717','CONVERTED',9),(7,'2026-06-30 03:42:04.046714','2026-06-30 03:44:23.655219','2026-06-30 03:57:04.037717','CONVERTED',10),(8,'2026-06-30 04:13:40.178721','2026-06-30 04:28:46.274742','2026-06-30 04:28:40.174717','EXPIRED',11),(9,'2026-06-30 04:13:40.188721','2026-06-30 04:28:46.293743','2026-06-30 04:28:40.174717','EXPIRED',12);
/*!40000 ALTER TABLE `room_holds` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `room_type_facilities`
--

DROP TABLE IF EXISTS `room_type_facilities`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `room_type_facilities` (
  `room_type_id` bigint NOT NULL,
  `facility_id` bigint NOT NULL,
  PRIMARY KEY (`room_type_id`,`facility_id`),
  KEY `FK3x6quqddcy358kvmjjc9d53vx` (`facility_id`),
  CONSTRAINT `FK3x6quqddcy358kvmjjc9d53vx` FOREIGN KEY (`facility_id`) REFERENCES `facilities` (`id`),
  CONSTRAINT `FKhegiykepjecgokfw7rwqjlhnj` FOREIGN KEY (`room_type_id`) REFERENCES `room_types` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `room_type_facilities`
--

LOCK TABLES `room_type_facilities` WRITE;
/*!40000 ALTER TABLE `room_type_facilities` DISABLE KEYS */;
INSERT INTO `room_type_facilities` VALUES (1,1),(2,1),(3,1),(4,1),(5,1),(1,2),(2,2),(3,2),(4,2),(5,2),(2,3),(3,3),(4,3),(5,3),(3,4),(5,4),(1,5),(2,5),(3,5),(4,5),(5,5),(1,6),(2,6),(3,6),(4,6),(5,6);
/*!40000 ALTER TABLE `room_type_facilities` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `room_types`
--

DROP TABLE IF EXISTS `room_types`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `room_types` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `created_at` datetime(6) DEFAULT NULL,
  `updated_at` datetime(6) DEFAULT NULL,
  `description` text,
  `image_url` varchar(500) DEFAULT NULL,
  `price` decimal(12,2) DEFAULT NULL,
  `type_name` varchar(100) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=6 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `room_types`
--

LOCK TABLES `room_types` WRITE;
/*!40000 ALTER TABLE `room_types` DISABLE KEYS */;
INSERT INTO `room_types` VALUES (1,'2026-06-30 02:29:36.000000','2026-06-30 02:29:36.000000','Phòng Standard tiện nghi đầy đủ, phù hợp cho cặp đôi hoặc du khách đơn lẻ.','https://cdn.luxstay.vn/room-types/standard.jpg',800000.00,'Standard'),(2,'2026-06-30 02:29:36.000000','2026-06-30 02:29:36.000000','Phòng Deluxe rộng rãi với ban công view thành phố, nội thất sang trọng.','https://cdn.luxstay.vn/room-types/deluxe.jpg',1500000.00,'Deluxe'),(3,'2026-06-30 02:29:36.000000','2026-06-30 02:29:36.000000','Phòng Suite cao cấp với phòng khách riêng, bồn tắm jacuzzi và dịch vụ butler.','https://cdn.luxstay.vn/room-types/suite.jpg',3000000.00,'Suite'),(4,'2026-06-30 02:29:36.000000','2026-06-30 02:29:36.000000','Phòng Family rộng lớn thiết kế cho gia đình, có 2 phòng ngủ và bếp nhỏ.','https://cdn.luxstay.vn/room-types/family.jpg',2200000.00,'Family'),(5,'2026-06-30 02:29:36.000000','2026-06-30 02:29:36.000000','Presidential Suite sang trọng bậc nhất với tầm nhìn panoramic 360 độ.','https://cdn.luxstay.vn/room-types/presidential.jpg',8000000.00,'Presidential Suite');
/*!40000 ALTER TABLE `room_types` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `rooms`
--

DROP TABLE IF EXISTS `rooms`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `rooms` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `created_at` datetime(6) DEFAULT NULL,
  `updated_at` datetime(6) DEFAULT NULL,
  `cleaning_status` enum('CLEAN','DIRTY','IN_PROGRESS') DEFAULT NULL,
  `description` text,
  `floor` int DEFAULT NULL,
  `room_name` varchar(20) DEFAULT NULL,
  `status` enum('AVAILABLE','BOOKED','CHECKED_IN','MAINTENANCE') DEFAULT NULL,
  `room_type_id` bigint DEFAULT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `UK36daphag00mnxmwforqperdnb` (`room_name`),
  KEY `FKh9m2n1paq5hmd3u0klfl7wsfv` (`room_type_id`),
  CONSTRAINT `FKh9m2n1paq5hmd3u0klfl7wsfv` FOREIGN KEY (`room_type_id`) REFERENCES `room_types` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=17 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `rooms`
--

LOCK TABLES `rooms` WRITE;
/*!40000 ALTER TABLE `rooms` DISABLE KEYS */;
INSERT INTO `rooms` VALUES (1,'2026-06-30 02:29:36.000000','2026-06-30 02:29:36.000000','CLEAN','Phòng Standard hướng sân vườn',1,'101','AVAILABLE',1),(2,'2026-06-30 02:29:36.000000','2026-06-30 02:29:36.000000','CLEAN','Phòng Standard hướng sân vườn',1,'102','AVAILABLE',1),(3,'2026-06-30 02:29:36.000000','2026-06-30 02:29:36.000000','DIRTY','Phòng Standard hướng đường',1,'103','AVAILABLE',1),(4,'2026-06-30 02:29:36.000000','2026-06-30 02:29:36.000000','IN_PROGRESS','Phòng Standard hướng đường',1,'104','AVAILABLE',1),(5,'2026-06-30 02:29:36.000000','2026-06-30 02:29:36.000000','CLEAN','Phòng Standard tầng 2',2,'201','AVAILABLE',1),(6,'2026-06-30 02:29:36.000000','2026-06-30 02:29:36.000000','CLEAN','Phòng Deluxe view thành phố',2,'202','AVAILABLE',2),(7,'2026-06-30 02:29:36.000000','2026-06-30 02:29:36.000000','DIRTY','Phòng Deluxe view thành phố',2,'203','AVAILABLE',2),(8,'2026-06-30 02:29:36.000000','2026-06-30 02:29:36.000000','CLEAN','Phòng Deluxe góc ban công đôi',2,'204','AVAILABLE',2),(9,'2026-06-30 02:29:36.000000','2026-06-30 02:29:36.000000','CLEAN','Phòng Deluxe tầng 3',3,'301','AVAILABLE',2),(10,'2026-06-30 02:29:36.000000','2026-06-30 02:29:36.000000','CLEAN','Phòng Family 2 phòng ngủ',3,'302','AVAILABLE',4),(11,'2026-06-30 02:29:36.000000','2026-06-30 02:29:36.000000','DIRTY','Phòng Family view sân vườn',3,'303','AVAILABLE',4),(12,'2026-06-30 02:29:36.000000','2026-06-30 02:29:36.000000','CLEAN','Phòng Deluxe tầng 3',3,'304','AVAILABLE',2),(13,'2026-06-30 02:29:36.000000','2026-06-30 02:29:36.000000','CLEAN','Suite với bồn tắm jacuzzi',4,'401','AVAILABLE',3),(14,'2026-06-30 02:29:36.000000','2026-06-30 02:29:36.000000','CLEAN','Suite view thành phố',4,'402','AVAILABLE',3),(15,'2026-06-30 02:29:36.000000','2026-06-30 02:29:36.000000','IN_PROGRESS','Suite góc panoramic',4,'403','AVAILABLE',3),(16,'2026-06-30 02:29:36.000000','2026-06-30 02:29:36.000000','CLEAN','Presidential Suite tầng thượng',5,'501','AVAILABLE',5);
/*!40000 ALTER TABLE `rooms` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `tbl_groups`
--

DROP TABLE IF EXISTS `tbl_groups`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `tbl_groups` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `created_at` datetime(6) DEFAULT NULL,
  `updated_at` datetime(6) DEFAULT NULL,
  `name` varchar(100) NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `UKp4yaoo44agqma8unhiyjnvbns` (`name`)
) ENGINE=InnoDB AUTO_INCREMENT=5 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `tbl_groups`
--

LOCK TABLES `tbl_groups` WRITE;
/*!40000 ALTER TABLE `tbl_groups` DISABLE KEYS */;
INSERT INTO `tbl_groups` VALUES (1,'2026-06-30 02:29:36.000000','2026-06-30 02:29:36.000000','ADMIN_GROUP'),(2,'2026-06-30 02:29:36.000000','2026-06-30 02:29:36.000000','RECEPTIONIST_GROUP'),(3,'2026-06-30 02:29:36.000000','2026-06-30 02:29:36.000000','HOUSEKEEPING_GROUP'),(4,'2026-06-30 02:29:36.000000','2026-06-30 02:29:36.000000','CUSTOMER_GROUP');
/*!40000 ALTER TABLE `tbl_groups` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `user_tokens`
--

DROP TABLE IF EXISTS `user_tokens`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `user_tokens` (
  `user_id` bigint NOT NULL,
  `access_token` varchar(500) DEFAULT NULL,
  `created_at` datetime(6) DEFAULT NULL,
  `refresh_token` varchar(500) NOT NULL,
  PRIMARY KEY (`user_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `user_tokens`
--

LOCK TABLES `user_tokens` WRITE;
/*!40000 ALTER TABLE `user_tokens` DISABLE KEYS */;
INSERT INTO `user_tokens` VALUES (1,'572ca7b2-3c43-4e17-ba11-6b91ed08eb79','2026-06-30 04:10:28.754223','05e07147-982d-4418-9fae-1035446edfa4'),(4,'eyJhbGciOiJIUzI1NiJ9.cust1_access_sample','2026-06-30 02:29:36.000000','eyJhbGciOiJIUzI1NiJ9.cust1_refresh_sample'),(5,'eyJhbGciOiJIUzI1NiJ9.cust2_access_sample','2026-06-30 02:29:36.000000','eyJhbGciOiJIUzI1NiJ9.cust2_refresh_sample');
/*!40000 ALTER TABLE `user_tokens` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `users`
--

DROP TABLE IF EXISTS `users`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `users` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `created_at` datetime(6) DEFAULT NULL,
  `updated_at` datetime(6) DEFAULT NULL,
  `address` text,
  `email` varchar(255) NOT NULL,
  `email_verified` bit(1) NOT NULL,
  `full_name` varchar(255) NOT NULL,
  `image_url` varchar(500) DEFAULT NULL,
  `password` varchar(255) DEFAULT NULL,
  `phone` varchar(255) NOT NULL,
  `status` enum('ACTIVE','INACTIVE','PENDING_VERIFICATION') DEFAULT NULL,
  `type` enum('ADMIN','CUSTOMER','STAFF') DEFAULT NULL,
  `username` varchar(255) NOT NULL,
  `verification_code` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `UK6dotkott2kjsp8vw4d0m25fb7` (`email`),
  UNIQUE KEY `UKdu5v5sr43g5bfnji4vb8hg5s3` (`phone`),
  UNIQUE KEY `UKr43af9ap4edm43mmtq01oddj6` (`username`)
) ENGINE=InnoDB AUTO_INCREMENT=11 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `users`
--

LOCK TABLES `users` WRITE;
/*!40000 ALTER TABLE `users` DISABLE KEYS */;
INSERT INTO `users` VALUES (1,'2026-06-30 02:29:36.000000','2026-06-30 02:46:29.231558','12 Lý Thường Kiệt, Hà Nội','admin@luxstay.vn',_binary '','Nguyễn Văn Admin',NULL,'$2a$10$4f3j3/6QartC5lmxzwag5ej2NJ8xl1zxc6DhQlY8P15bFWUYnxtm2','0901000001','ACTIVE','ADMIN','admin',NULL),(2,'2026-06-30 02:29:36.000000','2026-06-30 02:43:50.017559','45 Trần Hưng Đạo, Hà Nội','staff1@luxstay.vn',_binary '','Trần Thị Lan',NULL,'$2a$10$jX5cqFbNYNwMXShCGf7GheHTrCLPN7WbJkEx/KXv7NYSioaViP6BO','0901000002','ACTIVE','STAFF','staff1',NULL),(3,'2026-06-30 02:29:36.000000','2026-06-30 02:44:46.503332','78 Nguyễn Huệ, TP.HCM','staff2@luxstay.vn',_binary '','Lê Văn Minh',NULL,'$2a$10$RoPqu/dKCeCazDOuVPUGNu2SL4swq6/hzJvYTRU4n7aESjclZLGHS','0901000003','ACTIVE','STAFF','staff2',NULL),(4,'2026-06-30 02:29:36.000000','2026-06-30 02:44:51.571595','23 Hoàng Diệu, Đà Nẵng','customer1@gmail.com',_binary '','Phạm Thị Hoa',NULL,'$2a$10$vznrAn0agqatE6qja.9oReBZ8whZOdH.HqEEgb6/9kW81v2sQCWCy','0901000004','ACTIVE','CUSTOMER','customer1',NULL),(5,'2026-06-30 02:29:36.000000','2026-06-30 02:44:56.033260','56 Lê Lợi, Hà Nội','customer2@gmail.com',_binary '','Nguyễn Quốc Hùng',NULL,'$2a$10$m5N3Dq4fPupSAZBEKMQiOeajNVQJVIaMeWsh.CfJolivzEb8Rgn1u','0901000005','ACTIVE','CUSTOMER','customer2',NULL),(6,'2026-06-30 02:29:36.000000','2026-06-30 02:45:00.791369','90 Pasteur, TP.HCM','customer3@gmail.com',_binary '','Vũ Thị Mai',NULL,'$2a$10$F7yWWEJPTJFrolP8KxCwOedwSD7Ok7uQwFRrEHs34elSls1ac31pC','0901000006','ACTIVE','CUSTOMER','vtmai',NULL),(7,'2026-06-30 02:29:36.000000','2026-06-30 02:45:05.364070','11 Đinh Tiên Hoàng, Hà Nội','customer4@gmail.com',_binary '\0','Đặng Văn Tùng',NULL,'$2a$10$KaaELMbcbs2A1PZgaDo2teEGre6qd/G5LL5qrFGjwn4UzOBXrdAUG','0901000007','ACTIVE','CUSTOMER','dvtung','VERIFY_ABC123'),(8,'2026-06-30 02:29:36.000000','2026-06-30 02:45:11.009606','33 Hai Bà Trưng, TP.HCM','staff3@luxstay.vn',_binary '','Hoàng Thị Thu',NULL,'$2a$10$vScuXFOfYvD5wlEVflbq..xIXiOI05cgbIf4bGA.BgPie6ChODNpS','0901000008','ACTIVE','STAFF','staff_thu',NULL),(9,'2026-06-30 02:29:36.000000','2026-06-30 02:45:15.583025','67 Phan Chu Trinh, Đà Nẵng','customer5@gmail.com',_binary '','Bùi Thị Ngọc',NULL,'$2a$10$X8NIGgJpMFgtJpFiLhKxJ.URKaZhAAwXy5LhWA5leeY7W2y.YKzBa','0901000009','ACTIVE','CUSTOMER','btngocc',NULL),(10,'2026-06-30 02:29:36.000000','2026-06-30 02:45:35.530703','44 Võ Văn Tần, TP.HCM','customer6@gmail.com',_binary '','Trương Văn Khoa',NULL,'$2a$10$MHPfc4XmjM9X2Ful31qNK.dA9d0YgDOhebcD5.93cUUkq4q/okKmC','0901000010','INACTIVE','CUSTOMER','tvkhoa',NULL);
/*!40000 ALTER TABLE `users` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Dumping routines for database 'hotelmanagement'
--
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2026-06-30 21:21:39
