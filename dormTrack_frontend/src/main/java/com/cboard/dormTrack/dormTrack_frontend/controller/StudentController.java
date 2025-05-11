package com.cboard.dormTrack.dormTrack_frontend.controller;

import com.cboard.dormTrack.dormTrack_frontend.model.AddStudentDialog;
import com.cboard.dormTrack.dormTrack_common.dto.StudentDto;
import com.cboard.dormTrack.dormTrack_frontend.model.EditStudentDialog;
import javafx.beans.property.ReadOnlyObjectWrapper;
import javafx.beans.property.ReadOnlyStringWrapper;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;
import java.net.http.*;
import java.net.URI;
import java.util.List;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

public class StudentController {

    @FXML
    private TableView<StudentDto> studentTable;

    @FXML
    private TableColumn<StudentDto, Void> actionCol;

    private final ObjectMapper mapper = new ObjectMapper();

    @FXML
    public void initialize() {
        actionCol.setCellFactory(col -> new TableCell<>() {
            private final Button editBtn = new Button("Edit");

            {
                editBtn.setOnAction(e -> {
                    StudentDto student = getTableView().getItems().get(getIndex());
                    showEditDialog(student);
                });
            }

            @Override
            protected void updateItem(Void item, boolean empty) {
                super.updateItem(item, empty);
                if (empty) {
                    setGraphic(null);
                } else {
                    setGraphic(editBtn);
                }
            }
        });

        handleLoadStudents();
    }

    @FXML
    private void handleLoadStudents() {
        HttpClient client = HttpClient.newHttpClient();
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create("http://localhost:8080/students/all"))
                .build();

        client.sendAsync(request, HttpResponse.BodyHandlers.ofString())
                .thenApply(HttpResponse::body)
                .thenAccept(response -> {
                    try {
                        List<StudentDto> students = mapper.readValue(response, new TypeReference<>() {});
                        ObservableList<StudentDto> data = FXCollections.observableArrayList(students);
                        studentTable.setItems(data);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                });
    }

    @FXML
    private void handleAddStudent() {
        AddStudentDialog addDialog = new AddStudentDialog();
        Dialog<StudentDto> dialog = addDialog.getDialog();

        dialog.showAndWait().ifPresent(student -> {
            try {
                String json = mapper.writeValueAsString(student);
                HttpRequest request = HttpRequest.newBuilder()
                        .uri(URI.create("http://localhost:8080/students/add"))
                        .header("Content-Type", "application/json")
                        .POST(HttpRequest.BodyPublishers.ofString(json))
                        .build();

                HttpClient.newHttpClient().sendAsync(request, HttpResponse.BodyHandlers.ofString())
                        .thenAccept(response -> {
                            handleLoadStudents(); // reload list after adding
                        });

            } catch (Exception e) {
                e.printStackTrace();
            }
        });
    }


    @FXML
    private void handleBack() {
        try {
            Parent root = FXMLLoader.load(getClass().getResource("/com/cboard/dormTrack/dormTrack_frontend/dashboard.fxml"));
            Stage stage = (Stage) studentTable.getScene().getWindow();
            stage.setScene(new Scene(root));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void showEditDialog(StudentDto student) {
        EditStudentDialog editDialog = new EditStudentDialog();
        Dialog<StudentDto> dialog = editDialog.getDialog(student);

        dialog.showAndWait().ifPresent(result -> {
            if (result.getName() == null) {
                deleteStudent(student.getStudentId());
            } else {
                updateStudent(result);
            }
        });
    }

    private void updateStudent(StudentDto student) {
        try {
            String json = mapper.writeValueAsString(student);
            HttpRequest request = HttpRequest.newBuilder()
                    .uri(URI.create("http://localhost:8080/students/" + student.getStudentId()))
                    .header("Content-Type", "application/json")
                    .PUT(HttpRequest.BodyPublishers.ofString(json))
                    .build();

            HttpClient.newHttpClient().sendAsync(request, HttpResponse.BodyHandlers.ofString())
                    .thenRun(this::handleLoadStudents);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void deleteStudent(int studentId) {
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create("http://localhost:8080/students/" + studentId))
                .DELETE()
                .build();

        HttpClient.newHttpClient().sendAsync(request, HttpResponse.BodyHandlers.ofString())
                .thenRun(this::handleLoadStudents);
    }
}