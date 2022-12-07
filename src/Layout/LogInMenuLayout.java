package Layout;

import ApplicationMenu.Methods;
import ApplicationMenu.TypesOfTransactionEnum;
import DataSQL.AccountSql;
import DataSQL.PaymentHistorySql;

import javax.swing.*;
import javax.swing.border.Border;
import javax.swing.border.MatteBorder;
import java.awt.*;

public class LogInMenuLayout extends ApplicationLayout {

    ImageIcon imageButtonAccount = new ImageIcon("images\\AccountButton.png");
    ImageIcon imageButtonAccountRollOver = new ImageIcon("images\\AccountButtonRollOver.png");
    ImageIcon imageButtonSendMoney = new ImageIcon("images\\SendMoneyButton.png");
    ImageIcon imageButtonSendMoneyRollOver = new ImageIcon("images\\SendMoneyButtonRollOver.png");
    ImageIcon imageButtonLogout = new ImageIcon("images\\LogoutButton.png");
    ImageIcon imageButtonLogoutRollOver = new ImageIcon("images\\LogoutButtonRollOver.png");
    ImageIcon imageButtonPayments = new ImageIcon("images\\PaymentsButton.png");
    ImageIcon imageButtonPaymentsRollOver = new ImageIcon("images\\PaymentsButtonRollOver.png");
    ImageIcon topLayoutIcon = new ImageIcon("images\\topLayout.png");
    ImageIcon rightMenuIcon = new ImageIcon("images\\rightMenu.png");
    ImageIcon middleLayoutIcon = new ImageIcon("images\\middleLayout.png");
    ImageIcon rightMenuPopUpIcon = new ImageIcon("images\\rightMenuPopUp.png");
    ImageIcon balanceBgImageIcon = new ImageIcon("images\\balanceBgImage.png");
    ImageIcon messageSymbolGreyIcon = new ImageIcon("images\\messageSymbolGrey.png");
    ImageIcon messageSymbolYellowIcon = new ImageIcon("images\\messageSymbolYellow.png");
    ImageIcon sendPaymentBackground = new ImageIcon("images\\sendPaymentBackground.png");
    ImageIcon paymentsBackground = new ImageIcon("images\\paymentsBackground.png");


    JButton changeNameButton = new JButton();
    JButton changeEmailButton = new JButton();
    JButton changePasswordButton = new JButton();
    JButton sendPaymentButton = new JButton();


    JLabel changeNameLabel;
    JLabel changeEmailLabel;
    JLabel changePasswordLabel;
    JLabel nullLabel = new JLabel();

    JTextField textField = new JTextField();
    JTextField changeName;
    JTextField changeEmail;
    JPasswordField changeNamePasswordNeeded;
    JPasswordField changeEmailPasswordNeeded;
    JPasswordField changePasswordNewPassword;
    JPasswordField changePasswordOldPassword;

    JTextField beneficiary;
    JTextField beneficiaryAccountNumber;
    JTextField amount;
    JTextArea informationForBeneficiary;
    JTextField accountBalanceShow;

    JLabel enabledSymbol = super.enabledSymbol();
    JPanel panelMenu;
    JPanel panelAccount;
    JPanel panelMiddleLayout;
    JPanel panelSendPayment;
    JPanel textInputArea;
    JPanel payments;
    JLabel balance;


    String userEmail;
    String userName;
    int accountNumber;
    boolean isClicked = true;

    Methods methods = new Methods();
    AccountSql accountSql = new AccountSql();
    PaymentHistorySql paymentHistorySql = new PaymentHistorySql();


    public LogInMenuLayout(String userEmail, String userName, int accountNumber) {
        accountSql.openConnection();
        paymentHistorySql.openConnection();
        this.userEmail = userEmail;
        this.userName = userName;
        this.accountNumber = accountNumber;
        menuLayout();
    }

    @Override
    public JFrame menuLayout() {
        super.menuLayout();
        this.add(topPanel());
        this.add(panelPayments());
        this.add(panelSendPayment());
        this.add(panelMenu());
        this.add(panelAccount());
        this.add(panelMiddleLayout());
        this.add(super.backgroundImageLabel());
        return super.menuLayout();
    }

