package com.gmail.at.sichyuriyy;

public class Main {

	public static void main(String[] args) {
		long start = System.currentTimeMillis();
		Matrix A = new Matrix(256, 256);
		Matrix B = new Matrix(256, 256);
		A.defineMatrix();
		B.defineMatrix();
		
		Matrix C = A.multShtrassen(B);
		long end = System.currentTimeMillis();
		//C.print();
		System.out.println(end - start);
		

	}

}
