package es.prog2425.taskmanager.modelo

import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

/**
 * Representa una tarea en el sistema.
 *
 * @property estado El estado actual de la tarea.
 */
class Tarea private constructor(descripcion: String): Actividad(descripcion) {
    var estado: Estado = Estado.ABIERTA
    private val subtareas: MutableList<Tarea> = mutableListOf()
    private var usuarioAsignado: Usuario? = null
    private val historial = mutableListOf<Pair<String, String>>()

    init {
        require(descripcion.isNotEmpty()) { "La descripcion debe contener algo" }
    }

    companion object {
        /**
         * Crea una nueva instancia de Tarea.
         *
         * @param descripcion La descripción de la tarea.
         * @return Una nueva instancia de Tarea.
         */
        fun crearInstancia(descripcion: String) = Tarea(descripcion)
    }

    /**
     * Obtiene el historial de acciones realizadas en la tarea.
     *
     * @return Una lista de pares que representan la fecha y descripción de las acciones.
     */
    fun obtenerHistorial(): List<Pair<String, String>> = historial.toList()

    /**
     * Registra una acción en el historial de la tarea.
     *
     * @param descripcion La descripción de la acción.
     */
    fun registrarAccion(descripcion: String) {
        val fecha = LocalDateTime.now().format(DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm"))
        historial.add(Pair(fecha, descripcion))
    }

    /**
     * Cambia el estado de la tarea y registra la acción en el historial.
     *
     * @param nuevoEstado El nuevo estado de la tarea.
     * @throws IllegalStateException Si se intenta finalizar la tarea con subtareas abiertas.
     */
    fun cambiarEstadoConHistorial(nuevoEstado: Estado) {
        if (nuevoEstado == Estado.FINALIZADA && subtareas.any { it.estaAbierta() }) {
            throw IllegalStateException("No se puede finalizar la tarea con subtareas abiertas.")
        }
        estado = nuevoEstado
        registrarAccion("Estado cambiado a $nuevoEstado")
    }

    /**
     * Cierra la tarea y registra la acción en el historial.
     *
     * @throws IllegalStateException Si la tarea tiene subtareas abiertas.
     */
    fun cerrarConHistorial() {
        if (subtareas.any { it.estaAbierta() }) {
            throw IllegalStateException("No se puede cerrar la tarea porque tiene subtareas abiertas.")
        }
        estado = Estado.FINALIZADA
        registrarAccion("Tarea cerrada")
    }

    /**
     * Asigna un usuario a la tarea y registra la acción en el historial.
     *
     * @param usuario El usuario a asignar.
     */
    fun asignarUsuarioConHistorial(usuario: Usuario) {
        usuarioAsignado = usuario
        registrarAccion("Tarea asignada a ${usuario.nombre}")
    }

    /**
     * Agrega una subtarea a la tarea actual.
     *
     * @param subtarea La subtarea a agregar.
     */
    fun agregarSubtarea(subtarea: Tarea) {
        subtareas.add(subtarea)
    }

    /**
     * Obtiene la lista de subtareas asociadas a esta tarea.
     *
     * @return Una lista de subtareas.
     */
    fun obtenerSubtareas(): List<Tarea> = subtareas

    /**
     * Cambia el estado de la tarea.
     *
     * @param nuevoEstado El nuevo estado de la tarea.
     * @throws IllegalStateException Si se intenta finalizar la tarea con subtareas abiertas.
     */
    fun cambiarEstado(nuevoEstado: Estado) {
        if (nuevoEstado == Estado.FINALIZADA && subtareas.any { it.estado != Estado.FINALIZADA }) {
            throw IllegalStateException("No se puede marcar la tarea como FINALIZADA mientras tenga subtareas abiertas.")
        }
        estado = nuevoEstado

        if (estado == Estado.FINALIZADA && subtareas.all { it.estado == Estado.FINALIZADA }) {
            cerrar()
        }
    }

    /**
     * Cierra la tarea.
     */
    fun cerrar() {
        estado = Estado.FINALIZADA
    }

    /**
     * Obtiene los detalles de la tarea, incluyendo subtareas.
     *
     * @return Una cadena con los detalles de la tarea.
     */
    override fun obtenerDetalle(): String {
        val subtareasDetalles = if (subtareas.isEmpty()) {
            "No tiene subtareas."
        } else {
            subtareas.joinToString("\n") { it.obtenerDetalle() }
        }
        return super.obtenerDetalle() + " Estado: $estado\nSubtareas:\n$subtareasDetalles"
    }

    /**
     * Asigna un usuario a la tarea.
     *
     * @param usuario El usuario a asignar.
     */
    fun asignarUsuario(usuario: Usuario) {
        usuarioAsignado = usuario
    }

    /**
     * Obtiene el usuario asignado a la tarea.
     *
     * @return El usuario asignado, o null si no hay ninguno.
     */
    fun obtenerUsuarioAsignado(): Usuario? = usuarioAsignado

    /**
     * Verifica si la tarea está abierta.
     *
     * @return `true` si la tarea no está finalizada, `false` en caso contrario.
     */
    fun estaAbierta(): Boolean = estado != Estado.FINALIZADA
}