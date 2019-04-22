pipelineJob('Portafolio_deploy_docker') {
    description("Pipeline for nodejs app call Portafolio to deploy on a container that listen by the port 8085")
    triggers {
        githubPush()
        scm('H/5 * * * *')
    }
    definition {
        cpsScm {
            scm {
                git {
                    remote {
                        name('origin')
                        url('https://github.com/manfer993/portafolio.git') 
                    }
                    branches('*/development')
                    scriptPath('jenkinsfile')
                    extensions { }  // required as otherwise it may try to tag the repo, which you may not want
                }
            }
        }
    }
}