    @Override
    public JPanel panelMenu() {
        super.panelMenu();
        panelMenu = super.panelMenu();
        panelMenu.add(super.leftSideDate());
        panelMenu.add(enabledSymbol);
        panelMenu.add(nameText());
        panelMenu.add(accountButton());
        panelMenu.add(sendMoneyButton());
        panelMenu.add(paymentsButton());
        panelMenu.add(logoutButton());
        return panelMenu;
    }

    @Override
    public void panelContents(JPanel panelContents, boolean visible) {
        super.panelContents(panelContents, visible);
    }

    private JPanel panelMiddleLayout() {
        panelMiddleLayout = new JPanel();
        panelMiddleLayout.setVisible(false);
        panelContents(panelMiddleLayout, panelMiddleLayout.isVisible());

        JLabel middleLayoutBackground = new JLabel();
        middleLayoutBackground.setOpaque(false);
        middleLayoutBackground.setLayout(null);
        middleLayoutBackground.setBounds(15, 150, 244, 289);
        middleLayoutBackground.setIcon(middleLayoutIcon);
        middleLayoutBackground.add(changeNameLabel());
        middleLayoutBackground.add(changeEmailLabel());
        middleLayoutBackground.add(changePasswordLabel());

        panelMiddleLayout.add(middleLayoutBackground);
        changeNameButton.addActionListener(e -> changePopUpWindow(changeNameLabel));
        changeEmailButton.addActionListener(e -> changePopUpWindow(changeEmailLabel));
        changePasswordButton.addActionListener(e -> changePopUpWindow(changePasswordLabel));
        return panelMiddleLayout;
    }

    private void changePopUpWindow(JLabel changeLabel) {
        balance.setVisible(false);
        panelMiddleLayout.setVisible(true);
        changeEmailLabel.setVisible(false);
        changeNameLabel.setVisible(false);
        changePasswordLabel.setVisible(false);
        changeAllTextsInChangeTextFields();
        rightMenuPressed(false);
        changeLabel.setVisible(true);
    }

    private void changeAllTextsInChangeTextFields() {
        changeName.setText("Name");
        changeNamePasswordNeeded.setText("Password");
        changeEmail.setText("Email");
        changeEmailPasswordNeeded.setText("Password");
        changePasswordOldPassword.setText("Password");
        changePasswordNewPassword.setText("Password");
        textField.setText("");
    }


    private JLabel changeNameLabel() {
        changeNameLabel = new JLabel();
        changeNameLabel.setBounds(15, 15, 244, 289);
        changeName = new JTextField();
        changeNamePasswordNeeded = new JPasswordField();

        super.textFieldSet(changeName, "Name", 90,textField);
        super.textFieldSet(changeNamePasswordNeeded, "Password", 140,textField);

        changeNameLabel.setLayout(null);
        changeNameLabel.add(super.titleAboveLayout("Change Name", 25, 10));
        changeNameLabel.add(super.textAboveTextField("Enter new name", 25, 60));
        changeNameLabel.add(changeName);
        changeNameLabel.add(super.textAboveTextField("Enter password", 25, 110));
        changeNameLabel.add(changeNamePasswordNeeded);
        changeNameLabel.add(changeButton(ButtonChangeType.NAME));
        return changeNameLabel;
    }

    private JLabel changeEmailLabel() {
        changeEmailLabel = new JLabel();
        changeEmailLabel.setBounds(15, 15, 244, 289);
        changeEmail = new JTextField();
        changeEmailLabel.setLayout(null);
        changeEmailPasswordNeeded = new JPasswordField();

        super.textFieldSet(changeEmail, "Email", 90,textField);
        super.textFieldSet(changeEmailPasswordNeeded, "Password", 140,textField);

        changeEmailLabel.add(super.titleAboveLayout("Change Email", 25, 10));
        changeEmailLabel.add(super.textAboveTextField("Enter new email", 25, 60));
        changeEmailLabel.add(changeEmail);
        changeEmailLabel.add(super.textAboveTextField("Enter password", 25, 110));
        changeEmailLabel.add(changeEmailPasswordNeeded);
        changeEmailLabel.add(changeButton(ButtonChangeType.EMAIL));
        return changeEmailLabel;
    }


