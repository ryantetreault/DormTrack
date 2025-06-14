package com.cboard.dormTrack.dormTrack_frontend.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;

public class DashboardController {

    @FXML
    private void handleStudents(ActionEvent event) {
        switchScene(event, "/com/cboard/dormTrack/dormTrack_frontend/student_view.fxml");
    }

    @FXML
    private void handleAssignments(ActionEvent event) {
        switchScene(event, "/com/cboard/dormTrack/dormTrack_frontend/assignment_view.fxml");
    }

    @FXML
    private void handleStudentsByFloor(ActionEvent event) {
        switchScene(event, "/com/cboard/dormTrack/dormTrack_frontend/students_by_floor.fxml");
    }

    @FXML
    private void handleMaintenance(ActionEvent event) {
        switchScene(event, "/com/cboard/dormTrack/dormTrack_frontend/maintenance_view.fxml");
    }

    @FXML
    private void handleRoomChangeHistory(ActionEvent event) {
        switchScene(event, "/com/cboard/dormTrack/dormTrack_frontend/room_change_history_view.fxml");
    }

    @FXML
    private void handleInspections(ActionEvent event) {
        switchScene(event, "/com/cboard/dormTrack/dormTrack_frontend/inspection_view.fxml");
    }

    @FXML
    private void handleEvents(ActionEvent event) {
        switchScene(event, "/com/cboard/dormTrack/dormTrack_frontend/event_view.fxml");
    }

    private void switchScene(ActionEvent event, String fxmlPath) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource(fxmlPath));
            Parent root = loader.load();

            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            stage.setScene(new Scene(root));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}