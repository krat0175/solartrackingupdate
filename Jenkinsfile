pipeline {
    agent
    {
        ecs {
            inheritFrom 'fargate-cloud-j17-chrome'
        }
    }
    parameters {
            string(name: 'opcos', defaultValue: '014,015,019,027,075,163,429,060,134,335,344,005,009,016,022,023,025,037,043,078,137,146,164,293,306,001,042,148,004,008,011,013,017,018,031,035,036,045,047,049,050,052,055,059,061,101,102,147,320,450', description: 'opco list')
        }
    stages {
        stage('Checkout'){
            steps{
                git branch: 'master',
                    credentialsId: 'jenkins-user-github-token',
                    url: 'https://github.com/krat0175/solartrackingupdate.git'
            }
        }
        stage('execute') {
            steps {


                configFileProvider([configFile(fileId: 'QESettings.xml', variable: 'MAVEN_SETTINGS')]) {
                    withCredentials([usernamePassword(credentialsId: 'ssodetails', usernameVariable: 'ssousername', passwordVariable: 'ssopassword')]) {
                       sh 'chmod +x ./mvnw'
                       sh "./mvnw -s $MAVEN_SETTINGS -T 8 clean install"
                       //exeucte main class
                          sh "./mvnw -s $MAVEN_SETTINGS -T 8 exec:java -Dexec.mainClass=com.sysco.solar.SolarTrackingUpdate -Dexec.args=${params.opcos}"
                    }
                }
            }

        }
    }
  }
