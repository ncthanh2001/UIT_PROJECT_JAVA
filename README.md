# Smart Job Application Tracker

**Smart Job Application Tracker** là một ứng dụng desktop được phát triển bằng Java Swing giúp người dùng quản lý quá trình nộp hồ sơ xin việc một cách hiệu quả và trực quan.

## 🧠 Tính năng chính

- 📌 Theo dõi danh sách các công việc đã ứng tuyển
- 📝 Ghi chú chi tiết cho từng công việc
- 📊 Tạo báo cáo thống kê quá trình xin việc
- 📅 Nhắc lịch phỏng vấn
- 🤖 Gợi ý công việc phù hợp dựa trên kỹ năng người dùng (AI đơn giản)

## 🛠️ Công nghệ sử dụng

| Thành phần | Công nghệ |
|------------|-----------|
| Ngôn ngữ    | Java 11 |
| Giao diện   | Java Swing |
| Kết nối DB  | JDBC |
| Cơ sở dữ liệu | MySQL |
| Build tool | Maven |
| ORM tự viết | DAO + Service theo mô hình MVC |
| Thư viện hỗ trợ | Lombok (giảm boilerplate code) |
| AI Gợi ý    | Logic đơn giản dựa trên từ khóa kỹ năng |

## 📁 Cấu trúc dự án

```text
org.smart_job
├── common # Response wrapper dùng trong service
├── dao # DAO interface
│ └── impl # DAO implementation
├── entity # Entity class (User, BaseEntity, etc.)
├── service # Service interface
│ └── impl # Service implementation
├── ulties # JdbcUtils và tiện ích khác
└── view # Giao diện Swing (JFrame, JTable, etc.)
```

## ⚙️ Cài đặt & chạy

### 1. Yêu cầu

- Java 11 trở lên
- Maven
- MySQL (tạo sẵn database `uit_smart_job`)

### 2. Cài đặt MySQL bằng Docker

1. Cài đặt **Docker**
2. Vào thư mục của dự án và chạy command
   ```bash
    docker compose up -d
    ```
   **Lưu ý**: root password là
   ```text
   123456
   ```
3. Truy cập vào MySQL bên trong container
   ```bash
   docker exec -it [container_id] mysql -uroot -p
   ```
4. Kiểm tra Database
   ```mysql
   SHOW DATABASES;
   ```
   Output như sau
   ```text
   mysql> SHOW DATABASES;
   +--------------------+
   | Database           |
   +--------------------+
   | information_schema |
   | mysql              |
   | performance_schema |
   | sys                |
   | uit_smart_job      |
   +--------------------+
   ```

### 3. Cấu hình DB

Mở file `JdbcUtils.java` và sửa thông tin:

```java
private static final String URL = "jdbc:mysql://localhost:3306/uit_smart_job";
private static final String USER = "root";
private static final String PASSWORD = "123456";
```