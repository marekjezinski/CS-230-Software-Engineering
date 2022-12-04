module com.example.cs230 {
    requires javafx.controls;
    requires javafx.fxml;
    requires javafx.media;
    exports CS230;
    exports CS230.items;
    opens CS230.items to javafx.fxml;
    exports CS230.npc;
    opens CS230.npc to javafx.fxml;
}