package com.cboard.dormTrack.dormTrack_frontend.model;

import javafx.scene.control.*;
import javafx.scene.layout.GridPane;

public class AddStudentDialog {

    public Dialog<Student> getDialog() {
        Dialog<Student> dialog = new Dialog<>();
        dialog.setTitle("Add New Student");

        ButtonType addButtonType = new ButtonType("Add", ButtonBar.ButtonData.OK_DONE);
        dialog.getDialogPane().getButtonTypes().addAll(addButtonType, ButtonType.CANCEL);

        TextField nameField = new TextField();
        TextField genderField = new TextField();
        TextField yearField = new TextField();
        TextField emailField = new TextField();

        GridPane grid = new GridPane();
        grid.setHgap(10); grid.setVgap(10);

        grid.add(new Label("Name:"), 0, 0);
        grid.add(nameField, 1, 0);
        grid.add(new Label("Gender (M/F):"), 0, 1);
        grid.add(genderField, 1, 1);
        grid.add(new Label("Year:"), 0, 2);
        grid.add(yearField, 1, 2);
        grid.add(new Label("Email:"), 0, 3);
        grid.add(emailField, 1, 3);

        dialog.getDialogPane().setContent(grid);

        dialog.setResultConverter(dialogButton -> {
            if (dialogButton == addButtonType) {
                Student student = new Student();
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