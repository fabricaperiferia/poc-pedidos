pipeline {
	agent any
	stages {
		stage('Build Info') {
		    steps {
			echo "Running ${env.BUILD_ID} on ${env.JENKINS_URL}"
		    }
		}
		stage('Build and Test Image') {
		    steps {
			sh "docker build -f Dockerfile -t periferiafactory/pedidos:v$BUILD_NUMBER --build-arg APP_VERSION=1 ."
		    }
		}
		stage('Push Image') {
			steps {
				withDockerRegistry([ credentialsId: "docker", url: "" ]) {
					sh 'docker push periferiafactory/pedidos:v$BUILD_NUMBER'
				}
			}
	    	}
		stage('K8 Update') {
			steps {
				sh 'kubectl -n demo-ath  --record deployment.apps/pedidos-deployment set image deployment.v1.apps/pedidos-deployment pedidos=periferiafactory/pedidos:v$BUILD_NUMBER'
			}
		}
	}
}
