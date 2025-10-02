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
                sh 'mvn -Dtest=RegistrationTest.java verify'
            }
        }
    }
    post {
        always {
            echo "Пайплайн завершен со статусом: ${currentBuild.result}"
        }
    }
}