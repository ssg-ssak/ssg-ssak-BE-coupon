pipeline {
    agent any

    stages {
        stage('Build') {
            steps {
                sh '''
                    chmod +x gradlew
                    ./gradlew build -x test
                '''
            }
        }
        stage('DockerSize') {
            steps {
                sh '''
                    docker stop ssgpointapp_coupon || true
                    docker rm ssgpointapp_coupon || true
                    docker rmi ssgpoint_coupon || true
                    docker build -t ssgpoint_coupon .
                    echo ${MASTER_DB_URL}
                    echo $MASTER_DB_URL
                '''
            }
        }
        stage('Deploy') {
            steps {
                sh 'docker run -e MASTER_DB_URL="jdbc:mysql://15.164.17.12:3310/ssgcoupon?serverTimezone=Asia/Seoul&characterEncoding=UTF-8" -e MASTER_DB_USERNAME="${MASTER_DB_USERNAME}" -e MASTER_DB_PASSWORD="${MASTER_DB_PASSWORD}" -e SLAVE_DB_URL="jdbc:mysql://15.164.17.12:3311/ssgcoupon?serverTimezone=Asia/Seoul&characterEncoding=UTF-8" -e SLAVE_DB_USERNAME="${SLAVE_DB_USERNAME}" -e SLAVE_DB_PASSWORD="${SLAVE_DB_PASSWORD}" -e JWT_SECRET="${JWT_SECRET}" -d --network root_net-ssg-mysql --name ssgpointapp_coupon -p 8002:8002 ssgpoint_coupon'
            }
        }
    }
}