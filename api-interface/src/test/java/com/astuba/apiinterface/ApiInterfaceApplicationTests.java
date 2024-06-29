package com.astuba.apiinterface;

import com.astuba.apiclientsdk.client.ApiClient;
import com.astuba.apiclientsdk.model.User;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import javax.annotation.Resource;

@SpringBootTest
class ApiInterfaceApplicationTests {

    @Resource
    private ApiClient apiClient;

    @Test
    void contextLoads() {
        String result1 = apiClient.getNameByGet("astuba");
        String result2 = apiClient.getNameByPost("astuba");
        User user = new User();
        user.setName("astuba");
        String result3 = apiClient.getUserNameByPost(user);
        System.out.println(result1);
        System.out.println(result2);
        System.out.println(result3);
    }

}
