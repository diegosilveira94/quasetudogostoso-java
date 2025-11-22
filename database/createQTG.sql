-- MySQL Script - Quase Tudo Gostoso com Dados de Teste
-- Gerado automaticamente pelo MySQL Workbench
-- Adaptado com dados fictícios para testes

SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0;
SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0;
SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='ONLY_FULL_GROUP_BY,STRICT_TRANS_TABLES,NO_ZERO_IN_DATE,NO_ZERO_DATE,ERROR_FOR_DIVISION_BY_ZERO,NO_ENGINE_SUBSTITUTION';

-- -----------------------------------------------------
-- Schema quasetudogostoso
-- -----------------------------------------------------
CREATE SCHEMA IF NOT EXISTS `quasetudogostoso` DEFAULT CHARACTER SET utf8mb4 ;
USE `quasetudogostoso` ;

-- -----------------------------------------------------
-- Table `quasetudogostoso`.`usuario`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `quasetudogostoso`.`usuario` ;

CREATE TABLE IF NOT EXISTS `quasetudogostoso`.`usuario` (
  `idusuario` INT UNSIGNED NOT NULL AUTO_INCREMENT,
  `nome` VARCHAR(60) NOT NULL,
  `email` VARCHAR(80) NOT NULL,
  `data_nascimento` DATE NULL,
  `cep` INT NULL,
  `genero` TINYINT NULL,
  `senha` VARCHAR(300) NOT NULL,
  `salt` VARCHAR(600) NOT NULL,
  `inscrito` DATETIME NOT NULL DEFAULT current_timestamp(),
  `uuid` VARCHAR(80) NULL,
  PRIMARY KEY (`idusuario`),
  UNIQUE INDEX `idusuario_UNIQUE` (`idusuario` ASC),
  UNIQUE INDEX `email_UNIQUE` (`email` ASC))
ENGINE = InnoDB;

-- -----------------------------------------------------
-- Table `quasetudogostoso`.`preparo`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `quasetudogostoso`.`preparo` ;

CREATE TABLE IF NOT EXISTS `quasetudogostoso`.`preparo` (
  `idpreparo` INT UNSIGNED NOT NULL AUTO_INCREMENT,
  `modo_preparo` LONGTEXT NOT NULL,
  `urlvideo` VARCHAR(200) NULL,
  `tempopreparo` TIME NOT NULL,
  PRIMARY KEY (`idpreparo`),
  UNIQUE INDEX `idpreparo_UNIQUE` (`idpreparo` ASC))
ENGINE = InnoDB;

-- -----------------------------------------------------
-- Table `quasetudogostoso`.`dificuldade`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `quasetudogostoso`.`dificuldade` ;

CREATE TABLE IF NOT EXISTS `quasetudogostoso`.`dificuldade` (
  `iddificuldade` INT UNSIGNED NOT NULL AUTO_INCREMENT,
  `dificuldade` VARCHAR(45) NOT NULL,
  PRIMARY KEY (`iddificuldade`),
  UNIQUE INDEX `iddificuldade_UNIQUE` (`iddificuldade` ASC))
ENGINE = InnoDB;

-- -----------------------------------------------------
-- Table `quasetudogostoso`.`custo`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `quasetudogostoso`.`custo` ;

CREATE TABLE IF NOT EXISTS `quasetudogostoso`.`custo` (
  `idcusto` INT NOT NULL,
  `custo` VARCHAR(45) NOT NULL,
  PRIMARY KEY (`idcusto`))
ENGINE = InnoDB;

-- -----------------------------------------------------
-- Table `quasetudogostoso`.`receita`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `quasetudogostoso`.`receita` ;

