module edu.espol.grupo_12 {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.base;

    opens edu.espol.grupo_12 to javafx.fxml;
    exports edu.espol.grupo_12;
}