package com.sapienter.jbilling.server.discount.strategy;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import com.sapienter.jbilling.server.pricing.util.AttributeDefinition;

public abstract class AbstractDiscountStrategy implements DiscountStrategy {

	private List<AttributeDefinition> attributeDefinitions = Collections.emptyList();

	@Override
	public List<AttributeDefinition> getAttributeDefinitions() {
		return attributeDefinitions;
	}
	
	public void setAttributeDefinitions(AttributeDefinition ...attributeDefinitions) {
		this.attributeDefinitions = Collections.unmodifiableList(Arrays.asList(attributeDefinitions));
	}
}
