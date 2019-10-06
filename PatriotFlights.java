/*
Nay Naing
10/05/2019
IT-206-203

Programming Assignment 4 (Implementation)
This program will track flight sales for the Patriot Flight's 15 Boeing 787 planes. Program will track the name of the flight identifier, route, general and premium ticket cost.
Program will also be capable of tracking how many seats of each type has been sold (economy or first-class), and how much revenue has been generated from sales.

The main menu will consist of 5 options for the user : Create flight, Remove flight, Sell flight ticket, display flights, and exit program.

1. Create Flight : This option will allow the user to create a flight by entering the flight identifier, route, and general ticket price until the maximum of 15 flights or they choose to stop.
2. Remove Flight : This option will allow the user to remove a flight from service, by displaying a list of all current flights and then allowing them to choose which one to remove.
3. Sell Flight Ticket : This option will allow the user to sell tickets for flights that have been entered. User can select a flight and then the type of ticket (economy or first-class) to sell.
4. Display Flights : This option will allow the user to see a list of all flights that have been entered with their information including number of tickets sold for each type.
5. Exit the Program : This option will allow the user to exit, as well as display how many flights were entered and the total revenue from all flights.

Inputs: Flight Identifier, Flight Route, General Ticket Price, Seat Type (economy or first-class), Menu Options (1-5)
Expected output: Flight Identifier, Flight Route, General Ticket Price, Premium Ticket Price, Economy and First-Class Seats sold, and revenue for each individual flight.
    Also Total Revenue and Number of Flights on Record.
*/
import javax.swing.JOptionPane;

public class PatriotFlights {
    public static void main(String[] args) {
        // Set the maximum number of flights allowed to be on file.
        final int MAX_NUM_FLIGHTS = 5;
        // Set the maximum number of seats allowed to be sold for economy and first-class per flight.
        final int MAX_SEATS_ECO = 5;
        final int MAX_SEATS_FC = 5;
        // Accumulator for total revenue from each flight.
        double totalRevenue = 0;
        // Counter to keep track of how many flights are currently inside array.
        int numFlights = 0;
        // Create flightRecords array object and set maximum elements allowed to MAX_NUM_FLIGHTS
        Flight[] flightRecords = new Flight[MAX_NUM_FLIGHTS];
        // Get user menu choice 1-5
        int menuChoice = getMenuOption();
        while (menuChoice != 5) {
            switch (menuChoice) {
                case 1: // if user selects (1) call createFlight method
                    if (numFlights < flightRecords.length) {
                        createFlight(flightRecords, numFlights);
                        // Increment the counter for numFlights after every successful user flight input
                        ++numFlights;
                    } else {
                        JOptionPane.showMessageDialog(null, "No more additional flights can be added. Currently full.");
                    }
                    break;

                case 2: // if user selects (2) call removeFlight method
                    if (numFlights > 0) {
                        removeFlight(flightRecords, getRemovalChoice(flightRecords));
                        // Decrement the counter for numFlights after every successful user flight input
                        --numFlights;
                    } else {
                        JOptionPane.showMessageDialog(null, "There are no flights to remove.");
                    }
                    break;

                case 3: // if user selects (3) call sellFlightTicket method
                    if (numFlights > 0) {
                        sellFlightTicket(flightRecords, getTicketSaleChoice(flightRecords), MAX_SEATS_ECO, MAX_SEATS_FC);
                    } else {
                        JOptionPane.showMessageDialog(null, "There are no flights available.");
                    }
                    break;

                case 4: // if user selects (4) call displayFlight method
                    displayFlights(flightRecords, numFlights);
                    break;

                default:
                    throw new RuntimeException("Please select a valid menu choice.");
            }
            menuChoice = getMenuOption();
        }
        // if user selects (5) call exitProgram method which will exit the program and show number of flights entered and revenue generated.
        if(menuChoice == 5) {
            exitProgram(flightRecords, numFlights, totalRevenue);
        }
    }


