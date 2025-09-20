package org.smart_job.service.impl;

import org.smart_job.dao.CVDao;
import org.smart_job.dao.impl.CVDAOImpl;
import org.smart_job.entity.CV;
import org.smart_job.service.CVAnalysisService;

public class CVAnalysisServiceImpl implements CVAnalysisService {
    private final CVDao cvDao = new CVDAOImpl();

    @Override
    public CV uploadCV(Integer userId, String filePath, String parsedText) throws Exception {
        CV existing = cvDao.findLatestByUserId(userId);
        if (existing == null) {
            CV cv = new CV();
            cv.setUserId(userId);
            cv.setFilePath(filePath);
            cv.setParsedText(parsedText);
            return cvDao.insert(cv);
        } else {
            existing.setFilePath(filePath);
            existing.setParsedText(parsedText);
            return cvDao.update(existing);
        }
    }

    @Override
    public CV getLatestCVByUser(Integer userId) throws Exception {
        return cvDao.findLatestByUserId(userId);
    }
}
