# RBTrees Testat

## Contributors

* David Hermann
* Jannik Schmitt
* Friederike von Gruben
* Julia Kunze

## Installation

Es ist wichtig, dass folgende Packages auf Linux installiert sind:

* `pdftk` zum zusammenführen der PDFs
* `librsvg2-bin` für `rsvg-convert`

Beides kann einfach via `sudo apt-get install ...` installiert werden.

## Usage

Beide Files sind im Package `RBTrees`. Es muss also ein Ordner erstellt werden, welcher genauso heißt, indem die beiden .java Files reinkopiert werden.

Nun im Terminal aus dem Parent Pfad folgende Commands ausführen:

`javac RBTrees/Main.java RBTrees/RBTree.java`

`java RBTrees.Main`

Das Programm erstellt automatisch 3 Ordner im RBTrees Package indem die DOTs, die PDFs und die SVGs gespeichert werden. Das finale PDF ist im PDFs Ordner.