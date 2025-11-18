package com.icesi.devopssandboxbackend.config;

import java.util.ArrayList;
import java.util.List;

import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.icesi.devopssandboxbackend.domain.enums.ResourceType;
import com.icesi.devopssandboxbackend.domain.model.content.FaqEntry;
import com.icesi.devopssandboxbackend.domain.model.content.SupportResource;
import com.icesi.devopssandboxbackend.domain.model.guide.Guide;
import com.icesi.devopssandboxbackend.domain.model.guide.Phase;
import com.icesi.devopssandboxbackend.domain.model.guide.PhaseStep;
import com.icesi.devopssandboxbackend.domain.model.guide.StepResource;
import com.icesi.devopssandboxbackend.domain.repository.content.FaqEntryRepository;
import com.icesi.devopssandboxbackend.domain.repository.content.SupportResourceRepository;
import com.icesi.devopssandboxbackend.domain.repository.guide.GuideRepository;

@Component
public class MentorGuideDataSeeder implements CommandLineRunner {

	private final GuideRepository guideRepository;
	private final FaqEntryRepository faqEntryRepository;
	private final SupportResourceRepository supportResourceRepository;

	public MentorGuideDataSeeder(
			GuideRepository guideRepository,
			FaqEntryRepository faqEntryRepository,
			SupportResourceRepository supportResourceRepository) {
		this.guideRepository = guideRepository;
		this.faqEntryRepository = faqEntryRepository;
		this.supportResourceRepository = supportResourceRepository;
	}

	@Override
	@Transactional
	public void run(String... args) {
		try {
			if (guideRepository.count() == 0) {
				guideRepository.saveAll(buildGuides());
			}

			if (faqEntryRepository.count() == 0) {
				faqEntryRepository.saveAll(buildFaqs());
			}

			if (supportResourceRepository.count() == 0) {
				supportResourceRepository.saveAll(buildSupportResources());
			}
		} catch (Exception e) {
			// Si no hay BD disponible, los servicios usarán datos mockeados
			// No es necesario hacer nada aquí
		}
	}

	private List<Guide> buildGuides() {
		var guides = new ArrayList<Guide>();
		guides.add(buildIaGuide());
		guides.add(buildIotGuide());
		return guides;
	}

