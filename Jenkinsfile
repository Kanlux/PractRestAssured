pipeline {
    agent {
        node {
            label "agent"
        }
    }

    environment {
        JAVA_HOME = '/usr/lib/jvm/java-17-openjdk-amd64'
        PATH = "${JAVA_HOME}/bin:${PATH}"
    }

    stages {
        stage('Build') {
            steps {
                echo "Сборка проекта..."
                sh 'mvn clean compile -Dmaven.compiler.source=17 -Dmaven.compiler.target=17'
            }
        }

        stage('Run Tests') {
            steps {
                echo "Запуск тестов..."
                // Правильный синтаксис для запуска конкретных тестов
                sh 'mvn test -Dtest="RegistrationTest,LoginUserTest"'
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
}