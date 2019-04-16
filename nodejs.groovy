job('Deploy_Nodejs_Project') {
    scm {
        // git('https://github.com/manfer993/portafolio.git','development'){ node -> // is hudson.plugins.git.GitSCM
        //     node / gitConfigName('manfer993')
        //     node / gitConfigEmail('manfer93@gmail.com')
        // }
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
            
        }
    }
    triggers {
        githubPush()
        // scm('H/5 * * * *')
    }
    // wrappers {
    //     nodejs('nodeJS_10.15.3') // this is the name of the NodeJS installation in 
    //                      // Manage Jenkins -> Configure Global Tools -> NodeJS Installations -> Name
    // }
    // steps {
    //     shell("npm install @angular/cli")
    //     shell("npm install")
    // }
    publishers {
        git {
            pushOnlyIfSuccess()
            tag('heroku', 'foo-$PIPELINE_VERSION') {
                message('Release $PIPELINE_VERSION')
                create()
            }
            branch('heroku', 'master')
        }
    }
}