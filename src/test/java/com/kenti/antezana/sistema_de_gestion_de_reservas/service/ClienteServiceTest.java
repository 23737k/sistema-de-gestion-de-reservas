package com.kenti.antezana.sistema_de_gestion_de_reservas.service;

import static com.kenti.antezana.sistema_de_gestion_de_reservas.model.EstadoReserva.ASISTIDO;
import static com.kenti.antezana.sistema_de_gestion_de_reservas.model.EstadoReserva.CANCELADO;
import static com.kenti.antezana.sistema_de_gestion_de_reservas.model.EstadoReserva.CONFIRMADO;
import static com.kenti.antezana.sistema_de_gestion_de_reservas.model.TipoDeDocumento.DNI;
import static com.kenti.antezana.sistema_de_gestion_de_reservas.model.TipoDeEntrada.ENTRADA_GENERAL;
import static com.kenti.antezana.sistema_de_gestion_de_reservas.model.TipoDeEntrada.PLATEA;
import static com.kenti.antezana.sistema_de_gestion_de_reservas.model.TipoDeEvento.RECITAL;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.doAnswer;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.kenti.antezana.sistema_de_gestion_de_reservas.dto.mapper.ClienteMapper;
import com.kenti.antezana.sistema_de_gestion_de_reservas.dto.mapper.ReservaMapper;
import com.kenti.antezana.sistema_de_gestion_de_reservas.dto.request.ClienteReq;
import com.kenti.antezana.sistema_de_gestion_de_reservas.dto.request.EstadoReservaReq;
import com.kenti.antezana.sistema_de_gestion_de_reservas.dto.request.ReservaReq;
import com.kenti.antezana.sistema_de_gestion_de_reservas.dto.response.ClienteRes;
import com.kenti.antezana.sistema_de_gestion_de_reservas.dto.response.ReservaRes;
import com.kenti.antezana.sistema_de_gestion_de_reservas.model.Cliente;
import com.kenti.antezana.sistema_de_gestion_de_reservas.model.Disponibilidad;
import com.kenti.antezana.sistema_de_gestion_de_reservas.model.EstadoReserva;
import com.kenti.antezana.sistema_de_gestion_de_reservas.model.Evento;
import com.kenti.antezana.sistema_de_gestion_de_reservas.model.Funcion;
import com.kenti.antezana.sistema_de_gestion_de_reservas.model.Lugar;
import com.kenti.antezana.sistema_de_gestion_de_reservas.model.PaseGratis;
import com.kenti.antezana.sistema_de_gestion_de_reservas.model.Reserva;
import com.kenti.antezana.sistema_de_gestion_de_reservas.model.TipoDeEntrada;
import com.kenti.antezana.sistema_de_gestion_de_reservas.repository.ClienteRepo;
import com.kenti.antezana.sistema_de_gestion_de_reservas.repository.ReservaRepo;
import jakarta.persistence.EntityNotFoundException;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class ClienteServiceTest {

    @Mock
    private ClienteRepo clienteRepo;
    @Mock
    private ClienteMapper clienteMapper;
    @Mock
    private ReservaRepo reservaRepo;
    @Mock
    private ReservaMapper reservaMapper;
    @Mock
    private EventoService eventoService;
    @Spy
    private PaseGratisService paseGratisService;

    @InjectMocks
    private ClienteService clienteService;

    private Evento evento;
    private Funcion funcion;
    private Disponibilidad disponibilidad;
    private Cliente cliente;
    private ClienteReq clienteReq;
    private Reserva reserva;
    private ReservaReq reservaReq;

    @BeforeEach
    void setUp() {
        evento = new Evento(1L, "Concierto de Rock", "Concierto de la banda XYZ con entradas VIP y generales", RECITAL, new ArrayList<>());

        disponibilidad = new Disponibilidad(1L, TipoDeEntrada.PLATEA, BigDecimal.valueOf(1500), 6, 0);

        funcion= new Funcion(1L, LocalDate.of(2025, 8, 20), LocalTime.of(20, 0),
            new Lugar("Teatro Gran Rex", "Av. Corrientes 857", "CABA","CABA"), evento, List.of(disponibilidad));


        evento.getFunciones().add(funcion);

        cliente = new Cliente(1L, "Juan", "Perez", "juan.perez@example.com","1122334455",
            DNI, "12345678", new ArrayList<>(), new ArrayList<>());

        clienteReq = new ClienteReq("Juan", "Perez", "juan.perez@example.com","1122334455",
            DNI,"12345678");

        reservaReq = new ReservaReq(1L,PLATEA);
        reserva = new Reserva(1L,
            LocalDate.now(),funcion,PLATEA,cliente,CONFIRMADO,BigDecimal.valueOf(1500),false);

        cliente.getReservas().add(reserva);

    }

    //TESTS CLIENTES

    @Test
    void deberiaObtenerClientes() {
        when(clienteRepo.findAll()).thenReturn(List.of(cliente));
        when(clienteMapper.toRes(any(Cliente.class))).thenAnswer(invocation -> {
            Cliente c = invocation.getArgument(0);
            return new ClienteRes(
                c.getId(),
                c.getNombre(),
                c.getApellido(),
                c.getEmail(),
                c.getTelefono(),
                c.getTipoDeDocumento(),
                c.getDocumento(),
                new ArrayList<>()
            );
        });

        List<ClienteRes> resultado = clienteService.obtenerClientes();

        assertEquals(1, resultado.size());
        verify(clienteRepo).findAll();
    }

    @Test
    void deberiaObtenerClientePorId() {
        when(clienteRepo.findById(1L)).thenReturn(Optional.of(cliente));
        when(clienteMapper.toRes(any(Cliente.class))).thenAnswer(invocation -> {
            Cliente c = invocation.getArgument(0);
            return new ClienteRes(
                c.getId(),
                c.getNombre(),
                c.getApellido(),
                c.getEmail(),
                c.getTelefono(),
                c.getTipoDeDocumento(),
                c.getDocumento(),
                new ArrayList<>()
            );
        });

        ClienteRes resultado = clienteService.obtenerCliente(1L);

        assertEquals("Juan", resultado.nombre());
        assertEquals("Perez", resultado.apellido());
        verify(clienteRepo).findById(1L);
    }

    @Test
    void deberiaLanzarExcepcionSiClienteNoExiste() {
        when(clienteRepo.findById(99L)).thenReturn(Optional.empty());
        assertThrows(EntityNotFoundException.class,
            () -> clienteService.obtenerCliente(99L));
        verify(clienteRepo).findById(99L);
    }

    @Test
    void deberiaCrearCliente() {
        when(clienteMapper.toEntity(clienteReq)).thenReturn(cliente);
        when(clienteRepo.save(cliente)).thenReturn(cliente);
        when(clienteMapper.toRes(any(Cliente.class))).thenAnswer(invocation -> {
            Cliente c = invocation.getArgument(0);
            return new ClienteRes(
                c.getId(),
                c.getNombre(),
                c.getApellido(),
                c.getEmail(),
                c.getTelefono(),
                c.getTipoDeDocumento(),
                c.getDocumento(),
                new ArrayList<>()
            );
        });

        ClienteRes resultado = clienteService.crearCliente(clienteReq);

        assertEquals("Juan", resultado.nombre());
        assertEquals("Perez", resultado.apellido());
        verify(clienteRepo).save(cliente);
    }

    @Test
    void deberiaActualizarCliente() {
        Cliente copiaClienteViejo =  new Cliente(
            cliente.getId(),cliente.getNombre(),cliente.getApellido(),cliente.getEmail(),cliente.getTelefono(),cliente.getTipoDeDocumento(),cliente.getDocumento(),
            cliente.getReservas(),cliente.getPasesGratis());

        ClienteReq clienteActualizadoReq =
            new ClienteReq("NombreNuevo","Perez","juan.perez@example.com","1122334455",DNI,"12345678");

        when(clienteRepo.findById(1L)).thenReturn(Optional.of(cliente));
        when(clienteRepo.save(cliente)).thenAnswer(invocation -> invocation.getArgument(0));
        doAnswer(invocation -> {
            ClienteReq req = invocation.getArgument(0);
            Cliente c = invocation.getArgument(1);

            c.setNombre(req.nombre());
            c.setApellido(req.apellido());
            c.setEmail(req.email());
            c.setTelefono(req.telefono());
            c.setTipoDeDocumento(req.tipoDeDocumento());
            c.setDocumento(req.documento());
            return null;
        }).when(clienteMapper).updateEntity(clienteActualizadoReq, cliente);
        
        when(clienteMapper.toRes(any(Cliente.class))).thenAnswer(invocation -> {
            Cliente c = invocation.getArgument(0);
            return new ClienteRes(
                c.getId(),
                c.getNombre(),
                c.getApellido(),
                c.getEmail(),
                c.getTelefono(),
                c.getTipoDeDocumento(),
                c.getDocumento(),
                new ArrayList<>()
            );
        });
        
        ClienteRes resultado = clienteService.actualizarCliente(1L, clienteActualizadoReq);

        // asserts
        assertNotEquals(copiaClienteViejo.getNombre(), resultado.nombre());
        assertEquals(copiaClienteViejo.getApellido(), resultado.apellido());
        assertEquals(copiaClienteViejo.getEmail(), resultado.email());
        assertEquals(copiaClienteViejo.getTelefono(), resultado.telefono());
        assertEquals(copiaClienteViejo.getDocumento(), resultado.documento());
        assertEquals(1L, resultado.id());

        // verificar interacciones
        verify(clienteRepo).findById(1L);
        verify(clienteRepo).save(cliente);
        verify(clienteMapper).updateEntity(clienteActualizadoReq, cliente);
    }

    @Test
    void deberiaLanzarExcepcionSiClienteNoExisteAlActualizar() {
        ClienteReq clienteActualizadoReq = new ClienteReq("Pedro", "Perez", "juan.perez@example.com",
            "1122334455", DNI, "12345678");
        when(clienteRepo.findById(77L)).thenReturn(Optional.empty());

        assertThrows(EntityNotFoundException.class,
            () -> clienteService.actualizarCliente(77L, clienteActualizadoReq));
    }

    @Test
    void deberiaEliminarClienteSiExiste() {
        when(clienteRepo.existsById(5L)).thenReturn(true);

        clienteService.eliminarCliente(5L);

        verify(clienteRepo).deleteById(5L);
    }

    @Test
    void deberiaLanzarExcepcionAlEliminarClienteInexistente() {
        when(clienteRepo.existsById(123L)).thenReturn(false);

        assertThrows(EntityNotFoundException.class,
            () -> clienteService.eliminarCliente(123L));

        verify(clienteRepo, never()).deleteById(anyLong());
    }

    @Test
    void deberiaLanzarExcepcionSiNoEncuentraCliente() {
        when(clienteRepo.findById(50L)).thenReturn(Optional.empty());

        assertThrows(EntityNotFoundException.class,
            () -> clienteService.encontrarCliente(50L));
    }

    //TESTS RESERVAS
    @Test
    void deberiaObtenerReserva() {
        when(clienteRepo.findById(1L)).thenReturn(Optional.of(cliente));

        when(reservaMapper.toRes(any(Reserva.class))).thenAnswer(invocation -> {
            Reserva r = invocation.getArgument(0);
            return new ReservaRes(
                r.getId(),
                r.getFechaCreacion(),
                r.getFuncion().getId(),
                r.getTipoDeEntrada(),
                r.getCliente().getId(),
                r.getEstadoReserva(),
                r.getMontoTotal(),
                r.isBonificado()
            );
        });

        ReservaRes resultado = clienteService.obtenerReserva(1L, 1L);

        assertEquals(1L, resultado.id());
        verify(clienteRepo).findById(1L);
    }

    @Test
    void deberiaLanzarExcepcionSiReservaNoExiste() {
        when(clienteRepo.findById(1L)).thenReturn(Optional.of(cliente));
        assertThrows(EntityNotFoundException.class,
            () -> clienteService.obtenerReserva(1L, 99L));
    }

    @Test
    void deberiaCrearReservaSinPaseGratis() {
        int cuposAntesDeReserva = disponibilidad.getCuposOcupados();
        when(clienteRepo.findById(1L)).thenReturn(Optional.of(cliente));
        when(eventoService.encontrarFuncionPorId(1L)).thenReturn(funcion);
        when(reservaRepo.save(any(Reserva.class))).thenAnswer(invocation -> invocation.getArgument(0));

        when(reservaMapper.toRes(any(Reserva.class))).thenAnswer(invocation -> {
            Reserva r = invocation.getArgument(0);
            return new ReservaRes(
                r.getId(),
                r.getFechaCreacion(),
                r.getFuncion().getId(),
                r.getTipoDeEntrada(),
                r.getCliente().getId(),
                r.getEstadoReserva(),
                r.getMontoTotal(),
                r.isBonificado()
            );
        });

        ReservaRes resultado = clienteService.crearReserva(1L, reservaReq);

        assertEquals(BigDecimal.valueOf(1500), resultado.montoTotal());
        assertFalse(resultado.bonificado());
        assertEquals(cuposAntesDeReserva, disponibilidad.getCuposOcupados()-1);
        verify(reservaRepo).save(any());
    }

    @Test
    void deberiaCrearReservaConPaseGratis() {
        PaseGratis pase = new PaseGratis(1L,LocalDate.now(),false);
        cliente.getPasesGratis().add(pase);

        when(clienteRepo.findById(1L)).thenReturn(Optional.of(cliente));
        when(eventoService.encontrarFuncionPorId(1L)).thenReturn(funcion);
        when(reservaRepo.save(any(Reserva.class))).thenAnswer(invocation -> invocation.getArgument(0));
        when(reservaMapper.toRes(any(Reserva.class))).thenAnswer(invocation -> {
            Reserva r = invocation.getArgument(0);
            return new ReservaRes(
                r.getId(),
                r.getFechaCreacion(),
                r.getFuncion().getId(),
                r.getTipoDeEntrada(),
                r.getCliente().getId(),
                r.getEstadoReserva(),
                r.getMontoTotal(),
                r.isBonificado()
            );
        });

        ReservaRes resultado = clienteService.crearReserva(1L, reservaReq);

        assertEquals(BigDecimal.ZERO, resultado.montoTotal());
        assertTrue(resultado.bonificado());
        assertTrue(pase.isUsado());
    }


    @Test
    void deberiaModificarEstadoReservaValido() {
        EstadoReservaReq estadoReq = new EstadoReservaReq(ASISTIDO);
        when(clienteRepo.findById(1L)).thenReturn(Optional.of(cliente));
        when(reservaRepo.findById(1L)).thenReturn(Optional.of(reserva));
        when(reservaRepo.save(reserva)).thenAnswer(invocation -> invocation.getArgument(0));
        when(reservaMapper.toRes(any(Reserva.class))).thenAnswer(invocation -> {
            Reserva r = invocation.getArgument(0);
            return new ReservaRes(
                r.getId(),
                r.getFechaCreacion(),
                r.getFuncion().getId(),
                r.getTipoDeEntrada(),
                r.getCliente().getId(),
                r.getEstadoReserva(),
                r.getMontoTotal(),
                r.isBonificado()
            );
        });

        ReservaRes resultado = clienteService.modificarEstadoReserva(1L, 1L, estadoReq);

        assertEquals(ASISTIDO, resultado.estadoReserva());
        verify(reservaRepo).save(reserva);
    }

    @Test
    void deberiaOtorgarPaseGratisAlQuintoAsistido() {
        EstadoReservaReq estadoReq = new EstadoReservaReq(EstadoReserva.ASISTIDO);

        List<Reserva> reservas = new ArrayList<>();
        for (int i = 1; i <= 4; i++) {
            Reserva r = new Reserva();
            r.setId((long) i);
            r.setCliente(cliente);
            r.setEstadoReserva(EstadoReserva.ASISTIDO);
            r.setFechaCreacion(LocalDate.now());
            reservas.add(r);
        }

        Reserva quintaReserva = new Reserva();
        quintaReserva.setId(5L);
        quintaReserva.setCliente(cliente);
        quintaReserva.setEstadoReserva(CONFIRMADO);
        quintaReserva.setFuncion(funcion);
        quintaReserva.setTipoDeEntrada(ENTRADA_GENERAL);

        when(clienteRepo.findById(1L)).thenReturn(Optional.of(cliente));
        when(reservaRepo.findById(5L)).thenReturn(Optional.of(quintaReserva));
        when(reservaRepo.save(any(Reserva.class))).thenAnswer(invocation -> invocation.getArgument(0));
        when(reservaMapper.toRes(any(Reserva.class))).thenAnswer(invocation -> {
            Reserva r = invocation.getArgument(0);
            return new ReservaRes(
                r.getId(),
                r.getFechaCreacion(),
                r.getFuncion().getId(),
                r.getTipoDeEntrada(),
                r.getCliente().getId(),
                r.getEstadoReserva(),
                r.getMontoTotal(),
                r.isBonificado()
            );
        });
        reservas.add(quintaReserva);
        cliente.getReservas().addAll(reservas);
        ReservaRes resultado = clienteService.modificarEstadoReserva(cliente.getId(), 5L, estadoReq);

        assertEquals(ASISTIDO, resultado.estadoReserva());
        assertEquals(1,cliente.getPasesGratis().size());
        verify(reservaRepo).save(quintaReserva);
        verify(paseGratisService).calificaParaPaseGratis(any());
    }

    @Test
    void deberiaModificarEstadoReservaCANCELADOYDevolverCupo() {
        EstadoReservaReq estadoReq = new EstadoReservaReq(CANCELADO);

        when(reservaRepo.findById(1L)).thenReturn(Optional.of(reserva));
        when(reservaRepo.save(any(Reserva.class))).thenAnswer(invocation -> invocation.getArgument(0));
        when(reservaMapper.toRes(any(Reserva.class))).thenAnswer(invocation -> {
            Reserva r = invocation.getArgument(0);
            return new ReservaRes(
                r.getId(),
                r.getFechaCreacion(),
                r.getFuncion().getId(),
                r.getTipoDeEntrada(),
                r.getCliente().getId(),
                r.getEstadoReserva(),
                r.getMontoTotal(),
                r.isBonificado()
            );
        });

        reserva.setFuncion(funcion);
        clienteService.modificarEstadoReserva(1L, 1L, estadoReq);

        assertEquals(CANCELADO, reserva.getEstadoReserva());
        verify(eventoService).devolverCupo(funcion.getId(), PLATEA);
        verify(reservaRepo).save(reserva);
    }

}
