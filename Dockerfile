FROM openjdk

WORKDIR /app

COPY ./target/apibetwoc-0.0.1-SNAPSHOT.jar /app

CMD ["/bin/bash"]
