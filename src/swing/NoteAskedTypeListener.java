package swing;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class NoteAskedTypeListener implements ActionListener {
    public static String selectedAskedType;
    @Override
    public void actionPerformed(ActionEvent e) {
        selectedAskedType = e.getActionCommand();
    }

}
