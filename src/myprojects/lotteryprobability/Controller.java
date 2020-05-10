package myprojects.lotteryprobability;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.text.DecimalFormat;
import java.util.*;
import java.io.File;
import java.util.stream.Collectors;

public class Controller {
    /*
      Combinations formula needed to calculate the possible combinations of 6 numbers out of 53 for Florida Lotto. 69!/6!63! = 119,877,472
      Analyzes:
      - Amount of even vs odd numbers in total and in each set.
      - Most repeated and least repeated number.
      - Are numbers in sequence within a set? How many sets have numbers in sequence? What is the least amount of pairs in sequence and the greatest.
    */

    String urlLottery = "http://www.flalottery.com/exptkt/l6.htm";
    List<String> tableDataText = new ArrayList<>();
    ArrayList<ArrayList<Integer>> sixNumberSetList = new ArrayList<>();
    int numbersFoundCounter = 0;
    int numbersInOnePlay = 6;
    int arrayRow = 0;
    int arrayCol = 0;
    DecimalFormat numberFormat = new DecimalFormat("#.0000");
    File file = new File("C:\\Users\\estef\\OneDrive\\Desktop\\lotteryResults.txt");

    // Grabs information from lottery website
    public void obtainRecords() {
        try {
            // maxBodySize(0) allows files of unlimited size.
            Document doc = Jsoup.connect(urlLottery).maxBodySize(0).get();
            Elements tableData = doc.select("td");
            tableDataText = tableData.eachText();
            for (String e: tableDataText) {
                System.out.println(e);
            }
        } catch(IOException e) {
            e.printStackTrace();
        }
    } // End obtainRecords()

