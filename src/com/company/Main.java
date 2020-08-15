package com.company;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;
import java.util.stream.Collectors;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

public class Main {

    public static void main(String[] args) {
        try {
          HashMap<String,Integer> dict = ReadCorpus();
          ReadInput(dict);
         // System.out.println(editDistance("Boy","Toy"));
        }catch (IOException ex)
        {
            System.out.println(ex);
        }
    }



    public static HashMap ReadCorpus() throws IOException
    {
        HashMap<String,Integer> dict = new HashMap<>();
        BufferedReader reader = new BufferedReader(new FileReader("C:\\Users\\tejas\\IdeaProjects\\SpellChecker\\src\\com\\company\\Sherlock.txt"));

        String content = reader.readLine();

        while(content!=null)
        {

            String[] words = content.split(" ");
            for(int i=0;i<words.length;i++)
            {
                if(words[i].matches("[a-zA-Z]+") && words[i].length()>1)
                {
                    if (dict.containsKey(words[i])) {
                        int value = dict.get(words[i]);
                        dict.put(words[i], value + 1);
                    } else {
                        dict.put(words[i], 1);
                    }
                }
            }
            content = reader.readLine();
        }
        return dict;
    }

    public static void ReadInput(HashMap<String,Integer> dict)
    {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Please input the line");
        String input = scanner.nextLine();
        String[] words = input.split(" ");
        String[] suggestions = new String[4];
        for(int i=0;i<words.length;i++)
        {
            if(dict.containsKey(words[i]))
            {

            }
            else
            {
                System.out.println(words[i]+" - The word does not exist");
                System.out.println("Possible Suggestions");
                suggestions = getSuggestions(words[i],dict);
                for(int k=0;k<suggestions.length;k++)
                {
                    if(suggestions[k]!=null)
                    System.out.println((k+1)+"-"+suggestions[k]);
                }
            }
        }
    }

    public static int editDistance(String dict,String input)
    {
        int[][] distance = new int[dict.length()+1][input.length()+1];
        int cost=0;
        for(int j=1;j<=input.length();j++)
        {
            distance[0][j] = j;
        }
        for(int i=1;i<=dict.length();i++)
        {
            distance[i][0] = i;
        }
        for(int i=1;i<=dict.length();i++)
        {

            for(int j=1;j<=input.length();j++)
            {
                cost = (dict.charAt(i - 1) == input.charAt(j - 1)) ? 0:1;

                distance[i][j] = findMin(cost+distance[i-1][j],cost+distance[i][j-1],cost+distance[i-1][j-1]);

            }
        }
        return distance[dict.length()][input.length()];

    }

    public static String[] getSuggestions(String words,HashMap<String,Integer> dict)
    {
        int getDistance,i=0;
        String[] suggestions = new String[5];
        for(Map.Entry<String,Integer> set:dict.entrySet())
        {
            getDistance = editDistance(set.getKey(),words);
            if(getDistance<3)
            {
                if(i>4)
                {
                    break;
                }
                suggestions[i] = set.getKey();
                i++;
            }

        }
        return suggestions;
    }

    public static int findMin(int x,int y,int z)
    {
        if(x<=y && x<=z)
            return x;
        else if(y<=x && y<=z)
            return y;
        else if(z<=x && z<=y)
            return z;
        else
            return -1;
    }


}


