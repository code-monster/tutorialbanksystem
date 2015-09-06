-- phpMyAdmin SQL Dump
-- version 4.0.10deb1
-- http://www.phpmyadmin.net
--
-- Хост: localhost
-- Время создания: Сен 06 2015 г., 05:34
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
  `operation` text,
  `date` date DEFAULT NULL,
  `money` double NOT NULL,
  PRIMARY KEY (`transactions_id`),
  KEY `FK_transactions` (`account_id`)
) ENGINE=InnoDB  DEFAULT CHARSET=utf8 AUTO_INCREMENT=7 ;

--
-- Дамп данных таблицы `transactions`
--

INSERT INTO `transactions` (`transactions_id`, `account_id`, `operation`, `date`, `money`) VALUES
(3, 14, 'buy products ', '2015-08-18', 55),
(6, 14, 'presents for father', '2015-01-12', 0);

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
) ENGINE=InnoDB  DEFAULT CHARSET=utf8 AUTO_INCREMENT=9 ;

--
-- Дамп данных таблицы `users`
--

INSERT INTO `users` (`id`, `firstname`, `lastname`, `address`, `dob`) VALUES
(4, 'misha', 'fdfdf', 'dfdfdf', '1969-03-11'),
(5, 'иван', 'иванов', 'полськая 3', '1984-06-13'),
(7, 'fine', 'ok', 'ok 67', '2001-11-12'),
(8, '899', '9879', '98790', '2002-01-01');

-- --------------------------------------------------------

--
-- Структура таблицы `users_accounts`
--

CREATE TABLE IF NOT EXISTS `users_accounts` (
  `account_id` int(11) NOT NULL AUTO_INCREMENT,
  `user_id` int(11) DEFAULT NULL,
  PRIMARY KEY (`account_id`),
  KEY `FK_users_accounts` (`user_id`)
) ENGINE=InnoDB  DEFAULT CHARSET=utf8 AUTO_INCREMENT=33 ;

--
-- Дамп данных таблицы `users_accounts`
--

INSERT INTO `users_accounts` (`account_id`, `user_id`) VALUES
(14, 4),
(23, 4),
(24, 4),
(27, 4),
(28, 4),
(29, 4),
(18, 5),
(19, 5),
(20, 5),
(21, 5),
(30, 5),
(31, 5),
(32, 5),
(25, 7),
(26, 7);

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
