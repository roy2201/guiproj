package Models;

import com.example.mainapp.Database;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Observable;

public class ChannelModel extends Observable {

    private final ArrayList<String> comments;
    private final Connection con;

    public ChannelModel() {
        Database db = null;
        try {
            db = Database.getInstance();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        assert db != null;
        con = db.connect();
        this.comments = db.loadComments();
    }

    public ArrayList<String> getComments() {
        return this.comments;
    }

    public void clearComment() {
        comments.clear();
    }

    public void addComment(String comment) {
        comments.add(comment);
        setChanged();
        notifyObservers();
    }

    public void saveComment(String comment) {
        String query = "exec spComment ?, ?";
        try {
            CallableStatement cst = con.prepareCall(query);
            cst.setInt(1, LoginModel.getLogged());
            cst.setString(2, comment);
            cst.execute();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

}