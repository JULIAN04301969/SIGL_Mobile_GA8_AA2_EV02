# Sección 1 – Configuración del Entorno y Creación del Proyecto Android

## 1.1 Instalación del OpenJDK 21 (Temurin)
Android Studio requiere una implementación del Java Development Kit (JDK) para compilar y ejecutar aplicaciones Android. Se utilizó la distribución Eclipse Temurin 21.0.10+7, versión de soporte a largo plazo (LTS) del OpenJDK, provista por la fundación Adoptium. La instalación se realizó desde el sitio oficial adoptium.net y, al concluir, se verificó su correcta configuración ejecutando los siguientes comandos en PowerShell:
PS C:\Users\JULIAN\PROYECTOSADSO\leads-auth-api> java -version
openjdk version "21.0.10" 2026-01-20 LTS
OpenJDK Runtime Environment Temurin-21.0.10+7 (build 21.0.10+7-LTS)
OpenJDK 64-Bit Server VM Temurin-21.0.10+7 (build 21.0.10+7-LTS, mixed mode, sharing)

PS C:\Users\JULIAN\PROYECTOSADSO\leads-auth-api> java --version
openjdk 21.0.10 2026-01-20 LTS
OpenJDK Runtime Environment Temurin-21.0.10+7 (build 21.0.10+7-LTS)
OpenJDK 64-Bit Server VM Temurin-21.0.10+7 (build 21.0.10+7-LTS, mixed mode, sharing)

text

La salida de ambos comandos confirmó que el entorno dispone de OpenJDK 21.0.10 LTS en su variante de 64 bits con modo mixto (JIT + AOT), lo que garantiza compatibilidad plena con Android Studio Panda 4 y con el toolchain de Gradle utilizado en el proyecto SIGL_Mobile.

## 1.2 Instalación de Android Studio Panda 4 // 2025.3.4
Android Studio es el IDE oficial para el desarrollo de aplicaciones Android, construido sobre la plataforma IntelliJ IDEA. La versión instalada es Panda 4 // 2025.3.4, publicada en 2025. La instalación se realizó descargando el instalador desde developer.android.com/studio y se completó aceptando los acuerdos de licencia del Android SDK durante el primer inicio del IDE.

Los componentes principales instalados y configurados fueron:

| Componente          | Descripción |
|---------------------|-------------|
| Android SDK         | Conjunto de APIs, herramientas y bibliotecas para compilar apps Android. |
| Android SDK Build-Tools | Herramientas de compilación (aapt2, dx, zipalign) para generar el APK. |
| Android Emulator    | Motor de emulación que permite probar la app sin dispositivo físico. |
| AVD Manager         | Gestor de dispositivos virtuales Android para configurar emuladores. |
| Kotlin Plugin       | Soporte nativo de Kotlin integrado en el IDE para compilación y depuración. |
| Gradle (integrado)  | Sistema de automatización de construcción que gestiona dependencias y build. |

*Tabla 1. Componentes de Android Studio Panda 4 instalados.*

## 1.3 Creación del Proyecto SIGL_Mobile
Para iniciar el proyecto se seleccionó la opción New Project en la pantalla de bienvenida de Android Studio, eligiendo la plantilla Empty Views Activity, que genera la estructura mínima funcional de una aplicación Android con soporte para layouts XML tradicionales. Los parámetros de configuración establecidos se detallan en la tabla siguiente:

| Parámetro                | Valor configurado |
|--------------------------|-------------------|
| Nombre del proyecto      | SIGL_Mobile |
| Package name             | com.sigl.gestionleads |
| Directorio de guardado   | C:\Users\JULIAN\PROYECTOSADSO\SIGL_Mobile |
| Lenguaje                 | Kotlin |
| SDK mínimo               | API 26 – Android 8.0 (Oreo) |
| SDK objetivo (Target)    | API 37 – Android 13 |
| Build system             | Gradle (Kotlin DSL – build.gradle.kts) |

*Tabla 2. Configuración del proyecto SIGL_Mobile.*

La elección de API 26 como SDK mínimo garantiza cobertura sobre más del 90 por ciento de los dispositivos Android activos en el mercado, mientras que el SDK objetivo en API 37 asegura acceso a las APIs y optimizaciones recientes del sistema operativo.

