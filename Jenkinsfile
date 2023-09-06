currentBuild.displayName = "#${Build_Name}-${Test_Plan}"
pipeline {
    agent
    {
        ecs {
            inheritFrom 'fargate-cloud-j17-chrome'
        }
    }
    parameters {
            string(name: 'opcos', defaultValue: 'all', description: 'opco list')
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
                       sh 'export DISPLAY=:0'
                       sh 'chmod +x mvnw'
                       sh "./mvnw -s $MAVEN_SETTINGS -T 8 clean install"
                       //exeucte main class
                          sh "./mvnw -s $MAVEN_SETTINGS -T 8 exec:java -Dexec.mainClass=\"com.solartracking.SolarTrackingUpdate\" -Dexec.args=\"-opcos ${params.opcos}"
                    }
                }
            }

        }
    }
	post {
    }
  }

}