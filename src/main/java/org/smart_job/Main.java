package org.smart_job;

import com.formdev.flatlaf.FlatLightLaf;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.smart_job.controller.MainController;
import org.smart_job.controller.UserController;
import org.smart_job.service.UserService;
import org.smart_job.service.impl.UserServiceImpl;
import org.smart_job.util.JPAUtil;
import org.smart_job.view.BaseLayoutView;

import javax.swing.*;

public class Main {
    private static final Logger logger = LogManager.getLogger(Main.class);

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            try{
                // Bootstrap JPA → lúc này Hibernate mới scan entity và tạo table
                JPAUtil.getEntityManagerFactory();

                UserService userService = new UserServiceImpl(); // implementation cụ thể
                UserController controller = new UserController(userService);

                controller.register("test4@example.com", "Test4", "test4pass");

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
