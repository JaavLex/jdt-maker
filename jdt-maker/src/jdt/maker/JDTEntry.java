package jdt.maker;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.TimeZone;

public class JDTEntry {
    private final String start_time;
    private final String end_time;
    private final String body;

    JDTEntry(String start_time, String end_time, String body) {
        System.out.println(start_time);
        System.out.println(end_time);
        System.out.println(body);
        this.start_time = start_time;
        this.end_time = end_time;
        this.body = body;
        // TODO: check value and return error if needed
    }

    // Redefine the toString() method for the JDTEntry object
    @Override
    public String toString() {
        return getClass().getSimpleName() + "[start_time=" + this.start_time + ", end_time=" + this.end_time + ", body=" + this.body + "]";
    }

    public String get_start_time() {
        return this.start_time;
    }

    public String get_end_time() {
        return this.end_time;
    }

    public String get_body() {
        return this.body;
    }

    public String get_md_entry() {
        return System.lineSeparator() + System.lineSeparator() + "## " + this.start_time + " - " + this.end_time + System.lineSeparator() + System.lineSeparator() + this.body;
    }

    // https://stackoverflow.com/a/3914498
    public String get_today_iso_date() {
        TimeZone tz = TimeZone.getTimeZone("UTC+1");
        DateFormat df = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm'Z'"); // Quoted "Z" to indicate UTC, no timezone offset
        df.setTimeZone(tz);
        return df.format(new Date());
    }

}