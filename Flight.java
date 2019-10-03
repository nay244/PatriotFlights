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

    public Flight(String flightID) {
        if (flightID == null || flightID.equals("")) {
            throw new IllegalArgumentException(" Flight identifier cannot be blank");
        } else if (flightID.length() != 6) {
            throw new IllegalArgumentException(" Flight identifier must be 6 characters");
        } else if (flightID.substring(0,1).matches("[0-9]")) {
            throw new ArrayIndexOutOfBoundsException();
        } else if (flightID.substring(2,5).matches("[a-z]")) {
            throw new ArrayIndexOutOfBoundsException();
        } else {
            this.capitalizeFlightID = flightID.substring(0,2).toUpperCase();
        }
        this.flightID = this.capitalizeFlightID + flightID.substring(2);
    }

    public String getFlightID() { return this.flightID; }
    public String getFlightRoute() { return this.flightRoute; }
    public double getPriceGen() { return this.priceGen; }
    public double getPricePremium() { return this.pricePremium; }
    public int getSeatsEco() { return this.seatsEco; }
    public int getSeatsFC() { return this.seatsFC; }
    public double getFlightRevenue() { return this.flightRevenue; }

    public void setFlightRoute(String flightRoute) {
        if (flightRoute == null || flightRoute.equals("")) {
            throw new IllegalArgumentException(" Flight route cannot be blank");
        }
        this.flightRoute = flightRoute;
    }

    public static int getNumFlights() {
        return numFlights;
    }

    public void setPriceGen (double priceGen) {
        if (priceGen < MIN_PRICE || priceGen > MAX_PRICE) {
            throw new IllegalArgumentException("Price can only be set to values at or above "
                    + String.format("$%.2f", MIN_PRICE) + " and below " + String.format("$%.2f", MAX_PRICE));
        }
        this.priceGen = priceGen;
    }

    public void setPricePremium () {
        this.pricePremium = (priceGen + PREMIUM);
    }

    public void setSeatEco() {
        ++this.seatsEco;
    }

    public void setSeatFC() {
        ++this.seatsFC;
    }

    public static void addNumFlights() {
        ++numFlights;
    }

    public static void removeNumFlights() {
        --numFlights;
    }

    public void calcFlightRevenue() {
        this.flightRevenue = (this.priceGen * this.seatsEco) + (this.pricePremium * this.seatsFC);
    }

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
