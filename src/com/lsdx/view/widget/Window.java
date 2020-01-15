package com.lsdx.view.widget;

import javafx.scene.control.Alert;

/**
 * @Author: nhsoft.lsd
 * @Description:
 * @Date:Create：in 2020-01-09 18:25
 * @Modified By：
 */
public class Window {

    public static void showWaring(String message){
        Alert alert = new Alert(Alert.AlertType.WARNING);
        alert.titleProperty().set("警告");
        alert.headerTextProperty().set(message);
        alert.showAndWait();
    }

    public static void showError(String message){
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.titleProperty().set("错误");
        alert.headerTextProperty().set(message);
        alert.showAndWait();
    }

    public static void show(String message){
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.titleProperty().set("信息");
        alert.headerTextProperty().set(message);
        alert.showAndWait();
    }
}
