node {
    def repoURL = "${REGISTRY_URL}/${PROJECT_ID}/${ARTIFACT_REGISTRY}"

    stage('Github Repository Checkout') {
        checkout([$class: 'GitSCM',
            branches: [[name: '*/main']],
            extensions: [],
            userRemoteConfigs: [[credentialsId: 'git', 
            url: 'https://github.com/valorjj/cloud-gateway.git']]
        ])
    }

    stage('Build and Push Image to Google Cloud Artifact Registry') {
        withCredentials([file(credentialsId: 'gcp', variable: 'GC_KEY')]) {
            sh("gcloud auth activate-service-account --key-file=${GC_KEY}")
            sh("gcloud auth configure-docker ${REGISTRY_URL}")
            sh("./gradlew clean jib -DREPO_URL=${repoURL}")
        }
    }


    // ingress 테스트 중
    stage('Deploy to GKE') {
        sh("sed -i 's|IMAGE_URL|${repoURL}|g' k8s/deployment_ingress_test.yml")

        step([$class: 'KubernetesEngineBuilder',
            projectId: env.PROJECT_ID,
            clusterName: env.CLUSTER,
            location: env.ZONE,
            manifestPattern: 'k8s/deployment_ingress_test.yml',
            credentialsId: env.GOOGLE_SERVICE_ACCOUNT_CREDENTIAL,
            verifyDeployments: true])
    }
}