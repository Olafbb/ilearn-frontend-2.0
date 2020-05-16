package com.ilearn.view;

import com.ilearn.components.DialogComponent;
import com.ilearn.components.NavigationBar;
import com.ilearn.dto.UserDto;
import com.ilearn.service.BackendService;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.html.H1;
import com.vaadin.flow.component.html.Label;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.EmailField;
import com.vaadin.flow.component.textfield.PasswordField;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Route(value = "register")
@PageTitle("Register | i-learn")
public class RegisterView extends VerticalLayout {
    private BackendService backendService;
    private PasswordEncoder encoder;
    private VerticalLayout registerLayout;
    private HorizontalLayout background;

    private Button registerButton;
    private EmailField emailField;
    private PasswordField passwordField;
    private TextField idField;
    private H1 signUpLabel;
    private NavigationBar navigationBar;
    private Label emptyArea;

    public RegisterView(BackendService backendService) {
        this.backendService = backendService;
        this.encoder = PasswordEncoderFactories.createDelegatingPasswordEncoder();

        createComponents();
        createLayouts();
        configureComponents();

        setHorizontalComponentAlignment(Alignment.START, navigationBar);
        add(navigationBar);
        registerLayout.add(signUpLabel, emailField, passwordField, idField, registerButton);
        background.add(emptyArea, registerLayout);
        background.setWidthFull();
        add(background);
    }

    private void createComponents() {
        registerButton = new Button("Register new account");
        registerLayout = new VerticalLayout();
        emailField = new EmailField();
        passwordField = new PasswordField();
        signUpLabel = new H1("Sign up");
        idField = new TextField();
        navigationBar = new NavigationBar();
        emptyArea = new Label();

    }

    private void createLayouts() {
        background = new HorizontalLayout();
    }

    private void configureComponents() {
        registerLayout.setSizeFull();
        registerLayout.setSpacing(true);

        emailField.setLabel("Enter e-mail");
        emailField.setPlaceholder("e-mail");

        passwordField.setLabel("Enter Password");
        passwordField.setPlaceholder("password");

        idField.setLabel("Enter your student id");
        idField.setPlaceholder("id");

        registerButton.setWidth("200px");
        registerButton.addClickListener(event -> authenticateRegisterData());

        emptyArea.setWidth("30%");
    }

    private boolean validateEmail(String email) {
        String regex = "^[\\w-_\\.+]*[\\w-_\\.]\\@([\\w]+\\.)+[\\w]+[\\w]$";
        return email.matches(regex);
    }

    private boolean validatePassword(String password) {
        Pattern pattern = Pattern.compile("((?=.*[a-z])(?=.*\\d)(?=.*[A-Z])(?=.*[@#$%!]).{8,40})");
        Matcher matcher = pattern.matcher(password);
        return matcher.matches();
    }

    private boolean validateId(String id) {
        try {
            return Long.parseLong(id) > 0;
        } catch (NumberFormatException e) {
            return false;
        }
    }

    private boolean checkUserEmail(String email) {
        return backendService.getUsers().stream().anyMatch(userDto -> userDto.getEmail().equals(email));
    }

    private boolean checkStudent(Long id) {
        return backendService.checkStudentIdExist(id);
    }

    private boolean checkUserStudentId(Long id) {
        return backendService.getUsers().stream().anyMatch(userDto -> userDto.getStudentId().equals(id));
    }

    private void authenticateRegisterData() {
        if (!validateEmail(emailField.getValue()) || emailField.getValue().equals("")) {
            new DialogComponent().create("Insert correct e-mail ", true);
        } else if (!validatePassword(passwordField.getValue()) || passwordField.getValue().equals("")) {
            new DialogComponent().create("Insert correct password (min. 8 characters long, contains digits or upper", true);
        } else if (!validateId(idField.getValue()) || idField.getValue().equals("")) {
            new DialogComponent().create("Insert correct id (number)", true);
        } else if (checkUserEmail(emailField.getValue())) {
            new DialogComponent().create("Given e-mail is already taken", true);
        } else if (!checkStudent(Long.parseLong(idField.getValue()))) {
            new DialogComponent().create("There is no student with id equals " + Long.parseLong(idField.getValue()), true);
        } else if (checkUserStudentId(Long.parseLong(idField.getValue()))) {
            new DialogComponent().create("This id has been already taken by other account. Are you sure that you wrote correct id? If yes and problem still happens - just contact with us", true);
        } else {
            DialogComponent dialogComponent = new DialogComponent();
            backendService.createUser(new UserDto(1L, emailField.getValue(), encoder.encode(passwordField.getValue()), Long.parseLong(idField.getValue())));
            dialogComponent.create("Account was successfully created", new Button("Log in", event -> {
                getUI().ifPresent(ui -> ui.navigate("login"));
                dialogComponent.close();
            }), true);
        }
    }
}
