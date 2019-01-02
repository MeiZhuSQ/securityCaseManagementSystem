package swing;

import java.awt.Component;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.ParseException;
import java.util.ArrayList;

import javax.swing.AbstractCellEditor;
import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JTable;
import javax.swing.table.TableCellEditor;

import constant.CommonConstant;
import dto.ResultDTO;
import service.CaseService;
import util.DateUtil;
import util.GUIUtil;

/**
 * Table按鈕编辑
 * @author LiuPF
 * @date 2018-10-21
 */
public class CaseButtonEditor extends AbstractCellEditor implements TableCellEditor {

    private static final long serialVersionUID = -6546334664166791132L;

    private JPanel panel;

    private JButton button;

    private JButton button1;

    private JButton button2;

    private ArrayList<String> btnName;

    public CaseButtonEditor() {

        initButton();

        initPanel();

        panel.add(button);
        panel.add(button1);
        panel.add(button2);
    }

    private void initButton() {

        button = new ImageButton("view.png","详情1");
        button1 = new ImageButton("edit.png","修改1");
        button2 = new ImageButton("delete.png","删除1");
        button.setSize(new Dimension(16, 16));
        button1.setSize(new Dimension(50, 25));
        button2.setSize(new Dimension(50, 25));
        /*GUIUtil.setImageIcon(button, "edit.png", null);
        GUIUtil.setImageIcon(button1, "edit.png", null);
        GUIUtil.setImageIcon(button2, "edit.png", null);*/
        
        button.addActionListener(new ActionListener() {

            public void actionPerformed(ActionEvent e) {
                int i = MainFrame.getInstance().caseTable.getSelectedRow();
                int caseId = Integer.parseInt(MainFrame.getInstance().caseTableModel.getValueAt(i, 0).toString());
                String caseName = MainFrame.getInstance().caseTableModel.getValueAt(i, 1).toString();
                String caseTime = MainFrame.getInstance().caseTableModel.getValueAt(i, 2).toString();
                String caseRemark = MainFrame.getInstance().caseTableModel.getValueAt(i, 3).toString();
                ViewCasePanel viewPanel = ViewCasePanel.getInstance();
                viewPanel.setCaseId(caseId);
                viewPanel.caseNameField.setText(caseName);
                viewPanel.caseTimeField.setText(caseTime);
                viewPanel.caseRemarkField.setText(caseRemark);
                // 定义案件详情下的 综合列表Model，动态给Model赋值
                viewPanel.caseDetailTableModel.setList(caseId);
                MainFrame.tabbedPane.addTab("案件详情", viewPanel, null);
                MainFrame.tabbedPane.setSelectedComponent(viewPanel);
                fireEditingStopped();
            }
        });
        button1.addActionListener(new ActionListener() {

            public void actionPerformed(ActionEvent e) {
                CaseDialog caseDialog = CaseDialog.getInstance();
                caseDialog.setSize(new Dimension(500, 400));
                GUIUtil.setCenter(caseDialog);
                int i = MainFrame.getInstance().caseTable.getSelectedRow();
                caseDialog.setCaseId(Integer.parseInt(MainFrame.getInstance().caseTableModel.getValueAt(i, 0) + ""));
                caseDialog.caseNameField.setText(MainFrame.getInstance().caseTableModel.getValueAt(i, 2) + "");
                try {
                    caseDialog.datePickerField = DateUtil.setDatePicker(MainFrame.getInstance().caseTableModel.getValueAt(i, 3) + "");
                    caseDialog.datePickerField.updateUI();
                } catch (ParseException e1) {
                    e1.printStackTrace();
                }
                caseDialog.remarkField.setText(MainFrame.getInstance().caseTableModel.getValueAt(i, 4) + "");
                //注意：必须放在最后，否则无效
                caseDialog.setVisible(true);
                fireEditingStopped();
            }
        });
        button2.addActionListener(new ActionListener() {

            public void actionPerformed(ActionEvent e) {
            	if (MainFrame.getInstance().caseTable.getSelectedRow() < 0) {
	           		 MainFrame.alert("请选择一行！");
	           		 return;
            	}
                if (MainFrame.prompt("确定删除该案件吗？")){
                    int i = MainFrame.getInstance().caseTable.getSelectedRow();
                    String caseId = MainFrame.getInstance().caseTableModel.getValueAt(i, 0)+"";
                    CaseService caseService = new CaseService();
                    ResultDTO resultDTO = caseService.delCase(Integer.valueOf(caseId));
                    if (CommonConstant.RESULT_CODE_FAIL.equals(resultDTO.getCode())) {
                        MainFrame.alert(resultDTO.getMessage());
                        return;
                    }
                    MainFrame.alert("删除成功");
                    MainFrame.getInstance().updateCaseTable();
                }
                fireEditingStopped();
            }
        });
    }

    private void initPanel() {

        panel = new JPanel();

        panel.setLayout(new FlowLayout());
    }

    @Override
    public Component getTableCellEditorComponent(JTable table, Object value, boolean isSelected, int row, int column) {

        //btnName = (ArrayList<String>) value;显示按钮名字的
        /*button = new ImageButton("view.png");
        button1 = new ImageButton("edit.png");
        button2 = new ImageButton("delete.png");*/
        //button.setText(value == null ? "" : btnName.get(0));
        //button1.setText(value == null ? "" : btnName.get(1));
        //button2.setText(value == null ? "" : btnName.get(2));

        return panel;
    }

    @Override
    public Object getCellEditorValue() {

        return btnName;
    }

}