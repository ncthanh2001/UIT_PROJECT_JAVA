package org.smart_job;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.smart_job.config.AppConfig;
import org.smart_job.controller.MainController;
import org.smart_job.service.UserService;
import org.smart_job.service.impl.UserServiceImpl;
import org.smart_job.view.BaseLayoutView;

import javax.swing.*;

public class Main {
    private static final Logger logger = LogManager.getLogger(Main.class);

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            try{
                AppConfig.init();

                UserService userService = new UserServiceImpl();
                BaseLayoutView layoutView = new BaseLayoutView();

                MainController mainController = MainController.init(layoutView, userService);
                mainController.bootstrap();

            } catch (Exception e) {
                logger.error("Error init app: ", e);
                JOptionPane.showMessageDialog(null, "Failed to initialize the application: " + e.getMessage());
            }
        });
    }
}
