CREATE TABLE `answer` (
  `id` varchar(36) NOT NULL,
  `task_id` varchar(36) NOT NULL COMMENT '任务id',
  `received_task_id` varchar(36) NOT NULL COMMENT '已接受任务id',
  `author_id` varchar(16) NOT NULL,
  `tier` int(11) NOT NULL COMMENT '解读层数，大于1的为追加解读',
  `title` text NOT NULL,
  `content` text NOT NULL,
  `commit_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `praise_amount` int(11) NOT NULL COMMENT '点赞数',
  PRIMARY KEY (`id`),
  KEY `task_id` (`task_id`),
  KEY `received_task_id` (`received_task_id`),
  KEY `author_id` (`author_id`),
  CONSTRAINT `answer_ibfk_1` FOREIGN KEY (`task_id`) REFERENCES `task` (`id`),
  CONSTRAINT `answer_ibfk_2` FOREIGN KEY (`received_task_id`) REFERENCES `received_task` (`id`),
  CONSTRAINT `answer_ibfk_3` FOREIGN KEY (`author_id`) REFERENCES `student` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

CREATE TABLE `attachment` (
  `id` varchar(36) NOT NULL,
  `rely_on_id` varchar(36) NOT NULL COMMENT '对应任务id或解读id',
  `author_id` varchar(16) NOT NULL,
  `save_path` varchar(125) NOT NULL,
  `size` int(11) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

CREATE TABLE `check_log` (
  `id` varchar(36) NOT NULL,
  `check_type` smallint(6) NOT NULL COMMENT '审核内容类型，0任务1解读',
  `task_or_answer_id` varchar(36) NOT NULL COMMENT '审核的解读id或任务id',
  `author_id` varchar(16) NOT NULL COMMENT '解读或任务提交者id',
  `checker_id` varchar(16) NOT NULL COMMENT '审核者id',
  `check_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `is_pass` smallint(6) NOT NULL COMMENT '是否通过，0不通过1通过',
  `reason` text COMMENT '不通过理由',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

CREATE TABLE `feedback` (
  `id` varchar(36) NOT NULL,
  `author_id` varchar(16) NOT NULL,
  `content` text NOT NULL,
  `commit_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

CREATE TABLE `message` (
  `id` varchar(36) NOT NULL,
  `target_id` varchar(16) NOT NULL,
  `content` text NOT NULL,
  `send_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `is_read` char(1) NOT NULL DEFAULT '0' COMMENT '已读标记，0未读，1已读',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

CREATE TABLE `pay_record` (
  `id` varchar(36) NOT NULL,
  `pay_id` varchar(16) NOT NULL,
  `answer_id` varchar(32) NOT NULL,
  `received_task_id` varchar(36) NOT NULL,
  `pay_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`),
  KEY `pay_id` (`pay_id`),
  KEY `answer_id` (`answer_id`),
  KEY `received_task_id` (`received_task_id`),
  CONSTRAINT `pay_record_ibfk_1` FOREIGN KEY (`pay_id`) REFERENCES `student` (`id`),
  CONSTRAINT `pay_record_ibfk_2` FOREIGN KEY (`answer_id`) REFERENCES `answer` (`id`),
  CONSTRAINT `pay_record_ibfk_3` FOREIGN KEY (`received_task_id`) REFERENCES `received_task` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

CREATE TABLE `pending_answer` (
  `id` varchar(36) NOT NULL,
  `task_id` varchar(36) NOT NULL,
  `received_task_id` varchar(36) NOT NULL,
  `author_id` varchar(16) NOT NULL,
  `title` text NOT NULL,
  `content` text NOT NULL,
  `commit_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `check_time` timestamp NULL DEFAULT CURRENT_TIMESTAMP,
  `check_mark` char(1) NOT NULL COMMENT '审核标记，0未审核，1已通过，2未通过',
  `reason` text COMMENT '审核不通过理由',
  PRIMARY KEY (`id`),
  KEY `task_id` (`task_id`),
  KEY `received_task_id` (`received_task_id`),
  KEY `author_id` (`author_id`),
  CONSTRAINT `pending_answer_ibfk_1` FOREIGN KEY (`task_id`) REFERENCES `task` (`id`),
  CONSTRAINT `pending_answer_ibfk_2` FOREIGN KEY (`received_task_id`) REFERENCES `received_task` (`id`),
  CONSTRAINT `pending_answer_ibfk_3` FOREIGN KEY (`author_id`) REFERENCES `student` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

CREATE TABLE `pending_task` (
  `id` varchar(36) NOT NULL,
  `author_id` varchar(16) NOT NULL,
  `title` text NOT NULL,
  `content` text NOT NULL,
  `commit_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `check_time` timestamp NULL DEFAULT NULL,
  `check_mark` char(1) NOT NULL DEFAULT '0' COMMENT '审核标记，0未审核，1已通过，2未通过',
  `reason` text COMMENT '审核不通过理由',
  `team_id` varchar(36) NOT NULL,
  PRIMARY KEY (`id`),
  KEY `author_id` (`author_id`),
  KEY `pending_task_team_FK` (`team_id`),
  CONSTRAINT `pending_task_ibfk_1` FOREIGN KEY (`author_id`) REFERENCES `student` (`id`),
  CONSTRAINT `pending_task_team_FK` FOREIGN KEY (`team_id`) REFERENCES `team` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

CREATE TABLE `praise_record` (
  `id` varchar(36) NOT NULL,
  `answer_id` varchar(36) NOT NULL COMMENT '点赞解读id',
  `praise_id` varchar(16) NOT NULL COMMENT '点赞者id',
  `praise_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`),
  KEY `answer_id` (`answer_id`),
  KEY `praise_id` (`praise_id`),
  CONSTRAINT `praise_record_ibfk_1` FOREIGN KEY (`answer_id`) REFERENCES `answer` (`id`),
  CONSTRAINT `praise_record_ibfk_2` FOREIGN KEY (`praise_id`) REFERENCES `student` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

CREATE TABLE `received_task` (
  `id` varchar(36) NOT NULL,
  `task_id` varchar(36) NOT NULL,
  `receiver_id` varchar(16) NOT NULL,
  `receive_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `is_final` char(1) NOT NULL DEFAULT '0' COMMENT '是否可提交解读',
  PRIMARY KEY (`id`),
  KEY `task_id` (`task_id`),
  KEY `receiver_id` (`receiver_id`),
  CONSTRAINT `received_task_ibfk_1` FOREIGN KEY (`task_id`) REFERENCES `task` (`id`),
  CONSTRAINT `received_task_ibfk_2` FOREIGN KEY (`receiver_id`) REFERENCES `student` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

CREATE TABLE `score_standard` (
  `team_id` varchar(36) NOT NULL,
  `publish_task` smallint(6) DEFAULT '5' COMMENT '学生发布的任务通过',
  `praise` smallint(6) DEFAULT '1' COMMENT '点赞',
  `pay_for_answer` smallint(6) DEFAULT '2' COMMENT '付费查看解读',
  `initial_score` smallint(6) DEFAULT '20' COMMENT '初始积分',
  PRIMARY KEY (`team_id`),
  CONSTRAINT `score_standard_ibfk_1` FOREIGN KEY (`team_id`) REFERENCES `team` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

CREATE TABLE `stu_task` (
  `task_id` varchar(36) NOT NULL,
  `student_id` varchar(16) NOT NULL,
  PRIMARY KEY (`task_id`,`student_id`),
  KEY `stu_task_student_FK` (`student_id`),
  CONSTRAINT `stu_task_student_FK` FOREIGN KEY (`student_id`) REFERENCES `student` (`id`),
  CONSTRAINT `stu_task_task_FK` FOREIGN KEY (`task_id`) REFERENCES `task` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

CREATE TABLE `student` (
  `id` varchar(16) NOT NULL,
  `pwd` varchar(16) NOT NULL,
  `name` varchar(42) NOT NULL,
  `score` int(11) NOT NULL COMMENT '积分',
  `is_remove` char(1) NOT NULL COMMENT '移除标记，0未移除，1已移除',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

CREATE TABLE `task` (
  `id` varchar(36) NOT NULL,
  `team_id` varchar(36) NOT NULL,
  `author_id` varchar(16) NOT NULL,
  `reward` int(11) NOT NULL,
  `title` text NOT NULL,
  `content` text NOT NULL,
  `commit_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `end_time` timestamp NOT NULL DEFAULT '2030-01-01 00:00:00',
  `is_final` char(1) NOT NULL DEFAULT '0' COMMENT '是否不再可以被接收',
  PRIMARY KEY (`id`),
  KEY `task_team_FK` (`team_id`),
  CONSTRAINT `task_team_FK` FOREIGN KEY (`team_id`) REFERENCES `team` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

CREATE TABLE `teacher` (
  `id` varchar(16) NOT NULL,
  `pwd` varchar(16) NOT NULL,
  `name` varchar(40) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

CREATE TABLE `team` (
  `id` varchar(36) NOT NULL,
  `name` varchar(40) NOT NULL,
  `teacher_id` varchar(16) NOT NULL,
  `assistant_id` varchar(16) DEFAULT NULL,
  `is_remove` char(1) NOT NULL DEFAULT '0',
  PRIMARY KEY (`id`),
  KEY `teacher_id` (`teacher_id`),
  KEY `team_student_FK` (`assistant_id`),
  CONSTRAINT `team_ibfk_1` FOREIGN KEY (`teacher_id`) REFERENCES `teacher` (`id`),
  CONSTRAINT `team_student_FK` FOREIGN KEY (`assistant_id`) REFERENCES `student` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

CREATE TABLE `team_stu` (
  `student_id` varchar(16) NOT NULL,
  `team_id` varchar(36) NOT NULL,
  `is_remove` char(1) NOT NULL DEFAULT '0' COMMENT '是否被移除',
  PRIMARY KEY (`student_id`,`team_id`),
  KEY `team_stu_team_FK` (`team_id`),
  CONSTRAINT `team_stu_student_FK` FOREIGN KEY (`student_id`) REFERENCES `student` (`id`),
  CONSTRAINT `team_stu_team_FK` FOREIGN KEY (`team_id`) REFERENCES `team` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
