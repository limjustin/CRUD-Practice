package com.limjustin.crudprac.controller;

import com.limjustin.crudprac.controller.dto.MusicResponseDto;
import com.limjustin.crudprac.controller.dto.MusicSaveRequestDto;
import com.limjustin.crudprac.domain.music.Music;
import com.limjustin.crudprac.domain.music.MusicRepository;
import com.limjustin.crudprac.service.MusicService;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.ResponseEntity;

import java.util.List;

import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class MusicAPIControllerTest {

    @LocalServerPort private int port;
    @Autowired private MusicRepository musicRepository;
    @Autowired private TestRestTemplate restTemplate;  // 컨트롤러 테스트에서는 REST 관련 테스트가 진행되어야하기 때문


    @AfterEach
    void AfterEach() {
        musicRepository.deleteAll();  // 이거 때문에 repository 선언해야하나?
    }

    @Test
    void 등록() {
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

        // when
        // Long id = musicService.save(requestDto);  // 이건 쓰면 안 되나? -> 이거 안 쓰고 어떻게 넣을건데! (이거쓰면 repo 테스트랑 다를게 뭐임;;)
        System.out.println("MusicAPIControllerTest.when");
        ResponseEntity<Long> responseEntity = restTemplate.postForEntity(url, requestDto, Long.class);// 얘는 내가볼때 HTTP 테스트용임
        // postForEntity 가 알아서 save 함수 수행해주는건가? (로그 찍으니까 먼가 신기하네 이거 꼭 블로그에 올리자 깔끔하게 정리해서!)
        // HTTP 요청을 (테스트 코드가 아닌) 실제 코드를 사용해서 돌려주는건가
        // 사실 저 위에 한 줄이 제일 어려움, TestRestTemplate, ResponseEntity, Entity Default Constructor

        // then
        System.out.println("MusicAPIControllerTest.then");
        System.out.println("responseEntity = " + responseEntity);
        System.out.println("responseEntity.getStatusCode() = " + responseEntity.getStatusCode());
        System.out.println("responseEntity.getBody() = " + responseEntity.getBody());

        List<Music> musicList = musicRepository.findAll();
        System.out.println("musicList.get(0).getTitle() = " + musicList.get(0).getTitle());
        assertThat(musicList.get(0).getTitle()).isEqualTo(requestDto.getTitle());
        // 결국 검증 관련 부분은 큰 의미가 없네. 컨트롤러 테스트에서! (repo 테스트랑 다른게 머임)
        // 아니야 API 통해 넣어주어야지! 어떻게 근데?

        /* 이해한 부분
        * 1. 컨트롤러 테스트에서는 HTTP 요청 및 응답 관련한 테스트를 진행해야 함
        *    그래서 TestRestTemplate 이 필요하다 (REST 방식 호출 가능하도록 할 수 있는 객체)
        * 2. TestRestTemplate은 사용할 수 있는 포트가 하나 무조건 열려 있어야 작동 가능
        *    그래서 랜덤포트 사용한 것임!
        * 3. TestRestTemplate body 부분이 2인데 그게 무엇을 의미하는지 모르겠다...
        * 4. TestRestTemplate 객체를 활용해서 ResponseEntity 객체를 얻을 수 있고, 이를 통해 HTTP 통신에 관한 정보를 얻을 수 있음!
        *    (Status Code, Body value(이게 뭔지 모르겠다고)) -> 쨌든 HTTP 통신 정보를 한번 씌워서 제공해줌 (ResponseEntity)
        * */
    }

    @Test
    void 조회() {
        // given
        Music music = new Music("Loco", "ITZY", "CRAZY IN LOVE", "I'm gettin loco");
        musicRepository.save(music);  // 저장을 하는데 그것도 테스트 코드에서? DTO 쓸 필요없지 조금만 생각해보면?!!

        String url = "http://localhost:" + port + "/api/v1/music/" + music.getId();
        System.out.println(url);

        // when
        ResponseEntity<Music> responseEntity = restTemplate.getForEntity(url, Music.class);
        MusicResponseDto responseDto = new MusicResponseDto(responseEntity.getBody());  // Entity mapping into DTO

        // then
        System.out.println("responseEntity = " + responseEntity);
        System.out.println("responseDto.getTitle() = " + responseDto.getTitle());
        assertThat(responseDto.getTitle()).isEqualTo(music.getTitle());
    }

    @Test
    void 수정() {


    }
}