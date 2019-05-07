package org.droidmate

import kotlinx.coroutines.runBlocking
import org.droidmate.api.ExplorationAPI
import org.droidmate.command.ExploreCommandBuilder
import org.droidmate.exploration.strategy.FitnessProportionateSelection

class Main {
    companion object {
        @JvmStatic
        fun main(args: Array<String>) {
            runBlocking {
                println("Starting DroidMate")
                try {
                    // Create a configuration to run DroidMate
                    val cfg = ExplorationAPI.config(args)

                    // Create the strategy and update it to the list of default strategies on DroidMate
                    val myStrategy = FitnessProportionateSelection(cfg.randomSeed)

                    val builder = ExploreCommandBuilder.fromConfig(cfg)
                        .withStrategy(myStrategy)

                    // Run DroidMate
                    val explorationOutput = ExplorationAPI.explore(cfg, builder)

                    explorationOutput.forEach { appResult ->
                        // Process results for each application
                        println("App: ${appResult.key} Crashed? ${appResult.value.error.isNotEmpty()}")
                    }
                } catch (e: Exception) {
                    println("DroidMate finished with error")
                    println(e.message)
                    e.printStackTrace()
                    System.exit(1)
                }

                System.exit(0)
            }
        }
    }
}