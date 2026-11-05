package com.example.postgresqlreplication.util;

import lombok.experimental.UtilityClass;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

@UtilityClass
public class ProductFieldsValueGenerator {
    private static final List<String> ADJECTIVES = List.of(
            "Fresh", "Organic", "Premium", "Classic", "Natural", "Golden", "Crisp", "Smooth"
    );

    private static final List<String> NOUNS = List.of(
            "Apple", "Bread", "Cheese", "Juice", "Honey", "Rice", "Pasta", "Yogurt",
            "Butter", "Flour", "Sugar", "Salt", "Tea", "Coffee", "Milk", "Water"
    );
    
    public static String generateRandomName(ThreadLocalRandom random) {
        String adjective = ADJECTIVES.get(random.nextInt(ADJECTIVES.size()));
        String noun = NOUNS.get(random.nextInt(NOUNS.size()));
        return adjective + " " + noun;
    }

    public static BigDecimal generateRandomPrice(ThreadLocalRandom random) {
        double price = random.nextDouble(1.0, 10000.0);
        return BigDecimal.valueOf(price).setScale(2, RoundingMode.HALF_UP);
    }

    public static int generateRandomSale(ThreadLocalRandom random) {
        int shouldBeSale = random.nextInt(2);
        
        if (shouldBeSale == 1) {
            return random.nextInt(1, 100);
        }
        
        return 0;
    }
}
