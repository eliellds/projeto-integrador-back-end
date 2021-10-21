package br.com.rd.projetoVelhoLuxo.service;

import br.com.rd.projetoVelhoLuxo.model.dto.SubjectDTO;
import br.com.rd.projetoVelhoLuxo.model.entity.Subject;
import br.com.rd.projetoVelhoLuxo.repository.contract.SubjectRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class SubjectService {

    @Autowired
    SubjectRepository subjectRepository;

    public SubjectDTO create(SubjectDTO newSubject) {
        Subject subject = dtoToBusiness(newSubject);
        subject = subjectRepository.save(subject);

        return businessToDto(subject);
    }

    public List<SubjectDTO> findAll() {
        List<Subject> allList = subjectRepository.findAll();
        return listToDto(allList);
    }

    public SubjectDTO searchById(Long id) {
        Optional<Subject> option = subjectRepository.findById(id);

        if (option.isPresent()) {
            return businessToDto(option.get());
        }
        return null;
    }

    public SubjectDTO updateById(SubjectDTO dto, Long id) {
        Optional<Subject> option = subjectRepository.findById(id);

        if (option.isPresent()) {
            Subject subject = option.get();

            if (dto.getId() != null) {
                subject = subjectRepository.getById(id);
            }

            if (dto.getSubjectDescription() != null) {
                subject.setSubjectDescription(dto.getSubjectDescription());
            }

            subjectRepository.save(subject);
            return businessToDto(subject);

        }
        return null;
    }

    public SubjectDTO deleteByIdReturningDTO(Long id) {
        SubjectDTO dto = searchById(id);

        if (subjectRepository.existsById(id)) {
            subjectRepository.deleteById(id);
        }
        return dto;
    }

    public List<SubjectDTO> listToDto(List<Subject> list) {
        List<SubjectDTO> listDto = new ArrayList<>();

        for (Subject business : list) {
            listDto.add(businessToDto(business));
        }
        return listDto;
    }

    public Subject dtoToBusiness(SubjectDTO dto) {
        Subject business = new Subject();
        business.setSubjectDescription(dto.getSubjectDescription());

        return business;
    }

    private SubjectDTO businessToDto(Subject business) {
        SubjectDTO dto = new SubjectDTO();
        dto.setId(business.getId());
        dto.setSubjectDescription(business.getSubjectDescription());

        return dto;
    }

}
