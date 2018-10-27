package swing;

import java.awt.Dimension;
import java.awt.Insets;
import java.awt.Rectangle;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;

public class FullScreenTest2 {
	public static void main(String[] args) {
		final JFrame f = new JFrame("FullScreenTest");
		final JButton btn = new JButton("FullScreen");
		f.add(btn);
		f.setVisible(true);
		btn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (btn.getText().equals("FullScreen")) {
					f.dispose();
					f.setUndecorated(true);

					Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
					Rectangle bounds = new Rectangle(screenSize);
					Insets insets = Toolkit.getDefaultToolkit().getScreenInsets(f.getGraphicsConfiguration());
					bounds.x += insets.left;
					bounds.y += insets.top;
					bounds.width -= insets.left + insets.right;
					bounds.height -= insets.top + insets.bottom;

					f.setBounds(bounds);
					f.setVisible(true);
					btn.setText("NormalMode");
				} else {
					f.dispose();
					f.setUndecorated(false);
					f.pack();
					f.setLocationRelativeTo(null);
					f.setVisible(true);
					btn.setText("FullScreen");
				}
			}
		});
	}
}