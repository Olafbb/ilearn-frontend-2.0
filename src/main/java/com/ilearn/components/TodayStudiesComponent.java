package com.ilearn.components;

import com.ilearn.dto.LessonDto;
import com.ilearn.service.BackendService;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;

import java.time.LocalDate;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

public class TodayStudiesComponent extends VerticalLayout {
    private VerticalLayout layout;
    private HorizontalLayout background;
    private Grid<LessonDto> lessonsGrid;
    private BackendService backendService;
    private static final String TOPIC = "Connect to discord app?";

    public TodayStudiesComponent(BackendService backendService) {
        this.backendService = backendService;
        layout = new VerticalLayout();
        background = new HorizontalLayout();
        lessonsGrid = new Grid<>(LessonDto.class);

        configureLessonsGrid(lessonsGrid);
        updateLessonsGrid(lessonsGrid);

        background.add(lessonsGrid);
        layout.add(background);
        add(layout);
    }

    private void configureLessonsGrid(Grid<LessonDto> grid) {
        grid.setColumns("name", "date", "teachers");
        grid.getColumnByKey("name").setHeader("Name");
        grid.getColumnByKey("date").setHeader("Date");
        grid.getColumnByKey("teachers").setHeader("Teacher");

        grid.getColumns().forEach(lessonDtoColumn -> lessonDtoColumn.setAutoWidth(true));
        grid.setWidth("750px");

        grid.addItemClickListener(event -> {
            DialogComponent dialogComponent = new DialogComponent();
            dialogComponent.create(TOPIC, new Button("Yes", connect -> {
                getUI().ifPresent(ui -> ui.getPage().open(event.getItem().getLink()));
                dialogComponent.close();
            }), new Button("No", close -> dialogComponent.close()), false);
        });
    }

    private void updateLessonsGrid(Grid<LessonDto> grid) {
        grid.setItems(getLessonsList());
    }

    private List<LessonDto> getLessonsList() {
        List<LessonDto> lessons = backendService.getLessons();
        return lessons.stream()
                .filter(lessonDto -> lessonDto.getDay().equals(checkDay()))
                .sorted(Comparator.comparingInt(LessonDto::getLessonNr))
                .collect(Collectors.toList());
    }

    private String checkDay() {
        if (!(LocalDate.now().getDayOfWeek().toString().equals("SATURDAY")
                || LocalDate.now().getDayOfWeek().toString().equals("SUNDAY"))) {
            LocalDate localDate = LocalDate.now();
            return localDate.getDayOfWeek().toString();
        } else {
            return "MONDAY";
        }
    }

    private String beautifyDate(String date) {
        return date.replace("CEST", "").replace("2020", "");
    }
}
