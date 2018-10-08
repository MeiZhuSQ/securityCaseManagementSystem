package gui.frame;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

import org.apache.commons.lang.StringUtils;

import com.eltima.components.ui.DatePicker;

import entity.LegalCase;
import entity.Note;
import gui.frame.MainFrame.ActionPanelEditorRenderer;
import service.CaseService;
import util.DateUtil;
import javax.swing.JSeparator;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.JTableHeader;
import javax.swing.table.TableColumn;

public class ViewCasePanel extends JPanel {
    
    private static final long serialVersionUID = 7334759086622449699L;
    private JTable notetable;
    private DefaultTableModel noteTableModel;

    public ViewCasePanel() {
        setLayout(null);
        
        JPanel panel = new JPanel();
        panel.setBounds(0, 0, 450, 51);
        add(panel);
        panel.setLayout(null);
        
        JLabel label = new JLabel("案件详情");
        label.setBounds(0, 0, 72, 18);
        panel.add(label);
        
        JLabel lblNewLabel = new JLabel("笔录列表");
        lblNewLabel.setBounds(0, 62, 72, 18);
        add(lblNewLabel);
        
        JScrollPane scrollPane = new JScrollPane();
        scrollPane.setBounds(0, 90, 450, 86);
        add(scrollPane);
        
        initViewCaseTable();
        scrollPane.setRowHeaderView(notetable);
        
        JPanel panel_1 = new JPanel();
        panel_1.setBounds(0, 189, 450, 111);
        add(panel_1);
        panel_1.setLayout(null);
        
        JLabel lblNewLabel_1 = new JLabel("法律程序");
        lblNewLabel_1.setBounds(0, 0, 72, 18);
        panel_1.add(lblNewLabel_1);
        
    }
    
    public void initViewCaseTable () {
        notetable = new JTable();
        notetable.setRowHeight(30);
        noteTableModel = new DefaultTableModel(
                new Object[][] {
                },
                new String[] {
                    "笔录名称", "开始时间", "结束时间", "地点", "文件名", "警员",  "备注"
                }
            );
        CaseService caseService = new CaseService();
        List<Note> listNote = caseService.listNote();
        for (Note note : listNote) {
            String name = note.getName();
            String[] s ={"201800902", name, "11"};
            noteTableModel.addRow(s);
        }
        notetable.setModel(noteTableModel);
        JTableHeader head = notetable.getTableHeader(); // 创建表格标题对象
        head.setPreferredSize(new Dimension(head.getWidth(), 15));// 设置表头大小
        
        /*ActionPanelEditorRenderer er = new ActionPanelEditorRenderer();
        TableColumn column = notetable.getColumnModel().getColumn(3);
        column.setCellRenderer(er);
        column.setCellEditor(er);*/
    }
}
