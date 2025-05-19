package es.prog2425.taskmanager.servicios

import es.prog2425.taskmanager.datos.ActividadRepository
import es.prog2425.taskmanager.modelo.Actividad
import es.prog2425.taskmanager.presentacion.Consola
import es.prog2425.taskmanager.presentacion.Interfaz
import es.prog2425.taskmanager.utils.Utilidades
import es.prog2425.taskmanager.modelo.Tarea
import es.prog2425.taskmanager.modelo.Estado
import es.prog2425.taskmanager.modelo.Evento

class GestorActividades {

    private val salida: Interfaz = Consola()
    private val servicio = ActividadService(ActividadRepository())
    private val servicioUsuario: IUsuarioService = UsuarioService()

    fun crearEvento() {
        val descripcion = pedirDescripcion()
        val fecha = pedirFecha()
        val ubicacion = pedirUbicacion()
        servicio.crearEvento(descripcion, fecha, ubicacion)
        salida.mostrar("\nEvento creado exitosamente.")
    }

    fun crearTarea() {
        val descripcion = pedirDescripcion()
        servicio.crearTarea(descripcion)
        salida.mostrar("\nTarea creada con éxito y etiquetas asignadas.")
    }

    fun listarActividades() {
        val actividades = servicio.listarActividades()
        if (actividades.isEmpty()) {
            salida.mostrar("\nNo hay actividades registradas.")
        } else {
            salida.mostrar("\nListado de actividades:")
            actividades.forEach { salida.mostrar(it.obtenerDetalle()) }
        }
    }

    fun asociarSubtarea() {
        val tareaPrincipal = seleccionarTareaConMensaje("\nSelecciona la tarea principal:")
        salida.mostrar("\nDescribe la subtarea a asociar:")
        val descripcionSubtarea = pedirDescripcion()
        val subtarea = Tarea.crearInstancia(descripcionSubtarea)
        servicio.asociarSubtarea(tareaPrincipal, subtarea)
        salida.mostrar("\nSubtarea asociada a la tarea principal.")
    }

    fun cambiarEstadoTarea() {
        val tarea = seleccionarTareaConMensaje("\nSelecciona la tarea cuyo estado deseas cambiar:")
        val nuevoEstado = solicitarEstadoParaTarea() ?: return

        try {
            servicio.cambiarEstadoTarea(tarea, nuevoEstado)
            salida.mostrar("\nEstado de la tarea cambiado exitosamente a ${nuevoEstado.name}.")
        } catch (e: IllegalStateException) {
            salida.mostrar("\nError: ${e.message}")
        }
    }

    fun cerrarTarea() {
        val tarea = seleccionarTareaConMensaje("\nSelecciona la tarea a cerrar:")
        try {
            tarea.cerrar()
            salida.mostrar("\nTarea cerrada exitosamente.")
        } catch (e: IllegalStateException) {
            salida.mostrar("\nNo se puede cerrar la tarea porque tiene subtareas abiertas.")
        }
    }

    fun crearUsuario() {
        salida.mostrar("\nIntroduce el nombre del nuevo usuario: ")
        val nombre = salida.leerString()
        servicioUsuario.crearUsuario(nombre)
        salida.mostrar("\nUsuario '$nombre' creado con éxito.")
    }

    fun asignarTareaAUsuario() {
        val tarea = seleccionarTareaConMensaje("\nSelecciona la tarea a asignar: ")
        salida.mostrar("\nIntroduce el nombre del usuario al que asignar la tarea: ")
        val nombreUsuario = salida.leerString()
        val usuario = servicioUsuario.obtenerUsuarioPorNombre(nombreUsuario)

        if (usuario != null) {
            servicioUsuario.asignarTareaAUsuario(usuario, tarea)
            salida.mostrar("\nTarea asignada correctamente a $nombreUsuario.")
        } else {
            salida.mostrar("\nNo se encontró un usuario con ese nombre.")
        }
    }

    fun consultarTareasUsuario() {
        salida.mostrar("\nIntroduce el nombre del usuario para consultar sus tareas: ")
        val nombreUsuario = salida.leerString()
        val usuario = servicioUsuario.obtenerUsuarioPorNombre(nombreUsuario)

        if (usuario != null) {
            val tareas = servicioUsuario.obtenerTareasPorUsuario(usuario)
            if (tareas.isEmpty()) {
                salida.mostrar("\nEl usuario no tiene tareas asignadas.")
            } else {
                salida.mostrar("\nTareas asignadas a $nombreUsuario:")
                tareas.forEach { salida.mostrar(it.obtenerDetalle()) }
            }
        } else {
            salida.mostrar("\nNo se encontró un usuario con ese nombre.")
        }
    }

    fun consultarHistorialTarea() {
        val tarea = seleccionarTareaConMensaje("\nSelecciona la tarea para ver su historial:")
        val historial = tarea.obtenerHistorial()
        if (historial.isEmpty()) {
            salida.mostrar("\nLa tarea no tiene historial.")
        } else {
            salida.mostrar("\nHistorial de la tarea:")
            historial.forEach { (fecha, accion) ->
                salida.mostrar("[$fecha] $accion")
            }
        }
    }

