package com.ilearn.components;

import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.html.H1;
import com.vaadin.flow.component.html.Label;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;

import java.util.stream.Stream;

public class NavigationBar extends HorizontalLayout {
    private Button homeButton;
    private Button registerButton;
    private Button loginButton;
    private Button aboutButton;
    private Label emptyArea;
    private H1 logo;

    public NavigationBar() {
        setWidthFull();
        setSpacing(false);

        createButtons();
        configureAndAddButtons();

        createEmptyArea();
        createLogo();
        add(emptyArea, logo);
    }

    public void createButtons() {
        homeButton = new Button("Home", event -> getUI().ifPresent(ui -> ui.navigate("home")));
        registerButton = new Button("Register", event -> getUI().ifPresent(ui -> ui.navigate("register")));
        loginButton = new Button("Login", event -> getUI().ifPresent(ui -> ui.navigate("login")));
        aboutButton = new Button("About", event -> getUI().ifPresent(ui -> ui.navigate("about")));
    }

    public void configureAndAddButtons() {
        Stream.of(homeButton, registerButton, loginButton, aboutButton)
                .forEach(button -> {
                    button.setWidth("300px");
                    button.setHeight("80px");
                    button.setAutofocus(true);
                    add(button);
                });
        homeButton.setIconAfterText(true);
    }

    public void createEmptyArea() {
        emptyArea = new Label();
        emptyArea.setWidth("15%");
    }

    public void createLogo() {
        logo = new H1("i-learn");
        logo.setTitle("i-learn");
    }
}
