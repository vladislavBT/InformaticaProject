package com.example.demo;

import com.example.demo.Exceptions.EmptyException;
import com.example.demo.Exceptions.IncorrectException;
import com.example.demo.entities.User;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.*;
import java.util.Scanner;

public class SignUpPage extends JFrame {

    private JTextField usernameField;
    private JTextField emailField;
    private JTextField passwordField;
    private JButton nextPageButton;
    private JPanel panel;

    private int idUserCreator = 1;

    private BufferedWriter bufferedWriter;

    public SignUpPage(StringBuilder stringBuilder) {
        setContentPane(panel);
        setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
        setVisible(true);
        setSize(750,450);


        this.nextPageButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {


                //validation
                String textOfUsernameField = usernameField.getText();
                String textOfEmailField = emailField.getText();
                String textOfPassword = passwordField.getText();

                boolean passwordHasUpperCase = false;
                boolean passwordHasLowerCase = false;
                boolean passwordHasDigitCase = false;

                try {
                    if (textOfUsernameField.equals("")) {
                        throw new EmptyException("Username is empty");
                    }
                    if (textOfEmailField.equals("")) {
                        throw new EmptyException("Email is empty");
                    }
                    if (!textOfEmailField.contains("@")) {
                        throw new IncorrectException("Your email doesn't have @");
                    }
                    if (!textOfEmailField.contains(".")) {
                        throw new IncorrectException("Your email doesn't have .");
                    }
                    if (textOfPassword.length() < 8) {
                        throw new IncorrectException("Your password must be at least 8 characters");
                    }

                    for (int i = 0; i < textOfPassword.length(); i++) {
                        if (textOfPassword.charAt(i) <= 122 && textOfPassword.charAt(i) >= 97) {
                            passwordHasLowerCase = true;
                        }
                        if (textOfPassword.charAt(i) <= 90 && textOfPassword.charAt(i) >= 65) {
                            passwordHasUpperCase = true;
                        }
                        if (textOfPassword.charAt(i) <= 57 && textOfPassword.charAt(i) >= 48) {
                            passwordHasDigitCase = true;
                        }
                    }

                    if (!passwordHasLowerCase) {
                        throw new IncorrectException("Your password must have an Lowercase letter");
                    } else if (!passwordHasUpperCase) {
                        throw new IncorrectException("Your password must have an Uppercase letter");
                    } else if (!passwordHasDigitCase) {
                        throw new IncorrectException("Your password must have a digit");
                    }
                }catch (Exception ex){
                    JOptionPane.showMessageDialog(null, ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
                    throw new RuntimeException(ex);
                }


                String toGetLastId = stringBuilder.toString();

                String[] allThingsArr = toGetLastId.split("\\n");
                int idMax=-1;
                for (String s : allThingsArr) {
                    try {
                        String[] userProperties= s.split(" ");
                        idMax = Integer.parseInt(userProperties[0]);
                    }catch (Exception outOfBounds){
                        continue;
                    }
                }

                idUserCreator=idMax;
                idUserCreator++;
                User user = new User(idUserCreator,textOfUsernameField,textOfEmailField,textOfPassword);

                String fileLine = String.format("%d %s %s %s%n",user.getId(),user.getUsername(),user.getEmail(),user.getPassword());


                try {
                    BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter("D:\\Intelij\\IdeaProjects\\InformaticaProject\\demo\\src\\main\\resources\\UserAccounts"));
                    stringBuilder.append(fileLine);
                    bufferedWriter.write(stringBuilder.toString());
                    System.out.println(stringBuilder.toString());
                    bufferedWriter.close();



                } catch (IOException ex) {
                    throw new RuntimeException(ex);
                }

            }
        });
    }
}
