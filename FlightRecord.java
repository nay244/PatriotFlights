import javax.swing.JOptionPane;

public class FlightRecord {
    public static void main(String[] args) {
        final int MAX_NUM_FLIGHTS = 2;
        final int MAX_SEATS_ECO = 2;
        final int MAX_SEATS_FC = 2;
        double totalRevenue = 0;
        int numFlights = 0;
        Flight[] flights = new Flight[MAX_NUM_FLIGHTS];

        int menuChoice = getMenuOption();
        while (menuChoice != 5) {
            switch (menuChoice) {
                case 1:
                    if (numFlights < flights.length) {
                        createFlight(flights, numFlights);
                        ++numFlights;
                    } else {
                        JOptionPane.showMessageDialog(null, "No more additional flights can be added. Currently full.");
                    }
                    JOptionPane.showMessageDialog(null, numFlights);
                    break;

                case 2:
                    if (numFlights > 0) {
                        removeFlight(flights, getRemovalChoice(flights));
                        --numFlights;
                    } else {
                        JOptionPane.showMessageDialog(null, "There are no flights to remove.");
                    }
                    break;

                case 3:
                    if (numFlights > 0) {
                        sellFlightTicket(flights, getTicketSaleChoice(flights), MAX_SEATS_ECO, MAX_SEATS_FC);
                    } else {
                        JOptionPane.showMessageDialog(null, "There are no flights available.");
                    }
                    break;

                case 4:
                    displayFlights(flights, numFlights);
                    break;

                default:
                    throw new RuntimeException("Please select a valid menu choice.");
            }
            menuChoice = getMenuOption();
        }

        if(menuChoice == 5) {
            exitProgram(flights, numFlights, totalRevenue);
        }
    }



