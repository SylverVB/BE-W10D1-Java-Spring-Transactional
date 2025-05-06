### **Comprehensive Guide to Spring Annotations with Detailed Explanations and Examples**  

Spring annotations help in defining the behavior of different components in a Spring application. They simplify development by allowing **dependency injection, transaction management, data persistence, security, and scheduling** without requiring complex XML configurations. Below is a **detailed explanation** of each commonly used Spring annotation with examples.

---

## **1️⃣ Core Spring Annotations**
These annotations define **Spring-managed components** and facilitate dependency injection.

### **🔹 `@Component`**
- Marks a **Java class as a Spring-managed bean**.
- This tells Spring to create an **object (bean)** of this class and manage its lifecycle.
- It is a generic annotation; `@Service`, `@Repository`, and `@Controller` are specialized versions of `@Component`.

#### **Example**
```java
@Component
public class MyComponent {
    public void doSomething() {
        System.out.println("Hello from MyComponent!");
    }
}
```
Here, `MyComponent` is now a **Spring-managed bean**.

---

### **🔹 `@Service`**
- Specialized version of `@Component`.
- Used to **define service layer classes** that contain **business logic**.
- Helps separate business logic from controller and repository.

#### **Example**
```java
@Service
public class UserService {
    public String getUserInfo() {
        return "User Info";
    }
}
```
Spring will **detect** this service and allow it to be injected into other components.

---

### **🔹 `@Repository`**
- Specialized version of `@Component` for **DAO (Data Access Object)** classes.
- Helps in **automatic exception translation**.

#### **Example**
```java
@Repository
public interface UserRepository extends JpaRepository<User, Long> {
}
```
Spring automatically **implements** this interface and provides database operations.

---

### **🔹 `@Controller`**
- Specialized version of `@Component` for **handling web requests in Spring MVC**.
- Works with **view templates** (like Thymeleaf, JSP).

#### **Example**
```java
@Controller
public class WebController {
    @GetMapping("/home")
    public String home() {
        return "home"; // Returns home.html
    }
}
```
Spring will **map** `/home` to the corresponding **home.html** template.

---

### **🔹 `@RestController`**
- Combination of `@Controller` and `@ResponseBody`.
- Used to create **RESTful web services** that return **JSON**.

#### **Example**
```java
@RestController
public class ApiController {
    @GetMapping("/api/user")
    public String getUser() {
        return "User Data"; // Returns JSON response
    }
}
```
This API will return **raw JSON** instead of a view template.

---

## **2️⃣ Spring Boot Annotations**
These annotations are used **specifically in Spring Boot** to configure the application.

### **🔹 `@SpringBootApplication`**
- The **main annotation** in Spring Boot applications.
- Combines three annotations:
  - `@Configuration` - Declares Spring configuration.
  - `@EnableAutoConfiguration` - Enables automatic configuration.
  - `@ComponentScan` - Scans components automatically.

#### **Example**
```java
@SpringBootApplication
public class MyApp {
    public static void main(String[] args) {
        SpringApplication.run(MyApp.class, args);
    }
}
```
Spring Boot **auto-configures** the entire application.

---

### **🔹 `@Value`**
- Injects values from **application.properties** into Java fields.

#### **Example**
```java
@Value("${app.name}")
private String appName;
```
If `application.properties` contains:
```properties
app.name=My Awesome App
```
Then, `appName` will be **"My Awesome App"**.

---

## **3️⃣ Spring Dependency Injection Annotations**
These annotations handle **dependency injection**.

### **🔹 `@Autowired`**
- Automatically injects a **bean** (Spring-managed component).

#### **Example**
```java
@Service
public class UserService {
    public String getUser() {
        return "John Doe";
    }
}

@Component
public class UserController {
    @Autowired
    private UserService userService;

    public void showUser() {
        System.out.println(userService.getUser());
    }
}
```
Spring **automatically injects** `UserService` into `UserController`.

---

### **🔹 `@Qualifier`**
- Used when **multiple beans of the same type** exist.

#### **Example**
```java
@Component("bean1")
public class BeanOne { }

@Component("bean2")
public class BeanTwo { }

@Component
public class MyComponent {
    @Autowired
    @Qualifier("bean2")
    private BeanTwo myBean;
}
```
Spring injects **`bean2`** instead of `bean1`.

---

## **4️⃣ Spring Data JPA Annotations**
These annotations are used for **database interaction**.

### **🔹 `@Entity`**
- Marks a **class as a database entity** (table).

#### **Example**
```java
@Entity
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
}
```
Now, `User` is a **database table**.

---

### **🔹 `@Table`**
- Allows **custom table names**.

#### **Example**
```java
@Entity
@Table(name = "users_table")
public class User { }
```
Maps the entity to **"users_table"**.

---

### **🔹 `@OneToMany` & `@ManyToOne`**
- Defines **relationships** between tables.

#### **Example**
```java
@Entity
public class Album {
    @OneToMany(mappedBy = "album", cascade = CascadeType.ALL)
    private List<Song> songs;
}

@Entity
public class Song {
    @ManyToOne
    @JoinColumn(name = "album_id")
    private Album album;
}
```
- `@OneToMany(mappedBy = "album")` means **one album** has **many songs**.
- `@ManyToOne` means **many songs** belong to **one album**.

---

### **🔹 `@Query` & `@Param`**
- Used for **custom database queries**.

#### **Example**
```java
@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    @Query("SELECT u FROM User u WHERE u.name = :name")
    User findByName(@Param("name") String name);
}
```
Finds a user **by name**.

---

## **5️⃣ Spring Transaction Management**
### **🔹 `@Transactional`**
- Ensures all database operations inside a method **execute as a single transaction**.

#### **Example**
```java
@Service
public class UserService {
    @Transactional
    public void updateUser(Long id, String newName) {
        User user = userRepository.findById(id).orElseThrow();
        user.setName(newName);
    }
}
```
If an error occurs, **all changes are rolled back**.

---

## **6️⃣ Spring Security Annotations**
These annotations are used for **authentication and authorization**.

### **🔹 `@PreAuthorize`**
- Restricts access to methods based on **roles**.

#### **Example**
```java
@PreAuthorize("hasRole('ADMIN')")
public void deleteUser(Long id) { }
```
Only **ADMIN users** can delete users.

---

### **🔹 `@EnableWebSecurity`**
- Enables **Spring Security** in the application.

#### **Example**
```java
@Configuration
@EnableWebSecurity
public class SecurityConfig { }
```
---

## **7️⃣ Spring Scheduling Annotations**
### **🔹 `@Scheduled`**
- Runs tasks at **fixed intervals**.

#### **Example**
```java
@Scheduled(fixedRate = 5000)
public void runTask() {
    System.out.println("Running every 5 seconds");
}
```
This **executes every 5 seconds**.

---

## **Final Thoughts**
Spring annotations make development **easier** and **more maintainable** by reducing boilerplate code.