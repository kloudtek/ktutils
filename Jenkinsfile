node {
    def mvnHome
    stage('Checkout code') { // for display purposes
        git credentialsId: 'jenkins ssh', url: 'git@github.com:Kloudtek/ktutils.git'
        mvnHome = tool name: 'maven', type: 'maven'
    }
    stage('Build CI') {
        withMaven( maven: 'maven', mavenSettingsConfig: 'e37672cf-602b-476f-8ec4-da37669113e6') {
            sh "mvn -Dmaven.test.failure.ignore -U -P release clean deploy"
        }
        junit '**/target/surefire-reports/TEST-*.xml'
        archive 'target/*.jar'
    }
    milestone 1 continuousintegration
}