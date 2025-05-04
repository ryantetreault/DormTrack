package com.cboard.dormTrack.dormTrack_frontend.controller;

import com.cboard.dormTrack.dormTrack_frontend.model.AddStudentDialog;
import com.cboard.dormTrack.dormTrack_common.dto.StudentDTO;
import javafx.beans.property.ReadOnlyObjectWrapper;
import javafx.beans.property.ReadOnlyStringWrapper;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;
import java.net.http.*;
import java.net.URI;
import java.util.List;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

public class StudentController {

    @FXML private TableView<StudentDTO> studentTable;
    @FXML private TableColumn<StudentDTO, Integer> idCol;
    @FXML private TableColumn<StudentDTO, String> nameCol;
    @FXML private TableColumn<StudentDTO, String> genderCol;
    @FXML private TableColumn<StudentDTO, Integer> yearCol;
    @FXML private TableColumn<StudentDTO, String> emailCol;
    @FXML private TableColumn<StudentDTO, Void> actionCol;

    private final ObjectMapper mapper = new ObjectMapper();

    @FXML
    public void initialize() {
        idCol.setCellValueFactory(data -> new ReadOnlyObjectWrapper<>(data.getValue().getStudentId()));
        nameCol.setCellValueFactory(data -> new ReadOnlyStringWrapper(data.getValue().getName()));
        genderCol.setCellValueFactory(data -> new ReadOnlyStringWrapper(data.getValue().getGender()));
        yearCol.setCellValueFactory(data -> new ReadOnlyObjectWrapper<>(data.getValue().getYear()));
        emailCol.setCellValueFactory(data -> new ReadOnlyStringWrapper(data.getValue().getEmail()));
        actionCol.setCellFactory(col -> new TableCell<>() {
            private final Button editBtn = new Button("Edit");

            {
                editBtn.setOnAction(e -> {
                    StudentDTO student = getTableView().getItems().get(getIndex());
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
                        List<StudentDTO> students = mapper.readValue(response, new TypeReference<>() {});
                        ObservableList<StudentDTO> data = FXCollections.observableArrayList(students);
                        studentTable.setItems(data);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                });
    }

    @FXML
    private void handleAddStudent() {
        AddStudentDialog addDialog = new AddStudentDialog();
        Dialog<StudentDTO> dialog = addDialog.getDialog();

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
                            System.out.println("Student added!");
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

    private void showEditDialog(StudentDTO student) {
        Dialog<StudentDTO> dialog = new Dialog<>();
        dialog.setTitle("Edit Student");

        ButtonType saveBtnType = new ButtonType("Save", ButtonBar.ButtonData.OK_DONE);
        ButtonType deleteBtnType = new ButtonType("Delete", ButtonBar.ButtonData.LEFT);
        dialog.getDialogPane().getButtonTypes().addAll(saveBtnType, deleteBtnType, ButtonType.CANCEL);

        TextField nameField = new TextField(student.getName());
        TextField genderField = new TextField(student.getGender());
        TextField yearField = new TextField(String.valueOf(student.getYear()));
        TextField emailField = new TextField(student.getEmail());

        GridPane grid = new GridPane();
        grid.setHgap(10); grid.setVgap(10);
        grid.addRow(0, new Label("Name:"), nameField);
        grid.addRow(1, new Label("Gender:"), genderField);
        grid.addRow(2, new Label("Year:"), yearField);
        grid.addRow(3, new Label("Email:"), emailField);

        dialog.getDialogPane().setContent(grid);

        dialog.setResultConverter(button -> {
            if (button == saveBtnType) {
                student.setName(nameField.getText());
                student.setGender(genderField.getText());
                student.setYear(Integer.parseInt(yearField.getText()));
                student.setEmail(emailField.getText());
                return student;
            } else if (button == deleteBtnType) {
                deleteStudent(student.getStudentId());
                return null;
            }
            return null;
        });

        dialog.showAndWait().ifPresent(updatedStudent -> {
            updateStudent(updatedStudent);
        });
    }

    private void updateStudent(StudentDTO student) {
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