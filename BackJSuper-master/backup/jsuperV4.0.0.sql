-- MySQL dump 10.17  Distrib 10.3.15-MariaDB, for debian-linux-gnu (x86_64)
--
-- Host: localhost    Database: jsupertest
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
) ENGINE=InnoDB AUTO_INCREMENT=40 DEFAULT CHARSET=latin1;
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
  `punto_venta_id` tinyint(4) DEFAULT NULL,
  `comprobantes_hab` tinytext DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `fk_caja_app1_idx` (`app_id`),
  CONSTRAINT `fk_caja_app1` FOREIGN KEY (`app_id`) REFERENCES `app` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB AUTO_INCREMENT=22 DEFAULT CHARSET=utf8;
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
  `sucursales_id` int(11) NOT NULL,
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
-- Table structure for table `cbte_comp_adicional`
--

DROP TABLE IF EXISTS `cbte_comp_adicional`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `cbte_comp_adicional` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `nombre` varchar(250) NOT NULL,
  `importe` decimal(10,4) NOT NULL,
  `cbte_comp_enc_id` int(11) NOT NULL,
  PRIMARY KEY (`id`),
  KEY `fk_cont_factura_adicional_cbte_comp_enc1_idx` (`cbte_comp_enc_id`),
  CONSTRAINT `fk_cont_factura_adicional_cbte_comp_enc1` FOREIGN KEY (`cbte_comp_enc_id`) REFERENCES `cbte_comp_enc` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `cbte_comp_adicional`
--

LOCK TABLES `cbte_comp_adicional` WRITE;
/*!40000 ALTER TABLE `cbte_comp_adicional` DISABLE KEYS */;
/*!40000 ALTER TABLE `cbte_comp_adicional` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `cbte_comp_descuento`
--

DROP TABLE IF EXISTS `cbte_comp_descuento`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `cbte_comp_descuento` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `nombre` varchar(250) NOT NULL,
  `importe` decimal(10,4) NOT NULL,
  `cbte_comp_enc_id` int(11) NOT NULL,
  PRIMARY KEY (`id`),
  KEY `fk_cont_factura_descuento_cbte_comp_enc1_idx` (`cbte_comp_enc_id`),
  CONSTRAINT `fk_cont_factura_descuento_cbte_comp_enc1` FOREIGN KEY (`cbte_comp_enc_id`) REFERENCES `cbte_comp_enc` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `cbte_comp_descuento`
--

LOCK TABLES `cbte_comp_descuento` WRITE;
/*!40000 ALTER TABLE `cbte_comp_descuento` DISABLE KEYS */;
/*!40000 ALTER TABLE `cbte_comp_descuento` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `cbte_comp_det`
--

DROP TABLE IF EXISTS `cbte_comp_det`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `cbte_comp_det` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `id_producto` int(11) NOT NULL,
  `nombre_producto` varchar(254) NOT NULL,
  `cantidad` decimal(10,4) NOT NULL,
  `precio_sin_iva` decimal(10,4) DEFAULT NULL,
  `precio_con_iva` decimal(10,4) DEFAULT NULL,
  `total` decimal(10,4) DEFAULT NULL,
  `iva_des` varchar(20) DEFAULT NULL,
  `cant_agregada_stock` bit(1) NOT NULL COMMENT 'Indica si la cantidad fue agregada totalmente en stock',
  `iva_id` int(11) DEFAULT NULL,
  `cbte_comp_enc_id` int(11) NOT NULL,
  `base_imponible` decimal(10,4) DEFAULT NULL,
  `iva_total` decimal(10,4) DEFAULT NULL,
  `iva_unidad` decimal(10,4) DEFAULT NULL,
  `porcentaje_descuento` decimal(8,4) DEFAULT NULL,
  `descuento` decimal(9,4) DEFAULT NULL,
  `base_imponible_total` decimal(12,4) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `fk_cbte_comp_det_cbte_comp_enc1_idx` (`cbte_comp_enc_id`),
  CONSTRAINT `fk_cbte_comp_det_cbte_comp_enc1` FOREIGN KEY (`cbte_comp_enc_id`) REFERENCES `cbte_comp_enc` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB AUTO_INCREMENT=244 DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `cbte_comp_det`
--

LOCK TABLES `cbte_comp_det` WRITE;
/*!40000 ALTER TABLE `cbte_comp_det` DISABLE KEYS */;
/*!40000 ALTER TABLE `cbte_comp_det` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `cbte_comp_enc`
--

DROP TABLE IF EXISTS `cbte_comp_enc`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `cbte_comp_enc` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `fecha_carga` datetime NOT NULL,
  `fecha_cbte` datetime NOT NULL,
  `fecha_venc_cbte` datetime DEFAULT NULL,
  `numero` varchar(100) DEFAULT NULL,
  `total` decimal(12,4) NOT NULL,
  `tipo` tinyint(4) NOT NULL COMMENT '1 - factura de compra\n2 - factura de venta\n',
  `tipo_cbte` varchar(50) NOT NULL,
  `id_proveedor` int(11) DEFAULT NULL,
  `id_sucursal` int(11) DEFAULT NULL,
  `activo` bit(1) NOT NULL,
  `motivo_anulacion` tinytext DEFAULT NULL,
  `app_id` int(11) NOT NULL,
  `total_trib` decimal(10,4) DEFAULT NULL,
  `cargada` tinyint(4) DEFAULT NULL COMMENT 'Determina si la factura  fue cargada completamente o no\n0: no fue cargada nada\n1: cargada a medias\n2: cargada completamente',
  `tipo_pago` tinyint(4) DEFAULT NULL COMMENT 'Tipo pago 1: contado , 2: cuta corriente',
  `pagada` bit(1) DEFAULT NULL,
  `total_base_imp` decimal(10,4) DEFAULT NULL,
  `total_iva` decimal(10,4) DEFAULT NULL,
  `saldo` decimal(10,4) DEFAULT NULL,
  `sucursales_id` int(11) NOT NULL,
  `cae` varchar(45) DEFAULT NULL,
  `observacion` mediumtext DEFAULT NULL,
  `proveedores_id` int(11) unsigned DEFAULT NULL,
  `nombre_proveedor` varchar(80) DEFAULT NULL,
  `direccion_proveedor` varchar(150) DEFAULT NULL,
  `tipo_doc_proveedor` varchar(80) DEFAULT NULL,
  `nro_doc_proveedor` varchar(80) DEFAULT NULL,
  `tipo_proveedor` tinyint(3) DEFAULT NULL,
  `concepto` tinyint(3) DEFAULT NULL,
  `cae_venc` datetime DEFAULT NULL,
  `tipo_factura` tinyint(2) NOT NULL,
  PRIMARY KEY (`id`),
  KEY `fk_cont_factura_enc_app1_idx` (`app_id`),
  KEY `fk_cbte_comp_enc_sucursales1_idx` (`sucursales_id`),
  KEY `fk_cbte_comp_enc_proveedores1_idx` (`proveedores_id`),
  CONSTRAINT `fk_cbte_comp_enc_proveedores1` FOREIGN KEY (`proveedores_id`) REFERENCES `proveedores` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT `fk_cbte_comp_enc_sucursales1` FOREIGN KEY (`sucursales_id`) REFERENCES `sucursales` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT `fk_cont_factura_enc_app1` FOREIGN KEY (`app_id`) REFERENCES `app` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB AUTO_INCREMENT=187 DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `cbte_comp_enc`
--

LOCK TABLES `cbte_comp_enc` WRITE;
/*!40000 ALTER TABLE `cbte_comp_enc` DISABLE KEYS */;
/*!40000 ALTER TABLE `cbte_comp_enc` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `cbte_comp_impuestos`
--

DROP TABLE IF EXISTS `cbte_comp_impuestos`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `cbte_comp_impuestos` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `nombre` varchar(250) NOT NULL,
  `porcentaje` decimal(10,4) DEFAULT NULL,
  `importe` decimal(10,4) NOT NULL,
  `cbte_comp_enc_id` int(11) NOT NULL,
  `impuestos_id` int(11) NOT NULL,
  PRIMARY KEY (`id`),
  KEY `fk_cont_factura_impuestos_cbte_comp_enc1_idx` (`cbte_comp_enc_id`),
  KEY `fk_cbte_comp_impuestos_impuestos1_idx` (`impuestos_id`),
  CONSTRAINT `fk_cbte_comp_impuestos_impuestos1` FOREIGN KEY (`impuestos_id`) REFERENCES `impuestos` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT `fk_cont_factura_impuestos_cbte_comp_enc1` FOREIGN KEY (`cbte_comp_enc_id`) REFERENCES `cbte_comp_enc` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `cbte_comp_impuestos`
--

LOCK TABLES `cbte_comp_impuestos` WRITE;
/*!40000 ALTER TABLE `cbte_comp_impuestos` DISABLE KEYS */;
/*!40000 ALTER TABLE `cbte_comp_impuestos` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `cbte_ven_det`
--

DROP TABLE IF EXISTS `cbte_ven_det`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `cbte_ven_det` (
  `id` int(11) unsigned NOT NULL AUTO_INCREMENT,
  `cantidad` decimal(9,4) NOT NULL,
  `precio` decimal(10,4) NOT NULL COMMENT 'Precio real del producto',
  `codigoBarra` varchar(45) DEFAULT NULL,
  `descuento` decimal(9,4) DEFAULT NULL,
  `adicional` decimal(9,4) DEFAULT NULL,
  `descripcion` varchar(150) NOT NULL,
  `productos_id` int(11) DEFAULT NULL,
  `tipoPrecioVenta` varchar(100) DEFAULT NULL,
  `total` decimal(10,4) NOT NULL,
  `precio_costo` decimal(10,4) DEFAULT NULL,
  `ganancia` decimal(10,4) DEFAULT NULL COMMENT 'ganancia\nprecio - preciocosto + adicional - descuento',
  `precio_sin_iva` decimal(10,4) DEFAULT NULL,
  `precio_con_iva` decimal(10,4) DEFAULT NULL,
  `iva_id` tinyint(4) DEFAULT NULL,
  `iva_des` varchar(20) DEFAULT NULL,
  `base_imponible` decimal(10,4) DEFAULT NULL,
  `info_prod_adic` mediumtext DEFAULT NULL COMMENT 'informacion del producto adicional, como ser un numero de serie',
  `numero_serie` varchar(250) DEFAULT NULL,
  `oferta` bit(1) DEFAULT NULL,
  `oferta_data` mediumtext DEFAULT NULL,
  `oferta_descuento` decimal(10,4) DEFAULT NULL,
  `cbte_ven_enc_id` int(11) unsigned DEFAULT NULL,
  `base_imponible_total` decimal(10,4) DEFAULT NULL,
  `iva_total` decimal(10,4) DEFAULT NULL,
  `iva_unidad` decimal(10,4) DEFAULT NULL,
  `porcentaje_descuento` decimal(8,4) DEFAULT NULL,
  `porcentaje_adicional` decimal(8,4) DEFAULT NULL,
  `iva_value` decimal(5,4) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `fk_detalleVenta_productos1_idx` (`productos_id`),
  KEY `fk_cbte_ven_det_cbte_ven_enc1_idx` (`cbte_ven_enc_id`),
  CONSTRAINT `fk_cbte_ven_det_cbte_ven_enc1` FOREIGN KEY (`cbte_ven_enc_id`) REFERENCES `cbte_ven_enc` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB AUTO_INCREMENT=35777 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `cbte_ven_det`
--

LOCK TABLES `cbte_ven_det` WRITE;
/*!40000 ALTER TABLE `cbte_ven_det` DISABLE KEYS */;
/*!40000 ALTER TABLE `cbte_ven_det` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `cbte_ven_enc`
--

DROP TABLE IF EXISTS `cbte_ven_enc`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `cbte_ven_enc` (
  `id` int(11) unsigned NOT NULL AUTO_INCREMENT,
  `tipo_cbte` int(11) NOT NULL,
  `fecha_cbte` datetime DEFAULT NULL,
  `fecha_carga` datetime NOT NULL,
  `total` decimal(12,4) DEFAULT NULL,
  `observacion` mediumtext DEFAULT NULL,
  `clientes_id` int(11) DEFAULT NULL,
  `sucursales_id` int(11) DEFAULT NULL,
  `nombre_cliente` varchar(80) DEFAULT NULL,
  `direccion_cliente` varchar(100) DEFAULT NULL,
  `nro_doc_cliente` varchar(80) DEFAULT NULL,
  `tipo_doc_cliente` tinyint(3) DEFAULT NULL,
  `tipo_cliente` int(11) DEFAULT NULL,
  `app_id` int(11) NOT NULL,
  `transaccion_caja_id` int(11) DEFAULT NULL,
  `id_cliente` int(11) DEFAULT NULL,
  `estado` bit(1) NOT NULL DEFAULT b'1',
  `usuarios_id` int(11) unsigned NOT NULL,
  `pagada` bit(1) DEFAULT NULL,
  `saldo` decimal(10,4) DEFAULT NULL,
  `origen` tinyint(3) NOT NULL COMMENT '1: normal de punto de venta\\\\n2: fe desde la web\\\\n',
  `concepto` tinyint(3) DEFAULT NULL COMMENT 'Concepto, producot, serv o pserv y producto',
  `fecha_desde` datetime DEFAULT NULL,
  `fecha_hasta` datetime DEFAULT NULL,
  `fecha_ven_pag` datetime DEFAULT NULL,
  `pto_venta` tinyint(3) DEFAULT NULL,
  `total_iva` decimal(10,4) DEFAULT NULL,
  `total_base_imp` decimal(10,4) DEFAULT NULL,
  `fe` bit(1) DEFAULT NULL,
  `cae` varchar(200) DEFAULT NULL,
  `cae_venc` datetime DEFAULT NULL,
  `total_trib` decimal(10,4) DEFAULT NULL,
  `razon_social_empresa` varchar(220) DEFAULT NULL COMMENT 'Es la razon social actual de la empresa, com opodria cambiar, la guardamos para que quede registtarado como se imprimio el cbte cuando se lo realizo',
  `condicion_empresa` varchar(150) DEFAULT NULL COMMENT 'Es la condicion de la empresa ante la afip, com opodria cambiar, la guardamos para que quede registtarado como se imprimio el cbte cuando se lo realizo',
  `fecha_ini_act_empresa` varchar(45) DEFAULT NULL COMMENT 'Es la fecha de inicio de actividad de la empresa, com opodria cambiar, la guardamos para que quede registtarado como se imprimio el cbte cuando se lo realizo',
  `ing_brutos_empresa` varchar(50) DEFAULT NULL COMMENT 'Es el ing brutos de la la empresa, com opodria cambiar, la guardamos para que quede registtarado como se imprimio el cbte cuando se lo realizo',
  `cuit_empresa` varchar(45) DEFAULT NULL COMMENT 'Es la cuit actual de la empresa, com opodria cambiar, la guardamos para que quede registtarado como se imprimio el cbte cuando se lo realizo',
  `dom_comercial_empresa` varchar(45) DEFAULT NULL COMMENT 'Es el domicilio actual de la empresa, como opodria cambiar, la guardamos para que quede registtarado como se imprimio el cbte cuando se lo realizo',
  `cbte_nro` varchar(20) DEFAULT NULL,
  `afip_modo` varchar(10) DEFAULT NULL,
  `afip_valida` bit(1) DEFAULT NULL,
  `lista_precio` bit(1) DEFAULT NULL,
  `lista_precio_data` mediumtext DEFAULT NULL,
  `tipo_factura` tinyint(2) NOT NULL DEFAULT 20 COMMENT 'Si es una factura electronica, un ticket  o una no valida',
  `afip_error` text DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `fk_encabezadoventas_sucursales1_idx` (`sucursales_id`),
  KEY `fk_encabezadoventas_app1_idx` (`app_id`),
  KEY `fk_encabezadoventas_transaccion_caja1_idx` (`transaccion_caja_id`),
  KEY `fk_encabezadoventas_usuarios1_idx` (`usuarios_id`),
  CONSTRAINT `fk_encabezadoventas_app1` FOREIGN KEY (`app_id`) REFERENCES `app` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT `fk_encabezadoventas_sucursales1` FOREIGN KEY (`sucursales_id`) REFERENCES `sucursales` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT `fk_encabezadoventas_transaccion_caja1` FOREIGN KEY (`transaccion_caja_id`) REFERENCES `transaccion_caja` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT `fk_encabezadoventas_usuarios1` FOREIGN KEY (`usuarios_id`) REFERENCES `usuarios` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB AUTO_INCREMENT=19425 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `cbte_ven_enc`
--

LOCK TABLES `cbte_ven_enc` WRITE;
/*!40000 ALTER TABLE `cbte_ven_enc` DISABLE KEYS */;
/*!40000 ALTER TABLE `cbte_ven_enc` ENABLE KEYS */;
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
  `usuarios_id` int(11) unsigned NOT NULL,
  PRIMARY KEY (`id`),
  KEY `fk_claves_app1_idx` (`app_id`),
  KEY `fk_claves_usuarios1_idx` (`usuarios_id`),
  CONSTRAINT `fk_claves_app1` FOREIGN KEY (`app_id`) REFERENCES `app` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT `fk_claves_usuarios1` FOREIGN KEY (`usuarios_id`) REFERENCES `usuarios` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=latin1;
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
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `estado` bit(1) NOT NULL DEFAULT b'0',
  `tipo_cliente` tinyint(1) NOT NULL COMMENT 'value: 0, name: Sin especificar\\\\\\\\nvalue: 1, name: Consumidor Final\\\\\\\\nvalue: 2, name: Monotributo\\\\\\\\nvalue: 3, name: Responsable Incripto\\\\\\\\nvalue: 4, name: Otro',
  `observacion` mediumtext DEFAULT NULL,
  `app_id` int(11) NOT NULL,
  `entidad_id` int(11) unsigned NOT NULL,
  `tipo_doc_cliente` tinyint(1) unsigned NOT NULL,
  `nro_doc_cliente` varchar(45) NOT NULL,
  PRIMARY KEY (`id`),
  KEY `fk_clientes_app1_idx` (`app_id`),
  KEY `fk_clientes_entidad1_idx` (`entidad_id`),
  CONSTRAINT `fk_clientes_app1` FOREIGN KEY (`app_id`) REFERENCES `app` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT `fk_clientes_entidad1` FOREIGN KEY (`entidad_id`) REFERENCES `entidad` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB AUTO_INCREMENT=39 DEFAULT CHARSET=utf8;
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
  `producto_id` int(11) unsigned DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `fk_codigos_barras_producto1_idx` (`producto_id`),
  KEY `codigo_idx` (`codigo`),
  CONSTRAINT `fk_codigos_barras_producto1` FOREIGN KEY (`producto_id`) REFERENCES `producto` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB AUTO_INCREMENT=1424 DEFAULT CHARSET=utf8 COLLATE=utf8_spanish_ci;
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
  `backup_hab` bit(1) DEFAULT NULL,
  `backup_cron` varchar(250) DEFAULT NULL,
  `enabled_venta` bit(1) NOT NULL DEFAULT b'0',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=41 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `configuracion`
