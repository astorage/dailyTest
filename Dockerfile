FROM tomcat:8.5.24-jre8
MAINTAINER carson "carson.luo@jollycorp.com"

ENV TZ=Asia/Shanghai
ENV TOMCAT_HOME=/usr/local/tomcat
RUN ln -snf /usr/share/zoneinfo/$TZ /etc/localtime && echo $TZ > /etc/timezone

##RUN rm $TOMCAT_HOME/bin/catalina.sh
COPY catalina.sh $TOMCAT_HOME/bin/catalina.sh

##RUN rm $TOMCAT_HOME/conf/server.xml
COPY server.xml $TOMCAT_HOME/conf/server.xml

RUN rm -rf $TOMCAT_HOME/webapps/*
COPY target/test.war $TOMCAT_HOME/webapps

EXPOSE 8080


CMD ["catalina.sh", "run"]