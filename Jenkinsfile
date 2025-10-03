pipeline {
    agent {
        node {
            label "agent1"
        }
    }

    stages {
        stage("Checkout") {
            steps {
                git branch: 'Pract-M1-1',
                credentialsId: 'github-practrestassured-token',
                url: 'https://github.com/Kanlux/PractRestAssured.git'
            }
        }

        stage("Build") {
            steps {
                echo "Сборка проекта"
                sh "mvn clean compile"
            }
        }

        stage("Run Tests") {
            steps {
                sh "mvn test -Dtest=RegistrationTest,LoginUserTest"
            }
            post {
                always {
                    junit 'target/surefire-reports/*.xml'
                }
            }
        }

        stage('Package') {
            steps {
                echo "Создание артефакта..."
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