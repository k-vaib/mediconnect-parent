package com.mediconnect.appointment_service.client;

import com.mediconnect.appointment_service.dto.SlotResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = "doctor-service", url = "http://localhost:8082")
public interface DoctorClient {
    @GetMapping("/api/doctors/slots/{slotId}")
    SlotResponse getSlotById(@PathVariable Long slotId);
}
