# 🎬 Streaming Platform – README (English Version)

This project is a **complete streaming platform**, built with modern technologies and focused on performance, scalability, and an exceptional user experience.

---

## 🚀 Technologies Used

### **Backend – Spring Boot (Java)**

* **Spring Boot** as the core backend framework.
* **Netflix DGS GraphQL** for building schemas, queries, and mutations.
* **OAuth2 with Google** for secure user authentication.
* **DataLoaders** to optimize resolvers and **avoid N+1 problems**.
* **Cursor Connections** for efficient pagination of videos and comments.
* **Video upload** with local/external storage support.
* **HLS (HTTP Live Streaming)** for efficient video delivery.

### **Video Processing – Kafka**

* **Apache Kafka** used as a message queue for video processing tasks.
* **Consumers** perform:

  * Video transcoding.
  * Manifest generation.
  * Video segmentation.
  * Status updates in the backend.
* **Docker Compose** running services such as:

  * Kafka
  * Zookeeper
  * Database

### **Frontend – React**
## 🔗 Frontend Repository
[![Frontend](https://img.shields.io/badge/Repo-Frontend-blue?style=for-the-badge)](https://github.com/JoaoUntura/streaming-plataform-frontend).

* **React** with GraphQL integration using **Apollo Client**.
* HLS-compatible video player.
* Optimized data fetching with caching and cursor-based pagination.

---

## 🧱 System Architecture

```
React (Apollo Client)   -->   Spring Boot (GraphQL DGS)   -->   Database
                                    |                          
                                    |                          
                                  Kafka  <---- Consumers (Transcoding)
                                    |
                               HLS Segments / Manifest
```

---

## 🎞️ Video Upload & Processing Workflow

1. User uploads a video.
2. Backend stores the file and sends a message to **Kafka**.
3. A Consumer receives the event and processes the video:

   * Transcodes the video.
   * Generates **.ts segments**.
   * Generates the **.m3u8 manifest**.
4. Backend updates the video status through GraphQL.
5. Frontend displays the video using an HLS player.

---

## 📁 Simplified Project Structure

```
backend/
  ├── src/main/java/.../controllers
  ├── src/main/java/.../services
  ├── src/main/java/.../security
  ├── videos/
  ├── segments/
  ├── manifests/



infrastructure/
  ├── docker-compose.yml  (Kafka, Database)
```

---

## 🔐 Google OAuth2 Authentication

Implemented using Spring Security OAuth2 for fast, secure login.

---

## 📦 Docker & Infrastructure

* Kafka running in Docker.
* Database containerized.
* Backend also supports containerization.

Run the infrastructure with:

```bash
docker-compose up -d
```

---

## 📡 GraphQL API

Includes:

* **Queries** for videos, comments, and users.
* **Mutations** for uploads, creating comments, etc.
* **DataLoader integration** for optimized relational fetching.

---

## ▶️ HLS Player

The frontend uses an HLS-compatible player to load the generated manifest.

---

## ✅ Current Status

* ✅ Backend fully functional with GraphQL + OAuth2
* ✅ Optimized upload flow
* ✅ Kafka with video processing consumer
* ✅ HLS working end-to-end
* ✅ React + Apollo Client frontend

---

## 📌 Future Improvements

* CDN for segment delivery
* Distributed caching (Redis)
* Observability with Prometheus/Grafana
* Support for adaptive bitrate streaming up to 4K

---

## 👨‍💻 Author

Developed by **João Untura**.
