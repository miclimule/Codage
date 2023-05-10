package filtreBloom;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;

public class Hash {

	private static final int BASE = 31;
    private static final float MOD = 100000000;
    private static final float ELM = 10000;
    
    private static final byte mask1 = 0b00000001; 
    private static final byte mask2 = 0b00000010;
    private static final byte mask3 = 0b00000100;
    private static final byte mask4 = 0b00001000;
    private static final byte mask5 = 0b00010000;
    private static final byte mask6 = 0b00100000;
    private static final byte mask7 = 0b01000000;
    private static final byte mask8 = (byte) 0b10000000;

    // non-cryptographiques
    // un = (un−1 ∗ w + v)% MOD
    public static int noncryptographiques(String s) {
        int hashValue = 0;
        for (int i = 0; i < s.length(); i++) {
            hashValue = (int) (((long) hashValue * BASE + s.charAt(i)) % MOD);
        }
        return hashValue;
    }
    
    // cryptographiques
    public static String cryptographiques(String s) {
        try {
            MessageDigest md = MessageDigest.getInstance("SHA-256");
            byte[] hashBytes = md.digest(s.getBytes());
            StringBuilder sb = new StringBuilder();
            for (byte b : hashBytes) {
                sb.append(String.format("%02x", b));
            }
            return sb.toString();
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException(e);
        }
    }
    
