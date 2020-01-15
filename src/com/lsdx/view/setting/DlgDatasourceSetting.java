package com.lsdx.view.setting;

import com.lsdx.context.AppContext;
import com.lsdx.data.DataSourceConfig;
import com.lsdx.data.DbType;
import com.lsdx.excetion.ClientException;
import com.lsdx.view.widget.LabelFiled;
import com.lsdx.view.widget.Window;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import org.apache.commons.lang3.StringUtils;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.prefs.Preferences;

/**
 * @Author: nhsoft.lsd
 * @Description:
 * @Date:Create：in 2020-01-09 17:06
 * @Modified By：
 */
public class DlgDatasourceSetting extends VBox {

    private ComboBox<DbType> cbbDataBase;
    private TextField tdUrl;
    private TextField tdUserName;
    private TextField tdPassword;

    private Button btnSave;
    private Button btnTest;
    private Button btnClose;

    private final Stage stage;

    public DlgDatasourceSetting(Stage stage){

        this.stage = stage;

        List<DbType> dbTypes = new ArrayList<DbType>();
        dbTypes.add( DbType.MYSQL );
        dbTypes.add( DbType.SQL_SERVER );
        dbTypes.add( DbType.ORACLE );
        dbTypes.add( DbType.POSTGRE_SQL );

        ObservableList list = FXCollections.observableList(dbTypes);

        cbbDataBase = new ComboBox<>();
        cbbDataBase.setItems(list);
        cbbDataBase.setMinWidth(300);
        cbbDataBase.setMaxWidth(300);
        cbbDataBase.setValue(DbType.MYSQL);

        LabelFiled lfCbb = new LabelFiled("数据库：", cbbDataBase);
        lfCbb.setLabelWidth(100);

        getChildren().add(lfCbb);

        tdUrl = new TextField();
        tdUrl.setMinWidth(300);
        tdUrl.setMaxWidth(300);

        LabelFiled lfUrl = new LabelFiled("数据库链接：", tdUrl);
        lfUrl.setLabelWidth(100);

        getChildren().add(lfUrl);

        tdUserName = new TextField();
        tdUserName.setMinWidth(300);
        tdUserName.setMaxWidth(300);

        LabelFiled lfUserName = new LabelFiled("用户名：", tdUserName);
        lfUserName.setLabelWidth(100);

        getChildren().add(lfUserName);

        tdPassword = new TextField();
        tdPassword.setMinWidth(300);
        tdPassword.setMaxWidth(300);

        LabelFiled lfPassword = new LabelFiled("密 码：", tdPassword);
        lfPassword.setLabelWidth(100);

        getChildren().add(lfPassword);


        btnSave = new Button("保存");
        btnSave.setMaxWidth(100);
        btnSave.setMinWidth(100);
        btnSave.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {

                try {

                    DataSourceConfig dataSourceConfig = null;
                    try {
                        dataSourceConfig = getDataSourceConfig();
                    }catch (ClientException e){
                        Window.showWaring(e.getMessage());
                        return;
                    }

                    AppContext.DATASOURCE_CONFIG_SERVICE.save(dataSourceConfig);

                    Window.show("保存成功");

                    if(stage != null){
                        stage.close();
                    }

                }catch (Exception e){
                    Window.showError(e.getMessage());
                }

            }
        });


        btnTest = new Button("测试");
        btnTest.setMaxWidth(100);
        btnTest.setMinWidth(100);
        btnTest.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {



                try {

                    DataSourceConfig dataSourceConfig = null;
                    try {
                        dataSourceConfig = getDataSourceConfig();
                    }catch (ClientException e){
                        Window.showWaring(e.getMessage());
                        return;
                    }

                    AppContext.DATASOURCE_SERVICE.ping(dataSourceConfig);

                    Window.show("测试成功");

                }catch (Exception e){
                    Window.showError(e.getClass().getSimpleName() + ": " + e.getMessage());
                }

            }
        });
        HBox.setMargin(btnTest, new Insets(0, 0, 0, 10));


        btnClose = new Button("关闭");
        btnClose.setMaxWidth(100);
        btnClose.setMinWidth(100);
        btnClose.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                //TODO
                stage.close();
            }
        });
        HBox.setMargin(btnClose, new Insets(0, 0, 0, 10));


        HBox hboxBtn = new HBox();
        hboxBtn.setPadding(new Insets(4, 0, 0, 0));
        hboxBtn.setAlignment(Pos.BASELINE_CENTER);
        hboxBtn.getChildren().add(btnSave);
        hboxBtn.getChildren().add(btnTest);
        hboxBtn.getChildren().add(btnClose);

        getChildren().add(hboxBtn);

    }


    public void fill(){
        DataSourceConfig dataSourceConfig = AppContext.DATASOURCE_CONFIG_SERVICE.read();

        if(dataSourceConfig == null){
            return;
        }

        tdUrl.setText(dataSourceConfig.getUrl());
        tdUserName.setText(dataSourceConfig.getUserName());
        tdPassword.setText(dataSourceConfig.getUserPassword());
        cbbDataBase.setValue(dataSourceConfig.getDbType());
    }

    public File getPersonFilePath() {
        Preferences prefs = Preferences.userNodeForPackage(DlgDatasourceSetting.class);
        String filePath = prefs.get("filePath", null);
        if (filePath != null) {
            return new File(filePath);
        } else {
            return null;
        }
    }

    public void setPersonFilePath(File file) {
        Preferences prefs = Preferences.userNodeForPackage(DlgDatasourceSetting.class);
        if (file != null) {
            prefs.put("filePath", file.getPath());

        } else {
            prefs.remove("filePath");

        }
    }


    private DataSourceConfig getDataSourceConfig(){

        DbType dbType = cbbDataBase.getValue();
        if(dbType == null){
           throw new ClientException("数据库不允许为空！");
        }

        String url = tdUrl.getText();
        if(StringUtils.isEmpty(url)){
            throw new ClientException("数据库链接不允许为空！");
        }

        String userName = tdUserName.getText();
        if(StringUtils.isEmpty(userName)){
            throw new ClientException("用户名不允许为空！");
        }

        String password = tdPassword.getText();
        if(StringUtils.isEmpty(password)){
            throw new ClientException("密码不允许为空！");
        }

        DataSourceConfig dataSourceConfig = new DataSourceConfig();
        dataSourceConfig.setDbType(dbType);
        dataSourceConfig.setUrl(url);
        dataSourceConfig.setUserName(userName);
        dataSourceConfig.setUserPassword(password);

        return dataSourceConfig;

    }
}
