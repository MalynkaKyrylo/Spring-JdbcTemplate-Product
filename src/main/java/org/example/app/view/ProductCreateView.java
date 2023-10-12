package org.example.app.view;

import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

@Component
public class ProductCreateView {

    public Map<String, String> getData() {
        Scanner scanner = new Scanner(System.in);
        Map<String, String> map = new HashMap<>();

        String title = "Input name of the product: ";
        System.out.print(title);
        map.put("name", scanner.nextLine().trim());

        title = "Input quota of the product: ";
        System.out.print(title);
        map.put("quota", scanner.nextLine().trim());

        title = "Input price of the product in format 0.00: ";
        System.out.print(title);
        map.put("price", scanner.nextLine().trim());
        return map;
    }

    public void getOutput(String output) {
        System.out.println(output);
    }
}
