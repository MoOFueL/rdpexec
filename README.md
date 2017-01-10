# rdpexec
Remote execution of PowerShell scripts

## How to start:
1.mvn clean install

2.In application.properties change following:

    2.1 First ever starting:
        spring.datasource.initialize=false -> true
        spring.jpa.hibernate.ddl-auto=update -> create
        
    2.2 Next start ups:
        spring.datasource.initialize=true -> false
        spring.jpa.hibernate.ddl-auto=create -> update

3. java -jar rdpexec-0.1.jar
