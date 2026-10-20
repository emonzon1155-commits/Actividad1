# Análisis del Problema, Requerimientos y Modelado UML

## 1. Identificación de Actores del Sistema
- **Ciudadano / Reportante:** Usuario externo que identifica animales en riesgo, registra incidentes con geolocalización/multimedia y postula a adopción.
- **Voluntario / Veterinario:** Personal operativo. Veterinarios emiten diagnósticos y prescriben tratamientos; voluntarios asumen cuidados, traslados y seguimiento.
- **Administrador del Refugio:** Asigna personal responsable, supervisa el refugio y aprueba adopciones.

## 2. Requerimientos Funcionales (RF)
- **RF-01:** Registro de incidentes de rescate con geolocalización y multimedia.
- **RF-02:** Categorización de especie (Canino, Felino, Fauna Silvestre) y generación de expediente adaptado.
- **RF-03:** Asignación de personal operativo y médico a casos activos.
- **RF-04:** Registro de transiciones de estado: Reportado -> Rescatado -> En Tratamiento -> Disponible para Adopción -> Adoptado / Liberado.
- **RF-05:** Notificaciones automáticas reactivas en tiempo real.
- **RF-06:** Gestión y aprobación de postulaciones de adopción.

## 3. Requerimientos No Funcionales (RNF)
- **RNF-01 (Mantenibilidad):** Desacoplamiento de responsabilidades bajo principios SOLID.
- **RNF-02 (Escalabilidad):** Arquitectura modular para admitir incremento de reportes y nuevas especies.
- **RNF-03 (Trazabilidad):** Control de versiones formal mediante Gitflow en GitHub.
- **RNF-04 (Rendimiento):** Tiempo de respuesta y despacho menor a 2 segundos.

## 4. Diagrama de Casos de Uso (Mermaid)
```mermaid
usecaseDiagram
    actor "Ciudadano / Reportante" as CR
    actor "Voluntario / Veterinario" as VV
    actor "Administrador del Refugio" as AR
    package "Sistema de Control de Rescate y Adopción" {
        usecase "CU-01: Registrar Caso de Rescate" as UC1
        usecase "CU-02: Consultar Estado de Rescate" as UC2
        usecase "CU-03: Postular a Adopción" as UC3
        usecase "CU-04: Registrar Diagnóstico y Tratamiento" as UC4
        usecase "CU-05: Actualizar Estado del Animal" as UC5
        usecase "CU-06: Asignar Responsable a Caso" as UC6
        usecase "CU-07: Gestionar Expedientes por Especie" as UC7
        usecase "CU-08: Aprobar / Rechazar Adopción" as UC8
        usecase "CU-09: Despachar Notificaciones Automáticas" as UC9
    }
    CR --> UC1
    CR --> UC2
    CR --> UC3
    VV --> UC4
    VV --> UC5
    AR --> UC6
    AR --> UC7
    AR --> UC8
    UC1 ..> UC9 : <<include>>
    UC5 ..> UC9 : <<include>>
    UC4 ..> UC5 : <<extend>>
sequenceDiagram
    autonumber
    actor Admin as Administrador
    participant MVC_Ctrl as ControladorRescate
    participant Factoria as CreadorFaunaSilvestre
    participant Exp as ExpedienteFaunaSilvestre
    participant Singleton as MotorGestionRescate
    participant ObsVol as Voluntario (Observer)
    participant ObsVet as Veterinario (Observer)
    Admin->>MVC_Ctrl: postNuevoRescate(datos, "FaunaSilvestre")
    MVC_Ctrl->>Factoria: crearExpediente("Monito Aullador", 2)
    Factoria->>Exp: new ExpedienteFaunaSilvestre()
    Factoria-->>MVC_Ctrl: instanciaExpediente
    MVC_Ctrl->>Singleton: getInstancia()
    Singleton-->>MVC_Ctrl: motorInstancia
    MVC_Ctrl->>Singleton: registrarCaso(nuevoRescate)
    activate Singleton
    Singleton->>Singleton: listaRescates.add(nuevoRescate)
    Singleton->>Singleton: notificarObservadores("Nuevo rescate prioritario", nuevoRescate)
    par Alerta a Personal
        Singleton->>ObsVol: actualizar(mensaje, nuevoRescate)
        ObsVol-->>Singleton: confirmación recepción
    and Alerta a Veterinario Especialista
        Singleton->>ObsVet: actualizar(mensaje, nuevoRescate)
        ObsVet-->>Singleton: confirmación recepción
    end
    deactivate Singleton
    MVC_Ctrl-->>Admin: HTTP 201 Created (Rescate Registrado con Éxito)
