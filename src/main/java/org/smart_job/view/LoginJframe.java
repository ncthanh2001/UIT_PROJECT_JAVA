package org.smart_job.view;

import javax.swing.*;

public class LoginJframe {
    private JButton btn_login;
    private JPanel panel1;


    public static void main(String[] args){

        JFrame frame = new JFrame("App");
        frame.setContentPane(new LoginJframe().panel1);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.pack();
        frame.setVisible(true);
    }
}
