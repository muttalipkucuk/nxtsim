package simulator;

public class Code {
	
	private String[] code;
	private int aantalRegels;
	
	public Code(int max){
		aantalRegels = 0;
		code = new String[max];
	}
	
	public void add(String line){
		code[aantalRegels] = line;
		aantalRegels++;
	}
	
	public String get(int index){
		return code[index];
	}
	
	public int getSize(){
		return aantalRegels;
	}
	
	public void print(){
		for(int i=0; i<aantalRegels; i++){
			System.out.println((i+1) + "\t" + code[i]);
		}
	}
	
	public int numberOfSpace(int index){
		//System.out.println("#  numberOfSpace("+index+")");
		if(code[index].length()==0){
			return -1;
		}
		
		int space = 0;
		int i = 0;
		
		//System.out.println("   space = "+space+"\ti = "+i);
		
		while(i<code[index].length() && (code[index].charAt(i) == ' ' || code[index].charAt(i) == '\t')){
			//System.out.println("    code["+index+"].charAt("+i+") =" + code[index].charAt(i));
			
			if(code[index].charAt(i) == ' '){
				space++;
			}else{
				space+=4;
			}
			i++;
			//System.out.println("    space = "+space+"\ti = "+i);
		}
		return space;
	}
	
	
	public int regelEnd(int beginIndex, int aantalSpaties){
		//System.out.println("# regelEnd("+beginIndex+","+aantalSpaties+")");
		
		int i = beginIndex+1;
		
		while(i < aantalRegels){
			//System.out.println("  "+i+"<"+aantalRegels+"\tgeeft true");
			if(numberOfSpace(i) == aantalSpaties){
				return i;
			}else{
				i++;
			}
		}
		//System.out.println(" i = "+i);
		return -1;
	}
	
	
	public String toString(){
		String temp = "";
		
		for(int i=0; i<aantalRegels; i++){
			String whitespaces = "";
			//1 whitespaces
			if(i<10){
				whitespaces = " ";
			}
			temp += whitespaces+i+" "+code[i] + "\n";
		}
		
		if (temp.length() > 0){
			temp = temp.substring(0, temp.length()-1);
		}
		
		return temp;
	}
	
	
	public boolean isSpace(int i){
		String temp = String.copyValueOf(code[i].toCharArray());
		
		return (temp.trim().length() == 0 || code[i].length() == 0);
	}
	
	public int regelNummerEnd(int beginIndex){
		
		int aantalSpaties = numberOfSpace(beginIndex);
		
		int i = beginIndex;
		
		while(i < aantalRegels){
			if(numberOfSpace(i) == aantalSpaties && code[i].contains("end")){
				return i;
			}else{
				i++;
			}
		}
		return -1;
	}
	
	
	public int doBreak(int i){
		
		int teller = i;
		
		while(teller>=0){
			if(code[teller].contains("while") || code[teller].contains("for")){
				break;
			}
			teller--;
		}
		
		return regelNummerEnd(teller)+1;
	}
	
}
