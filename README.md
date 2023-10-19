- run following command to build and run application
  mvn spring-boot:run
- run following command to create .war file
  mvn clean install
- copy the .war file into C:\[APACHE-DIRECTORY]\webapps
- goto C:\[APACHE-DIRECTORY]\bin and run the following command
  catalina.bat run
- open a browser, goto http://localhost:8088/ ,it will open tomcat server homepage
- goto http://localhost:8088/manager/html, type username and password as [username: admin, password:password], which is mentioned in apache-tomcat/conf/tomcat-users.xml
- upload .war file or run existing war file
