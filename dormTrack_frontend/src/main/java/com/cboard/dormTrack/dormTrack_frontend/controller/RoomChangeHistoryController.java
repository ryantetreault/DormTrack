package com.cboard.dormTrack.dormTrack_frontend.controller;

import com.cboard.dormTrack.dormTrack_common.dto.RoomChangeHistoryDto;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.List;

public class RoomChangeHistoryController {

    @FXML
    private TableView<RoomChangeHistoryDto> historyTable;
    @FXML
    private TableColumn<RoomChangeHistoryDto, String> studentCol;
    @FXML
    private TableColumn<RoomChangeHistoryDto, String> oldRoomCol;
    @FXML
    private TableColumn<RoomChangeHistoryDto, String> newRoomCol;
    @FXML
    private TableColumn<RoomChangeHistoryDto, String> changeDateCol;

    private final HttpClient client = HttpClient.newHttpClient();
    private final ObjectMapper mapper = new ObjectMapper();

    @FXML
    public void initialize() {
        studentCol.setCellValueFactory(new PropertyValueFactory<>("studentName"));
        oldRoomCol.setCellValueFactory(new PropertyValueFactory<>("oldRoomLabel"));
        newRoomCol.setCellValueFactory(new PropertyValueFactory<>("newRoomLabel"));
        changeDateCol.setCellValueFactory(new PropertyValueFactory<>("changeDate"));

        loadHistory();
    }

    private void loadHistory() {
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create("http://localhost:8080/views/roomChangeHistory"))
                .GET()
                .build();

        client.sendAsync(request, HttpResponse.BodyHandlers.ofString())
                .thenApply(HttpResponse::body)
                .thenAccept(json -> {
                    try {
                        List<RoomChangeHistoryDto> history = mapper.readValue(json, new TypeReference<>() {});
                        historyTable.setItems(FXCollections.observableArrayList(history));
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                });
    }

    @FXML
    private void handleBack() {
        try {
            Parent root = FXMLLoader.load(getClass().getResource("/com/cboard/dormTrack/dormTrack_frontend/dashboard.fxml"));
            Stage stage = (Stage) historyTable.getScene().getWindow();
            stage.setScene(new Scene(root));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}