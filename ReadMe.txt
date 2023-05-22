.github is directory  -> we add new directory below it called workflow -> then add new file called main.yml for adding info that github needs with versions of java
Install maven on device and add its path to user console to run code
Add plugin of SureFire https://maven.apache.org/surefire/maven-surefire-plugin/examples/testng.html for make parallel execution
Allure result added by default when adding allure dependency

Add dependencies of testNG, rest-assured, java-faker, Allure-testNg, jackson for serialization, deserialization

test/java/qacart/todo
base package contains class specs for Specifications of repeated code for all project and Specification of environment
apis package for api functions
data package contains text values of variables
models package for all setter, getter values of apis
steps package contains functions for generating random values, to call it at test cases
testcases package contain test cases fos each api
specs - models - apis - data - steps - testCases