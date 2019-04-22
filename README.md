# Jobs DSL

Este proyecto genera mediante codigo en groovy, pipelines y jobs en jenkins

## Plugins de Jenkins necesarios

- Job DSL
- GitHub plugin

## Plugins utilizados dentro de los Jobs y Pipelines

- NodeJS Plugin
- SonarQube Scanner for Jenkins
- Docker Pipeline


## Como ejecutar el proyecto

1- Generar una nueva tarea de estilo libre en Jenkins
2- Configurar el origen del  codigo fuente en Git
    -- Ingresar la URL del repositorio **https://github.com/manfer993/jobs-dsl.git**
    -- Especificar la rama del repositorio **/master**
3- Establecer a Github hook trigger como disparador de ejecuciones
4- Añadir un paso para ejecutar de tipo **Process Job DSLs**  
    -- Ingresar el nombre de los DSL scripts que se van a construir **jobNodejsApp.groovy**
                                                                    **pipelineNodejsApp.groovy**
                                                                    **pipelineReact.groovy**
5- Guardar
6- Construir el proyecto

La primera construcción del proyecto va a fallar puesto que Jenkins necesita que sean aprobados los scripts antes de construirlos 

7- Ingresar a Manage Jenkins > In-process Script Approval 
8- Aprobar los 3 Scripts del proyecto
9- Construir el proyecto nuevamente

Ahora se deben haber generados los 2 nuevos pipelines y los 2 nuevos jobs dentro del workspace de Jenkins

|       Project Name       |    Type   |                    Description of the project                    |
|:------------------------:|:---------:|:----------------------------------------------------------------:|
| Portafolio_backend_test  | Freestyle | Run a test case to API REST services with Newman                 |
| Portafolio_deploy_docker | Pipeline  | Build an angular App and deploy it in a nginx container          |
| Portafolio_deploy_heroku | Freestyle | Build an angular App and deploy it in Heroku                     |
| ReactApp_deploy          | Pipeline  | Build a react App and deploy it in the localhost waiting to test |
