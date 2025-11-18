package com.icesi.devopssandboxbackend.service.impl;

import java.util.List;

import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.icesi.devopssandboxbackend.domain.repository.guide.GuideRepository;
import com.icesi.devopssandboxbackend.domain.repository.guide.PhaseRepository;
import com.icesi.devopssandboxbackend.dto.guide.GuideResponse;
import com.icesi.devopssandboxbackend.dto.guide.PhaseResponse;
import com.icesi.devopssandboxbackend.exception.ResourceNotFoundException;
import com.icesi.devopssandboxbackend.mapper.GuideMapper;
import com.icesi.devopssandboxbackend.service.GuideContentService;
import com.icesi.devopssandboxbackend.service.mock.MockDataProvider;

@Service
public class GuideContentServiceImpl implements GuideContentService {

	private final GuideRepository guideRepository;
	private final PhaseRepository phaseRepository;
	private final GuideMapper guideMapper;

	public GuideContentServiceImpl(
			GuideRepository guideRepository,
			PhaseRepository phaseRepository,
			GuideMapper guideMapper) {
		this.guideRepository = guideRepository;
		this.phaseRepository = phaseRepository;
		this.guideMapper = guideMapper;
	}

	@Override
	@Transactional(readOnly = true)
	public List<GuideResponse> listGuides() {
		try {
			long count = guideRepository.count();
			if (count > 0) {
				return guideMapper.toGuideResponses(guideRepository.findAll(Sort.by(Sort.Direction.ASC, "name")));
			}
		} catch (Exception e) {
			// Si hay error con BD, usar datos mockeados
			return MockDataProvider.getMockGuides();
		}
		// Si no hay datos en BD, devolver datos mockeados
		return MockDataProvider.getMockGuides();
	}

	@Override
	@Transactional(readOnly = true)
	public GuideResponse getGuide(String guideSlug) {
		try {
			long count = guideRepository.count();
			if (count > 0) {
				return guideRepository.findBySlug(guideSlug)
						.map(guideMapper::toGuideResponse)
						.orElseThrow(() -> new ResourceNotFoundException("Guide %s not found".formatted(guideSlug)));
			}
		} catch (Exception e) {
			// Si hay error con BD, usar datos mockeados
			GuideResponse mockGuide = MockDataProvider.getMockGuide(guideSlug);
			if (mockGuide != null) {
				return mockGuide;
			}
			throw new ResourceNotFoundException("Guide %s not found".formatted(guideSlug));
		}
		// Si no hay datos en BD, devolver datos mockeados
		GuideResponse mockGuide = MockDataProvider.getMockGuide(guideSlug);
		if (mockGuide == null) {
			throw new ResourceNotFoundException("Guide %s not found".formatted(guideSlug));
		}
		return mockGuide;
	}

	@Override
	@Transactional(readOnly = true)
	public PhaseResponse getPhase(String guideSlug, String phaseSlug) {
		try {
			long count = guideRepository.count();
			if (count > 0) {
				return phaseRepository.findDetailByGuideSlugAndSlug(guideSlug, phaseSlug)
						.map(guideMapper::toPhaseResponse)
						.orElseThrow(() -> new ResourceNotFoundException(
								"Phase %s not found for guide %s".formatted(phaseSlug, guideSlug)));
			}
		} catch (Exception e) {
			// Si hay error con BD, usar datos mockeados
			PhaseResponse mockPhase = MockDataProvider.getMockPhase(guideSlug, phaseSlug);
			if (mockPhase != null) {
				return mockPhase;
			}
			throw new ResourceNotFoundException(
					"Phase %s not found for guide %s".formatted(phaseSlug, guideSlug));
		}
		// Si no hay datos en BD, devolver datos mockeados
		PhaseResponse mockPhase = MockDataProvider.getMockPhase(guideSlug, phaseSlug);
		if (mockPhase == null) {
			throw new ResourceNotFoundException(
					"Phase %s not found for guide %s".formatted(phaseSlug, guideSlug));
		}
		return mockPhase;
	}
}

