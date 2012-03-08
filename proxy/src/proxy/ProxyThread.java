package proxy;

import java.net.*;
import java.io.*;
import java.util.*;

public class ProxyThread extends Thread {
    private Socket socket = null;
    private static final int BUFFER_SIZE = 32768;
    public ProxyThread(Socket socket) {
        super("ProxyThread");
        this.socket = socket;
    }

    public void run() {
        //get input from user
        //send request to server
        //get response from server
        //send response to user

        try {
            DataOutputStream out =
		new DataOutputStream(socket.getOutputStream());
            BufferedReader in = new BufferedReader(
		new InputStreamReader(socket.getInputStream()));

            String inputLine, outputLine;
            int cnt = 0;
            String urlToCall = "";
            ///////////////////////////////////
            //begin get request from client
            System.out.println("\n\n\n\nNew request");
            while ((inputLine = in.readLine()) != null) {
                try {
                    StringTokenizer tok = new StringTokenizer(inputLine);
                    tok.nextToken();
                } catch (Exception e) {
                    break;
                }
                //parse the first line of the request to find the url
                System.out.println(inputLine); //Reads the whole request.
                if (cnt == 0) {
                    String[] tokens = inputLine.split(" ");
                    urlToCall = tokens[1];
                    //can redirect this to output log
                    System.out.println("Request for : " + urlToCall);
                    
                    if(urlToCall.contains("localhost")){
                       // urlToCall="http://www.cs.ubc.ca/~will/";
                        urlToCall="http://www.sonsmall.com/";
                    }
                    System.out.println("Request URL [Changed for debug] : " + urlToCall);
                    
                }

                cnt++;
            }
            //end get request from client
            ///////////////////////////////////

            BufferedReader rd = null;
            try {
                //System.out.println("sending request
		//to real server for url: "
                //        + urlToCall);
                ///////////////////////////////////
                //begin send request to server, get response from server
                URL url = new URL(urlToCall);
                URLConnection conn = url.openConnection();
                conn.setDoInput(true);
                //not doing HTTP posts
                conn.setDoOutput(false);
                //System.out.println("Type is: "
			//+ conn.getContentType());
                //System.out.println("content length: "
			//+ conn.getContentLength());
                //System.out.println("allowed user interaction: "
			//+ conn.getAllowUserInteraction());
                //System.out.println("content encoding: "
			//+ conn.getContentEncoding());
                //System.out.println("content type: "
			//+ conn.getContentType());

                // Get the response
                InputStream is = null;
                HttpURLConnection huc = (HttpURLConnection)conn;
//                System.out.println("Getting input stream");
//                System.out.println(conn.getContentLength());
//                System.out.println("Got input stream");
                if (conn.getContentLength() > 0) {
                    try {
                    	
                        is = conn.getInputStream();
                        
                        rd = new BufferedReader(new InputStreamReader(is));
                        if (is == null){
                        	throw  new  Exception();
                        }
                    } catch (IOException ioe) {
                        System.out.println(
				"********* IO EXCEPTION **********: " + ioe);
                    }
                }
                
//                System.out.println("After if");
                //end send request to server, get response from server
                ///////////////////////////////////

//                is = null;
//                DataInputStream dis;
//                String line;
//
//                try {
//                    url = new URL(urlToCall);
//                    is = url.openStream();  // throws an IOException
//                    dis = new DataInputStream(new BufferedInputStream(is));
//
//                    while ((line = dis.readLine()) != null) {
////                        System.out.println(line);
//                    }
//                } catch (MalformedURLException mue) {
//                     mue.printStackTrace();
//                } catch (IOException ioe) {
//                     ioe.printStackTrace();
//                } finally {
//                    try {
//                        is.close();
//                    } catch (IOException ioe) {
//                        // nothing to see here
//                    }
//                }                
                
                ///////////////////////////////////
                //begin send response to client
                byte by[] = new byte[ BUFFER_SIZE ];
//                System.out.println(is==null); //False
                int index = is.read( by, 0, BUFFER_SIZE );
//                System.out.println("This the index " + index);
//                System.out.println("The Buffer Size " + BUFFER_SIZE);
                while ( index != -1 )
                {
                

                  out.write( by, 0, index );
                  index = is.read( by, 0, BUFFER_SIZE );
                }
                out.flush();

                //end send response to client
                ///////////////////////////////////
            } catch (Exception e) {
                //can redirect this to error log
                System.err.println("Encountered exception: " + e);
                //encountered error - just send nothing back, so
                //processing can continue
                out.writeBytes("");
            }

            //close out all resources
            if (rd != null) {
                rd.close();
            }
            if (out != null) {
                out.close();
            }
            if (in != null) {
                in.close();
            }
            if (socket != null) {
                socket.close();
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}