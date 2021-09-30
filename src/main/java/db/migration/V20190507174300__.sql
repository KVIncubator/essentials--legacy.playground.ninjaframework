-- Users

CREATE TABLE `users` (
    `id` int(11) UNSIGNED NOT NULL AUTO_INCREMENT,
    `email` varchar(255) NOT NULL,
    `password` varchar(255) NOT NULL,
    `created_at` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
    PRIMARY KEY (`id`),
    UNIQUE KEY(`email`)
    ) ENGINE=InnoDB DEFAULT CHARSET=utf8;

INSERT INTO `users` (`email`, `password`, `created_at`) VALUES ('admin@example.com', 'secret', NOW());