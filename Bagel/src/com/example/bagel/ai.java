package com.example.bagel;

/**
 * Created by ipedisich on 8/7/13.
 */
import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Scanner;


public class Main {

    /**
     * @param args
     */
    public static ArrayList<String> setupDict(String dict_file){
        BufferedReader r;
        try {
            r = new BufferedReader(new FileReader(new File(dict_file)));
            ArrayList<String> goodWords = new ArrayList<String>();
            while(r.ready())
            {
                String line = r.readLine();
                if (line.trim().length()==5)
                    goodWords.add(line.trim());
            }
            r.close();
            return goodWords;
        } catch (FileNotFoundException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();

        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return new ArrayList<String>();

    }

    public static boolean playerTurn(ArrayList<String> allWords, String compWord, Scanner s){
        System.out.println("Guess a word:");
        String myGuess = s.next();
        while (!allWords.contains(myGuess)){
            System.out.println("That word isn't in my dictionary. Guess again:");
            myGuess = s.next();
        }
        if (myGuess.equals(compWord) || myGuess.equals("cheat!")){
            System.out.println("You win! My word was "+compWord);
            return true;
        }
        System.out.println(countLetters(myGuess, compWord)+" from "+myGuess+" are in my word.");
        return false;
    }

    public static int countLetters(String guess, String realWord){
        int numLetters = 0;
        for(char letter:guess.toCharArray()){
            if (realWord.contains(String.valueOf(letter))){
                realWord = realWord.replaceFirst(String.valueOf(letter), "");
                numLetters++;
            }
        }
        return numLetters;
    }

    public static ArrayList<String> compTurn(ArrayList<String> wordsLeft, Scanner s){
        String guess = wordsLeft.get((int)(Math.random()*(wordsLeft.size()-1)));
        boolean good = false;
        int num=0;
        while(!good){
            System.out.println("How many letters from "+guess+" are in your word?");

            try{
                num = s.nextInt();
                good = true;
            }catch(Exception e)
            {
                good = false;
            }
        }
        if (num==5){
            String answer = "";
            while (!answer.toLowerCase().equals("yes") && !answer.toLowerCase().equals("no"))
            {
                System.out.println("Is "+guess+" your word?");
                answer = s.next();
            }
            if (answer.toLowerCase().equals("yes")){
                System.out.println("I WIN!");
                return new ArrayList<String>();
            }
        }
        ArrayList<char[]> notIn = letterCombos(guess, num+1);

        ArrayList<String> newWordlist = cutDown(notIn, wordsLeft, false);

        ArrayList<char[]> orIn = letterCombos(guess, num);
        newWordlist = cutDown(orIn, newWordlist, true);

        for(String word:newWordlist){
            System.out.print(word+",");
        }
        System.out.print('\n');
        return newWordlist;
    }

    public static ArrayList<String> cutDown(ArrayList<char[]> rule, ArrayList<String> wordlist, boolean allLettersGood){
        ArrayList<String> newWordlist = new ArrayList<String>();
        for(String word:wordlist)
        {
            boolean isOkay = !allLettersGood;
            for(char[] combo:rule)
            {
                boolean allLetters = true;
                String wordCheck = word;
                for (char letter:combo){
                    if (!wordCheck.contains(String.valueOf(letter))){
                        allLetters = false;
                        break;
                    }
                    wordCheck = wordCheck.replaceFirst(String.valueOf(letter), "");
                }
                if (allLetters){
                    isOkay = allLettersGood;
                    break;
                }
            }
            if (isOkay)
                newWordlist.add(word);
        }
        return newWordlist;
    }

    public static ArrayList<char[]> letterCombos(String word, int length)
    {
        ArrayList<char[]> combos = new ArrayList<char[]>();
        if(length==0 || length>word.length())
            return combos;
        char[] letters = word.toCharArray();
        for (int i=0; i<letters.length; i++){
            char l = letters[i];

            if(length==1){
                char[] thisCombo = new char[length];
                thisCombo[0] = l;
                combos.add(thisCombo);
            }else{
                ArrayList<char[]> remainingCombos = letterCombos(word.substring(i+1),length-1);
                for(int j=0; j<remainingCombos.size(); j++)
                {
                    char[] thisCombo = new char[length];
                    thisCombo[0] = l;
                    char[] nextCombo = remainingCombos.get(j);
                    for (int k=0; k<nextCombo.length; k++)
                        thisCombo[k+1]=nextCombo[k];
                    combos.add(thisCombo);
                }
            }
        }
        return combos;

    }
    public static void main(String[] args) {
        playBagel();
    }
    public static void playBagel() {
        System.out.println("Welcome to bagel!");
        ArrayList<String> allWords = setupDict("C:\\Users\\ipedisich\\programming\\eclipse\\workspace\\bagelAI\\dict.txt");
        ArrayList<String> wordsLeft = (ArrayList<String>) allWords.clone();
        String compWord = wordsLeft.get((int)(Math.random()*(wordsLeft.size()-1)));
        Scanner s = new Scanner(System.in);
        boolean showDict = (s.next().startsWith("y"));
        if (showDict)
            for(String w:allWords)
                System.out.println(w);
        boolean win = playerTurn(allWords, compWord, s);
        boolean lose = false;
        while(!win && !lose){
            wordsLeft = compTurn(wordsLeft, s);
            if(wordsLeft.size()==0){
                System.out.println("something messed up");
            }
            if(!lose)
                win = playerTurn(allWords, compWord, s);
            if(wordsLeft.size()==1)
                lose = true;
        }
        System.out.println("my word was "+compWord);

    }
}
