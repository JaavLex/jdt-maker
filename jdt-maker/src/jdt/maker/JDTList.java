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

public class JDTList {

private String md_output;
public ArrayList<JDTEntry> ListEntries = new ArrayList<JDTEntry>();

    public void print() {
        System.out.print(ListEntries);
    }

    public void add(JDTEntry entrys) {
        ListEntries.add(entrys);
        sort_jdtentries();
    }

    public JDTEntry get(int cursor) {
        return ListEntries.get(cursor);
    }
    
    public ArrayList get_list() {
        return ListEntries;
    }
    
    public void to_md(String date) {

        md_output = "# Journal de travail " + date;
        for (JDTEntry entry : ListEntries) {
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

    // Well, using Java's LocalTime would probably be better...
    // Sort ArrayList<JDTEntries> on JDTEntry.ste_start_time()
    public ArrayList sort_jdtentries() {
        Collections.sort(this.ListEntries, new Comparator<JDTEntry>() {
            public int compare(JDTEntry s1, JDTEntry s2) {
                return s1.get_start_time().split(":")[0].compareToIgnoreCase(s2.get_start_time().split(":")[0]);
            }
        });
        return this.ListEntries;
    }
    
    // https://stackoverflow.com/a/3914498
    public String get_today_iso_date() {
        TimeZone tz = TimeZone.getTimeZone("UTC+1");
        DateFormat df = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm'Z'"); // Quoted "Z" to indicate UTC, no timezone offset
        df.setTimeZone(tz);
        return df.format(new Date());
    }
}
