package com.cboard.dormTrack.dormTrack_frontend.controller;

import com.cboard.dormTrack.dormTrack_common.dto.*;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;
import javafx.util.StringConverter;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;


import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.time.LocalDate;
import java.util.List;
import java.util.Map;
import java.util.Optional;

public class RoomAssignmentController {

    @FXML
    private ComboBox<StudentDto> studentComboBox;
    @FXML
    private ComboBox<DormDto> dormComboBox;
    @FXML
    private ComboBox<RoomDto> roomComboBox;
    @FXML
    private DatePicker dateAssignedPicker;
    @FXML
    private TableView<AssignmentDto> assignmentTable;
    @FXML
    private TableColumn<AssignmentDto, String> colStudent;
    @FXML
    private TableColumn<AssignmentDto, String> colRoom;
    @FXML
    private TableColumn<AssignmentDto, String> colAssigned;
    @FXML
    private TableColumn<AssignmentDto, String> colVacated;
    @FXML
    private TableColumn<AssignmentDto, Void> actionCol;


    private final HttpClient httpClient = HttpClient.newHttpClient();
    private final ObjectMapper objectMapper = new ObjectMapper()
            .registerModule(new JavaTimeModule())
                    .disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);

    public void initialize() {
        loadStudents();
        // initially clear roomComboBox
        roomComboBox.setItems(FXCollections.observableArrayList());
        loadDorms();
        loadAssignments();

        dormComboBox.setOnAction(event -> {
            DormDto selected = dormComboBox.getValue();
            if (selected != null) {
                System.out.println("Dorm selected: " + selected.getName());
                loadRoomsByDormId(selected.getDormId());
            }
        });

        actionCol.setCellFactory(col -> new TableCell<>() {
            private final Button changeBtn = new Button("Edit");

            {
                changeBtn.setOnAction(e -> {
                    AssignmentDto assignment = getTableView().getItems().get(getIndex());
                    showChangeRoomDialog(assignment);
                });
            }

            @Override
            protected void updateItem(Void item, boolean empty) {
                super.updateItem(item, empty);
                setGraphic(empty ? null : changeBtn);
            }
        });
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
                    .uri(URI.create("http://localhost:8080/assignments/assign-via-procedure"))
                    .header("Content-Type", "application/json")
                    .POST(HttpRequest.BodyPublishers.ofString(json))
                    .build();

            httpClient.sendAsync(request, HttpResponse.BodyHandlers.ofString())
                    .thenAccept(response -> {
                        int status = response.statusCode();
                        String body = response.body();

                        Platform.runLater(() -> {
                            if (status == 200 || status == 201) {
                                loadAssignments();
                                showAlert(Alert.AlertType.INFORMATION, "Room and RA assigned!");
                            } else if (body.contains("Student already assigned to a room")) {
                                showAlert(Alert.AlertType.ERROR, "Student already assigned to a room.");
                            } else {
                                showAlert(Alert.AlertType.ERROR, "Assignment failed: " + body);
                            }
                        });
                    });
        } catch (Exception e) {
            e.printStackTrace();
            showAlert(Alert.AlertType.ERROR, "Error sending assignment.");
        }
    }

    private void showAlert(Alert.AlertType type, String msg) {
        Alert alert = new Alert(type, msg, ButtonType.OK);
        alert.showAndWait();
    }

    @FXML
    private void handleBackToDashboard() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/cboard/dormTrack/dormTrack_frontend/dashboard.fxml"));
            Parent root = loader.load();
            Stage stage = (Stage) assignmentTable.getScene().getWindow(); // <- FIXED LINE
            stage.setScene(new Scene(root));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void loadDorms() {
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create("http://localhost:8080/dorms/all"))
                .GET().build();

        httpClient.sendAsync(request, HttpResponse.BodyHandlers.ofString())
                .thenApply(HttpResponse::body)
                .thenAccept(json -> {
                    try {
                        List<DormDto> dorms = objectMapper.readValue(json, new TypeReference<>() {});
                        Platform.runLater(() -> {
                            dormComboBox.setItems(FXCollections.observableArrayList(dorms));
                            dormComboBox.setConverter(new StringConverter<>() {
                                public String toString(DormDto d) { return d.getName(); }
                                public DormDto fromString(String s) { return null; }
                            });
                        });
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                });
    }

    private void loadRoomsByDormId(int dormId) {
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create("http://localhost:8080/dorms/available/by-dorm/" + dormId))
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

    private void showChangeRoomDialog(AssignmentDto assignment) {
        Dialog<ButtonType> dialog = new Dialog<>();
        dialog.setTitle("Edit Assignment for " + assignment.getStudentName());

        ButtonType changeButton = new ButtonType("Change", ButtonBar.ButtonData.OK_DONE);
        ButtonType moveOutBtn = new ButtonType("Move Out", ButtonBar.ButtonData.LEFT);
        dialog.getDialogPane().getButtonTypes().addAll(changeButton, moveOutBtn, ButtonType.CANCEL);

        ComboBox<RoomDto> roomBox = new ComboBox<>();
        roomBox.setPrefWidth(250);

        // Load available rooms into the dialog-specific ComboBox
        loadRoomsForDialog(roomBox, assignment.getDormId());

        GridPane grid = new GridPane();
        grid.setHgap(10);
        grid.setVgap(10);
        grid.addRow(0, new Label("New Room:"), roomBox);

        dialog.getDialogPane().setContent(grid);

        dialog.setResultConverter(button -> button);
        Optional<ButtonType> result = dialog.showAndWait();

        if (result.isPresent()) {
            int studentId = studentComboBox.getItems().stream()
                    .filter(s -> s.getName().equals(assignment.getStudentName()))
                    .findFirst()
                    .map(StudentDto::getStudentId)
                    .orElseThrow();

            if (result.get() == changeButton && roomBox.getValue() != null) {
                sendChangeRoomRequest(studentId, roomBox.getValue().getRoomId());
            } else if (result.get() == moveOutBtn) {
                moveOutStudent(studentId);
            }
        }
    }

    private void loadRoomsForDialog(ComboBox<RoomDto> box, int dormId) {
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create("http://localhost:8080/dorms/available/by-dorm/" + dormId))
                .GET().build();

        httpClient.sendAsync(request, HttpResponse.BodyHandlers.ofString())
                .thenApply(HttpResponse::body)
                .thenAccept(json -> {
                    try {
                        List<RoomDto> rooms = objectMapper.readValue(json, new TypeReference<>() {});
                        Platform.runLater(() -> {
                            box.setItems(FXCollections.observableArrayList(rooms));
                            box.setConverter(new StringConverter<>() {
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

    private void sendChangeRoomRequest(int studentId, int newRoomId) {
        try {
            Map<String, Object> requestData = Map.of(
                    "studentId", studentId,
                    "newRoomId", newRoomId
            );

            String json = objectMapper.writeValueAsString(requestData);

            HttpRequest request = HttpRequest.newBuilder()
                    .uri(URI.create("http://localhost:8080/assignments/change-room"))
                    .header("Content-Type", "application/json")
                    .POST(HttpRequest.BodyPublishers.ofString(json))
                    .build();

            httpClient.sendAsync(request, HttpResponse.BodyHandlers.ofString())
                    .thenAccept(response -> Platform.runLater(() -> {
                        showAlert(Alert.AlertType.INFORMATION, "Room changed successfully.");
                        loadAssignments(); // Refresh the table
                    }));

        } catch (Exception e) {
            e.printStackTrace();
            Platform.runLater(() -> showAlert(Alert.AlertType.ERROR, "Failed to change room."));
        }
    }

    private void moveOutStudent(int studentId) {
        try {
            String json = objectMapper.writeValueAsString(Map.of("studentId", studentId));

            HttpRequest request = HttpRequest.newBuilder()
                    .uri(URI.create("http://localhost:8080/assignments/move-out"))
                    .header("Content-Type", "application/json")
                    .POST(HttpRequest.BodyPublishers.ofString(json))
                    .build();

            httpClient.sendAsync(request, HttpResponse.BodyHandlers.ofString())
                    .thenAccept(response -> Platform.runLater(() -> {
                        showAlert(Alert.AlertType.INFORMATION, "Student moved out.");
                        loadAssignments(); // refresh table
                    }));
        } catch (Exception e) {
            e.printStackTrace();
            showAlert(Alert.AlertType.ERROR, "Failed to move out.");
        }
    }
}