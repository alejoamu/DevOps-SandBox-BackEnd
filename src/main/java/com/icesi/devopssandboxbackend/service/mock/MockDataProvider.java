package com.icesi.devopssandboxbackend.service.mock;

import java.util.ArrayList;
import java.util.List;

import com.icesi.devopssandboxbackend.domain.enums.ResourceType;
import com.icesi.devopssandboxbackend.dto.content.FaqResponse;
import com.icesi.devopssandboxbackend.dto.content.SupportResourceResponse;
import com.icesi.devopssandboxbackend.dto.guide.GuideResponse;
import com.icesi.devopssandboxbackend.dto.guide.PhaseResponse;
import com.icesi.devopssandboxbackend.dto.guide.PhaseStepResponse;
import com.icesi.devopssandboxbackend.dto.guide.StepResourceResponse;

/**
 * Proveedor de datos mockeados para desarrollo sin base de datos.
 * Replica exactamente la estructura de guidesData.ts del frontend.
 */
public class MockDataProvider {

	public static List<GuideResponse> getMockGuides() {
		var guides = new ArrayList<GuideResponse>();
		guides.add(createIaGuide());
		guides.add(createIotGuide());
		return guides;
	}

	public static GuideResponse getMockGuide(String guideSlug) {
		return getMockGuides().stream()
				.filter(g -> g.getId().equals(guideSlug))
				.findFirst()
				.orElse(null);
	}

	public static PhaseResponse getMockPhase(String guideSlug, String phaseSlug) {
		GuideResponse guide = getMockGuide(guideSlug);
		if (guide == null || guide.getPhases() == null) {
			return null;
		}
		return guide.getPhases().stream()
				.filter(p -> p.getId().equals(phaseSlug))
				.findFirst()
				.orElse(null);
	}

	public static List<FaqResponse> getMockFaqs() {
		var faqs = new ArrayList<FaqResponse>();
		faqs.add(createFaq("¿Cuál es la diferencia entre PDG1 y PDG2?",
				"PDG1 se enfoca en la planificación y diseño de la investigación (definición del problema, marco teórico y metodología), mientras que PDG2 se centra en la ejecución, análisis de resultados y conclusiones del proyecto."));
		faqs.add(createFaq("¿Cuánto tiempo tengo para completar cada fase?",
				"El tiempo varía según la fase, pero generalmente cada una toma entre 2 y 6 semanas. Consulta la descripción específica de cada fase para tiempos estimados."));
		faqs.add(createFaq("¿Puedo cambiar mi tema de investigación después de PDG1?",
				"Los cambios mayores deben ser consultados con tu director de proyecto. Cambios menores de enfoque son posibles con justificación adecuada."));
		faqs.add(createFaq("¿Dónde puedo encontrar ejemplos de proyectos anteriores?",
				"La sección de Recursos contiene ejemplos de proyectos exitosos. También puedes consultar el repositorio institucional de la biblioteca."));
		faqs.add(createFaq("¿Qué formato de citación debo usar?",
				"La Universidad Icesi requiere el formato APA (última edición) para todas las citaciones y referencias bibliográficas."));
		faqs.add(createFaq("¿Cómo selecciono mi director de proyecto?",
				"Debes elegir un profesor del programa que tenga experiencia en tu área de interés. Contacta directamente al profesor y solicita una reunión para discutir tu propuesta."));
		faqs.add(createFaq("¿Puedo trabajar en grupo para el proyecto de grado?",
				"Depende del programa. Algunos permiten proyectos en pareja o grupos pequeños. Consulta con la coordinación de tu programa."));
		faqs.add(createFaq("¿Qué hago si no apruebo una fase?",
				"Debes revisar los comentarios de tu evaluador, hacer las correcciones necesarias y volver a presentar según el calendario académico."));
		faqs.add(createFaq("¿Cómo accedo a las bases de datos para mi investigación?",
				"Puedes acceder a través de la biblioteca virtual de la Universidad Icesi con tu usuario institucional. Consulta la sección de Recursos para más información."));
		faqs.add(createFaq("¿Necesito aprobación ética para mi proyecto?",
				"Si tu investigación involucra seres humanos, datos personales o experimentación, debes solicitar aprobación del comité de ética antes de iniciar la recolección de datos."));
		return faqs;
	}

