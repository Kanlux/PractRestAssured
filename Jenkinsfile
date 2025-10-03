pipeline {
    agent any
    tools {
        maven 'Maven-3.9'
        jdk 'JDK-17'
    }

    stages {
        stage("Build") {
            steps {
                echo "Сборка проекта"
            }
        }
        stage("Run Tests") {
            steps {
                sh "mvn -Dtest=RegistrationTest.java verify"
            }
        }
        stage('Package') {
            steps {
                echo "Создание артефакта..."
                sh 'mvn package -DskipTests'
                archiveArtifacts 'target/*.jar'
            }
        }
    }

    post {
        always {
            echo "Пайплайн завершен со статусом: ${currentBuild.result}"
        }
    }
}