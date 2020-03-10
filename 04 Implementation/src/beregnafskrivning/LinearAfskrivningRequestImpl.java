package beregnafskrivning;

public class LinearAfskrivningRequestImpl implements LinearAfskrivningsRequest, AfskrivningRequest {
    int brugstid;
    double scrapvaerdi;
    double anskaffelsesvaerdi;
    Afskrivningsmetoder afskrivningsmetode;
    double beloeb;
    boolean erBergenet;



    public LinearAfskrivningRequestImpl(int brugstid, double scrapvaerdi, double anskaffelsesvaerdi) {
        this.brugstid = brugstid;
        this.scrapvaerdi = scrapvaerdi;
        this.anskaffelsesvaerdi = anskaffelsesvaerdi;
        afskrivningsmetode = Afskrivningsmetoder.LINEAER;
    }

    @Override
    public boolean erBeregnet() {
        return erBergenet;
    }

    @Override
    public void angivAfskrivning(double beloeb) {
        this.beloeb = beloeb;
        erBergenet = true;
    }

    @Override
    public double hentAfskrivning() {
        return beloeb;
    }

    @Override
    public Afskrivningsmetoder hentAfskrivningsmetode() {
        return afskrivningsmetode;
    }

    @Override
    public int hentBrugstid() {
        return brugstid;
    }

    @Override
    public double hentScrapvaerdi() {
        return scrapvaerdi;
    }

    @Override
    public double hentAnskaffelsesvaerdi() {
        return anskaffelsesvaerdi;
    }
}
