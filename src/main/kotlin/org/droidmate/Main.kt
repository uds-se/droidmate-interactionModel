package org.droidmate

import org.droidmate.command.ExploreCommand
import org.droidmate.exploration.strategy.widget.FitnessProportionateSelection

class Main {
    companion object {
        @JvmStatic
        fun main(args: Array<String>) {
            println("Starting DroidMate")
            try {
                // Create a configuration to run DroidMate
                val cfg = ExplorationAPI.config(args)

                // Create the strategy and update it to the list of default strategies on DroidMate
                val myStrategy = FitnessProportionateSelection(cfg.randomSeed)

                val strategies = ExploreCommand.getDefaultStrategies(cfg).toMutableList()
                    .apply {
                        add(myStrategy)
                    }

                // Run DroidMate
                val explorationOutput = ExplorationAPI.explore(cfg, strategies)

                explorationOutput.forEach { appResult ->
                    // Process results for each application
                    println("App: ${appResult.apk} Crashed? ${appResult.exceptionIsPresent}")
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