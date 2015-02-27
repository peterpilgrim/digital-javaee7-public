README GLASSFISH
==================


*NOTE*

Sadly, this project fails to run against GlassFish 4.1 or Payara server, because of a flaw in the JPA implementation: *EclipseLink*
I strongly recommend that you run against WildFly 8 or better, because that application server uses Hibernate underneath.

Peter Pilgrim (27-March-2015)



This application runs best against a real database such as *MySQL*. So this reason, you need to set your application server *GlassFish*, *WildFly*, *Payara* with a JDBC Resource connector.

These following instructions are for GlassFish, but the other web application server will similar configuration:




WildFly 8.1 or better
-----------------------


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


##Add Mysql to WildFly##


Adminstrate WildFly from the command line, so open a Terminal first. To activate WildFly command line prompt start WildFly, go to the <WILDFLY_HOME>/bin folder and execute the command:

    > jboss-cli.sh(.bat) --connect


It connects to localhost and port 9990 by default. So you should see output look this:


    [standalone@localhost:9990 /]


This is the prompt for the JBoss Command Line Interface program that lets you the developer-operators to administrate WildFly.

You need add to a JDBC driver to WildFly, in long form this command line looks like this following multi-line input.

    [standalone@localhost:9990 /] /subsystem=datasources/jdbc-driver=mysql:add(
        driver-name=mysql,
        driver-module-name=com.mysql,
        driver-class-name=com.mysql.jdbc.Driver
    )



Unfortunately, you need type or copy and paste as one single line, which actually becomes this command request:

    [standalone@localhost:9990 /] /subsystem=datasources/jdbc-driver=mysql:add(driver-name=mysql,driver-module-name=com.mysql,driver-class-name=com.mysql.jdbc.Driver)


The above command returns {"outcome" => "success"} in case of success. This command resulted in the following part in the configuration file:

    <datasources>
        {...}
        <drivers>
            {...}
            <driver name="mysql" module="com.mysql">
                <driver-class>com.mysql.jdbc.Driver</driver-class>
            </driver>
        </drivers>
    </datasources>


It makes the JDBC driver module available for the datasource creation.


## Configure the JDBC Resource ##


Start WildFly server and navigate to the admin console [http://localhost:9990/console/](http://localhost:9990/console/)
On the admin console:

1. Go to Profile > subsytems > Connector > Datasources and click on *Add* to create a datasource.
You should be looking at the Data Source in the Admin Console [http://localhost:9990/console/App.html#datasources](http://localhost:9990/console/App.html#datasources)
2. Provide a name to the data source to easily identify it in the console. I suggest *ProductFlowDS*
3. Define the JNDI name appending the prefix java:/ to your current datasource name like *jdbc:mysql://localhost:3306/productflow  and click Next.
4. Select the driver you deployed or added as a module and click Next.
5. Fill in the connection parameters to your database and click Done when finished. For example:


    Connection URL: jdbc:mysql://localhost:3306/productflow

    Username: digital

    Password: digital


You should now have a working JDBC Data Source.

The JNDI name must match the entry in the *src/main/webapp/WEB-INF/classes/META-INF/persistence.xml*


## Configure the JDBC Connection Pool ##


Let us now configure the connection pool:

1. Go and select the MySQL Data Source *ProductFlowDS* that we created in the last section. You might have to enable editing the configuration, just click on the *Disable* button.
2. Select the tab *Pool* and then click on *Edit*.
3. Add and reconfigure the pool connection values. For our purposes we only a few connections of learning application.
Some good values are *Min Pool Size* with 3 and *Max Pool Size* as 10.
4. Click on Save and restart the server to all changes take effect.
5. Go back to Profile > subsytems > Connector > Datasources, select the recently created datasource, select the tab Connection and click on *Test connection*.

This completes the configuration. Now deploy the application.



