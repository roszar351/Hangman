import java.awt.Dimension;
import java.awt.Font;
import java.util.ArrayList;
import java.util.Arrays;

import javax.swing.JOptionPane;
import javax.swing.UIManager;

/** Hangman by Damian Skrzypek
 *  started - 26/01/2018
 *  finished - 29/01/2018
 * 
 *  Hangman - need to either input word or random words (or both)
 * 	   	    - needs to check if letter is in word or not
 * 	 	    - some kind of 'lives' system, visualisation or just text
 *  if lives are set to defualt 6 = visual, anything custom = text 
 * 									    _____
 * 										|   O            head,body,leg,leg,arm,arm
 * 										|  /|\
 * 										|	|
 * 										|  / \
 * 										|
 * 										|_____
 * 
 * 
 * 
 */
public class Hangman {
	
	private int lives = 6;
	private ArrayList<String> words = new ArrayList<>();
	private final String[] lifeVisual = {"_____\n|\n|\n|\n|\n|_____", "_____\n|    O\n|\n|\n|\n|_____", "_____\n|    O\n|     |\n|     |\n|\n|_____",
			 					         "_____\n|    O\n|     |\n|     |\n|    /\n|_____", "_____\n|    O\n|     |\n|     |\n|    / \\\n|_____", 
			 					         "_____\n|    O\n|    /|\n|     |\n|    / \\\n|_____", "_____\n|    O\n|    /|\\\n|     |\n|    / \\\n|_____"};
	private String answer = "";
	private String category = "";
	
	public static void main(String[] args) {
		Hangman h = new Hangman();
		UIManager.put("OptionPane.minimumSize",new Dimension(350,100));
		UIManager.put("OptionPane.messageFont", new Font("Arial", Font.BOLD, 18));
		UIManager.put("OptionPane.buttonFont", new Font("Arial", Font.PLAIN, 16));
		h.initWords();
		h.start(h);
		
	}
	
	public void start(Hangman h) {
		int choice = JOptionPane.showConfirmDialog(null, "Do you want to play?", "Hello there...", JOptionPane.YES_NO_OPTION);
		if(choice == 0) {
			Object[] gamemode  = {"Random Word", "Custom Word"};
			choice = JOptionPane.showOptionDialog(null, "Choose your gamemode: ", "Why are you reading this ಠ_ಠ", JOptionPane.DEFAULT_OPTION,
												  JOptionPane.PLAIN_MESSAGE, null, gamemode, gamemode[0]);
			if(choice == 0) {
				h.randomWord();
				h.playHangman(h);
			}else if(choice == 1) {
				h.customWord();
				h.playHangman(h);
			}else {
				return;
			}
			
		}else {
			return;
		}
	}
	
	public void playHangman(Hangman h) {
		answer = answer.toLowerCase();
		String hiddenAnswer = "";
		String guess = "";
		char ch = ' ';
		int livesLeft = lives;
		boolean found = false;
		
		for(int i = 0; i < answer.length(); i++) {
			ch = answer.charAt(i);
			if(Character.isLetter(ch) == false) {
				hiddenAnswer = hiddenAnswer + ch;
			}else {
				hiddenAnswer = hiddenAnswer + '-';
			}
		}
		
		char[] hiddenAnsCharacters = hiddenAnswer.toCharArray();
		
		while(livesLeft > 0 || !found) {
			hiddenAnswer = "";
			for(int i = 0; i < hiddenAnsCharacters.length; i++) {
				hiddenAnswer = hiddenAnswer + hiddenAnsCharacters[i]; 
			}
			if(answer.equalsIgnoreCase(hiddenAnswer) == true) {
				JOptionPane.showMessageDialog(null, "You Won!!!!!!" + "\n The answer was: " + answer, "Good Job.", JOptionPane.INFORMATION_MESSAGE);
				found = true;
				break;
			}else {
				while((guess = JOptionPane.showInputDialog(h.getLifeVisual(6-livesLeft) + "\nGuess a letter\nCategory: " + category + "\n" + hiddenAnswer)).length() != 1);
				ch = guess.charAt(0);
				ch = Character.toLowerCase(ch);
				if(answer.indexOf(ch) != -1) {
					for(int i = answer.indexOf(ch); i != -1; i = answer.indexOf(ch, i+1)) {
						hiddenAnsCharacters[i] = ch;
					}
				}else {
					livesLeft--;
				}
			}
			if(livesLeft == 0) {
				JOptionPane.showMessageDialog(null, "You Lost.\n" + h.getLifeVisual(6) + "\n The answer is: " + answer, "Maybe next time.", JOptionPane.INFORMATION_MESSAGE);
				break;
			}
		}
		h.start(h);
		
	}
	
	public void randomWord() {
		int r = (int) (Math.random() * words.size());
		answer = words.get(r);
	}
	
	public void customWord() {
		answer = JOptionPane.showInputDialog("Input your word.");
		answer = answer.toLowerCase();
		category = JOptionPane.showInputDialog("Input category of the word.");
		if(category.length() == 0) {
			category = "undefined";
		}
	}
	
	public String getLifeVisual(int i) {
		return lifeVisual[i];
	}

	public int getLives() {
		return lives;
	}
	
	public void setLives(int lives) {
		this.lives = lives;
	}
	
	public String getTheWord() {
		return answer;
	}
	
	public void setTheWord(String answer) {
		this.answer = answer.toLowerCase();
	}
	
	public ArrayList<String> getWords(){
		return words;
	}
	
	public void addWord(String word) {
		words.add(word);
	}
	
	public void addWords(ArrayList<String> arrayOfWords) {
		words.addAll(arrayOfWords);
	}
	
	public void removeWord(String word) {
		words.remove(word);
	}
	
	public void initWords() {
		String[] temp = {"acres", "adult", "advice", "arrangement", "attempt", "august", "autumn", "border", "breeze", "brick",
						 "calm", "canal", "casey", "cast", "chose", "claws", "coach", "code", "computer", "constantly", "contrast", "cookies", "customs",
						 "damage", "danny", "deeply", "depth", "discussion", "doll", "donkey", "eclipse", "egypt", "ellen", "essential", "exchange",
						 "exist", "explanation", "facing", "film", "finest", "fireplace", "floating", "folks", "fort", "garage", "grabbed",
						 "grandmother", "habit", "happily", "harry", "heading", "hunter", "illinois", "image", "independent", "instant", "internet",
						 "january", "java", "keyboard", "kids", "label", "lungs", "manufacturing", "martin", "mathematics", "melted", "memory", "mill", "mission",
						 "monkey", "mouse", "mysterious", "neighborhood", "norway", "nuts", "occasionally", "official", "ourselves", "palace", "plates",
						 "poetry", "policeman", "positive", "possibly", "practical", "pride", "program", "programming", "promised", "recall", "relationship", "remarkable",
						 "require", "rhyme", "rocky", "rubbed", "rush", "sale", "satellites", "satisfied", "scared", "selection", "shake", "shaking",
						 "shallow", "shout", "silly", "simplest", "slight", "slip", "slope", "soap", "solar", "species", "spin", "stiff", "swung",
						 "tales", "thumb", "tobacco", "toy", "trap", "treated", "tune", "university", "vapor", "vessels", "wealth", "wolf", "zoo"};
		words.addAll(Arrays.asList(temp));

	}
	
}
