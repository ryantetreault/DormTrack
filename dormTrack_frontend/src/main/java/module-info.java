module com.cboard.dormTrack.dormTrack_frontend {
    requires com.cboard.dormTrack.dormTrack_common;

    requires javafx.controls;
    requires javafx.fxml;
    requires javafx.graphics;
    requires java.net.http;
    requires com.fasterxml.jackson.databind;


    opens com.cboard.dormTrack.dormTrack_frontend to javafx.fxml;
    exports com.cboard.dormTrack.dormTrack_frontend;
    exports com.cboard.dormTrack.dormTrack_frontend.controller;
    opens com.cboard.dormTrack.dormTrack_frontend.controller to javafx.fxml;
}