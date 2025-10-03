# Flipkart Test Automation Framework

Enterprise-grade test automation framework for Flipkart search functionality using Java, Selenium, TestNG, Page Object Model, Apache POI, Extent Reports, and Jenkins CI/CD.

## Framework Architecture

```
flipkart-test-automation/
├── src/
│   ├── main/
│   │   ├── java/
│   │   │   └── com/flipkart/
│   │   │       ├── config/
│   │   │       │   └── ConfigReader.java
│   │   │       ├── listeners/
│   │   │       │   ├── ExtentReportListener.java
│   │   │       │   └── TestListener.java
│   │   │       ├── pages/
│   │   │       │   ├── BasePage.java
│   │   │       │   ├── HomePage.java
│   │   │       │   └── SearchResultsPage.java
│   │   │       ├── reports/
│   │   │       │   └── ExtentManager.java
│   │   │       └── utils/
│   │   │           ├── DriverFactory.java
│   │   │           ├── ExcelReader.java
│   │   │           └── ScreenshotUtil.java
│   │   └── resources/
│   │       └── config.properties
│   └── test/
│       ├── java/
│       │   └── com/flipkart/
│       │       ├── base/
│       │       │   └── BaseTest.java
│       │       └── tests/
│       │           └── SearchTest.java
│       └── resources/
│           └── testdata/
│               └── SearchTestData.xlsx
├── testng.xml
├── pom.xml
├── Jenkinsfile
└── README.md
```

## Technology Stack

| Technology | Version | Purpose |
|------------|---------|---------|
| Java | 11 | Programming Language |
| Selenium WebDriver | 4.15.0 | Browser Automation |
| TestNG | 7.8.0 | Test Framework |
| Apache POI | 5.2.5 | Excel Data Handling |
| Extent Reports | 5.1.1 | HTML Reporting |
| WebDriverManager | 5.6.2 | Driver Management |
| Maven | 3.x | Build Tool |
| Jenkins | 2.x | CI/CD Pipeline |

## Framework Features

### 1. Page Object Model (POM)
- Clean separation of test logic and page elements
- Reusable page classes
- Easy maintenance and scalability

### 2. Data-Driven Testing
- Excel-based test data management using Apache POI
- Parameterized test execution
- Easy test data updates without code changes

### 3. Extent Reports
- Rich HTML reports with screenshots
- Test execution timeline
- System information capture
- Failure screenshots attached automatically

### 4. Parallel Execution
- TestNG parallel execution support
- Thread-safe WebDriver instances
- Configurable thread count

### 5. CI/CD Integration
- Jenkins pipeline configuration
- Automated test execution
- Email notifications
- Report archiving

## Prerequisites

- Java JDK 11 or higher
- Maven 3.6 or higher
- Chrome/Firefox/Edge browser installed
- IDE (IntelliJ IDEA / Eclipse)

## Installation & Setup

### 1. Clone the Repository
```bash
git clone <repository-url>
cd flipkart-test-automation
```

### 2. Install Dependencies
```bash
mvn clean install -DskipTests
```

### 3. Configure Test Data
- Navigate to `src/test/resources/testdata/SearchTestData.xlsx`
- Update test data as needed with columns:
  - TestCaseID
  - SearchKeyword
  - ExpectedResult

### 4. Update Configuration
Edit `src/main/resources/config.properties`:
```properties
baseUrl=https://www.flipkart.com
browser=chrome
headless=false
implicitWait=10
explicitWait=20
```

## Running Tests

### 1. Command Line Execution

#### Run all tests:
```bash
mvn clean test
```

#### Run with specific browser:
```bash
mvn clean test -Dbrowser=chrome
```

#### Run specific test suite:
```bash
mvn clean test -Dsurefire.suiteXmlFiles=testng.xml
```

#### Run in headless mode:
Update `config.properties` and set `headless=true`

### 2. IDE Execution
- Right-click on `testng.xml`
- Select "Run As > TestNG Suite"

### 3. Parallel Execution
Configure in `testng.xml`:
```xml
<suite name="Suite" parallel="tests" thread-count="2">
```

