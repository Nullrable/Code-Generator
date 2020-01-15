package com.lsdx.view.setting;

import com.lsdx.context.AppContext;
import com.lsdx.data.PackageConfig;
import com.lsdx.data.Template;
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
 * @Date:Create：in 2020-01-10 09:57
 * @Modified By：
 */
public class DlgPackageConfig extends Stage {


    private VBox vBox;

    private TextField tdEntityPath;
    private TextField tdDaoPath;
    private TextField tdServicePath;
    private TextField tdServiceImplPath;
    private TextField tdDtoPath;
    private TextField tdEntity2DTOConverterPath;

    private Button btnSave;
    private Button btnClose;

    public DlgPackageConfig(){

        super();

        vBox = new VBox();

        Scene scene = new Scene(vBox, 550, 300);
        setScene(scene);
        sizeToScene();

        tdEntityPath = new TextField();
        tdEntityPath.setMinWidth(300);
        LabelFiled lfEntityPath = new LabelFiled("Entity：", tdEntityPath);
        lfEntityPath.setLabelWidth(200);
        vBox.getChildren().add(lfEntityPath);


        tdDaoPath = new TextField();
        tdDaoPath.setMinWidth(300);
        LabelFiled lfDaoPath = new LabelFiled("Dao：", tdDaoPath);
        lfDaoPath.setLabelWidth(200);
        vBox.getChildren().add(lfDaoPath);

        tdServicePath = new TextField();
        tdServicePath.setMinWidth(300);
        LabelFiled lfServicePath = new LabelFiled("Service：", tdServicePath);
        lfServicePath.setLabelWidth(200);
        vBox.getChildren().add(lfServicePath);


        tdServiceImplPath = new TextField();
        tdServiceImplPath.setMinWidth(300);
        LabelFiled lfServiceImplPath = new LabelFiled("ServiceImpl：", tdServiceImplPath);
        lfServiceImplPath.setLabelWidth(200);
        vBox.getChildren().add(lfServiceImplPath);


        tdDtoPath = new TextField();
        tdDtoPath.setMinWidth(300);
        LabelFiled lfDtoPath = new LabelFiled("DTO：", tdDtoPath);
        lfDtoPath.setLabelWidth(200);
        vBox.getChildren().add(lfDtoPath);

        tdEntity2DTOConverterPath = new TextField();
        tdEntity2DTOConverterPath.setMinWidth(300);
        LabelFiled lfEntity2DTOConterPath = new LabelFiled("Model2DTOConverter：", tdEntity2DTOConverterPath);
        lfEntity2DTOConterPath.setLabelWidth(200);
        vBox.getChildren().add(lfEntity2DTOConterPath);


        btnSave = new Button("保存");
        btnSave.setMaxWidth(100);
        btnSave.setMinWidth(100);
        btnSave.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {

                PackageConfig packageConfig =  getPackageConfig();

                try {
                    AppContext.PACKAGE_CONFIG_SERVICE.save(packageConfig);
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

        fill();

    }

    public PackageConfig getPackageConfig(){

        PackageConfig packageConfig = new PackageConfig();

        if(StringUtils.isNotEmpty(tdEntityPath.getText())){
            packageConfig.addPackage(Template.Entity, tdEntityPath.getText());
        }

        if(StringUtils.isNotEmpty(tdDaoPath.getText())){
            packageConfig.addPackage(Template.DAO, tdDaoPath.getText());
        }

        if(StringUtils.isNotEmpty(tdServicePath.getText())){
            packageConfig.addPackage(Template.SERVICE, tdServicePath.getText());
        }

        if(StringUtils.isNotEmpty(tdServiceImplPath.getText())){
            packageConfig.addPackage(Template.SERVICE_IMPL, tdServiceImplPath.getText());
        }


        if(StringUtils.isNotEmpty(tdDtoPath.getText())){
            packageConfig.addPackage(Template.DTO, tdDtoPath.getText());
        }

        if(StringUtils.isNotEmpty(tdEntity2DTOConverterPath.getText())){
            packageConfig.addPackage(Template.MODEL_2_DTO_CONVERTER, tdEntity2DTOConverterPath.getText());
        }


        return packageConfig;


    }

    private void fill(){

        PackageConfig packageConfig = AppContext.PACKAGE_CONFIG_SERVICE.read();
        if(packageConfig == null){
            return;
        }

        String entityPath = packageConfig.getPackage(Template.Entity);
        tdEntityPath.setText(entityPath);

        String daoPath = packageConfig.getPackage(Template.DAO);
        tdDaoPath.setText(daoPath);

        String servicePath = packageConfig.getPackage(Template.SERVICE);
        tdServicePath.setText(servicePath);

        String serviceImplPath = packageConfig.getPackage(Template.SERVICE_IMPL);
        tdServiceImplPath.setText(serviceImplPath);


        String serviceDtoPath = packageConfig.getPackage(Template.DTO);
        tdDtoPath.setText(serviceDtoPath);

        String entity2DTOConverterPath = packageConfig.getPackage(Template.MODEL_2_DTO_CONVERTER);
        tdEntity2DTOConverterPath.setText(entity2DTOConverterPath);

    }

}
