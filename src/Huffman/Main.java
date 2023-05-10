package Huffman;

import java.io.BufferedReader;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;


public class Main {
	
	public static HashMap<String, Double> getProba() throws Exception {

        HashMap<String, Double> result = new HashMap<String, Double>();
        try {

            int longueurFile = getLongueurFile();

            List<String> list = lireFile();
            Map<Character, Integer> charCounts = new HashMap<>();

            for (String str : list) {
                for (char c : str.toCharArray()) {
                    if (charCounts.containsKey(c)) {
                        charCounts.put(c, charCounts.get(c) + 1);
                    } else {
                        charCounts.put(c, 1);
                    }
                }
            }

            for (Map.Entry<Character, Integer> entry : charCounts.entrySet()) {
                double probaNumber = entry.getValue();
                double proba = probaNumber / longueurFile;
                String format = String.format("%.4f", proba);
                format = format.replace(',', '.');
                proba = Double.parseDouble(format);
                result.put(entry.getKey().toString(), proba);
            }

        } catch (Exception ex) {
            ex.printStackTrace();
            throw ex;
        }

        List<Map.Entry<String, Double>> list = new ArrayList<>(result.entrySet());

        Collections.sort(list, new Comparator<Map.Entry<String, Double>>() {
            public int compare(Map.Entry<String, Double> o1, Map.Entry<String, Double> o2) {
                return o1.getValue().compareTo(o2.getValue()); // sort in ascending order
            }
        });

        LinkedHashMap<String, Double> sortedMap = new LinkedHashMap<>();
        for (Map.Entry<String, Double> entry : list) {
            sortedMap.put(entry.getKey(), entry.getValue());
        }

        result = sortedMap;

        return result;
    }

    public static int getLongueurFile() throws Exception {
        int result = 0;

        List<String> fileContent;
        try {
            fileContent = lireFile();
        } catch (Exception ex) {
            throw ex;
        }

        for (String string : fileContent) {
            result += string.length();
        }

        return result;
    }

    public static List<String> lireFile() throws Exception {
        List<String> result = new ArrayList<String>();

        BufferedReader reader = null;

        try {
            reader = new BufferedReader(new FileReader(
                    "C:/Users/micli/eclipse-workspace/Codage/src/Huffman/data.txt"));
            String line;
            while ((line = reader.readLine()) != null) {
                // System.out.println(line);
                result.add(line);
            }

        } catch (Exception ex) {
            ex.printStackTrace();
            throw ex;
        } finally {
            reader.close();
        }

        return result;
    }

	public static void main(String[] args) throws Exception {
		System.out.println("Proba");
		HashMap<String, Double> source = getProba();
		for(String key : source.keySet()) {
			Double val = source.get(key);
			System.out.println(val +" "+ key);
		}
		HashMap<String, String> huffman = huffman(source);
		int maxSize = egaliseur(huffman);
		System.out.println("Egalise");
		for(String key : huffman.keySet()) {
			String valTmp = huffman.get(key);
			System.out.println(key+"-"+valTmp);
		}
		List<String> text = lireFile();
		List<String> code = codage(huffman,text);
		decodage(huffman,code,maxSize);
	}
	
	private static List<String> decodage(HashMap<String, String> huffman ,List<String> code, int maxSize) {
		List<String> text = new ArrayList<String>();
		System.out.println("Decodage");
		String ligneDecode = "" , caractere = "";
		for (String ligne : code) {
			ligneDecode = "";
			for (int i = 0; i < ligne.length(); i++) {
				caractere += ligne.charAt(i);
				if (caractere.length() == maxSize) {
					for(String key : huffman.keySet()) {
						String valTmp = huffman.get(key);
						if (valTmp.contains(caractere)) {
							ligneDecode += key;
						}
					}
					caractere = "";
				}
			}
			text.add(ligneDecode);
			System.out.println(ligneDecode);
		}
		return text;
	}
	
