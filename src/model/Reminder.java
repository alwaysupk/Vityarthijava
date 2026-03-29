package model;

import java.time.LocalDateTime;

public class Reminder {
    private int reminderId;
    private int medicineId;
    private LocalDateTime reminderTime;
    private String status;

    public Reminder() {
    }

    public Reminder(int reminderId, int medicineId, LocalDateTime reminderTime, String status) {
        this.reminderId = reminderId;
        this.medicineId = medicineId;
        this.reminderTime = reminderTime;
        this.status = status;
    }

    public int getReminderId() {
        return reminderId;
    }

    public void setReminderId(int reminderId) {
        this.reminderId = reminderId;
    }

    public int getMedicineId() {
        return medicineId;
    }

    public void setMedicineId(int medicineId) {
        this.medicineId = medicineId;
    }

    public LocalDateTime getReminderTime() {
        return reminderTime;
    }

    public void setReminderTime(LocalDateTime reminderTime) {
        this.reminderTime = reminderTime;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}