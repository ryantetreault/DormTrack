package com.cboard.dormTrack.dormTrack_frontend.model;

import javafx.beans.property.*;

public class Student {
    private final IntegerProperty studentId = new SimpleIntegerProperty();
    private final StringProperty name = new SimpleStringProperty();
    private final StringProperty gender = new SimpleStringProperty();
    private final IntegerProperty year = new SimpleIntegerProperty();
    private final StringProperty email = new SimpleStringProperty();

    public int getStudentId() { return studentId.get(); }
    public void setStudentId(int value) { studentId.set(value); }
    public IntegerProperty studentIdProperty() { return studentId; }

    public String getName() { return name.get(); }
    public void setName(String value) { name.set(value); }
    public StringProperty nameProperty() { return name; }

    public String getGender() { return gender.get(); }
    public void setGender(String value) { gender.set(value); }
    public StringProperty genderProperty() { return gender; }

    public int getYear() { return year.get(); }
    public void setYear(int value) { year.set(value); }
    public IntegerProperty yearProperty() { return year; }

    public String getEmail() { return email.get(); }
    public void setEmail(String value) { email.set(value); }
    public StringProperty emailProperty() { return email; }
}