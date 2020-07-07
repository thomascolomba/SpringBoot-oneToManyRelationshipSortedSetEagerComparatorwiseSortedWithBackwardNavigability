package com.thomas.oneToManyRelationshipSortedSetEagerComparatorwiseSortedWithBackwardNavigability.comparator;

import java.util.Comparator;

import com.thomas.oneToManyRelationshipSortedSetEagerComparatorwiseSortedWithBackwardNavigability.domains.B;

public class BComparator implements Comparator<B> {

	@Override
	public int compare(B b1, B b2) {
		final int BEFORE = -1;
	    final int AFTER = 1;
		if(b2 == null) {
			return BEFORE;
		}
		if(b1 == null) {
			return AFTER;
		}
		if(b2.getB() == null) {
			return AFTER;
		}
		if(b1.getB() == null) {
			return BEFORE;
		}
		return b1.getB().compareTo(b2.getB());
	}

}