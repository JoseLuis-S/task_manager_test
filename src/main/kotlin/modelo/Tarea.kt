package es.prog2425.taskmanager.modelo

import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

class Tarea private constructor(descripcion: String): Actividad(descripcion) {
    var estado: Estado = Estado.ABIERTA
    private val subtareas: MutableList<Tarea> = mutableListOf()
    private var usuarioAsignado: Usuario? = null
    private val historial = mutableListOf<Pair<String, String>>()

    init {
        require(descripcion.isNotEmpty()) { "La descripcion debe contener algo" }
    }

    companion object {
        fun crearInstancia(descripcion: String) = Tarea(descripcion)
    }

    fun obtenerHistorial(): List<Pair<String, String>> = historial.toList()

    fun registrarAccion(descripcion: String) {
        val fecha = LocalDateTime.now().format(DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm"))
        historial.add(Pair(fecha, descripcion))
    }

    fun cambiarEstadoConHistorial(nuevoEstado: Estado) {
        if (nuevoEstado == Estado.FINALIZADA && subtareas.any { it.estaAbierta() }) {
            throw IllegalStateException("No se puede finalizar la tarea con subtareas abiertas.")
        }
        estado = nuevoEstado
        registrarAccion("Estado cambiado a $nuevoEstado")
    }

    fun cerrarConHistorial() {
        if (subtareas.any { it.estaAbierta() }) {
            throw IllegalStateException("No se puede cerrar la tarea porque tiene subtareas abiertas.")
        }
        estado = Estado.FINALIZADA
        registrarAccion("Tarea cerrada")
    }

    fun asignarUsuarioConHistorial(usuario: Usuario) {
        usuarioAsignado = usuario
        registrarAccion("Tarea asignada a ${usuario.nombre}")
    }

    fun agregarSubtarea(subtarea: Tarea) {
        subtareas.add(subtarea)
    }

    fun obtenerSubtareas(): List<Tarea> = subtareas

    // Cambiar el estado de la tarea
    fun cambiarEstado(nuevoEstado: Estado) {
        if (nuevoEstado == Estado.FINALIZADA && subtareas.any { it.estado != Estado.FINALIZADA }) {
            throw IllegalStateException("No se puede marcar la tarea como FINALIZADA mientras tenga subtareas abiertas.")
        }
        estado = nuevoEstado

        if (estado == Estado.FINALIZADA && subtareas.all { it.estado == Estado.FINALIZADA }) {
            cerrar()
        }
    }

    // Cerrar la tarea
    fun cerrar() {
        estado = Estado.FINALIZADA
    }

    override fun obtenerDetalle(): String {
        val subtareasDetalles = if (subtareas.isEmpty()) {
            "No tiene subtareas."
        } else {
            subtareas.joinToString("\n") { it.obtenerDetalle() }
        }
        return super.obtenerDetalle() + " Estado: $estado\nSubtareas:\n$subtareasDetalles"
    }

    fun asignarUsuario(usuario: Usuario) {
        usuarioAsignado = usuario
    }

    fun obtenerUsuarioAsignado(): Usuario? = usuarioAsignado

    fun estaAbierta(): Boolean = estado != Estado.FINALIZADA
}