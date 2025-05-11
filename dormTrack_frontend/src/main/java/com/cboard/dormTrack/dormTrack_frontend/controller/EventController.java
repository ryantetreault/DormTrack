package com.cboard.dormTrack.dormTrack_frontend.controller;

import com.cboard.dormTrack.dormTrack_common.dto.DormDto;
import com.cboard.dormTrack.dormTrack_common.dto.EventDto;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import javafx.application.Platform;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;
import javafx.util.StringConverter;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.time.LocalDate;
import java.util.List;

public class EventController {
    @FXML
    private ComboBox<DormDto> dormComboBox2;
    @FXML
    private ComboBox<DormDto> dormComboBox;
    @FXML
    private
    TextField titleField;
    @FXML
    private
    TextArea descriptionField;
    @FXML
    private
    DatePicker datePicker;
    @FXML
    private
    TableView<EventDto> eventTable;
    @FXML
    private
    TableColumn<EventDto, String> titleCol;
    @FXML
    private
    TableColumn<EventDto, String> descCol;
    @FXML
    private TableColumn<EventDto, String> dateCol;

    private final HttpClient httpClient = HttpClient.newHttpClient();
    private final ObjectMapper mapper = new ObjectMapper().registerModule(new JavaTimeModule());

    @FXML
    public void initialize() {
        loadDorms();

        dormComboBox.setConverter(new StringConverter<>() {
            public String toString(DormDto dorm) { return dorm.getName(); }
            public DormDto fromString(String s) { return null; }
        });

        titleCol.setCellValueFactory(data -> new SimpleStringProperty(data.getValue().getTitle()));
        descCol.setCellValueFactory(data -> new SimpleStringProperty(data.getValue().getDescription()));
        dateCol.setCellValueFactory(data -> new SimpleStringProperty(data.getValue().getDate().toString()));

        eventTable.setPlaceholder(new Label("Select a dorm to view events"));
        dormComboBox2.setOnAction(e -> loadEventsByDorm());
    }

    private void loadDorms() {
        HttpRequest req = HttpRequest.newBuilder()
                .uri(URI.create("http://localhost:8080/dorms/all"))
                .GET()
                .build();

        httpClient.sendAsync(req, HttpResponse.BodyHandlers.ofString())
                .thenApply(HttpResponse::body)
                .thenAccept(json -> {
                    try {
                        List<DormDto> dorms = mapper.readValue(json, new TypeReference<>() {});
                        Platform.runLater(() -> {
                            dormComboBox.setItems(FXCollections.observableArrayList(dorms));
                            dormComboBox2.setItems(FXCollections.observableArrayList(dorms));

                            dormComboBox.setConverter(new StringConverter<>() {
                                public String toString(DormDto dorm) { return dorm.getName(); }
                                public DormDto fromString(String s) { return null; }
                            });

                            dormComboBox2.setConverter(new StringConverter<>() {
                                public String toString(DormDto dorm) { return dorm.getName(); }
                                public DormDto fromString(String s) { return null; }
                            });
                        });
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                });
    }

    private void loadEventsByDorm() {
        DormDto selected = dormComboBox2.getValue();
        if (selected == null) return;

        String url = "http://localhost:8080/events/by-dorm/" + selected.getDormId();
        HttpRequest req = HttpRequest.newBuilder().uri(URI.create(url)).GET().build();

        httpClient.sendAsync(req, HttpResponse.BodyHandlers.ofString())
                .thenApply(HttpResponse::body)
                .thenAccept(json -> {
                    try {
                        List<EventDto> events = mapper.readValue(json, new TypeReference<>() {});
                        Platform.runLater(() -> eventTable.setItems(FXCollections.observableArrayList(events)));
                    } catch (Exception e) { e.printStackTrace(); }
                });
    }

    @FXML
    private void handleAddEvent() {
        DormDto dorm = dormComboBox.getValue();
        String title = titleField.getText();
        String description = descriptionField.getText();
        LocalDate date = datePicker.getValue();

        if (dorm == null || title.isEmpty() || description.isEmpty() || date == null) {
            showAlert("Fill in all fields.");
            return;
        }

        EventDto event = new EventDto();
        event.setDormId(dorm.getDormId());
        event.setDorm(dorm);
        event.setTitle(title);
        event.setDescription(description);
        event.setDate(date);

        try {
            String json = mapper.writeValueAsString(event);
            HttpRequest req = HttpRequest.newBuilder()
                    .uri(URI.create("http://localhost:8080/events/add"))
                    .header("Content-Type", "application/json")
                    .POST(HttpRequest.BodyPublishers.ofString(json))
                    .build();

            httpClient.sendAsync(req, HttpResponse.BodyHandlers.ofString())
                    .thenRun(() -> Platform.runLater(() -> {
                        showAlert("Event added.");
                        loadEventsByDorm();
                    }));
        } catch (Exception e) {
            e.printStackTrace();
            showAlert("Failed to add event.");
        }
    }

    private void showAlert(String msg) {
        new Alert(Alert.AlertType.INFORMATION, msg, ButtonType.OK).showAndWait();
    }

    @FXML
    private void handleBack() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/cboard/dormTrack/dormTrack_frontend/dashboard.fxml"));
            Parent root = loader.load();
            Stage stage = (Stage) dormComboBox.getScene().getWindow();
            stage.setScene(new Scene(root));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}