	private Guide buildIaGuide() {
		var guide = createGuide(
				"ia",
				"Proyectos de Inteligencia Artificial",
				"Guía metodológica para proyectos de grado enfocados en IA y Machine Learning",
				"Brain");

		buildPhase(guide, 1, "fase1",
				"Definición del Problema de IA",
				"Identifica un problema que pueda ser abordado mediante técnicas de inteligencia artificial.",
				"2-3 semanas",
				List.of(
						"Identificar un problema real susceptible de ser resuelto con IA",
						"Evaluar la viabilidad técnica del proyecto",
						"Definir el alcance y limitaciones del sistema de IA",
						"Establecer métricas de éxito y validación"),
				List.of(
						"Documento de definición del problema (3-4 páginas)",
						"Análisis de viabilidad técnica",
						"Descripción del dataset objetivo",
						"Métricas de evaluación propuestas"),
				List.of(
						createStep("1. Identificación del Problema",
								"Explora casos de uso donde la IA pueda aportar valor significativo.",
								List.of(
										createStepResource(ResourceType.VIDEO, "Casos de uso de IA en la industria", "15:30", null),
										createStepResource(ResourceType.DOCUMENT, "Guía de identificación de problemas para IA.pdf", null,
												null))),
						createStep("2. Análisis de Viabilidad",
								"Evalúa disponibilidad de datos, recursos computacionales y complejidad técnica.",
								List.of(
										createStepResource(ResourceType.VIDEO, "Evaluación de viabilidad técnica en IA", "12:20", null),
										createStepResource(ResourceType.DOCUMENT, "Checklist de viabilidad para proyectos de IA.xlsx", null,
												null))),
						createStep("3. Definición de Dataset",
								"Identifica fuentes de datos, calidad y cantidad requerida para entrenar modelos.",
								List.of(
										createStepResource(ResourceType.VIDEO, "Fuentes de datos para proyectos de IA", "18:45", null),
										createStepResource(ResourceType.DOCUMENT, "Plantilla de análisis de datasets.docx", null, null)))));

		buildPhase(guide, 2, "fase2",
				"Marco Teórico y Estado del Arte en IA",
				"Fundamenta tu proyecto con teorías de IA, algoritmos relevantes y revisión de trabajos similares.",
				"3-4 semanas",
				List.of(
						"Revisar literatura sobre técnicas de IA aplicables",
						"Analizar trabajos previos y modelos existentes",
						"Identificar algoritmos y arquitecturas relevantes",
						"Justificar la selección de técnicas de IA"),
				List.of(
						"Marco teórico completo (10-15 páginas)",
						"Estado del arte documentado",
						"Justificación de técnicas seleccionadas",
						"Referencias bibliográficas (APA)"),
				List.of(
						createStep("1. Revisión de Algoritmos",
								"Estudia algoritmos de ML/DL relevantes para tu problema.",
								List.of(
										createStepResource(ResourceType.VIDEO, "Panorama de algoritmos de Machine Learning", "22:10", null),
										createStepResource(ResourceType.DOCUMENT, "Comparativa de algoritmos de IA.pdf", null, null))),
						createStep("2. Estado del Arte",
								"Analiza papers y proyectos similares en la literatura académica.",
								List.of(
										createStepResource(ResourceType.VIDEO, "Cómo hacer revisión de literatura en IA", "16:30", null),
										createStepResource(ResourceType.DOCUMENT, "Plantilla de análisis de papers.xlsx", null, null))),
						createStep("3. Selección de Arquitectura",
								"Justifica la elección de arquitectura de red o modelo de IA.",
								List.of(
										createStepResource(ResourceType.VIDEO, "Arquitecturas de redes neuronales", "20:15", null),
										createStepResource(ResourceType.DOCUMENT, "Guía de selección de arquitecturas.pdf", null, null)))));

		buildPhase(guide, 3, "fase3",
				"Diseño Metodológico",
				"Define el pipeline de procesamiento, arquitectura del modelo y estrategia de entrenamiento.",
				"3-4 semanas",
				List.of(
						"Diseñar el pipeline de datos end-to-end",
						"Definir arquitectura del modelo de IA",
						"Establecer estrategia de entrenamiento y validación",
						"Planificar experimentos y métricas"),
				List.of(
						"Diseño completo del pipeline (diagrama)",
						"Especificación de preprocesamiento",
						"Plan de entrenamiento y validación",
						"Definición de métricas de éxito"),
				List.of(
						createStep("1. Pipeline de Datos",
								"Diseña el flujo completo desde datos crudos hasta modelo entrenado.",
								List.of(
										createStepResource(ResourceType.VIDEO, "Diseño de pipelines de ML", "19:40", null),
										createStepResource(ResourceType.DOCUMENT, "Plantilla de arquitectura de datos.pdf", null, null))),
						createStep("2. Preprocesamiento",
								"Define técnicas de limpieza, normalización y augmentación de datos.",
								List.of(
										createStepResource(ResourceType.VIDEO, "Técnicas de preprocesamiento", "14:25", null),
										createStepResource(ResourceType.DOCUMENT, "Checklist de preprocesamiento.xlsx", null, null))),
						createStep("3. Estrategia de Validación",
								"Establece división de datos, validación cruzada y métricas de evaluación.",
								List.of(
										createStepResource(ResourceType.VIDEO, "Validación de modelos de IA", "17:50", null),
										createStepResource(ResourceType.DOCUMENT, "Guía de validación de modelos.pdf", null, null)))));

		buildPhase(guide, 4, "fase4",
				"Implementación del Sistema",
				"Desarrolla el modelo, entrena, optimiza hiperparámetros y documenta el código.",
				"5-6 semanas",
				List.of(
						"Implementar el modelo de IA seleccionado",
						"Entrenar y validar el modelo",
						"Optimizar hiperparámetros",
						"Documentar código y experimentos"),
				List.of(
						"Código fuente documentado (GitHub)",
						"Modelo entrenado y guardado",
						"Reporte de experimentos y métricas",
						"Notebooks de análisis"),
				List.of(
						createStep("1. Implementación del Modelo",
								"Codifica la arquitectura usando frameworks como TensorFlow o PyTorch.",
								List.of(
										createStepResource(ResourceType.VIDEO, "Implementación de modelos en PyTorch", "25:30", null),
										createStepResource(ResourceType.DOCUMENT, "Best practices de código en ML.pdf", null, null))),
						createStep("2. Entrenamiento",
								"Entrena el modelo, monitorea métricas y ajusta hiperparámetros.",
								List.of(
										createStepResource(ResourceType.VIDEO, "Estrategias de entrenamiento", "21:15", null),
										createStepResource(ResourceType.DOCUMENT, "Guía de optimización de hiperparámetros.pdf", null, null))),
						createStep("3. Validación y Testing",
								"Evalúa el modelo con datos de test y valida su rendimiento.",
								List.of(
										createStepResource(ResourceType.VIDEO, "Testing de modelos de IA", "16:40", null),
										createStepResource(ResourceType.DOCUMENT, "Checklist de validación.xlsx", null, null)))));

		buildPhase(guide, 5, "fase5",
				"Análisis de Resultados",
				"Analiza el rendimiento del modelo, interpreta resultados y compara con el estado del arte.",
				"3-4 semanas",
				List.of(
						"Analizar métricas de rendimiento",
						"Interpretar predicciones del modelo",
						"Comparar con baseline y estado del arte",
						"Identificar fortalezas y limitaciones"),
				List.of(
						"Análisis completo de resultados (8-10 páginas)",
						"Visualizaciones y gráficas",
						"Comparación con benchmarks",
						"Análisis de casos de éxito y fallo"),
				List.of(
						createStep("1. Análisis de Métricas",
								"Evalúa accuracy, precision, recall, F1-score y métricas específicas.",
								List.of(
										createStepResource(ResourceType.VIDEO, "Interpretación de métricas en ML", "18:20", null),
										createStepResource(ResourceType.DOCUMENT, "Guía de análisis de métricas.pdf", null, null))),
						createStep("2. Visualización de Resultados",
								"Crea gráficas, matrices de confusión y visualizaciones interpretables.",
								List.of(
										createStepResource(ResourceType.VIDEO, "Visualización de resultados de IA", "14:55", null),
										createStepResource(ResourceType.DOCUMENT, "Templates de visualización.pdf", null, null))),
						createStep("3. Comparación con Estado del Arte",
								"Compara tu modelo con trabajos previos y establece contribuciones.",
								List.of(
										createStepResource(ResourceType.VIDEO, "Benchmarking de modelos", "12:30", null),
										createStepResource(ResourceType.DOCUMENT, "Tabla comparativa.xlsx", null, null)))));

		buildPhase(guide, 6, "fase6",
				"Conclusiones y Trabajo Futuro",
				"Sintetiza hallazgos, reflexiona sobre contribuciones y propone mejoras futuras.",
				"2-3 semanas",
				List.of(
						"Sintetizar principales hallazgos",
						"Evaluar cumplimiento de objetivos",
						"Discutir limitaciones del modelo",
						"Proponer trabajo futuro y mejoras"),
				List.of(
						"Conclusiones (3-5 páginas)",
						"Evaluación de cumplimiento de objetivos",
						"Discusión de limitaciones",
						"Propuestas de trabajo futuro"),
				List.of(
						createStep("1. Síntesis de Hallazgos",
								"Resume los principales resultados y aprendizajes del proyecto.",
								List.of(
										createStepResource(ResourceType.VIDEO, "Cómo escribir conclusiones efectivas", "10:45", null),
										createStepResource(ResourceType.DOCUMENT, "Guía de conclusiones.pdf", null, null))),
						createStep("2. Evaluación de Objetivos",
								"Verifica el cumplimiento de objetivos planteados inicialmente.",
								List.of(
										createStepResource(ResourceType.VIDEO, "Evaluación de proyectos de IA", "11:20", null),
										createStepResource(ResourceType.DOCUMENT, "Checklist de objetivos.xlsx", null, null))),
						createStep("3. Trabajo Futuro",
								"Propón mejoras, optimizaciones y nuevas líneas de investigación.",
								List.of(
										createStepResource(ResourceType.VIDEO, "Identificación de trabajo futuro", "09:30", null),
										createStepResource(ResourceType.DOCUMENT, "Plantilla de trabajo futuro.docx", null, null)))));

		return guide;
	}

