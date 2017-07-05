# Fallstudie 2016 - WWI2015D

Software zur Erstellung von Aufwandschaetzungen und Planung von Mitarbeitern als Basis zur Beantragung von Projekten

## Instalation
1. Installiere das aktuellste Java JDK
2. Installiere Eclipse in der aktuellsten Version (Neon)
3. Installiere GitHub Desktop und melde dich mit deinem GitHub Account an
4. Clone das Projekt in dein gewünschtes Verzeichnis und öffene es mit Eclipse
5. Füge in Eclipse folgende Erweiterungen hinzu (Help -> Install new Software): 
	- e(fx)clipse - http://download.eclipse.org/efxclipse/updates-released/2.4.0/site/
6. Installiere den JavaFX Scene Builder (http://gluonhq.com/labs/scene-builder/#download)
7. In den Eclipse Einstellungen den Pfad zur Scenebuilder.exe Date eintragen (C:\Users\MeinBenutzerName\AppData\Local\SceneBuilder\SceneBuilder.exe)
8. Importiere die Libarys in das Javaprojekt (Properties -> Java Build Path)
	
## Programmierregeln
Um einen einheitlichen Code zu schreiben, sollten folgende Regeln beachtet werden.
**Bitte halte dich an diese Regeln!**

### Allgemeine Regeln
- Klassennamen fangen mit einem Großbuchstaben an
- Variablen und Methoden fangen mit einem Kleinbuchstaben an
- Methoden müssen mit Java Doc kommentiert und erklärt werden (/**)

### Quellcode spezifische Regeln
- Variablen sollen immer private sein und mit einer *seter* und *geter* Methode aufgerufen werden (Datenkapselung)
- Unüntze Variablen sollten vermiden werden
- Ergebnisse oder Variablen sollen über die Console ausgegeben werden, damit das Debugging leichter von der Hand geht

## JavaFX spezifische Regeln
- Jedes Element in der UI muss einen Namen erhalten, egal ob Label oder Button
- Elemente sollten nach folgendem Schema bezichnet werden: 
	- Buttons: btn_btnNamen
	- Labels: lbl_lblName
	- Textfelder: txt_txtName
	- usw...
- Verknüpfte Methoden sollten nach dem gleichen Schema bezeichnet werden: btn_btnName_click usw.
- Das Hauptprogramm soll in der MainUI.fxml Datei realsiert werden

## GitHub Regeln
Damit der Quellcode nicht durcheinander kommt, sollten ein paar Regeln beachtet werden.

- Arbeite auf einem eignen Branch
- Mache keine Commits auf den Staging oder Master Branch
- Pullrequests werden nur auf den Staging Branch gemacht
- Jeder Commit oder Pullrequest wird automatisch auf seine Fehlerfreiheit überprüft.
	
## Verwendete externe Bibliotheken
- [sql2o](https://sql2o.org/)
- [e(fx)clipse](http://download.eclipse.org/efxclipse/updates-released/2.4.0/site/)
- [Gluon Labs Scene Builder](http://gluonhq.com/labs/scene-builder/#download)
