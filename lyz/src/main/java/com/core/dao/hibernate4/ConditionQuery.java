package com.core.dao.hibernate4;

import java.util.ArrayList;
import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.criterion.Criterion;

public class ConditionQuery {
	private List<Criterion> criterions = new ArrayList<Criterion>();

	public void add(Criterion criterion) {
		criterions.add(criterion);
	}

	public void build(Criteria criteria) {
		for (Criterion criterion : criterions) {
			criteria.add(criterion);
		}
	}
}
