    def image = "fae-documentation-server"
    def registry = "docker.nexus.archi-lab.io/archilab"

    stage('Checkout Global') {
        dir("_posts/global") {
            git(
                    url: 'https://github.com/Archi-Lab-FAE/fae-global-documentation',
                    credentialsId: 'archilab-github-jenkins',
                    branch: 'master'
            )
            sh "chmod 755 $workspace/create_changeLog_of_directory.sh"
            sh "$workspace/create_changeLog_of_directory.sh"
        }
    }

    stage('Checkout Team-1') {
        dir("_posts/team1") {
            git(
                    url: 'https://github.com/Archi-Lab-FAE/fae-team-1-documentation.git',
                    credentialsId: 'archilab-github-jenkins',
                    branch: 'master'
            )
            sh "$workspace/create_changeLog_of_directory.sh"
        }
    }

    stage('Checkout Team-2') {
        dir("_posts/team2") {
            git(
                    url: 'https://github.com/Archi-Lab-FAE/fae-team-2-documentation.git',
                    credentialsId: 'archilab-github-jenkins',
                    branch: 'master'
            )
            sh "$workspace/create_changeLog_of_directory.sh"
        }
    }

    stage('Checkout Team-3') {
        dir("_posts/team3") {
            git(
                    url: 'https://github.com/Archi-Lab-FAE/fae-team-3-documentation.git',
                    credentialsId: 'archilab-github-jenkins',
                    branch: 'master'
            )
            sh "$workspace/create_changeLog_of_directory.sh"
        }
    }

    stage('Checkout Team-4') {
        dir("_posts/team4") {
            git(
                    url: 'https://github.com/Archi-Lab-FAE/fae-team-4-documentation.git',
                    credentialsId: 'archilab-github-jenkins',
                    branch: 'master'
            )
            sh "$workspace/create_changeLog_of_directory.sh"
        }
    }

    stage('Build Production Image') {
        docker.withRegistry('https://docker.nexus.archi-lab.io', 'archilab-nexus-jenkins') {
            def customImage = docker.build("${registry}/${image}:${env.BUILD_ID}")
            customImage.push()
            customImage.push('latest')
        }
    }

    stage('Deploy') {
        docker.withServer('tcp://10.10.10.61:2376', 'fae-ws2019-certs') {
            docker.withRegistry('https://docker.nexus.archi-lab.io', 'archilab-nexus-jenkins') {
                sh "env TAG=${env.BUILD_ID} docker stack deploy --with-registry-auth \
                    -c ./docker-compose-prod.yml ${image}"
            }
        }
    }

