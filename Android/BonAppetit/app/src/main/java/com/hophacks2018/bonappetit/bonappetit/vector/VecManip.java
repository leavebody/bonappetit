package com.hophacks2018.bonappetit.bonappetit.vector;


import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

// PUT IN SINGLETON CLASS. EXPENSIVE.
public class VecManip {
    public static double[][] mat1;
    public static double[][] mat2;
    public static double[][] mat3;
    public static double[][] mat4;

    private static VecManip instance;

    public static VecManip getInstance() {
        if (instance!=null){
            return instance;
        } else {
            return new VecManip();
        }
    }

    private VecManip() {
        // read in L1, L2, L3, L4 tests
        mat1 = new double[2][894];
        mat2 = new double[4][894];
        mat3 = new double[4][123];
        mat4 = new double[5][23];
        try {
            BufferedReader in = new BufferedReader(new FileReader("./L1.txt"));
            String str;
            str = in.readLine();
            int i = 0;
            while ((str = in.readLine()) != null) {
                String[] ar = str.split(",");
                int j = 0;
                for (String a : ar) {
                    double k = Double.parseDouble(a);
                    mat1[i][j] = Double.parseDouble(a);
                    j++;
                }
                i++;
            }
            in.close();
        } catch (IOException e) {
            System.out.println("File Read Error");
        }

        // read in L1, L2, L3, L4 tests
        try {
            BufferedReader in = new BufferedReader(new FileReader("./L2.txt"));
            String str;
            str = in.readLine();
            int i = 0;
            while ((str = in.readLine()) != null) {
                String[] ar = str.split(",");
                int j = 0;
                for (String a : ar) {
                    mat2[i][j] = Double.parseDouble(a);
                    j++;
                }
                i++;
            }
            in.close();
        } catch (IOException e) {
            System.out.println("File Read Error");
        }

        // read in L1, L2, L3, L4 tests
        try {
            BufferedReader in = new BufferedReader(new FileReader("./L3.txt"));
            String str;
            str = in.readLine();
            int i = 0;
            while ((str = in.readLine()) != null) {
                String[] ar = str.split(",");
                int j = 0;
                for (String a : ar) {
                    mat3[i][j] = Double.parseDouble(a);
                    j++;
                }
                i++;
            }
            in.close();
        } catch (IOException e) {
            System.out.println("File Read Error");
        }

        // read in L1, L2, L3, L4 tests
        try {
            BufferedReader in = new BufferedReader(new FileReader("./L4.txt"));
            String str;
            str = in.readLine();
            int i = 0;
            while ((str = in.readLine()) != null) {
                String[] ar = str.split(",");
                int j = 0;
                for (String a : ar) {
                    mat4[i][j] = Double.parseDouble(a);
                    j++;
                }
                i++;
            }
            in.close();
        } catch (IOException e) {
            System.out.println("File Read Error");
        }

        for (int i = 0; i < mat4.length; i++){
            double[] row = mat4[i];
            for (int j = 0; j < row.length ; j++ ){
                System.out.print(row[j]);
                System.out.print(" ");
            }
            System.out.print("\n");
            System.out.println(row.length);
        }

    }

    public double[][] getMat1(){
        return mat1;
    }

    public double[][] getMat2(){
        return mat2;
    }

    public double[][] getMat3(){
        return mat3;
    }

    public double[][] getMat4(){
        return mat4;
    }

    public double[] compute(ArrayList<double[]> aa) {

        double[] aa1 = Matrix.multiply(mat1, aa.get(0));
        double[] aa2 = Matrix.multiply(mat2, aa.get(1));
        double[] aa3 = Matrix.multiply(mat3, aa.get(2));
        double[] aa4 = Matrix.multiply(mat4, aa.get(3));


//        int totalLen  = aa1.length + aa2.length + aa3.length + aa4.length;
        double[] result = new double[16];
//        for (int i = 0; i < aa1.length; i++){
//            result[i] = aa1[i];
//        }
//        int ttl = aa1.length + aa2.length;
//        for (int j = aa1.length; j < ttl; j++){
//            result[j] = aa2[j-aa1.length];
//        }
//        int ttl1 = ttl + aa3.length;
//        for (int j = ttl; j < ttl1; j++){
//            result[j] = aa3[j-ttl];
//        }
//        for (int j = ttl1; j < ttl1+aa4.length; j++){
//            result[j] = aa4[j-ttl1];
//        }
        return result;
    }

    static public void main(String[] args) {
        VecManip a = new VecManip();
        ArrayList<double[]> pp = new ArrayList<double[]>();
        System.out.println(a.compute(pp));
    }
}
