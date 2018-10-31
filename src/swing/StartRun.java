package swing;

import java.lang.reflect.InvocationTargetException;
import javax.swing.SwingUtilities;

public class StartRun {
    public static LoginFrame frame;
    
    public static void main(String[] args) {
        try {
            SwingUtilities.invokeAndWait(new Runnable() {
                public void run() {
                    try {
                        frame = new LoginFrame();
                        frame.setVisible(true);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            });
        } catch (InvocationTargetException | InterruptedException e) {
            e.printStackTrace();
        }
    }
}
