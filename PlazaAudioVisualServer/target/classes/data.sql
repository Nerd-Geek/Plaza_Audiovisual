CREATE TABLE `users` (
                         `id` varchar(255) NOT NULL,
                         `username` varchar(255),
                         `name` varchar(255),
                         `lastname` varchar(255),
                         `email` varchar(255),
                         `phone_number` varchar(255),
                         `password` varchar(255),
                         `image` varchar(255),
                         `description` varchar(255),
                         PRIMARY KEY(`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

CREATE TABLE `login` (
                         `id` varchar(255) NOT NULL,
                         `token` varchar(512),
                         `instant` datetime(6),
                         `user_id` varchar(255),
                         PRIMARY KEY(`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

CREATE TABLE `user_roles` (
                              `user_id` varchar(255) NOT NULL,
                              `roles` varchar(255)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

CREATE TABLE `media` (
                         `id` varchar(255) NOT NULL,
                         `size` double,
                         `type` varchar(255),
                         `name` varchar(255),
                         `description` varchar(255),
                         `dimension` int,
                         `user_id` varchar(255),
                         PRIMARY KEY(`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

alter table users drop index if exists UK_6dotkott2kjsp8vw4d0m25fb7;
alter table users add constraint UK_6dotkott2kjsp8vw4d0m25fb7 unique (email);
alter table users drop index if exists UK_r43af9ap4edm43mmtq01oddj6;
alter table users add constraint UK_r43af9ap4edm43mmtq01oddj6 unique (username);
alter table media add constraint FK5k0oem3ypbsnpc0kt1htx37w4 foreign key (user_id) references users (id);
alter table login add constraint FKddrmlhg56oaq3coq9xohjulr4 foreign key (user_id) references users (id);
alter table user_roles add constraint FKhfh9dx7w3ubf1co1vdev94g3f foreign key (user_id) references users (id);

INSERT INTO `users`(`id`, `username`, `name`, `lastname`,`email`,`phone_number`, `password`,
                    `image`, `description`) VALUES
                                                ('49c32c1e-bdc0-4999-94b4-46b9e724ac25', 'manolo23', 'Manolo', 'Sánchez', 'manolo@sanchez.com',
                                                 '123456788', '$2a$10$Qar.LEjvMxWFsdgCQuQVU.5Eto7JqBzg3YEC94Dd6lOaNGlLirRtu', 'http://192.168.29.141:6668/rest/files/1666536614758_avatar.png', 'algo'),
                                                ('f24183eb-bd3d-4a98-af56-9c2409a5f87c', 'pepo321', 'Pepo', 'Pepito', 'pepo@pepito.com',
                                                 '123456789', '$2a$10$Qar.LEjvMxWFsdgCQuQVU.5Eto7JqBzg3YEC94Dd6lOaNGlLirRtu', 'http://192.168.29.141:6668/rest/files/1666536760224_avatar2.png', 'algo'),
                                                ('5a1ee629-81ce-4922-af8f-e4dd8dfd2cff', 'paco30', 'Paco', 'Sánchez', 'paco@sanchez.com',
                                                 '123456787', '$2a$10$Qar.LEjvMxWFsdgCQuQVU.5Eto7JqBzg3YEC94Dd6lOaNGlLirRtu', 'http://192.168.29.141:6668/rest/files/1666536874687_avatar3.png', 'algo');

INSERT INTO `user_roles` (`user_id`, `roles`) VALUES
                                                  ('49c32c1e-bdc0-4999-94b4-46b9e724ac25', 'ADMIN'),
                                                  ('f24183eb-bd3d-4a98-af56-9c2409a5f87c', 'ADMIN'),
                                                  ('5a1ee629-81ce-4922-af8f-e4dd8dfd2cff', 'NORMAL');

INSERT INTO `media` (`id`, `size`, `type`, `name`, `description`, `dimension`,`user_id`) VALUES
                                                                                             ('6a3831f9-d026-4764-9b46-44d1427f07e5', 12.5, 'png', 'http://192.168.29.141:6668/rest/files/1666513107101_gato.jpg', 'Foto', 1920,'49c32c1e-bdc0-4999-94b4-46b9e724ac25'),
                                                                                             ('dca76e7e-6dc5-4ad0-81a6-254f3c7b3b84', 12.5, 'png', 'http://192.168.29.141:6668/rest/files/1666513117352_playa.jpg', 'Foto', 1920,'f24183eb-bd3d-4a98-af56-9c2409a5f87c'),
                                                                                             ('bb552132-f830-410f-813b-fc777129492e', 12.5, 'png', 'http://192.168.29.141:6668/rest/files/1666513129289_puente.jpg', 'Foto', 1920,'5a1ee629-81ce-4922-af8f-e4dd8dfd2cff');