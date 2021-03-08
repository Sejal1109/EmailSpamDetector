package sample;

import javafx.application.Application;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Insets;
import javafx.scene.Group;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.stage.DirectoryChooser;
import javafx.stage.Stage;

import java.io.File;
import java.io.IOException;

public class Main extends Application {


    private TableView<TestFile> table = new TableView<TestFile>();
    final HBox hb = new HBox();

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage stage) throws IOException {
        DirectoryChooser directoryChooser = new DirectoryChooser();
        directoryChooser.setInitialDirectory(new File("."));
        File mainDirectory = directoryChooser.showDialog(stage);
        System.out.println(mainDirectory);
        Scene scene = new Scene(new Group());
        stage.setTitle("Spam MAster 1000");
        stage.setWidth(1000);
        stage.setHeight(600);
        WordCounter wordCounter = new WordCounter();
        final Label label = new Label("File Spam Probabilities");
        label.setFont(new Font("Franklin Gothic Medium", 20));

        wordCounter.parseFile(mainDirectory);
        wordCounter.outputHamWordCount(new File("ham.txt"));
        wordCounter.outputSpamWordCount(new File("spam.txt"));
        wordCounter.calculateProb();
        DirectoryChooser directoryChooser2 = new DirectoryChooser();
        directoryChooser2.setInitialDirectory(new File("."));
        File mainDirectory2 = directoryChooser2.showDialog(stage);
        wordCounter.parseFile2(mainDirectory2);

//

        TableColumn FileName = new TableColumn("File Name");
        FileName.setCellValueFactory(new PropertyValueFactory<>("filename"));
        TableColumn className = new TableColumn("Actual Class");
        className.setCellValueFactory(new PropertyValueFactory<>("actualClass"));
        TableColumn probability = new TableColumn("Spam Probability");
        probability.setCellValueFactory(new PropertyValueFactory<>("spamProbability"));

        ObservableList<TestFile> probbs = WordCounter.getAllProb();
        table.setItems(probbs);
        table.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);
        table.getColumns().addAll(FileName, className, probability);

//        final TextField addSId = new TextField();
//        addSId.setPromptText("Student ID");
//        final TextField addAssing = new TextField();
//        addAssing.setPromptText("Assignments/100");
//
//        final TextField addMid = new TextField();
//        addMid.setPromptText("Midterm/100");
//
//        final TextField addFinEx = new TextField();
//        addFinEx.setPromptText("Final Exam/100");
//
//        final Button addButton = new Button("Add");
//        addButton.setOnAction(new EventHandler<ActionEvent>() {
//            @Override
//            public void handle(ActionEvent actionEvent) {
//                float a = Float.parseFloat(addAssing.getText());
//                float b = Float.parseFloat(addMid.getText());
//                float c = Float.parseFloat(addFinEx.getText());
//                marks.add(new StudentRecord(addSId.getText(), a, b, c));
//                addSId.clear();
//                addFinEx.clear();
//                addMid.clear();
//                addAssing.clear();
//            }
//        });

//        hb.getChildren().addAll(addSId, addAssing, addMid, addFinEx, addButton);
//        hb.setSpacing(3);
        final VBox vbox = new VBox();
        vbox.setSpacing(5);
        vbox.setPadding(new Insets(10, 0, 0, 10));
        vbox.getChildren().addAll(table, hb);

        ((Group) scene.getRoot()).getChildren().addAll(vbox);

        stage.setScene(scene);
        stage.show();
    }
}