	private Guide buildIotGuide() {
		var guide = createGuide(
				"iot",
				"Proyectos de Internet de las Cosas (IoT)",
				"Guía metodológica para proyectos de grado enfocados en IoT y sistemas embebidos",
				"Cpu");

		buildPhase(guide, 1, "fase1",
				"Definición del Sistema IoT",
				"Identifica el problema y define los requisitos del sistema IoT a desarrollar.",
				"2-3 semanas",
				List.of(
						"Identificar un problema que requiera solución IoT",
						"Definir requisitos funcionales y no funcionales",
						"Evaluar viabilidad técnica y económica",
						"Establecer arquitectura preliminar del sistema"),
				List.of(
						"Documento de definición del sistema (3-4 páginas)",
						"Lista de requisitos funcionales y no funcionales",
						"Diagrama de arquitectura preliminar",
						"Análisis de viabilidad"),
				List.of(
						createStep("1. Identificación del Problema",
								"Explora escenarios donde IoT pueda aportar monitoreo, automatización o control.",
								List.of(
										createStepResource(ResourceType.VIDEO, "Aplicaciones de IoT en diferentes industrias", "14:20", null),
										createStepResource(ResourceType.DOCUMENT, "Guía de identificación de proyectos IoT.pdf", null, null))),
						createStep("2. Requisitos del Sistema",
								"Define sensores, actuadores, conectividad y procesamiento necesarios.",
								List.of(
										createStepResource(ResourceType.VIDEO, "Análisis de requisitos en IoT", "16:45", null),
										createStepResource(ResourceType.DOCUMENT, "Plantilla de requisitos IoT.docx", null, null))),
						createStep("3. Arquitectura Preliminar",
								"Diseña la arquitectura de capas: sensores, edge, comunicación y cloud.",
								List.of(
										createStepResource(ResourceType.VIDEO, "Arquitecturas de sistemas IoT", "19:30", null),
										createStepResource(ResourceType.DOCUMENT, "Diagramas de arquitectura IoT.pdf", null, null)))));

		buildPhase(guide, 2, "fase2",
				"Marco Teórico y Tecnologías IoT",
				"Fundamenta tu proyecto con protocolos, plataformas y tecnologías IoT relevantes.",
				"3-4 semanas",
				List.of(
						"Revisar protocolos de comunicación IoT",
						"Estudiar plataformas y frameworks disponibles",
						"Analizar proyectos IoT similares",
						"Justificar selección de tecnologías"),
				List.of(
						"Marco teórico completo (10-15 páginas)",
						"Justificación de tecnologías seleccionadas",
						"Estado del arte de proyectos similares",
						"Referencias bibliográficas (APA)"),
				List.of(
						createStep("1. Protocolos de Comunicación",
								"Estudia MQTT, CoAP, LoRaWAN, Zigbee, BLE según tu aplicación.",
								List.of(
										createStepResource(ResourceType.VIDEO, "Protocolos de comunicación en IoT", "20:15", null),
										createStepResource(ResourceType.DOCUMENT, "Comparativa de protocolos IoT.pdf", null, null))),
						createStep("2. Plataformas IoT",
								"Analiza AWS IoT, Azure IoT, Google Cloud IoT, ThingSpeak, etc.",
								List.of(
										createStepResource(ResourceType.VIDEO, "Plataformas cloud para IoT", "18:40", null),
										createStepResource(ResourceType.DOCUMENT, "Comparativa de plataformas.xlsx", null, null))),
						createStep("3. Hardware y Sensores",
								"Revisa microcontroladores, sensores y actuadores disponibles.",
								List.of(
										createStepResource(ResourceType.VIDEO, "Selección de hardware para IoT", "22:10", null),
										createStepResource(ResourceType.DOCUMENT, "Catálogo de sensores y actuadores.pdf", null, null)))));

		buildPhase(guide, 3, "fase3",
				"Diseño del Sistema IoT",
				"Define la arquitectura detallada, diseño de hardware, comunicaciones y software.",
				"3-4 semanas",
				List.of(
						"Diseñar arquitectura completa del sistema",
						"Definir esquemáticos de hardware",
						"Especificar flujos de datos y comunicación",
						"Diseñar interfaces de usuario"),
				List.of(
						"Diagrama de arquitectura completa",
						"Esquemáticos de hardware",
						"Especificación de comunicaciones",
						"Diseño de interfaces (mockups)"),
				List.of(
						createStep("1. Diseño de Hardware",
								"Crea esquemáticos, selecciona componentes y diseña PCB si es necesario.",
								List.of(
										createStepResource(ResourceType.VIDEO, "Diseño de circuitos para IoT", "21:30", null),
										createStepResource(ResourceType.DOCUMENT, "Guía de diseño de hardware.pdf", null, null))),
						createStep("2. Diseño de Comunicación",
								"Define topología de red, seguridad y manejo de datos.",
								List.of(
										createStepResource(ResourceType.VIDEO, "Diseño de redes IoT", "17:45", null),
										createStepResource(ResourceType.DOCUMENT, "Plantilla de arquitectura de red.pdf", null, null))),
						createStep("3. Diseño de Software",
								"Especifica firmware, lógica de edge, backend y aplicaciones.",
								List.of(
										createStepResource(ResourceType.VIDEO, "Arquitectura de software en IoT", "19:20", null),
										createStepResource(ResourceType.DOCUMENT, "Diagramas de flujo de software.pdf", null, null)))));

		buildPhase(guide, 4, "fase4",
				"Implementación del Sistema",
				"Desarrolla el hardware, firmware, backend y aplicaciones del sistema IoT.",
				"5-6 semanas",
				List.of(
						"Implementar hardware y conexiones",
						"Programar firmware para dispositivos",
						"Desarrollar backend y APIs",
						"Crear aplicaciones de visualización"),
				List.of(
						"Prototipo funcional del hardware",
						"Código fuente del firmware (GitHub)",
						"Backend y APIs documentadas",
						"Aplicación de visualización"),
				List.of(
						createStep("1. Construcción de Hardware",
								"Ensambla componentes, conecta sensores y verifica funcionamiento.",
								List.of(
										createStepResource(ResourceType.VIDEO, "Ensamblaje de prototipos IoT", "24:10", null),
										createStepResource(ResourceType.DOCUMENT, "Checklist de construcción.xlsx", null, null))),
						createStep("2. Programación de Firmware",
								"Desarrolla código para microcontroladores usando Arduino, ESP32, etc.",
								List.of(
										createStepResource(ResourceType.VIDEO, "Programación de ESP32 para IoT", "28:35", null),
										createStepResource(ResourceType.DOCUMENT, "Templates de firmware.pdf", null, null))),
						createStep("3. Backend y Visualización",
								"Implementa servidor, base de datos y dashboards de monitoreo.",
								List.of(
										createStepResource(ResourceType.VIDEO, "Desarrollo de backend para IoT", "26:20", null),
										createStepResource(ResourceType.DOCUMENT, "Guía de desarrollo de dashboards.pdf", null, null)))));

		buildPhase(guide, 5, "fase5",
				"Pruebas y Validación",
				"Realiza pruebas funcionales, de rendimiento, seguridad y validación del sistema.",
				"3-4 semanas",
				List.of(
						"Ejecutar pruebas funcionales completas",
						"Medir rendimiento y latencia",
						"Validar seguridad del sistema",
						"Documentar resultados de pruebas"),
				List.of(
						"Reporte de pruebas funcionales",
						"Análisis de rendimiento y métricas",
						"Evaluación de seguridad",
						"Videos demostrativos del sistema"),
				List.of(
						createStep("1. Pruebas Funcionales",
								"Verifica que cada componente funcione según especificaciones.",
								List.of(
										createStepResource(ResourceType.VIDEO, "Testing de sistemas IoT", "19:45", null),
										createStepResource(ResourceType.DOCUMENT, "Plan de pruebas funcionales.xlsx", null, null))),
						createStep("2. Pruebas de Rendimiento",
								"Mide latencia, throughput, consumo energético y confiabilidad.",
								List.of(
										createStepResource(ResourceType.VIDEO, "Medición de rendimiento en IoT", "17:30", null),
										createStepResource(ResourceType.DOCUMENT, "Métricas de rendimiento.pdf", null, null))),
						createStep("3. Validación de Seguridad",
								"Evalúa vulnerabilidades, encriptación y autenticación.",
								List.of(
										createStepResource(ResourceType.VIDEO, "Seguridad en sistemas IoT", "21:15", null),
										createStepResource(ResourceType.DOCUMENT, "Checklist de seguridad IoT.pdf", null, null)))));

		buildPhase(guide, 6, "fase6",
				"Conclusiones y Mejoras Futuras",
				"Sintetiza resultados, reflexiona sobre el desarrollo y propone mejoras.",
				"2-3 semanas",
				List.of(
						"Sintetizar hallazgos del proyecto",
						"Evaluar cumplimiento de objetivos",
						"Discutir desafíos y soluciones",
						"Proponer escalabilidad y mejoras"),
				List.of(
						"Conclusiones (3-5 páginas)",
						"Documentación de desafíos",
						"Propuestas de escalabilidad",
						"Recomendaciones de mejoras"),
				List.of(
						createStep("1. Análisis de Resultados",
								"Resume el funcionamiento del sistema y lecciones aprendidas.",
								List.of(
										createStepResource(ResourceType.VIDEO, "Análisis de proyectos IoT", "11:40", null),
										createStepResource(ResourceType.DOCUMENT, "Guía de análisis final.pdf", null, null))),
						createStep("2. Desafíos y Soluciones",
								"Documenta problemas encontrados y cómo fueron resueltos.",
								List.of(
										createStepResource(ResourceType.VIDEO, "Lecciones aprendidas en IoT", "13:25", null),
										createStepResource(ResourceType.DOCUMENT, "Template de lecciones aprendidas.docx", null, null))),
						createStep("3. Escalabilidad y Mejoras",
								"Propón cómo escalar el sistema y mejoras tecnológicas futuras.",
								List.of(
										createStepResource(ResourceType.VIDEO, "Escalabilidad en sistemas IoT", "15:10", null),
										createStepResource(ResourceType.DOCUMENT, "Guía de escalabilidad.pdf", null, null)))));

		return guide;
	}

