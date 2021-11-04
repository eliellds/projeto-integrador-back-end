package br.com.rd.projetoVelhoLuxo.service;

import br.com.rd.projetoVelhoLuxo.model.dto.AddressDTO;
import br.com.rd.projetoVelhoLuxo.model.dto.StoreDTO;
import br.com.rd.projetoVelhoLuxo.model.dto.TelephoneDTO;
import br.com.rd.projetoVelhoLuxo.model.entity.Address;
import br.com.rd.projetoVelhoLuxo.model.entity.Store;
import br.com.rd.projetoVelhoLuxo.model.entity.Telephone;
import br.com.rd.projetoVelhoLuxo.repository.contract.AddressRepository;
import br.com.rd.projetoVelhoLuxo.repository.contract.StoreRepository;
import br.com.rd.projetoVelhoLuxo.repository.contract.TelephoneRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class StoreService {

    @Autowired
    StoreRepository storeRepository;

    @Autowired
    AddressRepository addressRepository;

    @Autowired
    TelephoneRepository telephoneRepository;

//conversao dto to business Loja
    private Store dtoToBusiness(StoreDTO dto) {
        Store business = new Store();

        business.setId(dto.getId());
        business.setCnpj(dto.getCnpj());
        business.setEmail(dto.getEmail());
        business.setName(dto.getName());
        business.setStateRegistration(dto.getStateRegistration());
        if (dto.getTelephoneID() != null) {
            Telephone telephone = new Telephone();

            telephone.setId(dto.getTelephoneID().getId());
            telephone.setNumber(dto.getTelephoneID().getNumber());

            business.setTelephoneID(telephone);
        }
        if (dto.getAddressID() != null) {
            Address address = new Address();

            address.setId(dto.getAddressID().getId());
            address.setStreet(dto.getAddressID().getStreet());
            address.setNumber(dto.getAddressID().getNumber());
            address.setComplement(dto.getAddressID().getComplement());
            address.setCity(dto.getAddressID().getCity());
            address.setDistrict(dto.getAddressID().getDistrict());
            address.setState(dto.getAddressID().getState());
            address.setReference(dto.getAddressID().getReference());
            address.setCep(dto.getAddressID().getCep());

            business.setAddressID(address);
        }

        return business;
    }

//    Business to dto Loja
    private StoreDTO businessToDto(Store business) {
        StoreDTO dto = new StoreDTO();

        dto.setId(business.getId());
        dto.setName(business.getName());
        dto.setCnpj(business.getCnpj());
        dto.setEmail(business.getEmail());
        dto.setStateRegistration(business.getStateRegistration());
        if (business.getTelephoneID() != null) {
            TelephoneDTO telephone = new TelephoneDTO();

            telephone.setId(business.getTelephoneID().getId());
            telephone.setNumber(business.getTelephoneID().getNumber());

            dto.setTelephoneID(telephone);
        }
        if (business.getAddressID() != null) {
            AddressDTO address = new AddressDTO();

            address.setId(business.getAddressID().getId());
            address.setStreet(business.getAddressID().getStreet());
            address.setNumber(business.getAddressID().getNumber());
            address.setComplement(business.getAddressID().getComplement());
            address.setCity(business.getAddressID().getCity());
            address.setDistrict(business.getAddressID().getDistrict());
            address.setState(business.getAddressID().getState());
            address.setReference(business.getAddressID().getReference());
            address.setCep(business.getAddressID().getCep());

            dto.setAddressID(address);
        }

        return dto;
    }

    private List<StoreDTO> listToDto(List<Store> list){
        List<StoreDTO> listDto = new ArrayList<StoreDTO>();
        for (Store s: list){
            listDto.add(this.businessToDto(s));
        }
        return listDto;
    }

    public StoreDTO createStore(StoreDTO store){
        Store newStore = this.dtoToBusiness(store);

        if (newStore.getTelephoneID() != null) {
            Long id = newStore.getTelephoneID().getId();
            Telephone telephone;

            if (id != null) {
                telephone = this.telephoneRepository.getById(id);
            } else {
                telephone = this.telephoneRepository.save(newStore.getTelephoneID());
            }
            newStore.setTelephoneID(telephone);
        }
        if (newStore.getAddressID() != null) {
            Long id = newStore.getAddressID().getId();
            Address address;

            if (id != null) {
                address = this.addressRepository.getById(id);
            } else {
                address = this.addressRepository.save(newStore.getAddressID());
            }
            newStore.setAddressID(address);
        }

        newStore = storeRepository.save(newStore);
        return this.businessToDto(newStore);
    }

    public List<StoreDTO> findAllStores(){
        List<Store> allList = storeRepository.findAll();
        return this.listToDto(allList);
    }

    public StoreDTO searchById(Long id) {
        Optional<Store> option = storeRepository.findById(id);

        if (option.isPresent()) {
            return businessToDto(option.get());
        }
        return null;
    }

    public StoreDTO updateStore(StoreDTO dto, Long id) {
        Optional<Store> op = storeRepository.findById(id);

        if (op.isPresent()) {
            Store obj = op.get();

            if (dto.getCnpj() != null) {
                obj.setCnpj(dto.getCnpj());
            }
            if (dto.getName() != null) {
                obj.setName(dto.getName());
            }
            if (dto.getEmail() != null) {
                obj.setEmail(dto.getEmail());
            }
            if (dto.getStateRegistration() != null) {
                obj.setStateRegistration(dto.getStateRegistration());
            }
            if (dto.getTelephoneID() != null) {
                if (dto.getTelephoneID().getId() != null) {
                    if (telephoneRepository.existsById(obj.getTelephoneID().getId())) {
                        obj.setTelephoneID(telephoneRepository.getById(dto.getTelephoneID().getId()));
                    } else {
                        obj.getTelephoneID().setNumber(dto.getTelephoneID().getNumber());
                        obj.setTelephoneID(telephoneRepository.save(obj.getTelephoneID()));
                    }
                } else {
                    obj.getTelephoneID().setNumber(dto.getTelephoneID().getNumber());
                    obj.setTelephoneID(telephoneRepository.save(obj.getTelephoneID()));
                }
            }
            if (dto.getAddressID() != null) {
                if (dto.getAddressID().getId() != null) {
                    if (addressRepository.existsById(obj.getAddressID().getId())) {
                        obj.setAddressID(addressRepository.getById(dto.getAddressID().getId()));
                    } else {
                        obj.getAddressID().setStreet(dto.getAddressID().getStreet());
                        obj.getAddressID().setNumber(dto.getAddressID().getNumber());
                        obj.getAddressID().setReference(dto.getAddressID().getReference());
                        obj.getAddressID().setComplement(dto.getAddressID().getComplement());
                        obj.getAddressID().setCity(dto.getAddressID().getCity());
                        obj.getAddressID().setDistrict(dto.getAddressID().getDistrict());
                        obj.getAddressID().setState(dto.getAddressID().getState());
                        obj.getAddressID().setCep(dto.getAddressID().getCep());

                        obj.setAddressID(addressRepository.save(obj.getAddressID()));
                    }
                } else {
                    obj.getAddressID().setStreet(dto.getAddressID().getStreet());
                    obj.getAddressID().setNumber(dto.getAddressID().getNumber());
                    obj.getAddressID().setReference(dto.getAddressID().getReference());
                    obj.getAddressID().setComplement(dto.getAddressID().getComplement());
                    obj.getAddressID().setCity(dto.getAddressID().getCity());
                    obj.getAddressID().setDistrict(dto.getAddressID().getDistrict());
                    obj.getAddressID().setState(dto.getAddressID().getState());
                    obj.getAddressID().setCep(dto.getAddressID().getCep());
                    obj.setAddressID(addressRepository.save(obj.getAddressID()));
                }
            }
                storeRepository.save(obj);
                return businessToDto(obj);
            }
            return null;
        }

        public void deleteStore(Long id){
            if (storeRepository.existsById(id)) {
                storeRepository.deleteById(id);
            }
        }
    }

