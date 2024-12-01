package org.example.rouse_criterion;

import javafx.application.Application;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import java.io.InputStream;
import java.util.Stack;

public class Main extends Application {
    private static final int X_START_COORDINATE = 1020;
    private static final int Y_START_COORDINATE = 800;

    @Override
    public void start(Stage stage) throws Exception {


        FXMLLoader loader = new FXMLLoader(getClass().getResource("/org/example/rouse_criterion/layout.fxml"));
        Parent root = loader.load();

        Image icon = new Image(getClass().getResourceAsStream("/org/example/rouse_criterion/icon.png"));
        stage.getIcons().add(icon);


        Scene scene = new Scene(root, X_START_COORDINATE, Y_START_COORDINATE);
        scene.getStylesheets().add(getClass().getResource("/org/example/rouse_criterion/style.css").toExternalForm());

        stage.setScene(scene);
        stage.setTitle("Расчёт критерия Рауса");
        stage.show();

    }
}
