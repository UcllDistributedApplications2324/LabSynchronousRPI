package be.ucll.da.doctorservice.domain;

import be.ucll.da.doctorservice.api.model.ApiDoctor;
import be.ucll.da.doctorservice.api.model.ApiDoctors;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class DoctorService {

    public List<ApiDoctor> getDoctors(String fieldOfExpertise) {
        if (fieldOfExpertise == null || fieldOfExpertise.isEmpty()) {
            throw new NoFieldOfExpertiseException("FieldOfExpertise is empty");
        }

        return getAllDoctors().stream()
                .filter(doctor -> doctor.getFieldOfExpertise().equals(fieldOfExpertise))
                .collect(Collectors.toList());
    }

    private List<ApiDoctor> getAllDoctors() {
        ApiDoctors doctors = new ApiDoctors();
        doctors.add(
                new ApiDoctor()
                        .id(1L)
                        .fieldOfExpertise("Cardiologie")
                        .firstName("Juliette")
                        .lastName("Tucker")
                        .age(32)
                        .address("Rue du Centre 259, 3000 Leuven, Belgium"));
        doctors.add(
                new ApiDoctor()
                        .id(2L)
                        .fieldOfExpertise("Dermatologie")
                        .firstName("Preston")
                        .lastName("Mueller")
                        .age(45)
                        .address("Avenue Emile Vandervelde 465, 3000 Leuven, Belgium"));
        doctors.add(
                new ApiDoctor()
                        .id(1L)
                        .fieldOfExpertise("Gynaecologie")
                        .firstName("Katrina")
                        .lastName("Mendoza")
                        .age(67)
                        .address("Kapelaniestraat 94 22, 3000 Leuven, Belgium"));

        return doctors;
    }
}
