package cinema;

public class CinemaDetailProgrammeLib extends CinemaDetailProgramme {
    private String filmlib;
    private int filmeduree;
    private String programmefilm;
    private String idsalle;

    public CinemaDetailProgrammeLib() throws Exception {
        this.setNomTable("cinema_detail_programme_lib");
    }

    public String getFilmlib() {
        return filmlib;
    }

    public void setFilmlib(String filmlib) {
        this.filmlib = filmlib;
    }

    public int getFilmeduree() {
        return filmeduree;
    }

    public void setFilmeduree(int filmeduree) {
        this.filmeduree = filmeduree;
    }

    public String getProgrammefilm() {
        return programmefilm;
    }

    public void setProgrammefilm(String programmefilm) {
        this.programmefilm = programmefilm;
    }

    public String getIdsalle() {
        return idsalle;
    }

    public void setIdsalle(String idsalle) {
        this.idsalle = idsalle;
    }
}