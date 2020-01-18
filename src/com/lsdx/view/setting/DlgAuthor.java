package com.lsdx.view.setting;

import com.lsdx.context.AppContext;
import com.lsdx.data.PackageConfig;
import com.lsdx.view.widget.LabelFiled;
import com.lsdx.view.widget.Window;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import org.apache.commons.lang3.StringUtils;

/**
 * @Author: nhsoft.lsd
 * @Description:
 * @Date:Create：in 2020-01-18 20:02
 * @Modified By：
 */
public class DlgAuthor extends Stage {

    private TextField tdAuthor;

    private VBox vBox;

    private Button btnSave;
    private Button btnClose;

    public DlgAuthor(){


        vBox = new VBox();

        Scene scene = new Scene(vBox, 400, 100);
        setScene(scene);
        sizeToScene();

        tdAuthor = new TextField();
        tdAuthor.setMinWidth(300);
        LabelFiled lfEntityPath = new LabelFiled("作者：", tdAuthor);
        lfEntityPath.setLabelWidth(70);
        vBox.getChildren().add(lfEntityPath);

        btnSave = new Button("保存");
        btnSave.setMaxWidth(100);
        btnSave.setMinWidth(100);
        btnSave.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {

                String author = tdAuthor.getText();
                if(StringUtils.isEmpty(author)){
                    Window.show("请输入作者");
                    return;
                }

                try {
                    AppContext.AUTHOR_SERVICE.save(author);
                    Window.show("保存成功! ");
                    close();
                }catch (Exception e){
                    Window.showError("保存失败! ");
                }


            }
        });


        btnClose = new Button("关闭");
        btnClose.setMaxWidth(100);
        btnClose.setMinWidth(100);
        btnClose.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                close();
            }
        });
        HBox.setMargin(btnClose, new Insets(0, 0, 0, 10));


        HBox hboxBtn = new HBox();
        hboxBtn.setPadding(new Insets(4, 0, 0, 0));
        hboxBtn.setAlignment(Pos.BASELINE_CENTER);
        hboxBtn.getChildren().add(btnSave);
        hboxBtn.getChildren().add(btnClose);

        vBox.getChildren().add(hboxBtn);


        try {
            String author = AppContext.AUTHOR_SERVICE.read();
            tdAuthor.setText(author);
        }catch (Exception e){

        }
    }
}
