package swing;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.util.ArrayList;

import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JTable;
import javax.swing.table.TableCellRenderer;

import util.GUIUtil;

public class CaseButtonRenderer implements TableCellRenderer {
    private JPanel panel;

    private JButton button;

    private JButton button1;

    private JButton button2;

    private ArrayList<String> btnName;

    public CaseButtonRenderer() {
        initButton();

        initPanel();

        panel.add(button);
        panel.add(button1);
        panel.add(button2);
    }

    private void initButton() {

        button = new ImageButton("view.png");
        button1 = new ImageButton("edit.png");
        button2 = new ImageButton("delete.png");
        /*button.setSize(new Dimension(16, 16));
        button1.setSize(new Dimension(50, 25));
        button2.setSize(new Dimension(50, 25));*/
    }

    private void initPanel() {
        panel = new JPanel();

        panel.setLayout(new FlowLayout());
    }

    public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus,
            int row, int column) {
        btnName = (ArrayList<String>) value;
        button = new ImageButton("view.png");
        button1 = new ImageButton("edit.png");
        button2 = new ImageButton("delete.png");
        /*GUIUtil.setImageIcon(button, "edit.png", "修改案件");
        GUIUtil.setImageIcon(button1, "edit.png", "修改案件");
        GUIUtil.setImageIcon(button2, "edit.png", "修改案件");*/
//        button.setText(value == null ? "" : btnName.get(0));
//        button1.setText(value == null ? "" : btnName.get(1));
//        button2.setText(value == null ? "" : btnName.get(2));
        return panel;
    }

}