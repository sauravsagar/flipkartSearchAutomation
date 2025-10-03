# Quick Setup Guide

## Step-by-Step Setup Instructions

### 1. Environment Setup

#### Install Java JDK 11
```bash
# Check Java installation
java -version

# If not installed, download from:
# https://www.oracle.com/java/technologies/javase/jdk11-archive-downloads.html
```

#### Install Maven
```bash
# Check Maven installation
mvn -version

# If not installed, download from:
# https://maven.apache.org/download.cgi
```

### 2. Project Setup

#### Clone and Setup
```bash
# Navigate to project directory
cd flipkart-test-automation

# Install dependencies
mvn clean install -DskipTests

# Verify installation
mvn dependency:tree
```

### 3. Test Data Configuration

#### Create Excel Test Data
The framework expects an Excel file with this structure:

**File:** `src/test/resources/testdata/SearchTestData.xlsx`

**Sheet Name:** `SearchData`

**Columns:**
- TestCaseID (e.g., TC001, TC002)
- SearchKeyword (e.g., iPhone 15, Laptop)
- ExpectedResult (e.g., Products displayed)

**Note:** You'll need to manually create this Excel file with the above structure, as it requires Microsoft Excel, LibreOffice Calc, or Apache POI to generate programmatically.

Sample data:
```
TestCaseID    | SearchKeyword    | ExpectedResult
TC001         | iPhone 15        | Products displayed
TC002         | Samsung Galaxy   | Products displayed
TC003         | Laptop           | Products displayed
```

### 4. Running Your First Test

#### Option 1: Maven Command Line
```bash
# Run all tests
mvn clean test

# Run with Chrome browser
mvn clean test -Dbrowser=chrome

# Run with Firefox browser
mvn clean test -Dbrowser=firefox
```

#### Option 2: IDE (IntelliJ IDEA / Eclipse)
1. Import project as Maven project
2. Right-click on `testng.xml`
3. Select "Run as TestNG Suite"

### 5. View Test Reports

After test execution, reports are generated at:

```
test-output/
├── reports/
│   └── ExtentReport_<timestamp>.html    # Rich HTML report
├── screenshots/                          # Failure screenshots
└── index.html                           # TestNG native report
```

Open any HTML file in a browser to view results.

### 6. Jenkins Setup (Optional)

#### Prerequisites
- Jenkins installed and running
- Required plugins installed:
  - HTML Publisher Plugin
  - Email Extension Plugin
  - Maven Integration Plugin

#### Steps
1. Create new Pipeline job in Jenkins
2. Configure SCM with your repository URL
3. Set Pipeline script path to: `Jenkinsfile`
4. Add build parameters:
   - BROWSER (choice: chrome/firefox/edge)
   - TEST_SUITE (string: testng.xml)
5. Save and build

## Configuration Files

### config.properties
Location: `src/main/resources/config.properties`

```properties
# Application URL
baseUrl=https://www.flipkart.com

# Browser Configuration
browser=chrome
headless=false

# Timeout Configuration
implicitWait=10
explicitWait=20
pageLoadTimeout=30

# File Paths
testDataPath=src/test/resources/testdata/SearchTestData.xlsx
screenshotPath=test-output/screenshots/
reportPath=test-output/reports/
```

### testng.xml
Location: `testng.xml`

```xml
# Parallel execution settings
parallel="tests"      # Parallel mode
thread-count="2"      # Number of threads

# Browser parameter
<parameter name="browser" value="chrome"/>
```

## Troubleshooting

### Issue: WebDriver not found
**Solution:**
```bash
mvn clean install -U
```

### Issue: Tests timing out
**Solution:** Increase wait times in `config.properties`:
```properties
implicitWait=15
explicitWait=30
```

### Issue: Excel file not found
**Solution:**
1. Verify file exists at: `src/test/resources/testdata/SearchTestData.xlsx`
2. Check path in `config.properties`
3. Ensure sheet name is exactly: `SearchData`

### Issue: Login popup blocking tests
**Solution:** Already handled in `HomePage.java` - popup is automatically closed

### Issue: Elements not found
**Solution:**
1. Flipkart may have updated their UI
2. Update locators in page classes
3. Use browser DevTools to find correct locators

## Running Specific Tests

### Run single test method
```bash
mvn test -Dtest=SearchTest#testSearchBoxDisplayed
```

### Run test with parameters
```bash
mvn test -Dbrowser=firefox -Dheadless=true
```

### Run in headless mode
Update `config.properties`:
```properties
headless=true
```

Then run:
```bash
mvn clean test
```

## Framework Components Quick Reference

| Component | Location | Purpose |
|-----------|----------|---------|
| ConfigReader | config/ | Read configuration properties |
| DriverFactory | utils/ | WebDriver instance management |
| ExcelReader | utils/ | Read test data from Excel |
| ScreenshotUtil | utils/ | Capture screenshots |
| ExtentManager | reports/ | Configure Extent Reports |
| BasePage | pages/ | Common page operations |
| BaseTest | test/base/ | Test setup and teardown |
| Listeners | listeners/ | TestNG and reporting listeners |

## Test Execution Checklist

- [ ] Java JDK 11+ installed
- [ ] Maven 3.6+ installed
- [ ] Browser installed (Chrome/Firefox/Edge)
- [ ] Project dependencies downloaded (`mvn install`)
- [ ] Excel test data file created
- [ ] config.properties configured
- [ ] Tests executed successfully
- [ ] Reports generated and reviewed

## Next Steps

1. **Customize Test Data**: Update Excel file with your test scenarios
2. **Add More Tests**: Extend `SearchTest.java` with additional test cases
3. **Add New Pages**: Create page objects for other Flipkart pages
4. **Enhance Reporting**: Customize Extent Report configuration
5. **Setup CI/CD**: Integrate with Jenkins for automated execution

## Support

For issues or questions:
1. Check logs in: `test-output/logs/automation.log`
2. Review Extent Reports for detailed failure information
3. Check screenshot folder for visual debugging

---

**Happy Testing!**
