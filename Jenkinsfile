node {
    def repoURL = "${REGISTRY_URL}/${PROJECT_ID}/${ARTIFACT_REGISTRY}"

    stage('Github Checkout') {
        checkout([$class: 'GitSCM',
            branches: [[name: '*/main']],
            extensions: [],
            userRemoteConfigs: [[credentialsId: 'git', 
            url: 'https://github.com/valorjj/cloud-gateway.git']]
        ])
    }

    stage('Build and Push Image to Google Cloud') {
        withCredentials([file(credentialsId: 'gcp', variable: 'GC_KEY')]) {
            sh("gcloud auth activate-service-account --key-file=${GC_KEY}")
            sh("gcloud auth configure-docker asia-northeast3-docker.pkg.dev")
            sh("./gradlew clean jib -DREPO_URL=${repoURL}")
        }
    }

    stage('Deploy') {
        sh("sed -i 's|IMAGE_URL|${repoURL}|g' k8s/deployment.yml")
        step([$class: 'KubernetesEngineBuilder',
            projectId: env.PROJECT_ID,
            clusterName: env.CLUSTER,
            location: env.ZONE,
            manifestPattern: 'k8s/deployment.yml',
            credentialsId: env.GOOGLE_SERVICE_ACCOUNT_CREDENTIAL,
            verifyDeployments: true])
    }
}