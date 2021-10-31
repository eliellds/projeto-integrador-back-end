package br.com.rd.projetoVelhoLuxo.service;

import br.com.rd.projetoVelhoLuxo.model.dto.PaymentMethodsDTO;
import br.com.rd.projetoVelhoLuxo.model.entity.PaymentMethods;
import br.com.rd.projetoVelhoLuxo.repository.contract.PaymentMethodsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class PaymentMethodsService {

    @Autowired
    PaymentMethodsRepository paymentMethodsRepository;

//    Conversão  dto para business (método de pagamento)
    private PaymentMethods dtoToBusiness(PaymentMethodsDTO dto) {
        PaymentMethods business = new PaymentMethods();
        business.setId(dto.getId());
        business.setDescription(dto.getDescription());
        business.setInstallments(dto.getInstallments());

        return business;
    }

//    Conversão Business para DTO ( método de pagamento)
    private PaymentMethodsDTO businessToDto(PaymentMethods business) {
        PaymentMethodsDTO dto = new PaymentMethodsDTO();
        dto.setId(business.getId());
        dto.setDescription(business.getDescription());
        dto.setInstallments(business.getInstallments());

        return dto;
    }

    private List<PaymentMethodsDTO> listToDto(List<PaymentMethods> list){
        List<PaymentMethodsDTO> listDto = new ArrayList<PaymentMethodsDTO>();
        for (PaymentMethods p: list){
            listDto.add(this.businessToDto(p));
        }
        return listDto;
    }

//    Criar método de pagamento
    public PaymentMethodsDTO createPaymentMethod(PaymentMethodsDTO paymentMethod){
        PaymentMethods newPaymentMethod = this.dtoToBusiness(paymentMethod);
        newPaymentMethod = paymentMethodsRepository.save(newPaymentMethod);
        return this.businessToDto(newPaymentMethod);
    }

//    Enocntrar todas as formas de pagamento
    public List<PaymentMethodsDTO> findAllPaymentMethods(){
        List<PaymentMethods> allList = paymentMethodsRepository.findAll();
        return this.listToDto(allList);
    }

//    Buscar a forma de pagamento por ID
    public PaymentMethodsDTO searchById(Long id) {
        Optional<PaymentMethods> option = paymentMethodsRepository.findById(id);

        if (option.isPresent()) {
            return businessToDto(option.get());
        }
        return null;
    }

//    Atualizar a forma de pagamento
    public PaymentMethodsDTO updatePaymentMethod(PaymentMethodsDTO dto, Long id) {
        Optional<PaymentMethods> op = paymentMethodsRepository.findById(id);

        if(op.isPresent()){
            PaymentMethods obj = op.get();

            if (dto.getDescription() != null){
                obj.setDescription(dto.getDescription());
            }
            if (dto.getInstallments() != null) {
                obj.setInstallments(dto.getInstallments());
            }

            paymentMethodsRepository.save(obj);
            return businessToDto(obj);
        }
        return null;
    }

//    Deletar a forma de pagamento
    public void deletePaymentMethod(Long id) {
        if (paymentMethodsRepository.existsById(id)){
            paymentMethodsRepository.deleteById(id);
        }
    }
}
