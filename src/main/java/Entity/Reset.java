package Entity;

/**
 *
 * @author TheMaliik
 */
public class Reset {
    private int id;
    private String tel;
    private int code;
    private  String timeMils;
    User u = new User();
    public Reset(String tel,int code,String timeMils) {
        this.tel = tel;
        this.code = code;
        this.timeMils=timeMils;
    }

    public Reset(int id, String tel, int code) {
        this.id = id;
        this.tel = tel;
        this.code = code;
    }


    public Reset(int code) {
        this.code = code;
    }

    public Reset() {
    }

    public Reset(String tel, int code) {
        this.tel = tel;
        this.code = code;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTel() {
        return tel;
    }

    public void setTel(String email) {
        this.tel = tel;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getTimeMils() {
        return timeMils;
    }

    public void setTimeMils(String timeMils) {
        this.timeMils = timeMils;
    }


}