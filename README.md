Cucumber Spring and Eclipselink
===============================

a simple project -- this one makes use of cucumber to drive Spring and Eclipselink writing
persistent data to an HSQL in memory DB

There are 2 entity classes Book and Author. There is a bidirectional many to many relationship between them
 as a Book will have one or more Authors, and an Author may contribute to zero or more Books.

There is a relish project site at https://www.relishapp.com/zrgs-org/cucumber-spring-and-eclipselink/docs

Found the beginnings of this example in the book Cucumber Recipes

There are 2 libraries in use here that aren't being used by TopNG, they are the AssertJ assertions library
and the QueryDSL JPA querying library.

AssertJ allows for even more expressive fluent assertions than the fest assertions library,
which I tried out but abandoned once I realized how much more powerful AssertJ is.
(http://joel-costigliola.github.io/assertj/)
 .

As example difference is the following:

using fest:

    Assertions.assertThat(authorNameList.size()).isEqualTo(authorNamesToFind.size());

using AssertJ:

    Assertions.assertThat(authorNameList).hasSameSizeAs(authorNamesToFind);


QueryDSL allows a good deal more flexibility in querying persistent data, allowing us to derive even more
benefits from the use of Spring Data JPA. (http://www.querydsl.com/)

An example query follows:

    QAuthor author = QAuthor.author1;

    BooleanExpression authorsToLookup = author.authorName.eq("Andy Glick")
      .or(author.authorName.eq("Jim Laspada")).or(author.authorName.eq("Jeffrey Braxton"));

    Iterable<Author> locatedAuthors = authorRepository.findAll(authorsToLookup);

The query returns only the entries specified in the BooleanExpression

An odd finding: attempted to configure and use AuthorDelegate and BookDelegate classes via Spring dependency injection.
This worked successfully everywhere except in the Author and Book classes. It turns out that the JPA Entity classes 
are not Spring beans managed by the Spring DI container, they are instead managed by the JPA container itself. I had 
hoped that I could use the AspectJ weaving technique to do the injection, from what I read in the documentation there
was a suggestion that AspectJ weaving could do injection into instances not managed by the Spring container but I 
couldn't get that to work, and I don't know why, so that is worth further investigation.

In addition I have attempted to configure CDI using a number of different CDI environments in particular the 
DeltaSpike environment. I couldn't actually get a fully working environment configured using DeltaSpike. What was 
interesting about running in the DeltaSpike environment is that the Spring Data JPA repository proxies seemed to be 
recognized and may actually work, so there appears to be some non-trivial interoperability between Spring Data JPA 
and CDI JPA and or Eclipselink JPA.

What seems to be the underlying issue is the interoperability between the JPA container, and one or more of the DI 
containers. I am not actually sure if the JPA managed Entities can be treated as beans managed by any of the DI 
containers, that may be the problem. That said, I would have expected the AspectJ weaving to do the injection. I'll 
have to do a better job of sorting out how AspectJ weaving is supposed to work for non-Spring managed beans and what 
can be expected from it. 


