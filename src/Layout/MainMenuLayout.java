package Layout;

import ApplicationMenu.*;
import DataSQL.AccountSql;

import javax.swing.*;
import java.awt.*;


public class MainMenuLayout extends ApplicationLayout {
    ImageIcon imageButtonLogIn = new ImageIcon("images\\LogInButton.png");
    ImageIcon imageButtonLogInRollOver = new ImageIcon("images\\LogInButtonRollOver.png");
    ImageIcon imageButtonSignUp = new ImageIcon("images\\SignUpButton.png");
    ImageIcon imageButtonSignUpRollOver = new ImageIcon("images\\SignUpButtonRollOver.png");

    JLabel enabledSymbol = super.enabledSymbol();
    JTextField messageAfterEnteredData = new JTextField();

    JPanel panelMenu;
    JPanel logInContents;
    JPanel signUpContents;

    JTextField textFieldEmailLogIn;
    JPasswordField textFieldPasswordLogIn;
    JTextField textFieldEmailSignUp;
    JPasswordField textFieldPasswordSignUp;
    JTextField textFieldNameSignUp;
    AccountSql datasource = new AccountSql();
    Methods methods = new Methods();
    MainMenu mainMenu = new MainMenu(datasource);



    public MainMenuLayout() {
        menuLayout();
    }

    @Override
    public JFrame menuLayout() {
        super.menuLayout();
        this.add(enabledSymbol);
        this.add(panelMenu());
        this.add(signUpContents());
        this.add(logInContents());
        this.add(super.backgroundImageLabel());
        return super.menuLayout();
    }

    @Override
     public JPanel panelMenu() {
        panelMenu = super.panelMenu();
        panelMenu.add(super.leftSideDate());
        panelMenu.add(logInButton());
        panelMenu.add(signInButton());
        return panelMenu;
    }

    @Override
    public void panelContents(JPanel panelContents,boolean visible) {
        super.panelContents(panelContents,visible);
    }

    private JPanel signUpContents() {
        signUpContents = new JPanel();
        panelContents(signUpContents,false);
        signUpContents.add(super.textAboveTextField("Email", 100,158));
        signUpContents.add(super.textAboveTextField("Password", 100,218));
        signUpContents.add(super.textAboveTextField("Name", 100,278));
        signUpContents.add(signInToAccountButton());
        signUpContents.add(textFieldEmailSignUp());
        signUpContents.add(textFieldPasswordSignUp());
        signUpContents.add(textFieldNameSignUp());
        signUpContents.add(super.titleAboveLayout("Create new account",100,120));
        return signUpContents;
    }

    private JPanel logInContents() {
        logInContents = new JPanel();
        panelContents(logInContents,false);
        logInContents.add(logInToAccountButton());
        logInContents.add(super.textAboveTextField("Email", 100,158));
        logInContents.add(super.textAboveTextField("Password", 100,218));
        logInContents.add(textFieldEmailLogIn());
        logInContents.add(textFieldPasswordLogIN());
        logInContents.add(super.titleAboveLayout("Log In to account",100,120));
        return logInContents;
    }


    private void messageAfterEnteredData(JTextField messageAfterEnteredData, String text, SignUpOrLogInEnum signUpOrLogInEnum) {
        int y = 0;
        if(signUpOrLogInEnum.equals(SignUpOrLogInEnum.LOGIN)) {
            y = 282;
        } else if (signUpOrLogInEnum.equals(SignUpOrLogInEnum.SIGNUP) || signUpOrLogInEnum.equals(SignUpOrLogInEnum.ACCOUNT)) {
            y = 342;
        }

        if (signUpOrLogInEnum.equals(SignUpOrLogInEnum.SIGNUP)) {
            signUpContents.remove(messageAfterEnteredData);
            signUpContents.add(messageAfterEnteredData);
        } else if (signUpOrLogInEnum.equals(SignUpOrLogInEnum.LOGIN)) {
            logInContents.remove(messageAfterEnteredData);
            logInContents.add(messageAfterEnteredData);
        }

        messageAfterEnteredData.setForeground(new Color(125, 3, 3));
        messageAfterEnteredData.setText(text);
        messageAfterEnteredData.setVisible(true);
        messageAfterEnteredData.setBounds(100, y, 300, 20);
        messageAfterEnteredData.setFont(setFont(15));
        messageAfterEnteredData.setEditable(false);
        messageAfterEnteredData.setBorder(null);
        messageAfterEnteredData.setOpaque(false);

        if (signUpOrLogInEnum.equals(SignUpOrLogInEnum.ACCOUNT)) {
            signUpContents.remove(messageAfterEnteredData);
            signUpContents.add(messageAfterEnteredData);
            messageAfterEnteredData.setForeground(new Color(196, 171, 53));
            messageAfterEnteredData.setFont(setFont(16));
        }
    }


