#Imagem base
FROM imagesdasa.azurecr.io/prd/base-image

#Atualização de pacotes e instalação do java
RUN add-apt-repository ppa:openjdk-r/ppa
RUN apt-get update && \
apt-get install -y openjdk-8-jdk

#Criação do diretório /app
RUN mkdir /app

#Configuração de usuário e grupo
RUN groupadd app
RUN useradd app -u 1001 -g app -d /app -s /bin/bash

#Recebimento de argumentos via linha de comando (docker build )
ARG PROJECT_NAME
ARG VERSION
ARG REVISION
ARG elkHost
ARG elkIndex
ARG elkHealth

#Configuração de variáveis de ambiente da imagem
ENV PROJECT_NAME $PROJECT_NAME
ENV VERSION $VERSION
ENV REVISION $REVISION
ENV APPNAME $PROJECT_NAME-$VERSION-$REVISION.jar
ENV elkHost $elkHost
ENV elkIndex $elkIndex
ENV elkHealth $elkHealth

#Cópia de artefato localizado na máquina real para dentro da imagem
COPY target/"$PROJECT_NAME-$VERSION-$REVISION.jar" /app/

#Configuração de permissões
RUN chown 1001:app /app -R
#Configuração de diretório de trabalho. /app será a pasta inicial da imagem
WORKDIR /app

#Start app. O comando ENTRYPOINT é executado na inicialização do
#container baseado na imagem gerada pelo dockerfile em questão.
#Neste momento o resultado do comando pwd ja é o diretório definico pelo comando WORKDIR
CMD ["java", "-jar", "$APPNAME"]

#Expõe a porta 9090 quando o container é montado.
EXPOSE 9090