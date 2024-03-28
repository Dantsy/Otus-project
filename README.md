# Проект TaskManager
## Выпускная работа с курса Otus.Java Developer.Professional.

## Описание
Проект Task Manager - это приложение для управления задачами, позволяющее создавать, редактировать, удалять и отмечать задачи как выполненные. 
Также предусмотрена возможность добавлять заметки к задачам.

# Структура проекта TaskManager

```plaintext
/ru.otus.task.manager
├──postman-collections
│  └──otus-project.postman_collection.json
├── src
│   ├── main
│   │   ├── java
│   │   │   └── ru
│   │   │       └── otus
│   │   │           └── task
│   │   │               └── manager
│   │   │                   ├── config
│   │   │                   │   └── AppConfig.java
│   │   │                   ├── controllers
│   │   │                   │   ├── NoteController.java
│   │   │                   │   └── TaskController.java
│   │   │                   ├── dtos
│   │   │                   │   ├── CreateNoteDTO.java
│   │   │                   │   ├── CreateTaskDTO.java
│   │   │                   │   ├── ErrorResponse.java
│   │   │                   │   ├── NoteResponseDTO.java
│   │   │                   │   ├── TaskResponseDTO.java
│   │   │                   │   └── UpdateTaskDTO.java
│   │   │                   ├── entities
│   │   │                   │   ├── BaseEntity.java
│   │   │                   │   ├── NoteEntity.java
│   │   │                   │   └── TaskEntity.java
│   │   │                   ├── middleware
│   │   │                   │   └── LoggingInterceptor.java
│   │   │                   ├── repositories
│   │   │                   │   ├── NotesRepository.java
│   │   │                   │   └── TasksRepository.java
│   │   │                   ├── services
│   │   │                   │   ├── NoteService.java
│   │   │                   │   └── TaskService.java
│   │   │                   └── SpringTaskManagerApplication.java
│   │   └── resources
│   │       ├── application.properties
│   │       ├── application.yaml
│   │       ├── db
│   │       │   └── v1
│   │       │       └── V1__init.sql
│   │       └── META-INF
│   │           │── static
│   │           │   └── frontend
│   │           │       └── components
│   │           │           ├──about
│   │           │           │   ├── about.css
│   │           │           │   ├── about.html
│   │           │           │   └── about.js
│   │           │           │───assets
│   │           │           │   └── image.png
│   │           │           ├──contact
│   │           │           │   ├── contact.css
│   │           │           │   ├── contact.html
│   │           │           │   └── contact.js
│   │           │           ├──footer
│   │           │           │   ├── footer.css
│   │           │           │   ├── footer.html
│   │           │           │   └── footer.js
│   │           │           ├──header
│   │           │           │   ├── header.css
│   │           │           │   ├── header.html
│   │           │           │   └── header.js
│   │           │           ├──index
│   │           │           │   ├── index.css
│   │           │           │   ├── index.html
│   │           │           │   └── index.js
│   │           │           ├──scripts
│   │           │           │   └── script.js
│   │           │           ├──styles
│   │           │           │   └── style.css
│   │           │           ├── package.json
│   │           │           └── package-lock.json
│   │           │           └── webpack.config.js
│   │           │        
│   │           └──additional-spring-configuration-metadata.json
│   └── test
│       └── java
│           └── ru
│               └── otus
│                   └── task
│                       └── manager
│                           ├── repositories
│                           │   ├── NotesRepositoryTests.java
│                           │   └── TasksTests.java
│                           ├── services
│                           │   ├── NoteServiceTests.java
│                           │   └── TaskServiceTests.java
│                           └── SpringTaskManagerApplicationTests.java
├── build.gradle
├── package-lock.json
├── README.md
└── settings.gradle
```
## Технологии
- Java 17
- Spring Boot 2.7.8
- Spring Data JPA
- Node.js
- PostgreSQL
- Lombok
- Hibernate
- Jackson
- HTML
- CSS
- JavaScript

## Зависимости
- spring-boot-starter-data-jpa
- spring-boot-starter-validation
- spring-boot-starter-web
- org.projectlombok:lombok
- org.springframework.boot:spring-boot-devtools
- org.postgresql:postgresql
- spring-boot-starter-test

## Настройка
Клонируйте репозиторий:

```bash
"git clone https://github.com/dantsy/otus-project.git"
```

## Перейдите в директорию проекта:

```bash
cd otus-project
```

## Настройте файл application.properties или application.yml с параметрами подключения к базе данных PostgreSQL.

### Запустите приложение:

```bash
./gradlew bootRun
```

# Использование
## Приложение предоставляет следующие эндпоинты:

- GET /tasks - Получить список всех задач.

- POST /tasks - Создать новую задачу.

- GET /tasks/{id} - Получить задачу по идентификатору.

- PUT /tasks/{id} - Обновить задачу по идентификатору.

- DELETE /tasks/{id} - Удалить задачу по идентификатору.

- GET /tasks/{id}/notes - Получить список заметок для задачи.

- POST /tasks/{id}/notes - Добавить заметку к задаче.

- GET /tasks/{id}/notes/{noteId} - Получить заметку по идентификатору для задачи.

- PUT /tasks/{id}/notes/{noteId} - Обновить заметку по идентификатору для задачи.

- DELETE /tasks/{id}/notes/{noteId} - Удалить заметку по идентификатору для задачи.

# Тестирование
## Для тестирования приложения используйте:

```bash
./gradlew test
```

# Развертывание
## Для сборки и развертывания приложения используйте:

```bash
./gradlew build
java -jar build/libs/otus-project-0.0.1-SNAPSHOT.jar
```

# Лицензия:
## Проект распространяется под лицензией Copyright (c) [2024] [Dantsy][otus.ru].

# Контакты:
## Если у вас возникли вопросы или проблемы, пожалуйста, свяжитесь со мной по адресу [dantsy@yandex.ru].