import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.NoSuchElementException;

public class EfficientMarkov extends BaseMarkov {
	Map<String, ArrayList<String>> myMap;
	public EfficientMarkov(int order) {
		super(order);
		myMap = new HashMap<String, ArrayList<String>>();
	}
	public EfficientMarkov() {
		super();
		myMap = new HashMap<String, ArrayList<String>>();
	}

	@Override public void setTraining(String text) {
		myMap.clear();
		myText = text;
		int maxIndex = text.length() - myOrder - 1;
		for(int k = 0; k < maxIndex; k++) {
			String key = text.substring(k, k + myOrder);
			String follow = text.substring(k + myOrder, k + myOrder + 1);
			if(k == maxIndex) {
				follow = PSEUDO_EOS;
			}
			if(! myMap.containsKey(key)){
				ArrayList<String> value = new ArrayList<String>();
				value.add(follow);
				myMap.put(key, value);
			}
			else {
				myMap.get(key).add(follow);
			}
		}
	}
	@Override public ArrayList<String> getFollows(String key) throws NoSuchElementException{
		if(!(myMap.containsKey(key))) {
			throw new NoSuchElementException(key+"not found in map");
		}
		return myMap.get(key);
	}
}