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
                allure includeProperties: false, jdk: '', results: [[path: 'allure-results']]
    }
}