--

LOCK TABLES `configuracion` WRITE;
/*!40000 ALTER TABLE `configuracion` DISABLE KEYS */;
/*!40000 ALTER TABLE `configuracion` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `contactos`
--

DROP TABLE IF EXISTS `contactos`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `contactos` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `tipo` tinyint(4) NOT NULL COMMENT '1:telefono fijo\n2:telefono celular\n3:correo\n4:red social face\n5: red social twiter\n6:otro',
  `descripcion` varchar(254) NOT NULL,
  `subtipo` varchar(45) DEFAULT NULL,
  `detalle` tinytext DEFAULT NULL,
  `entidad` varchar(45) DEFAULT NULL,
  `entidad_id` int(11) unsigned NOT NULL,
  PRIMARY KEY (`id`),
  KEY `fk_contactos_entidad1_idx` (`entidad_id`),
  CONSTRAINT `fk_contactos_entidad1` FOREIGN KEY (`entidad_id`) REFERENCES `entidad` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB AUTO_INCREMENT=24 DEFAULT CHARSET=latin1;
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
  `cliente_id` int(11) NOT NULL,
  PRIMARY KEY (`cliente_id`),
  CONSTRAINT `fk_cuentas_corrientes_clientes1` FOREIGN KEY (`cliente_id`) REFERENCES `clientes` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION
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
  `proveedores_id` int(11) unsigned NOT NULL,
  PRIMARY KEY (`proveedores_id`),
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
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `cantidad` decimal(10,4) NOT NULL,
  `valor_inicial` decimal(10,4) NOT NULL,
  `valor_final` decimal(10,4) NOT NULL,
  `tipo` tinyint(2) NOT NULL,
  `referencia` varchar(250) DEFAULT NULL,
  `enc_movimientos_id` int(11) unsigned NOT NULL,
  `producto_id` int(11) unsigned NOT NULL,
  `origen_producto_id` int(11) DEFAULT NULL,
  `fecha_vencimiento` date DEFAULT NULL,
  `lote` varchar(80) DEFAULT NULL,
  `id_producto` int(11) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `fk_det_movimientos_enc_movimientos1_idx` (`enc_movimientos_id`),
  KEY `fk_det_movimientos_producto1_idx` (`producto_id`),
  CONSTRAINT `fk_det_movimientos_enc_movimientos1` FOREIGN KEY (`enc_movimientos_id`) REFERENCES `enc_movimientos` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT `fk_det_movimientos_producto1` FOREIGN KEY (`producto_id`) REFERENCES `producto` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB AUTO_INCREMENT=298 DEFAULT CHARSET=latin1;
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
  `transaccion_caja_id` int(11) NOT NULL,
  `monto` decimal(9,4) DEFAULT 0.0000,
  `id_usuario_auth` varchar(32) DEFAULT NULL,
  `nombre_usuario_auth` varchar(120) DEFAULT NULL,
  `dni_usuario_auth` varchar(10) DEFAULT NULL,
  `motivo` varchar(80) DEFAULT NULL,
  `subtipo` tinyint(4) DEFAULT NULL COMMENT 'Subtipo dentro de tipo\n',
  `asociada` bit(1) DEFAULT NULL COMMENT 'indica si esta asociada a otra tabla, por ej a una factura de compra',
  `asociada_tipo` tinyint(4) DEFAULT NULL COMMENT 'indica el tipo de asociasda por eje para la factura de compra es 1\nfactura de compra: 1\n',
  `asociada_id` int(11) DEFAULT NULL COMMENT 'el id de la asociada en cuestion',
  `usuarios_id` int(11) unsigned DEFAULT NULL,
  `cbte_ven_enc_id` int(11) unsigned DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `fk_detalle_transaccion_caja_transaccion_caja1_idx` (`transaccion_caja_id`),
  KEY `fk_detalle_transaccion_caja_usuarios1_idx` (`usuarios_id`),
  KEY `fk_detalle_transaccion_caja_cbte_ven_enc1_idx` (`cbte_ven_enc_id`),
  CONSTRAINT `fk_detalle_transaccion_caja_cbte_ven_enc1` FOREIGN KEY (`cbte_ven_enc_id`) REFERENCES `cbte_ven_enc` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT `fk_detalle_transaccion_caja_transaccion_caja1` FOREIGN KEY (`transaccion_caja_id`) REFERENCES `transaccion_caja` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT `fk_detalle_transaccion_caja_usuarios1` FOREIGN KEY (`usuarios_id`) REFERENCES `usuarios` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB AUTO_INCREMENT=1465 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `detalle_transaccion_caja`
--

LOCK TABLES `detalle_transaccion_caja` WRITE;
/*!40000 ALTER TABLE `detalle_transaccion_caja` DISABLE KEYS */;
/*!40000 ALTER TABLE `detalle_transaccion_caja` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `domicilios`
--

DROP TABLE IF EXISTS `domicilios`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `domicilios` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `calle` varchar(100) NOT NULL,
  `numero` int(11) DEFAULT NULL,
  `dpto` varchar(45) DEFAULT NULL,
  `piso` varchar(45) DEFAULT NULL,
  `provincia` varchar(45) DEFAULT NULL,
  `ciudad` varchar(150) DEFAULT NULL,
  `detalle` tinytext DEFAULT NULL,
  `entidad` varchar(45) DEFAULT NULL COMMENT 'si de una persona  y otra entidad como ser proveedores\n',
  `tipo` tinyint(4) DEFAULT NULL,
  `principal` bit(1) DEFAULT NULL,
  `entidad_id` int(11) unsigned NOT NULL,
  PRIMARY KEY (`id`),
  KEY `fk_domicilios_entidad1_idx` (`entidad_id`),
  CONSTRAINT `fk_domicilios_entidad1` FOREIGN KEY (`entidad_id`) REFERENCES `entidad` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB AUTO_INCREMENT=43 DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `domicilios`
--

LOCK TABLES `domicilios` WRITE;
/*!40000 ALTER TABLE `domicilios` DISABLE KEYS */;
/*!40000 ALTER TABLE `domicilios` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `empresas`
--

DROP TABLE IF EXISTS `empresas`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `empresas` (
  `razon_social` varchar(200) NOT NULL,
  `cuit` varchar(20) NOT NULL,
  `observacion` mediumtext DEFAULT NULL,
  `tipo` tinyint(4) NOT NULL,
  `entidad_id` int(11) unsigned NOT NULL,
  `personas_entidad_id` int(11) unsigned DEFAULT NULL,
  PRIMARY KEY (`entidad_id`),
  KEY `fk_empresas_personas1_idx` (`personas_entidad_id`),
  CONSTRAINT `fk_empresas_entidad1` FOREIGN KEY (`entidad_id`) REFERENCES `entidad` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT `fk_empresas_personas1` FOREIGN KEY (`personas_entidad_id`) REFERENCES `personas` (`entidad_id`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `empresas`
--

LOCK TABLES `empresas` WRITE;
/*!40000 ALTER TABLE `empresas` DISABLE KEYS */;
/*!40000 ALTER TABLE `empresas` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `enc_movimientos`
--

DROP TABLE IF EXISTS `enc_movimientos`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `enc_movimientos` (
  `id` int(11) unsigned NOT NULL AUTO_INCREMENT,
  `fecha_carga` datetime NOT NULL,
  `fecha_ingreso` datetime NOT NULL,
  `descripcion` tinytext DEFAULT NULL,
  `numero` varchar(200) DEFAULT NULL,
  `tipo` tinyint(2) DEFAULT NULL COMMENT '2 - ingreso\n3- egreso\n4- pase',
  `subtipo` tinyint(2) DEFAULT NULL COMMENT 'tipo2: - 1: otros, 2: factura\\ntipo3:- 1: uso personal, 2: robo',
  `sucursales_id` int(11) NOT NULL,
  `pase_movimientos_id` int(11) DEFAULT NULL,
  `cbte_comp_enc_id` int(11) DEFAULT NULL,
  `proveedores_id` int(11) unsigned DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `fk_enc_movimientos_sucursales1_idx` (`sucursales_id`),
  KEY `fk_enc_movimientos_pase_movimientos1_idx` (`pase_movimientos_id`),
  KEY `fk_enc_movimientos_cbte_comp_enc1_idx` (`cbte_comp_enc_id`),
  KEY `fk_enc_movimientos_proveedores1_idx` (`proveedores_id`),
  CONSTRAINT `fk_enc_movimientos_cbte_comp_enc1` FOREIGN KEY (`cbte_comp_enc_id`) REFERENCES `cbte_comp_enc` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT `fk_enc_movimientos_pase_movimientos1` FOREIGN KEY (`pase_movimientos_id`) REFERENCES `pase_movimientos` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT `fk_enc_movimientos_proveedores1` FOREIGN KEY (`proveedores_id`) REFERENCES `proveedores` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT `fk_enc_movimientos_sucursales1` FOREIGN KEY (`sucursales_id`) REFERENCES `sucursales` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB AUTO_INCREMENT=276 DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `enc_movimientos`
--

LOCK TABLES `enc_movimientos` WRITE;
/*!40000 ALTER TABLE `enc_movimientos` DISABLE KEYS */;
/*!40000 ALTER TABLE `enc_movimientos` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `entidad`
--

DROP TABLE IF EXISTS `entidad`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `entidad` (
  `id` int(11) unsigned NOT NULL AUTO_INCREMENT,
  `fecha_alta` datetime NOT NULL,
  `observacion` mediumtext DEFAULT NULL,
  `app_id` int(11) NOT NULL,
  `tipo` tinyint(2) NOT NULL COMMENT '1: persona\n2: empresa',
  PRIMARY KEY (`id`),
  KEY `fk_entidad_app1_idx` (`app_id`),
  CONSTRAINT `fk_entidad_app1` FOREIGN KEY (`app_id`) REFERENCES `app` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB AUTO_INCREMENT=86 DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `entidad`
--

LOCK TABLES `entidad` WRITE;
/*!40000 ALTER TABLE `entidad` DISABLE KEYS */;
/*!40000 ALTER TABLE `entidad` ENABLE KEYS */;
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
  `monto` decimal(9,4) DEFAULT NULL,
  `cbte_ven_enc_id` int(11) unsigned NOT NULL,
  PRIMARY KEY (`id`),
  KEY `fk_eventos_ventas_cbte_ven_enc1_idx` (`cbte_ven_enc_id`),
  CONSTRAINT `fk_eventos_ventas_cbte_ven_enc1` FOREIGN KEY (`cbte_ven_enc_id`) REFERENCES `cbte_ven_enc` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB AUTO_INCREMENT=5389 DEFAULT CHARSET=latin1;
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
) ENGINE=InnoDB AUTO_INCREMENT=161 DEFAULT CHARSET=utf8;
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
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `descripcion` varchar(45) DEFAULT NULL,
  `detalle` mediumtext DEFAULT NULL,
  `activo` bit(1) DEFAULT NULL,
  `proveedor` bit(1) DEFAULT NULL COMMENT 'Si esta habilitado para proveedores',
  `fac_elec` bit(1) DEFAULT NULL,
  `cta_cte_cli` bit(1) DEFAULT NULL,
  `cta_cte_prov` bit(1) DEFAULT NULL,
  `cliente` bit(1) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=5 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `formapagos`
