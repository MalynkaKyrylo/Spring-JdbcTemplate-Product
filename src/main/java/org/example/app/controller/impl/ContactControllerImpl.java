package org.example.app.controller.impl;

import org.example.app.controller.BaseController;
import org.example.app.entity.contact.Contact;
import org.example.app.service.impl.ContactServiceImpl;
import org.example.app.utils.starter.AppStarter;
import org.example.app.utils.Constants;
import org.example.app.view.contact.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Map;

@Component("contactController")
public class ContactControllerImpl implements BaseController {

    @Autowired
    ContactMenuView menuView;
    @Autowired
    ContactCreateView createView;
    @Autowired
    ContactReadView readView;
    @Autowired
    ContactReadByIdView readByIdView;
    @Autowired
    ContactUpdateView updateView;
    @Autowired
    ContactDeleteView deleteView;
    @Autowired
    ContactServiceImpl serviceImpl;

    public ContactControllerImpl() {

    }

    public void getOption() {
        int option = Integer.parseInt(menuView.getOption());
        switch (option) {
            case 1 -> create();
            case 2 -> getAll();
            case 3 -> getById();
            case 4 -> update();
            case 5 -> delete();
            case 0 -> menuView.getOutput(Constants.APP_CLOSE_MSG);
        }
    }

    public void create() {
        String[] data = createView.getData();
        Contact contact = new Contact(data[0], data[1], data[2]);
        createView.getOutput(serviceImpl.create(contact));
        AppStarter.startApp();
    }

    public void getAll() {
        readView.getOutput(serviceImpl.getAll());
        AppStarter.startApp();
    }

    public void getById() {
        readByIdView.getOutput(serviceImpl
                .getById(readByIdView.getData()));
        AppStarter.startApp();
    }

    public void update() {
        Map<String, String> data = updateView.getData();
        Contact contact = new Contact(Integer.parseInt(data.get("id")),
                data.get("firstName"), data.get("lastName"), data.get("phone"));
        updateView.getOutput(serviceImpl.update(contact));
        AppStarter.startApp();
    }

    public void delete() {
        deleteView.getOutput(serviceImpl
                .delete(deleteView.getData()));
        AppStarter.startApp();
    }
}
