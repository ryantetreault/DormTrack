package com.cboard.dormTrack.dormTrack_frontend.model;

import com.cboard.dormTrack.dormTrack_common.dto.StudentDto;
import javafx.event.ActionEvent;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;

public class EditStudentDialog {

    public Dialog<StudentDto> getDialog(StudentDto student) {
        Dialog<StudentDto> dialog = new Dialog<>();
        dialog.setTitle("Edit Student");

        ButtonType saveBtnType = new ButtonType("Save", ButtonBar.ButtonData.OK_DONE);
        ButtonType deleteBtnType = new ButtonType("Delete", ButtonBar.ButtonData.LEFT);
        dialog.getDialogPane().getButtonTypes().addAll(saveBtnType, deleteBtnType, ButtonType.CANCEL);

        TextField nameField = new TextField(student.getName());
        TextField genderField = new TextField(student.getGender());
        TextField yearField = new TextField(String.valueOf(student.getYear()));
        TextField emailField = new TextField(student.getEmail());
        Label errorLabel = new Label();
        errorLabel.setStyle("-fx-text-fill: red;");
        errorLabel.setVisible(false);

        GridPane grid = new GridPane();
        grid.setHgap(10); grid.setVgap(10);
        grid.addRow(0, new Label("Name:"), nameField);
        grid.addRow(1, new Label("Gender:"), genderField);
        grid.addRow(2, new Label("Year:"), yearField);
        grid.addRow(3, new Label("Email:"), emailField);
        grid.add(errorLabel, 1, 4);

        dialog.getDialogPane().setContent(grid);

        Node saveButton = dialog.getDialogPane().lookupButton(saveBtnType);
        saveButton.addEventFilter(ActionEvent.ACTION, event -> {
            String email = emailField.getText();
            if (!email.endsWith("@westfield.ma.edu")) {
                errorLabel.setText("Email must end with @westfield.ma.edu");
                errorLabel.setVisible(true);
                event.consume();
            } else {
                errorLabel.setVisible(false);
            }
        });

        dialog.setResultConverter(button -> {
            if (button == saveBtnType) {
                student.setName(nameField.getText());
                student.setGender(genderField.getText());
                student.setYear(Integer.parseInt(yearField.getText()));
                student.setEmail(emailField.getText());
                return student;
            } else if (button == deleteBtnType) {
                student.setName(null); // Mark for deletion (optional convention)
                return student;
            }
            return null;
        });

        return dialog;
    }
}