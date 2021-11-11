pipeline{
    agent any
    stages{
        stage('Build Backend'){
            steps{
                bat 'mvn clean package -DskipTests=true'
            }
        }
        stage('Unit Tests'){
            steps{
                bat 'mvn test'
            }
        }
        stage('Sonar Analysis'){
            environment{
                scannerHome = tool 'SONAR_SCANNER'
            }
            steps{
                withSonarQubeEnv('SONAR_LOCAL'){
                    bat "${scannerHome}/bin/sonar-scanner -e -Dsonar.projectKey=DeployBackend -Dsonar.host.url=http://localhost:9000 -Dsonar.login=93760b64892a24dc7f102f8d764b1428292c08de -Dsonar.java.binaries=target -Dsonar.coverage.exclusions=**/.mvn/**,**/src/test/**,**/model/**,**Application.java "
                }
            }
        }
        stage('Quality Gate'){
            steps{
                sleep(50)
                timeout(time: 1, unit: 'MINUTES'){
                    waitForQualityGate abortPipeline: true
                }
            }
        }
        stage('Deploy Backend'){
            steps{
                deploy adapters: [tomcat8(credentialsId: 'tomcat_login', path: '', url: 'http://localhost:8081/')], contextPath: 'tasks-backend', war: 'target\\tasks-backend.war'
            }
        }
        stage('API Tests'){
            steps{
                dir('api-test') {
                    git branch: 'main', credentialsId: 'github_login', url: 'https://github.com/moises78moura/tasks-api-test'
                    bat 'mvn test'
                }
            }
        }
        stage('Deploy Frontend'){
            steps{
                 dir('frontend') {
                    git branch: 'master', credentialsId: 'github_login', url: 'https://github.com/moises78moura/tasks-frontend'
                    bat 'mvn clean package'
                    deploy adapters: [tomcat8(credentialsId: 'tomcat_login', path: '', url: 'http://localhost:8081/')], contextPath: 'tasks', war: 'target\\tasks.war'
                }
            }
        }
        stage('Functional Tests'){
            steps{
                dir('functional-test') {
                    git branch: 'main', credentialsId: 'github_login', url: 'https://github.com/moises78moura/tasks-functional-test'
                    bat 'mvn test'
                }
            }
        }
        stage('Deploy Producao'){
            steps{
                bat 'docker-compose build'
                bat 'docker-compose up -d'
            }
        }
        stage('Health Check'){
            steps{
                sleep(5)
                dir('functional-test') {
                    bat 'mvn verify -Dskip.surefire.tests'
                }
            }
        }
    }
    post{
        always{
            junit allowEmptyResults: true, testResults: 'target/surefire-reports/*.xml, api-test/target/surefire-reports/*.xml, functional-test/target/surefire-reports/*.xml, functional-test/target/failsafe-reports/*.xml'
        }
    }

}