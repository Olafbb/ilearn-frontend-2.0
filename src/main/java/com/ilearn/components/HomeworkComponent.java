package com.ilearn.components;

import com.ilearn.dto.HomeworkDto;
import com.ilearn.service.BackendService;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.html.Div;

public class HomeworkComponent extends Div {
    private Grid<HomeworkDto> homeworkGrid;
    private BackendService backendService;

    public HomeworkComponent(BackendService backendService) {
        this.backendService = backendService;
        homeworkGrid = new Grid<>(HomeworkDto.class);

        updateHomeworkGrid();
        configureHomeworkGrid();

        add(homeworkGrid);
    }

    private void updateHomeworkGrid() {
        homeworkGrid.setItems(backendService.getHomework());
    }

    private void configureHomeworkGrid() {
        homeworkGrid.setColumns("subject", "deadline");
        homeworkGrid.getColumnByKey("subject").setHeader("Subject");
        homeworkGrid.getColumnByKey("deadline").setHeader("Deadline");
        homeworkGrid.getColumns().forEach(markDtoColumn -> markDtoColumn.setAutoWidth(true));
        homeworkGrid.setMinWidth("450px");
        homeworkGrid.addItemClickListener(event -> {
            new DialogComponent().createSendingVariant(event.getItem(), backendService);
            updateHomeworkGrid();
        });
    }
}
