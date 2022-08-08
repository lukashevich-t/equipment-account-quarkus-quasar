## Что это
Переделка проекта equipment-account под quarkus + quasar


## Bootstrap
### backend
    mvn io.quarkus.platform:quarkus-maven-plugin:2.11.1.Final:create -DprojectGroupId=by.gto.equipment -DprojectArtifactId=equipment.account.backend -Dextensions="resteasy,resteasy-jackson,elytron-security-jdbc,jdbc-mariadb,mybatis,kotlin" -DbuildTool=gradle-kotlin-dsl

### Frontend
```
yarn global add @quasar/cli
yarn create quasar

# Ответы на вопросы:
# √ What would you like to build? » App with Quasar CLI, let's go!
# √ Project folder: ... equipment-frontend
# √ Pick Quasar version: » Quasar v2 (Vue 3 | latest and greatest)
# √ Pick script type: » Typescript
# √ Pick Quasar App CLI variant: » Quasar App CLI with Vite
# √ Package name: ... equipment-frontend
# √ Project product name: (must start with letter if building mobile apps) ... Equipment frontend
# √ Project description: ... A Quasar Project
# √ Author: ... Tim Lukashevich <silvernet@yandex.ru>
# √ Pick a Vue component style: » Composition API
# √ Pick your CSS preprocessor: » Sass with SCSS syntax
# √ Check the features needed for your project: » ESLint, State Management (Pinia), Axios
# √ Pick an ESLint preset: » Prettier

cd equipment-frontend
quasar dev
```