    // Checks whether each element of tableDataText is a number and creates a file with the results
    public void findNumbers() {
        sixNumberSetList.add(new ArrayList<>());
        for(int i = 0; i < tableDataText.size(); ++i) {
            int elementNumber = i + 1;

            if(tableDataText.get(i) == null) {
                System.out.println("Element # " + elementNumber + " is empty");
            }
            try {
                int number = Integer.parseInt(tableDataText.get(i));
                if(numbersFoundCounter % 6 == 0 && numbersFoundCounter != 0) {
                    sixNumberSetList.add(new ArrayList<>());
                    ++arrayRow;
                }
//                System.out.println("Element # " + elementNumber + " is a number");
                if(arrayCol == 6) {
                    arrayCol = 0;
                }
                // Add the number found to the arrayList here
                sixNumberSetList.get(arrayRow).add(arrayCol, number);
                ++numbersFoundCounter;
                ++arrayCol;
            } catch(NumberFormatException nfe) {
//                System.out.println("Element # " + elementNumber + " is not a number");
            }
        } // End for loop

        // Creates a text file with the results extracted from the lottery website.
        /*
        if(!file.exists()) {
            try {
                file.createNewFile();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        try (FileWriter writer = new FileWriter(file.getAbsoluteFile())) {
            for(ArrayList<Integer> num : sixNumberSet) {
                writer.write(String.valueOf(num) + "\n");
            }
        } catch(IOException e) {
            e.printStackTrace();
        }
        */

        // Prints each six-number set in the list sixNumberSet
        /*
        for(ArrayList<Integer> num : sixNumberSet) {
            System.out.println(num);
        }
        */

        // Check how many numbers are even and odd in each set and in total.
        int largestAmountEven = 0;
        int largestAmountOdd = 0;
        int totalEvenNumbers = 0;
        int totalOddNumbers = 0;
        int zeroEvenInSet = 0;
        int oneEvenInSet = 0;
        int twoEvenInSet = 0;
        int threeEvenInSet = 0;
        int fourEvenInSet = 0;
        int fiveEvenInSet = 0;
        int sixEvenInSet = 0;
        int zeroOddInSet = 0;
        int oneOddInSet = 0;
        int twoOddInSet = 0;
        int threeOddInSet = 0;
        int fourOddInSet = 0;
        int fiveOddInSet = 0;
        int sixOddInSet = 0;
        double percentZeroEven = 0.0;
        double percentOneEven = 0.0;
        double percentTwoEven = 0.0;
        double percentThreeEven = 0.0;
        double percentFourEven = 0.0;
        double percentFiveEven = 0.0;
        double percentSixEven = 0.0;
        double percentZeroOdd = 0.0;
        double percentOneOdd = 0.0;
        double percentTwoOdd = 0.0;
        double percentThreeOdd = 0.0;
        double percentFourOdd = 0.0;
        double percentFiveOdd = 0.0;
        double percentSixOdd = 0.0;


        for(int i = 0; i < sixNumberSetList.size(); ++i) {
            int evenCounter = 0;
            int oddCounter = 0;
            System.out.println(sixNumberSetList.get(i));
            for(int j = 0; j < numbersInOnePlay; ++j) {
                if(sixNumberSetList.get(i).get(j) % 2 == 0) {
                    ++evenCounter;
                    ++totalEvenNumbers;
                } else {
                    ++oddCounter;
                    ++totalOddNumbers;
                }
            }
            if(evenCounter >= largestAmountEven) {
                largestAmountEven = evenCounter;
            }
            if(oddCounter >= largestAmountOdd) {
                largestAmountOdd = oddCounter;
            }
            //ToDo percentage even and odd
            switch (evenCounter) {
                case 1:
                    ++oneEvenInSet;
                    break;
                case 2:
                    ++twoEvenInSet;
                    break;
                case 3:
                    ++threeEvenInSet;
                    break;
                case 4:
                    ++fourEvenInSet;
                    break;
                case 5:
                    ++fiveEvenInSet;
                    break;
                case 6:
                    ++sixEvenInSet;
                    break;
                default:
                    ++zeroEvenInSet;
                    break;
            }
            switch (oddCounter) {
                case 1:
                    ++oneOddInSet;
                    break;
                case 2:
                    ++twoOddInSet;
                    break;
                case 3:
                    ++threeOddInSet;
                    break;
                case 4:
                    ++fourOddInSet;
                    break;
                case 5:
                    ++fiveOddInSet;
                    break;
                case 6:
                    ++sixOddInSet;
                    break;
                default:
                    ++zeroOddInSet;
                    break;
            }
            System.out.println("even = " + evenCounter);
            System.out.println("odd = " + oddCounter);
            System.out.println();
        } // End for loop that checks even and odd numbers

        // Obtain an average of even and odd numbers.
        int amountOfSets = sixNumberSetList.size();
        double averageEven = (double)totalEvenNumbers / amountOfSets;
        double averageOdd = (double)totalOddNumbers / amountOfSets;
        percentZeroEven = (zeroEvenInSet * 100.00) / amountOfSets;
        percentOneEven = (oneEvenInSet * 100.00) / amountOfSets;
        percentTwoEven = (twoEvenInSet * 100.00) / amountOfSets;


        System.out.println("The amount of numbers is: " + numbersFoundCounter);
        System.out.println("total sets = " + amountOfSets);
        System.out.println("greatest amount of even numbers per set = " + largestAmountEven);
        System.out.println("greatest amount of odd numbers per set = " + largestAmountOdd);
        System.out.println("total even = " + totalEvenNumbers);
        System.out.println("total odd = " + totalOddNumbers);
        System.out.println("Even average = " + averageEven);
        System.out.println("Odd average = " + averageOdd);
        // End check how many numbers are even and odd in each set and in total.

        // Which is the most repeated number and the least
        // ToDo is there a better way to do this?
        int counter1 = 0;
        int counter2 = 0;
        int counter3 = 0;
        int counter4 = 0;
        int counter5 = 0;
        int counter6 = 0;
        int counter7 = 0;
        int counter8 = 0;
        int counter9 = 0;
        int counter10 = 0;
        int counter11 = 0;
        int counter12 = 0;
        int counter13 = 0;
        int counter14 = 0;
        int counter15 = 0;
        int counter16 = 0;
        int counter17 = 0;
        int counter18 = 0;
        int counter19 = 0;
        int counter20 = 0;
        int counter21 = 0;
        int counter22 = 0;
        int counter23 = 0;
        int counter24 = 0;
        int counter25 = 0;
        int counter26 = 0;
        int counter27 = 0;
        int counter28 = 0;
        int counter29 = 0;
        int counter30 = 0;
        int counter31 = 0;
        int counter32 = 0;
        int counter33 = 0;
        int counter34 = 0;
        int counter35 = 0;
        int counter36 = 0;
        int counter37 = 0;
        int counter38 = 0;
        int counter39 = 0;
        int counter40 = 0;
        int counter41 = 0;
        int counter42 = 0;
        int counter43 = 0;
        int counter44 = 0;
        int counter45 = 0;
        int counter46 = 0;
        int counter47 = 0;
        int counter48 = 0;
        int counter49 = 0;
        int counter50 = 0;
        int counter51 = 0;
        int counter52 = 0;
        int counter53 = 0;

        // Counts numbers
        for(int i = 0; i < sixNumberSetList.size(); ++i) {
            for(int j = 0; j < 6; ++j) {
                switch(sixNumberSetList.get(i).get(j)) {
                    case 1:
                        ++counter1;
                        break;
                    case 2:
                        ++counter2;
                        break;
                    case 3:
                        ++counter3;
                        break;
                    case 4:
                        ++counter4;
                        break;
                    case 5:
                        ++counter5;
                        break;
                    case 6:
                        ++counter6;
                        break;
                    case 7:
                        ++counter7;
                        break;
                    case 8:
                        ++counter8;
                        break;
                    case 9:
                        ++counter9;
                        break;
                    case 10:
                        ++counter10;
                        break;
                    case 11:
                        ++counter11;
                        break;
                    case 12:
                        ++counter12;
                        break;
                    case 13:
                        ++counter13;
                        break;
                    case 14:
                        ++counter14;
                        break;
                    case 15:
                        ++counter15;
                        break;
                    case 16:
                        ++counter16;
                        break;
                    case 17:
                        ++counter17;
                        break;
                    case 18:
                        ++counter18;
                        break;
                    case 19:
                        ++counter19;
                        break;
                    case 20:
                        ++counter20;
                        break;
                    case 21:
                        ++counter21;
                        break;
                    case 22:
                        ++counter22;
                        break;
                    case 23:
                        ++counter23;
                        break;
                    case 24:
                        ++counter24;
                        break;
                    case 25:
                        ++counter25;
                        break;
                    case 26:
                        ++counter26;
                        break;
                    case 27:
                        ++counter27;
                        break;
                    case 28:
                        ++counter28;
                        break;
                    case 29:
                        ++counter29;
                        break;
                    case 30:
                        ++counter30;
                        break;
                    case 31:
                        ++counter31;
                        break;
                    case 32:
                        ++counter32;
                        break;
                    case 33:
                        ++counter33;
                        break;
                    case 34:
                        ++counter34;
                        break;
                    case 35:
                        ++counter35;
                        break;
                    case 36:
                        ++counter36;
                        break;
                    case 37:
                        ++counter37;
                        break;
                    case 38:
                        ++counter38;
                        break;
                    case 39:
                        ++counter39;
                        break;
                    case 40:
                        ++counter40;
                        break;
                    case 41:
                        ++counter41;
                        break;
                    case 42:
                        ++counter42;
                        break;
                    case 43:
                        ++counter43;
                        break;
                    case 44:
                        ++counter44;
                        break;
                    case 45:
                        ++counter45;
                        break;
                    case 46:
                        ++counter46;
                        break;
                    case 47:
                        ++counter47;
                        break;
                    case 48:
                        ++counter48;
                        break;
                    case 49:
                        ++counter49;
                        break;
                    case 50:
                        ++counter50;
                        break;
                    case 51:
                        ++counter51;
                        break;
                    case 52:
                        ++counter52;
                        break;
                    case 53:
                        ++counter53;
                        break;
                } // End switch
            } // Inner for loop
        } // End of loop that counts numbers

        // Prints amount of times every number appears
        ArrayList<Integer> counterArray = new ArrayList<Integer>();
        counterArray.add(counter1);
        counterArray.add(counter2);
        counterArray.add(counter3);
        counterArray.add(counter4);
        counterArray.add(counter5);
        counterArray.add(counter6);
        counterArray.add(counter7);
        counterArray.add(counter8);
        counterArray.add(counter9);
        counterArray.add(counter10);
        counterArray.add(counter11);
        counterArray.add(counter12);
        counterArray.add(counter13);
        counterArray.add(counter14);
        counterArray.add(counter15);
        counterArray.add(counter16);
        counterArray.add(counter17);
        counterArray.add(counter18);
        counterArray.add(counter19);
        counterArray.add(counter20);
        counterArray.add(counter21);
        counterArray.add(counter22);
        counterArray.add(counter23);
        counterArray.add(counter24);
        counterArray.add(counter25);
        counterArray.add(counter26);
        counterArray.add(counter27);
        counterArray.add(counter28);
        counterArray.add(counter29);
        counterArray.add(counter30);
        counterArray.add(counter31);
        counterArray.add(counter32);
        counterArray.add(counter33);
        counterArray.add(counter34);
        counterArray.add(counter35);
        counterArray.add(counter36);
        counterArray.add(counter37);
        counterArray.add(counter38);
        counterArray.add(counter39);
        counterArray.add(counter40);
        counterArray.add(counter41);
        counterArray.add(counter42);
        counterArray.add(counter43);
        counterArray.add(counter44);
        counterArray.add(counter45);
        counterArray.add(counter46);
        counterArray.add(counter47);
        counterArray.add(counter48);
        counterArray.add(counter49);
        counterArray.add(counter50);
        counterArray.add(counter51);
        counterArray.add(counter52);
        counterArray.add(counter53);
        System.out.println();
        for(int i = 0; i < counterArray.size(); ++i) {
            int number = i + 1;
            int amount = counterArray.get(i);
            System.out.println("Number " + number + " appears " + amount + " times");
        }

        // Prints amount of ocurrences of each number.
        HashMap<String, Integer> counterMap = new HashMap<>();
        // Add keys and values
        counterMap.put("1", counter1);
        counterMap.put("2", counter2);
        counterMap.put("3", counter3);
        counterMap.put("4", counter4);
        counterMap.put("5", counter5);
        counterMap.put("6", counter6);
        counterMap.put("7", counter7);
        counterMap.put("8", counter8);
        counterMap.put("9", counter9);
        counterMap.put("10", counter10);
        counterMap.put("11", counter11);
        counterMap.put("12", counter12);
        counterMap.put("13", counter13);
        counterMap.put("14", counter14);
        counterMap.put("15", counter15);
        counterMap.put("16", counter16);
        counterMap.put("17", counter17);
        counterMap.put("18", counter18);
        counterMap.put("19", counter19);
        counterMap.put("20", counter20);
        counterMap.put("21", counter21);
        counterMap.put("22", counter22);
        counterMap.put("23", counter23);
        counterMap.put("24", counter24);
        counterMap.put("25", counter25);
        counterMap.put("26", counter26);
        counterMap.put("27", counter27);
        counterMap.put("28", counter28);
        counterMap.put("29", counter29);
        counterMap.put("30", counter30);
        counterMap.put("31", counter31);
        counterMap.put("32", counter32);
        counterMap.put("33", counter33);
        counterMap.put("34", counter34);
        counterMap.put("35", counter35);
        counterMap.put("36", counter36);
        counterMap.put("37", counter37);
        counterMap.put("38", counter38);
        counterMap.put("39", counter39);
        counterMap.put("40", counter40);
        counterMap.put("41", counter41);
        counterMap.put("42", counter42);
        counterMap.put("43", counter43);
        counterMap.put("44", counter44);
        counterMap.put("45", counter45);
        counterMap.put("46", counter46);
        counterMap.put("47", counter47);
        counterMap.put("48", counter48);
        counterMap.put("49", counter49);
        counterMap.put("50", counter50);
        counterMap.put("51", counter51);
        counterMap.put("52", counter52);
        counterMap.put("53", counter53);

//        System.out.println();
//        // Prints the map sorted by value.
//        counterMap.entrySet()
//                .stream()
//                .sorted(Map.Entry.<String, Integer>comparingByValue())
//                .forEach(System.out::println);

        // Creates a new sorted-by-value map.
        Map<String, Integer> sortedCounterMap = counterMap.entrySet()
                .stream()
                .sorted(Map.Entry.<String, Integer>comparingByValue())
                .collect(Collectors.toMap(
                        Map.Entry::getKey,
                        Map.Entry::getValue,
                        (oldValue, newValue) -> oldValue, LinkedHashMap::new));
        System.out.println();
        System.out.println("Numbers in order of occurrence from least to greatest.");
        System.out.println(sortedCounterMap);
        // End which is the most repeated number and the least

        // Check for sequences in the sets
        int firstNumberToCheck;
        int secondNumberToCheck;
        int correctDifferenceBetweenNums = 1;
        int numberSetsNoSeq = 0;
        int numberSetsWithSeq = 0;
        int onePair = 0;
        int twoPair = 0;
        int threePair = 0;
        double percentSequenceSets = 0.0;
        double percentNoSequence = 0.0;
        double percentOnePair = 0.0;
        double percentTwoPair = 0.0;
        double percentThreePair = 0.0;
//        DecimalFormat numberFormat = new DecimalFormat("#.0000");
        System.out.println();
        for (int i = 0; i < sixNumberSetList.size(); ++i) {
            int numbersInSequenceCounter = 0;
            for (int j = 0; j < numbersInOnePlay - 1; ++j) { // numbersInOnePlay - 1 because the greatest index for first number is [0, 4] while index second pointer needs to be [0, 5].
                for (int k = j + 1; k < numbersInOnePlay; ++k) {
                    firstNumberToCheck = sixNumberSetList.get(i).get(j);
                    secondNumberToCheck = sixNumberSetList.get(i).get(k);

                    // Checks for sequences going up if first two numbers are in sequence.
                    if (secondNumberToCheck - firstNumberToCheck == correctDifferenceBetweenNums) {
                        ++numbersInSequenceCounter;
                    }
                    // Checks for sequences going down if first two numbers are in sequence.
                    else if (firstNumberToCheck - secondNumberToCheck == correctDifferenceBetweenNums) {
                        ++numbersInSequenceCounter;
                    }
                } // End of k variable loop
            } // End of j variable for loop
            switch (numbersInSequenceCounter) {
                case 1:
                    ++onePair;
                    break;
                case 2:
                    ++twoPair;
                    break;
                case 3:
                    ++threePair;
                    break;
                default:
                    ++numberSetsNoSeq;
                    break;
            }
            System.out.println("This set, " + sixNumberSetList.get(i) + ", has " + numbersInSequenceCounter + " pair/s of numbers in sequence");
            System.out.println();
        } // End of i variable loop.
        numberSetsWithSeq = onePair + twoPair + threePair;
        percentSequenceSets = (numberSetsWithSeq * 100.00) / amountOfSets;
        percentNoSequence = (numberSetsNoSeq * 100.00) / amountOfSets;
        percentOnePair = (onePair * 100.00) / amountOfSets;
        percentTwoPair = (twoPair * 100.00) / amountOfSets;
        percentThreePair = (threePair * 100.00) / amountOfSets;
        System.out.println("Out of " + amountOfSets + " sets, " + numberSetsWithSeq + "(" + numberFormat.format(percentSequenceSets) + "%)" + " contain numbers in sequence.");
        System.out.println("No sequence: " + numberSetsNoSeq + "(" + numberFormat.format(percentNoSequence) + "%)");
        System.out.println("One pair: " + onePair + "(" + numberFormat.format(percentOnePair) + "%)");
        System.out.println("Two pairs: " + twoPair + "(" + numberFormat.format(percentTwoPair) + "%)");
        System.out.println("Three pairs: " + threePair + "(" + numberFormat.format(percentThreePair) + "%)");

        // End check for sequences in the sets


    } // End findNumbers()

    // ToDo if this function is executed after the other two buttons, an array with many many arrays is printed. Why?
    public void createResultArray() {
//        int[][] numbers = {{1,2,3,4,5,6}, {7,8,9,10,11,12}, {13,14,15,16,17,18}};
//        System.out.println(numbers[2][0]);

        /*
        boolean add( ArrayList<Object> e) : It is used to insert elements in the specified collection.
        void add( int index, ArrayList<Object> e) : It is used to insert the elements at specified
        position in a Collection.
        */
        sixNumberSetList.add(new ArrayList<Integer>());
        sixNumberSetList.get(0).add(0, 1); // boolean add
        sixNumberSetList.add(new ArrayList<Integer>(Arrays.asList(2, 3, 4))); // void add
        System.out.println(sixNumberSetList);
    }





} // End Controller
