package com.sample.springcloudstream.source.ws.controller;

import brave.Tracer;
import brave.Tracing;
import com.sample.springcloudstream.source.dto.FooDTO;
import com.sample.springcloudstream.source.service.FooSourceService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.util.UriComponentsBuilder;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

/**
 * <p>
 * <p>
 * <p>
 * <p>
 *
 * @author cjrequena
 * @version 1.0
 * @see
 * @since JDK1.8
 */
@Log4j2
@RestController
@RequestMapping(value = "/foo-service")
@Api(
  value = "Foo Entity",
  tags = {"Foo Entity"}
)
public class FooController {

  @Autowired
  private Tracing tracing;
  @Autowired
  private Tracer tracer;
  @Autowired
  private FooSourceService fooSourceService;

  /**
   * Create a new foo.
   *
   * @param dto {@link FooDTO}
   * @param bindingResult {@link BindingResult}
   * @return ResponseEntity {@link ResponseEntity}
   */
  @ApiOperation(
    tags = "Foo Entity",
    value = "Send foo to kafka",
    notes = "Send foo to kafka"
  )
  @ApiResponses(
    value = {
      @ApiResponse(code = 200, message = "OK - The request was successful"),
      @ApiResponse(code = 400, message = "Bad Request - The data given in the POST failed validation. Inspect the response body for details."),
      @ApiResponse(code = 500, message = "Internal Server Error - We couldn't create the resource. Please try again."),
      @ApiResponse(code = 503, message = "Service Unavailable - We are temporarily unable. Please wait for a bit and try again. ")
    }
  )
  @PostMapping(
    path = "/foos",
    produces = {
      MediaType.APPLICATION_JSON_VALUE
    }
  )
  public ResponseEntity<Void> send(
    @ApiParam(value = "foo", name = "foo", required = true) @Valid @RequestBody FooDTO dto, BindingResult bindingResult,
    HttpServletRequest request, UriComponentsBuilder ucBuilder) {
    //--
    try {
      String traceID = tracer.currentSpan().context().traceIdString();
      String spanID = tracer.currentSpan().context().spanIdString();
      log.debug("traceID: {}, spainID: {} ", traceID, spanID);
      //
      fooSourceService.send(dto);
      // Headers
      HttpHeaders headers = new HttpHeaders();
      headers.setLocation(ucBuilder.path(new StringBuilder().append(request.getServletPath()).append("/{id}").toString())
        .buildAndExpand(dto.getId())
        .toUri());
      //
      return new ResponseEntity<>(headers, HttpStatus.OK);
    } catch (Exception ex) {
      log.error("{}", ex.getMessage(), ex);
      throw ex;
    }
    //---
  }

}
