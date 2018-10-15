package swing;

import java.awt.BorderLayout;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.HashMap;
import java.util.Map;

import javax.swing.ButtonGroup;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
/*
 * source code from 《java核心技术 卷1 基础知识》 P329
 */
@SuppressWarnings("serial")
public class JRadioButtonDemo extends JFrame {
  int DEFAULT_WIDTH = 600;
  int DEFAULT_HEIGHT = 400;
  private JLabel label;
  private JPanel buttonPanel;
  private ButtonGroup group;
  private static final int DEFAULT_SIZE = 12;
  private Map actionCommandSizeMap = new HashMap();
  // 二维数组存储按钮属性，第一维是按钮名称，第二维是字体大小
  private String[][] buttonAttributes = {
      { "Small", "Medium", "Large", "Extra" }, { "8", "12", "18", "36" } };
  public JRadioButtonDemo() {
    setTitle("JRadioButtonDemo - www.jb51.net");
    setSize(DEFAULT_WIDTH, DEFAULT_HEIGHT);
    // 添加label
    label = new JLabel("欢迎访问脚本之家 www.jb51.net");
    label.setFont(new Font("Serif", Font.PLAIN, DEFAULT_SIZE));
    add(label, BorderLayout.CENTER);
    // 添加buttonPanel,它包含4个radioButton
    buttonPanel = new JPanel();
    group = new ButtonGroup();
    add(buttonPanel, BorderLayout.SOUTH);
    // 添加radioButton
    for (int i = 0; i < buttonAttributes[0].length; i++) {
      addRadioButton(buttonAttributes[0][i],
          Integer.parseInt(buttonAttributes[1][i]));
      // 将按钮名称和字体大小添加为对应表，名称等同于actionCommand
      actionCommandSizeMap.put(buttonAttributes[0][i],
          Integer.parseInt(buttonAttributes[1][i]));
    }
  }
  public void addRadioButton(String name, final int size) {
    boolean selected = size == DEFAULT_SIZE;
    JRadioButton button = new JRadioButton(name, selected);
    button.setActionCommand(name);// 设置name即为actionCommand
    group.add(button);
    buttonPanel.add(button);
    // 构造一个监听器，响应checkBox事件
    ActionListener actionListener = new ActionListener() {
      public void actionPerformed(ActionEvent e) {
        // 1.通过eActionCommand
        String eActionCommand = e.getActionCommand();
        System.out.printf("e.getActionCommand() is %s\n",
            eActionCommand);
        // 2.通过getSource()
        Object sourceObject = e.getSource();
        if (sourceObject instanceof JRadioButton) {
          JRadioButton sourceButton = (JRadioButton) sourceObject;
          System.out.printf("selected JRadioButton is %s\n",
              sourceButton.getText());
        }
        // 3.通过groupSelectionActionCommand
        String groupSelectionActionCommand = group.getSelection()
            .getActionCommand();
        System.out.printf("groupSelectionActionCommand is %s\n",
            groupSelectionActionCommand);
        label.setFont(new Font("Serif", Font.PLAIN,
            (int) actionCommandSizeMap.get(groupSelectionActionCommand)));
      }
    };
    button.addActionListener(actionListener);
  }
  public static void main(String[] args) {
    // TODO Auto-generated method stub
    // 创建窗体并指定标题
    JRadioButtonDemo frame = new JRadioButtonDemo();
    // 关闭窗体后退出程序
    frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    // 自动适配所有控件大小
    // frame.pack();
    // 设置窗体位置在屏幕中央
    frame.setLocationRelativeTo(null);
    // 显示窗体
    frame.setVisible(true);
  }
}