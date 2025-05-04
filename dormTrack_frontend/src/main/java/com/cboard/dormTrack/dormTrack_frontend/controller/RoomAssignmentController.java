package com.cboard.dormTrack.dormTrack_frontend.controller;

import com.cboard.dormTrack.dormTrack_common.dto.AssignmentDto;
import com.cboard.dormTrack.dormTrack_common.dto.AssignmentRequest;
import com.cboard.dormTrack.dormTrack_common.dto.RoomDto;
import com.cboard.dormTrack.dormTrack_common.dto.StudentDto;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.util.StringConverter;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;


import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.time.LocalDate;
import java.util.List;

public class RoomAssignmentController {

    @FXML private ComboBox<StudentDto> studentComboBox;
    @FXML private ComboBox<RoomDto> roomComboBox;
    @FXML private DatePicker dateAssignedPicker;
    @FXML private TableView<AssignmentDto> assignmentTable;
    @FXML private TableColumn<AssignmentDto, String> colStudent;
    @FXML private TableColumn<AssignmentDto, String> colRoom;
    @FXML private TableColumn<AssignmentDto, String> colAssigned;
    @FXML private TableColumn<AssignmentDto, String> colVacated;

    private final HttpClient httpClient = HttpClient.newHttpClient();
    private final ObjectMapper objectMapper = new ObjectMapper()
            .registerModule(new JavaTimeModule())
                    .disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);

    public void initialize() {
        loadStudents();
        loadRooms();
        loadAssignments();
    }

    private void loadStudents() {
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create("http://localhost:8080/students/all"))
                .GET().build();

        httpClient.sendAsync(request, HttpResponse.BodyHandlers.ofString())
                .thenApply(HttpResponse::body)
                .thenAccept(json -> {
                    try {
                        List<StudentDto> students = objectMapper.readValue(json, new TypeReference<>() {});
                        Platform.runLater(() -> {
                            studentComboBox.setItems(FXCollections.observableArrayList(students));
                            studentComboBox.setConverter(new StringConverter<>() {
                                public String toString(StudentDto s) { return s.getName(); }
                                public StudentDto fromString(String s) { return null; }
                            });
                        });
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                });
    }

    private void loadRooms() {
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create("http://localhost:8080/rooms/all"))
                .GET().build();

        httpClient.sendAsync(request, HttpResponse.BodyHandlers.ofString())
                .thenApply(HttpResponse::body)
                .thenAccept(json -> {
                    try {
                        List<RoomDto> rooms = objectMapper.readValue(json, new TypeReference<>() {});
                        Platform.runLater(() -> {
                            roomComboBox.setItems(FXCollections.observableArrayList(rooms));
                            roomComboBox.setConverter(new StringConverter<>() {
                                public String toString(RoomDto r) {
                                    return "Room " + r.getRoomId() + " (Floor " + r.getFloor() + ")";
                                }
                                public RoomDto fromString(String s) { return null; }
                            });
                        });
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                });
    }

    private void loadAssignments() {
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create("http://localhost:8080/assignments/all"))
                .GET().build();

        httpClient.sendAsync(request, HttpResponse.BodyHandlers.ofString())
                .thenApply(HttpResponse::body)
                .thenAccept(json -> {
                    try {
                        List<AssignmentDto> assignments = objectMapper.readValue(json, new TypeReference<>() {});
                        Platform.runLater(() -> {
                            assignmentTable.setItems(FXCollections.observableArrayList(assignments));
                            colStudent.setCellValueFactory(cell -> new javafx.beans.property.SimpleStringProperty(cell.getValue().getStudentName()));
                            colRoom.setCellValueFactory(cell -> new javafx.beans.property.SimpleStringProperty(cell.getValue().getRoomLabel()));
                            colAssigned.setCellValueFactory(cell -> new javafx.beans.property.SimpleStringProperty(cell.getValue().getDateAssigned()));
                            colVacated.setCellValueFactory(cell -> new javafx.beans.property.SimpleStringProperty(cell.getValue().getDateVacated()));
                        });
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                });
    }

    @FXML
    private void onAssignRoom() {
        StudentDto student = studentComboBox.getValue();
        RoomDto room = roomComboBox.getValue();
        LocalDate date = dateAssignedPicker.getValue();

        if (student == null || room == null || date == null) {
            showAlert(Alert.AlertType.ERROR, "Please fill all fields.");
            return;
        }

        AssignmentRequest req = new AssignmentRequest(student.getStudentId(), room.getRoomId(), date);
        try {
            String json = objectMapper.writeValueAsString(req);
            HttpRequest request = HttpRequest.newBuilder()
                    .uri(URI.create("http://localhost:8080/assignments"))
                    .header("Content-Type", "application/json")
                    .POST(HttpRequest.BodyPublishers.ofString(json))
                    .build();

            httpClient.sendAsync(request, HttpResponse.BodyHandlers.ofString())
                    .thenAccept(res -> Platform.runLater(() -> {
                        loadAssignments();
                        showAlert(Alert.AlertType.INFORMATION, "Room assigned successfully.");
                    }));

        } catch (Exception e) {
            e.printStackTrace();
            showAlert(Alert.AlertType.ERROR, "Error sending assignment.");
        }
    }

    private void showAlert(Alert.AlertType type, String msg) {
        Alert alert = new Alert(type, msg, ButtonType.OK);
        alert.showAndWait();
    }
}