    private JLabel changePasswordLabel() {
        changePasswordLabel = new JLabel();
        changePasswordLabel.setBounds(15, 15, 244, 289);
        changePasswordNewPassword = new JPasswordField();
        changePasswordOldPassword = new JPasswordField();

        super.textFieldSet(changePasswordNewPassword, "Password", 90,textField);
        super.textFieldSet(changePasswordOldPassword, "Password", 140,textField);

        changePasswordLabel.setLayout(null);
        changePasswordLabel.add(super.titleAboveLayout("Change Password", 25, 10));
        changePasswordLabel.add(super.textAboveTextField("Enter NEW password", 25, 60));
        changePasswordLabel.add(changePasswordNewPassword);
        changePasswordLabel.add(super.textAboveTextField("Enter OLD password", 25, 110));
        changePasswordLabel.add(changePasswordOldPassword);
        changePasswordLabel.add(changeButton(ButtonChangeType.PASSWORD));
        return changePasswordLabel;
    }


    private JButton changeButton(ButtonChangeType buttonChangeType) {
        JButton changeButton = new JButton();
        changeButton.setBounds(120, 200, 70, 70);
        changeButton.setText("Change");
        defaultButtonSet(changeButton, 22);

        if (buttonChangeType.equals(ButtonChangeType.NAME)) {
            changeButton.addActionListener(e -> nameChangeButtonPressed());
        } else if (buttonChangeType.equals(ButtonChangeType.EMAIL)) {
            changeButton.addActionListener(e -> emailChangeButtonPressed());
        } else if (buttonChangeType.equals(ButtonChangeType.PASSWORD)) {
            changeButton.addActionListener(e -> passwordChangeButtonPressed());
        }
        return changeButton;
    }

    private void nameChangeButtonPressed() {
        if (changeName.getText().isEmpty() || String.valueOf(changeNamePasswordNeeded.getPassword()).isEmpty()) {
            changeNameLabel.add(messageAfterEnteredData("Fields can't be empty",false,25,165));
            super.textFieldIsEnabled(changeName);
            super.textFieldIsEnabled(changeNamePasswordNeeded);
        } else if (accountSql.passwordCheck(getUserEmail(), String.valueOf(changeNamePasswordNeeded.getPassword()))) {
            changeNameLabel.add(messageAfterEnteredData("Name Successfully changed",true,25,165));
            accountSql.changeName(changeName.getText(), getUserEmail());
        } else {
            changeNameLabel.add(messageAfterEnteredData("Entered incorrect password",false,25,165));
        }
    }

    private void emailChangeButtonPressed() {
        if (changeEmail.getText().isEmpty() || String.valueOf(changeEmailPasswordNeeded.getPassword()).isEmpty()) {
            changeEmailLabel.add(messageAfterEnteredData("Fields can't be empty",false,25,165));
            super.textFieldIsEnabled(changeEmail);
            super.textFieldIsEnabled(changeEmailPasswordNeeded);
        } else if (methods.checkEmailFormat(changeEmail.getText())) {
            if (accountSql.passwordCheck(getUserEmail(), String.valueOf(changeEmailPasswordNeeded.getPassword()))) {
                changeEmailLabel.add(messageAfterEnteredData("Email Successfully changed",true,25,165));
                accountSql.changeEmail(changeEmail.getText(), getUserEmail());
            } else {
                changeEmailLabel.add(messageAfterEnteredData("Entered incorrect password",false,25,165));
            }
        } else {
            changeEmailLabel.add(messageAfterEnteredData("Bad Email format",false,25,165));
        }
    }

    private void passwordChangeButtonPressed() {
        if (String.valueOf(changePasswordOldPassword.getPassword()).isEmpty() || String.valueOf(changePasswordNewPassword.getPassword()).isEmpty()) {
            changePasswordLabel.add(messageAfterEnteredData("Fields can't be empty",false,25,165));
            super.textFieldIsEnabled(changePasswordOldPassword);
            super.textFieldIsEnabled(changePasswordNewPassword);
        } else if (accountSql.passwordCheck(getUserEmail(), String.valueOf(changePasswordOldPassword.getPassword()))) {
            changePasswordLabel.add(messageAfterEnteredData("Password Successfully changed",true,25,165));
            accountSql.changePassword(String.valueOf(changePasswordNewPassword.getPassword()), getUserEmail());
        } else {
            changePasswordLabel.add(messageAfterEnteredData("Entered incorrect password",false,25,165));
        }
    }

