package com.example.tp3;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;

import java.io.*;
import java.util.*;

public class HelloController {

    @FXML
    public Button btn_calcul;
    public TextField field_bill;
    public TextField field_tip;
    public TextField field_nbpeople;
    public Label value_tip;
    public Label value_total;
    public Label error_message;
    public TextField fielddate;

    @FXML
    protected void onCalculClick() {
        try {

            String billStr = field_bill.getText();
            String tipStr = field_tip.getText();
            String nbpeopleStr = field_nbpeople.getText();

            float bill = this.verif("Bill", billStr);
            float tip = this.verif("Tip", tipStr);
            float nbpeople = this.verif("Nbpeople", nbpeopleStr);

            String date = fielddate.getText();

            verif_calendar(date);

            Calcul c = new Calcul(bill, tip, nbpeople);
            value_total.setText(String.valueOf(c.calculateTotal()));
            value_tip.setText(String.valueOf(c.calculateTipPerPeople()));
            error_message.setText("");

            List<String> lesDates = getDates();

            boolean isExist = dateExist(lesDates, date);

            if (!isExist) {
                writeFichier(date, billStr, tipStr, nbpeopleStr);
            } else {
                List<String> updatedDates = removeDate(lesDates, date);

                System.out.println(updatedDates);

                clear("calculs.txt");

                for (String date1 : updatedDates) {
                    writeFichier(date1, billStr, tipStr, nbpeopleStr);
                }
            }

        } catch (Exception e) {
            error_message.setText(e.getMessage());
        }
    }

    public float verif(String nomChamp, String message) throws NumberFormatException {

        if (message.isEmpty()) {
            throw new NumberFormatException("Le format de " + nomChamp + " ne peut pas etre vide");
        }

        try{
            return Float.parseFloat(message);
        }catch(NumberFormatException e){
            throw new NumberFormatException("Le format de " + nomChamp + " doit etre de type entier");
        }
    }

    public void verif_calendar(String date) throws IllegalArgumentException {

        if (!date.matches("\\d{2}-\\d{2}-\\d{4}")) {
            throw new IllegalArgumentException("Le format de la date est incorrect");
        }

        String jour = date.substring(0, 2);
        verif_numbers("jours", jour, 1, 31);
        String mois = date.substring(3, 5);
        verif_numbers("mois", mois, 1, 12);
        String annee = date.substring(6, 10);
        verif_numbers("année", annee, 1990,2024 );

    }

    public void verif_numbers(String nomChamp, String valeurChamp, int min, int max) throws NumberFormatException {
        try {
            int x = Integer.parseInt(valeurChamp);

            if (x < min || x > max) {
                throw new IllegalArgumentException("'" +nomChamp + "' doit être compris entre " + min + " et " + max);
            }
        } catch (NumberFormatException e) {
            throw new NumberFormatException("Le format de " + nomChamp + " doit être de type entier");
        }
    }

    private void writeFichier(String date, String bill, String tip, String nbpeople) {
        try {

            String filename = "calculs.txt";

            File nomFichier = new File(filename);
            FileOutputStream flux = new FileOutputStream(nomFichier, true);

            String texte = date + "; " + bill + "; " + tip + "; " + nbpeople + "\n";

            flux.write(texte.getBytes());
            flux.close();

        } catch (IOException e) {
            error_message.setText(e.getMessage());
        }
    }

    private List<String> getDates() throws FileNotFoundException {

        List<String> lesDates = new ArrayList<>();

        try {
            FileReader fileReader = new FileReader("calculs.txt");
            BufferedReader reader = new BufferedReader(fileReader);
            while (reader.ready()) {
                String ligneFile = reader.readLine();

                String laDate = ligneFile.split(";")[0];
                lesDates.add(laDate);
            }
            reader.close();
            fileReader.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return lesDates;
    }

    private boolean dateExist(List<String> lesDates, String laDate) {
        if (lesDates.contains(laDate)) {
            return true;
        }
        return false;
    }

    private List<String> removeDate(List<String> lesDates, String laDate) {

        List<String> updatedList = new ArrayList<>(lesDates);
        updatedList.remove(laDate);
        return updatedList;

    }

    private void clear(String filename) {
        try {
            FileWriter writer = new FileWriter(filename, false);
            writer.close();
        } catch (IOException e) {
            error_message.setText(e.getMessage());
        }
    }
}