//  Taken from https://github.com/TomGrill/gdx-testing
package com.unciv.testing

import com.badlogic.gdx.Gdx
import com.unciv.UncivGame
import com.unciv.models.ruleset.Ruleset
import com.unciv.models.ruleset.unit.BaseUnit
import org.junit.Assert
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(GdxTestRunner::class)
class BasicTests {
    @Test
    fun gamePngExists() {
        Assert.assertTrue("This test will only pass when the game.png exists",
                Gdx.files.local("game.png").exists())
    }

    @Test
    fun loadRuleset() {
        Assert.assertTrue("This test will only pass when the jsons can be loaded",
                Ruleset(true).Buildings.size > 0)
    }

    @Test
    fun gameIsNotRunWithDebugModes() {
        val game = UncivGame("", null)
        Assert.assertTrue("This test will only pass if the game is not run with debug modes",
                !game.superchargedForDebug && !game.viewEntireMapForDebug)
    }

    // If there's a unit that obsoletes with no upgrade then when it obsoletes
// and we try to work on its upgrade, we'll get an exception - see techManager
    @Test
    fun allObsoletingUnitsHaveUpgrades() {
        val units: Collection<BaseUnit> = Ruleset(true).Units.values
        var allObsoletingUnitsHaveUpgrades = true
        for (unit in units) {
            if (unit.obsoleteTech != null && unit.upgradesTo == null) {
                println(unit.name + " obsoletes but has no upgrade")
                allObsoletingUnitsHaveUpgrades = false
            }
        }
        Assert.assertTrue(allObsoletingUnitsHaveUpgrades)
    }
}