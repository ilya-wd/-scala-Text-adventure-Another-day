package o1.adventure.ui

import scala.swing._
import scala.swing.event._
import javax.swing.UIManager
import o1.adventure.Adventure

import scala.util.Random


////////////////// NOTE TO STUDENTS //////////////////////////
// For the purposes of our course, it's not necessary
// that you understand or even look at the code in this file.
//////////////////////////////////////////////////////////////


/** The singleton object `AdventureGUI` represents a GUI-based version of the Adventure
  * game application. The object serves as a possible entry point for the game app, and can
  * be run to start up a user interface that operates in a separate window. The GUI reads
  * its input from a text field and displays information about the game world in uneditable
  * text areas.
  *
  * '''NOTE TO STUDENTS: In this course, you don't need to understand how this object works
  * or can be used, apart from the fact that you can use this file to start the program.'''
  *
  * @see [[AdventureTextUI]] */

// TODO - function to mix the text up if a player is drunk?
object AdventureGUI extends SimpleSwingApplication {
  UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName)

  def top = new MainFrame {

    // Access to the application’s internal logic:

    val game = new Adventure
    val player = game.player

    // Components:

    val locationInfo = new TextArea(7, 80) {
      editable = false
      wordWrap = true
      lineWrap = true
    }

    val newScroll = new ScrollPane() {
      contents = locationInfo
    }

    val turnOutput = new TextArea(7, 80) {
      editable = false
      wordWrap = true
      lineWrap = true
    }

    val newScrollTwo = new ScrollPane() {
      contents = turnOutput
    }

    val input = new TextField(40) {
      minimumSize = preferredSize
    }
    this.listenTo(input.keys)
    val turnCounter = new Label

    // Events:

    this.reactions += {
      case keyEvent: KeyPressed =>
        if (keyEvent.source == this.input && keyEvent.key == Key.Enter && !this.game.isOver) {
          val command = this.input.text.trim
          if (command.nonEmpty) {
            this.input.text = ""
            this.playTurn(command)
          }
        }
    }

    // Layout:

    this.contents = new GridBagPanel {
      import scala.swing.GridBagPanel.Anchor._
      import scala.swing.GridBagPanel.Fill
      layout += new Label("Location:") -> new Constraints(0, 0, 1, 1, 0, 1, NorthWest.id, Fill.None.id, new Insets(8, 5, 5, 5), 0, 0)
      layout += new Label("Command:")  -> new Constraints(0, 1, 1, 1, 0, 0, NorthWest.id, Fill.None.id, new Insets(8, 5, 5, 5), 0, 0)
      layout += new Label("Events:")   -> new Constraints(0, 2, 1, 1, 0, 0, NorthWest.id, Fill.None.id, new Insets(8, 5, 5, 5), 0, 0)
      layout += turnCounter            -> new Constraints(0, 3, 2, 1, 0, 0, NorthWest.id, Fill.None.id, new Insets(8, 5, 5, 5), 0, 0)
      layout += newScroll           -> new Constraints(1, 0, 1, 1, 1, 1, NorthWest.id, Fill.Both.id, new Insets(5, 5, 5, 5), 0, 0)
//      layout += locationInfo           -> new Constraints(1, 0, 1, 1, 1, 1, NorthWest.id, Fill.Both.id, new Insets(5, 5, 5, 5), 0, 0)
      layout += input                  -> new Constraints(1, 1, 1, 1, 1, 0, NorthWest.id, Fill.None.id, new Insets(5, 5, 5, 5), 0, 0)
//      layout += turnOutput             -> new Constraints(1, 2, 1, 1, 1, 1, SouthWest.id, Fill.Both.id, new Insets(5, 5, 5, 5), 0, 0)
      layout += newScrollTwo             -> new Constraints(1, 2, 1, 1, 1, 1, SouthWest.id, Fill.Both.id, new Insets(5, 5, 5, 5), 0, 0)
    }

    // Menu:

    this.menuBar = new MenuBar {
      contents += new Menu("Program") {
        val quitAction = Action("Quit") { dispose() }
        contents += new MenuItem(quitAction)
      }
    }

    // Set up the GUI’s initial state:

    this.title = game.title
    this.updateInfo(this.game.welcomeMessage)
    this.location = new Point(50, 50)
    this.minimumSize = new Dimension(200, 200)
    this.pack()
    this.input.requestFocusInWindow()


    def playTurn(command: String) = {
      val turnReport = this.game.playTurn(command)
      if (this.player.hasQuit) {
        this.dispose()
      } else {
//        this.updateInfo(turnReport)
        this.updateInfo(turnReport.toString)
        this.input.enabled = !this.game.isOver
      }
    }

    val tipsyText = "\nYou drank too much for today, my dear. Your perception of reality became distorted. The show still goes on, you can continue with your day, it's just that it'll be a tad harder. " +
      "\nPerhaps you should take a break and go get some sleep? " +
      "\n\nTry to remember how to get to your apartment from here. Watch the time.\n\n"


   // In order to simulate the state of drunkeness, the text a player gets in GUI is shuffled after a character consumed too much beer
    def updateInfo(info: String) = {

      if (!this.game.isOver) {
        if (this.player.isTipsy) this.turnOutput.text = this.tipsyText + Random.shuffle(info).toString()
        else this.turnOutput.text = info

      } else {
        this.turnOutput.text = info + "\n\n" + this.game.goodbyeMessage
      }
      if (this.player.isTipsy) this.locationInfo.text = Random.shuffle(this.player.location.fullDescription).toString()
      else this.locationInfo.text = this.player.location.fullDescription
      this.turnCounter.text = "Days: " + this.game.dayCount + " | " + "Time: " + this.game.currentTime + ":00" + " | Beers today: " + this.player.beersForToday
    }


  }

}

