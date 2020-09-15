package ui;

import model.*;
import persistence.Reader;
import persistence.Writer;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.List;

// catalogue application
public class CatalogueApp {

    private Buyer buyer;
    private Buyer buyer2;
    private static final String BUYERS_FILE = "./data/buyers.txt";
    private static List<Buyer> buyers;
    private static Writer writer;

    public JFrame frame;

    private JPanel buttonPane;
    private JPanel mainPanel;
    private JPanel signInMain;
    private JPanel logInMain;

    private JButton createBtn;
    private JButton signInBtn;
    private JButton createAcctBtn;
    private JButton login;

    private String createAcct = "create new account";
    private String signInAcct = "sign-in";

    private JLabel welcome;
    private JLabel usernameLabel;
    private JLabel passwordLabel;

    private JTextField username;
    private JTextField password;

    private JPasswordField passwordField;

    //EFFECTS: run catalogue app and process user input
    public CatalogueApp() {
        loginPanel();
    }

    //MODIFIES: this
    //EFFECTS: creates frame and welcome panel
    public void loginPanel() {
        frame = new JFrame();
        mainPanel = new JPanel();
        frame.setContentPane(mainPanel);
        mainPanel.setLayout(new BoxLayout(this.mainPanel, BoxLayout.PAGE_AXIS));
        buttonPane = new JPanel();
        GridLayout gridLayout = new GridLayout(0, 2);
        buttonPane.setLayout(gridLayout);
        gridLayout.setHgap(50);
        buttonPane.setPreferredSize(new Dimension(600, 600));
        welcome = new JLabel("  Welcome to Edwin's Catalog, create account or login to proceed  ");
        Font font = welcome.getFont();
        welcome.setFont(font.deriveFont(20.0F));
        welcome.setAlignmentX(Component.CENTER_ALIGNMENT);
        mainPanel.add(welcome);
        createButtonSet();
        loginButtonSet();
        mainPanel.add(buttonPane);
        frame.pack();
        frame.setVisible(true);
    }

    // MODIFIES: this
    // EFFECTS: declares and sets create account button
    private void createButtonSet() {
        createBtn = new JButton(createAcct);
        createBtn.setActionCommand(createAcct);
        createBtn.addActionListener(new CreateAccountInit());
        buttonPane.add(createBtn);
    }

    //EFFECTS: prompts account creation panel when create account button is activated
    private class CreateAccountInit implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            createAccount();
        }
    }

    //REQUIRES: username has a non-zero length
    //MODIFIES: this
    //EFFECTS: prompts account declaration, ask for user input
    public void createAccount() {
        logInMain = new JPanel();
        logInMain.setSize(2000, 1000);
        frame.setContentPane(logInMain);
        logInMain.setLayout(null);
        usernameLabel = new JLabel("set username: ");
        passwordLabel = new JLabel("set password: ");
        createAcctBtn = new JButton("create account");
        username = new JTextField();
        password = new JTextField();
        usernameLabel.setBounds(100, 100, 100, 25);
        passwordLabel.setBounds(100, 200, 100, 25);
        username.setBounds(200, 100, 200, 25);
        password.setBounds(200, 200, 200, 25);
        createAcctBtn.setBounds(100, 300, 100, 25);
        logInMain.add(username);
        logInMain.add(usernameLabel);
        logInMain.add(createAcctBtn);
        logInMain.add(passwordLabel);
        logInMain.add(password);
        createAcctBtn.addActionListener(new CreateAccount());

    }


    //REQUIRES: username length > 0 & username was not taken before
    //MODIFIES: this, buyers and Buyer b
    // EFFECTS: creates Buyer account and opens shopping panel
    private class CreateAccount implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            if (password.getText().length() >= 8) {
                try {
                    buyers = Reader.readBuyers(new File(BUYERS_FILE));
                    Buyer b = new Buyer(username.getText(), password.getText(), null, 0.0);
                    buyers.add(b);
                    ListSet.createAndShowGUI(b, frame);
                } catch (IOException x) {
                    x.printStackTrace();
                }
            }
        }
    }

    // MODIFIES: this
    // EFFECTS: declares and sets login account button
    private void loginButtonSet() {
        signInBtn = new JButton(signInAcct);
        signInBtn.setActionCommand(signInAcct);
        signInBtn.addActionListener(new SignInAccount());
        buttonPane.add(signInBtn);
    }

    //EFFECTS: prompts login account panel
    private class SignInAccount implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            loginAccount();
        }
    }

    //MODIFIES: this
    //EFFECTS: user interface for logging into previously created accounts
    public void loginAccount() {
        signInMain = new JPanel();
        signInMain.setSize(2000, 1000);
        frame.setContentPane(signInMain);
        signInMain.setLayout(null);
        usernameLabel = new JLabel("username: ");
        passwordLabel = new JLabel("password: ");
        username = new JTextField();
        passwordField = new JPasswordField();
        login = new JButton("sign in");
        usernameLabel.setBounds(100, 100, 100, 25);
        passwordLabel.setBounds(100, 200, 100, 25);
        username.setBounds(200, 100, 200, 25);
        passwordField.setBounds(200, 200, 200, 25);
        login.setBounds(100, 300, 100, 25);
        signInMain.add(username);
        signInMain.add(passwordField);
        signInMain.add(login);
        signInMain.add(usernameLabel);
        signInMain.add(passwordLabel);
        login.addActionListener(new Login());
    }

    //MODIFIES: this
    //EFFECTS: logs into account and opens shopping panel
    private class Login implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            loadBuyers();
            for (Buyer b : buyers) {
                if (b.getUsername().equals(username.getText())) {
                    if (b.getPassword().equals(passwordField.getText())) {
                        viewItems(b);
                        break;
                    } else {
                        JLabel incorrect = new JLabel("Incorrect Password");
                        incorrect.setBounds(100, 350, 200, 25);
                        signInMain.add(incorrect);
                        break;
                    }
                }
            }
        }
    }

    //MODIFIES: this and buyer
    //EFFECTS: views items in clothes list and lets buyer add item to cart
    public void viewItems(Buyer b) {
        javax.swing.SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                ListSet.createAndShowGUI(b, frame);
            }
        });
    }

    // MODIFIES: this
    // EFFECTS: loads accounts from ACCOUNTS_FILE, if that file exists;
    // otherwise initializes accounts with default values
    private void loadBuyers() {
        try {
            buyers = Reader.readBuyers(new File(BUYERS_FILE));
        } catch (IOException e) {
            init();
        }
    }

    // MODIFIES: this
    // EFFECTS: initializes buyers
    private void init() {
        buyer = new Buyer("Edwin Keskin", "helper15", null, 0.0);
        buyer2 = new Buyer("Crazy Jack", "Elvis4evr", null, 0.0);
    }

    // EFFECTS: saves customer accounts to BUYERS_FILE
    protected static void saveBuyers() {
        try {
            writer = new Writer(new File(BUYERS_FILE));
            for (Buyer b : buyers) {
                writer.write(b);
            }
            writer.close();
        } catch (FileNotFoundException e) {
            System.out.println("Unable to save accounts to " + BUYERS_FILE);
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
    }
}