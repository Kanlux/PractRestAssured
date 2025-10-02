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
        stage ("run test"){
                    steps {
                        sh "mvn -Dtest=.** verify"
                    }
                }
        post {
            always {
                allure([
                    reportBuildPolicy: 'ALWAYS',
                    result: [[path: 'allure-result']]
                ])
            }
        }
    }
}