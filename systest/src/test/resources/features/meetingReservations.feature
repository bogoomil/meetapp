Feature: Meeting App Tests

  Four meetings already reserved, trying to create 5th reservation

  Scenario: Test feature
    Given four meetings already reserved
    When i try to create a new reservation
    Then i got an exception
