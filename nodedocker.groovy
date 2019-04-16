job('Build_Nodejs_Project') {
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
        nodejs('nodeJS_10.15.3') // this is the name of the NodeJS installation in 
                         // Manage Jenkins -> Configure Global Tools -> NodeJS Installations -> Name
    }
    steps {
        shell("npm install @angular/cli")
        shell("npm install")
        dockerBuildAndPublish {
            repositoryName('ferman18/nodejs_app_dev')
            tag('${BUILD_NUMBER}')
            registryCredentials('dockerhub')
            forcePull(false)
            forceTag(false)
            createFingerprints(false)
            skipDecorate()
        }        
    }
}