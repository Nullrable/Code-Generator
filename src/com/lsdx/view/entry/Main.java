package com.lsdx.view.entry;

import com.lsdx.context.ViewContext;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

public class Main extends Application {

    @Override
    public void start(Stage primaryStage) {

        FmeMain fmeMain = ViewContext.getInstance().getFmeMain();
        fmeMain.bind(primaryStage);

        Scene scene = new Scene(fmeMain, 600, 450, Color.rgb(0, 0, 0, 0));
        primaryStage.setScene(scene);
        primaryStage.show();


    }


    public static void main(String[] args) {
        launch(args);
    }
}
