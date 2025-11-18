package com.icesi.devopssandboxbackend.domain.model.content;

import java.util.UUID;

import com.icesi.devopssandboxbackend.domain.model.AuditableEntity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "faq_entries")
public class FaqEntry extends AuditableEntity {

	@Id
	@GeneratedValue(strategy = GenerationType.UUID)
	private UUID id;

	@Column(nullable = false, length = 250)
	private String question;

	@Column(nullable = false, length = 1000)
	private String answer;

	@Column(name = "order_index", nullable = false)
	private int orderIndex;

	public UUID getId() {
		return id;
	}

	public String getQuestion() {
		return question;
	}

	public void setQuestion(String question) {
		this.question = question;
	}

	public String getAnswer() {
		return answer;
	}

	public void setAnswer(String answer) {
		this.answer = answer;
	}

	public int getOrderIndex() {
		return orderIndex;
	}

	public void setOrderIndex(int orderIndex) {
		this.orderIndex = orderIndex;
	}
}

