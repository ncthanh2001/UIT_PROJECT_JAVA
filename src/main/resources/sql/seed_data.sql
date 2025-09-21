-- =========================
-- Xóa dữ liệu cũ
-- =========================
DELETE FROM job_skills;
DELETE FROM jobs;
DELETE FROM skills;
DELETE FROM job_application;

-- Reset auto increment
ALTER TABLE job_skills AUTO_INCREMENT = 1;
ALTER TABLE jobs AUTO_INCREMENT = 1;
ALTER TABLE skills AUTO_INCREMENT = 1;
ALTER TABLE job_application AUTO_INCREMENT = 1;

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
-- Seed Jobs (50 records)
-- =========================
INSERT INTO jobs (title, company_name, country, city, url, description, expiration_date) VALUES
-- Vietnam
('Java Developer', 'Google', 'Vietnam', 'Hanoi', 'https://example.com/', 'Phát triển ứng dụng Java và Spring Boot.', NOW() + INTERVAL 30 DAY),
('Frontend Engineer', 'Facebook', 'Vietnam', 'HCM', 'https://example.com/', 'Xây dựng UI bằng React.', NOW() + INTERVAL 40 DAY),
('Backend Engineer', 'Amazon', 'Vietnam', 'Da Nang', 'https://example.com/', 'Thiết kế API backend hiệu năng cao.', NOW() + INTERVAL 50 DAY),
('Data Scientist', 'Microsoft', 'Vietnam', 'Hanoi', 'https://example.com/', 'Phân tích dữ liệu lớn, ML.', NOW() + INTERVAL 60 DAY),
('Mobile Developer', 'Zalo', 'Vietnam', 'HCM', 'https://example.com/', 'Phát triển ứng dụng iOS/Android.', NOW() + INTERVAL 35 DAY),
('DevOps Engineer', 'Grab', 'Vietnam', 'Hanoi', 'https://example.com/', 'Quản lý CI/CD.', NOW() + INTERVAL 45 DAY),
('QA Engineer', 'Shopee', 'Vietnam', 'HCM', 'https://example.com/', 'Test automation.', NOW() + INTERVAL 25 DAY),
('System Architect', 'FPT Software', 'Vietnam', 'Da Nang', 'https://example.com/', 'Thiết kế hệ thống.', NOW() + INTERVAL 70 DAY),
('AI Engineer', 'Viettel AI', 'Vietnam', 'Hanoi', 'https://example.com/', 'Phát triển AI.', NOW() + INTERVAL 55 DAY),
('Fullstack Developer', 'Tiki', 'Vietnam', 'HCM', 'https://example.com/', 'Fullstack app.', NOW() + INTERVAL 65 DAY),

-- USA
('Java Engineer', 'Netflix', 'USA', 'San Francisco', 'https://example.com/', 'Java microservices.', NOW() + INTERVAL 45 DAY),
('React Developer', 'Airbnb', 'USA', 'New York', 'https://example.com/', 'React web app.', NOW() + INTERVAL 35 DAY),
('Cloud Engineer', 'Uber', 'USA', 'Seattle', 'https://example.com/', 'Cloud infra AWS.', NOW() + INTERVAL 60 DAY),
('ML Engineer', 'OpenAI', 'USA', 'San Francisco', 'https://example.com/', 'AI models.', NOW() + INTERVAL 80 DAY),
('Security Engineer', 'Tesla', 'USA', 'New York', 'https://example.com/', 'Cloud security.', NOW() + INTERVAL 40 DAY),
('Backend Engineer', 'Stripe', 'USA', 'Seattle', 'https://example.com/', 'Payment backend.', NOW() + INTERVAL 55 DAY),
('Frontend Engineer', 'Slack', 'USA', 'San Francisco', 'https://example.com/', 'React + TypeScript.', NOW() + INTERVAL 45 DAY),
('DevOps Engineer', 'Dropbox', 'USA', 'New York', 'https://example.com/', 'CI/CD pipelines.', NOW() + INTERVAL 70 DAY),
('QA Automation', 'LinkedIn', 'USA', 'Seattle', 'https://example.com/', 'E2E test.', NOW() + INTERVAL 50 DAY),
('Mobile Engineer', 'Twitter', 'USA', 'San Francisco', 'https://example.com/', 'iOS/Android dev.', NOW() + INTERVAL 65 DAY),

-- Japan
('Backend Engineer', 'Rakuten', 'Japan', 'Tokyo', 'https://example.com/', 'E-commerce backend.', NOW() + INTERVAL 45 DAY),
('AI Researcher', 'Sony AI', 'Japan', 'Osaka', 'https://example.com/', 'AI research.', NOW() + INTERVAL 90 DAY),
('Mobile Developer', 'Line', 'Japan', 'Tokyo', 'https://example.com/', 'Mobile messenger.', NOW() + INTERVAL 55 DAY),
('Frontend Engineer', 'Nintendo', 'Japan', 'Osaka', 'https://example.com/', 'Game UI.', NOW() + INTERVAL 65 DAY),
('Cloud Architect', 'Fujitsu', 'Japan', 'Tokyo', 'https://example.com/', 'Cloud infra.', NOW() + INTERVAL 70 DAY),
('QA Engineer', 'Hitachi', 'Japan', 'Osaka', 'https://example.com/', 'Automation testing.', NOW() + INTERVAL 30 DAY),
('Java Developer', 'Panasonic', 'Japan', 'Tokyo', 'https://example.com/', 'ERP system.', NOW() + INTERVAL 50 DAY),
('System Engineer', 'NEC', 'Japan', 'Tokyo', 'https://example.com/', 'Enterprise solutions.', NOW() + INTERVAL 40 DAY),
('Fullstack Engineer', 'SoftBank', 'Japan', 'Osaka', 'https://example.com/', 'Fullstack dev.', NOW() + INTERVAL 60 DAY),
('DevOps Engineer', 'Toshiba', 'Japan', 'Tokyo', 'https://example.com/', 'DevOps infra.', NOW() + INTERVAL 55 DAY),

