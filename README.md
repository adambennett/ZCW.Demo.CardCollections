# Card Collector

A simple command line application to demonstrate the usage of both OOP-based Inheritance and Java Collections.

The general idea is two players battle each other using cards they draw one at a time from a deck, with a set starting amount of health.

Each turn, each player draws a new card, and they may choose to attack or defend with their card.

When one player has no HP remaining, the battle is over!

## Building

- Requires Java 16+!

### Compile With `jpackage`

#### Pre-reqs
- Install WiX 3.0 or later `(Windows only)`
  - [Download WiX](https://wixtoolset.org)
- Before compilation, be sure to run `maven clean install` at least once to generate the necessary jars.

#### Windows
- Move into the root directory of the project
- Run the command: `"<path to Java16>\bin\jpackage.exe" --name "Card Collector" --input target --main-jar CardCollector-1.0.0-jar-with-dependencies.jar --main-class Collector.CardBattle --win-console`
- After a short period of time, `jpackage` will output an executable called `CardCollector-1.0.exe` in the root directory
- This file is a windows installer for the program, which will generate an executable called `CardCollector.exe`
- The installed executable can be run directly to execute the program on `Windows` machines

#### Mac (*untested*)
- Move into the root directory of the project
- Run the command: `"<path to Java16>\bin\jpackage.exe" --name CardCollector --input target --main-jar ZCW.Demo.CardCollections-1.0.0-jar-with-dependencies.jar --main-class Collector.CardBattle`
- After a short period of time, `jpackage` will output an executable called `CardCollector-1.0.dmg` in the root directory
- This file is a mac installer for the program, which will generate an application called `CardCollector`
- The installed executable can be run directly to execute the program on `Mac` machines
