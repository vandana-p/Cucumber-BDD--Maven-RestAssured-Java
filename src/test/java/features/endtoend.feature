Feature: End-to-end feature testing

@EndtoendTest 
Scenario: User logs into the system to place an order for sold pets from the store
Given Pet "<petname>", pet id "<petid>" and status "Sold" is added in the store
When User "<userdetails>" "login" 
And Order with orderid "<orderid>", pet id "<petid>", quantity "2", status "approved", and complete "true" is "placed"
Then Status code returned is "404"
And User "<username>" "logout"

Examples: 
|petname|petid|orderid|userdetails|
|Koki|12|4|6,Konhi,Young,Kohniyoung,ky@gmail.com,test139,9930904623,1|


@EndtoendTest 
Scenario: User logs into the system to place an order for available pets from the store
Given Pet "<petname>", pet id "<petid>" and status "Available" is added in the store
When User "<userdetails>" "login" 
And Order with orderid "<orderid>", pet id "<petid>", quantity "10", status "approved", and complete "true" is "placed"
Then Status code returned is "200"
And User "<username>" "logout"

Examples: 
|petname|petid|orderid|userdetails|
|Saol|30|30|8,ljuni,Juggle,jugss,jjuniv@gmail.com,test135,99309046978,1|

