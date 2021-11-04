package br.com.rd.projetoVelhoLuxo.controller;

import br.com.rd.projetoVelhoLuxo.model.dto.UserAddressDTO;
import br.com.rd.projetoVelhoLuxo.model.dto.UserAddressViewDTO;
import br.com.rd.projetoVelhoLuxo.model.entity.UserAddress;
import br.com.rd.projetoVelhoLuxo.service.UserAddressService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/userAddress")
public class UserAddressController {
    @Autowired
    UserAddressService service;
    @PostMapping
    public UserAddressViewDTO linkedUAddress(@RequestBody UserAddressDTO toLink) {
        return service.linkUAddress(toLink);

    }
    @GetMapping("/myAddress/{id}")
    public List<UserAddressViewDTO> getAll(@PathVariable ("id") Long id){

        return service.findAllByUser(id);
    }
}
