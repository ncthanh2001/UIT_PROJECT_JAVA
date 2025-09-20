package org.smart_job.view.CVAnalysis;

import lombok.Getter;

import javax.swing.*;
import java.awt.*;
import java.io.File;

@Getter
public class CVAnalysisContentPanel extends JPanel {
    private JLabel dropLabel;
    private JButton chooseFileButton;
    private JLabel uploadedFileLabel; // hiển thị file đã upload

    public CVAnalysisContentPanel() {
        initCVAnalysis();
    }

    private void initCVAnalysis() {
        setLayout(new BorderLayout());

        // Title + Subtitle
        JPanel headerPanel = new JPanel(new BorderLayout());
        JLabel title = new JLabel("CV Analysis");
        title.setFont(new Font("Arial", Font.BOLD, 28));
        JLabel subtitle = new JLabel("Use AI to analyze and improve your CV");
        subtitle.setFont(new Font("Arial", Font.PLAIN, 14));
        headerPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        headerPanel.add(title, BorderLayout.NORTH);
        headerPanel.add(subtitle, BorderLayout.SOUTH);

        // Upload Section
        JPanel uploadPanel = new JPanel();
        uploadPanel.setLayout(new BoxLayout(uploadPanel, BoxLayout.Y_AXIS));
        uploadPanel.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(Color.BLACK),
                BorderFactory.createEmptyBorder(20, 20, 20, 20)
        ));

        JLabel uploadTitle = new JLabel("Upload CV");
        uploadTitle.setFont(new Font("Arial", Font.BOLD, 18));
        JLabel uploadSubtitle = new JLabel("Upload file CV (PDF) to AI analysis");
        uploadSubtitle.setFont(new Font("Arial", Font.PLAIN, 14));

        // Drop Area
        JPanel dropPanel = new JPanel(new BorderLayout());
        dropPanel.setPreferredSize(new Dimension(600, 150));
        dropPanel.setBorder(BorderFactory.createDashedBorder(Color.DARK_GRAY));

        dropLabel = new JLabel("Drag and drop your CV file here", SwingConstants.CENTER);
        dropLabel.setFont(new Font("Arial", Font.BOLD, 16));
        dropPanel.add(dropLabel, BorderLayout.CENTER);

        // Text under drop
        JLabel orLabel = new JLabel("or select file from computer", SwingConstants.CENTER);
        orLabel.setForeground(Color.RED);
        JLabel hintLabel = new JLabel("Support PDF (max 10MB)", SwingConstants.CENTER);

        chooseFileButton = new JButton("Choose file from computer");

        uploadedFileLabel = new JLabel("No CV uploaded yet", SwingConstants.CENTER);
        uploadedFileLabel.setForeground(Color.BLUE);
        uploadedFileLabel.setFont(new Font("Arial", Font.ITALIC, 14));

        uploadPanel.add(uploadTitle);
        uploadPanel.add(uploadSubtitle);
        uploadPanel.add(Box.createVerticalStrut(10));
        uploadPanel.add(dropPanel);
        uploadPanel.add(Box.createVerticalStrut(10));
        uploadPanel.add(orLabel);
        uploadPanel.add(hintLabel);
        uploadPanel.add(Box.createVerticalStrut(10));
        uploadPanel.add(chooseFileButton);
        uploadPanel.add(Box.createVerticalStrut(20));
        uploadPanel.add(uploadedFileLabel); // thêm label hiển thị file

        // Wrap all
        JPanel wrapper = new JPanel();
        wrapper.setLayout(new BoxLayout(wrapper, BoxLayout.Y_AXIS));
        wrapper.setBorder(BorderFactory.createEmptyBorder(20, 30, 20, 30));
        wrapper.add(headerPanel);
        wrapper.add(Box.createVerticalStrut(30));
        wrapper.add(uploadPanel);

        add(wrapper, BorderLayout.CENTER);
    }

    public String chooseFile() {
        JFileChooser chooser = new JFileChooser();
        chooser.setFileFilter(new javax.swing.filechooser.FileNameExtensionFilter("PDF Files", "pdf"));
        int result = chooser.showOpenDialog(this);
        if (result == JFileChooser.APPROVE_OPTION) {
            File file = chooser.getSelectedFile();
            return file.getAbsolutePath();
        }
        return null;
    }

    public void setUploadedFilePath(String path) {
        uploadedFileLabel.setText("Uploaded CV: " + path);
    }

    public void showMessage(String msg) {
        JOptionPane.showMessageDialog(this, msg);
    }
}
