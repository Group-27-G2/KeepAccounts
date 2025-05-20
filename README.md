# 💰 Personal Finance Management System

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

---

## 👥 Team Member Task Allocation

| Module                                | Contributor | Student ID |
| ------------------------------------- | ----------- | ---------- |
| **1. User Management**                | Yuhan Feng  | 221166426  |
|     - `User.java` – User entity class |             |            |

* `UserManager.java` – Handles user info storage
* `UserPanel.java` – User settings UI |
  \| **2. Classification Management** | Xinzhu Yu | 221166390 |
  \|     - `CategoryManager.java` – Add/Edit/Delete categories
* `CategoryPanel.java` – Category GUI interface |
  \| **3. Transaction Management** | Yuetong Fang | 221166404 |
  \|     - `Transaction.java`, `TransactionManager.java`
* `TransactionPanel.java` – Manage income/expense entries |
  \| **4. Statistics Module** | Chenchen Guan | 221166448 |
  \|     - `StatisticsGUI.java` – Visualization UI
* `StatisticsPanel.java` – Chart layout & data display |
  \| **5. Main Interface & Utilities** | Danyao Yang | 221166460 |
  \|     - `MainGUI.java` – App entry point
* `Utilities.java`, `Constants.java` – Support functions |
  \| **6. Authentication & Resources** | Ziyao Tang | 221166389 |
  \|     - `LoginGUI.java`, `RegisterGUI.java`
* `AdvicePanel.java` – AI suggestions
* `bg1.jpg`, `bg3.jpg` – UI background resources |

---

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

### 📤 Data Export

* Export financial records as `.CSV` or `.XLSX`

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
│   ├── bg1.jpg
│   └── bg3.jpg
├── test/
│   ├── UserManagerTest.java
│   └── TransactionTest.java
```

---

## 🆕 Version History

| Version | Changes                                                                     |
| ------- | --------------------------------------------------------------------------- |
| v1.0    | Basic system implementation: user login/registration, add transactions      |
| v2.0    | ✨ Added AI financial suggestions via `AdvicePanel`                          |
| v3.0    | 📊 Introduced data visualization (bar & pie charts) for income and expenses |

---





