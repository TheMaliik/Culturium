package Services;

import Entity.Reset;
import Utils.DBConnection;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 *
 * @author TheMaliik
 */



public class imResetPassword {

    public Connection conx;

    public imResetPassword() {
        conx = DBConnection.getInstance().getConnection();

    }

    // public boolean ajout(Reset t) {
    //        try {
    //            String req = "SELECT * from user where email=?";
    //            PreparedStatement pst = conx.prepareStatement(req);
    //            pst.setString(1, t.getEmail());
    //            ResultSet rs = pst.executeQuery();
    //            if (rs.next()) {
    //                String reqs = "INSERT INTO reset(email,code,timeMils)VALUES(?,?,?)";
    //                PreparedStatement psts = conx.prepareStatement(reqs);
    //                psts.setString(1, t.getEmail());
    //                psts.setInt(2, t.getCode());
    //                psts.setString(3, t.getTimeMils());
    //                psts.executeUpdate();
    //                return true;
    //            } else {
    //                return false;
    //            }
    //
    //        } catch (SQLException e) {
    //
    //            System.out.println("Error inserting reset data:");
    //            System.out.println(e.getMessage());
    //            e.printStackTrace();
    //
    //        }
    //        return true;
//    public boolean ajout(Reset t) {
//        try {
//            String req = "SELECT * from user where email=?";
//            PreparedStatement pst = conx.prepareStatement(req);
//            pst.setString(1, t.getEmail());
//            ResultSet rs = pst.executeQuery();
//            if (rs.next()) {
//                String reqs = "INSERT INTO reset(email,code,timeMils)VALUES(?,?,?)";
//                PreparedStatement psts = conx.prepareStatement(reqs);
//                psts.setString(1, t.getEmail());
//                psts.setInt(2, t.getCode());
//                psts.setString(3, t.getTimeMils());
//                psts.executeUpdate();
//
//                return true;
//            } else {
//                return false;
//            }
//        } catch (SQLException e) {
//            System.out.println("Error inserting reset data:");
//            System.out.println(e.getMessage());
//            e.printStackTrace();
//        }
//        return true;
//    }

    public boolean ajout(Reset t) {
        try {
            String req = "SELECT * from user where tel=?";
            PreparedStatement pst = conx.prepareStatement(req);
            pst.setString(1, t.getTel());
            ResultSet rs = pst.executeQuery();
            if (rs.next()) {
                String reqs = "INSERT INTO reset(tel, code, timeMils) VALUES (?, ?, ?)";
                PreparedStatement psts = conx.prepareStatement(reqs);
                psts.setString(1, t.getTel());
                psts.setInt(2, t.getCode());
                psts.setString(3, t.getTimeMils());
                psts.executeUpdate();

                return true;
            } else {
                return false;
            }
        } catch (SQLException e) {
            System.out.println("Error inserting reset data:");
            System.out.println(e.getMessage());
            e.printStackTrace();
        }
        return false;
    }

}


