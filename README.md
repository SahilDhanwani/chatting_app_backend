# ChattingAppBackend

ChattingAppBackend is the server-side component of the **ChattingApp** project, designed to provide essential backend services such as real-time messaging, user authentication, and database management. It is built using **Spring Boot** and integrates seamlessly with the frontend, allowing users to chat in real-time.

---

## Live Demo
- **Live Demo**: The project is live at www.chat-zone.tech.

## Features

- **Real-time Messaging**: Enable instant message sending and receiving with WebSocket support.
- **User Authentication**: Secure login and registration system with JWT-based authentication.
- **Database Management**: Efficient handling of user data and chat history with **MySQL**.
- **Scalability**: Optimized for handling multiple concurrent users, ensuring smooth operation even with high traffic.
- **Security**: Implemented security best practices using **Spring Security** to protect user data.
- **Design Principles**: The project is built following SOLID principles and clean code practices, ensuring maintainability and scalability.
- **MVC Design Pattern**: The backend follows the Model-View-Controller (MVC) design pattern, organizing the code into controller, service, model, and repository layers.
- **Guest Login**: Allows users to access the chat application without the need for registration or login.
---

## Technologies Used

- **Spring Boot**: A framework for building Java-based backend applications.
- **Spring Data JPA**: A tool for simplifying database operations and interactions.
- **Spring Security**: Ensures secure authentication and authorization mechanisms.
- **WebSocket**: Enables real-time communication for messaging functionality.
- **MySQL**: Relational database for storing and managing user and chat data.
- **RestFul APIs**: Used for creating and managing RESTful web services.

---

## Getting Started

To get started with the **ChattingAppBackend**, follow the steps below:

### Prerequisites

Before you begin, ensure you have the following installed:

- **Java 11 or higher**
- **Maven**
- **MySQL** (or a similar relational database setup)

### Installation

1. Clone the repository:

    ```bash
    git clone https://github.com/yourusername/chatting_app_backend.git
    cd chatting_app_backend
    ```

2. Build the project using Maven:

    ```bash
    mvn clean install
    ```

### Running the Application

To run the application locally, use the following command:

```bash
mvn spring-boot:run

```

### Frontend

The frontend for this project is built using Angular. You can find the frontend repository [here](https://github.com/SahilDhanwani/chatting_app_frontend).

### Contributing

Contributions are welcome! If you have any ideas, suggestions, or bug reports, please open an issue or submit a pull request. Make sure to follow the project's code of conduct.