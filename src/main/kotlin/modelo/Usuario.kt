package es.prog2425.taskmanager.modelo

/**
 * Representa un usuario en el sistema.
 *
 * @property nombre El nombre del usuario.
 */
class Usuario(val nombre: String) {
    /**
     * Lista de tareas asignadas al usuario.
     */
    val tareasAsignadas: MutableList<Tarea> = mutableListOf()

    /**
     * Asigna una tarea a este usuario.
     *
     * @param tarea La tarea a asignar.
     */
    fun asignarTarea(tarea: Tarea) {
        tareasAsignadas.add(tarea)
    }

    /**
     * Obtiene la lista de tareas asignadas al usuario.
     *
     * @return Una lista de tareas asignadas.
     */
    fun obtenerTareasAsignadas(): List<Tarea> = tareasAsignadas
}