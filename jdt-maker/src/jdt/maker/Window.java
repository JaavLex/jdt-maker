package jdt.maker;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JButton;
import javax.swing.JRadioButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.JComboBox;
import java.awt.* ;
import javax.swing.* ;
import javax.swing.event.* ;
import java.text.DecimalFormat ;
import java.awt.EventQueue;
import java.awt.event.KeyEvent.*;
import javax.swing.ImageIcon;
import javax.swing.JFrame;



public class Window extends JFrame{
    private JPanel container = new JPanel();
    private JPanel hourPane = new JPanel();
    private JPanel fieldPane = new JPanel();
    private JPanel buttonPane = new JPanel();

    private JMenuBar menuBar = new JMenuBar();
    private JMenu fileMenu = new JMenu("File");
    private JMenuItem exitMenu = new JMenuItem("Exit");
    private JMenu helpMenu = new JMenu("Help");
    private JMenuItem aboutMenu = new JMenuItem("About");

    private JTextField fieldAction = new JTextField("");

    private JButton buttonPrev = new JButton("Prev");
    private JButton buttonNext = new JButton("Next");
    private JButton buttonFinish = new JButton("Finish");

    public Window(){
        this.setTitle("JDT Maker");
        this.setSize(400, 500);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setLocationRelativeTo(null);
        this.setResizable(false);

        exitMenu.setToolTipText("Informations about the application");
        exitMenu.addActionListener((event) -> System.exit(0));

        fileMenu.add(exitMenu);
        helpMenu.add(aboutMenu);
        menuBar.add(fileMenu);
        menuBar.add(helpMenu);
        setJMenuBar(menuBar);

        fieldAction.setPreferredSize(new Dimension(350, 150));

        fieldPane.add(fieldAction);

        buttonPane.add(buttonPrev);
        buttonPane.add(buttonNext);
        buttonPane.add(buttonFinish);

        container.add(fieldPane, BorderLayout.CENTER);
        container.add(buttonPane, BorderLayout.SOUTH);
        
        this.setContentPane(container);
        this.setVisible(true);
    }

}