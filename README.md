# Charity Platform – Java Swing Desktop Application

This is a desktop-based project developed using **Java Swing**, **AWT**, **JDBC**, and **PostgreSQL**. It is made to help manage data of people who need help (beneficiaries) and display their details to unknown donors, who can support them by scanning their UPI QR codes.

---

## 💡 Project Idea

In this application:

- A beneficiary can register by entering name, contact details, problem description, and uploading their photo and UPI QR.
- After login, the beneficiary can preview, edit, delete, or submit their information to show on the "Donate Now" section.
- The "Donate Now" page shows all verified beneficiaries. Donors don’t need to log in — they can view and directly help.

This app is fully designed using Java Swing GUI and stores all data in PostgreSQL.

---

## 🛠️ Technologies Used

- Java (Swing + AWT)
- JDBC
- PostgreSQL
- Image Upload (profile + QR)
- PreparedStatement (for secure queries)

---

## 🚀 How to Run

1. Make sure PostgreSQL is installed.
2. Create a database (e.g. `charity_platform`) and set up tables using the provided SQL file.
3. Compile:
   4 . Run

## How to Compile and Run

# For Linux/macOS:

`cd src

# Compile

javac -d . -cp .:lib/postgresql-42.7.5.jar main/\*.java

# Run

java -cp .:lib/postgresql-42.7.5.jar main.homepage

# For windows:

cd src

# Compile

javac -d . -cp .;lib/postgresql-42.7.5.jar main/\*.java

# Run

java -cp .;lib/postgresql-42.7.5.jar main.homepage

```

```
