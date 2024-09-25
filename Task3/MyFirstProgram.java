
class MyFirstClass {
public static void main(String[] args) {
	MySecondClass o = new MySecondClass(4, 9);
	System.out.println(o.max());
	for (int i = 1; i <= 8; i++) {
		 for (int j = 1; j <= 8; j++) {
			 o.setA(i);
			 o.setB(j);
			 System.out.print(o.max());
			 System.out.print(" ");
		 }
		 System.out.println();
	}
	
 	}
}

class MySecondClass {
	private int a;
	private int b;

	public MySecondClass(){
		a = 0;
		b=0;
	}

	public MySecondClass(int a, int b){
		this.a = a;
		this.b = a;
	};

	public void setA(int a) {
		this.a = a;
	}
	public void setB(int b) {
		this.b = b;
	}	
	public int getA(){
		return a;
	}
	public int getB(){
		return b;
	}
	public int max(){
		return (a > b) ? a : b;
	}
}

