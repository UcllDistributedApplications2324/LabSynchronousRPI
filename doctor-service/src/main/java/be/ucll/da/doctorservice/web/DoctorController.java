package be.ucll.da.doctorservice.web;

import be.ucll.da.doctorservice.domain.DoctorService;
import be.ucll.da.doctorservice.api.DoctorApiDelegate;
import be.ucll.da.doctorservice.model.Doctors;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

@Component
public class DoctorController implements DoctorApiDelegate {

    @Autowired
    private DoctorService doctorService;

    @Override
    public ResponseEntity<Doctors> getDoctors(String fieldOfExpertise) {
        Doctors doctors = new Doctors();
        doctors.addAll(doctorService.getDoctors(fieldOfExpertise));

        return ResponseEntity.ok(doctors);
    }
}
