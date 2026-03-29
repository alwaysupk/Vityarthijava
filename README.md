# Medicine Reminder & Inventory System

A Java-based console application to manage medicines, track inventory, monitor expiry dates, and schedule dose reminders.

## Problem Statement
People often forget to take medicines on time, fail to restock them before they run out, or overlook expiry dates. Managing multiple medicines manually can be confusing and unsafe. This project provides a simple digital solution for medicine tracking and reminder management.

## Features
- Add new medicine
- View all medicines
- Search medicine by name
- Delete medicine by ID
- Update medicine stock
- Check low-stock medicines
- Check expired medicines
- Check medicines expiring soon
- Add medicine reminders
- View pending reminders
- Mark reminders as completed

## Technologies Used
- Java
- JDBC
- MySQL
- IntelliJ IDEA
- Git & GitHub

## Project Structure
```text
src/
 ├── dao/
 │    ├── MedicineDAO.java
 │    └── ReminderDAO.java
 ├── model/
 │    ├── Medicine.java
 │    └── Reminder.java
 ├── threads/
 │    └── ReminderMonitorThread.java
 ├── util/
 │    └── DBConnection.java
 ├──MainApp.java
 │
 └──database/
     └── schema.sql

README.md