package org.smart_job.dao;

import org.smart_job.entity.CV;

import java.util.List;

public interface CVDao extends GenericDAO<CV, Integer> {
    List<CV> findByUserId(Integer userId) throws Exception;
    CV findLatestByUserId(Integer userId) throws Exception;
    List<CV> findByFilePathContaining(String keyword) throws Exception;
    boolean existsByFilePath(String filePath) throws Exception;
    List<CV> findByUserIdOrderByCreatedAt(Integer userId, boolean ascending) throws Exception;
    int countByUserId(Integer userId) throws Exception;
    List<CV> searchInParsedText(String keyword) throws Exception;
    void deleteByUserId(Integer userId) throws Exception;
}