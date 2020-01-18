package com.lsdx.view.setting;

import com.lsdx.context.AppContext;
import com.lsdx.data.Template;
import com.lsdx.view.widget.LabelFiled;
import com.lsdx.view.widget.Window;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import org.apache.commons.lang3.StringUtils;
import org.fxmisc.flowless.VirtualizedScrollPane;
import org.fxmisc.richtext.CodeArea;
import org.fxmisc.richtext.LineNumberFactory;
import org.fxmisc.richtext.model.StyleSpans;
import org.fxmisc.richtext.model.StyleSpansBuilder;
import org.reactfx.Subscription;
import java.time.Duration;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @Author: nhsoft.lsd
 * @Description:
 * @Date:Create：in 2020-01-10 09:57
 * @Modified By：
 */
public class DlgTemplateConfig extends Stage {


    private VBox vBox;

    private ComboBox<Template> cbbType;
    private Button btnSave;

    private CodeArea codeArea;

    public DlgTemplateConfig(){

        super();

        vBox = new VBox();

        Scene scene = new Scene(vBox, 800, 700);

        try {
            scene.getStylesheets().add( DlgTemplateConfig.class.getResource("/resources/css/java-keyword.css").toExternalForm());
        }catch (Exception e){
            e.printStackTrace();
        }

        setScene(scene);
        sizeToScene();

        HBox hBox = new HBox();

        List<Template> dbTypes = new ArrayList<Template>();
        dbTypes.add(Template.Entity);
        dbTypes.add(Template.DAO);
        dbTypes.add(Template.SERVICE);
        dbTypes.add(Template.SERVICE_IMPL);
        dbTypes.add(Template.MODEL_2_DTO_CONVERTER);

        ObservableList list = FXCollections.observableList(dbTypes);

        cbbType = new ComboBox<>();
        cbbType.setItems(list);
        cbbType.setMinWidth(150);
        cbbType.setMaxWidth(150);
        cbbType.setOnAction(event -> {
            String html = AppContext.TEMPLATE_SERVICE.read(cbbType.getValue());

            codeArea.clear();
            if(StringUtils.isNotEmpty(html)){
                codeArea.insertText(0, html);
            }
        });

        LabelFiled lfCbb = new LabelFiled("模板：", cbbType);
        lfCbb.setLabelWidth(100);

        hBox.getChildren().add(lfCbb);

        btnSave = new Button("保存");
        btnSave.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {

                btnSave.setDisable(true);
                String value = codeArea.textProperty().getValue();

                try {
                    AppContext.TEMPLATE_SERVICE.save(Template.Entity, value);
                    Window.show("保存成功！");

                    btnSave.setDisable(false);

                }catch (Exception e){
                    Window.showError("保存失败！");

                    btnSave.setDisable(false);
                }


            }
        });
        hBox.getChildren().add(btnSave);

        HBox.setMargin(btnSave, new Insets(4, 0, 0, 4));

        vBox.getChildren().add(hBox);

        codeArea = new CodeArea();
        codeArea.setMinHeight(900);
        codeArea.setMaxHeight(900);
        codeArea.setParagraphGraphicFactory(LineNumberFactory.get(codeArea));

        // recompute the syntax highlighting 500 ms after user stops editing area
        Subscription cleanupWhenNoLongerNeedIt = codeArea

                // plain changes = ignore style changes that are emitted when syntax highlighting is reapplied
                // multi plain changes = save computation by not rerunning the code multiple times
                //   when making multiple changes (e.g. renaming a method at multiple parts in file)
                .multiPlainChanges()

                // do not emit an event until 500 ms have passed since the last emission of previous stream
                .successionEnds(Duration.ofMillis(500))

                // run the following code block when previous stream emits an event
                .subscribe(ignore -> codeArea.setStyleSpans(0, computeHighlighting(codeArea.getText())));

        // when no longer need syntax highlighting and wish to clean up memory leaks
        // run: `cleanupWhenNoLongerNeedIt.unsubscribe();`


        // auto-indent: insert previous line's indents on enter
        final Pattern whiteSpace = Pattern.compile( "^\\s+" );
        codeArea.addEventHandler( KeyEvent.KEY_PRESSED, KE ->
        {
            if ( KE.getCode() == KeyCode.ENTER ) {
                int caretPosition = codeArea.getCaretPosition();
                int currentParagraph = codeArea.getCurrentParagraph();
                Matcher m0 = whiteSpace.matcher( codeArea.getParagraph( currentParagraph-1 ).getSegments().get( 0 ) );
                if ( m0.find() ) Platform.runLater( () -> codeArea.insertText( caretPosition, m0.group() ) );
            }
        });


        StackPane stackPane = new StackPane(new VirtualizedScrollPane<>(codeArea));
        stackPane.setMaxHeight(900);
        stackPane.setMinHeight(900);

        vBox.getChildren().add(stackPane);



    }

    private static StyleSpans<Collection<String>> computeHighlighting(String text) {
        Matcher matcher = PATTERN.matcher(text);
        int lastKwEnd = 0;
        StyleSpansBuilder<Collection<String>> spansBuilder
                = new StyleSpansBuilder<>();
        while(matcher.find()) {
            String styleClass =
                    matcher.group("KEYWORD") != null ? "keyword" :
                            matcher.group("PAREN") != null ? "paren" :
                                    matcher.group("BRACE") != null ? "brace" :
                                            matcher.group("BRACKET") != null ? "bracket" :
                                                    matcher.group("SEMICOLON") != null ? "semicolon" :
                                                            matcher.group("STRING") != null ? "string" :
                                                                    matcher.group("COMMENT") != null ? "comment" :
                                                                            null; /* never happens */ assert styleClass != null;
            spansBuilder.add(Collections.emptyList(), matcher.start() - lastKwEnd);
            spansBuilder.add(Collections.singleton(styleClass), matcher.end() - matcher.start());
            lastKwEnd = matcher.end();
        }
        spansBuilder.add(Collections.emptyList(), text.length() - lastKwEnd);
        return spansBuilder.create();
    }

    private static final String[] KEYWORDS = new String[] {
            "abstract", "assert", "boolean", "break", "byte",
            "case", "catch", "char", "class", "const",
            "continue", "default", "do", "double", "else",
            "enum", "extends", "final", "finally", "float",
            "for", "goto", "if", "implements", "import",
            "instanceof", "int", "interface", "long", "native",
            "new", "package", "private", "protected", "public",
            "return", "short", "static", "strictfp", "super",
            "switch", "synchronized", "this", "throw", "throws",
            "transient", "try", "void", "volatile", "while"
    };

    private static final String KEYWORD_PATTERN = "\\b(" + String.join("|", KEYWORDS) + ")\\b";
    private static final String PAREN_PATTERN = "\\(|\\)";
    private static final String BRACE_PATTERN = "\\{|\\}";
    private static final String BRACKET_PATTERN = "\\[|\\]";
    private static final String SEMICOLON_PATTERN = "\\;";
    private static final String STRING_PATTERN = "\"([^\"\\\\]|\\\\.)*\"";
    private static final String COMMENT_PATTERN = "//[^\n]*" + "|" + "/\\*(.|\\R)*?\\*/";

    private static final Pattern PATTERN = Pattern.compile(
            "(?<KEYWORD>" + KEYWORD_PATTERN + ")"
                    + "|(?<PAREN>" + PAREN_PATTERN + ")"
                    + "|(?<BRACE>" + BRACE_PATTERN + ")"
                    + "|(?<BRACKET>" + BRACKET_PATTERN + ")"
                    + "|(?<SEMICOLON>" + SEMICOLON_PATTERN + ")"
                    + "|(?<STRING>" + STRING_PATTERN + ")"
                    + "|(?<COMMENT>" + COMMENT_PATTERN + ")"
    );

}
