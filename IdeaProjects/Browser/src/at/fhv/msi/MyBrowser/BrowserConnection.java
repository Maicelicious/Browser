package at.fhv.msi.MyBrowser;

import java.io.*;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.Socket;

/**
 * Created by Marco Simeth on 28.02.2018.
 */
public class BrowserConnection {
    private String _url;
    private String _domain;
    private String _docRef;
    private MyBrowser _myBrowser;
    private Socket _socket;


    public BrowserConnection() {
        _myBrowser = new MyBrowser();
        _url = _myBrowser.getUrl();
        splitUrl();
        printAll();
        openConnection();
        openRequest();
        printResponse();
    }

    public BrowserConnection(String url) {
        _url = url;
        splitUrl();
        printAll();
        openConnection();
        openRequest();
        printResponse();
    }

    public void splitUrl() {
        String[] newString = _url.split("/");
        if (!_url.contains("//")) {
            _domain = newString[0];
            makeDocRef(newString, 0);
        } else {
            _domain = newString[2];
            makeDocRef(newString, 2);
        }
    }


    public void makeDocRef(String[] newString, int index) {
        StringBuilder stringBuilder = new StringBuilder();

        for (int i = index + 1; i < newString.length; i++) {
            stringBuilder.append("/" + newString[i]);
        }
        _docRef = stringBuilder.toString();
    }

    public void printAll() {
        System.out.println("Url:" + _url);
        System.out.println("Domain:" + _domain);
        System.out.println("DocRef:" + _docRef);
    }

    public void openConnection() {
        int timeout = 10000;
        try {
            _socket = new Socket();
            _socket.connect(new InetSocketAddress(InetAddress.getByName(_domain), 80), timeout);
            _socket.setSoTimeout(timeout);
            System.out.println("Connecting to Server: " + _domain + " at: " + InetAddress.getByName(_domain));


        } catch (IOException e) {
            e.printStackTrace();
        }

    }


    public void openRequest() {
        try {
            PrintWriter printWriter = new PrintWriter(new DataOutputStream(_socket.getOutputStream()));
            printWriter.println("GET / " + _docRef + "HTTP/1.1");
            printWriter.println("Host: " + _domain);
            printWriter.println("");
            printWriter.flush();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void printResponse() {
        StringBuilder input = new StringBuilder();
        InputStream inputStream;
        boolean isFound = false;
        try {
            inputStream = _socket.getInputStream();
            InputStreamReader inputStreamReader = new InputStreamReader(inputStream);
            BufferedReader bufferedReader = new BufferedReader(inputStreamReader);
            String line;
            while ((line = bufferedReader.readLine()) != null) {

                if (isFound) {
                    System.out.println(line);
                } else {
                    if (line.isEmpty()) {
                        isFound = true;
                        System.out.println("");
                    }
                }
            }
            _socket.close();
            System.exit(0);
        } catch (IOException e) {
            e.printStackTrace();
        }

    }


}
