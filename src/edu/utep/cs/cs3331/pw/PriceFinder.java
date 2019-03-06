package edu.utep.cs.cs3331.pw;

import java.text.DecimalFormat;
import java.util.Random;

public class PriceFinder {

    public PriceFinder(String URL) {
        String URL1 = URL;
    }

    public double getRandomPrice(){
        Random ran = new Random();
        double maxPrice = 500.00;
        double minPrice = 50.00;
        double randomPrice = ran.doubles(minPrice, (maxPrice + 1)).findFirst().getAsDouble();

        DecimalFormat df = new DecimalFormat("#.00");
        String priceFormated = df.format(randomPrice);
        randomPrice = Double.parseDouble(priceFormated);

        return randomPrice;
    }
}