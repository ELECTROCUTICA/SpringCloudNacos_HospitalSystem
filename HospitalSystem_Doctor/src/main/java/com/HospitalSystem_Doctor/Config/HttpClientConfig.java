//package com.HospitalSystem_Doctor.Config;
//
//import com.HospitalSystem_Doctor.Service.DoctorHttpExchangeService;
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
//    public DoctorHttpExchangeService doctorClient(RestClient.Builder restClientBuilder) {
//        return HttpServiceProxyFactory.builder().exchangeAdapter(RestClientAdapter.create(restClientBuilder.baseUrl("http://localhost:8080").build())).build().createClient(DoctorHttpExchangeService.class);
//    }
//}
