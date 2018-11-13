package swing;

import java.awt.Component;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;

import javax.swing.AbstractCellEditor;
import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JTable;
import javax.swing.table.TableCellEditor;

import constant.CommonConstant;
import dto.ResultDTO;
import entity.Clock;
import entity.Note;
import entity.Procedure;
import service.CaseService;
import util.DateUtil;
import util.GUIUtil;

/**
 * Table按鈕编辑
 * @author LiuPF
 * @date 2018-10-21
 */
public class CaseDetailButtonEditor extends AbstractCellEditor implements TableCellEditor {

    private static final long serialVersionUID = -6546334664166791132L;

    private JPanel panel;

    private JButton button;

    private JButton button1;

    private ArrayList<String> btnName;

    public CaseDetailButtonEditor() {

        initButton();

        initPanel();

        panel.add(button);
        panel.add(button1);
    }

    private void initButton() {

        button = new ImageButton("edit.png","");
        button1 = new ImageButton("delete.png","");
        button.setSize(new Dimension(16, 16));
        button1.setSize(new Dimension(50, 25));
        /*GUIUtil.setImageIcon(button, "edit.png", null);
        GUIUtil.setImageIcon(button1, "edit.png", null);
        GUIUtil.setImageIcon(button2, "edit.png", null);*/
        
        button.addActionListener(new ActionListener() {

            public void actionPerformed(ActionEvent e) {
                int i = ViewCasePanel.getInstance().caseDetailTable.getSelectedRow();
                int caseItemId = Integer.parseInt(ViewCasePanel.getInstance().caseDetailTable.getValueAt(i, 0).toString());
                String caseItemType = ViewCasePanel.getInstance().caseDetailTableModel.getValueAt(i, 3).toString();
                //选择的是笔录
                if ("笔录".equals(caseItemType)) {
                    Note note = new CaseService().selectNoteById(caseItemId);
                    ViewCasePanel viewPanel = ViewCasePanel.getInstance();
                    //编辑时，传入caseId
                    viewPanel.setCaseId(note.getCaseId());
                    viewPanel.caseNameField.setText(note.getName());
                    viewPanel.caseTimeField.setText(note.getStartTime());
                    viewPanel.caseRemarkField.setText(note.getRemark());
                    MainFrame.tabbedPane.addTab("编辑笔录", viewPanel, null);
                    MainFrame.tabbedPane.setSelectedComponent(viewPanel);
                    //选择的是手续
                } else if ("法律手续".equals(caseItemType)) {
                    Procedure procedure = new CaseService().selectProceduresById(caseItemId);
                    ProcedureDialog procedureDialog = ProcedureDialog.getInstance();
                    procedureDialog.setSize(new Dimension(500, 400));
                    GUIUtil.setCenter(procedureDialog);
                    procedureDialog.setCaseId(ViewCasePanel.getInstance().getCaseId());
                    procedureDialog.setProcedureId(caseItemId);
                    procedureDialog.procedureNameField.setText(procedure.getName());
                    procedureDialog.remarkField.setText(procedure.getRemark());
                    procedureDialog.setVisible(true);
                } else {
                    //选择的是闹钟
                    ClockDialog clockDialog = ClockDialog.getInstance();
                    clockDialog.setSize(new Dimension(500, 400));
                    GUIUtil.setCenter(clockDialog);
                    Clock clock = new CaseService().getClockById(caseItemId);
                    clockDialog.setClockId(clock.getId());
                    clockDialog.clockNameField.setText(clock.getName());
                    clockDialog.remarkField.setText(clock.getRemark());
                    clockDialog.setVisible(true);
                }
                fireEditingStopped();
            }
        });
        button1.addActionListener(new ActionListener() {

            public void actionPerformed(ActionEvent e) {
                if (MainFrame.prompt("确定删除该条记录吗？")){
                    int i = ViewCasePanel.getInstance().caseDetailTable.getSelectedRow();
                    int caseItemId = Integer.parseInt(ViewCasePanel.getInstance().caseDetailTableModel.getValueAt(i, 0)+"");
                    String caseItemType = ViewCasePanel.getInstance().caseDetailTableModel.getValueAt(i, 3).toString();
                    CaseService caseService = new CaseService();
                    ResultDTO resultDTO = new ResultDTO();
                    //选择的是笔录
                    if ("笔录".equals(caseItemType)) {
                        caseService.delNote(caseItemId);
                    } else if ("法律手续".equals(caseItemType)) {
                        resultDTO = caseService.delProcedure(caseItemId);
                    } else {
                        resultDTO = caseService.delClock(caseItemId);
                    }
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

        //btnName = (ArrayList<String>) value;
       /* button = new ImageButton("edit.png");
        button1 = new ImageButton("delete.png");*/
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