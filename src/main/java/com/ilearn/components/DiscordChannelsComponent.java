package com.ilearn.components;

import com.ilearn.dto.ChannelDto;
import com.ilearn.service.BackendService;
import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;

public class DiscordChannelsComponent extends VerticalLayout {

    private Grid<ChannelDto> discordChannelsGrid;
    private HorizontalLayout background;
    private VerticalLayout verticalLayout;
    private BackendService backendService;

    public DiscordChannelsComponent(BackendService backendService) {
        this.backendService = backendService;
        verticalLayout = new VerticalLayout();
        background = new HorizontalLayout();
        discordChannelsGrid = new Grid<>(ChannelDto.class);

        configureChannelsGrid();
        updateChannelsGrid();

        background.add(discordChannelsGrid);
        verticalLayout.add(background);
        add(verticalLayout);
       refresh();
    }

    private void configureChannelsGrid() {
        discordChannelsGrid.setColumns("name", "numberOfPeople");
        discordChannelsGrid.getColumnByKey("name").setHeader("Lesson");
        discordChannelsGrid.getColumnByKey("numberOfPeople").setHeader("Students");
        discordChannelsGrid.getColumns().forEach(lessonDtoColumn -> lessonDtoColumn.setAutoWidth(true));
        discordChannelsGrid.setWidth("500px");
        discordChannelsGrid.setMaxHeight("310px");
        discordChannelsGrid.addItemClickListener(event -> {
            DialogComponent dialogComponent = new DialogComponent();
            dialogComponent.create("Students: ", beautifyMember(event.getItem().getMembers().toString()), true);
        });
    }

    public void updateChannelsGrid() {
        discordChannelsGrid.setItems(backendService.getChannels());
    }
    private void refresh() {
        UI ui = UI.getCurrent();
        ui.setPollInterval(1000);
        ui.addPollListener(event -> updateChannelsGrid());
    }
    private String beautifyMember(String text) {
        return text.replace("[", "").replace("]", "");
    }
}

