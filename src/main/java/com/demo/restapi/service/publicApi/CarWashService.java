package com.demo.restapi.service.publicApi;

import com.demo.restapi.entity.CarWash;
import com.demo.restapi.model.paging.PageRequest;
import com.demo.restapi.model.publicApi.CarWashResponse;
import com.demo.restapi.model.publicApi.PublicApiParams;
import com.demo.restapi.repository.publicApi.CarWashJpaRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;

@Slf4j
@Service
@Transactional
@RequiredArgsConstructor
public class CarWashService {

    private final PublicApiService publicApiService;
    private final CarWashJpaRepository carWashJpaRepository;

    @Value("${spring.public-api.car-wash.api-url}")
    private String API_URL;

    @Value("${spring.public-api.car-wash.service-key}")
    private String SERVICE_KEY;

    private final String RESPONSE_TYPE = "xml";
    private final int NUM_OF_ROWS = 20000;

    /**
     * 단건 저장
     * @param carWash
     * @return
     */
    public CarWash saveOne(CarWash carWash) {
        return carWashJpaRepository.save(carWash);
    }

    /**
     * 다건 저장
     * @param list
     * @return
     */
    public boolean saveList(List<CarWash> list) {
        List<CarWash> result = carWashJpaRepository.saveAll(list);

        return result.size() == list.size();
    }

    /**
     * 목록 조회
     * @param pageRequest
     * @return
     */
    public Page<CarWash> getAll(PageRequest pageRequest) {
        return carWashJpaRepository.findAll(pageRequest.of());
    }

    /**
     * 전체 삭제
     */
    public void deleteAll() { carWashJpaRepository.deleteAll(); }

    /**
     * 공공 API Data 조회
     * @return
     */
    public boolean pullData() {
        List<CarWash> list = new ArrayList<>();

        int pageNo = 0;
        int totalCount = 0;

        while (pageNo * NUM_OF_ROWS <= totalCount) {
            pageNo++;

            PublicApiParams params = PublicApiParams.builder()
                    .apiUrl(API_URL)
                    .serviceKey(SERVICE_KEY)
                    .responseType(RESPONSE_TYPE)
                    .pageNo(pageNo)
                    .numOfRows(NUM_OF_ROWS)
                    .build();

            CarWashResponse response = publicApiService.callApi(params, CarWashResponse.class);
            totalCount = response.getBody().getTotalCount();
            list.addAll(response.getBody().getItems());
        }

        if (list.size() > 0) {
            deleteAll();
            return saveList(list);
        } else {
            return false;
        }
    }

}