--

LOCK TABLES `formapagos` WRITE;
/*!40000 ALTER TABLE `formapagos` DISABLE KEYS */;
INSERT INTO `formapagos` VALUES (1,'EFECTIVO','','','','','','',''),(2,'TARJETA DE CREDITO','','','','','','',''),(3,'TARJETA DE DEBITO','','','','','','',''),(4,'CUENTA CORRIENTE','','','','\0','\0','\0','');
/*!40000 ALTER TABLE `formapagos` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `hist_precios`
--

DROP TABLE IF EXISTS `hist_precios`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `hist_precios` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `fecha` datetime NOT NULL,
  `precio_inicial` decimal(10,4) NOT NULL,
  `precio_final` decimal(10,4) NOT NULL,
  `detalle` tinytext DEFAULT NULL,
  `producto_id` int(11) unsigned NOT NULL,
  `tipo_precios_id` int(11) NOT NULL,
  PRIMARY KEY (`id`),
  KEY `fk_hist_precios_producto1_idx` (`producto_id`),
  KEY `fk_hist_precios_tipo_precios1_idx` (`tipo_precios_id`),
  CONSTRAINT `fk_hist_precios_producto1` FOREIGN KEY (`producto_id`) REFERENCES `producto` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT `fk_hist_precios_tipo_precios1` FOREIGN KEY (`tipo_precios_id`) REFERENCES `tipo_precios` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB AUTO_INCREMENT=128 DEFAULT CHARSET=latin1;
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
  `producto_id` int(11) unsigned DEFAULT NULL,
  `alt` varchar(100) COLLATE utf8_spanish_ci DEFAULT NULL,
  `orden` int(11) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `fk_imagen_producto_producto1_idx` (`producto_id`),
  CONSTRAINT `fk_imagen_producto_producto1` FOREIGN KEY (`producto_id`) REFERENCES `producto` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB AUTO_INCREMENT=33 DEFAULT CHARSET=utf8 COLLATE=utf8_spanish_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `imagen_producto`
--

LOCK TABLES `imagen_producto` WRITE;
/*!40000 ALTER TABLE `imagen_producto` DISABLE KEYS */;
/*!40000 ALTER TABLE `imagen_producto` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `impuestos`
--

DROP TABLE IF EXISTS `impuestos`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `impuestos` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `nombre` varchar(250) NOT NULL,
  `activo` bit(1) NOT NULL,
  `porcentaje` decimal(10,4) DEFAULT NULL,
  `tipo` tinyint(4) DEFAULT NULL,
  `app_id` int(11) NOT NULL,
  `detalle` mediumtext DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=5 DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `impuestos`
--

LOCK TABLES `impuestos` WRITE;
/*!40000 ALTER TABLE `impuestos` DISABLE KEYS */;
/*!40000 ALTER TABLE `impuestos` ENABLE KEYS */;
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
  `descripcion` varchar(30) NOT NULL,
  `tipo_cbte` tinyint(1) NOT NULL,
  `alicuota` decimal(8,4) DEFAULT NULL,
  `id_cbte` int(11) unsigned NOT NULL COMMENT 'Aqui va el id del cbte, es para la venta o compra o lo que se defina en el futuro',
  PRIMARY KEY (`tipo_cbte`,`id_cbte`,`id_afip_iva`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `ivas_afip`
--

LOCK TABLES `ivas_afip` WRITE;
/*!40000 ALTER TABLE `ivas_afip` DISABLE KEYS */;
/*!40000 ALTER TABLE `ivas_afip` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `lista_precios`
--

DROP TABLE IF EXISTS `lista_precios`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `lista_precios` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `nombre` varchar(250) NOT NULL,
  `fecha_creacion` datetime NOT NULL,
  `tipo` tinyint(4) NOT NULL,
  `detalle` tinytext DEFAULT NULL,
  `valor` decimal(12,4) NOT NULL,
  `app_id` int(11) NOT NULL,
  `activo` bit(1) NOT NULL,
  PRIMARY KEY (`id`),
  KEY `fk_lista_precios_app1_idx` (`app_id`),
  CONSTRAINT `fk_lista_precios_app1` FOREIGN KEY (`app_id`) REFERENCES `app` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB AUTO_INCREMENT=12 DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `lista_precios`
--

LOCK TABLES `lista_precios` WRITE;
/*!40000 ALTER TABLE `lista_precios` DISABLE KEYS */;
/*!40000 ALTER TABLE `lista_precios` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `log`
--

DROP TABLE IF EXISTS `log`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `log` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `usuarios_id` int(11) unsigned DEFAULT NULL,
  `tipo` int(2) DEFAULT NULL,
  `accion` varchar(45) DEFAULT NULL,
  `fecha_hora` datetime DEFAULT NULL,
  `ip` varchar(20) DEFAULT NULL,
  `data` mediumtext DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `fk_log_usuarios1_idx` (`usuarios_id`),
  CONSTRAINT `fk_log_usuarios1` FOREIGN KEY (`usuarios_id`) REFERENCES `usuarios` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB AUTO_INCREMENT=1376 DEFAULT CHARSET=latin1;
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
  `usuarios_id` int(11) unsigned NOT NULL,
  PRIMARY KEY (`id`),
  KEY `fk_log_users_usuarios1_idx` (`usuarios_id`),
  CONSTRAINT `fk_log_users_usuarios1` FOREIGN KEY (`usuarios_id`) REFERENCES `usuarios` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB AUTO_INCREMENT=4849 DEFAULT CHARSET=latin1;
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
  `tipo_menu` tinyint(4) NOT NULL COMMENT '1: principal',
  `test` int(11) DEFAULT NULL,
  `roles_id` int(11) NOT NULL,
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
INSERT INTO `menu` VALUES (1,'Menu Admin',NULL,1,1,1),(2,'Menu Gerente',NULL,1,3,3);
/*!40000 ALTER TABLE `menu` ENABLE KEYS */;
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
  `router_link` varchar(200) DEFAULT NULL,
  `icon2` varchar(150) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `fk_menu_item_menu_item1_idx` (`menu_item_id`),
  KEY `fk_menu_item_menu_item_data1_idx` (`menu_item_data_id`),
  CONSTRAINT `fk_menu_item_menu_item1` FOREIGN KEY (`menu_item_id`) REFERENCES `menu_item` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT `fk_menu_item_menu_item_data1` FOREIGN KEY (`menu_item_data_id`) REFERENCES `menu_item_data` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB AUTO_INCREMENT=65 DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `menu_item`
--

LOCK TABLES `menu_item` WRITE;
/*!40000 ALTER TABLE `menu_item` DISABLE KEYS */;
INSERT INTO `menu_item` VALUES (1,'Inicio',NULL,NULL,1,'',1,'admin.dashboard','fa fa-home','Menu Inicio',1,'/home',NULL),(2,'Productos',NULL,NULL,2,'',0,'admin.productos.*','fa fa-product-hunt','Productos',2,'/productos','pe-7s-diamond'),(3,'Lista de Productos',NULL,2,3,'\0',1,'admin.productos.list({activo:1, tipo:1})','fa fa-caret-right','Listado de todos los productos del sistema',1,'/productos/lista',NULL),(4,'Nuevo Producto',NULL,2,4,'\0',1,'admin.productos.new','fa fa-caret-right','Cargar un nuevo producto',2,'/productos/nuevo',NULL),(5,'Nuevo Producto Compuesto',NULL,2,5,'\0',1,'admin.productos.compuesto-new','fa fa-caret-right',NULL,3,'/productos/nuevo-producto-compuesto',NULL),(6,'Mov. de existencia',NULL,2,6,'\0',0,'admin.productos.movimiento','fa fa-caret-right',NULL,4,NULL,NULL),(7,'Precios',NULL,2,7,'\0',0,'admin.productos.modificaprecio','fa fa-caret-right',NULL,5,'/productos/precios',NULL),(8,'Modificar Precios',NULL,7,8,'\0',1,'admin.productos.modificaprecio','fa fa-caret-right',NULL,0,'/productos/precios/update',NULL),(9,'Productos Padre',NULL,2,9,'\0',1,'admin.productopadre.*','fa fa-circle',NULL,7,'/productos-padre','pe-7s-box2'),(10,'Lista de Productos Padre',NULL,9,10,'\0',1,'admin.productopadre.list','fa fa-caret-right',NULL,1,'/productos-padre/lista',NULL),(11,'Nuevo Producto Padre',NULL,9,11,'\0',1,'admin.productopadre.new','fa fa-caret-right',NULL,2,'/productos-padre/nuevo',NULL),(12,'Familias',NULL,2,12,'\0',1,'admin.familias.*','fa fa-square-o',NULL,8,'/familias/','pe-7s-airplay'),(13,'Lista de Familias',NULL,12,13,'\0',1,'admin.familias.list','fa fa-caret-right',NULL,1,'/familias/lista',NULL),(14,'Nueva Familia',NULL,12,14,'\0',1,'admin.familias.new','fa fa-caret-right',NULL,2,'/familias/nueva',NULL),(15,'Personas',NULL,58,15,'\0',1,'admin.personas.*','fa fa-male',NULL,1,NULL,NULL),(16,'Lista de Personas',NULL,15,16,'\0',1,'admin.personas.list','fa fa-caret-right',NULL,1,'/personas/lista',NULL),(17,'Nueva Persona',NULL,15,17,'\0',1,'admin.personas.new','fa fa-caret-right',NULL,2,'/personas/nueva',NULL),(18,'Usuarios',NULL,58,18,'\0',1,'admin.usuarios.*','fa fa-user',NULL,5,NULL,NULL),(19,'Lista de Usuarios',NULL,18,19,'\0',1,'admin.usuarios.list','fa fa-caret-right',NULL,1,'/usuarios/lista',NULL),(20,'Nuevo Usuario',NULL,18,20,'\0',1,'admin.usuarios.new','fa fa-caret-right',NULL,2,'/usuarios/nuevo',NULL),(21,'Clientes',NULL,58,21,'\0',1,'admin.clientes.*','fa fa-user-circle-o',NULL,3,NULL,NULL),(22,'Lista de Clientes',NULL,21,22,'\0',1,'admin.clientes.list','fa fa-caret-right',NULL,1,'/clientes/lista',NULL),(23,'Nuevo Cliente',NULL,21,23,'\0',1,'admin.clientes.new','fa fa-caret-right',NULL,2,'/clientes/nuevo',NULL),(24,'Proveedores',NULL,58,24,'\0',1,'admin.proveedores.*','fa fa-truck',NULL,4,NULL,NULL),(25,'Lista de Proveedores',NULL,24,25,'\0',1,'admin.proveedores.list','fa fa-caret-right',NULL,1,'/proveedores/lista',NULL),(26,'Nuevo Proveedor',NULL,24,26,'\0',1,'admin.proveedores.new','fa fa-caret-right',NULL,2,'/proveedores/nuevo',NULL),(27,'Ventas',NULL,NULL,27,'',0,'admin.ventas.*','fa fa-dollar',NULL,4,NULL,'pe-7s-cash'),(28,'Lista de Ventas',NULL,27,28,'\0',1,'admin.ventas.list({totalMinimo:0,totalMaximo:0,pagada:2})','fa fa-caret-right',NULL,2,'/ventas/facturas/lista',NULL),(29,'Transacciones de Caja',NULL,27,29,'\0',1,'admin.ventas.transaccioneslist','fa fa-caret-right',NULL,3,'/cajas/transacciones/lista',NULL),(30,'Lista de Cajas',NULL,27,30,'\0',1,'admin.cajas.list','fa fa-caret-right',NULL,4,'/cajas/lista',NULL),(31,'Ofertas',NULL,27,31,'\0',1,'admin.ofertas.list','fa fa-caret-right',NULL,5,'/ofertas/lista',NULL),(32,'Configuración',NULL,NULL,32,'',0,'admin.config.*','fa fa-cogs',NULL,8,'/config','pe-7s-config'),(33,'App',NULL,32,33,'\0',1,'admin.config.app','fa fa-caret-right',NULL,1,'/config/app',NULL),(34,'Cuentas Corrientes',NULL,21,34,'\0',1,'admin.clientes.mtoCtaCteList','fa fa-caret-right',NULL,2,'/clientes/cta-cte/movimientos',NULL),(35,'Configuración gral.',NULL,32,35,'\0',1,'admin.config.configuracion','fa fa-caret-right',NULL,3,'/config/data',NULL),(36,'Ingreso',NULL,6,36,'\0',1,'admin.productos.movimiento','fa fa-caret-right',NULL,1,'/productos/stock/add',NULL),(37,'Egreso',NULL,6,37,'\0',1,'admin.productos.movimiento-egreso','fa fa-caret-right',NULL,2,'/productos/stock/salida',NULL),(38,'Consultas',NULL,NULL,38,'',0,'admin.consultas.*','fa fa-database',NULL,7,NULL,NULL),(39,'Ventas',NULL,38,39,'\0',1,'admin.consultas.*','fa fa-caret-right',NULL,1,NULL,NULL),(40,'Prod. más vendidos(#)',NULL,39,40,'\0',1,'admin.consultas.productosmasvendidos','fa fa-caret-right',NULL,2,NULL,NULL),(41,'Prod. con mas entrada($)',NULL,39,41,'\0',1,'admin.consultas.productosconmasentrada','fa fa-caret-right',NULL,3,NULL,NULL),(42,'Compras',NULL,NULL,42,'',0,'admin.compras.*','fa fa-shopping-cart',NULL,5,NULL,'pe-7s-cart'),(43,'Nueva fact. de compra',NULL,42,43,'\0',1,'admin.compras.nuevafactura','fa fa-caret-right',NULL,2,'/compras/facturas/nueva',NULL),(44,'Lista fact. de compras',NULL,42,44,'\0',1,'admin.compras.listfactura','fa fa-caret-right',NULL,1,'/compras/facturas/lista',NULL),(45,'Ganancias',NULL,38,45,'\0',1,'admin.consultas.ganancias','fa fa-caret-right',NULL,2,NULL,NULL),(46,'Movimientos de caja',NULL,27,46,'\0',1,'admin.ventas.dettransaccioneslist','fa fa-caret-right',NULL,6,'/cajas/transacciones/movimientos/lista',NULL),(49,'Varios',NULL,2,49,'\0',0,'admin.utils.vencimientoslist','fa fa-caret-right',NULL,6,'/productos/varios',NULL),(50,'Vencimientos',NULL,49,50,'\0',1,'admin.utils.vencimientoslist','fa fa-caret-right',NULL,1,'/productos/varios/vencimientos',NULL),(51,'Facturación',NULL,NULL,51,'',0,'admin.fe.*','fa fa-ticket',NULL,6,NULL,NULL),(52,'Nueva Factura',NULL,51,52,'\0',1,'admin.fe.newfe','fa fa-caret-right',NULL,0,NULL,NULL),(53,'Datos de facturacion AFIP',NULL,51,53,'\0',1,'admin.fe.datos','fa fa-caret-right',NULL,1,NULL,NULL),(54,'Pases',NULL,6,54,'\0',1,'admin.productos.listpase','fa fa-caret-right',NULL,3,'/productos/stock/pases',NULL),(56,'Lista de precios',NULL,7,56,'\0',1,'admin.utils.listaprecioslist','fa fa-caret-right',NULL,2,'/productos/precios/lista-precios',NULL),(57,'Generador de etiquetas',NULL,49,57,'\0',1,'admin.utils.barcode','fa fa-caret-right',NULL,2,'/productos/varios/codigos-barra/generar',NULL),(58,'Entidades',NULL,NULL,58,'',0,'','',NULL,3,'/personas','pe-7s-users'),(59,'Cuentas Corrientes',NULL,42,NULL,'\0',1,NULL,NULL,NULL,3,'/compras/cta-cte/movimientos',NULL),(60,'Cuentas Corrientes',NULL,24,NULL,'\0',1,NULL,NULL,NULL,3,'/proveedores/cta-cte/movimientos',NULL),(61,'Empresas',NULL,58,NULL,'\0',1,NULL,NULL,NULL,2,NULL,NULL),(62,'Lista de emp.',NULL,61,NULL,'\0',1,NULL,NULL,NULL,1,'/empresas/lista',NULL),(63,'Nueva emp.',NULL,61,NULL,'\0',1,NULL,NULL,NULL,2,'/empresas/nueva',NULL),(64,'Nueva venta',NULL,27,NULL,'\0',1,NULL,NULL,NULL,1,'/ventas/facturas/nueva',NULL);
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
) ENGINE=InnoDB AUTO_INCREMENT=59 DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `menu_item_data`
--

