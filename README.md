# 🗳️ Blockchain Voting System

A secure digital voting system built using **Spring Boot (Backend)** and **HTML/CSS/JavaScript (Frontend)**.  
The system simulates blockchain-based voting logic with candidate management, voter authentication, and live result calculation.

---

## 🚀 Features

- 👤 Voter Registration & Login
- 🧾 Candidate Management (Admin/demo feature)
- 🗳️ Cast Vote securely
- 📊 Live Result Calculation
- 🏆 Party-wise Winner Selection
- 🔐 Session handling using localStorage (frontend)
- 🌐 REST API-based backend (Spring Boot)
- ⚡ Real-time candidate update in dropdown

---

## 🛠️ Tech Stack

### Backend:
- Java
- Spring Boot
- Spring MVC
- REST APIs

### Frontend:
- HTML
- CSS
- JavaScript (Fetch API)

### Database:
- H2 / MySQL (based on your config)

---

## 📁 Project Structure
src/main/java/com/voting/system
├── controller
├── service
├── entity
├── repository
├── blockchain
└── config

---

## ▶️ How to Run the Project

### 1. Clone Repository
```bash
git clone https://github.com/Khushi-wadhera/blockchain-voting-system.git
2. Open Backend

Open in IntelliJ / Eclipse / VS Code

3. Run Spring Boot
mvn spring-boot:run

Server will start at:

http://localhost:8081
4. Open Frontend

Open:

src/main/resources/static/index.html

in browser.

🔗 API Endpoints
Method	Endpoint	Description
POST	/voters	Register voter
POST	/voters/login	Login voter
POST	/voters/vote	Cast vote
GET	/candidates	Get all candidates
POST	/candidates/add	Add candidate
GET	/results	Get results
🏆 Result System Logic
Votes are counted per candidate
Total votes are calculated
Winner is determined based on:
Highest votes OR
Party-wise aggregation (if enabled)
📌 Future Improvements
🔐 JWT Authentication
🧱 True blockchain hashing of votes
📡 Live voting updates (WebSocket)
👨‍💻 Admin dashboard
📊 Graph-based result visualization
👨‍💻 Author
Khushi Wadhera

GitHub: https://github.com/Khushi-wadhera

