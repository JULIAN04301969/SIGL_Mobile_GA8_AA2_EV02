# SIGL_Mobile_GA8_AA2_EV02

Aplicación móvil nativa para Android del Sistema Integral de Gestión de Leads (SIGL), 
desarrollada como evidencia de la competencia GA8-220501096-AA2-EV02.

## 🚀 Funcionalidades implementadas (Sección 2)
- Pantalla de inicio de sesión con validación de credenciales.
- Autenticación contra API REST (Node.js + JWT) con fallback a base de datos SQLite local.
- Dashboard con menú lateral (Navigation Drawer) y opciones de navegación.
- Registro de nuevos usuarios (online y offline).
- Cierre de sesión con limpieza de pila de actividades.
- Pruebas de funcionamiento sin conexión (modo offline).

## 🛠️ Tecnologías utilizadas
- Lenguaje: Kotlin
- UI: Jetpack Compose (tema MaterialComponents)
- Base de datos local: SQLite (SQLiteOpenHelper)
- Cliente HTTP: Retrofit + Gson
- Arquitectura: paquetes por responsabilidad (api, data, ui)

## 🌿 Estructura de ramas (colaboración simulada)
- `master` → rama principal con la integración final.
- `feature/backend-auth` → **jocampo-backend**: integración con API y base de datos.
- `feature/frontend-leads` → **mgarcia-frontend**: preparación del módulo de leads.
- `feature/frontend-auth` → **crodriguez-ui**: ajustes visuales en pantalla de login.

## 📁 Estructura del proyecto
app/src/main/java/com/sigl/gestionleads/
├── api/             # Modelos y cliente Retrofit
├── data/            # Helper de SQLite
├── ui/theme/        # Tema de la aplicación
├── LoginActivity.kt
├── DashboardActivity.kt
└── RegistroActivity.kt

app/src/main/res/
├── layout/          # Vistas XML
├── menu/            # Menú lateral
├── values/          # Colores, strings, temas
└── ...

## ▶️ Instrucciones para ejecutar
1. Clonar el repositorio.
2. Abrir en Android Studio.
3. Sincronizar dependencias (Gradle).
4. Ejecutar en emulador o dispositivo físico.
5. Para probar con API: tener el backend Node.js corriendo en `localhost:3000`.

## 👥 Autor
Julián Ocampo – Aprendiz SENA  
Ficha: 3118307 – Análisis y Desarrollo de Software
