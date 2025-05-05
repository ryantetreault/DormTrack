package com.cboard.dormTrack.dormTrack_frontend.controller;

import com.cboard.dormTrack.dormTrack_common.dto.CurrentStudentsByFloorDto;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.List;
import java.util.Scanner;

public class StudentsByFloorController {

    @FXML
    private TableView<CurrentStudentsByFloorDto> studentTable;
    @FXML
    private TableColumn<CurrentStudentsByFloorDto, String> studentNameCol;
    @FXML
    private TableColumn<CurrentStudentsByFloorDto, Integer> floorCol;
    @FXML
    private TableColumn<CurrentStudentsByFloorDto, String> dormNameCol;
    @FXML
    private TableColumn<CurrentStudentsByFloorDto, String> raNameCol;

    private final ObjectMapper objectMapper = new ObjectMapper();

    @FXML
    public void initialize() {
        studentNameCol.setCellValueFactory(new PropertyValueFactory<>("studentName"));
        floorCol.setCellValueFactory(new PropertyValueFactory<>("floor"));
        dormNameCol.setCellValueFactory(new PropertyValueFactory<>("dormName"));
        raNameCol.setCellValueFactory(new PropertyValueFactory<>("raName"));

        loadData();
    }

    private void loadData() {
        try {
            URL url = new URL("http://localhost:8080/views/currentStudents");
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("GET");
            conn.connect();

            Scanner sc = new Scanner(url.openStream());
            StringBuilder inline = new StringBuilder();
            while (sc.hasNext()) {
                inline.append(sc.nextLine());
            }
            sc.close();

            List<CurrentStudentsByFloorDto> list = objectMapper.readValue(
                    inline.toString(), new TypeReference<>() {}
            );

            ObservableList<CurrentStudentsByFloorDto> observableList = FXCollections.observableArrayList(list);
            studentTable.setItems(observableList);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    private void handleBackToDashboard() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/cboard/dormTrack/dormTrack_frontend/dashboard.fxml"));
            Parent root = loader.load();
            Stage stage = (Stage) studentTable.getScene().getWindow();
            stage.setScene(new Scene(root));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}