package com.ilearn.view;

import com.ilearn.components.*;
import com.ilearn.config.Links;
import com.ilearn.service.BackendService;
import com.vaadin.flow.component.dialog.Dialog;
import com.vaadin.flow.component.html.Label;
import com.vaadin.flow.component.menubar.MenuBar;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;

@Route(value = "")
@PageTitle("Start | i-learn")
public class StartView extends VerticalLayout {
    private MenuBar navigationBar;
    private ScheduleComponent scheduleComponent;
    private TodayStudiesComponent todayStudiesComponent;
    private MarksComponent marksComponent;
    private HomeworkComponent homeworkComponent;
    private DiscordChannelsComponent discordComponent;
    private ChatComponent chatComponent;

    private HorizontalLayout background;
    private VerticalLayout lessonsLayout;
    private VerticalLayout marksLayout;
    private VerticalLayout homeworkLayout;
    private HorizontalLayout MarksAndHomeworkLayout;
    private VerticalLayout configurationLayout;

    private Links links;
    private BackendService backendService;

    public StartView(Links links, BackendService backendService) {
        this.backendService = backendService;
        this.links = links;
        navigationBar = new ApplicationNavigationBar();
        background = new HorizontalLayout();

        add(navigationBar);
        createComponents();
        createLayouts();
        configureLayouts();
        add(background);
        addChatToNavigationBar();
    }

    private void createLayouts() {
        background = new HorizontalLayout();
        lessonsLayout = new VerticalLayout();
        marksLayout = new VerticalLayout();
        homeworkLayout = new VerticalLayout();
        MarksAndHomeworkLayout = new HorizontalLayout();
        configurationLayout = new VerticalLayout();
    }
    private void addChatToNavigationBar() {
        navigationBar.addItem("Chat",event -> new Dialog(chatComponent).open());
    }

    public void createComponents() {
        todayStudiesComponent = new TodayStudiesComponent(backendService);
        scheduleComponent = new ScheduleComponent(backendService);
        marksComponent = new MarksComponent(backendService);
        homeworkComponent = new HomeworkComponent(backendService);
        discordComponent = new DiscordChannelsComponent(backendService);
        chatComponent = new ChatComponent(backendService);
    }

    private void configureLayouts() {
        lessonsLayout.setDefaultHorizontalComponentAlignment(Alignment.CENTER);
        marksLayout.setDefaultHorizontalComponentAlignment(Alignment.AUTO);
        homeworkLayout.setDefaultHorizontalComponentAlignment(Alignment.AUTO);

        lessonsLayout.setSpacing(false);
        lessonsLayout.add("Lessons");
        lessonsLayout.add(todayStudiesComponent, scheduleComponent);
        lessonsLayout.setHorizontalComponentAlignment(Alignment.CENTER, todayStudiesComponent);

        marksLayout.add("Marks");
        marksLayout.add(marksComponent);
        marksLayout.setDefaultHorizontalComponentAlignment(Alignment.CENTER);

        homeworkLayout.add("Homework");
        homeworkLayout.add(homeworkComponent);
        homeworkLayout.setDefaultHorizontalComponentAlignment(Alignment.CENTER);

        MarksAndHomeworkLayout.add(marksLayout, homeworkLayout);

        Label emptyArea = new Label();
        emptyArea.setHeight("0px");

        configurationLayout.add(emptyArea, MarksAndHomeworkLayout);
        configurationLayout.add(discordComponent);

        background.setSpacing(false);
        background.add(lessonsLayout, configurationLayout);
        background.setSpacing(false);

        setSpacing(false);

    }


}
