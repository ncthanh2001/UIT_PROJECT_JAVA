create database uit_smart_job;

use uit_smart_job;

-- Create users table
CREATE TABLE users (
                       id INT AUTO_INCREMENT PRIMARY KEY,
                       first_name VARCHAR(255),
                       last_name VARCHAR(255),
                       email VARCHAR(255) UNIQUE NOT NULL,
                       password VARCHAR(255),
                       avatar VARCHAR(500),
                       country VARCHAR(100),
                       dob DATE,
                       created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
                       updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP
);

-- Create skills table
CREATE TABLE skills (
                        id INT AUTO_INCREMENT PRIMARY KEY,
                        name VARCHAR(255) UNIQUE NOT NULL,
                        created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
                        updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP
);

-- Create user_skills junction table
CREATE TABLE user_skills (
                             user_id INT,
                             skill_id INT,
                             created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
                             updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
                             PRIMARY KEY (user_id, skill_id),
                             FOREIGN KEY (user_id) REFERENCES users(id) ON DELETE CASCADE,
                             FOREIGN KEY (skill_id) REFERENCES skills(id) ON DELETE CASCADE
);

-- Create cvs table
CREATE TABLE cvs (
                     id INT AUTO_INCREMENT PRIMARY KEY,
                     user_id INT,
                     file_path VARCHAR(500),
                     parsed_text TEXT,
                     created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
                     updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
                     FOREIGN KEY (user_id) REFERENCES users(id) ON DELETE CASCADE
);

-- Create jobs table
CREATE TABLE jobs (
                      id INT AUTO_INCREMENT PRIMARY KEY,
                      title VARCHAR(255),
                      company_name VARCHAR(255),
                      country VARCHAR(100),
                      city VARCHAR(100),
                      url VARCHAR(500),
                      description TEXT,
                      expiration_date TIMESTAMP NULL,
                      created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
                      updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP
);

-- Create job_skills junction table
CREATE TABLE job_skills (
                            job_id INT,
                            skill_id INT,
                            created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
                            updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
                            PRIMARY KEY (job_id, skill_id),
                            FOREIGN KEY (job_id) REFERENCES jobs(id) ON DELETE CASCADE,
                            FOREIGN KEY (skill_id) REFERENCES skills(id) ON DELETE CASCADE
);

-- Create job_application table with ENUM for status
CREATE TABLE job_application (
                                 id INT AUTO_INCREMENT PRIMARY KEY,
                                 user_id INT NOT NULL,
                                 job_id INT NULL,
                                 custom_title VARCHAR(255),
                                 custom_company VARCHAR(255),
                                 custom_description TEXT,
                                 custom_url VARCHAR(500),
                                 application_date TIMESTAMP NULL,
                                 status ENUM('wish_list', 'applied', 'interview', 'offer', 'rejected', 'accepted', 'archived') DEFAULT 'wish_list',
                                 notes TEXT,
                                 created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
                                 updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
                                 FOREIGN KEY (user_id) REFERENCES users(id) ON DELETE CASCADE,
                                 FOREIGN KEY (job_id) REFERENCES jobs(id) ON DELETE SET NULL
);

-- Create indexes for better performance
CREATE INDEX idx_users_email ON users(email);
CREATE INDEX idx_cvs_user_id ON cvs(user_id);
CREATE INDEX idx_job_application_user_id ON job_application(user_id);
CREATE INDEX idx_job_application_job_id ON job_application(job_id);
CREATE INDEX idx_job_application_status ON job_application(status);
CREATE INDEX idx_jobs_company_name ON jobs(company_name);
CREATE INDEX idx_jobs_country_city ON jobs(country, city);

-- Insert some sample skills
INSERT INTO skills (name) VALUES
                              ('Java'),
                              ('Spring Boot'),
                              ('MySQL'),
                              ('JavaScript'),
                              ('React'),
                              ('Node.js'),
                              ('Python'),
                              ('Docker'),
                              ('AWS'),
                              ('Git');

COMMIT;