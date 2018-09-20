package gui.frame;

import java.awt.EventQueue;
import java.awt.Image;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.GroupLayout;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.GroupLayout.Alignment;
import javax.swing.JTabbedPane;
import javax.swing.JPanel;
import java.awt.BorderLayout;
import java.awt.Dimension;

import javax.swing.JMenuBar;
import javax.swing.JMenu;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import javax.swing.JMenuItem;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import javax.swing.JTable;
import javax.swing.JSplitPane;
import java.awt.FlowLayout;
import javax.swing.JSpinner;
import javax.swing.JSeparator;

public class MainFrame extends BaseFrame {

    private static final long serialVersionUID = 9154006143553537232L;
    private JFrame frame;

    private ClosableTabbedPane tabbedPane;

    public static void main(String[] args) {
        EventQueue.invokeLater(new Runnable() {
            public void run() {
                try {
                    MainFrame window = new MainFrame();
                    window.frame.setVisible(true);
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
        frame = new JFrame();
        frame.setTitle("案件管理系统");
        frame.setBounds(100, 100, 565, 397);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLocationRelativeTo(null);
        frame.getContentPane().setLayout(new BorderLayout(0, 0));
        
        tabbedPane = new ClosableTabbedPane();

        frame.getContentPane().add(tabbedPane, BorderLayout.CENTER);
        
        JSplitPane splitPane = new JSplitPane();
        tabbedPane.addTab("主页", null, splitPane, null);
        JPanel panel = new JPanel();
        splitPane.setLeftComponent(panel);
        
        JPanel panel_1 = new JPanel();
        splitPane.setRightComponent(panel_1);
        splitPane.setDividerLocation(0.5);
        
        JMenuBar menuBar = new JMenuBar();
        frame.setJMenuBar(menuBar);

        JMenu createMenu = new JMenu("新建");
        menuBar.add(createMenu);
        JMenu saveMenu = new JMenu("保存");
        menuBar.add(saveMenu);

        JMenuItem caseMenuItem = new JMenuItem("新建案件");
        caseMenuItem.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                JPanel createPanel = new JPanel();
                tabbedPane.addTab("新建案件", createPanel, null);
            }
        });
        createMenu.add(caseMenuItem);

        JMenuItem lawsMenuItem = new JMenuItem("新建法规");
        lawsMenuItem.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                JPanel createPanel = new JPanel();
                tabbedPane.addTab("新建法规", createPanel, null);
            }
        });
        createMenu.add(lawsMenuItem);

    }

    public void addTable() {
        Object[][] playerInfo = {
                { "明天10:00上街" }, { "明天14:00交报告" }};
        String[] Names = { "XX" };
    }
}
