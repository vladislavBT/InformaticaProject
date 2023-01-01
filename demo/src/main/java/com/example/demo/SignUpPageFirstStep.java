package com.example.demo;

import com.example.demo.Exceptions.EmptyException;
import com.example.demo.Exceptions.IncorrectException;
import com.example.demo.entities.UserAccount;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.*;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class SignUpPageFirstStep extends JFrame {

    private JTextField usernameField;
    private JTextField emailField;
    private JTextField passwordField;
    private JButton nextPageButton;
    private JPanel panel;

    private int idUserCreator = 1;

    private BufferedWriter bufferedWriter;

    public SignUpPageFirstStep(StringBuilder stringBuilder) {
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
                    if (textOfPassword.length() > 8) {
                        Pattern pattern = Pattern.compile("^(?=.*[0-9])(?=.*[A-Z])(?=.*[a-z]).*$");
                        Matcher matcher = pattern.matcher(textOfPassword);
                        if(!matcher.find()){
                            throw new IncorrectException("Your password must be at least 8 characters, have a Lowercase letter, have an Uppercase letter and a digit.");
                        }
                    }else {
                        throw new IncorrectException("Your password must be at least 8 characters.");
                    }

                }catch (Exception ex){
                    JOptionPane.showMessageDialog(null, ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
                    throw new RuntimeException(ex);
                }


                String toGetLastId = stringBuilder.toString();

                String[] allThingsArr = toGetLastId.split("\\n");
                int idMax=0;
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
                UserAccount user = new UserAccount(idUserCreator,textOfUsernameField,textOfEmailField,textOfPassword);

                String fileLine = String.format("%d %s %s %s%n",user.getId(),user.getUsername(),user.getEmail(),user.getPassword());


                try {
                    BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter("D:\\Intelij\\IdeaProjects\\InformaticaProject\\demo\\src\\main\\resources\\UserAccounts"));
                    stringBuilder.append(fileLine);
                    bufferedWriter.write(stringBuilder.toString());
                    System.out.println(stringBuilder.toString());
                    bufferedWriter.close();

                    //to not delete the UserProperties File From
                    File file = new File("D:\\Intelij\\IdeaProjects\\InformaticaProject\\demo\\src\\main\\resources\\UserProperties");
                    Scanner scanner = new Scanner(file);
                    StringBuilder stringBuilder = new StringBuilder();
                    while (scanner.hasNextLine()){
                        stringBuilder.append(scanner.nextLine()+String.format("%n"));
                    }
                    //To Here
                    SignUpPageSecondStep signUpSecondStep = new SignUpPageSecondStep(idUserCreator,stringBuilder);
                    dispose();



                } catch (IOException ex) {
                    throw new RuntimeException(ex);
                }

            }
        });
    }
}
