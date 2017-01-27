node {
    milestone 10
    stage('Continuous Integration') {
        checkout([$class: 'GitSCM', branches: [[name: '*/master']], doGenerateSubmoduleConfigurations: false, extensions: [[$class: 'WipeWorkspace']], submoduleCfg: [], userRemoteConfigs: [[credentialsId: 'jenkins ssh', url: 'git@github.com:Kloudtek/ktutils.git']]])
        mvnHome = tool name: 'maven', type: 'maven'
        echo "GIT Commit = ${GIT_COMMIT}"
        withMaven( maven: 'maven', mavenSettingsConfig: 'e37672cf-602b-476f-8ec4-da37669113e6') {
            sh "mvn -Dmaven.test.failure.ignore -U -P release clean deploy"
        }
        junit '**/target/surefire-reports/TEST-*.xml'
        archive 'target/*.jar'
    }
}
milestone 20
input message: 'Release ?', ok: 'Release', submitter: 'ymenager'
milestone 30
node {
    stage('Release') {
        checkout([$class: 'GitSCM', branches: [[name: '*/master']], doGenerateSubmoduleConfigurations: false, extensions: [[$class: 'WipeWorkspace']], submoduleCfg: [], userRemoteConfigs: [[credentialsId: 'jenkins ssh', url: 'git@github.com:Kloudtek/ktutils.git']]])
        withMaven( maven: 'maven', mavenSettingsConfig: 'e37672cf-602b-476f-8ec4-da37669113e6') {
            sh "mvn -Dmaven.test.failure.ignore -U clean"
        }
    }
    milestone 40
}