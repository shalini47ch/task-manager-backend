# TaskHub — Secure & Modern Task Manager

## 🎥 Demo Video

👉 [Click here to watch the demo](./recording-2025-06-19-171619_pUS7Scad.mp4)



## 1.Overview

**TaskHub** is a full-stack Task Management Application that helps users create, manage, and track tasks securely and efficiently.  
It features a responsive **React + Tailwind CSS** frontend, a robust **Spring Boot** backend with **JWT Authentication**, and **MongoDB** for storage — using **DBRef** to maintain clear relationships between tasks and their owners.

## 2.Tech Stack

- **Frontend:** React.js, Tailwind CSS
- **Backend:** Spring Boot, Spring Security, JWT
- **Database:** MongoDB with DBRef
- **Testing:** Postman

## 3.Features

### 1. Authentication & Security
- User registration with password hashing.
- JWT-based login for protected routes.
- Role-based permissions: Users & Admins.
- Logout to safely end session.

### 2.Task Management
- Add tasks with title, description, and status.
- Update task details and status (*To Do*, *In Progress*, *Done*).
- Delete tasks.
- Tasks are linked to users with MongoDB `DBRef`.

### 3.Role-Based Access
- **Users:** View, create, update, and delete *own* tasks.
- **Admins:** View *all tasks* by all users (read-only).

### 4.User Interface
- Modern, clean design using Tailwind CSS.
- Dashboard shows tasks grouped by status.
- Intuitive buttons for editing and deleting.
- Responsive layout.

### 5.Session Handling
- JWT stored securely.
- Logout clears token and redirects safely.

### 6.Demo Covers
- Register & login flow.
- Adding, editing, and deleting tasks.
- Role-based views: User vs Admin.
- Logout.
- Proper error handling for unauthorized actions.

### 7.Screenshots 
## Register

![image](https://github.com/user-attachments/assets/40a97de0-335f-4613-b72f-084595e78bb9)

## Login
![image](https://github.com/user-attachments/assets/c4000783-c558-4905-a384-fa3e77de5fee)

## Users view where he can edit tasks add tasks or delete those as well
![image](https://github.com/user-attachments/assets/272756d1-05bb-493c-914c-11fc94fcc8a4)

## Admins View(He can view all the tasks but cant modify any of those)

![image](https://github.com/user-attachments/assets/cc045624-bae3-494c-ae2c-fc6f0bd12373)

![image](https://github.com/user-attachments/assets/eb597cd7-69c0-4db8-9ffb-622883ef7e19)

## Edit Task 

![image](https://github.com/user-attachments/assets/9c428801-2cae-4d3f-a2bf-483d981230cc)


## 8. How DBRef Works

Each task references its creator’s user document using MongoDB `DBRef`.  
Example:
```json
{
  "_id": ObjectId("..."),
  "title": "Finish Module",
  "description": "Implement JWT filter",
  "status": "IN_PROGRESS",
  "user": DBRef("user", ObjectId("..."))
}```



| Method   | Endpoint      | Description                                   | Access        |
| -------- | ------------- | --------------------------------------------- | ------------- |
| `POST`   | `/register`   | Register new user                             | Public        |
| `POST`   | `/login`      | Login & receive JWT                           | Public        |
| `GET`    | `/tasks`      | Users: view own tasks, Admins: view all tasks | Authenticated |
| `POST`   | `/tasks`      | Create task                                   | User only     |
| `PUT`    | `/tasks/{id}` | Update task                                   | User only     |
| `DELETE` | `/tasks/{id}` | Delete task                                   | User only     |







