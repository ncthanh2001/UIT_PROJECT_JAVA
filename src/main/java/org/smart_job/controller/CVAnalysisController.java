package org.smart_job.controller;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.text.PDFTextStripper;
import org.smart_job.entity.CV;
import org.smart_job.service.CVAnalysisService;
import org.smart_job.service.impl.CVAnalysisServiceImpl;
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

//        Integer userId = SessionManager.getCurrentUserId();
//
//        // Nếu chưa đăng nhập
//        if (userId == null) {
//            view.showMessage("Bạn chưa đăng nhập. CV sẽ được lưu với user mặc định (ID=1).");
//            userId = 1; // user mặc định
//        }

        // Load CV cũ theo userId
//        CV latest = service.getLatestCVByUser(userId);
//        if (latest != null) {
//            view.setUploadedFilePath(latest.getFilePath());
//        } else {
//            if (SessionManager.getCurrentUserId() != null) {
//                view.showMessage("Bạn chưa upload CV nào!");
//            }
//        }

        // Sự kiện chọn file
        view.getChooseFileButton().addActionListener(e -> {
            String filePath = view.chooseFile();
            if (filePath != null) {
                try {
                    // Parse text từ PDF
                    String parsedText = extractTextFromPDF(filePath);

                    // Hardcode userId = 1 (sau này thay bằng SessionManager)
                    CV saved = service.uploadCV(1, filePath, parsedText);

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
        });
    }

    /**
     * Extract plain text from PDF file
     */
    private String extractTextFromPDF(String filePath) throws Exception {
        File file = new File(filePath);
        try (PDDocument document = PDDocument.load(file)) {
            PDFTextStripper pdfStripper = new PDFTextStripper();
            return pdfStripper.getText(document);
        }
    }
}
