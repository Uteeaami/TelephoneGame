package fi.utu.tech.telephonegame;

import java.util.*;
import fi.utu.tech.telephonegame.util.Words;


public class Refiner {
	
	public static String actions(String outText){
		int dice = rnd.nextInt(4);
		System.out.println(dice);
		switch (dice){
			case 0:
				return(shuffle(outText));
			case 1:
				return(addBjork(outText));
			case 2:
				return(reversedWord(outText));
			case 3:
				return(predicateAdder(outText));
		}
		return "";
	}

	public static ArrayList<String> listify(String outText){
		ArrayList<String> list = new ArrayList<>(Arrays.asList(outText.split(" ")));
		return list;
	}

	/**
	 * Shuffles the words
	 *
	 * @param outText
	 * @return
	 */
	public static String shuffle(String outText){
		String[] split = outText.split(" ");
		if (split.length == 1){
			return outText + " " +"gibe words";
		}
		outText = "";
		ArrayList<Integer> splitOrder = new ArrayList<>();
		for (int i = 0; i < split.length; i++) {
			splitOrder.add(i);
		}
		Collections.shuffle(splitOrder);
		for (int i = 0; i < split.length; i++) {
			outText += split[splitOrder.get(i)] + " ";
		}
		return outText;

	}

	/**
	 * Adds Tomi Björk citation and also maybe something else....
	 * @param outText
	 * @return
	 */
	public static String addBjork(String outText){
		int dice = rnd.nextInt(100);
		if(dice < 95){
			return outText + bjorks[rnd.nextInt(bjorks.length)];
		}else{
			return outText + plagueis[rnd.nextInt(plagueis.length)]; //Rare
		}
	}

	/**
	 * Reverses a random word
	 * @param outText
	 * @return
	 */
	public static String reversedWord(String outText){
		char reverse;
		String neword ="";
		ArrayList<String> list = listify(outText);
		int random = rnd.nextInt(list.size());
		String word = list.get(random);

		for (int i=0; i<word.length(); i++){
			reverse = word.charAt(i);
			neword =reverse+neword;
		}
		return outText + " " + neword;
	}

	/**
	 * Adds predefined predicate in a random location
	 * @param outText
	 * @return
	 */
	public static String predicateAdder(String outText){
		ArrayList<String> list = listify(outText);
		StringBuilder sb = new StringBuilder();

		for(int i=0;i<list.size();i++){
			if(i == rnd.nextInt(list.size())){
				sb.append(predicates[rnd.nextInt(predicates.length)]+ " ");
			}
			sb.append(list.get(i) + " ");
		}
		return sb.toString();
	}

	/*
		If you decide to use the lists above, comment out the following three lines.
	 */
	private static final String[] predicates = Words.predicates;
	private static final String[] bjorks = Words.bjorks;
	private static final String[] plagueis = Words.plagueis;

	private static final Random rnd = new Random();

	/*
	 * The refineText method is used to change the message
	 * Now it is time invent something fun!
	 *
	 * In the example implementation a random work from a word list is added to the end of the message.
	 * But you do you!
	 *
	 * Please keep the message readable. No ROT13 etc, please
	 *
	 */
	public static String refineText(String inText) {
		// Change the content of the message here.
		return(actions(inText));
	}

	/*
	 * This method changes the color. No editing needed.
	 *
	 * The color hue value is an integer between 0 and 360
	 */
	public static Integer refineColor(Integer inColor) {
		return (inColor + 20) < 360 ? (inColor + 20) : 0;
	}

}