	private static List<String> codage(HashMap<String, String> huffman , List<String> text) {
		List<String> code = new ArrayList<String>();
		String ligneCode = "";
		System.out.println("Codage");
		for (String ligne : text) {
			ligneCode = "";
			for (int i = 0; i < ligne.length(); i++) {
				for(String key : huffman.keySet()) {
					String valTmp = huffman.get(key);
					if (ligne.charAt(i)==key.charAt(0)) {
						ligneCode += valTmp;
					}
				}
			}
			System.out.println(ligneCode);
			code.add(ligneCode);
		}
		return code;
	}
	
	public static int egaliseur(HashMap<String, String> source) {
		int maxSize = 0;
		for(String key : source.keySet()) {
			String val = source.get(key);
			if (maxSize < val.length()) {
				maxSize = val.length();
			}
		}
		for(String key : source.keySet()) {
			String val = source.get(key);
			if (val.length() < maxSize) {
				String valTmp = val;
				for (int i = 0; i < maxSize - val.length(); i++) {
					valTmp += "0";
					source.put(key,valTmp);
				}
			}
		}
		return maxSize;
	}
	
	public static HashMap<String, String> huffman(HashMap<String, Double> source) {
		HashMap<String, String> huffman = new HashMap<>();
		for(String key : source.keySet()) {
//			huffman.put(key, (byte) 0b00000000);
			huffman.put(key, "");
		}
		source = sort(source);
		
		Boolean end = false;
		while(end == false) {
			source = sort(source);
			fusion(source , huffman);
			if (source.size() == 1) end = true;
		}
		
		System.out.println("Huffman");
		
		for(String key : huffman.keySet()) {
			String valTmp = huffman.get(key);
			System.out.println(key+"-"+valTmp);
		}
		return huffman;
	}
	
	public static void fusion(HashMap<String, Double> source, HashMap<String, String> huffman ) {
		String keyMin1 = "" , keyMin2 = "";
		Double valMin1 = 2.0 , valMin2 = 2.0;
		Double last = 2.0 , secondlast = 2.0; 
		String keylast = "" , secondKeylast = "";
		
		for(String key : source.keySet()) {
			Double valTmp = source.get(key);
			if (valMin1 > valTmp) {
				valMin2 = valMin1;
				keyMin2 = keyMin1;
				valMin1 = valTmp;
				keyMin1 = key;
			}
			else if (valMin2 > valTmp) {
				valMin2 = valTmp;
				keyMin2 = key;
			}
			secondKeylast = keylast;
			keylast = key;
			secondlast = last;
			last = valTmp;
		}
		
		source.remove(keyMin2);
		source.remove(keyMin1);
		source.put(keyMin1+keyMin2, valMin1+valMin2);
		
		
		for(String key : huffman.keySet()) {
			String valTmp = huffman.get(key);
			if (keyMin1.contains(key)) {
				huffman.put(key, "1"+valTmp);
			}
			if (keyMin2.contains(key)) {
				huffman.put(key, "0"+valTmp);
			}
		}
//		System.out.println("/////////////");
//		for(String key : huffman.keySet()) {
//			String valTmp = huffman.get(key);
//			System.out.println(key+"-"+valTmp);
//		}
//		System.out.println("source");
//		for(String key : source.keySet()) {
//			Double val = source.get(key);
//			System.out.println(val +" "+ key);
//		}
//		System.out.println("/////////////");
	}
	
	@SuppressWarnings("unchecked")
	private static HashMap sort(HashMap map) {
       List linkedlist = new LinkedList(map.entrySet());
       Collections.sort(linkedlist, new Comparator() {
            public int compare(Object o1, Object o2) {
               return ((Comparable) ((Map.Entry) (o2)).getValue())
                  .compareTo(((Map.Entry) (o1)).getValue());
            }
       });
       HashMap sortedHashMap = new LinkedHashMap();
       for (Iterator it = linkedlist.iterator(); it.hasNext();) {
              Map.Entry entry = (Map.Entry) it.next();
              sortedHashMap.put(entry.getKey(), entry.getValue());
       }
       return sortedHashMap;
	}
	
}
