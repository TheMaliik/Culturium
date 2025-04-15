package Entity;

import Utils.DBConnection;
import com.twilio.Twilio;
import com.twilio.rest.api.v2010.account.Message;
import com.twilio.type.PhoneNumber;

import java.sql.Connection;

public class SmsSender {

    Connection cnx;

    public SmsSender() {
        cnx = DBConnection.getInstance().getConnection();
    }

    // twilio.com/console
    public static final String ACCOUNT_SID = "";
    public static final String AUTH_TOKEN = "";

    public static void main(String[] args) {

    }

    public static void sendSMS(String clientPhoneNumber, String s) {

        String accountSid = "AC00b572fc076413d97f90c5481f1f9c23";
        String authToken = "3db1a8cb32090bc839fffee9202a0899";

        try {
            Twilio.init(accountSid, authToken);
            Message message = Message.creator(
                    new PhoneNumber("+216" + clientPhoneNumber),
                    new PhoneNumber("+15177818511"),
                    s
            ).create();

            System.out.println("SID du message : " + message.getSid());
        } catch (Exception ex) {
            System.out.println("Erreur : " + ex.getMessage());
        }
    }
}

