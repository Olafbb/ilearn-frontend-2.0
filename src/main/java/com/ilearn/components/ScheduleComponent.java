package com.ilearn.components;

import com.ilearn.dto.LessonDto;
import com.ilearn.service.BackendService;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;

import java.util.ArrayList;
import java.util.List;

public class ScheduleComponent extends VerticalLayout {
    private VerticalLayout verticalLayout;
    private HorizontalLayout background;
    private BackendService backendService;

    private Grid<LessonDto> mondayGrid;
    private Grid<LessonDto> tuesdayGrid;
    private Grid<LessonDto> wednesdayGrid;
    private Grid<LessonDto> thursdayGrid;
    private Grid<LessonDto> fridayGrid;

    public ScheduleComponent(BackendService backendService) {
        this.backendService = backendService;
        verticalLayout = new VerticalLayout();
        background = new HorizontalLayout();

        createGrids();
        configureGrids();

        background.setSpacing(false);
        background.add(mondayGrid, tuesdayGrid, wednesdayGrid, thursdayGrid, fridayGrid);
        verticalLayout.add(background);
        add(verticalLayout);
    }

    private void createGrids() {
        mondayGrid = new Grid<>(LessonDto.class);
        tuesdayGrid = new Grid<>(LessonDto.class);
        wednesdayGrid = new Grid<>(LessonDto.class);
        thursdayGrid = new Grid<>(LessonDto.class);
        fridayGrid = new Grid<>(LessonDto.class);
    }

    private void configureGrids() {
        List<Grid<LessonDto>> lessons = new ArrayList<>();
        lessons.add(mondayGrid);
        lessons.add(tuesdayGrid);
        lessons.add(wednesdayGrid);
        lessons.add(thursdayGrid);
        lessons.add(fridayGrid);
        lessons.forEach(grid -> grid.addItemClickListener(event -> {
            DialogComponent dialogComponent = new DialogComponent();
            dialogComponent.create(event.getItem().getTeachers().get(0), beautifyDate(event.getItem().getDate().toString()), true);
        }));
        lessons.forEach(grid -> {
            grid.setWidth("150px");
            grid.setMaxHeight("384px");
            grid.setColumns("name");
        });

        mondayGrid.getColumnByKey("name").setHeader("Monday");
        tuesdayGrid.getColumnByKey("name").setHeader("Tuesday");
        wednesdayGrid.getColumnByKey("name").setHeader("Wednesday");
        thursdayGrid.getColumnByKey("name").setHeader("Thursday");
        fridayGrid.getColumnByKey("name").setHeader("Friday");

        mondayGrid.setItems(backendService.getSchedule().get(0));
        tuesdayGrid.setItems(backendService.getSchedule().get(1));
        wednesdayGrid.setItems(backendService.getSchedule().get(2));
        thursdayGrid.setItems(backendService.getSchedule().get(3));
        fridayGrid.setItems(backendService.getSchedule().get(4));

    }

    private String beautifyDate(String date) {
        return date.replace("CEST", "").replace("2020", "");
    }
}
