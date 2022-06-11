package com.prio.pariwisata.model;

public class Model_Menu {

    public static Model_Menu sg;
    public static boolean isExist(){
        return Model_Menu.sg != null;
    }

    public static int selected_menu;

    public String menu_name,menu_icon_name,urls;
    public int menu_status;
}