	private void buildPhase(
			Guide guide,
			int order,
			String slug,
			String title,
			String description,
			String duration,
			List<String> objectives,
			List<String> deliverables,
			List<PhaseStep> steps) {

		var phase = new Phase();
		phase.setGuide(guide);
		phase.setOrderIndex(order);
		phase.setSlug(slug);
		phase.setTitle(title);
		phase.setDescription(description);
		phase.setDuration(duration);
		phase.setObjectives(new ArrayList<>(objectives));
		phase.setDeliverables(new ArrayList<>(deliverables));

		var managedSteps = new ArrayList<PhaseStep>();

		for (int i = 0; i < steps.size(); i++) {
			var step = steps.get(i);
			step.setPhase(phase);
			step.setOrderIndex(i);
			var resources = new ArrayList<>(step.getResources());
			for (int j = 0; j < resources.size(); j++) {
				resources.get(j).setStep(step);
				resources.get(j).setOrderIndex(j);
			}
			step.setResources(resources);
			managedSteps.add(step);
		}

		phase.setSteps(managedSteps);
		guide.getPhases().add(phase);
	}

	private PhaseStep createStep(String title, String description, List<StepResource> resources) {
		var step = new PhaseStep();
		step.setTitle(title);
		step.setDescription(description);
		step.setResources(new ArrayList<>(resources));
		return step;
	}

