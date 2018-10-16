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
	@Override public void setTraining(String text) {
		myMap.clear();
		myWords = text.split("\\s+");
		int maxIndex = myWords.length - myOrder;
		for(int k = 0; k < maxIndex; k++) {
			WordGram wkey = new WordGram(myWords, k, myOrder);
			String follows = myWords[k + myOrder];
			ArrayList<String> empty = new ArrayList<String>();
			if(k == maxIndex) {
				follows = PSEUDO_EOS;
			}
			myMap.putIfAbsent(wkey, empty);
			myMap.get(wkey).add(follows);
		}
	}
	@Override public ArrayList<String> getFollows(WordGram kgram) throws NoSuchElementException{
		if(!myMap.containsKey(kgram)) {
			throw new NoSuchElementException("Key " + kgram.toString() + " not found in map");
		}
		return myMap.get(kgram);
	}
}
