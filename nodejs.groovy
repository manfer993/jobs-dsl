job('deploy-app-heroku') {
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
    }
    publishers {
        git {
            pushOnlyIfSuccess()
            tag('heroku', 'foo-${BUILD_NUMBER}') {
                message('Release number ${BUILD_NUMBER}')
                create()
            }
            branch('heroku', 'master')
        }
    }
}