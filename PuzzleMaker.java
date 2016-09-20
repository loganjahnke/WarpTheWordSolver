import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;
import java.io.File;
import java.util.Scanner;
import java.util.Iterator;
 
/**
* Object that represents a puzzle maker
*/
public class PuzzleMaker {

    private String startWord;
    private String goalWord;
    private String[] files;
 	private int overallCounter = 0;
 	private List<String> storageForStarts;
 	private List<String> storageForLengths;
 	private List<String> smallestDistance;
 	private List<String> tempDistance;
 	private List<String> inOrderPrev;

    public PuzzleMaker(String one, String two)
    {
        startWord = one;
        goalWord = two;
        files = new String[]{ "a.txt","b.txt","c.txt","d.txt","e.txt","f.txt","g.txt","h.txt","i.txt","j.txt","k.txt","l.txt","m.txt","n.txt","o.txt","p.txt","q.txt","r.txt","s.txt","t.txt","u.txt","v.txt","w.txt","x.txt","y.txt","z.txt" };
        storageForStarts = new ArrayList<String>();
        storageForLengths = new ArrayList<String>();
        smallestDistance = new ArrayList<String>();
        tempDistance = new ArrayList<String>();
        inOrderPrev = new ArrayList<String>();
        storageForStarts.add(one);
        smallestDistance.add(one);
        tempDistance.add(one);
    }

    ///
    //
    ///
    public void findSmallestDistance(String wordToCompare) {

    	for (int i = 0; i < 26; i++) {
    		File path = new File("Dictionary/"+files[i]);

        	try (Scanner file = new Scanner(path)) {
		
				String wordInFile = file.next();

				while (wordInFile != "") {
					if (getHammingDistance(wordToCompare, wordInFile) == 1) {
						storageForLengths.add(wordInFile);
					}
					if (file.hasNext()) {
						wordInFile = file.next();
					} else {
						break;
					}
				}

	    	} catch (Exception e) { 
	    
	    		System.out.printf("A problem occurred: %s", e); 
	    
	    	}

    	}

    	int tempSmall = 1000;

    	for (int target = 1; target < 5; target++) {
    		for (int i = 0; i < storageForLengths.size(); i++) {
	    		tempSmall = getHammingDistance(storageForLengths.get(i), goalWord);
	    		if (tempSmall == 0) {
					System.out.println("\n\n\n\n\n\n\n\n/////////////////////////////////");
					tempDistance.add(storageForLengths.get(i));
					if (tempDistance.size() < smallestDistance.size() || smallestDistance.size() == 1) {
						smallestDistance.clear();
						smallestDistance = new ArrayList<String>(tempDistance);
						tempDistance.clear();
						tempDistance.add(startWord);
					}
					System.out.println(smallestDistance.toString());
				} else if (tempSmall == target) {
					inOrderPrev.add(storageForLengths.get(i));
					Iterator<String> tI = tempDistance.iterator();
					Iterator<String> sI = inOrderPrev.iterator();
					while (tI.hasNext()) {
					   String tS = tI.next();
					   while (sI.hasNext()) {
					   		String sS = sI.next();
					   		if (tS.equals(sS)) {
					   			sI.remove();
					   		}
					   }
					}
					tempDistance.add(inOrderPrev.get(0));
					System.out.println("Recursively going with " + inOrderPrev.get(0));
					System.out.println(tempDistance.toString());
					inOrderPrev.remove(0);
					storageForLengths.clear();
					findSmallestDistance(storageForLengths.get(0));
				}
	    	}
    	}

    }
 
    ///
    //  Calculating the Hamming Distance for two strings requires the string to be of the same length.
    ///
    public int getHammingDistance(String wordToCompareOne, String wordToCompareTwo)
    {
        if (wordToCompareOne.length() != wordToCompareTwo.length())
        {
            return -1;
        }
 
        int counter = 0;
 
        for (int i = 0; i < wordToCompareOne.length(); i++)
        {
            if (wordToCompareOne.charAt(i) != wordToCompareTwo.charAt(i)) counter++;
        }
 
        return counter;
    }
 
    ///
    //  Hamming distance works best with binary comparisons, this function takes a string arrary of binary
    //  values and returns the minimum distance value
    ///
    public int minDistance(String[] numbers)
    {
        int minDistance = Integer.MAX_VALUE;
 
        if (checkConstraints(numbers))
        {
            for (int i = 1; i < numbers.length; i++)
            {
                int counter = 0;
                for (int j = 1; j <= numbers[i].length(); j++)
                {
                    if (numbers[i-1].charAt(j-1) != numbers[i].charAt(j-1))
                    {
                        counter++;
                    }
                }
 
                if (counter == 0) return counter;
                if (counter < minDistance) minDistance = counter;
            }
        }
        else
        {
            return -1;
        }
 
        return minDistance;
    }
 
    private Boolean checkConstraints(String[] numbers)
    {
        if (numbers.length > 1 && numbers.length <=50)
        {
            int prevLength = -1;
            for (int i = 0; i < numbers.length; i++)
            {
                if (numbers[i].length() > 0 && numbers[i].length() <= 50)
                {
                    if (prevLength == -1)
                    {
                        prevLength = numbers[i].length();
                    }
                    else
                    {
                        if (prevLength != numbers[i].length())
                        {
                            return false;
                        }
                    }
 
                    for (int j = 0; j < numbers[i].length(); j++)
                    {
                        if (numbers[i].charAt(j) != '0' && numbers[i].charAt(j) != '1')
                        {
                            return false;
                        }
                    }
                }
                else
                {
                    return false;
                }
            }
        }
        else
        {
            return false;
        }
 
        return true;
    }

	/**
	* Main method
	*/
    public static void main(String[] args) {

    	if (args.length != 2) {
    		System.exit(0);
    	}
    	PuzzleMaker maker = new PuzzleMaker(args[0], args[1]);
    	maker.findSmallestDistance(args[0]);

    }
}
