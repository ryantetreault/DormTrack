Instructions for Running

add SQL database in MySQL
in the dormtrack_backend folder navigate to resources and then applicaton.properties and change username and password to match your MySQL workbench local instance credentials (or whatever you're using for a server)

username and password must match for spring boot to connect to the database

open the terminal
navigate to filepath dormtrack_backend
run mvn spring-boot:run

open another terminal
navigate to dormtrack_frontend
run mvn javafx:run

application should open if spring boot is successfully executed
