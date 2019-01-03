package swing;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.File;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.filechooser.FileFilter;
import util.SQLUtil;

public class BackupListener extends MouseAdapter {

    @Override
    public void mouseClicked(MouseEvent e) {
        JPanel p = new JPanel();
        //拉起选择器
        JFileChooser fc = new JFileChooser();
        fc.setSelectedFile(new File("backup.db"));
        //定义过滤器
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

        int returnVal = fc.showSaveDialog(p);
        File file = fc.getSelectedFile();
        //捕捉是否点击保存
        if (returnVal == JFileChooser.APPROVE_OPTION) {
            //如果保存的文件名没有以.db结尾，自动加上.db
            System.out.println(file);
            if (!file.getName().toLowerCase().endsWith(".db"))
                file = new File(file.getParent(), file.getName() + ".db");
            System.out.println(file);

            try {
                SQLUtil.backup(file.getAbsolutePath());
                /*打开本地文件
                Runtime runtime = Runtime.getRuntime();  
                runtime.exec("rundll32 url.dll FileProtocolHandler "+"C:/Users/lpf/Desktop/TestFrame.java"); */
                JOptionPane.showMessageDialog(p, "备份成功,备份文件位于:\r\n" + file.getAbsolutePath());
            } catch (Exception e1) {
                e1.printStackTrace();
                JOptionPane.showMessageDialog(p, "备份失败\r\n,错误:\r\n" + e1.getMessage());
            }

        }
    }

}