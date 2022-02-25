package Models;

import com.example.mainapp.Database;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.util.Callback;

import java.sql.*;
import java.util.concurrent.atomic.AtomicInteger;

public class ManagerModel extends Employee {

    private Database db;
    private Connection con;

    public ManagerModel() {
        try {
            db = Database.getInstance();
            con = db.connect();
        } catch (SQLException e) {
            e.printStackTrace();

        }
    }

    public ManagerModel(String username, String password) {
        super(username, password);
        try {
            db = Database.getInstance();
            con = db.connect();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public int AddEmployee(Employee e, int adding, int added) {
        CallableStatement cs;
        int errorCode = 0;
        if (adding == 1 && added == 1) {
            String query = "exec dbo.spAdminAddAdmin ?,?,?,?";
            try {
                cs = con.prepareCall(query);
                cs.setInt(1, LoginModel.admin_logged_id);
                cs.setString(2, e.getUsername());
                cs.setString(3, e.getPassword());
                cs.registerOutParameter(4, Types.INTEGER);
                cs.execute();
                errorCode = cs.getInt(4);
                //return errorCode;
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
        } else if (adding == 1 && added == 2) {
            String query = "exec dbo.spAdminAddSecretary ?,?,?,?";
            try {
                cs = con.prepareCall(query);
                cs.setInt(1, LoginModel.admin_logged_id);
                cs.setString(2, e.getUsername());
                cs.setString(3, e.getPassword());
                cs.registerOutParameter(4, Types.INTEGER);
                cs.execute();
                errorCode = cs.getInt(4);
                // return errorCode;
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
        } else if (adding == 1 && added == 3) {
            String query = "exec dbo.spAdminAddDriver ?,?,?,?";
            try {
                cs = con.prepareCall(query);
                cs.setInt(1, LoginModel.admin_logged_id);
                cs.setString(2, e.getUsername());
                cs.setString(3, e.getPassword());
                cs.registerOutParameter(4, Types.INTEGER);
                cs.execute();
                errorCode = cs.getInt(4);
                //return errorCode;
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
        } else if (adding == 2 && added == 2) {
            String query = "exec dbo.spSecretaryAddSecretary ?,?,?,?";
            try {
                cs = con.prepareCall(query);
                cs.setString(1, String.valueOf(LoginModel.secretary_logged_id));
                cs.setString(2, e.getUsername());
                cs.setString(3, e.getPassword());
                cs.registerOutParameter(4, Types.INTEGER);
                cs.execute();
                errorCode = cs.getInt(4);
                //return errorCode;
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
        } else if (adding == 2 && added == 3) {
            String query = "exec dbo.spSecretaryAddDriver ?,?,?,?";
            try {
                cs = con.prepareCall(query);
                cs.setInt(1, LoginModel.secretary_logged_id);
                cs.setString(2, e.getUsername());
                cs.setString(3, e.getPassword());
                cs.registerOutParameter(4, Types.INTEGER);
                cs.execute();
                errorCode = cs.getInt(4);
                // return errorCode;
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
        }
        return errorCode;
    }

    public int RemoveEmployee(String id_to_remove, int removing, int removed) {
        CallableStatement cs;
        AtomicInteger errorCode = new AtomicInteger();
        if (removing == 1 && removed == 1) {
            String query = "exec dbo.spAdminRemoveAdmin ?,?,?";
            try {
                cs = con.prepareCall(query);
                cs.setInt(1, LoginModel.admin_logged_id);
                cs.setString(2, id_to_remove);
                cs.registerOutParameter(3, Types.INTEGER);
                cs.execute();
                errorCode.set(cs.getInt(3));
                //return errorCode;
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
        } else if (removing == 1 && removed == 2) {
            String query = "exec dbo.spAdminRemoveSecretary ?,?,?";
            try {
                cs = con.prepareCall(query);
                cs.setInt(1, LoginModel.admin_logged_id);
                cs.setString(2, id_to_remove);
                cs.registerOutParameter(3, Types.INTEGER);
                cs.execute();
                errorCode.set(cs.getInt(3));
                //return errorCode;
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
        } else if (removing == 1 && removed == 3) {
            String query = "exec dbo.spAdminRemoveDriver ?,?,?";
            try {
                cs = con.prepareCall(query);
                cs.setInt(1, LoginModel.admin_logged_id);
                cs.setString(2, id_to_remove);
                cs.registerOutParameter(3, Types.INTEGER);
                cs.execute();
                errorCode.set(cs.getInt(3));
                //return errorCode;
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
        } else if (removing == 2 && removed == 2) {
            String query = "exec dbo.spSecretaryRemoveSecretary ?,?,?";
            try {
                cs = con.prepareCall(query);
                cs.setInt(1, LoginModel.secretary_logged_id);
                cs.setString(2, id_to_remove);
                cs.registerOutParameter(3, Types.INTEGER);
                cs.execute();
                errorCode.set(cs.getInt(3));
                //return errorCode;
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
        } else if (removing == 2 && removed == 3) {

            String query = "exec dbo.spSecretaryRemoveDriver ?,?,?";
            try {
                cs = con.prepareCall(query);

                cs.setInt(1, LoginModel.secretary_logged_id);
                cs.setString(2, id_to_remove);
                cs.registerOutParameter(3, Types.INTEGER);
                cs.execute();
                errorCode.set(cs.getInt(3));
                //return errorCode;
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
        }
        return errorCode.get();
    }

    private ResultSet rs;

    public void ViewEmployee(TableView tb, int viewing, int viewed) {
        PreparedStatement ps;
        if (viewing == 1 && viewed == 1) {
            String query = "select * from fnAdminShowAdmin(?)";
            try {
                ps = con.prepareStatement(query);
                ps.setInt(1, LoginModel.admin_logged_id);
                rs = ps.executeQuery();
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
        }
        if (viewing == 1 && viewed == 2) {
            String query = "select * from fnAdminShowSecretary(?)";
            try {
                ps = con.prepareStatement(query);
                ps.setInt(1, LoginModel.admin_logged_id);
                rs = ps.executeQuery();
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
        }
        if (viewing == 1 && viewed == 3) {

            String query = "select * from fnAdminShowDriver(?)";
            try {
                ps = con.prepareStatement(query);
                ps.setInt(1, LoginModel.admin_logged_id);
                rs = ps.executeQuery();
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
        }
        if (viewing == 2 && viewed == 2) {
            String query = "select * from fnSecretaryShowSecretary(?)";
            try {
                ps = con.prepareStatement(query);
                ps.setInt(1, LoginModel.secretary_logged_id);
                rs = ps.executeQuery();
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
        }
        if (viewing == 2 && viewed == 3) {

            String query = "select * from fnSecretaryShowDriver(?)";
            try {
                ps = con.prepareStatement(query);
                ps.setInt(1, LoginModel.secretary_logged_id);
                rs = ps.executeQuery();
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
        }
        if (rs != null) {
            try {
                ObservableList<ObservableList> data = FXCollections.observableArrayList();
                for (int i = 0; i < rs.getMetaData().getColumnCount(); i++) {
                    final int j = i;
                    TableColumn col = new TableColumn(rs.getMetaData().getColumnName(i + 1));
                    col.setCellValueFactory((Callback<TableColumn.CellDataFeatures<ObservableList, String>, ObservableValue<String>>) param -> new SimpleStringProperty(param.getValue().get(j).toString()));
                    tb.getColumns().addAll(col);
                    System.out.println("Column [" + i + "] ");
                }
                while (rs.next()) {
                    ObservableList<String> row = FXCollections.observableArrayList();
                    for (int i = 1; i <= rs.getMetaData().getColumnCount(); i++) {
                        row.add(rs.getString(i));
                    }
                    System.out.println("Row [1] added " + row);
                    data.add(row);
                    tb.setItems(data);
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

}
