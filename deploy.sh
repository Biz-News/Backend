#!/bin/bash

# 🔹 환경 변수 설정
DOCKER_USERNAME="${DOCKERHUB_USERNAME}"
IMAGE_NAME="${DOCKERHUB_IMAGENAME}"
DOCKER_IMAGE="${DOCKER_USERNAME}/${IMAGE_NAME}"
BLUE_PORT=8080
GREEN_PORT=8081
NETWORK_NAME="leaf_village_network"

# 🔹 현재 활성화된 서버 확인
get_active_server() {
    if docker ps --filter "name=blue" --format "{{.Names}}" | grep -q "blue"; then
        echo "blue"
    else
        echo "green"
    fi
}

# 🔹 새로운 버전 배포
deploy_new_version() {
    local new_color=$1
    local port=$2
    local container_name="${new_color}"

    echo "🚀 Deploying new version to $new_color server..."
    docker pull $DOCKER_IMAGE:latest
    docker run -d --name $container_name -p $port:8080 --network $NETWORK_NAME -e TZ=Asia/Seoul $DOCKER_IMAGE:latest
}

# 🔹 헬스 체크 수행
health_check() {
    local port=$1
    local max_attempts=10
    local attempt=1

    echo "🩺 Performing health check..."
    while [ $attempt -le $max_attempts ]; do
        if curl -s "http://localhost:$port/health" | grep -q "UP"; then
            echo "✅ Health check passed!"
            return 0
        fi
        echo "⚠️ Attempt $attempt: Health check failed. Retrying in 5 seconds..."
        sleep 5
        attempt=$((attempt + 1))
    done

    echo "❌ Health check failed after $max_attempts attempts."
    return 1
}

# 🔹 이전 버전 정리
cleanup_old_version() {
    local old_color=$1

    echo "🗑 Cleaning up old $old_color server..."
    docker stop $old_color
    docker rm $old_color
}

# 🔹 메인 실행 로직
main() {
    local active_server=$(get_active_server)
    local new_color
    local new_port

    if [ "$active_server" == "blue" ]; then
        new_color="green"
        new_port=$GREEN_PORT
    else
        new_color="blue"
        new_port=$BLUE_PORT
    fi

    deploy_new_version $new_color $new_port

    if health_check $new_port; then
        cleanup_old_version $active_server
        echo "✅ Deployment successful! New version is now active on $new_color server."
    else
        echo "❌ Deployment failed. Rolling back..."
        docker stop $new_color
        docker rm $new_color
    fi

    docker image prune -f
}

# 🔹 스크립트 실행
main