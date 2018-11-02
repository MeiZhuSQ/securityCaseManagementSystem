package swing;

import java.util.ArrayList;
import java.util.List;
import javax.swing.table.AbstractTableModel;
import service.CaseService;

public class CaseDetailTableModel extends AbstractTableModel {

    private static final long serialVersionUID = 5172529856464610326L;
    private String[] columnNames = new String[] { "序号", "名称", "类型", "时间", "备注", "操作" };
    public List<LegalCaseDetail> list = new ArrayList<>();

    public void setList(int caseId) {
        list = new CaseService().getLegalCaseDetailList(caseId);
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
        LegalCaseDetail legalCaseDetail = list.get(rowIndex);
        if (columnIndex == 0)
            return legalCaseDetail.getId();
        if (columnIndex == 1)
            return legalCaseDetail.getName();
        if (columnIndex == 2)
            return legalCaseDetail.getType();
        if (columnIndex == 3)
            return legalCaseDetail.getStartTime();
        if (columnIndex == 4)
            return legalCaseDetail.getRemark();
        return null;
    }

}
