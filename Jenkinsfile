pipeline {
    agent {
        node {
            label "agent"
        }
    }
    stages {
        stage ("build"){
            steps {
                echo "Работает"
            }
        }
        stage ("run tests"){
            steps {
                sh 'mvn -Dtest=LoginUserTest.java verify'
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