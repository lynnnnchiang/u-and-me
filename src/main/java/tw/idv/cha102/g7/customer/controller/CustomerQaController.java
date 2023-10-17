package tw.idv.cha102.g7.customer.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import tw.idv.cha102.g7.customer.entity.CustomerQa;
import tw.idv.cha102.g7.customer.service.CustomerQaService;
import tw.idv.cha102.g7.schedule.controller.exception.ScheduleNotFoundException;

import java.util.List;

@RestController
public class CustomerQaController {
    @Autowired
    private CustomerQaService service;


    /**
     * 搜尋全部的QA欄位資料
     *
     * @return 返回全部的QA資料
     */
    @GetMapping("/qas")
    public ResponseEntity<List<CustomerQa>> findAll() {
        return new ResponseEntity<>(service.findAllQA(), HttpStatus.OK);
    }


    /**
     * 依據QaId查詢單筆資料
     *
     * @param qaId QaId編號
     * @return 返回單筆QA資料
     */
    @GetMapping("/qas/{qaId}")
    public ResponseEntity<CustomerQa> findByQaId(@PathVariable Integer qaId) {
        return new ResponseEntity<>(service.findByQaId(qaId), HttpStatus.OK);
    }


    /**
     * 依據QA上下架狀態查詢多筆資料
     *
     * @param qaState 0:下架 1:上架 預設為下架
     * @return 返回依據上下狀態的單筆資料
     */
    @GetMapping("/state/{qaState}")
    public ResponseEntity<List<CustomerQa>> findByQaState(@PathVariable Integer qaState) {
        return new ResponseEntity<>(service.findByQaState(qaState), HttpStatus.OK);
    }


    /**
     * 新增單筆QA資料
     *
     * @param customerQa 單筆QA
     * @return 返回新增完畢的QA資料
     */
    @PostMapping("/add/qa")
    public ResponseEntity<?> addQa(@RequestBody CustomerQa customerQa) {

        try {
            service.addQa(customerQa);
            return ResponseEntity.ok().build();
        }catch (Exception e){
            return  ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }


    /**
     * 修改單筆QA資料
     *
     * @param qaId       QA的單筆ID
     * @param customerQa 修改單筆QA資料
     * @return 返回修改完畢的單筆QA資料
     */
    @PutMapping("/updqa/{qaId}")
    public ResponseEntity<?> updateById(@PathVariable Integer qaId, @RequestBody CustomerQa customerQa) {

        try {
            service.updateById(qaId, customerQa);
            return ResponseEntity.ok().build();
        } catch (Exception e) {
            return ResponseEntity
                    .status(HttpStatus.NOT_FOUND).body(new ScheduleNotFoundException(qaId));
        }
    }


    /**
     * 刪除單筆QA資料
     *
     * @param qaId QaId編號
     */
    @DeleteMapping("/delqa/{qaId}")
    public ResponseEntity<Boolean> deleteById(@PathVariable Integer qaId) {

        try {
            service.deleteById(qaId);
            return ResponseEntity.ok(true);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(false);
        }

    }

}
