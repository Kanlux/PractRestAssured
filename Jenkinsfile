pipeline {
    agent {
        node {
            label "agent"
        }
    }
    stages {
        stage ("Build"){
            steps {
                echo "Компиляция проекта..."
            }
        }
        stage ("Run Tests"){
            echo "Запуск тестов"
            steps {
                sh 'mvn -Dtest=RegistrationTest.java, GetUserData.java verify'
            }
        }
        post {
            always {
                junit '**/target/surefire-reports/*.xml'
                publishHTML([
                    target: [
                        allowMissing: true,
                        alwaysLinkToLastBuild: true,
                        keepAll: true,
                        reportDir: 'target/site',
                        reportFiles: 'index.html',
                        reportName: 'Test Report'
                    ]
                ])
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