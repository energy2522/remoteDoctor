package com.remote.doctor.service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

import javax.print.Doc;

import org.apache.commons.lang3.StringUtils;
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
        return doctors.stream().max(new DoctorPriceComparator()).orElse(new DoctorDto()).getPrice();
    }

    public double getMinPrice(List<DoctorDto> doctors) {
        return doctors.stream().min(new DoctorPriceComparator()).orElse(new DoctorDto()).getPrice();
    }

    public DoctorDto getDoctorById(int id) {
        return conversionService.convert(doctorRepository.findById(id).orElse(null), DoctorDto.class);
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

    @Transactional
    public List<String> updateOldDoctor(DoctorDto doctorDTO) {
        Doctor newClient = conversionService.convert(doctorDTO, Doctor.class);
        Doctor oldClient = doctorRepository.findById(doctorDTO.getId()).get();

        updateOldClient(newClient, oldClient);

        doctorRepository.save(oldClient);

        return Collections.emptyList();//TODO should be rewrite with validation
    }

    private class DoctorPriceComparator implements Comparator<DoctorDto> {

        @Override
        public int compare(DoctorDto a, DoctorDto b) {
            return Double.valueOf(a.getPrice() - b.getPrice()).intValue();
        }
    }

    private void updateOldClient(Doctor newDoctor, Doctor oldDoctor) {
        if (Objects.nonNull(newDoctor.getAvatar())) {
            oldDoctor.setAvatar(newDoctor.getAvatar());
        }

        if (StringUtils.isNotBlank(newDoctor.getPassword())) {
            oldDoctor.setPassword(newDoctor.getPassword());
        }

        oldDoctor.setPrice(newDoctor.getPrice());
        oldDoctor.setDoctorType(getDoctorType(newDoctor.getDoctorType().getName()));
        oldDoctor.setDescription(newDoctor.getDescription());
        oldDoctor.setCity(newDoctor.getCity());
        oldDoctor.setPhoneNumber(newDoctor.getPhoneNumber());
        oldDoctor.setUsername(newDoctor.getUsername());
        oldDoctor.setLastname(newDoctor.getLastname());
        oldDoctor.setFirstname(newDoctor.getFirstname());
    }
}
