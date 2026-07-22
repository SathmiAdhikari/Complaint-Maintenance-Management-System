-- phpMyAdmin SQL Dump
-- version 5.2.1
-- https://www.phpmyadmin.net/
--
-- Host: 127.0.0.1
-- Generation Time: Jul 22, 2026 at 01:18 PM
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
-- Database: `maintenance_db`
--

-- --------------------------------------------------------

--
-- Table structure for table `complaints`
--

CREATE TABLE `complaints` (
  `complaint_id` bigint(20) NOT NULL,
  `title` varchar(150) NOT NULL,
  `description` text NOT NULL,
  `category` enum('PLUMBING','ELECTRICAL','STRUCTURAL','CLEANING','OTHER') NOT NULL,
  `priority` enum('LOW','MEDIUM','HIGH','URGENT') NOT NULL DEFAULT 'LOW',
  `status` enum('SUBMITTED','ASSIGNED','IN_PROGRESS','COMPLETED','CLOSED') NOT NULL DEFAULT 'SUBMITTED',
  `location` varchar(100) DEFAULT NULL,
  `submitted_by` bigint(20) NOT NULL,
  `assigned_to` bigint(20) DEFAULT NULL,
  `created_at` datetime NOT NULL DEFAULT current_timestamp(),
  `updated_at` datetime NOT NULL DEFAULT current_timestamp() ON UPDATE current_timestamp(),
  `resolved_at` datetime DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

--
-- Dumping data for table `complaints`
--

INSERT INTO `complaints` (`complaint_id`, `title`, `description`, `category`, `priority`, `status`, `location`, `submitted_by`, `assigned_to`, `created_at`, `updated_at`, `resolved_at`) VALUES
(1, 'Leaking pipe in bathroom', 'There is a water leak under the bathroom sink. Water is dripping constantly.', 'PLUMBING', 'HIGH', 'ASSIGNED', 'Block A, Room 204', 2, 3, '2026-07-07 06:54:02', '2026-07-20 01:12:28', NULL),
(2, 'Power outage in apartment', 'The lights and power sockets are not working in the apartment.', 'ELECTRICAL', 'URGENT', 'COMPLETED', 'Block B, Room 305', 3, 16, '2026-07-07 06:54:02', '2026-07-21 22:34:43', '2026-07-21 22:34:43'),
(4, 'Common area cleaning issue', 'The corridor area has not been cleaned properly for several days.', 'CLEANING', 'LOW', 'IN_PROGRESS', 'Block C, Ground Floor', 5, NULL, '2026-07-07 06:54:02', '2026-07-07 06:54:02', NULL),
(5, 'Water heater not working', 'The bathroom water heater is not heating water properly.', 'PLUMBING', 'MEDIUM', 'ASSIGNED', 'Block B, Room 406', 4, NULL, '2026-07-07 06:54:02', '2026-07-07 06:54:02', NULL),
(6, 'Door lock is broken.', 'vvvvv', 'OTHER', 'URGENT', 'ASSIGNED', 'Room', 17, 5, '2026-07-20 01:30:23', '2026-07-20 01:50:57', NULL),
(7, 'Chair is broken', 'ccc', 'OTHER', 'MEDIUM', 'COMPLETED', 'Block C,205', 17, 16, '2026-07-20 01:53:38', '2026-07-20 01:54:36', '2026-07-20 01:54:36'),
(8, 'Leaking a pipe in washroom', '-', 'PLUMBING', 'HIGH', 'ASSIGNED', 'Block B, 205', 18, 3, '2026-07-21 22:32:53', '2026-07-21 22:33:46', NULL),
(9, 'door lock is broken', 'Urgent', 'OTHER', 'HIGH', 'COMPLETED', 'Block B,205', 18, 16, '2026-07-21 22:36:11', '2026-07-21 22:36:49', '2026-07-21 22:36:49');

-- --------------------------------------------------------

--
-- Table structure for table `feedback`
--

CREATE TABLE `feedback` (
  `feedback_id` bigint(20) NOT NULL,
  `complaint_id` bigint(20) NOT NULL,
  `resident_id` bigint(20) NOT NULL,
  `worker_id` bigint(20) NOT NULL,
  `rating` int(11) NOT NULL,
  `comment` text DEFAULT NULL,
  `created_at` datetime NOT NULL DEFAULT current_timestamp()
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

--
-- Dumping data for table `feedback`
--

INSERT INTO `feedback` (`feedback_id`, `complaint_id`, `resident_id`, `worker_id`, `rating`, `comment`, `created_at`) VALUES
(1, 7, 17, 16, 5, 'good service', '2026-07-20 01:55:05'),
(2, 9, 18, 16, 4, '', '2026-07-21 23:17:48');

-- --------------------------------------------------------

--
-- Table structure for table `notifications`
--

CREATE TABLE `notifications` (
  `notification_id` bigint(20) NOT NULL,
  `user_id` bigint(20) NOT NULL,
  `message` text NOT NULL,
  `is_read` tinyint(1) NOT NULL DEFAULT 0,
  `created_at` datetime NOT NULL DEFAULT current_timestamp()
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

--
-- Dumping data for table `notifications`
--

INSERT INTO `notifications` (`notification_id`, `user_id`, `message`, `is_read`, `created_at`) VALUES
(1, 3, 'You have been assigned a new complaint: Leaking pipe in bathroom', 0, '2026-07-20 01:12:28'),
(2, 2, 'Your complaint has been assigned to a worker.', 0, '2026-07-20 01:12:28'),
(3, 5, 'You have been assigned a new complaint: Door lock is broken.', 0, '2026-07-20 01:50:57'),
(4, 17, 'Your complaint has been assigned to a worker.', 0, '2026-07-20 01:50:57'),
(5, 16, 'You have been assigned a new complaint: Chair is broken', 0, '2026-07-20 01:54:04'),
(6, 17, 'Your complaint has been assigned to a worker.', 0, '2026-07-20 01:54:04'),
(7, 17, 'Your complaint status updated to: COMPLETED', 0, '2026-07-20 01:54:36'),
(8, 3, 'You have been assigned a new complaint: Leaking a pipe in washroom', 0, '2026-07-21 22:33:46'),
(9, 18, 'Your complaint has been assigned to a worker.', 0, '2026-07-21 22:33:46'),
(10, 16, 'You have been assigned a new complaint: Power outage in apartment', 0, '2026-07-21 22:34:05'),
(11, 3, 'Your complaint has been assigned to a worker.', 0, '2026-07-21 22:34:05'),
(12, 3, 'Your complaint status updated to: COMPLETED', 0, '2026-07-21 22:34:43'),
(13, 16, 'You have been assigned a new complaint: door lock is broken', 0, '2026-07-21 22:36:26'),
(14, 18, 'Your complaint has been assigned to a worker.', 0, '2026-07-21 22:36:26'),
(15, 18, 'Your complaint status updated to: COMPLETED', 0, '2026-07-21 22:36:49');

-- --------------------------------------------------------

--
-- Table structure for table `resident_profiles`
--

CREATE TABLE `resident_profiles` (
  `profile_id` bigint(20) NOT NULL,
  `user_id` bigint(20) NOT NULL,
  `apartment_number` varchar(20) DEFAULT NULL,
  `block` varchar(20) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

--
-- Dumping data for table `resident_profiles`
--

INSERT INTO `resident_profiles` (`profile_id`, `user_id`, `apartment_number`, `block`) VALUES
(1, 1, '101', 'A'),
(2, 2, '204', 'A'),
(3, 3, '305', 'B'),
(4, 4, '406', 'B'),
(5, 5, '502', 'C'),
(6, 17, '205', 'C'),
(7, 18, '205', 'B');

-- --------------------------------------------------------

--
-- Table structure for table `status_history`
--

CREATE TABLE `status_history` (
  `history_id` bigint(20) NOT NULL,
  `complaint_id` bigint(20) NOT NULL,
  `changed_by` bigint(20) NOT NULL,
  `old_status` enum('SUBMITTED','ASSIGNED','IN_PROGRESS','COMPLETED','CLOSED') DEFAULT NULL,
  `new_status` enum('SUBMITTED','ASSIGNED','IN_PROGRESS','COMPLETED','CLOSED') NOT NULL,
  `remarks` text DEFAULT NULL,
  `changed_at` datetime NOT NULL DEFAULT current_timestamp()
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

--
-- Dumping data for table `status_history`
--

INSERT INTO `status_history` (`history_id`, `complaint_id`, `changed_by`, `old_status`, `new_status`, `remarks`, `changed_at`) VALUES
(1, 1, 15, 'SUBMITTED', 'ASSIGNED', 'Worker assigned by admin', '2026-07-20 01:12:28'),
(2, 6, 17, NULL, 'SUBMITTED', 'Complaint submitted', '2026-07-20 01:30:23'),
(3, 6, 15, 'SUBMITTED', 'ASSIGNED', 'Worker assigned by admin', '2026-07-20 01:50:57'),
(4, 7, 17, NULL, 'SUBMITTED', 'Complaint submitted', '2026-07-20 01:53:38'),
(5, 7, 15, 'SUBMITTED', 'ASSIGNED', 'Worker assigned by admin', '2026-07-20 01:54:04'),
(6, 7, 16, 'ASSIGNED', 'COMPLETED', '', '2026-07-20 01:54:36'),
(7, 8, 18, NULL, 'SUBMITTED', 'Complaint submitted', '2026-07-21 22:32:53'),
(8, 8, 15, 'SUBMITTED', 'ASSIGNED', 'Worker assigned by admin', '2026-07-21 22:33:46'),
(9, 2, 15, 'SUBMITTED', 'ASSIGNED', 'Worker assigned by admin', '2026-07-21 22:34:05'),
(10, 2, 16, 'ASSIGNED', 'COMPLETED', 'done', '2026-07-21 22:34:43'),
(11, 9, 18, NULL, 'SUBMITTED', 'Complaint submitted', '2026-07-21 22:36:11'),
(12, 9, 15, 'SUBMITTED', 'ASSIGNED', 'Worker assigned by admin', '2026-07-21 22:36:26'),
(13, 9, 16, 'ASSIGNED', 'COMPLETED', '', '2026-07-21 22:36:49');

-- --------------------------------------------------------

--
-- Table structure for table `users`
--

CREATE TABLE `users` (
  `user_id` bigint(20) NOT NULL,
  `full_name` varchar(100) NOT NULL,
  `email` varchar(100) NOT NULL,
  `password` varchar(255) NOT NULL,
  `phone_number` varchar(20) DEFAULT NULL,
  `role` enum('RESIDENT','WORKER','ADMIN') NOT NULL,
  `is_active` tinyint(1) NOT NULL DEFAULT 1,
  `created_at` datetime NOT NULL DEFAULT current_timestamp()
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

--
-- Dumping data for table `users`
--

INSERT INTO `users` (`user_id`, `full_name`, `email`, `password`, `phone_number`, `role`, `is_active`, `created_at`) VALUES
(1, 'Himaya Perera', 'dinithihimaya2003@gmail.com', '$2y$10$cMcyJqsJIebUs2jgKueN2uvmLXrWBZT7W/zljwYXL5LVeX2.JMDQK', '0761067031', 'ADMIN', 1, '2026-07-07 06:35:08'),
(2, 'Sathmi', 'sathmi133@gmail.com', '$2y$10$mpmUMAmeBGxidZiOnLYP.eR.Ww3nW2tvfrhOiUFT9caJ9XKyL24ZG', '0761680700', 'ADMIN', 1, '2026-07-07 06:35:08'),
(3, 'Kamal Perera', 'kamal@gmail.com', '$2a$10$e0MY8uK6qYzY9GvK4m8YxO6Z3Xw9pFh5JqR7Lk1Vd2S8Nn4Qm5uXa', '0771234567', 'RESIDENT', 1, '2026-07-07 06:42:55'),
(4, 'Nimal Silva', 'nimal.silva@gmail.com', '$2a$10$w9Xk2Lm5Pq8Vt3Rz6HnYeu1A4cB7dF0gJ5sK8mN2pQ6xL9vT3ZaO', '0712345678', 'RESIDENT', 1, '2026-07-07 06:42:55'),
(5, 'Saman Fernando', 'saman.fernando@gmail.com', '$2a$10$K7mP4xV9nB2qR6sT8dF1gH5jL3zC0wY9uE6iO4aS7pN2vM8qX5Za', '0756789012', 'RESIDENT', 1, '2026-07-07 06:42:55'),
(6, 'Tharindu Jayasinghe', 'tharindu@gmail.com', '$2a$10$R5vN8mK2pL6xQ9sT3dF7gH1jC4zA0wY8uE5iO6bS2nM9qX7vP3Za', '0767890123', 'RESIDENT', 1, '2026-07-07 06:42:55'),
(7, 'Dilshan Perera', 'dilshan.perera@gmail.com', '$2a$10$Y8qL3mN6pR1vT5xK9dF2gH7jC4zA0wS6uE5iO8bM3nQ9vP2xL4Za', '0789012345', 'RESIDENT', 1, '2026-07-07 06:42:55'),
(8, 'Nimal Silva', 'nimal@gmail.com', '$2a$10$w9Xk2Lm5Pq8Vt3Rz6HnYeu1A4cB7dF0gJ5sK8mN2pQ6xL9vT3ZaO', '0787654321', 'WORKER', 1, '2026-07-07 06:52:22'),
(9, 'Kasun Perera', 'kasun.perera@gmail.com', '$2a$10$K7mP4xV9nB2qR6sT8dF1gH5jL3zC0wY9uE6iO4aS7pN2vM8qX5Za', '0774567890', 'WORKER', 1, '2026-07-07 06:52:22'),
(10, 'Ruwan Fernando', 'ruwan.fernando@gmail.com', '$2a$10$R5vN8mK2pL6xQ9sT3dF7gH1jC4zA0wY8uE5iO6bS2nM9qX7vP3Za', '0763456789', 'WORKER', 1, '2026-07-07 06:52:22'),
(11, 'Tharindu Jayasinghe', 'tharindu.worker@gmail.com', '$2a$10$Y8qL3mN6pR1vT5xK9dF2gH7jC4zA0wS6uE5iO8bM3nQ9vP2xL4Za', '0752345678', 'WORKER', 1, '2026-07-07 06:52:22'),
(12, 'Dilshan Kumar', 'dilshan.kumar@gmail.com', '$2a$10$e0MY8uK6qYzY9GvK4m8YxO6Z3Xw9pFh5JqR7Lk1Vd2S8Nn4Qm5uXa', '0791234567', 'WORKER', 1, '2026-07-07 06:52:22'),
(13, 'Dinithi Himaya', 'admin@maintenance.com', '$2a$10$iw3Qk30NVgno3KFT6LdqbeyoUoaX9pTh0LSgo4pbNKqagLbXC.77m', '0761067030', 'ADMIN', 1, '2026-07-08 06:41:24'),
(15, 'Sithara Adhikari', 'admin02@maintenance.com', '$2a$10$iw3Qk30NVgno3KFT6LdqbeyoUoaX9pTh0LSgo4pbNKqagLbXC.77m', '0761067032', 'ADMIN', 1, '2026-07-08 06:57:17'),
(16, 'Piyal Perera', 'piyal@gmail.com', '$2a$10$gdHKaed6wx8midKb4q8p5ejJ38FT5vda65nZz8junVpnp0Jseiw9S', '0772426426', 'WORKER', 1, '2026-07-20 01:18:17'),
(17, 'Manori Indika Kekulawala', 'manori@gmail.com', '$2a$10$PqwO6ovt90bRmTAXOa2a5u0dkK/1xusr.vprHyxVYVStf86NRMaiC', '0770456426', 'RESIDENT', 1, '2026-07-20 01:25:54'),
(18, 'Kaveesha Pathirathna', 'kaveesha@gmail.com', '$2a$10$QWEyJcqxePhfK0SwG0NyMu4OznJOAYoJaRM/Mw78.CdL3xD6dqzru', '0772456456', 'RESIDENT', 1, '2026-07-21 22:31:31');

-- --------------------------------------------------------

--
-- Table structure for table `worker_profiles`
--

CREATE TABLE `worker_profiles` (
  `profile_id` bigint(20) NOT NULL,
  `user_id` bigint(20) NOT NULL,
  `trade` enum('PLUMBER','ELECTRICIAN','CARPENTER','CLEANER','OTHER') NOT NULL,
  `experience_years` int(11) NOT NULL DEFAULT 0,
  `availability_status` enum('AVAILABLE','BUSY') NOT NULL DEFAULT 'AVAILABLE',
  `approval_status` enum('PENDING','APPROVED','REJECTED') NOT NULL DEFAULT 'PENDING',
  `average_rating` decimal(3,2) NOT NULL DEFAULT 0.00,
  `bio` text DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

--
-- Dumping data for table `worker_profiles`
--

INSERT INTO `worker_profiles` (`profile_id`, `user_id`, `trade`, `experience_years`, `availability_status`, `approval_status`, `average_rating`, `bio`) VALUES
(1, 3, 'PLUMBER', 5, 'AVAILABLE', 'APPROVED', 0.00, 'Experienced plumber with 5 years in residential maintenance.'),
(2, 4, 'ELECTRICIAN', 4, 'AVAILABLE', 'APPROVED', 0.00, 'Skilled electrician specializing in home wiring and electrical repairs.'),
(3, 5, 'CARPENTER', 7, 'BUSY', 'APPROVED', 0.00, 'Professional carpenter with experience in furniture repair and woodwork.'),
(4, 6, 'CLEANER', 3, 'AVAILABLE', 'APPROVED', 0.00, 'Reliable cleaner experienced in residential and apartment cleaning services.'),
(5, 7, 'OTHER', 2, '', 'PENDING', 0.00, 'General maintenance worker with experience in various repair tasks.'),
(6, 16, 'CARPENTER', 11, 'AVAILABLE', 'APPROVED', 4.50, 'My hope is give efficient and reliable service.');

--
-- Indexes for dumped tables
--

--
-- Indexes for table `complaints`
--
ALTER TABLE `complaints`
  ADD PRIMARY KEY (`complaint_id`),
  ADD KEY `idx_complaints_submitted_by` (`submitted_by`),
  ADD KEY `idx_complaints_assigned_to` (`assigned_to`),
  ADD KEY `idx_complaints_status` (`status`),
  ADD KEY `idx_complaints_category` (`category`);

--
-- Indexes for table `feedback`
--
ALTER TABLE `feedback`
  ADD PRIMARY KEY (`feedback_id`),
  ADD UNIQUE KEY `uq_feedback_complaint` (`complaint_id`),
  ADD KEY `fk_feedback_resident` (`resident_id`),
  ADD KEY `fk_feedback_worker` (`worker_id`);

--
-- Indexes for table `notifications`
--
ALTER TABLE `notifications`
  ADD PRIMARY KEY (`notification_id`),
  ADD KEY `idx_notifications_user_read` (`user_id`,`is_read`);

--
-- Indexes for table `resident_profiles`
--
ALTER TABLE `resident_profiles`
  ADD PRIMARY KEY (`profile_id`),
  ADD UNIQUE KEY `uq_resident_user` (`user_id`);

--
-- Indexes for table `status_history`
--
ALTER TABLE `status_history`
  ADD PRIMARY KEY (`history_id`),
  ADD KEY `fk_history_user` (`changed_by`),
  ADD KEY `idx_status_history_complaint` (`complaint_id`);

--
-- Indexes for table `users`
--
ALTER TABLE `users`
  ADD PRIMARY KEY (`user_id`),
  ADD UNIQUE KEY `uq_users_email` (`email`);

--
-- Indexes for table `worker_profiles`
--
ALTER TABLE `worker_profiles`
  ADD PRIMARY KEY (`profile_id`),
  ADD UNIQUE KEY `uq_worker_user` (`user_id`),
  ADD KEY `idx_worker_approval` (`approval_status`);

--
-- AUTO_INCREMENT for dumped tables
--

--
-- AUTO_INCREMENT for table `complaints`
--
ALTER TABLE `complaints`
  MODIFY `complaint_id` bigint(20) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=10;

--
-- AUTO_INCREMENT for table `feedback`
--
ALTER TABLE `feedback`
  MODIFY `feedback_id` bigint(20) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=3;

--
-- AUTO_INCREMENT for table `notifications`
--
ALTER TABLE `notifications`
  MODIFY `notification_id` bigint(20) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=16;

--
-- AUTO_INCREMENT for table `resident_profiles`
--
ALTER TABLE `resident_profiles`
  MODIFY `profile_id` bigint(20) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=8;

--
-- AUTO_INCREMENT for table `status_history`
--
ALTER TABLE `status_history`
  MODIFY `history_id` bigint(20) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=14;

--
-- AUTO_INCREMENT for table `users`
--
ALTER TABLE `users`
  MODIFY `user_id` bigint(20) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=19;

--
-- AUTO_INCREMENT for table `worker_profiles`
--
ALTER TABLE `worker_profiles`
  MODIFY `profile_id` bigint(20) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=7;

--
-- Constraints for dumped tables
--

--
-- Constraints for table `complaints`
--
ALTER TABLE `complaints`
  ADD CONSTRAINT `fk_complaint_resident` FOREIGN KEY (`submitted_by`) REFERENCES `users` (`user_id`) ON UPDATE CASCADE,
  ADD CONSTRAINT `fk_complaint_worker` FOREIGN KEY (`assigned_to`) REFERENCES `users` (`user_id`) ON DELETE SET NULL ON UPDATE CASCADE;

--
-- Constraints for table `feedback`
--
ALTER TABLE `feedback`
  ADD CONSTRAINT `fk_feedback_complaint` FOREIGN KEY (`complaint_id`) REFERENCES `complaints` (`complaint_id`) ON DELETE CASCADE ON UPDATE CASCADE,
  ADD CONSTRAINT `fk_feedback_resident` FOREIGN KEY (`resident_id`) REFERENCES `users` (`user_id`) ON UPDATE CASCADE,
  ADD CONSTRAINT `fk_feedback_worker` FOREIGN KEY (`worker_id`) REFERENCES `users` (`user_id`) ON UPDATE CASCADE;

--
-- Constraints for table `notifications`
--
ALTER TABLE `notifications`
  ADD CONSTRAINT `fk_notification_user` FOREIGN KEY (`user_id`) REFERENCES `users` (`user_id`) ON DELETE CASCADE ON UPDATE CASCADE;

--
-- Constraints for table `resident_profiles`
--
ALTER TABLE `resident_profiles`
  ADD CONSTRAINT `fk_resident_user` FOREIGN KEY (`user_id`) REFERENCES `users` (`user_id`) ON DELETE CASCADE ON UPDATE CASCADE;

--
-- Constraints for table `status_history`
--
ALTER TABLE `status_history`
  ADD CONSTRAINT `fk_history_complaint` FOREIGN KEY (`complaint_id`) REFERENCES `complaints` (`complaint_id`) ON DELETE CASCADE ON UPDATE CASCADE,
  ADD CONSTRAINT `fk_history_user` FOREIGN KEY (`changed_by`) REFERENCES `users` (`user_id`) ON UPDATE CASCADE;

--
-- Constraints for table `worker_profiles`
--
ALTER TABLE `worker_profiles`
  ADD CONSTRAINT `fk_worker_user` FOREIGN KEY (`user_id`) REFERENCES `users` (`user_id`) ON DELETE CASCADE ON UPDATE CASCADE;
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
