package edu.ucalgary.ensf409;

import java.awt.*;
import javax.swing.*;
import java.awt.event.*;
import java.util.concurrent.TimeUnit;


public class HamperGUI extends JFrame implements ActionListener, MouseListener{

    // Important Global Variable States
    private static int NumberofHampers;
    private static String[][] Request;
    private static Order createdOrder;
    // Panels and Fields
    private JTextField HamperInput;
    private JPanel masterContainer;
    private CardLayout masterCardLayout = new CardLayout(0,0);
    // Class wide Counters
    private static int hamperCounter = 0;

    public HamperGUI(){
        super("Hamper GUI");
        setupTestInventory();
        // Inventory.downloadDatabase();
        setupHamper(); //Calls a method to allow for GUI to work
        setSize(600,200); //sizing of the GUI
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); //Default Close 
    }

    public void setupHamper(){
        masterContainer = new JPanel(masterCardLayout);
        this.add(masterContainer);
        createHamperAsker();
    }

    public void actionPerformed(ActionEvent event){
        if (event.getActionCommand().equals("Submit")) {
            handleHamperNumber();
        }
        else if (event.getActionCommand().equals("Create")) {
            handleClientNumber();
        }
        else if (event.getActionCommand().equals("Print")) {
            try {
                OrderForm.printFormToFile(createdOrder, "C:/Users/hitma/OneDrive/Documents/Programming Work/ENSF 409/ENSF409-FinalProject/HamperReciept.txt");
            } catch (Exception e) { e.printStackTrace();}
        }
        else if (event.getActionCommand().equals("New Order")) {
            NumberofHampers = 0;
            Request = null;
            createdOrder = null;
            hamperCounter = 0;
            masterCardLayout.next(masterContainer);
            setSize(600,200);
            for (Component panel : masterContainer.getComponents()) {
                if (panel.getName() == "HamperAsker") {
                    continue;
                }
                masterContainer.remove(panel);
            }
        }
    }

    public void handleHamperNumber() {
        //Gets the input 
        NumberofHampers = Integer.parseInt(HamperInput.getText());

        //If the Input is valid goes to Client GUI and displays the number of Hampers reqeusted
        if(validHamperInput()){
            JOptionPane.showMessageDialog(this, "Number of Hampers: " + NumberofHampers);
            Request = new String[NumberofHampers][];
            for(int i = 1; i <= NumberofHampers; i++){
                createClientAsker(i);
            }
            masterCardLayout.next(masterContainer);
        }        
    }

    public void handleClientNumber() {
        JPanel currPanel = null;
        Component[] panels = masterContainer.getComponents();
        for (Component panel : panels) {
            if (panel.isVisible()) {
                currPanel = (JPanel)panel;
                break;
            }
        }
        
        panels = currPanel.getComponents();
        int i = 0;
        String[] values = {"","","","",""};
        for (Component panel : panels) {
            if (panel.getName().equals("Client Input")) {
                JPanel textFields = (JPanel)panel;
                for (Component textField : textFields.getComponents()) {
                    if (textField instanceof JTextField) {
                        JTextField input = (JTextField)textField;
                        values[i] = input.getText();
                        i++;
                    }
                }
            }
        }
        JOptionPane.showMessageDialog(this,"<html>You inputted the following values:<br/>" 
                                        + values[1] + " Adult Males,<br/>" 
                                        + values[2] + " Adult Females,<br/>"
                                        + values[3] + " Children Over 8,<br/>"
                                        + values[4] + " Children Under 8<html/>");

        if ( !validClientInput(values) ) {
            JOptionPane.showMessageDialog(this,"Inputted value is invalid, please try again");
            return;
        }

        Request[hamperCounter] = values;
        hamperCounter++;
        System.out.println(hamperCounter);
        if (hamperCounter == Request.length) {
            System.out.println("Printing Reciept, Hampers="+hamperCounter);
            createRecieptDisplay();
        }
        else {
            masterCardLayout.next(masterContainer);
        }
    }

    //Checks for Valid input
    private boolean validHamperInput(){
        boolean allValidInput = true;
        
        if(NumberofHampers <= 0 || NumberofHampers > 10){
            allValidInput = false;
            JOptionPane.showMessageDialog(this,NumberofHampers + "is an invalid input");
        }
        return allValidInput;
    }

    private boolean validClientInput(String[] values) {
        boolean allValidInput = true;
        for (String num : values) {
            if (Integer.parseInt(num) < 0){
                allValidInput = false;
                return allValidInput;
            };
        }
        if (Integer.parseInt(values[0]) != Integer.parseInt(values[1]) + Integer.parseInt(values[2])
            + Integer.parseInt(values[3]) + Integer.parseInt(values[4])) {
                allValidInput = false;
        }
        return allValidInput;
    }

    public void createHamperAsker() {
        //Instructions for GUI
        JLabel instructions = new JLabel("Enter desired amount of Hampers");
        JPanel instructionPanel = new JPanel();
        instructionPanel.setLayout(new FlowLayout());
        instructionPanel.add(instructions);

        //User Input for Number of Hampers
        JLabel HamperLabel = new JLabel("Number of Hampers: ");
        HamperInput = new JTextField(2);
        HamperInput.addMouseListener(this);
        JPanel HamperPanel = new JPanel();
        HamperPanel.setLayout(new FlowLayout());
        HamperPanel.add(HamperLabel);
        HamperPanel.add(HamperInput);
        HamperPanel.setName("Hamper Input");

        // Submit Button
        JButton submitInfo = new JButton("Submit");
        submitInfo.addActionListener(this);
        JPanel submitPanel = new JPanel();
        submitPanel.setLayout(new FlowLayout());
        submitPanel.add(submitInfo);

        // Changes color of the GUI
        instructionPanel.setBackground(new Color(189, 245, 255));
        HamperPanel.setBackground(new Color(189, 245, 255));
        submitPanel.setBackground(new Color(189, 245, 255));

        // Create HamperAsker Container
        JPanel HamperAsker = new JPanel();
        //Layout of the Panels 
        HamperAsker.setLayout(new BoxLayout(HamperAsker,BoxLayout.Y_AXIS));
        HamperAsker.add(instructionPanel, BorderLayout.NORTH);
        HamperAsker.add(HamperPanel, BorderLayout.CENTER);
        HamperAsker.add(submitPanel, BorderLayout.PAGE_END);
        HamperAsker.setName("HamperAsker");
        masterContainer.add(HamperAsker);        
    }

    public void createClientAsker(int i) {
        JLabel instructions = new JLabel("<html><h>HAMPER " + i + "<h><br/>"
        + "Please enter the number of clients i.e 4. and Please enter the Type of Client 1,2,3 or 4<html>");
        //Number of clients 
        JLabel NOCLabel = new JLabel("Number of Clients");
       
        //Type of Client
        JLabel TOCLabel1 = new JLabel("Client 1's: ");
        JLabel TOCLabel2 = new JLabel("Client 2's: ");
        JLabel TOCLabel3 = new JLabel("Client 3's: ");
        JLabel TOCLabel4 = new JLabel("Client 4's: ");
        

        //Input for the Type of Clients 
        JTextField TOCInput1 = new JTextField(1);
        JTextField TOCInput2 = new JTextField(1);
        JTextField TOCInput3 = new JTextField(1);
        JTextField TOCInput4 = new JTextField(1);
        

        //Input for the Number of Clients
        JTextField NOCInput = new JTextField(3);
        

        //Mouse inputs
        NOCInput.addMouseListener(this);
        TOCInput1.addMouseListener(this);
        TOCInput2.addMouseListener(this);
        TOCInput3.addMouseListener(this);
        TOCInput3.addMouseListener(this);
        TOCInput4.addMouseListener(this);
       

        
        //Submit button 
        JButton submitInfo = new JButton("Create");
        submitInfo.addActionListener(this);

        //Header Panels 
        JPanel headerPanel = new JPanel();
        headerPanel.setLayout(new FlowLayout());

        //Client Panel 
        JPanel clientPanel = new JPanel();
        clientPanel.setLayout(new FlowLayout());

        //Submit Panel 
        JPanel submitPanel = new JPanel();
        submitPanel.setLayout(new FlowLayout());

        //Adding the panels to the GUI 
        headerPanel.add(instructions);

        clientPanel.add(NOCLabel);
        clientPanel.add(NOCInput);
        clientPanel.add(TOCLabel1);
        clientPanel.add(TOCInput1);
        clientPanel.add(TOCLabel2);
        clientPanel.add(TOCInput2);
        clientPanel.add(TOCLabel3);
        clientPanel.add(TOCInput3);
        clientPanel.add(TOCLabel4);
        clientPanel.add(TOCInput4);

        // Set Panel Names
        headerPanel.setName("headerPanel");
        clientPanel.setName("Client Input");
        submitPanel.setName("submitPanel");
        

        submitPanel.add(submitInfo);
        //frame.getContentPane().add(BorderLayout.NORTH, buttonsPanel);

        // ClientAsker Container
        JPanel ClientAsker = new JPanel();
        //Layout of the Panels 
        ClientAsker.setLayout(new BoxLayout(ClientAsker,BoxLayout.Y_AXIS));
        ClientAsker.add(headerPanel, BorderLayout.NORTH);
        ClientAsker.add(clientPanel, BorderLayout.CENTER);
        ClientAsker.add(submitPanel, BorderLayout.PAGE_END);

        ClientAsker.setName("Hamper" + i);
        masterContainer.add(ClientAsker);      
    }

    public void createRecieptDisplay() {
        String reciept = "";
        try {
            //TODO
            System.out.println("Sending Order Request");

            long start = System.nanoTime();
            createdOrder = new Order( prepareRequest(Request) );
            long end = System.nanoTime();
            System.out.printf("That took: %d ms.%n", TimeUnit.NANOSECONDS.toMillis(end - start));

            System.out.println("Getting OrderForm");
            reciept = OrderForm.getOrderForm(createdOrder);
        } catch (InsufficientFoodException e) {
            reciept = "InsufficientFoodException";
        }

        JButton printButton = new JButton("Print");
        printButton.addActionListener(this);
        printButton.setAlignmentX(Component.CENTER_ALIGNMENT);
        JButton newOrderButton = new JButton("New Order");
        newOrderButton.addActionListener(this);
        newOrderButton.setAlignmentX(Component.CENTER_ALIGNMENT);

        JLabel recieptText = new JLabel("<html><pre>" + reciept + "<pre/><html/>");
        JPanel recieptPanel= new JPanel();
        recieptPanel.setLayout(new BoxLayout(recieptPanel,BoxLayout.Y_AXIS));

        recieptPanel.add(recieptText);
        recieptPanel.add(printButton);
        recieptPanel.add(newOrderButton);

        JScrollPane recieptScroller = new JScrollPane(recieptPanel);
        masterContainer.add(recieptScroller);
        masterCardLayout.next(masterContainer);
        setSize(600,500);
    }

    public String[][] prepareRequest(String[][] request) {
        String[][] outputRequest = new String[request.length][];
        for (int i =0; i < request.length; i++) {
            int total = Integer.parseInt(request[i][0]);
            int type1 = Integer.parseInt(request[i][1]);
            int type2 = Integer.parseInt(request[i][2]);
            int type3 = Integer.parseInt(request[i][3]);
            int type4 = Integer.parseInt(request[i][4]);
            String[] tmp = new String[total];
            int counter = 0;
            int j = 0;
            for (j=0; j < type1; j++) {
                tmp[counter] = "1";
                counter++;
            }
            for (j=0; j < type2; j++) {
                tmp[counter] = "2";
                counter++;
            }
            for (j=0; j < type3; j++) {
                tmp[counter] = "3";
                counter++;
            }
            for (j=0; j < type4; j++) {
                tmp[counter] = "4";
                counter++;
            }
            outputRequest[i] = tmp;
        }
        return outputRequest;
    }

    public void setupTestInventory() {
        // Setup Inventory for the test
        FoodItem[] foods = new FoodItem[] {
            new FoodItem("1","Apple, dozen", new int[]{0,700,0,0,700}),     // ('Apple, dozen', 0, 100, 0, 0, 700),
            new FoodItem("2","Ham", new int[]{0,0,250,0,250}),              // ('Ham', 0, 0, 100, 0, 250),     
            new FoodItem("3","Spam", new int[]{400,0,400,0,800}),           // ('Spam', 50, 0, 50, 0, 800),
            new FoodItem("4","Eggs, dozen", new int[]{0,0,4,786,864}),      // ('Eggs, dozen', 0, 0, 9, 91, 864),
            new FoodItem("5","Banana, bunch 5", new int[]{0,582,23,0,605}), // ('Banana, bunch 5', 0, 97, 3, 0, 605), 
            new FoodItem("6","Mineral Water", new int[]{0,0,0,750,750}),    // ('Mineral Water', 0, 0, 0, 100, 750),
            new FoodItem("7","Tuna", new int[]{0,0,100,0,100}),              // ('Tuna', 0, 0, 100, 0, 100),
            new FoodItem("8","Mixed nuts, 1 kg", new int[]{0, 0, 23, 77, 6000}),
            new FoodItem("9","Lentils, 1 kg", new int[]{63, 0, 25, 12, 3520}),
            new FoodItem("10","Greek yogurt, plain, 1 kg", new int[]{0, 0, 9, 91, 970}),
            new FoodItem("11","Tofu, 1 kg", new int[]{0, 0, 19, 81, 2700}),
            new FoodItem("12","Mayonnaise, 1 kg", new int[]{0, 0, 0, 100, 6800}),
            new FoodItem("13","Kidney beans, dozen cans", new int[]{25, 0, 9, 66, 6840}),
            new FoodItem("14","Bacon, 1 kg", new int[]{0, 0, 14, 86, 3930}),
            new FoodItem("15","Quinoa, 1 kg", new int[]{70, 0, 24, 6, 925}),
            new FoodItem("16","Chocolate chip cookies, 500 g", new int[]{71, 0, 1, 29, 2440}),
            new FoodItem("17","Boba green tea, 4 cans", new int[]{88, 0, 0, 12, 924}),
            new FoodItem("18","Kidney beans, 1 pound", new int[]{72, 0, 28, 0, 385}),
            new FoodItem("19","Beets, 1 pound", new int[]{0, 87, 13, 0, 193}),
            new FoodItem("20","Chickpeas, 1 kg", new int[]{67, 0, 22, 11, 1640}),
            new FoodItem("21","Hummus", new int[]{24, 0, 11, 65, 450}),
            new FoodItem("22","Beets, 1 pound", new int[]{0, 87, 13, 0, 193}),
            new FoodItem("23","Carrots", new int[]{0, 100, 0, 0, 264}),
            new FoodItem("24","Wheaties, family size", new int[]{92, 0, 8, 0, 8000}),
            new FoodItem("25","Whole wheat flour, 10 kg", new int[]{72, 0, 12, 16, 3660}),
            new FoodItem("26","Eggs, 1 kg", new int[]{2, 0, 36, 62, 1430}),
            new FoodItem("27","Avocado, 1 pound", new int[]{19, 76, 5, 0, 725}),
            new FoodItem("28","Black olives, large tin", new int[]{23, 0, 0, 77, 2200}),
        };
        for (FoodItem food : foods) {
            Inventory.addFoodItem(food);
        }
        // Setup Client Needs for the test
        Inventory.setClientNeeds( 
            new NutritionContent[] {
                new NutritionContent( 400,700,650,750,2500 ),   // Adult Male
                new NutritionContent( 320,560,520,600,2000 ),   // Adult Female
                new NutritionContent( 462,726,682,330,2200 ),   // Child Over 8
                new NutritionContent( 294,462,434,210,1400 )    // Child Under 8
            }
        );
    }

//Mouse inputs 
public void mouseClicked(MouseEvent event){}
public void mouseEntered(MouseEvent event){}
public void mouseExited(MouseEvent event){}
public void mousePressed(MouseEvent event){}
public void mouseReleased(MouseEvent event){}

public static void main(String[] args){
    EventQueue.invokeLater(() -> {
        new HamperGUI().setVisible(true);
    });
}

}