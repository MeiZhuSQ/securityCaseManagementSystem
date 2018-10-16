package swing;

import java.awt.EventQueue;
import java.awt.GridLayout;
import java.awt.Toolkit;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.JTableHeader;
import javax.swing.table.TableCellEditor;
import javax.swing.table.TableCellRenderer;
import javax.swing.table.TableColumn;

import entity.LegalCase;
import service.CaseService;
import util.GUIUtil;

import javax.swing.JPanel;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;

import javax.swing.JMenuBar;
import javax.swing.JMenu;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;

import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;
import java.awt.event.ActionEvent;
import javax.swing.JTable;
import javax.swing.JSplitPane;
import javax.swing.JScrollPane;
import javax.swing.JList;
import javax.swing.DefaultListModel;
import javax.swing.DefaultListSelectionModel;
import javax.swing.JButton;
import javax.swing.AbstractCellEditor;
import javax.swing.AbstractListModel;

public class MainFrame extends BaseFrame {

    private static final long serialVersionUID = 9154006143553537232L;
    private static JFrame frame;
    public static ClosableTabbedPane tabbedPane;
    public static DefaultTableModel caseTableModel;
    public static JTable caseTable;
    private TableColumn column;
    private JList<Object> clockList;
    private DefaultListModel clockModel;
    
    Toolkit toolkit = Toolkit.getDefaultToolkit();
    private int DEFAULE_WIDTH = 1000;
    private int DEFAULE_HEIGH = 600;
    private ArrayList<String> btnName = new ArrayList<String>();
    int Location_x = (int) (toolkit.getScreenSize().getWidth() - DEFAULE_WIDTH) / 2;
    int Location_y = (int) (toolkit.getScreenSize().getHeight() - DEFAULE_HEIGH) / 2;
    static {
        GUIUtil.useLNF();
    }
    public static void main(String[] args) {
        EventQueue.invokeLater(new Runnable() {
            public void run() {
                try {
                    frame = new MainFrame();
                    frame.setVisible(true);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }

    public MainFrame() {
        initialize();
    }

    private void initialize() {
        this.setTitle("案件管理系统");
        this.setBounds(100, 100, 1024, 600);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.getContentPane().setBackground(Color.white);
        this.setLocationRelativeTo(null);
        this.getContentPane().setLayout(new BorderLayout(0, 0));
        tabbedPane = new ClosableTabbedPane();
        
        JSplitPane splitPane = new JSplitPane();
        splitPane.setDividerSize(3);
        tabbedPane.addTab("主页", null, splitPane, null);
        
        JScrollPane caseScrollPane = new JScrollPane();
        splitPane.setLeftComponent(caseScrollPane);
        
        initMainTable();
        caseScrollPane.setViewportView(caseTable);
        
        JScrollPane clockScrollPane = new JScrollPane();
        splitPane.setRightComponent(clockScrollPane);
        
        initClock();
        
        clockScrollPane.setViewportView(clockList);
        
        JMenuBar menuBar = new JMenuBar();
        this.setJMenuBar(menuBar);

        JMenu createMenu = new JMenu("新建");
        menuBar.add(createMenu);
        JMenu saveMenu = new JMenu("保存");
        menuBar.add(saveMenu);

        JMenuItem caseMenuItem = new JMenuItem("新建案件");
        caseMenuItem.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                JPanel legalCasePanel = new LegalCasePanel();
                tabbedPane.addTab("新建案件", legalCasePanel, null);
                tabbedPane.setSelectedComponent(legalCasePanel);
            }
        });
        createMenu.add(caseMenuItem);

        JMenuItem lawsMenuItem = new JMenuItem("新建闹钟");
        lawsMenuItem.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                JPanel createPanel = new JPanel();
                tabbedPane.addTab("新建闹钟", createPanel, null);
                tabbedPane.setSelectedComponent(createPanel);
            }
        });
        createMenu.add(lawsMenuItem);
        
        JMenu policeMenu = new JMenu("警员维护");
        policeMenu.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                JPanel policePanel = new PolicePanel();
                tabbedPane.addTab("警员维护", policePanel, null);
                tabbedPane.setSelectedComponent(policePanel);
            }
        });
        menuBar.add(policeMenu);
        
        JMenu existMenu = new JMenu("退出");
        menuBar.add(existMenu);
        this.getContentPane().add(tabbedPane, BorderLayout.CENTER);
        this.setVisible(true);
        splitPane.setDividerLocation(0.8);
    }

    public void initMainTable() {
        caseTable = new JTable();
        caseTable.setRowHeight(30);
        caseTableModel = new DefaultTableModel(
                new Object[][] {
                },
                new String[] {
                    "序号", "案件名称", "时间", "操作"
                }
            ){
            private static final long serialVersionUID = 446802752841104386L;

            @Override
            public boolean isCellEditable(int row, int column) {
                if (column == 0 || column == 3) {
                    return true;
                }
                return false;
            }
        
        };
        CaseService caseService = new CaseService();
        List<LegalCase> listCases = caseService.listCase();
        for (LegalCase legalCase : listCases) {
            String id = String.valueOf(legalCase.getId());
            String name = legalCase.getName();
            String time = legalCase.getTime();
            Object[] row ={id, name, time,btnName};
            caseTableModel.addRow(row);
        }
        caseTable.setModel(caseTableModel);
        GUIUtil.hideColumn(caseTable, 0);
        JTableHeader head = caseTable.getTableHeader(); // 创建表格标题对象
        head.setPreferredSize(new Dimension(head.getWidth(), 30));// 设置表头大小
        //head.setFont(new Font("楷体", Font.PLAIN, 18));// 设置表格字体
        //table.setRowHeight(18);// 设置表格行宽

        /*caseTable.setAutoResizeMode(JTable.AUTO_RESIZE_SUBSEQUENT_COLUMNS);// 以下设置表格列宽
        for (int i = 0; i < 4; i++) {
            column = table.getColumnModel().getColumn(i);
            if (i == 0) {
                column.setPreferredWidth(50);
            }
        }*/
        btnName.add("详情");
        btnName.add("修改");
        btnName.add("删除");
        TableColumn column = caseTable.getColumnModel().getColumn(3);
        column.setCellRenderer(new MyButtonRenderer());
        column.setCellEditor(new MyButtonEditor());
    }
    
    @SuppressWarnings("unchecked")
    public void initClock() {
        clockList = new JList<Object>();
        clockModel = new DefaultListModel<>();
        clockList.setModel(new AbstractListModel() {
            String[] values = new String[] {"早上干活了 2018-09-20 ", "起来嗨 2018-09-30"};
            public int getSize() {
                return values.length;
            }
            public Object getElementAt(int index) {
                //values = new String[] {"3", "4"};
                return values[index];
            }
        });
        clockList.setOpaque(false);
        clockList.setBorder(null);
        JLabel label = (JLabel) clockList.getCellRenderer();
        label.setOpaque(false);
        clockList.setForeground(Color.darkGray);
        clockList.setSelectionForeground(new Color(40, 101, 156));
        clockList.setFixedCellHeight(25);
    }
}
