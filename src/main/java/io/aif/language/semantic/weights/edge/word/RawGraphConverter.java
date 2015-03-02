package io.aif.language.semantic.weights.edge.word;

import io.aif.language.word.IWord;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.OptionalDouble;

// TODO Change name to something more specific eg: EdgeValueReducer
class RawGraphConverter {

    public static final Map<IWord, Map<IWord, Double>> recalculateConnections(final Map<IWord, Map<IWord, List<Double>>> distancesGraph) {
        final Map<IWord, Map<IWord, Double>> results = new HashMap<>();
        distancesGraph.keySet().stream().forEach(key -> {
            results.put(key, new HashMap<>());
            distancesGraph
                    .get(key)
                    .keySet()
                    .forEach(innerKey -> {
                        results.get(key).put(innerKey, distancesGraph.get(key).get(innerKey).stream().mapToDouble(x -> x).sum());
                    });
        });
        final Double maxConnection = results.keySet().stream().mapToDouble(key -> {
            final OptionalDouble optMax = results.get(key).keySet().stream().mapToDouble(results.get(key)::get).max();
            if (optMax.isPresent()) return optMax.getAsDouble();
            return .0;
        }).max().getAsDouble();

        results.keySet().forEach(key -> {
            results.get(key).keySet().forEach(innerKey -> {
                results.get(key).put(innerKey, results.get(key).get(innerKey) / maxConnection);
            });
        });

        return results;
    }

}
