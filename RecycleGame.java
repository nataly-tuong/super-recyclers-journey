import java.util.Scanner;
import java.util.Arrays;
import java.util.InputMismatchException; //https://stackoverflow.com/questions/16816250/java-inputmismatchexception
import java.util.concurrent.TimeUnit; //Use time units instead of milliseconds only

public class RecycleGame {
  static Scanner input = new Scanner(System.in); // Scanner to receive input

  // ASCII color codes (taken from carbon monoxide module)
  static final String ANSI_RESET = "\u001B[0m"; // Reset to background color
  static final String ANSI_GREEN = "\u001B[32m"; // Green color
  static final String ANSI_RED = "\u001B[31m"; // Red color
  static final String ANSI_CYAN = "\u001B[36m"; // Cyan color

  // Dialogue tags
  static final String boldText = "\u001B[1m"; // Bold text
                                              // (https://stackoverflow.com/questions/29109678/print-in-bold-on-a-terminal)
  static final String superRecycler = boldText + ANSI_CYAN + "Super-Recycler" + ANSI_RESET + ": "; // Cyan coloring tag
                                                                                                   // for Super
                                                                                                   // Recycler's
                                                                                                   // dialogue
  static final String pollutionWizard = boldText + ANSI_RED + "Dr. Pollution Wizard" + ANSI_RESET + ": "; // Red
                                                                                                          // coloring
                                                                                                          // tag for
                                                                                                          // pollutionWizard's
                                                                                                          // dialogue

  // Game Settings
  static String playerName = ""; // Initialize a variable to store the player's name
  static boolean hasName = false; // Initialize a boolean to check whether or not a name is set yet

  public static void main(String[] args) {
    if (args.length != 0 && args[0].equals("-help")) { // Option to display help mode
      displayHelp(); // Display help mode through the method
    }

    // Dialogue (introduction)
    dialogue(1, "Oh no our world is a messy disaster!"); // Refer to dialogue() below
    dialogue(2, "Mwahahaha! I am so close to winning!");
    dialogue(1, "You there, maybe you can help out.");

    // Get the player's name
    getPlayerName(); // Refer to getPlayerName() below
    dialogue(2, getPlayerName() + ", you...? Darn it!");
    dialogue(1, getPlayerName() + " we must sort out these items to clean up the city!");

    // Difficulty selection
    Integer choice; // Initializes a choice variable for difficulty selection
    dialogue(3, "In which way should I do my recycling conquest?");

    String format = "| %-20s | %-4d |%n"; // https://stackoverflow.com/questions/15215326/how-can-i-create-table-using-ascii-in-a-console
    System.out.printf("*----------------------*------*%n");
    System.out.printf("| DIFFICULTY       	| ID   |%n");
    System.out.printf("*----------------------*------*%n");
    System.out.printf(format, "Easy", 1);
    System.out.printf(format, "Medium", 2);
    System.out.printf(format, "Hard", 3);
    System.out.printf("*----------------------*------*%n");

    do { // Only accepts proper integer input and rejects string input
      try {
        wait(1);
        choice = input.nextInt(); // Store the difficulty the player wants to do
        switch (choice) {
          case 1:
            easy(); // Refer to easy() below
            break;
          case 2:
            medium(); // Refer to medium() below
            break;
          case 3:
            hard(); // Refer to hard() below
            break;
          default:
            dialogue(3, "I should pick an existing adventure."); // Warns the user to pick a correct number
            break;
        }
      } catch (InputMismatchException e) {
        choice = 0; // Sets choice to an invalid number to reset the loop
        input.nextLine(); // Clear the scanner
        dialogue(3, "I need to pick an adventure through a number."); // Warns the user to only enter numbers
      }
    } while (choice < 1 || choice > 3);
  }

  // Methods
  public static void easy() { // Initializes the easy difficulty mode BUGGED WIP STILL
    dialogue(2, "There is only a bit of turmoil in this city!");
    String[][] chosenItem = pickRandom("easy");
    dialogue(2, "Filth has risen! I have placed " + makeBold(chosenItem[0][0]) + " everywhere!");
    dialogue(1, makeBold(chosenItem[0][0]) + " are litter!");

    String choice = null;

    do {
      dialogue(1, getPlayerName() + " are " + makeBold(chosenItem[0][0]) + " recycleable or not? [Y/N]");
      wait(1);
      choice = input.nextLine();
    } while (!choice.equalsIgnoreCase("y") && !choice.equalsIgnoreCase("n") && choice != null);

    if ((choice.equalsIgnoreCase("y")) && (chosenItem[0][1]).equals("can be recycled")) {
      dialogue(1, "Yes! " + makeBold(chosenItem[0][0]) + " " + (chosenItem[0][1]) + ".");
    } else {
      dialogue(2, "Hahaha! No, " + makeBold(chosenItem[0][0]) + " " + (chosenItem[0][1]) + ".");
    }
    System.out.println("* " + chosenItem[0][2]);
  }

