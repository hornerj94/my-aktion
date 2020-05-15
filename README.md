# Build
mvn clean package && docker build -t de.dpunkt/my-aktion .

# RUN

docker rm -f my-aktion || true && docker run -d -p 8080:8080 -p 4848:4848 --name my-aktion de.dpunkt/my-aktion 