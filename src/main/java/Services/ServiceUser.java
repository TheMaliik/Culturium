package Services;

import Entity.Reset;
import Entity.User;
import Securite.BCrypt;
import Utils.DBConnection;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;


/*
@author TheMaliik
 */


public class ServiceUser implements IUser<User> {

    public static int idUser;
    public static String FullName;
    public static String EMAIL;

    public static String email;

    /*public static String MDP;*/

    public static String TEL;
    public static String IMAGE;
    static Connection connection;

    public ServiceUser() {
        connection = DBConnection.getInstance().getConnection();

    }

    public static List<User> afficher() throws SQLException {
        List<User> clients = new ArrayList<>();
        String req = "select * from user";
        Statement st = connection.createStatement();
        ResultSet rs = st.executeQuery(req);
        while (rs.next()) {
            User p = new User();
            p.setId(rs.getInt(1));
            p.setFullName(rs.getString("FullName"));
            p.setEmail(rs.getString("EMAIL"));
            p.setMdp(rs.getString("MDP"));
            p.setTel(rs.getString("TEL"));
            p.setIs_approved(Boolean.parseBoolean(String.valueOf(rs.getBoolean("is_approved"))));
            p.setIs_blocked(Boolean.parseBoolean(String.valueOf(rs.getBoolean("is_blocked"))));
            clients.add(p);
        }
        return clients;
    }


    @Override
    public void ajouter(User client) throws SQLException {
        String hashed = BCrypt.hashpw(client.getMdp(), BCrypt.gensalt());
        String req = "INSERT INTO user (FullName,EMAIL,MDP,TEL,IMAGE) VALUES(?,?,?,?,?)";
        PreparedStatement stmt = connection.prepareStatement(req);
        stmt.setString(1, client.getFullName());
        stmt.setString(2, client.getEmail());
        stmt.setString(3, client.getMdp());
        stmt.setString(4, client.getTel());
        stmt.setString(5, client.getImage());
        int result = stmt.executeUpdate();
        System.out.println(result + " enregistrement ajouté.");

    }


    public void modifier(User client) throws SQLException {
        String req = "UPDATE user SET FullName=?, EMAIL=?, MDP=?, TEL=?, IMAGE=? WHERE ID=?";
        PreparedStatement stmt = connection.prepareStatement(req);
        stmt.setString(1, client.getFullName());
        stmt.setString(2, client.getEmail());
        stmt.setString(3, client.getMdp());
        stmt.setString(4, client.getTel());
        stmt.setString(5, client.getImage());
        stmt.setInt(6, client.getId());

        int result = stmt.executeUpdate();
        System.out.println(result + " modifié avec succès !");
    }


