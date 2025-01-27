# Currency Converter API

A Spring Boot application that provides real-time currency conversion functionality using a public exchange rates API.

## **Features**
1. Fetch live exchange rates for a given base currency.
2. Convert an amount from one currency to another based on the fetched rates.

---

## **Run the Application Locally**

### **1. Prerequisites**
Make sure you have the following installed on your system:
- **Java 17** or later
- **Maven 3.8+**

Recommended Extensions for Visual Studio Code:
**Spring Boot Extension Pack**

### **2. Clone the Repository**
- Clone the GitHub repository to your local system
- Open in Visual Studio Code.

### **3. Build the Project**
  Use Maven to download dependencies and build the project:

```bash
mvn clean install
```

### **4. Run the Application**
  Run the application using the java command:

```bash
java -jar target/currency-converter-0.0.1-SNAPSHOT.jar
```
The application will start and listen on http://localhost:8080.

## **API Endpoints**
### **1. Fetch Exchange Rates**
GET [http://localhost:8080/api/rates?base={BASE_CURRENCY}](http://localhost:8080/api/rates?base={BASE_CURRENCY})
- Default base currency: USD.
- Fetch the exchange rates for a given base currency.
- eg: [http://localhost:8080/api/rates?base=INR](http://localhost:8080/api/rates?base=INR)

### **1. Convert Currency**
- Test from **Postman**
- POST [http://localhost:8080/api/convert](http://localhost:8080/api/convert)
  
Request Body eg:
```bash
{
    "from": "USD",
    "to": "INR",
    "amount": 1.0
}
```
Sample Response:
```bash
{
    "amount": 1.0,
    "from": "USD",
    "to": "INR",
    "convertedAmount": 86.37
}
```


