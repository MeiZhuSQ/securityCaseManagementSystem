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

public class MyButtonRenderer implements TableCellRenderer {
    private JPanel panel;

    private JButton button;
    
    private JButton button1;
    
    private ArrayList<String> btnName;

    public MyButtonRenderer() {
        initButton();

        initPanel();

        panel.add(button);
        panel.add(button1);
    }

    private void initButton() {
    	
        button = new JButton();
        button1 = new JButton();
        button.setSize(new Dimension(50, 25));
        button1.setSize(new Dimension(50, 25));
    }

    private void initPanel() {
        panel = new JPanel();
        
        panel.setLayout(new FlowLayout());
    }

    public Component getTableCellRendererComponent(JTable table, Object value,
            boolean isSelected, boolean hasFocus, int row, int column) {
    	btnName = (ArrayList<String>) value;
        
        button.setText(value == null ? "" : btnName.get(0));
        button1.setText(value == null ? "" : btnName.get(1));
        return panel;
    }

}