	private StepResource createStepResource(ResourceType type, String title, String duration, String format) {
		var resource = new StepResource();
		resource.setType(type);
		resource.setTitle(title);
		resource.setDuration(duration);
		resource.setFormat(format);
		return resource;
	}

	private Guide createGuide(String slug, String name, String description, String icon) {
		var guide = new Guide();
		guide.setSlug(slug);
		guide.setName(name);
		guide.setDescription(description);
		guide.setIcon(icon);
		return guide;
	}

	private List<FaqEntry> buildFaqs() {
		var faqs = new ArrayList<FaqEntry>();
		String[][] faqData = {
				{ "¿Cuál es la diferencia entre PDG1 y PDG2?",
						"PDG1 se enfoca en la planificación y diseño de la investigación (definición del problema, marco teórico y metodología), mientras que PDG2 se centra en la ejecución, análisis de resultados y conclusiones del proyecto." },
				{ "¿Cuánto tiempo tengo para completar cada fase?",
						"El tiempo varía según la fase, pero generalmente cada una toma entre 2 y 6 semanas. Consulta la descripción específica de cada fase para tiempos estimados." },
				{ "¿Puedo cambiar mi tema de investigación después de PDG1?",
						"Los cambios mayores deben ser consultados con tu director de proyecto. Cambios menores de enfoque son posibles con justificación adecuada." },
				{ "¿Dónde puedo encontrar ejemplos de proyectos anteriores?",
						"La sección de Recursos contiene ejemplos de proyectos exitosos. También puedes consultar el repositorio institucional de la biblioteca." },
				{ "¿Qué formato de citación debo usar?",
						"La Universidad Icesi requiere el formato APA (última edición) para todas las citaciones y referencias bibliográficas." },
				{ "¿Cómo selecciono mi director de proyecto?",
						"Debes elegir un profesor del programa que tenga experiencia en tu área de interés. Contacta directamente al profesor y solicita una reunión para discutir tu propuesta." },
				{ "¿Puedo trabajar en grupo para el proyecto de grado?",
						"Depende del programa. Algunos permiten proyectos en pareja o grupos pequeños. Consulta con la coordinación de tu programa." },
				{ "¿Qué hago si no apruebo una fase?",
						"Debes revisar los comentarios de tu evaluador, hacer las correcciones necesarias y volver a presentar según el calendario académico." },
				{ "¿Cómo accedo a las bases de datos para mi investigación?",
						"Puedes acceder a través de la biblioteca virtual de la Universidad Icesi con tu usuario institucional. Consulta la sección de Recursos para más información." },
				{ "¿Necesito aprobación ética para mi proyecto?",
						"Si tu investigación involucra seres humanos, datos personales o experimentación, debes solicitar aprobación del comité de ética antes de iniciar la recolección de datos." }
		};

		for (int i = 0; i < faqData.length; i++) {
			var entry = new FaqEntry();
			entry.setQuestion(faqData[i][0]);
			entry.setAnswer(faqData[i][1]);
			entry.setOrderIndex(i);
			faqs.add(entry);
		}

		return faqs;
	}

