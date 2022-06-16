Feature: Validating Pet APIs

@SmokeTest @RegressionTest
Scenario: As a admin of pet store I should be able to create user 
Given User payload with user "<userdetails>"
When User makes "POST" request to "create user" user "<fn>"
Then Status code returned is "200"

Examples: 
|userdetails|fn|
|1,hs,Harry,Styles,harry@gmail.com,test123,9930904693,1|Harry|


@SmokeTest @RegressionTest
Scenario: As a admin of pet store I should be able to create user with list 
Given User payload with user "<user1>" and "<user2>" details
When User makes "POST" request to "create user list" user "<fn>"
Then Status code returned is "200"

Examples: 
|user1|user2|
|2,sj,Smith,John,mrjohn@gmail.com,test124,9930904694,1|3,mk,Micheal,Kors,micKors@gmail.com,test125,9930904695,1|


@SmokeTest @RegressionTest 
Scenario: As a user I want to view user details by passing existing username
Given User "<userdetails>" is "created"
When User makes "POST" request to "view" user "<fn>" 
Then Status code returned is "200"
And Response body is validated with details "<userdetails>"
 
Examples: 
|userdetails|fn|
|8,js,Jack,Sparrow,mrjs@gmail.com,test135,99309046978,1|Jack|


@SmokeTest @RegressionTest
Scenario: As a user if I try to view user details by passing non-existing username, then API 
should display validation error and 404 response code
When User makes "GET" request to "view" user "<fn>" 
Then Status code returned is "404"
And Response body displays message "User not found"
 
Examples: 
|userdetails|fn|
|8,ll,Laura,Love,llove@gmail.com,test135,99309046978,1|Laura|

@SmokeTest @RegressionTest
Scenario: As a admin I want to update the details of the existing user by passing existing user name
Given User "<userdetails old>" is "created"
And User payload with user "<userdetails new>"
When User makes "PUT" request to "update" user "<username>"
Then Status code returned is "200"
And Response body is validated with details "<userdetails new>"
 
Examples: 
|userdetails old|userdetails new|username|
|5,db,Daniel,Bell,db@gmail.com,test126,99309046976,1|5,rg,Rachel,Green,iamrachel@gmail.com,test128,9930904696,1|db|


@RegressionTest
Scenario: As a admin when I try to update the details of the existing user by incorrect passing user name, then API 
should display validation error and 404 response code
Given User "<userdetails>" is "not created"
And User payload with user "<userdetails>"
When User makes "PUT" request to "update" user "<username>"
Then Status code returned is "404"
And Response body displays message "User not found"
 
Examples: 
|userdetails|username|
|6,dslo,aa,bb,cd@gmail.com,test129,99309046977,1|ronnyy|


@SmokeTest @RegressionTest 
Scenario: As a user I want to delete existing user by its username
Given User "<userdetails>" is "created"
When User makes "DELETE" request to "delete" user "<username>"
Then Status code returned is "200"

Examples: 
|userdetails|username|
|8,ljuni,Juggle,jugss,jjuniv@gmail.com,test135,99309046978,1|ljuni|


@SmokeTest @RegressionTest 
Scenario: As a user when I try to delete user by passing non-existing username, then API 
should display validation error and 404 response code
Given User "<userdetails>" is "not created"
When User makes "DELETE" request to "delete" user "<username>"
Then Status code returned is "404"
And Response body displays message "User not found"
 
Examples: 
|userdetails|
|9,rickcool,Ricky,Rrock,rickcool@gmail.com,test138,99309046988,1|


@SmokeTest @RegressionTest
Scenario: As a user I want to login with correct credential
Given User "<userdetails>" is "created"
When User makes "GET" request to "login" for "<username>" and "<password>" 
Then Status code returned is "200"

Examples: 
|userdetails|username|password|
|8,lomeech,Lola,Meech,mrjs@gmail.com,test135,99309046978,1|lomeech|test135|


@RegressionTest  
Scenario Outline: As a user when I try to login with invalid username or password, then API 
should display validation error and 400 response code
Given User "<userdetails>" is "created"
When User makes "GET" request to "login" for "<username>" and "<password>"  
Then Status code returned is "400"
And Response body displays message "Invalid username/password supplied"

Examples: 
|userdetails|username|password|
|8,minne,jimme,jill,mrjs@gmail.com,test135,99309046978,1|minne|test139|
|8,kiop,ulandy,land,lands@gmail.com,test135,99309046978,1|jose|test135|

@SmokeTest @RegressionTest
Scenario: As a user I want to logout successfully
When User makes "GET" request to "logout" for "<username>" and "<password>"
Then Status code returned is "200"
And Response body displays message "User logged out"
