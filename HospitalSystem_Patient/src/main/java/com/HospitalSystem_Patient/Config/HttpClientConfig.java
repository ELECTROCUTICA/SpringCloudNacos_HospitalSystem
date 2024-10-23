//package com.HospitalSystem_Patient.Config;
//
//import com.HospitalSystem_Patient.Service.PatientHttpExchangeService;
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//import org.springframework.web.client.RestClient;
//import org.springframework.web.client.support.RestClientAdapter;
//import org.springframework.web.service.invoker.HttpServiceProxyFactory;
//
//@Configuration
//public class HttpClientConfig {
//
//    @Bean
//    RestClient.Builder restClientBuilder() {
//        return RestClient.builder();
//    }
//
//    @Bean
//    public PatientHttpExchangeService patientClient(RestClient.Builder restClientBuilder) {
//        return HttpServiceProxyFactory.builder().exchangeAdapter(RestClientAdapter.create(restClientBuilder.baseUrl("http://localhost:8080").build())).build().createClient(PatientHttpExchangeService.class);
//    }
//
//}
