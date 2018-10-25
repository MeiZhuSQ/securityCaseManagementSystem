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
import javax.swing.event.MenuListener;
import javax.swing.event.MenuEvent;
import javax.swing.SwingConstants;

/**
 * 主窗体
 * @author LiuPF
 * @date 2018-10-21
 */
public class MainFrame extends BaseFrame {

    private static final long serialVersionUID = 9154006143553537232L;
    public static JFrame frame;
    public static ClosableTabbedPane tabbedPane;
    public CaseTableModel caseTableModel;
    public JTable caseTable;
    private TableColumn column;
    private JList<String> clockList;
    private DefaultListModel clockModel;
    
    Toolkit toolkit = Toolkit.getDefaultToolkit();
    private int DEFAULE_WIDTH = 1000;
    private int DEFAULE_HEIGH = 600;
    private ArrayList<String> btnName = new ArrayList<String>();
    int Location_x = (int) (toolkit.getScreenSize().getWidth() - DEFAULE_WIDTH) / 2;
    int Location_y = (int) (toolkit.getScreenSize().getHeight() - DEFAULE_HEIGH) / 2;
    
    private static MainFrame instance;
    
    static {
        GUIUtil.useLNF();
    }
    
    public static MainFrame getInstance() {
        if(instance == null) {
            instance = new MainFrame();
        }
        return instance;
    } 
    
    public static void main(String[] args) {
        EventQueue.invokeLater(new Runnable() {
            public void run() {
                try {
                    frame = MainFrame.getInstance();
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
        this.setTitle("案件管理系统 V1.0");
        this.setBounds(100, 100, 1024, 700);
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
        caseTableModel = new CaseTableModel();
        caseTable = new JTable(caseTableModel);
        caseTable.setRowHeight(30);
        JTableHeader head = caseTable.getTableHeader();
        head.setPreferredSize(new Dimension(head.getWidth(), 30));
        // 以下设置表格列宽
        caseTable.setAutoResizeMode(JTable.AUTO_RESIZE_SUBSEQUENT_COLUMNS);
        for (int i = 0; i < 5; i++) {
            column = caseTable.getColumnModel().getColumn(i);
            if (i == 0) {
                column.setPreferredWidth(50);
                column.setMaxWidth(50);
                column.setMinWidth(50);
            }
            if (i == 4) {
                column.setPreferredWidth(100);
                column.setMaxWidth(100);
                column.setMinWidth(100);
            }
        }
        btnName.add("详情");
        btnName.add("修改");
        btnName.add("删除");
        TableColumn column = caseTable.getColumnModel().getColumn(4);
        column.setCellRenderer(new MyButtonRenderer());
        column.setCellEditor(new MyButtonEditor());
        
        //initMainTable();
        caseScrollPane.setViewportView(caseTable);
        
        JScrollPane clockScrollPane = new JScrollPane();
        splitPane.setRightComponent(clockScrollPane);
        
        initClock();
        
        clockScrollPane.setViewportView(clockList);
        
        JMenuBar menuBar = new JMenuBar();
        this.setJMenuBar(menuBar);

        JMenu createMenu = new JMenu("新建");
        menuBar.add(createMenu);

        JMenuItem caseMenuItem = new JMenuItem("新建案件");
        caseMenuItem.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                CaseDialog caseDialog = CaseDialog.getInstance();
                caseDialog.setSize(new Dimension(500, 400));
                GUIUtil.setCenter(caseDialog);
                // MainFrame.frame.setEnabled(false);
                caseDialog.setVisible(true);
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
        policeMenu.addMenuListener(new MenuListener() {
            public void menuSelected(MenuEvent e) {
                //注意：对（一个）单例容器对象进行操作 liupf 2018-10-17 19:00
                JPanel policePanel = PolicePanel.getInstance();
                tabbedPane.addTab("警员维护", policePanel, null);
                tabbedPane.setSelectedComponent(policePanel);
            }

            @Override
            public void menuDeselected(MenuEvent e) {
                
            }

            @Override
            public void menuCanceled(MenuEvent e) {
                
            }
        });
        menuBar.add(policeMenu);
        
        JMenu existMenu = new JMenu("退出");
        existMenu.addMenuListener(new MenuListener() {
            public void menuSelected(MenuEvent e) {
                System.exit(0);
            }

            @Override
            public void menuDeselected(MenuEvent e) {
            }

            @Override
            public void menuCanceled(MenuEvent e) {
            }
        });
        menuBar.add(existMenu);
        
        JLabel lblNewLabel = new JLabel("                                                          "
        		+ "                                                               ");
        lblNewLabel.setHorizontalAlignment(SwingConstants.RIGHT);
        menuBar.add(lblNewLabel);
        
        JLabel lblNewLabel_1 = new JLabel("如有问题，请联系管理员！");
        menuBar.add(lblNewLabel_1);
        this.getContentPane().add(tabbedPane, BorderLayout.CENTER);
        this.setVisible(true);
        splitPane.setDividerLocation(0.8);
    }

    //弃用DefaultTableModel方式
    /*public void initMainTable() {
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

        caseTable.setAutoResizeMode(JTable.AUTO_RESIZE_SUBSEQUENT_COLUMNS);// 以下设置表格列宽
        for (int i = 0; i < 4; i++) {
            column = table.getColumnModel().getColumn(i);
            if (i == 0) {
                column.setPreferredWidth(50);
            }
        }
        btnName.add("详情");
        btnName.add("修改");
        btnName.add("删除");
        TableColumn column = caseTable.getColumnModel().getColumn(3);
        column.setCellRenderer(new MyButtonRenderer());
        column.setCellEditor(new MyButtonEditor());
    }*/
    
    public void updateCaseTable() {
        caseTableModel.list = new CaseService().listCase();
        //或 policeTableModel.fireTableDataChanged();
        caseTable.updateUI();
    }
    
    public ClockListModel<String> clockListModel;
    
    public void initClock() {
        clockListModel = new ClockListModel<>();
        clockListModel.setValue();
        clockList = new JList<String>(clockListModel);
        clockList.setOpaque(false);
        clockList.setBorder(null);
        JLabel label = (JLabel) clockList.getCellRenderer();
        label.setOpaque(false);
        clockList.setForeground(Color.darkGray);
        clockList.setSelectionForeground(new Color(40, 101, 156));
        clockList.setFixedCellHeight(25);
    }
    
    

}
