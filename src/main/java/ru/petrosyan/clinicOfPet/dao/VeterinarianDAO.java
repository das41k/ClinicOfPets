package ru.petrosyan.clinicOfPet.dao;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import ru.petrosyan.clinicOfPet.model.Veterinarian;

import javax.sql.DataSource;
import java.sql.*;
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

    public Veterinarian getVeterinarianById(Integer veterinarianId) {
        Veterinarian veterinarian= null;
        try(Connection connection = dataSource.getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement("SELECT * from veterinarian where veterinarian_id = ?");
        ) {
            preparedStatement.setInt(1, veterinarianId);
            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                if (resultSet.next()) {
                    veterinarian = new Veterinarian();
                    veterinarian.setVeterinarian_id(resultSet.getInt("veterinarian_id"));
                    veterinarian.setName(resultSet.getString("name"));
                    veterinarian.setPhone(resultSet.getString("phone"));
                }
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
            e.printStackTrace();
        }
        return veterinarian;
    }

    public Veterinarian getVeterinarianByPhone(String phone) {
        Veterinarian veterinarian= null;
        try(Connection connection = dataSource.getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement("SELECT * from veterinarian where phone = ?");
        ) {
            preparedStatement.setString(1, phone);
            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                if (resultSet.next()) {
                    veterinarian = new Veterinarian();
                    veterinarian.setVeterinarian_id(resultSet.getInt("veterinarian_id"));
                    veterinarian.setName(resultSet.getString("name"));
                    veterinarian.setPhone(resultSet.getString("phone"));
                }
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
            e.printStackTrace();
        }
        return veterinarian;
    }

    public void insertVeterinarian(Veterinarian veterinarian) {
        try (Connection connection = dataSource.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement("insert into veterinarian (name, phone) values (?, ?)");
        ) {
            preparedStatement.setString(1, veterinarian.getName());
            preparedStatement.setString(2, veterinarian.getPhone());
            int row = preparedStatement.executeUpdate();
            if (row > 0) {
                System.out.println("Insert by veterinarian is success");
            } else {
                System.out.println("Insert by veterinarian is unsuccess");
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
            e.printStackTrace();
        }
    }
}
