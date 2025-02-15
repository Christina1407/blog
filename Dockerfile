FROM tomcat:10.1.35

RUN rm -rf /usr/local/tomcat/webapps/ROOT

COPY target/blog.war /usr/local/tomcat/webapps/blog.war

EXPOSE 8080