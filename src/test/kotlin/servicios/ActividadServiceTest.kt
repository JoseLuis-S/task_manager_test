package servicios

import es.prog2425.taskmanager.datos.ActividadRepository
import es.prog2425.taskmanager.servicios.ActividadService
import io.kotest.core.spec.style.DescribeSpec
import io.mockk.mockk
import io.mockk.verify

class ActividadServiceTest : DescribeSpec({

    // Usa relaxed para no definir el comportamiento del repo (porque si no casca :/ )
    val mockRepositorio = mockk<ActividadRepository>(relaxed = true) 
    val actividadService = ActividadService(mockRepositorio)

    describe("crearEvento") {

        it("Debería crear un evento y agregarlo al repositorio") {

            val descripcion = "Evento de prueba"
            val fecha = "12-05-2025"
            val ubicacion = "Ubicación de prueba"

            actividadService.crearEvento(descripcion, fecha, ubicacion)

            verify { mockRepositorio.agregarEvento(any()) } // Comprueba que se haya ejecutado correctamente
        }
    }
})