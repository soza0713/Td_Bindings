module com.example.td_bindings {
    requires javafx.controls;
    requires javafx.fxml;


    opens com.example.td_bindings to javafx.fxml;
    exports com.example.td_bindings;
}