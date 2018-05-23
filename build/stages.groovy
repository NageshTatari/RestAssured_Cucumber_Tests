#!groovy

def doDeploymentTest(env_name) {
	stage("Test") {
		try {
			sh "mvn test sonar:sonar -Dsonar.branch=develop -Dtest=RunCukesTest -Denvironment=${env_name}"
		} finally {
			step($class: 'CucumberTestResultArchiver', testResults: 'target/cucumber.reports/cucumber.json')
			cucumber '**/*.json'
		}
	}
}

return this
