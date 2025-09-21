-- =========================
-- Xóa dữ liệu cũ
-- =========================
DELETE FROM job_skills;
DELETE FROM jobs;
DELETE FROM skills;

-- Reset auto increment
ALTER TABLE job_skills AUTO_INCREMENT = 1;
ALTER TABLE jobs AUTO_INCREMENT = 1;
ALTER TABLE skills AUTO_INCREMENT = 1;

-- =========================
-- Seed Jobs
-- =========================
INSERT INTO jobs (title, company_name, country, city, url, description, expiration_date) VALUES
                                                                                             ('Java Developer', 'Google', 'Vietnam', 'Hanoi', 'https://example.com/', 'Phát triển ứng dụng Java và Spring Boot cho hệ thống quy mô lớn.', NOW() + INTERVAL 30 DAY),
                                                                                             ('Frontend Engineer', 'Facebook', 'Vietnam', 'HCMC', 'https://example.com/', 'Xây dựng giao diện người dùng bằng React, tối ưu hiệu suất.', NOW() + INTERVAL 45 DAY),
                                                                                             ('Backend Engineer', 'Amazon', 'Vietnam', 'Da Nang', 'https://example.com/', 'Thiết kế API backend hiệu năng cao, xử lý dữ liệu lớn.', NOW() + INTERVAL 60 DAY),
                                                                                             ('Data Scientist', 'Microsoft', 'Vietnam', 'Hanoi', 'https://example.com/', 'Phân tích dữ liệu, xây dựng mô hình Machine Learning.', NOW() + INTERVAL 40 DAY),
                                                                                             ('Mobile Developer', 'Zalo', 'Vietnam', 'HCMC', 'https://example.com/', 'Phát triển ứng dụng Android/iOS.', NOW() + INTERVAL 50 DAY),
                                                                                             ('DevOps Engineer', 'Grab', 'Vietnam', 'Hanoi', 'https://example.com/', 'Quản lý CI/CD pipeline, triển khai hệ thống cloud.', NOW() + INTERVAL 35 DAY),
                                                                                             ('QA Engineer', 'Shopee', 'Vietnam', 'HCMC', 'https://example.com/', 'Viết test case, kiểm thử tự động, đảm bảo chất lượng sản phẩm.', NOW() + INTERVAL 25 DAY),
                                                                                             ('System Architect', 'FPT Software', 'Vietnam', 'Da Nang', 'https://example.com/', 'Thiết kế kiến trúc hệ thống phân tán.', NOW() + INTERVAL 70 DAY),
                                                                                             ('AI Engineer', 'Viettel AI', 'Vietnam', 'Hanoi', 'https://example.com/', 'Xây dựng hệ thống trí tuệ nhân tạo.', NOW() + INTERVAL 55 DAY),
                                                                                             ('Fullstack Developer', 'Tiki', 'Vietnam', 'HCMC', 'https://example.com/', 'Phát triển cả backend và frontend.', NOW() + INTERVAL 65 DAY);

-- =========================
-- Seed Skills
-- =========================
INSERT INTO skills (name) VALUES
                              ('Java'),
                              ('Spring Boot'),
                              ('React'),
                              ('Angular'),
                              ('Vue.js'),
                              ('SQL'),
                              ('MongoDB'),
                              ('AWS'),
                              ('Docker'),
                              ('Kubernetes'),
                              ('Python'),
                              ('Machine Learning'),
                              ('iOS'),
                              ('Android'),
                              ('Testing Automation');

-- =========================
-- Seed Job-Skills Mapping
-- =========================
INSERT INTO job_skills (job_id, skill_id) VALUES
-- Job 1
(1, 1), (1, 2), (1, 6), (1, 8),
-- Job 2
(2, 3), (2, 4), (2, 5),
-- Job 3
(3, 1), (3, 2), (3, 6), (3, 9),
-- Job 4
(4, 11), (4, 6), (4, 12),
-- Job 5
(5, 14), (5, 13), (5, 1),
-- Job 6
(6, 9), (6, 10), (6, 8),
-- Job 7
(7, 15), (7, 6),
-- Job 8
(8, 1), (8, 8), (8, 10),
-- Job 9
(9, 11), (9, 12), (9, 8),
-- Job 10
(10, 1), (10, 2), (10, 3), (10, 6);
