package com.ilearn.view;

import com.ilearn.components.NavigationBar;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;

@Route(value = "reset-password")
@PageTitle("Reset password | i-learn")
public class ResetPasswordView extends VerticalLayout {
    public ResetPasswordView() {
        NavigationBar navigationBar = new NavigationBar();
        add(navigationBar);
    }
}
