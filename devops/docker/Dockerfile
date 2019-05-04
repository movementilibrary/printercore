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
ENV APPNAME $PROJECT_NAME-$VERSION-$REVISION.jar
# App envs config
ENV SPRING_PROFILES_ACTIVE=deploy

#Copia de artefato localizado na maquina real para dentro da imagem
#Configuracao de permissoes
COPY --chown=1001:app build/libs/"$APPNAME" /app/

#Configuracao de diretorio de trabalho. /app ser? a pasta inicial da imagem
WORKDIR /app

#Start app. O comando ENTRYPOINT e executado na inicializacao do
#container baseado na imagem gerada pelo dockerfile em quest?o.
#Neste momento o resultado do comando pwd ja e o diretorio definico pelo comando WORKDIR
CMD java -jar $APPNAME -Xms678m -Xmx950m

#Exp?e a porta 8080 quando o container ? montado.
EXPOSE 9090