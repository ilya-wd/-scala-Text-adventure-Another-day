package o1.adventure

import scala.util.Random

class Adventure {

  /** The title of the adventure game. */
  val title = "The For Loop / Another Student Day"

  

  // Most of this locations are based on real ones that one can find on campus. Names, however, are wordplays around the real names
  private val party        = new Area("Party", "You decide to go to a house party your friend throws.\nIt's been great fun! Many people, different stuff to do - heavy drinking, moderate drinking, light drinking. Board games, video games, beer-pong bong bong king kong, drunk fights, smoking on the balcony, karaoke, pole dancing, chatting and chanting, sleeping - everything you can imagine and even more.\nThe culmination of the party was chasing campus geese. You know, those dark and grey birds that you can see grazing on local fields during the day when it's warm. You and your friends always wanted to pet them, so you decided that tonight you'll finally do it, together.\nThe geese weren't happy. Luckily for them, you didn't manage to catch any of their brethren.\n\nIn the middle of the night you realized that something is wrong. Just recently you woke up with a heavy head, and decided to change your apartment as well as your life. But it's not what you're doing right now.\n\nRemember those lofty statements? For the kidz, for the country...\nBut now you are your old self. You betraying yourself, the kidz, and so on and so forth.\n\nHow further down can you fall? You won't find out, because the game is over.", this)
  private val room         = new Area("Room", "Your room. The place where you spend somewhat from a third to all of your waking hours.", this)
  private val DMTStreet    = new Area("DMT Street Housing", "Student housing area. Nothing particular. Just houses, parked cars, tagged trees, foiled windows.", this)
  private val eatery       = new Area("The Sweet Swede Eatery", "A nice buffet where you come to eat your greens. Good bang for the buck - otherwise you wouldn't frequent it.", this)
  private val extraArea    = new Area("An Area", "Empty meadow. Something gonna be here one day.", this)
  private val square       = new Area("Le Corbusier Square", "A huge grass field. Many consider it a square. On a lucky day you can see grazing geese here, on a less lucky – grazing people.", this)
  private val amphitheater = new Area("Amphitheater", "Nice amphitheatre. Feels like you are in high-tech ancient Athens. You imagine what if modern students were to wear togas.", this)
  private val omegaBloke   = new Area("Omega Bloke", "Shops, offices, buffets, metro - a thing in itself.", this)
  private val university   = new Area("University", "Alright, you made it to the university, congrats! You are a student (otherwise you wouldn't get an apartment on DMT) so you are supposed to study. That's exactly what you do here. You end up spending all your day on studying. \nNow your time for the day is up.", this)
  private val office       = new Area("Office", "Office Building of the company where you, hm, work at.", this)

 
  // Setting the scene
          room.setNeighbors(Vector("dmt street" -> DMTStreet))
     DMTStreet.setNeighbors(Vector("the sweet swede eatery" -> eatery, "your apartment" -> room))
        eatery.setNeighbors(Vector("some area" -> extraArea, "lecorbusierinaukio" -> square, "dmt street" -> DMTStreet))
     extraArea.setNeighbors(Vector("the sweet swede eatery" -> eatery))
        square.setNeighbors(Vector("the sweet swede eatery" -> eatery, "amphitheater" -> amphitheater, "university" -> university, "omega bloke" -> omegaBloke))
  amphitheater.setNeighbors(Vector("lecorbusierinaukio" -> square))
    university.setNeighbors(Vector("lecorbusierinaukio" -> square))
    omegaBloke.setNeighbors(Vector("lecorbusierinaukio" -> square, "office" -> office))

 
  // Quest items that a player has to acquire to win the game, their names, descriptions, code phrases required to get an item, and hints to code phrases a player can activate
  // https://en.wikipedia.org/wiki/Rutabaga if you don't what it it is
  private val swedeCupItem = new Item("swede-carved cup (rutabaga)",
    "Name speaks for itself - a cup curved out of a swede. Not for drinking from, but for the memory.",
    "You start laughing thunderously. Some people mistake it for a battle cry. You slap your table once, twice, it doesn't break. You continue hitting it until it breaks apart. Your food is all over the place. It's on you, on other people, on the walls, on the TV.\nAll the while people look at you. Confused at first, they soon start laughing, applauding, and cheering you up! Each and everyone wishes they were you.\nYou broke the table but you don't stop there. You continue laughing, stand on your hands and run around the eatery like that. People LOVE it!\nWhen you finished and go back to what left of your table to pick up your bag, people pat you on your shoulder and asking to take a photo or leave an autograph on their bodies. You just made their day.\nYou are approaching the doors out of this great (and now very funny!) establishment when one of the staff catches you by the hand. With tears of joy, a girl gives you something in the bag. She says it was spectacular, the staff team thanks you for the great performanсe. As a token of gratitude they want to give you that thing in the bag – a relic of sorts.\nOn the street you unpack the bag and find an unusual cup. It's carved from some rather strange material. Swede. It's carved out of a swede. You not sure that you can drink from it, but you definitely will keep it as a memorabilia.",
    "break table",
    "BEER HINT: What does that guy on the right do? https://youtu.be/670BrA4jUoM?t=145")
    // Note for an outside reader: 200 points is the max you could get for that game. O1 is the name of the course. The joke here is that it was advised to add more locations to your game - here it is, though per se this area is pretty pointless
  private val medalItem = new Item("+200c o1/extra area gold (you hope so) medal",
    "My precious thing",
    "In the very center of the meadow you see a golden medal. On one face the characters read \"+200c o1\" while on the other \"for an extra area\". You don't understand it means, but the medal is shiny, so you decide to take it.",
    "",
    "Nice place to drink some Karqoc")
  private val keyItem = new Item("the keys from sir mr. the president's residence",
    "They remind you that you met the man himself. Maybe you'll come visit him.",
    "As it is the best thing Sir Mr. The President has heard in the long time he grants you the keys from this residence and invites you to come to play some games with him sometime. You got the keys. He also would give you a beer but he drank all already.",
    "interview",
    "BEERHINT: (note that we're using English here) https://www.youtube.com/watch?v=KYhvVUwU__U")
  private val ticketItem = new Item("lifetime speksi ticket",
    "Nice thing to have, provided that your life will be long.",
    "You shout \"OMSTART ENOUGH\". The actors read it as \"finish the show\" and, hastily bowing to the audience, skitter away. When you're about to the ampthitheatre an actor approaches you and gives a lifetime ticket to any speksi show out there. \n\"You just saved some life, you know? Just last year 3 speksi actors were driven crazy by audience. One of them is still in a psych ward \"",
    "omstart enough",
    "BEERHINT: What is one of the words a person yells when wants to discontinue something? Also keep in mind that it's speksi.")
  private val silverTongueItem = new Item("silver tongue",
    "A silver tongue right from a preacher. You still feel somewhat disgusted about that thing, but it's too unique not to keep it.",
    "Speaking in tongues! That's what she does. You tell her about it and she immediately stops. People, as if they threw off the shackles of her magic, disperse. \n\"You are a smart one. I'll reward it.\" - she tells you. \nSilver tongue. The preacher starts pulling her tongue. At first you watch at it with disgust, then turn away. When preacher's grunting is over, you look at her. She extends you her hand with something silvery on it. You look closer – it's the tongue! Her tongue is silver? What? How?\n\"Take it, I don't really need it\".\nHaving no idea what is going on you take the silver tongue. ",
    "speaking in tongues",
    "BEERHINT: You can find the answer here: https://en.wikipedia.org/wiki/Charismatic_Christianity")
  private val mugItem = new Item("jumbo's mug",
    "\"Best Receptionist\" coffee mug. Nothing special about it. Can't say the same about its previous owner – which is the reason you keep it",
    "You are both squinting and clenching fists, prepared to both fight and be beaten. To your surprise, Jumbo applauds. He says you are the first answer to say \"no\" in years (he doesn't specify though, \"no\" to him or to this particular question. Probably both). \n\nHe gives you his \"Best Receptionist\" coffee mug with his photo on it. \"Hope I'll never see you here again\" he says to you when elevator's doors are closing." + """¯\\_(ツ)_/¯""",
    "no",
    "BEERHINT: Come on, this one is very simple: https://psychcentral.com/lib/learning-to-say-no")

