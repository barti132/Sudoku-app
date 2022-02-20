module com.example.sudokuapp {
    requires javafx.controls;
    requires javafx.fxml;

    requires org.kordamp.bootstrapfx.core;

    opens pl.bartoszsredzinski.sudokuapp to javafx.fxml;
    exports pl.bartoszsredzinski.sudokuapp;
}