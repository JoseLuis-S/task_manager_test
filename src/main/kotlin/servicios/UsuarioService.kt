package es.prog2425.taskmanager.servicios

import es.prog2425.taskmanager.datos.IUsuarioRepository
import es.prog2425.taskmanager.datos.UsuarioRepository
import es.prog2425.taskmanager.modelo.Tarea
import es.prog2425.taskmanager.modelo.Usuario

class UsuarioService(
    private val repositorio: IUsuarioRepository = UsuarioRepository()
) : IUsuarioService {

    override fun crearUsuario(nombre: String): Usuario {
        val usuario = Usuario(nombre)
        repositorio.agregarUsuario(usuario)
        return usuario
    }

    override fun asignarTareaAUsuario(usuario: Usuario, tarea: Tarea) {
        tarea.asignarUsuario(usuario)
        usuario.asignarTarea(tarea)
    }

    override fun obtenerTareasPorUsuario(usuario: Usuario): List<Tarea> {
        return usuario.obtenerTareasAsignadas()
    }

    override fun obtenerUsuarioPorNombre(nombre: String): Usuario? {
        return repositorio.obtenerUsuarioPorNombre(nombre)
    }
}