  // Adding quest items to the map
  eatery.addItem(swedeCupItem, "You go inside to eat some food with your mouth. It's kinda boring in there. Somewhat a lot of people inside, but you don't hear even the slightest sound of that mellow humming of a place where human beings are eating. Bummer.\nThere's a TV with some American late-night talk show. The host is a funny guy. He laughs a lot, makes faces. When he laughs he hits or slaps the table. Looks funny! He hits it so many times you get to wonder how he didn't crack the table.\nYou are the only one enjoying the show. All other visitors are silently eating their food.\nThere's something depressing in the whole scenery you witness. Why are people so sad and boring?\nYou want to be a funny person too! You want to make a difference!\nYou are sitting at your table now, what's your next move?")
  extraArea.addItem(medalItem, "")
  square.addItem(keyItem, "A grass field is crowded. On a make up scene there's Sir Mr. The President talking with people. People are lined up to the mic where they can ask a question to him.\nYuo watched something with him that was particularly joyful to you. You are trying to recall what it was to tell Sir Mr. The President about it. You know he gonna be happy to hear that.")
  amphitheater.addItem(ticketItem, "There’s a speksi show right now. \n\nYou decide to watch it.\n\nAs the show goes on, the crowd gets out of the line - the artists are forced to act with combination of french-chinese-russian accent, crawling, naked and jumping.\n\nYou get tired of all the cacophony. The viewers, feeling the complete control over the poor actors, run amok. What most folks shout is not even heard anymore – their voices are lost in the all-permeating roar of other viewers.\nYou get the strongest migraine, and feels your eardrums got permanent damage. You look around and see a couple of spectators in tears, some look lost and helpless, but most of the visitors try to shout as loud as they can and force their will upon the performers. Eyes of each of the rowdy crowd are bloodshot, their fists are clenched. The actors are on their last legs\nYou wish it all finished at once.\nEnough is enough.\nBut you realize you have the strength to shout so loud that the actors will hear you. What do you want to shout?")
  omegaBloke.addItem(silverTongueItem, "While walking through Omega Bloke you see a woman who invites you with a gesture. You have never seen her. It's difficult to tell her age. There's nothing particular about her at first glance, aside from the huge glittering cross at her chest.\nAs you start speaking, she asks you about the god and religion. Not a member of any particular confession nor an atheist, you chat with her. You agree with some her claims while questioning or opposing other.\n\nAs you two talk you realize she is extremely charming. You still don't know her name. Inwardly, you call her Charismatic Christian.\nShe tries you to convince you that miracles exist, you are doubtful.\n\"Then how do you call this\" - she says and starts something you have never seen or heard before. Funny thing is you can't really describe it. She utters something incessantly, but you can't understand it. Well, you understand something emotional level, but it all stays ineffable in our everyday language.\nOther people seem to be attracted by it. Soon there's crowd around Charismatic Christian. They look interested. Even more than that, they are enchanted for the reason unbeknownst to you.\n\nYour attitude is pretty straightforward - you don't understand what’s going on so you don't like it! And you want to end it, obviously.\n\n\nWhat language does she speak? She speaks..., speaks for..., speaks in... no idea. Do you have to speak in... a particular language? Can it be something else?\n\nIf you quickly look up the entomology of that word would it help? Or was ethnogenesis? Something like this certainly. You never had the aptitude for difficult words anyways but you know the direction.\n\nOne way or another, you feel you can figure out how to call what she’s doing.")
  office.addItem(mugItem, "On the way to the office you entertain yourself with thoughts about job and labour.\n\nYou heard somewhere that philosophers of ancient Greece thought that a free man (or a free woman to that end, we're in 21st century, not in antiquity anymore) is the one that doesn't have to work but rather chooses to work or not on whatever she or he deems important. Like philosophy, war, drinking or sports.\nThen you think those ancient greek philosophising guys were actually akin to super humans.\n\nTake Plato, for example. He wrestled, traveled, ran the first university in the area, wrote dystopian fiction and at the end became one of the founding fathers of Western civilization.\n\nNot bad, right? You could learn something from these folks.\n\n\nIn the office, you are greeted by a receptionist - Jumbo - a huge and tough guy, former MMA fighter and bouncer, with cauliflower ears. Very intimidating - you've always felt that if you tell him sth wrong he'll render you not long for this world. You're in different leagues, but it doesn't really matter.\n\nInterestingly, he is a somewhat of a mascot of HipAndNiceAndSuperPuperPlaceToWorkAt today - he always demonstrated so enthusiastic and loyal attitude to the company so that he became a pet of the management and most of your coworkers. Gossips are he even got a small number of company shares from owners.\n\nToday he seems to be very irritated, for some reason. You never saw him like that, but you've heard that few years ago he beat the crap out of one employee on the parking lot for no reason. The beaten one got generous compensation, while Jumbo got a warning and a voucher to a therapist.\nHe greets you with his grating voice: \"Hei, ready to work?\"\n\nWhat do you say?")