LOCK TABLES `menu_item_data` WRITE;
/*!40000 ALTER TABLE `menu_item_data` DISABLE KEYS */;
INSERT INTO `menu_item_data` VALUES (1,'Inicio',1,'admin.dashboard','fa fa-home','Menu Inicio',NULL),(2,'Productos',0,'admin.productos.*','fa fa-product-hunt','Productos',NULL),(3,'Lista de Productos',1,'admin.productos.list({activo:1, tipo:1})','fa fa-caret-right','Listado de todos los productos del sistema',NULL),(4,'Nuevo Producto',1,'admin.productos.new','fa fa-caret-right','Cargar un nuevo producto',NULL),(5,'Nuevo Producto Compuesto',1,'admin.productos.compuesto-new','fa fa-caret-right',NULL,NULL),(6,'Movimiento de existencia',0,'admin.productos.movimiento','fa fa-caret-right',NULL,NULL),(7,'Precios',0,'admin.productos.modificaprecio','fa fa-caret-right',NULL,NULL),(8,'Modificar Precios',1,'admin.productos.modificaprecio','fa fa-caret-right',NULL,NULL),(9,'Productos Padre',0,'admin.productopadre.*','fa fa-circle',NULL,NULL),(10,'Lista de Productos Padre',1,'admin.productopadre.list','fa fa-caret-right',NULL,NULL),(11,'Nuevo Producto Padre',1,'admin.productopadre.new','fa fa-caret-right',NULL,NULL),(12,'Familias',0,'admin.familias.*','fa fa-square-o',NULL,NULL),(13,'Lista de Familias',1,'admin.familias.list','fa fa-caret-right',NULL,NULL),(14,'Nueva Familia',1,'admin.familias.new','fa fa-caret-right',NULL,NULL),(15,'Personas',0,'admin.personas.*','fa fa-male',NULL,NULL),(16,'Lista de Personas',1,'admin.personas.list','fa fa-caret-right',NULL,NULL),(17,'Nueva Persona',1,'admin.personas.new','fa fa-caret-right',NULL,NULL),(18,'Usuarios',0,'admin.usuarios.*','fa fa-user',NULL,NULL),(19,'Lista de Usuarios',1,'admin.usuarios.list','fa fa-caret-right',NULL,NULL),(20,'Nuevo Usuario',1,'admin.usuarios.new','fa fa-caret-right',NULL,NULL),(21,'Clientes',0,'admin.clientes.*','fa fa-user-circle-o',NULL,NULL),(22,'Lista de Clientes',1,'admin.clientes.list','fa fa-caret-right',NULL,NULL),(23,'Nuevo Cliente',1,'admin.clientes.new','fa fa-caret-right',NULL,NULL),(24,'Proveedores',0,'admin.proveedores.*','fa fa-truck',NULL,NULL),(25,'Lista de Proveedores',1,'admin.proveedores.list','fa fa-caret-right',NULL,NULL),(26,'Nuevo Proveedor',1,'admin.proveedores.new','fa fa-caret-right',NULL,NULL),(27,'Ventas',0,'admin.ventas.*','fa fa-dollar',NULL,NULL),(28,'Lista de Ventas',1,'admin.ventas.list({totalMinimo:0,totalMaximo:0,pagada:2})','fa fa-caret-right',NULL,NULL),(29,'Transacciones de Caja',1,'admin.ventas.transaccioneslist','fa fa-caret-right',NULL,NULL),(30,'Lista de Cajas',1,'admin.cajas.list','fa fa-caret-right',NULL,NULL),(31,'Ofertas',1,'admin.ofertas.list','fa fa-caret-right',NULL,NULL),(32,'Configuración',0,'admin.config.*','fa fa-cogs',NULL,NULL),(33,'App',1,'admin.config.app','fa fa-caret-right',NULL,NULL),(34,'Cuentas corrientes',1,'admin.clientes.mtoCtaCteList','fa fa-caret-right',NULL,NULL),(35,'Configuración gral.',1,'admin.config.configuracion','fa fa-caret-right',NULL,NULL),(36,'Ingreso',1,'admin.productos.movimiento','fa fa-caret-right',NULL,NULL),(37,'Egreso',1,'admin.productos.movimiento-egreso','fa fa-caret-right',NULL,NULL),(38,'Consultas',0,'admin.consultas.*','fa fa-database',NULL,NULL),(39,'Ventas',1,'admin.consultas.*','fa fa-caret-right',NULL,0),(40,'Prod. más vendidos',1,'admin.consultas.productosmasvendidos','fa fa-caret-right',NULL,NULL),(41,'Prod. con mas entrada($)',1,'admin.consultas.productosconmasentrada','fa fa-caret-right',NULL,NULL),(42,'Compras',0,'admin.compras.*','fa fa-shopping-cart',NULL,NULL),(43,'Nueva fact. de compra',1,'admin.compras.nuevafactura','fa fa-caret-right',NULL,2),(44,'Lista fact. de compras',1,'admin.compras.listfactura','fa fa-caret-right',NULL,1),(45,'Ganancias',1,'admin.consultas.ganancias','fa fa-caret-right',NULL,1),(46,'Movimientos de caja',1,'admin.ventas.dettransaccioneslist','fa fa-caret-right',NULL,NULL),(47,'Parametricas',0,NULL,'fa fa-caret-right',NULL,NULL),(48,'Impuestos',1,'admin.contimpuestos.list','fa fa-caret-right',NULL,NULL),(49,'Varios',0,'admin.utils.vencimientoslist','fa fa-caret-right',NULL,1),(50,'Vencimientos',1,'admin.utils.vencimientoslist','fa fa-caret-right',NULL,1),(51,'Facturacion',0,'admin.fe.*','fa fa-ticket',NULL,NULL),(52,'Nueva factura',1,'admin.fe.newfe','fa fa-caret-right',NULL,NULL),(53,'Datos de facturacion AFIP',1,'','fa fa-caret-right',NULL,NULL),(54,'Pases',1,'admin.productos.listpase','fa fa-caret-right',NULL,NULL),(55,'Backup(Respaldo) de la BD',1,'admin.utils.backup','fa fa-caret-right',NULL,NULL),(56,'Lista de precios',1,'admin.utils.listaprecioslist','fa fa-caret_right',NULL,NULL),(57,'Generador de etiquetas',1,'admin.utils.barcode','fa fa-caret_right',NULL,NULL),(58,'Personas',0,'','','',NULL);
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
INSERT INTO `menu_menu_item` VALUES (1,1),(1,2),(1,27),(1,32),(1,42),(1,58),(2,1),(2,2),(2,9),(2,12),(2,27),(2,32),(2,42);
/*!40000 ALTER TABLE `menu_menu_item` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `movimientos_cta_cte`
--

DROP TABLE IF EXISTS `movimientos_cta_cte`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `movimientos_cta_cte` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `tipo` int(11) NOT NULL COMMENT '1: cta cte clientes\\n2: cta cte proveedores\n4: saldo',
  `monto` decimal(9,4) NOT NULL,
  `fecha` datetime NOT NULL,
  `saldo` decimal(9,4) NOT NULL,
  `descripcion` tinytext DEFAULT NULL,
  `pagado` bit(1) NOT NULL,
  `interes` decimal(9,4) NOT NULL,
  `monto_total` decimal(9,4) NOT NULL COMMENT 'el monto mas el interes que tenga',
  `pagos_cta_cte_id` int(11) DEFAULT NULL COMMENT 'corresponde al pago del movimiento\n',
  `pagos_cta_cte_saldo_id` int(11) DEFAULT NULL COMMENT 'para el tipo de mov saldo, corresponde  aque saldo corresponde el movimiento',
  `pago_cbte_ven_id` int(11) DEFAULT NULL,
  `cuentas_corrientes_clientes_id` int(11) NOT NULL,
  `app_id` int(11) NOT NULL,
  PRIMARY KEY (`id`),
  KEY `fk_movimientos_cta_cte_pagos_cta_cte1_idx` (`pagos_cta_cte_id`),
  KEY `fk_movimientos_cta_cte_pagos_cta_cte2_idx` (`pagos_cta_cte_saldo_id`),
  KEY `fk_movimientos_cta_cte_pago_cbte_ven1_idx` (`pago_cbte_ven_id`),
  KEY `fk_movimientos_cta_cte_cuentas_corrientes1_idx` (`cuentas_corrientes_clientes_id`),
  KEY `fk_movimientos_cta_cte_app1_idx` (`app_id`),
  CONSTRAINT `fk_movimientos_cta_cte_app1` FOREIGN KEY (`app_id`) REFERENCES `app` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT `fk_movimientos_cta_cte_cuentas_corrientes1` FOREIGN KEY (`cuentas_corrientes_clientes_id`) REFERENCES `cuentas_corrientes` (`cliente_id`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT `fk_movimientos_cta_cte_pago_cbte_ven1` FOREIGN KEY (`pago_cbte_ven_id`) REFERENCES `pago_cbte_ven` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT `fk_movimientos_cta_cte_pagos_cta_cte1` FOREIGN KEY (`pagos_cta_cte_id`) REFERENCES `pagos_cta_cte` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT `fk_movimientos_cta_cte_pagos_cta_cte2` FOREIGN KEY (`pagos_cta_cte_saldo_id`) REFERENCES `pagos_cta_cte` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB AUTO_INCREMENT=141 DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `movimientos_cta_cte`
--

LOCK TABLES `movimientos_cta_cte` WRITE;
/*!40000 ALTER TABLE `movimientos_cta_cte` DISABLE KEYS */;
/*!40000 ALTER TABLE `movimientos_cta_cte` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `movimientos_cta_cte_prov`
--

DROP TABLE IF EXISTS `movimientos_cta_cte_prov`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `movimientos_cta_cte_prov` (
  `id` int(11) unsigned NOT NULL AUTO_INCREMENT,
  `tipo` int(11) NOT NULL COMMENT '1: cta cte clientes\n2: cta cte proveedores',
  `monto` decimal(9,4) NOT NULL,
  `fecha` datetime NOT NULL,
  `saldo` decimal(9,4) NOT NULL,
  `descripcion` tinytext DEFAULT NULL,
  `pagado` bit(1) NOT NULL,
  `interes` decimal(9,4) NOT NULL,
  `monto_total` decimal(9,4) NOT NULL COMMENT 'el monto mas el interes que tenga',
  `pagos_cta_cte_prov_id` int(11) DEFAULT NULL,
  `pagos_cta_cte_prov_saldo_id` int(11) DEFAULT NULL,
  `pago_cbte_comp_id` int(11) DEFAULT NULL,
  `cuentas_corrientes_prov_proveedores_id` int(11) unsigned NOT NULL,
  `app_id` int(11) NOT NULL,
  PRIMARY KEY (`id`),
  KEY `fk_movimientos_cta_cte_prov_pagos_cta_cte_prov1_idx` (`pagos_cta_cte_prov_id`),
  KEY `fk_movimientos_cta_cte_prov_pagos_cta_cte_prov2_idx` (`pagos_cta_cte_prov_saldo_id`),
  KEY `fk_movimientos_cta_cte_prov_pago_cbte_comp1_idx` (`pago_cbte_comp_id`),
  KEY `fk_movimientos_cta_cte_prov_cuentas_corrientes_prov1_idx` (`cuentas_corrientes_prov_proveedores_id`),
  KEY `fk_movimientos_cta_cte_prov_app1_idx` (`app_id`),
  CONSTRAINT `fk_movimientos_cta_cte_prov_app1` FOREIGN KEY (`app_id`) REFERENCES `app` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT `fk_movimientos_cta_cte_prov_cuentas_corrientes_prov1` FOREIGN KEY (`cuentas_corrientes_prov_proveedores_id`) REFERENCES `cuentas_corrientes_prov` (`proveedores_id`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT `fk_movimientos_cta_cte_prov_pago_cbte_comp1` FOREIGN KEY (`pago_cbte_comp_id`) REFERENCES `pago_cbte_comp` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT `fk_movimientos_cta_cte_prov_pagos_cta_cte_prov1` FOREIGN KEY (`pagos_cta_cte_prov_id`) REFERENCES `pagos_cta_cte_prov` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT `fk_movimientos_cta_cte_prov_pagos_cta_cte_prov2` FOREIGN KEY (`pagos_cta_cte_prov_saldo_id`) REFERENCES `pagos_cta_cte_prov` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB AUTO_INCREMENT=41 DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `movimientos_cta_cte_prov`
--

LOCK TABLES `movimientos_cta_cte_prov` WRITE;
/*!40000 ALTER TABLE `movimientos_cta_cte_prov` DISABLE KEYS */;
/*!40000 ALTER TABLE `movimientos_cta_cte_prov` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `movimientos_producto`
--

DROP TABLE IF EXISTS `movimientos_producto`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `movimientos_producto` (
  `id` int(10) unsigned NOT NULL AUTO_INCREMENT,
  `cantidad` decimal(10,4) NOT NULL,
  `valor_inicial` decimal(10,4) DEFAULT NULL,
  `valor_final` decimal(10,4) DEFAULT NULL,
  `tipo` tinyint(4) NOT NULL COMMENT '1: mov de ingreso/egreso\n2:mov de venta',
  `fecha` varchar(45) NOT NULL,
  `det_movimientos_id` int(11) DEFAULT NULL,
  `cbte_ven_det_id` int(11) unsigned DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `fk_movimientos_producto_det_movimientos1_idx` (`det_movimientos_id`),
  KEY `fk_movimientos_producto_cbte_ven_det1_idx` (`cbte_ven_det_id`),
  CONSTRAINT `fk_movimientos_producto_cbte_ven_det1` FOREIGN KEY (`cbte_ven_det_id`) REFERENCES `cbte_ven_det` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT `fk_movimientos_producto_det_movimientos1` FOREIGN KEY (`det_movimientos_id`) REFERENCES `det_movimientos` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB AUTO_INCREMENT=728 DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `movimientos_producto`
--

LOCK TABLES `movimientos_producto` WRITE;
/*!40000 ALTER TABLE `movimientos_producto` DISABLE KEYS */;
/*!40000 ALTER TABLE `movimientos_producto` ENABLE KEYS */;
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
  `tipo_oferta_tipo` varchar(45) DEFAULT NULL,
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
) ENGINE=InnoDB AUTO_INCREMENT=126 DEFAULT CHARSET=latin1;
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
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `precio` decimal(9,4) DEFAULT NULL,
  `ofertas_id` int(11) NOT NULL,
  `producto_id` int(11) unsigned NOT NULL,
  `estado` bit(1) NOT NULL,
  `descuento` decimal(9,4) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `fk_ofertas_producto_ofertas1_idx` (`ofertas_id`),
  KEY `fk_ofertas_producto_producto1_idx` (`producto_id`),
  CONSTRAINT `fk_ofertas_producto_ofertas1` FOREIGN KEY (`ofertas_id`) REFERENCES `ofertas` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT `fk_ofertas_producto_producto1` FOREIGN KEY (`producto_id`) REFERENCES `producto` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB AUTO_INCREMENT=94 DEFAULT CHARSET=latin1;
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
  `id` int(11) NOT NULL AUTO_INCREMENT,
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
  `productos_id` int(11) DEFAULT NULL,
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
-- Table structure for table `pago_cbte_comp`
--

