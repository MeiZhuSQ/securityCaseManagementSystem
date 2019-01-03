package swing;

import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JTextField;

import constant.CommonConstant;
import dto.ResultDTO;
import entity.AskedPerson;
import entity.Police;
import service.CaseService;

import javax.swing.ButtonGroup;
import javax.swing.JComboBox;
import javax.swing.JRadioButton;
import javax.swing.DefaultComboBoxModel;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class AskedPersonDialog extends JDialog {
    
    private static final long serialVersionUID = -1192280828630672161L;
    public JTextField nameField;
    public JComboBox<String> sexComboBox;
    public JTextField idCardField;
    private int askedPersonId;
    private static AskedPersonDialog instance;
    public ButtonGroup askedAdultTypeGroup;
    public ButtonGroup askedAbleTypeGroup;
    public ButtonGroup askedTypeGroup;
    /*public NoteAskedTypeListener askedTypeListener = new NoteAskedTypeListener();
    public NoteAskedAdultListener askedAdultListener = new NoteAskedAdultListener();
    public NoteAskedAbleListener askedAbleListener = new NoteAskedAbleListener();*/
    
    public static AskedPersonDialog getInstance() {
        if (instance == null) {
            instance = new AskedPersonDialog();
        }
        return instance;
    }
    
    public AskedPersonDialog() {
        //设置模态
        setModal(true);
        getContentPane().setLayout(null);
        
        JLabel label = new JLabel("姓名：");
        label.setBounds(59, 61, 54, 19);
        getContentPane().add(label);
        
        nameField = new JTextField();
        nameField.setColumns(10);
        nameField.setBounds(110, 61, 156, 21);
        getContentPane().add(nameField);
        
        JLabel label_1 = new JLabel("性别：");
        label_1.setBounds(312, 61, 54, 19);
        getContentPane().add(label_1);
        
        sexComboBox = new JComboBox<String>();
        sexComboBox.setModel(new DefaultComboBoxModel(new String[] {"男", "女"}));
        sexComboBox.setBounds(361, 62, 66, 21);
        getContentPane().add(sexComboBox);
        
        JLabel label_2 = new JLabel("身份证号：");
        label_2.setBounds(59, 106, 77, 19);
        getContentPane().add(label_2);
        
        idCardField = new JTextField();
        idCardField.setColumns(10);
        idCardField.setBounds(135, 106, 315, 21);
        getContentPane().add(idCardField);
        
        askedAdultTypeGroup = new ButtonGroup();

        askedAbleTypeGroup = new ButtonGroup();

        askedTypeGroup = new ButtonGroup();
        
        /*NoteAskedTypeListener askedTypeListener = new NoteAskedTypeListener();
        NoteAskedAdultListener askedAdultListener = new NoteAskedAdultListener();
        NoteAskedAbleListener askedAbleListener = new NoteAskedAbleListener();*/
        
        JLabel label_3 = new JLabel("被询问人类型");
        label_3.setBounds(59, 153, 90, 15);
        getContentPane().add(label_3);
        
        JLabel label_4 = new JLabel("是否成人");
        label_4.setBounds(59, 195, 90, 21);
        getContentPane().add(label_4);
        
        JRadioButton radioButton = new JRadioButton("成年");
        //radioButton.addActionListener(askedAdultListener);
        askedAdultTypeGroup.add(radioButton);
        radioButton.setActionCommand("1");
        radioButton.setBounds(167, 195, 66, 23);
        getContentPane().add(radioButton);
        
        JRadioButton radioButton_1 = new JRadioButton("未成年");
        //radioButton_1.addActionListener(askedAdultListener);
        askedAdultTypeGroup.add(radioButton_1);
        radioButton_1.setActionCommand("2");
        radioButton_1.setBounds(244, 195, 90, 23);
        getContentPane().add(radioButton_1);
        
        JLabel label_5 = new JLabel("是否健全");
        label_5.setBounds(59, 250, 95, 17);
        getContentPane().add(label_5);
        
        JRadioButton radioButton_2 = new JRadioButton("健全");
        //radioButton_2.addActionListener(askedAbleListener);
        askedAbleTypeGroup.add(radioButton_2);
        radioButton_2.setActionCommand("1");
        radioButton_2.setBounds(167, 250, 90, 23);
        getContentPane().add(radioButton_2);
        
        JRadioButton radioButton_3 = new JRadioButton("非健全");
        //radioButton_3.addActionListener(askedAbleListener);
        radioButton_3.setActionCommand("2");
        askedAbleTypeGroup.add(radioButton_3);
        radioButton_3.setBounds(259, 250, 121, 23);
        getContentPane().add(radioButton_3);
        
        JRadioButton radioButton_4 = new JRadioButton("被害人");
        //radioButton_4.addActionListener(askedTypeListener);
        radioButton_4.setActionCommand("1");
        radioButton_4.setBounds(167, 149, 83, 23);
        askedTypeGroup.add(radioButton_4);
        getContentPane().add(radioButton_4);
        
        JRadioButton radioButton_5 = new JRadioButton("嫌疑人");
        //radioButton_5.addActionListener(askedTypeListener);
        askedTypeGroup.add(radioButton_5);
        radioButton_5.setActionCommand("2");
        radioButton_5.setBounds(272, 149, 83, 23);
        getContentPane().add(radioButton_5);
        
        JRadioButton radioButton_6 = new JRadioButton("证人");
        //radioButton_6.addActionListener(askedTypeListener);
        askedTypeGroup.add(radioButton_6);
        radioButton_6.setActionCommand("3");
        radioButton_6.setBounds(361, 149, 77, 23);
        getContentPane().add(radioButton_6);
        
        JButton button = new JButton("保存");
        button.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                CaseService caseService = new CaseService();
                ResultDTO resultDTO = new ResultDTO();
                String askedName = nameField.getText();
                Integer askedSex = sexComboBox.getSelectedIndex();
                String idCard = idCardField.getText();
                
                String selectedAskedType = askedTypeGroup.getSelection().getActionCommand();;
                String selectedAskedAudlt = askedAdultTypeGroup.getSelection().getActionCommand();
                String selectedAbled = askedAbleTypeGroup.getSelection().getActionCommand();
                /*// 其他人
                String otherName = "";otherNameField.getText();
                Integer otherSex = 0;otherSexComboBox.getSelectedIndex();
                String otherIdCard = "";otherIDField.getText();
                String selectedOtherType = otherTypeListener.selectedOtherType;*/
                //新增
                if (askedPersonId == 0) {
                    resultDTO = caseService.addAskedPerson(NotePanel.getInstance().noteId, askedName, String.valueOf(askedSex), selectedAskedType, selectedAskedAudlt, idCard, selectedAbled);
                } else {
                    //更新
                    AskedPerson askedPerson = caseService.selectAskedPersonById(askedPersonId);
                    askedPerson.setId(askedPersonId);
                    askedPerson.setName(askedName);
                    askedPerson.setSex(String.valueOf(askedSex));
                    askedPerson.setIdCard(idCard);
                    askedPerson.setType(selectedAskedType);
                    askedPerson.setAdultFlag(selectedAskedAudlt);
                    askedPerson.setDisabledFlag(selectedAbled);
                    resultDTO = caseService.updateAskedPerson(askedPerson);
                }
                if (CommonConstant.RESULT_CODE_FAIL.equals(resultDTO.getCode())) {
                    MainFrame.alert(resultDTO.getMessage());
                    return;
                }
                MainFrame.alert("保存成功");
                //NotePanel.getInstance().updateAskedTable();
                getInstance().setVisible(false);
            }
        });
        button.setBounds(140, 316, 113, 27);
        getContentPane().add(button);
        
        JButton button_1 = new JButton("取消");
        button_1.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                getInstance().setVisible(false);
            }
        });
        button_1.setBounds(267, 316, 113, 27);
        getContentPane().add(button_1);
        
    }

    public int getAskedPersonId() {
        return askedPersonId;
    }

    public void setAskedPersonId(int askedPersonId) {
        this.askedPersonId = askedPersonId;
    }

}
