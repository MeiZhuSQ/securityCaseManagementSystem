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

import constant.CommonConstant;
import dto.ResultDTO;
import service.CaseService;
import util.GUIUtil;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class AskedPersonPanel extends JPanel {

    private static final long serialVersionUID = -6102284463671287341L;
    private static AskedPersonPanel instance = null;
    private JTable policeTable;
    public PoliceTableModel policeTableModel;

    public AskedPersonPanel() {
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
                PoliceDialog policeDialog = PoliceDialog.getInstance();
                policeDialog.setSize(new Dimension(500, 400));
                GUIUtil.setCenter(policeDialog);
                // MainFrame.frame.setEnabled(false);
                policeDialog.setVisible(true);
            }
        });
        btnNewButton.setBounds(126, 348, 113, 27);
        add(btnNewButton);

        JButton btnNewButton_1 = new JButton("修改");
        btnNewButton_1.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                PoliceDialog policeDialog = PoliceDialog.getInstance();
                policeDialog.setSize(new Dimension(500, 400));
                GUIUtil.setCenter(policeDialog);
                int i = policeTable.getSelectedRow();
                policeDialog.setPoliceId(Integer.parseInt(policeTableModel.getValueAt(i, 0) + ""));
                policeDialog.policeNameField.setText(policeTableModel.getValueAt(i, 1) + "");
                String sex = policeTableModel.getValueAt(i, 2) + "";
                policeDialog.policeSexField.setSelectedIndex(sex.equals("男") ? 0 : 1);
                policeDialog.policeCodeField.setText(policeTableModel.getValueAt(i, 3) + "");
                // MainFrame.frame.setEnabled(false);
                policeDialog.setVisible(true);
            }
        });
        btnNewButton_1.setBounds(253, 348, 113, 27);
        add(btnNewButton_1);

        JButton btnNewButton_2 = new JButton("删除");
        btnNewButton_2.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                if (MainFrame.prompt("确定删除该民警信息吗？")){
                    int i = policeTable.getSelectedRow();
                    int policeId = Integer.parseInt(policeTableModel.getValueAt(i, 0) + "");
                    CaseService caseService = new CaseService();
                    ResultDTO resultDTO = caseService.delPolice(policeId);
                    if (CommonConstant.RESULT_CODE_FAIL.equals(resultDTO.getCode())) {
                        MainFrame.alert(resultDTO.getMessage());
                        return;
                    }
                    MainFrame.alert("删除成功");
                }
                instance.updateTable();
            }
        });
        btnNewButton_2.setBounds(380, 348, 113, 27);
        add(btnNewButton_2);
    }

    public static AskedPersonPanel getInstance() {
        if (instance == null) {
            instance = new AskedPersonPanel();
        }
        return instance;
    }
    /*
     * public void createPoliceTable () { policeTable.setRowHeight(30);
     * policeTableModel = new DefaultTableModel( new Object[][] { }, new
     * String[] { "序号", "姓名", "警号", "性别" }
     * 
     * ){ private static final long serialVersionUID = -4270056429026018083L;
     * 
     * @Override public boolean isCellEditable(int row, int column) { if (column
     * == 0) { return true; } return false; }
     * 
     * public void updateModel () { SwingUtilities.invokeLater(new Runnable(){
     * public void run() { fireTableDataChanged(); //通知JTable数据对象已更改,重绘界面 }
     * 
     * }); }
     * 
     * }; CaseService caseService = new CaseService(); List<Police> policeList =
     * caseService.listPolice(); for (Police police : policeList) { String id =
     * String.valueOf(police.getId()); String name = police.getName(); String
     * policeNum = police.getPoliceNumber(); String sex = police.getSex();
     * String[] row ={id, name, policeNum, sex}; policeTableModel.addRow(row); }
     * policeTable.setModel(policeTableModel); GUIUtil.hideColumn(policeTable,
     * 0); JTableHeader head = policeTable.getTableHeader();
     * head.setPreferredSize(new Dimension(head.getWidth(), 30));
     * 
     * }
     */

    public void updateTable() {
        policeTableModel.list = new CaseService().listPolice();
        //或 policeTableModel.fireTableDataChanged();
        policeTable.updateUI();
    }

}
