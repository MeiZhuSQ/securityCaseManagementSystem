package swing;

import java.awt.Cursor;
import java.awt.Image;
import java.awt.Insets;
import java.io.File;

import javax.swing.ImageIcon;
import javax.swing.JButton;

import util.GUIUtil;

public class ImageButton extends JButton {

    private static final long serialVersionUID = -6759584121307989937L;

    public ImageButton(String fileName) {
        ImageIcon icon = new ImageIcon((new File(GUIUtil.imgFolder, fileName)).getAbsolutePath());
        Image scaledInstance = icon.getImage().getScaledInstance(20, 20, Image.SCALE_DEFAULT);
        icon.setImage(scaledInstance);
        setSize(icon.getImage().getWidth(null), icon.getImage().getHeight(null));
        setIcon(icon);
        setMargin(new Insets(0, 0, 0, 0));// 将边框外的上下左右空间设置为0
        setIconTextGap(0);// 将标签中显示的文本和图标之间的间隔量设置为0
        setBorderPainted(false);// 不打印边框
        setBorder(null);// 除去边框
        setText(null);// 除去按钮的默认名称
        //setFocusPainted(false);// 除去焦点的框
        setContentAreaFilled(false);// 除去默认的背景填充
        setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
    }
}