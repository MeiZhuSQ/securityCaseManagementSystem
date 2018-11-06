package util;

import javax.swing.*;
import javax.swing.table.TableColumn;
import java.awt.*;

/**
 * 工具类 GUIUtil
 */
public class GUIUtil {
    public static String imgFolder = "resources/img/";

    public static void useLNF() {
        try {
            // javax.swing.UIManager.setLookAndFeel("com.pagosoft.plaf.PgsLookAndFeel");
            //UIManager.setLookAndFeel("com.sun.java.swing.plaf.windows.WindowsLookAndFeel");
//            javax.swing.UIManager.setLookAndFeel("com.jtattoo.plaf.smart.SmartLookAndFeel");
            javax.swing.UIManager.setLookAndFeel("com.jtattoo.plaf.mcwin.McWinLookAndFeel");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * @param textField
     *            文本框组件
     * @param name
     *            文本框名字
     * @return 文本框输入是否正确
     */
    public static boolean checkNumber(JTextField textField, String name) {
        String text = textField.getText().trim();
        if (text.length() <= 0) {
            JOptionPane.showMessageDialog(null, "输入有误，" + name + "不能为空");
            textField.grabFocus();
            return false;
        }
        // 直接用try检查
        try {
            Integer.parseInt(text);
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "输入有误，" + name + "不是数字");
            return false;
        }
        return true;
    }

    /**
     * 把若干个组件设置成指定颜色
     *
     * @param color
     *            颜色
     * @param cs
     *            组件
     */
    public static void setColor(Color color, JComponent... cs) {
        for (JComponent c : cs) {
            c.setForeground(color);
        }
    }

    /**
     * 隐藏表格中的某一列
     * 
     * @param table
     *            表格
     * @param index
     *            要隐藏的列 的索引
     */
    public static void hideColumn(JTable table, int index) {
        TableColumn tc = table.getColumnModel().getColumn(index);
        tc.setMaxWidth(0);
        tc.setPreferredWidth(0);
        tc.setMinWidth(0);
        tc.setWidth(0);

        table.getTableHeader().getColumnModel().getColumn(index).setMaxWidth(0);
        table.getTableHeader().getColumnModel().getColumn(index).setMinWidth(0);
    }

    /**
     * 设置JDialog居中
     * 
     * @param dialog
     */
    public static void setCenter(JDialog dialog) {
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        Dimension frameSize = dialog.getSize();
        if (frameSize.height > screenSize.height)
            frameSize.height = screenSize.height;
        if (frameSize.width > screenSize.width)
            frameSize.width = screenSize.width;
        dialog.setLocation((screenSize.width - frameSize.width) / 2, (screenSize.height - frameSize.height) / 2);
    }
}
