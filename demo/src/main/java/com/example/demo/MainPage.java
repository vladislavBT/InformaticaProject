package com.example.demo;


import javax.swing.*;
import javax.swing.border.BevelBorder;
import javax.swing.border.Border;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.*;
import java.util.Scanner;

public class MainPage extends JFrame {



    private JPanel mainPage;
    private JTextField emailField;
    private JButton signUpButton;
    private JTextField passwordField;
    private JButton logInButton;
    private JLabel emailLabel;
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

                    SignUpPage signUpPage = new SignUpPage(stringBuilder);

                    dispose();
                } catch (Exception ex) {

                }


            }
        });
    }

        public static void main(String[] args) throws IOException {
        MainPage mainPage = new MainPage();
        mainPage.setVisible(true);
        }
    }

