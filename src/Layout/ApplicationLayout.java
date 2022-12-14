
package Layout;

import ApplicationMenu.Methods;
import ApplicationMenu.TypesOfTransactionEnum;
import DataSQL.AccountSql;

import javax.swing.*;
import javax.swing.border.Border;
import javax.swing.plaf.basic.BasicScrollBarUI;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class ApplicationLayout extends JFrame {

    JLabel enabledSymbol;
    JPanel panelMenu = new JPanel();
//    JPanel panelContents = new JPanel();
    Methods methods = new Methods();
    AccountSql accountSql = new AccountSql();
    String[] wordsCanBeCleared = {"Email","Password","Name","Beneficiary Name","Account number","Amount","Information for Beneficiary"};

    public JFrame menuLayout() {
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(null);
        setSize(800, 739);
        setVisible(true);
        setResizable(false);
        setTitle("Bank Application");
        return this;
    }

    public JPanel panelMenu() {
        panelMenu = new JPanel();
        panelMenu.setBounds(0, 0, 253, 700);
        panelMenu.setLayout(null);
        panelMenu.setOpaque(false);
        panelMenu.setVisible(true);
        return panelMenu;
    }

    public void panelContents(JPanel panelContents, boolean visible) {
        panelContents.setBounds(270, 50, 530, 700);
        panelContents.setLayout(null);
        panelContents.setVisible(visible);
        panelContents.setOpaque(false);
    }

    protected JLabel backgroundImageLabel() {
        ImageIcon backgroundImage = new ImageIcon("images\\background.png");
        JLabel backgroundImageLabel = new JLabel();
        backgroundImageLabel.setVisible(true);
        backgroundImageLabel.setIcon(backgroundImage);
        backgroundImageLabel.setBounds(0, 0, 800, 700);
        backgroundImageLabel.setLayout(null);
        return backgroundImageLabel;
    }


    protected JTextArea leftSideDate() {
        JTextArea leftSideDate = new JTextArea();
        leftSideDate.setBounds(20, 20, 130, 20);
        leftSideDate.setFont(setFont(16));
//        leftSideDate.setForeground(new Color(196, 171, 53));
        leftSideDate.setForeground(new Color(202, 202, 202));
        leftSideDate.setEditable(false);
        leftSideDate.setOpaque(false);

        //Auto update
        ScheduledExecutorService e = Executors.newSingleThreadScheduledExecutor();
        e.scheduleAtFixedRate(() -> {
            SwingUtilities.invokeLater(() -> leftSideDate.setText(methods.currentDate())); // method that is updated
        }, 0, 1, TimeUnit.SECONDS);

        return leftSideDate;
    }

    protected Font setFont(int size) {
        return new Font("Gill Sans MT", Font.PLAIN, size);
    }

    protected Border setBorder(int r, int g, int b, int thickness) {
        return BorderFactory.createLineBorder(new Color(r, g, b), thickness);
    }

    protected JLabel enabledSymbol() {
        enabledSymbol = new JLabel();
        enabledSymbol.setVisible(false);
        return enabledSymbol;
    }

    protected void enableUnderlineInMenu(int y, int width, boolean visible) {
        ImageIcon imageEnabledSymbol = new ImageIcon("images\\EnabledSymbol.png");
        enabledSymbol.setBounds(80, y, width, 3);
        enabledSymbol.setIcon(imageEnabledSymbol);
        enabledSymbol.setVisible(visible);
    }

    protected void defaultButtonSet(JButton button, int fontSize) {
        button.setFont(setFont(fontSize));
        button.setContentAreaFilled(false);
        button.setLayout(null);
        button.setBorder(null);
        button.setVisible(true);
        button.setOpaque(false);
        button.setRolloverEnabled(true);
        button.setForeground(new Color(202, 202, 202));
        button.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        button.setFocusable(false);

        button.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                button.setForeground(new Color(196, 171, 53));
            }
        });

        button.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseExited(MouseEvent e) {
                button.setForeground(new Color(202, 202, 202));
            }
        });
    }

    protected void defaultImageButtonSet(JButton button) {
        button.setContentAreaFilled(false);
        button.setRolloverEnabled(true);
        button.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        button.setBorder(null);
        button.setFocusable(false);
    }

    protected void textFieldSet(JTextField textField, String text,int y, JTextField messageClear) {

        textField.setLayout(null);
        textField.setFont(setFont(16));
//        textField.setForeground(new Color(55, 55, 55));
        textField.setForeground(new Color(202, 202, 202));
//        textField.setCaretColor(new Color(155, 155, 155));
        textField.setCaretColor(new Color(202, 202, 202));
//        textField.setBorder(setBorder(155, 155, 155, 1));
        textField.setBorder(setBorder(63, 53, 71, 1));
//        textField.setBorder(null);
//        textField.setBackground(new Color(202, 202, 202));
        textField.setBackground(new Color(32, 25, 27));
        textField.setText(text);
        textField.setBounds(25, y, 160, 25);


        textField.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                textField.setBorder(setBorder(63, 53, 71, 1));
                textField.setForeground(new Color(202, 202, 202));
                messageClear.setVisible(false);

                for(int i = 0; i <= wordsCanBeCleared.length - 1;i++) {
                    String element = wordsCanBeCleared[i];
                    if (textField.getText().equals(element)) {
                        textField.setText("");
                    }
                }
            }
        });
    }



    protected void textAreaSet(JTextArea textArea, String text,int x,int y,int width,int height, JTextField messageClear) {

        textArea.setLayout(null);
        textArea.setFont(setFont(16));
        textArea.setForeground(new Color(202, 202, 202));
        textArea.setCaretColor(new Color(202, 202, 202));
        textArea.setBorder(setBorder(63, 53, 71, 1));
        textArea.setBackground(new Color(32, 25, 27));
        textArea.setText(text);
        textArea.setBounds(x, y, width, height);
        textArea.setLineWrap(true);
        textArea.setWrapStyleWord(true);
//        JScrollPane scrollPane = new JScrollPane(textArea,
//                JScrollPane.VERTICAL_SCROLLBAR_ALWAYS,
//                JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS
//        );
//        this.add(scrollPane);

//        scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
//        scrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS);
//        this.add(scrollPane);
//        textArea.add(scrollPane);

        textArea.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                textArea.setBorder(setBorder(63, 53, 71, 1));
                textArea.setForeground(new Color(202, 202, 202)); // 55,55,55
                messageClear.setVisible(false);

                for(int i = 0; i <= wordsCanBeCleared.length - 1;i++) {
                    String element = wordsCanBeCleared[i];
                    if (textArea.getText().equals(element)) {
                        textArea.setText("");
                    }
                }

            }
        });
    }

    protected JLabel textAboveTextField(String text,int x, int y) {
        JLabel textAboveTextField = new JLabel();
        textAboveTextField.setBounds(x, y, 250, 40);
        textAboveTextField.setForeground(new Color(202, 202, 202));
        textAboveTextField.setFont(setFont(15));
        textAboveTextField.setText(text);
        return textAboveTextField;
    }

    protected JTextField titleAboveLayout(String text,int x, int y) {
        JTextField signAndLogInTitle = new JTextField();
        signAndLogInTitle.setBounds(x, y, 250, 30);
        signAndLogInTitle.setForeground(new Color(196, 171, 53));
        signAndLogInTitle.setFont(setFont(25));
        signAndLogInTitle.setText(text);
        signAndLogInTitle.setBorder(null);
        signAndLogInTitle.setOpaque(false);
        signAndLogInTitle.setEditable(false);
        return signAndLogInTitle;

    }

    protected boolean textFieldBorderWhenFieldIsEmpty(JTextField textField) {
        if (textField.getText().isEmpty()) {
            textField.setBorder(setBorder(125, 3, 3, 3));
            return true;
        }
        return false;
    }

    protected JScrollPane scrollPanel(JTextArea textArea, int x, int y, int width, int height) {
        JScrollPane scrollPane = new JScrollPane(
                textArea,
                JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED,
                JScrollPane.HORIZONTAL_SCROLLBAR_NEVER
        );

        scrollPane.getVerticalScrollBar().setUI(new BasicScrollBarUI() {
            @Override
            protected void paintThumb(Graphics g, JComponent c, Rectangle thumbBounds) {
                super.paintThumb(g, c, thumbBounds);
            }

            @Override
            protected JButton createDecreaseButton(int orientation) {
                return createZeroButton();
            }

            @Override
            protected JButton createIncreaseButton(int orientation) {
                return createZeroButton();
            }

            private JButton createZeroButton() {
                JButton jbutton = new JButton();
                jbutton.setPreferredSize(new Dimension(0, 0));
                jbutton.setMinimumSize(new Dimension(0, 0));
                jbutton.setMaximumSize(new Dimension(0, 0));
                return jbutton;
            }
        });

        scrollPane.getVerticalScrollBar().setUnitIncrement(16);
        scrollPane.getVerticalScrollBar().setPreferredSize(new Dimension(10,Integer.MAX_VALUE));
        scrollPane.getVerticalScrollBar().setBackground(new Color(32, 25, 27));
        scrollPane.setBorder(null);
        scrollPane.setBounds(x,y,width,height);



        return scrollPane;
    }

    protected JScrollPane scrollPanel(JPanel jpanel, int x, int y, int width, int height) {
        JScrollPane scrollPane = new JScrollPane(
                jpanel,
                JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED,
                JScrollPane.HORIZONTAL_SCROLLBAR_NEVER
        );

        scrollPane.getVerticalScrollBar().setUI(new BasicScrollBarUI() {
            @Override
            protected void paintThumb(Graphics g, JComponent c, Rectangle thumbBounds) {
                super.paintThumb(g, c, thumbBounds);
            }

            @Override
            protected JButton createDecreaseButton(int orientation) {
                return createZeroButton();
            }

            @Override
            protected JButton createIncreaseButton(int orientation) {
                return createZeroButton();
            }

            private JButton createZeroButton() {
                JButton jbutton = new JButton();
                jbutton.setPreferredSize(new Dimension(0, 0));
                jbutton.setMinimumSize(new Dimension(0, 0));
                jbutton.setMaximumSize(new Dimension(0, 0));
                return jbutton;
            }
        });

        scrollPane.setOpaque(true);
        scrollPane.getVerticalScrollBar().setUnitIncrement(16);
        scrollPane.getVerticalScrollBar().setPreferredSize(new Dimension(10,Integer.MAX_VALUE));
        scrollPane.getVerticalScrollBar().setBackground(new Color(32, 25, 27));
        scrollPane.setViewportView(jpanel);

        scrollPane.setBorder(null);
        scrollPane.setBounds(x,y,width,height);



        return scrollPane;
    }

    protected JTextField textInPaymentHistoryBox(String text, int fontSize, String color, int x, int y, int width, int height) {

        JTextField textField = new JTextField();

        if(color.equals(String.valueOf(TypesOfTransactionEnum.Credit))) {
            textField.setForeground(new Color(149,200,67));
        } else if(color.equals(String.valueOf(TypesOfTransactionEnum.Debit))) {
            textField.setForeground(new Color(170,10,30));
        } else {
            textField.setForeground(new Color(202,202,202));
        }

        textField.setBounds(x,y,width,height);
        textField.setOpaque(true);
        textField.setBorder(null);
        textField.setBackground(null);
        textField.setText(text);
        textField.setEditable(false);
        textField.setFont(setFont(fontSize));
        textField.setLayout(null);
        return textField;
    }

    protected JTextArea textAreaInPaymentHistoryBox(String text, int fontSize, int x, int y, int width, int height) {

        JTextArea textArea = new JTextArea();

        textArea.setForeground(new Color(202,202,202));
        textArea.setBounds(x,y,width,height);
//        textArea.setOpaque(false);

        textArea.setBackground(new Color(37, 11, 53));
        textArea.setText(text);
        textArea.setCaretPosition(0);
        textArea.setEditable(false);
        textArea.setFont(setFont(fontSize));
        textArea.setLayout(null);
        textArea.setLineWrap(true);
        textArea.setWrapStyleWord(true);
        return textArea;
    }

}

