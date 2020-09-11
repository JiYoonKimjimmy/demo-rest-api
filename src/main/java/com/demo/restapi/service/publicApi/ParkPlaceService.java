package com.demo.restapi.service.publicApi;

import com.demo.restapi.common.advice.exception.PublicApiFailException;
import com.demo.restapi.entity.ParkPlace;
import com.demo.restapi.model.publicApi.ParkPlaceResponse;
import com.demo.restapi.repository.publicApi.ParkPlaceJpaRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponents;
import org.springframework.web.util.UriComponentsBuilder;

import javax.transaction.Transactional;
import java.net.URI;
import java.util.ArrayList;
import java.util.List;

@Service
@Slf4j
@Transactional
@RequiredArgsConstructor
public class ParkPlaceService {

    private final RestTemplate restTemplate;
    private final ParkPlaceJpaRepository parkPlaceJpaRepository;

    @Value("${spring.public-api.park-place.api-url}")
    private String API_URL;

    @Value("${spring.public-api.park-place.service-key}")
    private String SERVICE_KEY;

    private final String RESPONSE_TYPE = "xml";
    private final int NUM_OF_ROWS = 100;

    /**
     * 단건 저장
     * @param parkplace
     * @return
     */
    public ParkPlace saveOne(ParkPlace parkplace) {
        return parkPlaceJpaRepository.save(parkplace);
    }

    /**
     * 다건 저장
     * @param list
     * @return
     */
    public boolean saveList(List<ParkPlace> list) {
        List<ParkPlace> result = parkPlaceJpaRepository.saveAll(list);

        return result.size() == list.size();
    }

    /**
     * 목록 조회
     * @return
     */
    public List<ParkPlace> getAll() {
        return parkPlaceJpaRepository.findAll();
    }

    /**
     * 전체 삭제
     */
    public void deleteAll() { parkPlaceJpaRepository.deleteAll(); }

    /**
     * 공공 API Data 조회
     * @return
     */
    public boolean pullData() {
        List<ParkPlace> list = new ArrayList<>();

        int pageNo = 0;
        int totalCount = 0;

        while (pageNo * NUM_OF_ROWS <= totalCount) {
            pageNo++;
            ParkPlaceResponse response = callApi(pageNo);
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

    /**
     * call api
     * @param pageNo
     * @return
     */
    public ParkPlaceResponse callApi(int pageNo) {
        ParkPlaceResponse response = null;

        try {
            UriComponents uriComponents = UriComponentsBuilder
                    .fromHttpUrl(API_URL)
                    .queryParam("serviceKey", SERVICE_KEY)
                    .queryParam("pageNo", pageNo)
                    .queryParam("numOfRows", NUM_OF_ROWS)
                    .queryParam("type", RESPONSE_TYPE)
                    .build();

            URI uri = new URI(uriComponents.toString());

            log.info("public api call start [" + pageNo + "]");
            response = restTemplate.getForObject(uri, ParkPlaceResponse.class);
            log.info("public api call end [" + pageNo + "]");

        } catch (Exception e) {
            throw new PublicApiFailException();
        }

        return response;
    }



}
