job('Portafolio_deploy_heroku') {
    description('This job generate a image of the app and deploy it in a container and at heroku')
    scm {
        git(){ 
            remote {
                name('origin')
                url('https://github.com/manfer993/portafolio.git')
            }
            remote {
                name('heroku')
                url('https://git.heroku.com/ci-node-app.git')
                credentials('heroku')
            }
            branch('*/development')
            configure { node ->
                node / 'extensions' / 'hudson.plugins.git.extensions.impl.UserIdentity' << { 
                    name('manfer993') 
                    email('manfer93@gmail.com') 
                }
            }
        }
    }
    triggers {
        githubPush()
        scm('H/5 * * * *')        
    }
    wrappers {
        nodejs('nodeJS_10.15.3')
    }
    steps {
        shell('npm install @angular/cli')
        shell('npm install')
        dockerBuildAndPublish {
            repositoryName('ferman18/nodejs_app_dev')
            tag('${BUILD_NUMBER}')
            registryCredentials('dockerHub')
            forcePull(false)
            forceTag(false)
            createFingerprints(false)
            skipDecorate()        
        }
        shell ("if docker container ls -a | grep portafolioDev; then docker container stop portafolioDev && docker container rm portafolioDev; fi")
        shell('sleep 5')  
        shell('docker container run -d -p 8085:80 --name portafolioDev ferman18/nodejs_app_dev:latest')      
    }
    publishers {
        git {
            pushOnlyIfSuccess()
            branch('heroku', 'master')
        }
    }
}
job('Portafolio_backend_test'){
    description('This job run a test in Postman with Newman')
    scm {
        git('https://github.com/manfer993/test-postman-newman.git','*/master')
    }
    wrappers {
        nodejs('nodeJS_10.15.3')
    }
    steps {
        shell('npm install')
        shell('npm run test')
    }
}