package com.limjustin.crudprac.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.limjustin.crudprac.controller.dto.MusicResponseDto;
import com.limjustin.crudprac.controller.dto.MusicSaveRequestDto;
import com.limjustin.crudprac.controller.dto.MusicUpdateRequestDto;
import com.limjustin.crudprac.domain.music.Music;
import com.limjustin.crudprac.domain.music.MusicRepository;
import com.limjustin.crudprac.service.MusicService;
import org.aspectj.lang.annotation.After;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import java.util.List;

import static org.assertj.core.api.Assertions.*;
import static org.springframework.http.HttpStatus.OK;
import static org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers.springSecurity;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class MusicAPIControllerTest {

    @LocalServerPort
    private int port;
    @Autowired private MusicRepository musicRepository;
    @Autowired private TestRestTemplate restTemplate;  // 컨트롤러 테스트에서는 REST 관련 테스트가 진행되어야하기 때문

    @Autowired
    private WebApplicationContext context;
    private MockMvc mvc;

    @BeforeEach
    void setup() {
        mvc = MockMvcBuilders
                .webAppContextSetup(context)
                .apply(springSecurity())
                .build();
        System.out.println("mvc = " + mvc);
    }

    @AfterEach
    public void tearDown() throws Exception {
        musicRepository.deleteAll();
    }

    @Test
    @WithMockUser(roles = "USER")
    void 등록() throws Exception {
        // given (여기서는 Entity 사용 X, DTO 사용)
        System.out.println("MusicAPIControllerTest.given");
        MusicSaveRequestDto requestDto
                = new MusicSaveRequestDto("Sneakers", "ITZY", "CHECKMATE", "Put my sneakers on!");
        String url = "http://localhost:" + port + "/api/v1/music";  // 랜덤포트 사용하는 이유가 뭐임? -> 알아버렸다 TestRestTemplate이 돌아가지 않기 때문
        // "실제 위 url 같은 요청이 왔을 때!" 라는 것을 지금 테스트하기 위함이잖아!
        // 똑같은 상황을 테스트코드에서 만든거지!
        // 그러면 컨트롤러에서도 그거에 대해 매칭이 되겠지!
        // 실제 : 실제 url 링크를 통해 구동
        // 테스트 코드 : url 변수를 만들어서 구동

        // (new) when
        mvc.perform(post(url)
                .contentType(MediaType.APPLICATION_JSON)
                .content(new ObjectMapper().writeValueAsString(requestDto)))
                .andExpect(status().isOk());

        // when
        // Long id = musicService.save(requestDto);  // 이건 쓰면 안 되나? -> 이거 안 쓰고 어떻게 넣을건데! (이거쓰면 repo 테스트랑 다를게 뭐임;;)
//        System.out.println("MusicAPIControllerTest.when");
//        ResponseEntity<Long> responseEntity = restTemplate.postForEntity(url, requestDto, Long.class);// 얘는 내가볼때 HTTP 테스트용임
        // postForEntity 가 알아서 save 함수 수행해주는건가? (로그 찍으니까 먼가 신기하네 이거 꼭 블로그에 올리자 깔끔하게 정리해서!)
        // HTTP 요청을 (테스트 코드가 아닌) 실제 코드를 사용해서 돌려주는건가
        // 사실 저 위에 한 줄이 제일 어려움, TestRestTemplate, ResponseEntity, Entity Default Constructor

        // then
        System.out.println("MusicAPIControllerTest.then");
//        System.out.println("responseEntity = " + responseEntity);
//        System.out.println("responseEntity.getStatusCode() = " + responseEntity.getStatusCode());
//        System.out.println("responseEntity.getBody() = " + responseEntity.getBody());

         List<Music> musicList = musicRepository.findAll();
        // System.out.println("musicList.get(0).getTitle() = " + musicList.get(0).getTitle());
//        assertThat(responseEntity.getStatusCode()).isEqualTo(OK);
        assertThat(musicList.get(0).getTitle()).isEqualTo(requestDto.getTitle());
        // 결국 검증 관련 부분은 큰 의미가 없네. 컨트롤러 테스트에서! (repo 테스트랑 다른게 머임)
        // 아니야 API 통해 넣어주어야지! 어떻게 근데?
    }
    /*
    @Test
    void 조회() {
        // given
        Music music = new Music("Loco", "ITZY", "CRAZY IN LOVE", "I'm gettin loco");
        musicRepository.save(music);  // 저장을 하는데 그것도 테스트 코드에서? DTO 쓸 필요없지 조금만 생각해보면?!!

        String url = "http://localhost:" + port + "/api/v1/music/" + music.getId();
        System.out.println(url);

        // when
        System.out.println("Start getForEntity");
        ResponseEntity<Music> responseEntity = restTemplate.getForEntity(url, Music.class);  // 리턴 타입 주의! (Music 그릇에 잘 받음)
        // 타입은 ResponseEntity가 맞는데 body로 한번 까면 Music 객체로 사용할 수 있다는건가 ㅇㅇ 그렇게 이해하면 편할듯
        System.out.println("End getForEntity");
        MusicResponseDto responseDto = new MusicResponseDto(responseEntity.getBody());  // Entity mapping into DTO
        // 아 그치 테스트 코드는 일단 잘 돌아갔지! 단지 Status Code가 406이 떴을뿐!
        // 내 예상은 이거임. responseEntity의 결과는 실제 Controller 에서 return 받는거까지, 즉 동작이 완료된 상태의 값을 받는건데
        // responseEntity.getStatusCode()의 값이 406이 나온거니까
        // 서버가 목록과 일치하는 응답을 넘겨주지 못해서 406 에러(서버가 목록과 일치하는 응답을 생성할 수 없다)가 발생한 것임!
        // 값을 return 받을 때, Getter가 없었기 때문에 값을 받을 수 없었고, 그래서 제대로 응답을 생성할 수 없었던 것이지요!
        // 왜냐면 DTO 객체에 값은 잘 담겼는데, Controller에서 DTO 객체를 리턴받을 때 Getter 통해서 값을 리턴해오나봄!

        // then
        System.out.println("responseEntity = " + responseEntity);
        System.out.println("responseDto = " + responseDto);
        System.out.println("responseDto.getTitle() = " + responseDto.getTitle());
        assertThat(responseDto.getTitle()).isEqualTo(music.getTitle());
        assertThat(responseEntity.getStatusCode()).isEqualTo(OK);

        // 조회 테스트는 등록 테스트랑 비슷한 점이 많아서 로직 자체는 이해하기 괜찮다!
        // 다만 responseEntity 리턴 타입이 조금 걸린다! (해결 완료)
        // 406 에러 발생 이유 : Getter 써주지 않아서?
    }

     */

    @Test
    @WithMockUser(roles = "USER")
    void 수정() throws Exception {
        // 기존에 있던 글을 id를 기반으로 찾음
        // UpdateDto에 수정된 내용을 담고
        // 그걸 save 하면 된다? (INSERT 말고 UPDATE 쿼리가 떠야해! 그럼 그거 캡쳐해서 블로그에 포스팅하기!)

        // given
        Music music = new Music("I'm not the only one", "Sam Smith", "In the Lonely Hour", "You and me we made a vow");
        musicRepository.save(music);

        String url = "http://localhost:" + port + "/api/v1/music/" + music.getId();
        System.out.println(url);  // 여기 url 자체가 이미 GET 이라서 그런거 아님?

        MusicUpdateRequestDto requestDto = new MusicUpdateRequestDto("I don't think that I like her", "Charlie Puth", "Charlie", "Get her name and get her number");

        // when
        // ResponseEntity<Music> responseEntity = restTemplate.postForEntity(url, requestDto, Music.class);  // 왜 Long -> Music 하니까 오류 안 나지?
        // responseEntity 로 받아오는 body 부분에 어떤 데이터가 있는지 생각해봐
        System.out.println("MusicAPIControllerTest.수정.when");
        // ResponseEntity<Long> responseEntity = restTemplate.exchange(url, HttpMethod.PUT, new HttpEntity<>(requestDto), Long.class);
        // (new) when
        mvc.perform(put(url)
                .contentType(MediaType.APPLICATION_JSON)
                .content(new ObjectMapper().writeValueAsString(requestDto)))
                .andExpect(status().isOk());


        // then
        // System.out.println("responseEntity = " + responseEntity);
        // System.out.println("responseEntity.getBody() = " + responseEntity.getBody());
        List<Music> musicList = musicRepository.findAll();
        System.out.println(musicList.get(0).getTitle());
        assertThat(musicList.get(0).getTitle()).isEqualTo(requestDto.getTitle());
        // assertThat(responseEntity.getStatusCode()).isEqualTo(OK);

        // restTemplate.postForEntity(url, requestDto, Music.class) 이 코드가 왜 틀렸는지 405 Error 발생한 이유 정리하기
        // 405 에러 발생 이유 : GET, POST 겹쳐서?
        // 이 코드 역시 리턴 타입 체크하기
    }
    /*
    @Test
    void 삭제() {
        // given
        Music music = new Music("I'm not the only one", "Sam Smith", "In the Lonely Hour", "You and me we made a vow");
        musicRepository.save(music);

        String url = "http://localhost:" + port + "/api/v1/music/" + music.getId();

        // when
        restTemplate.delete(url, music);

        // then
    }

    */
}