  public static void medium() { // Initializes the medium difficulty mode
    dialogue(2, "There is a considerable amount of trouble in this city!");
  }

  public static void hard() { // Initializes the hard difficulty mode
    dialogue(2, "I will overtake this city and bring it to ruins!");
  }

  /*
   * The "displayHelp" method displays a certain message only when the -help
   * option
   * is used by the player.
   */
  public static void displayHelp() {
    System.out.println("This message is displayed when " +
        "you run this program with -help " +
        "option.");
    System.exit(1);
  }

  public static String[][] pickRandom(String difficulty) {
    String[][] easyBins = {
        { "Newspapers", "can be recycled",
            "These are made of high-quality fibers that can be easily pulped and remade into new paper products." },
        { "Magazines", "can be recycled",
            "Like newspapers, they are made from paper fibers that can be broken down and recycled into other paper products." },
        { "Office paper", "can be recycled",
            "Contains long fibers that are valuable in the paper recycling process to produce new paper." },
        { "Junk mail and envelopes", "can be recycled",
            "The fibers and even small plastic windows can be separated in the pulping process." },
        { "Cardboard", "can be recycled",
            "Made from a heavy paper stock that can be reprocessed into new cardboard and other paper products." },
        { "Paperboard", "can be recycled",
            "Similar to cardboard, it's made from processed paper fibers that can be recycled into new paper items." },
        { "Glass bottles and jars", "can be recycled",
            "Glass is 100% recyclable and can be melted down and reformed into new glass products indefinitely without loss of quality." },
        { "Aluminum cans", "can be recycled",
            "Aluminum is extremely recyclable, and recycling it saves a significant amount of energy compared to producing new aluminum from ore." },
        { "Steel cans and tin cans", "can be recycled",
            "Steel is magnetic, which makes it easy to separate from other waste using magnets, and it can be melted down and reused." },
        { "Aluminum foil and aluminum trays", "can be recycled",
            "Like cans, clean aluminum products can be melted and reused." },
        { "Metal lids from jars and bottles", "can be recycled",
            "These are generally made from steel or aluminum and can be recycled in the same way as cans." },
        { "Milk cartons", "can be recycled",
            "These are made from paper with a thin layer of plastic and sometimes aluminum, which can be separated in a pulping process." },
        { "Egg cartons", "can be recycled",
            "Made from recycled paper pulp, they can be easily broken down and remade into new paper products." },
        { "Phone books", "can be recycled",
            "Like other paper products, they are made from paper fibers that can be recycled into new paper items." },
        { "Paper towel rolls", "can be recycled",
            "The cardboard tubes are easily recyclable and can be pulped and remade into new paper products." },
        { "Brown paper bags", "can be recycled",
            "These can be recycled with other paper products and used to make new paper bags or other paper items." },
        { "Plastic grocery bags", "can not be recycled",
            "While not typically accepted in curbside bins, many grocery stores have collection bins for recycling plastic bags." },
        { "Glass containers", "can be recycled",
            "Clear glass is most common, but brown and green glass can also be recycled into new glass products." },
        { "Glassware", "can be recycled",
            "Clean glassware like drinking glasses can be recycled, but mirrors and window glass are typically not accepted." },
        { "Aluminum pie plates", "can be recycled",
            "Clean aluminum pie plates can be recycled along with aluminum cans and foil." },
        { "Styrofoam", "can not be recycled",
            "Styrofoam is not typically recyclable because it's difficult to process and often contaminated." },
        { "Disposable coffee cups", "can not be recycled",
            "Disposable coffee cups are often lined with plastic or wax, making them difficult to recycle." },
        { "Pizza boxes with grease or food residue", "can not be recycled",
            "Pizza boxes with grease or food residue are often too contaminated to be recycled." },
        { "Plastic straws and utensils", "can not be recycled",
            "Plastic straws and utensils are often too small and lightweight to be sorted and recycled effectively." },
        { "Broken glass", "can not be recycled",
            "Broken glass poses a safety hazard to workers in recycling facilities and is typically not accepted for recycling." },
        { "Light bulbs", "can not be recycled",
            "Light bulbs contain hazardous materials and should be disposed of properly according to local regulations." },
        { "Used tissues and paper towels", "can not be recycled",
            "Used tissues and paper towels are often too contaminated with germs to be recycled effectively." },
        { "Clothing and textiles", "can not be recycled",
            "Clothing and textiles can be reused or donated, but they are typically not accepted in curbside recycling bins." },
        { "Disposable diapers", "can not be recycled",
            "Disposable diapers are made from mixed materials and are not recyclable." },
        { "Chip bags and candy wrappers", "can not be recycled",
            "Chip bags and candy wrappers are often made from mixed materials that are difficult to separate and recycle." },
        { "Wet or heavily soiled paper", "can not be recycled",
            "Wet or heavily soiled paper can contaminate other recyclables and should be disposed of in the trash." },
        { "Wax paper and parchment paper", "can not be recycled",
            "Wax paper and parchment paper are often coated with non-recyclable materials." },
        { "Pet food bags", "can not be recycled",
            "Pet food bags are often made from mixed materials and are not accepted in curbside recycling programs." },
        { "Ceramics and pottery", "can not be recycled",
            "Ceramics and pottery are made from materials that cannot be recycled in typical curbside recycling programs." }
    };

    String[][] mediumBins = {
        { "Plastic bottles and containers with the recycling symbols #1 through #7", "can be recycled",
            "Plastics are categorized by resin codes which indicate the type of plastic. Most are recyclable depending on local facilities." },
        { "Plastic jugs (milk, juice, water)", "can be recycled",
            "These are often made from HDPE or PET, which are widely accepted plastics for recycling." },
        { "Detergent and soap bottles", "can be recycled",
            "Typically made from HDPE, these are easily recycled into new containers and other plastic products." },
        { "Shampoo and body wash bottles", "can be recycled",
            "Commonly made from PET, these can be recycled into polyester fiber for clothing, carpeting, and more." },
        { "Milk cartons", "can be recycled",
            "These are made from paper with a thin layer of plastic (and sometimes aluminum), which can be separated in a pulping process." },
        { "Juice boxes and soup cartons", "can be recycled",
            "Similar to milk cartons, these are recyclable through specialized processes that separate their composite materials." },
        { "Plastic plant pots", "can not be recycled",
            "Many recycling programs accept plastic plant pots, but check local guidelines as some may not." },
        { "Plastic buckets", "can not be recycled",
            "Often made from HDPE, plastic buckets can be recycled into new plastic products." },
        { "Plastic food containers (clean)", "can not be recycled",
            "Clean plastic food containers like yogurt cups and takeout containers are usually recyclable." },
        { "Plastic lids (clean)", "can not be recycled",
            "Clean plastic lids from containers and jars can often be recycled along with the containers." },
        { "Plastic squeeze bottles (clean)", "can not be recycled",
            "Clean plastic squeeze bottles for condiments, sauces, and other products are recyclable." },
        { "Plastic clamshell containers (clean)", "can not be recycled",
            "Clean plastic clamshell containers for berries, baked goods, and other products can be recycled." },
        { "Plastic cutlery (clean)", "can not be recycled",
            "Clean plastic cutlery can sometimes be recycled, but it's best to check local guidelines." },
        { "Plastic hangers", "can not be recycled",
            "Many recycling programs accept plastic hangers, but some may not due to their shape." },
        { "Plastic toys (clean)", "can not be recycled",
            "Clean plastic toys can often be recycled, but check local guidelines as some programs may not accept them." },
        { "Plastic shower curtains", "can not be recycled",
            "Made from PVC, plastic shower curtains can sometimes be recycled at specialized facilities." }
    };

    String[][] hardBins = {
        { "Styrofoam (Expanded Polystyrene)", "can not be recycled",
            "Styrofoam is not typically recyclable because it's difficult to process and often contaminated." },
        { "Disposable coffee cups", "can not be recycled",
            "Disposable coffee cups are often lined with plastic or wax, making them difficult to recycle." },
        { "Pizza boxes with grease or food residue", "can not be recycled",
            "Pizza boxes with grease or food residue are often too contaminated to be recycled." },
        { "Plastic straws and utensils", "can not be recycled",
            "Plastic straws and utensils are often too small and lightweight to be sorted and recycled effectively." },
        { "Broken glass", "can not be recycled",
            "Broken glass poses a safety hazard to workers in recycling facilities and is typically not accepted for recycling." },
        { "Light bulbs", "can not be recycled",
            "Light bulbs contain hazardous materials and should be disposed of properly according to local regulations." },
        { "Used tissues and paper towels", "can not be recycled",
            "Used tissues and paper towels are often too contaminated with germs to be recycled effectively." },
        { "Clothing and textiles", "can not be recycled",
            "Clothing and textiles can be reused or donated, but they are typically not accepted in curbside recycling bins." },
        { "Disposable diapers", "can not be recycled",
            "Disposable diapers are made from mixed materials and are not recyclable." },
        { "Chip bags and candy wrappers", "can not be recycled",
            "Chip bags and candy wrappers are often made from mixed materials that are difficult to separate and recycle." },
        { "Wet or heavily soiled paper", "can not be recycled",
            "Wet or heavily soiled paper can contaminate other recyclables and should be disposed of in the trash." },
        { "Wax paper and parchment paper", "can not be recycled",
            "Wax paper and parchment paper are often coated with non-recyclable materials." },
        { "Pet food bags", "can not be recycled",
            "Pet food bags are often made from mixed materials and are not accepted in curbside recycling programs." },
        { "Ceramics and pottery", "can not be recycled",
            "Ceramics and pottery are made from materials that cannot be recycled in typical curbside recycling programs." },
        { "Plastic food pouches (clean)", "can not be recycled",
            "Clean plastic food pouches for products like baby food and snacks are recyclable in some recycling programs." },
        { "Plastic toothbrushes (clean)", "can not be recycled",
            "Clean plastic toothbrushes can sometimes be recycled through specialized programs." },
        { "Plastic pens and markers (empty)", "can not be recycled",
            "Empty plastic pens and markers can sometimes be recycled through specialized programs." },
        { "Plastic CD and DVD cases", "can not be recycled",
            "Made from various types of plastic, CD and DVD cases can sometimes be recycled at specialized facilities." },
        { "Plastic film canisters", "can not be recycled",
            "Made from various types of plastic, film canisters can sometimes be recycled through specialized programs." }
    };

    if (difficulty.equals("easy")) {
      int random = (int) (Math.random() * (easyBins.length));
      String[][] chosenItem = new String[1][easyBins[random].length];
      chosenItem[0] = Arrays.copyOf(easyBins[random], easyBins[random].length);
      return chosenItem;
    }
    return null;
  }

