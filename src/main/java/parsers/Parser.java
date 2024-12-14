package parsers;

import generated.Medicine;

import java.util.List;

public interface Parser {
    List<Medicine.Drug> parse(String filePath);
}
