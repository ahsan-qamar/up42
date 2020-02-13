# up42

- Make sure there is no application running on 8080
- Navigate to the ``up42`` folder
- Build the application using mvn clean install
- Navigate to the ``target`` folder
- Execute the following command on terminal
    ``java -jar image-0.0.1-SNAPSHOT.jar``
- Test the endpoints using curl e.g.
    ``curl -H "content-type:application/json" "http://localhost:8080/features"``
