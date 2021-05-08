package sample;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;
import sample.controller.client.ClientController;

import java.io.IOException;
import java.util.Objects;

public class Client extends Application {
    private Stage window;
    private FXMLLoader loader;

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) {
        window = primaryStage;
        Parent root = null;

        try {
            loader = new FXMLLoader(Objects.requireNonNull(getClass().getResource("controller/client/AnotherClient.fxml")));
            root = loader.load();
        } catch (IOException e) {
            e.printStackTrace();
        }

        window.setTitle("To Bob");
        window.setOnCloseRequest(event -> closeProgram());
        window.getIcons().add(new Image("images/clientIcon.png"));
        assert root != null;
        window.setScene(new Scene(root, 605, 438));
        window.setResizable(false);
        window.show();
    }

    public void closeProgram() {
        try {
            window.close();
            ClientController controller = loader.getController();
            controller.closeSocket();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
