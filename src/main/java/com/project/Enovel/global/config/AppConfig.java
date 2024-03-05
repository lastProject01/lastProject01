package com.project.Enovel.global.config;

import jakarta.persistence.EntityManager;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;


@Configuration
public class AppConfig {

    // 스프링 부트 애플리케이션의 활성 프로파일을 저장하는 변수
    private static String activeProfile;

    // 스프링 프로퍼티 'spring.profiles.active'에서 현재 활성 프로파일의 값을 주입받음
    @Value("${spring.profiles.active}")
    public void setActiveProfile(String value) {
        activeProfile = value;
    }

    // 현재 활성 프로파일이 'prod'가 아닌지 확인하는 메소드
    public static boolean isNotProd() {
        return !isProd(); // 'prod'가 아니면 true를 반환
    }

    // 현재 활성 프로파일이 'prod'인지 확인하는 메소드
    public static boolean isProd() {
        return "prod".equals(activeProfile); // 'prod'이면 true를 반환
    }

    @Getter
    private static int orderCancelableSeconds;

    @org.springframework.beans.factory.annotation.Value("${custom.order.cancelableSeconds}")
    public void setOrderCancelableSeconds(int orderCancelableSeconds) {
        this.orderCancelableSeconds = orderCancelableSeconds;
    }
//    todo 결제 로직 완성 후 사용
//    // 토스 결제 위젯의 시크릿 키를 저장하는 필드
//    private static String tossPaymentsWidgetSecretKey;
//
//    // 토스 결제 위젯 시크릿 키를 설정하는 메서드
//    @Value("${custom.tossPayments.widget.secretKey}")
//    public void setTossPaymentsWidgetSecretKey(String tossPaymentsWidgetSecretKey) {
//        AppConfig.tossPaymentsWidgetSecretKey = tossPaymentsWidgetSecretKey;
//    }
//
//    // 토스 결제 위젯 시크릿 키를 반환하는 메서드
//    public static String getTossPaymentsWidgetSecretKey() {
//        return tossPaymentsWidgetSecretKey;
//    }
}
