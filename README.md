This is a reference implementation for Workfront's webhooks plugin framework. 
All APIs are based on Workfront's webhooks document API spec.  
In order to mimic a real document management system, we implements a simple file exchange system to
achieve the goal.  A maven tomcat7 plugin is embedded in the project to serve the application.


The steps to run the application are as follows:

1) mvn clean package -Pwebhooks-app

This will create webhooks-app-1.0-SNAPSHOT-war-exec.jar,  war-exec.manifest and war-exec.properties files under target directory.

2) java -jar <webhooks-app-1.0-SNAPSHOT--war-exec>.jar [options]

options:

 -ajpPort <ajpPort>                     ajp port to use
 -clientAuth                            enable client authentication for
                                        https
 -D <arg>                               key=value
 -extractDirectory <extractDirectory>   path to extract war content,
                                        default value: .extract
 -h,--help                              help
 -httpPort <httpPort>                   http port to use
 -httpProtocol <httpProtocol>           http protocol to use: HTTP/1.1 or
                                        org.apache.coyote.http11.Http11Nio
                                        Protocol
 -httpsPort <httpsPort>                 https port to use
 -keyAlias <keyAlias>                   alias from keystore for ssl
 -loggerName <loggerName>               logger to use: slf4j to use slf4j
                                        bridge on top of jul
 -obfuscate <password>                  obfuscate the password and exit
 -resetExtract                          clean previous extract directory
 -serverXmlPath <serverXmlPath>         server.xml to use, optional
 -X,--debug                             debug

3) You can either test it with AtTask or with Postman.

Here are some samples by Postman.

// register a user

method: post
url: http://localhost:9966/webhooks-app/rest/accounts
message body: {"Id":"1", "name":"zac@coritsutest.local", "password":"test"}

This should give back the json response

{
"name": "zac@coritsutest.local",
"links": [
	{
		"rel": "self",
		"href": "http://localhost:9966/webhooks-app/rest/accounts/1"
	}
	]
}


// get a list of folders/documents under those published directories

method: get
url: http://localhost:9966/webhooks-app/rest/api/files
header: Content-Type   application/json
        Accept         application/json
        username       zac@coritsutest.local
        apiKey         123456

This will return a list of metadata for files/folders.



The webhooks-config.xml configuration file under WEB-INF directory can be used to configure the ApiKeys and published directories for document access.  The apply-authentication tag can be used to turn on/off the authentication which is based on a registered user and ApiKey.  More info is under Workfront web site.

