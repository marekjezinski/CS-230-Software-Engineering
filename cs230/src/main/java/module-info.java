module com.example.cs230 {
    requires javafx.controls;
    requires javafx.fxml;
    opens CS230 to javafx.fxml;
    exports CS230;
}