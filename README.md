# webhooks-app

A Reference implementation for Workfront's webhooks plugin framework. All APIs are based on Workfront's webhooks document API spec.
In order to mimic a real document management system, we implements a simple file exchange system to
achieve the goal.  A maven tomcat7 plugin is embedded in the project to serve the application.

## Usage

The steps to run the application are as follows:

#### 1) mvn clean package -Pwebhooks-app

This will create webhooks-app-1.0-SNAPSHOT-war-exec.jar,  war-exec.manifest and war-exec.properties files under target directory.

####2) java -jar <webhooks-app-1.0-SNAPSHOT--war-exec>.jar [options]

options:

Option | Description
------- | -----------
 -ajpPort \<ajpPort>                    | ajp port to use
 -clientAuth                            | enable client authentication for  https                                      
 -D <arg>                               | key=value
 -extractDirectory \<extractDirectory>  | path to extract war content, default value: .extract                                     
 -h,--help                              | help
 -httpPort \<httpPort>                  | http port to use
 -httpProtocol \<httpProtocol>          | http protocol to use: HTTP/1.1 or org.apache.coyote.http11.Http11Nio Protocol
 -httpsPort \<httpsPort>                | https port to use
 -keyAlias \<keyAlias>                  | alias from keystore for ssl
 -loggerName \<loggerName>              | logger to use: slf4j to use slf4j bridge on top of jul
 -obfuscate \<password>                 | obfuscate the password and exit
 -resetExtract                          | clean previous extract directory
 -serverXmlPath \<serverXmlPath>        | server.xml to use, optional
 -X,--debug                             | debug

##Test
To verify the app works, AtTask or Postman can be used to test the build.

####Some examples for using Postman 

#####register a user

method: post  
url: http://localhost:9966/webhooks-app/rest/accounts  
message body: {"Id":"1", "name":"zac@coritsutest.local", "password":"test"}

This should give back the json response

{  
&nbsp;&nbsp;&nbsp;&nbsp;"name": "zac@coritsutest.local",  
&nbsp;&nbsp;&nbsp;&nbsp;"links": [  
&nbsp;&nbsp;&nbsp;&nbsp;	{  
&nbsp;&nbsp;&nbsp;&nbsp;		"rel": "self",  
&nbsp;&nbsp;&nbsp;&nbsp;		"href": "http://localhost:9966/webhooks-app/rest/accounts/1"  
&nbsp;&nbsp;&nbsp;&nbsp;	}  
&nbsp;&nbsp;&nbsp;&nbsp;	]  
}  


#####get a list of folders/documents under those published directories

method: get  
url: http://localhost:9966/webhooks-app/rest/api/files  
header:  
Content-Type   application/json  
Accept         application/json  
username       zac@coritsutest.local  
apiKey         123456

This will return a list of metadata for files/folders.

## Configuration File

The webhooks-config.xml configuration file under WEB-INF directory can be used to configure the ApiKeys and published directories for document access.  The apply-authentication tag can be used to turn on/off the authentication which is based on a registered user and ApiKey.  More info is under Workfront web site.


## License

Copyright (c) 2015 Workfront

Licensed under the Apache License, Version 2.0.
See the top-level file `LICENSE` and
(http://www.apache.org/licenses/LICENSE-2.0).


[license-image]: http://img.shields.io/badge/license-APv2-blue.svg?style=flat
[license-url]: LICENSE
