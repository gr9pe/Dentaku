package dentaku;

import java.util.ArrayList;
import java.util.List;

public class Main {

	public static void main(String[]args) {

		System.out.println("[電卓]>");

		for(;;) {
			String input = new java.util.Scanner(System.in).nextLine();
			System.out.print(input);

			//入力を切り分けリスト化
			List<String>siki = new ArrayList<>();
			siki = parse(input);

			//平方根計算は複数演算未対応
			if(siki.get(0).equals("r")){
				System.out.println
				("=" + Math.sqrt(Double.parseDouble(siki.get(1))));
				continue;
			}

			//演算子の数だけ演算を繰り返す
			double total = 0;
			int opsize = siki.size()/2;
			for(int i=0;i<opsize;i++) {
				total = calc(siki);

				//計算し終わったところの置換
				int op_i = opRank(siki);
				siki.set(op_i-1, Double.toString(total));
				siki.subList(op_i,op_i+2).clear();
			}
			System.out.println("=" + total);
		}
	}

	//数値と演算子を分けたlistを返す
	//(例){5,+,-3,*,0.2}
	public static List<String> parse(String input){

		if(input.equals("")) {
			throw new IllegalArgumentException();
		}

		List<String>siki = new ArrayList<>();
		int i = 0;

		while(i<input.length()) {

			StringBuilder sb = new StringBuilder();
			while(i<input.length() &&
					(Character.isDigit(input.charAt(i))||
							input.charAt(i)=='.'||
								input.charAt(i)=='-'&&sb.length()==0)) {

				sb.append(input.charAt(i));
				i++;
			}

			if(!sb.toString().equals("")) {
			siki.add(sb.toString());
			}

			if(i<input.length()) {
				siki.add(String.valueOf(input.charAt(i)));
			}
			i++;
		}
		return siki;
	}

	//右辺と左辺の計算
	public static double calc(List<String>siki) {

		//演算子
		int opIndex = opRank(siki);
		//被演算子a,b
		double a = Double.parseDouble(siki.get(opIndex-1));
		double b = Double.parseDouble(siki.get(opIndex+1));

		switch(siki.get(opIndex)) {
		case "*" :
			return a * b;
		case "/" :
			return a / b;
		case "%" :
			return a % b;
		case "^" :
			return Math.pow(a,b);
		case "+" :
			return a + b;
		case "-" :
			return a - b;
		default :
			throw new IllegalArgumentException();
		}
	}

	//演算子の順位を反映したインデックスを返す
	public static int opRank(List<String>siki) {

		if(siki.contains("^")) {
			return siki.indexOf("^");
		}else if(siki.contains("*")||siki.contains("/")||siki.contains("%")){
			for(int i=0;i<siki.size();i++) {
				if (siki.get(i).matches("[\\*\\/\\%]")){
					  return i;
				}
			}
		}else if(siki.contains("+")||siki.contains("-")||siki.contains("r")) {
			for(int i=0;i<siki.size();i++) {
				if (siki.get(i).matches("[\\+\\-r]")){
					  return i;
				}
			}
		}
		throw new IllegalArgumentException();
	}
}

