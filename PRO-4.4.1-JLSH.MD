# Actividad: Documentacion de Clases con KDoc y Dokka en Kotlin

## Descripcion
En este proyecto Kotlin, se debe documentar el codigo fuente de las clases utilizando comentarios KDoc y generar la documentacion en formato HTML u otro formato con Dokka. Ademas, se debe integrar esta generacion en el flujo de trabajo del IDE.

## Objetivos

1. Comentar el codigo fuente de las clases con la sintaxis KDoc (`/** ... */`).
2. Generar la documentacion completa del proyecto usando Dokka.
3. Integrar la generacion de documentacion en el IDE y/o en la linea de comandos.
4. Mostrar capturas de pantalla del codigo comentado y del resultado en HTML.

---

### 1. Comentarios KDoc

#### Clases comentadas:

- **Evento**
- **Tarea**
- **Usuario**

---

### 2. Generacion con Dokka

Se configuro el plugin Dokka en el archivo `build.gradle.kts`:

```kotlin
plugins {
    kotlin("jvm") version "2.0.10"
    id("org.jetbrains.dokka") version "1.8.10"
}

tasks.dokkaHtml.configure {
    outputDirectory.set(project.layout.buildDirectory.dir("docs").get().asFile)
}
```

---

### 3. Capturas y Proceso

#### Documentacion generada por Dokka
![](assets/image1.png)

### Clases documentadas con Kdoc

- Evento:

https://github.com/JoseLuis-S/task_manager_test/blob/5a14ef380d106dbe5120fcd27889c8d7e206c077/src/main/kotlin/modelo/Evento.kt#L1-L40

- Tarea:

https://github.com/JoseLuis-S/task_manager_test/blob/5a14ef380d106dbe5120fcd27889c8d7e206c077/src/main/kotlin/modelo/Tarea.kt#L1-L161

- Usuario: 

https://github.com/JoseLuis-S/task_manager_test/blob/5a14ef380d106dbe5120fcd27889c8d7e206c077/src/main/kotlin/modelo/Usuario.kt#L1-L29

---

### 4. Documentacion del Proceso

#### Paso 1: Añadir Comentarios KDoc al Codigo
Se eligieron tres clases principales del proyecto para documentar:

- **Usuario.kt**
- **Producto.kt**
- **Pedido.kt**

En cada una se añadieron bloques de comentarios KDoc en las clases, atributos y metodos. Ejemplo:

```kotlin
/**
 * Representa un usuario del sistema.
 *
 * @property id Identificador unico.
 * @property nombre Nombre completo del usuario.
 */
class Usuario(val id: Int, val nombre: String) {

    /**
     * Devuelve un saludo personalizado para el usuario.
     *
     * @param mensaje Texto del saludo.
     * @return Saludo completo.
     */
    fun saludar(mensaje: String): String {
        return "$mensaje, $nombre!"
    }
}
```

#### Paso 2: Configurar Dokka en `build.gradle.kts`
Se configuro el plugin Dokka agregando lo siguiente en `build.gradle.kts`:

```kotlin
plugins {
    kotlin("jvm") version "1.8.0"
    id("org.jetbrains.dokka") version "1.8.10"
}

tasks.dokkaHtml.configure {
    outputDirectory.set(project.layout.buildDirectory.dir("docs").get().asFile)
}
```

**Explicacion:**
- Se usa la forma recomendada con `project.layout.buildDirectory` para evitar usar el obsoleto `buildDir`.
- La documentacion se genera en la ruta: `build/docs`.

#### Paso 3: Ejecutar la Tarea de Dokka

**Opcion 1: Desde la terminal**
```bash
./gradlew dokkaHtml
```

**Opcion 2: Desde IntelliJ IDEA**
1. Abrir la pestaña Gradle en el lateral derecho.
2. Navegar a `Tasks > documentation > dokkaHtml`.
3. Hacer doble clic para ejecutar la tarea.

**Resultado:** Se genera la documentacion HTML en la ruta:
```bash
<proyecto>/build/docs/index.html
```

#### Capturas:

![](assets/image1.png)

![](assets/image2.png)