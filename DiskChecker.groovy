//package Checker

import SizeFormatter;


public class DiskChecker {


   public void diskCheck(){
        //File f = new File("c:/");
        //File f = new File("F:/");
        File f = new File(".");
        // prints the volume size in bytes.
        System.out.println("Total Space="+SizeFormatter.format(f.getTotalSpace()));
        // prints the total free bytes for the volume in bytes.
        System.out.println("Free Space="+SizeFormatter.format(f.getFreeSpace()));
        // prints an accurate estimate of the total free (and available) bytes
        // on the volume. This method may return the same result as 'getFreeSpace()' on
        // some platforms.
        
        System.out.println("Usable Space="+Checker.roundMe(f.getUsableSpace()));
    }

    public static void main(String[] args){
	DiskChecker dc = new DiskChecker();
	dc.diskCheck();
	
    }
    }

