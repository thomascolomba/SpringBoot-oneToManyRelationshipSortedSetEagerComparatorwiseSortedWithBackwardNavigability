CRUD operations on a OneToMany relationship with a java SortedSet type with Eager fetch, backward navigability on that relationship.<br/>
The ordering of the list's elements is determined by a java method (a Comparator class).
An instance of A has a Set of B.<br/>
An instance of B has a reference to the A instance it is related to.<br/>
A <><--0..*-----1-- B<br/>
<br/>
compile & execute :<br/>
mvn spring-boot:run<br/>
compile into fat jar then execute :<br/>
mvn clean package<br/>
java -jar target/oneToManyRelationshipSortedSetEagerComparatorwiseSortedWithBackwardNavigability-0.0.1-SNAPSHOT.jar<br/>
<br/>
To Compile from within Eclipse or any other IDE, you need to install Lombok : https://projectlombok.org/setup/overview<br/>
<br/>
<br/>

--A.java (entity that holds a collection of B entities)<br/>
<b>@OneToMany(mappedBy = "a", fetch = FetchType.EAGER, cascade = CascadeType.ALL, orphanRemoval = true)<br/>
@SortComparator(BComparator.class)<br/>
private SortedSet&lt;B&gt; bSet = new TreeSet&lt;B&gt;();</b><br/>

--B.java (entity related to a A entity)<br/>
<b>@ManyToOne<br/>
@JoinColumn(name = "a_id", nullable = false)<br/>
private A a;</b><br/>
a is a reference to the A instance that holds the B instance. It allows backward navigability (we can write b.getA() to retrieve the A instance holding this B instance).<br/>

--BComparator.java (implements Comparator&lt;B&gt;)<br/>
return b1.getB().compareTo(b2.getB());<br/>

--AccessingDataJpaApplication.java (main class)<br/>
log.info("===== Persisting As and Bs");<br/>
<b>persistData</b>(aRepository, bRepository);<br/>
readData(aRepository, bRepository);<br/>
log.info("===== Modifying some As and Bs");<br/>
<b>modifyData</b>(aRepository, bRepository);<br/>
readData(aRepository, bRepository);<br/>
log.info("===== Deleting some As and Bs");<br/>
<b>deleteData</b>(aRepository, bRepository);<br/>
readData(aRepository, bRepository);<br/>
...<br/>
<b>persistData(){</b><br/>
&nbsp;&nbsp;//we build A without nested Bs, we set A to each B<br/>
&nbsp;&nbsp;A a1 = new A("a1");<br/>
&nbsp;&nbsp;A a2 = new A("a2");<br/>
&nbsp;&nbsp;aRepository.save(a1);<br/>
&nbsp;&nbsp;aRepository.save(a2);<br/>
&nbsp;&nbsp;bRepository.save(new B("b1", a1));<br/>
&nbsp;&nbsp;bRepository.save(new B("b2", a1));<br/>
&nbsp;&nbsp;bRepository.save(new B("b3", a2));<br/>
&nbsp;&nbsp;bRepository.save(new B("b4", a2));<br/>
}<br/>
<b>modifyData(){</b><br/>
&nbsp;&nbsp;//we change change the value of a1's b1 to b5 and a2's b3 in b6<br/>
&nbsp;&nbsp;A a1 = aRepository.findByA("a1").get(0);<br/>
&nbsp;&nbsp;for(B b : a1.getBSet()) {<br/>
&nbsp;&nbsp;&nbsp;&nbsp;if(b.getB().equals("b1")) {<br/>
&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;b.setB("b5");<br/>
&nbsp;&nbsp;&nbsp;&nbsp;}<br/>
&nbsp;&nbsp;}<br/>
&nbsp;&nbsp;aRepository.save(a1);<br/>
&nbsp;&nbsp;B b3 = bRepository.findByB("b3").get(0);<br/>
&nbsp;&nbsp;b3.setB("b6");<br/>
&nbsp;&nbsp;bRepository.save(b3);<br/>
}<br/>
<b>deleteData(){</b><br/>
&nbsp;&nbsp;//we delete a1 and b4<br/>
&nbsp;&nbsp;A a1 = aRepository.findByA("a1").get(0);<br/>
&nbsp;&nbsp;aRepository.delete(a1);<br/>
&nbsp;&nbsp;A a2 = aRepository.findByA("a2").get(0);<br/>
&nbsp;&nbsp;a2.getBSet().removeIf((B b) -> b.getB().equals("b4"));<br/>
&nbsp;&nbsp;aRepository.save(a2);<br/>
}<br/>