	private List<SupportResource> buildSupportResources() {
		return List.of(
				createSupportResource(0, "Plantilla de Propuesta de Investigación", ResourceType.DOCUMENT, "PDG1",
						"Formato estándar para la presentación de tu propuesta inicial.", "DOCX", null),
				createSupportResource(1, "Guía de Redacción Académica", ResourceType.DOCUMENT, "General",
						"Normas y mejores prácticas para la escritura académica.", "PDF", null),
				createSupportResource(2, "Tutorial: Búsqueda en Bases de Datos", ResourceType.VIDEO, "PDG1",
						"Aprende a buscar literatura académica de manera efectiva.", null, "15:30"),
				createSupportResource(3, "Ejemplo de Marco Teórico", ResourceType.DOCUMENT, "PDG1",
						"Ejemplo completo de un marco teórico bien estructurado.", "PDF", null),
				createSupportResource(4, "Plantilla de Matriz Metodológica", ResourceType.DOCUMENT, "PDG1",
						"Herramienta para organizar tu diseño metodológico.", "XLSX", null),
				createSupportResource(5, "Diagramas y Modelos Conceptuales", ResourceType.IMAGE, "General",
						"Ejemplos de diagramas para representar conceptos e ideas.", "PNG", null),
				createSupportResource(6, "Tutorial: Análisis de Datos Cualitativos", ResourceType.VIDEO, "PDG2",
						"Técnicas para analizar datos cualitativos en tu investigación.", null, "22:15"),
				createSupportResource(7, "Formato de Presentación Final", ResourceType.DOCUMENT, "PDG2",
						"Plantilla para la presentación de tu proyecto de grado.", "PPTX", null));
	}

	private SupportResource createSupportResource(
			int order,
			String title,
			ResourceType type,
			String category,
			String description,
			String format,
			String duration) {
		var resource = new SupportResource();
		resource.setOrderIndex(order);
		resource.setTitle(title);
		resource.setType(type);
		resource.setCategory(category);
		resource.setDescription(description);
		resource.setFormat(format);
		resource.setDuration(duration);
		return resource;
	}
}

