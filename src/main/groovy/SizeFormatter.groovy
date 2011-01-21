//package Checker;


public class SizeFormatter{

    public static String format(long bytesNumber){

        def unites=["b","K","M","G","T"]
        def iter=0;
        while (bytesNumber >= 1024){
            bytesNumber= Math.round(bytesNumber/1024)
            iter++;
        }
        return bytesNumber.toString() + unites[iter];
    }
}

