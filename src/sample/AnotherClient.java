package sample;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.Objects;

public class AnotherClient extends Application {
    Stage window;

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) {
        window = primaryStage;
        Parent root = null;
        try {
            root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("Client.fxml")));
        } catch (IOException e) {
            e.printStackTrace();
        }
        window.setTitle("Chat AnotherClient");

        window.setOnCloseRequest(event -> closeProgram());

        //window.getIcons().add(new Image(getClass().getResourceAsStream("icon.png")));
        assert root != null;
        window.setScene(new Scene(root, 605, 438));
        window.setResizable(false);
        window.show();
    }

    public void closeProgram() {
        try {
            window.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}