DROP TABLE IF EXISTS `pago_cbte_comp`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `pago_cbte_comp` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `descripcion` varchar(100) DEFAULT NULL,
  `monto` decimal(9,4) DEFAULT NULL,
  `numero` varchar(100) DEFAULT NULL,
  `formapagos_id` int(11) DEFAULT NULL,
  `idx` int(11) DEFAULT NULL,
  `tarjeta` varchar(45) DEFAULT NULL,
  `tipo` varchar(45) DEFAULT NULL,
  `pago_con` decimal(9,4) DEFAULT NULL COMMENT 'con cuanto dinero pago',
  `asociada_id` int(11) DEFAULT NULL,
  `asociada_tipo` tinyint(2) DEFAULT NULL,
  `cbte_comp_enc_id` int(11) NOT NULL,
  PRIMARY KEY (`id`),
  KEY `fk_pagos_formapagos1_idx` (`formapagos_id`),
  KEY `fk_pago_cbte_comp_cbte_comp_enc1_idx` (`cbte_comp_enc_id`),
  CONSTRAINT `fk_pago_cbte_comp_cbte_comp_enc1` FOREIGN KEY (`cbte_comp_enc_id`) REFERENCES `cbte_comp_enc` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT `fk_pagos_formapagos10` FOREIGN KEY (`formapagos_id`) REFERENCES `formapagos` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB AUTO_INCREMENT=43 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `pago_cbte_comp`
