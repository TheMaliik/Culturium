package Utils;


/**
 *
 * @author TheMaliik
 */
public final class SessionManager {

    private static SessionManager instance;

    private static int id;
    private static String fullName;
    private static String email;

    private static String tel;
    private static String image;




    //SessionManager.getInstace(rs.getInt("id"),rs.getInt("cin"),rs.getString("user_name"),rs.getInt("numero"),rs.getString("email"),rs.getString("adresse"),rs.getString("roles"));
    private SessionManager(int id , String fullName , String email , String tel ,String image ) {
        SessionManager.id=id;
        SessionManager.fullName=fullName;
        SessionManager.email=email;
        SessionManager.tel=tel;
        SessionManager.image=image;
    }

    /**
     *
     *
     */
    public static SessionManager getInstace(int id , String fullName , String email , String tel ,String image ) {
        if(instance == null) {
            instance = new SessionManager( id , fullName ,  email ,  tel ,  image );
        }
        return instance;
    }

    public static SessionManager getInstance() {
        return instance;
    }

    public static void setInstance(SessionManager instance) {
        SessionManager.instance = instance;
    }

    public static int getId() {
        return id;
    }

    public static void setId(int id) {
        SessionManager.id = id;
    }

    public static String getFullName() {
        return fullName;
    }

    public static void setFullName(String fullName) {
        SessionManager.fullName = fullName;
    }

    public static String getEmail() {
        return email;
    }

    public static void setEmail(String email) {
        SessionManager.email = email;
    }

    public static String getTel() {
        return tel;
    }

    public static void setTel(String tel) {
        SessionManager.tel = tel;
    }

    public static String getImage() {
        return image;
    }

    public static void setImage(String image) {
        SessionManager.image = image;
    }

    public static void cleanUserSession() {
        id=0;
        fullName="";
        email="";
        tel="";
        image="";

    }




    static class cleanUserSession {

        public cleanUserSession() {
            id=0;
            fullName="";
            email="";
            tel="";
            image="";
        }
    }
}