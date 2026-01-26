package ru.petrosyan.clinicOfPet.dao;

import org.springframework.stereotype.Component;
import ru.petrosyan.clinicOfPet.model.Pet;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

@Component
public class PetDAO {
    private final DataSource dataSource;

    public PetDAO(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    public List<Pet> getAllPets() {

        List<Pet> pets = new ArrayList<>();
        try (Connection connection = dataSource.getConnection();
             Statement statement = connection.createStatement();
             ResultSet resultSet = statement.executeQuery("SELECT * from pet");
        ) {
            while (resultSet.next()) {
                Pet pet = new Pet();
                pet.setPet_id(resultSet.getInt("pet_id"));
                pet.setName(resultSet.getString("name"));
                pet.setDateBirth(resultSet.getDate("date_birth").toLocalDate());
                pet.setOwner_id(resultSet.getInt("owner_id"));
                pet.setPetType_id(resultSet.getInt("pet_type_id"));
                pets.add(pet);
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
            e.printStackTrace();
        }
        return pets;
    }

}
