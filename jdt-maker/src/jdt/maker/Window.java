package jdt.maker;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.event.*;
import java.text.*;
import java.awt.event.KeyEvent.*;
import java.io.*;
import java.util.*;
import java.util.ArrayList;

import org.json.simple.*;
import org.json.simple.parser.JSONParser;

import java.time.Instant.*;

public class Window extends JFrame {

    private final JPanel container = new JPanel();
    private final JPanel hourPane = new JPanel();
    private final JPanel fieldPane = new JPanel();
    private final JPanel buttonPane = new JPanel();
    private final JMenuBar menuBar = new JMenuBar();
    private final JMenu fileMenu = new JMenu("File");
    private final JMenuItem exitMenu = new JMenuItem("Exit");
    private final JMenu helpMenu = new JMenu("Help");
    private final JMenuItem aboutMenu = new JMenuItem("About");
    private final JLabel dateLabel = new JLabel("Date");
    private final JLabel startLabel = new JLabel("Start Time");
    private final JLabel endLabel = new JLabel("Ending Time");
    private final JTextField fieldDate = new JTextField();
    private final JComboBox startCombo = new JComboBox();
    private final JComboBox endCombo = new JComboBox();
    private final JTextArea fieldAction = new HintTextField("Type what you did here...");
    private final JButton buttonPrev = new JButton("Prev");
    private final JButton buttonNext = new JButton("Next");
    private final JButton buttonSave = new JButton("Save");
    private final JButton buttonFinish = new JButton("Finish");
    private final JOptionPane exitMessage = new JOptionPane();
    private final String[][] actionListLocal = new String[50][3];
    public int itemNumber = 0;
    public JSONObject jsonTitle = new JSONObject();
    public ArrayList<JDTEntry> JDTEntries = new ArrayList<JDTEntry>();

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

    // https://stackoverflow.com/a/3914498
    public String get_today_iso_date() {
        TimeZone tz = TimeZone.getTimeZone("UTC+1");
        DateFormat df = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm'Z'"); // Quoted "Z" to indicate UTC, no timezone offset
        df.setTimeZone(tz);
        return df.format(new Date());
    }

    // Previous Button
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

    // Next Button
    class BNListener implements ActionListener {
        public void actionPerformed(ActionEvent arg0) {
            actionListLocal[itemNumber][0] = startCombo.getSelectedItem().toString();
            actionListLocal[itemNumber][1] = endCombo.getSelectedItem().toString();
            actionListLocal[itemNumber][2] = fieldAction.getText();

            if (!startCombo.getSelectedItem().toString().equals("") && !endCombo.getSelectedItem().toString().equals("") && !fieldAction.getText().equals("")) {
                JDTEntry my_entry = new JDTEntry(startCombo.getSelectedItem().toString(), endCombo.getSelectedItem().toString(), fieldAction.getText());
                JDTEntries.add(my_entry);
            } else {
                System.out.println("Next button used without all value");
            }

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

    // Save Button
    class BSListener implements ActionListener {
        public void actionPerformed(ActionEvent arg0) {
            System.out.println(JDTEntries);
            actionListLocal[itemNumber][0] = startCombo.getSelectedItem().toString();
            actionListLocal[itemNumber][1] = endCombo.getSelectedItem().toString();
            actionListLocal[itemNumber][2] = fieldAction.getText();
        }
    }
    
    // Finish Button
    class BFListener implements ActionListener {

        private String md_output;

        public void actionPerformed(ActionEvent arg0) {

            md_output = "# Journal de travail " + fieldDate.getText();
            for (JDTEntry entry : JDTEntries) {
                md_output += entry.get_md_entry();
            }
            md_output += System.lineSeparator() + System.lineSeparator() + "<!-- Generated on " + get_today_iso_date() + " with JDT Maker -->";
            System.out.println(md_output);

            try (FileWriter file = new FileWriter("JDT" + fieldDate.getText() + ".md")) {
                file.write(md_output);
                file.flush();
            } catch (IOException e) {
                e.printStackTrace();
            }

            JOptionPane.showMessageDialog(null, "JDT" + fieldDate.getText() + ".md has been created in the project directory. The application will now shut down.", "MarkDown creation", JOptionPane.INFORMATION_MESSAGE);
            System.exit(0);
        }
    }
}