  // Adding hinting item (beer) to the map + descriptions
  DMTStreet.addBeer("While you're walking down the street, a flock of sparrows hovering over you. You see something glistening among them. That something falls right in front of you. It's a can of Karqoc beer. In mint condition, aside from a small scratch. You never trusted sparrows. But it also never hurts to have a beer at hand.")
  eatery.addBeer("Today is the day when instead of water The Sweet Swede Eatery serves beer out of the taps. One pint per person, you can fill your empty water bottle.")
  square.addBeer("There's a working portable sauna next to the field. You go there for just 5 minutes and discover an unopened can of Karqoc inside. Someone probably forgot it. You know it's bad to take someone else's beer, but you still can do it.")
  omegaBloke.addBeer("You pass through the alcohol shelves of the local shop and see a can of beer. The last one actually.")
  office.addBeer("You see an unopened can of beer under a bench. Take it or leave it.")

  def houseParty = this.party
  def yourRoom = this.room
  def additionalArea = this.extraArea

  val NPCsDirections = Vector(DMTStreet, eatery, extraArea, square, amphitheater, omegaBloke)
  val NPC = new NPC(NPCsDirections(Random.nextInt(NPCsDirections.size)), "Red Cross Lady", this)



  /** The character that the player controls in the game. */
  val player = new Player(room, this)

