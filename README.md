# **REST API Automation Framework**  

## **Overview**  
This framework is designed for **end-to-end REST API automation testing** using **Rest Assured**, following industry best practices. It enables **data-driven testing, dynamic JSON handling, OAuth authentication**, and seamless **CI/CD integration** for robust API validation.  

---

## **Technologies Used**  
- **Rest Assured** – For API testing and request validation  
- **Java** – Primary programming language  
- **TestNG** – For test execution, assertions, and reporting  
- **Maven** – Manages dependencies and builds  
- **Jenkins** – Continuous Integration (CI/CD) execution  
- **Apache POI** – For Excel-driven data-driven testing  

---

## **Key Features**  

### ✅ **End-to-End API Testing**  
- Comprehensive **E2E API automation testing** covering **GET, POST, PUT, and DELETE** requests.  
- Validates **response status codes, headers, and payloads** using assertions.  

### ✅ **Complex JSON Parsing**  
- Implements **advanced JSON parsing techniques** for handling **nested JSON structures**.  
- Extracts **dynamic values from API responses** for validations.  

### ✅ **Data-Driven Testing (Excel-Based)**  
- Supports **Excel-driven test data** for API request payloads.  
- Uses **Apache POI** to read test data dynamically.  

### ✅ **Dynamic JSON Payloads**  
- Implements **dynamic JSON payload generation** for flexible request handling.  
- Uses **parameterized JSON data** to test various input scenarios.  

### ✅ **Modular & Scalable Design**  
This framework follows a **structured and reusable test automation approach**, implementing **Page Object Model (POM) principles**:  
- **POJO Classes** – Represent API request/response payloads.  
- **Utility Classes (Utils)** – Contain reusable methods for API interactions.  
- **Resource Files** – Store API endpoint paths and request payloads.  

### ✅ **Specification Builder for API Requests & Responses**  
- Implements **Request and Response Spec Builder** for reusable and structured API test definitions.  
- Ensures **consistent headers, authentication, and request configurations**.  

### ✅ **OAuth & OAuth2 Authentication**  
- Implements **OAuth and OAuth2 mechanisms** for secure API authentication.  
- Automates **token generation and management** for API authentication.  

### ✅ **Serialization & Deserialization**  
- Uses **Jackson and Gson libraries** for **JSON serialization (Object to JSON) and deserialization (JSON to Object)**.  
- Helps in creating **structured request payloads** and validating **API responses**.  

### ✅ **TestNG Reporting**  
- Generates **detailed TestNG reports** to track **test execution results, assertions, and failures**.  
- Supports **logging and debugging** of API responses.  

### ✅ **Continuous Integration (CI/CD) Ready**  
- Seamlessly integrates with **Jenkins for automated test execution**.  
- Supports **Maven-based test execution** with CI/CD pipelines.  
- Generates **automated reports and logs** after each test run.  

---

## **Contributing**
Contributions are welcome! Please **submit a Pull Request (PR)** or open an **issue** for discussions.  

---

## **License**  
This project is **open-source software** licensed under the **MIT License**.  


