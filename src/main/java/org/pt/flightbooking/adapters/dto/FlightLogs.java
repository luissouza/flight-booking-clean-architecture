package org.pt.flightbooking.adapters.dto;

import lombok.*;

import java.time.LocalDateTime;

@Builder
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class FlightLogs {

	public String flyTo;
	public String currency;
	public String dateTo;
	public String dateFrom;
	public LocalDateTime recordDateTime;


}