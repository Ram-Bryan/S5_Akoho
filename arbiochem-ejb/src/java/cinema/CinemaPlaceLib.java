package cinema;

public class CinemaPlaceLib extends CinemaPlace {
    private String rangelib;
    private String sallelib;
    private String categorierangelib;
    private String idsalle;   // ← AJOUT

    public CinemaPlaceLib() throws Exception {
        this.setNomTable("cinema_place_lib");
    }

    public String getRangelib() {
        return rangelib;
    }

    public void setRangelib(String rangelib) {
        this.rangelib = rangelib;
    }

    public String getSallelib() {
        return sallelib;
    }

    public void setSallelib(String sallelib) {
        this.sallelib = sallelib;
    }

    public String getCategorierangelib() {
        return categorierangelib;
    }

    public void setCategorierangelib(String categorierangelib) {
        this.categorierangelib = categorierangelib;
    }

    public String getIdsalle() {
        return idsalle;
    }

    public void setIdsalle(String idsalle) {
        this.idsalle = idsalle;
    }
}