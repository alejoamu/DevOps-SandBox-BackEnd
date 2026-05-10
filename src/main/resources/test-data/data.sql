-- =============================================================================
-- Datos semilla para el perfil "test".
--
-- Convenciones de UUIDs:
--   * Methodology: 11111111-... ; Phase: 22222222-... ; Subphase: 33333333-...
--   * Resource:   44444444-... ; SubphaseResource une 33333333 + 44444444.
-- Estos IDs los usa la colección Postman para validar GETs deterministas.
-- =============================================================================

-- Usuario admin (contraseña en texto plano: AdminAuthService la acepta para
-- entornos legacy / tests; en prod siempre debe ir hasheada con BCrypt).
INSERT INTO users (username, password) VALUES ('admin', 'admin123');

-- Methodology semilla
INSERT INTO methodologies (
    id, slug, name, introduction, summary, status, version, language_code,
    created_at, updated_at
) VALUES (
    '11111111-1111-1111-1111-111111111111',
    'scrum-seed',
    'Scrum (semilla)',
    'Una metodología ágil para pruebas E2E.',
    'Resumen breve de Scrum.',
    'published',
    1,
    'es',
    CURRENT_TIMESTAMP,
    CURRENT_TIMESTAMP
);

-- Phase semilla (asociada a la metodología anterior)
INSERT INTO phases (
    id, methodology_id, code, title, description, order_index, created_at, updated_at
) VALUES (
    '22222222-2222-2222-2222-222222222222',
    '11111111-1111-1111-1111-111111111111',
    'P1',
    'Planificación',
    'Fase inicial de planificación.',
    0,
    CURRENT_TIMESTAMP,
    CURRENT_TIMESTAMP
);

-- Subphase semilla (asociada a la fase anterior)
INSERT INTO subphases (
    id, phase_id, code, title, content, order_index, created_at, updated_at
) VALUES (
    '33333333-3333-3333-3333-333333333333',
    '22222222-2222-2222-2222-222222222222',
    'S1',
    'Definición del backlog',
    'Contenido enriquecido de la subfase.',
    0,
    CURRENT_TIMESTAMP,
    CURRENT_TIMESTAMP
);

-- Resource semilla (tipo video)
INSERT INTO resources (
    id, type, title, url, description, provider, thumbnail_url, metadata,
    created_at, updated_at
) VALUES (
    '44444444-4444-4444-4444-444444444444',
    'video',
    'Intro a Scrum',
    'https://example.com/intro-scrum',
    'Video introductorio.',
    'youtube',
    'https://example.com/thumb.jpg',
    '{"youtubeId":"abc123"}',
    CURRENT_TIMESTAMP,
    CURRENT_TIMESTAMP
);

-- Relación subphase <-> resource
INSERT INTO subphase_resources (
    subphase_id, resource_id, order_index, note
) VALUES (
    '33333333-3333-3333-3333-333333333333',
    '44444444-4444-4444-4444-444444444444',
    1,
    'Recurso recomendado'
);
