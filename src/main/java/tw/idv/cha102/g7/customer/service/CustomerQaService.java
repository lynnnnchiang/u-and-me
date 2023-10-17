package tw.idv.cha102.g7.customer.service;

import tw.idv.cha102.g7.customer.entity.CustomerQa;

import java.util.List;

public interface CustomerQaService {

    //查詢全部的QA專欄
    public List<CustomerQa> findAllQA();

    //依上下架狀態查詢QA
    public List<CustomerQa> findByQaState(Integer qaState);

    //依qaId查詢單筆QA
    public CustomerQa findByQaId(Integer qaId);

    //新增QA
    public void addQa(CustomerQa customerQa);

    //編輯修改單筆QA
    public String updateById(Integer qaId, CustomerQa customerQa);

    //刪除QA
    public void deleteById(Integer qaId);


}
