package servicios

import es.prog2425.taskmanager.datos.ActividadRepository
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

        it("Deberia crear una tarea con etiquetas y agregarla al repositorio") {
            val descripcion = "Tarea importante"
            val etiquetas = listOf("urgente", "personal")

            val tareaMock = mockk<Tarea>(relaxed = true)
            every { Tarea.crearInstancia(descripcion) } returns tareaMock
            every { mockRepositorio.agregarTarea(tareaMock) } returns Unit

            val resultado = actividadService.crearTarea(descripcion, etiquetas)

            verify { tareaMock.agregarEtiqueta("urgente") }
            verify { tareaMock.agregarEtiqueta("personal") }
            verify { mockRepositorio.agregarTarea(tareaMock) }

            resultado shouldBe tareaMock
        }

        it("Deberia crear una tarea sin etiquetas si no se pasan etiquetas") {
            val descripcion = "Tarea sin etiquetas"
            val etiquetas = emptyList<String>()

            val tareaMock = mockk<Tarea>(relaxed = true)
            every { Tarea.crearInstancia(descripcion) } returns tareaMock
            every { mockRepositorio.agregarTarea(tareaMock) } returns Unit

            val resultado = actividadService.crearTarea(descripcion, etiquetas)

            verify(exactly = 0) { tareaMock.agregarEtiqueta(any()) } // No debe agregar ninguna etiqueta
            verify { mockRepositorio.agregarTarea(tareaMock) }

            resultado shouldBe tareaMock
        }
    }
})