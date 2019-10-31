package com.viktorija.coffeelist.utilities;
import com.viktorija.coffeelist.database.DrinkEntity;

import java.util.ArrayList;
import java.util.List;

public class SampleData {

    public static List<DrinkEntity> getAllDrinks() {
        List<DrinkEntity> allDrinks = new ArrayList<>();
        allDrinks.add(new DrinkEntity("Latte", "Also called a café latte, this drink is a single shot of espresso with three parts steamed milk. It is usually bigger than a cappuccino because it contains more milk and can be ordered with flavoring – usually vanilla, hazelnut or caramel."));
        allDrinks.add(new DrinkEntity("Ristretto", "A ristretto is an espresso shot that is extracted with the same amount of coffee but half the amount of water. The end result is a more concentrated and darker espresso extraction." ));
        allDrinks.add(new DrinkEntity("Cappuccino", "A cappuccino is similar to a latte. However the key difference between a latte and cappuccino is that a cappuccino has more foam and chocolate placed on top of the drink. Further a cappuccino is made in a cup rather than a tumbler glass."));
        allDrinks.add(new DrinkEntity("Mocha", "A mocha is a mix between a cappuccino and a hot chocolate. It is made by putting mixing chocolate powder with an espresso shot and then adding steamed milk and micro-foam into the beverage."));
        allDrinks.add(new DrinkEntity("Flat White", "A flat white is a coffee you’ll primarily find in Australia and New Zealand. It is made the same as a cappuccino expect it does not have any foam or chocolate on top."));
        return allDrinks;
    }
}

