# Key Account Management System

## Tech Stack


### Backend
<p align="left">
 <img alt="Java" src="https://img.shields.io/badge/Java-ED8B00?style=for-the-badge&logo=openjdk&logoColor=white"/></br>
 <img alt="Springboot" src="https://img.shields.io/badge/SpringBoot-6DB33F?style=flat-square&logo=Spring&logoColor=white"/> 
</p>

### Database
<p align="left">
<img alt="Postgres" src ="https://img.shields.io/badge/-SQL-000?&logo=MySQL&logoColor=4479A1"/> 
</p>

## Key Account Management System
Built a Key account management system with the following features

<ol>
<li> API Key authentication </li>
<li> Adding key account manager</li> 
<li> Adding customers(like restaurants leads) for key account manager</li> 
<li> Adding multiple point of contact for a customer and updating point of contact for the customer and key account manager</li> 
<li>Updating next meeting timestamp on the basis of common working hours between point of contact and key account manager, with point of contact working hours timezone given preference, if there is no common time.
<li> Adding interactions(calls) between point of contact and key account manager. Following are things possible post an interaction.</li> 
 <ol>
  <li> Handling change in key account manager for a customer.
   <li> Updating orders table, if an order is placed after an interaction. And consequently updating customer table with total orders and total TPV.
    <li> 
     <li>Every interaction leads to change in last meeting timestamp, and updating next meeting timestamp using frequency of days a customer needs to be called</li>
  <li> Changing lead status, if number of interactions is greater than zero for a customer.
   <li> Updating lead status if the interaction led to conversion of customer.
 </ol> 
<li>Listing interactions scheduled today for a given key account manager</li> 
<li>Listing top X (high performing) and bottom X(underperforming) customers for a praticular key account manager on the basis of total transaction value</li> 
<li>API documentation is accessible via both browser `http://localhost:8080/v3/swagger-ui/index.html#/` and postman `http://localhost:8080/v3/swagger-ui/index.html#/` </li>
</ol>

## Getting started 


### Pre-requisite

Need to install 
```
Maven(3.8.8)
Java(22)
```
### Building
Use below code to built project
```
mvn clean install
```

### Testing
```
mvn test
```

## Starting app 
### Backend 
```
mvn spring-boot:run
```

## Database schema
<img src="https://github.com/user-attachments/assets/595831ba-871a-47d4-86f6-841f1174ca78" alt="DB SCHEMA" align="center">