    private JTextField messageAfterEnteredData(String text,boolean successfully,int x, int y) {
        if(!successfully) {
            textField.setForeground(new Color(125, 3, 3));
        } else {
            textField.setForeground(new Color(196, 171, 53));
        }

        textField.setText(text);
        textField.setOpaque(false);
        textField.setBorder(null);
        textField.setVisible(true);
        textField.setEditable(false);
        textField.setBounds(x, y,300, 20); // 25,165
        textField.setFont(setFont(15));
        return textField;
    }


    private JPanel panelAccount() {
        panelAccount = new JPanel();
        panelAccount.setVisible(false);
        panelContents(panelAccount, panelAccount.isVisible());

        panelAccount.add(rightMenuPopUpMenu("> Change Name", 200, changeNameButton));
        panelAccount.add(rightMenuPopUpMenu("> Change Email", 240, changeEmailButton));
        panelAccount.add(rightMenuPopUpMenu("> Change Password", 280, changePasswordButton));

        panelAccount.add(balanceWindow());
        panelAccount.add(rightMenu());
        return panelAccount;
    }


    private JLabel balanceWindow() {
        balance = new JLabel();
        balance.setIcon(balanceBgImageIcon);
        balance.setOpaque(false);
        balance.setVisible(true);
        balance.setBounds(15, 150, 244, 289);
        balance.setBorder(null);

        JTextField textBalance = new JTextField();
        JTextField textAcc = new JTextField();
        JTextField textNumber = new JTextField();
        JTextField accNumber = new JTextField();

        balance.add(balanceWindowText(textBalance, "balance", 8, 87, 80, 24, "grey"));
        balance.add(balanceWindowText(textAcc, "acc", 18, 8, 40, 24, "grey"));
        balance.add(balanceWindowText(textNumber, "number", 8, 22, 90, 24, "grey"));
        balance.add(balanceWindowText(accNumber, String.valueOf(getAccountNumber()), 125, 22, 120, 28, "yellow"));
        balance.add(balanceShow());
        return balance;
    }

    private JTextField balanceWindowText(JTextField balanceText, String text, int x, int y, int width, int fontSize, String color) {
        balanceText.setText(text);
        balanceText.setBounds(x, y, width, 30);
        balanceText.setFont(setFont(fontSize));
        if (color.equals("yellow")) {
            balanceText.setForeground(new Color(196, 171, 53));
        } else {
            balanceText.setForeground(new Color(202, 202, 202));
        }

        balanceText.setLayout(null);
        balanceText.setOpaque(false);
        balanceText.setBorder(null);
        return balanceText;
    }

    /**
     * Method which set value of textField which show current account balance.
     *
     * @return accountBalanceShow;
     */
    private JTextField balanceShow() {
        accountBalanceShow = new JTextField();
        accountBalanceShow.setOpaque(false);
        accountBalanceShow.setLayout(null);
        accountBalanceShow.setBounds(93, 107, 95, 30);
        accountBalanceShow.setHorizontalAlignment(JTextField.CENTER);

        if (String.valueOf(accountSql.accountBalance(getUserEmail())).length() == 6) {
            accountBalanceShow.setFont(super.setFont(22));
        } else if (String.valueOf(accountSql.accountBalance(getUserEmail())).length() > 7) {
            accountBalanceShow.setFont(super.setFont(18));
        } else {
            accountBalanceShow.setFont(super.setFont(24));
        }

        accountBalanceShow.setEditable(false);
        accountBalanceShow.setBorder(null);
        accountBalanceShow.setForeground(new Color(242, 242, 242));

        return accountBalanceShow;
    }


    /**
     * Method Show top panel which is above main menu
     * It is shown all time.
     *
     * @return topPanel;
     */
    private JLabel topPanel() {
        JLabel topPanel = new JLabel();
        topPanel.setIcon(topLayoutIcon);
        topPanel.setBounds(285, 130, 487, 57);
        topPanel.setOpaque(false);
        topPanel.setVisible(true);
        topPanel.add(messagesButton());
        topPanel.add(backButton());
        return topPanel;
    }

    private JButton rightMenu() {
        JButton rightMenu = new JButton();
        rightMenu.setIcon(rightMenuIcon);
        rightMenu.setBounds(279, 150, 187, 46);
        rightMenu.setText("» Menu");
        rightMenu.setHorizontalTextPosition(JButton.CENTER);
        rightMenu.setVerticalTextPosition(JButton.CENTER);
        super.defaultButtonSet(rightMenu, 22);


        if (isClicked()) {
            rightMenu.addActionListener(e -> rightMenuPressed(isClicked()));
        } else {
            rightMenu.addActionListener(e -> rightMenuPressed(isClicked()));
        }

        return rightMenu;
    }

