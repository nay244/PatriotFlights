// DATA DEFINITION CLASS for PatriotFlights
public class Flight {
    private String flightID;
    private String capitalizeFlightID;
    private String flightRoute;
    private double priceGen;
    private double pricePremium;
    private int seatsEco = 0;
    private int seatsFC = 0;
    private double flightRevenue;
    private static int numFlights = 0;
    public static final double PREMIUM = 250.00;
    public static final double MIN_PRICE = 0.00;
    public static final double MAX_PRICE = 10000.00;

    // No default constructor, not necessary for program
    /* Flight constructor to create a flight object with a name.
       @param flight identifier
       Requirements: Field cannot be blank, must be 6 characters, first 2 characters must be letters and last 4 digits.
    */
    public Flight(String flightID) {
        if (flightID == null || flightID.equals("")) {
            throw new IllegalArgumentException(" Flight identifier cannot be blank");
        } else if (flightID.length() != 6) {
            throw new IllegalArgumentException(" Flight identifier must be 6 characters");
        } else if (flightID.substring(0,1).matches("[0-9]+")) {
            throw new IllegalArgumentException(" First 2 characters must be letters.");
        } else if (!flightID.substring(2).matches("[0-9]+")) {
            throw new IllegalArgumentException(" Last 4 characters must be digits.");
        } else {
            this.capitalizeFlightID = flightID.substring(0,2).toUpperCase();
        }
        this.flightID = this.capitalizeFlightID + flightID.substring(2);
    }

    // Retrieves the ID of a single flight
    public String getFlightID() { return this.flightID; }
    // Retrieves the route of a single flight
    public String getFlightRoute() { return this.flightRoute; }
    // Retrieves the general ticket price of a single flight
    public double getPriceGen() { return this.priceGen; }
    // Retrieves the premium ticket price of a single flight
    public double getPricePremium() { return this.pricePremium; }
    // Retrieves amount of economy seats sold for a single flight
    public int getSeatsEco() { return this.seatsEco; }
    // Retrieves amount of first-class seats sold for a single flight
    public int getSeatsFC() { return this.seatsFC; }
    // Retrieves amount of revenue for a single flight based on ticket sales.
    public double getFlightRevenue() { return this.flightRevenue; }

    /*
        Retrieves the current number of flights inside the flightRecords[] array.
        @return numFlights ( total number of flights in array )
    */
    public static int getNumFlights() {
        return numFlights;
    }

    // Mutator to set the flight route for a single Flight object
    public void setFlightRoute(String flightRoute) {
        if (flightRoute == null || flightRoute.equals("") || flightRoute.matches("[0-9]+")) {
            throw new IllegalArgumentException(" Flight route cannot be blank or contain numbers.");
        } else {
            this.flightRoute = flightRoute;
        }
    }
    // Mutator to set the general ticket price for a single Flight object
    public void setPriceGen (double priceGen) {
        if (priceGen < MIN_PRICE || priceGen > MAX_PRICE) {
            throw new IllegalArgumentException("Price can only be set to values at or above "
                    + String.format("$%.2f", MIN_PRICE) + " and below " + String.format("$%.2f", MAX_PRICE));
        }
        this.priceGen = priceGen;
    }
    // Mutator to set premium ticket price for a Flight object based on price of general.
    public void setPricePremium () {
        this.pricePremium = (priceGen + PREMIUM);
    }
    // Mutator to increment the number of economy seats for a single flight.
    public void setSeatEco() {
        ++this.seatsEco;
    }
    // Mutator to increment the number of first-class seats for a single flight.
    public void setSeatFC() {
        ++this.seatsFC;
    }
    // special purpose method to increment the number of flights currently inside the flightRecords array.
    public static void addNumFlights() {
        ++numFlights;
    }
    // special purpose method to decrement the number of flights currently inside the flightRecords array.
    public static void removeNumFlights() {
        --numFlights;
    }
    /*
     Determine the revenue received from ticket sales for a single flight.

     @param  type of ticket (economy & first-class)
      (price of general ticket * economy seats) + (price of premium ticket * first-class seats)
     */
    public void calcFlightRevenue() {
        this.flightRevenue = (this.priceGen * this.seatsEco) + (this.pricePremium * this.seatsFC);
    }
    /*
     Print Summary of user input for the Flight object
     @return flightID, flightRoute, priceGen, pricePremium, seatsEco, seatsFC, flightRevenue
     */
    public String toString() {
        return "Flight ID : "+ this.getFlightID() + " ||" +
                " Flight Route : (" + this.getFlightRoute() + ")" + " ||" +
                " General Ticket Price : (" + String.format("$%.2f",this.getPriceGen()) + ")" + " ||" +
                " Premium Ticket Price : (" + String.format("$%.2f", this.getPricePremium()) + ")" + " ||" +
                " Economy Seats : (" + this.getSeatsEco() + ")" + " ||" +
                " First-Class Seats : (" + this.getSeatsFC() + ")" + " || " +
                " Flight Revenue : (" + String.format("$%.2f", this.getFlightRevenue()) + ")";
    }

}