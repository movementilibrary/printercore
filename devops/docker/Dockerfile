#Imagem base
FROM imagesdasa.azurecr.io/prd/base-image

#Atualiza??o de pacotes e instalacao do java
RUN add-apt-repository ppa:openjdk-r/ppa
RUN apt-get update && \
apt-get install -y openjdk-8-jdk

#Cria??o do diretorio /app
RUN mkdir /app

#Configuracao de usuario e grupo
RUN groupadd app
RUN useradd app -u 1001 -g app -d /app -s /bin/bash

#Recebimento de argumentos via linha de comando (docker build )
ARG PROJECT_NAME
ARG VERSION
ARG REVISION
ARG elkHost
ARG elkIndex
ARG elkHealth
ARG rabbitHost
ARG rabbitPort
ARG rabbitUser
ARG rabbitPass
ARG redisHost
ARG redisPort
ARG redisPass
ARG oracleUrl
ARG oracleUser
ARG oraclePass

#Configuracao de variaveis de ambiente da imagem
ENV PROJECT_NAME $PROJECT_NAME
ENV VERSION $VERSION
ENV REVISION $REVISION
ENV APPNAME $PROJECT_NAME.jar
ENV elkHost $elkHost
ENV elkIndex $elkIndex
ENV elkHealth $elkHealth
# App envs config
ENV SPRING_RABBITMQ_ADDRESSES $rabbitHost
ENV SPRING_RABBITMQ_PORT $rabbitPort
ENV SPRING_RABBITMQ_USERNAME $rabbitUser
ENV SPRING_RABBITMQ_PASSWORD $rabbitPass
ENV SPRINT_REDIS_HOST $redisHost
ENV SPRINT_REDIS_PORT $redisPort
ENV SPRINT_REDIS_PASSWORD $redisPass
ENV SPRINT_DATASOURCE_URL $oracleUrl
ENV SPRINT_DATASOURCE_USERNAME $oracleUser
ENV SPRINT_DATASOURCE_PASSWORD $oraclePass
ENV SERVER_PORT 8080

#Copia de artefato localizado na maquina real para dentro da imagem
COPY build/libs/"$APP_NAME" /app/

#Configuracao de permissoes
RUN chown 1001:app /app -R
#Configuracao de diretorio de trabalho. /app ser? a pasta inicial da imagem
WORKDIR /app

#Start app. O comando ENTRYPOINT e executado na inicializacao do
#container baseado na imagem gerada pelo dockerfile em quest?o.
#Neste momento o resultado do comando pwd ja e o diretorio definico pelo comando WORKDIR
CMD ["java", "-jar", "$APPNAME"]

#Exp?e a porta 8080 quando o container ? montado.
EXPOSE 8080