package gui.frame;

import util.GUIUtil;

import java.awt.BorderLayout;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.*;

/**
 * 登录窗体
 * @date 2018-09-11
 */

public class LoginFrame extends JFrame {
    private static final long serialVersionUID = -2717090889684605270L;

    static {
        GUIUtil.useLNF();
    }

    public static LoginFrame frame = new LoginFrame();;

    private LoginFrame() {
        this.setType(Type.UTILITY);
        this.setAlwaysOnTop(true);
        this.setAutoRequestFocus(false);
        this.setResizable(false);
        this.setIconImage(Toolkit.getDefaultToolkit().createImage("D:/download/tipEg.png"));
        JLabel jl = new JLabel("欢迎使用案件管理系统", SwingUtilities.CENTER);
        Font font = new Font("宋体", Font.BOLD, 24);
        jl.setFont(font);
        jl.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        this.getContentPane().add(jl, BorderLayout.NORTH);

        font = new Font("宋体", Font.PLAIN, 12);

        JLabel jl_name = new JLabel("用户名：", SwingUtilities.RIGHT);
        jl_name.setFont(font);

        JLabel jl_pass = new JLabel("密　码：", SwingUtilities.RIGHT);
        jl_pass.setFont(font);

        JPanel jp_center_left = new JPanel();
        jp_center_left.setLayout(new GridLayout(5, 1));
        jp_center_left.add(jl_name);
        jp_center_left.add(jl_pass);

        final JTextField jt_name = new JTextField();
        final JPasswordField jt_pass = new JPasswordField();
        jt_pass.setEchoChar('*');
        JPanel jp_center_right = new JPanel();
        jp_center_right.setLayout(new GridLayout(5, 1));
        jp_center_right.add(jt_name);
        jp_center_right.add(jt_pass);

        JPanel jp_center = new JPanel();
        jp_center.setLayout(new GridLayout(1, 2));
        jp_center.setBorder(BorderFactory.createEmptyBorder(0, 0, 0, 60));
        jp_center.add(jp_center_left);
        jp_center.add(jp_center_right);
        final JButton jb1 = new JButton("确认");
        jb1.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent event) {
                new MainFrame().setVisible(true);
            }
        });
        JButton jb2 = new JButton("退出");
        jb2.addMouseListener(new MouseAdapter() {
            public void mouseClicked(MouseEvent event) {
                System.exit(0);
            }
        });
        JPanel jp_south = new JPanel();
        jp_south.add(jb1);
        jp_south.add(jb2);
        jp_south.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        this.getContentPane().add(jp_center);
        this.getContentPane().add(jp_south, BorderLayout.SOUTH);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setVisible(true);
        this.setSize(370, 280);
        this.setResizable(false);
        this.setLocationRelativeTo(null);
    }

    public static void main(String[] args) {
        /*try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (ClassNotFoundException | InstantiationException | IllegalAccessException
                | UnsupportedLookAndFeelException e) {
            e.printStackTrace();
        }
        SwingUtilities.updateComponentTreeUI(frame);*/
    }
}
