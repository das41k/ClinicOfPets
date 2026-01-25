package ru.petrosyan.clinicOfPet.dao;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import ru.petrosyan.clinicOfPet.model.Owner;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

@Component
public class OwnerDAO {

    private final DataSource dataSource;

    @Autowired
    public OwnerDAO(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    public List<Owner> getAllOwners() {
        List<Owner> owners = new ArrayList<>();
        try (Connection connection = dataSource.getConnection();
             Statement statement = connection.createStatement();
             ResultSet resultSet = statement.executeQuery("SELECT * from owner");
        ) {
            while (resultSet.next()) {
                Owner owner = new Owner();
                owner.setOwner_id(resultSet.getInt("owner_id"));
                owner.setName(resultSet.getString("name"));
                owner.setPhone(resultSet.getString("phone"));
                owners.add(owner);
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
            e.printStackTrace();
        }
        return owners;
    }
}
