@smoke
Feature: As I QE, I validate GoRest Post
  Scenario: Validating the POST Request
    Given I send a POST request with body
    Then Status code is 201