module com.example.tp3 {
    requires javafx.controls;
    requires javafx.fxml;

    requires org.controlsfx.controls;
    requires org.kordamp.bootstrapfx.core;

    opens com.example.tp3 to javafx.fxml;
    exports com.example.tp3;
}