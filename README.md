# 🟦 Catálogo de Camisetas - Entrega Parte Dos

[![Java](https://img.shields.io/badge/Java-17-007396?style=flat-square&logo=java)](https://www.oracle.com/java/)
[![Spring Boot](https://img.shields.io/badge/Spring%20Boot-4.1.1-6DB33F?style=flat-square&logo=spring-boot)](https://spring.io/projects/spring-boot)
[![MongoDB](https://img.shields.io/badge/MongoDB-Latest-13AA52?style=flat-square&logo=mongodb)](https://www.mongodb.com/)
[![Maven](https://img.shields.io/badge/Maven-3.8+-C71A36?style=flat-square&logo=apache-maven)](https://maven.apache.org/)

## 📋 Descripción

**Catálogo de Camisetas** es una API REST desarrollada con **Spring Boot 4.1.1** que permite consultar, gestionar información y solicitar compra/venta relacionada con **camisetas de fútbol**. 

La aplicación utiliza **MongoDB** como repositorio de datos para almacenar información sobre camisetas, competiciones, equipos y países. Incluye funcionalidades de **concurrencia y threading** para procesamiento asincrónico y está preparada para ser desplegada en **WebSphere** como archivo WAR.

### Características Principales ✨

- ✅ API RESTful con operaciones CRUD completas
- ✅ Integración con **MongoDB** como base de datos principal
- ✅ Procesamiento **asincrónico** y **concurrente** con threading
- ✅ Validaciones complejas de datos
- ✅ Manejo robusto de errores
- ✅ Logging detallado de operaciones
- ✅ Empaquetado como WAR para WebSphere
- ✅ Endpoints de alta completa con resolución de dependencias
- ✅ Tests unitarios e integración

---

## 📦 Requisitos Previos

Antes de ejecutar el proyecto, asegúrate de tener instalado:

- **Java 17** o superior
- **Maven 3.8+**
- **MongoDB** (versión 4.4+) o acceso a MongoDB Atlas
- **Git** (opcional, para clonar el repositorio)

### Verificar Instalación

```bash
# Verificar Java
java -version

# Verificar Maven
mvn -version
```

---

## 🚀 Instalación y Configuración

### 1. Clonar el Repositorio

```bash
git clone <tu-repositorio>
cd CatalogoDeCamisetasV2
```

### 2. Configurar MongoDB

#### Opción A: MongoDB Local
```bash
# Instalar MongoDB en tu máquina
# https://docs.mongodb.com/manual/installation/

# Iniciar el servicio MongoDB
mongod
```

#### Opción B: MongoDB Atlas (Nube)
La aplicación ya está configurada para conectarse a MongoDB Atlas. Edita el archivo `application.properties`:

```properties
spring.mongodb.uri=mongodb+srv://usuario:contraseña@cluster.mongodb.net
spring.mongodb.database=tienda_camisetas
```

### 3. Configurar Variables de Entorno

```bash
# Crear archivo .env o configurar variables:
MONGODB_URI=mongodb+srv://usuario:contraseña@cluster.mongodb.net
MONGODB_DATABASE=tienda_camisetas
```

### 4. Instalar Dependencias

```bash
mvn clean install
```

---

## 🏃 Ejecución

### Ejecutar Localmente

```bash
# Opción 1: Con Maven
mvn spring-boot:run

# Opción 2: Compilar y ejecutar JAR
mvn clean package
java -jar target/CatalogoDeCamisetasV2-0.0.1-SNAPSHOT.war

# Opción 3: Con IDE (IntelliJ IDEA)
# Click derecho en CatalogoDeCamisetasV2Application.java > Run
```

La aplicación estará disponible en: `http://localhost:8080`

### Ejecutar en Docker

```bash
# Construir imagen Docker
docker build -t catalogo-camisetas:latest .

# Ejecutar contenedor
docker run -p 8080:8080 \
  -e MONGODB_URI=mongodb+srv://usuario:contraseña@cluster.mongodb.net \
  -e MONGODB_DATABASE=tienda_camisetas \
  catalogo-camisetas:latest
```

---

## 📚 Estructura del Proyecto

```
src/
├── main/
│   ├── java/org/example/catalogodecamisetasv2/
│   │   ├── CatalogoDeCamisetasV2Application.java    # Clase principal
│   │   ├── config/                                  # Configuraciones (Async, MongoDB)
│   │   ├── controller/                              # Controllers REST
│   │   │   ├── CamisetaController.java
│   │   │   ├── CompeticionController.java
│   │   │   ├── EquipoController.java
│   │   │   ├── PaisController.java
│   │   │   └── WorkFlowController.java
│   │   ├── dto/                                     # Data Transfer Objects
│   │   ├── exception/                               # Manejo de excepciones
│   │   ├── mapper/                                  # Mapeos entidades-DTOs
│   │   ├── model/                                   # Entidades MongoDB
│   │   ├── repository/                              # Repositorios MongoDB
│   │   ├── service/                                 # Lógica de negocio
│   │   └── validation/                              # Validaciones personalizadas
│   └── resources/
│       ├── application.properties                   # Configuración
│       ├── logback-spring.xml                       # Configuración de logging
│       └── data/                                    # Datos iniciales
└── test/
    └── java/org/example/catalogodecamisetasv2/      # Tests unitarios e integración
        ├── controller/
        └── service/
```

### Entidades Principales

1. **Camiseta** - Prenda de vestiario con información de equipo, competición, etc.
2. **Competición** - Liga, torneo o campeonato
3. **Equipo** - Equipo de fútbol
4. **País** - País de origen del equipo

---

## 🔌 API Endpoints

### Camisetas
```
GET    /api/camisetas                    # Obtener todas las camisetas
GET    /api/camisetas/{id}               # Obtener camiseta por ID
POST   /api/camisetas                    # Crear nueva camiseta
PUT    /api/camisetas/{id}               # Actualizar camiseta
DELETE /api/camisetas/{id}               # Eliminar camiseta
POST   /api/camisetas/altaCompleta       # Alta completa (camiseta + entidades asociadas)
POST   /api/camisetas/altaCompletaAsync  # Alta completa asincrónica
```

### Competiciones
```
GET    /api/competiciones                # Obtener todas las competiciones
GET    /api/competiciones/{id}           # Obtener competición por ID
POST   /api/competiciones                # Crear nueva competición
PUT    /api/competiciones/{id}           # Actualizar competición
DELETE /api/competiciones/{id}           # Eliminar competición
```

### Equipos
```
GET    /api/equipos                      # Obtener todos los equipos
GET    /api/equipos/{id}                 # Obtener equipo por ID
POST   /api/equipos                      # Crear nuevo equipo
PUT    /api/equipos/{id}                 # Actualizar equipo
DELETE /api/equipos/{id}                 # Eliminar equipo
```

### Países
```
GET    /api/paises                       # Obtener todos los países
GET    /api/paises/{id}                  # Obtener país por ID
POST   /api/paises                       # Crear nuevo país
PUT    /api/paises/{id}                  # Actualizar país
DELETE /api/paises/{id}                  # Eliminar país
```

> 📌 Para más detalles sobre los endpoints, ver colecciones de Postman en la carpeta `/POSTMAN`

---

## 📬 Colecciones Postman

Se incluyen dos colecciones de Postman para probar la API:

### 1. **Catálogo de Camisetas - API Entrega** 📤
- Endpoints ordenados para ejecutar automáticamente
- Flujo completo de operaciones
- Incluye ejemplos de alta completa y asincrónica

### 2. **Catálogo de Camisetas - Pruebas de Error** ❌
- Validación de manejo de errores
- Casos edge
- Códigos de respuesta esperados

**Importar en Postman:**
1. Abrir Postman
2. Collections → Import
3. Seleccionar archivo JSON de `/POSTMAN`
4. Ejecutar requests

---

## 🧪 Testing

### Ejecutar Todos los Tests

```bash
# Con Maven
mvn test

# Con Maven (verbose)
mvn test -X
```

### Tests por Módulo

```bash
# Solo tests de controladores
mvn test -Dtest=*Controller*

# Solo tests de servicios
mvn test -Dtest=*Service*
```

### Tests Integrados

La aplicación incluye los siguientes tests de integración y unitarios:

#### 1. **CamisetaControllerWebMvcTest** 🎽
- Tests del controlador REST de camisetas
- Validación de endpoints usando MockMvc
- Pruebas de operaciones CRUD
- Validación de respuestas HTTP

**Ejecutar:**
```bash
mvn test -Dtest=CamisetaControllerWebMvcTest
```

#### 2. **PaisRepositoryImplTest** 🌍
- Tests del repositorio de países
- Validación de operaciones con MongoDB
- Pruebas de consultas personalizadas
- Verificación de persistencia de datos

**Ejecutar:**
```bash
mvn test -Dtest=PaisRepositoryImplTest
```

### Cobertura de Tests

```bash
# Generar reporte de cobertura
mvn clean test jacoco:report

# Ver reporte en target/site/jacoco/index.html
```

> 📌 Los tests principales se enfoca en la entidad **Camiseta** como la clase más importante

---

## ⚠️ Advertencias Importantes

### 1. 🐳 Docker - Limitaciones de Entorno

**❌ No es posible instalar Docker y probar los `Dockerfile` y `docker-compose.yml` en este entorno.**

- Los archivos Docker están incluidos en el proyecto como referencia
- Dockerfile: Configuración para empaquetar la aplicación en contenedor
- docker-compose.yml: Orquestación de servicios (aplicación + MongoDB)
- **Recomendación:** Verificar Docker en un entorno que lo soporte

**Para entornos con Docker disponible:**
```bash
docker-compose up -d
```

### 2. 📊 SonarQube - Problemas de Compatibilidad para Java 17

**⚠️ Se instaló localmente SonarQube para Java 17, pero existen limitaciones importantes:**

#### Problemas Identificados:
- ❌ **No existe versión gratuita de SonarQube con soporte completo para Java 17**
- ❌ La licencia gratuita no incluye análisis de código
- ❌ Error recurrente: *"Analyses suspended. Please set a valid license for the Edition you installed."*
- ❌ **Según documentación oficial, SonarQube Community Edition ha descontinuado el soporte para Java 17 en versiones recientes**

#### Comando que genera el error:
```bash
mvn clean verify sonar:sonar \
  "-Dsonar.projectKey=Trabajo-final-bbva" \
  "-Dsonar.host.url=http://localhost:9000" \
  "-Dsonar.login=sqp_0374d4db39920d1c991355ef953cb143daaeed16"
```

**Error:**
```
Analyses suspended. Please set a valid license for the Edition you installed.
```

#### Soluciones Alternativas:
1. **SonarCloud** (Nube - Gratuito para repositorios públicos)
   - Acceso: https://sonarcloud.io
   - Mejor soporte para Java 17
   - No requiere instalación local

2. **Otras herramientas de análisis gratuitas:**
   - SpotBugs
   - Checkstyle
   - PMD
   - Eclipse Formatter

3. **Licencia Comercial:**
   - Contactar con SonarSource para licencia empresarial

---

## ⚙️ Configuración

### Archivo `application.properties`

```properties
# Datos de aplicación
spring.application.name=CatalogoDeCamisetasV2

# Configuración MongoDB
spring.mongodb.uri=mongodb+srv://usuario:contraseña@cluster.mongodb.net
spring.mongodb.database=tienda_camisetas

# Logging
logging.level.root=DEBUG
logging.level.org.springframework.web=DEBUG
logging.level.org.example.catalogodecamisetasv2=DEBUG
logging.file.name=logs/application.log
logging.file.max-size=10MB
logging.file.max-history=30
```

### Configuración de Threading/Concurrencia

La aplicación incluye configuración de `AsyncExecutorConfig` para:
- Procesamiento asincrónico de operaciones
- Ejecución en múltiples threads
- Endpoint `altaCompletaAsync` para alta paralela de entidades

---

## 🔍 Características Destacadas

### 1. Alta Completa (`/api/camisetas/altaCompleta`)
Permite crear una camiseta con sus entidades asociadas (país, competición, equipo) en **un solo request**:

```json
{
  "camiseta": { "nombre": "...", "numero": "..." },
  "pais": { "nombre": "España" },
  "competicion": { "nombre": "La Liga" },
  "equipo": { "nombre": "Real Madrid" }
}
```

### 2. Alta Completa Asincrónica (`/api/camisetas/altaCompletaAsync`)
Procesa cada alta en **diferentes threads** para mayor performance:
- Threading controlado
- Resolución automática de dependencias
- Perfecto para demostraciones de concurrencia

### 3. Validaciones Robustas
- Validación en tiempo de compilación con Bean Validation
- Validaciones personalizadas en servicios
- Manejo integral de errores

### 4. Logging Detallado
- Logs en archivo y consola
- Configuración por componente
- Rotación automática de logs

---

## 📊 Migración desde V1

Esta es la segunda entrega del proyecto con los siguientes cambios:

| Aspecto | V1 | V2 |
|--------|----|----|
| **Base de Datos** | JSON (archivos) | MongoDB |
| **Concurrencia** | Básica | Threading avanzado |
| **Endpoints** | CRUD simple | CRUD + Alta completa |
| **Manejo de Errores** | Básico | Robusto y completo |

---

## 📝 Notas de Entrega

### Cambios Principales en V2
- ✅ Migración de JSON a MongoDB
- ✅ Correcciones de entregas anteriores
- ✅ Soporte Docker (Dockerfile incluido)
- ✅ Endpoints `altaCompleta` y `altaCompletaAsync`
- ✅ Manejo avanzado de concurrencia y threading
- ✅ Tests unitarios e integración

### Archivos de Entrega
- `pom.xml` - Configuración Maven
- `Dockerfile` - Definición de imagen Docker
- `POSTMAN/` - Colecciones de pruebas
- `logs/` - Archivos de log
- `src/` - Código fuente completo



