node {
    def mvnHome
    milestone 1
    stage('Checkout code') { // for display purposes
        git credentialsId: 'jenkins ssh', url: 'git@github.com:Kloudtek/ktutils.git'
        mvnHome = tool name: 'maven', type: 'maven'
    }
    milestone 2
    stage('Build CI') {
        withMaven( maven: 'maven', mavenSettingsConfig: 'e37672cf-602b-476f-8ec4-da37669113e6') {
            sh "mvn -Dmaven.test.failure.ignore -U -P release clean deploy"
        }
        junit '**/target/surefire-reports/TEST-*.xml'
        archive 'target/*.jar'
    }
}
milestone 3
input message: 'Release ?', ok: 'Release', submitter: 'ymenager'
milestone 4
node {
    stage('Build Release') {
        withMaven( maven: 'maven', mavenSettingsConfig: 'e37672cf-602b-476f-8ec4-da37669113e6') {
            sh "mvn -Dmaven.test.failure.ignore -U clean"
        }
    }
}