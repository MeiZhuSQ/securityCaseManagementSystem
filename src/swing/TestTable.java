package swing;

import java.awt.Color;
import java.awt.EventQueue;
import java.util.ArrayList;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

public class TestTable {

    private JFrame frame;
    private JTable table;
    
    private ArrayList<String> btnName = new ArrayList<String>();
    private Object[][] data;


    public static void main(String[] args) {
        EventQueue.invokeLater(new Runnable() {
            public void run() {
                try {
                    TestTable window = new TestTable();
                    window.frame.setVisible(true);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }


    public TestTable() {
    	btnName.add("增加");
    	btnName.add("删除");
   
        
        frame = new JFrame();
        frame.setBounds(100, 100, 450, 300);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.getContentPane().setLayout(null);

        JPanel panel = new JPanel();
        panel.setBounds(10, 10, 414, 242);
        frame.getContentPane().add(panel);
        panel.setLayout(null);

        JScrollPane scrollPane = new JScrollPane();
        scrollPane.setBounds(10, 10, 394, 222);
        panel.add(scrollPane);

        table = new JTable();
        scrollPane.setViewportView(table);
        table.setRowHeight(30);

        table.setModel(new DefaultTableModel() {
        	
            Object[][] data = {
                    {1, 2, btnName},
                    {4, 5, btnName},
                    {7, 8, btnName}};
            
            @Override
            public Object getValueAt(int row, int column) {
                return data[row][column];
            }

            @Override
            public int getRowCount() {
                return 3;
            }

            @Override
            public int getColumnCount() {
                return 3;
            }
            @Override
            public void setValueAt(Object aValue, int row, int column){
                data[row][column] = aValue;
                fireTableCellUpdated(row, column);
            }
            
            @Override
            public boolean isCellEditable(int row, int column) {
                if (column >= 2) {
                    return true;
                } else {
                    return false;
                }
            }
        });
        table.getColumnModel().getColumn(2).setMinWidth(120);
        table.getColumnModel().getColumn(2).setCellEditor(
                new MyButtonEditor());

        table.getColumnModel().getColumn(2).setCellRenderer(
                new MyButtonRenderer());
        
//        table.getColumnModel().getColumn(3).setCellEditor(
//                new MyButtonEditor());
//
//        table.getColumnModel().getColumn(3).setCellRenderer(
//                new MyButtonRenderer());
        table.setRowSelectionAllowed(true);
    }
}