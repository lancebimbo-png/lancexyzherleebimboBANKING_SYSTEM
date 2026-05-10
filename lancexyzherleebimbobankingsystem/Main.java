/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package lancexyzherleebimbobankingsystem;

import javax.swing.*;
 
public class Main {
    public static void main(String[] args) {
        
        try {
            for (UIManager.LookAndFeelInfo info : UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (Exception e) {
        }
 
        SwingUtilities.invokeLater(() -> {
            CustomerAccountFrame mainWindow = new CustomerAccountFrame();
            mainWindow.setVisible(true);
        });
    }
}