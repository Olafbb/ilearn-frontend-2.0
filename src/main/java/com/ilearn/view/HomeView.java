package com.ilearn.view;

import com.ilearn.components.NavigationBar;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;


@Route(value = "home")
@PageTitle("Home | i-learn")
public class HomeView extends VerticalLayout {
    private NavigationBar navigationBar;
    private Div homeDiv;
    private Div verticalEmptyArea;
    private Div horizontalEmptyArea;
    private HorizontalLayout background;
    private static String HOME_TEXT = "Project created with Spring and vaadin frameworks, to properly use remember to have backend launched.";

    public HomeView() {
        navigationBar = new NavigationBar();
        add(navigationBar);
        createComponents();
        createLayouts();
        createHorizontalEmptyArea();
        createVerticalEmptyArea();
        configureLayouts();
        configureComponents();
        configureLayouts();
    }
    private void createComponents() {
        homeDiv = new Div();
    }
    private void createLayouts() {
        background=new HorizontalLayout();
    }
    private void configureComponents() {
        homeDiv.add(HOME_TEXT);
    }
    private void configureLayouts() {
        add(verticalEmptyArea);
        background.add(horizontalEmptyArea, homeDiv);
        add(background);
    }
    private void createVerticalEmptyArea() {
        verticalEmptyArea = new Div();
        verticalEmptyArea.setHeight("50px");
    }
    private void createHorizontalEmptyArea() {
        horizontalEmptyArea = new Div();
        horizontalEmptyArea.setWidth("200px");
    }
}
