package swing;

import java.awt.Component;
import java.awt.FlowLayout;
import java.util.ArrayList;

import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JTable;
import javax.swing.table.TableCellRenderer;

public class CaseDetailButtonRenderer implements TableCellRenderer {
    private JPanel panel;

    private JButton button;

    private JButton button1;

    private ArrayList<String> btnName;

    public CaseDetailButtonRenderer() {
        initButton();

        initPanel();

        panel.add(button);
        panel.add(button1);
    }

    private void initButton() {

        button = new ImageButton("edit.png","");
        button1 = new ImageButton("delete.png","");
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
        //btnName = (ArrayList<String>) value;
        /*button = new ImageButton("edit.png");
        button1 = new ImageButton("delete.png");*/
        return panel;
    }

}