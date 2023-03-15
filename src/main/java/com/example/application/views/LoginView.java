package com.example.application.views;

import com.example.application.data.entity.User;
import com.example.application.security.SecurityService;
import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.html.H1;
import com.vaadin.flow.component.login.LoginForm;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.BeforeEnterEvent;
import com.vaadin.flow.router.BeforeEnterListener;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;

import java.util.Optional;

@Route("login")
@PageTitle("TSTIA会員ログイン")
public class LoginView extends VerticalLayout implements BeforeEnterListener {

    private LoginForm login= new LoginForm();

    private final SecurityService securityService;
    public LoginView(SecurityService securityService){
        this.securityService = securityService;
        addClassName("login-view");
        setSizeFull();
        setAlignItems(Alignment.CENTER);
        setJustifyContentMode(JustifyContentMode.CENTER);
        login.setForgotPasswordButtonVisible(false);
        login.setAction("login");

        //checks if already login
        Optional<User> maybeUser = securityService.get();
        if (maybeUser.isPresent()){
            UI.getCurrent().getPage().setLocation("/list");
        }

        add(new H1("TSTIA会員ログイン"), login);
    }
    @Override
    public void beforeEnter(BeforeEnterEvent beforeEnterEvent) {


        // inform the user about an authentication error
        if(beforeEnterEvent.getLocation()
                .getQueryParameters()
                .getParameters()
                .containsKey("error")) {
            login.setError(true);
        }
    }


    }

