# Enterprise System Starter - Basic Example

This project demonstrates the bare bones of how to get a rapid release enterprise application started.

## Layout
This section defines the basic layout of this project.

### com.starter.config
 Load the database username/password properties
 
 ### com.starter.entities
 Defines our two basic related entities; Department and Employee

### db.migration
This section houses the Java based migrations. These create tables and update them based on DTO analysis.

#### V16__initial_schema
The initial schema tells the system to manage the Department and Employee class. Along with inserting some mock data.

 #### V17__main_toolbar_schema
 The main toolbar schema updates our UI tables to notify the system that we have a new UI project that we want to begin hooking into.
 This section is only ever done once for a project so the system knows what your main starting window will be.
 
 #### V19__configure_ui_defaults
The system automatically generates the UI components and naming for you. It does this based on convention but this migration shows how to override the naming of UI columns for specific DTOs.
 
 ## Next Steps
 Try adding another DTO called Location that encapsulates the location information for an employee (Street, City,State,Country,Zip).
 Replace this information, in the Employee, with this new DTO and see the magic of how easy it is have all of these changes reflected in the UI automatically!
 
## Deployment
Please see README for the Java Enterprise System Framework - https://github.com/ampopp04/system

username: testuser
password: password