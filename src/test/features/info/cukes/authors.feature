Feature: Authors

  @txn @accepted @completed
  Scenario: Store authors and a book

    Given "Andy Glick" and "Jim Laspada" are authors
    When they write a book entitled "How to use SSH"
    Then their names should be associated with that title in the persistent store
