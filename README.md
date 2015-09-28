Digital Java EE 7 Book
==========================

Hello All


This is the author Peter Pilgrim


This is the official repository for my book, **Digital Java EE 7 Web Application Development** which was published
before the annual JavaOne 2015 in California.


You can obtain a copy of the book from [Packt Publishing](http://packtpub.com/) in hard copy and/or electronic format.
HINT: Search for my name surname [by Author](https://www.packtpub.com/all/?search=pilgrim#)




    ________  .__       .__  __         .__   
    \______ \ |__| ____ |__|/  |______  |  |  
     |    |  \|  |/ ___\|  \   __\__  \ |  |  
     |    `   \  / /_/  >  ||  |  / __ \|  |__
    /_______  /__\___  /|__||__| (____  /____/
            \/  /_____/               \/      
                 ____.                     ______________________ _________ 
            |    |____ ___  _______    \_   _____/\_   _____/ \______  \
            |    \__  \\  \/ /\__  \    |    __)_  |    __)_      /    /
        /\__|    |/ __ \\   /  / __ \_  |        \ |        \    /    / 
        \________(____  /\_/  (____  / /_______  //_______  /   /____/  
                      \/           \/          \/         \/            
                      (C) Peter Pilgrim, Packt Publishing, 2015




This source code is Java SE 8 and therefore you will require an appropriate. If do not have JDK 8 installed, 
the please download [Oracle JDK 8](http://java.oracle.com) and then install it on your system. 

You will also need an Integrated Development Environment of equivalent editor. I suggest you look at 
IntelliJ. However Eclipse and NetBean are also good second choices. 

You need also to download and install a Java EE 7 application server. This book was written with the 
 reference implementation [GlassFish 4.1](https://glassfish.java.net/). Since Java EE 7 is designed to be standard, 
 the examples should work mostly unchanged against [WildFly 9](http://wildfly.org/) or the upcoming 
 [Tom EE 2.0](http://tomee.apache.org/apache-tomee.html)
  
I build source code with The Gradle build tool. So it will help to have your own copy of [Gradle 2.7](http://gradle.org) 
or better installed on your system. 


## Source Code

This source code distribution can be found on GitHub after the book's publication date:

http://github.com/peterpilgrim/digital-javaee7

(The code will be uploaded simultaneously with the book's
publication date ;-)


# LICENSE

The entire source code and software for the book falls under the GNU
GENERAL PUBLIC LICENSE (Version 3, 29 June 2007)
http://www.gnu.org/licenses/gpl-3.0.txt

See the ``LICENSE.txt'' for the full legal text.


# Compile the projects

Each of the chapters in the book has its own folder, and within those
chapter folder there is at least one project folder with a Gradle
project. You will recognize them, because they have a ``build.gradle'
file inside them. This is the project build file.


Go http://gradle.org/ and download Gradle version 1.6 or better

To compile the program

        % gradle build


Explicitly to just compile the sources, run this command line:

        % gradle compileJava


To clean the project and reset to the start, run this command line:

        % gradle clean



## Running unit test from Gradle

Ask Gradle to run the Unit Tests with the following comand:

    % gradle test --info


Look for the results in the folder `build/reports' and view the HTML
file inside a web browser or examine the XML test file. By the way all
of the tests from the source code were written with JUnit.




## Generating a Eclipse project

Ask Gradle to (re-)create Eclipse project artifacts ``.classpath''
and ``.project''

    % gradle eclipse



## Generating a IDEA Project

Ask Gradle to (re-)create IDEA project configuration files ``*.ipr''
and ``*.iml'' etc

    % gradle idea



## Finding out project dependencies

You can ask a gradle project about its dependencies with the following:

    % gradle dependencies


And you can find out about the tasks that you can invoke with:

    % gradle tasks



                                           
## Building other artifacts

Some project create WAR files. Gradle plugin for WAR files bind automatically `assemble'. 
This also applies to EAR and RAR files. So this implies that:


    % gradle assemble



Will do the right thing for certain projects. Here are some other useful Gradle tasks.

    % gradle jar        // for project that builds a JAR
    % gradle war        // ditto
    % gradle ear        // ditto




The End.

