package com.cjrequena.sample.sourcesample.ws.controller;

import brave.Tracer;
import brave.Tracing;
import com.cjrequena.sample.sourcesample.dto.FooDTO;
import com.cjrequena.sample.sourcesample.service.FooSourceService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.enums.ParameterIn;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
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

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

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
@Tag(name = "Foo Service", description = "Foo Service")
public class FooController {

  @Autowired
  private Tracing tracing;
  @Autowired
  private Tracer tracer;
  @Autowired
  private FooSourceService fooSourceService;

  public static final String VND_FOO_SERVICE_V1 = "vnd.foo-service.v1";


  @Operation(
    summary = "Create a new foo.",
    description = "Create a new foo.",
    parameters = {@Parameter(name= "accept-version", required = true, in = ParameterIn.HEADER,schema=@Schema(name = "accept-version", type = "string",implementation = String.class, allowableValues = {VND_FOO_SERVICE_V1}))},
    requestBody =  @io.swagger.v3.oas.annotations.parameters.RequestBody(required = true, content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE, schema = @Schema(implementation = FooDTO.class)))
  )
  @ApiResponses(
    value = {
      @ApiResponse(responseCode = "200", description = "OK - The request was successful"),
      @ApiResponse(responseCode = "201", description = "Created - The request was successful, we created a new resource and the response body contains the representation."),
      @ApiResponse(responseCode = "204", description = "No Content - The request was successful, we created a new resource and the response body does not contains the representation."),
      @ApiResponse(responseCode = "400", description = "Bad Request - The data given in the POST failed validation. Inspect the response body for details."),
      @ApiResponse(responseCode = "401", description = "Unauthorized - The supplied credentials, if any, are not sufficient to access the resource."),
      @ApiResponse(responseCode = "408", description = "Request Timeout"),
      @ApiResponse(responseCode = "409", description = "Conflict - The request could not be processed because of conflict in the request"),
      @ApiResponse(responseCode = "429", description = "Too Many Requests - Your application is sending too many simultaneous requests."),
      @ApiResponse(responseCode = "500", description = "Internal Server Error - We couldn't create the resource. Please try again."),
      @ApiResponse(responseCode = "503", description = "Service Unavailable - We are temporarily unable. Please wait for a bit and try again. ")
    }
  )
  @PostMapping(
    path = "/fooes",
    produces = {APPLICATION_JSON_VALUE}
  )
  public ResponseEntity<Void> send(@Parameter @Valid @RequestBody FooDTO dto, HttpServletRequest request, UriComponentsBuilder ucBuilder) {
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
