## About ChattingAppBackend

ChattingAppBackend is the server-side component of the ChattingApp project, built using Spring Boot. It provides RESTful APIs for real-time messaging, user authentication, and other backend services required by the frontend application.

### Features

- **Real-time Messaging**: Handle real-time message sending and receiving.
- **User Authentication**: Secure login and registration system.
- **Database Management**: Manage user data and chat history.
- **Scalability**: Designed to handle multiple concurrent users efficiently.
- **Security**: Implement security best practices to protect user data.

### Technologies Used

- **Spring Boot**: Framework for building Java-based backend applications.
- **Spring Data JPA**: For database interactions.
- **Spring Security**: For authentication and authorization.
- **WebSocket**: For real-time communication.
- **MySQL**: Relational database for storing user and chat data.

### Getting Started

To get started with the project, clone the repository and build the project using Maven:

```bash
git clone https://github.com/yourusername/chatting_app_backend.git
cd chatting_app_backend
mvn clean install
```

After building the project, you can run the application using:

```bash
mvn spring-boot:run
```

### Frontend

The frontend for this project is built using Angular. You can find the frontend repository [here](https://github.com/SahilDhanwani/chatting_app_frontend).

### Contributing

Contributions are welcome! If you have any ideas, suggestions, or bug reports, please open an issue or submit a pull request. Make sure to follow the project's code of conduct.

### License

This project is licensed under the MIT License. See the [LICENSE](LICENSE) file for more details.