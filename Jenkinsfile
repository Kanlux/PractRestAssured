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
                        sh "mvn -Dtest=RegistrationTest.java, LoginUserTest.java verify"
                    }
                }
        }
    }
}