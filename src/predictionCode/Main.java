package predictionCode;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.Vector;

import sardinas_patterson.Sardinas;


public class Main {

	private static final int minLenWord = 1;
	private static final int maxLenWord = 7;
	
	private static final int minLenLang = 1;
	private static final int maxLenLang = 5;
	
	private static final int luckNumberFixe = 40;
	private static int isLongeurFixe = 0;
	private static int isPrefixe = 0;
	private static int isUnique = 0;
	private static int isFactorisable = 0;
	
	public static void main(String[] args) {
		Vector<String> csvData = new Vector<String>();
		Vector<String> langage = new Vector<String>();
		
		int minLen ;
		int maxLen ;
		
		int compteurZeros = 0;
        int compteurUns = 0;
        
        String[] array ;
		
		for (int i = 0; i < 100000; i++) {
			langage = generatLangage();
			minLen = langage.get(0).length();
			maxLen = langage.get(0).length();
			array = langage.toArray(new String[0]);
			
			for (String string : langage) {
				if (minLen > string.length()) {
					minLen = string.length();
				}
				if (maxLen < string.length()) {
					maxLen = string.length();
				}
				for (int j = 0; j < string.length(); j++) {
		            char caractere = string.charAt(j);
		            if (caractere == '0') {
		                compteurZeros++;
		            } else if (caractere == '1') {
		                compteurUns++;
		            }
		        }
			}
			
			csvData.add(calculateEntropy(array)+","+isLongeurFixe+","+isPrefixe+","+isUnique+","+isFactorisable+","+minLen+","+maxLen+","+langage.size()+","+compteurUns+","+compteurZeros+","+Sardinas.isCode(langage));
			isLongeurFixe = 0;
			isPrefixe = 0;
			isUnique = 0;
			isFactorisable = 0;
			compteurUns = 0;
			compteurZeros = 0;
		}
		
		writeFile(csvData, "D://Python/predictions.csv");
//		updateFile("predictionAjout.csv", "prediction.csv");
	}
	
	public static double calculateEntropy(String[] data) {
        // Compter la fréquence d'apparition de chaque mot binaire dans le vecteur
        Map<String, Integer> frequencyMap = new HashMap<>();
        int dataSize = data.length;

        for (String word : data) {
            frequencyMap.put(word, frequencyMap.getOrDefault(word, 0) + 1);
        }

        // Calculer l'entropie
        double entropy = 0.0;

        for (Map.Entry<String, Integer> entry : frequencyMap.entrySet()) {
            double probability = (double) entry.getValue() / dataSize;
            entropy -= probability * Math.log(probability);
        }

        return entropy;
    }
	
	public static void updateFile(String inputFile ,String outputFile) {

        try (BufferedReader reader = new BufferedReader(new FileReader(inputFile));
             BufferedWriter writer = new BufferedWriter(new FileWriter(outputFile))) {

            String line;
            while ((line = reader.readLine()) != null) {
                String modifiedLine = "test " + line;
                writer.write(modifiedLine);
                writer.newLine();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
	}
	
	public static void writeFile(Vector<String> data, String filePath) {
        try {
            File file = new File(filePath);
            
            if (!file.exists()) {
                file.createNewFile();
            }

            FileWriter fileWriter = new FileWriter(file, true);
            BufferedWriter bufferedWriter = new BufferedWriter(fileWriter);

            for (String line : data) {
                bufferedWriter.write(line);
                bufferedWriter.newLine();
            }

            bufferedWriter.close();
            fileWriter.close();

            System.out.println("Les données ont été écrites avec succès dans le fichier CSV !");
        } catch (IOException e) {
            System.out.println("Une erreur s'est produite lors de l'écriture dans le fichier CSV : " + e.getMessage());
        }
    }
	
	public static boolean codeFactorisable(Vector<String> language) {
        for (String word : language) {
            if (testFactorisation(word, language)) {
            	isFactorisable = 1;
                return false;
            }
        }
        isFactorisable = 0;
        return true;
    }

    private static boolean testFactorisation(String word, Vector<String> language) {
        List<String> factorizations = getFactorizations(word, language);
        return factorizations.size() > 1;
    }

    private static List<String> getFactorizations(String word, Vector<String> language) {
        List<String> factorizations = new ArrayList<>();
        if (word.isEmpty()) {
            factorizations.add("");
            return factorizations;
        }
        for (int i = 1; i <= word.length(); i++) {
            String prefix = word.substring(0, i);
            if (language.contains(prefix)) {
                String suffix = word.substring(i);
                List<String> suffixFactorizations = getFactorizations(suffix, language);
                for (String sf : suffixFactorizations) {
                    factorizations.add(prefix + sf);
                }
            }
        }
        return factorizations;
    }
	
	private static int codeUnique(Vector<String> langage) {
		for (int i = 0; i < langage.size(); i++) {
			for (int j = i+1; j < langage.size(); j++) {
				if (langage.get(i).equals(langage.get(j))) {
					isUnique = 1;
					return 1;
				}
			}
		}
		isUnique = 0;
		return 0;
	}
	
	private static int codePrefixe(Vector<String> langage) {
		for (String elements : langage) {
			for (String mots : langage) {
				if (elements.startsWith(mots) && elements != mots) {
					isPrefixe = 1;
					return 1;
				}
			}
		}
		isPrefixe = 0;
		return 0;
	}
	
	private static int codeLongeurFixe(Vector<String> langage) {
		if (langage.isEmpty()) {
			System.out.println("empty");
			isLongeurFixe = 0;
			return 0;
        }

        int length = langage.get(0).length();

        for (int i = 1; i < langage.size(); i++) {
            if (langage.get(i).length() != length) {
            	isLongeurFixe = 1;
            	return 1;
            }
        }

        isLongeurFixe = 0;
        return 0;
	}
	
	private static Vector<String> generatLangage() {
		Random random = new Random();
        int number = random.nextInt(maxLenLang) + minLenLang;
        Vector<String> langage = new Vector<String>(); 
        int porcentageLongeurFix = random.nextInt(100) + 1;
//        System.out.println("% : "+porcentageLongeurFix);
        if (porcentageLongeurFix >= 100 - luckNumberFixe) {
        	int taille = random.nextInt(maxLenWord) + minLenWord;
        	for (int i = 0; i < number; i++) {
    			langage.add(generatMot(taille));
    		}
        	
        	isLongeurFixe = 0;
        	isPrefixe = 0;
		}
        else {
        	for (int i = 0; i < number; i++) {
    			langage.add(generatMot());
    		}
        	
        	codeLongeurFixe(langage);
        	codePrefixe(langage);
		}
        
        codeUnique(langage);
        codeFactorisable(langage);
        
        return langage;
	}
	
	private static String generatMot(int number) {
		Random random = new Random();
        String binaryString = "";
        for (int i = 0; i < number; i++) {
			binaryString += random.nextInt(2);
		}
//        System.out.println(binaryString);
        return binaryString;
	}

	public static String generatMot() {
        Random random = new Random();
        int number = random.nextInt(maxLenWord) + minLenWord;
        
        String binaryString = "";
        for (int i = 0; i < number; i++) {
			binaryString += random.nextInt(2);
		}
//        System.out.println(binaryString);
        return binaryString;
    }
	
}
