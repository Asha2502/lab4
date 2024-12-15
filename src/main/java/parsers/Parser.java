package parsers;

import generated.Medicine;

import java.util.List;

@FunctionalInterface
public interface Parser {
    List<Medicine.Drug> parse(String filePath);
}
