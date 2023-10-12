package org.example.app.controller;

import org.example.app.entity.Product;
import org.example.app.service.ProductService;
import org.example.app.utils.AppStarter;
import org.example.app.utils.Constants;
import org.example.app.view.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Map;

@Component("productController")
public class ProductController implements BaseController {

    @Autowired
    AppView appView;
    @Autowired
    ProductCreateView createView;
    @Autowired
    ProductReadView readView;
    @Autowired
    ProductReadByIdView readByIdView;
    @Autowired
    ProductUpdateView updateView;
    @Autowired
    ProductDeleteView deleteView;
    @Autowired
    ProductService serviceImpl;

    public ProductController() {

    }

    public void getOption() {
        int option = Integer.parseInt(appView.getOption());
        switch (option) {
            case 1 -> create();
            case 2 -> getAll();
            case 3 -> getById();
            case 4 -> update();
            case 5 -> delete();
            case 0 -> appView.getOutput(Constants.APP_CLOSE_MSG);
        }
    }

    public void create() {
        Map<String, String> data = createView.getData();
        Product product = new Product(
                data.get("name"),
                Integer.parseInt(data.get("quota")),
                Double.parseDouble(data.get("price")));
        createView.getOutput(serviceImpl.create(product));
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
        Product product = new Product(
                Integer.parseInt(data.get("id")),
                data.get("name"),
                Integer.parseInt(data.get("quota")),
                Double.parseDouble(data.get("price")));
        updateView.getOutput(serviceImpl.update(product));
        AppStarter.startApp();
    }

    public void delete() {
        deleteView.getOutput(serviceImpl
                .delete(deleteView.getData()));
        AppStarter.startApp();
    }
}
