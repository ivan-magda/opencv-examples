package com.ivanmagda.cv;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;
import org.opencv.core.Core;

public class Main extends Application {

    private static final int SCENE_WIDTH = 640;
    private static final int SCENE_HEIGHT = 480;

    @Override
    public void start(Stage primaryStage) throws Exception {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("sample.fxml"));
        BorderPane root = loader.load();
        primaryStage.setTitle("Face Detection");
        primaryStage.setScene(new Scene(root, SCENE_WIDTH, SCENE_HEIGHT));
        primaryStage.show();

        Controller controller = loader.getController();
        controller.init();
    }


    public static void main(String[] args) {
        // Load the native OpenCV library.
        System.loadLibrary(Core.NATIVE_LIBRARY_NAME);
        launch(args);
    }
}