## Test Cases Covered

### SearchTest.java

| Test Case | Description |
|-----------|-------------|
| testSearchBoxDisplayed | Verifies search box is visible on homepage |
| testProductSearch | Data-driven test for product search functionality |
| testSearchResultsRelevance | Validates search results contain relevant products |
| testProductCountGreaterThanZero | Ensures search returns products |
| testFirstProductTitleNotEmpty | Validates product data is populated |

## Reports

### Extent Reports
- Location: `test-output/reports/ExtentReport_<timestamp>.html`
- Features:
  - Test execution summary
  - Pass/Fail status with details
  - Screenshots for failed tests
  - Execution timeline
  - System information

### TestNG Reports
- Location: `test-output/index.html`
- Native TestNG HTML reports

### Screenshots
- Location: `test-output/screenshots/`
- Automatically captured on test failures

## Jenkins CI/CD Setup

### 1. Install Jenkins Plugins
- HTML Publisher Plugin
- Email Extension Plugin
- Maven Integration Plugin
- TestNG Results Plugin

### 2. Create Jenkins Job
1. New Item > Pipeline
2. Pipeline script from SCM
3. Select Git and provide repository URL
4. Script Path: `Jenkinsfile`

### 3. Configure Build Parameters
- BROWSER: chrome/firefox/edge
- TEST_SUITE: testng.xml

### 4. Build Triggers
- Poll SCM: `H/5 * * * *` (every 5 minutes)
- Webhook for automatic builds

### 5. Post-Build Actions
- Publish HTML reports
- Email notifications
- Archive artifacts

## Framework Design Patterns

### 1. Singleton Pattern
- `DriverFactory` ensures single WebDriver instance per thread

### 2. Factory Pattern
- Browser creation based on configuration

### 3. Thread Local Pattern
- Thread-safe execution for parallel tests

### 4. Facade Pattern
- `BasePage` provides simplified interface for common operations

## Best Practices Implemented

1. **Explicit Waits**: Used for reliable element interactions
2. **Exception Handling**: Proper try-catch blocks
3. **Logging**: Console and Extent report logging
4. **Screenshot on Failure**: Automatic capture for debugging
5. **Configurable Timeouts**: Centralized configuration
6. **Clean Code**: Meaningful names and comments
7. **Reusability**: Utility classes and base classes
8. **Data Separation**: Test data in external Excel files

## Troubleshooting

### Common Issues

#### 1. WebDriver Not Found
```bash
mvn clean install -U
```

#### 2. Element Not Found
- Increase wait times in `config.properties`
- Check element locators in page classes

#### 3. Excel File Not Found
- Verify path in `config.properties`
- Ensure file exists at specified location

#### 4. Browser Not Opening
- Check browser installation
- Verify WebDriverManager setup

## Extending the Framework

### Adding New Test Cases
1. Create test method in `SearchTest.java`
2. Follow naming convention: `test<Functionality>`
3. Add proper assertions and logging

### Adding New Pages
1. Create page class extending `BasePage`
2. Define page elements using `@FindBy`
3. Implement page methods

### Adding New Utilities
1. Create utility class in `utils` package
2. Make methods static for easy access
3. Document usage with comments

## Test Data Management

Excel file structure (`SearchTestData.xlsx`):

| TestCaseID | SearchKeyword | ExpectedResult |
|------------|---------------|----------------|
| TC001 | iPhone 15 | Products displayed |
| TC002 | Samsung Galaxy | Products displayed |
| TC003 | Laptop | Products displayed |

## Continuous Improvement

### Future Enhancements
- API testing integration
- Database validation
- Cross-browser cloud testing (BrowserStack/Sauce Labs)
- Allure reporting integration
- Docker containerization
- Performance testing integration

## Contributing

1. Fork the repository
2. Create feature branch
3. Commit changes
4. Push to branch
5. Create pull request

## License

This project is licensed under the MIT License.

## Contact

For queries or support:
- Email: qa-team@company.com
- Slack: #automation-framework

---

**Version:** 1.0.0
**Last Updated:** 2025-10-03
**Maintained By:** QA Automation Team
