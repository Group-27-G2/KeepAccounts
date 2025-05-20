# 💰 KeepAccounts

A Java-based personal finance management tool designed to help users efficiently track income, expenses, and manage their financial data. This system includes user registration/login, transaction records, data visualization, account balance handling, and intelligent advice features.

---

## 🛠️ Installation & Setup Instructions

### 📌 Prerequisites
- Java Development Kit (JDK 17+ recommended)
- Java IDE (e.g., IntelliJ IDEA, Eclipse)

### 📦 Setup Steps

1. **Clone the Repository**

   git clone https://github.com/Group-27-G2/KeepAccounts.git

2. **Project Structure**


   - main/
     ├── src/            # Source files
     │   └── src/        # Java classes
     ├── image/          # Background & UI images (e.g., bg1.jpg, bg3.jpg)
     └── test/           # Test classes


3. **Run the Program**

   * Open the project in your IDE.
   * Run `LoginGUI.java` to launch the application.
     
## 🆕 Version History

| Version | Changes                                                                     |
| ------- | --------------------------------------------------------------------------- |
| v1.0    | Basic system implementation: user login/registration, add transactions      |
| v2.0    | ✨ Added AI financial suggestions via `AdvicePanel`                          |
| v3.0    | 📊 Introduced data visualization (bar & pie charts) for income and expenses |
---

## 👥 Team Member Task Allocation

| Module No. | Module Name                          | Member Name   | Student ID | Responsibilities                                                                                                                                                                                                                  |
| ---------- | ------------------------------------ | ------------- | ---------- | --------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------- |
| 1          | **User Management Module**           | Yuhan Feng    | 221166426  | - `User.java`: Defines the user entity with attributes and methods.<br>- `UserManager.java`: Handles user operations such as adding, deleting, and updating.<br>- `UserPanel.java`: GUI for user information display and updates. |
| 2          | **Classification Management Module** | Xinzhu Yu     | 221166390  | - `CategoryManager.java`: Manages category creation, modification, and deletion.<br>- `CategoryPanel.java`: GUI for category management.                                                                                          |
| 3          | **Transaction Management Module**    | Yuetong Fang  | 221166404  | - `Transaction.java`: Represents individual financial transactions.<br>- `TransactionManager.java`: Handles transaction operations and logic.<br>- `TransactionPanel.java`: GUI for entering and viewing transactions.            |
| 4          | **Statistical Module**               | Chenchen Guan | 221166448  | - `StatisticsGUI.java`: Interface for displaying statistical information.<br>- `StatisticsPanel.java`: Presents bar and pie charts for income/expense data.                                                                       |
| 5          | **Main Interface & Utility Classes** | Danyao Yang   | 221166460  | - `MainGUI.java`: Main entry point and dashboard for the application.<br>- `Utilities.java`: General helper functions.<br>- `Constants.java`: Stores global constants for consistency.                                            |
| 6          | **Authentication & Resource Module** | Ziyao Tang    | 221166389  | - `LoginGUI.java`: User login interface.<br>- `RegisterGUI.java`: New user registration interface.<br>- `AdvicePanel.java`: Displays AI-based financial advice.<br>- `bg1.jpg`, `bg3.jpg`: Background images for GUI styling.     |


## 🚀 Feature Overview

### 🔐 Authentication

* User registration and login
* Password validation and error handling

### 💸 Transaction Management

* Record income and expenses
* Update or delete existing records
* Filter by category

### 📊 Financial Reports

* View statistics by time range or category
* Interactive bar/pie charts

### 🧠 AI Advice (v2 Feature)

* `AdvicePanel` offers budgeting suggestions or warnings based on spending habits

### 🏦 Account & User Management

* View and edit account balance
* Change password
* Personalized user settings


---

## 📂 Project Directory Structure

```
main/
├── src/
│   └── src/
│       ├── User.java
│       ├── TransactionManager.java
│       ├── StatisticsGUI.java
│       └── ...
├── image/
├── test/
│   ├── UserManagerTest.java
│   └── TransactionTest.java
```

---





