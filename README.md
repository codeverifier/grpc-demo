# gRPC Demo Project

This simple project has both client and server to help with testing gRPC.
Built on Spring Boot using netty drivers.

Includes implementations for:
- unary
- client/server streaming
- bi-directional streaming

Exposes the following endpoints for testing on client side:
- `/car/manufacturer/{country}`
- `/car/{manufacturer_id}`
- `/manufacturer/{id}`
- `/car`
- `/cars`
- `/manufacturers`

## Build

### Maven
To compile all projects execute `./mvnw package`

### Docker
To build Docker images of both client and server,

```
docker build -t <repo>/client-service:<version> -t <repo>/client-service:latest -f client-service/Dockerfile client-service
docker build -t <repo>/backend-service:<version> -t <repo>/backend-service:latest -f backend-service/Dockerfile backend-service
```