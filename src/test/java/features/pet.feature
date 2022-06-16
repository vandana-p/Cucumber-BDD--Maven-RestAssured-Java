Feature: Validating User APIs

@SmokeTest @RegressionTest 
Scenario: As a user I want to add new pet to the store with status Available
Given Pet payload with petname "<petname>", pet id "<petid>" and status "Available"
When User makes "POST" request to "create" pet id "<petid>" in the store
Then Status code returned is "200"

Examples: 
|petname|petid|
|Boggy|10|


@SmokeTest @RegressionTest 
Scenario: As a user I want to update the details of the pet in the pet store
Given Pet "<petname old>", pet id "<petid>" and status "Available" is added in the store
And  Pet payload with petname "<petname new>", pet id "<petid>" and status "Sold"
When User makes "PUT" request to "update" pet id "<petid>" in the store
Then Status code returned is "200"
And "<petname new>" status is set to "Sold"
 
Examples: 
|petname old|petid|petname new|
|Thunder|23|Kokie|
  
@SmokeTest @RegressionTest 
Scenario Outline: As a user I want to view the details of pet by its id
Given Pet "<petname>", pet id "<petid>" and status "Available" is added in the store
When User makes "GET" request to "view" pet id "<petid>" in the store
Then Status code returned is "200"
And "<petname>" status is set to "Available"
Examples: 
|petname|petid|
|Doddle|14|


@SmokeTest @RegressionTest
Scenario Outline: As a user I want to view the details of pet by its statuses
When User makes GET request to view pet details by their status "<status>"
Then Status code returned is "200"
And Response retrieved contains all pet having status "<status>"

Examples:
|status| 
|available|
|pending|
|sold|

@RegressionTest
Scenario: As a user when I try add new pet to the store by passing invalid status Not Available, then API 
should display validation error and 400 response code
Given Pet payload with petname "<petname>", pet id "<petid>" and status "Not Available"
When User makes "POST" request to "create" pet id "<petid>" in the store
Then Status code returned is "400"
And Response body displays message "allowable values `[available, pending, sold]"

Examples: 
|petname|petid|
|Boggy|10|


@RegressionTest
Scenario: As a user when I try to view pet details by its status and pass incorrect pet status, then API
should display validation error and 400 response code
When User makes GET request to view pet details by their status "<status>"
Then Status code returned is "400"
And Response body displays message "allowable values `[available, pending, sold]"

Examples:
|status| 
|notavailable|

@SmokeTest @RegressionTest
Scenario: As a user I want to remove pet from the pet store
Given Pet "Dodo", pet id "11" and status "Available" is added in the store
When User makes "DELETE" request to "delete" pet id "11" in the store
Then Status code returned is "200"
And Response body displays message "Pet deleted"


@RegressionTest
Scenario: As a user when I pass not existing pet Id to remove pet from the store then
API response should throw validation error with 404 status code
Given Pet id "123456" does not exists in the store
When User makes "DELETE" request to "delete" pet id "123456" in the store
Then Status code returned is "404"
And Response body displays message "Pet not found"

@RegressionTest
Scenario: As a user when I pass not existing pet Id to retrieve pet details from the store then
API response should throw validation error with 404 status code
When User makes "GET" request to "view" pet id "123456" in the store
Then Status code returned is "404"
And Response body displays message "Pet not found"

@RegressionTest 
Scenario: As a user I want to upload an image of an existing pet in a store by passing pet Id
Given Pet "<petname>", pet id "<petid>" and status "Available" is added in the store
And An image "<filepath>" of a pet to upload
When User makes "POST" request to upload image of pet id "<petid>" in the store
Then Status code returned is "200"

Examples:
|filepath|petid|petname|
|src/test/java/TestData/Pet/lolo.jpg|16|Ruffle|




