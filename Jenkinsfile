node {
    // GCR 주소를 환경변수로 주입받음
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
        // jenkins 에 등록한 gcp 인증 정보
        withCredentials([file(credentialsId: 'gcp', variable: 'GC_KEY')]) {
            // gcp 에 등록한 서비스 계정의 토큰으로 인가
            sh("gcloud auth activate-service-account --key-file=${GC_KEY}")
            // gcp 에서 도커를 사용하기 위한 인증
            sh("gcloud auth configure-docker asia-northeast3-docker.pkg.dev")
            // 프로젝트 빌드 -> 도커 이미지 생성 -> 저장소에 push
            sh("./gradlew clean jib -DREPO_URL=${repoURL}") 
        }
    }

    stage('Deploy') {
        // 쿠버네티스 배포 파일에 변수 할당 -> artifact registry 주소
        sh("sed -i 's|IMAGE_URL|${repoURL}|g' k8s/deployment.yml") 
        // gcp 쿠버네티스 클러스터에 배포
        step([$class: 'KubernetesEngineBuilder',
            projectId: env.PROJECT_ID,
            clusterName: env.CLUSTER,
            location: env.ZONE,
            manifestPattern: 'k8s/deployment.yml',
            credentialsId: env.GOOGLE_SERVICE_ACCOUNT_CREDENTIAL,
            verifyDeployments: true])
    }
}