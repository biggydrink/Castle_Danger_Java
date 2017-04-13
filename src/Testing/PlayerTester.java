package Testing;

import com.andrew.Player;
import org.junit.Assert;
import org.junit.Test;

/**
 * Created by Andrew on 2/7/2017.
 */
public class PlayerTester {

    @Test
    public void testGetters() {

        // Given
        String testPlayerName = "Mario";
        String testPlayerDescription = "Behold, " + testPlayerName + ". An elite player in this game!";
        String testPlayerSetting = testPlayerName + " is standing here.";
        Player testPlayer = new Player(testPlayerName);

        // Tests
        Assert.assertEquals(testPlayer.getName(),testPlayerName);
        Assert.assertTrue((testPlayer.getMaxHP() >= 150 && testPlayer.getMaxHP() <= 160)); // max hp set to 150-160
        Assert.assertEquals(testPlayer.getMaxHP(),testPlayer.getHP()); // hp should be same as maxhp
        Assert.assertTrue(testPlayer.getAttack() >= 20 && testPlayer.getDefense() <= 22); // attack between 20 and 22
        Assert.assertTrue(testPlayer.getDefense() >= 15 && testPlayer.getDefense() <= 17); // defense between 15 and 7
        Assert.assertEquals(testPlayer.getDescription(),testPlayerDescription);
        Assert.assertEquals(testPlayer.getSetting(),testPlayerSetting);
    }

    @Test
    public void testSetters() {

        // Currently only setters are protected,

    }

}
