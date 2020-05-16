package com.ilearn.components;

import com.ilearn.dto.HomeworkDto;
import com.ilearn.service.BackendService;
import com.ilearn.user.ActualUser;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.dialog.Dialog;
import com.vaadin.flow.component.html.Label;
import com.vaadin.flow.component.orderedlayout.FlexComponent;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.upload.Upload;
import com.vaadin.flow.component.upload.receivers.MemoryBuffer;
import lombok.Getter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Getter
public class DialogComponent extends Dialog {
    private VerticalLayout background;

    public DialogComponent() {
        createLayouts();
        configureDialog();
        add(background);
        background.setDefaultHorizontalComponentAlignment(FlexComponent.Alignment.CENTER);
    }

    private void createLayouts() {
        background = new VerticalLayout();
    }

    private void configureDialog() {
        setWidth("300px");
        setHeight("150px");
        setCloseOnEsc(true);
        setCloseOnOutsideClick(true);
        setCloseOnEsc(true);
    }

    public void create(String text, String secondText, boolean isOkButton) {
        background.add(new Label(text), new Label(secondText));
        if (isOkButton)
            createOkButton();

        open();
    }

    public void create(String text, boolean isOkButton) {
        background.add(new Label(text));
        if (isOkButton)
            createOkButton();
        open();
    }

    public void create(String text, Button button, boolean isOkButton) {
        background.add(new Label(text), button);
        if (isOkButton) {
            createOkButton();
        }
        open();
    }

    public void create(String text, Button button, Button secondButton, boolean isOkButton) {
        HorizontalLayout horizontalLayout = new HorizontalLayout();
        horizontalLayout.add(button, secondButton);
        background.add(new Label(text), horizontalLayout);
        if (isOkButton) {
            createOkButton();
        }
        open();
    }

    public void createSendingVariant(HomeworkDto homeworkDto, BackendService backendService) {
        background.setDefaultHorizontalComponentAlignment(FlexComponent.Alignment.CENTER);
        if (homeworkDto.getIsDone()) {
            background.add(new Label("Zadanie oddane"));
            createOkButton();
        } else {
            background.add(new Label(homeworkDto.getContent()));
            MemoryBuffer memoryBuffer = new MemoryBuffer();
            Upload upload = new Upload(memoryBuffer);
            upload.addSucceededListener(event -> {
                backendService.uploadFile(event, memoryBuffer);
                remove(upload);
                backendService.editHomework(new HomeworkDto(homeworkDto.getId(), homeworkDto.getContent(),
                        homeworkDto.getDeadline(), !homeworkDto.getIsDone(), homeworkDto.getStudentId(), homeworkDto.getSubject()));
                close();
            });
            add(upload);
        }
        open();
    }

    private void createOkButton() {
        Button okButton = new Button("Ok", event -> {
            remove(background);
            close();
        });
        okButton.setHeight("50px");
        okButton.setWidth("75px");
        background.add(okButton);
    }

}
