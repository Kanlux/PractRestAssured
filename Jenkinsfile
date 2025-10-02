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
                        junit '**/target/surefire-reports/*.xml'
                    }
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