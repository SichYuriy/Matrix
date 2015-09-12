package com.gmail.at.sichyuriyy;

public class Matrix {

	private int n, m;
	private int[][] matrix;

	public Matrix(int n, int m) {
		this.n = n;
		this.m = m;
		matrix = new int[n][m];
	}

	public void defineMatrix(int n, int m) {
		matrix = new int[n][m];
		for (int i = 0; i < n; i++) {
			for (int j = 0; j < m; j++)
				matrix[i][j] = j + 1 + i * m;
		}
	}

	public void defineMatrix() {
		matrix = new int[n][m];
		for (int i = 0; i < n; i++) {
			for (int j = 0; j < m; j++)
				matrix[i][j] = j + 1 + i * m;
		}
	}

	public Matrix addMatrix(Matrix x) {
		Matrix result = new Matrix(this.n, this.m);
		for (int i = 0; i < n; i++) {
			for (int j = 0; j < m; j++) {
				result.matrix[i][j] = this.matrix[i][j] + x.matrix[i][j];
			}
		}
		return result;
	}

	public Matrix difMatrix(Matrix x) {
		Matrix result = new Matrix(this.n, this.m);
		for (int i = 0; i < n; i++) {
			for (int j = 0; j < m; j++) {
				result.matrix[i][j] = this.matrix[i][j] - x.matrix[i][j];
			}
		}
		return result;
	}

	public Matrix multMatrix(Matrix x) {
		if (this.m != x.n)
			return null;

		Matrix result = new Matrix(this.n, x.m);

		for (int i = 0; i < this.n; i++) {
			for (int j = 0; j < x.m; j++) {
				result.matrix[i][j] = 0;
				for (int k = 0; k < this.m; k++)
					result.matrix[i][j] += this.matrix[i][k] * x.matrix[k][j];
			}
		}

		return result;
	}

	public void print() {
		for (int i = 0; i < n; i++) {
			for (int j = 0; j < m; j++)
				System.out.print(matrix[i][j] + " ");
			System.out.println();
		}
	}

	public Matrix multShtrassen(Matrix x) {
		if (this.m != x.n)
			return null;

		Matrix result = new Matrix(n, n);

		if (n == 1) {
			result.matrix[0][0] = this.matrix[0][0] * x.matrix[0][0];
			return result;
		}

		Matrix A11, A12, A21, A22, B11, B12, B21, B22, res11, res12, res21, res22;

		A11 = new Matrix(n / 2, n / 2);
		A12 = new Matrix(n / 2, n / 2);
		A21 = new Matrix(n / 2, n / 2);
		A22 = new Matrix(n / 2, n / 2);

		B11 = new Matrix(n / 2, n / 2);
		B12 = new Matrix(n / 2, n / 2);
		B21 = new Matrix(n / 2, n / 2);
		B22 = new Matrix(n / 2, n / 2);

		this.portition(A11, A12, A21, A22);
		x.portition(B11, B12, B21, B22);

		Matrix S1 = B12.difMatrix(B22);
		Matrix S2 = A11.addMatrix(A12);
		Matrix S3 = A21.addMatrix(A22);
		Matrix S4 = B21.difMatrix(B11);
		Matrix S5 = A11.addMatrix(A22);
		Matrix S6 = B11.addMatrix(B22);
		Matrix S7 = A12.difMatrix(A22);
		Matrix S8 = B21.addMatrix(B22);
		Matrix S9 = A11.difMatrix(A21);
		Matrix S10 = B11.addMatrix(B12);

		Matrix P1 = A11.multShtrassen(S1);
		Matrix P2 = S2.multShtrassen(B22);
		Matrix P3 = S3.multShtrassen(B11);
		Matrix P4 = A22.multShtrassen(S4);
		Matrix P5 = S5.multShtrassen(S6);
		Matrix P6 = S7.multShtrassen(S8);
		Matrix P7 = S9.multShtrassen(S10);

		res11 = P5.addMatrix(P4).difMatrix(P2).addMatrix(P6);
		res12 = P1.addMatrix(P2);
		res21 = P3.addMatrix(P4);
		res22 = P5.addMatrix(P1).difMatrix(P3).difMatrix(P7);

		result.union(res11, res12, res21, res22);
		return result;
	}

	public Matrix multRecursive(Matrix x) {
		if (this.m != x.n)
			return null;
		Matrix result = null;
		int size;
		if (this.n >= this.m && this.n >= x.m)
			size = this.n;
		else if (this.m >= this.n && this.m >= x.m)
			size = this.m;
		else
			size = x.m;
		int newSize = 1;
		boolean flag = false;
		while (size != 0) {
			if (size % 2 != 0 && size != 1)
				flag = true;
			size /= 2;
			newSize *= 2;
		}
		if (!flag)
			newSize /= 2;
		if (newSize == this.n && newSize == this.m && newSize == x.m) {
			result = this.multShtrassen(x);
			return result;
		}
		result = new Matrix(this.n, x.m);
		Matrix copyResult = new Matrix(newSize, newSize);
		Matrix copyA = new Matrix(newSize, newSize);
		Matrix copyB = new Matrix(newSize, newSize);

		for (int i = 0; i < this.n; i++) {
			for (int j = 0; j < this.m; j++)
				copyA.matrix[i][j] = this.matrix[i][j];
		}
		for (int i = 0; i < x.n; i++) {
			for (int j = 0; j < x.m; j++)
				copyB.matrix[i][j] = x.matrix[i][j];
		}
		copyResult = copyA.multShtrassen(copyB);
		for (int i = 0; i < this.n; i++)
			for (int j = 0; j < x.m; j++)
				result.matrix[i][j] = copyResult.matrix[i][j];
		return result;
	}

	private void portition(Matrix A11, Matrix A12, Matrix A21, Matrix A22) {

		for (int i = 0; i < n / 2; i++) {
			for (int j = 0; j < n / 2; j++) {
				A11.matrix[i][j] = this.matrix[i][j];
				A12.matrix[i][j] = this.matrix[i][j + n / 2];
				A21.matrix[i][j] = this.matrix[i + n / 2][j];
				A22.matrix[i][j] = this.matrix[i + n / 2][j + n / 2];
			}
		}
	}

	private void union(Matrix A11, Matrix A12, Matrix A21, Matrix A22) {
		for (int i = 0; i < n / 2; i++) {
			for (int j = 0; j < n / 2; j++) {
				this.matrix[i][j] = A11.matrix[i][j];
				this.matrix[i][j + n / 2] = A12.matrix[i][j];
				this.matrix[i + n / 2][j] = A21.matrix[i][j];
				this.matrix[i + n / 2][j + n / 2] = A22.matrix[i][j];
			}
		}
	}
}
