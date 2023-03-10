package com.example.application;

import com.vaadin.flow.component.dependency.NpmPackage;
import com.vaadin.flow.component.page.AppShellConfigurator;
import com.vaadin.flow.server.PWA;
import com.vaadin.flow.theme.Theme;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * The entry point of the Spring Boot application.
 *
 * Use the @PWA annotation make the application installable on phones, tablets
 * and some desktop browsers.
 *
 */
@SpringBootApplication
@Theme(value = "flowcrmtutorial")
@NpmPackage(value = "line-awesome", version = "1.3.0")
@PWA(
        name = "Edge Sample CRM",
        shortName = "CRM",
        offlinePath="offline.html",
        iconPath = "icons/tstia.png",
        offlineResources = { "./images/offline.png"}
)

public class Application implements AppShellConfigurator {

    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }

}
