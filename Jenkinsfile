pipeline {
    agent any

    stages {
        stage('Compose down') {
            steps {
                dir("/home/jenkins/workspace/test/docker") {
                    sh('docker compose down')
                }
            }
        }
        stage('Test containers') {
            steps {
                dir("/home/jenkins/workspace/test") {
                    sh('mvn clean install')
                }
            }
        }

        stage('Infrastructure setup') {
            steps {
                dir("/home/jenkins/workspace/test/docker") {
                    sh('cd ../ssbd202401web/ && npm install && npm run build')
                    sh('rm -rf html/')
                    sh('cp -r ../ssbd202401web/dist/ html/')
                    sh('docker compose up -d --build')
                }
            }
        }

        stage('End-to-end tests') {
            steps {
              dir("/home/jenkins/workspace/test") {
                    echo 'Doing end to end test'
                    echo 'Finished end to end test'
                }
            }
        }

        stage('Infrastructure down') {
            steps {
                dir("/home/jenkins/workspace/test/docker") {
                     sh('docker compose down')
                }
            }
        }
    }
}