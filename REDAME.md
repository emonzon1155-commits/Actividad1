# Sistema de Control de Rescate y Adopción Animal

## Descripción del Proyecto
Sistema diseñado para la gestión de casos en un refugio de animales, permitiendo reportar ingresos de animales rescatados (perros, gatos, fauna silvestre), asignar voluntarios o veterinarios encargados y gestionar los estados del proceso (Rescatado, En Tratamiento, Adopción).

## Arquitectura y Patrones de Diseño
El sistema aplica una arquitectura desacoplada basada en tres patrones de diseño:
- **MVC (Modelo - Vista - Controlador):** Separación de la interfaz gráfica, la lógica de control y las entidades del modelo.
- **Factory Method:** Creación dinámica de expedientes según la especie del animal (Perro, Gato, Fauna Silvestre).
- **Observer:** Sistema de notificación automática a los responsables (voluntarios y veterinarios) ante asignaciones o cambios de estado.

## Metodología de Versionado (Gitflow)
El desarrollo y modelado del sistema se gestiona mediante la metodología Gitflow:
- `main`: Rama de producción y entrega final.
- `develop`: Rama de integración de características.
- `feature/*`: Ramas de trabajo para documentación, diagramas UML y arquitectura.

## Integrante
- Evelyn Monzón Castro
