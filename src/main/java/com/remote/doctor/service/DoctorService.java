package com.remote.doctor.service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.ConversionService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.remote.doctor.domain.Doctor;
import com.remote.doctor.domain.DoctorType;
import com.remote.doctor.dto.DoctorDto;
import com.remote.doctor.repository.DoctorRepository;
import com.remote.doctor.repository.DoctorTypeRepository;

@Service
public class DoctorService {
    @Autowired
    private ConversionService conversionService;

    @Autowired
    private DoctorRepository doctorRepository;

    @Autowired
    private DoctorTypeRepository doctorTypeRepository;

    public List<DoctorType> getAllTypes() {
        return doctorTypeRepository.findAll();
    }

    @Transactional
    public List<String> signUpDoctor(DoctorDto doctorDTO) {
        DoctorType doctorType = getDoctorType(doctorDTO.getType());

        Doctor doctor = conversionService.convert(doctorDTO, Doctor.class);
        doctor.setDoctorType(doctorType);

        doctorRepository.save(doctor);

        return Collections.emptyList();//TODO should be rewrite with validation
    }

    public List<DoctorDto> getDoctorsByType(String type) {
        List<DoctorDto> doctorDtos = new ArrayList<>();
        List<Doctor> doctors = doctorRepository.getAllByDoctorType_NameOrderByRateDesc(type);

        if (doctors != null) {
            doctorDtos = doctors.stream().map(e -> conversionService.convert(e, DoctorDto.class)).collect(Collectors.toList());
        }

        return doctorDtos;
    }

    public double getMaxPrice(List<DoctorDto> doctors) {
        return doctors.stream().max(new DoctorPriceComparator()).get().getPrice();
    }

    public double getMinPrice(List<DoctorDto> doctors) {
        return doctors.stream().min(new DoctorPriceComparator()).get().getPrice();
    }

    private DoctorType getDoctorType(String type) {
        DoctorType doctorType = doctorTypeRepository.getByName(type);

        if (doctorType != null) {
            return doctorType;
        }

        doctorType = new DoctorType();
        doctorType.setName(type);
        doctorTypeRepository.save(doctorType);

        return doctorType;
    }

    private class DoctorPriceComparator implements Comparator<DoctorDto> {

        @Override
        public int compare(DoctorDto a, DoctorDto b) {
            return Double.valueOf(a.getPrice() - b.getPrice()).intValue();
        }
    }
}
