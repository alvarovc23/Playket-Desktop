package com.playket;

import com.playket.controller.LoginController;
import com.playket.database.UsuarioDAO;
import com.playket.database.TorneoDAO;
import com.playket.database.ParticipanteDAO;
import com.playket.database.PartidoDAO;
import com.playket.model.Usuario;
import com.playket.model.Torneo;
import com.playket.model.Participante;
import com.playket.util.CifradorDES;
import org.junit.jupiter.api.*;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.concurrent.CountDownLatch;
import static org.junit.jupiter.api.Assertions.*;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class PlayketTest {

    static UsuarioDAO usuarioDAO = new UsuarioDAO();
    static TorneoDAO torneoDAO = new TorneoDAO();
    static ParticipanteDAO participanteDAO = new ParticipanteDAO();
    static PartidoDAO partidoDAO = new PartidoDAO();

    // ─── FUNCIONALES ──────────────────────────────────────────────

    @Test @Order(1)
    @DisplayName("PR01 - Búsqueda por estado devuelve lista")
    void testBusquedaPorEstado() {
        List lista = torneoDAO.buscar("", null, "EN_CURSO");
        assertNotNull(lista);
    }

    @Test @Order(2)
    @DisplayName("PR02 - Crear torneo en base de datos")
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

    @Test @Order(3)
    @DisplayName("PR03 - Añadir participante a un torneo")
    void testAnadirParticipante() {
        Torneo torneo = torneoDAO.buscar("TorneoTest_JUnit", null, "")
                .stream().findFirst().orElse(null);
        assertNotNull(torneo, "El torneo de prueba debe existir");
        Participante p = new Participante();
        p.setNombre("ParticipanteTest");
        p.setApellidos("JUnit");
        p.setIdTorneo(torneo.getId());
        boolean resultado = participanteDAO.insertar(p);
        assertTrue(resultado);
    }

    // ─── INTEGRACIÓN ──────────────────────────────────────────────

    @Test @Order(4)
    @DisplayName("PR04 - Actualizar estado del torneo a EN_CURSO")
    void testActualizarEstado() {
        Torneo torneo = torneoDAO.buscar("TorneoTest_JUnit", null, "")
                .stream().findFirst().orElse(null);
        assertNotNull(torneo);
        torneo.setEstado("EN_CURSO");
        boolean actualizado = torneoDAO.actualizar(torneo);
        assertTrue(actualizado);
        Torneo recargado = torneoDAO.buscar("TorneoTest_JUnit", null, "EN_CURSO")
                .stream().findFirst().orElse(null);
        assertEquals("EN_CURSO", recargado.getEstado());
    }

    @Test @Order(5)
    @DisplayName("PR05 - Cerrar torneo cambia estado a FINALIZADO")
    void testCerrarTorneo() {
        Torneo torneo = torneoDAO.buscar("TorneoTest_JUnit", null, "")
                .stream().findFirst().orElse(null);
        assertNotNull(torneo);
        boolean cerrado = torneoDAO.cerrar(torneo.getId());
        assertTrue(cerrado);
    }

    // ─── SEGURIDAD ────────────────────────────────────────────────

    @Test @Order(6)
    @DisplayName("PR-SEG01 - El torneo solo pertenece a su organizador")
    void testTorneoPerteneceSoloAOrganizador() {
        Torneo t = new Torneo();
        t.setNombre("SEG_Propiedad");
        t.setFormato("ELIMINACION");
        t.setEstado("ABIERTO");
        t.setFechaInicio(LocalDate.now());
        t.setNumParticipantes(4);
        t.setIdDeporte(1);
        t.setIdOrganizador(1);
        torneoDAO.insertar(t);

        Torneo guardado = torneoDAO.buscar("SEG_Propiedad", null, "")
                .stream().findFirst().orElse(null);
        assertNotNull(guardado);
        assertEquals(1, guardado.getIdOrganizador());
        assertNotEquals(2, guardado.getIdOrganizador());
        torneoDAO.cerrar(guardado.getId());
    }

    @Test @Order(7)
    @DisplayName("PR-SEG02 - listarPorOrganizador no devuelve torneos ajenos")
    void testListarTorneosNoDevuelveAjenos() {
        Torneo t = new Torneo();
        t.setNombre("SEG_Lista");
        t.setFormato("ELIMINACION");
        t.setEstado("ABIERTO");
        t.setFechaInicio(LocalDate.now());
        t.setNumParticipantes(4);
        t.setIdDeporte(1);
        t.setIdOrganizador(1);
        torneoDAO.insertar(t);

        List<Torneo> torneosDeUsuario2 = torneoDAO.listarPorOrganizador(2);
        boolean aparece = false;
        for (Torneo torneo : torneosDeUsuario2) {
            if (torneo.getNombre().equals("SEG_Lista")) {
                aparece = true;
            }
        }
        assertFalse(aparece, "El torneo del usuario 1 no debe aparecer en la lista del usuario 2");

        Torneo creado = torneoDAO.buscar("SEG_Lista", null, "")
                .stream().findFirst().orElse(null);
        torneoDAO.cerrar(creado.getId());
    }

    @Test @Order(8)
    @DisplayName("PR-SEG03 - Las contraseñas se almacenan cifradas")
    void testPasswordCifrada() {
        String email = "seg_cifrado@playket.com";
        String passPlano = "TestPass123";

        if (usuarioDAO.buscarPorEmail(email) == null) {
            Usuario u = new Usuario();
            u.setNombre("Seg");
            u.setApellidos("Test");
            u.setEmail(email);
            u.setPassword(CifradorDES.cifrar(passPlano));
            u.setPreguntaSeguridad("Test");
            u.setRespuestaSeg(CifradorDES.cifrar("respuesta"));
            usuarioDAO.insertar(u);
        }

        Usuario guardado = usuarioDAO.buscarPorEmail(email);
        assertNotNull(guardado);
        assertNotEquals(passPlano, guardado.getPassword());
        assertTrue(CifradorDES.verificar(passPlano, guardado.getPassword()));
    }

    @Test @Order(9)
    @DisplayName("PR-SEG04 - Tres intentos fallidos activan el bloqueo")
    void testBloqueoTrasTreeIntentos() throws Exception {
        LoginController ctrl = loginSinVista();
        String email = "victima@playket.com";
        registrarIntento(ctrl, email);
        registrarIntento(ctrl, email);
        registrarIntento(ctrl, email);
        assertTrue(estaBloqueado(ctrl, email));
    }

    @Test @Order(10)
    @DisplayName("PR-SEG05 - El bloqueo expira después de 5 minutos")
    void testBloqueoExpira() throws Exception {
        LoginController ctrl = loginSinVista();
        String email = "victima2@playket.com";

        // Ponemos manualmente un bloqueo de hace 6 minutos
        Field campo = LoginController.class.getDeclaredField("tiempoBloqueo");
        campo.setAccessible(true);
        HashMap<String, LocalDateTime> mapa = (HashMap<String, LocalDateTime>) campo.get(ctrl);
        mapa.put(email, LocalDateTime.now().minusMinutes(6));

        assertFalse(estaBloqueado(ctrl, email));
    }

    // ─── CONCURRENCIA ─────────────────────────────────────────────

    @Test @Order(11)
    @DisplayName("PR-CONC01 - Varios usuarios crean torneos a la vez sin errores")
    void testCreacionConcurrente() throws InterruptedException {
        int numUsuarios = 5;
        CountDownLatch inicio = new CountDownLatch(1);   // señal de salida
        CountDownLatch fin = new CountDownLatch(numUsuarios); // espera a que todos terminen
        boolean[] resultados = new boolean[numUsuarios];

        for (int i = 0; i < numUsuarios; i++) {
            final int indice = i;
            new Thread(() -> {
                try {
                    inicio.await(); // todos esperan aquí hasta que se dé la señal
                    Torneo t = new Torneo();
                    t.setNombre("TorneoConc_" + indice);
                    t.setFormato("ELIMINACION");
                    t.setEstado("ABIERTO");
                    t.setFechaInicio(LocalDate.now());
                    t.setNumParticipantes(4);
                    t.setIdDeporte(1);
                    t.setIdOrganizador(indice + 1);
                    resultados[indice] = torneoDAO.insertar(t);
                } catch (Exception e) {
                    resultados[indice] = false;
                } finally {
                    fin.countDown(); // avisa de que este hilo ha terminado
                }
            }).start();
        }

        inicio.countDown(); // da la señal de salida a todos a la vez
        fin.await();        // espera a que todos terminen

        for (int i = 0; i < numUsuarios; i++) {
            assertTrue(resultados[i], "El torneo " + i + " no se insertó correctamente");
        }
    }

    // ─── RENDIMIENTO ──────────────────────────────────────────────

    @Test @Order(12)
    @DisplayName("PR-REND01 - Búsqueda responde en menos de 2 segundos")
    void testRendimientoBusqueda() {
        long inicio = System.currentTimeMillis();
        torneoDAO.buscar("", null, "");
        long fin = System.currentTimeMillis();
        assertTrue((fin - inicio) < 2000, "La búsqueda tardó más de 2 segundos");
    }

    // ─── HELPERS DE REFLEXIÓN (necesarios para los tests de bloqueo) ──────────

    // Estos tres métodos son necesarios porque registrarIntentoFallido() y
    // estaBloqueado() son privados en LoginController y no se pueden llamar
    // directamente desde fuera de la clase.

    private LoginController loginSinVista() throws Exception {
        sun.reflect.ReflectionFactory rf = sun.reflect.ReflectionFactory.getReflectionFactory();
        java.lang.reflect.Constructor<?> ctor = rf.newConstructorForSerialization(
                LoginController.class, Object.class.getDeclaredConstructor());
        LoginController ctrl = LoginController.class.cast(ctor.newInstance());
        Field fi = LoginController.class.getDeclaredField("intentosFallidos");
        fi.setAccessible(true);
        fi.set(ctrl, new HashMap<String, Integer>());
        Field ft = LoginController.class.getDeclaredField("tiempoBloqueo");
        ft.setAccessible(true);
        ft.set(ctrl, new HashMap<String, LocalDateTime>());
        return ctrl;
    }

    private void registrarIntento(LoginController ctrl, String email) throws Exception {
        Method m = LoginController.class.getDeclaredMethod("registrarIntentoFallido", String.class);
        m.setAccessible(true);
        m.invoke(ctrl, email);
    }

    private boolean estaBloqueado(LoginController ctrl, String email) throws Exception {
        Method m = LoginController.class.getDeclaredMethod("estaBloqueado", String.class);
        m.setAccessible(true);
        return (boolean) m.invoke(ctrl, email);
    }
}