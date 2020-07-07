package com.thomas.oneToManyRelationshipSortedSetEagerComparatorwiseSortedWithBackwardNavigability.repositories;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

import com.thomas.oneToManyRelationshipSortedSetEagerComparatorwiseSortedWithBackwardNavigability.domains.A;

public interface ARepository extends CrudRepository<A, Long> {
	public List<A> findByA(String a);
}