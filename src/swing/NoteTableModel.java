package swing;

import java.util.ArrayList;
import java.util.List;

import javax.swing.table.AbstractTableModel;
import entity.Note;
import service.CaseService;

public class NoteTableModel extends AbstractTableModel {

    private static final long serialVersionUID = 5172529856464610326L;
    private String[] columnNames = new String[] { "序号", "笔录名称", "开始时间", "结束时间", "地点", "文件名", "警员", "备注" };
    public List<Note> list = new ArrayList<>();

    public void setList(int caseId) {
        list = new CaseService().selectNoteByCaseId(caseId);
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
        Note note = list.get(rowIndex);
        if (columnIndex == 0)
            return note.getId();
        if (columnIndex == 1)
            return note.getName();
        if (columnIndex == 2)
            return note.getStartTime();
        if (columnIndex == 3)
            return note.getEndTime();
        if (columnIndex == 4)
            return note.getPlace();
        if (columnIndex == 5)
            return note.getFileName();
        /*if (columnIndex == 6)
            return note.getPoliceList();*/
        if (columnIndex == 7)
            return note.getRemark();
        return null;
    }

}
