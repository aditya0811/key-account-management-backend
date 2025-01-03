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
<li> API Key authentication <li>
<li> Adding key account manager</li> 
<li> Adding customers(like restaurants leads) for key account manager</li> 
<li> Adding multiple point of contact for a customer and updating point of contact for the customer and key account manager</li> 
<li>Updating next meeting timestamp on the basis of common working hours between customer and key account manager, with customer timezone given preference, if there is no common time.
<li> Adding interactions(calls/offline discussion) between point of contact and key account manager</li> 
 <ol>
  <li> Handling change in key account manager for a customer, after an interaction.
   <li> Updating customer table with total orders and total TPV
    <li> Udpating orders table, if an order is placed after interaction
     <li>Every interaction leads to change in last meeting timestamp, and updating next meeting timestamp using frequency of days a customer needs to be called</li>
  <li> Changing Lead status, if number of interactions is greater than zero for a customer
   <li> Updating lead status if the interaction led to conversion of customer.
 </ol>
  
<li>Adding order details corresponding to an interaction</li> 
<li>Listing interactions scheduled today for a given key account manager</li> 
<li>Listing interactions scheduled today for a given key account manager</li>
<li>Upda</li> 
<li>Listing top X (high performing) and bottom X(underperforming) customers for a praticular key account manager on the basis of total transaction value</li> 
<li>Swagger is accessible via browser and api both  </li>
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
<img src="https://github.com/user-attachments/assets/574d4057-5ba7-4657-9f2c-133579c87588" alt="DB SCHEMA" align="center">

 ## API Endpoints
 Following are the api endpoints for this app.
 

<table style="width:100%">
  <tr>
    <th>Name</th>
    <th>URL</th>
    <th>Description</th>
  </tr>
 <tr>
    <td>SIGNUP</td>
    <td>/signup</td>
    <td>Signing up using username, password, email</td>
  </tr>
  <tr>
    <td>LOGIN</td>
    <td>/login </td>
    <td>Logging in using username, password</td>
  </tr>
 <tr>
    <td>CARDS</td>
    <td>/cards</td>
    <td>Adding cards</td>
  </tr>
   <tr>
    <td>VIEW CARDS</td>
    <td>/cards/pk</td>
    <td>About card</td>
  </tr>
    <tr>
    <td>VIEW STATEMENTS</td>
    <td>/cards/pk/statements</td>
    <td>To view transactions for a particular</td>
  </tr>
 </tr>
    <tr>
    <td>VIEW STATEMENTS FOR MM/YYYY</td>
    <td>/cards/pk/statements/mm/yyyy</td>
    <td>To view/post transactions for a MM/YYYY</td>
  </tr>
    <tr>
    <td>PAY BILL</td>
    <td>/cards/pk/pay</td>
    <td>To pay bill for a particular card</td>
  </tr>
    <tr>
    <td>SMART STATEMENTS </td>
    <td>/cards/pk/smartstatements</td>
    <td>To view top 10 vendors for a particular card</td>
  </tr>
    </tr>
    <tr>
    <td>SWAGGER </td>
    <td>/swagger</td>
    <td>Swagger doc for our apis </td>
  </tr>
</table>




