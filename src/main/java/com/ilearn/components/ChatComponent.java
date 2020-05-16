package com.ilearn.components;

import com.ilearn.dto.ChatMessageDto;
import com.ilearn.service.BackendService;
import com.ilearn.user.ActualUser;
import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.Key;
import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.html.H1;
import com.vaadin.flow.component.html.Paragraph;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.TextField;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class ChatComponent extends VerticalLayout {
    private ActualUser actualUser;
    private BackendService backendService;
    private MessageList messageList;
    private static final Logger LOGGER = LoggerFactory.getLogger(ChatComponent.class);

    public ChatComponent(BackendService backendService) {
        this.backendService = backendService;
        this.actualUser = new ActualUser();
        addClassName("chat-view");
        setSizeFull();
        H1 header = new H1("Chat");
        setHorizontalComponentAlignment(Alignment.CENTER, header);
        add(header);
        showChat();
        refresh();
    }
    private void refresh() {
        UI ui = UI.getCurrent();
        ui.setPollInterval(1000);
        ui.addPollListener(event -> getChat());
    }

    private void showChat() {
        messageList = new MessageList();
        add(messageList, createInputLayout());
        expand(messageList);

        backendService.getMessages().forEach(message -> {
            messageList.add(new Paragraph("(" + message.getTime() + ") " + message.getFrom() + ": " +
                    message.getMessage()));
        });
        scrollToNewMessage();
    }

    public Component createInputLayout() {
        HorizontalLayout inputLayout = new HorizontalLayout();
        inputLayout.setWidth("100%");
        TextField messageField = new TextField();
        Button sendButton = new Button("Send!");
        sendButton.addThemeName(ButtonVariant.LUMO_PRIMARY.getVariantName());
        sendButton.addClickShortcut(Key.ENTER);
        inputLayout.add(messageField, sendButton);
        inputLayout.expand(messageField);

        sendButton.addClickListener(event -> {
            backendService.createMessage(new ChatMessageDto(actualUser.getUsername(), LocalDateTime.now(), messageField.getValue()));
            messageList.removeAll();
            getChat();
            messageField.clear();
            messageField.focus();
            scrollToNewMessage();
        });
        messageField.focus();
        return inputLayout;
    }

    private void getChat() {
        messageList.removeAll();
        backendService.getMessages().forEach(message -> {
            messageList.add(new Paragraph("(" + message.getTime().format(DateTimeFormatter.ISO_LOCAL_DATE)
                    + " " + message.getTime().getHour() + ":"+ message.getTime().getSecond() + ") " + message.getFrom() + ": " +
                    message.getMessage()));
        });
    }
    private void scrollToNewMessage() {
        try {
            messageList.getChildren().skip(messageList.getChildren().count() - 1).findFirst().ifPresent(component -> component.getElement().callJsFunction("scrollIntoView"));
        } catch (IllegalArgumentException e) {
            LOGGER.info("There are no messages");
        }
    }
}
