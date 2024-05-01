import java.util.Scanner;
import java.util.InputMismatchException; //https://stackoverflow.com/questions/16816250/java-inputmismatchexception

public class RecycleGame {
  static Scanner input = new Scanner(System.in); //Scanner to receive input

  // ASCII color codes (taken from carbon monoxide module)
  static final String ANSI_RESET="\u001B[0m"; //Reset to background color
  static final String ANSI_GREEN = "\u001B[32m"; //Green color
  static final String ANSI_RED="\u001B[31m"; //Red color
  static final String ANSI_CYAN="\u001B[36m"; //Cyan color

  // Dialogue tags
  static final String boldText = "\u001B[1m"; //Bold text (https://stackoverflow.com/questions/29109678/print-in-bold-on-a-terminal)
  static final String superRecycler = boldText+ANSI_CYAN+"Super-Recycler"+ANSI_RESET+": "; //Cyan coloring tag for Super Recycler's dialogue
  static final String pollutionWizard = boldText+ANSI_RED+"Dr. Pollution Wizard"+ANSI_RESET+": "; //Red coloring tag for pollutionWizard's dialogue

  // Game Settings
  static String playerName = ""; //Initialize a variable to store the player's name
  static boolean hasName = false; //Initialize a boolean to check whether or not a name is set yet

  public static void main(String[] args) {
	if(args.length != 0 && args[0].equals("-help")) { //Option to display help mode
  	displayHelp(); //Display help mode through the method
	}

	//Dialogue (introduction)
	dialogue(1,"Oh no our world is a messy disaster!"); //Refer to dialogue() below
	dialogue(2,"Mwahahaha! I am so close to winning!");
	dialogue(1,"You there, maybe you can help out.");

	// Get the player's name
	getPlayerName(); //Refer to getPlayerName() below
	dialogue(2,getPlayerName()+", you...? Darn it!");
	dialogue(1,getPlayerName()+" we must sort out these items to clean up the city!");

	// Difficulty selection
	Integer choice; //Initializes a choice variable for difficulty selection
	dialogue(3,"In which way should I do my recycling conquest?");

  String format = "| %-20s | %-4d |%n"; //https://stackoverflow.com/questions/15215326/how-can-i-create-table-using-ascii-in-a-console
  System.out.printf("*----------------------*------*%n");
  System.out.printf("| DIFFICULTY           | ID   |%n");
  System.out.printf("*----------------------*------*%n");
  System.out.printf(format,"Easy",1);
	System.out.printf(format,"Medium",2);
	System.out.printf(format,"Hard",3);
  System.out.printf("*----------------------*------*%n");

	do { //Only accepts proper integer input and rejects string input
	try {
    	choice = input.nextInt(); //Store the difficulty the player wants to do
    	switch(choice) {
        	case 1:
            	easy(); //Refer to easy() below
            	break;
        	case 2:
            	medium(); //Refer to medium() below
            	break;
        	case 3:
            	hard(); //Refer to hard() below
            	break;
        	default:
            	dialogue(3,"I should pick an existing adventure."); //Warns the user to pick a correct number
            	break;
    	}
	} catch (InputMismatchException e) {
    	choice = 0; //Sets choice to an invalid number to reset the loop
    	input.nextLine(); //Clear the scanner
    	dialogue(3, "I need to pick an adventure through a number."); //Warns the user to only enter numbers
	}
  } while (choice < 1 || choice > 3);
}


  // Methods
  public static void easy() { //Initializes the easy difficulty mode
	dialogue(2,"There is only a bit of turmoil in this city!");
  }

  public static void medium() { //Initializes the medium difficulty mode
	dialogue(2,"There is a considerable amount of trouble in this city!");
  }

  public static void hard() { //Initializes the hard difficulty mode
	dialogue(2,"I will overtake this city and bring it to ruins!");
  }

  /*
  The "displayHelp" method displays a certain message only when the -help option
  is used by the player.
  */
  public static void displayHelp() {
	System.out.println("This message is displayed when " +
                   	"you run this program with -help " +
                   	"option.");
  }

  /*
  The "getPlayerName" method's purpose is to store and recall the player's name.
  The reason why this is a method is so that it can be accessed across other methods,
  such as the "dialogue" method. The first time it is called, it will prompt the
  user for their name. The proceeding times will not prompt them again.
  */
  public static String getPlayerName(){
	if (!hasName) { //Checks if the player already has a name
  	dialogue(1,"Hello, what is your name?"); //Ask for the player's name
  	playerName = input.nextLine(); //Stores the player's name

  	while(playerName.equals("")) { //The program will not continue unless a valid name is given
    	dialogue(1,"I'm not sure I quite understand..."); //Response to an empty response
    	playerName = input.nextLine(); //Stores the player's name
  	}
  	hasName = true; //Flags the player as having a name once set
  	playerName = (boldText+playerName+ANSI_RESET); //Formats the player's name
	}
	return playerName; //Return the saved name
  }


  /*
  The "dialogue" method takes two parameters. The first parameter taken is a integer and should either
  be 1,2, or 3. 1 is for Super Recycler's tag, 2 is for Pollution Wizard, and 3
  is for the player. The second parameter must be a string that the character will say.
  */
  public static void dialogue(int character, String dialogue) { //Method for dialogue
	if (character==1) //Checks if dialogue is for Super Recycler
  	System.out.println(superRecycler+dialogue);
	else if (character==2) //Checks if dialogue is for Pollution Wizard
  	System.out.println(pollutionWizard+dialogue);
	else //Defaults to dialogue for player
  	System.out.println(getPlayerName()+": "+dialogue);
  }
}
