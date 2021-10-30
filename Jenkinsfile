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
                    bat "${scannerHome}/bin/sonar-scanner -e -Dsonar.projectKey=DeployBackend -Dsonar.host.url=http://localhost:9000 -Dsonar.login=f372545406e925f2192e9eb2b0547e9f17f195b1 -Dsonar.java.binaries=target -Dsonar.coverage.exclusions=**/.mvn/**, **/src/test/**, **/model/**,**Application.java "
                }
            }
        }
    }
}
