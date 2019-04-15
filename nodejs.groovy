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
        sonarScanner properties:
        '''
        sonar.projectKey=build_NodeJs_App
        sonar.projectName=build_NodeJs_App
        sonar.projectVersion=1.0
        sonar.sourceEncoding=UTF-8
        sonar.sources=src
        sonar.exclusions=**/node_modules/**,**/*.spec.ts,**/dist/**,**/docs/**,**/*.js,**/e2e/**,**/coverage/**
        sonar.tests=src/app
        sonar.test.inclusions=**/*.spec.ts
        sonar.ts.tslint.configPath=tslint.json
        '''        
    }
}