	public static List<SupportResourceResponse> getMockResources() {
		var resources = new ArrayList<SupportResourceResponse>();
		resources.add(createResource("Plantilla de Propuesta de Investigación", "document", "PDG1",
				"Formato estándar para la presentación de tu propuesta inicial.", "DOCX", null));
		resources.add(createResource("Guía de Redacción Académica", "document", "General",
				"Normas y mejores prácticas para la escritura académica.", "PDF", null));
		resources.add(createResource("Tutorial: Búsqueda en Bases de Datos", "video", "PDG1",
				"Aprende a buscar literatura académica de manera efectiva.", null, "15:30"));
		resources.add(createResource("Ejemplo de Marco Teórico", "document", "PDG1",
				"Ejemplo completo de un marco teórico bien estructurado.", "PDF", null));
		resources.add(createResource("Plantilla de Matriz Metodológica", "document", "PDG1",
				"Herramienta para organizar tu diseño metodológico.", "XLSX", null));
		resources.add(createResource("Diagramas y Modelos Conceptuales", "image", "General",
				"Ejemplos de diagramas para representar conceptos e ideas.", "PNG", null));
		resources.add(createResource("Tutorial: Análisis de Datos Cualitativos", "video", "PDG2",
				"Técnicas para analizar datos cualitativos en tu investigación.", null, "22:15"));
		resources.add(createResource("Formato de Presentación Final", "document", "PDG2",
				"Plantilla para la presentación de tu proyecto de grado.", "PPTX", null));
		return resources;
	}

