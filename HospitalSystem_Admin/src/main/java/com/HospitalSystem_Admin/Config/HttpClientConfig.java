//package com.HospitalSystem_Admin.Config;
//
//import com.HospitalSystem_Admin.Service.AdminHttpExchangeService;
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
//    public AdminHttpExchangeService adminClient(RestClient.Builder restClientBuilder) {
//        return HttpServiceProxyFactory.builder().exchangeAdapter(RestClientAdapter.create(restClientBuilder.baseUrl("http://localhost:8080").build())).build().createClient(AdminHttpExchangeService.class);
//    }
//}
