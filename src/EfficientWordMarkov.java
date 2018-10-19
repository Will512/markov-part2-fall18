import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.NoSuchElementException;

public class EfficientWordMarkov extends BaseWordMarkov {
	Map<WordGram, ArrayList<String>> myMap;
	public EfficientWordMarkov(){
		super();
		myMap = new HashMap<WordGram, ArrayList<String>>();
	}
	public EfficientWordMarkov(int order) {
		super(order);
		myMap = new HashMap<WordGram, ArrayList<String>>();
	}
	/*
	 * (non-Javadoc)
	 * Uses the training text to make a hashmap of wordgrams and arraylists for Markov modelling
	 * @param text is the text to be analyzed for the hashmap
	 * @see BaseWordMarkov#setTraining(java.lang.String)
	 */
	@Override public void setTraining(String text) {
	myMap.clear();
	myWords = text.split("\\s+");
	int highest = myWords.length - myOrder + 1;
	for(int k = 0; k < highest; k++) {
		WordGram wkey;
		String wfollow;
		ArrayList<String> empty = new ArrayList<String>();
		wkey = new WordGram(myWords, k, myOrder);
		if(k == highest - 1) {
			//wkey = new WordGram(myWords, k, myOrder);
			wfollow = PSEUDO_EOS;
		}
		else {
			wfollow = myWords[k + myOrder];
		}
		myMap.putIfAbsent(wkey, empty);
		myMap.get(wkey).add(wfollow);
	}
	}
	/*
	 * (non-Javadoc)
	 * Gets the arraylist of following elements from the map, throwing an exception if not present
	 * @param kgram is the WordGram key to the map
	 * @exception NoSuchElementException occurs when kgram is not a key to the map
	 * @return an arraylist with all the possible following words
	 * @see BaseWordMarkov#getFollows(WordGram)
	 */
	@Override public ArrayList<String> getFollows(WordGram kgram) throws NoSuchElementException{
		if(!myMap.containsKey(kgram)) {
			throw new NoSuchElementException("Key " + kgram.toString() + " not found in map");
		}
		return myMap.get(kgram);
	}
}
