pipeline {
    agent none
    parameters {
        string(name: 'BRANCH', defaultValue: 'master', description: 'Branch')
        string(name: 'MOON_IP', defaultValue: 'moon.aerokube.local', description: 'Moon url')
        string(name: 'MOON_PORT', defaultValue: '4444', description: 'Moon port')
        string(name: 'BROWSER', defaultValue: 'chrome', description: 'Remote browser')
        booleanParam(name: 'FORCE', defaultValue: false, description: 'Принудительно пересобрать образ')
    }

    stages {
        stage("Build Docker image") {
            agent {
                kubernetes {
                    cloud "otus"
                    inheritFrom "kaniko"
                    defaultContainer "kaniko"
                }
            }
            steps {
                script {
                    def branch          = params.BRANCH ?: 'master'
                    def currentCommit   = env.GIT_COMMIT
                    def previousCommit  = env.GIT_PREVIOUS_COMMIT ?: ''
                    def force           = params.FORCE
                    def imageTag        = "registry.kube-system.svc.cluster.local/otus_homework_2:${branch}"

                    currentBuild.description =
                        "BRANCH: ${branch}\nCOMMIT: ${currentCommit}\nURL: ${params.URL}\nFORCE: ${force}"

                    if (force) {
                        echo "FORCE=true — принудительная сборка, пропускаем проверки."
                    } else {
                        if (previousCommit && previousCommit == currentCommit) {
                            echo "Коммит ${currentCommit} уже собирался — пропускаем."
                            return
                        }

                        def imageExists = sh(
                            returnStatus: true,
                            script: """
                                curl -sf -o /dev/null \
                                -H 'Accept: application/vnd.docker.distribution.manifest.v2+json' \
                                http://registry.kube-system.svc.cluster.local/v2/otus_homework_2/manifests/${branch}
                            """
                        ) == 0

                        if (imageExists && !previousCommit) {
                            echo "Образ ${imageTag} уже есть в реестре — пропускаем."
                            return
                        }
                    }

                    sh """
                        /kaniko/executor \
                        --context=dir://. \
                        --dockerfile=Dockerfile \
                        --cleanup \
                        --insecure \
                        --skip-tls-verify-pull \
                        --skip-tls-verify \
                        --destination=${imageTag}
                    """
                }
            }
        }
        stage('Run tests and generate report') {
            agent {
                kubernetes {
                    cloud "otus"
                    inheritFrom "default"
                    yaml """
                    apiVersion: v1
                    kind: Pod
                    spec:
                        containers:
                            - name: tests
                              image: localhost:5000/otus_homework_2:${branch}
                              imagePullPolicy: Always
                              command: ["sleep"]
                              args: ["infinity"]
                    """
                }
            }
            steps {
                container("tests") {
                    catchError(buildResult: "UNSTABLE", stageResult: "SUCCESS"){
                        script {
                            withEnv([
                                "MOON_IP=${params.MOON_IP}",
                                "MOON_PORT=${params.MOON_PORT}",
                                "BROWSER=${params.BROWSER}",
                            ]) {
                                sh "/otus_homework_2/entrypoint.sh"
                            }
                            sh "chmod -R 777 target || true"
                            allure(
                                results: [[path: "target/allure-results"]],
                                disabled: false,
                                reportBuildPolicy: "ALWAYS"
                            )
                        }
                    }
                }
            }
        }
    }
}