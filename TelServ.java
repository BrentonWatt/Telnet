/**
 * Created by Brenton on 2/9/2016.
 */
import java.net.*;
import java.io.*;

public class TelServ
{
    //Ansi idea used from stackoverflow.com
    public static final String ANSI_RESET = "\u001B[0m";
    public static final String ANSI_BLACK = "\u001B[30m";
    public static final String ANSI_RED = "\u001B[31m";
    public static final String ANSI_GREEN = "\u001B[32m";
    public static final String ANSI_YELLOW = "\u001B[33m";
    public static final String ANSI_BLUE = "\u001B[34m";
    public static final String ANSI_PURPLE = "\u001B[35m";
    public static final String ANSI_CYAN = "\u001B[36m";
    public static final String ANSI_WHITE = "\u001B[37m";
    public static final String ANSI_CLEAR = "\u001b[2J";
    public static void main(String[] args)throws IOException
    {

        final int port = 55555;
        System.out.println("Creating a server on port " + port);
        String fName = "QnA.txt";
        String line = null;
        boolean quit = false;
        String ans, cor = null, temp, question = null;
        int count = 1, correct, conAns, tot = 0, totQ = 0, tCor=0;
        char num;
        try
        {
            FileReader fileReader = new FileReader(fName);
            BufferedReader txt = new BufferedReader(fileReader);

            ServerSocket serv = new ServerSocket(port);
            while (true)
            {
                Socket sock = serv.accept();
                OutputStream oStream = sock.getOutputStream();
                PrintWriter print = new PrintWriter(oStream, true);
                print.println("Hello, what's your name");

                BufferedReader in = new BufferedReader(new InputStreamReader(sock.getInputStream()));
                String str = in.readLine();
                print.println("Hello " + str + ", The test will now begin, are you ready? Y/N");
                if(in.readLine().toUpperCase().charAt(0) == 'N')
                {
                    quit = true;
                }
                temp = txt.readLine();

                while (quit == false) //While the user hasn't chosen to quit the test
                {
                    totQ++;
                    correct = 0;
                    tCor = 0;
                    if(temp.charAt(0)== '?')//Make sure that line read in is a question
                    {
                        question = temp;
                    }
                    temp = txt.readLine();
                    print.println(ANSI_CLEAR + question.substring(1));
                    while(temp.charAt(0) != '?'&& temp.charAt(0) != '*')//Make sure we're still going through answers
                    {
                        if(temp.charAt(0)== '+')//If the answer is correct, check the number and store the answer
                        {
                            correct = count;
                            cor = temp;
                            tCor++;
                        }
                        num = (char)(count + 64);
                        print.println(num + ". " + temp.substring(1));
                        count++;
                        temp = txt.readLine();
                    }
                    if (correct == 0)
                    {
                        correct = count;
                        cor = "None of the above";
                        num = (char)(count + 64);
                        print.println(num + ". None of the above");
                    }
                    if(tCor > 1)
                    {
                        correct = count;
                        cor = "More than one of the above";
                        num = (char)(count + 64);
                        print.println(num + ". More than one of the above");
                    }
                    ans = in.readLine().toUpperCase();
                    conAns = (int)ans.charAt(0)-64;
                    if(conAns == correct)
                    {
                        print.println(ANSI_GREEN + "Congratulations, that is correct!");
                        tot = tot+1;
                    }
                    else if(conAns != correct)
                    {
                        print.println(ANSI_RED + "The correct answer is " + cor);
                    }
                    count = 1;
                    print.println(ANSI_WHITE + "Would you like to continue: Y/N");
                    if(in.readLine().toUpperCase().charAt(0) == 'N')
                    {
                        quit = true;
                        print.println("Your score is " + tot + "/" + totQ);
                    }
                    else if(temp.charAt(0) == '*')
                    {
                        quit = true;
                        print.println("Unfortunately there are no more questions, your score is " + tot + "/" + totQ);
                    }
                }
                print.close();
                sock.close();

                System.out.println(str + " connected and we said hello");
            }
        }
        catch(FileNotFoundException f)
        {
            System.out.println("There was an error reading the file");
        }
        catch (IOException i)
        {
            System.out.println("The file could not be found");
        }
    }
}
