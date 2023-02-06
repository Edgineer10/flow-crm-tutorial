package com.example.application.views.list;

import com.example.application.data.entity.Post;
import com.vaadin.flow.component.ComponentEvent;
import com.vaadin.flow.component.ComponentEventListener;
import com.vaadin.flow.component.Key;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.datepicker.DatePicker;
import com.vaadin.flow.component.formlayout.FormLayout;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.textfield.TextArea;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.data.binder.BeanValidationBinder;
import com.vaadin.flow.data.binder.Binder;
import com.vaadin.flow.data.binder.ValidationException;
import com.vaadin.flow.shared.Registration;

import java.time.LocalDate;

public class PostForm extends FormLayout {

    Binder<Post> binder = new BeanValidationBinder<>(Post.class);
    TextField postTitle = new TextField("タイトル");
    TextArea postDesc = new TextArea("内容");
    DatePicker postDate = new DatePicker("日付");

    Button save = new Button("保存");
    Button delete = new Button("デェリート");
    Button close = new Button("キャンセル");
    private Post post;

    public PostForm() {
        addClassName("contact-form");
        binder.bindInstanceFields(this);
        postDate.setValue(LocalDate.now());
        postDesc.setHeight("100px");
        add(postTitle,
                postDesc,
                postDate,
                createButtonsLayout());
    }

    private HorizontalLayout createButtonsLayout() {
        save.addThemeVariants(ButtonVariant.LUMO_PRIMARY);
        delete.addThemeVariants(ButtonVariant.LUMO_ERROR);
        close.addThemeVariants(ButtonVariant.LUMO_TERTIARY);

        save.addClickShortcut(Key.ENTER);
        close.addClickShortcut(Key.ESCAPE);
        save.addClickListener(event -> validateAndSave());
        delete.addClickListener(event -> fireEvent(new DeleteEvent(this, post)));
        close.addClickListener(event -> fireEvent(new CloseEvent(this)));

        binder.addStatusChangeListener(e -> save.setEnabled(binder.isValid()));

        return new HorizontalLayout(save, delete, close);
    }

    private void validateAndSave() {
        try {
            binder.writeBean(post);
            fireEvent(new SaveEvent(this, post));
        } catch (ValidationException e) {
            e.printStackTrace();
        }
    }

    public void setPost(Post post) {
        this.post = post;
        binder.readBean(post);
    }

    // Events
    public static abstract class PostFormEvent extends ComponentEvent<PostForm> {
        private Post post;

        protected PostFormEvent(PostForm source, Post post) {
            super(source, false);
            this.post = post;
        }

        public Post getPost() {
            return post;
        }
    }

    public static class SaveEvent extends PostFormEvent {
        SaveEvent(PostForm source, Post post) {
            super(source, post);
        }
    }

    public static class DeleteEvent extends PostFormEvent {
        DeleteEvent(PostForm source, Post post) {
            super(source, post);
        }

    }

    public static class CloseEvent extends PostFormEvent {
        CloseEvent(PostForm source) {
            super(source, null);
        }
    }

    public <T extends ComponentEvent<?>> Registration addListener(Class<T> eventType, ComponentEventListener<T> listener) {
        return getEventBus().addListener(eventType, listener);
    }
}