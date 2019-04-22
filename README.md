# Jobs DSL

Este proyecto genera mediante codigo en groovy, 2 pipelines y 2 jobs en jenkins

## Plugins de Jenkins necesarios

- Job DSL
- GitHub plugin

## Plugins utilizados dentro de los Jobs y Pipelines

- NodeJS Plugin
- SonarQube Scanner for Jenkins
- Docker Pipeline


## Como ejecutar el proyecto

1. Generar una nueva tarea de estilo libre en Jenkins.
2. Configurar el origen del  codigo fuente en Git.
    - Ingresar la URL del repositorio: **https://github.com/manfer993/jobs-dsl.git**
    - Especificar la rama del repositorio: **/master**
3. Establecer a Github hook trigger como disparador de ejecuciones.
4. Añadir un paso para ejecutar de tipo: **Process Job DSLs**  
    - Ingresar el nombre de los DSL scripts que se van a construir:
        - `jobNodejsApp.groovy`
        - `pipelineNodejsApp.groovy`
        - `pipelineReact.groovy`
5. Guardar.
6. Construir el proyecto.</br>

La primera construcción del proyecto va a fallar puesto que Jenkins necesita que sean aprobados los scripts antes de construirlos. </br>

7. Ingresar a Manage Jenkins > In-process Script Approval.
8. Aprobar los 3 Scripts del proyecto.
9. Construir el proyecto nuevamente.</br>

Ahora se deben haber generados los 2 nuevos pipelines y los 2 nuevos jobs dentro del workspace de Jenkins</br>

|       Project Name       |    Type   |              General description of the project                  |
|:------------------------:|:---------:|:----------------------------------------------------------------:|
| Portafolio_backend_test  | Freestyle | Run a test case to API REST services with Newman                 |
| Portafolio_deploy_docker | Pipeline  | Build an angular App and deploy it in a nginx container          |
| Portafolio_deploy_heroku | Freestyle | Build an angular App and deploy it in Heroku                     |
| ReactApp_deploy          | Pipeline  | Build a react App and deploy it in the localhost waiting to test |


## Proyecto Portafolio_backend_test

- Script groovy que genera el proyecto: jobNodejsApp.groovy.
- Url del proyecto: https://github.com/manfer993/test-postman-newman.git
- Rama del repositorio: `*/master`.
- Pre-requisitos Jenkins:
    - Plugin NodeJS.
    - Instalar Nodejs version 10.15.3 dentro de Jenkins, ruta: `manage jenkins > Global Tool Configuration > NodeJS`.
- Pasos del Freestyle job:
    - Obtener codigo fuente.
    - Proporcionar nombre de la instalacion de NodeJS, `Provide Node & npm bin/ folder to PATH`.
    - Instalar dependencias del proyecto mediante linea de comando shell `npm install`.
    - Correr test case de postman exportado en formato Json mediante Newman mediante linea de comando shell `npm run test`.

## Proyecto Portafolio_deploy_docker

- Script groovy que genera el proyecto: pipelineNodejsApp.groovy.
- Url del proyecto: https://github.com/manfer993/portafolio.git
- Rama del repositorio: `*/development`.
- Pre-requisitos Jenkins:
    - Plugins NodeJS, SonarQube Scanner.
    - Tener instalado docker dentro del servidor de Jenkins.
    - Instalar Nodejs version 10.15.3 dentro de Jenkins, ruta: `manage jenkins > Global Tool Configuration > NodeJS`.
- Pasos del Jobs DSL:
    - Establecer los push de github como disparador de la construccion.
    - Programar a Jenkins para que consulte cambios en el repositorio cada 5 minutos.
    - Definir el pipeline mediante script dentro del repositorio en el documento `jenkinsfile`.
- Pasos del Pipeline dentro del Jenkinsfile:
    - SCM: obtener codigo fuente.
    - Install node modules: instalar modulos del proyecto `npm install`.
    - Build project: Construir el proyecto `npm run build`.
    - Scan project: escanear el proyecto con sonarqube.
    - Build image: crear imagen base `docker.build("ferman18/nodejs_app_dev")` utilizando el dockerfile que se encuentra en la raiz del proyecto.
    - Validate container existence: validar que no exista un contenedor con el mismo nombre.
    - Create container: Crear contenedor con la imagen previamente creada y exponerlo por el puerto 8085.

## Proyecto Portafolio_deploy_heroku

- Script groovy que genera el proyecto: jobNodejsApp.groovy.
- Url del proyecto: https://github.com/manfer993/portafolio.git
- Rama del repositorio: `*/development`.
- Pre-requisitos proyecto:
    - Tener un proyecto en HerokuApp llamado `ci-node-app`
    - Tener un repositorio publico dentro de DockerHub llamado `nodejs_app_dev` 
- Pre-requisitos Jenkins:
    - Plugins NodeJS, docker build and publish.
    - Tener instalado docker dentro del servidor de Jenkins.
    - Instalar Nodejs version 10.15.3 dentro de Jenkins, ruta: `manage jenkins > Global Tool Configuration > NodeJS`.
    - Tener credenciales repositorio de Heroku
    - Tener credenciales repositorio de DockerHub
- Pasos del Freestyle job:
    - 