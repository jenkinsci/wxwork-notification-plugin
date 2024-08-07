/*
 See the documentation for more options:
 https://github.com/jenkins-infra/pipeline-library/
*/
buildPlugin(
    forkCount: '1C', // Run a JVM per core in tests
    useContainerAgent: true,
    configurations: [
        [platform: 'linux', jdk: 11],
        [platform: 'windows', jdk: 11],
    ]
)
