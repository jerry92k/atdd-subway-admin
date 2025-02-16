package nextstep.subway.station.ui;

import nextstep.subway.station.application.StationService;
import nextstep.subway.station.dto.StationRequest;
import nextstep.subway.station.dto.StationResponse;

import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/stations")
public class StationController {
	private StationService stationService;

	public StationController(StationService stationService) {
		this.stationService = stationService;
	}

	@PostMapping
	public ResponseEntity<StationResponse> createStation(@RequestBody StationRequest stationRequest) {
		StationResponse station = stationService.saveStation(stationRequest);
		return ResponseEntity.created(URI.create("/stations/" + station.getId())).body(station);
	}

	@GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<List<StationResponse>> showStations() {
		return ResponseEntity.ok().body(stationService.findAllStations());
	}

	@GetMapping(path = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<StationResponse> showStation(@PathVariable Long id) {
		return ResponseEntity.ok().body(stationService.findStationById(id));
	}

	@DeleteMapping("/{id}")
	public ResponseEntity<Void> deleteStation(@PathVariable Long id) {
		stationService.deleteStationById(id);
		return ResponseEntity.noContent().build();
	}
}
