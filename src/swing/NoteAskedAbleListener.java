package swing;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class NoteAskedAbleListener implements ActionListener {
    public static String selectedAbled; 
    @Override
    public void actionPerformed(ActionEvent e) {
        selectedAbled = e.getActionCommand();
    }

}
