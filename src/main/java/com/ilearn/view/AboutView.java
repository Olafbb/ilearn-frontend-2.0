package com.ilearn.view;

import com.ilearn.components.NavigationBar;
import com.ilearn.config.Links;
import com.vaadin.flow.component.html.Anchor;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;

@Route(value = "about")
@PageTitle("About | i-learn")
public class AboutView extends VerticalLayout {
    private NavigationBar navigationBar;
    private Anchor aboutMe;
    private Div verticalEmptyArea;
    private Div horizontalEmptyArea;
    private HorizontalLayout background;
    private static String ABOUT_TEXT = "Link to my github profile";
    private Links links;

    public AboutView(Links links) {
        navigationBar = new NavigationBar();
        this.links = links;
        add(navigationBar);
        createComponents();
        createLayouts();
        createHorizontalEmptyArea();
        createVerticalEmptyArea();
        configureLayouts();
    }

    private void createComponents() {
        aboutMe = new Anchor(links.getMyGithubAccount(), ABOUT_TEXT);
    }

    private void createLayouts() {
        background = new HorizontalLayout();
    }

    private void configureLayouts() {
        add(verticalEmptyArea);
        background.add(horizontalEmptyArea, aboutMe);
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
