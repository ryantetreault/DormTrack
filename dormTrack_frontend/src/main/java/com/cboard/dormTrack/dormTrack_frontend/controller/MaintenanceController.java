package com.cboard.dormTrack.dormTrack_frontend.controller;

import com.cboard.dormTrack.dormTrack_common.dto.CloseRequestDto;
import com.cboard.dormTrack.dormTrack_common.dto.OpenRequestDto;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextInputDialog;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.List;

public class MaintenanceController {

    @FXML
    private TableView<OpenRequestDto> maintenanceTable;

    private final HttpClient httpClient = HttpClient.newHttpClient();
    private final ObjectMapper mapper = new ObjectMapper()
            .registerModule(new JavaTimeModule())
            .disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);


    @FXML
    public void initialize() {
        loadOpenRequests();
    }

    private void loadOpenRequests() {
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create("http://localhost:8080/maintenance/open"))
                .GET().build();

        httpClient.sendAsync(request, HttpResponse.BodyHandlers.ofString())
                .thenApply(HttpResponse::body)
                .thenAccept(json -> {
                    try {
                        List<OpenRequestDto> requests = mapper.readValue(json, new TypeReference<>() {});
                        Platform.runLater(() -> maintenanceTable.setItems(FXCollections.observableArrayList(requests)));
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                });
    }

    @FXML
    private void handleCloseRequest() {
        OpenRequestDto selected = maintenanceTable.getSelectionModel().getSelectedItem();
        if (selected == null) return;

        TextInputDialog dialog = new TextInputDialog();
        dialog.setTitle("Resolve Request");
        dialog.setHeaderText("Enter your name to resolve this issue:");
        dialog.showAndWait().ifPresent(name -> {
            try {
                CloseRequestDto req = new CloseRequestDto();
                req.setRequestId(selected.getRequestId());
                req.setResolverName(name);

                String json = mapper.writeValueAsString(req);
                HttpRequest request = HttpRequest.newBuilder()
                        .uri(URI.create("http://localhost:8080/maintenance/close"))
                        .header("Content-Type", "application/json")
                        .POST(HttpRequest.BodyPublishers.ofString(json))
                        .build();

                httpClient.sendAsync(request, HttpResponse.BodyHandlers.ofString())
                        .thenAccept(res -> Platform.runLater(this::loadOpenRequests));

            } catch (Exception e) {
                e.printStackTrace();
            }
        });
    }

    @FXML
    private void handleBackToDashboard() {
        try {
            Parent root = FXMLLoader.load(getClass().getResource("/com/cboard/dormTrack/dormTrack_frontend/dashboard.fxml"));
            Stage stage = (Stage) maintenanceTable.getScene().getWindow();
            stage.setScene(new Scene(root));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    private void handleViewResolvedRequests() {
        try {
            Parent root = FXMLLoader.load(getClass().getResource("/com/cboard/dormTrack/dormTrack_frontend/resolved_requests.fxml"));
            Stage stage = (Stage) maintenanceTable.getScene().getWindow();
            stage.setScene(new Scene(root));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}