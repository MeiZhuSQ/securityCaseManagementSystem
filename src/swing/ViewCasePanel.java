package swing;

import java.awt.Color;
import java.awt.Dimension;
import javax.swing.BorderFactory;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.border.Border;
import javax.swing.border.TitledBorder;
import entity.LegalCase;
import entity.Note;
import entity.Police;
import service.CaseService;
import util.DateUtil;
import util.GUIUtil;

import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.JTableHeader;
import javax.swing.table.TableColumn;

import org.apache.commons.lang.StringUtils;

import constant.CommonConstant;
import dto.ResultDTO;

import javax.swing.JButton;
import java.awt.FlowLayout;
import java.awt.event.ActionListener;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;
import java.awt.event.ActionEvent;

public class ViewCasePanel extends JPanel {

    private static final long serialVersionUID = 7334759086622449699L;
    public  JTable caseDetailTable;
    public  CaseDetailTableModel caseDetailTableModel;
    private JTextField caseTimeField;
    private JTextField caseRemarkField;
    private JTable procedureTable;
    private ProcedureTableModel procedureTableModel;
    private ArrayList<String> btnName = new ArrayList<String>();
    // 记录案子ID
    private int caseId;
    
    private static ViewCasePanel instance;
    private JTextField textField;
    
    public static ViewCasePanel getInstance() {
        if (instance == null) {
            instance = new ViewCasePanel();
        }
        return instance;
    }
    
