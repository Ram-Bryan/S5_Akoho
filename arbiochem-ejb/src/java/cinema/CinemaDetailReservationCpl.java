package cinema;

public class CinemaDetailReservationCpl extends CinemaDetailReservation {
    private String placelib;
    private String rangelib;
    private String sallelib;
    private double prix;

    public CinemaDetailReservationCpl() throws Exception {
        this.setNomTable("cinema_detail_reservation_cpl");
    }

    public String getPlacelib() { return placelib; }
    public void setPlacelib(String placelib) { this.placelib = placelib; }

    public String getRangelib() { return rangelib; }
    public void setRangelib(String rangelib) { this.rangelib = rangelib; }

    public String getSallelib() { return sallelib; }
    public void setSallelib(String sallelib) { this.sallelib = sallelib; }

    public double getPrix() { return prix; }
    public void setPrix(double prix) { this.prix = prix; }
}