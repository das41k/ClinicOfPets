package ru.petrosyan.clinicOfPet.dao;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import ru.petrosyan.clinicOfPet.model.Owner;
import ru.petrosyan.clinicOfPet.model.Pet;

import javax.sql.DataSource;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

@Component
public class PetDAO {
    private final DataSource dataSource;
    private final OwnerDAO ownerDAO;

    @Autowired
    public PetDAO(DataSource dataSource, OwnerDAO ownerDAO) {
        this.dataSource = dataSource;
        this.ownerDAO = ownerDAO;
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
                Owner owner = ownerDAO.getOwnerById(resultSet.getInt("owner_id"));
                pet.setOwner(owner);
                String petType = getPetTypeById(resultSet.getInt("pet_type_id"));
                pet.setPetType(petType);
                pets.add(pet);
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
            e.printStackTrace();
        }
        return pets;
    }

    public String getPetTypeById(Integer id) {
        String petType = null;
        try (Connection connection = dataSource.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement("SELECT * from pet_type where pet_type_id = ?");
        ) {
            preparedStatement.setInt(1, id);
            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                if (resultSet.next()) {
                    petType = resultSet.getString("name");
                }
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
            e.printStackTrace();
        }
        return petType;
    }

    public Pet getPetById(Integer petId) {
        Pet pet = null;

        try (Connection connection = dataSource.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement("SELECT * from pet where pet_id = ?");
        ) {
            preparedStatement.setInt(1, petId);
            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                if (resultSet.next()) {
                    pet = new Pet();
                    pet.setPet_id(resultSet.getInt("pet_id"));
                    pet.setName(resultSet.getString("name"));
                    pet.setDateBirth(resultSet.getDate("date_birth").toLocalDate());
                    Owner owner = ownerDAO.getOwnerById(resultSet.getInt("owner_id"));
                    pet.setOwner(owner);
                    String petType = getPetTypeById(resultSet.getInt("pet_type_id"));
                    pet.setPetType(petType);
                }
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
            e.printStackTrace();
        }
        return pet;
    }

}
