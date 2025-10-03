pipeline {
    agent {
        node {
            label "agent1"
        }
    }

    tools {
        maven 'M3'
        jdk 'jdk17'
    }

    stages {
        stage('Build and Test') {
            steps {
                echo 'Сборка и запуск тестов'
                sh 'mvn clean compile test -Dtest=RegistrationTest'
            }
            post {
                always {
                    junit 'target/surefire-reports/*.xml'
                }
            }
        }

        stage('Package') {
            steps {
                echo 'Создание артефакта...'
                sh 'mvn package -DskipTests'
            }
            post {
                success {
                    archiveArtifacts 'target/*.jar'
                }
            }
        }
    }

    post {
        always {
            echo "Пайплайн завершен со статусом: ${currentBuild.result ?: 'SUCCESS'}"
        }
        failure {
            echo "Сборка завершилась с ошибками. Проверьте логи для деталей."
        }
    }
}