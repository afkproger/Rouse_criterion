package org.example.rouse_criterion.Controller;

import javafx.beans.property.SimpleObjectProperty;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import org.example.rouse_criterion.Model.Rouse_criterion;
import org.example.rouse_criterion.Model.Validation;


import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class MainController {
    private List<Boolean> userAnswers = new ArrayList<>();
    private List<List<Double>> rouseMatrix;
    private static int X_START_COORDINATE;
    private static int Y_START_COORDINATE;
    @FXML
    private Button aboutStudent;

//    @FXML
//    private Button checkAnswersButton;

    @FXML
    private Button clearAnswersButton;

    @FXML
    private Button aboutProject;

    @FXML
    private Button sendCoefficient;

    @FXML
    private TableView<List<Double>> matrixTable = new TableView<>();
    @FXML
    private TextArea inputData;

    @FXML
    private VBox questionsBox;

//    public boolean checkAnswers() {
//        List<Boolean> answers = Validation.analyzeRouseMatrix(rouseMatrix);
//        for (int i = 0; i < answers.size(); i++) {
//            if (answers.get(i) != userAnswers.get(i)) return false;
//        }
//        return true;
//    }

    public void displayQuestions(List<String> questions) {
        questionsBox.getChildren().clear();
        userAnswers = new ArrayList<>(Collections.nCopies(questions.size(), null)); // Инициализация списка

        for (String question : questions) {
            HBox questionRow = new HBox(10); // Создаем строку для вопроса и кнопок
            Label questionLabel = new Label(question); // Отображаем текст вопроса
            questionRow.getChildren().addAll(questionLabel);
            questionsBox.getChildren().add(questionRow); // Добавляем строку в контейнер
        }
    }

    public List<Boolean> getUserAnswers() {
        return userAnswers;
    }

    public void displayMatrix(List<List<Double>> matrix) {
        if (matrix.isEmpty()) return;

        matrixTable.getColumns().clear();
        matrixTable.getItems().clear();

        int columnCount = matrix.get(0).size();
        for (int colIndex = 0; colIndex < columnCount; colIndex++) {
            TableColumn<List<Double>, Double> column = new TableColumn<>("");

            final int finalColIndex = colIndex;
            column.setCellValueFactory(param -> {
                if (finalColIndex < param.getValue().size()) {
                    return new SimpleObjectProperty<>(param.getValue().get(finalColIndex));
                }
                return null;
            });
            column.setPrefWidth(200);
            matrixTable.getColumns().add(column);
        }

        matrixTable.getItems().setAll(matrix);

        // Автоматически подстраиваем размер строк в зависимости от контента
        matrixTable.setFixedCellSize(40); // Устанавливаем фиксированную высоту строки
        matrixTable.prefHeightProperty().bind(matrixTable.heightProperty()); // Масштабируем высоту таблицы
    }


    @FXML
    private void showModalInfo(String infoText, boolean isTextMode, String title) throws IOException {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/org/example/rouse_criterion/modal_layout.fxml"));
            Parent modalRoot = loader.load();

            ModalController controller = loader.getController();
            if (isTextMode) {
                X_START_COORDINATE = 350;
                Y_START_COORDINATE = 200;
                controller.setInfo(infoText);
            } else {
                X_START_COORDINATE = 750;
                Y_START_COORDINATE = 550;
                controller.setImage(infoText);
            }

            Stage modalStage = new Stage();
            modalStage.initModality(javafx.stage.Modality.APPLICATION_MODAL);
            InputStream iconStream = getClass().getResourceAsStream("/org/example/rouse_criterion/icon.png");
            Image icon = new Image(iconStream);
            modalStage.getIcons().add(icon);

            Scene modalScene = new Scene(modalRoot, X_START_COORDINATE, Y_START_COORDINATE);
            modalStage.setScene(modalScene);

            modalStage.setTitle(title);
            modalStage.showAndWait();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private List<String> generateQuestions(List<List<Double>> matrix) {
        if (Validation.isSystemStable(matrix) && Validation.isSystemOnTheVergeStability(matrix)){
            System.out.println(Validation.isSystemOnTheVergeStability(matrix));
            return List.of(
                    "Cистема устойчива?",
                    "Имеет ли система правые корни , если да , то сколько?",
                    "Если система устойчива , она находится на границе устойчивости?",
                    "Если система находится на границе устойчивости , она находится на колебательной или апериодической границе устойчивости?"
            );
        } else if (Validation.isSystemStable(matrix)) {
            return List.of(
                    "Cистема устойчива?",
                    "Имеет ли система правые корни , если да , то сколько?",
                    "Если система устойчива , она находится на границе устойчивости?"

            );
        }
        {
            return List.of(
                    "Cистема устойчива?",
                    "Имеет ли система правые корни , если да , то сколько?"
            );
        }

    }

    @FXML
    public void initialize() {
        aboutStudent.setOnMouseClicked(mouseEvent -> {
            try {
                showModalInfo("Работу выполнил студент 424 группы " + '\n' + " Барашенков Николай Андреевич", true, "О студенте");
                System.out.println(getUserAnswers());
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        });
        aboutProject.setOnMouseClicked(mouseEvent -> {
            try {
                showModalInfo("/org/example/rouse_criterion/info.png", false, "О проекте");
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        });
//        checkAnswersButton.setOnAction(event -> {
//            if (checkAnswers()){
//                Alert alert = new Alert(Alert.AlertType.INFORMATION);
//                alert.setTitle("Всё верно");
//                alert.setHeaderText("Вы ответили на все вопросы верно");
//                alert.showAndWait();
//            }else {
//                Alert alert = new Alert(Alert.AlertType.ERROR);
//                alert.setTitle("Ошибка");
//                alert.setHeaderText("Ошибка в ответах");
//                alert.setContentText("Проверьте проверьте введённые ответы. ");
//                alert.showAndWait();
//            }
//        });

        // Обработчик для очистки ответов
        clearAnswersButton.setOnAction(event -> {
            userAnswers.clear();
            inputData.clear();
            matrixTable.getItems().clear();
            questionsBox.getChildren().clear();
//            checkAnswersButton.setVisible(false);
            clearAnswersButton.setVisible(false);
            System.out.println("Ответы очищены");
        });


        sendCoefficient.setOnAction(actionEvent -> {
            String dataText = inputData.getText();
            try {
                rouseMatrix = Rouse_criterion.calculateRouseMatrix(dataText);
                if (rouseMatrix.isEmpty()) {
                    throw new Exception();
                }
                displayMatrix(rouseMatrix);
                List<String> questions = generateQuestions(rouseMatrix);
                displayQuestions(questions);
//                checkAnswersButton.setVisible(true);
                clearAnswersButton.setVisible(true);
            } catch (Exception e) {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Ошибка");
                alert.setHeaderText("Ошибка расчета матрицы Рауса");
                alert.setContentText("Проверьте данные. Убедитесь, что они в правильном формате.");
                alert.showAndWait();
                inputData.clear();
            }
        });
    }
}

