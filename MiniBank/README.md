# MiniBank – CEUE203 OOP Project (Practical 1–14)

Java 17 console banking application demonstrating the cumulative Part B requirements from the supplied practical list.

## Requirements
- JDK 17+
- Maven 3.8+ (for JUnit tests and packaging)

## Run in VS Code / terminal
Open this folder in VS Code. From the project root:

```bash
mvn clean package
java -jar target/minibank-1.0.0.jar
```

Without Maven, compile and run the main application:

```bash
javac -d out $(find src/main/java -name "*.java")
java -cp out MiniBank
```

## Menu
1. Open Account (Savings / Current / Fixed Deposit)
2. Deposit
3. Withdraw
4. Transfer
5. View Statement
6. List Accounts (sorted by balance)
7. End-of-day Report
8. Concurrent Deposit Demo
9. Producer-consumer Demo
10. Exit (saves account state)

Account state is stored in `data/accounts.dat`; transaction logs are stored in `data/logs/transactions.log`; the report is written to `data/report.txt`. These are generated at runtime and should not be committed to Git.

## Run tests
```bash
mvn test
```

## Notes
- Savings accounts maintain a minimum balance of ₹500.
- Current accounts have an overdraft limit of ₹5,000.
- Fixed-deposit accounts do not permit withdrawals in this practical implementation.
- The generic `Repository<T>` reads the `@Id` field using reflection. The application also uses a `ConcurrentHashMap`-backed `AccountStore` for concurrent access.
- This is an academic console project, not production banking software. It does not connect to real payment networks or use production-grade authentication/encryption.
