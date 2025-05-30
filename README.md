# Travel_Booking_System
🧳 Travel Booking System
The Travel Booking System is a full-stack web application designed to simplify the process of booking travel packages for users while allowing admins to manage packages, bookings, guides, and more. This system is ideal for travel agencies or tour organizers aiming to digitize their services.

✨ Features
🔐 User Authentication

Register, login, and secure access

Role-based access (Admin / User)

OTP-based password reset via email

📦 Travel Packages

Admin can create, update, and delete travel packages

Each package includes destination, price, duration, and description

🗓️ Itinerary Management

Day-wise itinerary for each travel package

Editable via admin panel

📅 Booking System

Multi-step booking form for users

Tracks booking status: Pending / Confirmed / Canceled

💰 Payment Handling

Admin can manage payments for each booking

Payment status: Paid / Pending / Refunded

🧑‍💼 Guide Management

Admin can add/edit guide profiles with experience and contact details

💬 Review & Rating

Users can leave feedback on travel packages

🛠️ **Technologies Used**
Frontend:
HTML, CSS, Bootstrap 

JavaScript, AJAX

Backend:
Java with Spring Boot (MVC Architecture)

Spring Data JPA, Hibernate

ModelMapper (DTO ↔ Entity conversion)

JavaMailSender (OTP email support)

Database:
MySQL

📌 **Project Structure**
arduino
Copy
Edit
src/
├── controller/
├── service/
├── repository/
├── dto/
├── model/ (Entities)
├── config/

=====================================================================================================================================
📬How to Run
======================================================================================================================================
Clone the repository

Configure the MySQL database in application.properties

Run the Spring Boot application

Access the frontend via browser (HTML + Bootstrap templates)

🙋‍♀️ Author
Ayesha Ushani
Undergraduate @ IJSE Panadura


==============================================================================================================================================

📌Screen shot provides -----> Travel_frontend/Screen_shot
================================================================================================================================================
* home page (user section)
      |
      |------------->
  *                     * about page  
    *                   * service
      *                 * destination
        *               * Packages
          *             * booking section
            *           * guides
              *         * reviews
              
* Admin Dashboard
        |
        |----------->
  *                     * Create packages
    *                   * Manage packages
    
* Guides dashboard
        |
        |---------->  
  *                     * Register as guide
    *                   * manage guides
* Register and login
        |
        |---------->    
*                       * register 
  *                     * login
    *                   * forget password
   
=========================================================================================================================================   
                                               You tube Link
=========================================================================================================================================
https://youtu.be/lBB_ScP2KX8?feature=shared
