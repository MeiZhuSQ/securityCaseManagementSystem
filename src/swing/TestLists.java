package swing;

import java.awt.Color;

import java.awt.Dimension;

import java.awt.FlowLayout;

import java.awt.GridLayout;

import java.awt.event.ActionEvent;

import java.awt.event.ActionListener;

import java.util.ArrayList;

import java.util.List;

import javax.swing.BorderFactory;

import javax.swing.DefaultListModel;

import javax.swing.JButton;

import javax.swing.JFrame;

import javax.swing.JList;

import javax.swing.JOptionPane;

import javax.swing.JScrollPane;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Container;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Vector;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JList;
import javax.swing.JScrollPane;
import javax.swing.ListCellRenderer;

import util.GUIUtil;
 
/**
 * 测试改变指定单元格元素
 * @author 天册
 * 2011年12月8日
 */
public class TestLists extends JFrame implements ActionListener {
 
    Vector v = new Vector();
    JList lst = new JList(v);
    JButton btnTest = new JButton("测试");
 
    public TestLists() {
        GUIUtil.useLNF();
        setLocation(400, 300);
        Container c = this.getContentPane();
        c.add(new JScrollPane(lst), BorderLayout.CENTER);
        c.add(btnTest, BorderLayout.SOUTH);
        pack();
        setVisible(true);
        lstLaunch();    //lst中添加元素和指定单元格类型的方法
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        btnTest.addActionListener(this);   //通过按钮事件改变JList某个单元格
    }
 
    private void lstLaunch() {
        for (int i = 0; i < 10; i++) {
            v.addElement(i);
        }
        lst.setCellRenderer(new MyRender());
    }
 
    /**
     * @param args
     */
    public static void main(String[] args) {
        TestLists tl = new TestLists();
 
    }
 
    @Override
    public void actionPerformed(ActionEvent e) {
        Object o = e.getSource();
        if (o.equals(btnTest)) {
            //跪求高人指点此处该怎么写才能改变指定单元格，比如第3个单元格背景色设置为红色
             
        }
 
    }
 
    /**
     * Copy 自API文档，应该是设置单元格属性的东东。。。
     * @author SUN
     */
    private class MyRender extends JButton implements ListCellRenderer {
 
        public MyRender() {
            this.setOpaque(true);
        }
 
        @Override
        public Component getListCellRendererComponent(JList list, Object value,
                int index, boolean isSelected, boolean cellHasFocus) {
            setText(value.toString());
 
            Color background;
            Color foreground;
 
            // check if this cell represents the current DnD drop location
            JList.DropLocation dropLocation = list.getDropLocation();
            if (dropLocation != null && !dropLocation.isInsert()
                    && dropLocation.getIndex() == index) {
 
                background = Color.BLUE;
                foreground = Color.red;
 
                // check if this cell is selected
            } else if (isSelected) {
                background = Color.green;
                foreground = Color.red;
 
                // unselected, and not the DnD drop location
            } else {
                background = Color.WHITE;
                foreground = Color.BLACK;
            }
            ;
 
            setBackground(background);
            setForeground(foreground);
 
            return this;
 
        }
 
    }
 
}