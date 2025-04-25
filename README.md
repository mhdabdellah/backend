markdown
# Complete API Documentation
**User Management System**  
**Base URL:** `https://backend-okyl.onrender.com`

## Developer Information
- **Name:** Mohamed Abdellahi Sidi Mohamed Blal
- **Email:** [mhdabdellahi0@gmail.com](mailto:mhdabdellahi0@gmail.com)
- **LinkedIn:** [Mohamed's Profile](https://linkedin.com/in/mohamed-abdellahi)

## Setup

### Backend
1. `cd backend`
2. `mvn clean install`
3. `mvn spring-boot:run`
4. Server runs on `http://localhost:8080`


# 🧾 API Documentation

## 🔑 Authentication Endpoints

### POST `/api/auth/register`
Register a new user account.

**Request Body:**
```json
{
  "username": "nouakchott_user",
  "password": "SaharaSecure!123",
  "role": "USER",
  "firstName": "Ahmed",
  "lastName": "Mahmoud",
  "email": "ahmed.mahmoud@mr.example"
}
```

| Field     | Type    | Required |
|-----------|---------|----------|
| username  | String  | Yes      |
| password  | String  | Yes      |
| role      | String  | Yes      |
| firstName | String  | Yes      |
| lastName  | String  | Yes      |
| sex       | String  | No       |
| age       | Integer | No       |
| email     | String  | Yes      |

---

### POST `/api/auth/login`
Authenticate user and receive a JWT token.

**Request Body:**
```json
{
  "username": "nouakchott_user",
  "password": "SaharaSecure!123"
}
```

---

### POST `/api/auth/logout`
Invalidate the current session.

---

## 👤 User Management Endpoints

### GET `/api/users/me`
Get current user details.

**Response Example:**
```json
{
  "username": "nouakchott_user",
  "role": "USER",
  "profile": {
    "firstName": "Ahmed",
    "lastName": "Mahmoud",
    "email": "ahmed.mahmoud@mr.example",
    "age": 28,
    "sex": "Male"
  }
}
```

---

### POST `/api/users/change-password`
Change the current user's password.

**Request Body:**
```json
{
  "oldPassword": "oldPassword123",
  "newPassword": "newSecurePassword456"
}
```

---

### PUT `/api/users/change-username`
Update the current user's username.

**Request Body:**
```json
{
  "newUsername": "updated_nouakchott"
}
```

---

### DELETE `/api/users/delete-account`
Permanently delete the user account.

**Response:**
```json
{
  "message": "User account deleted successfully"
}
```

---

## 🛡️ Admin Management Endpoints

### GET `/api/admin/users`
Search users with pagination.

**Query Parameters:**

| Parameter | Type   | Description                     |
|-----------|--------|---------------------------------|
| page      | int    | Page number (default: 1)        |
| size      | int    | Items per page (default: 10)    |
| search    | String | Search term                     |
| role      | String | Filter by role                  |

**Response:**
```json
{
  "data": [
    {
      "username": "admin_user",
      "role": "ADMIN",
      "profile": {
        "firstName": "Admin",
        "lastName": "User",
        "email": "admin@mr.example"
      }
    }
  ],
  "count": 1
}
```

---

### POST `/api/admin/users/{username}/reset-password`
Admin resets a user's password.

**Request Body:**
```json
{
  "newPassword": "newSecurePassword456"
}
```

---

### PUT `/api/admin/users/change-username`
Admin changes any user's username.

**Request Body:**
```json
{
  "newUsername": "updated_nouakchott"
}
```

---

### DELETE `/api/admin/users/{username}`
Admin deletes a user account.

**Response:**
```json
{
  "message": "User account deleted successfully"
}
```

---

### © 2025 Mohamed Abdellahi Sidi Mohamed Blal