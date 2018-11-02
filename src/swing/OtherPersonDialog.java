package swing;

import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JTextField;

import constant.CommonConstant;
import dto.ResultDTO;
import entity.AskedPerson;
import entity.OtherPerson;
import service.CaseService;

import javax.swing.ButtonGroup;
import javax.swing.JComboBox;
import javax.swing.JRadioButton;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class OtherPersonDialog extends JDialog {
    
    private static final long serialVersionUID = -5898195535936731794L;
    public JTextField nameField;
    public JTextField idCardField;
    public JComboBox<String> sexComboBox;
    private int otherPersonId;
    public ButtonGroup otherTypeGroup;
    private static OtherPersonDialog instance;

    public static OtherPersonDialog getInstance() {
        if (instance == null) {
            instance = new OtherPersonDialog();
        }
        return instance;
    }

    public OtherPersonDialog() {
        setModal(true);
        getContentPane().setLayout(null);

        JLabel label = new JLabel("姓名：");
        label.setBounds(39, 74, 59, 21);
        getContentPane().add(label);

        nameField = new JTextField();
        nameField.setColumns(10);
        nameField.setBounds(89, 76, 156, 21);
        getContentPane().add(nameField);

        JLabel label_1 = new JLabel("性别：");
        label_1.setBounds(295, 74, 54, 21);
        getContentPane().add(label_1);

        sexComboBox = new JComboBox<String>();
        sexComboBox.setModel(new DefaultComboBoxModel(new String[] {"男", "女"}));
        sexComboBox.setBounds(343, 77, 66, 21);
        getContentPane().add(sexComboBox);

        JLabel label_2 = new JLabel("身份证号：");
        label_2.setBounds(39, 125, 85, 19);
        getContentPane().add(label_2);

        idCardField = new JTextField();
        idCardField.setColumns(10);
        idCardField.setBounds(124, 125, 244, 21);
        getContentPane().add(idCardField);

        otherTypeGroup = new ButtonGroup();
        JLabel label_3 = new JLabel("其他人员类型");
        label_3.setBounds(39, 183, 108, 21);
        getContentPane().add(label_3);

        //OtherTypeListener otherTypeListener = new OtherTypeListener();
        JRadioButton radioButton = new JRadioButton("监护人");
        //radioButton.addActionListener(otherTypeListener);
        otherTypeGroup.add(radioButton);
        radioButton.setActionCommand("1");
        radioButton.setBounds(157, 182, 88, 23);
        getContentPane().add(radioButton);

        JRadioButton radioButton_1 = new JRadioButton("翻译");
        //radioButton_1.addActionListener(otherTypeListener);
        otherTypeGroup.add(radioButton_1);
        radioButton_1.setActionCommand("2");
        radioButton_1.setBounds(251, 182, 66, 23);
        getContentPane().add(radioButton_1);

        JRadioButton radioButton_2 = new JRadioButton("其他人员");
        //radioButton_2.addActionListener(otherTypeListener);
        otherTypeGroup.add(radioButton_2);
        radioButton_2.setActionCommand("3");
        radioButton_2.setBounds(332, 182, 97, 23);
        getContentPane().add(radioButton_2);
        
        JButton saveButton = new JButton("保存");
        saveButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                CaseService caseService = new CaseService();
                ResultDTO resultDTO = new ResultDTO();
                String otherName = nameField.getText();
                Integer otherSex = sexComboBox.getSelectedIndex();
                String idCard = idCardField.getText();
                
                String selectedOtherType = otherTypeGroup.getSelection().getActionCommand();;
                //新增
                if (otherPersonId == 0) {
                    resultDTO = caseService.addOtherPerson(NotePanel.noteId, otherName, String.valueOf(otherSex), idCard, selectedOtherType);
                } else {
                    //更新
                    OtherPerson otherPerson = caseService.selectOtherPersonById(otherPersonId);
                    otherPerson.setName(otherName);
                    otherPerson.setSex(String.valueOf(otherSex));
                    otherPerson.setIdCard(idCard);
                    otherPerson.setType(selectedOtherType);
                    resultDTO = caseService.updateOtherPerson(otherPerson);
                }
                if (CommonConstant.RESULT_CODE_FAIL.equals(resultDTO.getCode())) {
                    MainFrame.alert(resultDTO.getMessage());
                    return;
                }
                MainFrame.alert("保存成功");
                NotePanel.getInstance().updateOtherTable();
                getInstance().setVisible(false);
            }
        });
        saveButton.setBounds(89, 258, 113, 27);
        getContentPane().add(saveButton);
        
        JButton delButton = new JButton("取消");
        delButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                getInstance().setVisible(false);
            }
        });
        delButton.setBounds(255, 258, 113, 27);
        getContentPane().add(delButton);
    }

    public int getOtherPersonId() {
        return otherPersonId;
    }

    public void setOtherPersonId(int otherPersonId) {
        this.otherPersonId = otherPersonId;
    }

}
