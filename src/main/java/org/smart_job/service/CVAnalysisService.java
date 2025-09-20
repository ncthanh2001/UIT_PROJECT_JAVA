package org.smart_job.service;

import org.smart_job.entity.CV;

public interface CVAnalysisService {
    CV uploadCV(Integer userId, String filePath, String parsedText) throws Exception;
    CV getLatestCVByUser(Integer userId) throws Exception;
}
