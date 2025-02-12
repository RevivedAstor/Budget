Personal Finance Tracker
Overview
The Personal Finance Tracker is a Java-based application designed to help users manage their personal finances. It allows users to register, log in, and perform various actions such as adding transactions, viewing transaction history, transferring money to other users, and updating personal information. The app uses a database (e.g., PostgreSQL/MySQL) to store user and transaction data, with functionalities such as income/expense tracking, balance calculation, and transaction categorization.

Features
User Authentication:

Users can register, log in, and update their profiles.
Admin privileges for administrative actions.
Transaction Management:

Add, categorize, and view transactions.
Support for multiple transaction categories such as "Food", "Rent", "Salary", and more.
Ability to transfer money between users.
Balance Management:

View current balance and transaction details.
Income and expense summaries.
User Profile:

Update username, email, and password.
View account description.
Technologies Used
Java: Core programming language for the application.
SQL (PostgreSQL/MySQL): Database for storing user and transaction data.
JDBC: For database connectivity.
Scanner: For reading user input from the console.
ValidatorUtil: For input validation like username, email, and password.
Project Structure
menu: Contains the different menus of the application, like MainMenu, UnregisteredMenu, UserMenu, and AdminMenu.
dao: Contains the database access objects (DAOs) for interacting with the database (e.g., UserDAO, TransactionDAO, BalanceDAO).
utils: Contains utility classes such as Session (manages user session), UserUtils (for user-related DB operations), and ValidatorUtil (for input validation).
Setup
Clone the repository:

bash
Copy
Edit
git clone <repo_url>
cd Personal-Finance-Tracker
Database Setup: Set up PostgreSQL/MySQL with the required schema for users and transactions.

Configure Database: Ensure the connection details (e.g., database URL, username, password) are correctly set in the DatabaseManager.

Run the Application: Use the Main class to start the application:

bash
Copy
Edit
javac Main.java
java Main
How to Use
Registration:

Upon first run, you'll be prompted to either register or log in.
After registration, you can log in using your credentials.
Transaction Handling:

Once logged in, you can add transactions (e.g., for income or expenses) and view them.
Profile Management:

You can update your personal information, including username, email, and password.
Admin Access:

Admin users can perform administrative tasks, such as viewing or managing all users.