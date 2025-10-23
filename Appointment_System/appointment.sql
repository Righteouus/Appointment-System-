-- phpMyAdmin SQL Dump
-- version 5.2.1
-- https://www.phpmyadmin.net/
--
-- Host: 127.0.0.1
-- Generation Time: Oct 22, 2025 at 05:06 AM
-- Server version: 10.4.32-MariaDB
-- PHP Version: 8.2.12

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
START TRANSACTION;
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Database: `appointment`
--

-- --------------------------------------------------------

--
-- Table structure for table `appointment_date`
--

CREATE TABLE `appointment_date` (
  `AppointmentID` int(11) NOT NULL,
  `Patient_ID` int(11) NOT NULL,
  `DoctorID` int(11) NOT NULL,
  `Date` date NOT NULL,
  `Time` varchar(20) NOT NULL,
  `Drname` varchar(100) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dumping data for table `appointment_date`
--

INSERT INTO `appointment_date` (`AppointmentID`, `Patient_ID`, `DoctorID`, `Date`, `Time`, `Drname`) VALUES
(3, 24, 0, '2025-10-23', '10:30 am', 'Dr bert'),
(4, 25, 0, '2025-11-28', '4:30 pm', 'Dr dogs'),
(5, 26, 0, '2025-10-22', '9:00 am', 'Dr drey'),
(6, 27, 0, '2025-10-22', '10:30 am', 'Dr drey'),
(7, 28, 0, '2025-10-30', '2:30 pm', 'Dr bert'),
(8, 29, 0, '2025-10-23', '3:30 pm', 'Dr dogs');

-- --------------------------------------------------------

--
-- Table structure for table `patient_info`
--

CREATE TABLE `patient_info` (
  `Patient_ID` int(11) NOT NULL,
  `First_name` varchar(100) NOT NULL,
  `Last_name` varchar(100) NOT NULL,
  `Middle_name` varchar(100) NOT NULL,
  `Gmail` varchar(100) NOT NULL,
  `PhoneNumber` varchar(20) NOT NULL,
  `Date_of_Birth` varchar(50) NOT NULL,
  `Age` int(20) NOT NULL,
  `Gender` varchar(50) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dumping data for table `patient_info`
--

INSERT INTO `patient_info` (`Patient_ID`, `First_name`, `Last_name`, `Middle_name`, `Gmail`, `PhoneNumber`, `Date_of_Birth`, `Age`, `Gender`) VALUES
(21, 'Kayce', 'Saromines', 'E', 'Kayce@gmail.com', '0999999999999', '2006-11-28', 19, 'Female'),
(22, 'Kayce', 'Saromines', 'E', 'Kayce@gmail.com', '0999999999', '2006-11-28', 19, 'Female'),
(23, 'Kayce', 'Saromines', 'E', 'Kayce@gmail.com', '099999', '2025-11-28', 19, 'Female'),
(24, 'ghgf', 'ghfg', 'h', 'ghgff', '445', '2025-10-14', 2, 'Female'),
(25, 'jhkjhg', 'grd', 'e', 'tre', '3', '2025-10-16', 3, 'Female'),
(26, 'Jason', 'Japlag', 'D', 'Jason@gmailcom', '09368226323', '2006-05-25', 6, 'Male	'),
(27, 'Kayce', 'Saromines', 'E', 'kayce@gmail.com', '0999', '2025-11-28', 4, 'Female'),
(28, 'Charlie', 'Viador', 'B', 'CHARLIE@GMAIL.COM', '5445', '2025-10-14', 2, 'Male	'),
(29, 'sfsd', 'sdfsd', 'fd', 'fdf', '5656', '2025-10-07', 6, 'Male	'),
(30, 'GFDFFDG', 'GFG', 'GG', 'GFGF', '44', '2025-10-01', 4, 'Male	'),
(31, 'DFD', 'DF', 'F', 'DFD', '3', '2025-10-09', 2, 'Male	'),
(32, 'TIMBANG', 'timbang', 't', 'timbang@gmail.com', '765657', '2025-10-07', 2, 'Female'),
(33, 'fgfd', 'fgfg', 'f', 'fgf', '4', '2025-10-02', 3, 'Female'),
(34, 'gdfrdf', 'fdgfgd', 'g', 'gffd', '3', '2025-10-16', 2, 'Female'),
(35, 'dfgfd', 'fgfg', 'rdf', 'sdds', '34', '2025-10-13', 2, 'Female'),
(36, 'sfdfds', 'dfdf', 'f', 'dfd', '34', '2025-10-07', 3, 'Male	'),
(37, 'Jason', 'Japlag', 'D', 'jason@gmail.com', '099999', '2025-05-25', 3, 'Male	');

-- --------------------------------------------------------

--
-- Table structure for table `tbl_doctor`
--

CREATE TABLE `tbl_doctor` (
  `DoctorID` int(11) NOT NULL,
  `Dr_name` varchar(100) NOT NULL,
  `Specialty` varchar(100) NOT NULL,
  `Contact_number` varchar(100) NOT NULL,
  `Status` varchar(100) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dumping data for table `tbl_doctor`
--

INSERT INTO `tbl_doctor` (`DoctorID`, `Dr_name`, `Specialty`, `Contact_number`, `Status`) VALUES
(1, 'Jason', 'Cardiology', '767676', 'Inactive'),
(2, 'fghg', 'Family medicine', '434', 'Inactive'),
(3, 'Charlie', 'Cardiology', '099999', 'Inactive'),
(4, 'fdssdf', 'Surgery', '3333', 'Inactive'),
(5, 'gggg', 'Cardiology', '3343', 'Inactive'),
(6, 'rtyut', 'Cardiology', '5465', 'Active'),
(7, 'dfd', 'Cardiology', '223', 'Active'),
(8, 'hhhhhh', 'Surgery', '888', 'Active'),
(9, 'JJJasopn', 'Surgery', '3243', 'Active');

--
-- Indexes for dumped tables
--

--
-- Indexes for table `appointment_date`
--
ALTER TABLE `appointment_date`
  ADD PRIMARY KEY (`AppointmentID`),
  ADD KEY `FK_patientID` (`Patient_ID`);

--
-- Indexes for table `patient_info`
--
ALTER TABLE `patient_info`
  ADD PRIMARY KEY (`Patient_ID`);

--
-- Indexes for table `tbl_doctor`
--
ALTER TABLE `tbl_doctor`
  ADD PRIMARY KEY (`DoctorID`);

--
-- AUTO_INCREMENT for dumped tables
--

--
-- AUTO_INCREMENT for table `appointment_date`
--
ALTER TABLE `appointment_date`
  MODIFY `AppointmentID` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=9;

--
-- AUTO_INCREMENT for table `patient_info`
--
ALTER TABLE `patient_info`
  MODIFY `Patient_ID` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=38;

--
-- AUTO_INCREMENT for table `tbl_doctor`
--
ALTER TABLE `tbl_doctor`
  MODIFY `DoctorID` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=10;

--
-- Constraints for dumped tables
--

--
-- Constraints for table `appointment_date`
--
ALTER TABLE `appointment_date`
  ADD CONSTRAINT `FK_patientID` FOREIGN KEY (`Patient_ID`) REFERENCES `patient_info` (`Patient_ID`) ON DELETE CASCADE ON UPDATE CASCADE;
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
