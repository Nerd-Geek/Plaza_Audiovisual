SET sql_mode = 'NO_AUTO_VALUE_ON_ZERO';

DROP DATABASE IF EXISTS `plaza`;
CREATE DATABASE `plaza`;
USE `plaza`;

CREATE TABLE `login` (
    `id` varchar(255) NOT NULL,
    `token` varchar(512),
    `instant` datetime(6),
    `user_id` varchar(255),
    PRIMARY KEY(`id`),
    KEY `user_FK` (`user_id`),
        CONSTRAINT `user_FK` FOREIGN KEY (`user_id`)
                                        REFERENCES `users` (`id``)
) ENGINE=InnoDB;

CREATE TABLE `user_rol` (
    `user_id` varchar(255) NOT NULL,
    `roles` varchar(255),
    KEY `user_FK` (`user_id`),
            CONSTRAINT `user_FK` FOREIGN KEY (`user_id`)
                                            REFERENCES `users` (`id`)
) ENGINE=InnoDB;

CREATE TABLE `users` (
    `id` varchar(255) NOT NULL,
    `username` varchar(255),
    `name` varchar(255),
    `lastname` varchar(255),
    `email` varchar(255),
    `phonenumber` varchar(255),
    `password` varchar(255),
    `image` varchar(255),
    `description` varchar(255),
    PRIMARY KEY(`id`)
) ENGINE=InnoDB;

CREATE TABLE `media` (
    `id` varchar(255) NOT NULL,
    `size` double,
    `type` varchar(255),
    `name` varchar(255),
    `description` varchar(255),
    `user_id` varchar(255)
    PRIMARY KEY(`id`),
    KEY `user_FK` (`user_id`),
            CONSTRAINT `user_FK` FOREIGN KEY (`user_id`)
                                            REFERENCES `users` (`id`)
) ENGINE=InnoDB;

INSERT INTO `users`(`id`, `username`, `name`, `lastname`,`email`,`phonenumber`, `password`,
                    `image`, `description`) VALUES
                    ('49c32c1e-bdc0-4999-94b4-46b9e724ac25', 'manolo23', 'Manolo', 'Sánchez', 'manolo@sanchez.com',
                     '123456789', '$2a$10$Qar.LEjvMxWFsdgCQuQVU.5Eto7JqBzg3YEC94Dd6lOaNGlLirRtu', 'asd', 'algo'),
                    ('f24183eb-bd3d-4a98-af56-9c2409a5f87c', 'pepo321', 'Pepo', 'Pepito', 'pepo@pepito.com',
                                          '123456789', '$2a$10$Qar.LEjvMxWFsdgCQuQVU.5Eto7JqBzg3YEC94Dd6lOaNGlLirRtu', 'asd', 'algo'),
                    ('5a1ee629-81ce-4922-af8f-e4dd8dfd2cff', 'paco30', 'Paco', 'Sánchez', 'paco@sanchez.com',
                                         '123456789', '$2a$10$Qar.LEjvMxWFsdgCQuQVU.5Eto7JqBzg3YEC94Dd6lOaNGlLirRtu', 'asd', 'algo');

INSERT INTO `user_rol` (`user_id`, `roles`) VALUES
                        ('49c32c1e-bdc0-4999-94b4-46b9e724ac25', 'ADMIN'),
                        ('f24183eb-bd3d-4a98-af56-9c2409a5f87c', 'ADMIN'),
                        ('5a1ee629-81ce-4922-af8f-e4dd8dfd2cff', 'NORMAL');

INSERT INTO `media` (`id`, `size`, `type`, `name`, `description`, `user_id`) VALUES
                    ('6a3831f9-d026-4764-9b46-44d1427f07e5', 12.5, 'png', 'sda', 'Foto', '49c32c1e-bdc0-4999-94b4-46b9e724ac25'),
                    ('dca76e7e-6dc5-4ad0-81a6-254f3c7b3b84', 12.5, 'png', 'sda', 'Foto', 'f24183eb-bd3d-4a98-af56-9c2409a5f87c'),
                    ('bb552132-f830-410f-813b-fc777129492e', 12.5, 'png', 'sda', 'Foto', '5a1ee629-81ce-4922-af8f-e4dd8dfd2cff');