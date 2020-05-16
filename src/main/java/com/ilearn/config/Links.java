package com.ilearn.config;

import lombok.Getter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Getter
@Component
public class Links {
    @Value("${link.lessons}")
    private String lessonsLink;

    @Value("${link.marks}")
    private String marksLink;

    @Value("${link.homework}")
    private String homeworkLink;

    @Value("${link.channels}")
    private String channelsLink;

    @Value("${link.chat}")
    private String chatLink;

    @Value("${link.edit.homework}")
    private String editHomeworkLink;

    @Value("${link.upload}")
    private String uploadLink;

    @Value("${link.create.user}")
    private String createUserLink;

    @Value("${link.users.all}")
    private String usersLink;

    @Value("${link.students.all}")
    private String studentsLink;

    @Value("${link.student}")
    private String studentLink;

    @Value("${link.student.basic}")
    private String studentBasicLink;

    @Value("${link.messages.all}")
    private String messagesLink;

    @Value("${link.messages.create}")
    private String createMessageLink;

    @Value("${link.github.myaccount}")
    private String myGithubAccount;
}
