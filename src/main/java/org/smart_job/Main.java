package org.smart_job;

import com.formdev.flatlaf.FlatLightLaf;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.smart_job.controller.MainController;
import org.smart_job.view.BaseLayoutView;

import javax.swing.*;

public class Main {
    private static final Logger logger = LogManager.getLogger(Main.class);

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            try {
                // Check connect mysql
                UIManager.setLookAndFeel(new FlatLightLaf());

                BaseLayoutView layoutView = new BaseLayoutView();
                MainController mainController = MainController.init(layoutView);
                mainController.start();
            } catch (Exception e) {
                logger.error("Error init app: ", e);
                JOptionPane.showMessageDialog(null, "Failed to initialize the application: " + e.getMessage());
            }
        });
    }
}
