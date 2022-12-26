package com.example.demo;

import javax.swing.*;
import java.awt.*;

public class MyFrame extends JFrame {
    public MyFrame(int width, int height){
        this.setSize(width,height);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setLocationRelativeTo(null);
        this.setLayout(null);
        this.setVisible(true);
    }

    @Override
    public void paint(Graphics g){
        super.paint(g);
        g.drawString("asda*",50,50);
    }
}
