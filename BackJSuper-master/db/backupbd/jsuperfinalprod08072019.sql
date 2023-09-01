-- MySQL dump 10.17  Distrib 10.3.15-MariaDB, for debian-linux-gnu (x86_64)
--
-- Host: localhost    Database: jsuperfinalprod
-- ------------------------------------------------------
-- Server version	10.3.15-MariaDB-1:10.3.15+maria~bionic-log

/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;
/*!40103 SET @OLD_TIME_ZONE=@@TIME_ZONE */;
/*!40103 SET TIME_ZONE='+00:00' */;
/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;

--
-- Table structure for table `afip_pto_venta`
--

DROP TABLE IF EXISTS `afip_pto_venta`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `afip_pto_venta` (
  `nro` int(11) NOT NULL,
  `emision_tipo` varchar(100) NOT NULL,
  `bloqueado` varchar(45) DEFAULT NULL,
  `fecha_baja` datetime DEFAULT NULL,
  `app_id` int(11) NOT NULL,
  PRIMARY KEY (`nro`,`app_id`),
  KEY `fk_afip_pto_venta_app1_idx` (`app_id`),
  CONSTRAINT `fk_afip_pto_venta_app1` FOREIGN KEY (`app_id`) REFERENCES `app` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `afip_pto_venta`
--

LOCK TABLES `afip_pto_venta` WRITE;
/*!40000 ALTER TABLE `afip_pto_venta` DISABLE KEYS */;
/*!40000 ALTER TABLE `afip_pto_venta` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `afip_tpo_cbte`
--

DROP TABLE IF EXISTS `afip_tpo_cbte`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `afip_tpo_cbte` (
  `id` int(11) NOT NULL,
  `descripcion` varchar(100) NOT NULL,
  `fecha_desde` datetime DEFAULT NULL,
  `fecha_hasta` datetime DEFAULT NULL,
  `app_id` int(11) NOT NULL,
  PRIMARY KEY (`id`,`app_id`),
  KEY `fk_afip_tpo_cbte_app1_idx` (`app_id`),
  CONSTRAINT `fk_afip_tpo_cbte_app1` FOREIGN KEY (`app_id`) REFERENCES `app` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `afip_tpo_cbte`
--

LOCK TABLES `afip_tpo_cbte` WRITE;
/*!40000 ALTER TABLE `afip_tpo_cbte` DISABLE KEYS */;
/*!40000 ALTER TABLE `afip_tpo_cbte` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `afip_tpo_concepto`
--

DROP TABLE IF EXISTS `afip_tpo_concepto`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `afip_tpo_concepto` (
  `id` int(11) NOT NULL,
  `descripcion` varchar(80) NOT NULL,
  `fecha_desde` datetime DEFAULT NULL,
  `fecha_hasta` datetime DEFAULT NULL,
  `app_id` int(11) NOT NULL,
  PRIMARY KEY (`id`,`app_id`),
  KEY `fk_afip_tpo_concepto_app1_idx` (`app_id`),
  CONSTRAINT `fk_afip_tpo_concepto_app1` FOREIGN KEY (`app_id`) REFERENCES `app` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `afip_tpo_concepto`
--

LOCK TABLES `afip_tpo_concepto` WRITE;
/*!40000 ALTER TABLE `afip_tpo_concepto` DISABLE KEYS */;
/*!40000 ALTER TABLE `afip_tpo_concepto` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `afip_tpo_doc`
--

DROP TABLE IF EXISTS `afip_tpo_doc`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `afip_tpo_doc` (
  `id` int(11) NOT NULL,
  `descripcion` varchar(80) NOT NULL,
  `fecha_desde` datetime DEFAULT NULL,
  `fecha_hasta` datetime DEFAULT NULL,
  `app_id` int(11) NOT NULL,
  PRIMARY KEY (`id`,`app_id`),
  KEY `fk_afip_tpo_doc_app1_idx` (`app_id`),
  CONSTRAINT `fk_afip_tpo_doc_app1` FOREIGN KEY (`app_id`) REFERENCES `app` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `afip_tpo_doc`
--

LOCK TABLES `afip_tpo_doc` WRITE;
/*!40000 ALTER TABLE `afip_tpo_doc` DISABLE KEYS */;
/*!40000 ALTER TABLE `afip_tpo_doc` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `afip_tpo_iva`
--

DROP TABLE IF EXISTS `afip_tpo_iva`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `afip_tpo_iva` (
  `id` int(11) NOT NULL,
  `descripcion` varchar(100) NOT NULL,
  `fecha_desde` datetime DEFAULT NULL,
  `fecha_hasta` datetime DEFAULT NULL,
  `app_id` int(11) NOT NULL,
  PRIMARY KEY (`id`,`app_id`),
  KEY `fk_afip_tpo_iva_app1_idx` (`app_id`),
  CONSTRAINT `fk_afip_tpo_iva_app1` FOREIGN KEY (`app_id`) REFERENCES `app` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `afip_tpo_iva`
--

LOCK TABLES `afip_tpo_iva` WRITE;
/*!40000 ALTER TABLE `afip_tpo_iva` DISABLE KEYS */;
/*!40000 ALTER TABLE `afip_tpo_iva` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `afip_tpo_tributo`
--

DROP TABLE IF EXISTS `afip_tpo_tributo`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `afip_tpo_tributo` (
  `id` int(11) NOT NULL,
  `descripcion` varchar(180) NOT NULL,
  `fecha_desde` datetime DEFAULT NULL,
  `fecha_hasta` varchar(45) DEFAULT NULL,
  `app_id` int(11) NOT NULL,
  PRIMARY KEY (`id`,`app_id`),
  KEY `fk_afip_tpo_tributo_app1_idx` (`app_id`),
  CONSTRAINT `fk_afip_tpo_tributo_app1` FOREIGN KEY (`app_id`) REFERENCES `app` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `afip_tpo_tributo`
--

LOCK TABLES `afip_tpo_tributo` WRITE;
/*!40000 ALTER TABLE `afip_tpo_tributo` DISABLE KEYS */;
/*!40000 ALTER TABLE `afip_tpo_tributo` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `app`
--

DROP TABLE IF EXISTS `app`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `app` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `nombre` varchar(254) NOT NULL,
  `descripcion` text DEFAULT NULL,
  `key_app` varchar(254) DEFAULT NULL,
  `logo` text DEFAULT NULL,
  `alt_logo` text DEFAULT NULL,
  `configuracion_id` int(11) NOT NULL,
  `cuit_empresa` varchar(25) DEFAULT NULL COMMENT 'Cuil del dueño o la empresa, para lo que seria facutracion electronica',
  `afip_token` text DEFAULT NULL,
  `afip_sign` text DEFAULT NULL,
  `afip_fecha_init_token` datetime DEFAULT NULL,
  `afip_fecha_end_token` datetime DEFAULT NULL,
  `afip_fecha_init_token_test` datetime DEFAULT NULL,
  `afip_fecha_end_token_test` datetime DEFAULT NULL,
  `afip_token_test` text DEFAULT NULL,
  `afip_sign_test` text DEFAULT NULL,
  `afip_prod` bit(1) DEFAULT NULL,
  `afip_priv_key` tinytext DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `fk_app_configuracion1_idx` (`configuracion_id`),
  CONSTRAINT `fk_app_configuracion1` FOREIGN KEY (`configuracion_id`) REFERENCES `configuracion` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB AUTO_INCREMENT=20 DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `app`
--

LOCK TABLES `app` WRITE;
/*!40000 ALTER TABLE `app` DISABLE KEYS */;
/*!40000 ALTER TABLE `app` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `caja`
--

DROP TABLE IF EXISTS `caja`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `caja` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `ip_maquina` varchar(20) DEFAULT NULL,
  `nombre_maquina` varchar(45) DEFAULT NULL,
  `modelo_impresora` int(11) DEFAULT NULL,
  `marca_impresora` varchar(20) DEFAULT NULL,
  `sucursal` varchar(45) DEFAULT NULL,
  `observacion` mediumtext DEFAULT NULL,
  `entrada_lector_CB` bit(1) DEFAULT NULL,
  `sonidoBeep` bit(1) DEFAULT NULL,
  `modifica_adicional` bit(1) DEFAULT b'0',
  `modifica_precio` bit(1) DEFAULT b'0',
  `modifica_descuento` bit(1) DEFAULT b'0',
  `tipo_impresora` int(11) DEFAULT NULL,
  `sin_control` bit(1) DEFAULT NULL,
  `con_control` bit(1) DEFAULT NULL,
  `con_control_estricto` bit(1) DEFAULT NULL COMMENT 'Control stricto de stock',
  `nombre` varchar(80) DEFAULT NULL,
  `app_id` int(11) NOT NULL,
  `activo` bit(1) NOT NULL DEFAULT b'1',
  `limite_consumidor_final` decimal(10,4) NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `nombre_UNIQUE` (`nombre`),
  KEY `fk_caja_app1_idx` (`app_id`),
  CONSTRAINT `fk_caja_app1` FOREIGN KEY (`app_id`) REFERENCES `app` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB AUTO_INCREMENT=8 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `caja`
--

LOCK TABLES `caja` WRITE;
/*!40000 ALTER TABLE `caja` DISABLE KEYS */;
/*!40000 ALTER TABLE `caja` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `caja_sucursales`
--

DROP TABLE IF EXISTS `caja_sucursales`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `caja_sucursales` (
  `caja_id` int(11) NOT NULL,
  `sucursales_id` bigint(20) NOT NULL,
  PRIMARY KEY (`caja_id`,`sucursales_id`),
  KEY `fk_caja_has_sucursales_sucursales1_idx` (`sucursales_id`),
  KEY `fk_caja_has_sucursales_caja1_idx` (`caja_id`),
  CONSTRAINT `fk_caja_has_sucursales_caja1` FOREIGN KEY (`caja_id`) REFERENCES `caja` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT `fk_caja_has_sucursales_sucursales1` FOREIGN KEY (`sucursales_id`) REFERENCES `sucursales` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `caja_sucursales`
--

LOCK TABLES `caja_sucursales` WRITE;
/*!40000 ALTER TABLE `caja_sucursales` DISABLE KEYS */;
/*!40000 ALTER TABLE `caja_sucursales` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `ciudad`
--

DROP TABLE IF EXISTS `ciudad`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `ciudad` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `nombre` varchar(80) DEFAULT NULL,
  `cp` varchar(20) DEFAULT NULL,
  `provincia_id` int(11) NOT NULL,
  PRIMARY KEY (`id`),
  KEY `fk_ciudad_provincia1_idx` (`provincia_id`),
  CONSTRAINT `fk_ciudad_provincia1` FOREIGN KEY (`provincia_id`) REFERENCES `provincia` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `ciudad`
--

LOCK TABLES `ciudad` WRITE;
/*!40000 ALTER TABLE `ciudad` DISABLE KEYS */;
/*!40000 ALTER TABLE `ciudad` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `claves`
--

DROP TABLE IF EXISTS `claves`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `claves` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `clave` varchar(254) NOT NULL,
  `detalle` tinytext DEFAULT NULL,
  `app_id` int(11) NOT NULL,
  `usuarios_id` int(11) NOT NULL,
  PRIMARY KEY (`id`),
  KEY `fk_claves_app1_idx` (`app_id`),
  KEY `fk_claves_usuarios1_idx` (`usuarios_id`),
  CONSTRAINT `fk_claves_app1` FOREIGN KEY (`app_id`) REFERENCES `app` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT `fk_claves_usuarios1` FOREIGN KEY (`usuarios_id`) REFERENCES `usuarios` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `claves`
--

LOCK TABLES `claves` WRITE;
/*!40000 ALTER TABLE `claves` DISABLE KEYS */;
/*!40000 ALTER TABLE `claves` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `clientes`
--

DROP TABLE IF EXISTS `clientes`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `clientes` (
  `estado` bit(1) DEFAULT b'0',
  `tipo` tinyint(4) DEFAULT NULL COMMENT 'value: 0, name: Sin especificar\nvalue: 1, name: Consumidor Final\nvalue: 2, name: Monotributo\nvalue: 3, name: Responsable Incripto\nvalue: 4, name: Otro',
  `observacion` mediumtext DEFAULT NULL,
  `app_id` int(11) NOT NULL,
  `personas_id` int(11) NOT NULL,
  PRIMARY KEY (`personas_id`),
  KEY `fk_clientes_app1_idx` (`app_id`),
  CONSTRAINT `fk_clientes_app1` FOREIGN KEY (`app_id`) REFERENCES `app` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT `fk_clientes_personas1` FOREIGN KEY (`personas_id`) REFERENCES `personas` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `clientes`
--

LOCK TABLES `clientes` WRITE;
/*!40000 ALTER TABLE `clientes` DISABLE KEYS */;
/*!40000 ALTER TABLE `clientes` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `codigos_barras`
--

DROP TABLE IF EXISTS `codigos_barras`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `codigos_barras` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `codigo` varchar(45) COLLATE utf8_spanish_ci DEFAULT NULL,
  `nombre` varchar(80) COLLATE utf8_spanish_ci DEFAULT NULL,
  `cantidad_bulto` decimal(9,4) DEFAULT NULL,
  `tipo` tinyint(4) DEFAULT NULL,
  `descripcion` mediumtext COLLATE utf8_spanish_ci DEFAULT NULL,
  `producto_id` bigint(20) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `fk_codigos_barras_producto1_idx` (`producto_id`),
  KEY `codigo_idx` (`codigo`),
  CONSTRAINT `fk_codigos_barras_producto1` FOREIGN KEY (`producto_id`) REFERENCES `producto` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_spanish_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `codigos_barras`
--

LOCK TABLES `codigos_barras` WRITE;
/*!40000 ALTER TABLE `codigos_barras` DISABLE KEYS */;
/*!40000 ALTER TABLE `codigos_barras` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `configuracion`
--

DROP TABLE IF EXISTS `configuracion`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `configuracion` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `cli_ctacte_interes` decimal(9,4) NOT NULL DEFAULT 0.0000,
  `iva` decimal(9,4) NOT NULL DEFAULT 0.0000,
  `margen_ganancia` decimal(9,4) NOT NULL DEFAULT 0.0000,
  `pais` varchar(100) NOT NULL,
  `logo_reporte` varchar(200) DEFAULT NULL,
  `encabezado_reporte` mediumtext DEFAULT NULL,
  `tipo_empresa` tinyint(3) DEFAULT NULL,
  `cuit_empresa` varchar(25) DEFAULT NULL,
  `razon_social` varchar(220) DEFAULT NULL,
  `domicilio_comercial` varchar(220) DEFAULT NULL,
  `ingresos_brutos` varchar(45) DEFAULT NULL,
  `fecha_ini_act` datetime DEFAULT NULL,
  `cert_o` varchar(250) DEFAULT NULL,
  `cert_cn` varchar(250) DEFAULT NULL,
  `cert_serial_number` varchar(30) DEFAULT NULL,
  `cert_password` varchar(250) DEFAULT NULL,
  `cert_name_crt` text DEFAULT NULL,
  `afip_produccion` bit(1) DEFAULT NULL,
  `cut_desc_ticket` bit(1) DEFAULT NULL,
  `size_desc_ticket` int(11) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=21 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `configuracion`
--

LOCK TABLES `configuracion` WRITE;
/*!40000 ALTER TABLE `configuracion` DISABLE KEYS */;
/*!40000 ALTER TABLE `configuracion` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `cont_factura_adicional`
--

DROP TABLE IF EXISTS `cont_factura_adicional`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `cont_factura_adicional` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `nombre` varchar(250) NOT NULL,
  `importe` decimal(10,4) NOT NULL,
  `cont_factura_enc_id` bigint(20) NOT NULL,
  PRIMARY KEY (`id`),
  KEY `fk_cont_adicional_cont_factura_enc1_idx` (`cont_factura_enc_id`),
  CONSTRAINT `fk_cont_adicional_cont_factura_enc1` FOREIGN KEY (`cont_factura_enc_id`) REFERENCES `cont_factura_enc` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `cont_factura_adicional`
--

LOCK TABLES `cont_factura_adicional` WRITE;
/*!40000 ALTER TABLE `cont_factura_adicional` DISABLE KEYS */;
/*!40000 ALTER TABLE `cont_factura_adicional` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `cont_factura_descuento`
--

DROP TABLE IF EXISTS `cont_factura_descuento`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `cont_factura_descuento` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `nombre` varchar(250) NOT NULL,
  `importe` decimal(10,4) NOT NULL,
  `cont_factura_enc_id` bigint(20) NOT NULL,
  PRIMARY KEY (`id`),
  KEY `fk_cont_descuento_cont_factura_enc1_idx` (`cont_factura_enc_id`),
  CONSTRAINT `fk_cont_descuento_cont_factura_enc1` FOREIGN KEY (`cont_factura_enc_id`) REFERENCES `cont_factura_enc` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `cont_factura_descuento`
--

LOCK TABLES `cont_factura_descuento` WRITE;
/*!40000 ALTER TABLE `cont_factura_descuento` DISABLE KEYS */;
/*!40000 ALTER TABLE `cont_factura_descuento` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `cont_factura_det`
--

DROP TABLE IF EXISTS `cont_factura_det`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `cont_factura_det` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `id_producto` bigint(20) NOT NULL,
  `nombre_producto` varchar(254) NOT NULL,
  `cantidad` decimal(10,4) NOT NULL,
  `precio_sin_iva` decimal(10,4) DEFAULT NULL,
  `precio_con_iva` decimal(10,4) DEFAULT NULL,
  `subtotal` decimal(10,4) DEFAULT NULL,
  `iva_des` decimal(8,4) DEFAULT NULL,
  `cont_factura_enc_id` bigint(20) NOT NULL,
  `cant_agregada_stock` bit(1) NOT NULL COMMENT 'Indica si la cantidad fue agregada totalmente en stock',
  `iva_id` int(11) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `fk_cont_factura_det_cont_factura_enc_idx` (`cont_factura_enc_id`),
  CONSTRAINT `fk_cont_factura_det_cont_factura_enc` FOREIGN KEY (`cont_factura_enc_id`) REFERENCES `cont_factura_enc` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB AUTO_INCREMENT=172 DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `cont_factura_det`
--

LOCK TABLES `cont_factura_det` WRITE;
/*!40000 ALTER TABLE `cont_factura_det` DISABLE KEYS */;
/*!40000 ALTER TABLE `cont_factura_det` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `cont_factura_enc`
--

DROP TABLE IF EXISTS `cont_factura_enc`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `cont_factura_enc` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `fecha_carga` datetime NOT NULL,
  `fecha_factura` datetime NOT NULL,
  `numero` varchar(100) DEFAULT NULL,
  `subtotal` decimal(10,4) NOT NULL,
  `total` decimal(10,4) NOT NULL,
  `tipo` tinyint(4) NOT NULL COMMENT '1 - factura de compra\n2 - factura de venta\n',
  `tipo_factura` varchar(50) NOT NULL,
  `id_proveedor` bigint(20) DEFAULT NULL,
  `id_sucursal` bigint(20) NOT NULL,
  `activo` bit(1) NOT NULL,
  `motivo_anulacion` tinytext DEFAULT NULL,
  `proveedores_id` bigint(20) DEFAULT NULL,
  `app_id` int(11) NOT NULL,
  `total_descuentos` decimal(10,4) DEFAULT NULL,
  `total_adicionales` decimal(10,4) DEFAULT NULL,
  `total_impuestos` decimal(10,4) DEFAULT NULL,
  `cargada` tinyint(4) DEFAULT NULL COMMENT 'Determina si la factura  fue cargada completamente o no\n0: no fue cargada nada\n1: cargada a medias\n2: cargada completamente',
  `tipo_pago` tinyint(4) DEFAULT NULL COMMENT 'Tipo pago 1: contado , 2: cuta corriente',
  `pagada` bit(1) DEFAULT NULL,
  `total_base_imp` decimal(10,4) DEFAULT NULL,
  `total_iva` decimal(10,4) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `fk_cont_factura_enc_proveedores1_idx` (`proveedores_id`),
  KEY `fk_cont_factura_enc_app1_idx` (`app_id`),
  CONSTRAINT `fk_cont_factura_enc_app1` FOREIGN KEY (`app_id`) REFERENCES `app` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT `fk_cont_factura_enc_proveedores1` FOREIGN KEY (`proveedores_id`) REFERENCES `proveedores` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB AUTO_INCREMENT=136 DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `cont_factura_enc`
--

LOCK TABLES `cont_factura_enc` WRITE;
/*!40000 ALTER TABLE `cont_factura_enc` DISABLE KEYS */;
/*!40000 ALTER TABLE `cont_factura_enc` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `cont_factura_impuestos`
--

DROP TABLE IF EXISTS `cont_factura_impuestos`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `cont_factura_impuestos` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `nombre` varchar(250) NOT NULL,
  `porcentaje` decimal(10,4) DEFAULT NULL,
  `importe` decimal(10,4) NOT NULL,
  `cont_impuestos_id` int(11) DEFAULT NULL,
  `cont_factura_enc_id` bigint(20) NOT NULL,
  PRIMARY KEY (`id`),
  KEY `fk_coont_factura_impuestos_cont_impuestos1_idx` (`cont_impuestos_id`),
  KEY `fk_cont_factura_impuestos_cont_factura_enc1_idx` (`cont_factura_enc_id`),
  CONSTRAINT `fk_cont_factura_impuestos_cont_factura_enc1` FOREIGN KEY (`cont_factura_enc_id`) REFERENCES `cont_factura_enc` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT `fk_coont_factura_impuestos_cont_impuestos1` FOREIGN KEY (`cont_impuestos_id`) REFERENCES `cont_impuestos` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `cont_factura_impuestos`
--

LOCK TABLES `cont_factura_impuestos` WRITE;
/*!40000 ALTER TABLE `cont_factura_impuestos` DISABLE KEYS */;
/*!40000 ALTER TABLE `cont_factura_impuestos` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `cont_impuestos`
--

DROP TABLE IF EXISTS `cont_impuestos`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `cont_impuestos` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `nombre` varchar(250) NOT NULL,
  `activo` bit(1) NOT NULL,
  `porcentaje` decimal(10,4) DEFAULT NULL,
  `tipo` tinyint(4) DEFAULT NULL,
  `app_id` int(11) NOT NULL,
  `detalle` mediumtext DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `cont_impuestos`
--

LOCK TABLES `cont_impuestos` WRITE;
/*!40000 ALTER TABLE `cont_impuestos` DISABLE KEYS */;
INSERT INTO `cont_impuestos` VALUES (1,'Rentas','',15.0000,NULL,1,NULL);
/*!40000 ALTER TABLE `cont_impuestos` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `contactos`
--

DROP TABLE IF EXISTS `contactos`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `contactos` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `tipo` tinyint(4) NOT NULL COMMENT '1:telefono fijo\n2:telefono celular\n3:correo\n4:red social face\n5: red social twiter\n6:otro',
  `descripcion` varchar(254) NOT NULL,
  `subtipo` varchar(45) DEFAULT NULL,
  `detalle` tinytext DEFAULT NULL,
  `personas_id` int(11) DEFAULT NULL,
  `entidad` varchar(45) DEFAULT NULL,
  `proveedores_id` bigint(20) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `fk_contacto_personas_personas1_idx` (`personas_id`),
  KEY `fk_contactos_proveedores1_idx` (`proveedores_id`),
  CONSTRAINT `fk_contacto_personas_personas1` FOREIGN KEY (`personas_id`) REFERENCES `personas` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT `fk_contactos_proveedores1` FOREIGN KEY (`proveedores_id`) REFERENCES `proveedores` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `contactos`
--

LOCK TABLES `contactos` WRITE;
/*!40000 ALTER TABLE `contactos` DISABLE KEYS */;
/*!40000 ALTER TABLE `contactos` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `cuentas_corrientes`
--

DROP TABLE IF EXISTS `cuentas_corrientes`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `cuentas_corrientes` (
  `fecha_alta` datetime NOT NULL,
  `activo` bit(1) NOT NULL,
  `limite` decimal(10,4) NOT NULL,
  `descripcion` tinytext DEFAULT NULL,
  `cliente_personas_id` int(11) NOT NULL,
  PRIMARY KEY (`cliente_personas_id`),
  CONSTRAINT `fk_cuentas_corrientes_clientes1` FOREIGN KEY (`cliente_personas_id`) REFERENCES `clientes` (`personas_id`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `cuentas_corrientes`
--

LOCK TABLES `cuentas_corrientes` WRITE;
/*!40000 ALTER TABLE `cuentas_corrientes` DISABLE KEYS */;
/*!40000 ALTER TABLE `cuentas_corrientes` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `cuentas_corrientes_prov`
--

DROP TABLE IF EXISTS `cuentas_corrientes_prov`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `cuentas_corrientes_prov` (
  `fecha_alta` datetime NOT NULL,
  `activo` bit(1) NOT NULL,
  `limite` decimal(11,4) NOT NULL,
  `descripcion` tinytext DEFAULT NULL,
  `proveedores_id` bigint(20) NOT NULL,
  PRIMARY KEY (`proveedores_id`),
  KEY `fk_cuentas_corrientes_prov_proveedores1_idx` (`proveedores_id`),
  CONSTRAINT `fk_cuentas_corrientes_prov_proveedores1` FOREIGN KEY (`proveedores_id`) REFERENCES `proveedores` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `cuentas_corrientes_prov`
--

LOCK TABLES `cuentas_corrientes_prov` WRITE;
/*!40000 ALTER TABLE `cuentas_corrientes_prov` DISABLE KEYS */;
/*!40000 ALTER TABLE `cuentas_corrientes_prov` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `det_movimientos`
--

DROP TABLE IF EXISTS `det_movimientos`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `det_movimientos` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `cantidad` decimal(10,4) NOT NULL,
  `valor_inicial` decimal(10,4) NOT NULL,
  `valor_final` decimal(10,4) NOT NULL,
  `tipo` tinyint(2) NOT NULL,
  `referencia` varchar(250) DEFAULT NULL,
  `enc_movimientos_id` bigint(20) NOT NULL,
  `producto_id` bigint(20) NOT NULL,
  `origen_producto_id` bigint(20) DEFAULT NULL,
  `fecha_vencimiento` date DEFAULT NULL,
  `lote` varchar(80) DEFAULT NULL,
  `id_producto` bigint(20) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `fk_det_movimientos_enc_movimientos1_idx` (`enc_movimientos_id`),
  KEY `fk_det_movimientos_producto1_idx` (`producto_id`),
  CONSTRAINT `fk_det_movimientos_enc_movimientos1` FOREIGN KEY (`enc_movimientos_id`) REFERENCES `enc_movimientos` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT `fk_det_movimientos_producto1` FOREIGN KEY (`producto_id`) REFERENCES `producto` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `det_movimientos`
--

LOCK TABLES `det_movimientos` WRITE;
/*!40000 ALTER TABLE `det_movimientos` DISABLE KEYS */;
/*!40000 ALTER TABLE `det_movimientos` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `detalle_transaccion_caja`
--

DROP TABLE IF EXISTS `detalle_transaccion_caja`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `detalle_transaccion_caja` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `fecha` datetime NOT NULL COMMENT 'La fecha de apertura, cierre u otro movimiento\n',
  `tipo` tinyint(4) NOT NULL COMMENT '1: apertura\n2: ingreso dinero\n3: egreso dinero\n20:cierre\n5:otro \n',
  `observacion` mediumtext DEFAULT NULL,
  `transaccion_caja_id` bigint(20) NOT NULL,
  `monto` decimal(9,4) DEFAULT 0.0000,
  `encabezadoventas_id` bigint(20) DEFAULT NULL,
  `id_usuario_auth` varchar(32) DEFAULT NULL,
  `nombre_usuario_auth` varchar(120) DEFAULT NULL,
  `dni_usuario_auth` varchar(10) DEFAULT NULL,
  `motivo` varchar(80) DEFAULT NULL,
  `subtipo` tinyint(4) DEFAULT NULL COMMENT 'Subtipo dentro de tipo\n',
  `asociada` bit(1) DEFAULT NULL COMMENT 'indica si esta asociada a otra tabla, por ej a una factura de compra',
  `asociada_tipo` tinyint(4) DEFAULT NULL COMMENT 'indica el tipo de asociasda por eje para la factura de compra es 1\nfactura de compra: 1\n',
  `asociada_id` bigint(20) DEFAULT NULL COMMENT 'el id de la asociada en cuestion',
  `usuarios_id` int(11) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `fk_detalle_transaccion_caja_transaccion_caja1_idx` (`transaccion_caja_id`),
  KEY `fk_detalle_transaccion_caja_encabezadoventas1_idx` (`encabezadoventas_id`),
  KEY `fk_detalle_transaccion_caja_usuarios1_idx` (`usuarios_id`),
  CONSTRAINT `fk_detalle_transaccion_caja_encabezadoventas1` FOREIGN KEY (`encabezadoventas_id`) REFERENCES `encabezadoventas` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT `fk_detalle_transaccion_caja_transaccion_caja1` FOREIGN KEY (`transaccion_caja_id`) REFERENCES `transaccion_caja` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT `fk_detalle_transaccion_caja_usuarios1` FOREIGN KEY (`usuarios_id`) REFERENCES `usuarios` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB AUTO_INCREMENT=1442 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `detalle_transaccion_caja`
--

LOCK TABLES `detalle_transaccion_caja` WRITE;
/*!40000 ALTER TABLE `detalle_transaccion_caja` DISABLE KEYS */;
/*!40000 ALTER TABLE `detalle_transaccion_caja` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `detalleventas`
--

DROP TABLE IF EXISTS `detalleventas`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `detalleventas` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `cantidad` decimal(9,4) DEFAULT NULL,
  `precio` decimal(10,4) DEFAULT NULL,
  `codigoBarra` varchar(45) DEFAULT NULL,
  `descuento` decimal(9,4) DEFAULT NULL,
  `adicional` decimal(9,4) DEFAULT NULL,
  `descripcion` varchar(150) DEFAULT NULL,
  `productos_id` bigint(20) DEFAULT NULL,
  `encabezadoventas_id` bigint(20) DEFAULT NULL,
  `idx` bigint(20) DEFAULT NULL,
  `idSucursal` bigint(20) DEFAULT NULL,
  `tipoPrecioVenta` varchar(100) DEFAULT NULL,
  `subtotal` decimal(10,4) DEFAULT NULL,
  `descuento_oferta` decimal(9,4) DEFAULT NULL,
  `nombre_oferta` varchar(100) DEFAULT NULL,
  `ofertas_id` int(11) DEFAULT NULL,
  `precio_costo` decimal(10,4) DEFAULT NULL,
  `ganancia` decimal(10,4) DEFAULT NULL COMMENT 'ganancia\nprecio - preciocosto + adicional - descuento',
  `precio_sin_iva` decimal(10,4) DEFAULT NULL,
  `precio_con_iva` decimal(10,4) DEFAULT NULL,
  `afip_iva_id` tinyint(4) DEFAULT NULL,
  `afip_iva_desc` varchar(45) DEFAULT NULL,
  `importe_iva` decimal(10,4) DEFAULT NULL,
  `base_imponible` decimal(10,4) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `fk_detalleVenta_productos1_idx` (`productos_id`),
  KEY `fk_detalleventas_encabezadoventas1_idx` (`encabezadoventas_id`),
  KEY `fk_detalleventas_ofertas1_idx` (`ofertas_id`),
  CONSTRAINT `fk_detalleventas_encabezadoventas1` FOREIGN KEY (`encabezadoventas_id`) REFERENCES `encabezadoventas` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT `fk_detalleventas_ofertas1` FOREIGN KEY (`ofertas_id`) REFERENCES `ofertas` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `detalleventas`
--

LOCK TABLES `detalleventas` WRITE;
/*!40000 ALTER TABLE `detalleventas` DISABLE KEYS */;
/*!40000 ALTER TABLE `detalleventas` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `domicilios`
--

DROP TABLE IF EXISTS `domicilios`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `domicilios` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `calle` varchar(100) NOT NULL,
  `numero` int(11) DEFAULT NULL,
  `dpto` varchar(45) DEFAULT NULL,
  `piso` varchar(45) DEFAULT NULL,
  `provincia` varchar(45) DEFAULT NULL,
  `ciudad` varchar(150) DEFAULT NULL,
  `detalle` tinytext DEFAULT NULL,
  `personas_id` int(11) DEFAULT NULL,
  `entidad` varchar(45) DEFAULT NULL COMMENT 'si de una persona  y otra entidad como ser proveedores\n',
  `tipo` tinyint(4) DEFAULT NULL,
  `proveedores_id` bigint(20) DEFAULT NULL,
  `principal` bit(1) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `fk_domicilios_personas1_idx` (`personas_id`),
  KEY `fk_domicilios_proveedores1_idx` (`proveedores_id`),
  CONSTRAINT `fk_domicilios_personas1` FOREIGN KEY (`personas_id`) REFERENCES `personas` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT `fk_domicilios_proveedores1` FOREIGN KEY (`proveedores_id`) REFERENCES `proveedores` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `domicilios`
--

LOCK TABLES `domicilios` WRITE;
/*!40000 ALTER TABLE `domicilios` DISABLE KEYS */;
/*!40000 ALTER TABLE `domicilios` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `enc_movimientos`
--

DROP TABLE IF EXISTS `enc_movimientos`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `enc_movimientos` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `fecha_carga` datetime NOT NULL,
  `fecha_ingreso` datetime NOT NULL,
  `descripcion` tinytext DEFAULT NULL,
  `numero` varchar(200) DEFAULT NULL,
  `tipo` tinyint(2) DEFAULT NULL,
  `subtipo` tinyint(2) DEFAULT NULL,
  `sucursales_id` bigint(20) NOT NULL,
  `proveedores_id` bigint(20) DEFAULT NULL,
  `cont_factura_enc_id` bigint(20) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `fk_enc_movimientos_sucursales1_idx` (`sucursales_id`),
  KEY `fk_enc_movimientos_proveedores1_idx` (`proveedores_id`),
  KEY `fk_enc_movimientos_cont_factura_enc1_idx` (`cont_factura_enc_id`),
  CONSTRAINT `fk_enc_movimientos_cont_factura_enc1` FOREIGN KEY (`cont_factura_enc_id`) REFERENCES `cont_factura_enc` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT `fk_enc_movimientos_proveedores1` FOREIGN KEY (`proveedores_id`) REFERENCES `proveedores` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT `fk_enc_movimientos_sucursales1` FOREIGN KEY (`sucursales_id`) REFERENCES `sucursales` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB AUTO_INCREMENT=140 DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `enc_movimientos`
--

LOCK TABLES `enc_movimientos` WRITE;
/*!40000 ALTER TABLE `enc_movimientos` DISABLE KEYS */;
/*!40000 ALTER TABLE `enc_movimientos` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `encabezadoventas`
--

DROP TABLE IF EXISTS `encabezadoventas`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `encabezadoventas` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `fecha` datetime DEFAULT NULL,
  `tipoVenta` varchar(45) DEFAULT NULL,
  `total` decimal(10,4) DEFAULT NULL,
  `observacion` mediumtext DEFAULT NULL,
  `clientes_id` int(11) DEFAULT NULL,
  `sucursales_id` bigint(20) DEFAULT NULL,
  `idFacturaImpresoraFiscal` varchar(10) DEFAULT NULL,
  `nombreCliente` varchar(80) DEFAULT NULL,
  `direccionCliente` varchar(100) DEFAULT NULL,
  `cuilCliente` varchar(20) DEFAULT NULL,
  `dniCliente` varchar(15) DEFAULT NULL,
  `tipoFactura` varchar(15) DEFAULT NULL,
  `id_tipo_factura` int(11) DEFAULT NULL,
  `hora` time DEFAULT NULL,
  `app_id` int(11) NOT NULL,
  `transaccion_caja_id` bigint(20) DEFAULT NULL,
  `tipo_pago` varchar(200) DEFAULT NULL,
  `id_cliente` bigint(20) DEFAULT NULL,
  `estado` bit(1) NOT NULL DEFAULT b'1',
  `usuarios_id` int(11) NOT NULL,
  `efectivo` decimal(10,4) DEFAULT NULL,
  `vuelto` decimal(10,4) DEFAULT NULL,
  `pagada` bit(1) DEFAULT NULL,
  `saldo` decimal(10,4) DEFAULT NULL,
  `tipo_comprobante` tinyint(3) NOT NULL COMMENT '1: normal de punto de venta\\n2: fe desde la web\\n',
  `afip_concepto_id` tinyint(3) DEFAULT NULL COMMENT 'Concepto, producot, serv o pserv y producto',
  `afip_concepto_desc` varchar(80) DEFAULT NULL,
  `afip_fecha_desde` datetime DEFAULT NULL,
  `afip_fecha_hasta` datetime DEFAULT NULL,
  `afip_fecha_ven_pag` datetime DEFAULT NULL,
  `afip_tipo_cbte_id` tinyint(3) DEFAULT NULL,
  `afip_tipo_cbte_desc` varchar(80) DEFAULT NULL,
  `afip_pto_venta` tinyint(3) DEFAULT NULL,
  `afip_tipo_doc_id` tinyint(3) DEFAULT NULL,
  `afip_tipo_doc_desc` varchar(80) DEFAULT NULL,
  `afip_doc` varchar(80) DEFAULT NULL,
  `afip_total_iva` decimal(10,4) DEFAULT NULL,
  `afip_total_bas_imp` decimal(10,4) DEFAULT NULL,
  `afip_fe` bit(1) DEFAULT NULL,
  `afip_cae` varchar(200) DEFAULT NULL,
  `afip_cae_venc` datetime DEFAULT NULL,
  `afip_total_trib` decimal(10,4) DEFAULT NULL,
  `afip_fecha_cbte` datetime DEFAULT NULL,
  `afip_razon_social` varchar(220) DEFAULT NULL,
  `afip_condicion` varchar(150) DEFAULT NULL,
  `afip_fecha_ini_act` varchar(45) DEFAULT NULL,
  `afip_ing_brutos` varchar(50) DEFAULT NULL,
  `afip_cuit` varchar(45) DEFAULT NULL,
  `afip_dom_comercial` varchar(45) DEFAULT NULL,
  `afip_cbte_nro` varchar(20) DEFAULT NULL,
  `afip_nombre_cli` varchar(250) DEFAULT NULL,
  `afip_condicion_cli` varchar(100) DEFAULT NULL,
  `afip_modo` varchar(10) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `fk_encabezadoventas_sucursales1_idx` (`sucursales_id`),
  KEY `fk_encabezadoventas_app1_idx` (`app_id`),
  KEY `fk_encabezadoventas_transaccion_caja1_idx` (`transaccion_caja_id`),
  KEY `fk_encabezadoventas_usuarios1_idx` (`usuarios_id`),
  CONSTRAINT `fk_encabezadoventas_app1` FOREIGN KEY (`app_id`) REFERENCES `app` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT `fk_encabezadoventas_sucursales1` FOREIGN KEY (`sucursales_id`) REFERENCES `sucursales` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT `fk_encabezadoventas_transaccion_caja1` FOREIGN KEY (`transaccion_caja_id`) REFERENCES `transaccion_caja` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT `fk_encabezadoventas_usuarios1` FOREIGN KEY (`usuarios_id`) REFERENCES `usuarios` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB AUTO_INCREMENT=18960 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `encabezadoventas`
--

LOCK TABLES `encabezadoventas` WRITE;
/*!40000 ALTER TABLE `encabezadoventas` DISABLE KEYS */;
/*!40000 ALTER TABLE `encabezadoventas` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `eventos_ventas`
--

DROP TABLE IF EXISTS `eventos_ventas`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `eventos_ventas` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `nombre` varchar(45) NOT NULL,
  `tipo` int(11) DEFAULT NULL,
  `nombre_usuario_auth` varchar(225) DEFAULT NULL,
  `id_usuario_auth` varchar(10) DEFAULT NULL,
  `detalle` tinytext DEFAULT NULL,
  `fecha` datetime DEFAULT NULL,
  `encabezadoventas_id` bigint(20) NOT NULL,
  `monto` decimal(9,4) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `fk_eventos_ventas_encabezadoventas1_idx` (`encabezadoventas_id`),
  CONSTRAINT `fk_eventos_ventas_encabezadoventas1` FOREIGN KEY (`encabezadoventas_id`) REFERENCES `encabezadoventas` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `eventos_ventas`
--

LOCK TABLES `eventos_ventas` WRITE;
/*!40000 ALTER TABLE `eventos_ventas` DISABLE KEYS */;
/*!40000 ALTER TABLE `eventos_ventas` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `familias`
--

DROP TABLE IF EXISTS `familias`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `familias` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `nombre` varchar(80) CHARACTER SET utf8 COLLATE utf8_spanish_ci DEFAULT NULL,
  `nivel` int(11) DEFAULT NULL,
  `familias_id` int(11) DEFAULT NULL,
  `nombreCorto` varchar(10) CHARACTER SET utf8 COLLATE utf8_spanish_ci DEFAULT NULL,
  `app_id` int(11) NOT NULL,
  PRIMARY KEY (`id`),
  KEY `fk_familias_familias1_idx` (`familias_id`),
  KEY `fk_familias_app1_idx` (`app_id`),
  CONSTRAINT `fk_familias_app1` FOREIGN KEY (`app_id`) REFERENCES `app` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT `fk_familias_familias1` FOREIGN KEY (`familias_id`) REFERENCES `familias` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB AUTO_INCREMENT=63 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `familias`
--

LOCK TABLES `familias` WRITE;
/*!40000 ALTER TABLE `familias` DISABLE KEYS */;
/*!40000 ALTER TABLE `familias` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `formapagos`
--

DROP TABLE IF EXISTS `formapagos`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `formapagos` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `descripcion` varchar(45) DEFAULT NULL,
  `detalle` mediumtext DEFAULT NULL,
  `activo` bit(1) DEFAULT NULL,
  `proveedor` bit(1) DEFAULT NULL COMMENT 'Si esta habilitado para proveedores',
  `fac_elec` bit(1) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=5 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `formapagos`
--

LOCK TABLES `formapagos` WRITE;
/*!40000 ALTER TABLE `formapagos` DISABLE KEYS */;
INSERT INTO `formapagos` VALUES (1,'EFECTIVO','','','',''),(2,'TARJETA DE CREDITO','','','',''),(3,'TARJETA DE DEBITO','','','',''),(4,'CUENTA CORRIENTE','','','\0','\0');
/*!40000 ALTER TABLE `formapagos` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `hist_precios`
--

DROP TABLE IF EXISTS `hist_precios`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `hist_precios` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `fecha` datetime NOT NULL,
  `precio_inicial` decimal(10,4) NOT NULL,
  `precio_final` decimal(10,4) NOT NULL,
  `detalle` tinytext DEFAULT NULL,
  `producto_id` bigint(20) NOT NULL,
  `tipo_precios_id` int(11) NOT NULL,
  PRIMARY KEY (`id`),
  KEY `fk_hist_precios_producto1_idx` (`producto_id`),
  KEY `fk_hist_precios_tipo_precios1_idx` (`tipo_precios_id`),
  CONSTRAINT `fk_hist_precios_producto1` FOREIGN KEY (`producto_id`) REFERENCES `producto` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT `fk_hist_precios_tipo_precios1` FOREIGN KEY (`tipo_precios_id`) REFERENCES `tipo_precios` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `hist_precios`
--

LOCK TABLES `hist_precios` WRITE;
/*!40000 ALTER TABLE `hist_precios` DISABLE KEYS */;
/*!40000 ALTER TABLE `hist_precios` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `imagen_producto`
--

DROP TABLE IF EXISTS `imagen_producto`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `imagen_producto` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `path` varchar(100) COLLATE utf8_spanish_ci DEFAULT NULL,
  `tag` varchar(45) COLLATE utf8_spanish_ci DEFAULT NULL,
  `nombre` varchar(100) COLLATE utf8_spanish_ci DEFAULT NULL,
  `tamanio` varchar(50) COLLATE utf8_spanish_ci DEFAULT NULL,
  `tipo` varchar(50) COLLATE utf8_spanish_ci DEFAULT NULL,
  `producto_id` bigint(20) DEFAULT NULL,
  `alt` varchar(100) COLLATE utf8_spanish_ci DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `fk_imagen_producto_producto1_idx` (`producto_id`),
  CONSTRAINT `fk_imagen_producto_producto1` FOREIGN KEY (`producto_id`) REFERENCES `producto` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_spanish_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `imagen_producto`
--

LOCK TABLES `imagen_producto` WRITE;
/*!40000 ALTER TABLE `imagen_producto` DISABLE KEYS */;
/*!40000 ALTER TABLE `imagen_producto` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `ivas_afip`
--

DROP TABLE IF EXISTS `ivas_afip`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `ivas_afip` (
  `base_imponible` decimal(10,4) NOT NULL,
  `id_afip_iva` int(11) NOT NULL,
  `importe` decimal(10,4) NOT NULL,
  `encabezadoventas_id` bigint(20) NOT NULL,
  `descripcion` varchar(30) NOT NULL,
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `alicuota` decimal(8,4) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `fk_ivas_afip_encabezadoventas1_idx` (`encabezadoventas_id`),
  CONSTRAINT `fk_ivas_afip_encabezadoventas1` FOREIGN KEY (`encabezadoventas_id`) REFERENCES `encabezadoventas` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB AUTO_INCREMENT=141 DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `ivas_afip`
--

LOCK TABLES `ivas_afip` WRITE;
/*!40000 ALTER TABLE `ivas_afip` DISABLE KEYS */;
/*!40000 ALTER TABLE `ivas_afip` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `log`
--

DROP TABLE IF EXISTS `log`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `log` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `usuarios_id` int(11) DEFAULT NULL,
  `tipo` int(2) DEFAULT NULL,
  `accion` varchar(45) DEFAULT NULL,
  `fecha_hora` datetime DEFAULT NULL,
  `ip` varchar(20) DEFAULT NULL,
  `data` mediumtext DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `fk_log_usuarios1_idx` (`usuarios_id`),
  CONSTRAINT `fk_log_usuarios1` FOREIGN KEY (`usuarios_id`) REFERENCES `usuarios` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `log`
--

LOCK TABLES `log` WRITE;
/*!40000 ALTER TABLE `log` DISABLE KEYS */;
/*!40000 ALTER TABLE `log` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `log_users`
--

DROP TABLE IF EXISTS `log_users`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `log_users` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `fecha_ingreso` datetime NOT NULL,
  `navegador` tinytext DEFAULT NULL,
  `usuarios_id` int(11) NOT NULL,
  PRIMARY KEY (`id`),
  KEY `fk_log_users_usuarios1_idx` (`usuarios_id`),
  CONSTRAINT `fk_log_users_usuarios1` FOREIGN KEY (`usuarios_id`) REFERENCES `usuarios` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `log_users`
--

LOCK TABLES `log_users` WRITE;
/*!40000 ALTER TABLE `log_users` DISABLE KEYS */;
/*!40000 ALTER TABLE `log_users` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `menu`
--

DROP TABLE IF EXISTS `menu`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `menu` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `nombre` varchar(100) NOT NULL,
  `descripcion` tinytext DEFAULT NULL,
  `roles_id` int(11) NOT NULL,
  `tipo_menu` tinyint(4) NOT NULL COMMENT '1: principal',
  PRIMARY KEY (`id`),
  KEY `fk_menu_roles1_idx` (`roles_id`),
  CONSTRAINT `fk_menu_roles1` FOREIGN KEY (`roles_id`) REFERENCES `roles` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `menu`
--

LOCK TABLES `menu` WRITE;
/*!40000 ALTER TABLE `menu` DISABLE KEYS */;
INSERT INTO `menu` VALUES (1,'Menu Admin',NULL,1,1),(2,'Menu Gerente',NULL,3,1);
/*!40000 ALTER TABLE `menu` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `menu_data`
--

DROP TABLE IF EXISTS `menu_data`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `menu_data` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `nombre` varchar(45) DEFAULT NULL,
  `href` varchar(254) DEFAULT NULL,
  `class` varchar(45) DEFAULT NULL,
  `icon` varchar(45) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=22 DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `menu_data`
--

LOCK TABLES `menu_data` WRITE;
/*!40000 ALTER TABLE `menu_data` DISABLE KEYS */;
INSERT INTO `menu_data` VALUES (1,'Inicio','gestion.inicio',NULL,'glyphicon glyphicon-home'),(2,'Listar Reclamos','gestion.reclamos.listar','','fa fa-list-ol'),(3,'Mapa de Reclamos','gestion.reclamos.mapa',NULL,'glyphicon glyphicon-map-marker'),(4,'Cargar Reclamo','gestion.reclamos.carga-manual',NULL,'fa fa-map-o'),(5,'Gráfico general','gestion.reclamos.graficos',NULL,'fa fa-pie-chart '),(6,'Gráfico de reclamos por mes','gestion.reclamos.graficosporfecha',NULL,'fa fa-line-chart'),(7,'Reclamos',NULL,NULL,'glyphicon glyphicon-tasks'),(8,'Gráficos estadísticos',NULL,NULL,'glyphicon glyphicon-stats'),(9,'Mantenimiento',NULL,NULL,' glyphicon glyphicon-cog'),(10,'Barrios','gestion.barrios',NULL,'glyphicon glyphicon-tent'),(11,'Categorías','gestion.reclamos.categorias',NULL,'glyphicon glyphicon-th-large'),(12,'Ciudad','gestion.newCiudad',NULL,'glyphicon glyphicon-tower'),(13,'Configuración','gestion.configuracion',NULL,'fa fa-wrench'),(14,'Roles','gestion.roles',NULL,'glyphicon glyphicon-sort-by-attributes-alt'),(15,'Subcategorías','gestion.reclamos.subcategorias',NULL,'glyphicon glyphicon-th '),(16,'Usuarios','gestion.usuarios',NULL,'glyphicon glyphicon-user'),(17,'Listar Reclamos','gestion.reclamos.ov-listar',NULL,'glyphicon glyphicon-list'),(18,'Ingresar','http://vecinos.villarino.gob.ar/administracion/',NULL,''),(19,'Casos de Exito','resueltos',NULL,''),(20,'Distritos','gestion.distritos',NULL,'fa fa-university'),(21,'Ayuda','gestion.ayuda',NULL,'glyphicon glyphicon-film');
/*!40000 ALTER TABLE `menu_data` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `menu_item`
--

DROP TABLE IF EXISTS `menu_item`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `menu_item` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `nombre` varchar(100) DEFAULT NULL,
  `descripcion` tinytext DEFAULT NULL,
  `menu_item_id` int(11) DEFAULT NULL,
  `menu_item_data_id` int(11) DEFAULT NULL,
  `principal` bit(1) DEFAULT NULL,
  `tipo_href` int(11) DEFAULT NULL COMMENT '''1 : enlace\\n2: estado''\n',
  `href` tinytext DEFAULT NULL,
  `class_css` varchar(254) DEFAULT NULL,
  `title` tinytext DEFAULT NULL,
  `orden` int(11) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `fk_menu_item_menu_item1_idx` (`menu_item_id`),
  KEY `fk_menu_item_menu_item_data1_idx` (`menu_item_data_id`),
  CONSTRAINT `fk_menu_item_menu_item1` FOREIGN KEY (`menu_item_id`) REFERENCES `menu_item` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT `fk_menu_item_menu_item_data1` FOREIGN KEY (`menu_item_data_id`) REFERENCES `menu_item_data` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB AUTO_INCREMENT=54 DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `menu_item`
--

LOCK TABLES `menu_item` WRITE;
/*!40000 ALTER TABLE `menu_item` DISABLE KEYS */;
INSERT INTO `menu_item` VALUES (1,'Inicio',NULL,NULL,1,'',1,'admin.dashboard','fa fa-home','Menu Inicio',1),(2,'Productos',NULL,NULL,2,'',0,'admin.productos.*','fa fa-product-hunt','Productos',2),(3,'Lista de Productos',NULL,2,3,'\0',1,'admin.productos.list({activo:1, tipo:1})','fa fa-caret-right','Listado de todos los productos del sistema',1),(4,'Nuevo Producto',NULL,2,4,'\0',1,'admin.productos.new','fa fa-caret-right','Cargar un nuevo producto',2),(5,'Nuevo Producto Compuesto',NULL,2,5,'\0',1,'admin.productos.compuesto-new','fa fa-caret-right',NULL,3),(6,'Mov. de existencia',NULL,2,6,'\0',0,'admin.productos.movimiento','fa fa-caret-right',NULL,4),(7,'Precios',NULL,2,7,'\0',0,'admin.productos.modificaprecio','fa fa-caret-right',NULL,5),(8,'Modificar Precios',NULL,7,8,'\0',1,'admin.productos.modificaprecio','fa fa-caret-right',NULL,NULL),(9,'Productos Padre',NULL,NULL,9,'',0,'admin.productopadre.*','fa fa-circle',NULL,3),(10,'Lista de Productos Padre',NULL,9,10,'\0',1,'admin.productopadre.list','fa fa-caret-right',NULL,1),(11,'Nuevo Producto Padre',NULL,9,11,'\0',1,'admin.productopadre.new','fa fa-caret-right',NULL,2),(12,'Familias',NULL,NULL,12,'',0,'admin.familias.*','fa fa-square-o',NULL,4),(13,'Lista de Familias',NULL,12,13,'\0',1,'admin.familias.list','fa fa-caret-right',NULL,1),(14,'Nueva Familia',NULL,12,14,'\0',1,'admin.familias.new','fa fa-caret-right',NULL,2),(15,'Personas',NULL,NULL,15,'',0,'admin.personas.*','fa fa-male',NULL,5),(16,'Lista de Personas',NULL,15,16,'\0',1,'admin.personas.list','fa fa-caret-right',NULL,1),(17,'Nueva Persona',NULL,15,17,'\0',1,'admin.personas.new','fa fa-caret-right',NULL,2),(18,'Usuarios',NULL,NULL,18,'',0,'admin.usuarios.*','fa fa-user',NULL,6),(19,'Lista de Usuarios',NULL,18,19,'\0',1,'admin.usuarios.list','fa fa-caret-right',NULL,1),(20,'Nuevo Usuario',NULL,18,20,'\0',1,'admin.usuarios.new','fa fa-caret-right',NULL,2),(21,'Clientes',NULL,NULL,21,'',0,'admin.clientes.*','fa fa-user-circle-o',NULL,7),(22,'Lista de Clientes',NULL,21,22,'\0',1,'admin.clientes.list','fa fa-caret-right',NULL,1),(23,'Nuevo Cliente',NULL,21,23,'\0',1,'admin.clientes.new','fa fa-caret-right',NULL,2),(24,'Proveedores',NULL,NULL,24,'',0,'admin.proveedores.*','fa fa-truck',NULL,8),(25,'Lista de Proveedores',NULL,24,25,'\0',1,'admin.proveedores.list','fa fa-caret-right',NULL,1),(26,'Nuevo Proveedor',NULL,24,26,'\0',1,'admin.proveedores.new','fa fa-caret-right',NULL,2),(27,'Ventas',NULL,NULL,27,'',0,'admin.ventas.*','fa fa-dollar',NULL,10),(28,'Lista de Ventas',NULL,27,28,'\0',1,'admin.ventas.list({totalMinimo:0,totalMaximo:0,pagada:2})','fa fa-caret-right',NULL,1),(29,'Transacciones de Caja',NULL,27,29,'\0',1,'admin.ventas.transaccioneslist','fa fa-caret-right',NULL,2),(30,'Lista de Cajas',NULL,27,30,'\0',1,'admin.cajas.list','fa fa-caret-right',NULL,3),(31,'Ofertas',NULL,27,31,'\0',1,'admin.ofertas.list','fa fa-caret-right',NULL,4),(32,'Configuración',NULL,NULL,32,'',0,'admin.config.*','fa fa-cogs',NULL,13),(33,'App',NULL,32,33,'\0',1,'admin.config.app','fa fa-caret-right',NULL,1),(34,'Cuentas Corrientes',NULL,21,34,'\0',1,'admin.clientes.mtoCtaCteList','fa fa-caret-right',NULL,2),(35,'Configuración gral.',NULL,32,35,'\0',1,'admin.config.configuracion','fa fa-caret-right',NULL,3),(36,'Ingreso',NULL,6,36,'\0',1,'admin.productos.movimiento','fa fa-caret-right',NULL,1),(37,'Egreso',NULL,6,37,'\0',1,'admin.productos.movimiento-egreso','fa fa-caret-right',NULL,2),(38,'Consultas',NULL,NULL,38,'',0,'admin.consultas.*','fa fa-database',NULL,12),(39,'Ventas',NULL,38,39,'\0',1,'admin.consultas.*','fa fa-caret-right',NULL,1),(40,'Prod. más vendidos(#)',NULL,39,40,'\0',1,'admin.consultas.productosmasvendidos','fa fa-caret-right',NULL,2),(41,'Prod. con mas entrada($)',NULL,39,41,'\0',1,'admin.consultas.productosconmasentrada','fa fa-caret-right',NULL,3),(42,'Compras',NULL,NULL,42,'',0,'admin.compras.*','fa fa-shopping-cart',NULL,11),(43,'Nueva fact. de compra',NULL,42,43,'\0',1,'admin.compras.nuevafactura','fa fa-caret-right',NULL,2),(44,'Lista fact. de compras',NULL,42,44,'\0',1,'admin.compras.listfactura','fa fa-caret-right',NULL,1),(45,'Ganancias',NULL,38,45,'\0',1,'admin.consultas.ganancias','fa fa-caret-right',NULL,2),(46,'Movimientos de caja',NULL,27,46,'\0',1,'admin.ventas.dettransaccioneslist','fa fa-caret-right',NULL,5),(47,'Parametricas',NULL,32,47,'\0',0,NULL,'fa fa-caret-right',NULL,NULL),(48,'Impuestos',NULL,47,48,'\0',1,'admin.contimpuestos.list','fa fa-caret-right',NULL,NULL),(49,'Varios',NULL,2,49,'\0',0,'admin.utils.vencimientoslist','fa fa-caret-right',NULL,6),(50,'Vencimientos',NULL,49,50,'\0',1,'admin.utils.vencimientoslist','fa fa-caret-right',NULL,NULL),(51,'Facturación',NULL,NULL,51,'',0,'admin.fe.*','fa fa-ticket',NULL,9),(52,'Nueva Factura',NULL,51,52,'\0',1,'admin.fe.newfe','fa fa-caret-right',NULL,0),(53,'Datos de facturacion AFIP',NULL,51,53,'\0',1,'admin.fe.datos','fa fa-caret-right',NULL,1);
/*!40000 ALTER TABLE `menu_item` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `menu_item_data`
--

DROP TABLE IF EXISTS `menu_item_data`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `menu_item_data` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `nombre` varchar(150) NOT NULL,
  `tipo_href` int(11) NOT NULL COMMENT '1 : enlace\n2: estado',
  `href` tinytext DEFAULT NULL,
  `class_css` varchar(250) DEFAULT NULL,
  `title` tinytext DEFAULT NULL,
  `orden` int(11) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=54 DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `menu_item_data`
--

LOCK TABLES `menu_item_data` WRITE;
/*!40000 ALTER TABLE `menu_item_data` DISABLE KEYS */;
INSERT INTO `menu_item_data` VALUES (1,'Inicio',1,'admin.dashboard','fa fa-home','Menu Inicio',NULL),(2,'Productos',0,'admin.productos.*','fa fa-product-hunt','Productos',NULL),(3,'Lista de Productos',1,'admin.productos.list({activo:1, tipo:1})','fa fa-caret-right','Listado de todos los productos del sistema',NULL),(4,'Nuevo Producto',1,'admin.productos.new','fa fa-caret-right','Cargar un nuevo producto',NULL),(5,'Nuevo Producto Compuesto',1,'admin.productos.compuesto-new','fa fa-caret-right',NULL,NULL),(6,'Movimiento de existencia',0,'admin.productos.movimiento','fa fa-caret-right',NULL,NULL),(7,'Precios',0,'admin.productos.modificaprecio','fa fa-caret-right',NULL,NULL),(8,'Modificar Precios',1,'admin.productos.modificaprecio','fa fa-caret-right',NULL,NULL),(9,'Productos Padre',0,'admin.productopadre.*','fa fa-circle',NULL,NULL),(10,'Lista de Productos Padre',1,'admin.productopadre.list','fa fa-caret-right',NULL,NULL),(11,'Nuevo Producto Padre',1,'admin.productopadre.new','fa fa-caret-right',NULL,NULL),(12,'Familias',0,'admin.familias.*','fa fa-square-o',NULL,NULL),(13,'Lista de Familias',1,'admin.familias.list','fa fa-caret-right',NULL,NULL),(14,'Nueva Familia',1,'admin.familias.new','fa fa-caret-right',NULL,NULL),(15,'Personas',0,'admin.personas.*','fa fa-male',NULL,NULL),(16,'Lista de Personas',1,'admin.personas.list','fa fa-caret-right',NULL,NULL),(17,'Nueva Persona',1,'admin.personas.new','fa fa-caret-right',NULL,NULL),(18,'Usuarios',0,'admin.usuarios.*','fa fa-user',NULL,NULL),(19,'Lista de Usuarios',1,'admin.usuarios.list','fa fa-caret-right',NULL,NULL),(20,'Nuevo Usuario',1,'admin.usuarios.new','fa fa-caret-right',NULL,NULL),(21,'Clientes',0,'admin.clientes.*','fa fa-user-circle-o',NULL,NULL),(22,'Lista de Clientes',1,'admin.clientes.list','fa fa-caret-right',NULL,NULL),(23,'Nuevo Cliente',1,'admin.clientes.new','fa fa-caret-right',NULL,NULL),(24,'Proveedores',0,'admin.proveedores.*','fa fa-truck',NULL,NULL),(25,'Lista de Proveedores',1,'admin.proveedores.list','fa fa-caret-right',NULL,NULL),(26,'Nuevo Proveedor',1,'admin.proveedores.new','fa fa-caret-right',NULL,NULL),(27,'Ventas',0,'admin.ventas.*','fa fa-dollar',NULL,NULL),(28,'Lista de Ventas',1,'admin.ventas.list({totalMinimo:0,totalMaximo:0,pagada:2})','fa fa-caret-right',NULL,NULL),(29,'Transacciones de Caja',1,'admin.ventas.transaccioneslist','fa fa-caret-right',NULL,NULL),(30,'Lista de Cajas',1,'admin.cajas.list','fa fa-caret-right',NULL,NULL),(31,'Ofertas',1,'admin.ofertas.list','fa fa-caret-right',NULL,NULL),(32,'Configuración',0,'admin.config.*','fa fa-cogs',NULL,NULL),(33,'App',1,'admin.config.app','fa fa-caret-right',NULL,NULL),(34,'Cuentas corrientes',1,'admin.clientes.mtoCtaCteList','fa fa-caret-right',NULL,NULL),(35,'Configuración gral.',1,'admin.config.configuracion','fa fa-caret-right',NULL,NULL),(36,'Ingreso',1,'admin.productos.movimiento','fa fa-caret-right',NULL,NULL),(37,'Egreso',1,'admin.productos.movimiento-egreso','fa fa-caret-right',NULL,NULL),(38,'Consultas',0,'admin.consultas.*','fa fa-database',NULL,NULL),(39,'Ventas',1,'admin.consultas.*','fa fa-caret-right',NULL,0),(40,'Prod. más vendidos',1,'admin.consultas.productosmasvendidos','fa fa-caret-right',NULL,NULL),(41,'Prod. con mas entrada($)',1,'admin.consultas.productosconmasentrada','fa fa-caret-right',NULL,NULL),(42,'Compras',0,'admin.compras.*','fa fa-shopping-cart',NULL,NULL),(43,'Nueva fact. de compra',1,'admin.compras.nuevafactura','fa fa-caret-right',NULL,2),(44,'Lista fact. de compras',1,'admin.compras.listfactura','fa fa-caret-right',NULL,1),(45,'Ganancias',1,'admin.consultas.ganancias','fa fa-caret-right',NULL,1),(46,'Movimientos de caja',1,'admin.ventas.dettransaccioneslist','fa fa-caret-right',NULL,NULL),(47,'Parametricas',0,NULL,'fa fa-caret-right',NULL,NULL),(48,'Impuestos',1,'admin.contimpuestos.list','fa fa-caret-right',NULL,NULL),(49,'Varios',0,'admin.utils.vencimientoslist','fa fa-caret-right',NULL,1),(50,'Vencimientos',1,'admin.utils.vencimientoslist','fa fa-caret-right',NULL,1),(51,'Facturacion',0,'admin.fe.*','fa fa-ticket',NULL,NULL),(52,'Nueva factura',1,'admin.fe.newfe','fa fa-caret-right',NULL,NULL),(53,'Datos de facturacion AFIP',1,'','fa fa-caret-right',NULL,NULL);
/*!40000 ALTER TABLE `menu_item_data` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `menu_menu_item`
--

DROP TABLE IF EXISTS `menu_menu_item`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `menu_menu_item` (
  `menu_id` int(11) NOT NULL,
  `menu_item_id` int(11) NOT NULL,
  PRIMARY KEY (`menu_id`,`menu_item_id`),
  KEY `fk_menu_has_menu_item_menu_item1_idx` (`menu_item_id`),
  KEY `fk_menu_has_menu_item_menu1_idx` (`menu_id`),
  CONSTRAINT `fk_menu_has_menu_item_menu1` FOREIGN KEY (`menu_id`) REFERENCES `menu` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT `fk_menu_has_menu_item_menu_item1` FOREIGN KEY (`menu_item_id`) REFERENCES `menu_item` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `menu_menu_item`
--

LOCK TABLES `menu_menu_item` WRITE;
/*!40000 ALTER TABLE `menu_menu_item` DISABLE KEYS */;
INSERT INTO `menu_menu_item` VALUES (1,1),(1,2),(1,9),(1,12),(1,15),(1,18),(1,21),(1,24),(1,27),(1,32),(1,38),(1,42),(1,51),(2,1),(2,2),(2,9),(2,12),(2,15),(2,18),(2,21),(2,24),(2,27),(2,32),(2,38),(2,42);
/*!40000 ALTER TABLE `menu_menu_item` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `movimientos_cta_cte`
--

DROP TABLE IF EXISTS `movimientos_cta_cte`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `movimientos_cta_cte` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `tipo` int(11) NOT NULL COMMENT '1: cta cte clientes\n2: cta cte proveedores',
  `monto` decimal(9,4) NOT NULL,
  `fecha` datetime NOT NULL,
  `saldo` decimal(9,4) NOT NULL,
  `descripcion` tinytext DEFAULT NULL,
  `pagado` bit(1) NOT NULL,
  `cuentas_corrientes_cliente_personas_id` int(11) NOT NULL,
  `pagoventas_id` bigint(20) DEFAULT NULL COMMENT 'PUede ser nulo porq despues la cuenta corriente  se puede insertar otra cosa que no sea pagventa',
  `interes` decimal(9,4) NOT NULL,
  `monto_total` decimal(9,4) NOT NULL COMMENT 'el monto mas el interes que tenga',
  PRIMARY KEY (`id`),
  KEY `fk_movimientos_cta_cte_cuentas_corrientes1_idx` (`cuentas_corrientes_cliente_personas_id`),
  KEY `fk_movimientos_cta_cte_pagoventas1_idx` (`pagoventas_id`),
  CONSTRAINT `fk_movimientos_cta_cte_cuentas_corrientes1` FOREIGN KEY (`cuentas_corrientes_cliente_personas_id`) REFERENCES `cuentas_corrientes` (`cliente_personas_id`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT `fk_movimientos_cta_cte_pagoventas1` FOREIGN KEY (`pagoventas_id`) REFERENCES `pagoventas` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB AUTO_INCREMENT=102 DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `movimientos_cta_cte`
--

LOCK TABLES `movimientos_cta_cte` WRITE;
/*!40000 ALTER TABLE `movimientos_cta_cte` DISABLE KEYS */;
/*!40000 ALTER TABLE `movimientos_cta_cte` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `movimientos_cta_cte_pagos_cta_cte`
--

DROP TABLE IF EXISTS `movimientos_cta_cte_pagos_cta_cte`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `movimientos_cta_cte_pagos_cta_cte` (
  `movimientos_cta_cte_id` bigint(20) NOT NULL,
  `pagos_cta_cte_id` int(11) NOT NULL,
  PRIMARY KEY (`movimientos_cta_cte_id`,`pagos_cta_cte_id`),
  KEY `fk_movimientos_cta_cte_has_pagos_cta_cte_pagos_cta_cte1_idx` (`pagos_cta_cte_id`),
  KEY `fk_movimientos_cta_cte_has_pagos_cta_cte_movimientos_cta_ct_idx` (`movimientos_cta_cte_id`),
  CONSTRAINT `fk_movimientos_cta_cte_has_pagos_cta_cte_movimientos_cta_cte1` FOREIGN KEY (`movimientos_cta_cte_id`) REFERENCES `movimientos_cta_cte` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT `fk_movimientos_cta_cte_has_pagos_cta_cte_pagos_cta_cte1` FOREIGN KEY (`pagos_cta_cte_id`) REFERENCES `pagos_cta_cte` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `movimientos_cta_cte_pagos_cta_cte`
--

LOCK TABLES `movimientos_cta_cte_pagos_cta_cte` WRITE;
/*!40000 ALTER TABLE `movimientos_cta_cte_pagos_cta_cte` DISABLE KEYS */;
/*!40000 ALTER TABLE `movimientos_cta_cte_pagos_cta_cte` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `movimientos_cta_cte_prov`
--

DROP TABLE IF EXISTS `movimientos_cta_cte_prov`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `movimientos_cta_cte_prov` (
  `tipo` int(11) NOT NULL COMMENT '1: cta cte clientes\n2: cta cte proveedores',
  `monto` decimal(9,4) NOT NULL,
  `fecha` datetime NOT NULL,
  `saldo` decimal(9,4) NOT NULL,
  `descripcion` tinytext DEFAULT NULL,
  `pagado` bit(1) NOT NULL,
  `interes` decimal(9,4) NOT NULL,
  `monto_total` decimal(9,4) NOT NULL COMMENT 'el monto mas el interes que tenga',
  `cuentas_corrientes_prov_proveedores_id` bigint(20) NOT NULL,
  `cont_factura_enc_id` bigint(20) NOT NULL,
  PRIMARY KEY (`cont_factura_enc_id`),
  KEY `fk_movimientos_cta_cte_prov_cuentas_corrientes_prov1_idx` (`cuentas_corrientes_prov_proveedores_id`),
  CONSTRAINT `fk_movimientos_cta_cte_prov_cont_factura_enc1` FOREIGN KEY (`cont_factura_enc_id`) REFERENCES `cont_factura_enc` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT `fk_movimientos_cta_cte_prov_cuentas_corrientes_prov1` FOREIGN KEY (`cuentas_corrientes_prov_proveedores_id`) REFERENCES `cuentas_corrientes_prov` (`proveedores_id`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `movimientos_cta_cte_prov`
--

LOCK TABLES `movimientos_cta_cte_prov` WRITE;
/*!40000 ALTER TABLE `movimientos_cta_cte_prov` DISABLE KEYS */;
/*!40000 ALTER TABLE `movimientos_cta_cte_prov` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `movimientos_cta_cte_prov_pagos_cta_cte_prov`
--

DROP TABLE IF EXISTS `movimientos_cta_cte_prov_pagos_cta_cte_prov`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `movimientos_cta_cte_prov_pagos_cta_cte_prov` (
  `movimientos_cta_cte_prov_cont_factura_enc_id` bigint(20) NOT NULL,
  `pagos_cta_cte_prov_id` int(11) NOT NULL,
  PRIMARY KEY (`movimientos_cta_cte_prov_cont_factura_enc_id`,`pagos_cta_cte_prov_id`),
  KEY `fk_movimientos_cta_cte_prov_has_pagos_cta_cte_prov_pagos_ct_idx` (`pagos_cta_cte_prov_id`),
  KEY `fk_movimientos_cta_cte_prov_has_pagos_cta_cte_prov_movimien_idx` (`movimientos_cta_cte_prov_cont_factura_enc_id`),
  CONSTRAINT `fk_movimientos_cta_cte_prov_has_pagos_cta_cte_prov_movimiento1` FOREIGN KEY (`movimientos_cta_cte_prov_cont_factura_enc_id`) REFERENCES `movimientos_cta_cte_prov` (`cont_factura_enc_id`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT `fk_movimientos_cta_cte_prov_has_pagos_cta_cte_prov_pagos_cta_1` FOREIGN KEY (`pagos_cta_cte_prov_id`) REFERENCES `pagos_cta_cte_prov` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `movimientos_cta_cte_prov_pagos_cta_cte_prov`
--

LOCK TABLES `movimientos_cta_cte_prov_pagos_cta_cte_prov` WRITE;
/*!40000 ALTER TABLE `movimientos_cta_cte_prov_pagos_cta_cte_prov` DISABLE KEYS */;
/*!40000 ALTER TABLE `movimientos_cta_cte_prov_pagos_cta_cte_prov` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `ofertas`
--

DROP TABLE IF EXISTS `ofertas`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `ofertas` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `nombre` varchar(50) NOT NULL,
  `fecha_desde` datetime NOT NULL,
  `fecha_hasta` datetime NOT NULL,
  `categoria_oferta` varchar(45) NOT NULL,
  `tipo_oferta` varchar(45) NOT NULL,
  `tipo_oferta_tipo` varchar(45) NOT NULL,
  `tipo_descuento` varchar(45) NOT NULL,
  `detalle` tinytext DEFAULT NULL,
  `app_id` int(11) NOT NULL,
  `valor` decimal(9,4) DEFAULT NULL,
  `cantidad` decimal(9,4) DEFAULT NULL,
  `activo` bit(1) NOT NULL,
  `prioridad` int(11) NOT NULL,
  PRIMARY KEY (`id`),
  KEY `fk_ofertas_app1_idx` (`app_id`),
  CONSTRAINT `fk_ofertas_app1` FOREIGN KEY (`app_id`) REFERENCES `app` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB AUTO_INCREMENT=97 DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `ofertas`
--

LOCK TABLES `ofertas` WRITE;
/*!40000 ALTER TABLE `ofertas` DISABLE KEYS */;
/*!40000 ALTER TABLE `ofertas` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `ofertas_producto`
--

DROP TABLE IF EXISTS `ofertas_producto`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `ofertas_producto` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `precio` decimal(9,4) DEFAULT NULL,
  `ofertas_id` int(11) NOT NULL,
  `producto_id` bigint(20) NOT NULL,
  `estado` bit(1) NOT NULL,
  `descuento` decimal(9,4) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `fk_ofertas_producto_ofertas1_idx` (`ofertas_id`),
  KEY `fk_ofertas_producto_producto1_idx` (`producto_id`),
  CONSTRAINT `fk_ofertas_producto_ofertas1` FOREIGN KEY (`ofertas_id`) REFERENCES `ofertas` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT `fk_ofertas_producto_producto1` FOREIGN KEY (`producto_id`) REFERENCES `producto` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `ofertas_producto`
--

LOCK TABLES `ofertas_producto` WRITE;
/*!40000 ALTER TABLE `ofertas_producto` DISABLE KEYS */;
/*!40000 ALTER TABLE `ofertas_producto` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `ofertas_viejo`
--

DROP TABLE IF EXISTS `ofertas_viejo`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `ofertas_viejo` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `nombre` varchar(150) DEFAULT NULL,
  `desde` date DEFAULT NULL,
  `hasta` date DEFAULT NULL,
  `horaDesde` datetime DEFAULT NULL,
  `horaHasta` datetime DEFAULT NULL,
  `tipoOferta` varchar(45) DEFAULT NULL,
  `tipoPago` varchar(45) DEFAULT NULL,
  `tipoDescuento` varchar(45) DEFAULT NULL,
  `activo` bit(1) DEFAULT NULL,
  `lleva` int(11) DEFAULT NULL,
  `paga` decimal(9,4) DEFAULT NULL,
  `descuentoPesos` decimal(9,4) DEFAULT NULL,
  `descuentoPorcentaje` decimal(9,4) DEFAULT NULL,
  `observacion` mediumtext DEFAULT NULL,
  `productos_id` bigint(20) DEFAULT NULL,
  `pagoTotal` decimal(9,4) DEFAULT NULL,
  `precioProducto` decimal(9,4) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `fk_ofertas_productos1_idx` (`productos_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `ofertas_viejo`
--

LOCK TABLES `ofertas_viejo` WRITE;
/*!40000 ALTER TABLE `ofertas_viejo` DISABLE KEYS */;
/*!40000 ALTER TABLE `ofertas_viejo` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `pago_cont_facturas`
--

DROP TABLE IF EXISTS `pago_cont_facturas`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `pago_cont_facturas` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `descripcion` varchar(100) DEFAULT NULL,
  `monto` decimal(9,4) DEFAULT NULL,
  `numero` varchar(100) DEFAULT NULL,
  `formapagos_id` bigint(20) DEFAULT NULL,
  `idx` bigint(20) DEFAULT NULL,
  `tarjeta` varchar(45) DEFAULT NULL,
  `tipo` varchar(45) DEFAULT NULL,
  `pago_con` decimal(9,4) DEFAULT NULL COMMENT 'con cuanto dinero pago',
  `cont_factura_enc_id` bigint(20) NOT NULL,
  PRIMARY KEY (`id`),
  KEY `fk_pagos_formapagos1_idx` (`formapagos_id`),
  KEY `fk_pago_cont_facturas_cont_factura_enc1_idx` (`cont_factura_enc_id`),
  CONSTRAINT `fk_pago_cont_facturas_cont_factura_enc1` FOREIGN KEY (`cont_factura_enc_id`) REFERENCES `cont_factura_enc` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT `fk_pagos_formapagos10` FOREIGN KEY (`formapagos_id`) REFERENCES `formapagos` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `pago_cont_facturas`
--

LOCK TABLES `pago_cont_facturas` WRITE;
/*!40000 ALTER TABLE `pago_cont_facturas` DISABLE KEYS */;
/*!40000 ALTER TABLE `pago_cont_facturas` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `pagos_cta_cte`
--

DROP TABLE IF EXISTS `pagos_cta_cte`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `pagos_cta_cte` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `monto` decimal(10,4) NOT NULL,
  `numero` varchar(100) DEFAULT NULL,
  `tarjeta` varchar(45) DEFAULT NULL,
  `tipo` varchar(45) DEFAULT NULL,
  `pago_con` decimal(10,4) DEFAULT NULL,
  `descripcion` tinytext DEFAULT NULL,
  `formapagos_id` bigint(20) NOT NULL,
  `fecha` datetime NOT NULL,
  `interes` decimal(10,4) DEFAULT NULL,
  `monto_paga` decimal(10,4) DEFAULT NULL COMMENT 'El monto total que pago, mas o menos el interes',
  PRIMARY KEY (`id`),
  KEY `fk_pagos_cta_cte_formapagos1_idx` (`formapagos_id`),
  CONSTRAINT `fk_pagos_cta_cte_formapagos1` FOREIGN KEY (`formapagos_id`) REFERENCES `formapagos` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB AUTO_INCREMENT=61 DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `pagos_cta_cte`
--

LOCK TABLES `pagos_cta_cte` WRITE;
/*!40000 ALTER TABLE `pagos_cta_cte` DISABLE KEYS */;
INSERT INTO `pagos_cta_cte` VALUES (52,0.1000,NULL,NULL,'PAGO_EFECTIVO',0.1000,NULL,1,'2018-08-03 00:14:41',0.0000,0.1000),(53,0.1000,NULL,NULL,'PAGO_EFECTIVO',0.1000,NULL,1,'2018-08-03 00:15:55',0.0000,0.1000),(54,10.0000,NULL,NULL,'PAGO_EFECTIVO',15.0000,NULL,1,'2018-08-03 02:00:09',0.0000,10.0000),(55,60.5000,NULL,NULL,'PAGO_EFECTIVO',60.5000,NULL,1,'2018-11-28 14:24:37',0.0000,60.5000),(56,500.0000,NULL,NULL,'PAGO_EFECTIVO',500.0000,NULL,1,'2019-06-26 20:39:50',0.0000,500.0000),(57,356.4700,NULL,NULL,'PAGO_EFECTIVO',356.4700,NULL,1,'2019-06-26 20:40:25',0.0000,356.4700),(58,50.0000,NULL,NULL,'PAGO_EFECTIVO',50.0000,NULL,1,'2019-07-04 10:22:55',0.0000,50.0000),(59,100.0000,NULL,NULL,'PAGO_EFECTIVO',100.0000,NULL,1,'2019-07-04 10:27:43',0.0000,100.0000);
/*!40000 ALTER TABLE `pagos_cta_cte` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `pagos_cta_cte_prov`
--

DROP TABLE IF EXISTS `pagos_cta_cte_prov`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `pagos_cta_cte_prov` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `monto` decimal(10,4) NOT NULL,
  `numero` varchar(100) DEFAULT NULL,
  `tarjeta` varchar(45) DEFAULT NULL,
  `tipo` varchar(45) DEFAULT NULL,
  `pago_con` decimal(10,4) DEFAULT NULL,
  `descripcion` tinytext DEFAULT NULL,
  `fecha` datetime NOT NULL,
  `interes` decimal(10,4) DEFAULT NULL,
  `monto_paga` decimal(10,4) DEFAULT NULL COMMENT 'El monto total que pago, mas o menos el interes',
  `formapagos_id` bigint(20) NOT NULL,
  `saldo` decimal(10,4) DEFAULT NULL,
  `detalle_transaccion_caja_id` int(11) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `fk_pagos_cta_cte_prov_formapagos1_idx` (`formapagos_id`),
  KEY `fk_pagos_cta_cte_prov_detalle_transaccion_caja1_idx` (`detalle_transaccion_caja_id`),
  CONSTRAINT `fk_pagos_cta_cte_prov_detalle_transaccion_caja1` FOREIGN KEY (`detalle_transaccion_caja_id`) REFERENCES `detalle_transaccion_caja` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT `fk_pagos_cta_cte_prov_formapagos1` FOREIGN KEY (`formapagos_id`) REFERENCES `formapagos` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB AUTO_INCREMENT=125 DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `pagos_cta_cte_prov`
--

LOCK TABLES `pagos_cta_cte_prov` WRITE;
/*!40000 ALTER TABLE `pagos_cta_cte_prov` DISABLE KEYS */;
/*!40000 ALTER TABLE `pagos_cta_cte_prov` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `pagoventas`
--

DROP TABLE IF EXISTS `pagoventas`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `pagoventas` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `descripcion` varchar(100) DEFAULT NULL,
  `monto` decimal(9,4) DEFAULT NULL,
  `numero` varchar(100) DEFAULT NULL,
  `formapagos_id` bigint(20) DEFAULT NULL,
  `encabezadoventas_id` bigint(20) DEFAULT NULL,
  `idx` bigint(20) DEFAULT NULL,
  `tarjeta` varchar(45) DEFAULT NULL,
  `tipo` varchar(45) DEFAULT NULL,
  `pago_con` decimal(9,4) DEFAULT NULL COMMENT 'con cuanto dinero pago',
  PRIMARY KEY (`id`),
  KEY `fk_pagos_formapagos1_idx` (`formapagos_id`),
  KEY `fk_pagoventas_encabezadoventas1_idx` (`encabezadoventas_id`),
  CONSTRAINT `fk_pagos_formapagos1` FOREIGN KEY (`formapagos_id`) REFERENCES `formapagos` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT `fk_pagoventas_encabezadoventas1` FOREIGN KEY (`encabezadoventas_id`) REFERENCES `encabezadoventas` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB AUTO_INCREMENT=19017 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `pagoventas`
--

LOCK TABLES `pagoventas` WRITE;
/*!40000 ALTER TABLE `pagoventas` DISABLE KEYS */;
/*!40000 ALTER TABLE `pagoventas` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `param_entradas_caja`
--

DROP TABLE IF EXISTS `param_entradas_caja`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `param_entradas_caja` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `nombre` varchar(45) NOT NULL,
  `activo` bit(1) NOT NULL,
  `detalle` tinytext DEFAULT NULL,
  `orden` int(11) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=5 DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `param_entradas_caja`
--

LOCK TABLES `param_entradas_caja` WRITE;
/*!40000 ALTER TABLE `param_entradas_caja` DISABLE KEYS */;
INSERT INTO `param_entradas_caja` VALUES (1,'Solicitud de cambio','',NULL,1),(2,'Resguardo de dinero','',NULL,2),(3,'Recaudación','',NULL,3),(4,'Otro','',NULL,4);
/*!40000 ALTER TABLE `param_entradas_caja` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `param_salidas_caja`
--

DROP TABLE IF EXISTS `param_salidas_caja`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `param_salidas_caja` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `nombre` varchar(45) NOT NULL,
  `activo` bit(1) NOT NULL,
  `detalle` tinytext DEFAULT NULL,
  `orden` int(11) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=9 DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `param_salidas_caja`
--

LOCK TABLES `param_salidas_caja` WRITE;
/*!40000 ALTER TABLE `param_salidas_caja` DISABLE KEYS */;
INSERT INTO `param_salidas_caja` VALUES (1,'Pago a proveedores','',NULL,1),(2,'Recaudación','',NULL,2),(3,'Solicitud de cambio','',NULL,3),(4,'Arqueo parcial','',NULL,4),(5,'Arqueo','',NULL,5),(6,'Otros pagos','',NULL,6),(7,'Perdida de dinero','',NULL,7),(8,'Otro','',NULL,8);
/*!40000 ALTER TABLE `param_salidas_caja` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `personas`
--

DROP TABLE IF EXISTS `personas`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `personas` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `apellido` varchar(50) NOT NULL,
  `nombre` varchar(80) NOT NULL,
  `dni` varchar(10) DEFAULT NULL,
  `cuil` varchar(13) DEFAULT NULL,
  `sexo` tinyint(4) DEFAULT NULL,
  `fecha_nac` date DEFAULT NULL,
  `fecha_alta` datetime DEFAULT NULL,
  `direccion` varchar(45) DEFAULT NULL,
  `ciudad` varchar(45) DEFAULT NULL,
  `app_id` int(11) NOT NULL,
  PRIMARY KEY (`id`),
  KEY `fk_personas_app1_idx` (`app_id`),
  CONSTRAINT `fk_personas_app1` FOREIGN KEY (`app_id`) REFERENCES `app` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB AUTO_INCREMENT=136 DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `personas`
--

LOCK TABLES `personas` WRITE;
/*!40000 ALTER TABLE `personas` DISABLE KEYS */;
/*!40000 ALTER TABLE `personas` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `precio_producto`
--

DROP TABLE IF EXISTS `precio_producto`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `precio_producto` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `activo` bit(1) DEFAULT NULL,
  `tipo_precios_id` int(11) DEFAULT NULL,
  `precio` decimal(9,4) DEFAULT NULL,
  `producto_id` bigint(20) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `fk_precio_producto_tipo_precios1_idx` (`tipo_precios_id`),
  KEY `fk_precio_producto_producto1_idx` (`producto_id`),
  CONSTRAINT `fk_precio_producto_producto1` FOREIGN KEY (`producto_id`) REFERENCES `producto` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT `fk_precio_producto_tipo_precios1` FOREIGN KEY (`tipo_precios_id`) REFERENCES `tipo_precios` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_spanish_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `precio_producto`
--

LOCK TABLES `precio_producto` WRITE;
/*!40000 ALTER TABLE `precio_producto` DISABLE KEYS */;
/*!40000 ALTER TABLE `precio_producto` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `producto`
--

DROP TABLE IF EXISTS `producto`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `producto` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `nombre` varchar(250) NOT NULL,
  `contenido_neto` decimal(9,4) DEFAULT NULL,
  `iva` decimal(5,2) DEFAULT 21.00,
  `activo` bit(1) NOT NULL,
  `codigo_barra` tinytext DEFAULT NULL,
  `detalle` mediumtext DEFAULT NULL,
  `tags` varchar(45) DEFAULT NULL,
  `unidad` varchar(45) DEFAULT NULL,
  `unidad_id` int(11) DEFAULT NULL,
  `producto_padre_id` int(11) DEFAULT NULL,
  `precio_costo` decimal(9,4) DEFAULT 0.0000,
  `precio_venta` decimal(9,4) DEFAULT 0.0000,
  `nombre_final` varchar(45) NOT NULL,
  `pesable` bit(1) DEFAULT NULL COMMENT 'Indica si el produtco es pesaable',
  `ingreso_cantidad_manual` bit(1) DEFAULT NULL COMMENT 'Campo para que si la cantidad es manual vaya el focus directamente para el ingreso de la cantidad\n',
  `tipo` tinyint(4) DEFAULT NULL COMMENT '1: simple\n2: compuesto',
  `codigo_especial` varchar(100) DEFAULT NULL,
  `fecha_creacion` datetime DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `fk_detalle_productos_unidad1_idx` (`unidad_id`),
  KEY `fk_producto_producto_padre1_idx` (`producto_padre_id`),
  KEY `nombre_idx` (`nombre`),
  CONSTRAINT `fk_detalle_productos_unidad1` FOREIGN KEY (`unidad_id`) REFERENCES `unidad` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT `fk_producto_producto_padre1` FOREIGN KEY (`producto_padre_id`) REFERENCES `producto_padre` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB AUTO_INCREMENT=1512 DEFAULT CHARSET=utf8 COMMENT='Tabla con el detalle del producto, o presentacion, por ejemplo fanta es el producto  y viene con presetancion de 2.25, 1, 1.5 l etc\n';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `producto`
--

LOCK TABLES `producto` WRITE;
/*!40000 ALTER TABLE `producto` DISABLE KEYS */;
/*!40000 ALTER TABLE `producto` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `producto_padre`
--

DROP TABLE IF EXISTS `producto_padre`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `producto_padre` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `nombre` varchar(250) NOT NULL,
  `detalle` mediumtext DEFAULT NULL,
  `rubros_id` int(11) DEFAULT NULL,
  `familias_id` int(11) NOT NULL,
  `activo` bit(1) DEFAULT NULL,
  `app_id` int(11) NOT NULL,
  `nombre_corto` varchar(45) NOT NULL,
  `fecha_creacion` datetime DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `fk_productos_rubros1_idx` (`rubros_id`),
  KEY `fk_productos_familias1_idx` (`familias_id`),
  KEY `fk_producto_padre_app1_idx` (`app_id`),
  KEY `nombre_producto_padre` (`nombre`),
  CONSTRAINT `fk_producto_padre_app1` FOREIGN KEY (`app_id`) REFERENCES `app` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT `fk_productos_familias1` FOREIGN KEY (`familias_id`) REFERENCES `familias` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT `fk_productos_rubros1` FOREIGN KEY (`rubros_id`) REFERENCES `rubros` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB AUTO_INCREMENT=460 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `producto_padre`
--

LOCK TABLES `producto_padre` WRITE;
/*!40000 ALTER TABLE `producto_padre` DISABLE KEYS */;
/*!40000 ALTER TABLE `producto_padre` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `producto_proveedores`
--

DROP TABLE IF EXISTS `producto_proveedores`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `producto_proveedores` (
  `producto_id` bigint(20) NOT NULL,
  `proveedores_id` bigint(20) NOT NULL,
  PRIMARY KEY (`producto_id`,`proveedores_id`),
  KEY `fk_producto_has_proveedores_proveedores1_idx` (`proveedores_id`),
  KEY `fk_producto_has_proveedores_producto1_idx` (`producto_id`),
  CONSTRAINT `fk_producto_has_proveedores_producto1` FOREIGN KEY (`producto_id`) REFERENCES `producto` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT `fk_producto_has_proveedores_proveedores1` FOREIGN KEY (`proveedores_id`) REFERENCES `proveedores` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `producto_proveedores`
--

LOCK TABLES `producto_proveedores` WRITE;
/*!40000 ALTER TABLE `producto_proveedores` DISABLE KEYS */;
/*!40000 ALTER TABLE `producto_proveedores` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `productos_compuestos`
--

DROP TABLE IF EXISTS `productos_compuestos`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `productos_compuestos` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `cantidad` decimal(9,4) NOT NULL,
  `descripcion` tinytext DEFAULT NULL,
  `producto_principal_id` bigint(20) DEFAULT NULL,
  `producto_id` bigint(20) DEFAULT NULL,
  PRIMARY KEY (`id`,`cantidad`),
  KEY `fk_productos_compuestos_producto1_idx` (`producto_principal_id`),
  KEY `fk_productos_compuestos_producto2_idx` (`producto_id`),
  CONSTRAINT `fk_productos_compuestos_producto1` FOREIGN KEY (`producto_principal_id`) REFERENCES `producto` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT `fk_productos_compuestos_producto2` FOREIGN KEY (`producto_id`) REFERENCES `producto` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `productos_compuestos`
--

LOCK TABLES `productos_compuestos` WRITE;
/*!40000 ALTER TABLE `productos_compuestos` DISABLE KEYS */;
/*!40000 ALTER TABLE `productos_compuestos` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `proveedores`
--

DROP TABLE IF EXISTS `proveedores`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `proveedores` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `razon_social` varchar(200) DEFAULT NULL,
  `estado` bit(1) DEFAULT NULL,
  `cuit` varchar(25) DEFAULT NULL,
  `observacion` mediumtext DEFAULT NULL,
  `app_id` int(11) NOT NULL,
  `tipo` tinyint(4) DEFAULT 0,
  `personas_id` int(11) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `fk_proveedores_app1_idx` (`app_id`),
  KEY `fk_proveedores_personas1_idx` (`personas_id`),
  CONSTRAINT `fk_proveedores_app1` FOREIGN KEY (`app_id`) REFERENCES `app` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT `fk_proveedores_personas1` FOREIGN KEY (`personas_id`) REFERENCES `personas` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB AUTO_INCREMENT=23 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `proveedores`
--

LOCK TABLES `proveedores` WRITE;
/*!40000 ALTER TABLE `proveedores` DISABLE KEYS */;
/*!40000 ALTER TABLE `proveedores` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `provincia`
--

DROP TABLE IF EXISTS `provincia`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `provincia` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `nombre` varchar(50) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `provincia`
--

LOCK TABLES `provincia` WRITE;
/*!40000 ALTER TABLE `provincia` DISABLE KEYS */;
/*!40000 ALTER TABLE `provincia` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `roles`
--

DROP TABLE IF EXISTS `roles`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `roles` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `nombre` varchar(45) NOT NULL,
  `alias` varchar(45) NOT NULL,
  `realizaVentas` bit(1) DEFAULT NULL,
  `administraClientes` bit(1) DEFAULT NULL,
  `administraProveedores` bit(1) DEFAULT NULL,
  `administraOfertas` bit(1) DEFAULT NULL,
  `ingresaProdNoExistentes` bit(1) DEFAULT NULL,
  `modificaNombreProducto` bit(1) DEFAULT NULL,
  `realizaDescuentos` bit(1) DEFAULT NULL,
  `realizaAdicional` bit(1) DEFAULT NULL,
  `modificaPrecio` bit(1) DEFAULT NULL,
  `administraUsuarios` bit(1) DEFAULT NULL,
  `administraRoles` bit(1) DEFAULT NULL,
  `itemArchivo` bit(1) DEFAULT NULL,
  `itemConfiguracion` bit(1) DEFAULT NULL,
  `administraConfigPuntoVenta` bit(1) DEFAULT NULL,
  `itemAdministracion` bit(1) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `roles`
--

LOCK TABLES `roles` WRITE;
/*!40000 ALTER TABLE `roles` DISABLE KEYS */;
INSERT INTO `roles` VALUES (1,'ROLE_ADMIN','Admin','','','','','','','','','','','','','','',''),(2,'ROLE_CAJERO','Cajero','','\0','\0','\0','\0','\0','\0','\0','\0','\0','\0','\0','\0','\0','\0'),(3,'ROLE_GERENTE','Gerente',NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL);
/*!40000 ALTER TABLE `roles` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `rubros`
--

DROP TABLE IF EXISTS `rubros`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `rubros` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `nombre` varchar(100) DEFAULT NULL,
  `descripcion` varchar(100) DEFAULT NULL,
  `app_id` int(11) NOT NULL,
  PRIMARY KEY (`id`),
  KEY `fk_rubros_app1_idx` (`app_id`),
  CONSTRAINT `fk_rubros_app1` FOREIGN KEY (`app_id`) REFERENCES `app` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `rubros`
--

LOCK TABLES `rubros` WRITE;
/*!40000 ALTER TABLE `rubros` DISABLE KEYS */;
/*!40000 ALTER TABLE `rubros` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `stock_sucursal`
--

DROP TABLE IF EXISTS `stock_sucursal`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `stock_sucursal` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `stock` decimal(9,4) DEFAULT NULL,
  `stock_minimo` decimal(9,4) DEFAULT NULL,
  `detalle` mediumtext DEFAULT NULL,
  `sucursales_id` bigint(20) DEFAULT NULL,
  `idx` bigint(20) DEFAULT NULL,
  `ubicacion` varchar(254) DEFAULT NULL,
  `producto_id` bigint(20) DEFAULT NULL,
  `punto_reposicion` decimal(9,4) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `fk_stockSucursal_sucursales1_idx` (`sucursales_id`),
  KEY `fk_stock_sucursal_producto1_idx` (`producto_id`),
  CONSTRAINT `fk_stockSucursal_sucursales1` FOREIGN KEY (`sucursales_id`) REFERENCES `sucursales` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT `fk_stock_sucursal_producto1` FOREIGN KEY (`producto_id`) REFERENCES `producto` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `stock_sucursal`
--

LOCK TABLES `stock_sucursal` WRITE;
/*!40000 ALTER TABLE `stock_sucursal` DISABLE KEYS */;
/*!40000 ALTER TABLE `stock_sucursal` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `sucursales`
--

DROP TABLE IF EXISTS `sucursales`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `sucursales` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `nombre` varchar(150) NOT NULL,
  `direccion` varchar(150) NOT NULL,
  `prefijo_tel_fijo` varchar(15) DEFAULT NULL,
  `numero_tel_fijo` varchar(45) DEFAULT NULL,
  `activo` bit(1) DEFAULT NULL,
  `app_id` int(11) NOT NULL,
  `detalle` tinytext DEFAULT NULL,
  `principal` bit(1) DEFAULT NULL,
  `prefijo_tel_cel` varchar(15) DEFAULT NULL,
  `numero_tel_cel` varchar(45) DEFAULT NULL,
  `key_sucursal` varchar(254) NOT NULL,
  PRIMARY KEY (`id`),
  KEY `fk_sucursales_app1_idx` (`app_id`),
  CONSTRAINT `fk_sucursales_app1` FOREIGN KEY (`app_id`) REFERENCES `app` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB AUTO_INCREMENT=18 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `sucursales`
--

LOCK TABLES `sucursales` WRITE;
/*!40000 ALTER TABLE `sucursales` DISABLE KEYS */;
/*!40000 ALTER TABLE `sucursales` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `telefonos`
--

DROP TABLE IF EXISTS `telefonos`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `telefonos` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `prefijo` varchar(15) NOT NULL,
  `numero` varchar(45) NOT NULL,
  `empresa` varchar(45) DEFAULT NULL,
  `detalle` tinytext DEFAULT NULL,
  `tipo` tinyint(4) DEFAULT NULL COMMENT '1: celular\n2: fijo\n',
  `personas_id` int(11) DEFAULT NULL,
  `entidad` varchar(45) DEFAULT NULL COMMENT 'para saber si es de una persona p oproveddor por ejemplo',
  `proveedores_id` bigint(20) DEFAULT NULL,
  `principal` bit(1) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `fk_telefonos_personas_personas1_idx` (`personas_id`),
  KEY `fk_telefonos_proveedores1_idx` (`proveedores_id`),
  CONSTRAINT `fk_telefonos_personas_personas1` FOREIGN KEY (`personas_id`) REFERENCES `personas` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT `fk_telefonos_proveedores1` FOREIGN KEY (`proveedores_id`) REFERENCES `proveedores` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `telefonos`
--

LOCK TABLES `telefonos` WRITE;
/*!40000 ALTER TABLE `telefonos` DISABLE KEYS */;
/*!40000 ALTER TABLE `telefonos` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `tipo_precios`
--

DROP TABLE IF EXISTS `tipo_precios`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `tipo_precios` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `nombre` varchar(80) COLLATE utf8_spanish_ci DEFAULT NULL,
  `desde` date DEFAULT NULL,
  `hasta` date DEFAULT NULL,
  `activo` bit(1) DEFAULT NULL,
  `margenContado` decimal(9,4) DEFAULT NULL,
  `margenTarjetaCred` decimal(9,4) DEFAULT NULL,
  `margenTarjetaDeb` decimal(9,4) DEFAULT NULL,
  `margenCtaCorriente` decimal(9,4) DEFAULT NULL,
  `orden` int(11) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=5 DEFAULT CHARSET=utf8 COLLATE=utf8_spanish_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `tipo_precios`
--

LOCK TABLES `tipo_precios` WRITE;
/*!40000 ALTER TABLE `tipo_precios` DISABLE KEYS */;
INSERT INTO `tipo_precios` VALUES (1,'Costo','2016-01-25','2016-01-29','',20.0000,25.0000,25.0000,30.0000,1),(2,'Contado','2016-01-25','2016-01-25','',15.0000,25.0000,30.0000,30.0000,2),(3,'Lista','2016-06-01','2016-06-01','',NULL,NULL,NULL,NULL,3),(4,'Precio Tarjeta','2017-09-08','2017-09-08','',0.0000,0.0000,0.0000,0.0000,4);
/*!40000 ALTER TABLE `tipo_precios` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `transaccion_caja`
--

DROP TABLE IF EXISTS `transaccion_caja`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `transaccion_caja` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `estado` tinyint(4) NOT NULL COMMENT '1:abierta\n2: pausada\n3:cerrada\n',
  `detalle` mediumtext DEFAULT NULL,
  `caja_id` int(11) NOT NULL,
  `fecha_apertura` datetime DEFAULT NULL,
  `fecha_cierre` datetime DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `fk_transaccion_caja_caja1_idx` (`caja_id`),
  CONSTRAINT `fk_transaccion_caja_caja1` FOREIGN KEY (`caja_id`) REFERENCES `caja` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB AUTO_INCREMENT=321 DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `transaccion_caja`
--

LOCK TABLES `transaccion_caja` WRITE;
/*!40000 ALTER TABLE `transaccion_caja` DISABLE KEYS */;
/*!40000 ALTER TABLE `transaccion_caja` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `tributos_afip`
--

DROP TABLE IF EXISTS `tributos_afip`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `tributos_afip` (
  `descripcion` tinytext DEFAULT NULL,
  `base_imponible` decimal(10,4) NOT NULL,
  `alicuota` decimal(10,4) NOT NULL,
  `importe` decimal(10,4) NOT NULL,
  `id_afip_tpo_tributo` int(11) NOT NULL,
  `desc_afip_tpo_tributo` varchar(100) NOT NULL,
  `encabezadoventas_id` bigint(20) NOT NULL,
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  PRIMARY KEY (`id`),
  KEY `fk_tributos_afip_encabezadoventas1_idx` (`encabezadoventas_id`),
  CONSTRAINT `fk_tributos_afip_encabezadoventas1` FOREIGN KEY (`encabezadoventas_id`) REFERENCES `encabezadoventas` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `tributos_afip`
--

LOCK TABLES `tributos_afip` WRITE;
/*!40000 ALTER TABLE `tributos_afip` DISABLE KEYS */;
/*!40000 ALTER TABLE `tributos_afip` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `ubicaciones`
--

DROP TABLE IF EXISTS `ubicaciones`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `ubicaciones` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `nombre` varchar(80) DEFAULT NULL,
  `detalle` mediumtext DEFAULT NULL,
  `sucursales_id` bigint(20) NOT NULL,
  PRIMARY KEY (`id`),
  KEY `fk_ubicaciones_sucursales1_idx` (`sucursales_id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `ubicaciones`
--

LOCK TABLES `ubicaciones` WRITE;
/*!40000 ALTER TABLE `ubicaciones` DISABLE KEYS */;
/*!40000 ALTER TABLE `ubicaciones` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `unidad`
--

DROP TABLE IF EXISTS `unidad`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `unidad` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `nombre` varchar(80) DEFAULT NULL,
  `detalle` mediumtext DEFAULT NULL,
  `nombre_corto` varchar(5) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=18 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `unidad`
--

LOCK TABLES `unidad` WRITE;
/*!40000 ALTER TABLE `unidad` DISABLE KEYS */;
INSERT INTO `unidad` VALUES (1,'Unidad',NULL,'U'),(2,'Metros',NULL,'Mt.'),(5,'Litros',NULL,'L'),(6,'CM 3',NULL,'Cm 3'),(7,'MLL',NULL,'Mll'),(8,'Kilogramos',NULL,'Kg'),(12,'Gramos',NULL,'Gr'),(13,'Onza',NULL,'Oz'),(14,'Miligramos',NULL,'Mlg'),(15,'Mt. Cuadrado',NULL,'Mt 2'),(16,'Mlmt.',NULL,'Mlmt.'),(17,'Otro',NULL,'Otro');
/*!40000 ALTER TABLE `unidad` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `usuarios`
--

DROP TABLE IF EXISTS `usuarios`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `usuarios` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `usuario` varchar(45) DEFAULT NULL,
  `password` varchar(100) DEFAULT NULL,
  `tipo` varchar(20) DEFAULT NULL,
  `observacion` mediumtext DEFAULT NULL,
  `app_id` int(11) NOT NULL,
  `personas_id` int(11) NOT NULL,
  `mail` varchar(80) DEFAULT NULL,
  `key_user` varchar(250) DEFAULT NULL,
  `key_gravatar` varchar(150) DEFAULT NULL,
  `estado` bit(1) NOT NULL DEFAULT b'1',
  `tipo_usuario` tinyint(4) NOT NULL COMMENT '1: normal  app\n2: facebook\n3: twitter\n',
  `id_red_social` varchar(254) DEFAULT NULL,
  `logo` tinytext DEFAULT NULL,
  `alt_logo` tinytext DEFAULT NULL,
  `tipo_logo` tinyint(4) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `fk_usuarios_app1_idx` (`app_id`),
  KEY `fk_usuarios_personas1_idx` (`personas_id`),
  CONSTRAINT `fk_usuarios_app1` FOREIGN KEY (`app_id`) REFERENCES `app` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT `fk_usuarios_personas1` FOREIGN KEY (`personas_id`) REFERENCES `personas` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB AUTO_INCREMENT=72 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `usuarios`
--

LOCK TABLES `usuarios` WRITE;
/*!40000 ALTER TABLE `usuarios` DISABLE KEYS */;
/*!40000 ALTER TABLE `usuarios` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `usuarios_roles`
--

DROP TABLE IF EXISTS `usuarios_roles`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `usuarios_roles` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `usuarios_id` int(11) NOT NULL,
  `roles_id` int(11) NOT NULL,
  PRIMARY KEY (`id`),
  KEY `fk_usuarios_roles_usuarios1_idx` (`usuarios_id`),
  KEY `fk_usuarios_roles_roles1_idx` (`roles_id`),
  CONSTRAINT `fk_usuarios_roles_roles1` FOREIGN KEY (`roles_id`) REFERENCES `roles` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT `fk_usuarios_roles_usuarios1` FOREIGN KEY (`usuarios_id`) REFERENCES `usuarios` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `usuarios_roles`
--

LOCK TABLES `usuarios_roles` WRITE;
/*!40000 ALTER TABLE `usuarios_roles` DISABLE KEYS */;
/*!40000 ALTER TABLE `usuarios_roles` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `usuarios_sucursales`
--

DROP TABLE IF EXISTS `usuarios_sucursales`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `usuarios_sucursales` (
  `usuarios_id` int(11) NOT NULL,
  `sucursales_id` bigint(20) NOT NULL,
  PRIMARY KEY (`usuarios_id`,`sucursales_id`),
  KEY `fk_usuarios_has_sucursales_sucursales1_idx` (`sucursales_id`),
  KEY `fk_usuarios_has_sucursales_usuarios1_idx` (`usuarios_id`),
  CONSTRAINT `fk_usuarios_has_sucursales_sucursales1` FOREIGN KEY (`sucursales_id`) REFERENCES `sucursales` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT `fk_usuarios_has_sucursales_usuarios1` FOREIGN KEY (`usuarios_id`) REFERENCES `usuarios` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `usuarios_sucursales`
--

LOCK TABLES `usuarios_sucursales` WRITE;
/*!40000 ALTER TABLE `usuarios_sucursales` DISABLE KEYS */;
/*!40000 ALTER TABLE `usuarios_sucursales` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `vencimientos_productos`
--

DROP TABLE IF EXISTS `vencimientos_productos`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `vencimientos_productos` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `fecha_vencimiento` datetime NOT NULL,
  `descripcion` tinytext DEFAULT NULL,
  `activo` bit(1) NOT NULL,
  `dias_alerta` tinyint(4) NOT NULL COMMENT 'dias antes a aplicar alerta\n',
  `producto_id` bigint(20) DEFAULT NULL,
  `alerta_vencimientos` bit(1) NOT NULL,
  `tipo` tinyint(4) NOT NULL COMMENT 'tipo de factura\n1: por producto\n2:por factura\n\n',
  `lote` varchar(250) DEFAULT NULL,
  `cont_factura_det_id` bigint(20) DEFAULT NULL,
  `cantidad_productos` decimal(10,4) DEFAULT NULL,
  `fecha_carga` datetime NOT NULL,
  `app_id` int(11) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `fk_vencimientos_productos_producto1_idx` (`producto_id`),
  KEY `fk_vencimientos_productos_cont_factura_det1_idx` (`cont_factura_det_id`),
  CONSTRAINT `fk_vencimientos_productos_cont_factura_det1` FOREIGN KEY (`cont_factura_det_id`) REFERENCES `cont_factura_det` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT `fk_vencimientos_productos_producto1` FOREIGN KEY (`producto_id`) REFERENCES `producto` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `vencimientos_productos`
--

LOCK TABLES `vencimientos_productos` WRITE;
/*!40000 ALTER TABLE `vencimientos_productos` DISABLE KEYS */;
/*!40000 ALTER TABLE `vencimientos_productos` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2019-07-08 12:31:48
