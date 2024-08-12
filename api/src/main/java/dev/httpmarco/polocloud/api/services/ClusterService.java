package dev.httpmarco.polocloud.api.services;

import dev.httpmarco.polocloud.api.Detail;
import dev.httpmarco.polocloud.api.Named;
import dev.httpmarco.polocloud.api.groups.ClusterGroup;

import java.util.List;
import java.util.UUID;

public interface ClusterService extends Named, Detail {

    ClusterGroup group();

    int orderedId();

    UUID id();

    int port();

    String hostname();

    String runningNode();

    void shutdown();

    void executeCommand(String command);

    ClusterServiceState state();

    List<String> logs();

    void update();

    @Override
    default String name() {
        return group().name() + "-" + orderedId();
    }
}
