Procedure: 
1 - Begin by generating a Spring Boot project with necessary dependencies (Spring Security, Spring Data JPA for Hibernate and H2, Spring REST).

2 - From here, I recruited a local AI model to generate the boilerplate needed for a few key functions, the essential CRUD for the Booking and Equipment objects.

3 - After the boilerplate was generated, I conducted review to ensure the application as built to this point was in good working order.

4 - With the boilerplate shown not to have apparent compile errors, I attempted to run. This took a bit more configuring and revision.

5 - The application now runs and performs READ operations on an empty H2 DB table.

6 - Test case failures, reviewing and revising.

7 - Test cases pass, needed to add a proper mock for DB save call.

Pending - UI integration not implemented.