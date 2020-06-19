package jdt.maker;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.event.*;
import java.text.*;
import java.awt.event.KeyEvent.*;
import java.io.*;
import java.util.*;
import org.json.simple.*;
import org.json.simple.parser.JSONParser;
import java.time.Instant.*;

public class Window extends JFrame {

    private JPanel container = new JPanel();
    private JPanel hourPane = new JPanel();
    private JPanel fieldPane = new JPanel();
    private JPanel buttonPane = new JPanel();

    private JMenuBar menuBar = new JMenuBar();
    private JMenu fileMenu = new JMenu("File");
    private JMenuItem exitMenu = new JMenuItem("Exit");
    private JMenu helpMenu = new JMenu("Help");
    private JMenuItem aboutMenu = new JMenuItem("About");

    private JLabel dateLabel = new JLabel("Date");
    private JLabel startLabel = new JLabel("Start Time");
    private JLabel endLabel = new JLabel("Ending Time");

    private JTextField fieldDate = new JTextField();
    private JComboBox startCombo = new JComboBox();
    private JComboBox endCombo = new JComboBox();

    private JTextArea fieldAction = new HintTextField("Type what you did here...");

    private JButton buttonPrev = new JButton("Prev");
    private JButton buttonNext = new JButton("Next");
    private JButton buttonSave = new JButton("Save");
    private JButton buttonFinish = new JButton("Finish");

    private JOptionPane exitMessage = new JOptionPane();

    private String[][] actionListLocal = new String[50][3];

    public int itemNumber = 0;

    public JSONObject jsonTitle = new JSONObject();

    public Window() {
        this.setTitle("JDT Maker");
        this.setSize(600, 500);

        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setLocationRelativeTo(null);
        this.setResizable(true);

        this.setBackground(Color.WHITE);

        exitMenu.setToolTipText("Informations about the application");
        exitMenu.addActionListener((event) -> System.exit(0));

        fileMenu.add(exitMenu);
        helpMenu.add(aboutMenu);
        menuBar.add(fileMenu);
        menuBar.add(helpMenu);
        setJMenuBar(menuBar);

        for (int i = 0; i < 24; i++) {
            String var1 = i + ":00";
            String var2 = i + ":30";
            startCombo.addItem(var1);
            endCombo.addItem(var1);
            startCombo.addItem(var2);
            endCombo.addItem(var2);
        }

        fieldDate.setPreferredSize(new Dimension(80, 20));
        hourPane.add(dateLabel);
        hourPane.add(fieldDate);
        hourPane.add(startLabel);
        hourPane.add(startCombo);
        hourPane.add(endLabel);
        hourPane.add(endCombo);

        fieldAction.setPreferredSize(new Dimension(350, 150));

        fieldPane.add(fieldAction);

        buttonPrev.setBackground(Color.GRAY);
        buttonPrev.setFont(new Font("Verdana", Font.BOLD, 10));
        buttonPrev.setForeground(Color.WHITE);
        buttonPrev.setOpaque(true);
        buttonPrev.setBorderPainted(false);

        buttonNext.setBackground(Color.BLUE);
        buttonNext.setFont(new Font("Verdana", Font.BOLD, 10));
        buttonNext.setForeground(Color.WHITE);
        buttonNext.setOpaque(true);
        buttonNext.setBorderPainted(false);

        buttonSave.setBackground(Color.BLUE);
        buttonSave.setFont(new Font("Verdana", Font.BOLD, 10));
        buttonSave.setForeground(Color.WHITE);
        buttonSave.setOpaque(true);
        buttonSave.setBorderPainted(false);

        buttonFinish.setBackground(Color.GREEN);
        buttonFinish.setFont(new Font("Verdana", Font.BOLD, 10));
        buttonFinish.setForeground(Color.WHITE);
        buttonFinish.setOpaque(true);
        buttonFinish.setBorderPainted(false);

        buttonPrev.addActionListener(new BPListener());
        buttonNext.addActionListener(new BNListener());
        buttonSave.addActionListener(new BSListener());
        buttonFinish.addActionListener(new BFListener());
        buttonPane.add(buttonPrev);
        buttonPane.add(buttonNext);
        buttonPane.add(buttonSave);
        buttonPane.add(buttonFinish);

        container.setLayout(new BorderLayout());
        container.add(hourPane, BorderLayout.NORTH);

        container.add(fieldPane, BorderLayout.CENTER);
        container.add(buttonPane, BorderLayout.SOUTH);

        fieldDate.setText(String.valueOf(java.time.LocalDate.now()));

        this.setContentPane(container);
        this.setVisible(true);
    }