    private JButton logInToAccountButton() {
        JButton buttonLogInToAccount = new JButton();
        buttonLogInToAccount.setText("Log In");
        buttonLogInToAccount.setBounds(250, 310, 130, 25);
        super.defaultButtonSet(buttonLogInToAccount,22);

        buttonLogInToAccount.addActionListener(e -> logInToAccountButtonPressed());
        return buttonLogInToAccount;
    }

    private JButton signInToAccountButton() {
        JButton buttonSignUpToAccount = new JButton();
        buttonSignUpToAccount.setText("Sign Up");
        buttonSignUpToAccount.setBounds(250, 370, 130, 25);
        super.defaultButtonSet(buttonSignUpToAccount,22);

        buttonSignUpToAccount.addActionListener(e -> signInToAccountButtonPressed());
        return buttonSignUpToAccount;
    }


    public void logInToAccountButtonPressed() {
        super.enableUnderlineInMenu(0, 0, false);

        datasource.openConnection();

        if (textFieldEmailLogIn.getText().isEmpty() || String.valueOf(textFieldPasswordLogIn.getPassword()).isEmpty()) {
            messageAfterEnteredData(messageAfterEnteredData,  "Fields can't be empty", SignUpOrLogInEnum.LOGIN);

            for(int i = 0; i<2; i++) {
                if (textFieldIsEnabled(textFieldEmailLogIn) && i == 0) {
                    textFieldEmailLogIn.setText("Email");
                } else if (textFieldIsEnabled(textFieldPasswordLogIn) && i == 1) {
                    textFieldPasswordLogIn.setText("Password");
                }
            }


        } else if (mainMenu.logInToAccount(textFieldEmailLogIn.getText(), String.valueOf(textFieldPasswordLogIn.getPassword()))) {
            LogInMenuLayout logInMenuLayout = new LogInMenuLayout(textFieldEmailLogIn.getText(),datasource.getNameFromAccountNumber(datasource.getAccountNumber(textFieldEmailLogIn.getText())), datasource.getAccountNumber(textFieldEmailLogIn.getText()));

            textFieldEmailLogIn.setText("Email");
            textFieldPasswordLogIn.setText("Password");

            datasource.closeConnection();
            this.dispose();
        } else {
            messageAfterEnteredData(messageAfterEnteredData, "Invalid Email or password", SignUpOrLogInEnum.LOGIN);
        }
    }

    private void signInToAccountButtonPressed() {
        datasource.openConnection();

        if((textFieldEmailSignUp.getText().isEmpty()
                || String.valueOf(textFieldPasswordSignUp.getPassword()).isEmpty()
                || textFieldNameSignUp.getText().isEmpty())
                && datasource.emailExistChecker(textFieldEmailSignUp.getText())) {
            messageAfterEnteredData(messageAfterEnteredData,  "Fields can't be empty and Email already exists", SignUpOrLogInEnum.SIGNUP);
            textFieldEmailSignUp.setForeground(new Color(125, 3, 3));
        } else if (textFieldEmailSignUp.getText().isEmpty() || String.valueOf(textFieldPasswordSignUp.getPassword()).isEmpty() || textFieldNameSignUp.getText().isEmpty()) {
            messageAfterEnteredData(messageAfterEnteredData,  "Fields can't be empty", SignUpOrLogInEnum.SIGNUP);
        } else if (datasource.emailExistChecker(textFieldEmailSignUp.getText())) {
            textFieldEmailSignUp.setForeground(new Color(125, 3, 3));
            messageAfterEnteredData(messageAfterEnteredData,  "Email already exists", SignUpOrLogInEnum.SIGNUP);
        } else if(!methods.checkEmailFormat(textFieldEmailSignUp.getText())) {
            messageAfterEnteredData(messageAfterEnteredData, "Bad Email format",SignUpOrLogInEnum.SIGNUP);
        }

        if (textFieldEmailSignUp.getText().isEmpty()
                || String.valueOf(textFieldPasswordSignUp.getPassword()).isEmpty()
                || textFieldNameSignUp.getText().isEmpty()
                || datasource.emailExistChecker(textFieldEmailSignUp.getText())) {
            super.textFieldIsEnabled(textFieldEmailSignUp);
            super.textFieldIsEnabled(textFieldPasswordSignUp);
            super.textFieldIsEnabled(textFieldNameSignUp);
        } else {
            if (methods.checkEmailFormat(textFieldEmailSignUp.getText())) {
                mainMenu.registerAccount(textFieldEmailSignUp.getText(), String.valueOf(textFieldPasswordSignUp.getPassword()), textFieldNameSignUp.getText());
                messageAfterEnteredData(messageAfterEnteredData, "Account was successfully registered",SignUpOrLogInEnum.ACCOUNT);
                textFieldEmailSignUp.setText("Email");
                textFieldPasswordSignUp.setText("Password");
                textFieldNameSignUp.setText("Name");
                datasource.closeConnection();
            } else {
                textFieldEmailSignUp.setForeground(new Color(125, 3, 3));
                System.out.println("Bad Email format");
            }
        }
    }

