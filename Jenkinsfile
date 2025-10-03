pipeline {
    agent {
        node {
            label "agent1"
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
        stage("Checkout") {
            steps {
                git branch: 'Pract-M1-1',
                credentialsId: 'jenkins',
                url: 'https://github.com/Kanlux/PractRestAssured.git'
            }
        }
    }

    post {
        always {
            echo "Пайплайн завершен со статусом: ${currentBuild.result}"
        }
    }
}