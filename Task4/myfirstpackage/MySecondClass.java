package myfirstpackage;
	public class MySecondClass { //static - чтобы не создавать экземпляр внешнего класса
	private int a;
	private int b;

	public MySecondClass(){
		a = 0;
		b=0;
	}

	public MySecondClass(int a, int b){
		this.a = a;
		this.b = a;
	}

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