-- Germany
('Java Developer', 'SAP', 'Germany', 'Berlin', 'https://example.com/', 'ERP backend.', NOW() + INTERVAL 70 DAY),
('Data Engineer', 'Siemens', 'Germany', 'Munich', 'https://example.com/', 'Big data.', NOW() + INTERVAL 80 DAY),
('Security Engineer', 'Bosch', 'Germany', 'Berlin', 'https://example.com/', 'Security infra.', NOW() + INTERVAL 65 DAY),
('Cloud Engineer', 'Deutsche Telekom', 'Germany', 'Munich', 'https://example.com/', 'Cloud infra.', NOW() + INTERVAL 75 DAY),
('AI Engineer', 'BMW', 'Germany', 'Munich', 'https://example.com/', 'AI cars.', NOW() + INTERVAL 85 DAY),
('Mobile Engineer', 'Mercedes', 'Germany', 'Berlin', 'https://example.com/', 'Car apps.', NOW() + INTERVAL 55 DAY),
('Frontend Engineer', 'Allianz', 'Germany', 'Berlin', 'https://example.com/', 'Insurance portal.', NOW() + INTERVAL 65 DAY),
('Backend Engineer', 'Adidas', 'Germany', 'Munich', 'https://example.com/', 'E-commerce backend.', NOW() + INTERVAL 60 DAY),
('System Architect', 'Volkswagen', 'Germany', 'Berlin', 'https://example.com/', 'Car systems.', NOW() + INTERVAL 70 DAY),
('QA Engineer', 'Lufthansa', 'Germany', 'Munich', 'https://example.com/', 'QA airline systems.', NOW() + INTERVAL 45 DAY),

-- India
('Java Developer', 'Infosys', 'India', 'Bangalore', 'https://example.com/', 'Enterprise Java.', NOW() + INTERVAL 40 DAY),
('Fullstack Developer', 'TCS', 'India', 'Delhi', 'https://example.com/', 'Web apps.', NOW() + INTERVAL 50 DAY),
('AI Engineer', 'Wipro', 'India', 'Bangalore', 'https://example.com/', 'AI solutions.', NOW() + INTERVAL 70 DAY),
('Mobile Developer', 'Flipkart', 'India', 'Delhi', 'https://example.com/', 'Mobile shopping.', NOW() + INTERVAL 65 DAY),
('DevOps Engineer', 'Ola', 'India', 'Bangalore', 'https://example.com/', 'Cloud + CI/CD.', NOW() + INTERVAL 55 DAY),
('QA Engineer', 'Paytm', 'India', 'Delhi', 'https://example.com/', 'Payment testing.', NOW() + INTERVAL 45 DAY),
('Backend Engineer', 'Zomato', 'India', 'Bangalore', 'https://example.com/', 'Food backend.', NOW() + INTERVAL 60 DAY),
('Frontend Engineer', 'Swiggy', 'India', 'Delhi', 'https://example.com/', 'React frontend.', NOW() + INTERVAL 70 DAY),
('System Architect', 'HCL', 'India', 'Bangalore', 'https://example.com/', 'System design.', NOW() + INTERVAL 80 DAY),
('Data Scientist', 'Tech Mahindra', 'India', 'Delhi', 'https://example.com/', 'ML + AI.', NOW() + INTERVAL 75 DAY);

-- =========================
-- Seed Job-Skills Mapping (random sample)
-- =========================
INSERT INTO job_skills (job_id, skill_id) VALUES
                                              (1,1),(1,2),(1,6),
                                              (2,3),(2,4),
                                              (3,1),(3,6),(3,9),
                                              (4,11),(4,12),
                                              (5,13),(5,14),
                                              (6,8),(6,9),(6,10),
                                              (7,15),(7,6),
                                              (8,1),(8,2),(8,10),
                                              (9,11),(9,12),(9,8),
                                              (10,1),(10,3),(10,6),
                                              (11,1),(11,2),(11,8),
                                              (12,3),(12,5),
                                              (13,6),(13,7),(13,8),
                                              (14,11),(14,12),(14,6),
                                              (15,8),(15,9),(15,10),
                                              (16,1),(16,2),(16,6),
                                              (17,3),(17,4),(17,5),
                                              (18,8),(18,9),(18,10),
                                              (19,15),(19,6),
                                              (20,13),(20,14),
                                              (21,1),(21,2),
                                              (22,11),(22,12),
                                              (23,3),(23,4),
                                              (24,8),(24,9),(24,10),
                                              (25,15),(25,6),
                                              (26,13),(26,14),
                                              (27,1),(27,3),(27,6),
                                              (28,8),(28,9),
                                              (29,11),(29,12),
                                              (30,15),(30,6),
                                              (31,1),(31,2),(31,6),
                                              (32,3),(32,5),
                                              (33,11),(33,12),
                                              (34,8),(34,9),(34,10),
                                              (35,15),(35,6),
                                              (36,1),(36,3),(36,6),
                                              (37,13),(37,14),
                                              (38,11),(38,12),
                                              (39,8),(39,9),(39,10),
                                              (40,15),(40,6),
                                              (41,1),(41,2),(41,6),
                                              (42,3),(42,4),
                                              (43,11),(43,12),
                                              (44,8),(44,9),(44,10),
                                              (45,15),(45,6),
                                              (46,1),(46,2),
                                              (47,11),(47,12),
                                              (48,13),(48,14),
                                              (49,8),(49,9),(49,10),
                                              (50,15),(50,6);