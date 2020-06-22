package jdt.maker;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.event.*;
import java.lang.reflect.Array;
import java.text.*;
import java.awt.event.KeyEvent.*;
import java.io.*;
import java.util.*;
import java.util.ArrayList;

import org.json.simple.*;
import org.json.simple.parser.JSONParser;

import java.time.Instant.*;

public class JDTEntries extends JDTEntry{

private String md_output;
    
    public JDTEntries(String date, ArrayList JDTEntries) {

            md_output = "# Journal de travail " + date;
            for (JDTEntry entry : JDTEntries) {
                md_output += entry.get_md_entry();
            }
            md_output += System.lineSeparator() + System.lineSeparator() + "<!-- Generated on " + get_today_iso_date() + " with JDT Maker -->";
            System.out.println(md_output);

            try (FileWriter file = new FileWriter("JDT" + date + ".md")) {
                file.write(md_output);
                file.flush();
            } catch (IOException e) {
                e.printStackTrace();
            }
    }
}