## 1.4 Estructura Detallada del Proyecto Android
Una vez creado el proyecto, Android Studio generó automáticamente la estructura de directorios y archivos base que sigue la arquitectura estándar de aplicaciones Android. A continuación, se describe cada componente principal:
SIGL_Mobile/
├── app/
│ ├── manifests/
│ │ └── AndroidManifest.xml
│ ├── kotlin+java/
│ │ └── com.sigl.gestionleads/
│ │ ├── LoginActivity.kt
│ │ ├── DashboardActivity.kt
│ │ ├── RegistroActivity.kt
│ │ ├── api/
│ │ │ ├── Models.kt
│ │ │ ├── ApiService.kt
│ │ │ └── RetrofitClient.kt
│ │ └── data/
│ │ └── DatabaseHelper.kt
│ └── res/
│ ├── layout/
│ │ ├── activity_login.xml
│ │ ├── activity_dashboard.xml
│ │ ├── activity_registro.xml
│ │ └── nav_header.xml
│ ├── menu/
│ │ └── drawer_menu.xml
│ ├── drawable/
│ └── values/
│ ├── strings.xml
│ ├── colors.xml
│ └── themes.xml
├── build.gradle.kts (Project)
└── app/
└── build.gradle.kts (Module :app)

text

### AndroidManifest.xml
Archivo de configuración central de la aplicación. Declara todos los componentes de la app (Activities, Services, BroadcastReceivers), los permisos requeridos (INTERNET para comunicación con el backend del SIGL), las características de hardware necesarias y el Activity de arranque. Sin este archivo la app no puede ser instalada ni ejecutada en el dispositivo.

### kotlin+java / com.sigl.gestionleads
Directorio que contiene el código fuente Kotlin de la aplicación. Alberga las clases Activity (LoginActivity, DashboardActivity, RegistroActivity), el paquete api con los modelos de datos y servicios REST, y el paquete data con el helper de base de datos SQLite.

### res/layout
Directorio de archivos XML que definen la interfaz gráfica de usuario de cada pantalla. Incluye activity_login.xml, activity_dashboard.xml, activity_registro.xml y nav_header.xml para la cabecera del menú lateral.

### res/values
Contiene archivos de recursos reutilizables. strings.xml centraliza los textos, colors.xml define la paleta cromática corporativa del SIGL (verde primario #00695c), y themes.xml establece el tema Material Design 3 de la aplicación.

### build.gradle.kts
Archivo de configuración de Gradle que especifica las dependencias del proyecto, incluyendo Material Components, Retrofit, Gson y las bibliotecas AndroidX necesarias para el funcionamiento de la aplicación.

## 1.5 Ejecución de la Aplicación en el Emulador Android
Para verificar que el entorno de desarrollo está correctamente configurado y que el proyecto SIGL_Mobile compila sin errores, se procedió a ejecutar la aplicación en un dispositivo virtual Android (AVD - Android Virtual Device). Este proceso valida la instalación del SDK, las herramientas de compilación y el funcionamiento del emulador integrado en Android Studio.

**Creación del Android Virtual Device**  
Desde Android Studio se accedió al Device Manager mediante la opción Tools - Device Manager - Create Device. Se seleccionó el dispositivo Pixel 6a con las siguientes características:

- Modelo: Pixel 6a
- Resolución: 1080 x 2400 (densidad 420 dpi)
- System Image: API 37 (Android 13.0)
- Arquitectura: x86_64 (compatible con aceleración de hardware)

La imagen del sistema se descargó automáticamente desde los repositorios de Google y se configuró el AVD con 2 GB de RAM y 6 GB de almacenamiento interno virtual.

**Compilación y despliegue**  
Una vez configurado el emulador, se ejecutó la aplicación mediante el botón Run ubicado en la barra de herramientas superior de Android Studio. El IDE ejecutó automáticamente las siguientes tareas de Gradle: compilación del código Kotlin, fusión de recursos, empaquetado del APK, instalación en el emulador y lanzamiento de la actividad principal.

El proceso de construcción finalizó exitosamente, tal como se observa en el mensaje de la consola: `Install successfully finished`. La aplicación se lanzó correctamente mostrando la interfaz generada por defecto, confirmando que el entorno de desarrollo está completamente funcional.
