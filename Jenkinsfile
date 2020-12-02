pipeline {
    environment {
        DOCKER_HUB_LOGIN = credentials('docker-hub')
        IMAGE_NAME = "spring-boot-mvc-image-without-sql"
        DOCKER_IMAGE_NAME = '$DOCKER_HUB_LOGIN_USR/$IMAGE_NAME'
        DOCKER_IMAGE = ''
    }
    agent any

    tools {
        maven 'Maven' //Name should be exactly like this
    }

    triggers {
        pollSCM '* * * * *'
    }
    stages {
        stage('Build') {
            steps {
            //instead of install use package
                sh 'mvn -Dmaven.test.skip=true package'
            }
        }
        stage('Test') {
            steps {
                sh 'mvn test'
            }
        }
        stage('Build Docker image') {
            steps {
                script {
                    DOCKER_IMAGE = docker.build DOCKER_IMAGE_NAME
                }
            }
        }
        stage('Push Docker image') {
            steps {
                script {
                    docker.withRegistry('', 'docker-hub') {
                        DOCKER_IMAGE.push("$BUILD_NUMBER")
                    }
                }
            }
        }
        stage('Remove Unused Docker image') {
            steps{
                sh "docker rmi $DOCKER_IMAGE_NAME:$BUILD_NUMBER"
            }
        }
    }
}