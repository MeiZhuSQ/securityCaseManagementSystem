package swing;

import java.awt.Dimension;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.JTableHeader;

import entity.Note;
import entity.Police;
import service.CaseService;
import util.GUIUtil;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class PolicePanel extends JPanel {
    
    private static final long serialVersionUID = -6102284463671287341L;
    private JTable policeTable;
    private DefaultTableModel policeTableModel;
    
    public PolicePanel() {
        setLayout(null);
        
        JScrollPane scrollPane = new JScrollPane();
        scrollPane.setBounds(0, 0, 662, 334);
        add(scrollPane);
        
        policeTable = new JTable();
        scrollPane.setViewportView(policeTable);
        
        JButton btnNewButton = new JButton("新增");
        btnNewButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
            }
        });
        btnNewButton.setBounds(126, 348, 113, 27);
        add(btnNewButton);
        
        JButton btnNewButton_1 = new JButton("修改");
        btnNewButton_1.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
            }
        });
        btnNewButton_1.setBounds(253, 348, 113, 27);
        add(btnNewButton_1);
        
        JButton btnNewButton_2 = new JButton("删除");
        btnNewButton_2.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
            }
        });
        btnNewButton_2.setBounds(380, 348, 113, 27);
        add(btnNewButton_2);
    }
    
    public void createNoteTable () {
        policeTable.setRowHeight(30);
        policeTableModel = new DefaultTableModel(
                new Object[][] {
                },
                new String[] {
                    "序号", "姓名", "警号", "性别"
                }
                
            ){
                private static final long serialVersionUID = -4270056429026018083L;
                @Override
                public boolean isCellEditable(int row, int column) {
                    if (column == 0) {
                        return true;
                    }
                    return false;
                }
            
        };
        CaseService caseService = new CaseService();
        List<Police> policeList = caseService.listPolice();
        for (Police police : policeList) {
            String id = String.valueOf(police.getId());
            String name = police.getName();
            String policeNum = police.getPoliceNumber();
            String sex = police.getSex();
            String[] row ={id, name, policeNum, sex};
            policeTableModel.addRow(row);
        }
        policeTable.setModel(policeTableModel);
        GUIUtil.hideColumn(policeTable, 0);
        JTableHeader head = policeTable.getTableHeader(); 
        head.setPreferredSize(new Dimension(head.getWidth(), 30));
        
    }
}
