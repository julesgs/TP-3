package com.example.tp3;

public class Calcul {

    private float bill;
    private float tip;
    private float nbPeople;

    public Calcul(float bill, float tip, float nbPeople) {
            this.bill = bill;
            this.tip = tip;
            this.nbPeople = nbPeople;

    }

    public float calculateTotal() {

        float totalAPayer = bill + (int)((tip / 100.0) * bill);

        return (float) totalAPayer / nbPeople;
    }

    public float calculateTipPerPeople() {
        return Math.round((float) tip / nbPeople * 100) / 100f;
    }
}
