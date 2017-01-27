node {
    milestone 10
    stage('Continuous Integration') {
        checkout scm
        mvnHome = tool name: 'maven', type: 'maven'
        withMaven( maven: 'maven', mavenSettingsConfig: 'e37672cf-602b-476f-8ec4-da37669113e6') {
            sh "mvn -Dmaven.test.failure.ignore -U -P release clean deploy"
        }
        junit '**/target/surefire-reports/TEST-*.xml'
        archive '**/target/*.jar'
    }
}
milestone 20
input message: 'Release ?', ok: 'Release', submitter: 'ymenager'
milestone 30
node {
    stage('Release') {
        checkout scm
        withMaven( maven: 'maven', mavenSettingsConfig: 'e37672cf-602b-476f-8ec4-da37669113e6') {
            sh "mvn -Dmaven.test.failure.ignore -U clean package"
        }
        junit '**/target/surefire-reports/TEST-*.xml'
        archive '**/target/*.jar'
    }
    milestone 40
}