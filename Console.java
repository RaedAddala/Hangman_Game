import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Random;
import java.util.Scanner;

public class Console {
    public static void main(String[] args) throws FileNotFoundException {
        Scanner sc = new Scanner(System.in);
        String key;
        String word;
        System.out.println("Welcome to the Console Version of Hangman! Enjoy");
        do {
            System.out.print("Please Choose your Preferred Game Type : ");
            System.out.println("1 Player or 2 Players");
            key = sc.nextLine();
        }while(!key.equals("1") && !key.equals("2"));
        boolean KeepPlaying = true;
        int turn = 0;
        if(key.equals("2"))
        {
            String[] players = new String[2];
            Integer[] playerScore = {0,0};
            System.out.print("Player 1, Please enter your name : ");
            players[0] = sc.nextLine();
            System.out.print("Player 2, Please enter your name : ");
            players[1] = sc.nextLine();
            while(KeepPlaying)
            {
             System.out.print("SCORE : \n");
             System.out.print(players[0]+" : " + playerScore[0] + "  , ");
             System.out.println(players[1]+" : " + playerScore[1] + ".");
             do {
                 System.out.println("It's "+players[turn]+" turn. Please Choose Your word : ");
                 word = sc.nextLine();
             }while (!word.equals("") && !word.matches("^[a-zA-Z]*$"));
             word = word.toLowerCase();
             System.out.println("\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n");
             System.out.println(players[(turn+1)%2]+" good luck guessing :)");
             if(!Console.Game(word,sc)) {
                 System.out.println("You Lost ! The word is " + word);
                 System.out.println("Better Chance Next Time.");
                 playerScore[turn]++;
             }
             else
             {
                 System.out.println("Right Guess!");
                 playerScore[(turn + 1) % 2]++;
             }
             turn = (turn+1)%2;
             System.out.println("\nPress Q to quit.");
             System.out.println("Press Any Other Key to Continue.");
             key = sc.nextLine();
             if(key.equals("Q"))  KeepPlaying = false;
            }
            //Game Stats :
            System.out.println("\n\n"+players[0] + " Played "+(playerScore[0]+playerScore[1]) +" Games and Won "+playerScore[0]);
            System.out.println("Winning Rate : " + (float)playerScore[0]/(playerScore[0]+playerScore[1]) * 100 + " %." );
            System.out.println(players[1] + " Played "+(playerScore[0]+playerScore[1]) +" Games and Won "+playerScore[1]);
            System.out.println("Winning Rate : " + (float)playerScore[1]/(playerScore[0]+playerScore[1]) * 100 + " %." );
            if(playerScore[0] > playerScore[1]) System.out.println(players[0] + " WINS.");
            else if(playerScore[0] < playerScore[1]) System.out.println(players[1] + " WINS.");
            else System.out.println("TIE.");
        }
        else
        {
            String playername;
            int playerscore = 0;
            Scanner FileHandler = new Scanner(new File( new File("").getAbsolutePath()+"\\Source\\EnglishWords.txt"));
            ArrayList<String> EasyWords = new ArrayList<>();
            ArrayList<String> MediumWords= new ArrayList<>();
            ArrayList<String> HardWords= new ArrayList<>();
            ArrayList<String> BringItOnWords= new ArrayList<>();
            ArrayList[] Difficulties = {EasyWords,MediumWords,HardWords,BringItOnWords};
            int Difficulty;
            while(FileHandler.hasNext())
            {
                String temp = FileHandler.nextLine();
                if(temp.length() < 5) EasyWords.add(temp);
                else if(temp.length() < 8) MediumWords.add(temp);
                else if(temp.length() < 11) HardWords.add(temp);
                else BringItOnWords.add(temp);
            }
            System.out.print("Please enter your name : ");
            playername = sc.nextLine();
            do {
                System.out.println(playername +" Choose Your Preferred Difficulty : 0-Easy, 1-Medium, 2-Hard, 3-Very Long Words ");
                Difficulty = Integer.parseInt((sc.nextLine()));
            }while(Difficulty > 3 || Difficulty < 0);
            Random random =new Random();
            while (KeepPlaying)
            {
                turn++;
                word = (String) Difficulties[Difficulty].get(random.nextInt(Difficulties[Difficulty].size()));
                if(Console.Game(word,sc))
                {
                    System.out.println("Right Guess!");
                    playerscore++;
                }
                else {
                    System.out.println("You Lost ! The word is " + word);
                    System.out.println("Better Chance Next Time.");
                }
                System.out.println("\nPress Q to quit.");
                System.out.println("OR Press D to change difficulty.");
                System.out.println("ELSE press any Key to continue.");
                key = sc.nextLine();
                if(key.equals("Q"))  KeepPlaying = false;
                else if(key.equals("D"))
                {
                    do {
                        System.out.println(playername +" Choose Your Preferred Difficulty : 0-Easy, 1-Medium, 2-Hard, 3-Very Long Words ");
                        Difficulty = Integer.parseInt((sc.nextLine()));
                    }while(Difficulty > 3 || Difficulty < 0);
                }
            }
            //Game Stats :
            System.out.println("\n\n"+playername + " Played "+turn +" Games and Won "+playerscore);
            System.out.println("Winning Rate : " + (float)playerscore/turn * 100 + " %." );
        }
    }
    private static boolean Game(String word,Scanner sc)
    {
        String guess;
        int WrongGuesses = 0;
        ArrayList<Character> GuessedLetters = new ArrayList<>();
        while(true)
        {
            boolean StillNotRight = false;
            for(int i = 0 ; i < word.length() ; ++i){
                if(GuessedLetters.contains(word.charAt(i)))
                    System.out.print(word.charAt(i));
                else {
                    System.out.print("_");
                    StillNotRight = true;
                }
            }
            System.out.println(".");
            if(!StillNotRight) return true;
            do {
                System.out.println("Make your guess");
                guess = sc.nextLine();
            }while (guess.equals(""));
            char c = guess.toLowerCase().charAt(0);
            if( word.indexOf(c) != -1 && !GuessedLetters.contains(c))
            {
                GuessedLetters.add(c);
            }
            else
            {
                WrongGuesses++;
            }
            System.out.println("  +------------");
            System.out.println("  |            ");
            if (WrongGuesses > 1) System.out.print(" \\");
            else System.out.print("  ");
            if (WrongGuesses > 0) System.out.print("O");
            if (WrongGuesses > 2) System.out.println("/           ");
            else System.out.println("            ");
            if (WrongGuesses > 3) System.out.println("  |            ");
            if (WrongGuesses > 4) System.out.print(" /");
            if (WrongGuesses > 5) System.out.println(" \\           ");
            else System.out.println("             ");
            if(WrongGuesses == 6) return false;
        }
    }
}