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

    //constructor,
    //get db connection,
    //load comments from database
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

    //getter for comments
    public ArrayList<String> getComments() {
        return this.comments;
    }

    //clear arraylist of comments => used to avoid comments duplication
    //when user reenters the comment channel view.
    public void clearComment() {
        comments.clear();
    }

    //add new comment to list of comments in model
    //the state will change => observers updated.
    public void addComment(String comment) {
        comments.add(comment);
        setChanged();
        notifyObservers();
    }

    //save logged in user comment to database
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