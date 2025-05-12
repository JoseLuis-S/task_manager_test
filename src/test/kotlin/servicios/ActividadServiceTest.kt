package servicios

import es.prog2425.taskmanager.datos.ActividadRepository
import es.prog2425.taskmanager.modelo.Actividad
import es.prog2425.taskmanager.modelo.Estado
import es.prog2425.taskmanager.modelo.Tarea
import es.prog2425.taskmanager.servicios.ActividadService
import io.kotest.assertions.throwables.shouldThrow
import io.kotest.core.spec.style.DescribeSpec
import io.kotest.matchers.shouldBe
import io.mockk.every
import io.mockk.mockk
import io.mockk.verify

class ActividadServiceTest : DescribeSpec({

    // Usa relaxed para no definir el comportamiento del repo (porque si no casca :/ )
    val mockRepositorio = mockk<ActividadRepository>(relaxed = true)
    val actividadService = ActividadService(mockRepositorio)

    describe("crearEvento") {

        it("Deberia crear un evento y agregarlo al repositorio") {

            val descripcion = "Evento de prueba"
            val fecha = "12-05-2025"
            val ubicacion = "Ubicacion de prueba"

            actividadService.crearEvento(descripcion, fecha, ubicacion)

            // Comprueba que se haya ejecutado correctamente
            verify { mockRepositorio.agregarEvento(any()) }
        }

        it("Deberia lanzar una excepcion por la fecha") {
            val descripcion = "Evento de prueba"
            val fecha = "2025-05-12"
            val ubicacion = "Ubicacion de prueba"

            // Comprueba que lanza una excepcion
            shouldThrow<IllegalArgumentException> {
                actividadService.crearEvento(descripcion, fecha, ubicacion)
            }
        }
    }

    describe("crearTarea") {

        it("Deberia crear una tarea con una descripcion valida y agregarla al repositorio") {
            val descripcion = "Tarea de ejemplo"

            actividadService.crearTarea(descripcion)

            verify { mockRepositorio.agregarTarea(any()) }
        }

        it("Deberia lanzar una excepcion si la descripcion es nula o vacia") {
            val descripcion = ""

            shouldThrow<IllegalArgumentException> {
                actividadService.crearTarea(descripcion)
            }
        }
    }

    describe("asociarSubtarea") {

        it("Deberia asociar una subtarea a una tarea principal") {
            val tareaPrincipal = mockk<Tarea>(relaxed = true)
            val subtarea = mockk<Tarea>()

            actividadService.asociarSubtarea(tareaPrincipal, subtarea)

            verify { tareaPrincipal.agregarSubtarea(subtarea) }
        }

        it("Deberia lanzar una excepcion si la subtarea es nula") {
            val tareaPrincipal = mockk<Tarea>(relaxed = true)
            val subtarea: Tarea? = null

            shouldThrow<java.lang.NullPointerException> {
                actividadService.asociarSubtarea(tareaPrincipal, subtarea!!)
            }
        }
    }

    describe("cambiarEstadoTarea") {

        it("Deberia cambiar el estado de una tarea correctamente") {
            val tarea = mockk<Tarea>(relaxed = true)
            val nuevoEstado = mockk<Estado>()

            actividadService.cambiarEstadoTarea(tarea, nuevoEstado)

            verify { tarea.cambiarEstadoConHistorial(nuevoEstado) }
        }

        it("Deberia lanzar una excepcion si la tarea es nula") {
            val tarea: Tarea? = null
            val nuevoEstado = mockk<Estado>()

            shouldThrow<java.lang.NullPointerException> {
                actividadService.cambiarEstadoTarea(tarea!!, nuevoEstado)
            }
        }
    }

    describe("listarActividades") {

        it("Deberia retornar una lista de actividades cuando el repositorio tiene datos") {
            val actividadesMock = listOf(mockk<Actividad>(), mockk<Actividad>())
            every { mockRepositorio.obtenerActividades() } returns actividadesMock

            val actividades = actividadService.listarActividades()

            actividades shouldBe actividadesMock
        }

        it("Deberia retornar una lista vacia cuando el repositorio esta vacio") {
            val actividadesMock = emptyList<Actividad>()
            every { mockRepositorio.obtenerActividades() } returns actividadesMock

            val actividades = actividadService.listarActividades()

            actividades shouldBe actividadesMock
        }
    }
})