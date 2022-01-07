-- phpMyAdmin SQL Dump
-- version 5.0.3
-- https://www.phpmyadmin.net/
--
-- Host: 127.0.0.1
-- Generation Time: Jan 07, 2022 at 12:16 PM
-- Server version: 10.4.14-MariaDB
-- PHP Version: 7.2.34

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
START TRANSACTION;
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Database: `supermarket`
--

-- --------------------------------------------------------

--
-- Table structure for table `inventory`
--

CREATE TABLE `inventory` (
  `inventory_id` int(11) NOT NULL,
  `inventory_name` varchar(50) NOT NULL,
  `price` float NOT NULL,
  `stock` int(11) NOT NULL,
  `category` varchar(50) NOT NULL,
  `supplier_id` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

--
-- Dumping data for table `inventory`
--

INSERT INTO `inventory` (`inventory_id`, `inventory_name`, `price`, `stock`, `category`, `supplier_id`) VALUES
(1, 'Fresh Salmon 500gr', 20.7, 26, 'Meat', 2),
(2, 'Frozen Salmon 1kg', 39.99, 5, 'Meat', 2),
(3, 'Indomie Kaldu Spesial', 0, 100, 'Dry Foods', 3),
(5, 'Super Bihun', 0, 25, 'Dry Foods', 1),
(6, 'Super Bihun', 0, 22, 'Dry Foods', 3),
(7, 'Indomie Kaldu Spesial', 0, 200, 'Dry Foods', 3),
(8, 'Super Bihun', 0, 22, 'Fruit', 7);

-- --------------------------------------------------------

--
-- Table structure for table `invoice`
--

CREATE TABLE `invoice` (
  `invoice_id` int(11) NOT NULL,
  `transaction_date` date NOT NULL,
  `payment_method` varchar(50) NOT NULL,
  `price` double NOT NULL,
  `cashier_id` int(11) NOT NULL,
  `membership_id` int(11) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

--
-- Dumping data for table `invoice`
--

INSERT INTO `invoice` (`invoice_id`, `transaction_date`, `payment_method`, `price`, `cashier_id`, `membership_id`) VALUES
(1, '2022-01-06', 'Debit Card', 241.35, 4, 5),
(2, '2022-01-06', 'Debit Card', 82.8, 4, 5),
(3, '2022-01-07', 'Credit Card', 144.9, 4, NULL);

-- --------------------------------------------------------

--
-- Table structure for table `memberships`
--

CREATE TABLE `memberships` (
  `membership_id` int(11) NOT NULL,
  `first_name` varchar(50) NOT NULL,
  `last_name` varchar(50) NOT NULL,
  `phone_no` varchar(20) NOT NULL,
  `birth_date` date NOT NULL,
  `join_date` date NOT NULL,
  `points` int(11) NOT NULL DEFAULT 0
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

--
-- Dumping data for table `memberships`
--

INSERT INTO `memberships` (`membership_id`, `first_name`, `last_name`, `phone_no`, `birth_date`, `join_date`, `points`) VALUES
(1, 'Dennis', 'Nathanael', '08123456987', '2001-11-05', '2021-12-31', 0),
(2, 'Matthew', 'Kawira', '0812345678', '2002-12-04', '2021-12-31', 0),
(3, 'Someone', 'Doe', '0812345678', '1985-12-16', '2021-12-31', 0),
(5, 'Michael', 'Christopher', '085771736879', '2002-09-10', '2022-01-01', 32);

-- --------------------------------------------------------

--
-- Table structure for table `staff`
--

CREATE TABLE `staff` (
  `staff_id` int(11) NOT NULL,
  `first_name` varchar(50) NOT NULL,
  `last_name` varchar(50) NOT NULL,
  `address` varchar(100) NOT NULL,
  `phone_no` varchar(20) NOT NULL,
  `status` varchar(50) NOT NULL,
  `username` varchar(50) NOT NULL,
  `password` varchar(50) NOT NULL,
  `staff_category_id` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

--
-- Dumping data for table `staff`
--

INSERT INTO `staff` (`staff_id`, `first_name`, `last_name`, `address`, `phone_no`, `status`, `username`, `password`, `staff_category_id`) VALUES
(1, 'Michael', 'Christopher', 'Rawamangun, Jakarta Timur', '085771736879', 'Full Time', 'EuphosX', 'Mc123456', 2),
(2, 'Vania', 'Natalie', 'Jakarta Pusat', '085712345678', 'Full Time', 'vania2', '123456', 1),
(3, 'Angeline', 'Karen', 'Kelapa Gading, Jakarta Utara', '081545678901', 'Part Time', 'angeline', '234567', 3),
(4, 'Dennis', 'Nathanael', 'Belakang MKG', '081234567398', 'Part Time', 'DennisN4', 'DN081234567398', 4),
(5, 'Dennis', 'Nathanael', 'Belakang MKG', '081234567398', 'Part Time', 'DennisN5', 'DN081234567398', 3),
(6, 'Dennis', 'Arifin', 'Belakang MKG', '0835236247', 'Full Time', 'DennisA6', 'DA0835236247', 1),
(7, 'Dennis', 'Arifin', 'Belakang MKG', '0835236247', 'Full Time', 'DennisA7', 'DA0835236247', 1),
(8, 'Refael', 'Arifin', 'Jakarta Barat', '0021837843', 'Full Time', 'RefaelA8', 'RA0021837843', 4);

-- --------------------------------------------------------

--
-- Table structure for table `staffcategory`
--

CREATE TABLE `staffcategory` (
  `staff_category_id` int(11) NOT NULL,
  `category_name` varchar(505) NOT NULL,
  `hourly_salary` float NOT NULL,
  `working_days` varchar(50) NOT NULL,
  `working_hours` varchar(50) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

--
-- Dumping data for table `staffcategory`
--

INSERT INTO `staffcategory` (`staff_category_id`, `category_name`, `hourly_salary`, `working_days`, `working_hours`) VALUES
(1, 'Cashier', 6, 'Weekdays', 'Morning Shift'),
(2, 'Manager', 20, 'Everyday', 'Afternoon Shift'),
(3, 'Cashier', 5, 'Weekend', 'Morning Shift'),
(4, 'Cashier', 8, 'Everyday', 'Morning Shift');

-- --------------------------------------------------------

--
-- Table structure for table `supplier`
--

CREATE TABLE `supplier` (
  `supplier_id` int(11) NOT NULL,
  `supplier_name` varchar(50) NOT NULL,
  `address` varchar(50) NOT NULL,
  `phone_no` varchar(50) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

--
-- Dumping data for table `supplier`
--

INSERT INTO `supplier` (`supplier_id`, `supplier_name`, `address`, `phone_no`) VALUES
(1, 'Indofood', 'Surabaya', '085777778976'),
(2, 'Sibas Sea Factory', 'Semarang', '067857777325'),
(3, 'Wings Food', 'Sumatera Utara', '085777778979'),
(7, 'ABC Food', 'Jawa', '2134353'),
(8, 'DCB Food', 'Jawa', '2134353');

--
-- Indexes for dumped tables
--

--
-- Indexes for table `inventory`
--
ALTER TABLE `inventory`
  ADD PRIMARY KEY (`inventory_id`),
  ADD KEY `suplier_id` (`supplier_id`);

--
-- Indexes for table `invoice`
--
ALTER TABLE `invoice`
  ADD PRIMARY KEY (`invoice_id`),
  ADD KEY `cashier_id` (`cashier_id`),
  ADD KEY `membership_id` (`membership_id`);

--
-- Indexes for table `memberships`
--
ALTER TABLE `memberships`
  ADD PRIMARY KEY (`membership_id`);

--
-- Indexes for table `staff`
--
ALTER TABLE `staff`
  ADD PRIMARY KEY (`staff_id`),
  ADD KEY `staff_category_id` (`staff_category_id`);

--
-- Indexes for table `staffcategory`
--
ALTER TABLE `staffcategory`
  ADD PRIMARY KEY (`staff_category_id`);

--
-- Indexes for table `supplier`
--
ALTER TABLE `supplier`
  ADD PRIMARY KEY (`supplier_id`);

--
-- AUTO_INCREMENT for dumped tables
--

--
-- AUTO_INCREMENT for table `inventory`
--
ALTER TABLE `inventory`
  MODIFY `inventory_id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=9;

--
-- AUTO_INCREMENT for table `invoice`
--
ALTER TABLE `invoice`
  MODIFY `invoice_id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=4;

--
-- AUTO_INCREMENT for table `memberships`
--
ALTER TABLE `memberships`
  MODIFY `membership_id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=6;

--
-- AUTO_INCREMENT for table `staff`
--
ALTER TABLE `staff`
  MODIFY `staff_id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=9;

--
-- AUTO_INCREMENT for table `staffcategory`
--
ALTER TABLE `staffcategory`
  MODIFY `staff_category_id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=5;

--
-- AUTO_INCREMENT for table `supplier`
--
ALTER TABLE `supplier`
  MODIFY `supplier_id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=9;

--
-- Constraints for dumped tables
--

--
-- Constraints for table `inventory`
--
ALTER TABLE `inventory`
  ADD CONSTRAINT `suplier_id` FOREIGN KEY (`supplier_id`) REFERENCES `supplier` (`supplier_id`) ON UPDATE CASCADE;

--
-- Constraints for table `invoice`
--
ALTER TABLE `invoice`
  ADD CONSTRAINT `cashier_id` FOREIGN KEY (`cashier_id`) REFERENCES `staff` (`staff_id`) ON UPDATE CASCADE,
  ADD CONSTRAINT `membership_id` FOREIGN KEY (`membership_id`) REFERENCES `memberships` (`membership_id`) ON UPDATE CASCADE;

--
-- Constraints for table `staff`
--
ALTER TABLE `staff`
  ADD CONSTRAINT `staff_category_id` FOREIGN KEY (`staff_category_id`) REFERENCES `staffcategory` (`staff_category_id`) ON UPDATE CASCADE;
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