  // Limit after exceeding which a player gets an alternate ending
  val dayLimit = 6

  var currentTime = 9

  var dayCount = 0

  // Checking if a player can go outside - when the creatures are not on the streets
  def timeIsUp = this.currentTime >= 21 || this.currentTime < 9

  def isPlayerInUni = this.player.location.name == "University"


  /** Determines if the adventure is complete, that is, if the player has won. */
  def isComplete = {
    (this.yourRoom.itemsNumber == 6)
  }

  /** Determines whether the player has won, lost, or quit, thereby ending the game. */
  def isOver = this.isComplete || this.player.hasQuit || this.player.isDrunk || this.player.wentToParty

  /** Returns a message that is to be displayed to the player at the beginning of the game. */
  def welcomeMessage = s"Heya, and welcome to ${this.title}." +
      "\nBelow are the rules of the game. Scroll down to read the starting message and understand you current situation in the game. You also might want to expand the game window, for your convenience." +
      "\n\nThe game is pretty simple and straightforward. You move around, read letters and compose them into words, solve puzzles, get rewards and enjoy yourself." +
      "\nThere are two ways to lose in the game and two endings to it. These conditions won't be disclosed at that point, but you will understand when you lose or win. But if you make only prudent decisions, you won't lose. Perhaps, you'll even win. So the conditions are not really important." +
      "\n\nAnother noteworthy thing about the game is that game doesn't end until you win. So be prepared." +
      "\n\nIn this game there's a particular item you can use. You can use it with \"use itemname\" command. Don't ask me why it's phrased so weirdly, I don't understand it either, but such are the rules. If you have that item, some magic will happen, which may well help you in your adventure." +
      "\n\nWhen prompted for an answer during puzzles you can always opt to go to a neighboring location. Unless you solved that puzzle you always will be prompted for it in this location, so you can take your time and walk around until you come up with the answer." +
      "\n\nEach day you have a limited amount of time. When the time ends, you are forced to relocate to your room as soon as possible. The reason for that is that the universe is populated by wicked creatures, which crawl out of their dens only at night." +
      "\nThey call each other droogi (singular droog), and can be noticed from afar by colourful jumpsuits they wear. Depending on the clique a droog belongs to, the color of a jumpsuit differs. At time you can see them donned in eccentric headgear. They treat the surrounding as a vaysay, drink moloko and eager to apply ulta-violence to anyone who doesn't pay them tribute. Naturally, you don't want to meet them." +
      "\nIn any other area of your city the chance to meet them at night is next to none, but it just happens that you live where you live." +
      "\n\nSome puzzles are more challenging than others. Oftentimes you can get a hint right from the text of the puzzle. For some people, an answer might be not that obvious." +
      "\n\nOh, and you can type in \"help\" to get a full list of commands!" +
      "\n\nThat's about it, have fun!" +
      "\n\n_________________________________________________________________" +
      "\n\nYou wake up in your bed with a very, very heavy head. You don't remember what happened last night. Did you bring someone to your house? Was it a single person or a company? She, he or they? Did you come back home alone? Did someone break into your house?" +
      "\n\nIt's a wild goose chase. You can't remember a thing. All you can notice now is that your room is empty." +
      "\n\nRight, you still have your computer, phone, wallet and other personal belongings around – this doesn't count. The problem is that your room doesn't feel cozy as you want it to be. It lacks something." +
      "\nMaybe yours last night guest(s) took that something. Maybe it's always been like that." +
      "\n\nThe thing is, your room looks barren. You don't like it. You firmly decide to fill your room with memorable items as soon as possible. However, you don't want to make a scrapyard out of your room, so you decide to stick with the number that symbolizes luck." +
      "\n\nFor some reason it matters a lot for you. You WANT it. You want to do it for yourself. You want to do it for the kidz. You want to do it for your country, after all." +
      "\n\nIt's not a simple undertaking. You have an inkling that you'll have to face many challenges, solve cool puzzles, be on the verge of failing with your task or even dying, and fun fun fun." +
      "\n\nThat being said, you have no idea how to quickly decorate your rooms with something nice. Stock stuff from IKEAsque stores is not an option for you. But you also don’t want to spend too much time on that task." +
      "\n\nFor that reason, you decide just to go with the flow and live your normal day. Just be open to the world and it will help you!" +
      "\n\n[please scroll all the way up to read the full message and preferably EXPAND(!) the game window as much as possible]"


