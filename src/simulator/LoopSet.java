package simulator;

public class LoopSet {
	
	public Loop[] loops;
	public int aantalElementen;
	public int maxElementen;
	
	public LoopSet(int max){
		maxElementen = max;
		loops = new Loop[max];
		aantalElementen = 0;
		
	}
	
	
	public void push(Loop l){
		System.out.println("push("+l.type+")");
		loops[aantalElementen] = l;
		aantalElementen++;
	}
	
	public Loop pop(){
		System.out.println("pop");
		aantalElementen--;
		return loops[aantalElementen];
	}
	
	public boolean isEmpty(){
		return aantalElementen == 0;
	}
	
	public int get(String naam){
		for(int i=0; i<aantalElementen; i++){
			Loop loop = loops[i];
			if(loop.getVariabele().equals(naam)){
				return loop.i;
			}
			
		}
		return -1;
		
		
	}
	
	
	public void verwijderLoops(int regelBreak){
		System.out.println("verwijderLoops("+regelBreak+") aantalElementen = "+aantalElementen);
		while(!isEmpty()){
			
			Loop loop = pop();
			System.out.println("pop() ==>"+loop.type);
			System.out.println("loop.end("+loop.end+") >=" + "regelBreak("+regelBreak+")");
			if(loop.end >= regelBreak){
				System.out.println("\tpush");
				push(loop);
				return;
			}
			
		}
		
		
		
	}
	
	
	
}