  /*
   * The "getPlayerName" method's purpose is to store and recall the player's
   * name. The reason why this is a method is so that it can be accessed across
   * other
   * methods,
   * such as the "dialogue" method. The first time it is called, it will prompt
   * the
   * user for their name. The proceeding times will not prompt them again.
   */
  public static String getPlayerName() {
    if (!hasName) { // Checks if the player already has a name
      dialogue(1, "Hello, what is your name?"); // Ask for the player's name
      wait(1);
      playerName = input.nextLine(); // Stores the player's name

      while (playerName.equals("")) { // The program will not continue unless a valid name is given
        dialogue(1, "I'm not sure I quite understand..."); // Response to an empty response
        playerName = input.nextLine(); // Stores the player's name
      }
      hasName = true; // Flags the player as having a name once set
      playerName = (makeBold(playerName)); // Formats the player's name
    }
    return playerName; // Return the saved name
  }

  public static String makeBold(String thing) {
    String text = boldText + thing + ANSI_RESET;
    return text;
  }

  /*
   * The "dialogue" method takes two parameters. The first parameter taken is a
   * integer and should either
   * be 1,2, or 3. 1 is for Super Recycler's tag, 2 is for Pollution Wizard, and 3
   * is for the player. The second parameter must be a string that the character
   * will say.
   */
  public static void dialogue(int character, String dialogue) { // Method for dialogue
    wait(1);

    if (character == 1) // Checks if dialogue is for Super Recycler
      System.out.println(superRecycler + dialogue);
    else if (character == 2) // Checks if dialogue is for Pollution Wizard
      System.out.println(pollutionWizard + dialogue);
    else // Defaults to dialogue for player
      System.out.println(getPlayerName() + ": " + dialogue);
  }

  /*
   * The 'wait" method takes one parameter. The parameter taken is a integer.
   * The java programming will pause for the amount of seconds indicated.'
   */
  public static void wait(int seconds) {
    try { // https://coderanch.com/t/672865/java/making-console-program-pause
      TimeUnit.SECONDS.sleep(seconds);
    } catch (InterruptedException ie) {
    }
  }
}
