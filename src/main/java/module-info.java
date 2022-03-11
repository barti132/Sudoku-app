module com.example.sudokuapp {
    requires javafx.controls;
    requires javafx.fxml;
    requires com.google.gson;

    requires org.kordamp.bootstrapfx.core;

    opens pl.bartoszsredzinski.sudokuapp to javafx.fxml,com.google.gson;
    exports pl.bartoszsredzinski.sudokuapp;
    exports pl.bartoszsredzinski.sudokuapp.sudokualg;
    opens pl.bartoszsredzinski.sudokuapp.sudokualg to javafx.fxml;
    exports pl.bartoszsredzinski.sudokuapp.uicomponents;
    opens pl.bartoszsredzinski.sudokuapp.uicomponents to javafx.fxml, com.google.gson;
}