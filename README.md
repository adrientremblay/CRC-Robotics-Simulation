# CRC-Robotics-Simulation
Simulation of the scoring system of the 2020 'Flip!' [CRC](http://robo-crc.ca/)(Canadian Robotics Competitions) Robotics using the Java [JMonkey3](https://jmonkeyengine.org/) Game Engine.

## Purpose
The purpose of this simulation was to allow teams to test the scoring mechanism of the game beforehand.  *This simulation was infact distributed to the teams who participated in the competion*.  Having an accurate simulation of the scoring system to play with allowed teams stratagize in the design of their robots.  With the help of a UI users can mimick the triggering of actuators or change other behaviour parameters of the conveyor belt.

## Design
This simulation was created using the Java [JMonkey3 Game Engine](https://jmonkeyengine.org/).  Physics was modelled using the [JBullet physics Engine](http://jbullet.advel.cz/).  The user interface was created using [NiftyGUI](https://github.com/nifty-gui/nifty-gui)

## Running the simulation
The easiest way to run the simulation is to navigate to admin distribution/dist and unzip Robotics Competiton Simulation-MacOSX.zip if on Mac OSX, Robotics Competiton Simulation-Windows-x64.zip if on Windows, or to run Robotics Competiton Simulation.jar(which should theoretically run on anything).  Additonally you could also import the source code into the JMonkey3 Editor and run it from there.
