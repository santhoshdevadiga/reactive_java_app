package com.reactivesprintboot.reactiveapp.controller;

import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.time.Duration;

@RestController
public class FluxAndMonoController {


  @GetMapping(value = "/flux" , produces = MediaType.APPLICATION_STREAM_JSON_VALUE)
  public Flux<Long> flux(){
    return Flux.interval(Duration.ofSeconds(1))
        .log();
  }

  @GetMapping("/fluxApi")
  public Flux<Integer> fluxApi(){
      return Flux.just(1,2,3,4)
          .delayElements(Duration.ofSeconds(1))
          .log();
  }

  @GetMapping(value = "/fluxAllApi", produces = MediaType.APPLICATION_STREAM_JSON_VALUE)
  public Flux<Integer> fluxAllApi(){
    return Flux.just(1,2,3,4).delayElements(Duration.ofSeconds(1)).log();
  }


  @GetMapping(value = "/monoApi")
  public Mono<Integer> monoApi(){
    return Mono.just(1)
        .log();
  }

}