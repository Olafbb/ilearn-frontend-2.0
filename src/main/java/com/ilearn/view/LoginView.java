package com.ilearn.view;

import com.ilearn.components.NavigationBar;
import com.vaadin.flow.component.html.Label;
import com.vaadin.flow.component.login.LoginForm;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.BeforeEnterEvent;
import com.vaadin.flow.router.BeforeEnterObserver;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.theme.Theme;
import com.vaadin.flow.theme.material.Material;

import java.util.Collections;

@Route(value = "login")
@PageTitle("Login | i-learn")
@Theme(value = Material.class, variant = Material.LIGHT)
public class LoginView extends VerticalLayout implements BeforeEnterObserver {
    private LoginForm login;
    private NavigationBar navigationBar;
    private HorizontalLayout background;

    public LoginView() {
        addClassName("login-view");
        setSizeFull();
        createComponents();
        configureComponents();
        createLayouts();
        configureLayouts();
        setHorizontalComponentAlignment(Alignment.START, navigationBar);
        add(navigationBar);
        add(background);

    }

    @Override
    public void beforeEnter(BeforeEnterEvent event) {
        if (!event.getLocation()
                .getQueryParameters()
                .getParameters()
                .getOrDefault("error", Collections.emptyList())
                .isEmpty()) {
            login.setError(true);
        }
    }

    private void createComponents() {
        login = new LoginForm();
        navigationBar = new NavigationBar();
    }

    private void createLayouts() {
        background = new HorizontalLayout();
    }

    private void configureComponents() {
        login.addForgotPasswordListener(event -> event.getSource().getUI().ifPresent(ui -> ui.navigate("reset-password")));
        login.setAction("login");
    }

    private void configureLayouts() {
        background.setWidthFull();
        Label emptyArea = new Label();
        emptyArea.setWidth("21%");
        background.add(emptyArea, login);
    }
}
