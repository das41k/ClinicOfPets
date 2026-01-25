package ru.petrosyan.clinicOfPet.dao;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import ru.petrosyan.clinicOfPet.model.Owner;

import javax.sql.DataSource;
import java.sql.*;
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

    public Owner getOwnerById(Integer ownerId) {
        Owner owner = null;
        try (Connection connection = dataSource.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement("SELECT * from owner where owner_id = ?");
        ) {
            preparedStatement.setInt(1, ownerId);
            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                if (resultSet.next()) {
                    owner = new Owner();
                    owner.setOwner_id(resultSet.getInt("owner_id"));
                    owner.setName(resultSet.getString("name"));
                    owner.setPhone(resultSet.getString("phone"));
                }
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
            e.printStackTrace();
        }
        return owner;
    }

    public Owner getOwnerByPhone(String phone) {
        Owner owner = null;
        try (Connection connection = dataSource.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement("select * from owner where phone=?")
        ) {
            preparedStatement.setString(1, phone);
            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                if (resultSet.next()) {
                    owner = new Owner();
                    owner.setOwner_id(resultSet.getInt("owner_id"));
                    owner.setName(resultSet.getString("name"));
                    owner.setPhone(resultSet.getString("phone"));
                }
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
            e.printStackTrace();
        }
        return owner;
    }

    public void insertOwner(Owner owner) {
        try (Connection connection = dataSource.getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement("insert into owner (name, phone) values (?, ?)");
        ) {
            preparedStatement.setString(1, owner.getName());
            preparedStatement.setString(2, owner.getPhone());
            int row = preparedStatement.executeUpdate();
            if (row > 0) {
                System.out.println("Insert by owner is success");
            } else {
                System.out.println("Insert by owner is unsuccess");
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
            e.printStackTrace();
        }
    }

    public void updateOwner(Owner owner) {
        try (Connection connection = dataSource.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement("update owner set name=?, phone=? where owner_id = ?");
        ) {
            preparedStatement.setString(1, owner.getName());
            preparedStatement.setString(2, owner.getPhone());
            preparedStatement.setInt(3, owner.getOwner_id());
            int row = preparedStatement.executeUpdate();
            if (row > 0) {
                System.out.println("Update by owner is success");
            } else {
                System.out.println("Updatre by owner is unsuccess");
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
            e.printStackTrace();
        }
    }
}
