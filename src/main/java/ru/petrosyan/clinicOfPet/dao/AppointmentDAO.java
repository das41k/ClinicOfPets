package ru.petrosyan.clinicOfPet.dao;

import org.springframework.stereotype.Component;
import ru.petrosyan.clinicOfPet.model.Appointment;
import ru.petrosyan.clinicOfPet.model.AppointmentStatus;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

@Component
public class AppointmentDAO {

    private final DataSource dataSource;
    private final PetDAO petDAO;
    private final VeterinarianDAO veterinarianDAO;

    public AppointmentDAO(DataSource dataSource, PetDAO petDAO, VeterinarianDAO veterinarianDAO) {
        this.dataSource = dataSource;
        this.petDAO = petDAO;
        this.veterinarianDAO = veterinarianDAO;
    }

    public List<Appointment> getAllAppointments() {
        List<Appointment> appointments = new ArrayList<>();
        try (Connection connection = dataSource.getConnection();
             Statement statement = connection.createStatement();
             ResultSet resultSet = statement.executeQuery("SELECT * from appointment order by date_admission, time_admission, veterinarian_id")
        ) {
            while (resultSet.next()) {
                Appointment appointment = new Appointment();
                appointment.setAppointment_id(resultSet.getInt("appointment_id"));
                appointment.setDateAdmission(resultSet.getDate("date_admission").toLocalDate());
                appointment.setTimeAdmission(resultSet.getTime("time_admission").toLocalTime());
                AppointmentStatus status = AppointmentStatus.fromDbValue(resultSet.getString("status"));
                appointment.setStatus(status);
                appointment.setPet(petDAO.getPetById(resultSet.getInt("pet_id")));
                appointment.setVeterinarian(veterinarianDAO.getVeterinarianById(resultSet.getInt("veterinarian_id")));
                appointments.add(appointment);
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
            e.printStackTrace();
        }
        return appointments;
    }
}
