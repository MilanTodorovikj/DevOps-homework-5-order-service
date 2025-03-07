pipeline {
    agent any
    environment {
        HOST_JENKINS_HOME = 'E:/Fakultet/Master/IX semestar/DevOps/Domasni/Domasna 2/jenkins/jenkins_home'
        KUBECONFIG = '/home/jenkins/.kube/config'
    }
    stages {
        stage('Initialize Host Workspace') {
            steps {
                script {
                    def relativePath = WORKSPACE.replaceFirst("^/var/jenkins_home", "")
                    env.HOST_WORKSPACE = "${env.HOST_JENKINS_HOME}${relativePath}"
                    echo "Calculated HOST_WORKSPACE: ${env.HOST_WORKSPACE}"
                }
            }
        }

        stage('Build & Test') {
            options {
                timeout(time: 10, unit: 'MINUTES')
            }
            steps {
                sh '''
                docker run --rm \
                  -v "${HOST_WORKSPACE}:/app" \
                  -v maven-repo:/root/.m2 \
                  -v "${HOST_WORKSPACE}/.mvn/settings.xml:/root/.m2/settings.xml" \
                  -w /app \
                  maven:3.9.9-amazoncorretto-17-alpine \
                  bash -c "mvn clean test"
                '''
            }
        }

        stage('Deploy Artefact') {
            options {
                timeout(time: 10, unit: 'MINUTES')
            }
            when {
                branch 'master'
            }
            steps {
                sh '''
                docker run --rm \
                  --network domasna2_homework2  \
                  -v "${HOST_WORKSPACE}:/app" \
                  -v maven-repo:/root/.m2 \
                  -v "${HOST_WORKSPACE}/.mvn/settings.xml:/root/.m2/settings.xml" \
                  -w /app \
                  maven:3.9.9-amazoncorretto-17-alpine \
                  bash -c "mvn clean deploy"
                '''
            }
        }

        stage('Wait for User Service') {
            options {
                timeout(time: 10, unit: 'MINUTES')
            }
            when {
                branch 'master'
            }
            steps {
                script {
                    def userServiceReady = false
                    for (int i = 0; i < 10; i++) {
                        def output = sh(script: 'kubectl get pods -l app=user-service --field-selector=status.phase=Running', returnStdout: true).trim()
                        if (output) {
                            userServiceReady = true
                            break
                        }
                        sleep(10)
                    }
                    if (!userServiceReady) {
                        error("User Service is not running!")
                    }
                }
            }
        }

        stage('Build & Push Docker Image') {
            options {
                timeout(time: 10, unit: 'MINUTES')
            }
            when {
                branch 'master'
            }
            steps {
                sh '''
                eval $(minikube -p minikube docker-env) &&
                docker build -t order-service:1.0.0 .
                '''
            }
        }

        stage('Deploy to Minikube') {
            options {
                timeout(time: 10, unit: 'MINUTES')
            }
            when {
                branch 'master'
            }
            steps {
            sh '''
            kubectl apply -f k8s/order-service-deployment.yaml
            '''
            }
        }
    }
}