    private JButton logInButton() {
        JButton buttonLogIn = new JButton();
        buttonLogIn.setBounds(0, 200, 252, 59);
        buttonLogIn.setIcon(imageButtonLogIn);
        buttonLogIn.setRolloverIcon(imageButtonLogInRollOver);
        super.defaultImageButtonSet(buttonLogIn);
        buttonLogIn.addActionListener(e -> logInPanel());
        return buttonLogIn;
    }

    private JButton signInButton() {
        JButton buttonSignUp = new JButton();
        buttonSignUp.setBounds(0, 299, 252, 59);
        buttonSignUp.setIcon(imageButtonSignUp);
        buttonSignUp.setRolloverIcon(imageButtonSignUpRollOver);
        super.defaultImageButtonSet(buttonSignUp);
        buttonSignUp.addActionListener(e -> signInPanel());
        return buttonSignUp;
    }

    private void logInPanel() {
//        datasource.closeConnection();
        super.enableUnderlineInMenu(247, 90, true);
        signUpContents.setVisible(false);
        logInContents.setVisible(true);
    }

    private void signInPanel() {
        super.enableUnderlineInMenu(347, 90, true);
//        datasource.openConnection();
        logInContents.setVisible(false);
        signUpContents.setVisible(true);
    }

    private JTextField textFieldEmailLogIn() {
        this.textFieldEmailLogIn = new JTextField();
        super.textFieldSet(textFieldEmailLogIn, "stevo@mail.com",190, messageAfterEnteredData);
        this.textFieldEmailLogIn.setBounds(100, 190, 250, 30);
        return textFieldEmailLogIn;
    }

    private JPasswordField textFieldPasswordLogIN() {
        textFieldPasswordLogIn = new JPasswordField();
        super.textFieldSet(textFieldPasswordLogIn, "12345",190,messageAfterEnteredData);
        textFieldPasswordLogIn.setBounds(100, 250, 250, 30);
        return textFieldPasswordLogIn;
    }

    private JTextField textFieldEmailSignUp() {
        textFieldEmailSignUp = new JTextField();
        super.textFieldSet(textFieldEmailSignUp, "Email",190,messageAfterEnteredData);
        textFieldEmailSignUp.setBounds(100, 190, 250, 30);
        return textFieldEmailSignUp;
    }

    private JPasswordField textFieldPasswordSignUp() {
        textFieldPasswordSignUp = new JPasswordField();
        super.textFieldSet(textFieldPasswordSignUp, "Password",250,messageAfterEnteredData);
        textFieldPasswordSignUp.setBounds(100, 250, 250, 30);
        return textFieldPasswordSignUp;
    }

    private JTextField textFieldNameSignUp() {
        textFieldNameSignUp = new JTextField();
        super.textFieldSet(textFieldNameSignUp, "Name",310,messageAfterEnteredData);
        textFieldNameSignUp.setBounds(100, 310, 250, 30);
        return textFieldNameSignUp;
    }

}
