package util;

import javax.swing.*;
import javax.swing.table.TableColumn;

import java.awt.*;
import java.io.File;

/**
 * 工具类 GUIUtil GUI的各种常用工具
 */
public class GUIUtil {
    //图片的文件夹
    public static String imgFolder = "resources/img/";

    //定义皮肤
    public static void useLNF() {
        try {
            //org.jb2011.lnf.beautyeye.BeautyEyeLNFHelper.launchBeautyEyeLNF();
            //javax.swing.UIManager.setLookAndFeel("com.pagosoft.plaf.PgsLookAndFeel");
            UIManager.setLookAndFeel("com.sun.java.swing.plaf.windows.WindowsLookAndFeel");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * @param textField 文本框组件
     * @param name      文本框名字
     * @return 文本框输入是否正确
     */
    public static boolean checkNumber(JTextField textField, String name) {
        String text = textField.getText().trim();
        if (text.length() <= 0) {
            JOptionPane.showMessageDialog(null, "输入有误，" + name + "不能为空");
            textField.grabFocus();
            return false;
        }
        //直接用try检查
        try {
            Integer.parseInt(text);
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "输入有误，" + name + "不是数字");
            return false;
        }
        return true;
    }

    /**
     * 测试用，直接把面板放进去拉起一个窗体
     *
     * @param p       待测试的面板
     * @param stretch 缩放比例
     */
    /* public static void showPanel(JPanel p, double stretch) {
        GUIUtil.useLNF();
        JFrame f = new JFrame();
        f.setLocationRelativeTo(null);
        f.setSize(500, 500);
        CenterPanel cp = new CenterPanel(stretch);
        f.setContentPane(cp);
        f.setDefaultCloseOperation(3);
        f.setVisible(true);
        cp.show(p);
    }

    public static void showPanel(JPanel p) {
        showPanel(p, 0.85);
    }*/

    /**
     * 把一个按钮设置成图文形式
     *
     * @param b        按钮
     * @param fileName 图片名
     * @param tip      按钮下文字
     */
    public static void setImageIcon(JButton b, String fileName, String tip) {
        ImageIcon i = new ImageIcon((new File(imgFolder, fileName)).getAbsolutePath());
        b.setIcon(i);
        b.setPreferredSize(new Dimension(30, 30));
        b.setToolTipText(tip);
        b.setHorizontalTextPosition(JButton.CENTER);
        
        b.setFocusable(false);
        //b.setText(tip);
    }

    /**
     * 把若干个组件设置成指定颜色
     *
     * @param color 颜色
     * @param cs    组件
     */
    public static void setColor(Color color, JComponent... cs) {
        for (JComponent c : cs) {
            c.setForeground(color);
        }
    }
    
    /** 
    * 隐藏表格中的某一列 
    * @param table  表格 
    * @param index  要隐藏的列 的索引 
    */ 
    public static void hideColumn(JTable table,int index){ 
        TableColumn tc= table.getColumnModel().getColumn(index); 
        tc.setMaxWidth(0); 
        tc.setPreferredWidth(0); 
        tc.setMinWidth(0); 
        tc.setWidth(0); 
    
        table.getTableHeader().getColumnModel().getColumn(index).setMaxWidth(0); 
        table.getTableHeader().getColumnModel().getColumn(index).setMinWidth(0); 
    }

     
}
