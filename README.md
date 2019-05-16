# studious-spork

This repository implements a multi-location weather app for traveling business people (but does not solve the TSP). It lets users create schedules that take them to multiple cities or countries throughout their busy days. The app then provides an easy-to-read weather summary for _their_ schedule, instead of just one location. Schedules can easily be edited or deleted, and our APIs support weather querying at any place on earth.

## Setting up DevEnvironment

To do this please do the following:

1. Open a terminal to somewhere you want to work
1. Clone this repo using `git clone https://github.com/btfs2/studious-spork.git`
1. Move into this directory `cd studious-spork`
1. Run either `gradlew eclipse` or `gradlew idea` depending on your IDE preference
	* If you have a local gradle install you can use `gradle` instead of `gradlew`
1. Load into your prefered IDE and start programming

## Running and bundling

Note that none of these will sync to Github to avoid polluting the Repo.

### Running

To run the current version do the following

1. Open to the directory of this repo, i.e. the one with the README in it, in a terminal
2. In your terminal type `gradlew run`
3. Watch the magic of gradle do it's thing
4. Close the window open the config and put in your darksky key; In a user ready distro this will be included

## Building

To build and bundle the project please do the following

1. Open to the directory of this repo, i.e. the one with the README in it, in a terminal
2. In your terminal type `gradlew build`
3. Watch the magic of gradle do it's thing
4. Bundled distributions are now avaliable in `./build/distributions/`, and the jar in `./build/libs`

## FatJar

To build single fatjar containing all dependencies

1. Open to the directory of this repo, i.e. the one with the README in it, in a terminal
2. In your terminal type `gradlew fatJar`
3. Watch the magic of gradle do it's thing
4. The fatJar in `./build/libs`
