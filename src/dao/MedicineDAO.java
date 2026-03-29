package dao;

import model.Medicine;
import util.DBConnection;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class MedicineDAO {

    public void addMedicine(Medicine medicine) throws SQLException {
        String query = "INSERT INTO medicines(name, type, dosage, quantity, low_stock_threshold, expiry_date, notes) VALUES (?, ?, ?, ?, ?, ?, ?)";

        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(query)) {

            ps.setString(1, medicine.getName());
            ps.setString(2, medicine.getType());
            ps.setString(3, medicine.getDosage());
            ps.setInt(4, medicine.getQuantity());
            ps.setInt(5, medicine.getLowStockThreshold());
            ps.setDate(6, Date.valueOf(medicine.getExpiryDate()));
            ps.setString(7, medicine.getNotes());

            ps.executeUpdate();
        }
    }

    public List<Medicine> getAllMedicines() throws SQLException {
        List<Medicine> medicines = new ArrayList<>();
        String query = "SELECT * FROM medicines";

        try (Connection con = DBConnection.getConnection();
             Statement st = con.createStatement();
             ResultSet rs = st.executeQuery(query)) {

            while (rs.next()) {
                medicines.add(extractMedicineFromResultSet(rs));
            }
        }
        return medicines;
    }

    public List<Medicine> searchMedicineByName(String name) throws SQLException {
        List<Medicine> medicines = new ArrayList<>();
        String query = "SELECT * FROM medicines WHERE name LIKE ?";

        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(query)) {

            ps.setString(1, "%" + name + "%");

            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    medicines.add(extractMedicineFromResultSet(rs));
                }
            }
        }
        return medicines;
    }

    public boolean deleteMedicineById(int medicineId) throws SQLException {
        String query = "DELETE FROM medicines WHERE medicine_id = ?";

        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(query)) {

            ps.setInt(1, medicineId);
            int rowsAffected = ps.executeUpdate();
            return rowsAffected > 0;
        }
    }

    public boolean updateMedicineStock(int medicineId, int newQuantity) throws SQLException {
        String query = "UPDATE medicines SET quantity = ? WHERE medicine_id = ?";

        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(query)) {

            ps.setInt(1, newQuantity);
            ps.setInt(2, medicineId);

            int rowsAffected = ps.executeUpdate();
            return rowsAffected > 0;
        }
    }

    public List<Medicine> getLowStockMedicines() throws SQLException {
        List<Medicine> medicines = new ArrayList<>();
        String query = "SELECT * FROM medicines WHERE quantity <= low_stock_threshold";

        try (Connection con = DBConnection.getConnection();
             Statement st = con.createStatement();
             ResultSet rs = st.executeQuery(query)) {

            while (rs.next()) {
                medicines.add(extractMedicineFromResultSet(rs));
            }
        }
        return medicines;
    }

    public List<Medicine> getExpiredMedicines() throws SQLException {
        List<Medicine> medicines = new ArrayList<>();
        String query = "SELECT * FROM medicines WHERE expiry_date < CURDATE()";

        try (Connection con = DBConnection.getConnection();
             Statement st = con.createStatement();
             ResultSet rs = st.executeQuery(query)) {

            while (rs.next()) {
                medicines.add(extractMedicineFromResultSet(rs));
            }
        }
        return medicines;
    }

    public List<Medicine> getMedicinesExpiringSoon(int days) throws SQLException {
        List<Medicine> medicines = new ArrayList<>();
        String query = "SELECT * FROM medicines WHERE expiry_date BETWEEN CURDATE() AND DATE_ADD(CURDATE(), INTERVAL ? DAY)";

        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(query)) {

            ps.setInt(1, days);

            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    medicines.add(extractMedicineFromResultSet(rs));
                }
            }
        }
        return medicines;
    }

    public Medicine getMedicineById(int medicineId) throws SQLException {
        String query = "SELECT * FROM medicines WHERE medicine_id = ?";

        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(query)) {

            ps.setInt(1, medicineId);

            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return extractMedicineFromResultSet(rs);
                }
            }
        }
        return null;
    }

    private Medicine extractMedicineFromResultSet(ResultSet rs) throws SQLException {
        Medicine medicine = new Medicine();
        medicine.setMedicineId(rs.getInt("medicine_id"));
        medicine.setName(rs.getString("name"));
        medicine.setType(rs.getString("type"));
        medicine.setDosage(rs.getString("dosage"));
        medicine.setQuantity(rs.getInt("quantity"));
        medicine.setLowStockThreshold(rs.getInt("low_stock_threshold"));

        Date expirySqlDate = rs.getDate("expiry_date");
        if (expirySqlDate != null) {
            medicine.setExpiryDate(expirySqlDate.toLocalDate());
        } else {
            medicine.setExpiryDate((LocalDate) null);
        }

        medicine.setNotes(rs.getString("notes"));
        return medicine;
    }
}