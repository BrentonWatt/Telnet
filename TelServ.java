/**
 * Created by Brenton on 2/9/2016.
 */
import java.net.*;
import java.io.*;
public class TelServ
{
    public static void main(String[] args)throws IOException
    {
        final int port = 55555;
        System.out.println("Creating a server on port " + port);
        ServerSocket serv = new ServerSocket(port);
        while (true)
        {
            Socket sock = serv.accept();
            OutputStream oStream = sock.getOutputStream();
            PrintWriter print = new PrintWriter(oStream, true);
            print.println("Hello, what's your name");

            BufferedReader in = new BufferedReader(new InputStreamReader(sock.getInputStream()));
            String str = in.readLine();
            print.println("Hello " + str);
            print.close();
            sock.close();

            System.out.println(str + " connected and we said hello");
        }
    }
}