    // Method to get user input for main menu option
    public static int getMenuOption() {
        int choice;
        /*
            Prompts user to enter 1-5 to indicate their choice of creating, removing, or selling tickets for a flight,
		    or display the current flights entered or quit the program.
	     */
        do {
            try {
                choice = Integer.parseInt(JOptionPane.showInputDialog("Select an option from below :" +
                        "\n(1) Create a flight" +
                        "\n(2) Remove a flight" +
                        "\n(3) Sell flight ticket" +
                        "\n(4) Display Flights" +
                        "\n(5) Exit the program"));
            } catch (NumberFormatException e) {
                choice = 0;
            }
            if (choice < 1 || choice > 5) {
                JOptionPane.showMessageDialog(null, "Invalid choice. Please select a valid choice.");
            }
        } while (choice < 1 || choice > 5);

        return choice;
    }

    // Method to allow user to input flight information
    public static void createFlight(Flight[] flightRecords, int numFlights) {
        if (numFlights < flightRecords.length) {
            /*
		        Prompt user for flight identifier
	        */
            boolean flightIDSet = false;
            do {
                try {
                    /*
		                Create an Flight object with a name based on constructor.
	                */
                    Flight oneFlight = new Flight(JOptionPane.showInputDialog("Enter the flight ID: \n" + "(First 2 characters must be letters and the remaining 4 digits)" ));
                    flightIDSet = true;
                    // Assign flight object index to the number on counter.
                    flightRecords[numFlights] = oneFlight;
                } catch (IllegalArgumentException e) {
                    JOptionPane.showMessageDialog(null, " Flight could not be added." + e.getMessage());
                } catch(ArrayIndexOutOfBoundsException exception) {
                    JOptionPane.showMessageDialog(null, " First two characters must be letters.");
                }
            } while (!flightIDSet);
            /*
		        Prompt user for flight route
	        */
            boolean flightRouteSet = false;
            do {
                try {
                    flightRecords[numFlights].setFlightRoute(JOptionPane.showInputDialog("Enter flight route: "));
                    flightRouteSet = true;
                } catch (IllegalArgumentException e) {
                    JOptionPane.showMessageDialog(null, " Flight could not be added." + e.getMessage());
                }
            } while (!flightRouteSet);
            /*
		        Prompt user for price of general ticket
	        */
            boolean priceGenSet = false;
            do {
                try {
                    flightRecords[numFlights].setPriceGen(Double.parseDouble(JOptionPane.showInputDialog("Enter the general ticket price for flight: ")));
                    priceGenSet = true;
                } catch (NumberFormatException e) {
                    JOptionPane.showMessageDialog(null, "Please enter a valid ticket price");
                } catch (IllegalArgumentException e) {
                    JOptionPane.showMessageDialog(null, e.getMessage());
                }
            } while (!priceGenSet);
            // Set price for the premium ticket based on general ticket price + 250
            flightRecords[numFlights].setPricePremium();
            // Increment number of flights inside DDC
            Flight.addNumFlights();
        } else {
            JOptionPane.showMessageDialog(null, "No more additional flights can be added. Currently full.");
        }
    }

    // Method to allow user to remove a previously entered flight.
    public static void removeFlight(Flight[] flightRecords, int removalChoice) {
        int numFlights = Flight.getNumFlights();
        // Process flight removal based on user selection from the getRemovalChoice method.
        if(numFlights > 0) {
            flightRecords[removalChoice] = flightRecords[numFlights -1];
            Flight.removeNumFlights();
        } else {
            // If no flights were entered, user will get message stating so.
            JOptionPane.showMessageDialog(null, "No more flights to remove.");
        }
    }

    // Method to get user selection of flight they want to remove.
    public static int getRemovalChoice(Flight[] flightRecords) {
        int numFlight = Flight.getNumFlights();
        int removeChoice = 0;
        if(numFlight > 0) {
            /*
		        Prompt user for flight they want to remove from the list of flights available
	        */
            do {
                try {
                    removeChoice = Integer.parseInt(JOptionPane.showInputDialog(displayAvailableFlights(flightRecords,"remove")));
                } catch (NumberFormatException e) {
                    removeChoice = 0;
                }
                if (removeChoice < 1 || removeChoice > flightRecords.length) {
                    JOptionPane.showMessageDialog(null, "Invalid choice. Please select a valid flight to remove.");
                }
            } while (removeChoice < 1 || removeChoice > flightRecords.length);
        } else {
            JOptionPane.showMessageDialog(null, "There are no flights to remove.");
        }
        // return choice - 1 so selection matches location of element index in array.
        return removeChoice - 1;
    }

