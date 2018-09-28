package dbService.executor;

import dbService.dataSets.UsersDataSet;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

public class Executor {
    private final Connection connection;

    public Executor(Connection connection) {
        this.connection = connection;
    }

    public void execUpdate(String update) throws SQLException {
        Statement stmt = connection.createStatement();
        stmt.execute(update);
        stmt.close();
    }

    public ArrayList<UsersDataSet> execQueryList(String query)
            throws SQLException {

        ArrayList<UsersDataSet> tmp = new ArrayList<>();

        Statement stmt = connection.createStatement();
        stmt.execute(query);
        ResultSet result = stmt.getResultSet();

        result.beforeFirst();

        while (result.next() ==true){
            UsersDataSet tmpSet = new UsersDataSet(result.getLong(1), result.getString(2));
            tmp.add(tmpSet);
        }

        result.close();
        stmt.close();

        return tmp;
    }

    public <T> T execQuery(String query,
                           ResultHandler<T> handler)
            throws SQLException {
        Statement stmt = connection.createStatement();
        stmt.execute(query);
        ResultSet result = stmt.getResultSet();
        T value = handler.handle(result);
        result.close();
        stmt.close();

        return value;
    }

}
