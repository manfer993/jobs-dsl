pipelineJob('ReactApp_deploy') {
    description("Pipeline for react app")
    triggers {
        githubPush()
    }    
    definition {
        cpsScm {
            scm {
                git {
                    remote {
                        name('origin')
                        url('https://github.com/manfer993/simple-node-js-react-npm-app.git') 
                    }
                    branches('*/master')
                    scriptPath('Jenkinsfile')
                    extensions { }  // required as otherwise it may try to tag the repo, which you may not want
                }
            }
        }
    }
}