package be.ucll.da.appointmentservice.domain;

import be.ucll.da.appointmentservice.api.model.ApiAppointment;
import be.ucll.da.appointmentservice.client.doctor.api.DoctorApi;
import be.ucll.da.appointmentservice.client.doctor.api.model.ApiDoctor;
import com.netflix.appinfo.InstanceInfo;
import com.netflix.discovery.EurekaClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.client.circuitbreaker.CircuitBreakerFactory;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class AppointmentService {

    @Autowired
    private AppointmentRepository repository;

    @Autowired
    private DoctorApi doctorApi;

    @Autowired
    private CircuitBreakerFactory circuitBreakerFactory;

    @Autowired
    private EurekaClient discoveryClient;


    public void createAppointment(ApiAppointment data) {
        InstanceInfo instance = discoveryClient.getNextServerFromEureka("doctor-service", false);
        doctorApi.getApiClient().setBasePath(instance.getHomePageUrl());

        List<ApiDoctor> doctors = circuitBreakerFactory.create("doctorApi")
                .run(() ->  doctorApi.getDoctors(data.getNeededExpertise()), throwable -> new ArrayList<>());

        ApiDoctor selectedDoctor = null;
        for (ApiDoctor doctor : doctors) {
            List<Appointment> appointments = repository.getAppointmentByDoctorAndPreferredDay(doctor.getId(), data.getPreferredDay());

            if (appointments.isEmpty()) {
                selectedDoctor = doctor;
                break;
            }
        }

        if (selectedDoctor == null) {
            throw new AppointmentException("No doctor available for day and expertise");
        }

        Appointment appointment = new Appointment(
                data.getNeededExpertise(),
                data.getPatientFirstName(),
                data.getPatientLastName(),
                data.getPreferredDay(),
                selectedDoctor.getId());

        repository.save(appointment);
    }
}
