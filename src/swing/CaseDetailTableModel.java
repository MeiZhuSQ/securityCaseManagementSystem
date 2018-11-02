package swing;

import java.util.ArrayList;
import java.util.List;

import javax.swing.table.AbstractTableModel;

import constant.CommonConstant;
import service.CaseService;
import vo.CaseItemVO;

public class CaseDetailTableModel extends AbstractTableModel {

    private static final long serialVersionUID = 5172529856464610326L;
    private String[] columnNames = new String[] { "序号", "名称", "类型", "时间", "备注", "操作" };
    public List<CaseItemVO> list = new ArrayList<>();

    public void setList(int caseId) {
        list = new CaseService().getCaseItems(caseId);
    }

    @Override
    public int getRowCount() {
        return list.size();
    }

    @Override
    public int getColumnCount() {
        return columnNames.length;
    }

    @Override
    public String getColumnName(int columnIndex) {
        return columnNames[columnIndex];
    }

    @Override
    public boolean isCellEditable(int rowIndex, int columnIndex) {
        return false;
    }

    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {
        CaseItemVO caseItemVO = list.get(rowIndex);
        if (columnIndex == 0)
            return caseItemVO.getId();
        if (columnIndex == 1)
            return caseItemVO.getName();
        if (columnIndex == 2) {
            if (CommonConstant.CASE_ITEM_TYPE_NOTE.equals(caseItemVO.getType())) {
                return "笔录";
            } else if (CommonConstant.CASE_ITEM_TYPE_PROCEDURE.equals(caseItemVO.getType())) {
                return "法律手续";
            } else if (CommonConstant.CASE_ITEM_TYPE_CLOCK.equals(caseItemVO.getType())) {
                return "闹钟";
            } else {
                return "-";
            }
        }
        if (columnIndex == 3)
            return caseItemVO.getTime();
        if (columnIndex == 4)
            return caseItemVO.getRemark();
        return null;
    }

}
