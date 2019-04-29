#Imagem base
FROM imagesdasa.azurecr.io/prd/base-image

#Atualiza��o de pacotes e instala��o do java
RUN add-apt-repository ppa:openjdk-r/ppa
RUN apt-get update && \
apt-get install -y openjdk-8-jdk

#Cria��o do diret�rio /app
RUN mkdir /app

#Configura��o de usu�rio e grupo
RUN groupadd app
RUN useradd app -u 1001 -g app -d /app -s /bin/bash

#Recebimento de argumentos via linha de comando (docker build )
ARG PROJECT_NAME
ARG VERSION
ARG REVISION
ARG elkHost
ARG elkIndex
ARG elkHealth

#Configuracao de variaveis de ambiente da imagem
ENV PROJECT_NAME $PROJECT_NAME
ENV VERSION $VERSION
ENV REVISION $REVISION
ENV APPNAME $PROJECT_NAME.jar
ENV elkHost $elkHost
ENV elkIndex $elkIndex
ENV elkHealth $elkHealth

#C�pia de artefato localizado na m�quina real para dentro da imagem
COPY build/libs/"$APP_NAME" /app/

#Configura��o de permiss�es
RUN chown 1001:app /app -R
#Configura��o de diret�rio de trabalho. /app ser� a pasta inicial da imagem
WORKDIR /app

#Start app. O comando ENTRYPOINT � executado na inicializa��o do
#container baseado na imagem gerada pelo dockerfile em quest�o.
#Neste momento o resultado do comando pwd ja � o diret�rio definico pelo comando WORKDIR
CMD ["java", "-jar", "$APPNAME"]

#Exp�e a porta 8080 quando o container � montado.
EXPOSE 8080