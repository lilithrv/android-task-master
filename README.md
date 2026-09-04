# TaskMaster — Gestor de Tareas para Android

Una pequeña empresa emergente encarga el desarrollo de una aplicación de gestión de tareas llamada TaskMaster, que debe ayudar a los usuarios a organizar sus tareas diarias de manera eficiente. 

---

## 1. Funcionalidades principales

- **Registro de usuario** con validación de campos (usuario, nombre, email con formato válido,
  contraseña) y aceptación de términos.
- **Inicio de sesión** con validación de credenciales (usuario registrado o usuario *demo*).
- **Pantalla de bienvenida** personalizada con el nombre del usuario.
- **Gestión de tareas:** crear tareas indicando **categoría** (Spinner) y **prioridad**
  (RatingBar de 1 a 5 estrellas).
- **Marcado de tareas completadas** mediante *CheckBox* en cada tarjeta.
- **Filtro por estado** con *RadioButton*: **Todas / Pendientes / Completadas**.
- **Barra de progreso** que muestra el porcentaje de tareas completadas sobre el total real.
- **Cierre de sesión**, que regresa al inicio de sesión limpiando la pila de navegación.
- **Usuario demo:** al iniciar sesión con el usuario de prueba se precargan tareas de ejemplo.

---

## 2. Componentes de interfaz utilizados (cobertura de la rúbrica)

La siguiente tabla evidencia el uso de cada elemento solicitado y **dónde** se implementa.

| Categoría | Componente | Dónde se utiliza |
|---|---|---|
| Layout | **ConstraintLayout** | Raíz de `activity_main`, `activity_register`, `activity_welcome`, `activity_tasks` |
| Layout | **LinearLayout** | Todas las pantallas e `item_tarea` |
| Layout | **TableLayout** | Formulario de `activity_register` |
| Contenedor | **ScrollView** | `activity_register` (formulario desplazable) |
| Elemento UI | **TextView** | Títulos y etiquetas en todas las pantallas |
| Elemento UI | **Button** | Login, registro, agregar tarea, cerrar sesión |
| Widget | **CheckBox** | Aceptar términos (registro) y marcar tarea "hecha" |
| Widget | **RadioButton** | Filtro *Todas / Pendientes / Completadas* (`activity_tasks`) |
| Widget | **ProgressBar** | Progreso de tareas (`activity_tasks`) y carga (`activity_welcome`) |
| Widget | **RatingBar** | Prioridad de la tarea (`activity_tasks` e `item_tarea`) |
| Widget | **ImageView** | Logo (`activity_main`, `activity_welcome`) e ícono (`item_tarea`) |
| Contenedor | **Spinner** | Selección de categoría (`activity_tasks`) |
| Contenedor | **RecyclerView** | Lista de tareas (`activity_tasks`) |
| Contenedor | **CardView** | Tarjeta de cada tarea (`item_tarea`) |

---

## 3. Arquitectura y estructura del proyecto

El código se organiza en **módulos reutilizables**: cada pantalla es una *Activity*, la lógica
de datos se separa en clases de modelo y repositorio, y la lista se gestiona con un *Adapter*
propio del patrón RecyclerView.

```
android-task/
└── app/src/main/
    ├── AndroidManifest.xml
    ├── java/com/example/taskmaster/
    │   ├── MainActivity.java          # Inicio de sesión (Login)
    │   ├── RegisterActivity.java      # Registro de usuario + validaciones
    │   ├── WelcomeActivity.java       # Pantalla de bienvenida
    │   ├── TasksActivity.java         # Gestión de tareas: filtro, progreso, logout
    │   ├── Tarea.java                 # Modelo de tarea (título, categoría, prioridad, hecha)
    │   ├── TareaAdapter.java          # Adapter del RecyclerView
    │   ├── Usuario.java               # Modelo de usuario
    │   ├── UsuarioRepositorio.java    # Repositorio de usuarios (en memoria, incluye demo)
    │   └── Validaciones.java          # Utilidades de validación reutilizables
    └── res/
        ├── layout/                    # activity_*.xml, item_tarea.xml, item_spinner.xml
        └── values/                    # strings.xml, arrays.xml, colors.xml, themes.xml
```

**Separación de responsabilidades:**

- **Vistas (UI):** los archivos `res/layout/` definen la interfaz; las *Activities* la controlan.
- **Modelo:** `Tarea` y `Usuario` representan los datos.
- **Datos:** `UsuarioRepositorio` centraliza el registro y la validación de usuarios.
- **Reutilización:** `Validaciones` agrupa las comprobaciones de formularios; `TareaAdapter`
  encapsula el renderizado de cada elemento de la lista.

---

## 4. Requisitos técnicos

- **Android Studio** (versión reciente con soporte para el *Gradle wrapper* incluido).
- **JDK 11** (`sourceCompatibility` / `targetCompatibility` = 11).
- **SDK de Android:** `minSdk 26` (Android 8.0 Oreo), `compileSdk` / `targetSdk 37`.
- **Lenguaje:** Java. **Paquete:** `com.example.taskmaster`.
- **Dependencias principales:** AppCompat, ConstraintLayout, Material Components y Activity;
  pruebas con JUnit y Espresso.

---

## 5. Instalación y ejecución

1. Clonar el repositorio o descomprimir el proyecto:
   ```bash
   git clone <enlace al repositorio>
   ```
2. Abrir la carpeta del proyecto (`android-task/`) en **Android Studio**.
3. Esperar la **sincronización de Gradle** (descarga de dependencias).
4. Ejecutar la app con **Run ▶** sobre un emulador o dispositivo físico (Android 8.0 o superior).

---

## 6. Uso y credenciales de prueba

- Desde la pantalla de **inicio de sesión** puede acceder con el **usuario demo** para probar la
  aplicación con tareas de ejemplo ya cargadas (Reunión, Estudiar Android, Pedir hora control
  médico), o bien **registrar** un usuario nuevo desde el botón *Registrarse*.
- Una vez dentro, en la pantalla de **Tareas** puede: agregar tareas, marcarlas como completadas,
  **filtrarlas** por estado con los *RadioButton*, observar el **progreso** y **cerrar sesión**.

> **Nota:** los usuarios y las tareas se almacenan **en memoria** durante la ejecución (no hay
> base de datos), por lo que se reinician al cerrar la aplicación. La persistencia se plantea
> como mejora futura.

---

## 7. Flujo de navegación

```
[Login] ──registro──▶ [Registro] ──▶ (vuelve al Login)
   │
   └── credenciales válidas ──▶ [Bienvenida] ──▶ [Tareas]
                                                    │
                                        Cerrar sesión ──▶ [Login]
```

El cierre de sesión regresa al *Login* limpiando la pila de actividades
(`FLAG_ACTIVITY_NEW_TASK | FLAG_ACTIVITY_CLEAR_TASK`), de modo que el botón *atrás* no reingresa
a la sesión anterior.

---


