# CommandblockFinder

1. [Deutsch](#deutsch)
   - [Anwendung](#anwendung)
   - [Output-Format](#output-format)
   - [Wichtig](#wichtig)
   - [Systemvoraussetzungen](#systemvorraussetzungen)
   - [Referenzen](#referenzen)
2. [English](#english)
   - [Usage](#usage)
   - [Output format](#output-format-1)
   - [Important](#important)
   - [System requirements](#system-requirements)
   - [References](#references)
3. [Konsole öffnen / Open console](#konsole-öffnen--open-console)
4. [Download](#download)

## Deutsch
CommandblockFinder ist ein Programm, mit dem man Commandblöcke und deren Commands von einer Minecraft-Welt finden kann. Dies ist nützlich, wenn bspw. jemand einen Commandblock mit dem Command `kill @a` irgendwo hingestellt hat und man findet ihn nicht. Außerdem kann das Programm auch den Command von Commandblöcken entfernen.
### Anwendung
Einfach die Jar-Datei in den Welt-Ordner downloaden. Dann im Welt-Ordner ein Terminal öffnen; unter Windows geht das ganz einfach, indem man auf den Pfad klickt und `cmd` reinschreibt. In die jetzt geöffnete Konsole schreibt man den Befehl `java -jar CommandblockFinder.jar`. Man kann die Sprache des Programms mit einem Argument auswählen, zum Beispiel: `java -jar CommandblockFinder.jar de`. Verfügbare Sprachen sind Deutsch (de, ger, german) und Englisch (en, english). Ab der Eingabe des Befehls leitet das Programm einen durch den weiteren Ablauf.
#### OutOfMemoryError
Wenn ein OutOfMemoryError kommt oder die Prozentanzeige nicht mehr wächst, hat das Programm möglicherweise zu wenig Ram. Dann einfach diese Argumente zum Befehl hinzufügen: `java -Xmx4G -XX:+UseConcMarkSweepGC -jar CommmandblockFinder.jar`. Hier hätte das Programm dann 4GB zu Verfügung.
### Output-Format
Beim Finden von Commmandblöcken werden diese in einer Datei gespeichert.  
Das Format ist: `Welt / X Y Z: Command`. Verschiedene Commandblöcke werden durch eine Zeile Freiraum getrennt.
### Wichtig
Dieses Programm nicht benutzen, während die Welt in Minecraft geöffnet ist. Beim Speichern durch Minecraft werden Änderungen von diesem Programm nicht übernommen.
### Systemvorraussetzungen
Java 8 oder höher. [Java-Download](https://java.com/de/)
### Referenzen
- Als API habe ich die [NBT-API](https://github.com/Querz/NBT) von [Querz](https://github.com/Querz/) benutzt.

## English
CommandblockFinder is a program to find command blocks and their commands from a Minecraft world. This is useful if for example someone has put a command block with the command `kill @a` somewhere and you can't find it. The program can also remove the command from command blocks.
### Usage
Just download the jar file into the world folder. Then open a terminal in the world folder; in Windows this can be done very easy by clicking on the path and typing `cmd` into it. After that write the command `java -jar CommandblockFinder.jar` into the console. You can select the language of the program with an argument, for example: `java -jar CommandblockFinder.jar en`. Available languages are German (de, ger, german) and English (en, english). After entering the command, the program guides you through the rest of the process.
#### OutOfMemoryError
If an OutOfMemoryError occurs or the percentage does not increase, the program possibly has too few ram. You can just add these arguments to the command: `java -Xmx4G -XX:+UseConcMarkSweepGC -jar CommmandblockFinder.jar`. Here the program would have max 4GB.
### Output format
When Commmand Blocks are found, they are stored in a file.  
The format is: `World / X Y Z: Command`. Different command blocks are separated by a line of free space.
### Important
Do not use this program while the world is open in Minecraft. When saving the world in Minecraft, changes applied by this program will not be saved.
### System requirements
Java 8 or higher. [Java download](https://java.com/en/)
### References
- As an API I used the [NBT-API](https://github.com/Querz/NBT) by [Querz](https://github.com/Querz/).

## Konsole öffnen / Open console
![cmd tutorial](https://github.com/Rapha149/CommandblockFinder/blob/master/cmd.gif)

## Download
[CommandblockFinder.jar](https://www.dropbox.com/s/ll9qjp9p9z0jksp/CommandblockFinder.jar?dl=1)
