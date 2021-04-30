package sample;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.scene.image.Image;

import java.io.IOException;

public class Server extends Application {

    Stage window;

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) {
        window = primaryStage;
        Parent root = null;
        try {
            root = FXMLLoader.load(getClass().getResource("Server.fxml"));
        } catch (IOException e) {
            e.printStackTrace();
        }
        window.setTitle("Chat Server");

        window.setOnCloseRequest(event -> closeProgram());

        //window.getIcons().add(new Image(getClass().getResourceAsStream("icon.png")));
        assert root != null;
        window.setScene(new Scene(root, 600, 400));
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
