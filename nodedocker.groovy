job('Build_Nodejs_Project_docker2') {
    scm {
        git('https://github.com/manfer993/portafolio.git','development'){ node -> // is hudson.plugins.git.GitSCM
            node / gitConfigName('manfer993')
            node / gitConfigEmail('manfer93@gmail.com')
        }
    }
    triggers {
        githubPush()
        scm('H/5 * * * *')
    }
    wrappers {
        nodejs('nodeJS_10.15.3')    // this is the name of the NodeJS installation in 
                                    // Manage Jenkins -> Configure Global Tools -> NodeJS Installations -> Name
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
        shell('if [[ -n `docker container ls -a | grep portafolioDev` ]]; then docker container stop portafolioDev && docker container rm portafolioDev; fi')
        shell(sleep 5)  
        shell('docker run -d -p 8085:80 --name portafolioDev ferman18/nodejs_app_dev:${BUILD_NUMBER}')      
    }
}