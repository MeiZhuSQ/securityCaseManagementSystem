package swing;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.DefaultComboBoxModel;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JTextField;
import javax.swing.table.DefaultTableModel;

import constant.CommonConstant;
import dto.ResultDTO;
import entity.Police;
import service.CaseService;

public class PoliceDialog extends JDialog{
    
    private static final long serialVersionUID = 4805081264547840359L;
    public JTextField policeNameField;
    public JComboBox<String> policeSexField;
    public JTextField policeCodeField;
    private int policeId = 0;
    private static PoliceDialog instance;
    
    public static PoliceDialog getInstance () {
        if (instance == null) {
            instance = new PoliceDialog();
        }
        return instance;
    }
    
    public PoliceDialog() {
        getContentPane().setLayout(null);
        
        JLabel lblNewLabel = new JLabel("姓名");
        lblNewLabel.setBounds(28, 38, 72, 18);
        getContentPane().add(lblNewLabel);
        
        policeNameField = new JTextField();
        policeNameField.setBounds(126, 35, 162, 24);
        getContentPane().add(policeNameField);
        policeNameField.setColumns(10);
        
        JLabel lblNewLabel_1 = new JLabel("性别");
        lblNewLabel_1.setBounds(28, 88, 72, 18);
        getContentPane().add(lblNewLabel_1);
        
        policeSexField = new JComboBox<String>();
        policeSexField.setModel(new DefaultComboBoxModel<String>(new String[] {"男", "女"}));
        policeSexField.setBounds(126, 85, 162, 24);
        getContentPane().add(policeSexField);
        
        JLabel lblNewLabel_2 = new JLabel("警号");
        lblNewLabel_2.setBounds(28, 149, 72, 18);
        getContentPane().add(lblNewLabel_2);
        
        policeCodeField = new JTextField();
        policeCodeField.setBounds(126, 146, 162, 24);
        getContentPane().add(policeCodeField);
        policeCodeField.setColumns(10);
        
        JButton btnNewButton = new JButton("保存");
        btnNewButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                CaseService caseService = new CaseService();
                ResultDTO resultDTO = new ResultDTO();
                //新增
                if (policeId == 0) {
                    resultDTO = caseService.addPolice(policeNameField.getText(), String.valueOf(policeSexField.getSelectedIndex()), policeCodeField.getText());
                } else {
                    //更新
                    Police police = new Police();
                    police.setId(policeId);
                    police.setName(policeNameField.getText());
                    police.setSex(String.valueOf(policeSexField.getSelectedIndex()));
                    police.setPoliceNumber(policeCodeField.getText());
                    resultDTO = caseService.updatePolice(police);
                }
                if (CommonConstant.RESULT_CODE_FAIL.equals(resultDTO.getCode())) {
                    MainFrame.alert(resultDTO.getMessage());
                    return;
                }
                MainFrame.alert("保存成功");
                MainFrame.frame.setEnabled(true);
                PolicePanel.getInstance().updateTable();
                PoliceDialog.this.setVisible(false);
            }
        });
        btnNewButton.setBounds(75, 213, 113, 27);
        getContentPane().add(btnNewButton);
        
        JButton btnNewButton_1 = new JButton("取消");
        btnNewButton_1.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                MainFrame.frame.setEnabled(true);
                PoliceDialog.this.setVisible(false);
            }
        });
        btnNewButton_1.setBounds(202, 213, 113, 27);
        getContentPane().add(btnNewButton_1);
    }

    public int getPoliceId() {
        return policeId;
    }

    public void setPoliceId(int policeId) {
        this.policeId = policeId;
    }
    
}
