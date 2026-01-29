package ru.petrosyan.clinicOfPet.model;

public enum AppointmentStatus {
    SCHEDULED("запланирован"),
    COMPLETED("завершен");

    private final String dbValue;

    AppointmentStatus(String dbValue) {
        this.dbValue = dbValue;
    }

    public String getDbValue() {
        return dbValue;
    }

    public static AppointmentStatus fromDbValue(String dbValue) {
        for (AppointmentStatus status : values()) {
            if (status.dbValue.equals(dbValue)) {
                return status;
            }
        }
        throw new IllegalArgumentException("Unknown status: " + dbValue);
    }
}
