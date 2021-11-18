package nextstep.subway.line.dto;

import nextstep.subway.line.domain.Line;
import nextstep.subway.line.domain.Section;
import nextstep.subway.line.domain.Sections;
import nextstep.subway.station.domain.Station;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class LineResponse {
    private Long id;
    private String name;
    private String color;
    private List<Station> stations = new ArrayList<>();
    private LocalDateTime createdDate;
    private LocalDateTime modifiedDate;

    private LineResponse() {
    }

    private LineResponse(Long id, String name, String color,Sections sections, LocalDateTime createdDate, LocalDateTime modifiedDate ) {
            this.id = id;
            this.name = name;
            this.color = color;
            this.stations = sections.getStations();
            this.createdDate = createdDate;
            this.modifiedDate = modifiedDate;
    }

    public static LineResponse from(Line line) {
        return new LineResponse(line.getId(), line.getName(), line.getColor(), line.getSections(), line.getCreatedDate(), line.getModifiedDate());
    }

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }


}
