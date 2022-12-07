package ApplicationMenu;

import DataSQL.AccountSql;
import DataSQL.PaymentHistorySql;
import DataSQL.AccountList;

import java.util.List;

public class MainMenu {

    private final AccountSql dataSource;
    private final PaymentHistorySql paymentHistorySql = new PaymentHistorySql();
    private final LoggedInMenu loggedInMenu = new LoggedInMenu(paymentHistorySql);
    private final Methods methods = new Methods();

    public MainMenu(AccountSql dataSource) {
        this.dataSource = dataSource;
    }

    public void queryAccount() {
        List<AccountList> accounts = dataSource.queryAccount();
        if (accounts == null) {
            System.out.println("No accounts!");
            return;
        }

        for (AccountList accountList : accounts) {
            System.out.println("AccNumber: " + accountList.getNumber() + "\t\t Name: " + accountList.getName() + "\t Account Balance: " + accountList.getBalance() + "€");
        }
    }

    public void registerAccount(String email, String password, String name) {
        while (true) {
            int accNumberToInt = Integer.parseInt(accNumberGenerate().toString());
            if (!dataSource.accountNumberExistChecker(accNumberToInt)) {
                dataSource.registerAccount(accNumberToInt, email, password, name, 500);
                break;
            } else {
                System.out.println("Account number already exists.");
            }
        }
    }

    private StringBuilder accNumberGenerate() {
        StringBuilder accNumber = new StringBuilder();
        for (int i = 0; i < 7; i++) {
            int randomNumber = methods.getRandomValue(1,9);
            accNumber.append(randomNumber);
        }
        return accNumber;
    }


    public boolean logInToAccount(String email,String password) {
            if (dataSource.emailExistChecker(email) && dataSource.passwordCheck(email,password)) {
                loggedInMenu.setEmail(email);
                loggedInMenu.setAccNumber(dataSource.getAccountNumber(email));
                return true;
            } else {
                return false;
            }
    }

}