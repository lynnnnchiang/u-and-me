package tw.idv.cha102.g7.customer.service.Impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import tw.idv.cha102.g7.customer.entity.CustomerQa;
import tw.idv.cha102.g7.customer.repo.CustomerQaRepository;
import tw.idv.cha102.g7.customer.service.CustomerQaService;

import java.util.List;

@Component
public class CustomerQaServiceImpl implements CustomerQaService {
    @Autowired
    private CustomerQaRepository repository;

    @Override
    public List<CustomerQa> findAllQA() {
        return repository.findAll();
    }

    @Override
    public List<CustomerQa> findByQaState(Integer qaState) {
        return repository.findByQaState(qaState);
    }

    @Override
    public CustomerQa findByQaId(Integer qaId) {
        CustomerQa customerQa = repository.findById(qaId).orElse(null);
        return customerQa;
    }

    @Override
    public void addQa(CustomerQa customerQa) {
        repository.save(customerQa);
    }

    @Override
    public String updateById(Integer qaId, CustomerQa customerQa) {
        CustomerQa qa = repository.findById(qaId).orElse(null);

        if (qa != null) {
            qa.setQaTitle(customerQa.getQaTitle());
            qa.setQaCon(customerQa.getQaCon());
            qa.setQaState(customerQa.getQaState());
            qa.setQaLastUpdatedTime(customerQa.getQaLastUpdatedTime());
            repository.save(qa);
            return "更新成功";
        } else {
            return "更新失敗，不存在此QA";
        }
    }

    @Override
    public void deleteById(Integer qaId) {
        repository.deleteById(qaId);
    }

}
