package ApplicationMenu;

import DataSQL.*;

import java.util.List;
import java.util.Scanner;

public class LoggedInMenu {
    private final Scanner scanner = new Scanner(System.in);
    private final AccountSql accountSql = new AccountSql();
    private final PaymentHistorySql paymentHistorySql;
    private final Methods methods = new Methods();
    private String email;
    private int accNumber;

    public LoggedInMenu(PaymentHistorySql paymentHistorySql) {
        this.paymentHistorySql = paymentHistorySql;
    }

//    public void selectInstruction() {
//        paymentHistorySql.openConnection();
//        accountSql.openConnection();
//
//        boolean flag = true;
//        while (flag) {
//            printInstructions();
//            int chooseMenuOption = scanner.nextInt();
//            scanner.nextLine();
//            switch (chooseMenuOption) {
//                case 1 -> userProfile();
//                case 2 -> accountBalance();
//                case 3 -> sendPayment();
//                case 4 -> paymentHistoryMenu();
//                case 5 -> flag = false;
//                case 6 -> System.exit(1);
//                default -> {
//                }
//            }
//        }
//        accountSql.closeConnection();
//        paymentHistorySql.closeConnection();
//    }

    private void printInstructions() {
        System.out.println("\n\tPress:");
        System.out.println("\t 1 - Profile");
        System.out.println("\t 2 - Account balance");
        System.out.println("\t 3 - Send payment");
        System.out.println("\t 4 - Payment history");
        System.out.println("\t 5 - Log out");
        System.out.println("\t 6 - Close application");
        System.out.print("\n\tYour option: ");
    }


//    private void userProfile() {
//        boolean flag = true;
//        while (flag) {
//            userProfileInstructions();
//            String selectOption = scanner.nextLine();
//            switch (selectOption) {
//                case "1" -> changeName();
//                case "2" -> changeEmail();
//                case "3" -> changePassword();
//                case "4" -> flag = false;
//            }
//        }
//    }

    private void userProfileInstructions() {
        System.out.println("\n\tPress: ");
        System.out.println("\t 1 - Change your name.");
        System.out.println("\t 2 - Change your email.");
        System.out.println("\t 3 - Change password.");
        System.out.println("\t 4 - Go back to menu. ");
        System.out.print("\n\tYour option: ");
    }

    private void changeName() {
        System.out.print("\tEnter new name: ");
        String newName = scanner.nextLine();
        accountSql.changeName(newName, this.email);
    }

    private void changeEmail() {
        while (true) {
            System.out.print("\tEnter new email: ");
            String newEmail = scanner.nextLine();
            if (!accountSql.emailExistChecker(newEmail) && methods.checkEmailFormat(newEmail)) {
                while (true) {
                    System.out.print("\tEnter your password: ");
                    String password = scanner.nextLine();
                    if (accountSql.passwordCheck(this.email, password)) {
                        accountSql.changeEmail(newEmail, this.email);
                        setEmail(newEmail);
                        break;
                    } else {
                        System.out.println("\tYou entered incorrect password. Try again.");
                    }
                }
                break;
            } else {
                System.out.println("\tEmail already exist. Try again.");
            }
        }
    }

    private void changePassword() {
        System.out.print("\tEnter new password: ");
        String newPassword = scanner.nextLine();

        while (true) {
            System.out.print("\tTo change your password enter old password: ");
            String oldPassword = scanner.nextLine();

            if (accountSql.passwordCheck(this.email, oldPassword)) {
                accountSql.changePassword(newPassword, this.email);
                break;
            } else {
                System.out.println("\tIncorrect password. Try again.");
            }
        }
    }

    private void accountBalance() {
        System.out.println("\tYour account balance is: " + accountSql.accountBalance(getEmail()));
    }

    private void sendPayment() {
        int receiverAccNumber;
        while (true) {
            System.out.print("\tChoose receiver account number: ");
            receiverAccNumber = scanner.nextInt();
            if (accountSql.accountNumberExistChecker(receiverAccNumber)) {
                break;
            } else {
                System.out.println("\tReceiver account number does not exist. Try again. ");
            }
        }

        while (true) {
            System.out.print("\tAmount: ");
            double amount = scanner.nextDouble();

            scanner.nextLine();
            String informationForBeneficiary;

            do {
                System.out.print("\tInformation for beneficiary. Maximum 30 letters: ");
                informationForBeneficiary = scanner.nextLine();
            } while (!methods.getStringLength(informationForBeneficiary, 30));

            if (accountSql.checkAndSubtractSenderAccountBalance(amount, getEmail())) {
                accountSql.sendPayment(receiverAccNumber, amount);

                paymentHistorySql.addPaymentToHistory(getAccNumber(), receiverAccNumber, amount, TypesOfTransactionEnum.Debit, methods.currentDate(), informationForBeneficiary, getAccNumber());
                paymentHistorySql.addPaymentToHistory(receiverAccNumber, getAccNumber(), amount, TypesOfTransactionEnum.Credit, methods.currentDate(), informationForBeneficiary, receiverAccNumber);
                System.out.println("\tPayment sent.");
                break;
            } else {
                System.out.println("\tYou do not have enough money on your bank account. ");
                System.out.println("\tTry again.");
            }
        }
    }

    private void paymentHistoryInstructions() {
        System.out.println("\n\n\t 1 - Show all transaction.");
        System.out.println("\t 2 - Show sent payments.");
        System.out.println("\t 3 - Show received payments.");
        System.out.println("\t 4 - Go back to menu.\n");
        System.out.print("\t Your option: ");
    }

    private void paymentHistoryMenu() {
        boolean flag = true;
        while (flag) {
            paymentHistoryInstructions();
            String selectOptions = scanner.nextLine();
            switch (selectOptions) {
                case "1" -> paymentHistory(TypesOfTransactionEnum.All);
                case "2" -> paymentHistory(TypesOfTransactionEnum.Debit);
                case "3" -> paymentHistory(TypesOfTransactionEnum.Credit);
                case "4" -> flag = false;
            }
        }
    }

    private void paymentHistory(TypesOfTransactionEnum typesOfTransactionEnum) {

        List<paymentHistoryList> paymentHistory = this.paymentHistorySql.queryPaymentHistory(accNumber, typesOfTransactionEnum);

        System.out.println("\n\n\tTransaction history");
        System.out.println("\t-------------------------------");
        System.out.println("\tShow " + typesOfTransactionEnum + " payments:\n");

        if (paymentHistory.isEmpty()) {
            System.out.println("\tYour transaction list is empty.");
        }

        for (paymentHistoryList paymentHistoryList : paymentHistory) {
            System.out.println("\t\t" + accountSql.getNameFromAccountNumber(paymentHistoryList.getAccountToOrFrom()) + " - " + paymentHistoryList.getAccountToOrFrom()
                    + "\n\t\t\t" + paymentHistoryList.getDate()
                    + "\t\t\tAmount: " + paymentHistoryList.getAmount() + "€"
                    + "\t\t\tType of transaction: " + paymentHistoryList.getType()
                    + "\n\t\t\tInformation For Beneficiary: " + paymentHistoryList.getInformationForBeneficiary()
                    + "\n\t\t-----------------------------------------------------------------------------------------------");
        }
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public int getAccNumber() {
        return accNumber;
    }

    public void setAccNumber(int accNumber) {
        this.accNumber = accNumber;
    }
}