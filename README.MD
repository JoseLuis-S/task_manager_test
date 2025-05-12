# ActividadServiceTest

## Tabla de Casos de Prueba

| Método                     | Caso de prueba                                       | Estado inicial del mock                          | Acción                                             | Resultado esperado                                  |
|----------------------------|------------------------------------------------------|--------------------------------------------------|----------------------------------------------------|----------------------------------------------------|
| `crearEvento(...)`          | Datos válidos                                        | `mockRepositorio` no tiene interacciones previas | Llamar `crearEvento(descripcion, fecha, ubicacion)` | Evento creado y agregado al repositorio             |
| `crearEvento(...)`          | Fecha no válida                                      | `mockRepositorio` no tiene interacciones previas | Llamar `crearEvento(descripcion, fecha, ubicacion)` | Lanza `IllegalArgumentException`                    |
| `crearTarea(...)`           | Descripción válida                                   | `mockRepositorio` no tiene interacciones previas | Llamar `crearTarea(descripcion)`                   | Tarea creada y agregada al repositorio              |
| `crearTarea(...)`           | Descripción vacía                                    | `mockRepositorio` no tiene interacciones previas | Llamar `crearTarea("")`                            | Lanza `IllegalArgumentException`                    |
| `asociarSubtarea(...)`      | Subtarea asociada correctamente                      | `tareaPrincipal` y `subtarea` son mocks válidos  | Llamar `asociarSubtarea(tareaPrincipal, subtarea)` | Subtarea agregada a la tarea principal              |
| `asociarSubtarea(...)`      | Subtarea nula                                        | `tareaPrincipal` es un mock válido               | Llamar `asociarSubtarea(tareaPrincipal, null)`     | Lanza `NullPointerException`                        |
| `cambiarEstadoTarea(...)`   | Cambiar el estado de la tarea correctamente          | `tarea` es un mock válido                        | Llamar `cambiarEstadoTarea(tarea, nuevoEstado)`    | Estado de la tarea cambiado correctamente           |
| `cambiarEstadoTarea(...)`   | Tarea nula                                           | `tarea` es nula                                  | Llamar `cambiarEstadoTarea(null, nuevoEstado)`     | Lanza `NullPointerException`                        |
| `listarActividades()`       | Repositorio con datos                                | `mockRepositorio` devuelve una lista de actividades | Llamar `listarActividades()`                      | Retorna una lista con actividades                   |
| `listarActividades()`       | Repositorio vacío                                    | `mockRepositorio` devuelve una lista vacía      | Llamar `listarActividades()`                      | Retorna una lista vacía                             |

## Detalles de los casos de prueba

### `crearEvento(descripcion: String, fecha: String, ubicacion: String)`

- **Caso 1: Datos válidos**
    - **Estado inicial del mock**: `mockRepositorio` no tiene interacciones previas.
    - **Acción**: Llamada al método `crearEvento` con datos válidos.
    - **Resultado esperado**: Se crea un evento y se agrega al repositorio correctamente. Se verifica que `agregarEvento` fue invocado.

- **Caso 2: Fecha no válida**
    - **Estado inicial del mock**: `mockRepositorio` no tiene interacciones previas.
    - **Acción**: Llamada al método `crearEvento` con una fecha no válida.
    - **Resultado esperado**: Se lanza `IllegalArgumentException` porque la fecha no es válida.

### `crearTarea(descripcion: String)`

- **Caso 1: Descripción válida**
    - **Estado inicial del mock**: `mockRepositorio` no tiene interacciones previas.
    - **Acción**: Llamada al método `crearTarea` con una descripción válida.
    - **Resultado esperado**: Se crea una tarea y se agrega al repositorio correctamente. Se verifica que `agregarTarea` fue invocado.

- **Caso 2: Descripción vacía**
    - **Estado inicial del mock**: `mockRepositorio` no tiene interacciones previas.
    - **Acción**: Llamada al método `crearTarea` con una descripción vacía.
    - **Resultado esperado**: Se lanza `IllegalArgumentException` debido a que la descripción es vacía.

### `asociarSubtarea(tareaPrincipal: Tarea, subtarea: Tarea)`

- **Caso 1: Subtarea asociada correctamente**
    - **Estado inicial del mock**: `tareaPrincipal` y `subtarea` son mocks válidos.
    - **Acción**: Llamada al método `asociarSubtarea`.
    - **Resultado esperado**: La subtarea se agrega correctamente a la tarea principal. Se verifica que `agregarSubtarea` fue invocado.

- **Caso 2: Subtarea nula**
    - **Estado inicial del mock**: `tareaPrincipal` es un mock válido y `subtarea` es nula.
    - **Acción**: Llamada al método `asociarSubtarea` con una subtarea nula.
    - **Resultado esperado**: Se lanza `NullPointerException` debido a que no se puede agregar una subtarea nula.

### `cambiarEstadoTarea(tarea: Tarea, nuevoEstado: Estado)`

- **Caso 1: Cambiar estado correctamente**
    - **Estado inicial del mock**: `tarea` es un mock válido.
    - **Acción**: Llamada al método `cambiarEstadoTarea` con una tarea y un nuevo estado válidos.
    - **Resultado esperado**: Se cambia el estado de la tarea correctamente. Se verifica que `cambiarEstadoConHistorial` fue invocado.

- **Caso 2: Tarea nula**
    - **Estado inicial del mock**: `tarea` es nula.
    - **Acción**: Llamada al método `cambiarEstadoTarea` con una tarea nula.
    - **Resultado esperado**: Se lanza `NullPointerException` porque no se puede cambiar el estado de una tarea nula.

### `listarActividades()`

- **Caso 1: Repositorio con actividades**
    - **Estado inicial del mock**: `mockRepositorio` devuelve una lista de actividades.
    - **Acción**: Llamada al método `listarActividades`.
    - **Resultado esperado**: Se retorna una lista con las actividades del repositorio.

- **Caso 2: Repositorio vacío**
    - **Estado inicial del mock**: `mockRepositorio` devuelve una lista vacía.
    - **Acción**: Llamada al método `listarActividades`.
    - **Resultado esperado**: Se retorna una lista vacía.