package swing;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import javax.swing.AbstractCellEditor;
import javax.swing.JButton;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTable;
import javax.swing.table.TableCellEditor;

public class MyButtonEditor extends AbstractCellEditor implements
		TableCellEditor
{

	/**
	 * serialVersionUID
	 */
	private static final long serialVersionUID = -6546334664166791132L;

	private JPanel panel;

	private JButton button;
	
	private JButton button1;

	private ArrayList<String> btnName;

	public MyButtonEditor()
	{

		initButton();

		initPanel();

		panel.add(button);
		panel.add(button1);
	}

	private void initButton()
	{

		button = new JButton();
		button.setToolTipText("666666666");
		button1 = new JButton();
		  button.setSize(new Dimension(50, 25));
	        button1.setSize(new Dimension(50, 25));
		button.addActionListener(new ActionListener()
		{

			public void actionPerformed(ActionEvent e)
			{

				int res = JOptionPane.showConfirmDialog(null,
						"我是"+button.getName(), "choose one",
						JOptionPane.YES_NO_OPTION);

				if (res == JOptionPane.YES_OPTION)
				{
					//num++;
				}
				// stopped!!!!
				fireEditingStopped();

			}
		});
		button1.addActionListener(new ActionListener()
		{

			public void actionPerformed(ActionEvent e)
			{

				int res = JOptionPane.showConfirmDialog(null,
						"我是李四", "choose one",
						JOptionPane.YES_NO_OPTION);

				if (res == JOptionPane.YES_OPTION)
				{
					//num++;
				}
				// stopped!!!!
				fireEditingStopped();

			}
		});

	}

	private void initPanel()
	{

		panel = new JPanel();

		panel.setLayout(new FlowLayout());
	}

	@Override
	public Component getTableCellEditorComponent(JTable table, Object value,
			boolean isSelected, int row, int column)
	{

		btnName = (ArrayList<String>) value;
        
        button.setText(value == null ? "" : btnName.get(0));
        button1.setText(value == null ? "" : btnName.get(1));
        
        return panel;
	}

	@Override
	public Object getCellEditorValue()
	{

		return btnName;
	}

}