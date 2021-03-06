package swing;

import java.util.ArrayList;
import java.util.List;

import javax.swing.table.AbstractTableModel;

import constant.CommonConstant;
import service.CaseService;
import vo.CaseItemVO;

public class CaseDetailTableModel extends AbstractTableModel {

    private static final long serialVersionUID = 5172529856464610326L;
    private String[] columnNames = new String[] {"ID" ,"序号", "名称", "类型", "开始时间", "结束时间", "备注", "操作" };
    public List<CaseItemVO> list = new ArrayList<>();

    public void setList(int caseId, String name, String itemType) {
        list = new CaseService().getCaseItemsByKeyWord(caseId, name, itemType);
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
        if (columnIndex == 7) {
            return true;
        }
        return false;
    }

    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {
        CaseItemVO caseItemVO = list.get(rowIndex);
        if (columnIndex == 0)
            return caseItemVO.getId();
        if (columnIndex == 1)
            return rowIndex + 1;
        if (columnIndex == 2)
            return caseItemVO.getName();
        if (columnIndex == 3) {
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
        if (columnIndex == 4)
            return caseItemVO.getTime();
        if (columnIndex == 5)
            return caseItemVO.getEndTime();
        if (columnIndex == 6)
            return caseItemVO.getRemark();
        return null;
    }

}
