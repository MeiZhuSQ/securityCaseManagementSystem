package swing;

import java.awt.Dimension;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.JTableHeader;

import service.CaseService;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class PolicePanel extends JPanel {
    
    private static final long serialVersionUID = -6102284463671287341L;
    private static PolicePanel instance = null;
    private JTable policeTable;
    public PoliceTableModel policeTableModel;
    
    public PolicePanel() {
        setLayout(null);
        JScrollPane scrollPane = new JScrollPane();
        scrollPane.setBounds(0, 0, 662, 334);
        add(scrollPane);
        
        policeTableModel = new PoliceTableModel();
        policeTable = new JTable(policeTableModel);
        policeTable.setRowHeight(30);
        JTableHeader head = policeTable.getTableHeader(); 
        head.setPreferredSize(new Dimension(head.getWidth(), 30));
        scrollPane.setViewportView(policeTable);
        
        JButton btnNewButton = new JButton("新增");
        btnNewButton.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                PoliceDialog policeDialog = new PoliceDialog();
                policeDialog.setSize(new Dimension(500, 400));
                Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
                Dimension frameSize = policeDialog.getSize();
                if (frameSize.height > screenSize.height)
                    frameSize.height = screenSize.height;       
                if (frameSize.width > screenSize.width)
                    frameSize.width = screenSize.width;       
                policeDialog.setLocation((screenSize.width-frameSize.width)/2,(screenSize.height-frameSize.height) / 2);
                //MainFrame.frame.setEnabled(false);
                policeDialog.setVisible(true);
            }
        });
       /* btnNewButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                PoliceDialog policeDialog = new PoliceDialog();
                policeDialog.setSize(new Dimension(500, 400));
                Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
                Dimension frameSize = policeDialog.getSize();
                if (frameSize.height > screenSize.height)
                    frameSize.height = screenSize.height;       
                if (frameSize.width > screenSize.width)
                    frameSize.width = screenSize.width;       
                policeDialog.setLocation((screenSize.width-frameSize.width)/2,(screenSize.height-frameSize.height) / 2);
                policeDialog.setVisible(true);
            }
        });*/
        btnNewButton.setBounds(126, 348, 113, 27);
        add(btnNewButton);
        
        JButton btnNewButton_1 = new JButton("修改");
        btnNewButton_1.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                MainFrame.alert("111");
            }
        });
        btnNewButton_1.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                MainFrame.alert("222");
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
    
    public static PolicePanel getInstance() {
        if (instance == null) {
            instance = new PolicePanel();
        }
        return instance;
    }
    /*public void createPoliceTable () {
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
                
                public void updateModel () {
                    SwingUtilities.invokeLater(new Runnable(){
                        public void run() {
                            fireTableDataChanged();  //通知JTable数据对象已更改,重绘界面
                        }
                        
                    });
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
        
    }*/
    
    public void updateTable() {
        policeTableModel.list = new CaseService().listPolice();
        policeTableModel.fireTableDataChanged();
        policeTable.updateUI();
    }
}
