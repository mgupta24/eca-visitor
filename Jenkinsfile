pipeline {
    environment {
        REGISTRY_NAME = "mgupta25/eca-visitor"
        APP_NAME = "eca-visitor"
     }

    agent any
    tools {
            maven "MAVEN_HOME"
        }

    stages {
        stage('Checkout') {
            steps {
                cleanWs()
                     git branch: 'main', url: 'https://github.com/mgupta24/eca-apartment-management-system.git'
                }
            }
        stage('Build') {
            steps {
                sh "mvn -f $APP_NAME/pom.xml clean install -DskipTests"
            }
        }

        stage('Sonar Analysis') {
                    steps {
                       sh "mvn -f $APP_NAME/pom.xml clean package"
                        dir("eca-visitor"){
                        sh "mvn sonar:sonar \
                        -Dsonar.projectKey=eca-visitor: \
                        -Dsonar.projectName=eca-visitor \
                        -Dsonar.java.binaries=."
                    }
                }
        }



        stage('Building image') {
          steps{
            sh 'docker build -t $REGISTRY_NAME:$BUILD_NUMBER ./eca-visitor'
          }
        }

        stage('Docker Build & Push') {
            steps {
                withDockerRegistry(credentialsId: 'DOCKER_HUB_USER_PS', url: '') {
                    sh 'docker push $REGISTRY_NAME:$BUILD_NUMBER'
                }
            }
            post {
                success {
                    sh 'docker rmi $REGISTRY_NAME:$BUILD_NUMBER'
                }
            }
        }
    }

    post {
       always {
           deleteDir()
       }
   }
}
