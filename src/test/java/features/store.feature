Feature: Validating Store APIs

@SmokeTest @RegressionTest 
Scenario: As a user I want to retrieve pet fromventories from the pet store to check inventories statuses and quantities 
When User makes GET request to view inventories details by their statuses and quantities
Then Status code returned is "200"
And Response retrieved contains all status codes "approved", "placed" and "delivered" with quantities

@RegressionTest 
Scenario: As a user when I try to place new order for pet by passing invalid status Shipped, then API 
should display validation error and 400 response code
Given Order payload with orderid "<orderid>", pet id "<petid>", quantity "10", status "Shipped", and complete "true"
And Pet "<petname>", pet id "<petid>" and status "Available" is added in the store
When User makes "POST" request to "order" pet id "<petid>" in the store
Then Status code returned is "400"

Examples: 
|orderid|petid|petname|
|10|10|Jaggy|

@RegressionTest 
Scenario: As a user when I try to place new order for pet by passing non existing pet id, then API 
should display validation error and 404 response code
Given Order payload with orderid "<orderid>", pet id "<petid>", quantity "10", status "approved", and complete "true"
And Pet id "<petid>" does not exists in the store
When User makes "POST" request to "order" pet id "<petid>" in the store
Then Status code returned is "404"
And Response body displays message "pet not found"

Examples: 
|orderid|petid|
|11|123456|


@RegressionTest 
Scenario: As a user when I try to place new order for pet by passing complete status as string rather than boolean value, then API 
should display validation error and 405 response code
Given Order payload with orderid "<orderid>", pet id "<petid>", quantity "10", status "approved", and complete "<complete status>"
And Pet "<petname>", pet id "<petid>" and status "Available" is added in the store
When User makes "POST" request to "order" pet id "<petid>" in the store
Then Status code returned is "405"
And Response body displays message "Input error"

Examples: 
|orderid|petid|complete status|petname|
|12|12|done|sccoty|

@RegressionTest
Scenario Outline: As a user when I try to place new order for pet by passing invalid quantity, then API 
should display validation error and 400 response code
Given Order payload with orderid "<orderid>", pet id "<petid>", quantity "<quantity>", status "approved", and complete "true"
And Pet "<petname>", pet id "<petid>" and status "Available" is added in the store
When User makes "POST" request to "order" pet id "<petid>" in the store
Then Status code returned is "400"

Examples: 
|orderid|petid|petname|quantity|
|13|13|Jaggy|-1|
|14|14|Jaggy|five|


@SmokeTest @RegressionTest 
Scenario: As a user I want to place new order for existing pet from store
Given Order payload with orderid "<orderid>", pet id "<petid>", quantity "10", status "approved", and complete "true"
And Pet "<petname>", pet id "<petid>" and status "Available" is added in the store
When User makes "POST" request to "order" order "<petid>" from the store
Then Status code returned is "200"

Examples: 
|orderid|petid|petname|
|15|15|furryi|


@SmokeTest @RegressionTest
Scenario: As a user I want to view the purchase details by its id
Given Pet "<petname>", pet id "<petid>" and status "Available" is added in the store
And Order with orderid "<orderid>", pet id "<petid>", quantity "10", status "approved", and complete "true" is "placed"
When User makes "GET" request to "view" order "<orderid>" from the store
Then Status code returned is "200"
And User verify response body with orderid "<orderid>", pet id "<petid>", quantity "10", status "approved", and complete "true"

Examples: 
|orderid|petid|petname|
|16|16|nugeter|


@SmokeTest @RegressionTest 
Scenario: As a user when I pass not existing order Id to view the purchase details, then API
should display validation error and 404 response code 
Given Order with orderid "<orderid>", pet id "<petid>", quantity "10", status "approved", and complete "true" is "deleted"
When User makes "GET" request to "view" order "<orderid>" from the store
Then Status code returned is "404"
And Response body displays message "Order not found"

Examples: 
|orderid|petid|petname|
|23|23|tyrrp|

@SmokeTest @RegressionTest
Scenario: As a user I want to delete purchase order from the store
Given Pet "<petname>", pet id "<petid>" and status "Available" is added in the store
And Order with orderid "<orderid>", pet id "<petid>", quantity "10", status "approved", and complete "true" is "placed"
When User makes "DELETE" request to "delete" order "<orderid>" from the store
Then Status code returned is "200"


Examples: 
|orderid|petid|petname|
|17|17|derry|


@RegressionTest
Scenario: As a user when I pass not existing order Id to delete the purchase details, then API
should display validation error and 404 response code 
Given Order with orderid "<orderid>", pet id "<petid>", quantity "10", status "approved", and complete "true" is "deleted"
When User makes "DELETE" request to "delete" order "<orderid>" from the store
Then Status code returned is "404"
And Response body displays message "Order not found"

Examples: 
|orderid|petid|petname|
|18|18|yoddoy|


