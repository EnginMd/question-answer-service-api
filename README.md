## This project is a sample application built with Spring Boot, highlighting the following concepts. It represents the backend part of the question-and-answer full-stack example.
- RESTful api design
- GET, POST, PUT, DELETE endpoints
- Custom Exception Handling
- DTO usage (Request/Response classes)
- Database integration (MySQL)
- JPA / Hibernate
- Spring Security
  - JWT token generation and validation
  - Access token and refresh token generation and validation
  - Authentication filter implementation
  - Security configuration
  - Role based authorization (Only one role in this application)

Frontend repo of this app: https://github.com/EnginMd/question-answer-fe

## Project Description and Some Details

This application is designed to allow users to register and log in, ask questions, answer questions, and like posts. 
While some features are accessible without logging in, others require authentication.

### Entities:
- **User**: Stores information about registered users.

- **Post**: Represents a post created by a user.

- **Like**: Tracks likes given by users to posts.

- **Comment**: Stores comments written under posts.

- **RefreshToken**: Stores refresh tokens associated with users.

### Request/Response Examples for Some Endpoints

    http://localhost:8080/auth/login

Request:

```json
{
"userName": "user10",
"password": "password"
}
```

Response:
```json
{
"message": null,
"userId": 3,
"accessToken": "Bearer eyJhbGciOiJIUzUxMiJ9.eyJzdWIiOiIzIiwiaWF0IjoxNzU0MjMxNjE3LCJleHAiOjE3NTQyMzUyMTd9.deu0ZqgWxq34fJ5FAvzCnd81d8v3-AWX49kYSV5RM3HePPwXCRqNcOdnePYHU1IAU1Z8D9l7vC1tAWZOG4wcrw",
"refreshToken": "f2362f7d-0345-4115-8010-e3c6996c6015"
}
``` 
---

    http://localhost:8080/auth/register
Request:
```json
{
"userName": "userNew3",
"password": "p"
}
```
Response:
```json
{
"message": "User successfully registered.",
"userId": 14,
"accessToken": "Bearer eyJhbGciOiJIUzUxMiJ9.eyJzdWIiOiIxNCIsImlhdCI6MTc1NDE1MDM5NiwiZXhwIjoxNzU0MTUzOTk2fQ.AKOzCmfwoPoZ8aNFu8q6lqpPKAD9N5Mej89ckbzVZ5PcQ1OKh9fZ3BhlQ0pzKL5ZBMiWjv44Bynevof1UfC69A",
"refreshToken": "07febcae-2570-48e7-8ca6-0e284537b4e6"
}
```
---
    http://localhost:8080/posts

Response:
```json
[
  {
    "id": 1,
    "userId": 1,
    "userName": "engin1",
    "title": "Post1 deneme",
    "text": "Post1 açıklama metin",
    "postLikes": [
      {
        "id": 28,
        "userId": 1,
        "postId": 1
      },
      {
        "id": 30,
        "userId": 1,
        "postId": 1
      },
      {
        "id": 31,
        "userId": 1,
        "postId": 1
      },
      {
        "id": 32,
        "userId": 11,
        "postId": 1
      }
    ]
  },
  {
    "id": 2,
    "userId": 1,
    "userName": "engin1",
    "title": "Post2 deneme",
    "text": "Post2 açıklama",
    ......
```
---
    http://localhost:8080/like
Response:
```json
[
    {
        "id": 27,
        "userId": 1,
        "postId": 2
    },
    {
        "id": 28,
        "userId": 1,
        "postId": 1
    },
    {
        "id": 29,
        "userId": 1,
        "postId": 1
    },
    {
        "id": 30,
        "userId": 1,
        "postId": 1
    },
    {
        "id": 31,
        "userId": 1,
        "postId": 1
    },
    {
        "id": 32,
        "userId": 11,
        "postId": 1
    },
    {
        "id": 33,
        "userId": 11,
        "postId": 2
    }
]
```


