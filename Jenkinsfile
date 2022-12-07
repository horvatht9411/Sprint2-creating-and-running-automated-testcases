pipeline{
   agent any
   stages {
         stage("build"){
             steps{
                 echo "Building..."
                 sh(script: "mvn compile")
                 sh(script: echo "username = $username" > src/main/resources/init.properties
                            echo "password = $password" >> src/main/resources/init.properties
                            echo "url = $url" >> src/main/resources/init.properties
                            echo "local = false" >> src/main/resources/init.properties
                            echo "headless = true" >> src/main/resources/init.properties
                 )
             }
         }
         stage("run"){
             parallel{
                     stage("With Chrome"){
                         steps{
                             echo "Running with chrome..."
                             sh(script: "mvn clean test -DremoteBrowser=chrome")
                         }
                     }
                     stage("With Firefox"){
                         steps{
                             echo "Running with firefox..."
                             sh(script: "mvn clean test -DremoteBrowser=firefox")
                         }
                     }
                 }

             post {
                 always {
                     junit testResults: '*/target/surefire-reports/TEST-.xml', skipPublishingChecks: true
                     cleanWs()
                 }
             }
         }
     }
 }