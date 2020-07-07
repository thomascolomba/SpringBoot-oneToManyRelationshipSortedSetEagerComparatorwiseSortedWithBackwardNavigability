package com.thomas.oneToManyRelationshipSortedSetEagerComparatorwiseSortedWithBackwardNavigability.domains;

import javax.persistence.*;

import org.hibernate.annotations.SortComparator;

import com.thomas.oneToManyRelationshipSortedSetEagerComparatorwiseSortedWithBackwardNavigability.comparator.BComparator;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;
import java.util.SortedSet;
import java.util.TreeSet;

@Entity
@Table(name = "A")
@NoArgsConstructor
@Setter @Getter
@EqualsAndHashCode(of = {"id"})
public class A implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String a;

    @OneToMany(mappedBy = "a", fetch = FetchType.EAGER,
            cascade = CascadeType.ALL, orphanRemoval = true)
    @SortComparator(BComparator.class)
    private SortedSet<B> bSet = new TreeSet<B>();

    public A(String a) {
        this.a = a;
    }

    @Override
    public String toString() {
        String toReturn = "A{" +
                "id=" + id +
                ", a='" + a + "' Bs : ";
        for(B b : bSet) {
        	toReturn += b.getB()+" ";
        }
        toReturn += '}';
        return toReturn;
    }
}