	private static GuideResponse createIaGuide() {
		var guide = new GuideResponse();
		guide.setId("ia");
		guide.setName("Proyectos de Inteligencia Artificial");
		guide.setDescription("Guía metodológica para proyectos de grado enfocados en IA y Machine Learning");
		guide.setIcon("Brain");

		var phases = new ArrayList<PhaseResponse>();
		phases.add(createPhase("fase1", "Definición del Problema de IA",
				"Identifica un problema que pueda ser abordado mediante técnicas de inteligencia artificial.",
				"2-3 semanas",
				List.of("Identificar un problema real susceptible de ser resuelto con IA",
						"Evaluar la viabilidad técnica del proyecto",
						"Definir el alcance y limitaciones del sistema de IA",
						"Establecer métricas de éxito y validación"),
				List.of("Documento de definición del problema (3-4 páginas)",
						"Análisis de viabilidad técnica",
						"Descripción del dataset objetivo",
						"Métricas de evaluación propuestas"),
				List.of(
						createStep("1. Identificación del Problema",
								"Explora casos de uso donde la IA pueda aportar valor significativo.",
								List.of(
										createStepResource("video", "Casos de uso de IA en la industria", "15:30", null),
										createStepResource("document", "Guía de identificación de problemas para IA.pdf", null, null))),
						createStep("2. Análisis de Viabilidad",
								"Evalúa disponibilidad de datos, recursos computacionales y complejidad técnica.",
								List.of(
										createStepResource("video", "Evaluación de viabilidad técnica en IA", "12:20", null),
										createStepResource("document", "Checklist de viabilidad para proyectos de IA.xlsx", null, null))),
						createStep("3. Definición de Dataset",
								"Identifica fuentes de datos, calidad y cantidad requerida para entrenar modelos.",
								List.of(
										createStepResource("video", "Fuentes de datos para proyectos de IA", "18:45", null),
										createStepResource("document", "Plantilla de análisis de datasets.docx", null, null))))));

		phases.add(createPhase("fase2", "Marco Teórico y Estado del Arte en IA",
				"Fundamenta tu proyecto con teorías de IA, algoritmos relevantes y revisión de trabajos similares.",
				"3-4 semanas",
				List.of("Revisar literatura sobre técnicas de IA aplicables",
						"Analizar trabajos previos y modelos existentes",
						"Identificar algoritmos y arquitecturas relevantes",
						"Justificar la selección de técnicas de IA"),
				List.of("Marco teórico completo (10-15 páginas)",
						"Estado del arte documentado",
						"Justificación de técnicas seleccionadas",
						"Referencias bibliográficas (APA)"),
				List.of(
						createStep("1. Revisión de Algoritmos",
								"Estudia algoritmos de ML/DL relevantes para tu problema.",
								List.of(
										createStepResource("video", "Panorama de algoritmos de Machine Learning", "22:10", null),
										createStepResource("document", "Comparativa de algoritmos de IA.pdf", null, null))),
						createStep("2. Estado del Arte",
								"Analiza papers y proyectos similares en la literatura académica.",
								List.of(
										createStepResource("video", "Cómo hacer revisión de literatura en IA", "16:30", null),
										createStepResource("document", "Plantilla de análisis de papers.xlsx", null, null))),
						createStep("3. Selección de Arquitectura",
								"Justifica la elección de arquitectura de red o modelo de IA.",
								List.of(
										createStepResource("video", "Arquitecturas de redes neuronales", "20:15", null),
										createStepResource("document", "Guía de selección de arquitecturas.pdf", null, null))))));

		phases.add(createPhase("fase3", "Diseño Metodológico",
				"Define el pipeline de procesamiento, arquitectura del modelo y estrategia de entrenamiento.",
				"3-4 semanas",
				List.of("Diseñar el pipeline de datos end-to-end",
						"Definir arquitectura del modelo de IA",
						"Establecer estrategia de entrenamiento y validación",
						"Planificar experimentos y métricas"),
				List.of("Diseño completo del pipeline (diagrama)",
						"Especificación de preprocesamiento",
						"Plan de entrenamiento y validación",
						"Definición de métricas de éxito"),
				List.of(
						createStep("1. Pipeline de Datos",
								"Diseña el flujo completo desde datos crudos hasta modelo entrenado.",
								List.of(
										createStepResource("video", "Diseño de pipelines de ML", "19:40", null),
										createStepResource("document", "Plantilla de arquitectura de datos.pdf", null, null))),
						createStep("2. Preprocesamiento",
								"Define técnicas de limpieza, normalización y augmentación de datos.",
								List.of(
										createStepResource("video", "Técnicas de preprocesamiento", "14:25", null),
										createStepResource("document", "Checklist de preprocesamiento.xlsx", null, null))),
						createStep("3. Estrategia de Validación",
								"Establece división de datos, validación cruzada y métricas de evaluación.",
								List.of(
										createStepResource("video", "Validación de modelos de IA", "17:50", null),
										createStepResource("document", "Guía de validación de modelos.pdf", null, null))))));

		phases.add(createPhase("fase4", "Implementación del Sistema",
				"Desarrolla el modelo, entrena, optimiza hiperparámetros y documenta el código.",
				"5-6 semanas",
				List.of("Implementar el modelo de IA seleccionado",
						"Entrenar y validar el modelo",
						"Optimizar hiperparámetros",
						"Documentar código y experimentos"),
				List.of("Código fuente documentado (GitHub)",
						"Modelo entrenado y guardado",
						"Reporte de experimentos y métricas",
						"Notebooks de análisis"),
				List.of(
						createStep("1. Implementación del Modelo",
								"Codifica la arquitectura usando frameworks como TensorFlow o PyTorch.",
								List.of(
										createStepResource("video", "Implementación de modelos en PyTorch", "25:30", null),
										createStepResource("document", "Best practices de código en ML.pdf", null, null))),
						createStep("2. Entrenamiento",
								"Entrena el modelo, monitorea métricas y ajusta hiperparámetros.",
								List.of(
										createStepResource("video", "Estrategias de entrenamiento", "21:15", null),
										createStepResource("document", "Guía de optimización de hiperparámetros.pdf", null, null))),
						createStep("3. Validación y Testing",
								"Evalúa el modelo con datos de test y valida su rendimiento.",
								List.of(
										createStepResource("video", "Testing de modelos de IA", "16:40", null),
										createStepResource("document", "Checklist de validación.xlsx", null, null))))));

		phases.add(createPhase("fase5", "Análisis de Resultados",
				"Analiza el rendimiento del modelo, interpreta resultados y compara con el estado del arte.",
				"3-4 semanas",
				List.of("Analizar métricas de rendimiento",
						"Interpretar predicciones del modelo",
						"Comparar con baseline y estado del arte",
						"Identificar fortalezas y limitaciones"),
				List.of("Análisis completo de resultados (8-10 páginas)",
						"Visualizaciones y gráficas",
						"Comparación con benchmarks",
						"Análisis de casos de éxito y fallo"),
				List.of(
						createStep("1. Análisis de Métricas",
								"Evalúa accuracy, precision, recall, F1-score y métricas específicas.",
								List.of(
										createStepResource("video", "Interpretación de métricas en ML", "18:20", null),
										createStepResource("document", "Guía de análisis de métricas.pdf", null, null))),
						createStep("2. Visualización de Resultados",
								"Crea gráficas, matrices de confusión y visualizaciones interpretables.",
								List.of(
										createStepResource("video", "Visualización de resultados de IA", "14:55", null),
										createStepResource("document", "Templates de visualización.pdf", null, null))),
						createStep("3. Comparación con Estado del Arte",
								"Compara tu modelo con trabajos previos y establece contribuciones.",
								List.of(
										createStepResource("video", "Benchmarking de modelos", "12:30", null),
										createStepResource("document", "Tabla comparativa.xlsx", null, null))))));

		phases.add(createPhase("fase6", "Conclusiones y Trabajo Futuro",
				"Sintetiza hallazgos, reflexiona sobre contribuciones y propone mejoras futuras.",
				"2-3 semanas",
				List.of("Sintetizar principales hallazgos",
						"Evaluar cumplimiento de objetivos",
						"Discutir limitaciones del modelo",
						"Proponer trabajo futuro y mejoras"),
				List.of("Conclusiones (3-5 páginas)",
						"Evaluación de cumplimiento de objetivos",
						"Discusión de limitaciones",
						"Propuestas de trabajo futuro"),
				List.of(
						createStep("1. Síntesis de Hallazgos",
								"Resume los principales resultados y aprendizajes del proyecto.",
								List.of(
										createStepResource("video", "Cómo escribir conclusiones efectivas", "10:45", null),
										createStepResource("document", "Guía de conclusiones.pdf", null, null))),
						createStep("2. Evaluación de Objetivos",
								"Verifica el cumplimiento de objetivos planteados inicialmente.",
								List.of(
										createStepResource("video", "Evaluación de proyectos de IA", "11:20", null),
										createStepResource("document", "Checklist de objetivos.xlsx", null, null))),
						createStep("3. Trabajo Futuro",
								"Propón mejoras, optimizaciones y nuevas líneas de investigación.",
								List.of(
										createStepResource("video", "Identificación de trabajo futuro", "09:30", null),
										createStepResource("document", "Plantilla de trabajo futuro.docx", null, null))))));

		guide.setPhases(phases);
		return guide;
	}

