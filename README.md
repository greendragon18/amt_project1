amt_project1
============

##Intro
Amt_project1 is a web application that manage a collection of sensors, sorted by organisations and belong to many users.  
You can use the REST interface to collect observations from your sensors and save them into a database. Those informations can be compute and sorted for a monitoring usage, in a custom client-side application.  
According to your settings, all collected datas can be public or private to a specific organisation (for the related users).

##Installation
Please read the following steps to install amt_project1:

### Step 1: Required Software
Before using our application, you will first need to download and install the following software:  

- MySql (client-server) 5.5 or newer  
- Java EE 7 with an application server (GlassFish 4 or newer recommended)  
- Maven 3.2 or newer

**Note:** Please make sure everything has the same bitness: **32-bit and 64-bit modules do not mix under any circumstances**.

### Step 2: Catch the beast
Clone this repository with the following commands:  

```
git clone https://github.com/greendragon18/amt_project1.git
```
...or just download the master branch's ZIP file from https://github.com/greendragon18/amt_project1/archive/master.zip.

### Step 3: Configure the beast (domain and database)
Open the **INSTALL.sh** file and check on the header if constants fits with your system (especially the "PATH" part). If all seems good to you, run it:

```
cd amt_project1
./INSTALL.sh
```  
Be sure that a new folder has been created in your application server and a new user / database in your MySQL server.  

Then we'll build the application with maven.  
To download and install all the dependencies of the application, go to your **amt_project1** folder (you must find a **pom.xml** file) and run:

```
mvn package
```

### Step 4: Launch the beast
If everything has been setted up correctly to this point, you can try to launch the application throught your application server with the following command (GlassFish):

```
asadmin start-domain domainAMT  
deploy target/project1-1.0-SNAPSHOT
```

Please note that, in every launch of the domain, we create a sample of 2 organisations, 4 users and 4 sensors (equally distributed in both organisations).  
Those samples can be removed in **ch.heig.amt.project1.init.InitData.java**.
##Specification available
All available functionnalities are documented in our [API Reference](http://greendragon18.github.io/amt_project1). You can see below what we already have implemented:

Specification               | Details                                       | Done ?
--------------------------- | :-------------------------------------------- | :----:
            |  | NO
            |  | NO
            |  | NO
            |  | NO
            |  | NO
            |  | NO
            |  | NO
            |  | NO


##Testing application
The application is delivred with a summary client-side test application that can be used for fill the database very quickly (with some random values) or simulate a constant calling of the REST API from virtual sensors.  
This test application runs with Node.js and some npm plugins.
### Step 1: Setup Node.js
Download and install Node.js from http://nodejs.org.  
Then install the following node's plugins:

```
npm install async
npm install sleep
```

More informations can be found on their respective websites:  

- async : https://www.npmjs.org/package/async  
- sleep : https://www.npmjs.org/package/sleep

### Step 2: Use the script
The script works on two mode:  

- **fill**: will fill all your existing sensors with a given number of observations.  
- **simulate**: will randomly put an observation in an existing sensor.  

####Usage

```
node obsGenerator fill fill_amount  
-or-
node obsGenerator simulate simulation_cadency
```

####Default
**fill_amount** (observations / sensors) = 5000  
**simulation_cadency** (max time lapse) = [1-30] seconds