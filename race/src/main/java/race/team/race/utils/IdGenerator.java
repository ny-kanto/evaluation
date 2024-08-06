package race.team.race.utils;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

@Service
public class IdGenerator {

    @Autowired
    private DataSource dataSource;

    public String generateId(String productIdPrefix, String sequenceName) {
        String productId = "";
        try {
            Connection connection = this.dataSource.getConnection();
            String query = "SELECT nextval(?)";
            PreparedStatement statement = connection.prepareStatement(query);
            statement.setString(1, sequenceName);
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                productId = productIdPrefix + String.format("%04d", resultSet.getInt(1));
            } else {
                throw new SQLException("Impossible de récupérer la prochaine valeur de la séquence.");
            }
            resultSet.close();
            statement.close();
            connection.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return productId;
    }
}
