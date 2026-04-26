-- MySQL dump 10.13  Distrib 8.0.45, for Win64 (x86_64)
--
-- Host: localhost    Database: banking_system
-- ------------------------------------------------------
-- Server version	8.0.45

/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!50503 SET NAMES utf8 */;
/*!40103 SET @OLD_TIME_ZONE=@@TIME_ZONE */;
/*!40103 SET TIME_ZONE='+00:00' */;
/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;

--
-- Table structure for table `account`
--

DROP TABLE IF EXISTS `account`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `account` (
  `account_id` int NOT NULL AUTO_INCREMENT,
  `customer_id` int NOT NULL,
  `account_type` enum('Savings','Current') NOT NULL,
  `balance` decimal(15,2) NOT NULL DEFAULT '0.00',
  `created_at` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP,
  PRIMARY KEY (`account_id`),
  KEY `customer_id` (`customer_id`),
  CONSTRAINT `account_ibfk_1` FOREIGN KEY (`customer_id`) REFERENCES `customer` (`customer_id`) ON DELETE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=15 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `account`
--

LOCK TABLES `account` WRITE;
/*!40000 ALTER TABLE `account` DISABLE KEYS */;
INSERT INTO `account` VALUES (1,1,'Savings',136312.00,'2026-04-25 22:07:47'),(2,2,'Current',12000.00,'2026-04-25 22:07:47'),(4,5,'Current',6000.00,'2026-04-25 22:59:26'),(5,7,'Savings',80500.00,'2026-04-25 23:00:14'),(6,8,'Savings',50000.00,'2026-04-25 23:00:45'),(7,9,'Savings',696969.00,'2026-04-25 23:01:17'),(8,10,'Savings',20500.00,'2026-04-25 23:02:15'),(9,11,'Savings',10192.00,'2026-04-25 23:03:32'),(10,12,'Current',67000.00,'2026-04-25 23:04:45'),(11,13,'Savings',6687600.00,'2026-04-25 23:05:10'),(12,14,'Savings',50100.00,'2026-04-25 23:05:36'),(13,15,'Savings',3000.00,'2026-04-25 23:31:35');
/*!40000 ALTER TABLE `account` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `customer`
--

DROP TABLE IF EXISTS `customer`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `customer` (
  `customer_id` int NOT NULL AUTO_INCREMENT,
  `first_name` varchar(50) NOT NULL,
  `last_name` varchar(50) NOT NULL,
  `email` varchar(100) NOT NULL,
  `phone_number` varchar(20) NOT NULL,
  PRIMARY KEY (`customer_id`),
  UNIQUE KEY `email` (`email`)
) ENGINE=InnoDB AUTO_INCREMENT=17 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `customer`
--

LOCK TABLES `customer` WRITE;
/*!40000 ALTER TABLE `customer` DISABLE KEYS */;
INSERT INTO `customer` VALUES (1,'Juan','Dela Cruz','juan@gmail.com','09171234567'),(2,'Maria','Santos','maria@gmail.com','09281234567'),(4,'baby','shark','babyshark@gmail.com','091239019'),(5,'matoy','sebastian','akosimatoy@gmail.com','091230913'),(7,'dodong','dodoso','dodoako@gmail.com','0912391093'),(8,'edgar','cut','edgarcut@gmail.com','091239093'),(9,'brian','bunjing','bunjing123@gmail.com','091029392'),(10,'reds','yobmot','mapulangtuhod@gmail.com','09090931903'),(11,'guilbert','mulletdadi','badboi123@gmail.com','09090909909'),(12,'daddy','ferns','loverboi@gmail.com','0909090909'),(13,'lance','bimbo','lancebimbo@gmail.com','096767677'),(14,'berto','gout','mygoutisreal@gmail.com','09103190391'),(15,'herb','burp','herbburp@gmail.com','019230193'),(16,'bulbasaur','emerald','3213@gmail.com','019230193');
/*!40000 ALTER TABLE `customer` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `transaction`
--

DROP TABLE IF EXISTS `transaction`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `transaction` (
  `transaction_id` int NOT NULL AUTO_INCREMENT,
  `account_id` int NOT NULL,
  `transaction_type` enum('Deposit','Withdraw') NOT NULL,
  `amount` decimal(15,2) NOT NULL,
  `transaction_date` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP,
  PRIMARY KEY (`transaction_id`),
  KEY `account_id` (`account_id`),
  CONSTRAINT `transaction_ibfk_1` FOREIGN KEY (`account_id`) REFERENCES `account` (`account_id`) ON DELETE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=11 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `transaction`
--

LOCK TABLES `transaction` WRITE;
/*!40000 ALTER TABLE `transaction` DISABLE KEYS */;
INSERT INTO `transaction` VALUES (1,1,'Deposit',5000.00,'2026-04-25 22:07:47'),(2,2,'Deposit',15000.00,'2026-04-25 22:07:47'),(3,2,'Withdraw',3000.00,'2026-04-25 22:07:47'),(4,1,'Deposit',131312.00,'2026-04-25 22:46:33'),(5,12,'Deposit',100.00,'2026-04-25 23:23:16'),(6,11,'Withdraw',6700.00,'2026-04-25 23:23:28'),(7,13,'Withdraw',2500.00,'2026-04-25 23:32:25'),(8,13,'Deposit',500.00,'2026-04-25 23:32:46'),(9,11,'Withdraw',6000.00,'2026-04-25 23:38:01'),(10,11,'Deposit',300.00,'2026-04-25 23:38:21');
/*!40000 ALTER TABLE `transaction` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2026-04-27  0:58:35
