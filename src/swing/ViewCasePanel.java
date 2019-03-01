package swing;

import java.awt.Color;
import java.awt.Dimension;
import javax.swing.BorderFactory;
import javax.swing.DefaultComboBoxModel;
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
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.awt.event.ActionEvent;
import javax.swing.JSlider;
import javax.swing.JComboBox;

public class ViewCasePanel extends JPanel {

    private static final long serialVersionUID = 7334759086622449699L;
    public  JTable caseDetailTable;
    public  CaseDetailTableModel caseDetailTableModel;
    public JTextField caseNameField;
    public JTextField caseTimeField;
    public JTextField caseRemarkField;
    private JTable procedureTable;
    private ProcedureTableModel procedureTableModel;
    private ArrayList<String> btnName = new ArrayList<String>();
    // 记录案子ID
    private int caseId;
    private static ViewCasePanel instance;
    public JTextField searchNametextField;
    public JComboBox<String> searchTypeComboBox;
    
    public static ViewCasePanel getInstance() {
        if (instance == null) {
            instance = new ViewCasePanel();
        }
        return instance;
    }
    
    public ViewCasePanel() {
        setLayout(null);

        JPanel casePanel = new JPanel();
        casePanel.setBounds(15, 15, 1150, 65);
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
        caseRemarkLabel.setBounds(617, 29, 84, 18);
        casePanel.add(caseRemarkLabel);

        caseTimeField = new JTextField();
        caseTimeField.setEditable(false);
        caseTimeField.setBounds(398, 26, 176, 24);
        casePanel.add(caseTimeField);
        caseTimeField.setColumns(10);

        caseRemarkField = new JTextField();
        caseRemarkField.setEditable(false);
        caseRemarkField.setBounds(689, 26, 182, 24);
        casePanel.add(caseRemarkField);
        caseRemarkField.setColumns(10);
        
        caseNameField = new JTextField();
        caseNameField.setEditable(false);
        caseNameField.setColumns(10);
        caseNameField.setBounds(107, 26, 176, 24);
        casePanel.add(caseNameField);
        
        JLabel label = new JLabel("案件名称");
        label.setBounds(35, 29, 84, 18);
        casePanel.add(label);

        JScrollPane noteScrollPane = new JScrollPane();
        noteScrollPane.setBounds(15, 125, 1150, 485);
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
        GUIUtil.hideColumn(caseDetailTable, 0);
        for (int i = 0; i < 8; i++) {
            TableColumn column = caseDetailTable.getColumnModel().getColumn(i);
            if (i == 1) {
                column.setPreferredWidth(50);
                column.setMaxWidth(50);
                column.setMinWidth(50);
            }
            if (i == 2) {
                column.setPreferredWidth(250);
                column.setMaxWidth(250);
                column.setMinWidth(250);
            }
            if (i == 3) {
                column.setPreferredWidth(80);
                column.setMaxWidth(80);
                column.setMinWidth(80);
            }
            if (i == 4 || i == 5) {
                column.setPreferredWidth(150);
                column.setMaxWidth(150);
                column.setMinWidth(150);
            }
            if (i == 7) {
                column.setPreferredWidth(80);
                column.setMaxWidth(80);
                column.setMinWidth(80);
            }
        }
        
        btnName.add("修改");
        btnName.add("删除");
        TableColumn column = caseDetailTable.getColumnModel().getColumn(7);
        column.setCellRenderer(new CaseDetailButtonRenderer());
        column.setCellEditor(new CaseDetailButtonEditor());
        noteScrollPane.setViewportView(caseDetailTable);
        
        JLabel lblNewLabel_1 = new JLabel("New label");
        noteScrollPane.setColumnHeaderView(lblNewLabel_1);

        JPanel panel = new JPanel();
        panel.setBounds(15, 615, 1150, 36);
        add(panel);

        JButton createNoteButton = new JButton("新建笔录");
        createNoteButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent arg0) {
                //NotePanel notePanel = new NotePanel(caseId);
                NotePanel notePanel = NotePanel.getInstance();
                notePanel.setCaseId(caseId);
                //重置noteId
                notePanel.noteId = 0;
                notePanel.newNoteId = 0;
                //给两表格置空
                notePanel.policeTableModel.setList(0);
                notePanel.otherPersonTableModel.setList(0);
                //notePanel.askedPersonTableModel.setList(0);
                //notePanel.otherPersonTableModel.setList(0);
                notePanel.getNoteNameField().setText("");;
                notePanel.getPlaceField().setText("");
                notePanel.startDateTimePiker.clear();
                notePanel.endDateTimePiker.clear();
                notePanel.fileNameField.setText("");
                notePanel.getRemarkTextArea().setText("");
                //被询问人置空
                notePanel.askedNameField.setText("");
                notePanel.askedIdCardField.setText("");
                notePanel.askedSexComboBox.setSelectedIndex(0);
                notePanel.askedTypeGroup.getElements().nextElement().setSelected(true);
                notePanel.askedAdultTypeGroup.getElements().nextElement().setSelected(true);
                notePanel.askedAbleTypeGroup.getElements().nextElement().setSelected(true);
                //List<String> policeList = new ArrayList<>();
                //Object[] defaultValue = new Object[] {};
                /*CaseService caseService = new CaseService();
                List<Police> polices = caseService.listPolice();
                for (Police police : polices) {
                    policeList.add(polices.get(i).getId() + "_" + police.getName());

                }
                Object[] defaultValue = new String[] {"请选择"};
                notePanel.mulit.MulitCombobox_all(policeList.toArray(new String[policeList.size()]), defaultValue);*/
                MainFrame.tabbedPane.addTab("新建笔录", notePanel, null);
                MainFrame.tabbedPane.setSelectedComponent(notePanel);
            }
        });
        panel.add(createNoteButton);

        JButton createProcedureButton = new JButton("新建法律手续");
        createProcedureButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                ProcedureDialog procedureDialog = ProcedureDialog.getInstance();
                procedureDialog.setTitle("新建法律手续");
                procedureDialog.setSize(new Dimension(500, 400));
                procedureDialog.setProcedureId(0);
                procedureDialog.procedureNameField.setText("");
                procedureDialog.remarkField.setText("");
                procedureDialog.dateTimePicker.clear();
                GUIUtil.setCenter(procedureDialog);
                procedureDialog.setVisible(true);
            }
        });
        panel.add(createProcedureButton);

        JButton createClockButton = new JButton("新建闹钟");
        createClockButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                ClockDialog clockDialog = ClockDialog.getInstance();
                clockDialog.setTitle("新建闹钟");
                clockDialog.setSize(new Dimension(500, 400));
                clockDialog.setClockId(0);
                clockDialog.clockNameField.setText("");
                clockDialog.remarkField.setText("");
                clockDialog.dateTimePicker.clear();
                GUIUtil.setCenter(clockDialog);
                clockDialog.setVisible(true);
            }
        });
        panel.add(createClockButton);
        
        JPanel searchPanel = new JPanel();
        searchPanel.setBounds(25, 85, 661, 30);
        add(searchPanel);
        searchPanel.setLayout(null);
        
        searchNametextField = new JTextField();
        searchNametextField.setBounds(99, 2, 176, 24);
        searchPanel.add(searchNametextField);
        
        JLabel label_1 = new JLabel("按名称搜索");
        label_1.setBounds(14, 5, 104, 18);
        searchPanel.add(label_1);
        
        searchTypeComboBox = new JComboBox<String>();
        searchTypeComboBox.setModel(new DefaultComboBoxModel<String>(new String[] {"全部", "笔录", "法律手续", "闹钟"}));
        searchTypeComboBox.setBounds(403, 2, 163, 24);
        searchPanel.add(searchTypeComboBox);
        
        JLabel lblNewLabel = new JLabel("按类型搜索");
        lblNewLabel.setBounds(315, 5, 96, 18);
        searchPanel.add(lblNewLabel);

        ImageButton searchButton = new ImageButton("search.png","案件搜索");
        searchButton.setLocation(580, 5);
        searchButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                updateCaseDetailTable();
            }
        });
        searchPanel.add(searchButton);
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
    public void updateCaseDetailTable() {
        String searchName = searchNametextField.getText();
        int selectedIndex = searchTypeComboBox.getSelectedIndex();
        caseDetailTableModel.setList(caseId, searchName, String.valueOf(selectedIndex));
        caseDetailTable.updateUI();
        caseDetailTable.setRowHeight(30);
        JTableHeader head = caseDetailTable.getTableHeader();
        head.setPreferredSize(new Dimension(head.getWidth(), 30));
    }

    /*public void updateProcedureTable() {
        procedureTableModel.setList(caseId);
        procedureTable.updateUI();
    }*/

	public int getCaseId() {
		return caseId;
	}

	public void setCaseId(int caseId) {
		this.caseId = caseId;
	}
}
