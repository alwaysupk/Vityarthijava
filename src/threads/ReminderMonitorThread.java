package threads;

import dao.ReminderDAO;
import model.Reminder;

import java.time.LocalDateTime;
import java.util.List;

public class ReminderMonitorThread extends Thread {
    private ReminderDAO reminderDAO;

    public ReminderMonitorThread(ReminderDAO reminderDAO) {
        this.reminderDAO = reminderDAO;
    }

    @Override
    public void run() {
        while (true) {
            try {
                List<Reminder> reminders = reminderDAO.getAllPendingReminders();
                for (Reminder reminder : reminders) {
                    if (!reminder.getReminderTime().isAfter(LocalDateTime.now())) {
                        System.out.println("Reminder Alert! Time to take medicine ID: " + reminder.getMedicineId());
                    }
                }
                Thread.sleep(10000);
            } catch (Exception e) {
                System.out.println("Reminder thread error: " + e.getMessage());
            }
        }
    }
}