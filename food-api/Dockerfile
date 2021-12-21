# FROM - imagem base
# WORKDIR - diretorio onde os comandos serão executadas
# COPY - copia o arquivo .jar para dentro da imagem
# EXPOSE - informa qual porta o container vai executar quando a aplicação API estiver em execução
# CMD - define comando padrão quando o container inicia, parâmetros como array
# ARG - cria variável em tempo de build, para receber JAR_FILE configurado no pom.xml
# RUN - torna arquivo executável

FROM openjdk:11-jre-slim

WORKDIR /app

ARG JAR_FILE

COPY target/${JAR_FILE} /app/api.jar
COPY wait-for-it.sh /wait-for-it.sh

RUN chmod +x /wait-for-it.sh

EXPOSE 8080

CMD ["java", "-jar", "api.jar"]
