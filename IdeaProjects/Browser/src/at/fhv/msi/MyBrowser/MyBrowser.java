package at.fhv.msi.MyBrowser;


import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.StackPane;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.stage.Stage;
import javafx.scene.text.Text;

/**
 * Created by Marco Simeth on 22.02.2018.
 * The gui in combination with the Connection does not work and its not finished
 */
public class MyBrowser extends Application{
    public static void main(String[] args) {
        //launch(args);
        BrowserConnection browserConnection = new BrowserConnection("http://orf.at/");
    }
    private String _url;


    @Override
    public void start(Stage primaryStage) throws Exception {
        primaryStage.setTitle("Simple Jack Browser");
        GridPane gridPane = new GridPane();
        gridPane.setHgap(20);
        gridPane.setVgap(20);
        gridPane.setPadding(new Insets(0,20,0,20));

        Text text = new Text("Search:");
        text.setFont(Font.font("Arial", FontWeight.BOLD, 20));
        Button button = new Button();
        TextField urlTextField = new TextField();

        gridPane.add(text, 9,9);
        gridPane.add(button, 9, 10);
        gridPane.add(urlTextField, 10, 10);


        button.setText("Start");
        button.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {

                BrowserConnection browserConnection = new BrowserConnection();
                if (text == null){
                    GridPane gridPane1 = new GridPane();
                    gridPane1.setHgap(10);
                    gridPane1.setVgap(10);
                    gridPane1.setPadding(new Insets(0,10,0,10));

                    StackPane stackPane = new StackPane();
                    stackPane.getChildren().add(gridPane1);
                    Stage stage = new Stage();
                    stage.setScene(new Scene(stackPane, 150,150));
                    Text text1 = new Text("Why you give me a false or no URL");
                    gridPane1.add(text1, 9,9);
                    stage.show();

                }
                _url = text.getText();
            }
        });

        StackPane stackPane = new StackPane();
        stackPane.getChildren().add(gridPane);
        primaryStage.setScene(new Scene(stackPane,720, 480));
        primaryStage.show();
    }

    public String getUrl(){
        return _url;
    }


}