    class BPListener implements ActionListener {

        public void actionPerformed(ActionEvent arg0) {

            if (itemNumber > 0) {
                actionListLocal[itemNumber][0] = startCombo.getSelectedItem().toString();
                actionListLocal[itemNumber][1] = endCombo.getSelectedItem().toString();
                actionListLocal[itemNumber][2] = fieldAction.getText();  

                itemNumber--;

                startCombo.setSelectedItem(actionListLocal[itemNumber][0]);
                endCombo.setSelectedItem(actionListLocal[itemNumber][1]);
                fieldAction.setText(actionListLocal[itemNumber][2]);
            }
        }
    }

    class BNListener implements ActionListener {

        public void actionPerformed(ActionEvent arg0) {

            actionListLocal[itemNumber][0] = startCombo.getSelectedItem().toString();
            actionListLocal[itemNumber][1] = endCombo.getSelectedItem().toString();
            actionListLocal[itemNumber][2] = fieldAction.getText();  

            startCombo.setSelectedItem(endCombo.getSelectedItem());
            fieldAction.setText("");
            itemNumber++;   

            if (actionListLocal[itemNumber][0] != null || actionListLocal[itemNumber][1] != null || actionListLocal[itemNumber][2] != null) {
                startCombo.setSelectedItem(actionListLocal[itemNumber][0]);
                endCombo.setSelectedItem(actionListLocal[itemNumber][1]);
                fieldAction.setText(actionListLocal[itemNumber][2]);
            }
        }
    }

    class BSListener implements ActionListener {


        public void actionPerformed(ActionEvent arg0) {

            actionListLocal[itemNumber][0] = startCombo.getSelectedItem().toString();
            actionListLocal[itemNumber][1] = endCombo.getSelectedItem().toString();
            actionListLocal[itemNumber][2] = fieldAction.getText(); 
        }
    }

    class BFListener implements ActionListener {

        public void actionPerformed(ActionEvent arg0) {
            System.out.println(Arrays.deepToString(actionListLocal));


            try (FileWriter file = new FileWriter("JDT" + fieldDate.getText() + ".md")) {

                file.write(String.format("# JDT " + fieldDate.getText() + "%n%n"));
                file.write(String.format("---%n%n"));
                file.write(String.format("## Summary%n"));

                for (int i = 0; actionListLocal[i][0] != null || actionListLocal[i][1] != null || actionListLocal[i][2] != null; i++) {
                    file.write(String.format("1. " + actionListLocal[i][0] + " - " + actionListLocal[i][1] + "%n"));
                } 

                file.write(String.format("%n---%n%n"));

                for (int i = 0; actionListLocal[i][0] != null || actionListLocal[i][1] != null || actionListLocal[i][2] != null; i++) {
                    file.write(String.format("## " + actionListLocal[i][0] + " - " + actionListLocal[i][1] + "%n"));
                    file.write(String.format("**Action completed :** " + actionListLocal[i][2] + "%n%n"));
                } 
                file.flush();
            } catch (IOException e) {
                e.printStackTrace();
            }

            exitMessage.showMessageDialog(null, "JDT" + fieldDate.getText() + ".md has been created in the project directory. The application will now shut down.", "MarkDown creation", JOptionPane.INFORMATION_MESSAGE);
            System.exit(0);
        }
    }

}
