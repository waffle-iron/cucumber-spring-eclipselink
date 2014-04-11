@txn
Feature: Books

  Scenario: Save books

    Given a writer has contributed to the following titles:
      | The Cucumber Book |
      | Cucumber Recipes  |
    When someone fetches the books
    Then 2 titles named as above have been added to the database


