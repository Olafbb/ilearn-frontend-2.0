package com.ilearn.view;

import com.ilearn.components.ApplicationNavigationBar;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;

@Route(value = "settings")
@PageTitle("Settings | i-learn")
public class SettingsView extends VerticalLayout {
    public SettingsView() {
        add(new ApplicationNavigationBar());
    }
}
