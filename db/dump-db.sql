-- MySQL dump 10.13  Distrib 8.0.26, for Win64 (x86_64)
--
-- Host: 127.0.0.1    Database: alquiler_vehiculos
-- ------------------------------------------------------
-- Server version	8.0.26

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

DROP DATABASE IF EXISTS alquiler_vehiculos;
CREATE DATABASE `alquiler_vehiculos` /*!40100 DEFAULT CHARACTER SET utf8 */ /*!80016 DEFAULT ENCRYPTION='N' */;

USE alquiler_vehiculos;
--
-- Table structure for table `alquiler`
--

DROP TABLE IF EXISTS `alquiler`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `alquiler` (
  `dni` varchar(10) NOT NULL,
  `fechaHoraInicio` datetime NOT NULL,
  `idVeh` int NOT NULL,
  `fechaHoraFin` datetime NOT NULL,
  `estado` varchar(25) DEFAULT 'Pendiente',
  `costoTotal` decimal(8,2) DEFAULT '0.00',
  `idCob` int NOT NULL,
  PRIMARY KEY (`dni`,`fechaHoraInicio`),
  KEY `idVeh_idx` (`idVeh`),
  KEY `idCob_idx` (`idCob`),
  CONSTRAINT `dni` FOREIGN KEY (`dni`) REFERENCES `persona` (`dni`) ON DELETE RESTRICT ON UPDATE CASCADE,
  CONSTRAINT `idCob` FOREIGN KEY (`idCob`) REFERENCES `cobertura` (`idCob`) ON DELETE RESTRICT ON UPDATE CASCADE,
  CONSTRAINT `idVeh` FOREIGN KEY (`idVeh`) REFERENCES `vehiculo` (`idVeh`) ON DELETE RESTRICT ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `alquiler_extra`
--

DROP TABLE IF EXISTS `alquiler_extra`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `alquiler_extra` (
  `dni` varchar(10) NOT NULL,
  `fechaHoraInicio` datetime NOT NULL,
  `idExtra` int NOT NULL,
  PRIMARY KEY (`dni`,`fechaHoraInicio`,`idExtra`),
  KEY `idExtra_idx` (`idExtra`),
  CONSTRAINT `dniFechaHoraIni` FOREIGN KEY (`dni`, `fechaHoraInicio`) REFERENCES `alquiler` (`dni`, `fechaHoraInicio`) ON DELETE RESTRICT ON UPDATE CASCADE,
  CONSTRAINT `idExtra` FOREIGN KEY (`idExtra`) REFERENCES `extra` (`idExtra`) ON DELETE RESTRICT ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `cobertura`
--

DROP TABLE IF EXISTS `cobertura`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `cobertura` (
  `idCob` int NOT NULL AUTO_INCREMENT,
  `descripcion` varchar(45) NOT NULL,
  `precioDia` decimal(6,2) NOT NULL,
  `estado` tinyint DEFAULT '1',
  PRIMARY KEY (`idCob`)
) ENGINE=InnoDB AUTO_INCREMENT=7 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `extra`
--

DROP TABLE IF EXISTS `extra`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `extra` (
  `idExtra` int NOT NULL AUTO_INCREMENT,
  `descripcion` varchar(45) NOT NULL,
  `precioDia` decimal(6,2) NOT NULL,
  `estado` tinyint DEFAULT '1',
  PRIMARY KEY (`idExtra`)
) ENGINE=InnoDB AUTO_INCREMENT=6 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `localidad`
--

DROP TABLE IF EXISTS `localidad`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `localidad` (
  `codPostal` varchar(10) NOT NULL,
  `nombre` varchar(45) NOT NULL,
  `estado` tinyint DEFAULT '1',
  `idProv` int NOT NULL,
  PRIMARY KEY (`codPostal`),
  UNIQUE KEY `nombre_UNIQUE` (`nombre`),
  KEY `idProv_idx` (`idProv`),
  CONSTRAINT `idProv` FOREIGN KEY (`idProv`) REFERENCES `provincia` (`idProv`) ON DELETE RESTRICT ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `marca`
--

DROP TABLE IF EXISTS `marca`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `marca` (
  `idMarca` int NOT NULL AUTO_INCREMENT,
  `denominacion` varchar(45) NOT NULL,
  `estado` tinyint DEFAULT '1',
  PRIMARY KEY (`idMarca`),
  UNIQUE KEY `denominacion_UNIQUE` (`denominacion`)
) ENGINE=InnoDB AUTO_INCREMENT=16 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `pais`
--

DROP TABLE IF EXISTS `pais`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `pais` (
  `idPais` int NOT NULL AUTO_INCREMENT,
  `denominacion` varchar(45) NOT NULL,
  `estado` tinyint DEFAULT '1',
  PRIMARY KEY (`idPais`),
  UNIQUE KEY `denominacion_UNIQUE` (`denominacion`)
) ENGINE=InnoDB AUTO_INCREMENT=40 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `persona`
--

DROP TABLE IF EXISTS `persona`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `persona` (
  `dni` varchar(10) NOT NULL,
  `nombre` varchar(20) NOT NULL,
  `apellido` varchar(20) NOT NULL,
  `email` varchar(45) NOT NULL,
  `password` varchar(200) NOT NULL,
  `telefono` varchar(20) NOT NULL,
  `estado` tinyint DEFAULT '1',
  `codPostal` varchar(10) NOT NULL,
  PRIMARY KEY (`dni`),
  UNIQUE KEY `email_UNIQUE` (`email`),
  KEY `codPostal_idx` (`codPostal`),
  CONSTRAINT `codPostal` FOREIGN KEY (`codPostal`) REFERENCES `localidad` (`codPostal`) ON DELETE RESTRICT ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `persona_rol`
--

DROP TABLE IF EXISTS `persona_rol`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `persona_rol` (
  `dni` varchar(10) NOT NULL,
  `idRol` int NOT NULL,
  PRIMARY KEY (`dni`,`idRol`),
  KEY `idRol_idx` (`idRol`),
  CONSTRAINT `dniRol` FOREIGN KEY (`dni`) REFERENCES `persona` (`dni`) ON DELETE RESTRICT ON UPDATE CASCADE,
  CONSTRAINT `idRol` FOREIGN KEY (`idRol`) REFERENCES `rol` (`idRol`) ON DELETE RESTRICT ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `provincia`
--

DROP TABLE IF EXISTS `provincia`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `provincia` (
  `idProv` int NOT NULL AUTO_INCREMENT,
  `denominacion` varchar(45) NOT NULL,
  `estado` tinyint DEFAULT '1',
  `idPais` int NOT NULL,
  PRIMARY KEY (`idProv`),
  UNIQUE KEY `denominacion_UNIQUE` (`denominacion`),
  KEY `idPais_idx` (`idPais`),
  CONSTRAINT `idPais` FOREIGN KEY (`idPais`) REFERENCES `pais` (`idPais`) ON DELETE RESTRICT ON UPDATE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=37 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `rol`
--

DROP TABLE IF EXISTS `rol`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `rol` (
  `idRol` int NOT NULL AUTO_INCREMENT,
  `nombre` varchar(45) NOT NULL,
  PRIMARY KEY (`idRol`)
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `vehiculo`
--

DROP TABLE IF EXISTS `vehiculo`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `vehiculo` (
  `idVeh` int NOT NULL AUTO_INCREMENT,
  `denominacion` varchar(45) NOT NULL,
  `imagen` varchar(45) DEFAULT 'predeterminada.png',
  `cantPersonas` int NOT NULL,
  `tipoCambio` varchar(20) NOT NULL,
  `aireAc` tinyint NOT NULL,
  `abs` tinyint NOT NULL,
  `precioDia` decimal(7,2) NOT NULL,
  `cantidad` int NOT NULL,
  `estado` tinyint DEFAULT '1',
  `idMarca` int NOT NULL,
  PRIMARY KEY (`idVeh`),
  KEY `idMarca_idx` (`idMarca`),
  CONSTRAINT `idMarca` FOREIGN KEY (`idMarca`) REFERENCES `marca` (`idMarca`) ON DELETE RESTRICT ON UPDATE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=38 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2021-10-30 17:55:41