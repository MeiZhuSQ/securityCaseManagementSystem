package swing;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.File;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.filechooser.FileFilter;

import util.SQLUtil;

public class RecoverListener extends MouseAdapter {

    @Override
    public void mouseClicked(MouseEvent e) {
        JPanel p = new JPanel();
        JFileChooser fc = new JFileChooser();
        fc.setSelectedFile(new File("backup.db"));
        fc.setFileFilter(new FileFilter() {

            @Override
            public String getDescription() {
                return ".db";
            }

            @Override
            public boolean accept(File f) {
                return f.getName().toLowerCase().endsWith(".db");
            }
        });

        int returnVal = fc.showOpenDialog(p);
        File file = fc.getSelectedFile();
        if (returnVal == JFileChooser.APPROVE_OPTION) {
            try {
                SQLUtil.recover(file.getAbsolutePath());
                JOptionPane.showMessageDialog(p, "恢复成功");
            } catch (Exception e1) {
                e1.printStackTrace();
                JOptionPane.showMessageDialog(p, "恢复失败\r\n,错误:\r\n" + e1.getMessage());
            }

        }
    }

}