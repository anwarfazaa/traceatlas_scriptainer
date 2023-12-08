package org.traceatlas.Scriptainer.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.traceatlas.Scriptainer.dataobjects.ExtensionData;

import java.util.ArrayList;
import java.util.List;


@Service
@Component
public class DataSenderServiceExtensions {

    private static final String PLATFORM_URL = "localhost:";

    private final RestTemplate restTemplate;
    private final List<ExtensionData> extensionDataList;

    @Autowired
    public DataSenderServiceExtensions(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
        this.extensionDataList = new ArrayList<>();
    }

    public void populateExtensionDataPublisher(ExtensionData extensionData) {
        this.extensionDataList.add(extensionData);
    }

    @Scheduled(fixedRate = 5000)
    public void extensionDataPublisher() {
        restTemplate.postForLocation(PLATFORM_URL,extensionDataList);
    }


}
