package ru.petrosyan.clinicOfPet.dao;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import ru.petrosyan.clinicOfPet.model.Veterinarian;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

@Component
public class VeterinarianDAO {
    private final DataSource dataSource;

    @Autowired
    public VeterinarianDAO(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    public List<Veterinarian> getAllVeterinarians() {
        final String SQL = "SELECT * FROM veterinarian;";
        List<Veterinarian> veterinarians = new ArrayList<>();
        try(Connection connection = dataSource.getConnection();
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery(SQL);
        ) {
            while (resultSet.next()) {
                Veterinarian veterinarian = new Veterinarian();
                veterinarian.setVeterinarian_id(resultSet.getInt("veterinarian_id"));
                veterinarian.setName(resultSet.getString("name"));
                veterinarian.setPhone(resultSet.getString("phone"));
                veterinarians.add(veterinarian);
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
            e.printStackTrace();
        }
        return veterinarians;
    }
}
