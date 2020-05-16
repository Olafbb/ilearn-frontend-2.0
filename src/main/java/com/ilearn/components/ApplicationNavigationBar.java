package com.ilearn.components;

import com.vaadin.flow.component.HasComponents;
import com.vaadin.flow.component.contextmenu.MenuItem;
import com.vaadin.flow.component.contextmenu.SubMenu;
import com.vaadin.flow.component.icon.Icon;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.menubar.MenuBar;


public class ApplicationNavigationBar extends MenuBar {
    private MenuItem more;
    private SubMenu settingsSubMenu;

    public ApplicationNavigationBar() {
        createSubmenu();
        addItem("Start", event -> getUI().ifPresent(ui -> ui.navigate("")));
    }

    private void createSubmenu() {
        more = addItem(new Icon(VaadinIcon.BULLETS));
        settingsSubMenu = more.getSubMenu();
        settingsSubMenu.addItem("settings", event -> getUI().ifPresent(ui -> ui.navigate("settings")));
        settingsSubMenu.addItem("log out", event -> {
            getUI().ifPresent(ui -> ui.getPage().setLocation("http://localhost:8888/logout"));
            getUI().ifPresent(HasComponents::removeAll);
            getUI().ifPresent(ui -> ui.getSession().close());
            getUI().ifPresent(ui -> ui.getPage().setLocation("home"));
        });
    }

}
