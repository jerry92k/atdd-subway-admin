package nextstep.subway.line.application;

import nextstep.subway.exception.NotFoundException;
import nextstep.subway.line.domain.Line;
import nextstep.subway.line.domain.LineRepository;
import nextstep.subway.line.dto.LineRequest;
import nextstep.subway.line.dto.LineResponse;
import nextstep.subway.section.domain.Section;
import nextstep.subway.section.dto.SectionRequest;
import nextstep.subway.station.domain.Station;
import nextstep.subway.station.domain.StationRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
public class LineService {
    private LineRepository lineRepository;
    private StationRepository stationRepository;

    public LineService(LineRepository lineRepository, StationRepository stationRepository) {
        this.lineRepository = lineRepository;
        this.stationRepository = stationRepository;
    }

    public LineResponse saveLine(LineRequest request) {
        Station upStation =  stationRepository.findById(request.getUpStationId()).orElseThrow(NotFoundException::new);
        Station downStation = stationRepository.findById(request.getDownStationId()).orElseThrow(NotFoundException::new);
        Line persistLine = lineRepository.save(new Line(request.getName(), request.getColor(), upStation, downStation, request.getDistance()));
        return LineResponse.of(persistLine);
    }

    @Transactional(readOnly = true)
    public List<LineResponse> findAllLines() {
        List<Line> lines = lineRepository.findAll();
        return lines.stream()
                .map(line -> LineResponse.of(line))
                .collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public LineResponse findLine(Long id) {
        Line line = lineRepository.findById(id).orElseThrow(NotFoundException::new);
        return LineResponse.of(line);
    }

    public void updateLine(Long id, LineRequest request) {
        Line line = lineRepository.findById(id).orElseThrow(NotFoundException::new);
        line.update(request.toLine());
    }

    public void deleteLineById(Long id) {
        lineRepository.deleteById(id);
    }

    public void addSection(Long id, SectionRequest sectionRequest) {
        Line line = lineRepository.findById(id).orElseThrow(NotFoundException::new);
        Station upStation = stationRepository.findById(sectionRequest.getUpStationId()).orElseThrow(NotFoundException::new);
        Station downStation = stationRepository.findById(sectionRequest.getDownStationId()).orElseThrow(NotFoundException::new);
        line.addSection(new Section(line, upStation, downStation, sectionRequest.getDistance()));
    }

    public void deleteLineStation(Long lineId, Long stationId) {
        Station station = stationRepository.findById(stationId).orElseThrow(NotFoundException::new);
        Line line = lineRepository.findById(lineId).orElseThrow(NotFoundException::new);
        line.deleteSection(station);
    }
}
