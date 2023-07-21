import java.io.*;
import java.util.Scanner;

/** @author Ozan Ege Çalışır
 *  @date   21.12.2022
 */
public class StoreUsingFiles_20220808003 {

    public static void main(String[] args)throws Exception{
        String arg =args[0];
        String info = arg + "_ProductInfo.txt";
        String order = arg + "_Order.txt";
        String infoAfter = arg + "_ProductInfoAfterOrder.txt";
        String log = arg + "_log";
        String receipt = arg + "_Receipt.txt";


       int productAmount = countProducts(info);
       String[] itemID = new String[productAmount];
       String[] itemName = new String[productAmount];
       int[] quantity = new int[productAmount];
       double[] price = new double[productAmount];
        String[] itemIDOrder = new String[countProducts(order)];
        int[] quantityOrder = new int[countProducts(order)];
        int[] sum = new int[countProducts(order)];
       getProductInfo(itemID, itemName, quantity, price, info);
       File orderFile = new File(order);
       Scanner orderScanner = new Scanner(orderFile);



        PrintWriter Receipt = new PrintWriter(receipt);
        PrintWriter logs = new PrintWriter(log);

        int j = 0;
        int i = 0;
        while (orderScanner.hasNext()) {
                itemIDOrder[j] = orderScanner.next();
                quantityOrder[j] = Integer.parseInt(orderScanner.next());
                j++;
        }
        Scanner orderScanner2 = new Scanner(orderFile);
        while(orderScanner2.hasNext() && i < quantity.length) {
            for (int l = 0 ; l < quantityOrder.length ; l++) {
                for (int h = 0; h < quantity.length; h++) {
                    if (quantityOrder[l] > quantity[h] && itemIDOrder[l].equals(itemID[h])) {
                        logs.println("ERROR: " + itemName[h] + " - " + quantityOrder[l] + " requested but only "
                                + quantity[h] + " remaining.");
                    }if (quantityOrder[l] < 0 && itemIDOrder[l].equals(itemID[h])) {
                        logs.println("ERROR: Invalid amount requested (" + quantityOrder[l] + ")");
                    }if (!itemIDOrder[l].equals(itemID[h])) {
                        logs.println("ERROR: Product " + itemIDOrder[l] + " not found.");
                    }else {
                        i++;
                        if (itemIDOrder[l].equals(itemID[h]) && quantity[h] > quantityOrder[l] && quantityOrder[l]>=0){
                            quantity[h] = quantity[h] - quantityOrder[l];
                        }else {
                            quantityOrder[l] = 0;
                        }
                        sum[l] = quantityOrder[l];
                    }
                }
            }
           }
    double sumTotal = (sum[0] * price[0]) + (sum[1] * price[1]) + (sum[2] * price[2])
            + (sum[3] * price[3]);
        if (sumTotal > 0) {
            Receipt.println("****** Customer Receipt ******");
            for (int k = 0; k < sum.length; k++) {
                if (sum[k] != 0)
                    Receipt.println(itemName[k] + " - " + sum[k] + " * " + price[k] + " = " + sum[k] * price[k]);
            }
            Receipt.println("---------------------------");
            Receipt.println("Total due - " + sumTotal);
            logs.close();
            Receipt.close();
        }
        writeProductInfo(itemID,itemName,quantity,price,info);
    }



    public static int countProducts(String filename) throws Exception{
        int lAmount = 0;
        File file = new File(filename);
        Scanner input = new Scanner(file);
        while(input.hasNextLine()){
            input.nextLine();
            lAmount += 1;
        }
        return lAmount;
    }

    public static void getProductInfo(String[] itemID, String[] itemName, int[] quantity,
                                      double[] price, String filename) throws Exception {

        Scanner sc = new Scanner(new File(filename));
        int i = 0;
        while (sc.hasNext()) {
            itemID[i] = sc.next();
            itemName[i] = sc.next();
            quantity[i] = Integer.parseInt(sc.next());
            price[i] = Double.parseDouble(sc.next());
            i++;
        }
    }

    public static void writeProductInfo(String[] itemID, String[] itemName, int[] quantity,
                                        double[] price, String filename) throws Exception{

        Scanner sc = new Scanner(new File(filename));
        PrintWriter output = new PrintWriter("Assignment04_ProductInfoAfterOrder.txt");
      int i = 0;
      while (sc.hasNext() && i < countProducts(filename)) {
            output.print(itemID[i] + " " + itemName[i] + " " + quantity[i] + " " + price[i] + "\n");
          i++;
      }
        output.close();
    }
}