--

LOCK TABLES `pago_cbte_comp` WRITE;
/*!40000 ALTER TABLE `pago_cbte_comp` DISABLE KEYS */;
/*!40000 ALTER TABLE `pago_cbte_comp` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `pago_cbte_ven`
--

DROP TABLE IF EXISTS `pago_cbte_ven`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `pago_cbte_ven` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `descripcion` varchar(100) DEFAULT NULL,
  `monto` decimal(9,4) DEFAULT NULL,
  `numero` varchar(100) DEFAULT NULL,
  `formapagos_id` int(11) DEFAULT NULL,
  `idx` int(11) DEFAULT NULL,
  `tarjeta` varchar(45) DEFAULT NULL,
  `tipo` varchar(45) DEFAULT NULL,
  `pago_con` decimal(9,4) DEFAULT NULL COMMENT 'con cuanto dinero pago',
  `cbte_ven_enc_id` int(11) unsigned NOT NULL,
  PRIMARY KEY (`id`),
  KEY `fk_pagos_formapagos1_idx` (`formapagos_id`),
  KEY `fk_pagoventas_cbte_ven_enc1_idx` (`cbte_ven_enc_id`),
  CONSTRAINT `fk_pagos_formapagos1` FOREIGN KEY (`formapagos_id`) REFERENCES `formapagos` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT `fk_pagoventas_cbte_ven_enc1` FOREIGN KEY (`cbte_ven_enc_id`) REFERENCES `cbte_ven_enc` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB AUTO_INCREMENT=19489 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `pago_cbte_ven`
--

LOCK TABLES `pago_cbte_ven` WRITE;
/*!40000 ALTER TABLE `pago_cbte_ven` DISABLE KEYS */;
/*!40000 ALTER TABLE `pago_cbte_ven` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `pago_pago_cta_cte`
--

DROP TABLE IF EXISTS `pago_pago_cta_cte`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `pago_pago_cta_cte` (
  `id` int(10) unsigned NOT NULL AUTO_INCREMENT,
  `descripcion` varchar(255) DEFAULT NULL,
  `monto` decimal(15,4) DEFAULT NULL,
  `numero` varchar(100) DEFAULT NULL,
  `tarjeta` varchar(100) DEFAULT NULL,
  `tipo` varchar(45) DEFAULT NULL,
  `pago_con` decimal(15,4) DEFAULT NULL,
  `asociada_id` int(10) unsigned DEFAULT NULL,
  `asociada_tipo` tinyint(2) DEFAULT NULL,
  `formapagos_id` int(11) NOT NULL,
  `pagos_cta_cte_id` int(11) NOT NULL,
  PRIMARY KEY (`id`),
  KEY `fk_formas_pago_cta_cte_formapagos1_idx` (`formapagos_id`),
  KEY `fk_formas_pago_cta_cte_pagos_cta_cte1_idx` (`pagos_cta_cte_id`),
  CONSTRAINT `fk_formas_pago_cta_cte_formapagos1` FOREIGN KEY (`formapagos_id`) REFERENCES `formapagos` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT `fk_formas_pago_cta_cte_pagos_cta_cte1` FOREIGN KEY (`pagos_cta_cte_id`) REFERENCES `pagos_cta_cte` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB AUTO_INCREMENT=23 DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `pago_pago_cta_cte`
--

LOCK TABLES `pago_pago_cta_cte` WRITE;
/*!40000 ALTER TABLE `pago_pago_cta_cte` DISABLE KEYS */;
/*!40000 ALTER TABLE `pago_pago_cta_cte` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `pago_pago_cta_cte_prov`
--

DROP TABLE IF EXISTS `pago_pago_cta_cte_prov`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `pago_pago_cta_cte_prov` (
  `id` int(10) unsigned NOT NULL AUTO_INCREMENT,
  `descripcion` varchar(255) DEFAULT NULL,
  `monto` decimal(12,4) DEFAULT NULL,
  `numero` varchar(100) DEFAULT NULL,
  `tarjeta` varchar(100) DEFAULT NULL,
  `tipo` varchar(45) DEFAULT NULL,
  `pago_con` decimal(15,4) DEFAULT NULL,
  `asociada_id` int(10) unsigned DEFAULT NULL,
  `asociada_tipo` tinyint(2) DEFAULT NULL,
  `formapagos_id` int(11) NOT NULL,
  `pagos_cta_cte_prov_id` int(11) NOT NULL,
  PRIMARY KEY (`id`),
  KEY `fk_pago_pago_cta_cte_prov_formapagos1_idx` (`formapagos_id`),
  KEY `fk_pago_pago_cta_cte_prov_pagos_cta_cte_prov1_idx` (`pagos_cta_cte_prov_id`),
  CONSTRAINT `fk_pago_pago_cta_cte_prov_formapagos1` FOREIGN KEY (`formapagos_id`) REFERENCES `formapagos` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT `fk_pago_pago_cta_cte_prov_pagos_cta_cte_prov1` FOREIGN KEY (`pagos_cta_cte_prov_id`) REFERENCES `pagos_cta_cte_prov` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB AUTO_INCREMENT=10 DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `pago_pago_cta_cte_prov`
--

LOCK TABLES `pago_pago_cta_cte_prov` WRITE;
/*!40000 ALTER TABLE `pago_pago_cta_cte_prov` DISABLE KEYS */;
/*!40000 ALTER TABLE `pago_pago_cta_cte_prov` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `pagos_cta_cte`
--

DROP TABLE IF EXISTS `pagos_cta_cte`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `pagos_cta_cte` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `monto_neto` decimal(12,4) NOT NULL COMMENT 'monto neto',
  `numero` varchar(100) DEFAULT NULL,
  `tarjeta` varchar(45) DEFAULT NULL,
  `tipo` varchar(45) DEFAULT NULL,
  `pago_con` decimal(12,4) DEFAULT NULL,
  `descripcion` tinytext DEFAULT NULL,
  `fecha` datetime NOT NULL,
  `interes` decimal(10,4) DEFAULT NULL,
  `monto_total` decimal(12,4) DEFAULT NULL COMMENT 'El monto total que pago, mas o menos el interes',
  `pago` decimal(12,4) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=104 DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `pagos_cta_cte`
--

LOCK TABLES `pagos_cta_cte` WRITE;
/*!40000 ALTER TABLE `pagos_cta_cte` DISABLE KEYS */;
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
  `monto_neto` decimal(12,4) NOT NULL,
  `numero` varchar(100) DEFAULT NULL,
  `tarjeta` varchar(45) DEFAULT NULL,
  `tipo` varchar(45) DEFAULT NULL,
  `pago_con` decimal(10,4) DEFAULT NULL,
  `descripcion` tinytext DEFAULT NULL,
  `fecha` datetime NOT NULL,
  `interes` decimal(10,4) DEFAULT NULL,
  `monto_total` decimal(12,4) DEFAULT NULL COMMENT 'El monto total que pago, mas o menos el interes',
  `pago` decimal(12,4) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=144 DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `pagos_cta_cte_prov`
