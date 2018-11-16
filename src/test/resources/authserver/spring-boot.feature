Feature: Spring Boot
  
  Scenario: Starting the spring boot application
    Given I start the spring boot application
    Then The application context should load
    