package swing;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.EventQueue;
import java.awt.Insets;
import java.awt.Rectangle;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;
import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSplitPane;
import javax.swing.JTable;
import javax.swing.table.JTableHeader;
import javax.swing.table.TableColumn;

import constant.CommonConstant;
import dto.ResultDTO;
import entity.Clock;
import service.CaseService;
import util.DateUtil;
import util.GUIUtil;

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
    public JList<Clock> clockList;
    private DefaultListModel clockModel;
    public DefaultListModel clockListModel;
    public JFrame f;
    
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
    
    public MainFrame() {
        initialize();
    }

    private void initialize() {
        this.setTitle("案件管理系统 V1.0");
        this.setBounds(100, 100, 1200, 750);
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
        for (int i = 0; i < 6; i++) {
            column = caseTable.getColumnModel().getColumn(i);
            if (i == 0) {
                GUIUtil.hideColumn(caseTable, 0);
            }
            if (i == 5) {
                column.setPreferredWidth(100);
                column.setMaxWidth(100);
                column.setMinWidth(100);
            }
        }
        btnName.add("详情");
        btnName.add("修改");
        btnName.add("删除");
        TableColumn column = caseTable.getColumnModel().getColumn(5);
        column.setCellRenderer(new CaseButtonRenderer());
        column.setCellEditor(new CaseButtonEditor());
        
        //initMainTable();
        caseScrollPane.setViewportView(caseTable);
        
        JPanel panel = new JPanel();
        splitPane.setRightComponent(panel);
        panel.setLayout(null);
        
        JScrollPane clockScrollPane = new JScrollPane();
        clockScrollPane.setBounds(0, 0, 544, 531);
        panel.add(clockScrollPane);
        
        clockListModel = new DefaultListModel();
        List<Clock> clocks = new CaseService().getClocks();
        for (Clock clock : clocks) {
            clockListModel.addElement(clock);
        }
        clockList = new JList(clockListModel);
        clockList.setOpaque(false);
        clockList.setBorder(null);
        JLabel label = (JLabel) clockList.getCellRenderer();
        label.setOpaque(false);
        clockList.setForeground(Color.darkGray);
        clockList.setSelectionForeground(new Color(40, 101, 156));
        clockList.setFixedCellHeight(40);
        
        clockScrollPane.setViewportView(clockList);
        
        JButton clockAddButton = new ImageButton("clock_add.png");
        clockAddButton.addActionListener(new ActionListener() {
        	public void actionPerformed(ActionEvent arg0) {
        		ClockDialog clockDialog = ClockDialog.getInstance();
            	clockDialog.setSize(new Dimension(500, 400));
                clockDialog.clockNameField.setText("");
                clockDialog.remarkField.setText("");
                GUIUtil.setCenter(clockDialog);
                clockDialog.setVisible(true);
        	}
        });
        clockAddButton.setBounds(25, 544, 30, 30);
        panel.add(clockAddButton);
        
        JButton clockEditButton = new ImageButton("clock_edit.png");
        clockEditButton.addActionListener(new ActionListener() {
        	public void actionPerformed(ActionEvent e) {
        		ClockDialog clockDialog = ClockDialog.getInstance();
        		clockDialog.setSize(new Dimension(500, 400));
                GUIUtil.setCenter(clockDialog);
                Clock selectedValue = clockList.getSelectedValue();
                clockDialog.setClockId(selectedValue.getId());
                clockDialog.clockNameField.setText(selectedValue.getName());
                clockDialog.remarkField.setText(selectedValue.getRemark());
                clockDialog.setVisible(true);
        	}
        });
        clockEditButton.setBounds(70, 544, 30, 30);
        panel.add(clockEditButton);
        
        JButton clockDeleteButton = new ImageButton("clock_delete.png");
        clockDeleteButton.addActionListener(new ActionListener() {
        	public void actionPerformed(ActionEvent e) {
        		if (MainFrame.prompt("确定删除该闹钟吗？")){
        			Clock selectedValue = clockList.getSelectedValue();
                    CaseService caseService = new CaseService();
                    ResultDTO resultDTO = caseService.delClock(selectedValue.getId());
                    if (CommonConstant.RESULT_CODE_FAIL.equals(resultDTO.getCode())) {
                        MainFrame.alert(resultDTO.getMessage());
                        return;
                    }
                    MainFrame.alert("删除成功");
                    clockListModel.removeAllElements();
                    List<Clock> clocks = new CaseService().getClocks();
                    for (Clock clock : clocks) {
                    	clockListModel.addElement(clock);
                    }
                }
        	}
        });
        clockDeleteButton.setBounds(115, 544, 30, 30);
        panel.add(clockDeleteButton);
        
        JMenuBar menuBar = new JMenuBar();
        this.setJMenuBar(menuBar);

        JMenu createMenu = new JMenu("新建");
        createMenu.setPreferredSize(new Dimension(40, 20));
        menuBar.add(createMenu);

        JMenuItem caseMenuItem = new JMenuItem("新建案件");
        caseMenuItem.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                CaseDialog caseDialog = CaseDialog.getInstance();
                caseDialog.setSize(new Dimension(500, 400));
                caseDialog.caseNameField.setText("");
                caseDialog.remarkField.setText("");
                GUIUtil.setCenter(caseDialog);
                caseDialog.setVisible(true);
            }
        });
        createMenu.add(caseMenuItem);

        JMenuItem lawsMenuItem = new JMenuItem("新建闹钟");
        lawsMenuItem.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
            	ClockDialog clockDialog = ClockDialog.getInstance();
            	clockDialog.setSize(new Dimension(500, 400));
                GUIUtil.setCenter(clockDialog);
                clockDialog.setVisible(true);
            }
        });
        createMenu.add(lawsMenuItem);
        
        /*JMenu policeMenu = new JMenu("警员维护");
        policeMenu.setPreferredSize(new Dimension(60, 20));
        policeMenu.addMouseListener(new MouseAdapter() {
        	@Override
        	public void mouseClicked(MouseEvent arg0) {
        		//注意：对（一个）单例容器对象进行操作 liupf 2018-10-17 19:00
                JPanel policePanel = PolicePanel.getInstance();
                tabbedPane.addTab("警员维护", policePanel, null);
                tabbedPane.setSelectedComponent(policePanel);
        	}
        });
        menuBar.add(policeMenu);*/
        
        JMenu backupMenu = new JMenu("备份");
        backupMenu.setPreferredSize(new Dimension(40, 20));
        backupMenu.addMouseListener(new BackupListener());
        menuBar.add(backupMenu);
        
        JMenu recoverMenu = new JMenu("恢复");
        recoverMenu.setPreferredSize(new Dimension(40, 20));
        recoverMenu.addMouseListener(new RecoverListener());
        menuBar.add(recoverMenu);
        
        JMenu existMenu = new JMenu("退出");
        existMenu.setPreferredSize(new Dimension(40, 20));
        existMenu.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                System.exit(0);
            }
        });
        menuBar.add(existMenu);
        
        /*JLabel lblNewLabel_1 = new JLabel("如有问题，请联系管理员！");
        lblNewLabel_1.setPreferredSize(new Dimension(500, 20));
        menuBar.add(lblNewLabel_1);*/
        this.getContentPane().add(tabbedPane, BorderLayout.CENTER);
        this.setVisible(true);
        splitPane.setDividerLocation(0.8);
        //定时提醒
        f = new JFrame("闹钟提示");
        setLayout(new BorderLayout());
        Timer timer=new Timer();
	    timer.schedule(new TimerTask(){
	        @Override
	        public void run() {
	            List<Clock> clockList = new CaseService().getClocks();
	            for (Clock clock : clockList) {
					if (clock.getTime().equals(DateUtil.getTime())) {
						f.setUndecorated(true);
			        	JButton j = new JButton();
			        	j.setSize(new Dimension(100, 100));
			        	j.setText("8888888");
			        	f.add(j, BorderLayout.CENTER);
			        	Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
						Rectangle bounds = new Rectangle(screenSize);
						Insets insets = Toolkit.getDefaultToolkit().getScreenInsets(f.getGraphicsConfiguration());
						bounds.x += insets.left;
						bounds.y += insets.top;
						bounds.width -= insets.left + insets.right;
						bounds.height -= insets.top + insets.bottom;
						f.setBounds(bounds);
			        	f.setVisible(true);
			        	File file = new File("resources/audio/1073.wav");

						try {
							AudioInputStream audioIn = AudioSystem.getAudioInputStream(file);
							Clip clip = AudioSystem.getClip();
							clip.open(audioIn);
							clip.start();
						} catch (LineUnavailableException | IOException | UnsupportedAudioFileException e) {
							e.printStackTrace();
						}
					}
				}
	        }
	    },0,1000);
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
}
