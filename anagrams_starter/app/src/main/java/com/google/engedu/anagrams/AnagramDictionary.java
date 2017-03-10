/* Copyright 2016 Google Inc.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.google.engedu.anagrams;

import java.io.BufferedReader;
import java.io.CharArrayReader;
import java.io.IOException;
import java.io.Reader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Random;
import java.util.Arrays;
import java.util.regex.Pattern;

import android.os.DeadObjectException;
import android.util.Log;

public class AnagramDictionary {

    private static final int MIN_NUM_ANAGRAMS = 5;
    private static final int DEFAULT_WORD_LENGTH = 3;
    private static final int MAX_WORD_LENGTH = 7;
    private Random random = new Random();
    private Integer wordLength = DEFAULT_WORD_LENGTH;
    private ArrayList<String> wordList = new ArrayList<>();
    private HashSet<String> wordSet = new HashSet<>();
    private HashMap<String, ArrayList<String>> lettersToWord = new HashMap<>();
    private HashMap<Integer, ArrayList<String>> sizeToWords = new HashMap<>();

    public AnagramDictionary(Reader reader) throws IOException {
        BufferedReader in = new BufferedReader(reader);
        String line;

        while((line = in.readLine()) != null) {
            String word = line.trim();
            wordList.add(word);
            wordSet.add(word);

            if(lettersToWord.containsKey(sortLetters(word))){
                ArrayList<String> temp;
                temp = lettersToWord.get(sortLetters(word));
                temp.add(word);
                lettersToWord.put(sortLetters(word), temp);
            }
            else{
                ArrayList<String> temp = new ArrayList<>();
                temp.add(word);
                lettersToWord.put(sortLetters(word), temp);
            }
        }
    }

    public String sortLetters(String inputWord){
        char[] chars = inputWord.toCharArray();
        Arrays.sort(chars);
        String sorted = new String(chars);

        return sorted;
    }


    public boolean isGoodWord(String word, String base) {
        if(wordSet.contains(word)){
            if(word.contains(base)){
                return false;
            }
            else {
                return true;
            }
        }
        return false;
    }

    public List<String> getAnagrams(String targetWord) {
        ArrayList<String> result = new ArrayList<String>();

        String sortedWord;
        sortedWord = sortLetters(targetWord);

        for(String sample: wordList){
            String tempWord = sortLetters(sample);
            if(sortedWord.length() == tempWord.length()){
                if(sortedWord.equals(tempWord)) {
                    result.add(sample);
                }
            }
        }

        return result;
    }

    public List<String> getAnagramsWithOneMoreLetter(String word) {
        ArrayList<String> result = new ArrayList<String>();
        char[] alphabet = "abcdefghijklmnopqrstuvwxyz".toCharArray();

        result = new ArrayList<>(getAnagrams(word));

        for(int i=0; i < alphabet.length; i++){
            String newWord = word.concat(Character.toString(alphabet[i]));
            if(lettersToWord.containsKey(sortLetters(newWord))){
//                Log.d("KARENTAFOLLA", sortLetters(newWord));
                for(String s:lettersToWord.get(sortLetters(newWord))){
                    result.add(s);
                }
            }
        }

        return result;
    }

    public String pickGoodStarterWord() {
        int wordAna = 0;

        while(wordAna < wordLength){
            int x = random.nextInt(wordList.size());
            wordAna =  lettersToWord.get(sortLetters(wordList.get(x))).size();
            if(wordAna == MIN_NUM_ANAGRAMS){
                return wordList.get(x);
            }
        }

        return "stop";
    }


}