    private void rightMenuPressed(boolean pressed) {
        if (pressed) {
            changeNameButton.setVisible(true);
            changeEmailButton.setVisible(true);
            changePasswordButton.setVisible(true);
            setClicked(false);
        } else {
            changeNameButton.setVisible(false);
            changeEmailButton.setVisible(false);
            changePasswordButton.setVisible(false);
            setClicked(true);
        }
    }

    private JButton rightMenuPopUpMenu(String text, int y, JButton button) {
        button.setText(text);
        button.setLayout(null);
        button.setBounds(321, y, 145, 38);
        super.defaultButtonSet(button, 15);

        button.setIcon(rightMenuPopUpIcon);
        button.setOpaque(false);
        button.setVisible(false);
        button.setHorizontalTextPosition(JButton.CENTER);
        button.setVerticalTextPosition(JButton.CENTER);
        return button;
    }




    private JButton messagesButton() {
        JButton messagesButton = new JButton();
        messagesButton.setBounds(390, 9, 55, 40);
        messagesButton.setIcon(messageSymbolGreyIcon);
        messagesButton.setRolloverIcon(messageSymbolYellowIcon);
        super.defaultImageButtonSet(messagesButton);
        return messagesButton;
    }

    private JButton backButton() {
        JButton backButton = new JButton();
        backButton.setBounds(15, 9, 100, 40);
        backButton.setText("< Back");
        super.defaultButtonSet(backButton, 22);
        return backButton;
    }

    /**
     * -------------------
     *  Left side panel buttons and perform action after click on it.
     */
    private JButton accountButton() {
        JButton buttonAccount = new JButton();
        buttonAccount.setBounds(1, 200, 252, 59);
        buttonAccount.setIcon(imageButtonAccount);
        buttonAccount.setRolloverIcon(imageButtonAccountRollOver);
        super.defaultImageButtonSet(buttonAccount);
        buttonAccount.addActionListener(e -> accountButtonPressed());
        return buttonAccount;
    }
    private void accountButtonPressed() {
        super.enableUnderlineInMenu(247, 160, true);
        accountBalanceShow.setText(String.valueOf(accountSql.accountBalance(getUserEmail())));
        rightMenuPressed(false);
        panelSendPayment.setVisible(false);
        panelAccount.setVisible(true);
        balance.setVisible(true);
        panelMiddleLayout.setVisible(false);
    }
    private JButton sendMoneyButton() {
        JButton buttonSendMoney = new JButton();
        buttonSendMoney.setBounds(1, 300, 252, 59);
        buttonSendMoney.setIcon(imageButtonSendMoney);
        buttonSendMoney.setRolloverIcon(imageButtonSendMoneyRollOver);
        super.defaultImageButtonSet(buttonSendMoney);
        buttonSendMoney.addActionListener(e -> sendMoneyButtonPressed());
        return buttonSendMoney;
    }
    private void sendMoneyButtonPressed() {
        super.enableUnderlineInMenu(347, 160, true);
        changePopUpWindow(nullLabel);
        panelSendPayment.setVisible(true);
        panelAccount.setVisible(false);
        payments.setVisible(false);
    }
    private JButton paymentsButton() {
        JButton buttonPayments = new JButton();
        buttonPayments.setBounds(1, 400, 252, 59);
        buttonPayments.setIcon(imageButtonPayments);
        buttonPayments.setRolloverIcon(imageButtonPaymentsRollOver);
        super.defaultImageButtonSet(buttonPayments);
        buttonPayments.addActionListener(e -> paymentsButtonPressed());
        return buttonPayments;
    }
    private void paymentsButtonPressed() {
        super.enableUnderlineInMenu(447, 160, true);
        changePopUpWindow(nullLabel);
        panelSendPayment.setVisible(false);
        panelAccount.setVisible(false);
        payments.setVisible(true);
    }
    private JButton logoutButton() {
        JButton buttonLogout = new JButton();
        buttonLogout.setBounds(1, 550, 252, 59);
        buttonLogout.setIcon(imageButtonLogout);
        buttonLogout.setRolloverIcon(imageButtonLogoutRollOver);
        super.defaultImageButtonSet(buttonLogout);

        buttonLogout.addActionListener(e -> logoutButtonPressed());
        return buttonLogout;
    }

