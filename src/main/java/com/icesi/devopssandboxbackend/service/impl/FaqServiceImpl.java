package com.icesi.devopssandboxbackend.service.impl;

import java.util.Comparator;
import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.icesi.devopssandboxbackend.domain.repository.content.FaqEntryRepository;
import com.icesi.devopssandboxbackend.dto.content.FaqResponse;
import com.icesi.devopssandboxbackend.service.FaqService;
import com.icesi.devopssandboxbackend.service.mock.MockDataProvider;

@Service
public class FaqServiceImpl implements FaqService {

	private final FaqEntryRepository faqEntryRepository;

	public FaqServiceImpl(FaqEntryRepository faqEntryRepository) {
		this.faqEntryRepository = faqEntryRepository;
	}

	@Override
	@Transactional(readOnly = true)
	public List<FaqResponse> list() {
		try {
			long count = faqEntryRepository.count();
			if (count > 0) {
				return faqEntryRepository.findAll().stream()
						.sorted(Comparator.comparingInt(faq -> faq.getOrderIndex()))
						.map(faq -> {
							var response = new FaqResponse();
							response.setQuestion(faq.getQuestion());
							response.setAnswer(faq.getAnswer());
							return response;
						})
						.toList();
			}
		} catch (Exception e) {
			// Si hay error con BD, usar datos mockeados
			return MockDataProvider.getMockFaqs();
		}
		// Si no hay datos en BD, devolver datos mockeados
		return MockDataProvider.getMockFaqs();
	}
}