    public ViewCasePanel() {
        // 更新选中的caseId
        //int i = MainFrame.getInstance().caseTable.getSelectedRow();
        //this.caseId = Integer.parseInt(MainFrame.getInstance().caseTableModel.getValueAt(i, 0) + "");
        //CaseService caseService = new CaseService();
        //LegalCase legalCase = caseService.selectCaseById(caseId);

        setLayout(null);

        JPanel casePanel = new JPanel();
        casePanel.setBounds(0, 0, 1000, 77);
        add(casePanel);
        casePanel.setLayout(null);

        Border caseTitleBorder, caseLineBorder;
        caseLineBorder = BorderFactory.createLineBorder(Color.DARK_GRAY);
        caseTitleBorder = BorderFactory.createTitledBorder(caseLineBorder, "案件详情",
                TitledBorder.LEFT, TitledBorder.CENTER);
        casePanel.setBorder(caseTitleBorder);

        JLabel caseTimeLabel = new JLabel("案件时间");
        caseTimeLabel.setBounds(327, 29, 84, 18);
        casePanel.add(caseTimeLabel);

        JLabel caseRemarkLabel = new JLabel("案件备注");
        caseRemarkLabel.setBounds(547, 29, 84, 18);
        casePanel.add(caseRemarkLabel);

        caseTimeField = new JTextField();
        caseTimeField.setEditable(false);
        caseTimeField.setBounds(398, 26, 135, 24);
        casePanel.add(caseTimeField);
        caseTimeField.setColumns(10);

        caseRemarkField = new JTextField();
        caseRemarkField.setEditable(false);
        caseRemarkField.setBounds(619, 26, 182, 24);
        casePanel.add(caseRemarkField);
        caseRemarkField.setColumns(10);
        
        textField = new JTextField();
        textField.setEditable(false);
        textField.setColumns(10);
        textField.setBounds(107, 26, 176, 24);
        casePanel.add(textField);
        
        JLabel label = new JLabel("案件名称");
        label.setBounds(35, 29, 84, 18);
        casePanel.add(label);

        JScrollPane noteScrollPane = new JScrollPane();
        noteScrollPane.setBounds(0, 90, 1000, 398);
        add(noteScrollPane);

        Border noteTitleBorder, noteLineBorder;
        noteLineBorder = BorderFactory.createLineBorder(Color.DARK_GRAY);
        noteTitleBorder = BorderFactory.createTitledBorder(noteLineBorder, "关联笔录/手续/闹钟", TitledBorder.LEFT,
                TitledBorder.CENTER);
        noteScrollPane.setBorder(noteTitleBorder);

        // createNoteTable();
        caseDetailTableModel = new CaseDetailTableModel();
        caseDetailTable = new JTable(caseDetailTableModel);
        caseDetailTable.setRowHeight(30);
        JTableHeader head = caseDetailTable.getTableHeader();
        head.setPreferredSize(new Dimension(head.getWidth(), 30));
        
        btnName.add("修改");
        btnName.add("删除");
        TableColumn column = caseDetailTable.getColumnModel().getColumn(5);
        column.setCellRenderer(new CaseDetailButtonRenderer());
        column.setCellEditor(new CaseDetailButtonEditor());
        noteScrollPane.setViewportView(caseDetailTable);

        JPanel panel = new JPanel();
        panel.setBounds(0, 501, 1000, 36);
        add(panel);

        JButton createNoteButton = new JButton("新建笔录");
        createNoteButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent arg0) {
                //NotePanel notePanel = new NotePanel(caseId);
                NotePanel notePanel = NotePanel.getInstance();
                notePanel.setCaseId(caseId);
                //重置noteId
                NotePanel.noteId = 0;
                //给两表格置空
                notePanel.askedPersonTableModel.setList(0);
                notePanel.otherPersonTableModel.setList(0);
                notePanel.getNoteNameField().setText("");;
                notePanel.getPlaceField().setText("");
                notePanel.getFileNameField().setText("");
                notePanel.getRemarkTextArea().setText("");
                List<String> policeList = new ArrayList<>();
                //Object[] defaultValue = new Object[] {};
                CaseService caseService = new CaseService();
                List<Police> polices = caseService.listPolice();
                for (Police police : polices) {
                    policeList.add(/*polices.get(i).getId() + "_" + */police.getName());

                }
                Object[] defaultValue = new String[] {"请选择"};
                notePanel.mulit.MulitCombobox_all(policeList.toArray(new String[policeList.size()]), defaultValue);
                MainFrame.tabbedPane.addTab("新建笔录", notePanel, null);
                MainFrame.tabbedPane.setSelectedComponent(notePanel);
            }
        });
        panel.add(createNoteButton);

        JButton createProcedureButton = new JButton("新建法律手续");
        createProcedureButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                //NotePanel notePanel = new NotePanel(caseId);
                NotePanel notePanel = NotePanel.getInstance();
                notePanel.setCaseId(caseId);
                int i = caseDetailTable.getSelectedRow();
                int noteId = Integer.parseInt(caseDetailTableModel.getValueAt(i, 0) + "");
                //askedPersonTableModel赋值
                notePanel.askedPersonTableModel.setList(noteId);
                //otherPersonTableModel赋值
                notePanel.otherPersonTableModel.setList(noteId);
                //传入noteId，防止新增时选中了行
                NotePanel.noteId = noteId;
                //给note赋值
                Note note = new CaseService().selectNoteById(noteId);
                notePanel.getNoteNameField().setText(note.getName());
                notePanel.getPlaceField().setText(note.getPlace());
                notePanel.getFileNameField().setText(note.getFileName());
                notePanel.getRemarkTextArea().setText(note.getRemark());
                
                List<String> policeList = new ArrayList<>();
                //Object[] defaultValue = new Object[] {};
                CaseService caseService = new CaseService();
                List<Police> polices = caseService.listPolice();
                for (Police police : polices) {
                    policeList.add(/*polices.get(i).getId() + "_" + */police.getName());

                }
                String[] defaultValue = /*note.getPoliceList().split(",")*/null;
                notePanel.mulit.MulitCombobox_all(policeList.toArray(new String[policeList.size()]), defaultValue);
                MainFrame.tabbedPane.addTab("修改笔录", notePanel, null);
                MainFrame.tabbedPane.setSelectedComponent(notePanel);
            }
        });
        panel.add(createProcedureButton);

        JButton createClockButton = new JButton("新建闹钟");
        createClockButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
            	if (caseDetailTable.getSelectedRow() <= 0) {
	           		 MainFrame.alert("请选择一行！");
	           		 return;
            	}
            	if (MainFrame.prompt("确定删除该笔录吗？")){
                    int i = caseDetailTable.getSelectedRow();
                    String noteId = caseDetailTableModel.getValueAt(i, 0)+"";
                    CaseService caseService = new CaseService();
                    ResultDTO resultDTO = caseService.delNote(Integer.valueOf(noteId));
                    if (CommonConstant.RESULT_CODE_FAIL.equals(resultDTO.getCode())) {
                        MainFrame.alert(resultDTO.getMessage());
                        return;
                    }
                    MainFrame.alert("删除成功");
                    updateNoteTable();
                }
            }
        });
        panel.add(createClockButton);

        /*JButton viewNoteButton = new JButton("详情");
        viewNoteButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
            }
        });
        panel.add(viewNoteButton);*/

        JScrollPane lawScrollPane = new JScrollPane();
        lawScrollPane.setBounds(0, 298, 1000, 155);
        //2018-10-29 确认综合笔录、法律手续、闹钟为一个综合列表
        //add(lawScrollPane);
        Border lawTitleBorder, lawLineBorder;
        lawLineBorder = BorderFactory.createLineBorder(Color.DARK_GRAY);
        lawTitleBorder = BorderFactory.createTitledBorder(lawLineBorder, "关联法律程序", TitledBorder.LEFT,
                TitledBorder.CENTER);
        lawScrollPane.setBorder(lawTitleBorder);
        // createLawTable();
        procedureTableModel = new ProcedureTableModel();
        // 定义法律程序Model 动态给Model赋值
        procedureTableModel.setList(caseId);
        procedureTable = new JTable(procedureTableModel);
        procedureTable.setRowHeight(30);
        head.setPreferredSize(new Dimension(procedureTable.getTableHeader().getWidth(), 30));
        lawScrollPane.setViewportView(procedureTable);

        /*JPanel panel_1 = new JPanel();
        panel_1.setBounds(0, 453, 1000, 40);
        add(panel_1);
        panel_1.setLayout(new FlowLayout(FlowLayout.CENTER, 5, 5));

        JButton createLawButton = new JButton("新建");
        createLawButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
            	ProcedureDialog procedureDialog = ProcedureDialog.getInstance();
            	procedureDialog.setSize(new Dimension(500, 400));
            	procedureDialog.procedureNameField.setText("");
            	procedureDialog.remarkField.setText("");
                GUIUtil.setCenter(procedureDialog);
                procedureDialog.setVisible(true);
            }
        });
        panel_1.add(createLawButton);

        JButton editLawButton = new JButton("修改");
        editLawButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
            	ProcedureDialog procedureDialog = ProcedureDialog.getInstance();
            	procedureDialog.setSize(new Dimension(500, 400));
                GUIUtil.setCenter(procedureDialog);
                int i = procedureTable.getSelectedRow();
                procedureDialog.setCaseId(caseId);
                procedureDialog.setProcedureId(Integer.parseInt(procedureTable.getValueAt(i, 0) + ""));
                procedureDialog.procedureNameField.setText(procedureTable.getValueAt(i, 1) + "");
                procedureDialog.remarkField.setText(procedureTableModel.getValueAt(i, 3) + "");
                procedureDialog.setVisible(true);
            }
        });
        panel_1.add(editLawButton);

        JButton deleteLawButton = new JButton("删除");
        deleteLawButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
            	if (procedureTable.getSelectedRow() <= 0) {
            		 MainFrame.alert("请选择一行！");
            		 return;
            	}
            	if (MainFrame.prompt("确定删除该法律手续吗？")){
                    int i = procedureTable.getSelectedRow();
                    String procedureId = procedureTableModel.getValueAt(i, 0)+"";
                    CaseService caseService = new CaseService();
                    ResultDTO resultDTO = caseService.delProcedure(Integer.valueOf(procedureId));
                    if (CommonConstant.RESULT_CODE_FAIL.equals(resultDTO.getCode())) {
                        MainFrame.alert(resultDTO.getMessage());
                        return;
                    }
                    MainFrame.alert("删除成功");
                    updateProcedureTable();
                }
            }
        });
        panel_1.add(deleteLawButton);*/

        /*JButton viewLawButton = new JButton("详情");
        viewLawButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
            }
        });
        panel_1.add(viewLawButton);*/

    }

    /*
     * public void createNoteTable () { noteTable = new JTable();
     * noteTable.setRowHeight(30); noteTableModel = new DefaultTableModel( new
     * Object[][] { }, new String[] { "序号", "笔录名称", "开始时间", "结束时间", "地点", "文件名",
     * "警员", "备注" }
     * 
     * ){ private static final long serialVersionUID = -4270056429026018083L;
     * 
     * @Override public boolean isCellEditable(int row, int column) { if (column
     * == 0) { return true; } return false; }
     * 
     * }; CaseService caseService = new CaseService(); List<Note> listNote =
     * caseService.selectNoteByCaseId(caseId); for (Note note : listNote) {
     * String id = String.valueOf(note.getId()); String name = note.getName();
     * String startTime = note.getStartTime(); String endTime =
     * note.getEndTime(); String place = note.getPlace(); String fileName =
     * note.getFileName(); String policeList = note.getPoliceList(); String
     * remark = note.getRemark(); String[] row ={id, name,
     * startTime,endTime,place,fileName,policeList,remark};
     * noteTableModel.addRow(row); } noteTable.setModel(noteTableModel);
     * GUIUtil.hideColumn(noteTable, 0); JTableHeader head =
     * noteTable.getTableHeader(); head.setPreferredSize(new
     * Dimension(head.getWidth(), 30));
     * 
     * }
     */

    /*
     * public void createLawTable () { lawTable.setRowHeight(30); lawTableModel
     * = new DefaultTableModel( new Object[][] { }, new String[] { "序号",
     * "法律程序名称", "时间", "备注" }
     * 
     * ){ private static final long serialVersionUID = -4270056429026018083L;
     * 
     * @Override public boolean isCellEditable(int row, int column) { if (column
     * == 0) { return true; } return false; }
     * 
     * }; CaseService caseService = new CaseService(); List<Procedure>
     * procedureList = caseService.selectProceduresByCaseId(caseId); for
     * (Procedure procedure : procedureList) { String id =
     * String.valueOf(procedure.getId()); String name = procedure.getName();
     * String time = procedure.getTime(); String remark = procedure.getRemark();
     * String[] row ={id, name, time,remark}; lawTableModel.addRow(row); }
     * lawTable.setModel(lawTableModel); GUIUtil.hideColumn(lawTable, 0);
     * JTableHeader head = lawTable.getTableHeader(); head.setPreferredSize(new
     * Dimension(head.getWidth(), 30));
     * 
     * }
     */
    public void updateNoteTable() {
        caseDetailTableModel.setList(caseId);
        caseDetailTable.updateUI();
    }

    public void updateProcedureTable() {
        procedureTableModel.setList(caseId);
        procedureTable.updateUI();
    }

	public int getCaseId() {
		return caseId;
	}

	public void setCaseId(int caseId) {
		this.caseId = caseId;
	}
}
