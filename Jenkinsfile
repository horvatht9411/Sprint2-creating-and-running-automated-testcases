pipeline{
   agent any
   parameters {
     string defaultValue: 'automation33', name: 'username'
     password defaultValue: 'CCAutoTest19.', name: 'password'
     string defaultValue: 'https://jira-auto.codecool.metastage.net/', name: 'url'
   }

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
                             sh(script: "mvn clean test -Dusername=${params.username} -Dpassword=${params.password} -Dbaseurl='${params.url}' -Dlocal=false -Dheadless=true -DremoteBrowser=chrome")
                         }
                     }
                     stage("With Firefox"){
                         steps{
                             echo "Running with firefox..."
                             sh(script: "mvn clean test -Dusername=${USERNAME} -Dpassword=${PASSWORD} -Dbaseurl='${URL}' -Dlocal=false -Dheadless=true -DremoteBrowser=firefox")
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