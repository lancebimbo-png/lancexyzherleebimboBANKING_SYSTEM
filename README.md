# BankingSystemCRUD

**Student Activity — Simple Banking System with CRUD Operations**  
*Java Swing + MySQL + DAO Pattern*

---

## System Description

A desktop banking application built with **Java Swing** that manages:
- Customer accounts (Create, Read, Update, Delete)
- Bank transactions (Deposit & Withdraw)
- Transaction history with filters

The project follows the **DAO (Data Access Object)** design pattern, keeping GUI code cleanly separated from database logic.

---

## ERD Explanation

```
CUSTOMER (1) ───< ACCOUNT (1) ───< TRANSACTION
```

| Table       | Primary Key      | Foreign Key                      |
|-------------|-----------------|-----------------------------------|
| customer    | customer_id (PK) | —                                |
| account     | account_id (PK)  | customer_id → customer(customer_id) |
| transaction | transaction_id (PK) | account_id → account(account_id) |

### Relationships
- One **Customer** can have **many Accounts**
- One **Account** can have **many Transactions**
- Both foreign keys use `ON DELETE CASCADE`

---

## Project Structure

```
BankingSystemCRUD/
├── src/
│   ├── Main.java                        ← Entry point
│   ├── util/
│   │   └── DatabaseConnection.java      ← DB connection
│   ├── model/
│   │   ├── Customer.java                ← Data model
│   │   ├── Account.java
│   │   └── Transaction.java
│   ├── dao/
│   │   ├── CustomerDAO.java             ← CRUD for customer table
│   │   ├── AccountDAO.java              ← CRUD for account table
│   │   └── TransactionDAO.java          ← CRUD for transaction table
│   └── view/
│       ├── CustomerAccountFrame.java    ← Window 1: CRUD + Search
│       ├── TransactionFrame.java        ← Window 2: Deposit / Withdraw
│       └── TransactionHistoryFrame.java ← Window 3: Logs & Filters
└── banking_system.sql                   ← Database setup script
```

---

## How to Run

### Requirements
- JDK 8 or later
- MySQL Server 8.x
- NetBeans IDE (recommended) or any Java IDE
- `mysql-connector-j-x.x.x.jar` (JDBC driver)

### Steps

1. **Set up the database**
   ```sql
   -- Open MySQL Workbench and run:
   source banking_system.sql
   ```

2. **Open in NetBeans**
   - File → Open Project → select the `BankingSystemCRUD` folder

3. **Add MySQL JDBC Driver**
   - Right-click project → Properties → Libraries → Add JAR/Folder
   - Select `mysql-connector-j.jar`

4. **Update credentials**
   - Open `src/util/DatabaseConnection.java`
   - Change `USER` and `PASSWORD` to match your MySQL setup

5. **Run**
   - Right-click `Main.java` → Run File (or press `Shift + F6`)

---

## Features

| Feature | Status |
|---|---|
| Add new customer + account | ✅ |
| View all accounts (JTable) | ✅ |
| Update account details | ✅ |
| Delete account (cascades) | ✅ |
| Search by name or account ID | ✅ |
| Auto-fill form on row click | ✅ |
| Deposit money | ✅ |
| Withdraw with balance check | ✅ |
| Transaction history table | ✅ |
| Filter by Account ID & Type | ✅ |
| Color-coded transaction rows | ✅ |
| Live balance display | ✅ |

---

## Screenshots
<img width="1919" height="1073" alt="Screenshot 2026-04-25 232816" src="https://github.com/user-attachments/assets/ee51bac3-9858-49c3-8bf3-68fab29e8b02" />
<img width="1919" height="1074" alt="Screenshot 2026-04-25 232800" src="https://github.com/user-attachments/assets/163ff3b4-a74c-4ff8-9ebe-5b40835f30ed" />
<img width="1919" height="1068" alt="Screenshot 2026-04-25 232824" src="https://github.com/user-attachments/assets/7c97ef3f-8578-48cb-bb89-b5cac185c4ac" />

---

## Author
- **Name:** Lance Xyzherlee A. Bimbo
- **Instructor:** Engr. Jamie Eduardo Rosal, MSCpE
