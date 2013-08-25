package com.example.bagel.logic;

/**
 * Created by ipedisich on 8/7/13.
 */
import android.content.Context;

import com.example.bagel.R;
import com.example.bagel.utils.TextUtils;


import java.util.ArrayList;



public class AI {

    ArrayList<String> dictionary;
    ArrayList<String> wordsLeft;

    /**
     * To set up a new AI from scratch at the beginning of a new game
     * @param ctx   The Context for the application, used to recover resources
     */
    public AI(Context ctx){
        dictionary = TextUtils.readTextAsList(ctx, R.raw.dictionary);
        wordsLeft = dictionary;
    }

    /**
     * To set up an AI that has already had words removed from its wordlist
     * @param ctx   The The Context for the application, used to recover resources
     * @param wordsLeft The words remaining in the dictionary
     */
    public AI(Context ctx, ArrayList<String> wordsLeft){
        dictionary = TextUtils.readTextAsList(ctx, R.raw.dictionary);
        this.wordsLeft = wordsLeft;
    }

    /**
     * Run every time the computer gets a response from the user with how many letters from
     * the computer's guess are in the target word. Remakes the dictionary to include only the words
     * that still fit the criterion
     * @param compGuess The word that the computer guessed
     * @param usrAnswer The number of letters in that word that are in the target word
     */
    public void makeNewDictionary(String compGuess, int usrAnswer)
    {
        ArrayList<char[]> notIn = letterCombos(compGuess, usrAnswer+1);
        cutDown(notIn, false);

        ArrayList<char[]> orIn = letterCombos(compGuess, usrAnswer);
        cutDown(orIn, true);
    }

    /**
     * Counts the number of letters that overlap between two words
     * @param guess Word 1
     * @param realWord  Word 2 (ordering doesn't really matter)
     * @return  The number of letters of overlap
     */
    public int countLetters(String guess, String realWord){
        int numLetters = 0;
        for(char letter:guess.toCharArray()){
            if (realWord.contains(String.valueOf(letter))){
                realWord = realWord.replaceFirst(String.valueOf(letter), "");
                numLetters++;
            }
        }
        return numLetters;
    }

    /**
     * Actually performs the cutting down of the dictionary that the AI contains.
     * Will have to be changed at some point to allow for making the AI less good.
     * @param rule  The combination of letters that either all should be or all shouldn't be in the word
     * @param allLettersGood    Whether all letters should or shouln't be in the word
     */
    private void cutDown(ArrayList<char[]> rule, boolean allLettersGood){
        ArrayList<String> newWordlist = new ArrayList<String>();
        for(String word:dictionary)
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
        dictionary = newWordlist;
    }


    /**
     * Makes all of the letter combinations of a certain length that occur within the given word
     * @param word  The given word
     * @param length    The length of the combinations
     * @return  All of the combinations
     */
    private static ArrayList<char[]> letterCombos(String word, int length)
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
}
