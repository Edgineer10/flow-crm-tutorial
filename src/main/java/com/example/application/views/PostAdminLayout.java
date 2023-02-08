package com.example.application.views;

import com.example.application.data.entity.User;
import com.example.application.security.SecurityService;
import com.vaadin.flow.component.applayout.AppLayout;
import com.vaadin.flow.component.avatar.Avatar;
import com.vaadin.flow.component.contextmenu.MenuItem;
import com.vaadin.flow.component.html.Anchor;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.html.H1;
import com.vaadin.flow.component.icon.Icon;
import com.vaadin.flow.component.menubar.MenuBar;
import com.vaadin.flow.component.orderedlayout.FlexComponent;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.server.StreamResource;

import javax.annotation.security.RolesAllowed;
import java.io.ByteArrayInputStream;
import java.util.Optional;

@RolesAllowed("ADMIN")
public class PostAdminLayout extends AppLayout {
    private final SecurityService securityService;


    public PostAdminLayout(SecurityService securityService ){
        this.securityService = securityService;
        createHeader();
//        createDrawer();
    }
/*
    private void createDrawer() {
        RouterLink postlistLink = new RouterLink("Posts", PostListView.class);
        postlistLink.setHighlightCondition(HighlightConditions.sameLocation());
        addToDrawer(new VerticalLayout( postlistLink
        ));
    }*/

    private void createHeader() {
        H1 logo = new H1("お知らせデータベース");
        logo.addClassNames("text-l", "m-m");


        HorizontalLayout header = new HorizontalLayout(logo);


        Optional<User> maybeUser = securityService.get();
        if (maybeUser.isPresent()) {
            User user = maybeUser.get();

            Avatar avatar = new Avatar(user.getName());
            StreamResource resource = new StreamResource("profile-pic",
                    () -> new ByteArrayInputStream(user.getProfilePicture()));
            avatar.setImageResource(resource);
            avatar.setThemeName("xsmall");
            avatar.getElement().setAttribute("tabindex", "-1");

            MenuBar userMenu = new MenuBar();
            userMenu.setThemeName("tertiary-inline contrast");

            MenuItem userName = userMenu.addItem("");
            Div div = new Div();
            div.add(avatar);
            div.add(user.getName());
            div.add(new Icon("lumo", "dropdown"));
            div.getElement().getStyle().set("display", "flex");
            div.getElement().getStyle().set("align-items", "center");
            div.getElement().getStyle().set("gap", "var(--lumo-space-s)");
            userName.add(div);
            userName.getSubMenu().addItem("Log out", e -> {
                securityService.logout();
            });

            header.add(userMenu);
        } else {
            Anchor loginLink = new Anchor("login", "Sign in");
            header.add(loginLink);
        }

        header.setDefaultVerticalComponentAlignment(FlexComponent.Alignment.CENTER);
        header.expand(logo);
        header.setWidth("100%");
        header.addClassNames("py-0", "px-m");

        addToNavbar(header);
    }
}