--

LOCK TABLES `pagos_cta_cte_prov` WRITE;
/*!40000 ALTER TABLE `pagos_cta_cte_prov` DISABLE KEYS */;
/*!40000 ALTER TABLE `pagos_cta_cte_prov` ENABLE KEYS */;
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
-- Table structure for table `pase_movimientos`
--

DROP TABLE IF EXISTS `pase_movimientos`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `pase_movimientos` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `cantidad` decimal(10,4) NOT NULL,
  `fecha` datetime NOT NULL,
  `sucursales_origen_id` int(11) NOT NULL,
  `sucursales_destino_id` int(11) NOT NULL,
  `producto_id` int(11) unsigned NOT NULL,
  `referencia` varchar(254) DEFAULT NULL,
  `descripcion` mediumtext DEFAULT NULL,
  `estado` bit(1) DEFAULT NULL,
  `app_id` int(11) NOT NULL,
  PRIMARY KEY (`id`),
  KEY `fk_pase_movimientos_sucursales1_idx` (`sucursales_origen_id`),
  KEY `fk_pase_movimientos_sucursales2_idx` (`sucursales_destino_id`),
  KEY `fk_pase_movimientos_producto1_idx` (`producto_id`),
  KEY `fk_pase_movimientos_app1_idx` (`app_id`),
  CONSTRAINT `fk_pase_movimientos_app1` FOREIGN KEY (`app_id`) REFERENCES `app` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT `fk_pase_movimientos_producto1` FOREIGN KEY (`producto_id`) REFERENCES `producto` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT `fk_pase_movimientos_sucursales1` FOREIGN KEY (`sucursales_origen_id`) REFERENCES `sucursales` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT `fk_pase_movimientos_sucursales2` FOREIGN KEY (`sucursales_destino_id`) REFERENCES `sucursales` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB AUTO_INCREMENT=12 DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `pase_movimientos`
--

