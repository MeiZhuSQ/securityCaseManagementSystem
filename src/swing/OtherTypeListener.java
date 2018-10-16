package swing;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class OtherTypeListener implements ActionListener {
    public static String selectedOtherType;
    @Override
    public void actionPerformed(ActionEvent e) {
        selectedOtherType = e.getActionCommand();
    }

}
