package org.smart_job.controller;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.text.PDFTextStripper;
import org.smart_job.entity.CV;
import org.smart_job.entity.User;
import org.smart_job.service.CVAnalysisService;
import org.smart_job.service.impl.CVAnalysisServiceImpl;
import org.smart_job.session.UserSession;
import org.smart_job.view.CVAnalysis.CVAnalysisContentPanel;

import java.io.File;

public class CVAnalysisController {
    private final CVAnalysisContentPanel view;
    private final CVAnalysisService service;
    private static final Logger logger = LogManager.getLogger(CVAnalysisController.class);

    public CVAnalysisController(CVAnalysisContentPanel view) {
        this.view = view;
        this.service = new CVAnalysisServiceImpl();
        init();
    }

    private void init() {
        logger.info("Initializing CVAnalysisController");

        User currentUser = UserSession.getInstance().getCurrentUser();
        if (currentUser == null) {
            view.showMessage("Bạn chưa đăng nhập. Không thể sử dụng chức năng CV Analysis.");
            return;
        }

        Integer userId = currentUser.getId();

        // Load CV cũ theo userId
        loadLastUploadedCV(userId);

        // Sự kiện nút chọn file
        view.getChooseFileButton().addActionListener(e -> {
            String filePath = view.chooseFile();
            if (filePath != null) {
                handleUpload(new File(filePath), userId);
            }
        });

        // Sự kiện kéo–thả file
        view.setFileDropListener(file -> handleUpload(file, userId));
    }

    /**
     * Load CV gần nhất của user
     */
    private void loadLastUploadedCV(Integer userId) {
        try {
            CV latest = service.getLatestCVByUser(userId);
            if (latest != null) {
                view.setUploadedFilePath(latest.getFilePath());
            } else {
                view.setUploadedFilePath("No CV uploaded yet");
                view.showMessage("Bạn chưa upload CV nào!");
            }
        } catch (Exception ex) {
            logger.error("Error loading CV for user {}", userId, ex);
            view.showMessage("Error loading CV: " + ex.getMessage());
        }
    }

    /**
     * Xử lý upload CV (dùng chung cho nút và drag&drop)
     */
    private void handleUpload(File file, Integer userId) {
        try {
            if (!file.getName().toLowerCase().endsWith(".pdf")) {
                view.showMessage("Chỉ hỗ trợ file PDF!");
                return;
            }

            // Parse text từ PDF
            String parsedText = extractTextFromPDF(file);

            // Upload và lưu CV
            CV saved = service.uploadCV(userId, file.getAbsolutePath(), parsedText);

            if (saved != null) {
                view.setUploadedFilePath(saved.getFilePath());
                view.showMessage("CV uploaded and saved successfully!");
            } else {
                view.showMessage("Failed to save CV.");
            }
        } catch (Exception ex) {
            logger.error("Error uploading CV", ex);
            view.showMessage("Error: " + ex.getMessage());
        }
    }

    /**
     * Extract plain text from PDF file
     */
    private String extractTextFromPDF(File file) throws Exception {
        try (PDDocument document = PDDocument.load(file)) {
            PDFTextStripper pdfStripper = new PDFTextStripper();
            return pdfStripper.getText(document);
        }
    }
}
