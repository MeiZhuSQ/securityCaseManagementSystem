package swing;

import util.Loading;

public class StartRun {
    public static LoginFrame frame;

    public static void main(String[] args) {
        Loading loading = Loading.getLoading();
        loading.open();
        try {
            frame = new LoginFrame();
            frame.setVisible(true);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            loading.close();
        }
    }
}
