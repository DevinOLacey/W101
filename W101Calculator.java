import java.math.BigDecimal;
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

    // Convert a debuff percentage to a decimal multiplier
    public BigDecimal convertDebuff(BigDecimal num) {
        return ONE.subtract(num.multiply(valueOf(.01))); // updated to return 1 - num * 0.01
    }

    public static BigDecimal getShieldValue(BigDecimal input) {
        BigDecimal result = valueOf(1).subtract(input.multiply(valueOf(.01)));
        DecimalFormat df = new DecimalFormat("#.##");
        return BigDecimal.valueOf(Double.parseDouble(df.format(result)));
    }

    public static BigDecimal calculateTotalDamageTest(BigDecimal baseDamage, BigDecimal damageMultiplier, BigDecimal blades, BigDecimal traps, BigDecimal weaknesses, BigDecimal aura, BigDecimal bubble) {
        final BigDecimal multiply = baseDamage.multiply(damageMultiplier).multiply(aura).multiply(bubble);
        final BigDecimal multiply1 = multiply.multiply(blades).multiply(traps);
        return multiply1.multiply(weaknesses);
    }


    public static BigDecimal calculateCriticalDamage (BigDecimal total, BigDecimal critMod){
        return total.multiply(critMod);
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

    public static BigDecimal shieldsTotalValue (ArrayList<BigDecimal> list) {
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

        ArrayList<BigDecimal> bladeList = new ArrayList<>();
        BigDecimal blades = valueOf(1);
        ArrayList<BigDecimal> trapList = new ArrayList<>();
        BigDecimal traps = valueOf(1);
        ArrayList<BigDecimal> weaknessList = new ArrayList<>();
        BigDecimal weaknesses = valueOf(1);
        ArrayList<BigDecimal> shieldsList = new ArrayList<>();
        ArrayList<BigDecimal> shieldValue = new ArrayList<>();
        ArrayList<BigDecimal> originalShields = new ArrayList<>();
        BigDecimal shields;
        BigDecimal allShields = valueOf(0);
        BigDecimal piercedShield;
        int lastShieldValueIndex = 0;
        int lastShieldListIndex = 0;
        int lastOGShieldIndex = 0;
        BigDecimal recentShield = ZERO;

        BigDecimal aura = valueOf(1);
        BigDecimal bubble = valueOf(1);
        BigDecimal one = ONE;

        System.out.print("Spell DMG: ");
        BigDecimal spellDamage = (input.nextBigDecimal());

        System.out.print("DMG: ");
        BigDecimal characterDamage = (calculator.getDamageMultiplier(input.nextBigDecimal()));

        System.out.print("Crit Mod: ");
        BigDecimal critMod = (calculator.getDamageMultiplier(input.nextBigDecimal()));

        System.out.print("Pierce: ");
        BigDecimal pierce = (calculator.getPierceValue(input.nextBigDecimal()));
        BigDecimal ogPierce = pierce;

        System.out.print("Enemy Resist: ");
        BigDecimal resist = (calculator.getResistValue(input.nextBigDecimal()));
        BigDecimal ogResist = resist;


        label:
        while (true) {


            BigDecimal total = calculateTotalDamageTest(spellDamage, characterDamage, blades, traps, weaknesses, aura, bubble);

            if (pierce.compareTo(allShields) >= 0) {
                pierce = pierce.subtract(allShields);

                if (pierce.compareTo(resist) > 0){
                    pierce = valueOf(0);
                    resist = valueOf(0);
                }

                total = total.multiply(one.subtract(resist.subtract(pierce)));


            }else {
                do {
                    pierce = pierce.subtract(shieldValue.get(shieldValue.size() - 1));
                    if (pierce.compareTo(ZERO) > 0) {
                        shieldValue.remove(shieldValue.size() - 1);
                        allShields = (shieldsTotalValue(shieldValue));
                        shieldsList.remove(shieldsList.size() - 1);
                        lastShieldValueIndex = shieldValue.size() - 1;
                        lastShieldListIndex = shieldsList.size() -1;


                    }else {
                        piercedShield = pierce.abs();
                        shieldValue.set(lastShieldValueIndex, piercedShield);
                        shieldsList.set(lastShieldListIndex, addShields(piercedShield));
                    }
                    shields = (calculateProduct(shieldsList));


                } while (pierce.compareTo(valueOf(0)) > 0);


                total = total.multiply(shields).multiply(one.subtract(resist));
            }

            if (!shieldsList.isEmpty()) {
                shieldValue.set(lastShieldValueIndex, addShields(recentShield));
                shieldsList.set(lastShieldListIndex, (recentShield));
            }

            pierce = ogPierce;
            resist = ogResist;
            if (!originalShields.equals(shieldValue)){
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
            System.out.println("Total: " + total + "\nCritical: " + calculateCriticalDamage(total,critMod));
            System.out.println();
            System.out.print("Enter a command: ");
            String command = scanner.nextLine();

            switch (command) {
                case "b": {
                    try{
                        System.out.print("Blade value: ");
                        BigDecimal buff = (calculator.getBuffValue(input.nextBigDecimal()));
                        bladeList.add(buff); //adds buff to blades list
                        blades = (calculateProduct(bladeList));
                        break;
                    }catch (InputMismatchException e){
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
                        }else {
                            allShields = ZERO;
                        }
                    }else {
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
                    aura = valueOf(1);
                    bubble = valueOf(1);
                    break;

                case "ns":
                    try {
                        System.out.print("Enter new spell damage: ");
                        spellDamage = (input.nextBigDecimal());
                    }catch (InputMismatchException e) {
                        System.out.println("Invalid input! Please enter a valid decimal number.");
                    }
                    break;

                case "nd":
                    try {
                        System.out.print("Enter new DMG: ");
                        characterDamage = (calculator.getDamageMultiplier(input.nextBigDecimal()));
                    }catch (InputMismatchException e) {
                        System.out.println("Invalid input! Please enter a valid decimal number.");
                    }
                    break;

                case "np":
                    try {
                        System.out.print("Enter new pierce: ");
                        ogPierce = (calculator.getPierceValue(input.nextBigDecimal()));
                        pierce = ogPierce;
                    }catch (InputMismatchException e) {
                        System.out.println("Invalid input! Please enter a valid decimal number.");
                    }
                    break;

                case "nr":
                    try {
                        System.out.print("Enter new enemy resist: ");
                        ogResist = (calculator.getResistValue(input.nextBigDecimal()));
                        resist = ogResist;
                    }catch (InputMismatchException e) {
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

                case "q":
                    break label;

                case "help":
                    System.out.println("b\tadds a blade\nrb\tremoves the value of the last blade\n0b\tresets all blade\nt\tadds a trap\nrt\tremoves the value of the last trap\n0t\tresets all traps\nw\tadds a weakness\nrw\tre adds the value lost from the last weakness\n0w\tresets all weaknesses\ns\tadds a shield\nrs\tremoves the value of the last shield\n0s\tresets all shields\na\tadds an aura\n0a\tremoves an aura\nbub\tadds a bubble\n0bub\tremoves a bubble\n00\tresets ALL buffs and weaknesses\nns\tchanges the spells base dmg value\nnd\tchanges the dmg value\nnp\tchanges the pierce value\nnr\tchanges the resist value\nnc\tchanges the crit mod\nq\tstops the code");

                case default:
                    System.out.println("Invalid command");
                    break;
            }
        }
    }
}