    // Method to get user selection of flight they want to sell a ticket for.
    public static int getTicketSaleChoice(Flight[] flightRecords) {
        int numFlight = Flight.getNumFlights();
        int sellChoice = 0;
        if(numFlight > 0) {
            /*
		        Prompt user for flight they want to sell a ticket for from the list of flights available
	        */
            do {
                try {
                    sellChoice = Integer.parseInt(JOptionPane.showInputDialog(displayAvailableFlights(flightRecords,"sell")));
                } catch (NumberFormatException e) {
                    sellChoice = -1;
                }
                if (sellChoice < 1 || sellChoice > flightRecords.length) {
                    JOptionPane.showMessageDialog(null, "Invalid choice. Please select a valid flight.");
                }
            } while (sellChoice < 1 || sellChoice > flightRecords.length);
        } else {
            JOptionPane.showMessageDialog(null, "There are no flights available at the moment.");
        }
        // return choice - 1 so selection matches location of element index in array.
        return sellChoice - 1;
    }

    // Method to sell allow user to sell either a economy class ticket or first-class from their chosen flight
    public static void sellFlightTicket(Flight[] flightRecords, int sellChoice, int MAX_SEATS_ECO, int MAX_SEATS_FC) {
        int seatsEco = flightRecords[sellChoice].getSeatsEco() + 1;
        int seatsFC = flightRecords[sellChoice].getSeatsFC() + 1;
        int choice;
        do {
            /*
		        Prompt user for ticket type they want to sell from the flight selected.
	        */
            try {
                choice = Integer.parseInt(JOptionPane.showInputDialog("Please select ticket type to sell" +
                        "\n(1) Economy" +
                        "\n(2) First-Class" +
                        "\n(0) Go Back"));
                if(choice < 0 || choice > 2) {
                    JOptionPane.showMessageDialog(null, "Please select a valid option.");
                } else {
                    if (choice == 1 && seatsEco <= MAX_SEATS_ECO) {
                        flightRecords[sellChoice].setSeatEco();
                    } else if (choice == 2 && seatsFC <= MAX_SEATS_FC) {
                        flightRecords[sellChoice].setSeatFC();
                    } else if (choice == 0) {
                        JOptionPane.showMessageDialog(null, "Going back to main menu.");
                    } else {
                        JOptionPane.showMessageDialog(null, "Flights are limited to 300 Economy seats and 30 FC seats.");
                    }
                }
            } catch (NumberFormatException e) {
                choice = -1;
            }
        } while (choice < 0 || choice > 2);
        // get the revenue for one flight based on ticket sales
        flightRecords[sellChoice].calcFlightRevenue();
        double flightRevenue = flightRecords[sellChoice].getFlightRevenue();
    }

    // Method to display all available flights and their information for flight selection methods (ticket sell, and remove): flight id, route, general ticket price, premium ticket price and revenue
    public static String displayAvailableFlights(Flight[] flightRecords, String option) {
        String displayFlights = "Please select a flight to " + option + "\n";
        for (int x = 0; x < Flight.getNumFlights(); x++) {
            displayFlights += (x + 1) + ": " + flightRecords[x].toString() + "\n";
        }
        return displayFlights;
    }

    // Method to display all available flights and their information: flight id, route, general ticket price, premium ticket price and revenue
    public static void displayFlights(Flight[] flightRecords, int numFlights) {
        String flightsRecord = "All Available Flights \n";
        if(numFlights > 0) {
            for (int x = 0; x < Flight.getNumFlights(); x++) {
                flightsRecord += (x + 1) + ": " + flightRecords[x].toString() + "\n";
            }
        } else {
            flightsRecord = "No flights on file at the moment.";
        }

        JOptionPane.showMessageDialog(null, flightsRecord);
    }

    // Method to execute when user selects exit program : will show number of flights entered and the total revenue across all flights.
    public static void exitProgram(Flight[] flightRecords, int numFlights, double totalRevenue) {
        for (int x = 0; x < Flight.getNumFlights(); x++) {
            totalRevenue += flightRecords[x].getFlightRevenue();
        }
        String exitMessage = "Thank you for using this program \n";
        exitMessage += " Number of flights : " + numFlights + "\n" +
                " Total Revenue : " + String.format("$%.2f", totalRevenue);

        JOptionPane.showMessageDialog(null, exitMessage);
    }
}