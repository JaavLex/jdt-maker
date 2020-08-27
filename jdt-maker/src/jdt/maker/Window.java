package jdt.maker;

// v1.0.0-beta
import java.util.Properties;
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;      
import javax.swing.event.*;
import java.lang.reflect.Array;
import java.text.*;
import java.awt.event.KeyEvent.*;
import java.io.*;
import static java.lang.Double.parseDouble;
import static java.lang.Integer.parseInt;
import java.util.*;
import java.util.ArrayList;

import org.json.simple.*;
import org.json.simple.parser.JSONParser;

import java.time.Instant.*;
import java.time.LocalDate;
import java.time.Period;
import java.time.temporal.ChronoUnit;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Window extends JFrame {

    public String version = "1.0.0-beta";
    private final JPanel container = new JPanel();
    private final JPanel hourPane = new JPanel();
    private final JPanel fieldPane = new JPanel();
    private final JPanel buttonPane = new JPanel();
    private final JMenuBar menuBar = new JMenuBar();
    private final JMenu fileMenu = new JMenu("File");
    private final JMenuItem exitMenu = new JMenuItem("Exit");
    private final JMenuItem saveMenu = new JMenuItem("Save");
    private final JMenu helpMenu = new JMenu("Help");
    private final JMenuItem aboutMenu = new JMenuItem("About");
    private final JLabel dateLabel = new JLabel("Date");
    private final JLabel startLabel = new JLabel("Start Time");
    private final JLabel endLabel = new JLabel("Ending Time");
    private final JLabel mailLabel = new JLabel("Recipient");
    private final JTextField fieldDate = new JTextField();
    private final JTextField fieldMail = new JTextField();
    private final JComboBox startCombo = new JComboBox();
    private final JComboBox endCombo = new JComboBox();
    private final JTextArea fieldAction = new HintTextField("Type what you did here...");
    private final JButton buttonPrev = new JButton("Prev");
    private final JButton buttonNext = new JButton("Next");
    private final JButton buttonFinish = new JButton("Finish");
    private final JOptionPane exitMessage = new JOptionPane();
    private final String[][] actionListLocal = new String[50][3];
    public int itemNumber = 0;
    public int cursor = 0; // Used to iterate across JDTEntries items
    public JSONObject jsonTitle = new JSONObject();
    public JDTList entries = new JDTList();
    public int timeDiff = 120;
    
    public Window() {
        this.setTitle("JDT Maker");
        this.setSize(800, 500);
        this.setMinimumSize(new Dimension(800, 500));

        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setLocationRelativeTo(null);
        this.setResizable(true);

        this.setBackground(Color.WHITE);        

        exitMenu.setToolTipText("Informations about the application");
        exitMenu.addActionListener((event) -> System.exit(0));
        
        saveMenu.setToolTipText("Save current actions");
        saveMenu.addActionListener(new saveListener());

        aboutMenu.addActionListener((event) -> JOptionPane.showMessageDialog(null, "APP PURPOSE : Lorem ipsum \n APP VERSION : " + version, "About", JOptionPane.INFORMATION_MESSAGE));

        fileMenu.add(exitMenu);
        fileMenu.add(saveMenu);
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
        fieldMail.setPreferredSize(new Dimension(200, 20));
        dateLabel.setFont(new Font("Verdana", Font.BOLD, 14));
        fieldDate.setFont(new Font("Verdana", Font.PLAIN, 14));
        startLabel.setFont(new Font("Verdana", Font.BOLD, 14));
        startCombo.setFont(new Font("Verdana", Font.PLAIN, 14));
        endLabel.setFont(new Font("Verdana", Font.BOLD, 14));
        endCombo.setFont(new Font("Verdana", Font.PLAIN, 14));
        mailLabel.setFont(new Font("Verdana", Font.BOLD, 14));
        fieldMail.setFont(new Font("Verdana", Font.PLAIN, 10));
        hourPane.add(dateLabel);
        hourPane.add(fieldDate);
        hourPane.add(startLabel);
        hourPane.add(startCombo);
        hourPane.add(endLabel);
        hourPane.add(endCombo);
        hourPane.add(mailLabel);
        hourPane.add(fieldMail);
        
        startCombo.addActionListener(new startListener());
        endCombo.addActionListener(new endListener());
        
        startCombo.setSelectedItem("8:00");
        endCombo.setSelectedItem("10:00");

        fieldAction.setPreferredSize(new Dimension(700, 350));
        fieldAction.setFont(new Font("Verdana", Font.BOLD, 14));
        fieldAction.setLineWrap(true);
        fieldAction.setWrapStyleWord(true);

        fieldPane.add(fieldAction);

        buttonPrev.setBackground(new java.awt.Color(1,149,203));
        buttonPrev.setFont(new Font("Verdana", Font.BOLD, 14));
        buttonPrev.setForeground(Color.WHITE);
        buttonPrev.setOpaque(true);
        buttonPrev.setBorderPainted(false);
        buttonPrev.setPreferredSize(new Dimension(80, 30));

        buttonNext.setBackground(new java.awt.Color(0,188,255));
        buttonNext.setFont(new Font("Verdana", Font.BOLD, 14));
        buttonNext.setForeground(Color.WHITE);
        buttonNext.setOpaque(true);
        buttonNext.setBorderPainted(false);
        buttonNext.setPreferredSize(new Dimension(80, 30));

        buttonFinish.setBackground(new java.awt.Color(29,189,61));
        buttonFinish.setFont(new Font("Verdana", Font.BOLD, 14));
        buttonFinish.setForeground(Color.WHITE);
        buttonFinish.setOpaque(true);
        buttonFinish.setBorderPainted(false);
        buttonFinish.setPreferredSize(new Dimension(80, 30));
        
        buttonPrev.addActionListener(new BPListener());
        buttonNext.addActionListener(new BNListener());
        buttonFinish.addActionListener(new BFListener());
        buttonPane.add(buttonPrev);
        buttonPane.add(buttonNext);
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
    
    class startListener implements ActionListener {

        public void actionPerformed(ActionEvent arg0) {       
            String foo = (String) startCombo.getSelectedItem();
            String[] fooArr = foo.split(":");
            double newTime = (parseDouble(fooArr[0])*60 + parseDouble(fooArr[1]) + timeDiff)/60;
            String strnewTime = Double.toString(newTime);
            String[] finalArr = strnewTime.split("\\.");

            if (parseInt(finalArr[1]) > 0) {
                endCombo.setSelectedItem(finalArr[0] + ":30");
            } else {
                endCombo.setSelectedItem(finalArr[0] + ":00");
            }       
        }
    }
    
    class endListener implements ActionListener {

        public void actionPerformed(ActionEvent arg0) {    
            String foo = (String) startCombo.getSelectedItem();
            String foo2 = (String) endCombo.getSelectedItem();
            String[] fooArr = foo.split(":");
            String[] fooArr2 = foo2.split(":");
            int startTime = parseInt(fooArr[0])*60 + parseInt(fooArr[1]);
            int endTime = parseInt(fooArr2[0])*60 + parseInt(fooArr2[1]);
            
            timeDiff = endTime - startTime;
        }
    }

    // Previous Button
    class BPListener implements ActionListener {

        public void actionPerformed(ActionEvent arg0) {

            cursor--;

            System.out.println(entries.get_list().size());
            System.out.println(cursor);

            // Get the JDTEntry at the cursor position
            JDTEntry my_entry = entries.get(cursor);

            // Set the values
            startCombo.setSelectedItem(my_entry.get_start_time());
            endCombo.setSelectedItem(my_entry.get_end_time());
            fieldAction.setText(my_entry.get_body());

        }
    }

    // Next Button
    class BNListener implements ActionListener {

        public void actionPerformed(ActionEvent arg0) {
            actionListLocal[itemNumber][0] = startCombo.getSelectedItem().toString();
            actionListLocal[itemNumber][1] = endCombo.getSelectedItem().toString();
            actionListLocal[itemNumber][2] = fieldAction.getText();

            // Only do something if times and body are set
            if (!startCombo.getSelectedItem().toString().equals("") && !endCombo.getSelectedItem().toString().equals("") && !fieldAction.getText().equals("")) {
                // Create a new JDTEntry
                JDTEntry my_entry = new JDTEntry(startCombo.getSelectedItem().toString(), endCombo.getSelectedItem().toString(), fieldAction.getText());
                // Save the new JDTEntry in JDTEntries ArrayList
                entries.add(my_entry);
                // Sort the JDTEntries based on start_time

                // Increment the cursor position
                cursor++;
                // Set the start_time with the previous end_time
                startCombo.setSelectedItem(endCombo.getSelectedItem());
                // Clear the body
                fieldAction.setText("");
            } else {
                System.out.println("Next button used without all value");
            }

        }
    }

    // Save menu [STILL DOESN'T WORK]
    class saveListener implements ActionListener {

        public void actionPerformed(ActionEvent arg0) {
            entries.sort_jdtentries();
            entries.print();
        }
    }

    // Finish Button
    class BFListener implements ActionListener {
        
        public void actionPerformed(ActionEvent arg0) {

            entries.sort_jdtentries();

            entries.to_md(fieldDate.getText());

            //String homeDir = System.getenv("HOME");
            String[] cmd = { "./mdtopdfmail.sh", "-f=JDT"+fieldDate.getText(), "-m="+fieldMail.getText() };
            try {
                Process p = Runtime.getRuntime().exec(cmd);
            } catch (IOException ex) {
                Logger.getLogger(Window.class.getName()).log(Level.SEVERE, null, ex);
            }

            System.out.print("./mdtopdfmail.sh -f=JDT"+fieldDate.getText()+" -m="+fieldMail.getText());
            
            JOptionPane.showMessageDialog(null, "JDT" + fieldDate.getText() + ".md has been created in the project directory. The application will now shut down.", "MarkDown creation", JOptionPane.INFORMATION_MESSAGE);
            System.exit(0);
        }
    }
}
