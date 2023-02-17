-- phpMyAdmin SQL Dump
-- version 5.0.2
-- https://www.phpmyadmin.net/
--
-- Host: 127.0.0.1:3306
-- Generation Time: Feb 17, 2023 at 04:43 AM
-- Server version: 5.7.31
-- PHP Version: 7.3.21

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
START TRANSACTION;
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Database: `pokemon`
--

CREATE DATABASE IF NOT EXISTS pokemon;

USE pokemon;

-- --------------------------------------------------------

--
-- Table structure for table `dex`
--

DROP TABLE IF EXISTS `dex`;
CREATE TABLE IF NOT EXISTS `dex` (
  `id` int(3) NOT NULL AUTO_INCREMENT,
  `name` varchar(20) NOT NULL,
  `type1` varchar(10) NOT NULL,
  `type2` varchar(10) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=MyISAM AUTO_INCREMENT=32 DEFAULT CHARSET=latin1;

--
-- Dumping data for table `dex`
--

INSERT INTO `dex` (`id`, `name`, `type1`, `type2`) VALUES
(1, 'Bulbasaur', 'Grass', 'Poison'),
(2, 'Ivysaur', 'Grass', 'Poison'),
(3, 'Venusaur', 'Grass', 'Poison'),
(4, 'Charmander', 'Fire', '---'),
(5, 'Charmeleon', 'Fire', '---'),
(6, 'Charizard', 'Fire', 'Flying'),
(7, 'Squirtle', 'Water', '---'),
(8, 'Wartortle', 'Water', '---'),
(9, 'Blastoise', 'Water', '---'),
(10, 'Caterpie', 'Bug', '---'),
(11, 'Metapod', 'Bug', '---'),
(12, 'Butterfree', 'Bug', 'Flying'),
(13, 'Weedle', 'Bug', 'Poison'),
(14, 'Kakuna', 'Bug', 'Poison'),
(16, 'Pidgey', 'Normal', 'Flying'),
(15, 'Beedrill', 'Bug', 'Poison'),
(17, 'Pidgeotto', 'Normal', 'Flying'),
(18, 'Pidgeot', 'Normal', 'Flying'),
(19, 'Rattata', 'Normal', '---'),
(20, 'Raticate', 'Normal', '---'),
(21, 'Spearow', 'Normal', 'Flying'),
(22, 'Fearow', 'Normal', 'Flying'),
(23, 'Ekans', 'Poison', '---'),
(24, 'Arbok', 'Poison', '---'),
(25, 'Pikachu', 'Electric', '---'),
(26, 'Raichu', 'Electric', '---'),
(27, 'Sandshrew', 'Ground', '---'),
(28, 'Sandslash', 'Ground', '---'),
(29, 'Nidoran F', 'Poison', '---'),
(30, 'Nidorina', 'Poison', '---'),
(31, 'Nidorina', 'Poison', '---');

-- --------------------------------------------------------

--
-- Table structure for table `info`
--

DROP TABLE IF EXISTS `info`;
CREATE TABLE IF NOT EXISTS `info` (
  `iid` int(3) NOT NULL AUTO_INCREMENT,
  `height` varchar(10) NOT NULL,
  `weight` varchar(10) NOT NULL,
  `egg_group1` varchar(10) NOT NULL,
  `egg_group2` varchar(10) NOT NULL,
  KEY `pid` (`iid`)
) ENGINE=MyISAM AUTO_INCREMENT=30 DEFAULT CHARSET=latin1;

--
-- Dumping data for table `info`
--

INSERT INTO `info` (`iid`, `height`, `weight`, `egg_group1`, `egg_group2`) VALUES
(1, '0.7', '6.9', 'Monster', 'Grass'),
(2, '1.0', '13.0', 'Monster', 'Grass'),
(3, '2.0', '100.0', 'Monster', 'Grass'),
(4, '0.6', '8.5', 'Dragon', 'Monster'),
(5, '1.1', '19.0', 'Dragon', 'Monster'),
(6, '1.7', '90.5', 'Dragon', 'Monster'),
(7, '0.5', '9.0', 'Monster', 'Water 1'),
(8, '1.0', '22.5', 'Monster', 'Water 1'),
(9, '1.6', '85.5', 'Monster', 'Water 1'),
(10, '0.3', '2.9', 'Bug', '---'),
(11, '0.7', '9.9', 'Bug', '---'),
(12, '1.1', '32.0', 'Bug', '---'),
(13, '0.3', '3.2', 'Bug', '---'),
(14, '0.6', '10.0', 'Bug', '---'),
(15, '1.0', '29.5', 'Bug', '---'),
(16, '0.3', '1.8', 'Flying', '---'),
(17, '1.1', '30.0', 'Flying', '---'),
(18, '1.5', '39.5', 'Flying', '---'),
(19, '0.3', '3.5', 'Field', '---'),
(20, '0.7', '18.5', 'Field', '---'),
(21, '0.3', '2.0', 'Flying', '---'),
(22, '1.2', '38.0', 'Flying', '---'),
(23, '2.0', '6.9', 'Dragon', 'Field'),
(24, '3.5', '65', 'Dragon', 'Field'),
(25, '0.4', '6.0', 'Fairy', 'Field'),
(26, '0.8', '30.0', 'Fairy', 'Field'),
(27, '0.6', '12.0', 'Field', '---'),
(28, '1.0', '29.5', 'Field', '---'),
(29, '0.41', '7.0', 'Field', 'Monster');

-- --------------------------------------------------------

--
-- Table structure for table `stats`
--

DROP TABLE IF EXISTS `stats`;
CREATE TABLE IF NOT EXISTS `stats` (
  `sid` int(3) NOT NULL AUTO_INCREMENT,
  `hp` int(3) NOT NULL,
  `atk` int(3) NOT NULL,
  `def` int(3) NOT NULL,
  `spa` int(3) NOT NULL,
  `spd` int(3) NOT NULL,
  `spe` int(3) NOT NULL,
  KEY `pid` (`sid`)
) ENGINE=MyISAM AUTO_INCREMENT=32 DEFAULT CHARSET=latin1;

--
-- Dumping data for table `stats`
--

INSERT INTO `stats` (`sid`, `hp`, `atk`, `def`, `spa`, `spd`, `spe`) VALUES
(1, 45, 49, 49, 65, 65, 45),
(2, 60, 62, 63, 80, 80, 60),
(3, 80, 82, 83, 100, 100, 80),
(4, 39, 52, 43, 60, 50, 65),
(5, 58, 64, 58, 80, 65, 80),
(6, 78, 84, 78, 109, 85, 100),
(7, 44, 48, 65, 50, 64, 43),
(8, 59, 63, 80, 65, 80, 58),
(9, 79, 83, 100, 85, 105, 78),
(10, 45, 30, 35, 20, 20, 45),
(11, 50, 20, 55, 25, 25, 30),
(12, 60, 45, 50, 90, 80, 70),
(13, 40, 35, 30, 20, 20, 50),
(14, 45, 20, 50, 25, 25, 35),
(15, 65, 90, 40, 45, 80, 75),
(16, 40, 45, 40, 35, 35, 56),
(17, 63, 60, 55, 50, 50, 71),
(18, 83, 80, 75, 70, 70, 101),
(19, 30, 56, 35, 25, 35, 72),
(20, 55, 81, 60, 50, 70, 97),
(21, 40, 60, 30, 31, 31, 70),
(22, 65, 90, 65, 61, 61, 100),
(23, 35, 60, 44, 40, 54, 55),
(24, 60, 95, 69, 65, 79, 80),
(25, 35, 55, 40, 50, 50, 90),
(26, 60, 90, 55, 90, 80, 110),
(27, 50, 75, 85, 20, 30, 40),
(28, 75, 100, 110, 45, 55, 65),
(29, 55, 47, 52, 40, 40, 41),
(30, 70, 62, 67, 55, 55, 56),
(31, 70, 62, 67, 55, 55, 56);
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
