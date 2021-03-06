package swing;

import java.util.ArrayList;
import java.util.List;
import javax.swing.table.AbstractTableModel;
import entity.LegalCase;
import service.CaseService;

public class CaseTableModel extends AbstractTableModel{
    
    private static final long serialVersionUID = -9105203202190115194L;
    
    private String[] columnNames = new String[] { "ID", "序号", "案件名称", "时间", "备注","操作"};
    
    public List<LegalCase> list = new ArrayList<LegalCase>();

    public void setList(String caseName) {
        list = new CaseService().listCaseByKeyWord(caseName);
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
        //第5列可编辑
        if (columnIndex == 5) {
            return true;
        }
        return false;
    }

    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {
        LegalCase legalCase = list.get(rowIndex);
        if (columnIndex == 0)
            return legalCase.getId();
        if (columnIndex == 1)
            return rowIndex + 1;
        if (columnIndex == 2)
            return legalCase.getName();
        if (columnIndex == 3)
            return legalCase.getTime();
        if (columnIndex == 4)
            return legalCase.getRemark();
        return null;
    }
}
