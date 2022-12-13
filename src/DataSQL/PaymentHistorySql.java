package DataSQL;

import ApplicationMenu.TypesOfTransactionEnum;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class PaymentHistorySql {

    public static final String DB_NAME = "UserAccount.db";
    public static final String CONNECTION_STRING = "jdbc:sqlite:SQL\\" + DB_NAME;
    public static final String TABLE_PAYMENT_HISTORY = "PaymentHistory";
    public static final String COLUMN_RECEIVER_ACCOUNT_NUMBER = "receiverAccount";
    public static final String COLUMN_SENDER_ACCOUNT_NUMBER = "senderAccount";
    public static final String COLUMN_AMOUNT = "amount";
    public static final String COLUMN_TYPE = "type";
    public static final String COLUMN_DATE = "date";
    public static final String COLUMN_ID = "_id";
    public static final String COLUMN_INFORMATION_FOR_BENEFICIARY = "informationForBeneficiary";
    public static final String COLUMN_BELONGS_TO_ACCOUNT = "belongsToAccount";

    private Connection conn;
    private Statement statement;

    public PaymentHistorySql() {}

    public void openConnection() {
        try {
            conn = DriverManager.getConnection(CONNECTION_STRING);
            statement();
        } catch (SQLException e) {
            System.out.println("Couldn't connect to database: " + e.getMessage());
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

    public void statement() {
        try {
            statement = conn.createStatement();
        } catch (SQLException e) {
            System.out.println("Something went wrong: " + e.getMessage());
        }
    }

    public void addPaymentToHistory(int senderAccountNumber, int receiverAccountNumber, double amount, TypesOfTransactionEnum type, String date, String informationForBeneficiary, int belongsToAccount) {

        try (ResultSet ignored = statement.executeQuery("SELECT * FROM '" + TABLE_PAYMENT_HISTORY + "'")) {
                statement.execute("INSERT INTO '" + TABLE_PAYMENT_HISTORY + "' " +
                        " (" + COLUMN_SENDER_ACCOUNT_NUMBER + ", " + COLUMN_RECEIVER_ACCOUNT_NUMBER + ", " + COLUMN_AMOUNT + ", " + COLUMN_TYPE + ", " + COLUMN_DATE + ", " + COLUMN_INFORMATION_FOR_BENEFICIARY + ", " + COLUMN_BELONGS_TO_ACCOUNT + " ) " +
                        "VALUES(" + senderAccountNumber + ", " + receiverAccountNumber + ", " + amount + ", '" + type + "', '" + date + "', '" + informationForBeneficiary + "', " + belongsToAccount + ")");
        } catch (SQLException e) {
            System.out.println("Something went wrong in LoggedInSql in addPaymentToHistory. " + e.getMessage());
        }
    }

    public List<paymentHistoryList> queryPaymentHistory(int accNumber, TypesOfTransactionEnum type) {

        try (ResultSet ignored = statement.executeQuery("SELECT * FROM '" + TABLE_PAYMENT_HISTORY + "'")) {
            ResultSet results;
            if(type.equals(TypesOfTransactionEnum.Credit)) {
                results = statement.executeQuery("SELECT * FROM '" + TABLE_PAYMENT_HISTORY + "'"
                        + " WHERE " + COLUMN_TYPE + " = '" + TypesOfTransactionEnum.Credit + "'"
                        + " AND " + COLUMN_BELONGS_TO_ACCOUNT + " = '" + accNumber + "'");
            } else if(type.equals(TypesOfTransactionEnum.Debit)) {
                results = statement.executeQuery("SELECT * FROM '" + TABLE_PAYMENT_HISTORY + "'"
                        + " WHERE " + COLUMN_TYPE + " = '" + TypesOfTransactionEnum.Debit + "'"
                        + " AND " + COLUMN_BELONGS_TO_ACCOUNT + " = '" + accNumber + "'");
            } else {
                results = statement.executeQuery("SELECT * FROM '" + TABLE_PAYMENT_HISTORY + "'"
                        + " WHERE " + COLUMN_BELONGS_TO_ACCOUNT + " = '" + accNumber + "'" + " AND " + COLUMN_TYPE + " = '" + TypesOfTransactionEnum.Debit + "'"
                        + " OR " + COLUMN_BELONGS_TO_ACCOUNT + " = '" + accNumber + "'" + " AND " + COLUMN_TYPE + " = '" + TypesOfTransactionEnum.Credit + "'");
            }

            List<paymentHistoryList> historyPayment = new ArrayList<>();
            while (results.next()) {
                paymentHistoryList paymentHistory = new paymentHistoryList();
                paymentHistory.setReceiverAccount(results.getInt(COLUMN_RECEIVER_ACCOUNT_NUMBER));
                paymentHistory.setSenderAccount(results.getInt(COLUMN_SENDER_ACCOUNT_NUMBER));
                paymentHistory.setAmount(results.getDouble(COLUMN_AMOUNT));
                paymentHistory.setType(results.getString(COLUMN_TYPE));
                paymentHistory.setDate(results.getString(COLUMN_DATE));
                paymentHistory.setInformationForBeneficiary(results.getString(COLUMN_INFORMATION_FOR_BENEFICIARY));
                paymentHistory.setBelongsToAccount(results.getInt(COLUMN_BELONGS_TO_ACCOUNT));
                paymentHistory.setId(results.getString(COLUMN_ID));
                historyPayment.add(paymentHistory);
            }

            return historyPayment;

        } catch (SQLException e) {
            System.out.println("Query failed: " + e.getMessage());
            return null;
        }
    }

    public String getInformationForBeneficiary(String id) {
        try (ResultSet informationForBeneficiaryGet = statement.executeQuery(" SELECT " + COLUMN_INFORMATION_FOR_BENEFICIARY + " FROM " + TABLE_PAYMENT_HISTORY + " WHERE " + COLUMN_ID + " = " + id)) {
            return informationForBeneficiaryGet.getString(COLUMN_INFORMATION_FOR_BENEFICIARY);

        } catch (SQLException e) {
            System.out.println("Something went wrong in getNameFromAccountNumber in DataSql." + e.getMessage());
            System.exit(1);
        }
        return null;
    }

}