LOCK TABLES `pase_movimientos` WRITE;
/*!40000 ALTER TABLE `pase_movimientos` DISABLE KEYS */;
/*!40000 ALTER TABLE `pase_movimientos` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `permissions`
--

DROP TABLE IF EXISTS `permissions`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `permissions` (
  `id` bigint(20) unsigned NOT NULL AUTO_INCREMENT,
  `name` varchar(191) COLLATE utf8mb4_unicode_ci NOT NULL,
  `guard_name` varchar(191) COLLATE utf8mb4_unicode_ci NOT NULL,
  `created_at` timestamp NULL DEFAULT NULL,
  `updated_at` timestamp NULL DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=9 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `permissions`
--

LOCK TABLES `permissions` WRITE;
/*!40000 ALTER TABLE `permissions` DISABLE KEYS */;
INSERT INTO `permissions` VALUES (1,'administrar usuarios','web','2020-07-13 23:41:42','2020-07-13 23:41:42'),(2,'servicios CUHC RENAPER CUIDAR','web','2020-07-13 23:41:42','2020-07-13 23:41:42'),(3,'visualizar reportes','web','2020-07-13 23:41:42','2020-07-13 23:41:42'),(4,'visualizar debug','web','2020-07-13 23:41:42','2020-07-13 23:41:42'),(5,'visualizar certificados','web','2020-07-13 23:41:42','2020-07-13 23:41:42'),(6,'bloquear certificado','web','2020-07-13 23:41:42','2020-07-13 23:41:42'),(7,'regenerar archivos','web','2020-07-13 23:41:42','2020-07-13 23:41:42'),(8,'validar renaper','web','2020-07-13 23:41:42','2020-07-13 23:41:42');
/*!40000 ALTER TABLE `permissions` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `personas`
--

DROP TABLE IF EXISTS `personas`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `personas` (
  `apellido` varchar(50) NOT NULL,
  `nombre` varchar(80) NOT NULL,
  `dni` varchar(10) DEFAULT NULL,
  `cuil` varchar(13) DEFAULT NULL,
  `sexo` tinyint(4) DEFAULT NULL,
  `fecha_nac` date DEFAULT NULL,
  `fecha_alta` datetime DEFAULT NULL,
  `direccion` varchar(45) DEFAULT NULL,
  `ciudad` varchar(45) DEFAULT NULL,
  `entidad_id` int(11) unsigned NOT NULL,
  PRIMARY KEY (`entidad_id`),
  KEY `fk_personas_entidad1_idx` (`entidad_id`),
  CONSTRAINT `fk_personas_entidad1` FOREIGN KEY (`entidad_id`) REFERENCES `entidad` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
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
  `producto_id` int(11) unsigned DEFAULT NULL,
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
  `id` int(11) unsigned NOT NULL AUTO_INCREMENT,
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
  `iva_id` tinyint(4) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `fk_detalle_productos_unidad1_idx` (`unidad_id`),
  KEY `fk_producto_producto_padre1_idx` (`producto_padre_id`),
  KEY `nombre_idx` (`nombre`),
  CONSTRAINT `fk_detalle_productos_unidad1` FOREIGN KEY (`unidad_id`) REFERENCES `unidad` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT `fk_producto_producto_padre1` FOREIGN KEY (`producto_padre_id`) REFERENCES `producto_padre` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB AUTO_INCREMENT=1561 DEFAULT CHARSET=utf8 COMMENT='Tabla con el detalle del producto, o presentacion, por ejemplo fanta es el producto  y viene con presetancion de 2.25, 1, 1.5 l etc\n';
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
) ENGINE=InnoDB AUTO_INCREMENT=497 DEFAULT CHARSET=utf8;
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
  `producto_id` int(11) unsigned NOT NULL,
  `proveedores_id` int(11) unsigned NOT NULL,
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
  `producto_principal_id` int(11) unsigned DEFAULT NULL,
  `producto_id` int(11) unsigned DEFAULT NULL,
  PRIMARY KEY (`id`,`cantidad`),
  KEY `fk_productos_compuestos_producto1_idx` (`producto_principal_id`),
  KEY `fk_productos_compuestos_producto2_idx` (`producto_id`),
  CONSTRAINT `fk_productos_compuestos_producto1` FOREIGN KEY (`producto_principal_id`) REFERENCES `producto` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT `fk_productos_compuestos_producto2` FOREIGN KEY (`producto_id`) REFERENCES `producto` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB AUTO_INCREMENT=12 DEFAULT CHARSET=latin1;
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
  `id` int(11) unsigned NOT NULL AUTO_INCREMENT,
  `observacion` mediumtext DEFAULT NULL,
  `estado` bit(1) NOT NULL,
  `app_id` int(11) NOT NULL,
  `entidad_id` int(11) unsigned NOT NULL,
  `tipo_proveedor` varchar(45) NOT NULL,
  `tipo_doc_proveedor` tinyint(1) NOT NULL,
  `nro_doc_proveedor` varchar(45) NOT NULL,
  PRIMARY KEY (`id`),
  KEY `fk_proveedores_app1_idx` (`app_id`),
  KEY `fk_proveedores_entidad1_idx` (`entidad_id`),
  CONSTRAINT `fk_proveedores_app1` FOREIGN KEY (`app_id`) REFERENCES `app` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT `fk_proveedores_entidad1` FOREIGN KEY (`entidad_id`) REFERENCES `entidad` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB AUTO_INCREMENT=23 DEFAULT CHARSET=latin1;
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
-- Table structure for table `puntos_venta`
--

DROP TABLE IF EXISTS `puntos_venta`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `puntos_venta` (
  `nro` varchar(5) NOT NULL,
  `descripcion` varchar(100) DEFAULT NULL,
  `configuracion_id` int(11) NOT NULL,
  PRIMARY KEY (`nro`,`configuracion_id`),
  KEY `fk_puntos_venta_configuracion1_idx` (`configuracion_id`),
  CONSTRAINT `fk_puntos_venta_configuracion1` FOREIGN KEY (`configuracion_id`) REFERENCES `configuracion` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `puntos_venta`
--

LOCK TABLES `puntos_venta` WRITE;
/*!40000 ALTER TABLE `puntos_venta` DISABLE KEYS */;
/*!40000 ALTER TABLE `puntos_venta` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `reportes`
--

DROP TABLE IF EXISTS `reportes`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `reportes` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `nombre` varchar(45) DEFAULT NULL,
  `titulo` varchar(100) DEFAULT NULL,
  `subtitulo` varchar(100) DEFAULT NULL,
  `logo_default` tinyint(1) DEFAULT NULL,
  `logo_izq` varchar(45) DEFAULT NULL,
  `logo_der` varchar(45) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `reportes`
--

LOCK TABLES `reportes` WRITE;
/*!40000 ALTER TABLE `reportes` DISABLE KEYS */;
INSERT INTO `reportes` VALUES (1,'Reporte Formulario Reclamos','SECRETARIA DE OBRAS Y SERVICIOS PÚBLICOS',NULL,0,'images/logobanda.png','images/logoBicentenario.png');
/*!40000 ALTER TABLE `reportes` ENABLE KEYS */;
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
) ENGINE=InnoDB AUTO_INCREMENT=8 DEFAULT CHARSET=utf8;
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
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `stock` decimal(15,4) DEFAULT NULL,
  `stock_minimo` decimal(9,4) DEFAULT NULL,
  `detalle` mediumtext DEFAULT NULL,
  `sucursales_id` int(11) DEFAULT NULL,
  `idx` int(11) DEFAULT NULL,
  `ubicacion` varchar(254) DEFAULT NULL,
  `producto_id` int(11) unsigned DEFAULT NULL,
  `punto_reposicion` decimal(9,4) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `fk_stockSucursal_sucursales1_idx` (`sucursales_id`),
  KEY `fk_stock_sucursal_producto1_idx` (`producto_id`),
  CONSTRAINT `fk_stockSucursal_sucursales1` FOREIGN KEY (`sucursales_id`) REFERENCES `sucursales` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT `fk_stock_sucursal_producto1` FOREIGN KEY (`producto_id`) REFERENCES `producto` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB AUTO_INCREMENT=1321 DEFAULT CHARSET=utf8;
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
  `id` int(11) NOT NULL AUTO_INCREMENT,
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
) ENGINE=InnoDB AUTO_INCREMENT=38 DEFAULT CHARSET=utf8;
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
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `prefijo` varchar(15) NOT NULL,
  `numero` varchar(45) NOT NULL,
  `empresa_tel` varchar(45) DEFAULT NULL,
  `detalle` tinytext DEFAULT NULL,
  `tipo` tinyint(4) DEFAULT NULL COMMENT '1: celular\n2: fijo\n',
  `entidad` varchar(45) DEFAULT NULL COMMENT 'para saber si es de una persona p oproveddor por ejemplo',
  `principal` bit(1) DEFAULT NULL,
  `entidad_id` int(11) unsigned NOT NULL,
  PRIMARY KEY (`id`),
  KEY `fk_telefonos_entidad1_idx` (`entidad_id`),
  CONSTRAINT `fk_telefonos_entidad1` FOREIGN KEY (`entidad_id`) REFERENCES `entidad` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB AUTO_INCREMENT=35 DEFAULT CHARSET=latin1;
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
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `estado` tinyint(4) NOT NULL COMMENT '1:abierta\n2: pausada\n3:cerrada\n',
  `detalle` mediumtext DEFAULT NULL,
  `caja_id` int(11) NOT NULL,
  `fecha_apertura` datetime DEFAULT NULL,
  `fecha_cierre` datetime DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `fk_transaccion_caja_caja1_idx` (`caja_id`),
  CONSTRAINT `fk_transaccion_caja_caja1` FOREIGN KEY (`caja_id`) REFERENCES `caja` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB AUTO_INCREMENT=327 DEFAULT CHARSET=latin1;
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
  `tipo_cbte` tinyint(1) NOT NULL,
  `id_cbte` int(11) unsigned NOT NULL,
  `ind` tinyint(2) NOT NULL COMMENT 'index\n',
  PRIMARY KEY (`tipo_cbte`,`id_cbte`,`ind`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
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
  `sucursales_id` int(11) NOT NULL,
  PRIMARY KEY (`id`),
  KEY `fk_ubicaciones_sucursales1_idx` (`sucursales_id`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `ubicaciones`
--

LOCK TABLES `ubicaciones` WRITE;
/*!40000 ALTER TABLE `ubicaciones` DISABLE KEYS */;
INSERT INTO `ubicaciones` VALUES (1,'Estante A1','Un estante mas\n',0),(2,'Estante A2','Segundo Piso\n',0);
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
  `id` int(11) unsigned NOT NULL AUTO_INCREMENT,
  `usuario` varchar(45) NOT NULL,
  `password` varchar(100) NOT NULL,
  `tipo` varchar(20) DEFAULT NULL,
  `observacion` mediumtext DEFAULT NULL,
  `app_id` int(11) NOT NULL,
  `mail` varchar(80) NOT NULL,
  `key_user` varchar(250) DEFAULT NULL,
  `key_gravatar` varchar(150) DEFAULT NULL,
  `estado` bit(1) NOT NULL DEFAULT b'1',
  `tipo_usuario` tinyint(4) NOT NULL COMMENT '1: normal  app\n2: facebook\n3: twitter\n',
  `id_red_social` varchar(254) DEFAULT NULL,
  `logo` tinytext DEFAULT NULL,
  `alt_logo` tinytext DEFAULT NULL,
  `tipo_logo` tinyint(4) DEFAULT NULL,
  `personas_entidad_id` int(11) unsigned NOT NULL,
  `root` bit(1) NOT NULL DEFAULT b'0',
  PRIMARY KEY (`id`),
  KEY `fk_usuarios_app1_idx` (`app_id`),
  KEY `fk_usuarios_personas1_idx` (`personas_entidad_id`),
  CONSTRAINT `fk_usuarios_app1` FOREIGN KEY (`app_id`) REFERENCES `app` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT `fk_usuarios_personas1` FOREIGN KEY (`personas_entidad_id`) REFERENCES `personas` (`entidad_id`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB AUTO_INCREMENT=102 DEFAULT CHARSET=utf8;
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
  `usuarios_id` int(11) unsigned NOT NULL,
  `roles_id` int(11) NOT NULL,
  `test` int(11) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `fk_usuarios_roles_usuarios1_idx` (`usuarios_id`),
  KEY `fk_usuarios_roles_roles1_idx` (`roles_id`),
  CONSTRAINT `fk_usuarios_roles_roles1` FOREIGN KEY (`roles_id`) REFERENCES `roles` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT `fk_usuarios_roles_usuarios1` FOREIGN KEY (`usuarios_id`) REFERENCES `usuarios` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB AUTO_INCREMENT=113 DEFAULT CHARSET=utf8;
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
  `usuarios_id` int(11) unsigned NOT NULL,
  `sucursales_id` int(11) NOT NULL,
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
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `fecha_vencimiento` datetime NOT NULL,
  `descripcion` tinytext DEFAULT NULL,
  `activo` bit(1) NOT NULL,
  `dias_alerta` tinyint(4) NOT NULL COMMENT 'dias antes a aplicar alerta\n',
  `producto_id` int(11) unsigned DEFAULT NULL,
  `alerta_vencimientos` bit(1) NOT NULL,
  `tipo` tinyint(4) NOT NULL COMMENT 'tipo de factura\n1: por producto\n2:por factura\n\n',
  `lote` varchar(250) DEFAULT NULL,
  `cantidad_productos` decimal(10,4) DEFAULT NULL,
  `fecha_carga` datetime NOT NULL,
  `app_id` int(11) DEFAULT NULL,
  `cbte_comp_det_id` int(11) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `fk_vencimientos_productos_producto1_idx` (`producto_id`),
  KEY `fk_vencimientos_productos_cbte_comp_det1_idx` (`cbte_comp_det_id`),
  CONSTRAINT `fk_vencimientos_productos_cbte_comp_det1` FOREIGN KEY (`cbte_comp_det_id`) REFERENCES `cbte_comp_det` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT `fk_vencimientos_productos_producto1` FOREIGN KEY (`producto_id`) REFERENCES `producto` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB AUTO_INCREMENT=43 DEFAULT CHARSET=latin1;
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

-- Dump completed on 2020-09-21 10:08:54
