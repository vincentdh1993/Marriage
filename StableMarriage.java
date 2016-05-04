
import java.io.*;
import java.util.*;

public class StableMarriage {
	static List<Match> choose = new LinkedList<Match>();
	static List<Match> chosen = new ArrayList<Match>();
	
	public static void main(String[] args) throws FileNotFoundException {
		Scanner console = new Scanner(System.in);
		System.out.println("What is the file name?");
		String f = console.next();
		File file = new File(f);
		//First part for male choosing.
		intro(1);
		loadMatches(new Scanner (file), true, true);
		matchMake();
		printChoices();
		System.out.println();

		//Second part for female choosing.
		intro(2);
		loadMatches(new Scanner (file), false, true);
		matchMake();
		printChoices();
	}
	
	/*
	 * This method is for intro and decides which gender should go first and reset the list for choosing part and chosen part.
	 */
	
	public static void intro(int a) throws FileNotFoundException {
		// LinkedList for the choosing people since it is in order.
		choose = new LinkedList<Match>();
		// ArrayList for the chosen people since it is not in order.
		chosen = new ArrayList<Match>();
		
		//taking int parameter to know which gender goes first.
		System.out.print("Matchmaking for ");
		if (a==1) {
			System.out.println("MALES: ");
		} else {
			System.out.println("FEMALES:");
		}
	}

	/*
	 * Method that loads the matches to the list
	 */
	public static void loadMatches(Scanner file, boolean maleFirst, boolean notchecked) {
		boolean Done = false;
		
		while (!Done) {
			Scanner line= new Scanner(file.nextLine()); //scanner that takes the lines.
			String name = line.next(); //scans through the line and takes strings which are names.
			List<Integer> choices = new ArrayList<Integer>(); //take integers to choices list.
			if (name.equals("END")) { //If the name is equal to end, which means it ended, change the boolean. 
				Done = true;
			} else {
				while (line.hasNextInt()) {
					choices.add(line.nextInt());
				}
			}
			if (!Done) {
				// add the matches to each list depending on which gender are we on. By using substring method, we can erase the colon.
				if (!maleFirst) {
					chosen.add(new Match(name.substring(0, name.length()-1), choices));
				} else {
					choose.add(new Match(name.substring(0, name.length()-1), choices));
				}
			}
		}
		// repeats the method, which goes to other gender part.
		if (notchecked) {
			loadMatches(file, !maleFirst, false);
		}
	}	
	/*
	 * Method that frees each Match from the list.
	 */
	public static void letfree( List<Match> list){
		for (Match m : list){
			m.free();
		}
	}
	/*
	 * Method that checks if some man are fee from the list.
	 */
	public static boolean someManFree(List<Match> list) {
		for (Match Match : list) {
			if (Match.isFree() && !Match.getMatch().isEmpty()) {
				return true;
			}
		}
		return false;
	}
	/*
	 * Method that makes the match. First, let each person to be free and while some man is free, make the match with their first choice. 
	 * If successed, remove all the other chances.
	 */

	public static void matchMake() {
		letfree(choose); //let ppl free.
		letfree(chosen);
		//System.out.println(choose);  //>> If the user wants to see the list, just un-comment this line.
		
	
		int favorGender = 0; // favorGender is the index for favored gender on the List
		while (someManFree(choose)){// method call to check if someone's single
			for (Match d: choose) {
				if (d.isFree() && !d.getMatch().isEmpty()) {
					int bestone = d.getMatch().get(0);// get their best choice's index number
					Match best = chosen.get(bestone);
					
					if (!best.isFree()) {							
						choose.get(best.getPartner()).free();
						best.free();									
					}
					
					d.success(bestone);								//successing the match make setting partners.
					best.success(favorGender);							
					removeMatches(favorGender, best, bestone);			// remove all chances.
				}
				favorGender = (favorGender+1) % choose.size();			// reset favorGender, if it reaches the end, it will be 0.
			}
		}
	}
		
	/*
	 * Method that removes Matches from the list. 
	 * delete w from q's preference list delete q from w's preference list
	 */
	public static void removeMatches(int Match, Match best, int bestone) {
		
		Match Fail; //create the failed person from the matchmaking.
		
		// in the loop, remove the match at int i of best's match since it failed. Also remove the match in int i.
		for (int i = best.getMatch().indexOf(Match) + 1; i <= best.getMatch().size()-1;i++ ) {
			Fail = choose.get(best.getMatch().get(i));
			Fail.getMatch().remove(Fail.getMatch().indexOf(bestone));
			best.getMatch().remove(i);
		}
	}
	
	
	/*
	 * Method that prints the final match results.
	 */
	public static void printChoices() {
		double choiceSum=0;
		double coupleNumb=0;
		int choiceNum;
		System.out.printf("%-13s%-10s%-10s\r\n","Name","Choice","Partner"); //setting the format for the list.
		System.out.println("================================="); //10+10+13
		
		for (Match person : choose) {
			if (person.getPartner() != -1) { //if the person did not fail the match making,
				choiceNum = person.getDreamMatch().indexOf(person.getPartner()) + 1; //recall the choice number that the person chose.
				System.out.printf("%-13s%-10d%-10s\r\n", person.getName(), choiceNum, chosen.get(person.getPartner()).getName()); // print the name, choice, and partner name.
				choiceSum += choiceNum; // keep updating the total sum of the choice numbers.
				coupleNumb++; //the number of couple increases.
			} else { // if the person failed the match making,
				System.out.printf("%-13s%-10s%-10s\r\n", person.getName(), "--", "Nobody.."); //sadly, type nobody and the number of couple stays.
			}
		}
		System.out.printf("Mean choice: %.3f\r\n", (choiceSum / coupleNumb)); //Lastly, print out the math result.
	}
	
	
}