    public static int getMenuOption() {
        int choice;

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

    public static void createFlight(Flight[] flights, int numFlights) {
        if (numFlights < flights.length) {

            boolean flightIDSet = false;
            do {
                try {
                    Flight aFlight = new Flight(JOptionPane.showInputDialog("Enter the flight ID: \n" + "(First 2 characters must be letters and the remaining 4 digits)" ));
                    flightIDSet = true;
                    flights[numFlights] = aFlight;
                } catch (IllegalArgumentException e) {
                    JOptionPane.showMessageDialog(null, " Flight could not be added." + e.getMessage());
                } catch(ArrayIndexOutOfBoundsException exception) {
                    JOptionPane.showMessageDialog(null, " First two characters must be letters.");
                }
            } while (!flightIDSet);

            boolean flightRouteSet = false;
            do {
                try {
                    flights[numFlights].setFlightRoute(JOptionPane.showInputDialog("Enter flight route: "));
                    flightRouteSet = true;
                } catch (IllegalArgumentException e) {
                    JOptionPane.showMessageDialog(null, " Flight could not be added." + e.getMessage());
                }
            } while (!flightRouteSet);

            boolean priceGenSet = false;
            do {
                try {
                    flights[numFlights].setPriceGen(Double.parseDouble(JOptionPane.showInputDialog("Enter the general ticket price for flight: ")));
                    priceGenSet = true;
                } catch (NumberFormatException e) {
                    JOptionPane.showMessageDialog(null, "Please enter a valid ticket price");
                } catch (IllegalArgumentException e) {
                    JOptionPane.showMessageDialog(null, e.getMessage());
                }
            } while (!priceGenSet);

            flights[numFlights].setPricePremium();
            Flight.addNumFlights();
        } else {
            JOptionPane.showMessageDialog(null, "No more additional flights can be added. Currently full.");
        }
    }

    public static void removeFlight(Flight[] flights, int removalChoice) {
        int numFlights = Flight.getNumFlights();
        if(numFlights > 0) {
            flights[removalChoice] = null;
            Flight.removeNumFlights();
        } else {
            JOptionPane.showMessageDialog(null, "No more flights to remove.");
        }
    }

    public static int getRemovalChoice(Flight[] flights) {
        int numFlight = Flight.getNumFlights();
        int removeChoice = 0;
        if(numFlight > 0) {
            do {
                try {
                    removeChoice = Integer.parseInt(JOptionPane.showInputDialog(displayAvailableFlights(flights,"remove")));
                } catch (NumberFormatException e) {
                    removeChoice = 0;
                }
                if (removeChoice < 1 || removeChoice > flights.length) {
                    JOptionPane.showMessageDialog(null, "Invalid choice. Please select a valid flight to remove.");
                }
            } while (removeChoice < 1 || removeChoice > flights.length);
        } else {
            JOptionPane.showMessageDialog(null, "There are no flights to remove.");
        }

        return removeChoice - 1;
    }

    public static int getTicketSaleChoice(Flight[] flights) {
        int numFlight = Flight.getNumFlights();
        int sellChoice = 0;
        if(numFlight > 0) {
            do {
                try {
                    sellChoice = Integer.parseInt(JOptionPane.showInputDialog(displayAvailableFlights(flights,"sell")));
                } catch (NumberFormatException e) {
                    sellChoice = -1;
                }
                if (sellChoice < 1 || sellChoice > flights.length) {
                    JOptionPane.showMessageDialog(null, "Invalid choice. Please select a valid flight.");
                }
            } while (sellChoice < 1 || sellChoice > flights.length);
        } else {
            JOptionPane.showMessageDialog(null, "There are no flights available at the moment.");
        }

        return sellChoice - 1;
    }

    public static void sellFlightTicket(Flight[] flights, int sellChoice, int MAX_SEATS_ECO, int MAX_SEATS_FC) {
        int seatsEco = flights[sellChoice].getSeatsEco();
        int seatsFC = flights[sellChoice].getSeatsFC();
        int choice;
        do {
            try {
                choice = Integer.parseInt(JOptionPane.showInputDialog("Please select ticket type to sell" +
                        "\n(1) Economy" +
                        "\n(2) First-Class" +
                        "\n(0) Go Back"));
                if(choice < 0 || choice > 2) {
                    JOptionPane.showMessageDialog(null, "Please select a valid option.");
                } else {
                    if (choice == 1 && seatsEco <= MAX_SEATS_ECO) {
                        flights[sellChoice].setSeatEco();
                    } else if (choice == 2 && seatsFC <= MAX_SEATS_FC) {
                        flights[sellChoice].setSeatFC();
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
        flights[sellChoice].calcFlightRevenue();
        double flightRevenue = flights[sellChoice].getFlightRevenue();
        JOptionPane.showMessageDialog(null, "Seats Eco : " + seatsEco + "\n" + "Seats FC: " + seatsFC + "\n" + flightRevenue);
    }

    public static String displayAvailableFlights(Flight[] flights, String option) {
        String output = "Please select a flight to " + option + " or (0) to quit" + "\n";
        for (int x = 0; x < Flight.getNumFlights(); x++) {
            output += (x + 1) + ": " + flights[x].toString() + "\n";
        }
        return output;
    }

    public static void displayFlights(Flight[] flights, int numFlights) {
        String output = "All Available Flights \n";
        if(numFlights > 0) {
            for (int x = 0; x < Flight.getNumFlights(); x++) {
                output += (x + 1) + ": " + flights[x].toString() + "\n";
            }
        } else {
            output = "No flights on file at the moment.";
        }

        JOptionPane.showMessageDialog(null, output);
    }

    public static void exitProgram(Flight[] flights, int numFlights, double totalRevenue) {
        for (int x = 0; x < Flight.getNumFlights(); x++) {
            totalRevenue += flights[x].getFlightRevenue();
        }
        String output = "Thank you for using this program \n";
        output += " Number of flights entered: " + numFlights + "\n" +
                " Total Revenue : " + String.format("$%.2f", totalRevenue);

        JOptionPane.showMessageDialog(null, output);
    }
}
