# Java Jenkins POC

Java 21 + Maven + JUnit 5 project with a console calculator, a web UI (WAR), a Jenkins pipeline and JaCoCo coverage.

## Build and test
```bash
mvn clean test       # run unit tests
mvn clean package    # builds target/java-jenkins-poc.war and target/java-jenkins-poc-cli.jar
```
Coverage report: `target/site/jacoco/index.html`

## Console version
```bash
java -jar target/java-jenkins-poc-cli.jar
```

## Web version
Open http://localhost:8080/java-jenkins-poc/ after deploying.

Quick local run without installing a server (optional):
```bash
mvn org.eclipse.jetty.ee10:jetty-ee10-maven-plugin:12.0.12:run
# then open http://localhost:8080/
```

API example: `GET /java-jenkins-poc/api/calculate?op=add&a=10&b=5` returns `{"result":15}`.
Operations: add, subtract, multiply, divide, evenodd.

### Deploy on Tomcat (10.1 or newer, Java 21)
Copy `target/java-jenkins-poc.war` into `<tomcat>/webapps/` and start Tomcat.

### Deploy on JBoss / WildFly (WildFly 27+ or JBoss EAP 8)
Copy the WAR into `<jboss>/standalone/deployments/`, or use the admin console.

Note: this uses the `jakarta.servlet` namespace. Tomcat 9 and older JBoss/WildFly (before 27) use `javax.servlet` and will not run this WAR as is.

## Jenkins setup
1. Install plugins: Pipeline, Git, JUnit.
2. Manage Jenkins > Tools: add a Maven install named `Maven3` and a JDK named `JDK21`.
3. New Item > Pipeline > "Pipeline script from SCM" > your GitHub repo URL.
4. Script Path: `Jenkinsfile`. The pipeline archives the WAR and CLI jar.

## Structure
```
pom.xml
Jenkinsfile
src/main/java/com/example/poc/{App,Calculator,CalculationHandler,CalculatorServlet}.java
src/main/webapp/index.html
src/test/java/com/example/poc/{CalculatorTest,AppTest,CalculationHandlerTest}.java
```
