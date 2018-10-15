package swing;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class AskedAbleListener implements ActionListener {

    @Override
    public void actionPerformed(ActionEvent e) {
        String actionCommand = e.getActionCommand();
    }

}
