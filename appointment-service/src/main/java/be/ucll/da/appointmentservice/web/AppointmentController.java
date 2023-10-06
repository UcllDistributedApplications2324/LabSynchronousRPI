package be.ucll.da.appointmentservice.web;

import be.ucll.da.appointmentservice.api.AppointmentApiDelegate;
import be.ucll.da.appointmentservice.api.model.ApiAppointment;
import be.ucll.da.appointmentservice.domain.AppointmentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

@Component
public class AppointmentController implements AppointmentApiDelegate {

    @Autowired
    private AppointmentService appointmentService;

    @Override
    public ResponseEntity<Void> createAppointment(ApiAppointment data) {
        appointmentService.createAppointment(data);
        return ResponseEntity.ok().build();
    }
}
