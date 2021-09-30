-- Blog

CREATE TABLE `blog_posts` (
    `id` int(11) unsigned NOT NULL AUTO_INCREMENT,
    `title` varchar(255) DEFAULT NULL,
    `content` text,
    `created_at` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
    PRIMARY KEY (`id`)
    ) ENGINE=InnoDB DEFAULT CHARSET=utf8;

CREATE TABLE `blog_post_user_relations` (
    `post_id` int(11) unsigned NOT NULL,
    `user_id` int(11) unsigned NOT NULL,
    KEY `post_id` (`post_id`),
    KEY `user_id` (`user_id`),
    CONSTRAINT `blog_post_user_ibfk_1` FOREIGN KEY (`post_id`) REFERENCES `blog_posts` (`id`) ON DELETE CASCADE,
    CONSTRAINT `blog_post_user_ibfk_2` FOREIGN KEY (`user_id`) REFERENCES `users` (`id`) ON DELETE CASCADE
    ) ENGINE=InnoDB DEFAULT CHARSET=utf8;

INSERT INTO `blog_posts` (`title`, `content`, `created_at`) VALUES ('Hello to the blog example!', '<p>Hi and welcome to the demo of Ninja!</p>', NOW());
INSERT INTO `blog_post_user_relations` (`post_id`, `user_id`) VALUES (1, 1);