module com.cboard.dormTrack.dormTrack_frontend {
    requires com.cboard.dormTrack.dormTrack_common;

    requires javafx.controls;
    requires javafx.fxml;


    opens com.cboard.dormTrack.dormTrack_frontend to javafx.fxml;
    exports com.cboard.dormTrack.dormTrack_frontend;
}