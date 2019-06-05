-- phpMyAdmin SQL Dump
-- version 4.8.5
-- https://www.phpmyadmin.net/
--
-- Host: 127.0.0.1
-- Generation Time: 05-Jun-2019 às 04:12
-- Versão do servidor: 10.1.38-MariaDB
-- versão do PHP: 7.3.4

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
SET AUTOCOMMIT = 0;
START TRANSACTION;
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Database: `revendaveiculos`
--

-- --------------------------------------------------------

--
-- Estrutura da tabela `clientes`
--

CREATE TABLE `clientes` (
  `id` int(11) NOT NULL,
  `nome` varchar(100) DEFAULT NULL,
  `cpf` varchar(20) DEFAULT NULL,
  `telefone` varchar(30) DEFAULT NULL,
  `endereco` varchar(100) DEFAULT NULL,
  `sexo` varchar(30) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Extraindo dados da tabela `clientes`
--

INSERT INTO `clientes` (`id`, `nome`, `cpf`, `telefone`, `endereco`, `sexo`) VALUES
(1, 'Felipe Silva Ribeiro', '07577538337', '85997969128', 'Rua humberto de campus 55', 'Masculino'),
(2, 'Rafael Laurindo', '0465765135', '85997969128', 'Rua das goiaberias 105', 'Masculino'),
(15, 'Fabiano Fontenelle', '50205430040', '97969128', 'Rua do Ordones', 'Masculino');

-- --------------------------------------------------------

--
-- Estrutura da tabela `veiculos`
--

CREATE TABLE `veiculos` (
  `id` int(11) NOT NULL,
  `nome` varchar(50) DEFAULT NULL,
  `marca` varchar(100) DEFAULT NULL,
  `cor` varchar(50) DEFAULT NULL,
  `alugado_id` int(11) DEFAULT NULL,
  `ano` varchar(30) DEFAULT NULL,
  `preco` varchar(100) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Extraindo dados da tabela `veiculos`
--

INSERT INTO `veiculos` (`id`, `nome`, `marca`, `cor`, `alugado_id`, `ano`, `preco`) VALUES
(2, 'Punto', 'Branco', 'Fiat', 2, '2012', '15000'),
(6, 'Compass', 'Jeep', 'Azul', 1, '2012', '15000'),
(9, 'Compass', 'Jeep', 'Azul', 15, '2012', '15000'),
(11, 'Celta', 'Fiat', 'branco', NULL, '2012', '15000'),
(13, 'Celta', 'Fiat', 'branco', 2, '2007', '15000'),
(14, 'Celta', 'Fiat', 'branco', 2, '2012', '15000'),
(15, 'Celta', 'Fiat', 'branco', 15, '2008', '15000'),
(16, 'Celta', 'Fiat', 'branco', NULL, '2012', '15000'),
(17, 'Celta', 'Fiat', 'branco', 15, '2010', '15000'),
(18, 'Celta', 'Fiat', 'branco', NULL, '2000', '15000'),
(21, 'Celta', 'Fiat', 'branco', NULL, '2020', '15000'),
(23, 'Celta', 'Fiat', 'branco', 1, '2009', '15000'),
(24, 'Celta', 'Fiat', 'branco', NULL, '2010', '15000'),
(25, 'Celta', 'Fiat', 'branco', NULL, '2000', '15000'),
(33, 'Uno', 'Azul', 'Fiat', 15, '210', '200'),
(34, 'Punto', 'Preto', 'Fiat', 2, '2017', '16000');

--
-- Indexes for dumped tables
--

--
-- Indexes for table `clientes`
--
ALTER TABLE `clientes`
  ADD PRIMARY KEY (`id`);

--
-- Indexes for table `veiculos`
--
ALTER TABLE `veiculos`
  ADD PRIMARY KEY (`id`),
  ADD KEY `on_delete_alugado_id` (`alugado_id`);

--
-- AUTO_INCREMENT for dumped tables
--

--
-- AUTO_INCREMENT for table `clientes`
--
ALTER TABLE `clientes`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=16;

--
-- AUTO_INCREMENT for table `veiculos`
--
ALTER TABLE `veiculos`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=35;

--
-- Constraints for dumped tables
--

--
-- Limitadores para a tabela `veiculos`
--
ALTER TABLE `veiculos`
  ADD CONSTRAINT `on_delete_alugado_id` FOREIGN KEY (`alugado_id`) REFERENCES `clientes` (`id`) ON DELETE CASCADE,
  ADD CONSTRAINT `veiculos_ibfk_1` FOREIGN KEY (`alugado_id`) REFERENCES `clientes` (`id`);
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
