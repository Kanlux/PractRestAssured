pipeline {
    agent {
        node {
            label "agent1"
        }
    }

    stages {
        stage("Checkout") {
            steps {
                git branch: 'Pract-M1-1',
                credentialsId: 'github-practrestassured-token',
                url: 'https://github.com/Kanlux/PractRestAssured.git'
            }
        }

        stage("Build") {
            steps {
                echo "Сборка проекта"
                sh "mvn clean compile"
            }
        }

        stage("Run Tests") {
            steps {
                sh "mvn test -Dtest=RegistrationTest"
            }
            post {
                always {
                    junit 'target/surefire-reports/*.xml'
                }
            }
        }

        stage('Reports') {
            steps {
                echo "Создание артефакта..."
                sh 'mvn package -DskipTests'
            }
            post {
                success {
                    archiveArtifacts 'target/*.jar'
                }
            }
        }
        stage ('Publish') {
            steps {
             echo 'Публикация артефактов'
             archiveArtifacts artifacts: 'target/*.jar', fingerprint: true
            }
        }
    }

    post {
        always {
            echo "Пайплайн завершен со статусом: ${currentBuild.result ?: 'SUCCESS'}"
        }
        failure {
            echo "Сборка завершилась с ошибками. Проверьте логи для деталей."
        }
    }
}

//
// pipeline {
//     agent {
//         node {
//             label "agent2"
//         }
//     }
//
//     stages {
//         stage("Checkout") {
//             steps {
//                 git branch: 'Pract-M1-1',
//                 credentialsId: 'github-practrestassured-token',
//                 url: 'https://github.com/Kanlux/PractRestAssured.git'
//             }
//         }
//
//         stage("Build") {
//             steps {
//                 echo "Сборка проекта"
//                 sh "mvn clean compile"
//             }
//         }
//
//         stage("Run Tests") {
//             steps {
//                 echo "Запуск тестов..."
//                 sh "mvn test -Dtest=RegistrationTest"
//             }
//             post {
//                 always {
//                     junit 'target/surefire-reports/*.xml'
//                     // Добавляем отчет о покрытии кода, если используется JaCoCo
//                     jacoco(
//                         execPattern: 'target/jacoco.exec',
//                         classPattern: 'target/classes',
//                         sourcePattern: 'src/main/java'
//                     )
//                 }
//             }
//         }
//
//         stage('Package') {
//             steps {
//                 echo "Создание артефакта..."
//                 sh 'mvn package -DskipTests'
//             }
//         }
//
//         stage('Publish & Reports') {
//             steps {
//                 echo 'Публикация артефактов и отчетов'
//                 script {
//                     // Архивируем артефакты
//                     archiveArtifacts artifacts: 'target/*.jar', fingerprint: true
//                 }
//             }
//         }
//     }
//
//     post {
//         always {
//             echo "Пайплайн завершен со статусом: ${currentBuild.result ?: 'SUCCESS'}"
//             // Очистка workspace (опционально)
//             cleanWs()
//         }
//         success {
//             echo "✅ Сборка завершена успешно!"
//         }
//         failure {
//             echo "❌ Сборка завершилась с ошибками. Проверьте логи для деталей."
//         }
//     }
// }