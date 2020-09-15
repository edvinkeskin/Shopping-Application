package ui;

import exceptions.InvalidInputException;
import model.Buyer;
import model.Clothes;

import javax.swing.*;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import static data.ClothesList.clothesList;
import static ui.CatalogueApp.saveBuyers;

// creates visual list version of clothes with add to cart button

public class ListSet extends JPanel implements ListSelectionListener {
    private JButton addBtn;
    private JButton removeBtn;
    private JButton loadBtn;
    private JButton saveBtn;

    private static final String addToCart = "add to cart";
    private static final String removeFromCart = "remove item";
    private static final String loadCart = "load previous cart";
    private static final String saveCart = "save cart";

    private JList list;
    private DefaultListModel listModel;

    private static Buyer customer;
    private static JComponent newContentPane;
    private static JFrame frame;
    private JPanel buttonPane;


    // EFFECTS: creates list with clothing name, price
    //          creates scroll and calls button declaration methods
    public ListSet() {
        super(new BorderLayout());
        buttonPane = new JPanel();
        addButtonSet();
        removeButtonSet();
        loadButtonSet();
        saveButtonSet();
        setVisible(true);

        listModel = new DefaultListModel<>();
        listModel.addElement("Item name  Category  Price");
        clothesPrint(clothesList,listModel);
        listModel.addElement("---------------------------------");
        listModel.addElement("Items in buyers cart");
        if (!loadBtn.isEnabled()) {
            clothesPrint(customer.getCart(),listModel);
        }


        list = new JList(listModel);
        list.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        list.setSelectedIndex(0);
        list.addListSelectionListener(this);
        list.setVisibleRowCount(13);
        JScrollPane listScrollPane = new JScrollPane(list);
        add(listScrollPane, BorderLayout.CENTER);

        JButton exit = new JButton("Exit App");
        exit.addActionListener(new ExitCommand());
        add(exit, BorderLayout.PAGE_START);
    }

    //EFFECTS: handles exit button activation
    private class ExitCommand implements ActionListener {
        @Override
        //EFFECTS: prompts panel change if exit button is clicked
        public void actionPerformed(ActionEvent e) {
            exit();
        }
    }

    // MODIFIES: this
    // EFFECTS: declares and sets add to cart button
    public void addButtonSet() {
        addBtn = new JButton(addToCart);
        addBtn.setActionCommand(addToCart);
        addBtn.addActionListener(new Purchase());

        setButtonPane(addBtn);
    }

    // MODIFIES: this
    // EFFECTS: declares and sets remove item button
    public void removeButtonSet() {
        removeBtn = new JButton(removeFromCart);
        removeBtn.setActionCommand(removeFromCart);
        removeBtn.addActionListener(new Remove());
        removeBtn.setEnabled(false);

        setButtonPane(removeBtn);
    }

    // MODIFIES: this
    // EFFECTS: declares and sets load previous cart button
    public void loadButtonSet() {
        loadBtn = new JButton(loadCart);
        loadBtn.setActionCommand(loadCart);
        loadBtn.addActionListener(new Load());
        if (customer.getCart().size() == 0) {
            loadBtn.setEnabled(false);
        }

        setButtonPane(loadBtn);

    }

    // MODIFIES: this
    // EFFECTS: declares and sets save cart button
    public void saveButtonSet() {
        saveBtn = new JButton(saveCart);
        saveBtn.setActionCommand(saveCart);
        saveBtn.addActionListener(new Save());
        if (customer.getCart().size() == 0) {
            saveBtn.setEnabled(false);
        }

        setButtonPane(saveBtn);
    }


