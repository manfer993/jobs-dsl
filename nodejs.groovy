job('NodeJS') {
    scm {
        git('git://github.com/wardviaene/docker-demo.git') {  node -> // is hudson.plugins.git.GitSCM
            node / gitConfigName('DSL User')
            node / gitConfigEmail('jenkins-dsl@newtech.academy')
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
        shell("npm install")
    }
}