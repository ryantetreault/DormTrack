package com.cboard.dormTrack.dormTrack_frontend.model;

import com.cboard.dormTrack.dormTrack_common.dto.StudentDto;
import javafx.event.ActionEvent;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;

public class AddStudentDialog {

    public Dialog<StudentDto> getDialog() {
        Dialog<StudentDto> dialog = new Dialog<>();
        dialog.setTitle("Add New Student");

        ButtonType addButtonType = new ButtonType("Add", ButtonBar.ButtonData.OK_DONE);
        dialog.getDialogPane().getButtonTypes().addAll(addButtonType, ButtonType.CANCEL);

        TextField nameField = new TextField();
        TextField genderField = new TextField();
        TextField yearField = new TextField();
        TextField emailField = new TextField();
        Label errorLabel = new Label();
        errorLabel.setStyle("-fx-text-fill: red;");
        errorLabel.setVisible(false);

        GridPane grid = new GridPane();
        grid.setHgap(10);
        grid.setVgap(10);

        grid.add(new Label("Name:"), 0, 0);
        grid.add(nameField, 1, 0);
        grid.add(new Label("Gender (M/F):"), 0, 1);
        grid.add(genderField, 1, 1);
        grid.add(new Label("Year:"), 0, 2);
        grid.add(yearField, 1, 2);
        grid.add(new Label("Email:"), 0, 3);
        grid.add(emailField, 1, 3);
        grid.add(errorLabel, 1, 4); // Add error label to grid

        dialog.getDialogPane().setContent(grid);

        // block dialog from closing if email is invalid
        Node addButton = dialog.getDialogPane().lookupButton(addButtonType);
        addButton.addEventFilter(ActionEvent.ACTION, event -> {
            String email = emailField.getText();
            if (!email.endsWith("@westfield.ma.edu")) {
                errorLabel.setText("Email must end with @westfield.ma.edu");
                errorLabel.setVisible(true);
                event.consume(); // Prevent dialog from closing
            } else {
                errorLabel.setVisible(false); // Clear any previous errors
            }
        });

        dialog.setResultConverter(dialogButton -> {
            if (dialogButton == addButtonType) {
                StudentDto student = new StudentDto();
                student.setName(nameField.getText());
                student.setGender(genderField.getText());
                student.setYear(Integer.parseInt(yearField.getText()));
                student.setEmail(emailField.getText());
                return student;
            }
            return null;
        });

        return dialog;
    }
}