    public void supprimer(User client) throws SQLException {
        String req = "DELETE FROM user WHERE ID=" + client.getId() + "";
        try {
            PreparedStatement stmt = connection.prepareStatement(req);
            stmt.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }




    public boolean existemail(String email) throws SQLException {
        boolean exist = false;
        String query = "SELECT * FROM user WHERE email = ?";
        PreparedStatement ps = connection.prepareStatement(query);
        ps.setString(1, email);
        ResultSet rs = ps.executeQuery();
        if (rs.next()) {
            exist = true;

        }
        return exist;
    }


    /*public List<User> triEmail() {

        List<User> list1 = new ArrayList<>();
        List<User> list2 = null;
        try {
            list2 = afficher();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        list1 = list2.stream().sorted((o1, o2) -> o1.getEmail().compareTo(o2.getEmail())).collect(Collectors.toList());
        return list1;

    }*/




    public List<User> Search(String searchTerm) throws SQLException {
        List<User> resultList = new ArrayList<>();
        List<User> allUsers = afficher();

        resultList = allUsers.stream()
                .filter(user -> user.getFullName().startsWith(searchTerm) || user.getEmail().startsWith(searchTerm) || user.getTel().startsWith(searchTerm))
                .collect(Collectors.toList());

        return resultList;
    }




    public User findById(int id) throws SQLException {
        User u = new User();
        try {
            String sql = "SELECT * FROM user WHERE id = " + id;
            Statement ste1 = connection.createStatement();
            ResultSet rs = ste1.executeQuery(sql);
            while (rs.next()) {
                u.setId(rs.getInt("id"));
                u.setFullName(rs.getString("FullName"));
                u.setEmail(rs.getString("EMAIL"));
                u.setMdp(rs.getString("MDP"));
                u.setTel(rs.getString("TEL"));
                u.setImage(rs.getString("IMAGE"));
                u.setIs_blocked(Boolean.parseBoolean(String.valueOf(rs.getBoolean("is_blocked"))));
                u.setIs_approved(Boolean.parseBoolean(String.valueOf(rs.getBoolean("is_approved"))));


            }

        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
        return u;
    }






    public void ban(User u) throws SQLException {

        String req = "UPDATE user SET is_blocked = 1 , is_approved = 0 where id = ?";
        PreparedStatement ps = connection.prepareStatement(req);
        ps.setInt(1, u.getId());
        ps.executeUpdate();

    }



    public void unban(User u) throws SQLException {

        String req = "UPDATE user SET is_blocked = 0 , is_approved = 1 where id = ?";
        PreparedStatement ps = connection.prepareStatement(req);
        ps.setInt(1, u.getId());
        ps.executeUpdate();

    }




    public static String login(User t) {
        String message = "";

        try {
            if (!t.getEmail().equals("") && !t.getMdp().equals("")) {
                String req = "SELECT * FROM user WHERE email = ?";
                PreparedStatement pst = connection.prepareStatement(req);
                pst.setString(1, t.getEmail());
                ResultSet rs = pst.executeQuery();
                if (rs.next()) {
                    // L'email existe dans la base de données, vérifiez le mot de passe
                    if (BCrypt.checkpw(t.getMdp(), rs.getString("mdp"))) {
                        if (!rs.getBoolean("is_blocked")) {
                            idUser = rs.getInt("id");
                            FullName = rs.getString("fullName");
                            EMAIL = rs.getString("email");
                            TEL = rs.getString("tel");
                            IMAGE = rs.getString("image");
                            System.out.println(" Salut :" + FullName);
                        } else {
                            return message = "Compte désactivé ! Veuillez contacter l'administrateur.";
                        }
                    } else {
                        return message = "Mot de passe incorrect.";
                    }
                } else {
                    return message = "Email non trouvé.";
                }
            } else {
                return message = "Champs vide.";
            }
        } catch (SQLException e) {
            System.err.println(e.getMessage());
        }
        return message;
    }



    public void ModifMDP(String email, String mdp) throws SQLException{
        String hashed = BCrypt.hashpw(mdp, BCrypt.gensalt());
        String req = "UPDATE user SET mdp=? WHERE email=?";
        PreparedStatement stmt = connection.prepareStatement(req);
        stmt.setString(1, hashed); // Utilise le mot de passe haché
        stmt.setString(2, email);
        stmt.executeUpdate();
    }







    public boolean reset(Reset t) {
        long end = System.currentTimeMillis();
        try {
            String req = "SELECT * from reset where code=?";
            PreparedStatement pst = connection.prepareStatement(req);
            pst.setInt(1, t.getCode());
            ResultSet rs = pst.executeQuery();
            if (rs.next()) {
                long StartTime = Long.parseLong(rs.getString("timeMils"));
                long calT = end - StartTime;
                if (calT < 120000) {
                    email = rs.getString("email");
                    return true;
                } else {
                    String reqs = "DELETE FROM reset WHERE code=?";
                    PreparedStatement psts = connection.prepareStatement(reqs);
                    psts.setInt(1, t.getCode());
                    psts.executeUpdate();
                    System.err.println("Time OUT !! Code Introuvable.");
                    return false;
                }
            } else {
                System.err.println("Code Incorrect !");
                return false;
            }

        } catch (SQLException e) {

            System.out.println(e.getMessage());
            return false;

        }
    }




    public List<User> triEmail() throws SQLException {

        List<User> list1 = new ArrayList<>();
        List<User> list2 = afficher();

        list1 = list2.stream().sorted((o1, o2) -> o1.getEmail().compareTo(o2.getEmail())).collect(Collectors.toList());
        return list1;

    }


    public List<User> triFullName() throws SQLException {

        List<User> list1 = new ArrayList<>();
        List<User> list2 = afficher();

        list1 = list2.stream().sorted((o1, o2) -> o1.getFullName().compareTo(o2.getFullName())).collect(Collectors.toList());
        return list1;

    }




}













