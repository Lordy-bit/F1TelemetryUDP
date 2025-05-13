# F1 Dashboard

> A parser for real-time F1 telemetry data received via UDP.

## Demo

![Dashboard video](docs/demo.gif)

## Docs

[This is the overview tree of the classes](https://Lordy-bit.github.io/F1TelemetryUDP/docs/overview-tree.html)
  

## Index

- Getting started
- Usage
- Built with
- Demo
- Supported Game Version
- Project structure


## Getting Started

### Prerequisites

Make sure you have the following installed:

- Java (version 17 or above)

### Installation

#### Clone the repository
```bash

git  clone  https://github.com/yourusername/F1TelemetryUDP.git

```

##  Usage

### Run the application:

```bash

# Run directly with Java:

java  -cp  out/production/F1Telemetry Server

```

##  Built With


- Java
 
##  Supported Game Versions

This parser is compatible with:
- F1 21 (UDP Spec v1.18) onwards


##  Project Structure

```
F1Telemetry:
+---docs
|   +---index-files
|   +---legal
|   +---resources
|   \---script-dir
+---F1 Telemetry
|   +---.idea
|   +---Resources
|   \---src
+---graficTest
|   +---.idea
|   +---out
|   |   \---production
|   |       \---graficTest
|   +---Resources
|   \---src
+---PacketWriter
|   +---.idea
|   \---src
\---UDPF1Test
    +---.idea
    |   \---inspectionProfiles
    \---src

```


