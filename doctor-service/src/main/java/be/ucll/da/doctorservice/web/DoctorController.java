package be.ucll.da.doctorservice.web;

import be.ucll.da.doctorservice.api.model.ApiDoctors;
import be.ucll.da.doctorservice.domain.DoctorService;
import be.ucll.da.doctorservice.api.DoctorApiDelegate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

@Component
public class DoctorController implements DoctorApiDelegate {

    @Autowired
    private DoctorService doctorService;

    @Override
    public ResponseEntity<ApiDoctors> getDoctors(String fieldOfExpertise) {
        ApiDoctors doctors = new ApiDoctors();
        doctors.addAll(doctorService.getDoctors(fieldOfExpertise));

        return ResponseEntity.ok(doctors);
    }
}
