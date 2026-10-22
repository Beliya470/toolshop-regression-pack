// Jenkins pipeline for the Toolshop regression pack: the API tests and the UI tests.
// The agent is a Linux machine with Java 21, Maven and Chrome installed, so I use sh.
pipeline {
    agent any

    stages {
        stage('Checkout') {
            steps {
                // gets the exact commit that started this build
                checkout scm
            }
        }

        stage('Build') {
            steps {
                // cleans and compiles both projects. Clean removes old reports, so a skipped stage cannot publish the results of an earlier build.
                sh 'mvn clean compile'
            }
        }

        stage('API Tests') {
            steps {
                sh 'mvn -pl api-tests test'
            }
        }

        stage('UI Tests (headless)') {
            steps {
                // the CI server has no screen, so Chrome runs without a window (-Dheadless=true, see DriverFactory)
                sh 'mvn -pl ui-tests test -Dheadless=true'
            }
        }
    }

    post {
        always {
            // publishes the results of both projects, also when a test failed
            junit '**/target/surefire-reports/*.xml'
        }
    }
}
