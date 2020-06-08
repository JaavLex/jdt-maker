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

    var menuBar = new JMenuBar();

    var fileMenu = new JMenu("Help");
        fileMenu.setMnemonic(KeyEvent.VK_F);

    public JMenuItem eMenuItem = new JMenuItem("About");
        eMenuItem.setMnemonic(KeyEvent.VK_E);
        eMenuItem.setToolTipText("Informations about the application");
        eMenuItem.addActionListener((event) -> System.exit(0));

        fileMenu.add(eMenuItem);
        menuBar.add(fileMenu);

    setJMenuBar(menuBar);


    private JPanel container = new JPanel();
    private JPanel hourPane = new JPanel();
    private JPanel fieldPane = new JPanel();
    private JPanel buttonPane = new JPanel();

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