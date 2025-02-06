# OIDC Demo

** Please note this demo use Java 23, Please update build.gradle & Dockerfile with version you would like to test. **

This project is to demo OIDC setup with Google and Github

This project contains 2 endpoints

1. /authenticate - Login into Google/Github profile and get authorization token

2. /myprofile - Use the authorization token and fetcht the profile

use below command to run the application

```gradle
# Build
./gradlew clean build

# Run
./gradlew bootRun
```

## If you want to run the application using docker, Please run below commands

```docker
# Generate the artifacts
./gradlew clean build

# Docker build
docker build -t autonomous-app .

# Docker run
docker run -p 8080:8080 autonomous-app

# Authenticate
http://localhost:8080/authenticate

# Profile
http://localhost:8080/myprofile

# Logout
http://localhost:8080/logout
```

### Thank you
