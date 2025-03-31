#  Traffic control

## Overview

The goal of this project is to create a simulation of intelligent traffic lights at an intersection. The system manages the light cycles based on traffic density on different roads, ensuring smooth traffic flow and avoiding conflicts at the intersection.

## Key Features

- **Intersection Representation:** The simulation models an intersection with four incoming roads: North, South, East, and West.
- **Traffic Lights:** The simulation handles standard traffic light cycles (Green, Yellow, Red), as well as special phases (e.g., green arrows) for each incoming road.
- **Conflict Prevention:** The system ensures safety by avoiding conflicts such as two conflicting directions having green lights simultaneously.
- **Vehicle Tracking:** It keeps track of the number of vehicles waiting at each road.

## Setup

```bash
mvn clean install
```

```bash
mvn exec:java -Dexec.args="examplein.json exampleout.json"
```
  
## Technologies

- **Backend** - Java 21
- **Development Environment** - Intellij IDEA

## Authors 

- Kamil Rudny [GitHub](https://github.com/krudny)
