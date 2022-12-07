package DataSQL;

import java.awt.*;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class AccountSql {

    public static final String DB_NAME = "UserAccount.db";
    public static final String CONNECTION_STRING = "jdbc:sqlite:SQL\\" + DB_NAME;

    public static final String TABLE_ACCOUNT = "Account";
    public static final String COLUMN_ACC_NUMBER = "number";
    public static final String COLUMN_MAIL = "email";
    public static final String COLUMN_PASSWORD = "password";
    public static final String COLUMN_USER_NAME = "name";
    public static final String COLUMN_BALANCE = "balance";
    private Connection conn;
    private Statement statement;

    public boolean openConnection() {
        try {
            conn = DriverManager.getConnection(CONNECTION_STRING);
            statement();
            return true;
        } catch (SQLException e) {
            System.out.println("Couldn't connect to database: " + e.getMessage());
            return false;
        }
    }

    public void statement() {
        try {
            statement = conn.createStatement();
        } catch (SQLException e) {
            System.out.println("Something went wrong: " + e.getMessage());
        }
    }

    public void closeConnection() {
        try {
            if (conn != null) {
                conn.close();
            }
        } catch (SQLException e) {
            System.out.println("Couldn't close connection: " + e.getMessage());
        }
    }

    public List<AccountList> queryAccount() {
        try (ResultSet results = statement.executeQuery("SELECT * FROM " + TABLE_ACCOUNT)) {

            List<AccountList> accounts = new ArrayList<>();
            while (results.next()) {
                AccountList accountList = new AccountList();
                accountList.setNumber(results.getInt(COLUMN_ACC_NUMBER));
                accountList.setEmail(results.getString(COLUMN_MAIL));
                accountList.setName(results.getString(COLUMN_USER_NAME));
                accountList.setBalance(results.getDouble(COLUMN_BALANCE));
                accounts.add(accountList);
            }

            return accounts;

        } catch (SQLException e) {
            System.out.println("Query failed: " + e.getMessage());
            return null;
        }
    }

    public void registerAccount(int accNumber, String email, String password, String name, double balance) {
//        createUserDatabase(accNumber);

        try (ResultSet ignored = statement.executeQuery("SELECT * FROM " + TABLE_ACCOUNT)) {
            statement.execute("INSERT INTO " + TABLE_ACCOUNT +
                    " ("
                    + COLUMN_ACC_NUMBER + ", " + COLUMN_MAIL + ", " + COLUMN_PASSWORD + ", " + COLUMN_USER_NAME + ", " + COLUMN_BALANCE + " ) " +
                    "VALUES(" + accNumber + ",'" + email + "', '" + password + "', '" + name + "', " + balance + ")");

            System.out.println("\n\tYour account was successfully registered.");
            System.out.println("\tYour account number is: " + accNumber);
            System.out.println("\tYour email is: " + email);
            System.out.println("\tYou can now log in to your account.");

        } catch (SQLException e) {
            System.out.println("Something went wrong. " + e.getMessage());
        }
    }

//    private void createUserDatabase(int accNumber) {
//        String accNumberToString = String.valueOf(accNumber);
//        try {
//            statement.execute("CREATE TABLE '" + accNumberToString + "'(" +
//                    "senderAccount INTEGER, " +
//                    "receiverAccount INTEGER, " +
//                    "amount INTEGER, " +
//                    "type TEXT, " +
//                    "date TEXT, " +
//                    "informationForBeneficiary TEXT)");
//        } catch (SQLException e) {
//            System.out.println("Something went wrong in createUserDatabase in DataSql. " + e.getMessage());
//        }
//    }

    public boolean emailExistChecker(String email) {
        try (ResultSet mail = statement.executeQuery("SELECT " + COLUMN_MAIL + " FROM " + TABLE_ACCOUNT)) {
            while (mail.next()) {
//                return mail.getString(COLUMN_MAIL.toLowerCase()).equals(email.toLowerCase());
                if(mail.getString(COLUMN_MAIL.toLowerCase()).equals(email.toLowerCase())) {
                    return true;
                }
            }
        } catch (SQLException e) {
            System.out.println("Something went wrong in emailExistChecker in DataSql. " + e.getMessage());
            System.exit(1);
        }

        return false;
    }

    public boolean accountNumberExistChecker(int accNumber) {

        try (ResultSet number = statement.executeQuery("SELECT * FROM " + TABLE_ACCOUNT)) {

            while (number.next()) {
                AccountList accNumbersList = new AccountList();
                accNumbersList.setNumber(number.getInt(COLUMN_ACC_NUMBER));

                if (accNumbersList.getNumber() == accNumber) {
                    return true;
                }
            }
        } catch (SQLException e) {
            System.out.println("Something went wrong in accountNumberChecker in DataSql." + e.getMessage());
            System.exit(1);
        }
        return false;
    }

    public boolean passwordCheck(String email, String password) {
        try (ResultSet passwordGet = statement.executeQuery(" SELECT " + COLUMN_PASSWORD + " FROM " + TABLE_ACCOUNT + " WHERE " + COLUMN_MAIL + " = '" + email + "'")) {
            while (passwordGet.next()) {
                if (passwordGet.getString(COLUMN_PASSWORD).equals(password)) {
                    return true;
                }
            }
        } catch (SQLException e) {
            System.out.println("Something went wrong in passwordCheck in DataSql." + e.getMessage());
            System.exit(1);
        }
        return false;
    }

    public int getAccountNumber(String email) {

        try (ResultSet accNumberGet = statement.executeQuery(" SELECT " + COLUMN_ACC_NUMBER + " FROM " + TABLE_ACCOUNT + " WHERE " + COLUMN_MAIL + " = '" + email + "'")) {
            return accNumberGet.getInt(COLUMN_ACC_NUMBER);

        } catch (SQLException e) {
            System.out.println("Something went wrong in getAccountNumber in DataSql." + e.getMessage());
            System.exit(1);
        }
        return 0;
    }

    public String getNameFromAccountNumber(int accNumber) {

        try (ResultSet emailGet = statement.executeQuery(" SELECT " + COLUMN_USER_NAME + " FROM " + TABLE_ACCOUNT + " WHERE " + COLUMN_ACC_NUMBER + " = " + accNumber)) {
            return emailGet.getString(COLUMN_USER_NAME);

        } catch (SQLException e) {
            System.out.println("Something went wrong in getNameFromAccountNumber in DataSql." + e.getMessage());
            System.exit(1);
        }
        return null;
    }

    public String getNameFromEmail(String email) {
        try (ResultSet emailGet = statement.executeQuery(" SELECT " + COLUMN_USER_NAME + " FROM " + TABLE_ACCOUNT + " WHERE " + COLUMN_MAIL + " = " + email)) {
            return emailGet.getString(COLUMN_USER_NAME);

        } catch (SQLException e) {
            System.out.println("Something went wrong in getNameFromEmail in DataSql." + e.getMessage());
            System.exit(1);
        }
        return null;
    }


    public double accountBalance(String email) {
        try {
            ResultSet resultSet = statement.executeQuery("SELECT " + COLUMN_BALANCE + " FROM " + TABLE_ACCOUNT + " WHERE " + COLUMN_MAIL + " = '" + email + "'");
            return resultSet.getDouble(COLUMN_BALANCE);

        } catch (SQLException e) {
            System.out.println("Something went wrong in AccountSQL in accountBalance. " + e.getMessage());
        }
        return 0;
    }

    public void changeName(String name, String email) {
        try {
            statement.execute(" UPDATE " + TABLE_ACCOUNT + " SET " + COLUMN_USER_NAME + " = '" + name + "' WHERE " + COLUMN_MAIL + " = '" + email + "'");
            System.out.println("\tName was successfully changed. New name is: " + name);
        } catch (SQLException e) {
            System.out.println("Something went wrong in LoggedInSql in changeName. " + e.getMessage());
        }
    }

    public void changeEmail(String newEmail, String oldEmail) {
        try {
            statement.execute(" UPDATE " + TABLE_ACCOUNT + " SET " + COLUMN_MAIL + " = '" + newEmail + "' WHERE " + COLUMN_MAIL + " = '" + oldEmail + "'");
            System.out.println("\tEmail was successfully changed. New email is: " + newEmail);
        } catch (SQLException e) {
            System.out.println("Something went wrong in LoggedInSql in changeEmail. " + e.getMessage());
        }
    }

    public void changePassword(String newPassword, String email) {
        try {
            statement.execute(" UPDATE " + TABLE_ACCOUNT + " SET " + COLUMN_PASSWORD + " = '" + newPassword + "' WHERE " + COLUMN_MAIL + " = '" + email + "'");
            System.out.println("\tPassword was successfully changed.");
        } catch (SQLException e) {
            System.out.println("Something went wrong in LoggedInSql in changePassword. " + e.getMessage());
        }
    }


    public void sendPayment(int receiverAccNumber, double amount) {
        try {
            ResultSet receiverBalance = statement.executeQuery(" SELECT " + COLUMN_BALANCE + " FROM " + TABLE_ACCOUNT + " WHERE " + COLUMN_ACC_NUMBER + " = " + receiverAccNumber);
            amount += receiverBalance.getInt(COLUMN_BALANCE);
            statement.execute(" UPDATE " + TABLE_ACCOUNT + " SET " + COLUMN_BALANCE + " = " + amount + " WHERE " + COLUMN_ACC_NUMBER + " = " + receiverAccNumber);

        } catch (SQLException e) {
            System.out.println("Something went wrong in LoggedInSql in sendPayment. " + e.getMessage());
            System.exit(1);
        }
    }

    public boolean checkAndSubtractSenderAccountBalance(double amount, String email) {
        try {
            ResultSet accountBalance = statement.executeQuery(" SELECT " + COLUMN_BALANCE + " FROM " + TABLE_ACCOUNT + " WHERE " + COLUMN_MAIL + " = '" + email + "'");
            if (accountBalance.getInt(COLUMN_BALANCE) >= amount) {
                amount = accountBalance.getInt(COLUMN_BALANCE) - amount;
                statement.execute("UPDATE " + TABLE_ACCOUNT + " SET " + COLUMN_BALANCE + " = " + amount + " WHERE " + COLUMN_MAIL + " = '" + email + "'");
                return true;
            }
        } catch (SQLException e) {
            System.out.println("Something went wrong in LoggedInSql in checkSenderAccountBalance. " + e.getMessage());
            System.exit(1);
        }
        return false;
    }
}