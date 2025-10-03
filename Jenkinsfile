pipeline {
    agent any

    tools {
        maven 'Maven 3.8.6'
        jdk 'JDK 11'
    }

    parameters {
        choice(name: 'BROWSER', choices: ['chrome', 'firefox', 'edge'], description: 'Select browser for test execution')
        string(name: 'TEST_SUITE', defaultValue: 'testng.xml', description: 'TestNG suite file to execute')
    }

    environment {
        MAVEN_OPTS = '-Dmaven.test.failure.ignore=true'
    }

    stages {
        stage('Checkout') {
            steps {
                echo 'Checking out code from repository...'
                checkout scm
            }
        }

        stage('Build') {
            steps {
                echo 'Building the project...'
                sh 'mvn clean compile'
            }
        }

        stage('Run Tests') {
            steps {
                echo "Running tests on ${params.BROWSER} browser..."
                sh """
                    mvn test -Dsurefire.suiteXmlFiles=${params.TEST_SUITE} \
                    -Dbrowser=${params.BROWSER}
                """
            }
        }

        stage('Generate Reports') {
            steps {
                echo 'Generating test reports...'
                sh 'ls -la test-output/'
            }
        }
    }

    post {
        always {
            echo 'Publishing TestNG results...'
            publishHTML([
                allowMissing: false,
                alwaysLinkToLastBuild: true,
                keepAll: true,
                reportDir: 'test-output/reports',
                reportFiles: 'ExtentReport*.html',
                reportName: 'Extent Report',
                reportTitles: 'Flipkart Automation Test Report'
            ])

            publishHTML([
                allowMissing: false,
                alwaysLinkToLastBuild: true,
                keepAll: true,
                reportDir: 'test-output',
                reportFiles: 'index.html',
                reportName: 'TestNG Report',
                reportTitles: 'TestNG Execution Report'
            ])

            archiveArtifacts artifacts: 'test-output/**/*', allowEmptyArchive: true

            emailext (
                subject: "Test Execution Status: ${currentBuild.result}",
                body: """
                    <h3>Flipkart Automation Test Execution</h3>
                    <p><strong>Build Number:</strong> ${BUILD_NUMBER}</p>
                    <p><strong>Build Status:</strong> ${currentBuild.result}</p>
                    <p><strong>Browser:</strong> ${params.BROWSER}</p>
                    <p><strong>Build URL:</strong> ${BUILD_URL}</p>
                    <p>Check the attached reports for detailed results.</p>
                """,
                to: 'qa-team@company.com',
                mimeType: 'text/html',
                attachLog: true
            )
        }

        success {
            echo 'Test execution completed successfully!'
        }

        failure {
            echo 'Test execution failed! Check logs and reports.'
        }

        unstable {
            echo 'Test execution completed with failures!'
        }
    }
}
