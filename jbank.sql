-- phpMyAdmin SQL Dump
-- version 4.0.10deb1
-- http://www.phpmyadmin.net
--
-- Хост: localhost
-- Время создания: Авг 01 2015 г., 15:55
-- Версия сервера: 5.5.44-0ubuntu0.14.04.1
-- Версия PHP: 5.5.9-1ubuntu4.11

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8 */;

--
-- База данных: `jbank`
--

-- --------------------------------------------------------

--
-- Структура таблицы `transactions`
--

CREATE TABLE IF NOT EXISTS `transactions` (
  `transactions_id` int(11) NOT NULL AUTO_INCREMENT,
  `account_id` int(11) DEFAULT NULL,
  `money` decimal(10,0) DEFAULT NULL,
  `operation` text,
  `date` date DEFAULT NULL,
  PRIMARY KEY (`transactions_id`),
  KEY `FK_transactions` (`account_id`)
) ENGINE=InnoDB  DEFAULT CHARSET=utf8 AUTO_INCREMENT=7 ;

--
-- Дамп данных таблицы `transactions`
--

INSERT INTO `transactions` (`transactions_id`, `account_id`, `money`, `operation`, `date`) VALUES
(1, 13, 4535, 'add money in bank ', '2015-08-18'),
(2, 13, 5, 'add money in bank ', '2015-08-18'),
(3, 14, -50, 'buy products ', '2015-08-18'),
(4, 13, -500, 'buy computer ', '2015-01-12'),
(5, 13, -50, 'buy products for lanch', '2015-08-18'),
(6, 14, -5648, 'presents for father', '2015-01-12');

-- --------------------------------------------------------

--
-- Структура таблицы `users`
--

CREATE TABLE IF NOT EXISTS `users` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `firstname` char(50) DEFAULT NULL,
  `lastname` char(50) DEFAULT NULL,
  `address` char(100) DEFAULT NULL,
  `dob` date DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB  DEFAULT CHARSET=utf8 AUTO_INCREMENT=5 ;

--
-- Дамп данных таблицы `users`
--

INSERT INTO `users` (`id`, `firstname`, `lastname`, `address`, `dob`) VALUES
(3, '456', '67687', '64567', '1992-12-07'),
(4, 'misha', 'fdfdf', 'dfdfdf', '1969-06-07');

-- --------------------------------------------------------

--
-- Структура таблицы `users_accounts`
--

CREATE TABLE IF NOT EXISTS `users_accounts` (
  `account_id` int(11) NOT NULL AUTO_INCREMENT,
  `user_id` int(11) DEFAULT NULL,
  `balance` decimal(10,0) DEFAULT NULL,
  PRIMARY KEY (`account_id`),
  KEY `FK_users_accounts` (`user_id`)
) ENGINE=InnoDB  DEFAULT CHARSET=utf8 AUTO_INCREMENT=15 ;

--
-- Дамп данных таблицы `users_accounts`
--

INSERT INTO `users_accounts` (`account_id`, `user_id`, `balance`) VALUES
(13, 3, 0),
(14, 4, 0);

--
-- Ограничения внешнего ключа сохраненных таблиц
--

--
-- Ограничения внешнего ключа таблицы `transactions`
--
ALTER TABLE `transactions`
  ADD CONSTRAINT `FK_transactions` FOREIGN KEY (`account_id`) REFERENCES `users_accounts` (`account_id`) ON DELETE CASCADE ON UPDATE CASCADE;

--
-- Ограничения внешнего ключа таблицы `users_accounts`
--
ALTER TABLE `users_accounts`
  ADD CONSTRAINT `FK_users_accounts` FOREIGN KEY (`user_id`) REFERENCES `users` (`id`) ON DELETE CASCADE ON UPDATE CASCADE;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
