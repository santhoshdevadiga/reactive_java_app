package com.reactivesprintboot.reactiveapp.controller;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.WebFluxTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.reactive.server.EntityExchangeResult;
import org.springframework.test.web.reactive.server.WebTestClient;
import reactor.core.publisher.Flux;
import reactor.test.StepVerifier;

import java.util.Arrays;
import java.util.List;

import static org.junit.Assert.assertEquals;

@RunWith(SpringRunner.class)
@WebFluxTest
public class FluxAndMonoControllerTest {

    @Autowired
    WebTestClient webTestClient;

    @Test
    public void fluxApproach1(){
      Flux<Integer> responseBody = webTestClient.get()
          .uri("/fluxApi")
          .accept(MediaType.APPLICATION_JSON_UTF8)
          .exchange()
          .expectStatus().isOk()
          .returnResult(Integer.class)
          .getResponseBody();

      StepVerifier.create(responseBody)
          .expectSubscription()
          .expectNext(1)
          .expectNext(2)
          .expectNext(3)
          .expectNext(4)
          .verifyComplete();
    }

    @Test
    public void fluxApproach2(){
      webTestClient.get()
          .uri("/fluxApi")
          .accept(MediaType.APPLICATION_JSON_UTF8)
          .exchange()
          .expectStatus().isOk()
          .expectHeader().contentType(MediaType.APPLICATION_JSON_UTF8)
          .expectBodyList(Integer.class)
          .hasSize(4);
    }

  @Test
  public void fluxApproach3(){

      List<Integer> expectedList = Arrays.asList(1,2,3,4);
    EntityExchangeResult<List<Integer>> listEntityExchangeResult = webTestClient.get()
        .uri("/fluxApi")
        .accept(MediaType.APPLICATION_JSON_UTF8)
        .exchange()
        .expectStatus().isOk()
        .expectBodyList(Integer.class)
        .returnResult();

    assertEquals(expectedList,listEntityExchangeResult.getResponseBody());
  }


  @Test
  public void fluxApproach4(){

    List<Integer> expectedList = Arrays.asList(1,2,3,4);
    webTestClient.get()
        .uri("/fluxApi")
        .accept(MediaType.APPLICATION_JSON_UTF8)
        .exchange()
        .expectStatus().isOk()
        .expectBodyList(Integer.class)
        .consumeWith(response -> {
          assertEquals(expectedList,response.getResponseBody());
        });

  }


  @Test
  public void infiniteFluxStream(){
    Flux<Long> responseBody = webTestClient.get()
        .uri("/flux")
        .accept(MediaType.APPLICATION_STREAM_JSON)
        .exchange()
        .expectStatus().isOk()
        .returnResult(Long.class)
        .getResponseBody();

    StepVerifier.create(responseBody)
        .expectSubscription()
        .expectNext(0l)
        .expectNext(1l)
        .expectNext(2l)
        .thenCancel()
        .verify();

  }

  @Test
  public void monoApi(){

    Integer expectedInt = new Integer(1);
    webTestClient.get()
        .uri("/monoApi")
        .accept(MediaType.APPLICATION_JSON_UTF8)
        .exchange()
        .expectStatus().isOk()
        .expectBody(Integer.class)
        .consumeWith(response -> {
          assertEquals(expectedInt, response.getResponseBody());
        });

  }


}
