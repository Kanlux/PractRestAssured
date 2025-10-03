pipeline {
    agent {
        node {
            label "710a04c249021dda2bcfb3b6e0b477927295a19947a9175479e06943a5fb2e08"
        }
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