	private static GuideResponse createIotGuide() {
		var guide = new GuideResponse();
		guide.setId("iot");
		guide.setName("Proyectos de Internet de las Cosas (IoT)");
		guide.setDescription("Guía metodológica para proyectos de grado enfocados en IoT y sistemas embebidos");
		guide.setIcon("Cpu");

		var phases = new ArrayList<PhaseResponse>();
		phases.add(createPhase("fase1", "Definición del Sistema IoT",
				"Identifica el problema y define los requisitos del sistema IoT a desarrollar.",
				"2-3 semanas",
				List.of("Identificar un problema que requiera solución IoT",
						"Definir requisitos funcionales y no funcionales",
						"Evaluar viabilidad técnica y económica",
						"Establecer arquitectura preliminar del sistema"),
				List.of("Documento de definición del sistema (3-4 páginas)",
						"Lista de requisitos funcionales y no funcionales",
						"Diagrama de arquitectura preliminar",
						"Análisis de viabilidad"),
				List.of(
						createStep("1. Identificación del Problema",
								"Explora escenarios donde IoT pueda aportar monitoreo, automatización o control.",
								List.of(
										createStepResource("video", "Aplicaciones de IoT en diferentes industrias", "14:20", null),
										createStepResource("document", "Guía de identificación de proyectos IoT.pdf", null, null))),
						createStep("2. Requisitos del Sistema",
								"Define sensores, actuadores, conectividad y procesamiento necesarios.",
								List.of(
										createStepResource("video", "Análisis de requisitos en IoT", "16:45", null),
										createStepResource("document", "Plantilla de requisitos IoT.docx", null, null))),
						createStep("3. Arquitectura Preliminar",
								"Diseña la arquitectura de capas: sensores, edge, comunicación y cloud.",
								List.of(
										createStepResource("video", "Arquitecturas de sistemas IoT", "19:30", null),
										createStepResource("document", "Diagramas de arquitectura IoT.pdf", null, null))))));

		phases.add(createPhase("fase2", "Marco Teórico y Tecnologías IoT",
				"Fundamenta tu proyecto con protocolos, plataformas y tecnologías IoT relevantes.",
				"3-4 semanas",
				List.of("Revisar protocolos de comunicación IoT",
						"Estudiar plataformas y frameworks disponibles",
						"Analizar proyectos IoT similares",
						"Justificar selección de tecnologías"),
				List.of("Marco teórico completo (10-15 páginas)",
						"Justificación de tecnologías seleccionadas",
						"Estado del arte de proyectos similares",
						"Referencias bibliográficas (APA)"),
				List.of(
						createStep("1. Protocolos de Comunicación",
								"Estudia MQTT, CoAP, LoRaWAN, Zigbee, BLE según tu aplicación.",
								List.of(
										createStepResource("video", "Protocolos de comunicación en IoT", "20:15", null),
										createStepResource("document", "Comparativa de protocolos IoT.pdf", null, null))),
						createStep("2. Plataformas IoT",
								"Analiza AWS IoT, Azure IoT, Google Cloud IoT, ThingSpeak, etc.",
								List.of(
										createStepResource("video", "Plataformas cloud para IoT", "18:40", null),
										createStepResource("document", "Comparativa de plataformas.xlsx", null, null))),
						createStep("3. Hardware y Sensores",
								"Revisa microcontroladores, sensores y actuadores disponibles.",
								List.of(
										createStepResource("video", "Selección de hardware para IoT", "22:10", null),
										createStepResource("document", "Catálogo de sensores y actuadores.pdf", null, null))))));

		phases.add(createPhase("fase3", "Diseño del Sistema IoT",
				"Define la arquitectura detallada, diseño de hardware, comunicaciones y software.",
				"3-4 semanas",
				List.of("Diseñar arquitectura completa del sistema",
						"Definir esquemáticos de hardware",
						"Especificar flujos de datos y comunicación",
						"Diseñar interfaces de usuario"),
				List.of("Diagrama de arquitectura completa",
						"Esquemáticos de hardware",
						"Especificación de comunicaciones",
						"Diseño de interfaces (mockups)"),
				List.of(
						createStep("1. Diseño de Hardware",
								"Crea esquemáticos, selecciona componentes y diseña PCB si es necesario.",
								List.of(
										createStepResource("video", "Diseño de circuitos para IoT", "21:30", null),
										createStepResource("document", "Guía de diseño de hardware.pdf", null, null))),
						createStep("2. Diseño de Comunicación",
								"Define topología de red, seguridad y manejo de datos.",
								List.of(
										createStepResource("video", "Diseño de redes IoT", "17:45", null),
										createStepResource("document", "Plantilla de arquitectura de red.pdf", null, null))),
						createStep("3. Diseño de Software",
								"Especifica firmware, lógica de edge, backend y aplicaciones.",
								List.of(
										createStepResource("video", "Arquitectura de software en IoT", "19:20", null),
										createStepResource("document", "Diagramas de flujo de software.pdf", null, null))))));

		phases.add(createPhase("fase4", "Implementación del Sistema",
				"Desarrolla el hardware, firmware, backend y aplicaciones del sistema IoT.",
				"5-6 semanas",
				List.of("Implementar hardware y conexiones",
						"Programar firmware para dispositivos",
						"Desarrollar backend y APIs",
						"Crear aplicaciones de visualización"),
				List.of("Prototipo funcional del hardware",
						"Código fuente del firmware (GitHub)",
						"Backend y APIs documentadas",
						"Aplicación de visualización"),
				List.of(
						createStep("1. Construcción de Hardware",
								"Ensambla componentes, conecta sensores y verifica funcionamiento.",
								List.of(
										createStepResource("video", "Ensamblaje de prototipos IoT", "24:10", null),
										createStepResource("document", "Checklist de construcción.xlsx", null, null))),
						createStep("2. Programación de Firmware",
								"Desarrolla código para microcontroladores usando Arduino, ESP32, etc.",
								List.of(
										createStepResource("video", "Programación de ESP32 para IoT", "28:35", null),
										createStepResource("document", "Templates de firmware.pdf", null, null))),
						createStep("3. Backend y Visualización",
								"Implementa servidor, base de datos y dashboards de monitoreo.",
								List.of(
										createStepResource("video", "Desarrollo de backend para IoT", "26:20", null),
										createStepResource("document", "Guía de desarrollo de dashboards.pdf", null, null))))));

		phases.add(createPhase("fase5", "Pruebas y Validación",
				"Realiza pruebas funcionales, de rendimiento, seguridad y validación del sistema.",
				"3-4 semanas",
				List.of("Ejecutar pruebas funcionales completas",
						"Medir rendimiento y latencia",
						"Validar seguridad del sistema",
						"Documentar resultados de pruebas"),
				List.of("Reporte de pruebas funcionales",
						"Análisis de rendimiento y métricas",
						"Evaluación de seguridad",
						"Videos demostrativos del sistema"),
				List.of(
						createStep("1. Pruebas Funcionales",
								"Verifica que cada componente funcione según especificaciones.",
								List.of(
										createStepResource("video", "Testing de sistemas IoT", "19:45", null),
										createStepResource("document", "Plan de pruebas funcionales.xlsx", null, null))),
						createStep("2. Pruebas de Rendimiento",
								"Mide latencia, throughput, consumo energético y confiabilidad.",
								List.of(
										createStepResource("video", "Medición de rendimiento en IoT", "17:30", null),
										createStepResource("document", "Métricas de rendimiento.pdf", null, null))),
						createStep("3. Validación de Seguridad",
								"Evalúa vulnerabilidades, encriptación y autenticación.",
								List.of(
										createStepResource("video", "Seguridad en sistemas IoT", "21:15", null),
										createStepResource("document", "Checklist de seguridad IoT.pdf", null, null))))));

		phases.add(createPhase("fase6", "Conclusiones y Mejoras Futuras",
				"Sintetiza resultados, reflexiona sobre el desarrollo y propone mejoras.",
				"2-3 semanas",
				List.of("Sintetizar hallazgos del proyecto",
						"Evaluar cumplimiento de objetivos",
						"Discutir desafíos y soluciones",
						"Proponer escalabilidad y mejoras"),
				List.of("Conclusiones (3-5 páginas)",
						"Documentación de desafíos",
						"Propuestas de escalabilidad",
						"Recomendaciones de mejoras"),
				List.of(
						createStep("1. Análisis de Resultados",
								"Resume el funcionamiento del sistema y lecciones aprendidas.",
								List.of(
										createStepResource("video", "Análisis de proyectos IoT", "11:40", null),
										createStepResource("document", "Guía de análisis final.pdf", null, null))),
						createStep("2. Desafíos y Soluciones",
								"Documenta problemas encontrados y cómo fueron resueltos.",
								List.of(
										createStepResource("video", "Lecciones aprendidas en IoT", "13:25", null),
										createStepResource("document", "Template de lecciones aprendidas.docx", null, null))),
						createStep("3. Escalabilidad y Mejoras",
								"Propón cómo escalar el sistema y mejoras tecnológicas futuras.",
								List.of(
										createStepResource("video", "Escalabilidad en sistemas IoT", "15:10", null),
										createStepResource("document", "Guía de escalabilidad.pdf", null, null))))));

		guide.setPhases(phases);
		return guide;
	}