    // With key
    public static byte[] cryptographiquesACle(String message, String key) {
        try {
            Mac mac = Mac.getInstance("HmacSHA256");
            SecretKeySpec keySpec = new SecretKeySpec(key.getBytes(), "HmacSHA256");
            mac.init(keySpec);
            return mac.doFinal(message.getBytes());
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
    
    private static String bytesToHex(byte[] bytes) {
        StringBuilder sb = new StringBuilder();
        for (byte b : bytes) {
            sb.append(String.format("%02x", b));
        }
        return sb.toString();
    }
    
    // x % MOD
    private static int hash12(String s) {
    	int hashValue = 0;
        for (int i = 0; i < s.length(); i++) {
        	char c = s.charAt(i);
            hashValue += (int) (((long) c) % MOD);
        }
        return hashValue%((int)MOD);
    }
    
    public static int hash1(String key) {
        int FNV_OFFSET_BASIS = 0x811c9dc5;
        int FNV_PRIME = 0x01000193;
        int hash = FNV_OFFSET_BASIS;
        for (int i = 0; i < key.length(); i++) {
            hash = hash * FNV_PRIME;
            hash = hash ^ key.charAt(i);
        }
        if (hash<0) {
			hash = -hash;
		}
        return hash%((int)MOD);
    }

    public static int jenkinsHash(String key) {
        int hash = 0;
        for (int i = 0; i < key.length(); i++) {
            hash += key.charAt(i);
            hash += (hash << 10);
            hash ^= (hash >>> 6);
        }
        hash += (hash << 3);
        hash ^= (hash >>> 11);
        hash += (hash << 15);
        if (hash<0) {
			hash = -hash;
		}
        return hash%((int)MOD);
    }

    public static void main(String[] args) throws IOException {

    	String[] pwd = getFile("C:\\Users\\micli\\eclipse-workspace\\Codage\\sha256.txt");
//    	
    	List<String> pwdHash = new ArrayList<String>();
//    	for (int i = 0 ; i< ELM ; i++) {
//			pwdHash.add(cryptographiques("root"+i));
//		}
    	pwdHash.addAll(Arrays.asList(pwd));
    	
    	byte[] bloom = bloomfilter(pwdHash, MOD);
    	
    	System.out.println("Filtre de bloom");
//    	for (byte b : bloom) {
//    		System.out.print(String.format("%8s",Integer.toBinaryString(b & 0xFF)).replace(' ', '0'));
//		}
    	System.out.println();
//    	String query = "12345";
//    	System.out.println("Valeur de test : " + query);
    	System.out.println("La proba d'un faux positif est de : " + proba(MOD,ELM, 3));
    	System.out.println("Etude : " + etude(ELM, 0.02f));
//    	boolean val = testQuery(cryptographiques(query), bloom);
//    	if (val) {
//    		Set<String> set = new HashSet<>(pwdHash);
//    		if (set.contains(cryptographiques(query))) {
//    		    System.out.println("Le mot est présent dans le tableau");
//    		} else {
//    		    System.out.println("Le mot n'est pas présent dans le tableau");
//    		}
//		}
    	String[] txt = getFile("C:\\Users\\micli\\eclipse-workspace\\Codage\\alea.txt");
    	etudeFile(bloom,txt,pwdHash);
    	
//    	setFile("alea", pwdHash , ".txt");
    }
    
    public static double etude(float nbrElement,float taux) {
    	return - ((nbrElement*Math.log(taux))/(Math.pow(Math.log(2), 2)));
    }
    
    private static void etudeFile(byte[] bloom , String[] txt , List<String> pwdHash) {
    	int vrai = 0;
    	int pos = 0;
//    	Set<String> set = new HashSet<>(pwdHash);
//    	System.out.println(txt.length);
    	for (int i = 0; i < txt.length; i++) {
    		try {
    			String query = txt[i];
            	boolean val = testQuery(query, bloom);
            	if (val) {
            		pos++;
            		Set<String> set = new HashSet<>(pwdHash);
            		if (set.contains(query)) {
            			
//            		    System.out.println("Le mot est présent dans le tableau");
            		} else {
            			vrai++;
//            		    System.out.println("Le mot n'est pas présent dans le tableau");
            		}
        		}
			} catch (Exception e) {
				break;
			}
    		
		}
    	System.out.println("nbr positit : " + pos);
    	System.out.println("nbr faux positif : " + vrai);
    }
    
    private static void setFile(String fileName, List<String> list , String ext) throws IOException {
    	FileWriter fw = new FileWriter(fileName+ext);
        BufferedWriter bw = new BufferedWriter(fw);
        PrintWriter pw = new PrintWriter(bw);

        for (String item : list) {
            pw.println(item);
        }

        pw.close();
        bw.close();
        fw.close();
    }
    
    private static String[] getFile(String road) {
        int maxTest = (int) ELM;
        String[] result = new String[maxTest];

        File file = new File(
                road);

        BufferedReader bReader = null;
        try {
            bReader = new BufferedReader(new FileReader(file));
            String line = "";
            int index = 0;

            while ((line = bReader.readLine()) != null && (index < maxTest)) {
                // System.out.println(line);
                result[index] = line;
                index++;
            }

            bReader.close();
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

        return result;
    }
    
    private static double proba(float tailleBloom , float nbrElement , float nbrHash) {
    	return Math.pow(1 - Math.exp(-nbrHash * nbrElement / tailleBloom), nbrHash);
    }
    
    private static boolean testQuery(String query , byte[] bloom) {
    	int pos1 , pos2 , pos3;
    	pos1 = jenkinsHash(query);
		pos2 = hash1(query);
		pos3 = noncryptographiques(query);
		
		boolean bool1 ,bool2 ,bool3;
		bool1 = searchInBloom(bloom, pos1);
		bool2 = searchInBloom(bloom, pos2);
		bool3 = searchInBloom(bloom, pos3);
		
		if (bool3 && bool2 && bool1) {
//			System.out.println("faux positif");
			return true;
		}
//		System.out.println("negatif");
		return false;
	}
    
    private static boolean searchInBloom(byte[] byte1 , int pos) {
    	int tab1 = (int) Math.floor(pos/8);
    	switch (pos%8) {
			case 0:
				return bitCompare(byte1[tab1], mask1);
			case 1:
				return bitCompare(byte1[tab1], mask2);
			case 2:
				return bitCompare(byte1[tab1], mask3);
			case 3:
				return bitCompare(byte1[tab1], mask4);
			case 4:
				return bitCompare(byte1[tab1], mask5);
			case 5:
				return bitCompare(byte1[tab1], mask6);
			case 6:
				return bitCompare(byte1[tab1], mask7);
			case 7:
				return bitCompare(byte1[tab1], mask8);
			default:
				break;
    	}
		return false;
	}
    
    private static boolean bitCompare(byte byte1 , byte mask) {
    	if ((byte1 & mask) == mask) {
    	    return true;
    	} 
    	return false;
	}
    
    private static byte[] bloomfilter(List<String> pwdHash , float size) {
    	byte[] byte1 = new byte[(int) Math.ceil(size/8)];
    	
    	int pos1 , pos2 , pos3;
    	
    	for (String hash : pwdHash) {
			 pos1 = jenkinsHash(hash);
			 pos2 = hash12(hash);
			 pos3 = noncryptographiques(hash);
			 pos2 = hash1(hash);
			 
			 insertionInBloom(byte1,pos1);
			 insertionInBloom(byte1,pos2);
			 insertionInBloom(byte1,pos3);
		}

    	return byte1;
	}
    
    private static void insertionInBloom(byte[] byte1 , int pos) {
    	
    	int tab1 = (int) Math.floor(pos/8);
    	
    	switch (pos%8) {
			case 0:
				byte1[tab1] |= mask1;
				break;
			case 1:
				byte1[tab1] |= mask2;
				break;
			case 2:
				byte1[tab1] |= mask3;
				break;
			case 3:
				byte1[tab1] |= mask4;
				break;
			case 4:
				byte1[tab1] |= mask5;
				break;
			case 5:
				byte1[tab1] |= mask6;
				break;
			case 6:
				byte1[tab1] |= mask7;
				break;
			case 7:
				byte1[tab1] |= mask8;
				break;
			default:
				break;
		}
	}
	
//	Byte byte1 = 0b0;
//	for (int i = 1; i < 8; i++) {
//	    byte1 = (byte) (byte1 << 1);
//	}
//	System.out.println(byte1.SIZE);
	
	
//	byte b = 0b00110011; // byte à décaler
//	int shift = 5; // nombre de positions à décaler
//
//	byte resultLeft = (byte) (b << shift); // décalage à gauche
//	byte resultRight = (byte) (b >> shift); // décalage à droite
//
//	System.out.println("Byte initial : " + Integer.toBinaryString(b));
//	System.out.println("Résultat du décalage à gauche : " + String.format("%8s", Integer.toBinaryString(resultLeft)).replace(' ', '0'));
//	System.out.println("Résultat du décalage à droite : " + String.format("%8s", Integer.toBinaryString(resultRight)).replace(' ', '0'));
//

//	byte b = 0b01011001; // byte d'origine
//	byte mask1 = 0b00000010; // masque pour le deuxième bit
//	byte mask2 = 0b00001000; // masque pour le quatrième bit
//	System.out.println(Integer.toBinaryString(b));
//	// Changer le deuxième bit en 1
//	b |= mask1;
//
//	// Changer le quatrième bit en 0
//	b &= ~mask2;
//
//	System.out.println(Integer.toBinaryString(b));
}