    fun filtrarActividades() {
        salida.mostrar("\nFiltrar actividades por:")
        salida.mostrar("1. Tipo (Tarea o Evento)")
        salida.mostrar("2. Estado (ABIERTA, EN_PROGRESO, FINALIZADA)")
        salida.mostrar("3. Etiquetas")
        salida.mostrar("4. Usuario")
        when (salida.leerNum()) {
            1 -> filtrarPorTipo()
            2 -> filtrarPorEstado()
            3 -> filtrarPorEtiquetas()
            4 -> filtrarPorUsuario()
            else -> salida.mostrar("\nOpción no válida.")
        }
    }

    // --- Métodos auxiliares extraídos ---

    private fun seleccionarTareaConMensaje(mensaje: String): Tarea {
        salida.mostrar(mensaje)
        return obtenerTarea()
    }

    private fun solicitarEstadoParaTarea(): Estado? {
        salida.mostrar("\nElige el nuevo estado para la tarea:")
        salida.mostrar("1. ABIERTA")
        salida.mostrar("2. EN PROGRESO")
        salida.mostrar("3. FINALIZADA")

        return when (salida.leerNum()) {
            1 -> Estado.ABIERTA
            2 -> Estado.EN_PROGRESO
            3 -> Estado.FINALIZADA
            else -> {
                salida.mostrar("\nOpción no válida.")
                null
            }
        }
    }

    private fun obtenerTarea(): Tarea {
        val tareas = servicio.listarActividades().filterIsInstance<Tarea>()
        if (tareas.isEmpty()) throw IllegalStateException("No hay tareas disponibles para seleccionar.")

        salida.mostrar("\nSelecciona una tarea de la lista:")
        tareas.forEachIndexed { index, tarea ->
            salida.mostrar("${index + 1}. ${tarea.obtenerDetalle()}")
        }

        var seleccion: Tarea? = null
        while (seleccion == null) {
            salida.mostrar("\nIntroduce el número de la tarea:")
            val input = salida.leerNum()
            if (input in 1..tareas.size) {
                seleccion = tareas[input - 1]
            } else {
                salida.mostrar("\nOpción inválida. Intenta de nuevo.")
            }
        }

        return seleccion
    }

    private fun pedirDescripcion(): String {
        while (true) {
            salida.mostrar("\nIntroduce la descripcion")
            salida.mostrarInput("> ")
            val descripcion = salida.leerString()
            if (descripcion.isNotBlank()) return descripcion
            salida.mostrar("\nLa descripcion debe contener algo.")
        }
    }

    private fun pedirFecha(): String {
        while (true) {
            salida.mostrar("\nIntroduce la fecha (dd-MM-yyyy)")
            salida.mostrarInput("> ")
            val fecha = salida.leerString()
            if (Utilidades().esFechaValida(fecha)) return fecha
            salida.mostrar("\nFecha invalida.")
        }
    }

    private fun pedirUbicacion(): String {
        while (true) {
            salida.mostrar("\nIntroduce la ubicacion")
            salida.mostrarInput("> ")
            val ubicacion = salida.leerString()
            if (ubicacion.isNotBlank()) return ubicacion
            salida.mostrar("\nLa ubicacion debe contener algo.")
        }
    }

    private fun filtrarPorTipo() {
        salida.mostrar("\nSelecciona el tipo de actividad a filtrar:")
        salida.mostrar("1. Tarea")
        salida.mostrar("2. Evento")
        val tipoSeleccionado = salida.leerNum()

        val filtradas = when (tipoSeleccionado) {
            1 -> servicio.listarActividades().filterIsInstance<Tarea>()
            2 -> servicio.listarActividades().filterIsInstance<Evento>()
            else -> {
                salida.mostrar("\nOpción no válida.")
                return
            }
        }

        mostrarActividades(filtradas)
    }

    private fun filtrarPorEstado() {
        salida.mostrar("\nSelecciona el estado:")
        salida.mostrar("1. ABIERTA")
        salida.mostrar("2. EN PROGRESO")
        salida.mostrar("3. FINALIZADA")
        val estadoSeleccionado = salida.leerNum()

        val estado = when (estadoSeleccionado) {
            1 -> Estado.ABIERTA
            2 -> Estado.EN_PROGRESO
            3 -> Estado.FINALIZADA
            else -> {
                salida.mostrar("\nOpción no válida.")
                return
            }
        }

        val filtradas = servicio.listarActividades()
            .filterIsInstance<Tarea>()
            .filter { it.estado == estado }

        mostrarActividades(filtradas)
    }

    private fun filtrarPorEtiquetas() {
        salida.mostrar("\nIntroduce la etiqueta a filtrar:")
        val etiqueta = salida.leerString()
        val filtradas = servicio.listarActividades()
            .filter { it.obtenerEtiquetas().contains(etiqueta) }

        mostrarActividades(filtradas)
    }

    private fun filtrarPorUsuario() {
        salida.mostrar("\nIntroduce el nombre del usuario:")
        val nombre = salida.leerString()
        val usuario = servicioUsuario.obtenerUsuarioPorNombre(nombre) ?: run {
            salida.mostrar("\nNo se encontró un usuario con ese nombre.")
            return
        }

        val filtradas = servicio.listarActividades()
            .filterIsInstance<Tarea>()
            .filter { it.obtenerUsuarioAsignado() == usuario }

        mostrarActividades(filtradas)
    }

    private fun mostrarActividades(actividades: List<Actividad>) {
        if (actividades.isEmpty()) {
            salida.mostrar("\nNo se encontraron actividades con los filtros seleccionados.")
        } else {
            salida.mostrar("\nActividades filtradas:")
            actividades.forEach { salida.mostrar(it.obtenerDetalle()) }
        }
    }
}
