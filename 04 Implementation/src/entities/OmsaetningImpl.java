package entities;

public class OmsaetningImpl implements Omsaetning, Observable {
    private ObserverManager observerManager;
    private Bruttofortjeneste bruttofortjeneste;
    private Vareforbrug vareforbrug;
    private PrimoAarsomsaetning primoAarsomsaetning;
    private Procentstigning procentstigning;
    private Afsaetning afsaetning;
    private Salgspris salgspris;
    private double beloeb;

    public OmsaetningImpl() {
        observerManager = newObserverManager();
    }

    @Override
    public void anvendBruttofortjenesteOgVareforbrug(Bruttofortjeneste bruttofortjeneste, Vareforbrug vareforbrug) {
        this.bruttofortjeneste = bruttofortjeneste;
        this.vareforbrug = vareforbrug;
        this.primoAarsomsaetning = null;
        this.procentstigning = null;
        this.afsaetning = null;
        this.salgspris = null;

        observerManager.notificerObservere(this);
    }

    @Override
    public void anvendAfsaetningOgSalgspris(Afsaetning afsaetning, Salgspris salgspris) {
        this.afsaetning = afsaetning;
        this.salgspris = salgspris;
        this.primoAarsomsaetning = null;
        this.procentstigning = null;
        this.bruttofortjeneste = null;
        this.vareforbrug = null;

        observerManager.notificerObservere(this);
    }

    @Override
    public void anvendPrimoAarsomsaetningOgProcentstigning(PrimoAarsomsaetning primoAarsomsaetning, Procentstigning procentstigning) {
        this.primoAarsomsaetning = primoAarsomsaetning;
        this.procentstigning = procentstigning;
        this.salgspris = null;
        this.vareforbrug = null;
        this.bruttofortjeneste = null;
        this.afsaetning = null;

        observerManager.notificerObservere(this);
    }

    public void hentOmsaetning() {
        if (primoAarsomsaetning != null && procentstigning != null) {
            beloeb = primoAarsomsaetning.hentBeloeb()*procentstigning.hentDecimaltal();
        }
        else if (vareforbrug != null && bruttofortjeneste != null){
            beloeb = vareforbrug.hentBeloeb() + bruttofortjeneste.hentbeloeb();
        }

        else if (salgspris != null && afsaetning != null) {
            beloeb = salgspris.hentPris() * afsaetning.hentAntal();
        }

        observerManager.notificerObservere(this);
    }


    @Override
    public void tilmeldObserver(Observer observer) {

    }

    @Override
    public void afmeldObserver(Observer observer) {

    }

    protected ObserverManager newObserverManager() {
        return new ObserverManagerImpl();
    }
}
