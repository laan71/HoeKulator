package entities;

import entities.exceptions.NegativBeloebException;

public class VarelagerPrimoImpl implements VarelagerPrimo, Observable {
    private double beloeb;
    ObserverManager observerManager;

    public VarelagerPrimoImpl() { observerManager = newObserverManager(); }

    @Override
    public void angivBeloeb(double beloeb) throws NegativBeloebException{
        if (beloeb <= 0) {
            throw new NegativBeloebException("Beløbet må ikke være negativt");
        }
        this.beloeb = beloeb;
        observerManager.notificerObservere(this);
    }

    @Override
    public void tilmeldObserver(Observer observer) {observerManager.tilmeldObserver(observer);}

    @Override
    public void afmeldObserver(Observer observer) {observerManager.afmeldObserver(observer);};

    public double hentBeloeb() {
        return beloeb;
    }

    protected ObserverManager newObserverManager() { return new ObserverManagerImpl();}
}
