package sample;

import java.net.InetAddress;
import java.net.UnknownHostException;

public class GetHost {
    public static void main(String[] args) {
        try {
            InetAddress address = InetAddress.getLocalHost();
            System.out.println(address.toString());
        } catch (UnknownHostException e) {
            e.printStackTrace();
        }
    }
}
