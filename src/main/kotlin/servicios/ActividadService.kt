package es.prog2425.taskmanager.servicios

import es.prog2425.taskmanager.datos.ActividadRepository
import es.prog2425.taskmanager.datos.IActividadRepository
import es.prog2425.taskmanager.modelo.*

class ActividadService(private val repositorio: IActividadRepository) {

    fun crearEvento(descripcion: String, fecha: String, ubicacion: String) {
        val evento = Evento.crearInstancia(descripcion, fecha, ubicacion)
        repositorio.agregarEvento(evento)
    }

    fun crearTarea(descripcion: String, etiquetas: List<String>): Tarea {
        val tarea = Tarea.crearInstancia(descripcion)
        etiquetas.forEach { tarea.agregarEtiqueta(it) }
        repositorio.agregarTarea(tarea)
        return tarea
    }

    fun asociarSubtarea(tareaPrincipal: Tarea, subtarea: Tarea) {
        tareaPrincipal.agregarSubtarea(subtarea)
    }

    fun cambiarEstadoTarea(tarea: Tarea, nuevoEstado: Estado) {
        tarea.cambiarEstadoConHistorial(nuevoEstado)
    }

    fun listarActividades(): List<Actividad> = repositorio.obtenerActividades()
}