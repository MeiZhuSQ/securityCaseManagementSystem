package swing;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class NoteAskedAdultListener implements ActionListener {
    public String selectedAskedAudlt; 
    @Override
    public void actionPerformed(ActionEvent e) {
        selectedAskedAudlt = e.getActionCommand();
    }

}
