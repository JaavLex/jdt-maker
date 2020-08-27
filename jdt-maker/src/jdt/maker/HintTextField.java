// Code from http://javaswingcomponents.blogspot.com/2012/05/how-to-create-simple-hinttextfield-in.html
package jdt.maker;

import java.awt.Color;
import java.awt.Font;
import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;
import javax.swing.JTextArea;

public class HintTextField extends JTextArea {

    Font gainFont = new Font("Tahoma", Font.PLAIN, 14);
    Font lostFont = new Font("Tahoma", Font.ITALIC, 14);

    public HintTextField(final String hint) {

        setText(hint);
        setFont(lostFont);
        setForeground(Color.BLACK);

        this.addFocusListener(new FocusAdapter() {

            @Override
            public void focusGained(FocusEvent e) {
                if (getText().equals(hint)) {
                    setText("");
                    setFont(gainFont);
                } else {
                    setText(getText());
                    setFont(gainFont);
                }
            }

            @Override
            public void focusLost(FocusEvent e) {
                if (getText().equals(hint) || getText().length() == 0) {
                    setText(hint);
                    setFont(lostFont);
                    setForeground(Color.GRAY);
                } else {
                    setText(getText());
                    setFont(gainFont);
                    setForeground(Color.BLACK);
                }
            }
        });

    }
}
