Feature: Test for checked working service

  Scenario: Call backend with checked service
    When the client calls endpoint "/health"
    Then response status code is 200
    And returned string should be "true"