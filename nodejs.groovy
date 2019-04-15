job('Build_Nodejs_Project') {
    scm {
        git{  node -> // is hudson.plugins.git.GitSCM
            node / gitConfigName('manfer993')
            node / gitConfigEmail('manfer93@gmail.com')
            remote {
                name('origin')
                url('https://github.com/manfer993/portafolio.git')
            }
            branchs('development')
        }
    }
    triggers {
        scm('H/5 * * * *')
    }
    wrappers {
        nodejs('nodeJS_10.15.3') // this is the name of the NodeJS installation in 
                         // Manage Jenkins -> Configure Global Tools -> NodeJS Installations -> Name
    }
    steps {
        shell("npm install @angular/cli")
        shell("npm install")
    }
}