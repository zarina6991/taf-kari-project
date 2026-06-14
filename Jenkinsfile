pipeline {
    agent any

    environment {
        kari_login    = credentials('KARI_LOGIN')
        kari_password = credentials('KARI_PASSWORD')
    }

    stages {
        stage('Checkout') {
            steps {
                checkout scm
            }
        }

        stage('Test') {
            steps {
                script {
                    if (isUnix()) {
                        sh 'mvn clean test'
                    } else {
                        bat 'mvn clean test'
                    }
                }
            }
        }
    }
}
