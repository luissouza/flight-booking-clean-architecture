package org.pt.flightbooking.adapters.infrastructure.webclient;
import com.google.gson.Gson;
import java.util.function.Consumer;
import lombok.extern.slf4j.Slf4j;
import org.pt.flightbooking.adapters.exception.mappings.ExceptionMapper;
import org.pt.flightbooking.adapters.exception.dto.ErrorResponseDto;
import org.pt.flightbooking.adapters.exception.error.ErrorBuilder;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;
import java.time.Duration;
import static org.pt.flightbooking.adapters.exception.message.ErrorMessage.exchangeRequestError;

@Slf4j
@Service
public class WebClientHandler {

  private final WebClient webClient;
  private final WebClientProperties properties;
  private final ErrorBuilder errorBuilder;
  private final ExceptionMapper exceptionMapper;

  public WebClientHandler(
    final WebClient webClient,
    final WebClientProperties properties,
    final ErrorBuilder errorBuilder,
    final ExceptionMapper exceptionMapper
  ) {
    this.properties = properties;
    this.webClient = webClient;
    this.errorBuilder = errorBuilder;
    this.exceptionMapper = exceptionMapper;
  }


  protected ResponseEntity<?> retrieveQueryParams(final String queryParams,
                                                  final String component,
                                                  final WebClientPropertiesService service,
                                                  final String serviceLabel,
                                                  final Class<?> responseClass) {

    final String baseUrl = properties.getBaseUrl(component);
    final String path = properties.getPath(service, serviceLabel);
    final HttpMethod method = properties.getMethod(service, serviceLabel);
    final Consumer<HttpHeaders> httpHeaders = WebClientHeader.httpHeaders(properties.getHeaders(component));
    final int timeout = properties.getTimeout(service, serviceLabel);
    final String fullPath = baseUrl + path + queryParams;

    Object result = webClient.method(method).uri(fullPath).headers(httpHeaders).exchangeToMono(response -> {

      System.out.println(fullPath);
      System.out.println("response.statusCode()");
      System.out.println(response.statusCode());

      if(response.statusCode().equals(HttpStatus.OK)) {
        return response.bodyToMono(responseClass);
      } else {
        if (response instanceof ErrorResponseDto) {
          return response.bodyToMono(ErrorResponseDto.class);
        }
        return response.bodyToMono(String.class).flatMap(errorBody -> Mono.just(new Gson().fromJson(errorBody, ErrorResponseDto.class)));
      }
    }).doOnError(Throwable::printStackTrace)
      .doOnError(error -> log.error(error.getLocalizedMessage()))
      .onErrorReturn(errorBuilder.createError(exchangeRequestError(fullPath), "", HttpStatus.SERVICE_UNAVAILABLE))
      .timeout(Duration.ofSeconds(timeout))
      .block();

    if(result instanceof ErrorResponseDto) {
      final HttpStatus httpStatus = HttpStatus.valueOf(((ErrorResponseDto) result).getHttpCode());
      result = exceptionMapper.mapException((ErrorResponseDto) result);
      return new ResponseEntity<>(result, httpStatus);
    }

    return new ResponseEntity<>(result, HttpStatus.OK);
  }
}
