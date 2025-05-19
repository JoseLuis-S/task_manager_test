package es.prog2425.taskmanager.modelo

import es.prog2425.taskmanager.utils.Utilidades

/**
 * Representa un evento en el sistema.
 *
 * @property fecha La fecha del evento.
 * @property ubicacion La ubicación del evento.
 */
class Evento private constructor(
    descripcion: String,
    val fecha: String,
    private val ubicacion: String
): Actividad(descripcion) {

    companion object {
        /**
         * Crea una nueva instancia de Evento.
         *
         * @param descripcion La descripción del evento.
         * @param fecha La fecha del evento en formato dd-MM-yyyy.
         * @param ubicacion La ubicación del evento.
         * @return Una nueva instancia de Evento.
         */
        fun crearInstancia(descripcion: String, fecha: String, ubicacion: String) = Evento(descripcion, fecha, ubicacion)
    }

    init {
        require(Utilidades().esFechaValida(fecha)) { "\nLa fecha de tener el siguiente formato (dd-MM-yyyy)\n" }
        require(ubicacion.isNotEmpty())
    }

    /**
     * Obtiene los detalles del evento.
     *
     * @return Una cadena con los detalles del evento.
     */
    override fun obtenerDetalle(): String = super.obtenerDetalle() + "Fecha: $fecha, Ubicación: $ubicacion"
}