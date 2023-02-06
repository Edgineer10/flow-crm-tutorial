package com.example.application.views.list;


import com.example.application.data.entity.Contact;
import com.example.application.data.entity.Post;
import com.example.application.data.service.CrmService;
import com.example.application.data.service.PostsService;
import com.example.application.views.MainLayout;
import com.example.application.views.PostAdminLayout;
import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.data.value.ValueChangeMode;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import org.springframework.security.access.prepost.PreAuthorize;

import javax.annotation.security.PermitAll;
import javax.annotation.security.RolesAllowed;
import java.time.LocalDate;

@RolesAllowed("ADMIN")
@Route(value = "", layout = PostAdminLayout.class)
@PageTitle("Posts | TSTIA")
public class PostListView extends VerticalLayout {
    Grid<Post> grid = new Grid<>(Post.class);
    TextField filterText = new TextField();

    PostForm form;
    private PostsService service;

    public PostListView(PostsService service) {
        this.service=service;
        addClassName("list-view");
        setSizeFull();
        configureGrid();
        configureForm();

        add(getToolbar(), getContent());

        updateList();
        closeEditor();
    }

    private void closeEditor() {
        form.setPost(null);
        form.setVisible(false);
        removeClassNames("editing");
    }

    private void updateList() {
        grid.setItems(service.findAllPosts(filterText.getValue()));
    }

    private Component getContent() {
        HorizontalLayout content = new HorizontalLayout(grid,form);
        content.setFlexGrow(2,grid);
        content.setFlexGrow(1,form);
        content.addClassName("content");
        content.setSizeFull();
        return content;
    }

    private void configureForm() {
        form=new PostForm();
        form.setWidth("45em");
        form.addListener(PostForm.SaveEvent.class, this::savePost);
        form.addListener(PostForm.DeleteEvent.class, this::deletePost);
        form.addListener(PostForm.CloseEvent.class, e -> closeEditor());
    }
    private void savePost(PostForm.SaveEvent event) {
        service.savePost(event.getPost());
        updateList();
        closeEditor();
    }

    private void deletePost(PostForm.DeleteEvent event) {
        service.deletePost(event.getPost());
        updateList();
        closeEditor();
    }
    private void configureGrid() {
        grid.addClassNames("contact-grid");
        grid.setSizeFull();

        grid.setColumns("postTitle", "postDesc", "postDate");
        grid.getColumnByKey("postTitle").setHeader("Title");
        grid.getColumnByKey("postDesc").setHeader("Description");
        grid.getColumnByKey("postDate").setHeader("Date");
        grid.getColumns().forEach(col -> col.setAutoWidth(true));
        grid.asSingleSelect().addValueChangeListener(event ->
                editPost(event.getValue()));
    }

    private void editPost(Post post) {
        if (post == null) {
            closeEditor();
        } else {
            form.setPost(post);
            form.setVisible(true);
            form.postDate.setValue(LocalDate.now());
            addClassName("editing");
        }
    }

    private HorizontalLayout getToolbar() {
        filterText.setPlaceholder("キーワードでサーチ");
        filterText.setClearButtonVisible(true);
        filterText.setValueChangeMode(ValueChangeMode.LAZY);
        filterText.addValueChangeListener(e->updateList());

        Button addPostButton = new Button("新たなポストを入力");
        addPostButton.addClickListener(click -> addContact());
        HorizontalLayout toolbar = new HorizontalLayout(filterText, addPostButton);
        toolbar.addClassName("toolbar");
        return toolbar;
    }

    private void addContact() {
        grid.asSingleSelect().clear();
        editPost(new Post());
    }
}