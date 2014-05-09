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

AssertJ allows for more expressive fluent assertions even over a fluent library such as fest.

As example difference is the following:

using fest:

    Assertions.assertThat(authorNameList.size()).isEqualTo(authorNamesToFind.size());

using AssertJ:

    Assertions.assertThat(authorNameList).hasSameSizeAs(authorNamesToFind);

QueryDSL allows a good deal more flexibility in querying persistent data allowing us to derive even more
benefits from the use of Spring Data JPA. An example query follows:

    QAuthor author = QAuthor.author1;

    BooleanExpression authorsToLookup = author.authorName.eq("Andy Glick")
      .or(author.authorName.eq("Jim Laspada")).or(author.authorName.eq("Jeffrey Braxton"));

    Iterable<Author> locatedAuthors = authorRepository.findAll(authorsToLookup);

The query returns only the entries specified in the BooleanExpression


