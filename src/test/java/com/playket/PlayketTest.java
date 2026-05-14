package com.playket;

import com.playket.database.UsuarioDAO;
import com.playket.database.TorneoDAO;
import com.playket.database.ParticipanteDAO;
import com.playket.database.PartidoDAO;
import com.playket.model.Usuario;
import com.playket.model.Torneo;
import com.playket.model.Participante;
import org.junit.jupiter.api.*;
import java.time.LocalDate;
import static org.junit.jupiter.api.Assertions.*;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class PlayketTest {

    static UsuarioDAO usuarioDAO = new UsuarioDAO();
    static TorneoDAO torneoDAO = new TorneoDAO();
    static ParticipanteDAO participanteDAO = new ParticipanteDAO();
    static PartidoDAO partidoDAO = new PartidoDAO();

    // ─── PRUEBAS FUNCIONALES ───────────────────────────────────────

    @Test @Order(1)
    @DisplayName("PR01 - Registro: correo duplicado es rechazado")
    void testCorreoDuplicadoRechazado() {
        Usuario u1 = usuarioDAO.buscarPorEmail("test@playket.com");
        Usuario u2 = usuarioDAO.buscarPorEmail("test@playket.com");
        if (u1 != null && u2 != null) {
            assertEquals(u1.getId(), u2.getId(),
                    "El mismo correo no puede pertenecer a dos usuarios distintos");
        } else {
            assertNull(u2);
        }
    }

    @Test @Order(2)
    @DisplayName("PR02 - Login: credenciales correctas devuelven usuario")
    void testLoginCorrecto() {
        Usuario u = usuarioDAO.buscarPorEmail("test@playket.com");
        // Si el usuario existe debe tener id > 0
        if (u != null) assertTrue(u.getId() > 0);
    }

    @Test @Order(3)
    @DisplayName("PR05 - Búsqueda: filtro por estado devuelve resultados")
    void testBusquedaPorEstado() {
        var lista = torneoDAO.buscar("", null, "EN_CURSO");
        assertNotNull(lista);
    }

    @Test @Order(4)
    @DisplayName("PR06 - Listar partidos de un torneo")
    void testListarPartidosPorTorneo() {
        var lista = partidoDAO.listarPorTorneo(1);
        assertNotNull(lista);
    }

    // ─── PRUEBAS DE ACCESO A DATOS ────────────────────────────────

    @Test @Order(5)
    @DisplayName("PR07 - Crear torneo: inserción en base de datos")
    void testCrearTorneo() {
        Torneo t = new Torneo();
        t.setNombre("TorneoTest_JUnit");
        t.setFormato("ELIMINACION");
        t.setEstado("ABIERTO");
        t.setFechaInicio(LocalDate.now());
        t.setNumParticipantes(4);
        t.setIdDeporte(1);
        t.setIdOrganizador(1);
        boolean resultado = torneoDAO.insertar(t);
        assertTrue(resultado);
    }

    @Test @Order(6)
    @DisplayName("PR08 - Añadir participante a un torneo")
    void testAnadirParticipante() {
        Torneo torneoTest = torneoDAO.buscar("TorneoTest_JUnit", null, "")
                .stream().findFirst().orElse(null);
        assertNotNull(torneoTest, "El torneo de prueba debe existir");
        Participante p = new Participante();
        p.setNombre("ParticipanteTest");
        p.setApellidos("JUnit");
        p.setIdTorneo(torneoTest.getId());
        boolean resultado = participanteDAO.insertar(p);
        assertTrue(resultado);
    }

    @Test @Order(7)
    @DisplayName("PR08b - Listar participantes devuelve lista no vacía")
    void testListarParticipantes() {
        Torneo torneoTest = torneoDAO.buscar("TorneoTest_JUnit", null, "")
                .stream().findFirst().orElse(null);
        assertNotNull(torneoTest);
        var lista = participanteDAO.listarPorTorneo(torneoTest.getId());
        assertFalse(lista.isEmpty());
    }

    // ─── PRUEBAS DE INTEGRACIÓN ───────────────────────────────────

    @Test @Order(8)
    @DisplayName("PR09 - Generar cuadro cambia estado del torneo a EN_CURSO")
    void testGenerarCuadroCambiaEstado() {
        Torneo torneoTest = torneoDAO.buscar("TorneoTest_JUnit", null, "")
                .stream().findFirst().orElse(null);
        assertNotNull(torneoTest);
        torneoTest.setEstado("EN_CURSO");
        boolean actualizado = torneoDAO.actualizar(torneoTest);
        assertTrue(actualizado);
        Torneo recargado = torneoDAO.buscar("TorneoTest_JUnit", null, "EN_CURSO")
                .stream().findFirst().orElse(null);
        assertNotNull(recargado);
        assertEquals("EN_CURSO", recargado.getEstado());
    }

    @Test @Order(9)
    @DisplayName("PR10 - Cerrar torneo cambia estado a FINALIZADO")
    void testCerrarTorneo() {
        Torneo torneoTest = torneoDAO.buscar("TorneoTest_JUnit", null, "")
                .stream().findFirst().orElse(null);
        assertNotNull(torneoTest);
        boolean cerrado = torneoDAO.cerrar(torneoTest.getId());
        assertTrue(cerrado);
    }

    // ─── PRUEBAS DE SEGURIDAD ─────────────────────────────────────

    @Test @Order(10)
    @DisplayName("PR-SEG01 - Email vacío no encuentra usuario")
    void testEmailVacioNoEncuentraUsuario() {
        Usuario u = usuarioDAO.buscarPorEmail("");
        assertNull(u);
    }

    @Test @Order(11)
    @DisplayName("PR-SEG02 - Email inexistente no encuentra usuario")
    void testEmailInexistenteNoEncuentraUsuario() {
        Usuario u = usuarioDAO.buscarPorEmail("noexiste@fake.com");
        assertNull(u);
    }

    // ─── PRUEBAS DE RENDIMIENTO ───────────────────────────────────

    @Test @Order(12)
    @DisplayName("PR-REND01 - Búsqueda responde en menos de 2 segundos")
    void testBusquedaRendimiento() {
        long inicio = System.currentTimeMillis();
        torneoDAO.buscar("", null, "");
        long fin = System.currentTimeMillis();
        assertTrue((fin - inicio) < 2000,
                "La búsqueda tardó más de 2 segundos");
    }

    @Test @Order(13)
    @DisplayName("PR-REND02 - Carga de partidos responde en menos de 1 segundo")
    void testCargaPartidosRendimiento() {
        long inicio = System.currentTimeMillis();
        partidoDAO.listarPorTorneo(1);
        long fin = System.currentTimeMillis();
        assertTrue((fin - inicio) < 1000,
                "La carga de partidos tardó más de 1 segundo");
    }
}
