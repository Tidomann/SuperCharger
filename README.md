# SuperCharger
SuperCharger is a java project to simulate an electrical charging station for a vehicle

Readme

GENERAL USAGE NOTES
(* -> Fully Implemented )
(- -> Incomplete        )
(x -> Planned           )

* The GUI will display panels representing the java objects used to simulate a charging station
* The user must enter a valid account ID in the login text field and then press return/enter to access the GUI
* They can then use the jToggleButton “Charge” to simulate charging their vehicle
X Have not added the ability to have multiple vehicles per account
X Could generate the SuperCharger object in Main and pass a reference as a parameter into the GUI. This could allow multiple GUI’s to access the same data allowing multiple charging stations to have functioning GUI’s.
X Ability to merge accounts or functions to handle data in the invalidAccounts.txt file

NOTES ABOUT USING THE PROGRAM:
Enter a valid account ID found in the accountData.txt file into the jtextarea and pressing return/enter
Use the charge button to simulate charging the vehicle
When finished press the charge button to deselect it and discontinue charging or press logout
Repeat as desired.
