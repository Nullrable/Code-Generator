package com.lsdx.view.widget;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Control;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;

/**
 * @Author: nhsoft.lsd
 * @Description:
 * @Date:Create：in 2020-01-09 17:28
 * @Modified By：
 */
public class LabelFiled extends HBox {

    private Label label;

    public LabelFiled(String labelName, Control widget){

        this(labelName);

        getChildren().add(widget);
    }

    public LabelFiled(String labelName){

        setAlignment(Pos.CENTER_LEFT);
        setPadding(new Insets(4, 0, 4, 0));

        label = new Label(labelName);
        label.setMinWidth(75);
        label.setMaxWidth(75);
        label.setAlignment(Pos.CENTER_RIGHT);

        getChildren().add(label);
    }

    public void setLabelWidth(int width){
        label.setMinWidth(width);
        label.setMaxWidth(width);
    }

}
