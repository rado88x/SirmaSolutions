CREATE DATABASE  IF NOT EXISTS `employee_sirma_db`;
USE `employee_sirma_db`;

--
-- Table structure for table `employee`
--

DROP TABLE IF EXISTS `employee_records`;

CREATE TABLE `employee_records` (
  `id` int NOT NULL AUTO_INCREMENT,
  `employee_id` int NOT NULL,
  `project_id` int NOT NULL,
  `date_from` varchar(45) DEFAULT NULL,
  `date_to` date DEFAULT NULL,
  `first_name` date DEFAULT NULL,
  `last_name` varchar(45) DEFAULT NULL,
  `email` varchar(45) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=latin1;

--
-- Data for table `employee`
--

INSERT INTO `employee` VALUES 
	(1, 143, 12, '2013-11-01', '2014-01-05', 'Peter', 'Petrov', 'No client input'),
	(2, 218, 10, '2012-05-16', NULL, 'Todor', 'George', 'No client input'),
	(3, 143, 10, '2009-01-01', '2011-04-27', 'Peter', 'Petrov', 'No client input');



