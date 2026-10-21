import java.util.ArrayList;
import java.util.List;

// ========================================================
// 1. PATRÓN CREACIONAL: FACTORY METHOD
// ========================================================
public abstract class ExpedienteAnimal {
    protected String id;
    protected String nombre;
    protected int edadAprox;

    public ExpedienteAnimal(String id, String nombre, int edadAprox) {
        this.id = id;
        this.nombre = nombre;
        this.edadAprox = edadAprox;
    }
    public abstract void mostrarProtocoloCuidados();
    public abstract String getTipoEspecie();
}

public class ExpedientePerro extends ExpedienteAnimal {
    public ExpedientePerro(String id, String nombre, int edad) { super(id, nombre, edad); }
    @Override public void mostrarProtocoloCuidados() {
        System.out.println("Protocolo Canino: Vacunación Séxtuple, Desparasitación y Plan de Paseos.");
    }
    @Override public String getTipoEspecie() { return "Canino"; }
}

public class ExpedienteFaunaSilvestre extends ExpedienteAnimal {
    private String registroAmbiental;
    public ExpedienteFaunaSilvestre(String id, String nombre, int edad) {
        super(id, nombre, edad);
        this.registroAmbiental = "MAATE-FAUNA-" + System.currentTimeMillis();
    }
    @Override public void mostrarProtocoloCuidados() {
        System.out.println("Protocolo Silvestre: Cuarentena estricta y reporte ministerial: " + registroAmbiental);
    }
    @Override public String getTipoEspecie() { return "Fauna Silvestre"; }
}

public abstract class CreadorExpediente {
    public abstract ExpedienteAnimal crearExpediente(String id, String nombre, int edad);
}

public class CreadorPerro extends CreadorExpediente {
    @Override public ExpedienteAnimal crearExpediente(String id, String nombre, int edad) {
        return new ExpedientePerro(id, nombre, edad);
    }
}

public class CreadorFaunaSilvestre extends CreadorExpediente {
    @Override public ExpedienteAnimal crearExpediente(String id, String nombre, int edad) {
        return new ExpedienteFaunaSilvestre(id, nombre, edad);
    }
}

// ========================================================
// 2. PATRÓN DE COMPORTAMIENTO: OBSERVER
// ========================================================
public interface IObserverNotificacion {
    void actualizar(String evento, String detalleRescate);
}

public class Voluntario implements IObserverNotificacion {
    private String nombre;
    public Voluntario(String nombre) { this.nombre = nombre; }
    @Override public void actualizar(String evento, String detalleRescate) {
        System.out.println("[SMS a Voluntario " + nombre + "] Evento: " + evento + " -> Info: " + detalleRescate);
    }
}

public class Veterinario implements IObserverNotificacion {
    private String doctor;
    public Veterinario(String doctor) { this.doctor = doctor; }
    @Override public void actualizar(String evento, String detalleRescate) {
        System.out.println("[URGENCIA VET Dr. " + doctor + "] Notificación: " + evento + " | Paciente: " + detalleRescate);
    }
}

// ========================================================
// 3. PATRÓN CREACIONAL: SINGLETON
// ========================================================
public class MotorGestionRescate {
    private static volatile MotorGestionRescate instanciaUnica;
    private List<IObserverNotificacion> observadores = new ArrayList<>();

    private MotorGestionRescate() {}

    public static MotorGestionRescate getInstancia() {
        if (instanciaUnica == null) {
            synchronized (MotorGestionRescate.class) {
                if (instanciaUnica == null) {
                    instanciaUnica = new MotorGestionRescate();
                }
            }
        }
        return instanciaUnica;
    }

    public void suscribirObservador(IObserverNotificacion o) { observadores.add(o); }
    public void removerObservador(IObserverNotificacion o) { observadores.remove(o); }
    public void notificar(String evento, String detalle) {
        for (IObserverNotificacion obs : observadores) {
            obs.actualizar(evento, detalle);
        }
    }
}
