package swing;

import util.GUIUtil;

import java.awt.EventQueue;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import javax.swing.*;

import constant.CommonConstant;
import dto.ResultDTO;
import service.CaseService;
import service.UserService;
import java.awt.Color;

/**
 * 登录窗体
 * @author LiuPF
 * @date 2018-09-11
 */

public class LoginFrame extends BaseFrame {
    private static final long serialVersionUID = -2717090889684605270L;
    private final JTextField jt_name;
    private final JPasswordField jt_pass;
    private JLabel tip;
    
    static {
        GUIUtil.useLNF();
    }

    public static LoginFrame frame;

    public LoginFrame() {
        this.setType(Type.UTILITY);
        this.setAlwaysOnTop(true);
        this.setAutoRequestFocus(false);
        this.setResizable(false);
        this.setIconImage(Toolkit.getDefaultToolkit().createImage("resources/img/person.png"));
        Font font = new Font("宋体", Font.BOLD, 24);

        font = new Font("宋体", Font.PLAIN, 12);
        getContentPane().setLayout(null);
        
        JPanel panel = new JPanel();
        panel.setBounds(0, 0, 364, 254);
        getContentPane().add(panel);
        panel.setLayout(null);
        JLabel jl = new JLabel("欢迎使用案件管理系统", SwingUtilities.CENTER);
        jl.setBounds(22, 5, 316, 55);
        panel.add(jl);
        jl.setFont(new Font("微软雅黑", Font.BOLD, 20));
        jl.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        final JButton jb1 = new JButton("登录");
        jb1.setBounds(67, 15, 70, 23);
        jb1.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent event) {
                /*String username = jt_name.getText();
                String password = new String(jt_pass.getPassword());
                ResultDTO result = new UserService().userLogin(username, password);
                if (CommonConstant.RESULT_CODE_FAIL.equals(result.getCode())) {
                    tip.setText(result.getMessage());
                    return;
                }*/
                StartRun.frame.dispose();
                MainFrame.getInstance().setVisible(true);
            }
        });
        JButton jb2 = new JButton("退出");
        jb2.addActionListener(new ActionListener() {
        	public void actionPerformed(ActionEvent e) {
        	    System.exit(0);
        	}
        });
        jb2.setBounds(185, 15, 70, 23);
        /*jb2.addMouseListener(new MouseAdapter() {
            public void mouseClicked(MouseEvent event) {
                //System.exit(0);
            }
        });*/
        JPanel jp_south = new JPanel();
        jp_south.setBounds(22, 180, 289, 64);
        panel.add(jp_south);
        jp_south.setLayout(null);
        jp_south.add(jb1);
        jp_south.add(jb2);
        jp_south.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        
                JLabel jl_name = new JLabel("用户名：", SwingUtilities.RIGHT);
                jl_name.setBounds(65, 0, 69, 26);
                jl_name.setFont(font);
                
                        JLabel jl_pass = new JLabel("密　码：", SwingUtilities.RIGHT);
                        jl_pass.setBounds(65, 45, 69, 26);
                        jl_pass.setFont(font);
                        
                                JPanel jp_center_left = new JPanel();
                                jp_center_left.setBounds(0, 73, 148, 76);
                                panel.add(jp_center_left);
                                jp_center_left.setLayout(null);
                                jp_center_left.add(jl_name);
                                jp_center_left.add(jl_pass);
                                
                                        jt_name = new JTextField();
                                        jt_name.setBounds(0, 0, 134, 26);
                                        jt_pass = new JPasswordField();
                                        jt_pass.setBounds(0, 44, 134, 26);
                                        jt_pass.setEchoChar('*');
                                        JPanel jp_center_right = new JPanel();
                                        jp_center_right.setBounds(158, 73, 206, 76);
                                        panel.add(jp_center_right);
                                        jp_center_right.setLayout(null);
                                        jp_center_right.add(jt_name);
                                        jp_center_right.add(jt_pass);
                                        
                                        tip = new JLabel();
                                        tip.setForeground(Color.RED);
                                        tip.setBounds(83, 151, 206, 27);
                                        panel.add(tip);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setVisible(true);
        this.setSize(370, 280);
        this.setResizable(false);
        this.setLocationRelativeTo(null);
    }

    /*public static void main(String[] args) {
        EventQueue.invokeLater(new Runnable() {
            public void run() {
                try {
                    frame = new LoginFrame();
                    frame.setVisible(true);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }*/
}
