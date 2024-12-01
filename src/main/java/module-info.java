module org.example.rouse_criterion {
    requires javafx.controls;
    requires javafx.fxml;


    opens org.example.rouse_criterion to javafx.fxml;
    exports org.example.rouse_criterion;
    exports org.example.rouse_criterion.Controller;
    opens org.example.rouse_criterion.Controller to javafx.fxml;
}