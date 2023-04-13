import java.math.BigDecimal;
import java.math.RoundingMode;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.Scanner;

import static java.math.BigDecimal.*;

public class W101Calculator {


    // Convert a buff percentage to a decimal multiplier
    public BigDecimal convertBuff(BigDecimal num) {
        return ONE.add(num.multiply(valueOf(.01))); // updated to return 1 + num * 0.01
    }

    public static ArrayList<BigDecimal> displayBuffs(ArrayList<BigDecimal> buffs){
        ArrayList<BigDecimal> newBuffs = new ArrayList<>();
        for (BigDecimal buff : buffs) {
            BigDecimal newBuff = valueOf(100).multiply(buff.subtract(ONE).setScale(0,RoundingMode.FLOOR));
            newBuffs.add(newBuff);
        }
        return newBuffs;
    }

    // Convert a debuff percentage to a decimal multiplier
    public BigDecimal convertDebuff(BigDecimal num) {
        return ONE.subtract(num.multiply(valueOf(.01))); // updated to return 1 - num * 0.01
    }

    public static ArrayList<BigDecimal> displayWeaknesses(ArrayList<BigDecimal> debuffs){
        ArrayList<BigDecimal> newDebuffs = new ArrayList<>();
        for (BigDecimal debuff : debuffs) {
            BigDecimal newDebuff = valueOf(100).multiply(ONE.subtract(debuff).setScale(0, RoundingMode.FLOOR));
            newDebuffs.add(newDebuff);
        }
        return newDebuffs;
    }
    public static BigDecimal getShieldValue(BigDecimal input) {
        BigDecimal result = valueOf(1).subtract(input.multiply(valueOf(.01)));
        DecimalFormat df = new DecimalFormat("#.##");
        return BigDecimal.valueOf(Double.parseDouble(df.format(result)));
    }

    public static BigDecimal calculateTotalDamageTest(BigDecimal baseDamage, BigDecimal damageMultiplier, BigDecimal blades, BigDecimal traps, BigDecimal weaknesses, BigDecimal aura, BigDecimal bubble) {
        final BigDecimal multiply0 = baseDamage.multiply(damageMultiplier).setScale(0, RoundingMode.DOWN);
        final BigDecimal multiply1 = multiply0.multiply(aura).setScale(0, RoundingMode.DOWN);
        final BigDecimal multiply2 = multiply1.multiply(bubble).setScale(0, RoundingMode.DOWN);
        final BigDecimal multiply3 = multiply2.multiply(blades).setScale(0, RoundingMode.DOWN);
        final BigDecimal multiply4 = multiply3.multiply(traps).setScale(0, RoundingMode.DOWN);
        return multiply4.multiply(weaknesses).setScale(0, RoundingMode.DOWN);
    }


    public static BigDecimal calculateCriticalDamage(BigDecimal total, BigDecimal critMod) {
        return total.multiply(critMod).setScale(0, RoundingMode.DOWN);
    }

    public BigDecimal getBuffValue(BigDecimal input) {
        return convertBuff(input);
    }

    public BigDecimal getDebuffValue(BigDecimal input) {
        System.out.print("Debuff value: ");
        return convertDebuff(input);
    }

    public BigDecimal getDamageMultiplier(BigDecimal input) {
        return convertBuff(input);
    }

    public BigDecimal getPierceValue(BigDecimal input) {
        return input.multiply(valueOf(.01));
    }

    public BigDecimal getResistValue(BigDecimal input) {
        return input.multiply(valueOf(.01));
    }

    public static BigDecimal calculateProduct(ArrayList<BigDecimal> list) {
        BigDecimal product = BigDecimal.valueOf(1.0);
        for (BigDecimal d : list) {
            product = product.multiply(d);
        }
        return product;
    }

    public static BigDecimal addShields(BigDecimal shield) {
        return valueOf(1).subtract(shield);
    }

    public static BigDecimal shieldsTotalValue(ArrayList<BigDecimal> list) {
        BigDecimal sum = BigDecimal.valueOf(0.0);
        for (BigDecimal aDouble : list) {
            sum = sum.add(aDouble);
        }
        DecimalFormat df = new DecimalFormat("#.##");
        return BigDecimal.valueOf(Double.parseDouble(df.format(sum)));
    }

    public static BigDecimal subtractFromOne(BigDecimal temp) {
        temp = ONE.subtract(temp);
        return temp;
    }


