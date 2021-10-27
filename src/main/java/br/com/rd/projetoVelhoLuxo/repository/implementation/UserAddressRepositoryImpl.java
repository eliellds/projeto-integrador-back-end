package br.com.rd.projetoVelhoLuxo.repository.implementation;

import br.com.rd.projetoVelhoLuxo.model.entity.UserAddress;
import br.com.rd.projetoVelhoLuxo.repository.custom.UserAddressRepositoryCustom;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import java.util.List;

@Repository
public class UserAddressRepositoryImpl implements UserAddressRepositoryCustom {
    @PersistenceContext
    EntityManager em;
    @Override
    public List<UserAddress> findAllByUser(Long id) {
        Query sql = em.createNativeQuery("SELECT * FROM endereco_usuario WHERE cl_id_usuario = ?", UserAddress.class);

        sql.setParameter(1, id);
        List<UserAddress> list = sql.getResultList();

        return list;

    }
}
