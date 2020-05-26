Feature: Komoot Scenarios

Scenario: Change Language and Validate URL
Given: I am on www.komoot.com
When: I select a language
Then: I should be redirected to corresponding country url

Scenario: Check Expected Places In The Map
Given: I am on www.komoot.com
When: I access menu Route Planner
And: I select the field Search For Place Or Address
Then: I should see the list SHOW PLACES ON THE MAP with all expected values

Scenario: Search Places or Adresses
Given: I am on www.komoot.com
When: I access menu Route Planner
And: I fill the field Search For Place Or Address
Then: I should see a list with the expected results
