package cinema;

public class CinemaProgrammeLib extends CinemaProgramme {
    private String filmlib;
    private String sallelib;

    public CinemaProgrammeLib() throws Exception {
        this.setNomTable("cinema_programme_lib");
    }

    public String getFilmlib() {
        return filmlib;
    }

    public void setFilmlib(String filmlib) {
        this.filmlib = filmlib;
    }

    public String getSallelib() {
        return sallelib;
    }

    public void setSallelib(String sallelib) {
        this.sallelib = sallelib;
    }
}
