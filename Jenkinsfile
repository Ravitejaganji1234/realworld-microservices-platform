pipeline {
  agent any

  environment {
    // ===== GCP / Registry =====
    GCP_PROJECT   = "test-12625323"
    REGION        = "us-east1"
    REGISTRY_REPO = "gke-microservice"
    REGISTRY      = "${REGION}-docker.pkg.dev/${GCP_PROJECT}/${REGISTRY_REPO}"

    // ===== Git =====
    GITOPS_REPO   = "https://github.com/Ravitejaganji1234/realworld-microservices-gitops.git"

    // ===== Image Tag =====
    IMAGE_TAG     = "${BUILD_NUMBER}"
  }

  options {
    timestamps()
  }

  stages {

    stage("Checkout App Repo") {
      steps {
        checkout scm
      }
    }

    stage("GCP Auth") {
  steps {
    sh '''
//       gcloud config set project $GCP_PROJECT
      gcloud auth configure-docker ${REGION}-docker.pkg.dev -q
    '''
  }
}

// stage("Verify GCR Auth") {
//   steps {
//     sh '''
//       set -e

//       echo "=== Active GCP Account (ADC) ==="
//       gcloud auth list

//       echo "=== GCP Project ==="
//       gcloud config get-value project

//       echo "=== Docker config (jenkins user) ==="
//       cat ~/.docker/config.json || echo "NO DOCKER CONFIG FOUND"

//       echo "=== Checking Artifact Registry access ==="
//       gcloud artifacts repositories list \
//         --location=$REGION \
//         --project=$GCP_PROJECT

//       echo "=== Dry-run Docker auth check ==="
//       docker pull ${REGION}-docker.pkg.dev/google-containers/pause:3.9 || true
//     '''
//   }
// }



    stage("Build & Push Images") {
      steps {
        sh '''
          set -e

          docker build -t $REGISTRY/user-service:$IMAGE_TAG ./userservice
          docker push $REGISTRY/user-service:$IMAGE_TAG

          docker build -t $REGISTRY/order-service:$IMAGE_TAG ./orderservice
          docker push $REGISTRY/order-service:$IMAGE_TAG

          docker build -t $REGISTRY/catalog-service:$IMAGE_TAG ./catalogservice
          docker push $REGISTRY/catalog-service:$IMAGE_TAG

          docker build -t $REGISTRY/api-gateway:$IMAGE_TAG ./gateway
          docker push $REGISTRY/api-gateway:$IMAGE_TAG

          docker build -t $REGISTRY/frontend:$IMAGE_TAG ./frontend
          docker push $REGISTRY/frontend:$IMAGE_TAG
        '''
      }
    }

    stage("Update GitOps Repo") {
      steps {
        withCredentials([usernamePassword(
          credentialsId: 'github-creds',
          usernameVariable: 'GIT_USER',
          passwordVariable: 'GIT_TOKEN'
        )]) {
          sh '''
            set -e

            rm -rf gitops
            git clone https://$GIT_USER:$GIT_TOKEN@${GITOPS_REPO#https://} gitops
            cd gitops

            sed -i "s|user-service:.*|user-service:$IMAGE_TAG|" argocd-prod/user-service.yaml
            sed -i "s|order-service:.*|order-service:$IMAGE_TAG|" argocd-prod/order-service.yaml
            sed -i "s|catalog-service:.*|catalog-service:$IMAGE_TAG|" argocd-prod/catalog-service.yaml
            sed -i "s|api-gateway:.*|api-gateway:$IMAGE_TAG|" argocd-prod/api-gateway.yaml
            sed -i "s|frontend:.*|frontend:$IMAGE_TAG|" argocd-prod/frontend.yaml

            git config user.name "jenkins"
            git config user.email "jenkins@ci.local"

            git add argocd-prod
            git commit -m "ci: update images to build $IMAGE_TAG"
            git push origin main
          '''
        }
      }
    }
  }

  post {
    success {
      echo "CI completed. Argo CD will deploy build ${IMAGE_TAG}."
    }
    failure {
      echo "CI failed. No changes pushed to GitOps repo."
    }
  }
}
