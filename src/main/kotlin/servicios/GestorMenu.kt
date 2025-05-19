package es.prog2425.taskmanager.servicios

import es.prog2425.taskmanager.presentacion.Interfaz

class GestorMenu(
    private val salida: Interfaz,
    private val gestor: GestorActividades
) {
    fun mostrarMenuPrincipal() {
        var salir = false
        do {
            try {
                salida.mostrarMenu()
                when (salida.leerNum()) {
                    -1 -> salida.mostrar("\nOpcion no valida.")
                    1 -> gestor.crearEvento()
                    2 -> gestor.crearTarea()
                    3 -> gestor.listarActividades()
                    4 -> gestor.asociarSubtarea()
                    5 -> gestor.cambiarEstadoTarea()
                    6 -> gestor.cerrarTarea()
                    7 -> gestor.crearUsuario()
                    8 -> gestor.asignarTareaAUsuario()
                    9 -> gestor.consultarTareasUsuario()
                    10 -> gestor.filtrarActividades()
                    11 -> gestor.consultarHistorialTarea()
                    12 -> salir = true
                }
            } catch (e: IllegalStateException) {
                salida.mostrar("$e")
            }
        } while (!salir)
    }
}
