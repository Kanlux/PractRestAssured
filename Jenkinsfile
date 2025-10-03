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
            steps {
                sh 'mvn -Dtest=RegistrationTest.java, GetUserData.java verify'
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