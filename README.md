# up42

- Make sure there is not application running on 8080
- Execute the following command on terminal
    ``java -jar image-0.0.1-SNAPSHOT.jar``
- Test the endpoints using curl e.g.
    ``curl -H "content-type:application/json" "http://localhost:8080/features"``

- To build the application and produce artifacts use mvn clean install