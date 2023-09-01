-- phpMyAdmin SQL Dump
-- version 4.7.4
-- https://www.phpmyadmin.net/
--
-- Servidor: localhost
-- Tiempo de generación: 26-04-2018 a las 16:42:29
-- Versión del servidor: 10.1.28-MariaDB
-- Versión de PHP: 7.0.25

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
SET AUTOCOMMIT = 0;
START TRANSACTION;
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Base de datos: `jsuperfinal`
--

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `app`
--

CREATE TABLE `app` (
  `id` int(11) NOT NULL,
  `nombre` varchar(254) NOT NULL,
  `descripcion` tinytext,
  `key_app` varchar(254) DEFAULT NULL,
  `logo` varchar(200) DEFAULT NULL,
  `alt_logo` varchar(200) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Volcado de datos para la tabla `app`
--

INSERT INTO `app` (`id`, `nombre`, `descripcion`, `key_app`, `logo`, `alt_logo`) VALUES
(1, 'Autoservicio Eva', 'oooooooo', 'mkxF+70FIYgr6GYs0/3iNg==', 'd4422cb887050bab862ee8c86ba8a9110.jpg', 'comodin2_1'),
(2, 'App-erika2001', '', '2O9bxersNMeR2b5RJ3F+gA==', NULL, NULL),
(3, 'App-erika200', '', 'yN1aQEhIQ7mTxdZEZ69dVA==', NULL, NULL),
(4, 'App-juan', '', 'odF0rc6lHXG3YywMKT3DfQ==', NULL, NULL),
(5, 'App-juan', '', '/ypSKnhCXJuWZ+DJ+KVg+Q==', NULL, NULL),
(6, 'App-juan', '', 'sq97IATDeYkFM5dBHrS/kg==', NULL, NULL),
(7, 'App-juan11', '', '8GUXFdLmuf+q3pAD1bw/cA==', NULL, NULL),
(8, 'App-admins', '', 'IvPcnY073kCxxjwXT5IuhQ==', NULL, NULL),
(9, 'App-martita', '', 'a3AZ2TQeuOqN0Eo5iRMzHg==', NULL, NULL),
(10, 'App-erika', '', 'IwJbeenM7Zxz/U4sPmLaYA==', NULL, NULL),
(14, 'App-mariel', '', 'gB0Q72OgWmeU5kcaZl+BOQ==', NULL, NULL),
(20, 'App-null', '', 'z5m3Iub/pO+1zgwDr6slSg==', NULL, NULL),
(21, 'App-Rafael', '', 'x7o6f+HZVCmW8k/r1z8QzA==', NULL, NULL),
(22, 'App-Rafael', '', '2sd0dkabr01lmBWn0d24vg==', NULL, NULL),
(23, 'App-Rafael', '', 'jNWqeu1GQrf9MsuKYCAztQ==', NULL, NULL),
(24, 'App-Rafael', '', 'WF6CuClOvLYYbTMWVJHoFQ==', NULL, NULL),
(25, 'App-Rafael', '', 'dIfxBwjRzLeULAGcmPh6lg==', NULL, NULL),
(26, 'App-Rafael', '', '6IJETNdRdmCQq1hM6lxFXA==', NULL, NULL),
(27, 'App-Rafael', '', 'hNlXpWWUgQgSqqyCt60ohg==', NULL, NULL),
(28, 'App-Rafael', '', 'RevAfrzOwFJSP0/slTB3pA==', NULL, NULL),
(29, 'App-Rafael', '', 'FOvNC8qNZAUQ46bxXwyUlg==', NULL, NULL),
(30, 'App-Rafael', '', 'yrL1MO2l2CUQ7Ic8cbHNGQ==', NULL, NULL),
(31, 'App-Rafael', '', 'trNTlIyLKFP+wErDBv8T7A==', NULL, NULL),
(32, 'App-Rafael', '', 'oQDDgLQ1iMZRQs2wsVdzUw==', NULL, NULL),
(33, 'App-Rafael', '', 'cz74TabACzoiNrlGkSVF2w==', NULL, NULL),
(34, 'App-Rafael', '', 'EmNFb9SnwyGuP/EZcHlsDw==', NULL, NULL),
(35, 'App-Rafael', '', 'tWtRdYSdXdOaOTqfRqsUEA==', NULL, NULL),
(36, 'App-Rafael', '', 'u7ABBj4OX1R5NfdWsvlttw==', NULL, NULL),
(37, 'App-Rafael', '', 'LVbM9tPgMMMih0kcuWMcHw==', NULL, NULL),
(38, 'App-Rafael', '', 'AIu3gI1Kt9dAegwGoIIM1Q==', NULL, NULL),
(39, 'App-Rafael', '', 'yQPayvyZqJ76D4Kk2xSrGw==', NULL, NULL),
(40, 'App-Rafael', '', '5AJd/nGvMfKz6/c+8kMPPA==', NULL, NULL);

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `caja`
--

CREATE TABLE `caja` (
  `id` int(11) NOT NULL,
  `ip_maquina` varchar(20) DEFAULT NULL,
  `nombre_maquina` varchar(45) DEFAULT NULL,
  `modelo_impresora` int(11) DEFAULT NULL,
  `marca_impresora` varchar(20) DEFAULT NULL,
  `sucursal` varchar(45) DEFAULT NULL,
  `observacion` mediumtext,
  `entrada_lector_CB` bit(1) DEFAULT NULL,
  `sonidoBeep` bit(1) DEFAULT NULL,
  `modifica_adicional` bit(1) DEFAULT b'0',
  `modifica_precio` bit(1) DEFAULT b'0',
  `modifica_descuento` bit(1) DEFAULT b'0',
  `tipo_impresora` int(11) DEFAULT NULL,
  `sin_control` bit(1) DEFAULT NULL,
  `con_control` bit(1) DEFAULT NULL,
  `con_control_estricto` bit(1) DEFAULT NULL,
  `nombre` varchar(80) DEFAULT NULL,
  `app_id` int(11) NOT NULL,
  `activo` bit(1) NOT NULL DEFAULT b'1',
  `limite_consumidor_final` decimal(9,4) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

--
-- Volcado de datos para la tabla `caja`
--

INSERT INTO `caja` (`id`, `ip_maquina`, `nombre_maquina`, `modelo_impresora`, `marca_impresora`, `sucursal`, `observacion`, `entrada_lector_CB`, `sonidoBeep`, `modifica_adicional`, `modifica_precio`, `modifica_descuento`, `tipo_impresora`, `sin_control`, `con_control`, `con_control_estricto`, `nombre`, `app_id`, `activo`, `limite_consumidor_final`) VALUES
(1, '', 'J2EE', NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, 'Caja 01', 1, b'1', '5000.0000'),
(2, NULL, '787744', NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, 'Caja Rapida', 1, b'1', '0.0000'),
(5, NULL, 'rafa22', NULL, NULL, NULL, 'detall de cahas88877', NULL, NULL, b'1', b'0', b'1', NULL, NULL, NULL, NULL, 'Caja 002', 1, b'1', '2000.0000'),
(6, NULL, 'rafa22', NULL, NULL, NULL, 'Caja inicial', NULL, NULL, b'1', b'1', b'1', NULL, NULL, NULL, NULL, 'Caja 001', 14, b'1', '2000.0000');

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `ciudad`
--

CREATE TABLE `ciudad` (
  `id` int(11) NOT NULL,
  `nombre` varchar(80) DEFAULT NULL,
  `cp` varchar(20) DEFAULT NULL,
  `provincia_id` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `claves`
--

CREATE TABLE `claves` (
  `id` int(11) NOT NULL,
  `clave` varchar(254) NOT NULL,
  `detalle` tinytext,
  `app_id` int(11) NOT NULL,
  `usuarios_id` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Volcado de datos para la tabla `claves`
--

INSERT INTO `claves` (`id`, `clave`, `detalle`, `app_id`, `usuarios_id`) VALUES
(1, 'e10adc3949ba59abbe56e057f20f883e', 'Rafael ramos', 1, 3);

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `clientes`
--

CREATE TABLE `clientes` (
  `id` bigint(20) NOT NULL,
  `estado` bit(1) DEFAULT b'0',
  `tipo` tinyint(4) DEFAULT NULL COMMENT 'value: 0, name: Sin especificar\nvalue: 1, name: Consumidor Final\nvalue: 2, name: Monotributo\nvalue: 3, name: Responsable Incripto\nvalue: 4, name: Otro',
  `observacion` mediumtext,
  `personas_id` int(11) NOT NULL,
  `app_id` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `codigos_barras`
--

CREATE TABLE `codigos_barras` (
  `id` int(11) NOT NULL,
  `codigo` varchar(45) COLLATE utf8_spanish_ci DEFAULT NULL,
  `nombre` varchar(80) COLLATE utf8_spanish_ci DEFAULT NULL,
  `cantidad_bulto` decimal(9,4) DEFAULT NULL,
  `tipo` tinyint(4) DEFAULT NULL,
  `descripcion` mediumtext COLLATE utf8_spanish_ci,
  `producto_id` bigint(20) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_spanish_ci;

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `configuracion`
--

CREATE TABLE `configuracion` (
  `id` int(11) NOT NULL,
  `tipoCliente` tinytext,
  `ciudades` tinytext,
  `tipoProveedor` tinytext,
  `codigosEspecialesProd` tinytext,
  `nombreEmpresa` varchar(100) DEFAULT NULL,
  `direccionEmpresa` varchar(80) DEFAULT NULL,
  `telEmpresa` varchar(45) DEFAULT NULL,
  `telCelEmpresa` varchar(45) DEFAULT NULL,
  `logo` longblob,
  `acceso` int(11) DEFAULT NULL,
  `codigoAutorizacion` varchar(80) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

--
-- Volcado de datos para la tabla `configuracion`
--

INSERT INTO `configuracion` (`id`, `tipoCliente`, `ciudades`, `tipoProveedor`, `codigosEspecialesProd`, `nombreEmpresa`, `direccionEmpresa`, `telEmpresa`, `telCelEmpresa`, `logo`, `acceso`, `codigoAutorizacion`) VALUES
(1, 'CONSUMIDOR FINAL', 'JUJUY', 'MONOTRIBUTISTA', NULL, 'Compujuy 2', '', '', '', 0xffd8ffe000104a46494600010200000100010000ffdb004300080606070605080707070909080a0c140d0c0b0b0c1912130f141d1a1f1e1d1a1c1c20242e2720222c231c1c2837292c30313434341f27393d38323c2e333432ffdb0043010909090c0b0c180d0d1832211c213232323232323232323232323232323232323232323232323232323232323232323232323232323232323232323232323232ffc00011080078019003012200021101031101ffc4001f0000010501010101010100000000000000000102030405060708090a0bffc400b5100002010303020403050504040000017d01020300041105122131410613516107227114328191a1082342b1c11552d1f02433627282090a161718191a25262728292a3435363738393a434445464748494a535455565758595a636465666768696a737475767778797a838485868788898a92939495969798999aa2a3a4a5a6a7a8a9aab2b3b4b5b6b7b8b9bac2c3c4c5c6c7c8c9cad2d3d4d5d6d7d8d9dae1e2e3e4e5e6e7e8e9eaf1f2f3f4f5f6f7f8f9faffc4001f0100030101010101010101010000000000000102030405060708090a0bffc400b51100020102040403040705040400010277000102031104052131061241510761711322328108144291a1b1c109233352f0156272d10a162434e125f11718191a262728292a35363738393a434445464748494a535455565758595a636465666768696a737475767778797a82838485868788898a92939495969798999aa2a3a4a5a6a7a8a9aab2b3b4b5b6b7b8b9bac2c3c4c5c6c7c8c9cad2d3d4d5d6d7d8d9dae2e3e4e5e6e7e8e9eaf2f3f4f5f6f7f8f9faffda000c03010002110311003f00f7fa28a2800a0900124e00ac9d7bc4761e1eb5f36edf3230fddc2bf79fff00adef5e49af78c354d79992494c16a7a5bc470bf89fe2fc6bd1c16595b15ef2d23dff00c8f3f199952c2fbaf5976ff33d3754f1ce85a59286e7ed328fe0b71bbf5e9fad72f77f15662c459e988abd9a690927f018fe75e75457d0d1c970b4d7bcb99f9ffc03e7eb673899bf75f2af2ff8276e7e28eb59e2d74fc7fd737ffe2e93fe168eb7ff003eba7ffdfb7ffe2eb89a2ba7fb3709ff003ed1cffda38afe7676dff0b475bff9f5d3ff00efdbff00f1747fc2d1d6ff00e7d74fff00bf6fff00c5d713451fd9b84ff9f683fb4715fcecedbfe168eb7ff3eba7ff00dfb7ff00e2e8ff0085a3adff00cfae9fff007edfff008bae268a3fb3709ff3ed07f68e2bf9d9db7fc2d1d6ff00e7d74fff00bf6fff00c5d1ff000b475bff009f5d3ffefdbfff00175c4d147f66e13fe7da0fed1c57f3b3b6ff0085a3adff00cfae9fff007edfff008ba3fe168eb7ff003eba7ffdfb7ffe2eb89a28fecdc27fcfb41fda38afe7676dff000b475bff009f5d3ffefdbfff001747fc2d1d6ffe7d74ff00fbf6ff00fc5d713451fd9b84ff009f683fb4715fcecedbfe168eb7ff003eba7ffdfb7ffe2ebd1345d527d47c3306a532c6b3c9133954042e413d89cf6f5af05af6df0aff00c88569ff005eeffcdabc9cdf09428d28ba714aeffccf5729c5d6ad564aa4afa7f91c47fc2d1d6ffe7d74ff00fbf6ff00fc5d1ff0b475bff9f5d3ff00efdbff00f175c4d15eb7f66e13fe7da3cafed1c57f3b3b6ff85a3adffcfae9ff00f7edff00f8ba3fe168eb7ff3eba7ff00dfb7ff00e2eb89a28fecdc27fcfb41fda38afe7676dff0b475bff9f5d3ff00efdbff00f1747fc2d1d6ff00e7d74fff00bf6fff00c5d713451fd9b84ff9f683fb4715fcecedbfe168eb7ff3eba7ff00dfb7ff00e2e8ff0085a3adff00cfae9fff007edfff008bae268a3fb3709ff3ed07f68e2bf9d9db7fc2d1d6ff00e7d74fff00bf6fff00c5d1ff000b475bff009f5d3ffefdbfff00175c4d147f66e13fe7da0fed1c57f3b3b6ff0085a3adff00cfae9fff007edfff008ba3fe168eb7ff003eba7ffdfb7ffe2eb89a28fecdc27fcfb41fda38afe7676dff000b475bff009f5d3ffefdbfff001747fc2d1d6ffe7d74ff00fbf6ff00fc5d713451fd9b84ff009f683fb4715fcecedbfe168eb7ff003eba7ffdfb7ffe2e8ff85a3adffcfae9ff00f7edff00f8bae268a3fb3709ff003ed07f68e2bf9d9db7fc2d1d6ffe7d74ff00fbf6ff00fc5d1ff0b475bff9f5d3ff00efdbff00f175c4d147f66e13fe7da0fed1c57f3b3b6ff85a3adffcfae9ff00f7edff00f8ba3fe168eb7ff3eba7ff00dfb7ff00e2eb89a28fecdc27fcfb41fda38afe7676dff0b475bff9f5d3ff00efdbff00f1747fc2d1d6ff00e7d74fff00bf6fff00c5d713451fd9b84ff9f683fb4715fcecedbfe168eb7ff3eba7ff00dfb7ff00e2e8ff0085a3adff00cfae9fff007edfff008bae268a3fb3709ff3ed07f68e2bf9d9db7fc2d1d6ff00e7d74fff00bf6fff00c5d1ff000b475bff009f5d3ffefdbfff00175c4d147f66e13fe7da0fed1c57f3b3b6ff0085a3adff00cfae9fff007edfff008ba3fe168eb7ff003eba7ffdfb7ffe2eb89a28fecdc27fcfb41fda38afe767750fc4ed6a59e38cdae9e03305388dfb9ff7ebd5ebe74b4ff8fc83feba2ff3afa2ebc0cef0d4a8387b38daf7fd0f7726c455aca7ed257b5bf50ac5f13788adfc39a69b890079df2b0c59fbedfe03bd6bcb2a410bcb2b048d14b331e800e49af08f126b92ebfaccb76e48887cb0a1fe141d3f13d4fd6b9b2bc0fd6aafbdf0adff00c8e9ccf1bf55a5eefc4f6ff3296a1a8dd6a97b25e5e4a649a43c93d87a0f415568a9ad2d27beba8edad62696690e1117a9afb44a308f648f8e6e539776c86b534ef0eeafaae0d9d84d221ff96846d5fccf15d7a693a07826dd27d676ea1aab0ca5b2e0aafe07b7b9fc05636a5f1075cbd256de54b287a048179c7fbc79fcb15c2b1556bffbb474fe67b7c96ecee785a547fde25af65bfcdec89e3f869afba06636884ff0b4a723f2047eb4eff8565aeffcf4b2ff00bfadff00c4d73726b9ab4b279926a776cfebe737f8d68d8f8dbc4162e0aea0f32f749c6f07f3e7f5a89c330b5e338dfd19509e01bb4a32fbcd3ff8565aeffcf4b2ff00bfadff00c4d1ff000acb5dff009e965ff7f5bff89aec3c33e3cb3d7244b4ba41697adc2a939490ff00b27d7d8feb5d7578d5f34c7d09f254493f43d7a396606b479e9b6d7a9e43ff000acb5dff009e965ff7f5bff89a3fe1596bbff3d2cbfefeb7ff00135ebd5c3f897e21c1a64d259e991a5cdca1daf231fdda1f4e3ef1aac3e678fc44f929a4dfa0abe5b81a11e7a8da5ea733ff000acb5dff009e965ff7f5bff89a43f0cb5eff009e967ff7f4ff00f1359b73e36f115d312da94918ce42c40201f973562cfe207886d5d4bddadc20fe09a31cfe2307f5af51c7334aea51bfccf2d4b2dbdb9656f916bfe159ebdfdfb3ff00bfa7fc28ff008567af7f7ecffefe9ff0aee7c3be35d3f5d899642b69751aee78e461820752a7b8ae7f5ff8962391adf458d5f0706e651c1ff757fa9fcab8a9e2f339d5749455d6fa69f79db3c2e5b0a6aab93b3f3d4c6ff8567af7f7ecff00efe9ff000a3fe159ebdfdfb3ff00bfa7fc2b1e5f17f882690bb6ab720fa236d1f90abfa7fc40d7ac641e65c0bb8fba4ca0fea39aee9473251ba945bf99c51965cdd9c6497c8b3ff0acf5efefd9ff00dfd3fe15e8fa1e9971a7f85e0d3a7d9e7c71321da72b924f7fc699e1cf13d9788ed8b4198ee107ef2063cafb8f51ef5b75f3b8ec6e26a7eeabab34fb1efe0b0786a7fbda0ee9a3c83fe159ebdfdfb3ff00bfa7fc28ff008567af7f7ecffefe9ff0af5fae275ff88b65a748f6da720bcb85382f9c46a7ebfc5f87e75db4333c7e225cb4927f2392be5b81a11e6a8daf99cb7fc2b3d7bfbf67ff007f4ff851ff000acf5efefd9ffdfd3fe1542f3c75e21bc627ede615feec0a140fc7afeb5563f16ebf1b6e5d5ae89ff69f70fd6bd650cc5ad651fb99e539e5c9e9197de8d9ff008567af7f7ecffefe9ff0a3fe159ebdfdfb3ffbfa7fc2a7d2be26ea36eea9a9431dd45dd906c71fd0fe55e93a4eb363add98b9b198489d194f0c87d08ed5e7e2b1998e1b5a895bba5a1df85c1e5d89d20ddfb37a9e5ff00f0acf5efefd9ff00dfd3fe147fc2b3d7bfbf67ff007f4ff857afd792df7c43d76df50b9811adb6472b22e62ec091eb5384c7e3f14daa7cba771e2b0381c2a4ea5f522ff8567af7f7ecff00efe9ff000a3fe159ebdfdfb3ff00bfa7fc299ff0b27c41fdeb6ffbf5ff00d7aebbc0be26d47c4335f2df18888550a6c4dbd739fe55d188ad9950a6ea4f96c8c2851cbabd454e1cd76729ff000acf5efefd9ffdfd3fe147fc2b3d7bfbf67ff7f4ff008574fe39f14ea5e1fbeb48ac4c41258cb36f4ddce715caff00c2c9f107f7adbfefd7ff005e8a15b32af4d54872d9857a59751a8e9cf9ae87ff00c2b3d7bfbf67ff007f4ff85646bbe16d43c3b1c325eb4044c485f2dc9e9f80f5ae8349f881ae5eeb3656b2b5bf9734e91be22c1c1600f7ad3f8abff1e7a6ff00d747fe42ae9e27190c5428d7b5a57d88a986c1cf0d3ad42f78db73cc69d1a192458d7196200cfbd36a48095b8888ea1c1fd6bda7b1e32dcebbfe159ebdfdfb3ffbfa7fc28ff8567af7f7ecff00efe9ff000af46f156a571a47872eafad4a09a3dbb77ae472c01e3f1af36ff8593e20fef5b7fdfaff00ebd7cf6171598e2a0e74f96d7b1efe2b0b97e1a6a1539afb8fff008567af7f7ecffefe9ff0a3fe159ebdfdfb3ffbfa7fc299ff000b27c41fdeb6ff00bf5ffd7af4ff000f5f4da968165797057ce9a3dcdb460673462f17986162a55396cc785c2e0313271a7cd74799ff00c2b3d7bfbf67ff007f4ff851ff000acf5efefd9ffdfd3fe1536a3f10b5db6d4eeede36b6d914ce8b98b9c062077aadff000b27c41fdeb6ff00bf5ffd7ae88bcd24935ca73c96589d9f30ff00f8567af7f7ecff00efe9ff000a3fe159ebdfdfb3ff00bfa7fc2baaf02f89f51f105cde25f188ac28a57626de493fe15cbf887c63afd97886fedadf5064862999517cb4381f88aca9d7cc275a542f1ba57ea6b52865f0a31ad6959e837fe159ebdfdfb3ff00bfa7fc28ff008567af7f7ecffefe9ff0acff00f84ebc4bff004136ff00bf51ff00f135d67807c47ab6b3ac5c41a85e19a3480baa945183b80cf007ad695e798d1a6ea4a51b2f533a10cbeb5454e3195dfa187ff0acf5efefd9ff00dfd3fe147fc2b3d7bfbf67ff007f4ff85759f1075bd4745b6b17d3ee4c0d23b872155b20018ea0d707ff0009d7897fe826dff7ea3ffe26a70d5b30c4525560e367dee5626965f87a8e9c94aebd0d383e1b6bb1dc47216b3c2b027f7a7b1fa57ae578945e39f1234a80ea6d82c01fdd27ff00135edb5e666f1c4a70fac34f7b5be47a594cb0cd4fd826b6bdfe671df12354363e1e1691b624bc7d871fdc1cb7f41f8d78fd76ff0013aeccde2082d7f860841fc58e4fe8057115eee514553c2c5f57aff5f23c4cdab3a98a92e8b4febe615e93a5c10f81bc26dabdcc4adaade00b1237550790bedc727f2f4ae37c2fa7aea9e25b0b47198da4dce3d5546e23f1c63f1adef899a835c78863b307f776b10e07f79b93fa6da316fdb578617a3d65e8b65f361845ec68cf13d765eafafc91c85d5d4f7b7525cdccad2cd21dccedd49a868a9ecace7d42f22b4b58cc93cadb51477af47dd847b2479def4e5ddb20a2bd221f8544db033eabb672390916541fa935c56bba15e787f5036976a0e46e8e45fbaebea3fc2b9a863f0f5e4e14e5767557c0e228479ea46c8cc048208382390457b0780bc50fad593595e3eebdb651f393cca9d33f51d0fe1eb5e3f5b1e16d41b4bf13585c024299446e3d55b83fcf3f856798e16388a0d5b55aafebccbcbb152c3d74fa3d19e99f1035e9347d156deddcadcde128ac3aaa0fbc47bf207e35e375dc7c51959bc436b11276a5b0207b966cff00215c3d6594518d3c2c5ade5ab35cdab4aa62649ed1d105156f4cd3e7d575282c6df6f9b336d52c7007724fd0026b5fc4de11baf0d24124b711cf1cc4a864041047b1aee957a71a8a937ef3d91c31a15254dd54bdd5bb39da28a2b6320a2ba6bff046a5a7787d75699e22bb559e104ee407a7b7719ae66b2a55a9d64dd3774b434ab46a52695456bea5dd23539f47d520beb7621e26c919e197b83f515f405bce9756d15c44731ca81d4fa82322be72af70f08dd01e08b1b890fcb142d9fa2923f90af0f3fa29c61516f7b1ede4559a94e9bdad739af889e2992163a2594854900dcba9e707a27e5c9fc3debcd2a7bdbb92fafa7bb94e649a42edf89cd415ebe0f0b1c35154d6fd7d4f2b19899626ab9bdba7a0515ada17876ffc4372d0d92285400c92b9c2a67a67dfdab7352f86fabd8dab4f0c90ddec19648f21b1ec0f5a75319429cfd9ce693269e12bd487b4845b471b5a3a26b577a16a297968fc8e1d09f9645f4359d456f3846717192ba66309ca12528bb347d0da5ea306ada6c17d6c7314ab900f507b83ee0f15e09aaffc862fbfebe24ffd08d77ff0b351665bed35db2ab89a31e9d9bff65ae0355ff90c5f7fd7c49ffa11af132cc3fd5f15569f456b7a1ece6588fac61a954eaeff007952bd17e14ffc7c6a9fee47fcdabceabd17e14ffc7c6a9fee47fcdabb336ff739fcbf3472655fef90f9fe4c87e2a7fc8534ff00fae2dffa157015dffc54ff0090a69fff005c5bff0042ae029e55fee70febab1669fef73feba1a5e1eff91974bffafb8bff004215de7c55ff008f3d37feba3ff215c1f87bfe465d2ffebee2ff00d085779f157fe3cf4dff00ae8ffc8573e2bfe46147d1fea74617fe45f5bd57e8798d3e1ff5f1ff00bc3f9d329f0ffaf8ff00de1fcebd77b1e42dcf6af1effc8997dff00ffd0d6bc4abdb7c7bff002265f7fc03ff00435af12af1721ff7697afe88f6b3dff788fa7eac2bddbc1fff00228e99ff005c47f335e135eede0fff0091474cff00ae23f99a8cff00f811f5fd19590ff1a5e9faa3c5b59ff90e6a1ff5f327fe846a955dd67fe439a87fd7cc9ffa11aa55ed52f823e878d53e37ea7a1fc2aff8fcd4bfeb9a7f335ca78aff00e46bd53febe1bf9d757f0abfe3f352ff00ae69fccd729e2bff0091af54ff00af86fe75e650ff0091955f45fa1e956ff91752f57fa98f5dcfc2dff9182eff00ebd4ff00e84b5c35773f0b7fe460bbff00af53ff00a12d74e67fee93f439f2dff7b87a9abf157fe3cf4dff00ae8ffc85798d7a77c55ff8f3d37feba3ff00215e6358e4dfee71f9fe6cd737ff007b97cbf21f0ffaf8ff00de1fcebe8eaf9c61ff005f1ffbc3f9d7d1d5e7710ef4fe7fa1e8e41b54f97ea78bfc43cffc265759fee478ff00be4572d5db7c4eb430f88a1b9fe19e01f9a9c1fe95c4d7b397c94b0b4daec8f1f1f171c5544fbb3acf873ff237c5d3fd4c9d7e954fc71ff239ea5e9b93ff00405a83c29a82e99e28b0b97388c49b1cfb30dbfa673f856ffc4ed39adf5d86f80fdddcc4149ff69783fa115cefdccc537f6a365f2773a17bf9734beccaefe68e1ebb0f868611e2bc498de6ddc479f5c8cfe99ae3ea5b6b99acee63b9b791a39a36dc8ebd41aedc4d275a8ca9a76ba38b0d5551ad1a8d5eccfa32bcefe2b18becba603b7cef31c8f5db819fc338fd2a9c3f152e96dd566d3229260305d652a09f5c60ff003ae3b59d66f35dbf6bcbd705f1b555461517d00af032ecab114b10aa54564bcf73decc334a1570ee9d3776ff00033ea5b6c9bb836fdef3171f5cd455b5e13d39b54f13d8c01728b209643e8abc9fe83f1afa2ab350a729bd923e7e8c1cea462b76cdaf89dff23341ff005e8bff00a1357175dafc4fff00919e0ffaf55ffd09ab8aae6cbbfdd29fa1d198ff00bd4fd4e9be1f807c656791fc2fff00a09aeb7e2a7fc826c3febb9ffd06b93f87ff00f23959ff00bb27fe826bacf8a9ff00209b0ffaee7ff41af3715ff235a5e9fe67a386ff00915d4f5ff23cb294751494a3ef0fad7be7847b7f8bbfe449bfff00ae2bfcc5787d7b8f8bbfe449bfff00ae2bfcc5787578790ff025ebfa23dbcf7f8d1f4fd5857ad68ee63f84f230ea2d67fe6d5e4b5eb1a5ff00c92497febd66fe6d5ae6ff00053ff12fd4c729f8ea7f85fe8793d14515eb9e51ec3f0d6dd22f0af9aa06f9a67663eb8e07f2aec2bcbecb53bbd23e195a5e594a63996f08ce3208cb6411e9595a8fc43d6f50b36b6061b6575daef0a90c47d4938fc2be4eae5b5b155e7522d5b99afb8faaa59951c2d084249df953fbcc6f1135bb788f516b5c7926e1f6ede9d7b7b66b328a2beaa11e48a8f63e5e72e6939773b4f863bbfe127971d3ecad9ffbe96b97d57fe4317dff005f127fe846bbef859a7b017da932e15b10a1f5eedffb2d715e25b6369e26d4a123a5c3b0fa1391fa1af368548cb1f562ba25fd7e27a15e9ca381a727d5bfebf032abd17e14ff00c7c6a9fee47fcdabceaba0f09f89dbc357d2ca60f3a19942ba86c118e847ebf9d6f98519d6c34e9c16affccc72fab0a3898ce7b2ff00237fe2a7fc8534ff00fae2dffa157015bbe2af11b7893524b8f27c98a24d91a6727ae4927d6b0a9e0294a8e1a109ee858eab1ab8894e1b334bc3dff232e97ff5f717fe842bbcf8abff001e7a6ffd747fe42b8cf085b35d78b34d4519db3090fd17e6fe95d9fc55ff008f3d37feba3ff215c38a6bfb4a8af27fa9db864ffb3ab3f35fa1e634f87fd7c7fef0fe74ca7c3febe3ff00787f3af65ec78eb73dabc7bff2265f7fc03ff435af12af76f17dbb5d784b528d4648877e3fdd21bfa5784d7879035f5792f3fd11ede7a9fb78bf2fd5857bb783ff00e451d33feb88fe66bc26bb9d07e219d1f434b096c4cd2420889c3e0119c8cf1db35be6f86ab88a318d2576998e5389a787ab2954764d1ca6b3ff0021cd43febe64ff00d08d51a92799ee2e259e439791cbb1f72726a3af520b962933cc9bbc9b3d0fe157fc7e6a5ff5cd3f99ae53c57ff235ea9ff5f0dfcebb3f8556cc23d4ae883b58a463ea324ff315c778b90c7e2dd4c1ea6727f3e7fad793879279955b765fa1ead78b59752bf77fa98b5dcfc2e23fe120ba19e7eca7ff00425ae1aade9ba9dde937a97965298e65e338c823b823b8aefc5d175a84a9c7768e0c256546b46a3d933d0fe2b11f64d30679f3243fa0af31ad2d635dd435db849afe60e506d455501547b0acda8c061e587c3c69cb75fe65e3b111c4579548ecc7c3febe3ff787f3afa3abe7187fd7c7fef0fe75f47578dc43bd3f9fe87b1906d53e5fa9c5fc4ad2cde6809791ae5ecdf71ff71b83fae0d79157d1b3c31dcc124132078a4528ea7a104608af06f10e8b3683ac4d65264a03ba273fc687a1fe9f515ae458a5283a0f75aaf432cf30ce3355d6cf47ea65d7a8e91716fe3bf08be9377205d46d80c39eb91f75fdfd0fe3ea2bcbaac58df5ce9b7b1ddda4a629e339561fcbdc57ab8cc37b782e57694754fccf330789f6137ccaf17a35e42dfd85ce997b259de44639a33820f7f71ea2ab57a745abf87fc77671daeac16cb5241847dd8e7fd963d47fb27ffaf585a9fc38d66cdd8d9f977b0f628c15bf107fa1ac696611bfb3c47b93f3d9fa335ab97c9af6987f7e3e5baf5471d456b3f8635d8e5f2db49bbdded1123f31c568d8f803c4179200f6ab6c9dde67031f80c9ae9962e8415e535f79cd1c2d793b460fee3995567754452ccc70aaa3249f402bd93c0de176d06c1ae2ed40bfb80378ebe5af65faf73ffd6a97c37e08b0d00adc39fb55e8e9338c04ff007476faf5ae9ebe6f34cd5575eca8fc3d5f7ff807d1e5995ba0fdad5f8ba2edff0004f23f89ff00f233c1ff005eabff00a1357155e9fe38f0a6adae6b915d58c28f12c0a84b48179058f7fad735ff000aefc47ff3ed17fdfe5ff1af5b018cc3c30d08ca6934bb9e563b095e7899ca306d5fb0cf87ff00f23959ff00bb27fe826bacf8a9ff00209b0ffaee7ff41aa3e12f06eb5a47892daf6ee08d6040e188941232a40e056ff8f743d435db0b48b4f896478e52cc0b85c0c63bd70623114a59953a8a4b952defa753ba861eac72ea94dc5ddbdadaf43c6e947de1f5ae9ffe15ef893fe7ce3ffbfe9fe34a3e1ef89011fe871ffdff004ff1af6febd86ff9f8bef478bf52c4ff00cfb7f733d23c5dff00224dff00fd715fe62bc3abde7c456171a87862eecad9034f2461554b019391dcd7977fc2bdf127fcf9c7ff007fd3fc6bc6c97114695192a924b5eafc91ebe7387ab52ac5c22de9d179b397af58d2ff00e4924bff005eb37f36ae3ffe15ef893fe7ce3ffbfe9fe35dd47a7dce97f0cae6ceed024f1dacdb943038c962391ec6b7ccb1346ac69aa724df32d99965b87ab4a551ce2d2e57ba3c768a28af70f10eda6ff92496ff00f5fa7f9b57135df436375a87c2b821b3b7927945d96d918c9c0279ae513c3bad48fb1749bdddef030fe95e760eac23ed149a5ef33d0c5d39cbd9b8a6fdd46656868da3dd6b9a8c767689966e59cf445ee4d745a4fc38d5ef5d5afb65943df710ce7e807f535e9ba368763a0d9fd9aca2da0f2f2372ce7d49ac31b9bd2a3171a4f9a5f8237c165356b494aaae58fe2c9b4ad360d234c82c6d8623897193d58f727dc9e6bcf7e26684eb731eb50a131b811cf8fe161f74fe238fc07ad7a75473c115cc12413c6b2452295746190457cd617193a15fdb6fdfcee7d1e2b090af43d8eddbcac7ce54577be20f86f776f2b4fa37fa44079f219b0e9ec09fbc3f5fad71b71a65fda394b8b2b88987678c8afb4a18ca35e37a72ff0033e36be12b5095a71ff22a51576d747d4ef5b6db69f752fba44c40fa9c605773e1bf86f279a975aded0aa722d54e73fef11fc854e231b430f1bce5f2ea561f055abcad08fcfa163e1af87de08e4d66e536995765b8239dbddbf1e83f1f5a3e2b11f64d3077df21fd057a12aaa285550aa060003000af24f897a9a5e6bb159c4c196d23c311fdf6e48fc80af9ec0d59e2f31555f4bfc95ac8fa0c6d286132f74975b7cddee7154f87fd7c7fef0fe74cab5a640d73aad9c0a326499107e2457d549da2db3e5a2af2491f42ba2c91b2380cac0820f715e0be22d165d07599ace407cb07742e7f8d0f43fd3ea2bdf2b275ff000fd97886cbecf74a43ae4c52afde43fe1ed5f179663bea951f37c2f7ff0033ecb32c0fd6a9ae5f896dfe4782515d2eafe05d6f4b76296cd7700e92403771eebd4560359dca36d6b79837a1439afb0a75e9555cd09268f91a942ad27cb38b4c86a482096eae238208da496460a88a3924d6ae9de15d6b54702dec260a7fe5a4abb147e27fa57a7f857c176de1e1f689985c5f9183263e541e8bfe3fcab93199951c345eb7976ff33af079756c4496968f7ff234fc37a32e85a1c1659064037cac3bb9ebfe1f85705f13345787518f578d098675092903eeb8e99fa8fe55ea750ddda417d6b25add44b2c320dac8dd08af95c2e3a74713ede5adf7f99f5189c142b61fd8474b6df23e74a2bd0357f861771cccfa55c24b113911cc76b2fb67a1fd2b360f86fe209640b247042bdd9e507f966beb619961651e6e75fa9f293cbb151972f23391a2b6bc4ba3db6837d1d84572d717089bae1f1850c7a281f4fe758b5d74ea46a414e3b3396a539539384b743e1ff5f1ff00bc3f9d7d1d5f3ad94666bfb789792f2aa8fc48afa2abe73885eb4d7afe87d0e40b4a9f2fd42b0bc53e1b83c47a6f94484ba8b26094f63e87d8d6ed15e052ab3a535383b347bd569c6ac1c26ae99f3b5ed95ce9d7925addc4d14d19c32b7f9e955ebddfc45e18b1f11db04b81e5ce83f773a0f997dbdc7b57916bbe16d534090fda612f6f9f9678f943f5f43ec6becb0399d2c4a517a4bb7f91f1f8ecb6a619f32d63dffccc5adad37c59ade94152dafe4318e91cbf3afebd3f0ac5a2bd0a94e15172cd26bcce0a75274dde0ecfc8ede2f8a1aca2057b6b391bfbc55867f234ff00f85a5ab7fcf9597e4fff00c5570b45723cb308ff00e5da3a96658afe76775ff0b4b56ff9f2b2fc9fff008aa3fe1696adff003e565f93ff00f155c2d14bfb3309fc883fb4b17fceceebfe1696adff003e565f93ff00f1547fc2d2d5bfe7cacbf27ffe2ab85a28feccc27f220fed2c5ff3b3baff0085a5ab7fcf9597e4ff00fc5521f8a5abf6b2b2ff00be5fff008aae1a8a3fb3309fc883fb4b17fcecee3fe1696b1ff3e763ff007cbfff001547fc2d2d63fe7cec7fef97ff00e2ab87a29ff66613f9107f6962bf9d9dc7fc2d2d63fe7cec7fef97ff00e2a8ff0085a5ac7fcf9d8ffdf2ff00fc5570f451fd9984fe441fda58afe76771ff000b4b58ff009f3b1ffbe5ff00f8aaec67d465d5be1cdcdfce88924d672332a6703a8e33f4af16af5db1ff00924cdff5e52ffecd5e6e6384a145539538d9f323d1cbf175ab3a91a92bae56791514515f40782751a278eb51d0b4c4b1b6b7b578d58b0691589e4e7b115a1ff0b4b58ff9f3b1ff00be5fff008aae1e8ae39e5f869c9ca504db3ae18fc4c22a319bb23b8ff85a5ac7fcf9d8ff00df2fff00c551ff000b4b58ff009f3b1ffbe5ff00f8aae1e8a9feccc27f222bfb4b15fcecee3fe1696b1ff3e763ff007cbfff001547fc2d2d63fe7cec7fef97ff00e2ab87a28feccc27f220fed2c57f3b3b8ff85a5ac7fcf9d8ff00df2fff00c551ff000b4b58ff009f3b1ffbe5ff00f8aae1e8a3fb3309fc883fb4b15fcecee3fe168eb39cfd8ec7fef97ffe2a8ff85a5acffcf9d87fdf2fff00c5570f451fd9b84ff9f683fb4b15fcececaebe25eb7716cf12476b6ecdc7991a9c8fa649ae39dda4767762ccc72589c927d6928ade8e1a950baa71b5cc2b622ad6b3a92bd82baef877a535ff008916e8afee6cd7cc63fed1e147f33f85737a769d75aadec769671192673c01d00f527b0af70f0e6830f87b494b48c8790fcd3498fbedfe1d8579f9be323468ba69fbd2fcbb9df94e0e55ab2a8d7bb1fcfb13eb9a93691a2dd5fac42530a86d85b19e40ebf8d705ff000b5a7ffa04c7ff007fcff8575fe35ff913b52ffae63ff4215e195e7e4f82a188a329558dddfcfb23d0cdf1b5e8558c694acade5dd9e89ff0b5a7ff00a04c7ff7fcff00851ff0b5a7ff00a04c7ff7fcff008579dd15eb7f64e0ff0093f17fe6795fdab8cfe7fc17f91e89ff000b5a7ffa04c7ff007fcff851ff000b5a7ffa04c7ff007fcff8579dd147f64e0ff93f17fe61fdab8cfe7fc17f91e89ff0b5a7ff00a04c7ff7fcff00851ff0b5a7ff00a04c7ff7fcff008579dd147f64e0ff0093f17fe61fdab8cfe7fc17f91e89ff000b5a7ffa04c7ff007fcff854571f14ef64b774834e8a2948c2c8642db7df1819ae028a1655834efc9f8bff00313cd316d5b9ff0005fe4493cd2dccf24f33b492c8c59dd8e4927bd47454f6767717f771dadac4d2cd21c2a2f7af41b515d923852727ddb3a0f01696da8f8a2de42b98ad7f7ce7dc7ddfd71f91af6aac2f0af8762f0e69421c87b9970d3c83b9f41ec3fc7d6b76be2333c5ac4d7e68fc2b447da65985786a1696ef5614514579c7a0148caaea55d432918208c834b450072baa7c3fd0f512cf144d6729fe280e17fef93c7e58ae5eefe165f2126cf508251d84aa50fe99af52a2bd0a39a62a92b29dd79ea7056cb30b55ddc6cfcb43c69fe1cf8854e04303fbacc3fad37fe15df88ffe7d62ff00bfcbfe35ecf45757f6ee2bb2fbbfe09cdfd8786eefefff008078c7fc2bbf11ff00cfac5ff7f97fc68ff8577e23ff009f58bfeff2ff008d7b3d147f6ee27b2fb9ff0098bfb0f0dddfdebfc8f18ff8577e23ff009f58bfeff2ff008d1ff0aefc47ff003eb17fdfe5ff001af67a28feddc4f65f73ff0030fec3c3777f7aff0023c63fe15df88ffe7d62ff00bfcbfe347fc2bbf11ffcfac5ff007f97fc6bd9e8a3fb7713d97dcffcc3fb0f0dddfdebfc8f18ff008577e23ff9f58bfeff002ff8d1ff000aefc47ff3eb17fdfe5ff1af67af1cf88f2788edbc5935cdd5d78a2d7414813ec771a060a43263e66b84eae3701dc71c0eb47f6ee27b2fb9ff00987f61e1bbbfbd7f911ffc2bbf11ff00cfac5ff7f97fc68ff8577e23ff009f58bfeff2ff008d6eafc44974c8e096eeeadaff004c9b407d42d2fd21685ee268b891590b1c672a703a722b3f56f883e31d33fb196e6d343d392e2c63b9b9bbd423b910191cf312bc6184654601de4e491f427f6ee27b2fb9ff00987f61e1bbbfbd7f914bfe15df88ff00e7d62ffbfcbfe35e8965a45dc3e016d2648d7ed7f65922d9b811b8eec73f88ae3b56f16f88f4ff008965fed3a749e1eb7d01f547822767df10c6e65603e69370c29fbbb0f4c9ac6d07e375f6a706aa264d264b94d32e6fed12d967fdcb468584536f0a18e3ba1c707d457362734ad88494d2d1dffad4e8c3e59470edb837aab7f5a137fc2bbf11ff00cfac5ff7f97fc68ff8577e23ff009f58bfeff2ff008d5bd3fc79f102fa4b0b61a4f87fcfd5b4a1a8d8bf9b2848c00bbbcc1c939ce42af4c8049e4d2ea3f13fc44fe1bf0eea7a7e9fa759c5a8db34d737b7f1dc496d0b2b6dd998812b9e4e5b8c7d335d3fdbb89ecbee7fe673ff0061e1bbbfbd7f914ffe15df88ff00e7d62ffbfcbfe347fc2bbf11ff00cfac5ff7f97fc6bd574cd4fedde1eb7d48cb6521920f34bdbce5a0271c90e403b7dc8e3d2bce2cfe24788e3d4351b5d422d0ee5069171a958dc69c27319f2c1203338024078e531fad1fdbb89ecbee7fe61fd8786eefef5fe451ff008577e23ff9f58bfeff002ff8d1ff000aefc47ff3eb17fdfe5ff1ad3d0be237891aff00433e20d374c8ec759d365bcb7364ee6453147bdb706e0023a019c64649aa56de2af186b1aaf82750d423b2b2d2b55be67852c2e64de63f2d888e653c37ae471c74147f6ee27b2fb9ff00987f61e1bbbfbd7f910ffc2bbf11ff00cfac5ff7f97fc68ff8577e23ff009f58bfeff2ff008d6e7c55bff10d9eabe154f0ddc4ab76f713c86dd642a972238f7f96c3a1c852067b9ed58bff000b452dee7c51e24b6965bab28b4fb13059c92b6c86691991830fe120fdec0c9dbf4a3fb7713d97dcff00cc3fb0f0dddfdebfc86ffc2bbf11ff00cfac5ff7f97fc68ff8577e23ff009f58bfeff2ff008d553f15b5cd7bc07e266b0bad286a9a6451ca6f6c926589a172436c5954309171dc639ad7d57e21f8a7c39637d637d69a5dc6b42d2ca5d3920590a4e65629206dcc0b60a93c63af3eb47f6ee27b2fb9ff00987f61e1bbbfbd7f914bfe15df88ff00e7d62ffbfcbfe347fc2bbf11ff00cfac5ff7f97fc6b65be285dcb63a8ea161676b756c9f61b6b200b2f997570a18866ce022875ed9ebcd57d5be2278a3c37a47882db59b1d23fb774db486f617b5323db4b1c9288ce4310c08cfaf3fccfeddc4f65f73ff0030fec3c3777f7aff00233d7e1d78898e0dbc2bee665ad8d3fe165c3306d46fe38d7ba400b13f89c63f2358be2af1d7887c21aee8b7daff00922692caed858d84b27d9dd895f2f7eeea541396c71838aea6f35fd56c7c45a5c174d6935cb68775792bdbb4cb09913690153ccda579eac0b7a119c544f3bc5c95934bd17f9dcb864d858bbb4dfabff23aed2343d3f43b6f26c201183f79cf2cff00535a35e53a07c46f13cba87869f5dd334b8f4cd7ad669616b37732a3471ef25831c00474519c6793469df10fc59747c377f73a66930e93afea2b05bec92479a388eee1c70bb8e3208c8ec4579539ca727293bb3d4842308f2c5591dff8a2cae351f0d5eda5a47e64f2a0089b80c9dc3b9e2bca7fe101f137fd033ff23c7ffc557b6d15df83ccaae120e14d269bbeb7ff0033871796d2c54d4e6de9a696ff0023c4bfe101f137fd033ff23c7ffc551ff080f89bfe819ff91e3ffe2abdb68aeafedfc4ff002c7ee7fe672ff6161ff9a5f7aff23c4bfe101f137fd033ff0023c7ff00c551ff00080f89bfe819ff0091e3ff00e2abdb68a3fb7f13fcb1fb9ff987f6161ff9a5f7aff23c4bfe101f137fd033ff0023c7ff00c551ff00080f89bfe819ff0091e3ff00e2abdb68a3fb7f13fcb1fb9ff987f6161ff9a5f7aff23c4bfe101f137fd033ff0023c7ff00c551ff00080f89bfe819ff0091e3ff00e2abdb68a3fb7f13fcb1fb9ff987f6161ff9a5f7aff23c934ef865ab5c3837d2c3691f7c1dedf90e3f5af44d0bc35a7787e029671665618799f976fc7b0f615af457162731c462572cde9d91d986cba861df3416bdd8514515c2770514514005145140051451400514514005145140051451400514514005719ae7c35d2b5ad6a7d55352d634bb8ba454bc1a6de1856e80180241839e38e31d68a28031fc5df0fd3588bc2de1cd3b4931693a5dc24b2ddc92214100043c4016deccdc672b8f7ed5d17897c0d6de27b85927d6b5eb28bcbf2a5b6b1bf68a19d3b874c11d320e319079a28a006dc7c3dd166d5b4dbf8deeed858589d3d6da09b6c535be3fd5c80825979f51ce339c0aad63f0d74fb1b0d46c06b7e20b8b2bdb57b35b6b8d40c91db46e0a911a118040380583631f5c9450068d9782f4eb0b9d2278a6ba2fa569c74d8033ae1a321465be5e5be41c8c0ebc566c9f0d2c4787f4ed1ed35ff1169f058466347b2d40c4d2a924e1c01b4f24f3b41a28a00ddb4f0c69565e14ff00846a0b72ba5fd9dad8c5b89251810d93d72724e7deb9ad3fe12e89a78f9352d6666fb0cda786b8ba1262090636282b850bfc3b40f7cd145006ac3e03d2619bc3d279b7527f615a49696e8eca564478c46de67cbc9c0ed8accd2fe14687a4ea7a7dec1a86b2e34e9da6b3b79ef3cc860041051548e179cfafbd1450074da9787ed354d6748d52792659f4a9249205460158ba143bb2093c1ec456127c30f0e25cf882511dc6cd74a35cc21c2a46ca77068f00153b8eeea79a28a009adfc01651f87752d16f358d735383508fcb965d42fda691076d991b57f2e78ce681f0f74a6d43c3d7f7375a85ddce871bc7049712abb4c1863f7a4ae5b1db18fc68a2801961f0d3c3ba7f842e7c331c333d85c4cd3b3338120909043065030570b8fa0eb5547c2bd15f41d534cb9bfd5ef25d4d634b9d42eae84b72511832a8665c0008f4a28a00ddd4bc29a6eadae5aeab7824924b7b59ad04248f2de39400db8632781d88aa16be00d3ad458e6fb5098d9584da742659109f264238384192a0000fa0e73451400e83c05a55baf86c2cd74c3c3f1c91da87652240e9b0f9836f3c7a62bcdbc33f0f3c536be26d261bfd2fecfa7e9ba835e0b95d50cb6c146ec470404968f25812589ce3a8e9451401ee545145001451450014514500145145001451450014514500145145007ffd9, 2, 'k3SDyU4ifWj++mfQyF/Itg==');

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `contactos`
--

CREATE TABLE `contactos` (
  `id` bigint(20) NOT NULL,
  `tipo` tinyint(4) NOT NULL COMMENT '1:telefono fijo\n2:telefono celular\n3:correo\n4:red social face\n5: red social twiter\n6:otro',
  `descripcion` varchar(254) NOT NULL,
  `subtipo` varchar(45) DEFAULT NULL,
  `detalle` tinytext,
  `personas_id` int(11) DEFAULT NULL,
  `entidad` varchar(45) DEFAULT NULL,
  `proveedores_id` bigint(20) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `detalleventas`
--

CREATE TABLE `detalleventas` (
  `id` bigint(20) NOT NULL,
  `cantidad` decimal(9,4) DEFAULT NULL,
  `precio` decimal(9,4) DEFAULT NULL,
  `codigoBarra` varchar(45) DEFAULT NULL,
  `descuento` decimal(9,4) DEFAULT NULL,
  `adicional` decimal(9,4) DEFAULT NULL,
  `descripcion` varchar(150) DEFAULT NULL,
  `productos_id` bigint(20) DEFAULT NULL,
  `encabezadoventas_id` bigint(20) DEFAULT NULL,
  `idx` bigint(20) DEFAULT NULL,
  `idSucursal` bigint(20) DEFAULT NULL,
  `tipoPrecioVenta` varchar(100) DEFAULT NULL,
  `subtotal` decimal(9,4) DEFAULT NULL,
  `descuento_oferta` decimal(9,4) DEFAULT NULL,
  `nombre_oferta` varchar(100) DEFAULT NULL,
  `ofertas_id` int(11) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

--
-- Volcado de datos para la tabla `detalleventas`
--

INSERT INTO `detalleventas` (`id`, `cantidad`, `precio`, `codigoBarra`, `descuento`, `adicional`, `descripcion`, `productos_id`, `encabezadoventas_id`, `idx`, `idSucursal`, `tipoPrecioVenta`, `subtotal`, `descuento_oferta`, `nombre_oferta`, `ofertas_id`) VALUES
(1, '1.0000', '125.0000', NULL, '0.0000', '0.0000', 'Fideos spagetti', 1, 1, NULL, 0, NULL, '125.0000', NULL, NULL, NULL);

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `detalle_transaccion_caja`
--

CREATE TABLE `detalle_transaccion_caja` (
  `id` int(11) NOT NULL,
  `fecha` datetime NOT NULL COMMENT 'La fecha de apertura, cierre u otro movimiento\n',
  `tipo` tinyint(4) NOT NULL COMMENT '1: apertura\n2: ingreso dinero\n3: egreso dinero\n20:cierre\n5:otro \n',
  `observacion` mediumtext,
  `transaccion_caja_id` bigint(20) NOT NULL,
  `monto` decimal(9,4) DEFAULT '0.0000',
  `encabezadoventas_id` bigint(20) DEFAULT NULL,
  `id_usuario_auth` varchar(32) DEFAULT NULL,
  `nombre_usuario_auth` varchar(120) DEFAULT NULL,
  `dni_usuario_auth` varchar(10) DEFAULT NULL,
  `motivo` varchar(80) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

--
-- Volcado de datos para la tabla `detalle_transaccion_caja`
--

INSERT INTO `detalle_transaccion_caja` (`id`, `fecha`, `tipo`, `observacion`, `transaccion_caja_id`, `monto`, `encabezadoventas_id`, `id_usuario_auth`, `nombre_usuario_auth`, `dni_usuario_auth`, `motivo`) VALUES
(1, '2018-04-20 19:08:14', 1, '', 44, '500.0000', NULL, NULL, NULL, NULL, '');

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `domicilios`
--

CREATE TABLE `domicilios` (
  `id` bigint(20) NOT NULL,
  `calle` varchar(100) NOT NULL,
  `numero` int(11) DEFAULT NULL,
  `dpto` varchar(45) DEFAULT NULL,
  `piso` varchar(45) DEFAULT NULL,
  `provincia` varchar(45) DEFAULT NULL,
  `ciudad` varchar(150) DEFAULT NULL,
  `detalle` tinytext,
  `personas_id` int(11) DEFAULT NULL,
  `entidad` varchar(45) DEFAULT NULL COMMENT 'si de una persona  y otra entidad como ser proveedores\n',
  `tipo` tinyint(4) DEFAULT NULL,
  `proveedores_id` bigint(20) DEFAULT NULL,
  `principal` bit(1) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `encabezadoventas`
--

CREATE TABLE `encabezadoventas` (
  `id` bigint(20) NOT NULL,
  `fecha` datetime DEFAULT NULL,
  `tipoVenta` varchar(45) DEFAULT NULL,
  `total` decimal(9,4) DEFAULT NULL,
  `observacion` mediumtext,
  `encabezadoventascol` varchar(45) DEFAULT NULL,
  `clientes_id` bigint(20) DEFAULT NULL,
  `sucursales_id` bigint(20) DEFAULT NULL,
  `idFacturaImpresoraFiscal` varchar(10) DEFAULT NULL,
  `nombreCliente` varchar(80) DEFAULT NULL,
  `direccionCliente` varchar(100) DEFAULT NULL,
  `cuilCliente` varchar(20) DEFAULT NULL,
  `dniCliente` varchar(15) DEFAULT NULL,
  `tipoFactura` varchar(15) DEFAULT NULL,
  `hora` time DEFAULT NULL,
  `app_id` int(11) NOT NULL,
  `transaccion_caja_id` bigint(20) DEFAULT NULL,
  `tipo_pago` varchar(200) DEFAULT NULL,
  `id_cliente` bigint(20) DEFAULT NULL,
  `estado` bit(1) NOT NULL DEFAULT b'1',
  `usuarios_id` int(11) NOT NULL,
  `efectivo` decimal(9,4) DEFAULT NULL,
  `vuelto` decimal(9,4) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

--
-- Volcado de datos para la tabla `encabezadoventas`
--

INSERT INTO `encabezadoventas` (`id`, `fecha`, `tipoVenta`, `total`, `observacion`, `encabezadoventascol`, `clientes_id`, `sucursales_id`, `idFacturaImpresoraFiscal`, `nombreCliente`, `direccionCliente`, `cuilCliente`, `dniCliente`, `tipoFactura`, `hora`, `app_id`, `transaccion_caja_id`, `tipo_pago`, `id_cliente`, `estado`, `usuarios_id`, `efectivo`, `vuelto`) VALUES
(1, '2018-04-20 16:08:59', 'CONTADO', '125.0000', NULL, NULL, NULL, 1, '', '', '', '', '', 'CONS. FINAL', NULL, 1, 44, 'PAGO_EFECTIVO', NULL, b'1', 3, '125.0000', '0.0000');

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `eventos_ventas`
--

CREATE TABLE `eventos_ventas` (
  `id` int(11) NOT NULL,
  `nombre` varchar(45) NOT NULL,
  `tipo` int(11) DEFAULT NULL,
  `nombre_usuario_auth` varchar(225) DEFAULT NULL,
  `id_usuario_auth` varchar(10) DEFAULT NULL,
  `detalle` tinytext,
  `fecha` datetime DEFAULT NULL,
  `encabezadoventas_id` bigint(20) NOT NULL,
  `monto` decimal(9,4) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `facturas`
--

CREATE TABLE `facturas` (
  `id` int(11) NOT NULL,
  `numero` varchar(150) DEFAULT NULL,
  `tipo` tinyint(4) DEFAULT NULL,
  `sucursales_id` bigint(20) NOT NULL,
  `proveedores_id` bigint(20) DEFAULT NULL,
  `descripcion` tinytext,
  `subtipo` tinyint(4) DEFAULT NULL,
  `fecha_ingreso` date DEFAULT NULL,
  `fecha_carga` datetime DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `familias`
--

CREATE TABLE `familias` (
  `id` int(11) NOT NULL,
  `nombre` varchar(80) COLLATE utf8_spanish_ci DEFAULT NULL,
  `nivel` int(11) DEFAULT NULL,
  `familias_id` int(11) DEFAULT NULL,
  `nombreCorto` varchar(10) COLLATE utf8_spanish_ci DEFAULT NULL,
  `app_id` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_spanish_ci;

--
-- Volcado de datos para la tabla `familias`
--

INSERT INTO `familias` (`id`, `nombre`, `nivel`, `familias_id`, `nombreCorto`, `app_id`) VALUES
(1, 'Comestibles', 1, NULL, 'com', 1),
(2, 'Fideos', 2, 1, 'fid', 1);

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `formapagos`
--

CREATE TABLE `formapagos` (
  `id` bigint(20) NOT NULL,
  `descripcion` varchar(45) DEFAULT NULL,
  `detalle` mediumtext,
  `activo` bit(1) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

--
-- Volcado de datos para la tabla `formapagos`
--

INSERT INTO `formapagos` (`id`, `descripcion`, `detalle`, `activo`) VALUES
(1, 'EFECTIVO', '', b'1'),
(2, 'TARJETA DE CREDITO', '', b'1'),
(3, 'TARJETA DE DEBITO', '', b'1');

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `imagen_producto`
--

CREATE TABLE `imagen_producto` (
  `id` int(11) NOT NULL,
  `path` varchar(100) COLLATE utf8_spanish_ci DEFAULT NULL,
  `tag` varchar(45) COLLATE utf8_spanish_ci DEFAULT NULL,
  `nombre` varchar(100) COLLATE utf8_spanish_ci DEFAULT NULL,
  `tamanio` varchar(50) COLLATE utf8_spanish_ci DEFAULT NULL,
  `tipo` varchar(50) COLLATE utf8_spanish_ci DEFAULT NULL,
  `producto_id` bigint(20) DEFAULT NULL,
  `alt` varchar(100) COLLATE utf8_spanish_ci DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_spanish_ci;

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `log_users`
--

CREATE TABLE `log_users` (
  `id` int(11) NOT NULL,
  `fecha_ingreso` datetime NOT NULL,
  `navegador` tinytext,
  `usuarios_id` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Volcado de datos para la tabla `log_users`
--

INSERT INTO `log_users` (`id`, `fecha_ingreso`, `navegador`, `usuarios_id`) VALUES
(1, '2018-04-20 19:08:06', NULL, 3),
(2, '2018-04-20 19:21:43', NULL, 4),
(3, '2018-04-25 12:31:33', NULL, 3);

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `movimientos`
--

CREATE TABLE `movimientos` (
  `id` int(11) NOT NULL,
  `cantidad` decimal(9,4) NOT NULL,
  `producto_id` bigint(20) NOT NULL,
  `referencia` varchar(100) DEFAULT NULL,
  `facturas_id` int(11) DEFAULT NULL,
  `tipo` tinyint(4) NOT NULL COMMENT '{id:1, value:"Tel. Celular"},\n                    {id:2, value:"Tel. Fijo"},\n                    {id:3, value:"Mail"},\n                    {id:4, value:"Facebook"},\n                    {id:5, value:"Twiter"},\n                    {id:6, value:"Otro"},',
  `valor_inicial` decimal(9,4) DEFAULT NULL,
  `valor_final` decimal(9,4) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `ofertas`
--

CREATE TABLE `ofertas` (
  `id` int(11) NOT NULL,
  `nombre` varchar(50) NOT NULL,
  `fecha_desde` datetime NOT NULL,
  `fecha_hasta` datetime NOT NULL,
  `categoria_oferta` varchar(45) NOT NULL,
  `tipo_oferta` varchar(45) NOT NULL,
  `tipo_oferta_tipo` varchar(45) NOT NULL,
  `tipo_descuento` varchar(45) NOT NULL,
  `detalle` tinytext,
  `app_id` int(11) NOT NULL,
  `valor` decimal(9,4) DEFAULT NULL,
  `cantidad` decimal(9,4) DEFAULT NULL,
  `activo` bit(1) NOT NULL,
  `prioridad` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `ofertas_producto`
--

CREATE TABLE `ofertas_producto` (
  `id` bigint(20) NOT NULL,
  `precio` decimal(9,4) DEFAULT NULL,
  `ofertas_id` int(11) NOT NULL,
  `producto_id` bigint(20) NOT NULL,
  `estado` bit(1) NOT NULL,
  `descuento` decimal(9,4) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `ofertas_viejo`
--

CREATE TABLE `ofertas_viejo` (
  `id` bigint(20) NOT NULL,
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
  `observacion` mediumtext,
  `productos_id` bigint(20) DEFAULT NULL,
  `pagoTotal` decimal(9,4) DEFAULT NULL,
  `precioProducto` decimal(9,4) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `pagoventas`
--

CREATE TABLE `pagoventas` (
  `id` bigint(20) NOT NULL,
  `descripcion` varchar(100) DEFAULT NULL,
  `monto` decimal(9,4) DEFAULT NULL,
  `numero` varchar(100) DEFAULT NULL,
  `formapagos_id` bigint(20) DEFAULT NULL,
  `encabezadoventas_id` bigint(20) DEFAULT NULL,
  `idx` bigint(20) DEFAULT NULL,
  `tarjeta` varchar(45) DEFAULT NULL,
  `tipo` varchar(45) DEFAULT NULL,
  `pago_con` decimal(9,4) DEFAULT NULL COMMENT 'con cuanto dinero pago'
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

--
-- Volcado de datos para la tabla `pagoventas`
--

INSERT INTO `pagoventas` (`id`, `descripcion`, `monto`, `numero`, `formapagos_id`, `encabezadoventas_id`, `idx`, `tarjeta`, `tipo`, `pago_con`) VALUES
(1, NULL, '125.0000', NULL, 1, 1, NULL, NULL, 'PAGO_EFECTIVO', '125.0000');

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `personas`
--

CREATE TABLE `personas` (
  `id` int(11) NOT NULL,
  `apellido` varchar(50) NOT NULL,
  `nombre` varchar(80) NOT NULL,
  `dni` varchar(10) DEFAULT NULL,
  `cuil` varchar(13) DEFAULT NULL,
  `sexo` tinyint(4) DEFAULT NULL,
  `fecha_nac` date DEFAULT NULL,
  `fecha_alta` datetime DEFAULT NULL,
  `direccion` varchar(45) DEFAULT NULL,
  `ciudad` varchar(45) DEFAULT NULL,
  `app_id` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Volcado de datos para la tabla `personas`
--

INSERT INTO `personas` (`id`, `apellido`, `nombre`, `dni`, `cuil`, `sexo`, `fecha_nac`, `fecha_alta`, `direccion`, `ciudad`, `app_id`) VALUES
(1, 'Ramos', 'Rafael Aldo', '31442888', '23314428889', 1, '2017-11-14', '2017-09-23 19:24:07', NULL, NULL, 1),
(2, 'Ramos', 'Rafael', '31454545', '', 0, NULL, '2018-04-20 19:12:25', NULL, NULL, 1);

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `precio_producto`
--

CREATE TABLE `precio_producto` (
  `id` int(11) NOT NULL,
  `activo` bit(1) DEFAULT NULL,
  `tipo_precios_id` int(11) DEFAULT NULL,
  `precio` decimal(9,4) DEFAULT NULL,
  `producto_id` bigint(20) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_spanish_ci;

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `producto`
--

CREATE TABLE `producto` (
  `id` bigint(20) NOT NULL,
  `nombre` varchar(250) DEFAULT NULL,
  `contenido_neto` decimal(9,4) DEFAULT NULL,
  `iva` decimal(5,2) DEFAULT '21.00',
  `activo` bit(1) DEFAULT NULL,
  `codigo_barra` tinytext,
  `detalle` mediumtext,
  `tags` varchar(45) DEFAULT NULL,
  `unidad` varchar(45) DEFAULT NULL,
  `unidad_id` int(11) DEFAULT NULL,
  `producto_padre_id` int(11) DEFAULT NULL,
  `precio_costo` decimal(9,4) DEFAULT '0.0000',
  `precio_venta` decimal(9,4) DEFAULT '0.0000',
  `nombre_final` varchar(45) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='Tabla con el detalle del producto, o presentacion, por ejemplo fanta es el producto  y viene con presetancion de 2.25, 1, 1.5 l etc\n';

--
-- Volcado de datos para la tabla `producto`
--

INSERT INTO `producto` (`id`, `nombre`, `contenido_neto`, `iva`, `activo`, `codigo_barra`, `detalle`, `tags`, `unidad`, `unidad_id`, `producto_padre_id`, `precio_costo`, `precio_venta`, `nombre_final`) VALUES
(1, 'spagetti', '1000.0000', '21.00', b'1', NULL, '', '', NULL, 12, 1, '100.0000', '125.0000', 'Fideos spagetti');

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `productos_compuestos`
--

CREATE TABLE `productos_compuestos` (
  `id` int(11) NOT NULL,
  `cantidad` decimal(9,4) NOT NULL,
  `descripcion` tinytext,
  `producto_principal_id` bigint(20) DEFAULT NULL,
  `producto_id` bigint(20) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `producto_padre`
--

CREATE TABLE `producto_padre` (
  `id` int(11) NOT NULL,
  `nombre` varchar(250) NOT NULL,
  `detalle` mediumtext,
  `rubros_id` int(11) DEFAULT NULL,
  `familias_id` int(11) NOT NULL,
  `activo` bit(1) DEFAULT NULL,
  `app_id` int(11) NOT NULL,
  `nombre_corto` varchar(45) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

--
-- Volcado de datos para la tabla `producto_padre`
--

INSERT INTO `producto_padre` (`id`, `nombre`, `detalle`, `rubros_id`, `familias_id`, `activo`, `app_id`, `nombre_corto`) VALUES
(1, 'Fideos', '', NULL, 2, b'0', 1, 'Fideos');

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `producto_proveedores`
--

CREATE TABLE `producto_proveedores` (
  `producto_id` bigint(20) NOT NULL,
  `proveedores_id` bigint(20) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `proveedores`
--

CREATE TABLE `proveedores` (
  `id` bigint(20) NOT NULL,
  `razon_social` varchar(200) DEFAULT NULL,
  `estado` bit(1) DEFAULT NULL,
  `cuit` varchar(25) DEFAULT NULL,
  `observacion` mediumtext,
  `app_id` int(11) NOT NULL,
  `tipo` tinyint(4) DEFAULT '0',
  `personas_id` int(11) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `provincia`
--

CREATE TABLE `provincia` (
  `id` int(11) NOT NULL,
  `nombre` varchar(50) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `roles`
--

CREATE TABLE `roles` (
  `id` int(11) NOT NULL,
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
  `itemAdministracion` bit(1) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

--
-- Volcado de datos para la tabla `roles`
--

INSERT INTO `roles` (`id`, `nombre`, `alias`, `realizaVentas`, `administraClientes`, `administraProveedores`, `administraOfertas`, `ingresaProdNoExistentes`, `modificaNombreProducto`, `realizaDescuentos`, `realizaAdicional`, `modificaPrecio`, `administraUsuarios`, `administraRoles`, `itemArchivo`, `itemConfiguracion`, `administraConfigPuntoVenta`, `itemAdministracion`) VALUES
(1, 'ROLE_ADMIN', 'Admin', b'1', b'1', b'1', b'1', b'1', b'1', b'1', b'1', b'1', b'1', b'1', b'1', b'1', b'1', b'1'),
(2, 'ROLE_CAJERO', 'Cajero', b'1', b'0', b'0', b'0', b'0', b'0', b'0', b'0', b'0', b'0', b'0', b'0', b'0', b'0', b'0');

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `rubros`
--

CREATE TABLE `rubros` (
  `id` int(11) NOT NULL,
  `nombre` varchar(100) DEFAULT NULL,
  `descripcion` varchar(100) DEFAULT NULL,
  `app_id` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

--
-- Volcado de datos para la tabla `rubros`
--

INSERT INTO `rubros` (`id`, `nombre`, `descripcion`, `app_id`) VALUES
(2, 'rerere', 'ererer', 1),
(3, 'un producto que nose de que es', 'descripcion', 1),
(4, 'un producto que nose de que es', 'descripcion', 1),
(5, 'Electricidad 22', 'Descripcion Electricidad 22', 1),
(6, 'Electricidad 22', 'Descripcion Electricidad 22', 1),
(7, 'Electricidad 22', 'Descripcion Electricidad 78787', 1),
(8, 'Electricidad 999922', 'Descripcion Electricidad 78787', 1),
(9, 'Electricidad 1111', 'Descripcion Electricidad 78787', 1),
(25, 'Electricidad 4444', 'Descripcion Electricidad 78787', 1),
(26, 'Electricidad 4444', 'Descripcion Electricidad 78787', 1),
(30, 'Rubro chocolates', 'una descripcion', 1),
(41, 'Rubro chocolates', 'una descripcion', 1),
(63, 'Rubro chocolates', 'una descripcion', 1),
(64, 'Rubro chocolates', 'una descripcion', 1),
(65, 'Rubro chocolates', 'una descripcion', 1),
(66, 'Rubro chocolates', 'una descripcion', 1),
(67, 'un producto que nose de que es', 'descripcion', 1);

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `stock_sucursal`
--

CREATE TABLE `stock_sucursal` (
  `id` bigint(20) NOT NULL,
  `stock` decimal(9,4) DEFAULT NULL,
  `stock_minimo` decimal(9,4) DEFAULT NULL,
  `detalle` mediumtext,
  `sucursales_id` bigint(20) DEFAULT NULL,
  `idx` bigint(20) DEFAULT NULL,
  `ubicacion` varchar(254) DEFAULT NULL,
  `producto_id` bigint(20) DEFAULT NULL,
  `punto_reposicion` decimal(9,4) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `sucursales`
--

CREATE TABLE `sucursales` (
  `id` bigint(20) NOT NULL,
  `nombre` varchar(150) NOT NULL,
  `direccion` varchar(150) NOT NULL,
  `prefijo_tel_fijo` varchar(15) DEFAULT NULL,
  `numero_tel_fijo` varchar(45) DEFAULT NULL,
  `activo` bit(1) DEFAULT NULL,
  `app_id` int(11) NOT NULL,
  `detalle` tinytext,
  `principal` bit(1) DEFAULT NULL,
  `prefijo_tel_cel` varchar(15) DEFAULT NULL,
  `numero_tel_cel` varchar(45) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

--
-- Volcado de datos para la tabla `sucursales`
--

INSERT INTO `sucursales` (`id`, `nombre`, `direccion`, `prefijo_tel_fijo`, `numero_tel_fijo`, `activo`, `app_id`, `detalle`, `principal`, `prefijo_tel_cel`, `numero_tel_cel`) VALUES
(1, 'Sucursal Central', 'Lavalle  1254', '388', '4273111', b'1', 1, 'Sucursal  Central', b'1', '388', '4642513'),
(2, 'suc central', 'cvzvx', 'xzvx', NULL, b'1', 14, NULL, b'1', NULL, NULL);

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `telefonos`
--

CREATE TABLE `telefonos` (
  `id` bigint(20) NOT NULL,
  `prefijo` varchar(15) NOT NULL,
  `numero` varchar(45) NOT NULL,
  `empresa` varchar(45) DEFAULT NULL,
  `detalle` tinytext,
  `tipo` tinyint(4) DEFAULT NULL COMMENT '1: celular\n2: fijo\n',
  `personas_id` int(11) DEFAULT NULL,
  `entidad` varchar(45) DEFAULT NULL COMMENT 'para saber si es de una persona p oproveddor por ejemplo',
  `proveedores_id` bigint(20) DEFAULT NULL,
  `principal` bit(1) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `tipo_precios`
--

CREATE TABLE `tipo_precios` (
  `id` int(11) NOT NULL,
  `nombre` varchar(80) COLLATE utf8_spanish_ci DEFAULT NULL,
  `desde` date DEFAULT NULL,
  `hasta` date DEFAULT NULL,
  `activo` bit(1) DEFAULT NULL,
  `margenContado` decimal(9,4) DEFAULT NULL,
  `margenTarjetaCred` decimal(9,4) DEFAULT NULL,
  `margenTarjetaDeb` decimal(9,4) DEFAULT NULL,
  `margenCtaCorriente` decimal(9,4) DEFAULT NULL,
  `orden` int(11) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_spanish_ci;

--
-- Volcado de datos para la tabla `tipo_precios`
--

INSERT INTO `tipo_precios` (`id`, `nombre`, `desde`, `hasta`, `activo`, `margenContado`, `margenTarjetaCred`, `margenTarjetaDeb`, `margenCtaCorriente`, `orden`) VALUES
(1, 'Costo', '2016-01-25', '2016-01-29', b'1', '20.0000', '25.0000', '25.0000', '30.0000', 1),
(2, 'Contado', '2016-01-25', '2016-01-25', b'1', '15.0000', '25.0000', '30.0000', '30.0000', 2),
(3, 'Lista', '2016-06-01', '2016-06-01', b'1', NULL, NULL, NULL, NULL, 3),
(4, 'Precio Tarjeta', '2017-09-08', '2017-09-08', b'1', '0.0000', '0.0000', '0.0000', '0.0000', 4);

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `transaccion_caja`
--

CREATE TABLE `transaccion_caja` (
  `id` bigint(20) NOT NULL,
  `estado` tinyint(4) NOT NULL COMMENT '1:abierta\n2: pausada\n3:cerrada\n',
  `detalle` mediumtext,
  `caja_id` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Volcado de datos para la tabla `transaccion_caja`
--

INSERT INTO `transaccion_caja` (`id`, `estado`, `detalle`, `caja_id`) VALUES
(8, 2, NULL, 5),
(9, 2, NULL, 5),
(10, 2, NULL, 5),
(11, 2, NULL, 5),
(12, 2, NULL, 5),
(13, 2, NULL, 5),
(14, 2, NULL, 5),
(15, 2, NULL, 5),
(16, 2, NULL, 5),
(17, 2, NULL, 5),
(18, 2, NULL, 5),
(19, 2, NULL, 5),
(20, 2, NULL, 5),
(21, 2, NULL, 5),
(22, 2, NULL, 5),
(23, 2, NULL, 5),
(24, 2, NULL, 5),
(25, 2, NULL, 5),
(27, 2, NULL, 5),
(28, 2, NULL, 5),
(29, 2, NULL, 5),
(30, 2, NULL, 5),
(31, 2, NULL, 5),
(32, 2, NULL, 5),
(33, 2, NULL, 5),
(34, 1, NULL, 6),
(35, 2, NULL, 5),
(36, 2, NULL, 5),
(37, 2, NULL, 5),
(38, 2, NULL, 5),
(39, 2, NULL, 5),
(40, 2, NULL, 5),
(41, 2, NULL, 5),
(42, 2, NULL, 1),
(43, 1, NULL, 5),
(44, 1, NULL, 5);

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `ubicaciones`
--

CREATE TABLE `ubicaciones` (
  `id` int(11) NOT NULL,
  `nombre` varchar(80) DEFAULT NULL,
  `detalle` mediumtext,
  `sucursales_id` bigint(20) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Volcado de datos para la tabla `ubicaciones`
--

INSERT INTO `ubicaciones` (`id`, `nombre`, `detalle`, `sucursales_id`) VALUES
(1, 'Estante A1', 'Un estante mas\n', 1),
(2, 'Estante A2', 'Segundo Piso\n', 1);

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `ubicacion_stock`
--

CREATE TABLE `ubicacion_stock` (
  `id` int(11) NOT NULL,
  `cantidad` decimal(9,4) DEFAULT NULL,
  `ubicaciones_id` int(11) DEFAULT NULL,
  `stock_sucursal_id` bigint(20) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `unidad`
--

CREATE TABLE `unidad` (
  `id` int(11) NOT NULL,
  `nombre` varchar(80) DEFAULT NULL,
  `detalle` mediumtext
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

--
-- Volcado de datos para la tabla `unidad`
--

INSERT INTO `unidad` (`id`, `nombre`, `detalle`) VALUES
(1, 'Unidad', NULL),
(2, 'Metros', NULL),
(5, 'Litros', NULL),
(6, 'CM 3', NULL),
(7, 'MLL', NULL),
(8, 'Kilogramos', NULL),
(12, 'Gramos', NULL);

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `usuarios`
--

CREATE TABLE `usuarios` (
  `id` int(11) NOT NULL,
  `usuario` varchar(45) DEFAULT NULL,
  `password` varchar(100) DEFAULT NULL,
  `tipo` varchar(20) DEFAULT NULL,
  `observacion` mediumtext,
  `app_id` int(11) NOT NULL,
  `personas_id` int(11) NOT NULL,
  `mail` varchar(80) DEFAULT NULL,
  `key_user` varchar(250) DEFAULT NULL,
  `key_gravatar` varchar(150) DEFAULT NULL,
  `estado` bit(1) NOT NULL DEFAULT b'1',
  `tipo_usuario` tinyint(4) NOT NULL COMMENT '1: normal  app\n2: facebook\n3: twitter\n',
  `id_red_social` varchar(254) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

--
-- Volcado de datos para la tabla `usuarios`
--

INSERT INTO `usuarios` (`id`, `usuario`, `password`, `tipo`, `observacion`, `app_id`, `personas_id`, `mail`, `key_user`, `key_gravatar`, `estado`, `tipo_usuario`, `id_red_social`) VALUES
(3, 'admin', '$2a$10$F.5S9.Hu72V1ZT87TcnsbOEcM9cwqCKwjJkgdJ2gpiA5dHntZxogC', 'usuario', 'nose senoasd asdasdfa sda ', 1, 1, 'rafa2299@gmail.com', 'yN1aQEhIQ7mTxdZEZ69dVA==', NULL, b'1', 0, NULL),
(4, 'admin2', '$2a$10$RqsF7logrlBTCstM3YIHNeTv4ZUBjDqlKRF1qAqNYeH7hBwA3Ss5i', NULL, NULL, 1, 2, 'email@fmsdf.com', 'odF0rc6lHXG3YywMKT3DfQ==', '376ea88e5a5af691756e88cc088b952e', b'1', 1, NULL);

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `usuarios_roles`
--

CREATE TABLE `usuarios_roles` (
  `id` int(11) NOT NULL,
  `usuarios_id` int(11) NOT NULL,
  `roles_id` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

--
-- Volcado de datos para la tabla `usuarios_roles`
--

INSERT INTO `usuarios_roles` (`id`, `usuarios_id`, `roles_id`) VALUES
(1, 3, 1),
(2, 4, 1);

--
-- Índices para tablas volcadas
--

--
-- Indices de la tabla `app`
--
ALTER TABLE `app`
  ADD PRIMARY KEY (`id`);

--
-- Indices de la tabla `caja`
--
ALTER TABLE `caja`
  ADD PRIMARY KEY (`id`),
  ADD UNIQUE KEY `nombre_UNIQUE` (`nombre`),
  ADD KEY `fk_caja_app1_idx` (`app_id`);

--
-- Indices de la tabla `ciudad`
--
ALTER TABLE `ciudad`
  ADD PRIMARY KEY (`id`),
  ADD KEY `fk_ciudad_provincia1_idx` (`provincia_id`);

--
-- Indices de la tabla `claves`
--
ALTER TABLE `claves`
  ADD PRIMARY KEY (`id`),
  ADD KEY `fk_claves_app1_idx` (`app_id`),
  ADD KEY `fk_claves_usuarios1_idx` (`usuarios_id`);

--
-- Indices de la tabla `clientes`
--
ALTER TABLE `clientes`
  ADD PRIMARY KEY (`id`),
  ADD KEY `fk_clientes_personas1_idx` (`personas_id`),
  ADD KEY `fk_clientes_app1_idx` (`app_id`);

--
-- Indices de la tabla `codigos_barras`
--
ALTER TABLE `codigos_barras`
  ADD PRIMARY KEY (`id`),
  ADD KEY `fk_codigos_barras_producto1_idx` (`producto_id`);

--
-- Indices de la tabla `configuracion`
--
ALTER TABLE `configuracion`
  ADD PRIMARY KEY (`id`);

--
-- Indices de la tabla `contactos`
--
ALTER TABLE `contactos`
  ADD PRIMARY KEY (`id`),
  ADD KEY `fk_contacto_personas_personas1_idx` (`personas_id`),
  ADD KEY `fk_contactos_proveedores1_idx` (`proveedores_id`);

--
-- Indices de la tabla `detalleventas`
--
ALTER TABLE `detalleventas`
  ADD PRIMARY KEY (`id`),
  ADD KEY `fk_detalleVenta_productos1_idx` (`productos_id`),
  ADD KEY `fk_detalleventas_encabezadoventas1_idx` (`encabezadoventas_id`),
  ADD KEY `fk_detalleventas_ofertas1_idx` (`ofertas_id`);

--
-- Indices de la tabla `detalle_transaccion_caja`
--
ALTER TABLE `detalle_transaccion_caja`
  ADD PRIMARY KEY (`id`,`transaccion_caja_id`),
  ADD KEY `fk_detalle_transaccion_caja_transaccion_caja1_idx` (`transaccion_caja_id`),
  ADD KEY `fk_detalle_transaccion_caja_encabezadoventas1_idx` (`encabezadoventas_id`);

--
-- Indices de la tabla `domicilios`
--
ALTER TABLE `domicilios`
  ADD PRIMARY KEY (`id`),
  ADD KEY `fk_domicilios_personas1_idx` (`personas_id`),
  ADD KEY `fk_domicilios_proveedores1_idx` (`proveedores_id`);

--
-- Indices de la tabla `encabezadoventas`
--
ALTER TABLE `encabezadoventas`
  ADD PRIMARY KEY (`id`,`usuarios_id`),
  ADD KEY `fk_encabezadoventas_clientes1_idx` (`clientes_id`),
  ADD KEY `fk_encabezadoventas_sucursales1_idx` (`sucursales_id`),
  ADD KEY `fk_encabezadoventas_app1_idx` (`app_id`),
  ADD KEY `fk_encabezadoventas_transaccion_caja1_idx` (`transaccion_caja_id`),
  ADD KEY `fk_encabezadoventas_usuarios1_idx` (`usuarios_id`);

--
-- Indices de la tabla `eventos_ventas`
--
ALTER TABLE `eventos_ventas`
  ADD PRIMARY KEY (`id`),
  ADD KEY `fk_eventos_ventas_encabezadoventas1_idx` (`encabezadoventas_id`);

--
-- Indices de la tabla `facturas`
--
ALTER TABLE `facturas`
  ADD PRIMARY KEY (`id`),
  ADD KEY `fk_facturas_sucursales1_idx` (`sucursales_id`),
  ADD KEY `fk_facturas_proveedores1_idx` (`proveedores_id`);

--
-- Indices de la tabla `familias`
--
ALTER TABLE `familias`
  ADD PRIMARY KEY (`id`),
  ADD KEY `fk_familias_familias1_idx` (`familias_id`),
  ADD KEY `fk_familias_app1_idx` (`app_id`);

--
-- Indices de la tabla `formapagos`
--
ALTER TABLE `formapagos`
  ADD PRIMARY KEY (`id`);

--
-- Indices de la tabla `imagen_producto`
--
ALTER TABLE `imagen_producto`
  ADD PRIMARY KEY (`id`),
  ADD KEY `fk_imagen_producto_producto1_idx` (`producto_id`);

--
-- Indices de la tabla `log_users`
--
ALTER TABLE `log_users`
  ADD PRIMARY KEY (`id`),
  ADD KEY `fk_log_users_usuarios1_idx` (`usuarios_id`);

--
-- Indices de la tabla `movimientos`
--
ALTER TABLE `movimientos`
  ADD PRIMARY KEY (`id`),
  ADD KEY `fk_movimientos_producto1_idx` (`producto_id`),
  ADD KEY `fk_movimientos_facturas1_idx` (`facturas_id`);

--
-- Indices de la tabla `ofertas`
--
ALTER TABLE `ofertas`
  ADD PRIMARY KEY (`id`),
  ADD KEY `fk_ofertas_app1_idx` (`app_id`);

--
-- Indices de la tabla `ofertas_producto`
--
ALTER TABLE `ofertas_producto`
  ADD PRIMARY KEY (`id`),
  ADD KEY `fk_ofertas_producto_ofertas1_idx` (`ofertas_id`),
  ADD KEY `fk_ofertas_producto_producto1_idx` (`producto_id`);

--
-- Indices de la tabla `ofertas_viejo`
--
ALTER TABLE `ofertas_viejo`
  ADD PRIMARY KEY (`id`),
  ADD KEY `fk_ofertas_productos1_idx` (`productos_id`);

--
-- Indices de la tabla `pagoventas`
--
ALTER TABLE `pagoventas`
  ADD PRIMARY KEY (`id`),
  ADD KEY `fk_pagos_formapagos1_idx` (`formapagos_id`),
  ADD KEY `fk_pagoventas_encabezadoventas1_idx` (`encabezadoventas_id`);

--
-- Indices de la tabla `personas`
--
ALTER TABLE `personas`
  ADD PRIMARY KEY (`id`),
  ADD KEY `fk_personas_app1_idx` (`app_id`);

--
-- Indices de la tabla `precio_producto`
--
ALTER TABLE `precio_producto`
  ADD PRIMARY KEY (`id`),
  ADD KEY `fk_precio_producto_tipo_precios1_idx` (`tipo_precios_id`),
  ADD KEY `fk_precio_producto_producto1_idx` (`producto_id`);

--
-- Indices de la tabla `producto`
--
ALTER TABLE `producto`
  ADD PRIMARY KEY (`id`),
  ADD KEY `fk_detalle_productos_unidad1_idx` (`unidad_id`),
  ADD KEY `fk_producto_producto_padre1_idx` (`producto_padre_id`);

--
-- Indices de la tabla `productos_compuestos`
--
ALTER TABLE `productos_compuestos`
  ADD PRIMARY KEY (`id`,`cantidad`),
  ADD KEY `fk_productos_compuestos_producto1_idx` (`producto_principal_id`),
  ADD KEY `fk_productos_compuestos_producto2_idx` (`producto_id`);

--
-- Indices de la tabla `producto_padre`
--
ALTER TABLE `producto_padre`
  ADD PRIMARY KEY (`id`),
  ADD KEY `fk_productos_rubros1_idx` (`rubros_id`),
  ADD KEY `fk_productos_familias1_idx` (`familias_id`),
  ADD KEY `fk_producto_padre_app1_idx` (`app_id`);

--
-- Indices de la tabla `producto_proveedores`
--
ALTER TABLE `producto_proveedores`
  ADD PRIMARY KEY (`producto_id`,`proveedores_id`),
  ADD KEY `fk_producto_has_proveedores_proveedores1_idx` (`proveedores_id`),
  ADD KEY `fk_producto_has_proveedores_producto1_idx` (`producto_id`);

--
-- Indices de la tabla `proveedores`
--
ALTER TABLE `proveedores`
  ADD PRIMARY KEY (`id`),
  ADD KEY `fk_proveedores_app1_idx` (`app_id`),
  ADD KEY `fk_proveedores_personas1_idx` (`personas_id`);

--
-- Indices de la tabla `provincia`
--
ALTER TABLE `provincia`
  ADD PRIMARY KEY (`id`);

--
-- Indices de la tabla `roles`
--
ALTER TABLE `roles`
  ADD PRIMARY KEY (`id`);

--
-- Indices de la tabla `rubros`
--
ALTER TABLE `rubros`
  ADD PRIMARY KEY (`id`),
  ADD KEY `fk_rubros_app1_idx` (`app_id`);

--
-- Indices de la tabla `stock_sucursal`
--
ALTER TABLE `stock_sucursal`
  ADD PRIMARY KEY (`id`),
  ADD KEY `fk_stockSucursal_sucursales1_idx` (`sucursales_id`),
  ADD KEY `fk_stock_sucursal_producto1_idx` (`producto_id`);

--
-- Indices de la tabla `sucursales`
--
ALTER TABLE `sucursales`
  ADD PRIMARY KEY (`id`),
  ADD KEY `fk_sucursales_app1_idx` (`app_id`);

--
-- Indices de la tabla `telefonos`
--
ALTER TABLE `telefonos`
  ADD PRIMARY KEY (`id`),
  ADD KEY `fk_telefonos_personas_personas1_idx` (`personas_id`),
  ADD KEY `fk_telefonos_proveedores1_idx` (`proveedores_id`);

--
-- Indices de la tabla `tipo_precios`
--
ALTER TABLE `tipo_precios`
  ADD PRIMARY KEY (`id`);

--
-- Indices de la tabla `transaccion_caja`
--
ALTER TABLE `transaccion_caja`
  ADD PRIMARY KEY (`id`),
  ADD KEY `fk_transaccion_caja_caja1_idx` (`caja_id`);

--
-- Indices de la tabla `ubicaciones`
--
ALTER TABLE `ubicaciones`
  ADD PRIMARY KEY (`id`),
  ADD KEY `fk_ubicaciones_sucursales1_idx` (`sucursales_id`);

--
-- Indices de la tabla `ubicacion_stock`
--
ALTER TABLE `ubicacion_stock`
  ADD PRIMARY KEY (`id`),
  ADD KEY `fk_ubicacion_sctock_stock_sucursal1_idx` (`stock_sucursal_id`),
  ADD KEY `fk_ubicacion_sctock_ubicaciones1_idx` (`ubicaciones_id`);

--
-- Indices de la tabla `unidad`
--
ALTER TABLE `unidad`
  ADD PRIMARY KEY (`id`);

--
-- Indices de la tabla `usuarios`
--
ALTER TABLE `usuarios`
  ADD PRIMARY KEY (`id`),
  ADD KEY `fk_usuarios_app1_idx` (`app_id`),
  ADD KEY `fk_usuarios_personas1_idx` (`personas_id`);

--
-- Indices de la tabla `usuarios_roles`
--
ALTER TABLE `usuarios_roles`
  ADD PRIMARY KEY (`id`),
  ADD KEY `fk_usuarios_roles_usuarios1_idx` (`usuarios_id`),
  ADD KEY `fk_usuarios_roles_roles1_idx` (`roles_id`);

--
-- AUTO_INCREMENT de las tablas volcadas
--

--
-- AUTO_INCREMENT de la tabla `app`
--
ALTER TABLE `app`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=41;

--
-- AUTO_INCREMENT de la tabla `caja`
--
ALTER TABLE `caja`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=7;

--
-- AUTO_INCREMENT de la tabla `ciudad`
--
ALTER TABLE `ciudad`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT;

--
-- AUTO_INCREMENT de la tabla `claves`
--
ALTER TABLE `claves`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=2;

--
-- AUTO_INCREMENT de la tabla `clientes`
--
ALTER TABLE `clientes`
  MODIFY `id` bigint(20) NOT NULL AUTO_INCREMENT;

--
-- AUTO_INCREMENT de la tabla `codigos_barras`
--
ALTER TABLE `codigos_barras`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT;

--
-- AUTO_INCREMENT de la tabla `configuracion`
--
ALTER TABLE `configuracion`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=2;

--
-- AUTO_INCREMENT de la tabla `contactos`
--
ALTER TABLE `contactos`
  MODIFY `id` bigint(20) NOT NULL AUTO_INCREMENT;

--
-- AUTO_INCREMENT de la tabla `detalleventas`
--
ALTER TABLE `detalleventas`
  MODIFY `id` bigint(20) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=2;

--
-- AUTO_INCREMENT de la tabla `detalle_transaccion_caja`
--
ALTER TABLE `detalle_transaccion_caja`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=2;

--
-- AUTO_INCREMENT de la tabla `domicilios`
--
ALTER TABLE `domicilios`
  MODIFY `id` bigint(20) NOT NULL AUTO_INCREMENT;

--
-- AUTO_INCREMENT de la tabla `encabezadoventas`
--
ALTER TABLE `encabezadoventas`
  MODIFY `id` bigint(20) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=2;

--
-- AUTO_INCREMENT de la tabla `eventos_ventas`
--
ALTER TABLE `eventos_ventas`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT;

--
-- AUTO_INCREMENT de la tabla `facturas`
--
ALTER TABLE `facturas`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT;

--
-- AUTO_INCREMENT de la tabla `familias`
--
ALTER TABLE `familias`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=3;

--
-- AUTO_INCREMENT de la tabla `formapagos`
--
ALTER TABLE `formapagos`
  MODIFY `id` bigint(20) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=4;

--
-- AUTO_INCREMENT de la tabla `imagen_producto`
--
ALTER TABLE `imagen_producto`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT;

--
-- AUTO_INCREMENT de la tabla `log_users`
--
ALTER TABLE `log_users`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=4;

--
-- AUTO_INCREMENT de la tabla `movimientos`
--
ALTER TABLE `movimientos`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT;

--
-- AUTO_INCREMENT de la tabla `ofertas`
--
ALTER TABLE `ofertas`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT;

--
-- AUTO_INCREMENT de la tabla `ofertas_producto`
--
ALTER TABLE `ofertas_producto`
  MODIFY `id` bigint(20) NOT NULL AUTO_INCREMENT;

--
-- AUTO_INCREMENT de la tabla `ofertas_viejo`
--
ALTER TABLE `ofertas_viejo`
  MODIFY `id` bigint(20) NOT NULL AUTO_INCREMENT;

--
-- AUTO_INCREMENT de la tabla `pagoventas`
--
ALTER TABLE `pagoventas`
  MODIFY `id` bigint(20) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=2;

--
-- AUTO_INCREMENT de la tabla `personas`
--
ALTER TABLE `personas`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=3;

--
-- AUTO_INCREMENT de la tabla `precio_producto`
--
ALTER TABLE `precio_producto`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT;

--
-- AUTO_INCREMENT de la tabla `producto`
--
ALTER TABLE `producto`
  MODIFY `id` bigint(20) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=2;

--
-- AUTO_INCREMENT de la tabla `productos_compuestos`
--
ALTER TABLE `productos_compuestos`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT;

--
-- AUTO_INCREMENT de la tabla `producto_padre`
--
ALTER TABLE `producto_padre`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=2;

--
-- AUTO_INCREMENT de la tabla `proveedores`
--
ALTER TABLE `proveedores`
  MODIFY `id` bigint(20) NOT NULL AUTO_INCREMENT;

--
-- AUTO_INCREMENT de la tabla `provincia`
--
ALTER TABLE `provincia`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT;

--
-- AUTO_INCREMENT de la tabla `roles`
--
ALTER TABLE `roles`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=3;

--
-- AUTO_INCREMENT de la tabla `rubros`
--
ALTER TABLE `rubros`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=68;

--
-- AUTO_INCREMENT de la tabla `stock_sucursal`
--
ALTER TABLE `stock_sucursal`
  MODIFY `id` bigint(20) NOT NULL AUTO_INCREMENT;

--
-- AUTO_INCREMENT de la tabla `sucursales`
--
ALTER TABLE `sucursales`
  MODIFY `id` bigint(20) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=3;

--
-- AUTO_INCREMENT de la tabla `telefonos`
--
ALTER TABLE `telefonos`
  MODIFY `id` bigint(20) NOT NULL AUTO_INCREMENT;

--
-- AUTO_INCREMENT de la tabla `tipo_precios`
--
ALTER TABLE `tipo_precios`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=5;

--
-- AUTO_INCREMENT de la tabla `transaccion_caja`
--
ALTER TABLE `transaccion_caja`
  MODIFY `id` bigint(20) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=45;

--
-- AUTO_INCREMENT de la tabla `ubicaciones`
--
ALTER TABLE `ubicaciones`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=3;

--
-- AUTO_INCREMENT de la tabla `ubicacion_stock`
--
ALTER TABLE `ubicacion_stock`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT;

--
-- AUTO_INCREMENT de la tabla `unidad`
--
ALTER TABLE `unidad`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=13;

--
-- AUTO_INCREMENT de la tabla `usuarios`
--
ALTER TABLE `usuarios`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=5;

--
-- AUTO_INCREMENT de la tabla `usuarios_roles`
--
ALTER TABLE `usuarios_roles`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=3;

--
-- Restricciones para tablas volcadas
--

--
-- Filtros para la tabla `caja`
--
ALTER TABLE `caja`
  ADD CONSTRAINT `fk_caja_app1` FOREIGN KEY (`app_id`) REFERENCES `app` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION;

--
-- Filtros para la tabla `ciudad`
--
ALTER TABLE `ciudad`
  ADD CONSTRAINT `fk_ciudad_provincia1` FOREIGN KEY (`provincia_id`) REFERENCES `provincia` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION;

--
-- Filtros para la tabla `claves`
--
ALTER TABLE `claves`
  ADD CONSTRAINT `fk_claves_app1` FOREIGN KEY (`app_id`) REFERENCES `app` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  ADD CONSTRAINT `fk_claves_usuarios1` FOREIGN KEY (`usuarios_id`) REFERENCES `usuarios` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION;

--
-- Filtros para la tabla `clientes`
--
ALTER TABLE `clientes`
  ADD CONSTRAINT `fk_clientes_app1` FOREIGN KEY (`app_id`) REFERENCES `app` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  ADD CONSTRAINT `fk_clientes_personas1` FOREIGN KEY (`personas_id`) REFERENCES `personas` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION;

--
-- Filtros para la tabla `codigos_barras`
--
ALTER TABLE `codigos_barras`
  ADD CONSTRAINT `fk_codigos_barras_producto1` FOREIGN KEY (`producto_id`) REFERENCES `producto` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION;

--
-- Filtros para la tabla `contactos`
--
ALTER TABLE `contactos`
  ADD CONSTRAINT `fk_contacto_personas_personas1` FOREIGN KEY (`personas_id`) REFERENCES `personas` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  ADD CONSTRAINT `fk_contactos_proveedores1` FOREIGN KEY (`proveedores_id`) REFERENCES `proveedores` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION;

--
-- Filtros para la tabla `detalleventas`
--
ALTER TABLE `detalleventas`
  ADD CONSTRAINT `fk_detalleventas_encabezadoventas1` FOREIGN KEY (`encabezadoventas_id`) REFERENCES `encabezadoventas` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  ADD CONSTRAINT `fk_detalleventas_ofertas1` FOREIGN KEY (`ofertas_id`) REFERENCES `ofertas` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION;

--
-- Filtros para la tabla `detalle_transaccion_caja`
--
ALTER TABLE `detalle_transaccion_caja`
  ADD CONSTRAINT `fk_detalle_transaccion_caja_encabezadoventas1` FOREIGN KEY (`encabezadoventas_id`) REFERENCES `encabezadoventas` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  ADD CONSTRAINT `fk_detalle_transaccion_caja_transaccion_caja1` FOREIGN KEY (`transaccion_caja_id`) REFERENCES `transaccion_caja` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION;

--
-- Filtros para la tabla `domicilios`
--
ALTER TABLE `domicilios`
  ADD CONSTRAINT `fk_domicilios_personas1` FOREIGN KEY (`personas_id`) REFERENCES `personas` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  ADD CONSTRAINT `fk_domicilios_proveedores1` FOREIGN KEY (`proveedores_id`) REFERENCES `proveedores` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION;

--
-- Filtros para la tabla `encabezadoventas`
--
ALTER TABLE `encabezadoventas`
  ADD CONSTRAINT `fk_encabezadoventas_app1` FOREIGN KEY (`app_id`) REFERENCES `app` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  ADD CONSTRAINT `fk_encabezadoventas_clientes1` FOREIGN KEY (`clientes_id`) REFERENCES `clientes` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  ADD CONSTRAINT `fk_encabezadoventas_sucursales1` FOREIGN KEY (`sucursales_id`) REFERENCES `sucursales` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  ADD CONSTRAINT `fk_encabezadoventas_transaccion_caja1` FOREIGN KEY (`transaccion_caja_id`) REFERENCES `transaccion_caja` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  ADD CONSTRAINT `fk_encabezadoventas_usuarios1` FOREIGN KEY (`usuarios_id`) REFERENCES `usuarios` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION;

--
-- Filtros para la tabla `eventos_ventas`
--
ALTER TABLE `eventos_ventas`
  ADD CONSTRAINT `fk_eventos_ventas_encabezadoventas1` FOREIGN KEY (`encabezadoventas_id`) REFERENCES `encabezadoventas` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION;

--
-- Filtros para la tabla `facturas`
--
ALTER TABLE `facturas`
  ADD CONSTRAINT `fk_facturas_proveedores1` FOREIGN KEY (`proveedores_id`) REFERENCES `proveedores` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  ADD CONSTRAINT `fk_facturas_sucursales1` FOREIGN KEY (`sucursales_id`) REFERENCES `sucursales` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION;

--
-- Filtros para la tabla `familias`
--
ALTER TABLE `familias`
  ADD CONSTRAINT `fk_familias_app1` FOREIGN KEY (`app_id`) REFERENCES `app` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  ADD CONSTRAINT `fk_familias_familias1` FOREIGN KEY (`familias_id`) REFERENCES `familias` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION;

--
-- Filtros para la tabla `imagen_producto`
--
ALTER TABLE `imagen_producto`
  ADD CONSTRAINT `fk_imagen_producto_producto1` FOREIGN KEY (`producto_id`) REFERENCES `producto` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION;

--
-- Filtros para la tabla `log_users`
--
ALTER TABLE `log_users`
  ADD CONSTRAINT `fk_log_users_usuarios1` FOREIGN KEY (`usuarios_id`) REFERENCES `usuarios` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION;

--
-- Filtros para la tabla `movimientos`
--
ALTER TABLE `movimientos`
  ADD CONSTRAINT `fk_movimientos_facturas1` FOREIGN KEY (`facturas_id`) REFERENCES `facturas` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  ADD CONSTRAINT `fk_movimientos_producto1` FOREIGN KEY (`producto_id`) REFERENCES `producto` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION;

--
-- Filtros para la tabla `ofertas`
--
ALTER TABLE `ofertas`
  ADD CONSTRAINT `fk_ofertas_app1` FOREIGN KEY (`app_id`) REFERENCES `app` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION;

--
-- Filtros para la tabla `ofertas_producto`
--
ALTER TABLE `ofertas_producto`
  ADD CONSTRAINT `fk_ofertas_producto_ofertas1` FOREIGN KEY (`ofertas_id`) REFERENCES `ofertas` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  ADD CONSTRAINT `fk_ofertas_producto_producto1` FOREIGN KEY (`producto_id`) REFERENCES `producto` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION;

--
-- Filtros para la tabla `pagoventas`
--
ALTER TABLE `pagoventas`
  ADD CONSTRAINT `fk_pagos_formapagos1` FOREIGN KEY (`formapagos_id`) REFERENCES `formapagos` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  ADD CONSTRAINT `fk_pagoventas_encabezadoventas1` FOREIGN KEY (`encabezadoventas_id`) REFERENCES `encabezadoventas` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION;

--
-- Filtros para la tabla `personas`
--
ALTER TABLE `personas`
  ADD CONSTRAINT `fk_personas_app1` FOREIGN KEY (`app_id`) REFERENCES `app` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION;

--
-- Filtros para la tabla `precio_producto`
--
ALTER TABLE `precio_producto`
  ADD CONSTRAINT `fk_precio_producto_producto1` FOREIGN KEY (`producto_id`) REFERENCES `producto` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  ADD CONSTRAINT `fk_precio_producto_tipo_precios1` FOREIGN KEY (`tipo_precios_id`) REFERENCES `tipo_precios` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION;

--
-- Filtros para la tabla `producto`
--
ALTER TABLE `producto`
  ADD CONSTRAINT `fk_detalle_productos_unidad1` FOREIGN KEY (`unidad_id`) REFERENCES `unidad` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  ADD CONSTRAINT `fk_producto_producto_padre1` FOREIGN KEY (`producto_padre_id`) REFERENCES `producto_padre` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION;

--
-- Filtros para la tabla `productos_compuestos`
--
ALTER TABLE `productos_compuestos`
  ADD CONSTRAINT `fk_productos_compuestos_producto1` FOREIGN KEY (`producto_principal_id`) REFERENCES `producto` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  ADD CONSTRAINT `fk_productos_compuestos_producto2` FOREIGN KEY (`producto_id`) REFERENCES `producto` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION;

--
-- Filtros para la tabla `producto_padre`
--
ALTER TABLE `producto_padre`
  ADD CONSTRAINT `fk_producto_padre_app1` FOREIGN KEY (`app_id`) REFERENCES `app` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  ADD CONSTRAINT `fk_productos_familias1` FOREIGN KEY (`familias_id`) REFERENCES `familias` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  ADD CONSTRAINT `fk_productos_rubros1` FOREIGN KEY (`rubros_id`) REFERENCES `rubros` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION;

--
-- Filtros para la tabla `producto_proveedores`
--
ALTER TABLE `producto_proveedores`
  ADD CONSTRAINT `fk_producto_has_proveedores_producto1` FOREIGN KEY (`producto_id`) REFERENCES `producto` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  ADD CONSTRAINT `fk_producto_has_proveedores_proveedores1` FOREIGN KEY (`proveedores_id`) REFERENCES `proveedores` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION;

--
-- Filtros para la tabla `proveedores`
--
ALTER TABLE `proveedores`
  ADD CONSTRAINT `fk_proveedores_app1` FOREIGN KEY (`app_id`) REFERENCES `app` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  ADD CONSTRAINT `fk_proveedores_personas1` FOREIGN KEY (`personas_id`) REFERENCES `personas` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION;

--
-- Filtros para la tabla `rubros`
--
ALTER TABLE `rubros`
  ADD CONSTRAINT `fk_rubros_app1` FOREIGN KEY (`app_id`) REFERENCES `app` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION;

--
-- Filtros para la tabla `stock_sucursal`
--
ALTER TABLE `stock_sucursal`
  ADD CONSTRAINT `fk_stockSucursal_sucursales1` FOREIGN KEY (`sucursales_id`) REFERENCES `sucursales` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  ADD CONSTRAINT `fk_stock_sucursal_producto1` FOREIGN KEY (`producto_id`) REFERENCES `producto` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION;

--
-- Filtros para la tabla `sucursales`
--
ALTER TABLE `sucursales`
  ADD CONSTRAINT `fk_sucursales_app1` FOREIGN KEY (`app_id`) REFERENCES `app` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION;

--
-- Filtros para la tabla `telefonos`
--
ALTER TABLE `telefonos`
  ADD CONSTRAINT `fk_telefonos_personas_personas1` FOREIGN KEY (`personas_id`) REFERENCES `personas` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  ADD CONSTRAINT `fk_telefonos_proveedores1` FOREIGN KEY (`proveedores_id`) REFERENCES `proveedores` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION;

--
-- Filtros para la tabla `transaccion_caja`
--
ALTER TABLE `transaccion_caja`
  ADD CONSTRAINT `fk_transaccion_caja_caja1` FOREIGN KEY (`caja_id`) REFERENCES `caja` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION;

--
-- Filtros para la tabla `ubicaciones`
--
ALTER TABLE `ubicaciones`
  ADD CONSTRAINT `fk_ubicaciones_sucursales1` FOREIGN KEY (`sucursales_id`) REFERENCES `sucursales` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION;

--
-- Filtros para la tabla `ubicacion_stock`
--
ALTER TABLE `ubicacion_stock`
  ADD CONSTRAINT `fk_ubicacion_sctock_stock_sucursal1` FOREIGN KEY (`stock_sucursal_id`) REFERENCES `stock_sucursal` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  ADD CONSTRAINT `fk_ubicacion_sctock_ubicaciones1` FOREIGN KEY (`ubicaciones_id`) REFERENCES `ubicaciones` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION;

--
-- Filtros para la tabla `usuarios`
--
ALTER TABLE `usuarios`
  ADD CONSTRAINT `fk_usuarios_app1` FOREIGN KEY (`app_id`) REFERENCES `app` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  ADD CONSTRAINT `fk_usuarios_personas1` FOREIGN KEY (`personas_id`) REFERENCES `personas` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION;

--
-- Filtros para la tabla `usuarios_roles`
--
ALTER TABLE `usuarios_roles`
  ADD CONSTRAINT `fk_usuarios_roles_roles1` FOREIGN KEY (`roles_id`) REFERENCES `roles` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  ADD CONSTRAINT `fk_usuarios_roles_usuarios1` FOREIGN KEY (`usuarios_id`) REFERENCES `usuarios` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION;
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
