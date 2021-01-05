package web;

import java.util.HashMap;
import java.util.Map;

public class Menu {
    private static Map<String, MenuItem[]> items = new HashMap<>();
    static {
        items.put("admin", new MenuItem[] {
            new MenuItem("Пользователи", "/user/index.html")
        });
    }
    public static MenuItem[] getMenu(String role) {
        return items.get(role);
    }
}