package es.prog2425.taskmanager.presentacion

import es.prog2425.taskmanager.modelo.Actividad
import es.prog2425.taskmanager.modelo.Evento
import es.prog2425.taskmanager.modelo.Tarea

class Consola : Interfaz {
    private val separator = "=============================="

    override fun mostrarMenu() {
        println(separator)
        println("Menu principal:")
        println("1. Crear evento")
        println("2. Crear tarea")
        println("3. Listar actividades")
        println("4. Asociar subtarea a una tarea principal")
        println("5. Cambiar estado de tarea")
        println("6. Cerrar tarea")
        println("7. Crear usuario")
        println("8. Asignar tarea a usuario")
        println("9. Consultar tareas de un usuario")
        println("10. Filtrar tareas")
        println("11. Consultar historial de tareas")
        println("12. Salir")
        println(separator)
        print("> ")
    }

    override fun leerString(): String {
        return readlnOrNull() ?: ""
    }

    override fun leerNum(): Int {
        return readlnOrNull()?.toIntOrNull() ?: -1
    }

    override fun mostrar(x: Any) {
        println(x)
    }

    override fun mostrarActividades(x: List<Actividad>) {
        val eventos: MutableList<Evento> = mutableListOf()
        val tareas: MutableList<Tarea> = mutableListOf()
        println(separator)
        println("Listado de actividades:")
        println(separator)
        for (actividad in x) {
            if (actividad is Evento) {
                eventos.add(actividad)
            } else if (actividad is Tarea) {
                tareas.add(actividad)
            }
        }
        println("Eventos:")
        for (evento in eventos) {
            println(evento.obtenerDetalle())
        }
        println("Tareas:")
        for (tarea in tareas) {
            println(tarea.obtenerDetalle())
        }
    }

    override fun mostrarInput(x: Any) {
        print(x)
    }
}