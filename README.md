# Image Analysis API

## Descripción

Esta es una API de backend construida con Spring Boot que analiza imágenes utilizando un servicio de IA de terceros (Imagga). La API expone un único endpoint que acepta un archivo de imagen, lo envía al servicio de análisis y devuelve una lista de etiquetas (tags) con sus respectivos niveles de confianza.

## Tecnologías Utilizadas

- **Java 17**: Versión del JDK utilizada.
- **Spring Boot 3.2.5**: Framework principal para la construcción de la aplicación.
- **Maven**: Herramienta para la gestión de dependencias y construcción del proyecto.
- **Spring Web**: Para la creación del endpoint REST.
- **Spring WebFlux**: Para realizar llamadas reactivas a la API externa con `WebClient`.
- **Imagga API**: Servicio de IA utilizado para el análisis de imágenes.

## Instalación y Ejecución Local

Sigue estos pasos para instalar las dependencias y ejecutar el proyecto en tu máquina local.

### Prerrequisitos

- **Java 17** o superior instalado.
- **Maven** instalado.
- Una **API Key** y **API Secret** de [Imagga](https://imagga.com/). Puedes obtenerlas creando una cuenta en su sitio web.

### Pasos

1.  **Clona el repositorio** (o descarga el código fuente).

2.  **Configura las variables de entorno**:

    Esta aplicación requiere la API Key y el API Secret de Imagga para funcionar. La forma recomendada de configurarlas es a través de variables de entorno.

    En sistemas UNIX (Linux/macOS):
    ```bash
    export IMAGGA_API_KEY="tu_api_key"
    export IMAGGA_API_SECRET="tu_api_secret"
    ```

    En Windows (PowerShell):
    ```powershell
    $env:IMAGGA_API_KEY="tu_api_key"
    $env:IMAGGA_API_SECRET="tu_api_secret"
    ```

    Alternativamente, para desarrollo local, puedes editar el archivo `src/main/resources/application.properties` y añadir tus credenciales, pero **no subas este archivo a un repositorio público con tus credenciales**.

3.  **Compila y ejecuta el proyecto**:

    Navega a la raíz del proyecto y utiliza Maven para ejecutar la aplicación:

    ```bash
    mvn spring-boot:run
    ```

4.  **La API estará disponible** en `http://localhost:8090`.

## Cómo Usar la API

Puedes probar la API enviando una petición `POST` al siguiente endpoint con un archivo de imagen.

- **URL**: `http://localhost:8090/api/analyze`
- **Método**: `POST`
- **Body**: `form-data`
  - **Key**: `file`
  - **Value**: (selecciona tu archivo de imagen)

### Ejemplo con cURL

```bash
curl -X POST -F "file=@/ruta/a/tu/imagen.jpg" http://localhost:8090/api/analyze
```

### Respuesta Exitosa (Ejemplo)

```json
{
  "tags": [
    {
      "label": "dog",
      "confidence": 98.3
    },
    {
      "label": "golden retriever",
      "confidence": 95.1
    }
  ]
}
```
