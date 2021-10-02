package com.sparta.springcore;

import com.sparta.springcore.model.Product;
import com.sparta.springcore.model.User;
import com.sparta.springcore.model.UserRoleEnum;
import com.sparta.springcore.model.dto.SignupRequestDto;
import com.sparta.springcore.repository.ProductRepository;
import com.sparta.springcore.repository.UserRepository;
import com.sparta.springcore.service.ItemSearchService;
import com.sparta.springcore.service.UserService;
import lombok.NoArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.event.ApplicationStartedEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import static com.sparta.springcore.service.ProductService.MIN_MY_PRICE;

@NoArgsConstructor
@Component
public class SimpleListener implements ApplicationListener<ApplicationStartedEvent> {

    @Autowired
    private EntityManagerFactory entityManagerFactory;

    @Autowired
    UserService userService;

    @Autowired
    ProductRepository productRepository;

    @Autowired
    UserRepository userRepository;

    @Autowired
    PasswordEncoder passwordEncoder;

    @Autowired
    ItemSearchService itemSearchService;

    @SneakyThrows
    @Override
    public void onApplicationEvent(ApplicationStartedEvent event) {
        EntityManager em = entityManagerFactory.createEntityManager();
        EntityTransaction transaction = em.getTransaction();
        transaction.begin();

        // 테스트 User 생성
        User testUser1 = new User("슈가1", passwordEncoder.encode("123"), "sugar1@sparta.com", UserRoleEnum.USER);
        User testUser2 = new User("슈가2", passwordEncoder.encode("123"), "sugar2@sparta.com", UserRoleEnum.USER);
        User testAdminUser1 = new User("admin", passwordEncoder.encode("123"), "sugar3@sparta.com", UserRoleEnum.ADMIN);


        testUser1 = userRepository.save(testUser1);
        testUser2 = userRepository.save(testUser2);
        testAdminUser1 = userRepository.save(testAdminUser1);



        // 테스트 User 의 관심상품 등록
        // 검색어 당 관심상품 10개 등록
        createTestData(testUser1, "신발");
        createTestData(testUser1, "과자");
        createTestData(testUser1, "키보드");
        createTestData(testUser1, "휴지");
        createTestData(testUser1, "휴대폰");
        createTestData(testUser1, "앨범");
        createTestData(testUser1, "헤드폰");
        createTestData(testUser1, "이어폰");
        createTestData(testUser1, "노트북");
        createTestData(testUser1, "무선 이어폰");
        createTestData(testUser1, "모니터");




        em.getTransaction().commit();
    }

    private void createTestData(User user, String searchWord) throws IOException {
// 네이버 쇼핑 API 통해 상품 검색
        List<SignupRequestDto.ItemDto> itemDtoList = itemSearchService.getItems(searchWord);

        List<Product> productList = new ArrayList<>();

        for (SignupRequestDto.ItemDto itemDto : itemDtoList) {
            Product product = new Product();
// 관심상품 저장 사용자
            product.setUserId(user.getId());
// 관심상품 정보
            product.setTitle(itemDto.getTitle());
            product.setLink(itemDto.getLink());
            product.setImage(itemDto.getImage());
            product.setLprice(itemDto.getLprice());

// 희망 최저가 랜덤값 생성
// 최저 (100원) ~ 최대 (상품의 현재 최저가 + 10000원)
            int myPrice = getRandomNumber(MIN_MY_PRICE, itemDto.getLprice() + 10000);
            product.setMyprice(myPrice);

            productList.add(product);
        }

        productRepository.saveAll(productList);
    }

    public int getRandomNumber(int min, int max) {
        return (int) ((Math.random() * (max - min)) + min);
    }
}
