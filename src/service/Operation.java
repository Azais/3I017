package service;

public class Operation {
	public int calcul(int a, int b, String operation){
		if(operation.equals("add")){
			return a+b;
		}
		else if(operation.equals("mul")){
			return a*b;
		}else if (operation.equals("div")){
			return a/b;
		}else return 0;
	}

}
