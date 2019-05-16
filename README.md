# studious-spork

This repository implements a multi-location weather app for traveling business people (but does not solve the TSP). It lets users create schedules that take them to multiple cities or countries throughout their busy days. The app then provides an easy-to-read weather summary for _their_ schedule, instead of just one location. Schedules can easily be edited or deleted, and our APIs support weather querying at any place on earth.

## Setting up Dev Environment
To do this please do the following:

1. Open a terminal to somewhere you want to work
1. Clone this repo using `git clone https://github.com/btfs2/studious-spork.git`
1. Move into this directory `cd studious-spork`
1. Run either `gradlew eclipse` or `gradlew idea` depending on your IDE preference
	* If you have a local gradle install you can use `gradle` instead of `gradlew`
1. Load into your prefered IDE and start programming

## Run the app 
This project runs on Windows and UNIX systems. To run,

1. Open the repo's root dir in (i.e. the one with the README in it) in a terminal
1. Type `./gradlew run`
1. Open the `config.json`and add your darksky key to the file. You can obtain a darksky key at `https://darksky.net/`. Note: this step can be avoided for production versions.

## Build the code

To build and bundle the project please do the following

1. Open the repo's root dir in (i.e. the one with the README in it) in a terminal
1. Type `./gradlew build`
1. Bundled distributions are now avaliable in `./build/distributions/`, and the jar in `./build/libs`

## FatJar

To build single fatjar containing all dependencies

1. Open the repo's root dir in (i.e. the one with the README in it) in a terminal
1. Type `./gradlew fatJar`
1. The fatJar will be in `./build/libs`

## Libraries and Tools used

We employed the following external tools in our project:
- Darksky weather API: https://darksky.net/
- Photon location service: https://github.com/komoot/photon
