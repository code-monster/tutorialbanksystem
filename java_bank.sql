-- phpMyAdmin SQL Dump
-- version 4.0.10deb1
-- http://www.phpmyadmin.net
--
-- Хост: localhost
-- Время создания: Июл 26 2015 г., 02:05
-- Версия сервера: 5.5.43-0ubuntu0.14.04.1
-- Версия PHP: 5.5.9-1ubuntu4.11

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8 */;

--
-- База данных: `java_bank`
--

-- --------------------------------------------------------

--
-- Структура таблицы `transactions`
--

CREATE TABLE IF NOT EXISTS `transactions` (
  `transactions_id` int(11) NOT NULL AUTO_INCREMENT,
  `account_id` int(11) DEFAULT NULL,
  `account_current_operation_id` int(11) DEFAULT NULL,
  `money` decimal(10,0) DEFAULT NULL,
  `date` datetime DEFAULT NULL,
  `self_operations` tinyint(1) DEFAULT NULL,
  UNIQUE KEY `id_transactions` (`transactions_id`)
) ENGINE=InnoDB  DEFAULT CHARSET=utf8 AUTO_INCREMENT=2 ;

--
-- Дамп данных таблицы `transactions`
--

INSERT INTO `transactions` (`transactions_id`, `account_id`, `account_current_operation_id`, `money`, `date`, `self_operations`) VALUES
(1, 1, 1, 200, '2015-06-20 01:00:00', 0);

-- --------------------------------------------------------

--
-- Структура таблицы `users`
--

CREATE TABLE IF NOT EXISTS `users` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `firstname` char(50) NOT NULL,
  `lastname` char(50) NOT NULL,
  `address` char(100) NOT NULL,
  `dob` date DEFAULT NULL,
  UNIQUE KEY `id` (`id`)
) ENGINE=InnoDB  DEFAULT CHARSET=utf8 AUTO_INCREMENT=21 ;

--
-- Дамп данных таблицы `users`
--

INSERT INTO `users` (`id`, `firstname`, `lastname`, `address`, `dob`) VALUES
(18, 'миша', '54rere', 'ппп 66', '1983-01-04'),
(19, '6576', '765878', '8768', '9999-09-17'),
(20, '545', '56546', '676', '2000-12-09');

-- --------------------------------------------------------

--
-- Структура таблицы `users_accounts`
--

CREATE TABLE IF NOT EXISTS `users_accounts` (
  `account_id` int(11) NOT NULL AUTO_INCREMENT,
  `user_id` int(11) DEFAULT NULL,
  `balance` decimal(10,0) DEFAULT NULL,
  UNIQUE KEY `id_account` (`account_id`)
) ENGINE=InnoDB  DEFAULT CHARSET=utf8 AUTO_INCREMENT=42 ;

--
-- Дамп данных таблицы `users_accounts`
--

INSERT INTO `users_accounts` (`account_id`, `user_id`, `balance`) VALUES
(1, 1, 1000),
(9, 17, 0),
(11, 17, 0),
(14, 16, 0),
(15, 16, 0),
(16, 16, 0),
(17, 16, 0),
(18, 16, 0),
(19, 16, 0),
(20, 16, 0),
(21, 16, 0),
(22, 16, 0),
(25, 16, 0),
(26, 18, 0),
(27, 18, 0),
(32, 18, 0),
(34, 19, 0),
(35, 19, 0),
(38, 20, 0),
(39, 20, 0),
(40, 20, 0),
(41, 20, 0);

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
