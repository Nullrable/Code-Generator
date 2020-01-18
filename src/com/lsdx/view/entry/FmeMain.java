package com.lsdx.view.entry;

import com.lsdx.context.AppContext;
import com.lsdx.data.Template;
import com.lsdx.view.setting.DlgAuthor;
import com.lsdx.view.setting.DlgPackageConfig;
import com.lsdx.view.setting.DlgDatasourceSetting;
import com.lsdx.view.setting.DlgTemplateConfig;
import com.lsdx.view.widget.LabelFiled;
import com.lsdx.view.widget.Window;
import javafx.application.Platform;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.DirectoryChooser;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import org.apache.commons.lang3.StringUtils;

import java.io.File;


public class FmeMain extends BorderPane {

    private  MenuBar menuBar;

    private VBox vBox;

    private TextField tdOutput;
    private TextArea taTables;
    private CheckBox chkAll;

    private Stage primaryStage;

    public FmeMain(){

        menuBar = new MenuBar();
        setTop(menuBar);

        // File menu - new, save, exit
        Menu fileMenu = new Menu("设置");
        MenuItem itemDatasource = new MenuItem("数据库设置");
        MenuItem itemPathConfig = new MenuItem("包名设置");
        MenuItem itemTemplateConfig = new MenuItem("模板设置");
        MenuItem itemAuthorConfig = new MenuItem("作者设置");
        MenuItem exitMenuItem = new MenuItem("退出");

        fileMenu.getItems().addAll(itemDatasource, itemPathConfig, itemTemplateConfig, itemAuthorConfig,
                new SeparatorMenuItem(), exitMenuItem);

        itemDatasource.setOnAction(event -> {

            Stage  stage = new Stage();

            DlgDatasourceSetting fmeSetting = new DlgDatasourceSetting(stage);
            fmeSetting.fill();
            Scene scene = new Scene(fmeSetting, 450, 300);
            stage.setScene(scene);
            stage.sizeToScene();
            stage.show();



        });


        itemPathConfig.setOnAction(event -> {
            DlgPackageConfig dlgPathConfig = new DlgPackageConfig();
            dlgPathConfig.show();
        });

        itemTemplateConfig.setOnAction(event -> {

            DlgTemplateConfig dlgTemplateConfig = new DlgTemplateConfig();
            dlgTemplateConfig.show();
        });

        itemAuthorConfig.setOnAction(event -> {

            DlgAuthor dlgAuthor = new DlgAuthor();
            dlgAuthor.show();

        });

        exitMenuItem.setOnAction(actionEvent -> Platform.exit());

        menuBar.getMenus().add(fileMenu);


        vBox = new VBox();

        taTables = new TextArea();
        taTables.setMaxWidth(400);
        taTables.setWrapText(true);
        taTables.setPromptText("请输入需要执行的表，逗号隔开");
        LabelFiled lfTables = new LabelFiled("Tables：", taTables);
        lfTables.setLabelWidth(100);
        vBox.getChildren().add(lfTables);

        chkAll = new CheckBox();
        chkAll.setText("是否所有表");
        LabelFiled lfAll = new LabelFiled("", chkAll);
        lfAll.setLabelWidth(100);
        vBox.getChildren().add(lfAll);


        HBox hBox = new HBox();
        tdOutput = new TextField();
        tdOutput.setMinWidth(400);
        tdOutput.setMinWidth(400);
        LabelFiled lfOutput = new LabelFiled("输出目录：", tdOutput);
        lfOutput.setLabelWidth(100);
        hBox.getChildren().add(lfOutput);

        Button btnDir = new Button("选择");
        btnDir.setMinWidth(60);
        HBox.setMargin(btnDir, new Insets(4, 0, 0, 4));
        btnDir.setOnAction(event -> {

            DirectoryChooser directoryChooser = new DirectoryChooser();
            File file = directoryChooser.showDialog(primaryStage);
            String filePath = file.getPath();

            tdOutput.setText(filePath);

            AppContext.OUTPUT_PATH_SERVICE.save(filePath);
        });

        hBox.getChildren().add(btnDir);
        vBox.getChildren().add(hBox);


        Button btnGenerate = new Button("执行");
        btnGenerate.setMinWidth(170);
        btnGenerate.setOnAction(event -> {


            String filePath = tdOutput.getText();

            if(StringUtils.isEmpty(filePath)){
                Window.showWaring("输出目录不允许为空");
                return;
            }

            String tables = taTables.getText();

            if (StringUtils.isEmpty(tables)) {
                Window.showWaring("表不允许为空");
                return;
            }

            String[] tableArry = tables.split(",");

            for(String table : tableArry){
                try {
                    AppContext.FILE_SERVICE.generate(table, filePath);
                }catch (Exception e){
                    Window.showError(e.getMessage());
                    return;
                }

            }

            Window.show("执行成功！");

        });

        Button btnOpen = new Button("打开生成文件所在位置");
        btnOpen.setMinWidth(170);
        btnOpen.setOnAction(event -> {

            String filePath = tdOutput.getText();

            if(StringUtils.isEmpty(filePath)){
                return;
            }

            FileChooser fileChooser = new FileChooser();
            fileChooser.showOpenMultipleDialog(primaryStage);
            fileChooser.setInitialDirectory(new File(filePath));

        });

        HBox.setMargin(btnOpen, new Insets(0, 0, 0, 10));

        HBox hboxBtn = new HBox();
        hboxBtn.setPadding(new Insets(4, 0, 0, 0));
        hboxBtn.setAlignment(Pos.BASELINE_CENTER);
        hboxBtn.getChildren().add(btnGenerate);
        hboxBtn.getChildren().add(btnOpen);

        vBox.getChildren().add(hboxBtn);

        setCenter(vBox);


        tdOutput.setText(AppContext.OUTPUT_PATH_SERVICE.read());

    }

    public void bind(Stage primaryStage){
        this.primaryStage = primaryStage;
        menuBar.prefWidthProperty().bind(primaryStage.widthProperty());
    }
}