	private static PhaseResponse createPhase(String id, String title, String description, String duration,
			List<String> objectives, List<String> deliverables, List<PhaseStepResponse> steps) {
		var phase = new PhaseResponse();
		phase.setId(id);
		phase.setTitle(title);
		phase.setDescription(description);
		phase.setDuration(duration);
		phase.setObjectives(objectives);
		phase.setDeliverables(deliverables);
		phase.setSteps(steps);
		return phase;
	}

	private static PhaseStepResponse createStep(String title, String description, List<StepResourceResponse> resources) {
		var step = new PhaseStepResponse();
		step.setTitle(title);
		step.setDescription(description);
		step.setResources(resources);
		return step;
	}

	private static StepResourceResponse createStepResource(String type, String title, String duration, String format) {
		var resource = new StepResourceResponse();
		resource.setType(type);
		resource.setTitle(title);
		resource.setDuration(duration);
		resource.setFormat(format);
		return resource;
	}

	private static FaqResponse createFaq(String question, String answer) {
		var faq = new FaqResponse();
		faq.setQuestion(question);
		faq.setAnswer(answer);
		return faq;
	}

	private static SupportResourceResponse createResource(String title, String type, String category,
			String description, String format, String duration) {
		var resource = new SupportResourceResponse();
		resource.setTitle(title);
		resource.setType(type);
		resource.setCategory(category);
		resource.setDescription(description);
		resource.setFormat(format);
		resource.setDuration(duration);
		return resource;
	}
}