CREATE TABLE IF NOT EXISTS `quasetudogostoso`.`receita` (
  `idreceita` INT UNSIGNED NOT NULL AUTO_INCREMENT,
  `titulo` VARCHAR(100) NOT NULL,
  `descricao` MEDIUMTEXT NULL,
  `imagem` LONGBLOB NULL,
  `cadastro_idusuario` INT UNSIGNED NOT NULL,
  `preparo_idpreparo` INT UNSIGNED NOT NULL,
  `dificuldade_iddificuldade` INT UNSIGNED NOT NULL,
  `custo_idcusto` INT NOT NULL,
  PRIMARY KEY (`idreceita`),
  UNIQUE INDEX `idreceita_UNIQUE` (`idreceita` ASC),
  INDEX `fk_receita_usuario1_idx` (`cadastro_idusuario` ASC),
  INDEX `fk_receita_preparo1_idx` (`preparo_idpreparo` ASC),
  INDEX `fk_receita_dificuldade1_idx` (`dificuldade_iddificuldade` ASC),
  INDEX `fk_receita_custo1_idx` (`custo_idcusto` ASC),
  CONSTRAINT `fk_receita_usuario1`
    FOREIGN KEY (`cadastro_idusuario`)
    REFERENCES `quasetudogostoso`.`usuario` (`idusuario`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_receita_preparo1`
    FOREIGN KEY (`preparo_idpreparo`)
    REFERENCES `quasetudogostoso`.`preparo` (`idpreparo`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_receita_dificuldade1`
    FOREIGN KEY (`dificuldade_iddificuldade`)
    REFERENCES `quasetudogostoso`.`dificuldade` (`iddificuldade`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_receita_custo1`
    FOREIGN KEY (`custo_idcusto`)
    REFERENCES `quasetudogostoso`.`custo` (`idcusto`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;

-- -----------------------------------------------------
-- Table `quasetudogostoso`.`categoria`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `quasetudogostoso`.`categoria` ;

CREATE TABLE IF NOT EXISTS `quasetudogostoso`.`categoria` (
  `idcategoria` INT UNSIGNED NOT NULL AUTO_INCREMENT,
  `categoria` VARCHAR(80) NOT NULL,
  `ativo` TINYINT UNSIGNED NOT NULL DEFAULT 1,
  PRIMARY KEY (`idcategoria`),
  UNIQUE INDEX `idcategoria_UNIQUE` (`idcategoria` ASC),
  UNIQUE INDEX `categoria_UNIQUE` (`categoria` ASC))
ENGINE = InnoDB;

-- -----------------------------------------------------
-- Table `quasetudogostoso`.`categoria_receita`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `quasetudogostoso`.`categoria_receita` ;

CREATE TABLE IF NOT EXISTS `quasetudogostoso`.`categoria_receita` (
  `receita_idreceita` INT UNSIGNED NOT NULL,
  `categoria_idcategoria` INT UNSIGNED NOT NULL,
  PRIMARY KEY (`receita_idreceita`, `categoria_idcategoria`),
  INDEX `fk_receita_has_categoria_categoria1_idx` (`categoria_idcategoria` ASC),
  INDEX `fk_receita_has_categoria_receita_idx` (`receita_idreceita` ASC),
  CONSTRAINT `fk_receita_has_categoria_receita`
    FOREIGN KEY (`receita_idreceita`)
    REFERENCES `quasetudogostoso`.`receita` (`idreceita`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_receita_has_categoria_categoria1`
    FOREIGN KEY (`categoria_idcategoria`)
    REFERENCES `quasetudogostoso`.`categoria` (`idcategoria`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;

-- -----------------------------------------------------
-- Table `quasetudogostoso`.`comentario`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `quasetudogostoso`.`comentario` ;

CREATE TABLE IF NOT EXISTS `quasetudogostoso`.`comentario` (
  `idcomentario` INT UNSIGNED NOT NULL AUTO_INCREMENT,
  `receita_idreceita` INT UNSIGNED NOT NULL,
  `usuario_idusuario` INT UNSIGNED NOT NULL,
  `comentario` LONGTEXT NULL,
  `nota` TINYINT NOT NULL,
  `datacomentario` DATETIME NULL DEFAULT current_timestamp(),
  PRIMARY KEY (`idcomentario`),
  INDEX `fk_receita_has_usuario_usuario1_idx` (`usuario_idusuario` ASC),
  INDEX `fk_receita_has_usuario_receita1_idx` (`receita_idreceita` ASC),
  UNIQUE INDEX `idcomentario_UNIQUE` (`idcomentario` ASC),
  CONSTRAINT `fk_receita_has_usuario_receita1`
    FOREIGN KEY (`receita_idreceita`)
    REFERENCES `quasetudogostoso`.`receita` (`idreceita`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_receita_has_usuario_usuario1`
    FOREIGN KEY (`usuario_idusuario`)
    REFERENCES `quasetudogostoso`.`usuario` (`idusuario`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;

-- -----------------------------------------------------
-- Table `quasetudogostoso`.`ingrediente`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `quasetudogostoso`.`ingrediente` ;

CREATE TABLE IF NOT EXISTS `quasetudogostoso`.`ingrediente` (
  `idingrediente` INT UNSIGNED NOT NULL AUTO_INCREMENT,
  `ingrediente` VARCHAR(90) NOT NULL,
  PRIMARY KEY (`idingrediente`),
  UNIQUE INDEX `idingrediente_UNIQUE` (`idingrediente` ASC))
ENGINE = InnoDB;

-- -----------------------------------------------------
-- Table `quasetudogostoso`.`dosagem`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `quasetudogostoso`.`dosagem` ;

CREATE TABLE IF NOT EXISTS `quasetudogostoso`.`dosagem` (
  `iddosagem` INT UNSIGNED NOT NULL AUTO_INCREMENT,
  `dosagem` VARCHAR(60) NOT NULL,
  `abreviacao` VARCHAR(45) NULL,
  PRIMARY KEY (`iddosagem`),
  UNIQUE INDEX `iddosagem_UNIQUE` (`iddosagem` ASC))
ENGINE = InnoDB;

-- -----------------------------------------------------
-- Table `quasetudogostoso`.`ingrediente_receita`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `quasetudogostoso`.`ingrediente_receita` ;

CREATE TABLE IF NOT EXISTS `quasetudogostoso`.`ingrediente_receita` (
  `ingrediente_idingrediente` INT UNSIGNED NOT NULL,
  `receita_idreceita` INT UNSIGNED NOT NULL,
  `quantidade` FLOAT NULL,
  `dosagem_iddosagem` INT UNSIGNED NOT NULL,
  PRIMARY KEY (`ingrediente_idingrediente`, `receita_idreceita`),
  INDEX `fk_ingrediente_has_receita_receita1_idx` (`receita_idreceita` ASC),
  INDEX `fk_ingrediente_has_receita_ingrediente1_idx` (`ingrediente_idingrediente` ASC),
  INDEX `fk_receita_ingrediente_dosagem1_idx` (`dosagem_iddosagem` ASC),
  CONSTRAINT `fk_ingrediente_has_receita_ingrediente1`
    FOREIGN KEY (`ingrediente_idingrediente`)
    REFERENCES `quasetudogostoso`.`ingrediente` (`idingrediente`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_ingrediente_has_receita_receita1`
    FOREIGN KEY (`receita_idreceita`)
    REFERENCES `quasetudogostoso`.`receita` (`idreceita`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_receita_ingrediente_dosagem1`
    FOREIGN KEY (`dosagem_iddosagem`)
    REFERENCES `quasetudogostoso`.`dosagem` (`iddosagem`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;

-- -----------------------------------------------------
-- Table `quasetudogostoso`.`utensilio`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `quasetudogostoso`.`utensilio` ;

CREATE TABLE IF NOT EXISTS `quasetudogostoso`.`utensilio` (
  `idutensilio` INT UNSIGNED NOT NULL AUTO_INCREMENT,
  `utensilio` VARCHAR(45) NOT NULL,
  PRIMARY KEY (`idutensilio`),
  UNIQUE INDEX `idutensilio_UNIQUE` (`idutensilio` ASC))
ENGINE = InnoDB;

-- -----------------------------------------------------
-- Table `quasetudogostoso`.`utensilio_receita`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `quasetudogostoso`.`utensilio_receita` ;

CREATE TABLE IF NOT EXISTS `quasetudogostoso`.`utensilio_receita` (
  `receita_idreceita` INT UNSIGNED NOT NULL,
  `utensilio_idutensilio` INT UNSIGNED NOT NULL,
  PRIMARY KEY (`receita_idreceita`, `utensilio_idutensilio`),
  INDEX `fk_receita_has_utensilio_utensilio1_idx` (`utensilio_idutensilio` ASC),
  INDEX `fk_receita_has_utensilio_receita1_idx` (`receita_idreceita` ASC),
  CONSTRAINT `fk_receita_has_utensilio_receita1`
    FOREIGN KEY (`receita_idreceita`)
    REFERENCES `quasetudogostoso`.`receita` (`idreceita`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_receita_has_utensilio_utensilio1`
    FOREIGN KEY (`utensilio_idutensilio`)
    REFERENCES `quasetudogostoso`.`utensilio` (`idutensilio`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;

-- -----------------------------------------------------
-- Table `quasetudogostoso`.`cozinha`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `quasetudogostoso`.`cozinha` ;

CREATE TABLE IF NOT EXISTS `quasetudogostoso`.`cozinha` (
  `idcozinha` INT UNSIGNED NOT NULL,
  `cozinha` VARCHAR(85) NOT NULL,
  `ativo` TINYINT UNSIGNED NOT NULL,
  PRIMARY KEY (`idcozinha`),
  UNIQUE INDEX `idcozinha_UNIQUE` (`idcozinha` ASC))
ENGINE = InnoDB;

-- -----------------------------------------------------
-- Table `quasetudogostoso`.`refeicao`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `quasetudogostoso`.`refeicao` ;

CREATE TABLE IF NOT EXISTS `quasetudogostoso`.`refeicao` (
  `idrefeicao` INT UNSIGNED NOT NULL,
  `refeicao` VARCHAR(65) NOT NULL,
  `ativo` TINYINT NOT NULL,
  PRIMARY KEY (`idrefeicao`),
  UNIQUE INDEX `idrefeicao_UNIQUE` (`idrefeicao` ASC))
ENGINE = InnoDB;

-- -----------------------------------------------------
-- Table `quasetudogostoso`.`refeicao_receita`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `quasetudogostoso`.`refeicao_receita` ;

CREATE TABLE IF NOT EXISTS `quasetudogostoso`.`refeicao_receita` (
  `receita_idreceita` INT UNSIGNED NOT NULL,
  `refeicao_idrefeicao` INT UNSIGNED NOT NULL,
  PRIMARY KEY (`receita_idreceita`, `refeicao_idrefeicao`),
  INDEX `fk_receita_has_refeicao_refeicao1_idx` (`refeicao_idrefeicao` ASC),
  INDEX `fk_receita_has_refeicao_receita1_idx` (`receita_idreceita` ASC),
  CONSTRAINT `fk_receita_has_refeicao_receita1`
    FOREIGN KEY (`receita_idreceita`)
    REFERENCES `quasetudogostoso`.`receita` (`idreceita`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_receita_has_refeicao_refeicao1`
    FOREIGN KEY (`refeicao_idrefeicao`)
    REFERENCES `quasetudogostoso`.`refeicao` (`idrefeicao`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;

-- -----------------------------------------------------
-- Table `quasetudogostoso`.`cozinha_receita`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `quasetudogostoso`.`cozinha_receita` ;

CREATE TABLE IF NOT EXISTS `quasetudogostoso`.`cozinha_receita` (
  `cozinha_idcozinha` INT UNSIGNED NOT NULL,
  `receita_idreceita` INT UNSIGNED NOT NULL,
  PRIMARY KEY (`cozinha_idcozinha`, `receita_idreceita`),
  INDEX `fk_cozinha_has_receita_receita1_idx` (`receita_idreceita` ASC),
  INDEX `fk_cozinha_has_receita_cozinha1_idx` (`cozinha_idcozinha` ASC),
  CONSTRAINT `fk_cozinha_has_receita_cozinha1`
    FOREIGN KEY (`cozinha_idcozinha`)
    REFERENCES `quasetudogostoso`.`cozinha` (`idcozinha`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_cozinha_has_receita_receita1`
    FOREIGN KEY (`receita_idreceita`)
    REFERENCES `quasetudogostoso`.`receita` (`idreceita`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;

-- =====================================================
-- DADOS FICTÍCIOS PARA TESTES
-- =====================================================

-- Inserir usuários de teste
INSERT INTO `usuario` (`nome`, `email`, `data_nascimento`, `senha`, `salt`, `uuid`) VALUES
('Maria Silva', 'maria.silva@email.com', '1990-05-15', 'senha_hash_maria_123', 'salt_maria_abc', 'uuid-maria-001'),
('João Santos', 'joao.santos@email.com', '1985-08-22', 'senha_hash_joao_456', 'salt_joao_def', 'uuid-joao-002'),
('Ana Costa', 'ana.costa@email.com', '1995-03-10', 'senha_hash_ana_789', 'salt_ana_ghi', 'uuid-ana-003'),
('Pedro Oliveira', 'pedro.oliveira@email.com', '1992-11-30', 'senha_hash_pedro_321', 'salt_pedro_jkl', 'uuid-pedro-004');

-- Inserir categorias
INSERT INTO `categoria` (`categoria`, `ativo`) VALUES
('Doces', 1),
('Salgados', 1),
('Bebidas', 1),
('Massas', 1),
('Carnes', 1),
('Vegetariano', 1),
('Sobremesas', 1),
('Lanches', 1);

-- Inserir tipos de refeição
INSERT INTO `refeicao` (`idrefeicao`, `refeicao`, `ativo`) VALUES
(1, 'Café da Manhã', 1),
(2, 'Almoço', 1),
(3, 'Jantar', 1),
(4, 'Lanche da Tarde', 1),
(5, 'Ceia', 1),
(6, 'Brunch', 1);

-- Inserir dificuldades
INSERT INTO `dificuldade` (`dificuldade`) VALUES
('Fácil'),
('Médio'),
('Difícil');

-- Inserir custos
INSERT INTO `custo` (`idcusto`, `custo`) VALUES
(1, 'Baixo'),
(2, 'Médio'),
(3, 'Alto');

-- Inserir tipos de cozinha
INSERT INTO `cozinha` (`idcozinha`, `cozinha`, `ativo`) VALUES
(1, 'Brasileira', 1),
(2, 'Italiana', 1),
(3, 'Japonesa', 1),
(4, 'Mexicana', 1),
(5, 'Francesa', 1),
(6, 'Chinesa', 1);

-- Inserir ingredientes
INSERT INTO `ingrediente` (`ingrediente`) VALUES
('Farinha de trigo'),
('Açúcar'),
('Ovos'),
('Leite'),
('Manteiga'),
('Chocolate em pó'),
('Queijo mussarela'),
('Tomate'),
('Sal'),
('Fermento em pó'),
('Óleo'),
('Carne moída'),
('Cebola'),
('Alho'),
('Arroz');

-- Inserir dosagens
INSERT INTO `dosagem` (`dosagem`, `abreviacao`) VALUES
('Grama', 'g'),
('Quilograma', 'kg'),
('Mililitro', 'ml'),
('Litro', 'l'),
('Xícara', 'xíc'),
('Colher de sopa', 'cs'),
('Colher de chá', 'cc'),
('Unidade', 'un'),
('A gosto', 'q.b.');

-- Inserir utensílios
INSERT INTO `utensilio` (`utensilio`) VALUES
('Tigela'),
('Colher de pau'),
('Forno'),
('Panela'),
('Liquidificador'),
('Forma de bolo'),
('Frigideira'),
('Faca'),
('Tábua de corte'),
('Batedeira');

-- Inserir preparos de exemplo
INSERT INTO `preparo` (`modo_preparo`, `urlvideo`, `tempopreparo`) VALUES
('1. Pré-aqueça o forno a 180°C.\n2. Em uma tigela, misture a farinha, açúcar e fermento.\n3. Adicione os ovos e o leite, misturando bem.\n4. Despeje em uma forma untada.\n5. Asse por 40 minutos.', 
'https://www.youtube.com/watch?v=exemplo1', '00:50:00'),

('1. Cozinhe o macarrão em água fervente com sal.\n2. Refogue o alho e a cebola.\n3. Adicione o molho de tomate.\n4. Misture com o macarrão e sirva.', 
'https://www.youtube.com/watch?v=exemplo2', '00:30:00'),

('1. Tempere a carne com sal e pimenta.\n2. Grelhe por 5 minutos de cada lado.\n3. Deixe descansar por 5 minutos antes de servir.', 
NULL, '00:20:00');

-- Inserir receitas de exemplo
INSERT INTO `receita` (`titulo`, `descricao`, `cadastro_idusuario`, `preparo_idpreparo`, `dificuldade_iddificuldade`, `custo_idcusto`) VALUES
('Chineque', 'Chineque de Creme', 1, 1, 1, 1),
('Pizza', 'De Frango Catupiry', 2, 2, 1, 1),
('Feijoada', 'Tem de tudo menos linguiça.', 3, 3, 2, 2);

-- Relacionar receitas com categorias
INSERT INTO `categoria_receita` (`receita_idreceita`, `categoria_idcategoria`) VALUES
(1, 1), 
(1, 7), 
(2, 4), 
(3, 5); 

-- Relacionar receitas com refeições
INSERT INTO `refeicao_receita` (`receita_idreceita`, `refeicao_idrefeicao`) VALUES
(1, 4), 
(2, 2), 
(2, 3), 
(3, 2); 

-- Relacionar receitas com cozinhas
INSERT INTO `cozinha_receita` (`cozinha_idcozinha`, `receita_idreceita`) VALUES
(1, 1), 
(2, 2), 
(1, 3); 

-- Inserir comentários
INSERT INTO `comentario` (`receita_idreceita`, `usuario_idusuario`, `comentario`, `nota`) VALUES
(1, 2, 'Adorei esta receita! Ficou muito gostoso e fácil de fazer!', 5),
(1, 3, 'Bom bolo, mas achei um pouco doce demais para o meu gosto.', 4),
(2, 1, 'Macarrão delicioso! Minha família toda amou!', 5),
(3, 4, 'Bife perfeito! Seguindo essas instruções fica no ponto certo.', 5);

-- Relacionar ingredientes com receitas (Bolo de Chocolate)
INSERT INTO `ingrediente_receita` (`ingrediente_idingrediente`, `receita_idreceita`, `quantidade`, `dosagem_iddosagem`) VALUES
(1, 1, 2, 5),   -- 2 xícaras de farinha
(2, 1, 1.5, 5), -- 1.5 xícaras de açúcar
(3, 1, 3, 8),   -- 3 unidades de ovos
(4, 1, 1, 5),   -- 1 xícara de leite
(6, 1, 0.5, 5), -- 0.5 xícara de chocolate em pó
(10, 1, 1, 6);  -- 1 colher de sopa de fermento

-- Relacionar utensílios com receitas
INSERT INTO `utensilio_receita` (`receita_idreceita`, `utensilio_idutensilio`) VALUES
(1, 1), -- Bolo -> Tigela
(1, 3), -- Bolo -> Forno
(1, 6), -- Bolo -> Forma de bolo
(2, 4), -- Macarrão -> Panela
(3, 7); -- Bife -> Frigideira

-- Mensagem final
SELECT '✅ Setup do banco de dados concluído com sucesso!' AS status;
SELECT '📊 Dados de teste inseridos:' AS info;
SELECT COUNT(*) AS total_usuarios FROM usuario;
SELECT COUNT(*) AS total_categorias FROM categoria;
SELECT COUNT(*) AS total_refeicoes FROM refeicao;
SELECT COUNT(*) AS total_receitas FROM receita;

SET SQL_MODE=@OLD_SQL_MODE;
SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS;
SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS;
