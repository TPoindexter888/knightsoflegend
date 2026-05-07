package knightsoflegend;

public class TestHarness {
    public static void main(String[] args) {

        // === Test Fortune ===
        Fortune f = new Fortune("Blessing of Valor", 2, 1, 5, DiceType.D6);
        System.out.println("=== Fortune Test ===");
        System.out.println(f);
        System.out.println();

        // === Test MOB ===
        MOB goblin = new MOB("Goblin", 1, 0, 10, DiceType.D4);
        System.out.println("=== MOB Test ===");
        System.out.println(goblin);
        goblin.addDamage(3);
        System.out.println("Goblin HP after 3 dmg: " + goblin.getHP());
        goblin.resetDamage();
        System.out.println("Goblin HP after reset: " + goblin.getHP());
        System.out.println();

        // === Test Knight (no fortune) ===
        Knight k = new Knight(101, "Sir Roland", 20, 3, 2, DiceType.D8, 50);
        System.out.println("=== Knight Test (No Fortune) ===");
        System.out.println(k);
        System.out.println("Armor: " + k.getArmor());
        System.out.println("HitMod: " + k.getHitModifier());
        System.out.println("MaxHP: " + k.getMaxHP());
        System.out.println("DamageDie: " + k.getDamageDie());
        System.out.println("CSV: " + k.toCSV());
        System.out.println();

        // === Apply Fortune ===
        k.setActiveFortune(f);
        System.out.println("=== Knight Test (With Fortune) ===");
        System.out.println(k);
        System.out.println("Armor: " + k.getArmor());
        System.out.println("HitMod: " + k.getHitModifier());
        System.out.println("MaxHP: " + k.getMaxHP());
        System.out.println("DamageDie: " + k.getDamageDie());
        System.out.println("CSV: " + k.toCSV());
        System.out.println();

        // === Damage Test ===
        k.addDamage(7);
        System.out.println("Knight HP after 7 dmg: " + k.getHP());
        k.resetDamage();
        System.out.println("Knight HP after reset: " + k.getHP());
    }
}