    private void logoutButtonPressed() {
        accountSql.closeConnection();
        paymentHistorySql.closeConnection();
        this.dispose();
        MainMenuLayout mainMenuLayout = new MainMenuLayout();
        this.setVisible(false);
        mainMenuLayout.setVisible(true);
    }


    /**
     * jTextArea which show name above left side buttons. Automatically update from database according to log in user.
     *
     * @return nameText;
     */
    private JTextArea nameText() {
        JTextArea nameText = new JTextArea();
        nameText.setBounds(35, 100, 190, 40);
        nameText.setVisible(true);
        nameText.setOpaque(false);
        nameText.setEditable(false);
        nameText.setBorder(null);
        if (getUserName().length() > 17) {
            nameText.setFont(super.setFont(18));
            nameText.setLineWrap(true);
            nameText.setWrapStyleWord(true);
        } else {
            nameText.setFont(super.setFont(22));
        }
        nameText.setForeground(new Color(196, 171, 53));
        nameText.setText(getUserName());
        return nameText;
    }


    public String getUserEmail() {
        return userEmail;
    }

    public void setUserEmail(String userEmail) {
        this.userEmail = userEmail;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public int getAccountNumber() {
        return accountNumber;
    }

    public void setAccountNumber(int accountNumber) {
        this.accountNumber = accountNumber;
    }

    public boolean isClicked() {
        return isClicked;
    }

    public void setClicked(boolean clicked) {
        isClicked = clicked;
    }


    /**
     * Send payment panels.
     *
     */
    private JPanel panelSendPayment() {
        panelSendPayment = new JPanel();
        JLabel labelSendPayment = new JLabel();
        labelSendPayment.setIcon(sendPaymentBackground);
        labelSendPayment.setBounds(0,0,486,404);
        panelSendPayment.setBounds(285,197,486,404);
        panelSendPayment.setLayout(null);
        panelSendPayment.add(textInputArea());
        panelSendPayment.add(sendPaymentChangingArea());
        panelSendPayment.add(labelSendPayment);
        panelSendPayment.setOpaque(false);
        panelSendPayment.setVisible(false);
        return panelSendPayment;
    }

    private JPanel textInputArea() {
        textInputArea = new JPanel();
        beneficiary = new JTextField();
        beneficiary.setText("");
        beneficiaryAccountNumber = new JTextField();
        beneficiaryAccountNumber.setText("");
        amount = new JTextField();
        amount.setText("");
        informationForBeneficiary = new JTextArea();
        informationForBeneficiary.setText("");

        textFieldSet(beneficiary,"Beneficiary Name", 80,textField);
        textFieldSet(beneficiaryAccountNumber,"Account number", 130,textField);
        textFieldSet(amount,"Amount", 180,textField);
        textAreaSet(informationForBeneficiary,"Information for Beneficiary", 25,230,200,80,textField);

        textInputArea.add(sendPaymentButton());
        textInputArea.add(super.scrollPanel(informationForBeneficiary,25,230,200,80));
        textInputArea.add(super.titleAboveLayout("Send Payment",25,15));
        textInputArea.add(super.textAboveTextField("Beneficiary Name",25,50));
        textInputArea.add(beneficiary);
        textInputArea.add(super.textAboveTextField("Beneficiary Account number",25,100));
        textInputArea.add(beneficiaryAccountNumber);
        textInputArea.add(super.textAboveTextField("Amount",25,150));
        textInputArea.add(amount);
        textInputArea.add(super.textAboveTextField("Information for Beneficiary",25,200));
//        textInputArea.add(informationForBeneficiary);



        textInputArea.setVisible(true);
        textInputArea.setOpaque(false);
        textInputArea.setLayout(null);
        textInputArea.setBackground(Color.green);

        textInputArea.setBounds(6,8,270,390);
        return textInputArea;
    }

    private JPanel sendPaymentChangingArea() {
        JPanel sendPaymentChangingArea = new JPanel();
        sendPaymentChangingArea.setVisible(true);
//        sendPaymentChangingArea.setOpaque(false);
        sendPaymentChangingArea.setLayout(null);
        sendPaymentChangingArea.setBackground(Color.blue);
        sendPaymentChangingArea.setBounds(284,8,197,390);
        return sendPaymentChangingArea;
    }

    private JButton sendPaymentButton() {
        super.defaultButtonSet(sendPaymentButton,25);
        sendPaymentButton.setBounds(160,320,80,70);
        sendPaymentButton.setText("Send");
        sendPaymentButton.addActionListener(e -> pressedSendPaymentButton());
        return sendPaymentButton;
    }

    private void pressedSendPaymentButton() {
        if(!accountSql.accountNumberExistChecker(Integer.parseInt(beneficiaryAccountNumber.getText())) && !accountSql.checkAndSubtractSenderAccountBalance(Double.parseDouble(amount.getText()),getUserEmail())) {
            textInputArea.add(messageAfterEnteredData("Not enough money",false,25,340));
            textInputArea.add(messageAfterEnteredData("Beneficiary account does not exist",false,25,310));
        }

        if(accountSql.accountNumberExistChecker(Integer.parseInt(beneficiaryAccountNumber.getText()))) {
            if(accountSql.checkAndSubtractSenderAccountBalance(Double.parseDouble(amount.getText()),getUserEmail())) {
                accountSql.sendPayment(Integer.parseInt(beneficiaryAccountNumber.getText()),Double.parseDouble(amount.getText()));

                paymentHistorySql.addPaymentToHistory(getAccountNumber(),
                        Integer.parseInt(beneficiaryAccountNumber.getText()),
                        Double.parseDouble(amount.getText()),
                        TypesOfTransactionEnum.Debit,
                        methods.currentDate(),
                        informationForBeneficiary.getText(),
                        getAccountNumber());

                paymentHistorySql.addPaymentToHistory(Integer.parseInt(beneficiaryAccountNumber.getText()),
                        getAccountNumber(),
                        Double.parseDouble(amount.getText()),
                        TypesOfTransactionEnum.Credit,
                        methods.currentDate(),
                        informationForBeneficiary.getText(),
                        Integer.parseInt(beneficiaryAccountNumber.getText()));

                textInputArea.add(messageAfterEnteredData("Payment proceed successfully",true,25,310));
            } else {
                textInputArea.add(messageAfterEnteredData("Not enough money",false,25,310));
            }
        } else {
            textInputArea.add(messageAfterEnteredData("Beneficiary account does not exist",false,25,310));
        }
    }






    private JPanel panelPayments() {
        payments = new JPanel();
        JLabel labelPayments = new JLabel();
        labelPayments.setIcon(paymentsBackground);
        labelPayments.setBounds(0,0,486,404);
        payments.setBounds(285,197,486,400);
        payments.setLayout(null);
        payments.setOpaque(false);
        payments.setVisible(false);

        payments.add(super.titleAboveLayout("Payments",200,10));
        payments.add(scrollPanel(paymentHistory(),8,50,470,350));
//        payments.add(paymentHistory());
        payments.add(labelPayments);
        return payments;
    }

    private JPanel paymentHistory() {
        int numbersOfTransactions = 15;

        JPanel paymentHistory = new JPanel();
        paymentHistory.setPreferredSize(new Dimension(470,(numbersOfTransactions * 50) + (numbersOfTransactions * 5) - 10 ));
        paymentHistory.setMinimumSize(new Dimension(470,350));
        paymentHistory.setMaximumSize(new Dimension(470,Integer.MAX_VALUE));
        paymentHistory.setBackground(new Color(37,11,53));
//        paymentHistory.setBackground(null);
        paymentHistory.setLayout(new BoxLayout(paymentHistory,BoxLayout.PAGE_AXIS));

        for(int i = 0; i < numbersOfTransactions; i++) {
            System.out.println(i);
            paymentHistory.add(paymentHistoryRecord());
            paymentHistory.add(Box.createRigidArea(new Dimension(430,5)));
        }

        return paymentHistory;
    }

    private JPanel paymentHistoryRecord() {
        JPanel record = new JPanel();
        record.setMaximumSize(new Dimension(430,50));
        record.setMinimumSize(new Dimension(430,50));
        record.setBackground(new Color(37,11,53));
//        record.setBackground(null);
        record.setBorder(setBorder(103,92,111,1));
        record.setBorder(new MatteBorder(1,0,1,0, new Color(103,92,111)));


        JTextField plusOrMinusText = new JTextField();
        record.add(super.textInPaymentHistoryBox(plusOrMinusText,"+",20,"green"));

        return record;
    }


}
