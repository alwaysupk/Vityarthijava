import dao.MedicineDAO;
import dao.ReminderDAO;
import model.Medicine;
import model.Reminder;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Scanner;

public class MainApp {

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        MedicineDAO medicineDAO = new MedicineDAO();
        ReminderDAO reminderDAO = new ReminderDAO();

        while (true) {
            printMenu();

            int choice;
            try {
                choice = Integer.parseInt(sc.nextLine());
            } catch (Exception e) {
                System.out.println("Invalid input. Please enter a number.");
                continue;
            }

            try {
                switch (choice) {
                    case 1:
                        addMedicine(sc, medicineDAO);
                        break;

                    case 2:
                        viewAllMedicines(medicineDAO);
                        break;

                    case 3:
                        searchMedicine(sc, medicineDAO);
                        break;

                    case 4:
                        deleteMedicine(sc, medicineDAO);
                        break;

                    case 5:
                        updateStock(sc, medicineDAO);
                        break;

                    case 6:
                        checkLowStockMedicines(medicineDAO);
                        break;

                    case 7:
                        checkExpiryAlerts(sc, medicineDAO);
                        break;

                    case 8:
                        addReminder(sc, medicineDAO, reminderDAO);
                        break;

                    case 9:
                        viewPendingReminders(sc, reminderDAO);
                        break;

                    case 10:
                        System.out.println("Exiting application.");
                        return;

                    default:
                        System.out.println("Invalid choice. Please select a valid option.");
                }
            } catch (Exception e) {
                System.out.println("Error: " + e.getMessage());
            }
        }
    }

    private static void printMenu() {
        System.out.println("\n==== Medicine Reminder & Inventory System ====");
        System.out.println("1. Add Medicine");
        System.out.println("2. View All Medicines");
        System.out.println("3. Search Medicine by Name");
        System.out.println("4. Delete Medicine by ID");
        System.out.println("5. Update Medicine Stock");
        System.out.println("6. Check Low Stock Medicines");
        System.out.println("7. Check Expiry Alerts");
        System.out.println("8. Add Reminder");
        System.out.println("9. View Pending Reminders");
        System.out.println("10. Exit");
        System.out.print("Enter choice: ");
    }

    private static void addMedicine(Scanner sc, MedicineDAO medicineDAO) throws Exception {
        System.out.print("Enter medicine name: ");
        String name = sc.nextLine();

        System.out.print("Enter type: ");
        String type = sc.nextLine();

        System.out.print("Enter dosage: ");
        String dosage = sc.nextLine();

        System.out.print("Enter quantity: ");
        int quantity = Integer.parseInt(sc.nextLine());

        System.out.print("Enter low stock threshold: ");
        int threshold = Integer.parseInt(sc.nextLine());

        System.out.print("Enter expiry date (YYYY-MM-DD): ");
        LocalDate expiryDate = LocalDate.parse(sc.nextLine());

        System.out.print("Enter notes: ");
        String notes = sc.nextLine();

        Medicine medicine = new Medicine(0, name, type, dosage, quantity, threshold, expiryDate, notes);
        medicineDAO.addMedicine(medicine);

        System.out.println("Medicine added successfully.");
    }

    private static void viewAllMedicines(MedicineDAO medicineDAO) throws Exception {
        List<Medicine> medicines = medicineDAO.getAllMedicines();

        if (medicines.isEmpty()) {
            System.out.println("No medicines found.");
            return;
        }

        System.out.println("\nAll Medicines:");
        for (Medicine medicine : medicines) {
            medicine.displayDetails();
        }
    }

    private static void searchMedicine(Scanner sc, MedicineDAO medicineDAO) throws Exception {
        System.out.print("Enter medicine name to search: ");
        String keyword = sc.nextLine();

        List<Medicine> medicines = medicineDAO.searchMedicineByName(keyword);

        if (medicines.isEmpty()) {
            System.out.println("No medicines found with that name.");
            return;
        }

        System.out.println("\nSearch Results:");
        for (Medicine medicine : medicines) {
            medicine.displayDetails();
        }
    }

    private static void deleteMedicine(Scanner sc, MedicineDAO medicineDAO) throws Exception {
        System.out.print("Enter medicine ID to delete: ");
        int medicineId = Integer.parseInt(sc.nextLine());

        boolean deleted = medicineDAO.deleteMedicineById(medicineId);

        if (deleted) {
            System.out.println("Medicine deleted successfully.");
        } else {
            System.out.println("Medicine not found.");
        }
    }

    private static void updateStock(Scanner sc, MedicineDAO medicineDAO) throws Exception {
        System.out.print("Enter medicine ID to update stock: ");
        int medicineId = Integer.parseInt(sc.nextLine());

        Medicine medicine = medicineDAO.getMedicineById(medicineId);
        if (medicine == null) {
            System.out.println("Medicine not found.");
            return;
        }

        System.out.println("Current details:");
        medicine.displayDetails();

        System.out.print("Enter new quantity: ");
        int newQuantity = Integer.parseInt(sc.nextLine());

        boolean updated = medicineDAO.updateMedicineStock(medicineId, newQuantity);

        if (updated) {
            System.out.println("Medicine stock updated successfully.");
        } else {
            System.out.println("Stock update failed.");
        }
    }

    private static void checkLowStockMedicines(MedicineDAO medicineDAO) throws Exception {
        List<Medicine> medicines = medicineDAO.getLowStockMedicines();

        if (medicines.isEmpty()) {
            System.out.println("No low stock medicines found.");
            return;
        }

        System.out.println("\nLow Stock Medicines:");
        for (Medicine medicine : medicines) {
            medicine.displayDetails();
        }
    }

    private static void checkExpiryAlerts(Scanner sc, MedicineDAO medicineDAO) throws Exception {
        System.out.println("\nExpiry Alert Options:");
        System.out.println("1. View already expired medicines");
        System.out.println("2. View medicines expiring soon");
        System.out.print("Enter choice: ");

        int option = Integer.parseInt(sc.nextLine());

        switch (option) {
            case 1:
                List<Medicine> expiredMedicines = medicineDAO.getExpiredMedicines();
                if (expiredMedicines.isEmpty()) {
                    System.out.println("No expired medicines found.");
                } else {
                    System.out.println("\nExpired Medicines:");
                    for (Medicine medicine : expiredMedicines) {
                        medicine.displayDetails();
                    }
                }
                break;

            case 2:
                System.out.print("Enter number of days to check upcoming expiry: ");
                int days = Integer.parseInt(sc.nextLine());

                List<Medicine> expiringSoon = medicineDAO.getMedicinesExpiringSoon(days);
                if (expiringSoon.isEmpty()) {
                    System.out.println("No medicines expiring in the next " + days + " days.");
                } else {
                    System.out.println("\nMedicines Expiring Soon:");
                    for (Medicine medicine : expiringSoon) {
                        medicine.displayDetails();
                    }
                }
                break;

            default:
                System.out.println("Invalid expiry option.");
        }
    }

    private static void addReminder(Scanner sc, MedicineDAO medicineDAO, ReminderDAO reminderDAO) throws Exception {
        System.out.print("Enter medicine ID for reminder: ");
        int medicineId = Integer.parseInt(sc.nextLine());

        Medicine medicine = medicineDAO.getMedicineById(medicineId);
        if (medicine == null) {
            System.out.println("Medicine not found. Cannot create reminder.");
            return;
        }

        System.out.println("Medicine selected:");
        medicine.displayDetails();

        System.out.print("Enter reminder date and time (YYYY-MM-DDTHH:MM): ");
        String dateTimeInput = sc.nextLine();

        LocalDateTime reminderTime = LocalDateTime.parse(dateTimeInput);

        Reminder reminder = new Reminder();
        reminder.setMedicineId(medicineId);
        reminder.setReminderTime(reminderTime);
        reminder.setStatus("PENDING");

        reminderDAO.addReminder(reminder);

        System.out.println("Reminder added successfully.");
    }

    private static void viewPendingReminders(Scanner sc, ReminderDAO reminderDAO) throws Exception {
        List<Reminder> reminders = reminderDAO.getAllPendingReminders();

        if (reminders.isEmpty()) {
            System.out.println("No pending reminders found.");
            return;
        }

        System.out.println("\nPending Reminders:");
        for (Reminder reminder : reminders) {
            System.out.println("Reminder ID: " + reminder.getReminderId()
                    + ", Medicine ID: " + reminder.getMedicineId()
                    + ", Time: " + reminder.getReminderTime()
                    + ", Status: " + reminder.getStatus());
        }

        System.out.print("\nDo you want to mark any reminder as completed? (yes/no): ");
        String answer = sc.nextLine();

        if (answer.equalsIgnoreCase("yes")) {
            System.out.print("Enter Reminder ID to mark as completed: ");
            int reminderId = Integer.parseInt(sc.nextLine());

            boolean updated = reminderDAO.markReminderAsCompleted(reminderId);
            if (updated) {
                System.out.println("Reminder marked as completed.");
            } else {
                System.out.println("Reminder not found.");
            }
        }
    }
}