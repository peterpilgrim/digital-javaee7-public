README GLASSFISH
==================


*VERY IMPORTANT NOTE*

Sadly, this project fails to run against GlassFish 4.1 or Payara server, because of a flaw in the JPA implementation: *EclipseLink*
I strongly recommend that you run against WildFly 8 or better, because that application server uses Hibernate underneath.

Peter Pilgrim (27-March-2015)



This application runs best against a real database such as *MySQL*. So this reason, you need to set your application server *GlassFish*, *WildFly*, *Payara* with a JDBC Resource connector.

These following instructions are for GlassFish, but the other web application server will similar configuration:


GlassFish 4.1
--------------


Download a JDBC MySQL driver  _mysql-connector-java-<VERSION>.jar_ and install in the shared library folder.


```
    cp mysql-connector-java-5.1.25/mysql-connector-java-5.1.25-bin.jar  glassfish-4.1-b13-12_11_2014/glassfish/lib
```


You need to download and install MySQL database, if you have not done so already.

You need to log into MySQL as administrator

```
    $ mysql --user=root --password=toomanysecrets
```

Create a schema for the product flow:

<pre>
   CREATE DATABASE productflow
</pre>

MySql names are case insensitive.

Create a new user account:

<pre>
   GRANT ALL PRIVILEGES ON product_flow.* TO 'digital'@'localhost' IDENTIFIED BY 'digital';

   FLUSH PRIVILEGES;
</pre>


In a separate terminal, ensure that you can login to MySQL separately as the user identified as *digital*.

```
    $ mysql --user=digital --password=digital
```

You should see something like this:

<pre>
    Welcome to the MySQL monitor.  Commands end with ; or \g.
    Your MySQL connection id is 8
    Server version: 5.6.11 MySQL Community Server (GPL)

    ...

    mysql>
</pre>

Exit the shell.

Restart GlassFish and enter the administration console.

## Create the JDBC Connection Pool ##

Navigate on the left hand pane to *Resources -> JDBC -> JDBC Connection Pools*.
In the JDBC Connection Pools frame click New. You will enter a two step wizard. In the Name field under *General Settings*
enter the name for the connection pool, for example enter *MySQLProductFlowPool*.


        Pool Name:  MySQLProductFlowPool
        Resource Type: javax.sql.DataSource
        Select or enter a database driver vendor: MySQL
        Introspect: Enabled
        DataSource Class Name: com.mysql.jdbc.jdbc2.optional.MysqlDataSource


In the *Advanced Properties* section, ensure that the following details are set. In particular, the user credential match the MySQL login user.


        User: digital
        Password: digital
        Url: jdbc:mysql://localhost:3306/productflow
        Server Name: localhost
        Database Name: productflow


In the JDBC Connection Pools frame click on the connection pool you just created. Here, you can review and edit information about the connection pool. Because the MySQL Connector/J does
not support optimized validation queries, go to the *Advanced* tab, and under *Connection Validation*, configure the following settings:

        Connection Validation - select Required.


Validation Method - select table from the drop-down menu.

        Table Name - enter DUAL.


The URL is a JDBC connection. After setting up the JDBC resource pool, you should be able to test the connection with a ping.



## Create the JDBC Resource  ##


Navigate on the left hand pane to *Resources -> JDBC -> JDBC Resources* and create a new resource:


        JNDI Name: jdbc/mysqlProductFlowDB
        JDBC Pool Name: MySQLProductFlowPool
        Description: optional description


The JNDI name must match the entry in the *src/main/webapp/WEB-INF/classes/META-INF/persistence.xml*