    public static void main(String[] args) {

        W101Calculator calculator = new W101Calculator();
        Scanner input = new Scanner(System.in);
        Scanner scanner = new Scanner(System.in);

        //records all blade values
        ArrayList<BigDecimal> bladeList = new ArrayList<>();
        BigDecimal blades = valueOf(1);

        //records all trap values
        ArrayList<BigDecimal> trapList = new ArrayList<>();
        BigDecimal traps = valueOf(1);

        //records all weakness values
        ArrayList<BigDecimal> weaknessList = new ArrayList<>();
        BigDecimal weaknesses = valueOf(1);

        //records the total multiplier of the shields
        ArrayList<BigDecimal> shieldsList = new ArrayList<>();
        BigDecimal shields;

        //records the total added value of all shields
        ArrayList<BigDecimal> shieldValue = new ArrayList<>();
        BigDecimal allShields = valueOf(0);

        //records the originally entered value of all the shields
        ArrayList<BigDecimal> originalShields = new ArrayList<>();

        //records the value of a shield after it has been partially pierced
        BigDecimal piercedShield;

        //value of the most recently imputed shield
        BigDecimal recentShield = ZERO;

        //sets up an index for the most recent value in each shield array
        int lastShieldValueIndex = 0;
        int lastShieldListIndex = 0;
        int lastOGShieldIndex = 0;


        BigDecimal aura = valueOf(1); //only one aura value can be recorded
        BigDecimal bubble = valueOf(1); //only one bubble value can be recorded

        //base damage a spell does (printed damage)
        System.out.print("Spell DMG: ");
        BigDecimal spellDamage = (input.nextBigDecimal());

        //the amount of damage from gear/pets/etc...
        System.out.print("DMG: ");
        BigDecimal characterDamage = (calculator.getDamageMultiplier(input.nextBigDecimal()));

        //additional damage received from a critical hit
        System.out.print("Crit Mod: ");
        BigDecimal critMod = (calculator.getDamageMultiplier(input.nextBigDecimal()));

        //pierce given by gear/enchants/pets/etc
        System.out.print("Pierce: ");
        BigDecimal pierce = (calculator.getPierceValue(input.nextBigDecimal()));
        BigDecimal ogPierce = pierce;

        //enemy resist to the spells school of damage
        System.out.print("Enemy Resist: ");
        BigDecimal resist = (calculator.getResistValue(input.nextBigDecimal()));
        BigDecimal ogResist = resist;


        label:
        while (true) {

            //calculates the total before shields and resist
            BigDecimal total = calculateTotalDamageTest(spellDamage, characterDamage, blades, traps, weaknesses, aura, bubble);

            //checks if you can pierce all current shields
            if (pierce.compareTo(allShields) >= 0) {
                pierce = pierce.subtract(allShields);

                //checks for left over pierce
                if (pierce.compareTo(resist) > 0) {
                    pierce = valueOf(0);
                    resist = valueOf(0);
                }

                //final calculation if there was left over pierce from all the shields
                total = total.multiply(ONE.subtract(resist.subtract(pierce))).setScale(0, RoundingMode.DOWN);

                //pierces each shield one by one
            } else {
                do {
                    pierce = pierce.subtract(shieldValue.get(shieldValue.size() - 1));
                    if (pierce.compareTo(ZERO) > 0) { //this section removes shields that no longer have a positive value
                        shieldValue.remove(shieldValue.size() - 1);
                        allShields = (shieldsTotalValue(shieldValue));
                        shieldsList.remove(shieldsList.size() - 1);
                        lastShieldValueIndex = shieldValue.size() - 1;
                        lastShieldListIndex = shieldsList.size() - 1;

                        //finds the remaining value of the shield after pierce is all used up and changes the value within the arrays
                    } else {
                        piercedShield = pierce.abs();
                        shieldValue.set(lastShieldValueIndex, piercedShield);
                        shieldsList.set(lastShieldListIndex, addShields(piercedShield));
                    }
                    //new total multiplier for shields
                    shields = (calculateProduct(shieldsList));


                } while (pierce.compareTo(valueOf(0)) > 0);

                //final damage calculation for the true total
                total = total.multiply(shields).multiply(ONE.subtract(resist)).setScale(0, RoundingMode.DOWN);
            }

            //checks to see if any shields were removed during the pierce section
            if (!shieldsList.isEmpty()) {
                shieldValue.set(lastShieldValueIndex, addShields(recentShield));
                shieldsList.set(lastShieldListIndex, (recentShield));
            }

            //resets pierce and resist to the user imputed values
            pierce = ogPierce;
            resist = ogResist;

            //makes sure that the arrays are restored to the user imputed values
            if (!originalShields.equals(shieldValue)) {
                shieldValue.clear();
                shieldsList.clear();
                for (int i = 0; i <= lastOGShieldIndex; i++) {
                    BigDecimal temp = originalShields.get(i);
                    shieldValue.add(temp);
                    shieldsList.add(subtractFromOne(temp));
                }
            }


            System.out.println();
            System.out.println("'help' for list of commands");
            System.out.println();
            System.out.println("Total: " + total + "\nCritical: " + calculateCriticalDamage(total, critMod));
            System.out.println();
            System.out.print("Enter a command: ");
            String command = scanner.nextLine();

            switch (command) {
                case "b": {
                    try {
                        System.out.print("Blade value: ");
                        BigDecimal buff = (calculator.getBuffValue(input.nextBigDecimal()));
                        bladeList.add(buff); //adds buff to blades list
                        blades = (calculateProduct(bladeList));
                        break;
                    } catch (InputMismatchException e) {
                        System.out.println("Invalid input. Please enter a valid number.");
                        input.next();
                        break;
                    }

                }
                case "rb":
                    if (!bladeList.isEmpty()) { // make sure the list is not empty
                        bladeList.remove(bladeList.size() - 1); // remove the last element from the list
                        blades = calculateProduct(bladeList);
                    }
                    break;

                case "0b":
                    bladeList.clear();
                    blades = calculateProduct(bladeList);
                    break;

                case "t": {
                    try {
                        System.out.print("Trap value: ");
                        BigDecimal buff = (calculator.getBuffValue(input.nextBigDecimal()));
                        trapList.add(buff);
                        traps = calculateProduct(trapList);
                    } catch (InputMismatchException e) {
                        System.out.println("Invalid input. Please enter a number.");
                        input.nextLine();
                    }
                    break;

                }
                case "rt":
                    if (!trapList.isEmpty()) {
                        trapList.remove(trapList.size() - 1);
                        traps = calculateProduct(trapList);
                    }
                    break;

                case "0t":
                    trapList.clear();
                    traps = calculateProduct(trapList);
                    break;

                case "w": {
                    try {
                        System.out.print("Weakness value: ");
                        BigDecimal debuff = (calculator.getDebuffValue(input.nextBigDecimal()));
                        weaknessList.add(debuff);
                        weaknesses = calculateProduct(weaknessList);
                    } catch (InputMismatchException e) {
                        System.out.println("Invalid input. Please enter a number.");
                        input.nextLine();
                    }
                    break;

                }
                case "rw":
                    if (!weaknessList.isEmpty()) {
                        weaknessList.remove(weaknessList.size() - 1);
                        weaknesses = calculateProduct(weaknessList);
                    }
                    break;

                case "0w":
                    weaknessList.clear();
                    weaknesses = calculateProduct(weaknessList);
                    break;

                case "s": {
                    try {
                        System.out.print("Shield value: ");
                        recentShield = getShieldValue(input.nextBigDecimal());
                        shieldsList.add(recentShield);
                        shieldValue.add(addShields(recentShield));
                        originalShields.add(addShields(recentShield));
                        allShields = shieldsTotalValue(shieldValue);
                        lastShieldValueIndex = (shieldValue.size() - 1);
                        lastShieldListIndex = (shieldsList.size() - 1);
                        lastOGShieldIndex = ((originalShields.size() - 1));
                    } catch (InputMismatchException e) {
                        System.out.println("Invalid input. Please enter a number.");
                        input.nextLine();
                    }
                    break;
                }

                case "rs":
                    if (!shieldsList.isEmpty()) {
                        shieldsList.remove(shieldsList.size() - 1);
                        shieldValue.remove(shieldValue.size() - 1);
                        originalShields.remove(originalShields.size() - 1);
                        if (!shieldsList.isEmpty()) {
                            allShields = shieldsTotalValue(shieldValue);
                        } else {
                            allShields = ZERO;
                        }
                    } else {
                        System.out.println("There are no shields to remove!");
                    }
                    break;

                case "0s":
                    shieldsList.clear();
                    shieldValue.clear();
                    originalShields.clear();
                    allShields = shieldsTotalValue(shieldValue);
                    break;

                case "a":
                    System.out.print("Aura Value: ");
                    try {
                        aura = (calculator.getBuffValue(input.nextBigDecimal()));
                    } catch (InputMismatchException e) {
                        System.out.println("Invalid input. Please enter a number.");
                        input.nextLine();
                    }
                    break;

                case "0a":
                    aura = valueOf(1);
                    break;

                case "bub":
                    System.out.print("Bubble value: ");
                    try {
                        bubble = (calculator.getBuffValue(input.nextBigDecimal()));
                    } catch (InputMismatchException e) {
                        System.out.println("Invalid input. Please enter a number.");
                        input.nextLine();
                    }
                    break;

                case "0bub":
                    bubble = valueOf(1);
                    break;

                case "00":
                    bladeList.clear();
                    blades = calculateProduct(bladeList);

                    trapList.clear();
                    traps = calculateProduct(trapList);

                    weaknessList.clear();
                    weaknesses = calculateProduct(weaknessList);

                    shieldsList.clear();
                    shieldValue.clear();
                    originalShields.clear();
                    allShields = shieldsTotalValue(shieldValue);

                    aura = valueOf(1);
                    bubble = valueOf(1);
                    break;

                case "ns":
                    try {
                        System.out.print("Enter new spell damage: ");
                        spellDamage = (input.nextBigDecimal());
                    } catch (InputMismatchException e) {
                        System.out.println("Invalid input! Please enter a valid decimal number.");
                    }
                    break;

                case "nd":
                    try {
                        System.out.print("Enter new DMG: ");
                        characterDamage = (calculator.getDamageMultiplier(input.nextBigDecimal()));
                    } catch (InputMismatchException e) {
                        System.out.println("Invalid input! Please enter a valid decimal number.");
                    }
                    break;

                case "np":
                    try {
                        System.out.print("Enter new pierce: ");
                        ogPierce = (calculator.getPierceValue(input.nextBigDecimal()));
                        pierce = ogPierce;
                    } catch (InputMismatchException e) {
                        System.out.println("Invalid input! Please enter a valid decimal number.");
                    }
                    break;

                case "nr":
                    try {
                        System.out.print("Enter new enemy resist: ");
                        ogResist = (calculator.getResistValue(input.nextBigDecimal()));
                        resist = ogResist;
                    } catch (InputMismatchException e) {
                        System.out.println("Invalid input! Please enter a valid decimal number.");
                    }
                    break;

                case "nc":
                    try {
                        System.out.println("Enter new crit mod: ");
                        critMod = (calculator.getDamageMultiplier(input.nextBigDecimal()));
                    } catch (InputMismatchException e) {
                        System.out.println("Invalid input! Please enter a valid decimal number.");
                    }
                    break;

                case "check":
                    System.out.println("Spell Damage: " + spellDamage);
                    System.out.println("Player Damage: " + valueOf(100).multiply(characterDamage.subtract(ONE)).setScale(0,RoundingMode.FLOOR));
                    System.out.println("Critical Multiplier: " + valueOf(100).multiply(critMod.subtract(ONE)).setScale(0,RoundingMode.FLOOR));
                    System.out.println("Pierce: " + valueOf(100).multiply(ONE.subtract(pierce)).setScale(0,RoundingMode.FLOOR));
                    System.out.println("Enemy Resist: " + valueOf(100).multiply(ONE.subtract(resist)).setScale(0,RoundingMode.FLOOR));

                    if (aura.compareTo(ONE) == 0) {
                        System.out.println("No Aura");
                    } else {
                        System.out.println("Aura: " + valueOf(100).multiply(aura.subtract(ONE)).setScale(0,RoundingMode.FLOOR));
                    }

                    if (bubble.compareTo(ONE) == 0) {
                        System.out.println("No Bubble");
                    } else {
                        System.out.println("Bubble: " + valueOf(100).multiply(bubble.subtract(ONE)).setScale(0,RoundingMode.FLOOR));
                    }

                    if (!bladeList.isEmpty()) {
                        System.out.println("Blades: " + displayBuffs(bladeList));
                    }else {
                        System.out.println("No Blades");
                    }

                    if (!trapList.isEmpty()){
                        System.out.println("Traps: " + displayBuffs(trapList));
                    }else {
                        System.out.println("No Traps");
                    }

                    if (!weaknessList.isEmpty()){
                        System.out.println("Weaknesses: " + displayWeaknesses(weaknessList));
                    }else {
                        System.out.println("No Weaknesses");
                    }

                    if (!originalShields.isEmpty()){
                        System.out.println("Shields: " + displayWeaknesses(originalShields));
                    }else {
                        System.out.println("No Shields");
                    }

                    break;

                case "q":
                    break label;

                case "help":
                    System.out.println("b\tadds a blade\nrb\tremoves the value of the last blade\n0b\tresets all blade\nt\tadds a trap\nrt\tremoves the value of the last trap\n0t\tresets all traps\nw\tadds a weakness\nrw\tre adds the value lost from the last weakness\n0w\tresets all weaknesses\ns\tadds a shield\nrs\tremoves the value of the last shield\n0s\tresets all shields\na\tadds an aura\n0a\tremoves an aura\nbub\tadds a bubble\n0bub\tremoves a bubble\n00\tresets ALL buffs and weaknesses\nns\tchanges the spells base dmg value\nnd\tchanges the dmg value\nnp\tchanges the pierce value\nnr\tchanges the resist value\nnc\tchanges the crit mod\ncheck\tdisplays all current buffs and debuffs\nq\tstops the code");

                case default:
                    System.out.println("Invalid command");
                    break;


            }
        }
    }
}
