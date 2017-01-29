package Testing;

import com.andrew.Mob;
import org.junit.Test;
import org.junit.Assert;

public class MobTester {

    @Test
    public void mobGetterTests() {
        /* The two mob constructors
        public Mob(String name, String description, String setting, int maxHP, int attack, int defense) {
        public Mob(String name, String description, String setting, int maxHP, int attack, int defense, String defaultWeapon, String defaultBody, String defaultLegs) {
        */

        // Given
        String mobName = "Test Mob";
        String mobDescription = "It looks like a test";
        String mobSetting = "A piece of paper with questions and check boxes lies here";
        int mobHP = 100;
        int mobAttack = 10;
        int mobDefense = 5;
        Mob testMob = new Mob(mobName,mobDescription,mobSetting,mobHP,mobAttack,mobDefense);

        // Test getters
        Assert.assertEquals(mobName,testMob.getName()); // getName()
        Assert.assertEquals(mobDescription,testMob.getDescription()); // getDescription()
        Assert.assertEquals(mobSetting,testMob.getSetting()); // getSetting()
        Assert.assertEquals(mobHP,testMob.getHP()); // getHP()
        Assert.assertEquals(mobHP,testMob.getMaxHP()); // getMaxHP()
        Assert.assertEquals(mobAttack,testMob.getAttack()); // getAttack()

    }

    @Test
    public void mobSetterTests() {

        // Given
        String mobName = "Test Mob";
        String mobDescription = "It looks like a test";
        String mobSetting = "A piece of paper with questions and check boxes lies here";
        int mobHP = 100;
        int mobAttack = 10;
        int mobDefense = 5;
        Mob testMob = new Mob(mobName,mobDescription,mobSetting,mobHP,mobAttack,mobDefense);

        // Run setters
        String newMobName = "Changing Test";
        String newMobDescription = "A paper with swirling text";
        String newMobSetting = "A piece of paper lies here, but every time you think you can read a line, it changes..";
        int newMobHP = 153;
        int newMobMaxHP = 200;
        int newMobAttack = 20;
        int newMobDefense = 15;

        testMob.setAttack(newMobAttack);
        testMob.setDefense(newMobDefense);
        testMob.setHP(newMobHP);
        // Test to make sure hp can't be higher than max hp
        Assert.assertEquals(mobHP,testMob.getHP());
        testMob.setMaxHP(newMobMaxHP);
        testMob.setHP(newMobHP); // Setting again after new max hp set because new hp is higher than old max hp
        testMob.setSetting(newMobSetting);
        testMob.setDescription(newMobDescription);

        // Test getters for new values
        Assert.assertEquals(newMobDescription,testMob.getDescription());
        Assert.assertEquals(newMobSetting,testMob.getSetting());
        Assert.assertEquals(newMobHP,testMob.getHP());
        Assert.assertEquals(newMobMaxHP,testMob.getMaxHP());
        Assert.assertEquals(newMobAttack,testMob.getAttack());
        Assert.assertEquals(newMobDefense,testMob.getDefense());

    }

}
