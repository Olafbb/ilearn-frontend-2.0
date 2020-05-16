package com.ilearn.components;

import com.ilearn.dto.MarkDto;
import com.ilearn.service.BackendService;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.html.Div;

public class MarksComponent extends Div {
    private Grid<MarkDto> marksGrid;
    private BackendService backendService;

    public MarksComponent(BackendService backendService) {
        this.backendService = backendService;
        marksGrid = new Grid<>(MarkDto.class);

        configureMarksGrid(marksGrid);
        updateMarksGrid(marksGrid);

        add(marksGrid);
    }

    private void updateMarksGrid(Grid<MarkDto> grid) {
        grid.setItems(backendService.getMarks());
    }

    private void configureMarksGrid(Grid<MarkDto> grid) {
        grid.setColumns("value", "subject");
        grid.getColumnByKey("value").setHeader("Mark");
        grid.getColumnByKey("subject").setHeader("Subject");
        grid.getColumns().forEach(markDtoColumn -> markDtoColumn.setAutoWidth(true));
        grid.setMinWidth("300px");
        grid.addItemClickListener(event -> {
            DialogComponent dialogComponent = new DialogComponent();
            dialogComponent.create(event.getItem().getDescription(), beautifyDate(event.getItem().getDate().toString()), true);
        });
    }

    private String beautifyDate(String date) {
        return date.replace("CEST", "").replace("2020", "");
    }

}