    // MODIFIES: this
    // EFFECTS: sets button layout and adds to panel
    public void setButtonPane(JButton button) {
        buttonPane.setLayout(new BoxLayout(buttonPane,
                BoxLayout.LINE_AXIS));
        buttonPane.add(button);
        buttonPane.add(Box.createHorizontalStrut(5));
        buttonPane.add(Box.createHorizontalStrut(5));
        buttonPane.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));
        add(buttonPane, BorderLayout.PAGE_END);
    }

    // MODIFIES: this
    // EFFECTS: adds items to jlist
    public void clothesPrint(ArrayList<Clothes> clothesArrayList, DefaultListModel jlist) {
        for (Clothes clothes : clothesArrayList) {
            jlist.addElement(clothes.getName() + "         " + clothes.getType()
                    + "        " + clothes.getPrice());
        }
    }


    // required, not used because there is no value change
    @Override
    public void valueChanged(ListSelectionEvent e) {
    }

    //MODIFIES: this and buyer
    //EFFECTS: adds selected item to cart and jlist, beeps if invalid selection made
    class Purchase implements ActionListener {
        public void actionPerformed(ActionEvent e) {
            removeBtn.setEnabled(true);

            int index = list.getSelectedIndex() - 1;

            if (loadBtn.isEnabled()) {
                customer.getCart().clear();
                customer.setSubtotal(0);
                loadBtn.setEnabled(false);
            }

            saveBtn.setEnabled(true);
            if (index >= 0 && index < clothesList.size()) {
                Clothes clothes = clothesList.get(index);
                customer.addToCart(clothes);
                listModel.addElement(clothes.getName() + "         " + clothes.getType()
                        + "        " + clothes.getPrice());
            } else {
                Toolkit.getDefaultToolkit().beep();
            }


            list.setSelectedIndex(index + 1);
            list.ensureIndexIsVisible(index + 1);


        }
    }

    //MODIFIES: buyer and this
    //EFFECTS: removed selected item from cart and jlist, beeps if invalid selection
    class Remove implements ActionListener {
        public void actionPerformed(ActionEvent e) {
            int index = list.getSelectedIndex();
            try {
                customer.removeItemFromCart(index - 18);
                listModel.removeElementAt(index);
            } catch (InvalidInputException invalidInputException) {
                Toolkit.getDefaultToolkit().beep();
            }
            list.setSelectedIndex(index);
            list.ensureIndexIsVisible(index);

            if (customer.getCart().size() == 0) {
                removeBtn.setEnabled(false);
            }
        }
    }

    // MODIFIES: this
    // EFFECTS: adds previous cart to jlist
    private class Load implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            saveBtn.setEnabled(true);
            removeBtn.setEnabled(true);
            clothesPrint(customer.getCart(), listModel);
            loadBtn.setEnabled(false);
        }
    }

    // MODIFIES: buyers list
    // EFFECTS: saves cart
    private class Save implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            saveBuyers();
        }
    }

    // MODIFIES: this
    // EFFECTS: creates and sets window
    public static void createAndShowGUI(Buyer buyer, JFrame frame) {
        ListSet.frame = frame;
        customer = buyer;
        newContentPane = new ListSet();
        newContentPane.setOpaque(true);
        frame.setContentPane(newContentPane);
        frame.invalidate();
        frame.validate();


        frame.pack();
        frame.setVisible(true);
    }

    //EFFECTS: conducts exiting message, shows basket and total cost
    public void exit() {
        JPanel exitPanel = new JPanel();
        exitPanel.setSize(new Dimension(600, 600));
        exitPanel.setLayout(null);
        JLabel purchaseDecision = new JLabel("Purchase later or now?");
        JButton purchaseLater = new JButton("Purchase later");
        purchaseLater.addActionListener(new PurchaseLater());
        JButton purchaseNow = new JButton("Purchase Now");
        purchaseNow.addActionListener(new PurchaseNow());
        purchaseDecision.setBounds(200, 100, 150, 25);
        purchaseLater.setBounds(50, 150, 200, 25);
        purchaseNow.setBounds(250, 150, 200, 25);
        exitPanel.add(purchaseDecision);
        exitPanel.add(purchaseLater);
        exitPanel.add(purchaseNow);
        frame.setContentPane(exitPanel);
    }

    // MODIFIES: this
    // EFFECTS: creates panel that is shown before app is closed when purchase later is selected
    //          panel automatically closed after 3 seconds of its initialization
    private class PurchaseLater implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            JPanel byeNow = new JPanel();
            frame.setContentPane(byeNow);
            byeNow.setLayout(null);
            JLabel exit = new JLabel("saving cart and exiting app");
            exit.setBounds(150, 100, 200, 25);
            byeNow.add(exit);
            saveBuyers();
            frame.invalidate();
            frame.validate();
            new Timer(3_000, (b) -> {
                frame.setVisible(false);
                frame.dispose();
            }).start();
        }
    }

    // MODIFIES: this, customer
    // EFFECTS: creates panel shown after purchase now is selected
    //          displays all items in cart and subtotal, clears the cart
    //          automatically closes panel after 10 seconds
    private class PurchaseNow implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            JPanel byeNow = new JPanel();
            DefaultListModel checkoutCart = new DefaultListModel();
            clothesPrint(customer.getCart(),checkoutCart);
            JList jlist = new JList(checkoutCart);
            frame.setContentPane(byeNow);
            byeNow.setLayout(null);
            JLabel exitMsg1 = new JLabel("Exiting and checking out for this purchase");
            JLabel exitMsg2 = new JLabel("The following has been purchased:");
            JLabel exitMsg3 = new JLabel(String.format("Total cost is %,.2f%n", customer.getSubtotal()));
            exitMsg1.setBounds(100,50,300,25);
            exitMsg2.setBounds(100,100,300,25);
            exitMsg3.setBounds(100,checkoutCart.size() * 18 + 140,300,25);
            jlist.setBounds(100,130,300,checkoutCart.size() * 18);
            byeNow.add(exitMsg1);
            byeNow.add(exitMsg2);
            byeNow.add(exitMsg3);
            byeNow.add(jlist);
            customer.getCart().clear();
            customer.setSubtotal(0.0);
            saveBuyers();
            frame.invalidate();
            frame.validate();
            new Timer(10_000, (b) -> {
                frame.setVisible(false);
                frame.dispose();
            }).start();

        }
    }
}
