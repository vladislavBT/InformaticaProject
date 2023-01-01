package com.example.demo;

import com.example.demo.entities.UserProperties;
import com.example.demo.entities.enums.Gender;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Locale;

public class SignUpPageSecondStep extends JFrame{
    private JTextField ageTextField;
    private JTextField heightTextField;
    private JRadioButton femaleRadioButton;
    private JRadioButton maleRadioButton;
    private JTextField weightTextField;
    private JLabel HeightLabel;
    private JButton nextStepButton;
    private JPanel panel;

    public SignUpPageSecondStep(int id,StringBuilder stringBuilder){
        setContentPane(panel);
        setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
        setVisible(true);
        setSize(750,450);

        ButtonGroup buttonGroup = new ButtonGroup();
        buttonGroup.add(maleRadioButton);
        buttonGroup.add(femaleRadioButton);

        nextStepButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                UserProperties userProperties=null;
                int age = Integer.parseInt(ageTextField.getText());
                int height = Integer.parseInt(heightTextField.getText());
                int weight = Integer.parseInt(weightTextField.getText());
                Gender gender = null;
                if(maleRadioButton.isSelected()){
                   gender= Gender.MAN;
                }
                if(femaleRadioButton.isSelected()){
                    gender= Gender.WOMAN;
                }
                if(!femaleRadioButton.isSelected() && !maleRadioButton.isSelected()){
                    gender = null;
                }
                try {
                    userProperties = new UserProperties(id,age,height,weight,gender);
                }catch (Exception exception){
                    JOptionPane.showMessageDialog(null, exception.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
                }
                //add the new User properties to the stringBuilder
                String fileLine = String.format("%d %d %d %d %s%n",userProperties.getId(),userProperties.getAge(),userProperties.getHeight(),userProperties.getWeight(),userProperties.getGender().toString());
                stringBuilder.append(fileLine);
                //save to UserProperties
                BufferedWriter bufferedWriter = null;
                try {
                    bufferedWriter = new BufferedWriter(new FileWriter("D:\\Intelij\\IdeaProjects\\InformaticaProject\\demo\\src\\main\\resources\\UserProperties"));
                    bufferedWriter.write(stringBuilder.toString());
                    //System.out.println(stringBuilder.toString());
                    bufferedWriter.close();
                    dispose();
                    JOptionPane.showMessageDialog(null, "You created your account successfully!", "Success", JOptionPane.INFORMATION_MESSAGE);
                    MainPage mainPage = new MainPage();

                } catch (IOException ex) {
                    throw new RuntimeException(ex);
                }


            }
        });
    }

}
