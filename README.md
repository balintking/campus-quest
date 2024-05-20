
# Campus Quest: A Java Game Project

This repository contains the "Campus Quest" game, developed as part of the Software Project Laboratory course at Budapest University of Technology and Economics (BME).

> **Group:** Mumbai IT Solution.
> 
> **Authors:** Cardinael Jan, Görömbey Lilla, Király Bálint, Riba Miklós, Szakos Máté

# Overview

- [About the Game](#About-the-Game)
- [Getting Started](#Getting-Started)
- [Documentation](#Documentation)

# About the Game
*(Our task was to design and implement the game based on this given description)*

### Description

In the sprawling basement of the BME Central Building lies a cursed labyrinth. Engineering students are tasked with finding the magical artifact known as the Logarléc. The labyrinth consists of various rooms separated by doors that allow passage from one room to another, with some doors being one-way only.

Rooms may contain various objects, including the Logarléc, that students can pick up. However, a student can carry no more than five objects at a time. These objects can also be dropped. Professors roam the labyrinth attempting to thwart the students' progress. If a professor enters a room with one or more students, they claim the students' souls, effectively expelling them from the university. Some objects, like printed copies of the TVSZ on bat leather, offer temporary protection against professors, saving the student's life three times before losing their magic. Other items, such as the sacred beer mugs, offer protection for a limited time, and a wet chalkboard cloth that paralyzes professors in the same room until it dries.

Additionally, rooms may contain transistors that can be paired and used to teleport the student to the other transistor's location. Each room has a specific capacity, and there are rooms with toxic gas where students and professors lose consciousness temporarily and drop their objects unless they have an FFP2 mask, which offers limited-time protection. Some enchanted rooms have doors that disappear and reappear randomly.

The labyrinth's rooms can merge or divide, altering their properties and capacities. The game is played by multiple players controlling the students, aiming to find and secure the Logarléc within a set time.

Recent updates to the game have introduced new items and characters, such as air fresheners that neutralize gas effects in rooms and a janitor character who can expel all mobile persons from a room and end its gaseous state. Some objects now have fake versions with no beneficial properties, adding further challenges to the game.

# Getting Started

### Prerequisites
To run this game, you'll need:
- Java JDK 11 or higher

### Running the Game
1. **Clone the repository**
2. **Navigate to the source directory**
   ```bash
   cd campus-quest/src
   ```
3. Compile the Java files
   ```bash
   javac *.java
   ```
4. Run the main Java class
   ```bash
   java Main
   ```

# Documentation

**Project Documents:** Find all written documents related to the project in the `documents` directory.

**Diagrams:** Architectural and other related diagrams can be found in the `diagrams` directory.
