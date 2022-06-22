
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Arrays;
import java.util.Scanner;

public class Main {
    final static Scanner scanner = new Scanner(System.in);
    public static void main(String[] args) {
        int userNum = 0;
        double plus = 0;
        double minus = 0;
        String[] masterList = new String[6];
        //plus minus Food Clothes Entertainment Other
        File file = new File("purchases.txt");

        printStatements();
        userNum = scanner.nextInt();
        while(userNum != 0) {
            switch(userNum) {
                case 1: masterList = choiceOne(masterList); break;
                case 2: masterList = choiceTwo(masterList); break;
                case 3: masterList = choiceThree(masterList); break;
                case 4: choiceFour(masterList[0], masterList[1]); break;
                case 5: saveFile(file, masterList); break;
                case 6: masterList = loadFile(file, masterList); break;
                case 7: analyze(masterList); break;
            }
            printStatements();
            userNum = scanner.nextInt();
        }
        System.out.println("\nBye!");
    }

    private static void analyze(String[] masterList) {
        System.out.println("\n");
        System.out.println("How do you want to sort?");
        System.out.println("1) Sort all purchases");
        System.out.println("2) Sort by type");
        System.out.println("3) Sort certain type");
        System.out.println("4) Back");
        int userNum = scanner.nextInt();
        System.out.println("\n");
        while(userNum != 4) {
            switch(userNum) {
                case 1: analyzeOne(masterList); break;
                case 2: analyzeTwo(masterList); break;
                case 3: analyzeThree(masterList); break;
            }
            System.out.println("\n");
            System.out.println("How do you want to sort?");
            System.out.println("1) Sort all purchases");
            System.out.println("2) Sort by type");
            System.out.println("3) Sort certain type");
            System.out.println("4) Back");
            userNum = scanner.nextInt();
            System.out.println("\n");
        }
    }

    private static void analyzeOne(String[] masterList) {
        int count = 2;
        for(int i = 2; i < masterList.length; i++) {
            if(masterList[i] == null) { count++; }
        }
        if(count == masterList.length) {
            System.out.println("The purchase list is empty");
            System.out.println("\n");
            return;
        }

        String masterString = masterList[2];
        for(int i = 3; i < masterList.length; i++) {
            if(masterList[i] == null || masterList[i].equals("")) { continue; }
            masterString += "@@" + masterList[i]; //Combine all with @@'s
        }

        String[] masterArray = masterString.split("@@");
        Pair[] theArray = new Pair[masterArray.length];
        for(int j = 0; j < masterArray.length; j++) {
            String theSubstring = masterArray[j].substring(masterArray[j].length() - 5);
            if(theSubstring.charAt(0) == '$') { theSubstring = theSubstring.substring(1); }
            double theValue = Double.parseDouble(theSubstring);
            int theIndex = j;
            theArray[j] = new Pair(theValue, theIndex);
        }
        Arrays.sort(theArray);

        String[] finalArray = new String[masterArray.length];
        for(int k = 0; k < masterArray.length; k++) {
            int index = theArray[k].index;
            finalArray[k] = masterArray[index];
        }

        System.out.println("\nAll:");
        double total = 0;
        for(int l = 0; l < finalArray.length; l++) {
            System.out.println(finalArray[l]);
            total += theArray[l].value;
        }
        System.out.printf("Total: $%.2f\n", total);
        return;
    }

    public static class Pair implements Comparable<Pair> {
        public int index;
        public double value;

        public Pair(double value, int index) {
            this.index = index;
            this.value = value;
        }

        @Override
        public int compareTo(Pair other) {
            return -1 * Double.valueOf(this.value).compareTo(other.value);
        }
    }