  /** Returns a message that is to be displayed to the player at the end of the game. The message
    * will be different depending on whether or not the player has completed their quest. */
  def goodbyeMessage = {
    if (this.isComplete && this.dayCount > this.dayLimit) {
      "Congrats, you did it! But... spent too much time on it. Why did it take so long?" +
        "\n\nIt doesn't matter now, since you've been visited by authorities." +
        "\nYou've been doing weird stuff around for too long now and been snitched on by local residents." +
        "\n\nAuthorities revoke your visa or stip you of the citizenship (even if you are the most Finnish and finest Finn out there). The state doesn't tolerate individuals like you." +
        "\n\nBut at least you managed to collect the items for the apartment – the one you can't live in anymore." +
        "\n\nWell done:)"
    } else if (this.isComplete) {
       "At long last you did it. You are the happiest person on earth!" +
       "\nYou just realized you collected that lucky number of memorable items - 6 in total!" +
       "\n\nNot only the items you collected are very special, but you also met interesting people along the way, demonstrated the sharpness of your wit and avoided temptations." +
       "\n\nCongrats!"
    } else if (this.player.isDrunk)
      "You should not have had that last Karqoc... That beer turned out to be way stronger than you expected. You can't continue with your enterprise in particular and your life in general." +
        "\n\nYou are a mess. Game Over."
      else if (this.player.wentToParty)
      "Why would you go to a party if you have so much to do? Game Over."
      else  // game over due to player quitting
      "Quitter!"
  }



  /** Plays a turn by executing the given in-game command, such as "go west". Returns a textual
    * report of what happened, or an error message if the command was unknown. In the latter
    * case, no turns elapse. */
  def playTurn(command: String) = {
    val action = new Action(command)
    val outcomeReport = action.execute(this.player)


    if (outcomeReport.isDefined) {
      if (this.player.location == this.party) {
        this.player.wentToParty = true
      } else if (this.player.location == this.university) {
        this.currentTime = 21
        this.NPC.teleport()
      } else {
        if (this.currentTime >= 24) {
          this.currentTime = 0
          this.dayCount += 1
          this.NPC.teleport()
          this.player.transferItems
        } else {
            this.currentTime += 1
            this.NPC.teleport()
            this.player.transferItems
          }
      }
    }
    outcomeReport.getOrElse("Unknown command: \"" + command + "\".")
  }


}

