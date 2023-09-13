pipeline {
    agent
    {
        ecs {
            inheritFrom 'fargate-cloud-j17-chrome'
        }
    }
    parameters {
            string(name: 'opcos', defaultValue: '001,002,003,004,005,008,009,011,013,014,015,016,017,018,019,022,023,025,027,031,035,036,037,043,045,047,049,050,052,055,059,060,061,075,078,101,102,134,137,146,147,148,163,164,293,306,320,335,344,429,450', description: 'opco list')
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