    private static void analyzeTwo(String[] masterList) {
        double[] eachVal = {0,0,0,0};
        for (int i = 2; i < masterList.length; i++) {
            if(masterList[i] == null || masterList[i].equals("")) { continue; }
            String[] temp = masterList[i].split("@@");
            for (int j = 0; j < temp.length; j++) {
                String theSubstring = temp[j].substring(temp[j].length() - 5);
                if (theSubstring.charAt(0) == '$') {
                    theSubstring = theSubstring.substring(1);
                }
                eachVal[i - 2] += Double.parseDouble(theSubstring);
            }
        }

        String[] words = {"Food - $", "Clothes - $", "Entertainment - $", "Other - $"};
        System.out.println("\nTypes:");
        double total = 0;
        System.out.printf(words[0] + "%.2f\n", eachVal[0]);
        System.out.printf(words[2] + "%.2f\n", eachVal[2]);
        System.out.printf(words[1] + "%.2f\n", eachVal[1]);
        System.out.printf(words[3] + "%.2f\n", eachVal[3]);
        for(int k = 0; k < 4; k++) { total += eachVal[k]; }
        System.out.printf("Total sum: $%.2f", total);
        System.out.println("\n");
    }

    private static void analyzeThree(String[] masterList) {
        System.out.println("\nChoose the type of purchase");
        System.out.println("1) Food");
        System.out.println("2) Clothes");
        System.out.println("3) Entertainment");
        System.out.println("4) Other");
        int userNum = scanner.nextInt();

        if(masterList[userNum + 1] == null || masterList[userNum + 1].equals("")) {
            System.out.println("\nThe purchase list is empty!"); return;
        }

        switch(userNum) {
            case 1:  System.out.println("\nFood:"); break;
            case 2:  System.out.println("\nClothes:"); break;
            case 3:  System.out.println("\nEntertainment:"); break;
            case 4:  System.out.println("\nOther:"); break;
        }

        String[] masterArray = masterList[userNum + 1].split("@@");
        Pair[] theArray = new Pair[masterArray.length];
        for(int j = 0; j < masterArray.length; j++) {
            String theSubstring = masterArray[j].substring(masterArray[j].length() - 5);
            if(theSubstring.charAt(0) == '$') { theSubstring = theSubstring.substring(1); }
            double theValue = Double.parseDouble(theSubstring);
            int theIndex = j;
            theArray[j] = new Pair(theValue, theIndex);
        }
        Arrays.sort(theArray);

        String[] finalArray = new String[masterArray.length];
        for(int k = 0; k < masterArray.length; k++) {
            int index = theArray[k].index;
            finalArray[k] = masterArray[index];
        }

        double total = 0;
        for(int l = 0; l < finalArray.length; l++) {
            System.out.println(finalArray[l]);
            total += theArray[l].value;
        }
        System.out.printf("Total sum: $%.2f\n", total);
        return;
    }

    public static void printStatements() {
        System.out.println("Choose your action");
        System.out.println("1) Add income");
        System.out.println("2) Add purchase");
        System.out.println("3) Show list of purchases");
        System.out.println("4) Balance");
        System.out.println("5) Save");
        System.out.println("6) Load");
        System.out.println("7) Analyze (Sort)");
        System.out.println("0) Exit");
    }

    public static String[] choiceOne(String[] masterList) {
        System.out.println("\n");
        System.out.println("Enter income:");
        double income = scanner.nextDouble();
        double plus = 0;
        if(masterList[0] != null) { plus = Double.parseDouble(masterList[0]); }
        plus += income;
        masterList[0] = Double.toString(plus);
        System.out.println("Income was added!");
        System.out.println("\n");
        return masterList;
    }

    public static String[] choiceTwo(String[] purchase) {
        System.out.println("\n");
        int choiceTwoNum = 0;
        while(choiceTwoNum != 5) {
            choiceTwoNum = choiceTwoPrints();
            System.out.println("\n");
            if(choiceTwoNum == 5) { break; }
            purchase = processTwo(purchase, choiceTwoNum + 1);
        }
        return purchase;
    }

    public static int choiceTwoPrints() {
        System.out.println("Choose the type of purchase");
        System.out.println("1) Food\n2) Clothes\n3) Entertainment\n4) Other\n5) Back");
        return scanner.nextInt();
    }

