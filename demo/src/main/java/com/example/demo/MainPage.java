package com.example.demo;


import com.example.demo.entities.UserAccount;

import javax.swing.*;
import javax.swing.border.BevelBorder;
import javax.swing.border.Border;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class MainPage extends JFrame {



    private JPanel mainPage;
    private JTextField usernameField;
    private JButton signUpButton;
    private JTextField passwordField;
    private JButton logInButton;
    private JLabel usernameLabel;
    private JLabel passwordLabel;

    private JLabel lbImage;

    public MainPage() throws IOException {
            setContentPane(mainPage);
            setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
            setVisible(true);
            setSize(750,450);

            Border border = new BevelBorder(0,new Color(255, 255, 255),Color.ORANGE);
            this.passwordField.setBorder(border);

        signUpButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                try {
                    File file = new File("D:\\Intelij\\IdeaProjects\\InformaticaProject\\demo\\src\\main\\resources\\UserAccounts");
                    Scanner scanner = new Scanner(file);
                    StringBuilder stringBuilder = new StringBuilder();
                    while (scanner.hasNextLine()){
                        stringBuilder.append(scanner.nextLine()+String.format("%n"));
                    }

                    SignUpPageFirstStep signUpPage = new SignUpPageFirstStep(stringBuilder);

                    dispose();
                } catch (Exception ex) {

                }
            }
        });
        logInButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    File file = new File("D:\\Intelij\\IdeaProjects\\InformaticaProject\\demo\\src\\main\\resources\\UserAccounts");
                    Scanner scannerAccounts = new Scanner(file);

                    List<UserAccount> userAccounts = new ArrayList<>();
                    while (scannerAccounts.hasNextLine()){
                        String line = scannerAccounts.nextLine();
                        String[] properties = line.split(" ");

                        int id = Integer.parseInt(properties[0]);
                        String username = properties[1];
                        String email = properties[2];
                        String password = properties[3];

                        UserAccount userAccount = new UserAccount(id,username,email,password);

                        userAccounts.add(userAccount);
                    }
                    boolean loggedIn =false;
                    UserAccount ourUser = null;
                    for (int i = 0; i < userAccounts.size(); i++) {
                        if(userAccounts.get(i).getUsername().equals(usernameField.getText()) && userAccounts.get(i).getPassword().equals(passwordField.getText())){
                            dispose();
                            loggedIn = true;
                            ourUser=userAccounts.get(i);
                        }
                    }
                    if(!loggedIn){
                        JOptionPane.showMessageDialog(null, "Incorrect password or username", "Unsuccessful log in", JOptionPane.ERROR_MESSAGE);
                        usernameField.setText("");
                        passwordField.setText("");
                    }else {
                        LoggedInPage loggedInPage = new LoggedInPage(ourUser);
                    }
                } catch (FileNotFoundException ex) {
                    throw new RuntimeException(ex);
                }
            }
        });
    }

        public static void main(String[] args) throws IOException {
        MainPage mainPage = new MainPage();
        mainPage.setVisible(true);
        }
    }

