package org.smart_job.config;

import com.formdev.flatlaf.FlatLightLaf;
import org.smart_job.util.JPAUtil;

import javax.swing.*;

public class AppConfig {
    public static void init() throws Exception {
        // Bootstrap JPA → lúc này Hibernate scan entity và tạo tables
        JPAUtil.getEntityManagerFactory();

        // UI theme
        UIManager.setLookAndFeel(new FlatLightLaf());
    }
}