    public static String[] processTwo(String[] purchase, int num) {
        String processString = new String();
        if(purchase[num] != null) { processString = purchase[num]; }
        System.out.println("Enter purchase name:");
        scanner.nextLine();
        String purchaseName = scanner.nextLine();
        System.out.println("Enter its price:");
        double purchaseCost = scanner.nextDouble();

        purchaseName += " $" + purchaseCost;
        int length = purchaseName.length();
        if(purchaseName.charAt(length - 2) == '.') {
            purchaseName += '0';
        }
        if(processString.equals("")) {
            processString = purchaseName;
        } else {
            processString += "@@" + purchaseName;
        }

        purchase[num] = processString;
        double temp = 0;
        if(purchase[1] != null) {
            temp = Double.parseDouble(purchase[1]);
        }
        temp += purchaseCost;
        purchase[1] = Double.toString(temp);

        System.out.println("Purchase was added!");
        System.out.println("\n");

        return purchase;
    }

    public static String[] choiceThree(String[] purchase) {
        System.out.println("\n");
        int choiceThreeNum = 0;
        float total = 0;
        while(choiceThreeNum != 6) {
            choiceThreeNum = choiceThreePrints();
            System.out.println("\n");
            if(choiceThreeNum == 6 ) { break;
            } else if (choiceThreeNum == 5) {
                processTotal(purchase);
                System.out.println("\n");
            } else {
                processThree(purchase[choiceThreeNum + 1]);
                System.out.println("\n");
            }
        }

        if(choiceThreeNum != 6) {
            processTotal(purchase);
            System.out.println("\n");
        }
        return purchase;
    }

    public static int choiceThreePrints() {
        System.out.println("Choose the type of purchase");
        System.out.println("1) Food\n2) Clothes\n3) Entertainment\n4) Other\n5) All\n6) Back");
        return scanner.nextInt();
    }

    public static void processThree(String purchase) {
        if(purchase == null) {
            System.out.println("The purchase list is empty");
            System.out.println("\n");
            return;
        }

        String[] allText = purchase.split("@@");
        float allNum = 0;
        for(int i = 0; i < allText.length; i++) {
            StringBuilder num = new StringBuilder();
            for(int j = 1; allText[i].charAt(allText[i].length() - j) != '$'; j++) {
                num.insert(0, allText[i].charAt(allText[i].length() - j));
            }
            allNum += Float.parseFloat(num.toString());
            System.out.println(allText[i]);
        }
        System.out.printf("Total: $%2.2f", allNum);
        System.out.println("\n");
    }

    public static void processTotal(String[] purchase) {
        int count = 2;
        for(int i = 2; i < purchase.length; i++) {
            if(purchase[i] == null) { count++; }
        }
        if(count == purchase.length) {
            System.out.println("The purchase list is empty");
            System.out.println("\n");
            return;
        }

        float allNum = 0;
        System.out.println("All:");
        for(int k = 2; k < purchase.length; k++) {
            if(purchase[k] == null) { continue; }
            String cycle = purchase[k];
            String[] allText = cycle.split("@@");
            for(int i = 0; i < allText.length; i++) {
                StringBuilder num = new StringBuilder();
                for(int j = 1; allText[i].charAt(allText[i].length() - j) != '$'; j++) {
                    num.insert(0, allText[i].charAt(allText[i].length() - j));
                }
                allNum += Float.parseFloat(num.toString());
                System.out.println(allText[i]);
            }
        }
        System.out.printf("Total sum: $%2.2f\n", allNum);
    }

    public static void choiceFour(String plus, String minus) {
        System.out.println("\n");
        double add = 0;
        double sub = 0;
        if(plus != null) {
            add = Double.parseDouble(plus);
        }
        if(minus != null) {
            sub = Double.parseDouble(minus);
        }
        double balance = add - sub;
        System.out.printf("Balance: $%.2f", balance);
        System.out.println("\n");
    }

    public static void saveFile(File file, String[] purchase) {
        String combine = new String();
        for(int i = 0; i < purchase.length; i++) {
            combine += purchase[i];
            combine += "--";
        }

        try {
            FileWriter writer = new FileWriter(file);
            writer.write(combine);
            writer.close();
            System.out.println("Purchases were saved!\n");
        } catch (IOException e) {
            System.out.println("Error\n");
        }
    }

    public static String[] loadFile(File file, String[] purchase) {
        try {
            Scanner fileScan = new Scanner(file);
            String[] loadString = fileScan.nextLine().split("--");
            purchase = loadString;
        } catch (FileNotFoundException e) {
            System.out.println("Error\n");
        }
        System.out.println("\nPurchases were loaded!\n");

        return purchase;
    }
}
