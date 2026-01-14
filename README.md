# 🎓 Campus Complaint Management System – Backend

A secure backend application built using **Spring Boot** to manage campus complaints raised by students and handled by admins.

---

## 🚀 Features

### 👨‍🎓 Student
- Register & Login (JWT Authentication)
- Raise complaints
- View own complaints
- Secure password storage using BCrypt

### 🧑‍💼 Admin
- Login with role-based access
- View all complaints
- Update complaint status

---

## 🔐 Security
- Spring Security
- JWT Authentication & Authorization
- Role-based access control (`STUDENT`, `ADMIN`)
- BCrypt password encoding

---

## 🛠️ Tech Stack
- Java 17
- Spring Boot
- Spring Security
- Spring Data JPA
- JWT (jjwt)
- MySQL
- Maven
- Lombok
- Postman (Testing)

---

## 📂 Project Structure

controller/
service/
repository/
entity/
security/
dto/
exception/

---

## 🧪 API Testing
All APIs tested using **Postman**.

- Auth APIs
- Student APIs
- Complaint APIs
- Admin APIs

---

## 🗄️ Database
- MySQL
- JPA/Hibernate ORM
- Proper entity relationships

---

## ▶️ How to Run

1. Clone the repository:
```bash
git clone https://github.com/Arijitcoding-2005/campus-complaint-management-backend.git
```
2. Configure application.properties:
```bash
   spring.datasource.url=jdbc:mysql://localhost:3306/complaint_db
   spring.datasource.username=root
   spring.datasource.password=your_password
   ```
3. Run the application:
```bash
mvn spring-boot:run
```
## 📌 Future Enhancements

- Frontend integration

- Email notifications

- File uploads

- Deployment (AWS / Render)

---
## 👤 Author

### Arijit Saha
Backend Developer | Java | Spring Boot