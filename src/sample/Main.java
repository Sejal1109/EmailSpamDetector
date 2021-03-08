package sample;

import javafx.application.Application;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Insets;
import javafx.scene.Group;
import javafx.scene.Parent;
import javafx.scene.paint.Color;
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


    private TableView table = new TableView();
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
        Scene scene = new Scene(new Group(), Color.LIGHTSKYBLUE);
        stage.setTitle("Spaminator");
        stage.setWidth(833);
        stage.setHeight(600);
        WordCounter wordCounter = new WordCounter();
        final Label label = new Label("File Spam Probabilities");
        label.setFont(new Font("Franklin Gothic Medium", 20));

        wordCounter.parseFile(mainDirectory);
        //wordCounter.outputHamWordCount(new File("ham.txt"));
        //wordCounter.outputSpamWordCount(new File("spam.txt"));
        wordCounter.calculateProb();
        DirectoryChooser directoryChooser2 = new DirectoryChooser();
        directoryChooser2.setInitialDirectory(new File("."));
        File mainDirectory2 = directoryChooser2.showDialog(stage);
        wordCounter.parseFile2(mainDirectory2);


        TableColumn FileName = new TableColumn("File Name");
        FileName.setMinWidth(350);
        FileName.setCellValueFactory(new PropertyValueFactory<>("filename"));
        TableColumn className = new TableColumn("Actual Class");
        className.setMinWidth(300);
        className.setCellValueFactory(new PropertyValueFactory<>("actualClass"));
        TableColumn probability = new TableColumn("Spam Probability");
        probability.setMinWidth(150);
        probability.setCellValueFactory(new PropertyValueFactory<>("roundedSpam"));

        table.setItems(WordCounter.FinalProbList);
        table.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);
        table.getColumns().addAll(FileName, className, probability);
        
        double Accuracy = (double) (wordCounter.numTrueNegatives + wordCounter.numTruePositives)/ wordCounter.numFiles;
        double Precision = (double) wordCounter.numTruePositives/ (wordCounter.numFalsePositives + wordCounter.numTruePositives) ;

        TextField textField1 = new TextField(String.valueOf(Accuracy));
        TextField textField2 = new TextField(String.valueOf(Precision));

        Label label1 = new Label("Accuracy: ");
        Label label2 = new Label("Precision: ");


        hb.getChildren().addAll(label1, textField1, label2, textField2);
        final VBox vbox = new VBox();
        vbox.setSpacing(5);
        vbox.setPadding(new Insets(10, 0, 0, 10));
        vbox.getChildren().addAll(table, hb);

        ((Group) scene.getRoot()).getChildren().addAll(vbox);

        stage.setScene(scene);
        stage.show();
    }
}
