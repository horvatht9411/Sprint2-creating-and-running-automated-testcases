pipeline{
   agent any
   stages {
         stage("build"){
             steps{
                 echo "Building..."
                 sh(script: "mvn compile")
             }
         }
         stage("run"){
             parallel{
                     stage("With Chrome"){
                         steps{
                             echo "Running with chrome..."
                             sh(script: "mvn clean test -Dusername=$username -Dpassword=$password -Durl='$url' -Dlocal=false -Dheadless=true -DremoteBrowser=chrome")
                         }
                     }
                     stage("With Firefox"){
                         steps{
                             echo "Running with firefox..."
                             sh(script: "mvn clean test -Dusername=$username -Dpassword=$password -Durl='$url' -Dlocal=false -Dheadless=true -DremoteBrowser=firefox")
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