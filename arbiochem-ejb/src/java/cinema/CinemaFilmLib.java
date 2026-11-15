package cinema;

public class CinemaFilmLib extends CinemaFilm {
    private String classificationlib;

    public CinemaFilmLib() throws Exception {
        this.setNomTable("cinema_film_lib");
    }

    public String getClassificationlib() {
        return classificationlib;
    }

    public void setClassificationlib(String classificationlib) {
        this.classificationlib = classificationlib;
    }
}