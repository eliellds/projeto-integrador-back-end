package br.com.rd.projetoVelhoLuxo.service;

import br.com.rd.projetoVelhoLuxo.model.dto.OrderStatusDTO;
import br.com.rd.projetoVelhoLuxo.model.entity.OrderStatus;
import br.com.rd.projetoVelhoLuxo.repository.contract.OrderStatusRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class OrderStatusService {

    @Autowired
    OrderStatusRepository orderStatusRepository;

    public OrderStatusDTO create(OrderStatusDTO newStatus) {
        OrderStatus status = dtoToBusiness(newStatus);
        status = orderStatusRepository.save(status);

        return businessToDto(status);
    }

    public List<OrderStatusDTO> findAll() {
        List<OrderStatus> allList = orderStatusRepository.findAll();
        return listToDto(allList);
    }

    public OrderStatusDTO searchById(Long id) {
        Optional<OrderStatus> option = orderStatusRepository.findById(id);

        if (option.isPresent()) {
            return businessToDto(option.get());
        }
        return null;
    }

    public OrderStatusDTO updateById(OrderStatusDTO dto, Long id) {
        Optional<OrderStatus> option = orderStatusRepository.findById(id);

        if (option.isPresent()) {
            OrderStatus status = option.get();

            if (dto.getId() != null) {
                status = orderStatusRepository.getById(id);
            }

            if (dto.getStatusDescription() != null) {
                status.setStatusDescription(dto.getStatusDescription());
            }

            orderStatusRepository.save(status);
            return businessToDto(status);

        }
        return null;
    }

    public OrderStatusDTO deleteByIdReturningDTO(Long id) {
        OrderStatusDTO dto = searchById(id);

        if (orderStatusRepository.existsById(id)) {
            orderStatusRepository.deleteById(id);
        }
        return dto;
    }

    public List<OrderStatusDTO> listToDto(List<OrderStatus> list) {
        List<OrderStatusDTO> listDto = new ArrayList<>();

        for (OrderStatus business : list) {
            listDto.add(businessToDto(business));
        }
        return listDto;
    }

    public OrderStatus dtoToBusiness(OrderStatusDTO dto) {
        OrderStatus business = new OrderStatus();
        business.setStatusDescription(dto.getStatusDescription());

        return business;
    }

    private OrderStatusDTO businessToDto(OrderStatus business) {
        OrderStatusDTO dto = new OrderStatusDTO();
        dto.setId(business.getId());
        dto.setStatusDescription(business.getStatusDescription());

        return dto;
    }

}
