pipeline {
    agent {
        node {
            label "agent2   "
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
                sh "mvn test -Dtest=RegistrationTest"
            }
            post {
                always {
                    junit 'target/surefire-reports/*.xml'
                }
            }
        }

        stage('Reports') {
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
        stage ('Publish') {
            steps {
             echo 'Публикация артефактов'
             archiveArtifacts artifacts: 'target/*.jar', fingerprint: true
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