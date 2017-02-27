package app;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;
import org.opencv.core.Core;

import java.io.IOException;

public class Main extends Application {

    private static final String TITLE = "Capture Video";

    @Override
    public void start(Stage stage) {
        try {
            configureWithStage(stage);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * For launching the application...
     *
     * @param args optional params
     */
    public static void main(String[] args) {
        // Load the native OpenCV library
        System.loadLibrary(Core.NATIVE_LIBRARY_NAME);
        launch(args);
    }

    private void configureWithStage(Stage stage) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("sample.fxml"));
        BorderPane root = loader.load();
        stage.setTitle(TITLE);
        stage.setScene(new Scene(root, 300, 275));
        stage.show();

        Controller controller = loader.getController();

        stage.setOnCloseRequest(event -> controller.setClosed());
    }

}
