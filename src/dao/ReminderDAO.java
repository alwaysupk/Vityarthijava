package dao;

import model.Reminder;
import util.DBConnection;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

public class ReminderDAO {

    public void addReminder(Reminder reminder) throws SQLException {
        String query = "INSERT INTO reminders(medicine_id, reminder_time, status) VALUES (?, ?, ?)";

        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(query)) {

            ps.setInt(1, reminder.getMedicineId());
            ps.setTimestamp(2, Timestamp.valueOf(reminder.getReminderTime()));
            ps.setString(3, reminder.getStatus());

            ps.executeUpdate();
        }
    }

    public List<Reminder> getAllPendingReminders() throws SQLException {
        List<Reminder> reminders = new ArrayList<>();
        String query = "SELECT * FROM reminders WHERE status = 'PENDING' ORDER BY reminder_time";

        try (Connection con = DBConnection.getConnection();
             Statement st = con.createStatement();
             ResultSet rs = st.executeQuery(query)) {

            while (rs.next()) {
                Reminder reminder = new Reminder();
                reminder.setReminderId(rs.getInt("reminder_id"));
                reminder.setMedicineId(rs.getInt("medicine_id"));
                reminder.setReminderTime(rs.getTimestamp("reminder_time").toLocalDateTime());
                reminder.setStatus(rs.getString("status"));
                reminders.add(reminder);
            }
        }
        return reminders;
    }

    public boolean markReminderAsCompleted(int reminderId) throws SQLException {
        String query = "UPDATE reminders SET status = 'COMPLETED' WHERE reminder_id = ?";

        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(query)) {

            ps.setInt(1, reminderId);
            int rowsAffected = ps.executeUpdate();
            return rowsAffected > 0;
        }
    }

    public List<Reminder> getAllReminders() throws SQLException {
        List<Reminder> reminders = new ArrayList<>();
        String query = "SELECT * FROM reminders ORDER BY reminder_time";

        try (Connection con = DBConnection.getConnection();
             Statement st = con.createStatement();
             ResultSet rs = st.executeQuery(query)) {

            while (rs.next()) {
                Reminder reminder = new Reminder();
                reminder.setReminderId(rs.getInt("reminder_id"));
                reminder.setMedicineId(rs.getInt("medicine_id"));
                reminder.setReminderTime(rs.getTimestamp("reminder_time").toLocalDateTime());
                reminder.setStatus(rs.getString("status"));
                reminders.add(reminder);
            }
        }
        return reminders;
    }
}