package Number;

public class ConvertionBase {

	public String changeBase(String number, int base_a, int base_b) {
	    int decimal_number = Integer.parseInt(number, base_a);
	    String digits = "0123456789ABCDEFGHIJKLMNOPQRSTUVWXYZ";
	    if (decimal_number == 0) {
	        return "0";
	    }
	    String new_number = "";
	    if (base_b == 1) {
			for (int i = 0; i < decimal_number; i++) {
				new_number += "1";
			}
			return new_number;
		}
	    while (decimal_number > 0) {
	        int remainder = decimal_number % base_b;
	        new_number = digits.charAt(remainder) + new_number;
	        decimal_number /= base_b;
	    }
	    return new_number;
	}
}
