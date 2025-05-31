# Documentación del Proyecto eCommerce Microservice Backend

## Índice

- [Introducción](#introducción)
- [Arquitectura General](#arquitectura-general)
- [Servicios y Componentes](#servicios-y-componentes)
- [Pruebas](#pruebas)
  - [Pruebas Unitarias](#pruebas-unitarias)
  - [Pruebas de Integración](#pruebas-de-integración)
  - [Pruebas End-to-End (E2E)](#pruebas-end-to-end-e2e)
  - [Pruebas de Carga y Estrés (Locust)](#pruebas-de-carga-y-estrés-locust)
- [Contenerización y Orquestación](#contenerización-y-orquestación)
  - [Dockerfiles](#dockerfiles)
  - [Docker Compose](#docker-compose)
  - [Kubernetes (K8s)](#kubernetes-k8s)
- [Pipeline CI/CD (Jenkins)](#pipeline-cicd-jenkins)
- [Notas y Limitaciones](#notas-y-limitaciones)

---

## Introducción

Este proyecto implementa un sistema de microservicios para eCommerce utilizando **Spring Boot**, **Spring Cloud**, **Docker**, **Kubernetes**, y una arquitectura orientada a pruebas (TDD). Incluye pruebas unitarias, de integración, E2E y de carga, así como scripts y configuraciones para despliegue automatizado.

---

## Arquitectura General

- **Microservicios**: Cada dominio funcional (usuarios, productos, órdenes, pagos, favoritos, etc.) es un microservicio independiente.
- **Service Discovery**: Eureka.
- **Configuración Centralizada**: Spring Cloud Config.
- **Gateway**: API Gateway para entrada unificada.
- **Bases de datos**: H2 y MySQL.
- **Observabilidad**: Zipkin para trazabilidad.
- **Orquestación**: Docker Compose y Kubernetes.

---

## Servicios y Componentes

- **user-service**: Gestión de usuarios y autenticación.
- **product-service**: Catálogo de productos.
- **order-service**: Gestión de órdenes.
- **payment-service**: Procesamiento de pagos.
- **favourite-service**: Favoritos de usuario.
- **shipping-service**: Envíos (comentado en algunos scripts por recursos).
- **cloud-config**: Configuración centralizada.
- **service-discovery**: Eureka.
- **api-gateway**: Entrada unificada.
- **proxy-client**: Cliente de ejemplo.
- **locust**: Pruebas de carga.

---

## Pruebas

### Pruebas Unitarias

> **Nota:** Se incluye una imagen mostrando la correcta ejecución de las pruebas de carga y estrés en la carpeta `CapturaDePantalla TESTS`.

#### Servicios con pruebas unitarias implementadas:

- **user-service**
  - Archivo: [`UserServiceImplTest.java`](user-service/src/test/java/com/selimhorri/app/unit/service/UserServiceImplTest.java)
  - **Cobertura**:
    - Prueba de obtención de usuarios por ID y username.
    - Prueba de manejo de usuarios inexistentes (excepciones).
    - Prueba de listado de usuarios.
    - Prueba de lógica de negocio en métodos del servicio.
- **product-service**
  - Archivo: [`ProductServiceImplTest.java`](product-service/src/test/java/com/selimhorri/app/unit/service/ProductServiceImplTest.java)
  - **Cobertura**:
    - Prueba de obtención de productos por ID.
    - Prueba de guardado de productos.
    - Prueba de listado de productos.
  - Archivo: [`CategoryServiceImplTest.java`](product-service/src/test/java/com/selimhorri/app/unit/service/CategoryServiceImplTest.java)
    - Prueba de obtención de categorías por ID.
    - Prueba de manejo de categorías inexistentes.
- **payment-service**, **order-service**, **favourite-service**, **shipping-service**, **cloud-config**, **service-discovery**, **api-gateway**, **proxy-client**:
  - Tienen pruebas mínimas de contexto de aplicación (SpringBootTest vacío) para asegurar que el contexto arranca correctamente.

**Ejemplo de ejecución:**
```sh
mvn test -pl user-service
```

---

### Pruebas de Integración

> **Nota:** Se incluye una imagen mostrando la correcta ejecución de las pruebas de carga y estrés en la carpeta `CapturaDePantalla TESTS`.

#### Servicios con pruebas de integración implementadas:

- **user-service**
  - Archivo: [`UserServiceIntegrationTest.java`](user-service/src/test/java/com/selimhorri/app/integration/UserServiceIntegrationTest.java)
  - **Cobertura**:
    - Prueba de creación de usuario vía API REST.
    - Prueba de obtención de usuario por ID.
    - Prueba de listado de usuarios.
    - Uso de base de datos embebida H2 y perfil `test`.
- **product-service**
  - Archivo: [`ProductControllerIntegrationTest.java`](product-service/src/test/java/com/selimhorri/app/integration/ProductControllerIntegrationTest.java)
  - **Cobertura**:
    - Prueba de creación, obtención, actualización y eliminación de productos vía API REST.
    - Prueba de obtención de todos los productos.
    - Validación de respuestas HTTP y persistencia.
  - Archivo: [`ProductServiceIntegrationTest.java`](product-service/src/test/java/com/selimhorri/app/integration/ProductServiceIntegrationTest.java)
    - Prueba directa de la capa de servicio: guardar, buscar, actualizar y eliminar productos.

**Ejemplo de ejecución:**
```sh
mvn verify -pl product-service
```

---

### Pruebas End-to-End (E2E)

> **Nota:** Se incluye una imagen mostrando la correcta ejecución de las pruebas de carga y estrés en la carpeta `CapturaDePantalla TESTS`.

#### Servicios cubiertos por pruebas E2E:

- **user-service**
  - Archivo: [`UserServiceE2ETest.java`](e2e-tests/src/test/java/com/e2e/tests/user/UserServiceE2ETest.java)
  - **Cobertura**:
    - Prueba de creación de usuario real usando TestContainers y REST.
    - Validación de integración completa entre servicios y dependencias reales.
- **product-service**
  - Archivo: [`ProductServiceE2ETest.java`](e2e-tests/src/test/java/com/e2e/tests/product/ProductServiceE2ETest.java)
  - **Cobertura**:
    - Prueba de obtención de categorías y productos usando el entorno real.
- **order-service**
  - Archivo: [`OrderServiceE2ETest.java`](e2e-tests/src/test/java/com/e2e/tests/order/OrderServiceE2ETest.java)
  - **Cobertura**:
    - Prueba de obtención de órdenes por ID.
- **payment-service**
  - Archivo: [`PaymentServiceE2ETest.java`](e2e-tests/src/test/java/com/e2e/tests/payment/PaymentServiceE2ETest.java)
  - **Cobertura**:
    - Prueba de creación de pagos asociados a órdenes.
- **favourite-service**
  - Archivo: [`FavouriteServiceE2ETest.java`](e2e-tests/src/test/java/com/e2e/tests/favourite/FavouriteServiceE2ETest.java)
  - **Cobertura**:
    - Prueba de obtención de favoritos de usuario.

**Tecnología:**
Se utiliza el módulo [`e2e-tests`](e2e-tests/) con **TestContainers** para levantar los servicios reales en Docker y probar flujos completos, simulando un entorno de producción.

**Ejemplo de ejecución:**
```sh
mvn verify -pl e2e-tests
```

---

### Pruebas de Carga y Estrés (Locust)

> **Nota:** Se incluye una imagen mostrando la correcta ejecución de las pruebas de carga y estrés en la carpeta `CapturaDePantalla TESTS`.

- Scripts en: `locust/test/`
- Ejemplo: `locustfile.py` para order-service y payment-service.
- Dockerfile para entorno Locust: `locust/Dockerfile`
- **Cobertura**:
  - Simulación de usuarios concurrentes realizando operaciones sobre los endpoints principales de los servicios.
  - Generación de reportes CSV y resultados históricos.
- **Nota:** Las pruebas para algunos servicios (como favourite-service) están comentadas en la pipeline por limitaciones de memoria local.

**Ejemplo de ejecución manual:**
```sh
docker run --rm --network ecommerce-test \
  -v "%CD%\\locust:/mnt" \
  -v "%CD%\\locust-results:/app" \
  kevinloachamin/locust:dev \
  -f /mnt/test/order-service/locustfile.py \
  --host http://order-service-container:8300 \
  --headless -u 10 -r 1 -t 1m \
  --csv order-service-stress --csv-full-history
```

---

## Contenerización y Orquestación

### Dockerfiles

Cada microservicio tiene su propio Dockerfile, por ejemplo:
```dockerfile
# product-service/Dockerfile
FROM openjdk:11
RUN mkdir -p /home/app
WORKDIR /home/app
COPY target/product-service-v0.1.0.jar product-service.jar
EXPOSE 8500
ENTRYPOINT ["java", "-jar", "product-service.jar"]
```

### Docker Compose

Archivo principal: `compose.yml`

Define todos los servicios, redes y variables de entorno.
Ejemplo de uso:
```sh
docker-compose -f compose.yml up
```

### Kubernetes (K8s)

Manifiestos en la carpeta `k8s/`
Ejemplo de despliegue para shipping-service: `k8s/shipping-service/deployment.yaml`
Despliegue:
```sh
kubectl apply -f k8s/shipping-service/deployment.yaml -n ecommerce
```

---

## Pipeline CI/CD (Jenkins)

- Archivo principal: [`Jenkinsfile`](Jenkinsfile)
- Stages principales:
    - Build & Package
    - Unit, Integration y E2E Tests
    - Start containers for testing (PowerShell y Docker)
    - Load y Stress Tests con Locust
    - Deploy a Kubernetes (solo en master)
    - Generación automática de Release Notes

- **Nota importante:**
  En la pipeline existen etapas y comandos comentados (por ejemplo, pruebas de carga para favourite-service y shipping-service, y despliegue de algunos servicios) debido a limitaciones de recursos locales de la máquina actual. Esto permite que la pipeline sea ejecutable en entornos con menos memoria, pero deja preparado el flujo para entornos de CI/CD más robustos.

- Ejemplo de stage para pruebas de estrés:
```groovy
stage('Run Stress Tests with Locust') {
    when { branch 'master' }
    steps {
        script {
            bat '''
            echo 🔥 Levantando Locust para prueba de estrés...
            docker run --rm --network ecommerce-test ^
            -v "%CD%\\locust:/mnt" ^
            -v "%CD%\\locust-results:/app" ^
            kevinloachamin/locust:%IMAGE_TAG% ^
            -f /mnt/test/order-service/locustfile.py ^
            --host http://order-service-container:8300 ^
            --headless -u 10 -r 1 -t 1m ^
            --csv order-service-stress --csv-full-history
            '''
        }
    }
}
```

---

## Notas y Limitaciones

- **Recursos Locales:**
  Algunas pruebas y despliegues están comentados en la pipeline y scripts debido a la falta de memoria RAM o CPU en el entorno local. En un entorno de CI/CD con más recursos, se recomienda descomentar estas secciones para lograr una cobertura total.
- **Extensibilidad:**
  La estructura de pruebas y despliegue está preparada para escalar y cubrir todos los microservicios, solo es necesario ajustar los recursos y descomentar las secciones correspondientes.
- **Documentación adicional:**
  Para detalles sobre configuración centralizada y buenas prácticas de K8s, revisar [`k8s/README.md`](k8s/README.md).

---
