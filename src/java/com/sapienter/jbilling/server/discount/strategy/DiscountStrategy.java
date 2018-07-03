package com.sapienter.jbilling.server.discount.strategy;

import java.util.List;

import com.sapienter.jbilling.server.pricing.util.AttributeDefinition;

public interface DiscountStrategy {

	public List<AttributeDefinition> getAttributeDefinitions();
	
}
