REST API TESTING PRACTICE
==================
For those of you looking to gain some experience working with [REST Assured](http://rest-assured.io/).

What do I need?
---
A Java 8 JDK and Gradle. That's it.

What API is used for the exercises?
---
All API calls that are used in the examples and exercises have been mocked using [WireMock](http://wiremock.org/). The standalone version of WireMock is included in this project, so there's no need for additional setup.

Running the mock server
---
In order to get a response from the WireMock mock server, you'll need to start it before you run your tests. You can do so by running this command from the `src/test/resources` folder:
```
java -jar wiremock-standalone-2.15.0.jar --port 9876
```

In our execrcises, you have complete the test practice due to the comments in test files.

**Tips**
You can search the url which should be tested by IDE global search, it will be found in files of Resource folder.
