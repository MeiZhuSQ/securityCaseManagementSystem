package swing;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class AskedAbleListener implements ActionListener {
    public static String selectedAbled; 
    @Override
    public void actionPerformed(ActionEvent e) {
        selectedAbled = e.getActionCommand();
    }

}
